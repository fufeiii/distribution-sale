package cn.fufeii.ds.server.security;

import lombok.Data;

/**
 * 鉴权实体
 *
 * @author FuFei
 * @date 2022/4/5
 */
@Data
public class AuthorizationParam {
    /**
     * 平台主键
     */
    private Long pid;

    /**
     * 签名
     */
    private String signature;

}
