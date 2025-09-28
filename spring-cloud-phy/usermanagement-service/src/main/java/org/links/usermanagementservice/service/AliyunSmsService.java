package org.links.usermanagementservice.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务类。
 * <p>用于通过阿里云短信平台发送验证码短信。</p>
 */
@Service
public class AliyunSmsService {

    /**
     * 阿里云短信服务的 AccessKey ID。
     */
    @Value("********")
    private String accessKeyId;

    /**
     * 阿里云短信服务的 AccessKey Secret。
     */
    @Value("************")
    private String accessKeySecret;

    /**
     * 短信签名名称。
     */
    @Value("${aliyun.sms.sign-name}")
    private String signName;

    /**
     * 短信模板代码。
     */
    @Value("${aliyun.sms.template-code}")
    private String templateCode;

    /**
     * 发送短信验证码到指定手机号。
     *
     * @param phoneNumber 接收短信的手机号
     * @param code 要发送的验证码内容
     * @return 短信发送是否成功
     */
    public boolean sendSms(String phoneNumber, String code) {
        // 初始化阿里云客户端配置
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 创建并配置发送请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(com.aliyuncs.http.MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 设置请求参数
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");

        try {
            // 执行请求发送短信
            CommonResponse response = client.getCommonResponse(request);
            System.out.println("阿里云短信服务响应: " + response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            System.err.println("短信发送失败: " + e.getMessage());
            return false;
        }
    }
}
