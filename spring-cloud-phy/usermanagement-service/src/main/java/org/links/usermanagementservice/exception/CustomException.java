package org.links.usermanagementservice.exception;

/**
 * 用户管理服务的自定义异常。
 * <p>用于在业务逻辑中抛出语义清晰的错误信息，支持统一异常处理与响应格式化。</p>
 */
public class CustomException extends RuntimeException {

    /**
     * 构造方法，使用指定错误消息创建异常实例。
     *
     * @param message 异常描述信息
     */
    public CustomException(String message) {
        super(message);
    }
}
