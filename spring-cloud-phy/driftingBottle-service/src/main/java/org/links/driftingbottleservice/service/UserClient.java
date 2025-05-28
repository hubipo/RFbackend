package org.links.driftingbottleservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务客户端。
 * <p>通过 OpenFeign 调用用户管理服务接口，获取用户 ID。</p>
 */
@FeignClient(name = "user-management-service", url = "http://localhost:9080")
public interface UserClient {

    /**
     * 根据手机号从用户服务获取用户 ID。
     *
     * @param phoneNumber 用户手机号
     * @return 用户 ID
     */
    @GetMapping("/api/v1/auth/user-id")
    Long getUserIdByPhoneNumber(@RequestParam(value = "phoneNumber") String phoneNumber);
}
