package org.links.driftingbottleservice.controller;

import org.links.driftingbottleservice.dto.*;
import org.links.driftingbottleservice.service.BottleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 漂流瓶服务接口
 * <p>提供发布、捞取、评论、删除、回收、投放、查询漂流瓶等接口。</p>
 */
@RestController
@RequestMapping("/api/v1/bottle")
public class BottleController {

    private final BottleService bottleService;

    public BottleController(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    /**
     * D01 - 发布一个新的漂流瓶。
     *
     * @param request 包含手机号和漂流瓶内容的请求体 {@link BottleRequest}
     * @return 返回一个 JSON 对象，包含漂流瓶 ID 和发布成功提示信息
     *
     * 示例返回：
     * <pre>
     * {
     *   "message": "漂流瓶发布成功",
     *   "bottleId": 123
     * }
     * </pre>
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBottle(@RequestBody BottleRequest request) {
        Long bottleId = bottleService.createBottle(request);
        Map<String, Object> response = Map.of(
                "message", "漂流瓶发布成功",
                "bottleId", bottleId
        );
        return ResponseEntity.ok(response);
    }

    /**
     * D02 - 随机捞取一个不属于当前用户的漂流瓶。
     *
     * @param phoneNumber 当前用户手机号
     * @return 捞取到的漂流瓶信息
     */
    @GetMapping("/pick")
    public ResponseEntity<BottleResponse> pickBottle(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(bottleService.pickBottle(phoneNumber));
    }

    /**
     * D03 - 对指定漂流瓶添加评论。
     *
     * @param bottleId 漂流瓶 ID
     * @param request 包含评论内容和手机号的请求体
     * @return 评论成功提示
     */
    @PostMapping("/{bottleId}/comment")
    public ResponseEntity<?> commentBottle(
            @PathVariable Long bottleId,
            @RequestBody BottleCommentRequest request) {
        bottleService.commentBottle(bottleId, request);
        return ResponseEntity.ok("评论成功");
    }

    /**
     * D04 - 删除指定漂流瓶及其所有评论。
     *
     * @param bottleId 漂流瓶 ID
     * @param phoneNumber 发起删除请求的手机号
     * @param isAdmin 是否为管理员，默认为 false
     * @return 删除成功提示
     */
    @DeleteMapping("/{bottleId}")
    public ResponseEntity<?> deleteBottle(
            @PathVariable Long bottleId,
            @RequestParam String phoneNumber,
            @RequestParam Boolean isAdmin) {
        bottleService.deleteBottle(bottleId, phoneNumber, isAdmin);
        return ResponseEntity.ok("漂流瓶及其评论已被删除");
    }

    /**
     * D05 - 将漂流瓶回收到用户背包中。
     *
     * @param bottleId 漂流瓶 ID
     * @param phoneNumber 用户手机号
     * @return 回收成功提示
     */
    @PutMapping("/{bottleId}/recycle")
    public ResponseEntity<?> recycleBottle(@PathVariable Long bottleId, @RequestParam String phoneNumber) {
        bottleService.recycleBottle(bottleId, phoneNumber);
        return ResponseEntity.ok("漂流瓶已回收到背包");
    }

    /**
     * D06 - 将漂流瓶再次投放到海洋中。
     *
     * @param bottleId 漂流瓶 ID
     * @param phoneNumber 用户手机号
     * @return 投放成功提示
     */
    @PutMapping("/{bottleId}/throw")
    public ResponseEntity<?> throwBottle(@PathVariable Long bottleId, @RequestParam String phoneNumber) {
        bottleService.throwBottle(bottleId, phoneNumber);
        return ResponseEntity.ok("漂流瓶已重新投放到海洋");
    }

    /**
     * D07 - 获取用户的漂流瓶列表，可按状态筛选。
     *
     * @param phoneNumber 用户手机号
     * @param status 漂流瓶状态（可选：IN_OCEAN、RECYCLED）
     * @return 漂流瓶列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<BottleResponse>> getBottlesByUserAndStatus(
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String status) {
        List<BottleResponse> bottles = bottleService.getBottlesByUserAndStatus(phoneNumber, status);
        return ResponseEntity.ok(bottles);
    }

    /**
     * D08 - 获取指定漂流瓶的所有评论。
     *
     * @param bottleId 漂流瓶 ID
     * @return 评论列表
     */
    @GetMapping("/{bottleId}/comments")
    public ResponseEntity<List<BottleCommentResponse>> getBottleComments(@PathVariable Long bottleId) {
        return ResponseEntity.ok(bottleService.getBottleComments(bottleId));
    }
}
