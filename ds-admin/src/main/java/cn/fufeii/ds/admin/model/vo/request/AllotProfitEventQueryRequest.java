package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.model.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 分润事件 Request
 *
 * @author FuFei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ApiModel
public class AllotProfitEventQueryRequest extends PageRequest {

    @ApiModelProperty(value = "事件触发会员主键")
    private Long triggerMemberId;

}