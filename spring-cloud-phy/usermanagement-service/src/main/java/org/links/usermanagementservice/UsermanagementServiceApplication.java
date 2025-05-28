package org.links.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 用户管理服务应用启动类。
 * <p>作为 Spring Boot 应用程序入口，支持配置中心的动态刷新功能。</p>
 *
 * <ul>
 *   <li>{@link SpringBootApplication} 标注为 Spring Boot 启动类</li>
 *   <li>{@link RefreshScope} 支持通过 Spring Cloud Config 动态刷新配置</li>
 * </ul>
 */
@SpringBootApplication
@RefreshScope
public class UsermanagementServiceApplication {

	/**
	 * 应用主方法。
	 *
	 * @param args 命令行启动参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(UsermanagementServiceApplication.class, args);
	}
}
