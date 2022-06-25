package cn.fufeii.ds.admin.security.login;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.crud.CrudSystemUserService;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.repository.entity.SystemUser;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

/**
 * jwt校验的认证逻辑
 *
 * @author FuFei
 * @date 2022/3/25
 */
@Slf4j
@Component
public class JwtLoginProvider implements AuthenticationProvider {
    @Autowired
    private CrudSystemUserService crudSystemUserService;
    @Autowired
    private CrudPlatformService crudPlatformService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PlatformUsernamePasswordAuthenticationToken token = (PlatformUsernamePasswordAuthenticationToken) authentication;

        // 验证平台
        String platformUsername = token.getPlatformUsername();
        if (CharSequenceUtil.isNotBlank(platformUsername)) {
            Optional<Platform> platformOptional = crudPlatformService.selectByUsernameOptional(platformUsername);
            if (!platformOptional.isPresent()) {
                throw new BadCredentialsException("平台不存在");
            }
            Platform platform = platformOptional.get();
            if (StateEnum.DISABLE == platform.getState()) {
                throw new BadCredentialsException("平台被禁用");
            }
        }

        // 查找用户
        String username = token.getPrincipal().toString();
        LambdaQueryWrapper<SystemUser> queryWrapper = Wrappers.<SystemUser>lambdaQuery()
                // 保证走索引，并且admin的platformUsername字段的值为空串
                .eq(SystemUser::getPlatformUsername, CharSequenceUtil.isNotBlank(platformUsername) ? platformUsername : CharSequenceUtil.EMPTY)
                .eq(SystemUser::getUsername, username);
        SystemUser systemUser = crudSystemUserService.selectOneOptional(queryWrapper)
                .orElseThrow(() -> new BadCredentialsException(String.format("用户[%s]不存在", username)));
        if (StateEnum.DISABLE == systemUser.getState()) {
            throw new DisabledException(String.format("用户[%s]被禁用", username));
        }

        // 对比账号
        String password = token.getCredentials().toString();
        boolean equals = systemUser.getPassword().equals(SecureUtil.md5(password + systemUser.getSlat()));
        if (!equals) {
            // TODO 限制登录次数
            throw new BadCredentialsException("密码错误");
        }

        return new PlatformUsernamePasswordAuthenticationToken(systemUser, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(PlatformUsernamePasswordAuthenticationToken.class);
    }
}
