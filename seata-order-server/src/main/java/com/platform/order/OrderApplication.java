package com.platform.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAutoDataSourceProxy
@EnableFeignClients(basePackages={"com.platform.order.feign"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
