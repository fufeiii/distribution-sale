package cn.fufeii.ds.portal.model.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建会员
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Data
@ApiModel
public class MemberCreateResponse {

    @ApiModelProperty(value = "会员主键")
    private Long memberId;

}
