package org.links.usermanagementservice.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    private final AliyunSmsService aliyunSmsService;

    // 存储验证码和过期时间（手机号 + 类型为键）
    private final ConcurrentHashMap<String, String> codeStorage = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> codeExpiry = new ConcurrentHashMap<>();

    public VerificationCodeService(AliyunSmsService aliyunSmsService) {
        this.aliyunSmsService = aliyunSmsService;
    }

    // 生成并发送验证码
    public String generateAndSendCode(String phoneNumber, String type) {
        if (!phoneNumber.matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("手机号格式错误");
        }

        String key = phoneNumber + ":" + type; // 唯一键
        String code = generateCode();

        // 存储验证码和过期时间
        codeStorage.put(key, code);
        codeExpiry.put(key, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)); // 5 分钟有效

        System.out.println("发送验证码到 " + phoneNumber + "，验证码为：" + code);

        // 调用阿里云短信服务发送验证码
        boolean success = aliyunSmsService.sendSms(phoneNumber, code);
        if (!success) {
            throw new RuntimeException("验证码发送失败");
        }

        return code;
    }

    // 验证验证码
    public boolean verifyCode(String phoneNumber, String type, String inputCode) {
        String key = phoneNumber + ":" + type; // 唯一键
        String storedCode = codeStorage.get(key);
        Long expiryTime = codeExpiry.get(key);

        // 检查验证码是否存在或已过期
        if (storedCode == null || expiryTime == null || System.currentTimeMillis() > expiryTime) {
            codeStorage.remove(key);
            codeExpiry.remove(key);
            return false; // 验证码不存在或已过期
        }

        // 比较验证码是否一致
        return storedCode.equals(inputCode);
    }

    // 生成 6 位随机验证码
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}
