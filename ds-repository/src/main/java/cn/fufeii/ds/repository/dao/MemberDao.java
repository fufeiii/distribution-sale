package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员信息 Dao
 *
 * @author FuFei
 */
@Mapper
public interface MemberDao extends BaseMapper<Member> {
}