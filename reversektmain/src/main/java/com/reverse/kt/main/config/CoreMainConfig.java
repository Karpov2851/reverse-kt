package com.reverse.kt.main.config;

import com.reverse.kt.core.config.CoreBaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vikas on 02-04-2020.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {CoreMainConfig.CORE_MAIN_PACKAGE_NESTED,CoreMainConfig.CORE_BASE_PACKAGE_NESTED})
@Import({CoreBaseConfig.class,MvcConfig.class})
public class CoreMainConfig {
    static final String CORE_MAIN_PACKAGE_NESTED = "com.reverse.kt.main.*";
    static final String CORE_BASE_PACKAGE_NESTED = "com.reverse.kt.core.*";
}
