package org.xiaow.behappy.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.xiaowu.behappy.common.feign.annotation.EnableBeHappyFeignClients;
import org.xiaowu.behappy.common.satoken.annoations.EnableSaToken;

/**
 * @author xiaowu
 */
@EnableSaToken
@EnableBeHappyFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class BehappyOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyOrderApplication.class, args);
    }

}
