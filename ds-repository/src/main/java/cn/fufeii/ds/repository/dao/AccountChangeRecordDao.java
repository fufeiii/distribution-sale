package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.AccountChangeRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 账户变动记录 Dao
 *
 * @author FuFei
 */
@Mapper
public interface AccountChangeRecordDao extends BaseMapper<AccountChangeRecord> {
}