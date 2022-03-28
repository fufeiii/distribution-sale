package cn.fufeii.ds.admin.security.logout;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.constant.DsConstant;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 注销jwt
 *
 * @author FuFei
 * @date 2022/3/27
 */
@Slf4j
@Component
public class JwtLogoutHandler implements LogoutHandler {
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwtStr = Optional.ofNullable(request.getHeader(DsConstant.HEADER_AUTHORIZATION))
                .map(it -> it.replaceFirst(DsConstant.HEADER_AUTHORIZATION_PREFIX, CharSequenceUtil.EMPTY))
                .orElse(null);
        log.info("logout current jwt is {}", jwtStr);
        if (jwtStr == null) {
            log.error("缺少jwt信息：请求IP为{}", request.getRemoteAddr());
            return;
        }
        // 制作token黑名单
        long ttl = ((Integer) JWT.of(jwtStr).getPayload(JWT.EXPIRES_AT)) - (System.currentTimeMillis() / 1000);
        String signature = StrUtil.split(jwtStr, CharUtil.DOT).get(2);
        redissonClient.getBucket(DsAdminConstant.REDIS_JWT_PREFIX + signature).set(1, ttl, TimeUnit.SECONDS);
    }

}
