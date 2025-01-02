package org.links.driftingbottleservice.service;

import org.links.driftingbottleservice.dto.*;
import org.links.driftingbottleservice.entity.*;
import org.links.driftingbottleservice.exception.CustomException;
import org.links.driftingbottleservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Service for managing drifting bottles.
 */
@Service
public class BottleService {

    private final BottleRepository bottleRepository;
    private final BottleCommentRepository bottleCommentRepository;
    private final UserClient userClient;

    public BottleService(BottleRepository bottleRepository, BottleCommentRepository bottleCommentRepository, UserClient userClient) {
        this.bottleRepository = bottleRepository;
        this.bottleCommentRepository = bottleCommentRepository;
        this.userClient = userClient;
    }

    /**
     * Helper to get the user ID by phone number.
     */
    private Long getUserIdByPhoneNumber(String phoneNumber) {
        try {
            return userClient.getUserIdByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            throw new CustomException("无法通过手机号获取用户 ID，请检查用户服务是否可用");
        }
    }

    /**
     * Create a drifting bottle.
     */
    @Transactional
    public void createBottle(BottleRequest request) {
        Long ownerId = getUserIdByPhoneNumber(request.getPhoneNumber());
        Bottle bottle = new Bottle();
        bottle.setOwnerId(ownerId);
        bottle.setContent(request.getContent());
        bottle.setStatus("IN_OCEAN");
        bottle.setCreatedAt(LocalDateTime.now());
        bottleRepository.save(bottle);
    }

    /**
     * Pick a random drifting bottle.
     */
    @Transactional(readOnly = true)
    public BottleResponse pickBottle(String phoneNumber) {
        Long userId = getUserIdByPhoneNumber(phoneNumber);
        List<Bottle> availableBottles = bottleRepository.findByOwnerIdNotAndStatus(userId, "IN_OCEAN");
        if (availableBottles.isEmpty()) {
            throw new CustomException("当前无可捞取的漂流瓶");
        }
        Random random = new Random();
        Bottle pickedBottle = availableBottles.get(random.nextInt(availableBottles.size()));

        return new BottleResponse(
                pickedBottle.getId(),
                pickedBottle.getOwnerId(),
                pickedBottle.getContent(),
                pickedBottle.getCreatedAt().toString()
        );
    }

    /**
     * Comment on a drifting bottle.
     */
    @Transactional
    public void commentBottle(Long bottleId, BottleCommentRequest request) {
        Bottle bottle = bottleRepository.findById(bottleId)
                .orElseThrow(() -> new CustomException("漂流瓶不存在"));

        BottleComment comment = new BottleComment();
        comment.setBottleId(bottleId);
        comment.setUserId(getUserIdByPhoneNumber(request.getPhoneNumber()));
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        bottleCommentRepository.save(comment);
    }

    /**
     * Delete a drifting bottle and its comments.
     */
    @Transactional
    public void deleteBottle(Long bottleId, String phoneNumber, Boolean isAdmin) {
        Long userId = getUserIdByPhoneNumber(phoneNumber);
        Bottle bottle = bottleRepository.findById(bottleId)
                .orElseThrow(() -> new CustomException("漂流瓶不存在"));

        if (!bottle.getOwnerId().equals(userId) && !isAdmin) {
            throw new CustomException("您无权删除该漂流瓶");
        }
        bottleCommentRepository.deleteByBottleId(bottleId);
        bottleRepository.delete(bottle);
    }

    /**
     * Recycle a drifting bottle into the user's inventory.
     */
    @Transactional
    public void recycleBottle(Long bottleId, String phoneNumber) {
        Long userId = getUserIdByPhoneNumber(phoneNumber);
        Bottle bottle = bottleRepository.findById(bottleId)
                .orElseThrow(() -> new CustomException("漂流瓶不存在"));

        if (!bottle.getOwnerId().equals(userId)) {
            throw new CustomException("您无权回收该漂流瓶");
        }
        bottle.setStatus("RECYCLED");
        bottleRepository.save(bottle);
    }

    /**
     * Throw a drifting bottle back into the ocean.
     */
    @Transactional
    public void throwBottle(Long bottleId, String phoneNumber) {
        Long userId = getUserIdByPhoneNumber(phoneNumber);
        Bottle bottle = bottleRepository.findById(bottleId)
                .orElseThrow(() -> new CustomException("漂流瓶不存在"));

        if (!bottle.getOwnerId().equals(userId)) {
            throw new CustomException("您无权投放该漂流瓶");
        }
        bottle.setStatus("IN_OCEAN");
        bottleRepository.save(bottle);
    }

    /**
     * Get the status of the user's drifting bottles.
     */
    @Transactional(readOnly = true)
    public List<BottleResponse> getBottlesByUserAndStatus(String phoneNumber, String status) {
        Long userId = getUserIdByPhoneNumber(phoneNumber);

        List<Bottle> bottles;
        if (status == null || status.isEmpty()) {
            // 如果状态为空，则返回所有漂流瓶
            bottles = bottleRepository.findByOwnerId(userId);
        } else {
            // 根据状态筛选漂流瓶
            bottles = bottleRepository.findByOwnerIdAndStatus(userId, status);
        }

        return bottles.stream()
                .map(bottle -> new BottleResponse(
                        bottle.getId(),
                        bottle.getOwnerId(),
                        bottle.getContent(),
                        bottle.getCreatedAt().toString()
                ))
                .toList();
    }


    /**
     * Get all comments for a drifting bottle.
     */
    @Transactional(readOnly = true)
    public List<BottleCommentResponse> getBottleComments(Long bottleId) {
        List<BottleComment> comments = bottleCommentRepository.findByBottleId(bottleId);
        return comments.stream().map(comment -> new BottleCommentResponse(
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt().toString()
        )).toList();
    }
}
