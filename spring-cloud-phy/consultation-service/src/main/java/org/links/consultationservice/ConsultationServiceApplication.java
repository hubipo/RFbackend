package org.links.consultationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 咨询微服务的主启动类。
 * 使用 @SpringBootApplication 注解标记为 Spring Boot 应用入口，
 * 使用 @RefreshScope 允许在运行时刷新配置（例如通过 Spring Cloud Config 实现）。
 */
@RefreshScope
@SpringBootApplication
public class ConsultationServiceApplication {

    /**
     * 应用程序主方法，负责启动 Spring Boot 应用。
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsultationServiceApplication.class, args);
    }

}
