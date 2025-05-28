package org.links.usermanagementservice.dto;

import lombok.Data;

/**
 * 发送验证码请求数据传输对象。
 * <p>用于接收发送验证码接口中的手机号参数。</p>
 */
@Data
public class SendCodeRequest {

    /**
     * 用户手机号。
     * <p>接收验证码的手机号码。</p>
     */
    private String phoneNumber;
}
