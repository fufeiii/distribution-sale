package cn.fufeii.ds.common.exception;

/**
 * @author FuFei
 * @date 2021/8/22
 */

import cn.fufeii.ds.common.util.CommonResult;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 将未知错误异常转换成标准内容
 * 目前只处理了404的情况
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions attributeOptions) {

        // 1.先获取spring默认的返回内容
        Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, attributeOptions);

        // 2.如果返回的是404 http状态码
        Integer status = (Integer) defaultErrorAttributes.get("status");
        if (status.equals(HttpStatus.NOT_FOUND.value())) {
            Object path = defaultErrorAttributes.get("path");
            return BeanUtil.beanToMap(CommonResult.fail(400, "path:[" + path + "] is invalid"));
        }

        // 3.无法确定的不处理
        return defaultErrorAttributes;
    }

}
