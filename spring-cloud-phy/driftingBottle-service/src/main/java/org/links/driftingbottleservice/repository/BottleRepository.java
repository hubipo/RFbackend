package org.links.driftingbottleservice.repository;

import org.links.driftingbottleservice.entity.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing Bottle entities.
 */
@Repository
public interface BottleRepository extends JpaRepository<Bottle, Long> {

    List<Bottle> findByOwnerIdAndStatus(Long ownerId, String status);

    List<Bottle> findByOwnerIdNotAndStatus(Long ownerId, String status);
    List<Bottle> findByOwnerId(Long ownerId);
}
