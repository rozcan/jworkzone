package com.ocean.workzone.repository;

import com.ocean.workzone.domain.Location;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
public interface LocationRepository extends JpaRepository<Location,Long> {

    @Query("select distinct location from Location location left join fetch location.users")
    List<Location> findAllWithEagerRelationships();

    @Query("select location from Location location left join fetch location.users where location.id =:id")
    Location findOneWithEagerRelationships(@Param("id") Long id);

}
