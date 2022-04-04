package org.xiaowu.behappy.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BehappyProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyProductApplication.class, args);
    }

}
