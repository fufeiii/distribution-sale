package cn.fufeii.ds.server.subscribe.event;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import lombok.Data;

/**
 * 金钱交易事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class TradeEvent extends AbstractProfitEvent {

    public TradeEvent(ProfitTypeEnum profitType, Object source) {
        super(profitType, source);
    }

    @Data
    public static class Source {

        /**
         * 会员主键
         */
        private Long memberId;

        /**
         * 交易金额
         */
        private Integer tradeAmount;

        /**
         * 交易编号
         */
        private String tradeNumber;
    }

}
