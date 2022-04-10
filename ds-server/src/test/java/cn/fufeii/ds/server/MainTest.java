package cn.fufeii.ds.server;

import cn.hutool.core.date.SystemClock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * MainTest
 *
 * @author FuFei
 * @date 2022/4/7
 */
public class MainTest {

    @Test
    public void t0() {
        int i = new BigDecimal("0.53").setScale(0, RoundingMode.HALF_UP).intValue();
        System.out.println("i = " + i);
        long now = SystemClock.now();
        System.out.println("now = " + now);
        System.out.println("now = " + now / 1000);
    }

}
