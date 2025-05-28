package org.links.usermanagementservice.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务类。
 * <p>负责生成、发送和验证短信验证码，验证码按手机号和使用类型区分，默认有效期为 5 分钟。</p>
 */
@Service
public class VerificationCodeService {

    private final AliyunSmsService aliyunSmsService;

    /**
     * 验证码存储（键为手机号+类型，值为验证码）。
     */
    private final ConcurrentHashMap<String, String> codeStorage = new ConcurrentHashMap<>();

    /**
     * 验证码过期时间存储（键为手机号+类型，值为时间戳）。
     */
    private final ConcurrentHashMap<String, Long> codeExpiry = new ConcurrentHashMap<>();

    /**
     * 构造函数，注入阿里云短信服务。
     *
     * @param aliyunSmsService 用于发送短信验证码的服务
     */
    public VerificationCodeService(AliyunSmsService aliyunSmsService) {
        this.aliyunSmsService = aliyunSmsService;
    }

    /**
     * 生成并发送验证码到指定手机号。
     *
     * @param phoneNumber 手机号
     * @param type 验证码类型（如 login、register、reset）
     * @return 生成的验证码（用于测试或记录）
     */
    public String generateAndSendCode(String phoneNumber, String type) {
        if (!phoneNumber.matches("^1[3-9]\\d{9}$")) {
            throw new RuntimeException("手机号格式错误");
        }

        String key = phoneNumber + ":" + type;
        String code = generateCode();

        // 存储验证码及其过期时间
        codeStorage.put(key, code);
        codeExpiry.put(key, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)); // 5分钟有效

        System.out.println("发送验证码到 " + phoneNumber + "，验证码为：" + code);

        // 发送验证码短信
        boolean success = aliyunSmsService.sendSms(phoneNumber, code);
        if (!success) {
            throw new RuntimeException("验证码发送失败");
        }

        return code;
    }

    /**
     * 校验用户输入的验证码是否正确且未过期。
     *
     * @param phoneNumber 手机号
     * @param type 验证码类型
     * @param inputCode 用户输入的验证码
     * @return 验证是否通过
     */
    public boolean verifyCode(String phoneNumber, String type, String inputCode) {
        String key = phoneNumber + ":" + type;
        String storedCode = codeStorage.get(key);
        Long expiryTime = codeExpiry.get(key);

        // 检查是否过期或不存在
        if (storedCode == null || expiryTime == null || System.currentTimeMillis() > expiryTime) {
            codeStorage.remove(key);
            codeExpiry.remove(key);
            return false;
        }

        return storedCode.equals(inputCode);
    }

    /**
     * 生成六位随机验证码。
     *
     * @return 六位数字验证码字符串
     */
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}
