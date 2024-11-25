package com.ningf.ningfojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.ningf.ningfojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.ningf")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.ningf.ningfojbackendserviceclient.service"})
public class NingfojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NingfojBackendUserServiceApplication.class, args);
    }

}
