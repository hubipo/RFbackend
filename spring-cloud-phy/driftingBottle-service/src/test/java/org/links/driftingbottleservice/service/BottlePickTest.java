package org.links.driftingbottleservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.links.driftingbottleservice.dto.BottleResponse;
import org.links.driftingbottleservice.entity.Bottle;
import org.links.driftingbottleservice.exception.CustomException;
import org.links.driftingbottleservice.repository.BottleCommentRepository;
import org.links.driftingbottleservice.repository.BottleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BottlePickTest {

    @Mock
    private BottleRepository bottleRepository;

    @Mock
    private BottleCommentRepository bottleCommentRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BottleService bottleService;

    @Test
    void testPickBottle_Success() {
        // TC1: 有可捞取漂流瓶
        Long userId = 1L;
        String phoneNumber = "13212345678";
        Bottle mockBottle = new Bottle();
        mockBottle.setId(100L);
        mockBottle.setOwnerId(2L);
        mockBottle.setContent("漂流瓶内容");

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findByOwnerIdNotAndStatus(userId, "IN_OCEAN"))
                .thenReturn(List.of(mockBottle));

        BottleResponse response = bottleService.pickBottle(phoneNumber);

        assertEquals(100L, response.getId());
        assertEquals("漂流瓶内容", response.getContent());
    }

    @Test
    void testPickBottle_EmptyBottleList() {
        // TC2: 无可捞取漂流瓶
        Long userId = 1L;
        String phoneNumber = "13212345678";

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findByOwnerIdNotAndStatus(userId, "IN_OCEAN"))
                .thenReturn(Collections.emptyList());

        CustomException ex = assertThrows(CustomException.class, () -> {
            bottleService.pickBottle(phoneNumber);
        });
        assertEquals("当前无可捞取的漂流瓶", ex.getMessage());
    }

    @Test
    void testPickBottle_UserServiceFail() {
        // TC3: 获取用户 ID 失败
        String phoneNumber = "13212345678";

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenThrow(new RuntimeException("连接失败"));

        CustomException ex = assertThrows(CustomException.class, () -> {
            bottleService.pickBottle(phoneNumber);
        });
        assertEquals("无法通过手机号获取用户 ID，请检查用户服务是否可用", ex.getMessage());
    }
}