package cn.fufeii.ds.admin.security;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.result.CommonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

;

/**
 * 处理{@link org.springframework.security.web.access.ExceptionTranslationFilter}过滤器后面的过滤器产生的以下异常
 * {@link org.springframework.security.access.AccessDeniedException}
 * （会被包装成{@link org.springframework.security.authentication.InsufficientAuthenticationException}异常）
 * {@link AuthenticationException}
 *
 * @author FuFei
 * @date 2022/3/25
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 打印日志，一般到这里都是ExceptionTranslationFilter发现权限不足或者认证有问题
        log.warn("请求:[{} {}], 异常:[{}]", request.getMethod(), request.getServletPath(), authException.getMessage());
        // 响应
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(CommonResult.fail(ExceptionEnum.JWT_ERROR)));
    }

}
