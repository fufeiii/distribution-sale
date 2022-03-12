package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.AccountRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户记录 Dao
 *
 * @author FuFei
 */
@Mapper
public interface AccountRecordDao extends BaseMapper<AccountRecord> {
}