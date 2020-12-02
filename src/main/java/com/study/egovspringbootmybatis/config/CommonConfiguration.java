package com.study.egovspringbootmybatis.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author skysoo
 * @version 1.0.0
 * @since 2020-06-23 오후 1:26
 **/
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "common")
public class CommonConfiguration {
    private String filePath;
    private String fileSavePath;
}