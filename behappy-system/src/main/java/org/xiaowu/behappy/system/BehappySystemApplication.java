package org.xiaowu.behappy.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.xiaowu.behappy.common.satoken.annoations.EnableSaToken;

@EnableSaToken
@SpringBootApplication
@EnableDiscoveryClient
public class BehappySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappySystemApplication.class, args);
    }

}
