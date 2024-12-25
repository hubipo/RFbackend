package org.links.diaryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class DiaryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiaryServiceApplication.class, args);
    }

}
