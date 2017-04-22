package com.ocean.workzone.repository;

import com.ocean.workzone.domain.WorkEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the WorkEntry entity.
 */
@SuppressWarnings("unused")
public interface WorkEntryRepository extends JpaRepository<WorkEntry,Long> {

}
