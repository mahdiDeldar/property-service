package com.chubock.propertyservice.repository;

import com.chubock.propertyservice.entity.Property;
import com.chubock.propertyservice.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends AbstractRepository<Property>, JpaSpecificationExecutor<Property> {

    @Query("select p from Property p where p.id = :id")
    @EntityGraph(attributePaths = {"owner", "amenities", "images", "videos"})
    Optional<Property> findByIdWithDetails(@Param("id") String id);

    @EntityGraph(attributePaths = {"owner", "amenities", "images"})
    List<Property> findByOwnerAndDeletedIsFalseOrderByCreateDateDesc(User user);

    Optional<Property> findTopByOwnerAndLivingLandlordIsTrue(User user);

    Optional<List<Property>> findAllByOwner(User user);

}
