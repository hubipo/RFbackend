package org.links.driftingbottleservice.repository;

import org.links.driftingbottleservice.entity.BottleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing BottleComment entities.
 */
@Repository
public interface BottleCommentRepository extends JpaRepository<BottleComment, Long> {

    /**
     * Find all comments associated with a specific bottle.
     *
     * @param bottleId the ID of the bottle
     * @return a list of comments for the specified bottle
     */
    List<BottleComment> findByBottleId(Long bottleId);

    /**
     * Delete all comments associated with a specific bottle.
     *
     * @param bottleId the ID of the bottle
     */
    void deleteByBottleId(Long bottleId);
}
