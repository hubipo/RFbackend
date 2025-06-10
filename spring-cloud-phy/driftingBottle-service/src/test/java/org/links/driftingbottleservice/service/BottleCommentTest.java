package org.links.driftingbottleservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.links.driftingbottleservice.dto.BottleCommentRequest;
import org.links.driftingbottleservice.entity.Bottle;
import org.links.driftingbottleservice.entity.BottleComment;
import org.links.driftingbottleservice.exception.CustomException;
import org.links.driftingbottleservice.repository.BottleCommentRepository;
import org.links.driftingbottleservice.repository.BottleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BottleCommentTest {

    @Mock
    private BottleRepository bottleRepository;

    @Mock
    private BottleCommentRepository bottleCommentRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BottleService bottleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TC-3-1 正常评论
     */
    @Test
    void testCommentBottle_Success() {
        Long bottleId = 1L;
        String phoneNumber = "13200000000";
        String content = "这是一条评论";
        Long userId = 100L;

        BottleCommentRequest request = new BottleCommentRequest();
        request.setPhoneNumber(phoneNumber);
        request.setContent(content);

        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(new Bottle()));
        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);

        assertDoesNotThrow(() -> bottleService.commentBottle(bottleId, request));
        verify(bottleCommentRepository, times(1)).save(any(BottleComment.class));
    }

    /**
     * TC-3-2 bottleId 不存在
     */
    @Test
    void testCommentBottle_BottleNotFound() {
        Long bottleId = 999L;
        BottleCommentRequest request = new BottleCommentRequest();
        request.setPhoneNumber("13200000000");
        request.setContent("评论内容");

        when(bottleRepository.findById(bottleId)).thenReturn(Optional.empty());

        CustomException e = assertThrows(CustomException.class, () -> {
            bottleService.commentBottle(bottleId, request);
        });
        assertEquals("漂流瓶不存在", e.getMessage());
    }

    /**
     * TC-3-3 评论内容为 null
     */
    @Test
    void testCommentBottle_ContentIsNull() {
        Long bottleId = 1L;
        BottleCommentRequest request = new BottleCommentRequest();
        request.setPhoneNumber("13200000000");
        request.setContent(null);

        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(new Bottle()));
        when(userClient.getUserIdByPhoneNumber(any())).thenReturn(1L);

        // 你可以根据实际代码是否支持空内容决定断言类型
        assertThrows(NullPointerException.class, () -> {
            bottleService.commentBottle(bottleId, request);
        });
    }
    @Test
    void testCommentBottle_UserClientFail() {
        Long bottleId = 1L;
        BottleCommentRequest request = new BottleCommentRequest();
        request.setPhoneNumber("13200000000");
        request.setContent("comment");

        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(new Bottle()));
        when(userClient.getUserIdByPhoneNumber(any()))
                .thenThrow(new RuntimeException("服务不可用"));

        CustomException e = assertThrows(CustomException.class, () -> {
            bottleService.commentBottle(bottleId, request);
        });

        assertEquals("无法通过手机号获取用户 ID，请检查用户服务是否可用", e.getMessage());
    }
}