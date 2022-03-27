package cn.fufeii.ds.admin.security.login;

import cn.fufeii.ds.repository.crud.CrudSysUserService;
import cn.fufeii.ds.repository.entity.SysUser;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * jwt校验的认证逻辑
 *
 * @author FuFei
 * @date 2022/3/25
 */
@Component
public class JwtLoginProvider implements AuthenticationProvider {
    @Autowired
    private CrudSysUserService crudSysUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getPrincipal().toString();

        // 查找用户
        SysUser sysUser = crudSysUserService.selectOneOpt(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, username))
                .orElseThrow(() -> new BadCredentialsException(String.format("用户[%s]不存在", username)));

        // 对比账号
        String password = token.getCredentials().toString();
        boolean equals = sysUser.getPassword().equals(SecureUtil.md5(password + sysUser.getSlat()));
        if (!equals) {
            // TODO 限制登录次数
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(sysUser, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
