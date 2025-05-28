package org.links.driftingbottleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 漂流瓶服务启动类。
 * <p>作为 Spring Boot 应用程序的入口，启用了 OpenFeign 客户端与动态配置刷新功能。</p>
 *
 * <ul>
 *   <li>{@link EnableFeignClients}：启用 Feign 声明式远程调用</li>
 *   <li>{@link RefreshScope}：支持配置中心动态刷新配置</li>
 * </ul>
 */
@RefreshScope
@SpringBootApplication
@EnableFeignClients
public class DriftingBottleServiceApplication {

    /**
     * 应用程序主入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(DriftingBottleServiceApplication.class, args);
    }
}
