package cn.fufeii.ds.server.model.api.request;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import lombok.Data;

/**
 * AllotEventNotifyDTO
 *
 * @author FuFei
 * @date 2022/4/23
 */
@Data
public class AllotEventNotifyRequest {

    /**
     * 分润类型
     */
    private ProfitTypeEnum profitType;

    /**
     * 事件触发会员主键
     */
    private Long triggerMemberId;

    /**
     * 事件编号
     */
    private String eventNumber;

    /**
     * 事件金额
     */
    private Integer eventAmount;

    /**
     * 备注
     */
    private String memo;

}
