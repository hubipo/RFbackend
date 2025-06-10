package org.links.driftingbottleservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.links.driftingbottleservice.dto.BottleRequest;
import org.links.driftingbottleservice.entity.Bottle;
import org.links.driftingbottleservice.exception.CustomException;
import org.links.driftingbottleservice.repository.BottleCommentRepository;
import org.links.driftingbottleservice.repository.BottleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BottleCreateTest {

    @Mock
    private BottleRepository bottleRepository;

    @Mock
    private BottleCommentRepository bottleCommentRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BottleService bottleService;

    private BottleRequest request;

    @BeforeEach
    public void setup() {
        request = new BottleRequest();
        request.setPhoneNumber("13212345678");
        request.setContent("漂流瓶内容");
    }

    @Test
    void testCreateBottle_Success() {
        Long mockUserId = 1L;
        when(userClient.getUserIdByPhoneNumber("13212345678")).thenReturn(mockUserId);

        assertDoesNotThrow(() -> bottleService.createBottle(request));
        verify(bottleRepository, times(1)).save(any(Bottle.class));
    }

    @Test
    void testCreateBottle_ContentIsNull() {
        request.setContent(null);
        Long mockUserId = 1L;
        when(userClient.getUserIdByPhoneNumber("13212345678")).thenReturn(mockUserId);

        assertThrows(NullPointerException.class, () -> bottleService.createBottle(request));
    }

    @Test
    void testCreateBottle_UserNotFound() {
        when(userClient.getUserIdByPhoneNumber("13212345678"))
                .thenThrow(new RuntimeException("用户不存在"));

        CustomException exception = assertThrows(CustomException.class, () -> bottleService.createBottle(request));
        assertEquals("无法通过手机号获取用户 ID，请检查用户服务是否可用", exception.getMessage());
    }
}
