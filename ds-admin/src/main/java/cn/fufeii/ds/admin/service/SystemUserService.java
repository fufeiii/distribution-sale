package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.SystemUserCreateRequest;
import cn.fufeii.ds.admin.model.vo.request.SystemUserQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.SystemUserResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.crud.CrudSystemUserService;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.repository.entity.SystemUser;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CrudPlatformService crudPlatformService;

    /**
     * 当前用户
     */
    public SystemUserResponse current() {
        SystemUser systemUser = CurrentUserHelper.self();
        SystemUserResponse response = new SystemUserResponse();
        this.setResponse(systemUser, response);
        return response;
    }


    /**
     * 分页查询
     */
    public IPage<SystemUserResponse> page(SystemUserQueryRequest pageParam, IPage<SystemUser> pageable) {
        LambdaQueryWrapper<SystemUser> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, SystemUser.class));
        IPage<SystemUser> selectPage = crudSystemUserService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            SystemUserResponse response = new SystemUserResponse();
            this.setResponse(it, response);
            return response;
        });
    }

    /**
     * 创建用户
     */
    @GlobalLock(key = "#request.platformUsername + ':' + #request.username")
    public void create(SystemUserCreateRequest request) {
        // 不能使用超管账号
        if (DsAdminConstant.ADMIN_USERNAME.equals(request.getUsername())) {
            throw BizException.client("禁止使用系统预留登录名");
        }
        // 填充默认值
        if (CharSequenceUtil.isBlank(request.getNickname())) {
            request.setNickname(request.getUsername());
        }
        if (CharSequenceUtil.isBlank(request.getAvatar())) {
            request.setAvatar(CharSequenceUtil.EMPTY);
        }

        // 查询出平台（需要校验平台的状态吗？）
        Platform platform = crudPlatformService.selectByUsername(request.getPlatformUsername());

        // 查询用户是否已存在，比较特殊的接口，没办法走多租户插件
        LambdaQueryWrapper<SystemUser> queryWrapper = Wrappers.<SystemUser>lambdaQuery()
                .eq(SystemUser::getPlatformUsername, request.getPlatformUsername())
                .eq(SystemUser::getUsername, request.getUsername());
        if (crudSystemUserService.exist(queryWrapper)) {
            throw BizException.client("用户已存在");
        }

        // 创建用户
        SystemUser user = new SystemUser();
        user.setPlatformUsername(platform.getUsername());
        user.setPlatformNickname(platform.getNickname());
        user.setUsername(request.getUsername());
        user.setNickname(request.getNickname());
        user.setAvatar(request.getAvatar());
        String slat = RandomUtil.randomString(8);
        user.setSlat(slat);
        user.setPassword(SecureUtil.md5(request.getPassword() + slat));
        user.setState(StateEnum.ENABLE);
        crudSystemUserService.insert(user);
    }


    /**
     * 设置通用响应字段
     */
    private void setResponse(SystemUser systemUser, SystemUserResponse response) {
        response.setId(systemUser.getId());
        response.setPlatformUsername(systemUser.getPlatformUsername());
        response.setPlatformNickname(systemUser.getPlatformNickname());
        response.setUsername(systemUser.getUsername());
        response.setNickname(systemUser.getNickname());
        response.setAvatar(systemUser.getAvatar());
        response.setState(systemUser.getState().getMessage());
        response.setCreateDateTime(systemUser.getCreateDateTime());
        response.setIsAdmin(CurrentUserHelper.isAdmin(systemUser));
    }


    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        SystemUser systemUser = crudSystemUserService.selectById(id);
        if (DsAdminConstant.ADMIN_USERNAME.equals(systemUser.getUsername())) {
            throw BizException.client("禁止修改超级管理员状态");
        }
        if (stateEnum == systemUser.getState()) {
            throw new BizException(ExceptionEnum.UPDATE_STATE_REPEATEDLY);
        }
        systemUser.setState(stateEnum);
        crudSystemUserService.updateById(systemUser);
    }

}