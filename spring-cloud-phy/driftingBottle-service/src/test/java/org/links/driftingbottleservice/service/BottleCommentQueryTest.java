package org.links.driftingbottleservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.links.driftingbottleservice.dto.BottleCommentResponse;
import org.links.driftingbottleservice.entity.BottleComment;
import org.links.driftingbottleservice.repository.BottleCommentRepository;
import org.links.driftingbottleservice.repository.BottleRepository;
import org.links.driftingbottleservice.service.BottleService;
import org.links.driftingbottleservice.service.UserClient;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BottleCommentQueryTest {

    @Mock
    private BottleRepository bottleRepository;

    @Mock
    private BottleCommentRepository bottleCommentRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BottleService bottleService;

    private final Long bottleId = 100L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TC-7-1 查询有评论的瓶子
     */
    @Test
    void testGetBottleComments_HasComments() {
        BottleComment comment = new BottleComment();
        comment.setBottleId(bottleId);
        comment.setUserId(1L);
        comment.setContent("测试评论");
        comment.setCreatedAt(LocalDateTime.now());

        when(bottleCommentRepository.findByBottleId(bottleId)).thenReturn(List.of(comment));

        List<BottleCommentResponse> result = bottleService.getBottleComments(bottleId);

        assertEquals(1, result.size());
        assertEquals("测试评论", result.get(0).getContent());
        assertEquals(1L, result.get(0).getUserId());
    }

    /**
     * TC-7-2 查询没有评论的瓶子（空列表）
     */
    @Test
    void testGetBottleComments_EmptyComments() {
        when(bottleCommentRepository.findByBottleId(bottleId)).thenReturn(Collections.emptyList());

        List<BottleCommentResponse> result = bottleService.getBottleComments(bottleId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * TC-7-3 查询不存在的瓶子ID（也返回空列表）
     */
    @Test
    void testGetBottleComments_BottleNotExist() {
        when(bottleCommentRepository.findByBottleId(999L)).thenReturn(Collections.emptyList());

        List<BottleCommentResponse> result = bottleService.getBottleComments(999L);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}