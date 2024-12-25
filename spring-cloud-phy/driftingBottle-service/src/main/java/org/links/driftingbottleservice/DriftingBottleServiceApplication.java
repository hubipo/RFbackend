package org.links.driftingbottleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class DriftingBottleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriftingBottleServiceApplication.class, args);
    }

}
