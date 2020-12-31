package com.sales.main.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class AppConfig extends HikariConfig {
    public static final String BASE_MAPPER_PACKAGE_PREFIX = "com.sales.main.mapper";
    public static final String DATASOURCE_MAIN = "dataSource1";
    public static final String TRANSACTION_MANAGER_MAIN = "transactionManager1";
    public static final String TRANSACTION_SESSION_FACTORY_MAIN = "sqlSessionFactory1";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


}
