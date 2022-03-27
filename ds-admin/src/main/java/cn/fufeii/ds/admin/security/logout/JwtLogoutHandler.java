package cn.fufeii.ds.admin.security.logout;

import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 注销jwt
 *
 * @author FuFei
 * @date 2022/3/27
 */
@Slf4j
@Component
public class JwtLogoutHandler implements LogoutHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String jwt = Optional.ofNullable(request.getHeader(DsConstant.HEADER_AUTHORIZATION))
                .map(it -> it.replaceFirst(DsConstant.HEADER_AUTHORIZATION_PREFIX, CharSequenceUtil.EMPTY))
                .orElse(null);
        log.info("logout current jwt is {}", jwt);
        // 通过token黑名单实现
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(CommonResult.fail(ExceptionEnum.SUCCESS)));
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.debug("logout success");
    }

}
