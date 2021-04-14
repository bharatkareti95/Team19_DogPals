package com.dogpals.training.web.rest;

import com.dogpals.training.DogPalsTrainingApp;
import com.dogpals.training.domain.Training;
import com.dogpals.training.repository.TrainingRepository;
import com.dogpals.training.repository.search.TrainingSearchRepository;
import com.dogpals.training.service.TrainingService;
import com.dogpals.training.service.dto.TrainingDTO;
import com.dogpals.training.service.mapper.TrainingMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TrainingResource} REST controller.
 */
@SpringBootTest(classes = DogPalsTrainingApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TrainingResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final String DEFAULT_AGENCY = "AAAAAAAAAA";
    private static final String UPDATED_AGENCY = "BBBBBBBBBB";

    private static final String DEFAULT_BOOKING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_BOOKING_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SLOT = 1;
    private static final Integer UPDATED_SLOT = 2;

    private static final Float DEFAULT_POPULARITY = 1F;
    private static final Float UPDATED_POPULARITY = 2F;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainingMapper trainingMapper;

    @Autowired
    private TrainingService trainingService;

    /**
     * This repository is mocked in the com.dogpals.training.repository.search test package.
     *
     * @see com.dogpals.training.repository.search.TrainingSearchRepositoryMockConfiguration
     */
    @Autowired
    private TrainingSearchRepository mockTrainingSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainingMockMvc;

    private Training training;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createEntity(EntityManager em) {
        Training training = new Training()
            .title(DEFAULT_TITLE)
            .date(DEFAULT_DATE)
            .details(DEFAULT_DETAILS)
            .location(DEFAULT_LOCATION)
            .price(DEFAULT_PRICE)
            .agency(DEFAULT_AGENCY)
            .bookingStatus(DEFAULT_BOOKING_STATUS)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .slot(DEFAULT_SLOT)
            .popularity(DEFAULT_POPULARITY);
        return training;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Training createUpdatedEntity(EntityManager em) {
        Training training = new Training()
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .details(UPDATED_DETAILS)
            .location(UPDATED_LOCATION)
            .price(UPDATED_PRICE)
            .agency(UPDATED_AGENCY)
            .bookingStatus(UPDATED_BOOKING_STATUS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .slot(UPDATED_SLOT)
            .popularity(UPDATED_POPULARITY);
        return training;
    }

    @BeforeEach
    public void initTest() {
        training = createEntity(em);
    }

    @Test
    @Transactional
    public void createTraining() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isCreated());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTraining.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTraining.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testTraining.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTraining.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTraining.getAgency()).isEqualTo(DEFAULT_AGENCY);
        assertThat(testTraining.getBookingStatus()).isEqualTo(DEFAULT_BOOKING_STATUS);
        assertThat(testTraining.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTraining.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTraining.getSlot()).isEqualTo(DEFAULT_SLOT);
        assertThat(testTraining.getPopularity()).isEqualTo(DEFAULT_POPULARITY);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(1)).save(testTraining);
    }

    @Test
    @Transactional
    public void createTrainingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trainingRepository.findAll().size();

        // Create the Training with an existing ID
        training.setId(1L);
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(0)).save(training);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setTitle(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setDate(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setLocation(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setPrice(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setAgency(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBookingStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setBookingStatus(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setStartTime(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setEndTime(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPopularityIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainingRepository.findAll().size();
        // set the field null
        training.setPopularity(null);

        // Create the Training, which fails.
        TrainingDTO trainingDTO = trainingMapper.toDto(training);


        restTrainingMockMvc.perform(post("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrainings() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get all the trainingList
        restTrainingMockMvc.perform(get("/api/trainings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].agency").value(hasItem(DEFAULT_AGENCY)))
            .andExpect(jsonPath("$.[*].bookingStatus").value(hasItem(DEFAULT_BOOKING_STATUS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].slot").value(hasItem(DEFAULT_SLOT)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.agency").value(DEFAULT_AGENCY))
            .andExpect(jsonPath("$.bookingStatus").value(DEFAULT_BOOKING_STATUS))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.slot").value(DEFAULT_SLOT))
            .andExpect(jsonPath("$.popularity").value(DEFAULT_POPULARITY.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTraining() throws Exception {
        // Get the training
        restTrainingMockMvc.perform(get("/api/trainings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Update the training
        Training updatedTraining = trainingRepository.findById(training.getId()).get();
        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
        em.detach(updatedTraining);
        updatedTraining
            .title(UPDATED_TITLE)
            .date(UPDATED_DATE)
            .details(UPDATED_DETAILS)
            .location(UPDATED_LOCATION)
            .price(UPDATED_PRICE)
            .agency(UPDATED_AGENCY)
            .bookingStatus(UPDATED_BOOKING_STATUS)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .slot(UPDATED_SLOT)
            .popularity(UPDATED_POPULARITY);
        TrainingDTO trainingDTO = trainingMapper.toDto(updatedTraining);

        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isOk());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
        Training testTraining = trainingList.get(trainingList.size() - 1);
        assertThat(testTraining.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTraining.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTraining.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testTraining.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTraining.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTraining.getAgency()).isEqualTo(UPDATED_AGENCY);
        assertThat(testTraining.getBookingStatus()).isEqualTo(UPDATED_BOOKING_STATUS);
        assertThat(testTraining.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTraining.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTraining.getSlot()).isEqualTo(UPDATED_SLOT);
        assertThat(testTraining.getPopularity()).isEqualTo(UPDATED_POPULARITY);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(1)).save(testTraining);
    }

    @Test
    @Transactional
    public void updateNonExistingTraining() throws Exception {
        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();

        // Create the Training
        TrainingDTO trainingDTO = trainingMapper.toDto(training);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainingMockMvc.perform(put("/api/trainings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(trainingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Training in the database
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(0)).save(training);
    }

    @Test
    @Transactional
    public void deleteTraining() throws Exception {
        // Initialize the database
        trainingRepository.saveAndFlush(training);

        int databaseSizeBeforeDelete = trainingRepository.findAll().size();

        // Delete the training
        restTrainingMockMvc.perform(delete("/api/trainings/{id}", training.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Training> trainingList = trainingRepository.findAll();
        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Training in Elasticsearch
        verify(mockTrainingSearchRepository, times(1)).deleteById(training.getId());
    }

    @Test
    @Transactional
    public void searchTraining() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        trainingRepository.saveAndFlush(training);
        when(mockTrainingSearchRepository.search(queryStringQuery("id:" + training.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(training), PageRequest.of(0, 1), 1));

        // Search the training
        restTrainingMockMvc.perform(get("/api/_search/trainings?query=id:" + training.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].agency").value(hasItem(DEFAULT_AGENCY)))
            .andExpect(jsonPath("$.[*].bookingStatus").value(hasItem(DEFAULT_BOOKING_STATUS)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].slot").value(hasItem(DEFAULT_SLOT)))
            .andExpect(jsonPath("$.[*].popularity").value(hasItem(DEFAULT_POPULARITY.doubleValue())));
    }
}
