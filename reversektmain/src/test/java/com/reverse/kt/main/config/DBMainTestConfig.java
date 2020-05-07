package com.reverse.kt.main.config;

import com.reverse.kt.core.config.AbstractDBConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.inject.Inject;
import javax.naming.NameNotFoundException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by vikas on 24-04-2020.
 */
@Configuration
@PropertySource("classpath:test_main.properties")
public class DBMainTestConfig extends AbstractDBConfig {

    @Inject
    public Environment env;

    @Override
    public DataSource getDataSource() throws NameNotFoundException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername("sa");
        dataSource.setPassword("sa");
        return dataSource;
    }

    @Override
    public Properties getHibernateProperties(){
        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        props.put("javax.persistence.schema-generation.database.action",env.getProperty("generate-schema"));
        return props;
    }
}
