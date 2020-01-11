package com.tang.dbtable.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Classname DataSourceConfig
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2020/1/11 16:52
 * @Created by ASUS
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "hikariConfigMaster0")
    @ConfigurationProperties(prefix = "spring.datasource.database0-master")
    public HikariConfig hikariConfigMaster0() {
        return new HikariConfig();
    }

    @Bean(name = "hikariConfigSlave0")
    @ConfigurationProperties(prefix = "spring.datasource.database0-slave")
    public HikariConfig hikariConfigSlave0() {
        return new HikariConfig();
    }

    @Bean(name = "hikariConfigMaster1")
    @ConfigurationProperties(prefix = "spring.datasource.database1-master")
    public HikariConfig hikariConfigMaster1() {
        return new HikariConfig();
    }

    @Bean(name = "hikariConfigSlave1")
    @ConfigurationProperties(prefix = "spring.datasource.database1-slave")
    public HikariConfig hikariConfigSlave1() {
        return new HikariConfig();
    }

    @Bean("dataSourceMaster0")
    public DataSource dataSourceMaster0(
            @Qualifier(value = "hikariConfigMaster0") HikariConfig hikariConfigMaster0) {
        return new HikariDataSource(hikariConfigMaster0);
    }

    @Bean("dataSourceSlave0")
    public DataSource dataSourceSlave0(
            @Qualifier(value = "hikariConfigSlave0") HikariConfig hikariConfigSlave0) {
        return new HikariDataSource(hikariConfigSlave0);
    }

    @Bean("dataSourceMaster1")
    public DataSource dataSourceMaster1(
            @Qualifier(value = "hikariConfigMaster1") HikariConfig hikariConfigMaster1) {
        return new HikariDataSource(hikariConfigMaster1);
    }

    @Bean("dataSourceSlave1")
    public DataSource dataSourceSlave1(
            @Qualifier(value = "hikariConfigSlave1") HikariConfig hikariConfigSlave1) {
        return new HikariDataSource(hikariConfigSlave1);
    }


}