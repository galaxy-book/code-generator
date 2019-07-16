package org.nico.codegenerator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {

    public static int pageMaxLength = 20;

    @Value("${app.pageMaxLength}")
    public void setPageMaxLength(int pageMaxLength) {
        BaseConfig.pageMaxLength = pageMaxLength;
    }
    
}
