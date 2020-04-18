package com.reverse.kt.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by vikas on 02-04-2020.
 */
@Configuration
@EnableTransactionManagement
@Import(CoreDBConfig.class)
public class CoreBaseConfig {
    static final String CORE_MODEL_PACKAGE = "com.reverse.kt.core.model";
}
