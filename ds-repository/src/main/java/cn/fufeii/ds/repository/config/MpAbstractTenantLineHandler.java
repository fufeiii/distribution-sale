package cn.fufeii.ds.repository.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

import java.util.Arrays;
import java.util.List;

/**
 * mp租户插件的处理器抽象类
 * 主要是为了满足admin和server服务能够动态使用这个租户插件
 *
 * @author FuFei
 */
public abstract class MpAbstractTenantLineHandler implements TenantLineHandler {

    /**
     * 需要忽略的表，这些表中没有平台标识
     * ds_system_user中用户标识全局唯一，所以不走多租户插件
     * ds_platform中租户字段是username，所以不走多租户插件
     * 上述两张表的，自行在service中增加租户字段
     */
    private static final List<String> IGNORE_TABLE = Arrays.asList("ds_account", "ds_account_change_record",
            "ds_platform", "ds_profit_income_record", "ds_system_user", "liquibase_changelog", "liquibase_changelog_lock");

    @Override
    public final Expression getTenantId() {
        return new StringValue(this.getPlatformUsername());
    }

    @Override
    public final String getTenantIdColumn() {
        // 出于项目定位考虑，使用[platform_username](字符串类型)
        // 可以减轻项目运维压力(当需要查找数据的时候和导出数据的时候，不用去记住ID，因为每个环境的ID都不会一样)
        // 且合理设计此字段，效率不会比使用ID效率低
        return "platform_username";
    }

    @Override
    public final boolean ignoreTable(String tableName) {
        if (IGNORE_TABLE.stream().anyMatch(tableName::equals)) {
            return true;
        }
        return this.canIgnorePlatformUsername();
    }

    /**
     * 能够忽略租户标识
     */
    protected abstract boolean canIgnorePlatformUsername();

    /**
     * 获取平台标识
     */
    protected abstract String getPlatformUsername();

}
