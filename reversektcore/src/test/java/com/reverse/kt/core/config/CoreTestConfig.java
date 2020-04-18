package com.reverse.kt.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by vikas on 04-04-2020.
 */
@Configuration
@ComponentScan(basePackages = "com.reverse.kt.core.*")
@Import(DBTestConfig.class)
public class CoreTestConfig {

}
