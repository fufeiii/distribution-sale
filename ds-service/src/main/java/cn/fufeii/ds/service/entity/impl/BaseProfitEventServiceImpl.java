package cn.fufeii.ds.service.entity.impl;

import cn.fufeii.ds.repository.dao.ProfitEventDao;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import cn.fufeii.ds.service.entity.BaseProfitEventService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 分润事件 ServiceImpl
 *
 * @author FuFei
 */
@Service
public class BaseProfitEventServiceImpl extends ServiceImpl<ProfitEventDao, ProfitEvent> implements BaseProfitEventService {

}