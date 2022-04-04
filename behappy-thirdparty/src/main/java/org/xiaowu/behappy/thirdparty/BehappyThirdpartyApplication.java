package org.xiaowu.behappy.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.xiaowu.behappy.common.feign.annotation.EnableBeHappyFeignClients;

/**
 * @author xiaowu
 */
@EnableBeHappyFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class BehappyThirdpartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyThirdpartyApplication.class, args);
    }

}
