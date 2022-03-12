package cn.fufeii.ds.repository.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author FuFei
 */
@Component
public class CommonMetaObjectHandler implements MetaObjectHandler {

    private static final String VERSION_FIELD_NAME = "version";
    private static final String CREATE_DATE_TIME_FIELD_NAME = "createDateTime";
    private static final String UPDATE_DATE_TIME_FIELD_NAME = "updateDateTime";
    private static final int DEFAULT_VERSION = 0;

    @Override
    public void insertFill(MetaObject metaObject) {
        // 版本号
        this.strictInsertFill(metaObject, VERSION_FIELD_NAME, Integer.class, DEFAULT_VERSION);
        Date now = new Date();
        // 创建时间
        this.strictInsertFill(metaObject, CREATE_DATE_TIME_FIELD_NAME, Date.class, now);
        // 更新时间
        this.strictInsertFill(metaObject, UPDATE_DATE_TIME_FIELD_NAME, Date.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间
        this.strictInsertFill(metaObject, UPDATE_DATE_TIME_FIELD_NAME, Date.class, new Date());
    }

}