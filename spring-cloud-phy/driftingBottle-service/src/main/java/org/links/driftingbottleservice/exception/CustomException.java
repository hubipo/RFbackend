package org.links.driftingbottleservice.exception;

/**
 * 自定义业务异常。
 * <p>用于在业务逻辑中抛出统一格式的错误信息，便于接口异常处理与响应。</p>
 */
public class CustomException extends RuntimeException {

    /**
     * 构造函数，根据错误消息创建异常实例。
     *
     * @param message 错误描述信息
     */
    public CustomException(String message) {
        super(message);
    }
}
