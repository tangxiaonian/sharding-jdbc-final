package com.tang.dbtable.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @Classname DatabaseShardingRule
 * @Description [ 数据库分片规则 ]
 * @Author Tang
 * @Date 2020/1/11 9:25
 * @Created by ASUS
 */
public class DatabaseShardingRule implements PreciseShardingAlgorithm<Long> {

    private static Logger logger = LoggerFactory.getLogger(DatabaseShardingRule.class);

    /**
     *@MethodName doSharding
     *@Description [ availableTargetNames 可用到的所有数据库名，shardingValue 分库列字段的值 ]
     *@Date 2020/1/11 9:28
     *@Param [availableTargetNames:传递过来所有可用的数据库名的集合, shardingValue:当前分片字段的值]
     *@return 返回值为 Master数据库的名字
     **/
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        logger.info("分库算法{}{}",availableTargetNames,shardingValue);

        if (shardingValue.getValue() < 20L) {
            return "master_0";
        }
        return "master_1";

//        return "db_" + shardingValue.getValue() % availableTargetNames.size();
    }
}