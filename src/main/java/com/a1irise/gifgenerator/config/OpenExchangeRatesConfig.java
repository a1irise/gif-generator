package com.a1irise.gifgenerator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("properties")
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "openexchangerates")
public class OpenExchangeRatesConfig {

    private String url;
    private String appId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
