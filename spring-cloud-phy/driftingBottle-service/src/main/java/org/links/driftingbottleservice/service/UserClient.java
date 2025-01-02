package org.links.driftingbottleservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-management-service", url = "http://localhost:9080")
public interface UserClient {
    @GetMapping("/api/v1/auth/user-id")
    Long getUserIdByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber);
}
