package org.links.usermanagementservice.controller;

import org.links.usermanagementservice.dto.*;
import org.links.usermanagementservice.response.ApiResponse;
import org.links.usermanagementservice.dto.SendCodeRequest;
import org.links.usermanagementservice.service.UserService;
import org.links.usermanagementservice.service.VerificationCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务接口。
 * 提供登录、注册、找回密码、验证码发送，以及用户信息更新、封禁/解封等接口。
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;

    public AuthController(UserService userService, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * U01 - 发送登录验证码到指定手机号。
     *
     * @param request 包含手机号的请求体
     * @return 成功响应消息
     */
    @PostMapping("/send-login-code")
    public ResponseEntity<?> sendLoginCode(@RequestBody SendCodeRequest request) {
        verificationCodeService.generateAndSendCode(request.getPhoneNumber(), "login");
        return ResponseEntity.ok(new ApiResponse(true, "登录验证码发送成功"));
    }

    /**
     * U02 - 发送注册验证码到指定手机号。
     *
     * @param request 包含手机号的请求体
     * @return 成功响应消息
     */
    @PostMapping("/send-register-code")
    public ResponseEntity<?> sendRegisterCode(@RequestBody SendCodeRequest request) {
        verificationCodeService.generateAndSendCode(request.getPhoneNumber(), "register");
        return ResponseEntity.ok(new ApiResponse(true, "注册验证码发送成功"));
    }

    /**
     * U03 - 发送重置密码验证码到指定手机号。
     *
     * @param request 包含手机号的请求体
     * @return 成功响应消息
     */
    @PostMapping("/send-reset-code")
    public ResponseEntity<?> sendResetCode(@RequestBody SendCodeRequest request) {
        verificationCodeService.generateAndSendCode(request.getPhoneNumber(), "reset");
        return ResponseEntity.ok(new ApiResponse(true, "找回密码验证码发送成功"));
    }

    /**
     * U04 - 用户登录（支持密码或验证码登录）。
     *
     * @param request 登录请求信息（手机号 + 密码 或 验证码）
     * @return 登录成功返回 JWT 或失败信息
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String result = userService.login(request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * U05 - 用户注册。
     *
     * @param request 注册请求，包括手机号、验证码、密码、昵称等信息
     * @return 注册结果响应
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok("注册成功");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * U06 - 找回密码，设置新密码。
     *
     * @param request 包含手机号、验证码与新密码
     * @return 密码重置操作结果
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request);
            return ResponseEntity.ok(new ApiResponse(true, "密码重置成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * U07 - 批量封禁用户。
     *
     * @param request 请求体，包含用户 ID 列表和封禁原因
     * @return 操作结果响应
     */
    @PutMapping("/ban")
    public ResponseEntity<?> batchBanUsers(@RequestBody BatchOperationRequest request) {
        try {
            userService.batchBanUsers(request.getUserIds(), request.getReason());
            return ResponseEntity.ok(new ApiResponse(true, "批量封禁成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * U08 - 批量解封用户。
     *
     * @param request 请求体，包含用户 ID 列表
     * @return 操作结果响应
     */
    @PutMapping("/unban")
    public ResponseEntity<?> batchUnbanUsers(@RequestBody BatchOperationRequest request) {
        try {
            userService.batchUnbanUsers(request.getUserIds());
            return ResponseEntity.ok(new ApiResponse(true, "批量解封成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * U09 - 更新指定用户信息。
     *
     * @param userId 用户 ID
     * @param updateRequest 用户更新信息请求体
     * @return 更新操作结果响应
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<?> updateUserInfo(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest updateRequest
    ) {
        try {
            userService.updateUser(userId, updateRequest);
            return ResponseEntity.ok(new ApiResponse(true, "用户信息更新成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    /**
     * U10 - 获取所有用户列表。
     *
     * @return 所有用户的基本信息列表
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * U11 - 获取指定用户详细信息。
     *
     * @param userId 用户 ID
     * @return 指定用户信息
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    /**
     * U12 - 根据手机号获取用户 ID。
     *
     * @param phoneNumber 用户手机号
     * @return 对应的用户 ID
     */
    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserIdByPhoneNumber(@RequestParam String phoneNumber) {
        Long userId = userService.getUserIdByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(userId);
    }
}
