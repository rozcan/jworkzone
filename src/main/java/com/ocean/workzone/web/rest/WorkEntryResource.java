package com.ocean.workzone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ocean.workzone.service.WorkEntryService;
import com.ocean.workzone.web.rest.util.HeaderUtil;
import com.ocean.workzone.service.dto.WorkEntryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing WorkEntry.
 */
@RestController
@RequestMapping("/api")
public class WorkEntryResource {

    private final Logger log = LoggerFactory.getLogger(WorkEntryResource.class);

    private static final String ENTITY_NAME = "workEntry";
        
    private final WorkEntryService workEntryService;

    public WorkEntryResource(WorkEntryService workEntryService) {
        this.workEntryService = workEntryService;
    }

    /**
     * POST  /work-entries : Create a new workEntry.
     *
     * @param workEntryDTO the workEntryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workEntryDTO, or with status 400 (Bad Request) if the workEntry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-entries")
    @Timed
    public ResponseEntity<WorkEntryDTO> createWorkEntry(@RequestBody WorkEntryDTO workEntryDTO) throws URISyntaxException {
        log.debug("REST request to save WorkEntry : {}", workEntryDTO);
        if (workEntryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new workEntry cannot already have an ID")).body(null);
        }
        WorkEntryDTO result = workEntryService.save(workEntryDTO);
        return ResponseEntity.created(new URI("/api/work-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-entries : Updates an existing workEntry.
     *
     * @param workEntryDTO the workEntryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workEntryDTO,
     * or with status 400 (Bad Request) if the workEntryDTO is not valid,
     * or with status 500 (Internal Server Error) if the workEntryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-entries")
    @Timed
    public ResponseEntity<WorkEntryDTO> updateWorkEntry(@RequestBody WorkEntryDTO workEntryDTO) throws URISyntaxException {
        log.debug("REST request to update WorkEntry : {}", workEntryDTO);
        if (workEntryDTO.getId() == null) {
            return createWorkEntry(workEntryDTO);
        }
        WorkEntryDTO result = workEntryService.save(workEntryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-entries : get all the workEntries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workEntries in body
     */
    @GetMapping("/work-entries")
    @Timed
    public List<WorkEntryDTO> getAllWorkEntries() {
        log.debug("REST request to get all WorkEntries");
        return workEntryService.findAll();
    }

    /**
     * GET  /work-entries/:id : get the "id" workEntry.
     *
     * @param id the id of the workEntryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workEntryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/work-entries/{id}")
    @Timed
    public ResponseEntity<WorkEntryDTO> getWorkEntry(@PathVariable Long id) {
        log.debug("REST request to get WorkEntry : {}", id);
        WorkEntryDTO workEntryDTO = workEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workEntryDTO));
    }

    /**
     * DELETE  /work-entries/:id : delete the "id" workEntry.
     *
     * @param id the id of the workEntryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-entries/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkEntry(@PathVariable Long id) {
        log.debug("REST request to delete WorkEntry : {}", id);
        workEntryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
