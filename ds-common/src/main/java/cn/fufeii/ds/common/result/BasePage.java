package cn.fufeii.ds.common.result;

import lombok.Data;

/**
 * 基础分页
 *
 * @author FuFei
 * @date 2021/9/6
 */
@Data
public class BasePage {

    /**
     * 页码数
     */
    private int page = 1;

    /**
     * 页大小
     */
    private int size = 10;

}
