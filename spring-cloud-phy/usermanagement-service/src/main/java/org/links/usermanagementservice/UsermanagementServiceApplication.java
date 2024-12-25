package org.links.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class UsermanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermanagementServiceApplication.class, args);
	}

}
