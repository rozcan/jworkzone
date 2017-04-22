package com.ocean.workzone.service;

import com.ocean.workzone.service.dto.WorkEntryDTO;
import java.util.List;

/**
 * Service Interface for managing WorkEntry.
 */
public interface WorkEntryService {

    /**
     * Save a workEntry.
     *
     * @param workEntryDTO the entity to save
     * @return the persisted entity
     */
    WorkEntryDTO save(WorkEntryDTO workEntryDTO);

    /**
     *  Get all the workEntries.
     *  
     *  @return the list of entities
     */
    List<WorkEntryDTO> findAll();

    /**
     *  Get the "id" workEntry.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    WorkEntryDTO findOne(Long id);

    /**
     *  Delete the "id" workEntry.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
