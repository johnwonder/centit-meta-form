package com.centit.metaform.config;

import com.centit.framework.config.BaseSpringMvcConfig;
import com.centit.framework.config.WebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * Created by zou_wy on 2017/3/29.
 */
@Configuration
@Import(WebConfig.class)
@ComponentScan(basePackages = {"com.centit.metaform.controller", "com.centit.product.metadata.controller"},
        includeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value= org.springframework.stereotype.Controller.class)},
        useDefaultFilters = false)
public class NormalSpringMvcConfig extends BaseSpringMvcConfig {

}
