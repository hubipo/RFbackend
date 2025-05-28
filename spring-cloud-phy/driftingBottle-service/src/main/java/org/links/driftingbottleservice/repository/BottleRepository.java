package org.links.driftingbottleservice.repository;

import org.links.driftingbottleservice.entity.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 漂流瓶实体的持久层接口。
 * <p>提供按用户 ID、漂流瓶状态等条件查询漂流瓶数据的方法。</p>
 */
@Repository
public interface BottleRepository extends JpaRepository<Bottle, Long> {

    /**
     * 查询某个用户拥有的、处于指定状态的所有漂流瓶。
     *
     * @param ownerId 用户 ID
     * @param status 漂流瓶状态（如 IN_OCEAN, RECYCLED）
     * @return 漂流瓶列表
     */
    List<Bottle> findByOwnerIdAndStatus(Long ownerId, String status);

    /**
     * 查询不属于该用户且处于指定状态的所有漂流瓶。
     * <p>通常用于捞取他人漂流瓶。</p>
     *
     * @param ownerId 当前用户 ID（排除）
     * @param status 状态（通常为 IN_OCEAN）
     * @return 可被捞取的漂流瓶列表
     */
    List<Bottle> findByOwnerIdNotAndStatus(Long ownerId, String status);

    /**
     * 查询指定用户的所有漂流瓶（不区分状态）。
     *
     * @param ownerId 用户 ID
     * @return 漂流瓶列表
     */
    List<Bottle> findByOwnerId(Long ownerId);
}
