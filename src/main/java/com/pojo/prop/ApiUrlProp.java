package com.pojo.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "project.api-url")
public class ApiUrlProp {
    private String singleStock;
    private String singleOdd;
    private String multiStock;
    private String multiOdd;
}
