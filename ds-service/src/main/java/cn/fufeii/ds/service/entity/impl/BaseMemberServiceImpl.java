package cn.fufeii.ds.service.entity.impl;

import cn.fufeii.ds.repository.dao.MemberDao;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.service.entity.BaseMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 会员信息 ServiceImpl
 *
 * @author FuFei
 */
@Service
public class BaseMemberServiceImpl extends ServiceImpl<MemberDao, Member> implements BaseMemberService {

}