package com.reverse.kt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.naming.NameNotFoundException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by vikas on 02-04-2020.
 */
public abstract class AbstractDBConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean getSessionFactory() throws NameNotFoundException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(getDataSource());
        factoryBean.setPackagesToScan(new String[] { CoreBaseConfig.CORE_MODEL_PACKAGE });
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(getHibernateProperties());
        return factoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }

    @Bean
    @Inject
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }

    abstract DataSource getDataSource() throws NameNotFoundException;

    abstract Properties getHibernateProperties();
}
