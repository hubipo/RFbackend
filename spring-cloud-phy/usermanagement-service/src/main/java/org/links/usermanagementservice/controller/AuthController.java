package org.links.usermanagementservice.controller;

import org.links.usermanagementservice.dto.*;
import org.links.usermanagementservice.response.ApiResponse;
import org.links.usermanagementservice.dto.SendCodeRequest;
import org.links.usermanagementservice.service.UserService;
import org.links.usermanagementservice.service.VerificationCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final VerificationCodeService verificationCodeService;

    public AuthController(UserService userService, VerificationCodeService verificationCodeService) {
        this.userService = userService;
        this.verificationCodeService = verificationCodeService;
    }

    // 发送登录验证码
    @PostMapping("/send-login-code")
    public ResponseEntity<?> sendLoginCode(@RequestBody SendCodeRequest request) {
        verificationCodeService.generateAndSendCode(request.getPhoneNumber(), "login");
        return ResponseEntity.ok(new ApiResponse(true, "登录验证码发送成功"));
    }

    // 发送注册验证码
    @PostMapping("/send-register-code")
    public ResponseEntity<?> sendRegisterCode(@RequestBody SendCodeRequest request) {
        verificationCodeService.generateAndSendCode(request.getPhoneNumber(), "register");
        return ResponseEntity.ok(new ApiResponse(true, "注册验证码发送成功"));
    }

    // 发送找回密码验证码
    @PostMapping("/send-reset-code")
    public ResponseEntity<?> sendResetCode(@RequestBody SendCodeRequest request) {
        verificationCodeService.generateAndSendCode(request.getPhoneNumber(), "reset");
        return ResponseEntity.ok(new ApiResponse(true, "找回密码验证码发送成功"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String result = userService.login(request);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok("注册成功");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request);
            return ResponseEntity.ok(new ApiResponse(true, "密码重置成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/ban")
    public ResponseEntity<?> batchBanUsers(@RequestBody BatchOperationRequest request) {
        try {
            userService.batchBanUsers(request.getUserIds(), request.getReason());
            return ResponseEntity.ok(new ApiResponse(true, "批量封禁成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/unban")
    public ResponseEntity<?> batchUnbanUsers(@RequestBody BatchOperationRequest request) {
        try {
            userService.batchUnbanUsers(request.getUserIds());
            return ResponseEntity.ok(new ApiResponse(true, "批量解封成功"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage()));
        }
    }

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


    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user-id")
    public ResponseEntity<Long> getUserIdByPhoneNumber(@RequestParam String phoneNumber) {
        Long userId = userService.getUserIdByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(userId);
    }
}
