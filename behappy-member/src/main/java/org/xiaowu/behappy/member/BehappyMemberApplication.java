package org.xiaowu.behappy.member;

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
public class BehappyMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyMemberApplication.class, args);
    }

}
