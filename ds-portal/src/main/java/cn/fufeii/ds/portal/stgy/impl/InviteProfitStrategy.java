package cn.fufeii.ds.portal.stgy.impl;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @author FuFei
 * @date 2022/3/22
 */
@Service
public class InviteProfitStrategy extends AbstractProfitStrategy {
    @Override
    public boolean match(ProfitTypeEnum profitType) {
        return false;
    }

    @Override
    public void profit() {

    }
}
