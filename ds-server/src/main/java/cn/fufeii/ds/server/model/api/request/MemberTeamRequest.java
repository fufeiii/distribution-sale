package cn.fufeii.ds.server.model.api.request;

import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import lombok.Data;

/**
 * 会员团队（会员树）
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Data
public class MemberTeamRequest {
    private ProfitLevelEnum level;
    private String username;
}
