package org.links.usermanagementservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用 API 响应封装类。
 * <p>用于统一返回格式，适用于成功或失败的简单响应。</p>
 */
@Data
@AllArgsConstructor
public class ApiResponse {

    /**
     * 响应是否成功。
     */
    private boolean success;

    /**
     * 响应消息，通常为成功提示或错误描述。
     */
    private String message;
}
