package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员账户 Dao
 *
 * @author FuFei
 */
@Mapper
public interface AccountDao extends BaseMapper<Account> {
}