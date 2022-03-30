package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.SystemUserQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.SystemUserResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudSystemUserService;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 系统用户 Service
 *
 * @author FuFei
 */
@Service
public class SystemUserService {

    @Autowired
    private CrudSystemUserService crudSystemUserService;

    /**
     * 当前用户
     */
    public SystemUserResponse current() {
        SystemUser systemUser = (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SystemUserResponse response = new SystemUserResponse();
        this.setResponse(systemUser, response);
        return response;
    }


    /**
     * 分页查询
     */
    public IPage<SystemUserResponse> page(SystemUserQueryRequest pageParam, IPage<SystemUser> pageable) {
        Wrapper<SystemUser> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, SystemUser.class));
        IPage<SystemUser> selectPage = crudSystemUserService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            SystemUserResponse response = new SystemUserResponse();
            this.setResponse(it, response);
            return response;
        });
    }


    private void setResponse(SystemUser systemUser, SystemUserResponse response) {
        response.setId(systemUser.getId());
        response.setPlatformUsername(systemUser.getPlatformUsername());
        response.setPlatformNickname(systemUser.getPlatformNickname());
        response.setUsername(systemUser.getUsername());
        response.setNickname(systemUser.getNickname());
        response.setAvatar(systemUser.getAvatar());
        response.setState(systemUser.getState().getMessage());
    }

}