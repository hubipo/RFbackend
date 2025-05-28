package org.links.anonymouschatroomservice.controller;

import org.links.anonymouschatroomservice.service.DeviceService;
import org.links.anonymouschatroomservice.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anonymouschatroom/device")
public class DeviceController {
    private final DeviceService deviceService;
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerDevice(@RequestBody DeviceTokenRequest req, @RequestHeader("Authorization") String jwt) {
        String userId = JwtUtil.parse(jwt).get("userId", String.class);
        deviceService.bind(userId, req.getDeviceToken(), req.getPlatform());
        return ResponseEntity.ok().build();
    }
}