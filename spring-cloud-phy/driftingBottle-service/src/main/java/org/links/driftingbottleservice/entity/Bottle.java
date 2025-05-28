package org.links.driftingbottleservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 漂流瓶实体类。
 * <p>对应数据库中的 {@code bottle} 表，记录每个漂流瓶的基本信息。</p>
 */
@Entity
@Table(name = "bottle")
@Data
public class Bottle {

    /**
     * 漂流瓶主键 ID，自增生成。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 拥有者用户 ID。
     * <p>通过手机号在用户系统中解析得到。</p>
     */
    @Column(nullable = false)
    private Long ownerId;

    /**
     * 漂流瓶的内容。
     * <p>允许为较长文本。</p>
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * 漂流瓶状态。
     * <p>合法值：IN_OCEAN、RECYCLED。</p>
     */
    @Column(nullable = false)
    private String status;

    /**
     * 创建时间。
     * <p>用于记录漂流瓶生成的时间。</p>
     */
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
