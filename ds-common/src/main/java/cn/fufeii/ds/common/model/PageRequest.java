package cn.fufeii.ds.common.model;

import lombok.Data;

/**
 * 分页请求
 *
 * @author FuFei
 */
@Data
public class PageRequest {

    /**
     * 页码数
     */
    private int page = 1;

    /**
     * 页大小
     */
    private int size = 10;

}
