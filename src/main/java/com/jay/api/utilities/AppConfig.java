package com.jay.api.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("file:config.properties")
public class AppConfig {
    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }
    @Bean
    public String companyName() {
        return environment.getProperty("companyName");
    }
    @Bean
    public String address() {
        return environment.getProperty("address");
    }
    @Bean
    public String gstin() {
        return environment.getProperty("gstin");
    }
    @Bean
    public String state() {
        return environment.getProperty("state");
    }
    @Bean
    public String mobileNumber() {
        return environment.getProperty("mobileNumber");
    }
    @Bean
    public String email() {
        return environment.getProperty("email");
    }
    @Bean
    public String bankName() {
        return environment.getProperty("bankName");
    }
    @Bean
    public String bankAccountNumber() {
        return environment.getProperty("bankAccountNumber");
    }
    @Bean
    public String bankIfscCode() {
        return environment.getProperty("bankIfscCode");
    }
    @Bean
    public String paidBillLocation() {
        return environment.getProperty("paidBillLocation");
    }
    @Bean
    public String unpaidBillLocation() {
        return environment.getProperty("unpaidBillLocation");
    }
    @Bean
    public String previousYear() {
        return environment.getProperty("previousYear");
    }
    @Bean
    public String currentYear() {
        return environment.getProperty("currentYear");
    }

}
