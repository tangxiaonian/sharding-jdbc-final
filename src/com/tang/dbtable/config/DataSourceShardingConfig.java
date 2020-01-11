package com.tang.dbtable.config;

import com.google.common.collect.Lists;
import io.shardingsphere.api.algorithm.masterslave.RoundRobinMasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @ Classname DataSourceConfig
 * @ Description [ TODO ]
 * @ Author Tang
 * @ Date 2020/1/10 23:38
 * @ Created by ASUS
 *  ASUS
 */
@Configuration
public class DataSourceShardingConfig {

    @Qualifier("dataSourceMaster0")
    @Resource
    private DataSource dataSourceMaster0;

    @Qualifier("dataSourceSlave0")
    @Resource
    private DataSource dataSourceSlave0;

    @Qualifier("dataSourceMaster1")
    @Resource
    private DataSource dataSourceMaster1;

    @Qualifier("dataSourceSlave1")
    @Resource
    private DataSource dataSourceSlave1;

    private Map<String, DataSource> createDataSourceMap() {

        Map<String, DataSource> result = new HashMap<>(16);

        result.put("ds_m0", dataSourceMaster0);
        result.put("ds_s0", dataSourceSlave0);
        result.put("ds_m1", dataSourceMaster1);
        result.put("ds_s1", dataSourceSlave1);

        return result;
    }

    @Bean("dataSource")
    public DataSource dataSource()
            throws SQLException {
        return buildDataSource();
    }

    /**
     *@ MethodName buildDataSource
     *@ Description [ 构建数据源 ]
     *@ Date 2020/1/11 9:53
     *@ Param [dataSource0, dataSource1]
     *@ return
     **/
    private DataSource buildDataSource() throws SQLException {

//      创建分片规则类
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

//      默认数据源名称
        shardingRuleConfig.setDefaultDataSourceName("ds_m0");

//       分表的规则
        shardingRuleConfig.getTableRuleConfigs().add(getTableRule01());

        shardingRuleConfig.getBindingTableGroups().add("goods");

//        可以配置默认的规则
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration());
//        shardingRuleConfig.getDefaultTableShardingStrategyConfig();

//       设置读写分离配置
        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());

//      设置  其他一些属性配置  详细查看 ShardingPropertiesConstant 类
        Properties properties = new Properties();
//      显示sql日志
        properties.setProperty("sql.show", "true");

//       构建数据源
        return ShardingDataSourceFactory.createDataSource(
                createDataSourceMap(),
                shardingRuleConfig,
                new HashMap<>(16), properties);
    }

    /**
     *@ MethodName getMasterSlaveRuleConfigurations
     *@ Description [ 读写分离配置 ]
     *@ Date 2020/1/11 16:46
     *@ Param []
     *@ return
     **/
    private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {

        // 配置 master - slave 指定负载均衡算法    name：逻辑数据库名  对应 真实数据库名
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration(
                "master_0", "ds_m0",
                Collections.singletonList("ds_s0"),new RoundRobinMasterSlaveLoadBalanceAlgorithm());

        // 配置 master - slave
        MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration(
                "master_1", "ds_m1",
                Collections.singletonList("ds_s1"),new RoundRobinMasterSlaveLoadBalanceAlgorithm());

        return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
    }

    /**
     *@ MethodName getTableRule01
     *@ Description [ 配置goods分表配置  多个表就配置多个]
     *@ Date 2020/1/11 9:22
     *@ Param []
     *@ return
     **/
    private TableRuleConfiguration getTableRule01() {
        // 创建表规则配置
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();

        // 配置逻辑表名
        tableRuleConfiguration.setLogicTable("goods");

        // 配置goods表规则   逻辑数据库名[序号].真实表[序号]
        tableRuleConfiguration.setActualDataNodes("master_${0..1}.goods_${[0,1]}");

        // 配置数据库 分库规则
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("goods_id", new DatabaseShardingRule())
        );

        // 配置表  分表规则
        tableRuleConfiguration.setTableShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("goods_type",new TableShardingRule())
        );

        return tableRuleConfiguration;
    }

    /**
     *@MethodName sqlSessionFactory
     *@Description [ 重新配置sqlSessionFactory ]
     *@Date 2020/1/11 17:27
     *@Param [dataSource]
     *@return
     **/
    @Bean
    SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();

        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:/mapper/*.xml"));

        return sessionFactoryBean.getObject();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }

}