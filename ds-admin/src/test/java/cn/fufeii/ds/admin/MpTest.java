package cn.fufeii.ds.admin;

import cn.fufeii.ds.repository.crud.CrudProfitParamService;
import cn.fufeii.ds.repository.entity.ProfitParam;
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
    CrudProfitParamService crudProfitParamService;

    @Test
    public void t0() {
        ProfitParam profitParam = crudProfitParamService.selectById(1502919209045798914L);
        System.out.println("profitParam = " + profitParam);
    }

}
