package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.response.SysUserResponse;
import cn.fufeii.ds.repository.crud.CrudSysUserService;
import cn.fufeii.ds.repository.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 系统用户 Service
 *
 * @author FuFei
 */
@Service
public class SysUserService {

    @Autowired
    private CrudSysUserService crudSysUserService;

    /**
     * 当前用户
     */
    public SysUserResponse current() {
        SysUser sysUser = (SysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUserResponse response = new SysUserResponse();
        response.setId(sysUser.getId());
        response.setPlatformId(sysUser.getPlatformId());
        response.setPlatformUsername(sysUser.getPlatformUsername());
        response.setUsername(sysUser.getUsername());
        response.setNickname(sysUser.getNickname());
        response.setAvatar(sysUser.getAvatar());
        return response;
    }

}