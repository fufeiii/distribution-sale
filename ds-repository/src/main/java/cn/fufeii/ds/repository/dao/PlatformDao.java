package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.Platform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 平台信息 Dao
 *
 * @author FuFei
 */
@Mapper
public interface PlatformDao extends BaseMapper<Platform> {
}