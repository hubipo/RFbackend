package org.links.driftingbottleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 漂流瓶评论实体类。
 * <p>对应数据库中的 {@code bottle_comment} 表，记录用户对漂流瓶的评论信息。</p>
 */
@Entity
@Table(name = "bottle_comment")
@Data
public class BottleComment {

    /**
     * 评论主键 ID，自增生成。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属漂流瓶的 ID。
     */
    @Column(nullable = false)
    private Long bottleId;

    /**
     * 评论者的用户 ID。
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * 评论内容。
     * <p>支持长文本。</p>
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 评论创建时间。
     */
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
