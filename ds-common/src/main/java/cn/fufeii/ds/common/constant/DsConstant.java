package cn.fufeii.ds.common.constant;

/**
 * 常量类
 *
 * @author FuFei
 * @date 2022/3/20
 */
public final class DsConstant {

    public static final String[] KNIFE4J_URL = {"/favicon.ico", "/doc.html", "/webjars/**", "/swagger-resources", "/v3/api-docs"};

    public static final String REDIS_NAMESPACE = "ds:";
    public static final String REDIS_GL_NAMESPACE = REDIS_NAMESPACE + "gl:";

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTHORIZATION_PREFIX = "Bearer ";

    public static final Long NULL_MEMBER_INVITER_ID = 0L;

}
