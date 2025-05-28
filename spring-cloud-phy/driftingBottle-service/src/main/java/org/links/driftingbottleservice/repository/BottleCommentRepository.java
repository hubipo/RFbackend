package org.links.driftingbottleservice.repository;

import org.links.driftingbottleservice.entity.BottleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 漂流瓶评论持久层接口。
 * <p>用于管理 {@link BottleComment} 实体，包括查询和删除指定漂流瓶的所有评论。</p>
 */
@Repository
public interface BottleCommentRepository extends JpaRepository<BottleComment, Long> {

    /**
     * 根据漂流瓶 ID 查询所有评论。
     *
     * @param bottleId 漂流瓶 ID
     * @return 属于该漂流瓶的评论列表
     */
    List<BottleComment> findByBottleId(Long bottleId);

    /**
     * 根据漂流瓶 ID 删除所有评论。
     *
     * @param bottleId 漂流瓶 ID
     */
    void deleteByBottleId(Long bottleId);
}
