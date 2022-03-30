package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Dao
 *
 * @author FuFei
 */
@Mapper
public interface SystemUserDao extends BaseMapper<SystemUser> {
}