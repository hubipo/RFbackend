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

public class BottleDeleteTest {

    @Mock
    private BottleRepository bottleRepository;

    @Mock
    private BottleCommentRepository bottleCommentRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private BottleService bottleService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * TC-4-1 所有者自己删除
     */
    @Test
    void testDeleteBottle_ByOwner_Success() {
        Long bottleId = 10L;
        String phoneNumber = "13200000000";
        Long userId = 1L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(userId);

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        assertDoesNotThrow(() -> bottleService.deleteBottle(bottleId, phoneNumber, false));
        verify(bottleRepository, times(1)).delete(bottle);
        verify(bottleCommentRepository, times(1)).deleteByBottleId(bottleId);
    }

    /**
     * TC-4-2 非所有者且非管理员删除 => 应该抛出异常
     */
    @Test
    void testDeleteBottle_ByOtherUser_ShouldFail() {
        Long bottleId = 10L;
        String phoneNumber = "13200000000";
        Long userId = 2L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(999L); // 不同用户

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        CustomException e = assertThrows(CustomException.class, () ->
                bottleService.deleteBottle(bottleId, phoneNumber, false));

        assertEquals("您无权删除该漂流瓶", e.getMessage());
    }

    /**
     * TC-4-3 管理员删除
     */
    @Test
    void testDeleteBottle_ByAdmin_Success() {
        Long bottleId = 10L;
        String phoneNumber = "13200000000";
        Long userId = 999L;

        Bottle bottle = new Bottle();
        bottle.setId(bottleId);
        bottle.setOwnerId(100L); // 管理员不是拥有者

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.of(bottle));

        assertDoesNotThrow(() -> bottleService.deleteBottle(bottleId, phoneNumber, true));
        verify(bottleRepository, times(1)).delete(bottle);
        verify(bottleCommentRepository, times(1)).deleteByBottleId(bottleId);
    }
    /**
     * TC-4-4 漂流瓶不存在，应该抛出异常
     */
    @Test
    void testDeleteBottle_BottleNotFound() {
        Long bottleId = 999L;
        String phoneNumber = "13200000000";
        Long userId = 1L;

        when(userClient.getUserIdByPhoneNumber(phoneNumber)).thenReturn(userId);
        when(bottleRepository.findById(bottleId)).thenReturn(Optional.empty());

        CustomException e = assertThrows(CustomException.class, () ->
                bottleService.deleteBottle(bottleId, phoneNumber, false));

        assertEquals("漂流瓶不存在", e.getMessage());
    }
}