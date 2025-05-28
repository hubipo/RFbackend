package org.links.consultationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DashScope 配置类。
 * <p>从配置文件中读取 DashScope 的相关属性（如 API Key），用于与大模型服务交互。</p>
 *
 * <p>对应配置项示例：</p>
 * <pre>
 * dashscope.api-key=your-api-key-here
 * </pre>
 */
@Component
@ConfigurationProperties(prefix = "dashscope")
public class DashScopeConfig {

    /**
     * DashScope 服务的 API Key，用于身份认证。
     */
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
