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
 * 漂流瓶服务类。
 * <p>提供发布、捞取、评论、删除、回收、再投放和查询漂流瓶相关的核心业务逻辑。</p>
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
     * 根据手机号获取用户 ID。
     *
     * @param phoneNumber 用户手机号
     * @return 用户 ID
     * @throws CustomException 如果用户服务不可用或查询失败
     */
    private Long getUserIdByPhoneNumber(String phoneNumber) {
        try {
            return userClient.getUserIdByPhoneNumber(phoneNumber);
        } catch (Exception e) {
            throw new CustomException("无法通过手机号获取用户 ID，请检查用户服务是否可用");
        }
    }

    /**
     * 发布一个新的漂流瓶。
     *
     * @param request 包含手机号和漂流瓶内容的请求对象
     * @return 新创建漂流瓶的唯一标识符（ID）
     * @throws CustomException 如果手机号无法解析为用户ID，抛出异常
     */
    @Transactional
    public Long createBottle(BottleRequest request) {
        Long ownerId = getUserIdByPhoneNumber(request.getPhoneNumber());
        Bottle bottle = new Bottle();
        bottle.setOwnerId(ownerId);
        bottle.setContent(request.getContent());
        bottle.setStatus("IN_OCEAN");
        bottle.setCreatedAt(LocalDateTime.now());

        bottleRepository.save(bottle);
        return bottle.getId();
    }


    /**
     * 随机捞取一个不属于当前用户的漂流瓶。
     *
     * @param phoneNumber 当前用户手机号
     * @return 漂流瓶响应对象
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
     * 对指定漂流瓶添加评论。
     *
     * @param bottleId 漂流瓶 ID
     * @param request 评论请求，包含手机号和评论内容
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
     * 删除漂流瓶及其所有评论。
     *
     * @param bottleId 漂流瓶 ID
     * @param phoneNumber 请求用户手机号
     * @param isAdmin 是否为管理员
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
     * 回收漂流瓶至用户背包（设置为 RECYCLED 状态）。
     *
     * @param bottleId 漂流瓶 ID
     * @param phoneNumber 用户手机号
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
     * 再次将漂流瓶投放到海洋（设置为 IN_OCEAN 状态）。
     *
     * @param bottleId 漂流瓶 ID
     * @param phoneNumber 用户手机号
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
     * 获取当前用户的漂流瓶（可选按状态过滤）。
     *
     * @param phoneNumber 用户手机号
     * @param status 状态（可为 null，代表全部）
     * @return 符合条件的漂流瓶响应列表
     */
    @Transactional(readOnly = true)
    public List<BottleResponse> getBottlesByUserAndStatus(String phoneNumber, String status) {
        Long userId = getUserIdByPhoneNumber(phoneNumber);

        List<Bottle> bottles;
        if (status == null || status.isEmpty()) {
            bottles = bottleRepository.findByOwnerId(userId);
        } else {
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
     * 获取指定漂流瓶的所有评论。
     *
     * @param bottleId 漂流瓶 ID
     * @return 评论响应列表
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
