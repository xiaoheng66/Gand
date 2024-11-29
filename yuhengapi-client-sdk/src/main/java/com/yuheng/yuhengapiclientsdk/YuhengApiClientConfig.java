package com.yuheng.yuhengapiclientsdk;

import com.yuheng.yuhengapiclientsdk.client.YuhengApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("yuhengapi.client")
@Data
@ComponentScan
public class YuhengApiClientConfig {
    private String accessKey;

    private String secretKey;


    @Bean
    public YuhengApiClient yuhengApiClient(){
        return new YuhengApiClient(accessKey,secretKey);

    }

}
