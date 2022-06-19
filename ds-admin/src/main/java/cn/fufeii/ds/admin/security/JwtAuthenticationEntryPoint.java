package cn.fufeii.ds.admin.security;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 一般到这里都是发现权限不足或者认证有问题, 所以不需要再次认证了
        log.warn("请求:[{} {}], 异常:[{}]", request.getMethod(), request.getServletPath(), authException.getMessage());
        // 响应
        ResponseUtil.write(response, CommonResult.fail(ExceptionEnum.LOGIN_IN_ERROR.getCode(), "登录异常: " + authException.getMessage()));
    }

}
