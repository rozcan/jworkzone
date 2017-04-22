package com.ocean.workzone.service.impl;

import com.ocean.workzone.service.WorkEntryService;
import com.ocean.workzone.domain.WorkEntry;
import com.ocean.workzone.repository.WorkEntryRepository;
import com.ocean.workzone.service.dto.WorkEntryDTO;
import com.ocean.workzone.service.mapper.WorkEntryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing WorkEntry.
 */
@Service
@Transactional
public class WorkEntryServiceImpl implements WorkEntryService{

    private final Logger log = LoggerFactory.getLogger(WorkEntryServiceImpl.class);
    
    private final WorkEntryRepository workEntryRepository;

    private final WorkEntryMapper workEntryMapper;

    public WorkEntryServiceImpl(WorkEntryRepository workEntryRepository, WorkEntryMapper workEntryMapper) {
        this.workEntryRepository = workEntryRepository;
        this.workEntryMapper = workEntryMapper;
    }

    /**
     * Save a workEntry.
     *
     * @param workEntryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkEntryDTO save(WorkEntryDTO workEntryDTO) {
        log.debug("Request to save WorkEntry : {}", workEntryDTO);
        WorkEntry workEntry = workEntryMapper.workEntryDTOToWorkEntry(workEntryDTO);
        workEntry = workEntryRepository.save(workEntry);
        WorkEntryDTO result = workEntryMapper.workEntryToWorkEntryDTO(workEntry);
        return result;
    }

    /**
     *  Get all the workEntries.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<WorkEntryDTO> findAll() {
        log.debug("Request to get all WorkEntries");
        List<WorkEntryDTO> result = workEntryRepository.findAll().stream()
            .map(workEntryMapper::workEntryToWorkEntryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one workEntry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkEntryDTO findOne(Long id) {
        log.debug("Request to get WorkEntry : {}", id);
        WorkEntry workEntry = workEntryRepository.findOne(id);
        WorkEntryDTO workEntryDTO = workEntryMapper.workEntryToWorkEntryDTO(workEntry);
        return workEntryDTO;
    }

    /**
     *  Delete the  workEntry by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkEntry : {}", id);
        workEntryRepository.delete(id);
    }
}
