package org.links.driftingbottleservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.links.driftingbottleservice.dto.BottleResponse;
import org.links.driftingbottleservice.entity.Bottle;
import org.links.driftingbottleservice.repository.BottleCommentRepository;
import org.links.driftingbottleservice.repository.BottleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BottleListTest {

    @Mock
    private BottleRepository bottleRepository;

    @Mock
    private BottleCommentRepository bottleCommentRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BottleService bottleService;

    private final String phoneNumber = "13200000000";
    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TC-6-1 合法 status：IN_OCEAN / RECYCLED
     */
    @Test
    void testGetBottlesByUserAndStatus_validStatus() {
        for (String status : List.of("IN_OCEAN", "RECYCLED")) {
            Bottle bottle = new Bottle();
            bottle.setId(1L);
            bottle.setOwnerId(userId);
            bottle.setStatus(status);
            bottle.setContent("test");
            bottle.setCreatedAt(LocalDateTime.now());

            when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
            when(bottleRepository.findByOwnerIdAndStatus(userId, status)).thenReturn(List.of(bottle));

            List<BottleResponse> responses = bottleService.getBottlesByUserAndStatus(phoneNumber, status);
            assertEquals(1, responses.size());
            assertEquals("test", responses.get(0).getContent());
        }
    }

    /**
     * TC-6-2 不传 status：应列出所有瓶子
     */
    @Test
    void testGetBottlesByUserAndStatus_nullStatus() {
        Bottle bottle = new Bottle();
        bottle.setId(2L);
        bottle.setOwnerId(userId);
        bottle.setStatus("IN_OCEAN");
        bottle.setContent("no status");
        bottle.setCreatedAt(LocalDateTime.now());

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findByOwnerId(userId)).thenReturn(List.of(bottle));

        List<BottleResponse> responses = bottleService.getBottlesByUserAndStatus(phoneNumber, null);
        assertEquals(1, responses.size());
        assertEquals("no status", responses.get(0).getContent());
    }

    /**
     * TC-6-3 非法 status：OUT（数据库没有匹配结果也不会抛异常）
     */
    @Test
    void testGetBottlesByUserAndStatus_invalidStatus() {
        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findByOwnerIdAndStatus(userId, "OUT")).thenReturn(List.of());

        List<BottleResponse> responses = bottleService.getBottlesByUserAndStatus(phoneNumber, "OUT");
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }
    /**
     * TC-6-4 status为空
     */
    @Test
    void testGetBottlesByUserAndStatus_emptyStatus() {
        Bottle bottle = new Bottle();
        bottle.setId(3L);
        bottle.setOwnerId(userId);
        bottle.setStatus("RECYCLED");
        bottle.setContent("empty status");
        bottle.setCreatedAt(LocalDateTime.now());

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findByOwnerId(userId)).thenReturn(List.of(bottle));

        List<BottleResponse> responses = bottleService.getBottlesByUserAndStatus(phoneNumber, "");
        assertEquals(1, responses.size());
        assertEquals("empty status", responses.get(0).getContent());
    }
}