package org.links.driftingbottleservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.links.driftingbottleservice.entity.Bottle;
import org.links.driftingbottleservice.exception.CustomException;
import org.links.driftingbottleservice.repository.BottleCommentRepository;
import org.links.driftingbottleservice.repository.BottleRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BottleRecycleThrowTest {

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
     * TC-5-1 回收成功：当前用户是拥有者
     */
    @Test
    void testRecycleBottle_OwnerSuccess() {
        Long bottleId = 1L;
        String phoneNumber = "13200000000";
        Long userId = 10L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(userId);
        bottle.setStatus("IN_OCEAN");

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        assertDoesNotThrow(() -> bottleService.recycleBottle(bottleId, phoneNumber));
        assertEquals("RECYCLED", bottle.getStatus());
    }

    /**
     * TC-5-2 回收失败：用户不是拥有者
     */
    @Test
    void testRecycleBottle_NotOwnerShouldFail() {
        Long bottleId = 1L;
        String phoneNumber = "13200000000";
        Long userId = 11L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(999L);

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        CustomException e = assertThrows(CustomException.class,
                () -> bottleService.recycleBottle(bottleId, phoneNumber));

        assertEquals("您无权回收该漂流瓶", e.getMessage());
    }

    /**
     * TC-5-3 投放成功：状态被设为 IN_OCEAN
     */
    @Test
    void testThrowBottle_OwnerSuccess() {
        Long bottleId = 2L;
        String phoneNumber = "13200000000";
        Long userId = 10L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(userId);
        bottle.setStatus("RECYCLED");

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        assertDoesNotThrow(() -> bottleService.throwBottle(bottleId, phoneNumber));
        assertEquals("IN_OCEAN", bottle.getStatus());
    }

    /**
     * TC-5-4 投放失败：用户不是拥有者
     */
    @Test
    void testThrowBottle_NotOwnerShouldFail() {
        Long bottleId = 2L;
        String phoneNumber = "13200000000";
        Long userId = 11L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(999L);
        bottle.setStatus("RECYCLED");

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        CustomException e = assertThrows(CustomException.class,
                () -> bottleService.throwBottle(bottleId, phoneNumber));

        assertEquals("您无权投放该漂流瓶", e.getMessage());
    }
    @Test
    void testRecycleBottle_BottleNotFound() {
        Long bottleId = 100L;
        String phoneNumber = "13200000000";
        Long userId = 1L;

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.empty());

        CustomException e = assertThrows(CustomException.class, () ->
                bottleService.recycleBottle(bottleId, phoneNumber));

        assertEquals("漂流瓶不存在", e.getMessage());
    }
    @Test
    void testThrowBottle_BottleNotFound() {
        Long bottleId = 200L;
        String phoneNumber = "13200000000";
        Long userId = 1L;

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.empty());

        CustomException e = assertThrows(CustomException.class, () ->
                bottleService.throwBottle(bottleId, phoneNumber));

        assertEquals("漂流瓶不存在", e.getMessage());
    }
}