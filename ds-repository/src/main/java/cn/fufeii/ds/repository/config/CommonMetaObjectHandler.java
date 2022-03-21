package cn.fufeii.ds.repository.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.Setter;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 通用字段处理器
 *
 * @author FuFei
 */
@Setter
@Component
@ConfigurationProperties(prefix = "mybatis-plus.entity")
public class CommonMetaObjectHandler implements MetaObjectHandler {
    private String versionFieldName = "version";
    private Integer versionFieldValue = 0;
    private String createDateTimeFieldName = "createDateTime";
    private String updateDateTimeFieldName = "updateDateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        // 版本号
        this.strictInsertFill(metaObject, versionFieldName, Integer.class, versionFieldValue);
        Date now = new Date();
        // 创建时间
        this.strictInsertFill(metaObject, createDateTimeFieldName, Date.class, now);
        // 更新时间
        this.strictInsertFill(metaObject, updateDateTimeFieldName, Date.class, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间
        this.strictInsertFill(metaObject, updateDateTimeFieldName, Date.class, new Date());
    }

}