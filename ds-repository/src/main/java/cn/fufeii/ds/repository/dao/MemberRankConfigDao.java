package cn.fufeii.ds.repository.dao;

import cn.fufeii.ds.repository.entity.MemberRankConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员段位配置 Dao
 *
 * @author FuFei
 */
@Mapper
public interface MemberRankConfigDao extends BaseMapper<MemberRankConfig> {
}