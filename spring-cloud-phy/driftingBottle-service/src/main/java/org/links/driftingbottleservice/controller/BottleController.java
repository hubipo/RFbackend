package org.links.driftingbottleservice.controller;

import org.links.driftingbottleservice.dto.*;
import org.links.driftingbottleservice.service.BottleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing drifting bottles.
 */
@RestController
@RequestMapping("/api/v1/bottle")
public class BottleController {

    private final BottleService bottleService;

    public BottleController(BottleService bottleService) {
        this.bottleService = bottleService;
    }

    /**
     * Create a new drifting bottle.
     */
    @PostMapping
    public ResponseEntity<?> createBottle(@RequestBody BottleRequest request) {
        bottleService.createBottle(request);
        return ResponseEntity.ok("漂流瓶发布成功");
    }

    /**
     * Pick a drifting bottle from the ocean.
     */
    @GetMapping("/pick")
    public ResponseEntity<BottleResponse> pickBottle(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(bottleService.pickBottle(phoneNumber));
    }

    /**
     * Comment on a drifting bottle.
     */
    @PostMapping("/{bottleId}/comment")
    public ResponseEntity<?> commentBottle(
            @PathVariable Long bottleId,
            @RequestBody BottleCommentRequest request) {
        bottleService.commentBottle(bottleId, request);
        return ResponseEntity.ok("评论成功");
    }

    /**
     * Delete a drifting bottle and its comments.
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
     * Recycle a drifting bottle into the user's inventory.
     */
    @PutMapping("/{bottleId}/recycle")
    public ResponseEntity<?> recycleBottle(@PathVariable Long bottleId, @RequestParam String phoneNumber) {
        bottleService.recycleBottle(bottleId, phoneNumber);
        return ResponseEntity.ok("漂流瓶已回收到背包");
    }

    /**
     * Throw a drifting bottle back into the ocean.
     */
    @PutMapping("/{bottleId}/throw")
    public ResponseEntity<?> throwBottle(@PathVariable Long bottleId, @RequestParam String phoneNumber) {
        bottleService.throwBottle(bottleId, phoneNumber);
        return ResponseEntity.ok("漂流瓶已重新投放到海洋");
    }

    /**
     * Get the status of the user's drifting bottles.
     */
    @GetMapping("/list")
    public ResponseEntity<List<BottleResponse>> getBottlesByUserAndStatus(
            @RequestParam String phoneNumber,
            @RequestParam(required = false) String status) {
        List<BottleResponse> bottles = bottleService.getBottlesByUserAndStatus(phoneNumber, status);
        return ResponseEntity.ok(bottles);
    }


    /**
     * Get all comments for a drifting bottle.
     */
    @GetMapping("/{bottleId}/comments")
    public ResponseEntity<List<BottleCommentResponse>> getBottleComments(@PathVariable Long bottleId) {
        return ResponseEntity.ok(bottleService.getBottleComments(bottleId));
    }
}
