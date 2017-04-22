package com.ocean.workzone.web.rest;

import com.ocean.workzone.JworkzoneApp;

import com.ocean.workzone.domain.WorkEntry;
import com.ocean.workzone.repository.WorkEntryRepository;
import com.ocean.workzone.service.WorkEntryService;
import com.ocean.workzone.service.dto.WorkEntryDTO;
import com.ocean.workzone.service.mapper.WorkEntryMapper;
import com.ocean.workzone.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.ocean.workzone.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WorkEntryResource REST controller.
 *
 * @see WorkEntryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JworkzoneApp.class)
public class WorkEntryResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UNIQUE_ID = "AAAAAAAAAA";
    private static final String UPDATED_UNIQUE_ID = "BBBBBBBBBB";

    @Autowired
    private WorkEntryRepository workEntryRepository;

    @Autowired
    private WorkEntryMapper workEntryMapper;

    @Autowired
    private WorkEntryService workEntryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWorkEntryMockMvc;

    private WorkEntry workEntry;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WorkEntryResource workEntryResource = new WorkEntryResource(workEntryService);
        this.restWorkEntryMockMvc = MockMvcBuilders.standaloneSetup(workEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEntry createEntity(EntityManager em) {
        WorkEntry workEntry = new WorkEntry()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .uniqueId(DEFAULT_UNIQUE_ID);
        return workEntry;
    }

    @Before
    public void initTest() {
        workEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEntry() throws Exception {
        int databaseSizeBeforeCreate = workEntryRepository.findAll().size();

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.workEntryToWorkEntryDTO(workEntry);
        restWorkEntryMockMvc.perform(post("/api/work-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEntry testWorkEntry = workEntryList.get(workEntryList.size() - 1);
        assertThat(testWorkEntry.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkEntry.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkEntry.getUniqueId()).isEqualTo(DEFAULT_UNIQUE_ID);
    }

    @Test
    @Transactional
    public void createWorkEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEntryRepository.findAll().size();

        // Create the WorkEntry with an existing ID
        workEntry.setId(1L);
        WorkEntryDTO workEntryDTO = workEntryMapper.workEntryToWorkEntryDTO(workEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEntryMockMvc.perform(post("/api/work-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWorkEntries() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        // Get all the workEntryList
        restWorkEntryMockMvc.perform(get("/api/work-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].uniqueId").value(hasItem(DEFAULT_UNIQUE_ID.toString())));
    }

    @Test
    @Transactional
    public void getWorkEntry() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);

        // Get the workEntry
        restWorkEntryMockMvc.perform(get("/api/work-entries/{id}", workEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workEntry.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.uniqueId").value(DEFAULT_UNIQUE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEntry() throws Exception {
        // Get the workEntry
        restWorkEntryMockMvc.perform(get("/api/work-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEntry() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();

        // Update the workEntry
        WorkEntry updatedWorkEntry = workEntryRepository.findOne(workEntry.getId());
        updatedWorkEntry
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .uniqueId(UPDATED_UNIQUE_ID);
        WorkEntryDTO workEntryDTO = workEntryMapper.workEntryToWorkEntryDTO(updatedWorkEntry);

        restWorkEntryMockMvc.perform(put("/api/work-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isOk());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate);
        WorkEntry testWorkEntry = workEntryList.get(workEntryList.size() - 1);
        assertThat(testWorkEntry.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkEntry.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkEntry.getUniqueId()).isEqualTo(UPDATED_UNIQUE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEntry() throws Exception {
        int databaseSizeBeforeUpdate = workEntryRepository.findAll().size();

        // Create the WorkEntry
        WorkEntryDTO workEntryDTO = workEntryMapper.workEntryToWorkEntryDTO(workEntry);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWorkEntryMockMvc.perform(put("/api/work-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkEntry in the database
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWorkEntry() throws Exception {
        // Initialize the database
        workEntryRepository.saveAndFlush(workEntry);
        int databaseSizeBeforeDelete = workEntryRepository.findAll().size();

        // Get the workEntry
        restWorkEntryMockMvc.perform(delete("/api/work-entries/{id}", workEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<WorkEntry> workEntryList = workEntryRepository.findAll();
        assertThat(workEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkEntry.class);
    }
}
