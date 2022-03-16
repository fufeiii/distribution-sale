package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.RankParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.RankParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.RankParamResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudRankParamService;
import cn.fufeii.ds.repository.entity.RankParam;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润参数 Service Impl
 * 业务实现
 *
 * @author FuFei
 */
@Service
public class RankParamService {

    @Autowired
    private CrudRankParamService crudRankParamService;

    /**
     * 分页查询
     */
    public IPage<RankParamResponse> page(RankParamQueryRequest pageParam, IPage<RankParam> pageable) {
        Wrapper<RankParam> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, RankParam.class));
        IPage<RankParam> selectPage = crudRankParamService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            RankParamResponse response = new RankParamResponse();
            response.setId(it.getId());
            response.setMemberRankType(it.getMemberRankType().getMessage());
            response.setBeginIntegral(it.getBeginIntegral());
            response.setEndIntegral(it.getEndIntegral());
            response.setCreateDateTime(it.getCreateDateTime());
            response.setUpdateDateTime(it.getUpdateDateTime());
            return response;
        });
    }

    /**
     * 获取
     */
    public RankParamResponse get(Long id) {
        RankParam rankParam = crudRankParamService.selectById(id);
        RankParamResponse response = new RankParamResponse();
        response.setId(rankParam.getId());
        response.setMemberRankType(rankParam.getMemberRankType().name());
        response.setBeginIntegral(rankParam.getBeginIntegral());
        response.setEndIntegral(rankParam.getEndIntegral());
        response.setCreateDateTime(rankParam.getCreateDateTime());
        response.setUpdateDateTime(rankParam.getUpdateDateTime());
        return response;
    }

    /**
     * 保存
     */
    public void create(RankParamUpsertRequest addParam) {
        RankParam rankParam = new RankParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(addParam, rankParam);
        crudRankParamService.insertOrUpdate(rankParam);
    }

    /**
     * 更新
     */
    public void modify(RankParamUpsertRequest modifyParam) {
        RankParam rankParam = new RankParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(modifyParam, rankParam);
        crudRankParamService.insertOrUpdate(rankParam);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        crudRankParamService.deleteById(id);
    }

}