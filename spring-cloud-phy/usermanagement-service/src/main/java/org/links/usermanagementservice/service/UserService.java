package org.links.usermanagementservice.service;

import org.links.usermanagementservice.dto.*;
import org.links.usermanagementservice.entity.User;
import org.links.usermanagementservice.entity.User.Status;
import org.links.usermanagementservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务类。
 * <p>负责用户的注册、登录、信息更新、密码重置、封禁/解封操作以及用户数据查询等核心业务逻辑。</p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;

    /**
     * 构造函数，注入所需依赖。
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationCodeService verificationCodeService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * 用户登录，支持密码登录和验证码登录。
     *
     * @param request 登录请求信息
     * @return 登录成功提示信息
     */
    public String login(LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (user.getStatus() == Status.BANNED) {
            throw new RuntimeException("用户已被封禁");
        }

        if (request.getVerificationCode() != null) {
            if (!verificationCodeService.verifyCode(request.getPhoneNumber(), "login", request.getVerificationCode())) {
                throw new RuntimeException("验证码错误或已过期");
            }
            return "登录成功（验证码登录）";
        }

        if (request.getPassword() != null) {
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("密码错误");
            }
            return "登录成功（密码登录）";
        }

        throw new RuntimeException("请提供密码或验证码进行登录");
    }

    /**
     * 用户注册。
     *
     * @param request 注册请求数据
     */
    public void register(RegisterRequest request) {
        if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("手机号已被注册");
        }

        if (!verificationCodeService.verifyCode(request.getPhoneNumber(), "register", request.getVerificationCode())) {
            throw new RuntimeException("验证码错误或已过期");
        }

        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNickname(request.getNickname());
        user.setGender(parseGender(request.getGender()));
        user.setBirthDate(request.getBirthDate());
        user.setSignature(request.getSignature());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setStatus(Status.ACTIVE);

        userRepository.save(user);
    }

    /**
     * 重置用户密码。
     *
     * @param request 包含手机号、验证码和新密码
     */
    public void resetPassword(ResetPasswordRequest request) {
        boolean isValid = verificationCodeService.verifyCode(
                request.getPhoneNumber(), "reset", request.getVerificationCode());
        if (!isValid) {
            throw new RuntimeException("验证码错误或已过期");
        }

        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    /**
     * 更新用户信息。
     *
     * @param userId 用户 ID
     * @param updateRequest 包含更新内容的请求体
     */
    public void updateUser(Long userId, UpdateUserRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        if (updateRequest.getNickname() != null) {
            user.setNickname(updateRequest.getNickname());
        }
        if (updateRequest.getGender() != null) {
            user.setGender(parseGender(updateRequest.getGender()));
        }
        if (updateRequest.getBirthDate() != null) {
            user.setBirthDate(updateRequest.getBirthDate());
        }
        if (updateRequest.getSignature() != null) {
            user.setSignature(updateRequest.getSignature());
        }
        if (updateRequest.getAvatarUrl() != null) {
            user.setAvatarUrl(updateRequest.getAvatarUrl());
        }

        userRepository.save(user);
    }

    /**
     * 获取所有用户列表。
     *
     * @return 所有用户的响应对象列表
     */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定 ID 的用户信息。
     *
     * @param userId 用户 ID
     * @return 用户响应信息
     */
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return mapToUserResponse(user);
    }

    /**
     * 根据手机号获取用户 ID。
     *
     * @param phoneNumber 用户手机号
     * @return 对应的用户 ID
     */
    public Long getUserIdByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    /**
     * 批量封禁用户。
     *
     * @param userIds 用户 ID 列表
     * @param reason 封禁原因
     */
    public void batchBanUsers(List<Long> userIds, String reason) {
        List<User> users = userRepository.findAllById(userIds);

        if (users.isEmpty()) {
            throw new RuntimeException("未找到相关用户");
        }

        for (User user : users) {
            user.setStatus(Status.BANNED);
            user.setBanReason(reason);
        }

        userRepository.saveAll(users);
    }

    /**
     * 批量解封用户。
     *
     * @param userIds 用户 ID 列表
     */
    public void batchUnbanUsers(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);

        if (users.isEmpty()) {
            throw new RuntimeException("未找到相关用户");
        }

        for (User user : users) {
            user.setStatus(Status.ACTIVE);
            user.setBanReason(null); // 清除封禁原因
        }

        userRepository.saveAll(users);
    }

    /**
     * 将 User 实体对象转换为响应对象。
     *
     * @param user 用户实体
     * @return 用户响应对象
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .avatarUrl(user.getAvatarUrl())
                .gender(user.getGender().name())
                .age(calculateAge(user.getBirthDate()))
                .signature(user.getSignature())
                .build();
    }

    /**
     * 根据出生日期计算年龄。
     *
     * @param birthDate 出生日期
     * @return 年龄（岁）
     */
    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) {
            return 0;
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * 解析字符串形式的性别值为 Gender 枚举。
     *
     * @param gender 字符串性别
     * @return 枚举类型的性别值
     */
    private User.Gender parseGender(String gender) {
        try {
            return User.Gender.valueOf(gender.toUpperCase());
        } catch (Exception e) {
            return User.Gender.OTHER;
        }
    }
}
