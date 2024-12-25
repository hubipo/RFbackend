package org.links.anonymouschatroomservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;

@RefreshScope
@SpringBootApplication
public class AnonymousChatroomServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnonymousChatroomServiceApplication.class, args);
    }

}

