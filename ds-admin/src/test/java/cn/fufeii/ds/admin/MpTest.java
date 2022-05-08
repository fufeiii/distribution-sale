package cn.fufeii.ds.admin;

import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.repository.crud.CrudMemberRankConfigService;
import cn.fufeii.ds.repository.entity.MemberRankConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author FuFei
 * @date 2022/3/13
 */
@SpringBootTest
public class MpTest {
    @Autowired
    CrudMemberRankConfigService crudMemberRankConfigService;

    @Test
    public void t0() {
        // where ((this.begin >= begin and this.begin <= end) or (this.end >= begin and this.end <= end)) and member_type != 自己
        LambdaQueryWrapper<MemberRankConfig> queryWrapper = Wrappers.<MemberRankConfig>lambdaQuery()
                .ne(MemberRankConfig::getMemberRankType, MemberRankTypeEnum.BRONZE)
                .and(it -> {
                    it.ge(MemberRankConfig::getBeginPoints, 2).le(MemberRankConfig::getEndPoints, 2)
                            .or()
                            .ge(MemberRankConfig::getBeginPoints, 3).le(MemberRankConfig::getEndPoints, 3);

                });
        crudMemberRankConfigService.exist(queryWrapper);
    }

}
