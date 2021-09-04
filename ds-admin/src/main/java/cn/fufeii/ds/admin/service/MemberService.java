package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.service.entity.CrudMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员信息 ServiceImpl
 *
 * @author FuFei
 */
@Service
public class MemberService {

    @Autowired
    private CrudMemberService crudMemberService;

    /**
     * 获取CRUD基础类
     */
    public CrudMemberService getCrudMemberService() {
        return crudMemberService;
    }


}