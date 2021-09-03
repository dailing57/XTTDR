package com.xttdr.utils.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssConfigProperties {
    //地域节点
    private String endpoint;
    //个人域名
    private String domain;
    //bucketName
    private String bucketName;
    private String accessKeyId;
    private String accessKeySecret;

}
