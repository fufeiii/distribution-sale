package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.model.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 等级参数 Request
 *
 * @author FuFei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ApiModel
public class MemberRankConfigQueryRequest extends PageRequest {

}