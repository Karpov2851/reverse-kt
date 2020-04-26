package com.reverse.kt.main.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by vikas on 17-04-2020.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {CoreMainConfig.CORE_MAIN_PACKAGE_NESTED,"com.reverse.kt.core.dao"},excludeFilters =
        {@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=CoreMainConfig.class)})
@Import({DBMainTestConfig.class,MvcConfig.class})
public class CoreMainConfigTest {

}
