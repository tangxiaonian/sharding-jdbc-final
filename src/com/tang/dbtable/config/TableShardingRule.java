package com.tang.dbtable.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @Classname TableShardingRule
 * @Description [ 表分片规则 ]
 * @Author Tang
 * @Date 2020/1/11 9:38
 * @Created by ASUS
 */
public class TableShardingRule implements PreciseShardingAlgorithm<Long> {

    private static Logger logger = LoggerFactory.getLogger(TableShardingRule.class);

    /**
     *@MethodName doSharding
     *@Description [ 表分片算法 ]
     *@Date 2020/1/11 22:47
     *@Param [availableTargetNames: 传递过来所有可用的表名集合, shardingValue：当前分片字段的值]
     *@return
     **/
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {

        logger.info("分表算法{}{}",availableTargetNames,shardingValue);

        return "goods_" + shardingValue.getValue() % availableTargetNames.size();
    }
}