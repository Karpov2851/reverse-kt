package com.reverse.kt.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.inject.Inject;
import javax.naming.NameNotFoundException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by vikas on 02-04-2020.
 */
@Configuration
@Import(CorePropertyConfig.class)
public class CoreDBConfig extends AbstractDBConfig{

    @Inject
    public Environment env;

    @Override
    public DataSource getDataSource() throws NameNotFoundException{
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource(env.getProperty("jndi.namespace"));
        return dataSource;
    }

    @Override
    public Properties getHibernateProperties(){
        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return props;
    }
}
