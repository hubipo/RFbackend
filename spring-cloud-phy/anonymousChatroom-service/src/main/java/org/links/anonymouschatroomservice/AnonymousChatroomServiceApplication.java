package org.links.anonymouschatroomservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@RefreshScope
@SpringBootApplication
@EnableFeignClients
public class AnonymousChatroomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnonymousChatroomServiceApplication.class, args);
    }

}

