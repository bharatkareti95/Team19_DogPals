package com.dogpals.gateway.web.rest;

import com.dogpals.gateway.DogPalsApp;
import com.dogpals.gateway.domain.Information;
import com.dogpals.gateway.repository.InformationRepository;
import com.dogpals.gateway.repository.search.InformationSearchRepository;
import com.dogpals.gateway.service.InformationService;
import com.dogpals.gateway.service.dto.InformationDTO;
import com.dogpals.gateway.service.mapper.InformationMapper;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link InformationResource} REST controller.
 */
@SpringBootTest(classes = DogPalsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private InformationMapper informationMapper;

    @Autowired
    private InformationService informationService;

    /**
     * This repository is mocked in the com.dogpals.gateway.repository.search test package.
     *
     * @see com.dogpals.gateway.repository.search.InformationSearchRepositoryMockConfiguration
     */
    @Autowired
    private InformationSearchRepository mockInformationSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInformationMockMvc;

    private Information information;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Information createEntity(EntityManager em) {
        Information information = new Information()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .date(DEFAULT_DATE);
        return information;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Information createUpdatedEntity(EntityManager em) {
        Information information = new Information()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);
        return information;
    }

    @BeforeEach
    public void initTest() {
        information = createEntity(em);
    }

    @Test
    @Transactional
    public void createInformation() throws Exception {
        int databaseSizeBeforeCreate = informationRepository.findAll().size();
        // Create the Information
        InformationDTO informationDTO = informationMapper.toDto(information);
        restInformationMockMvc.perform(post("/api/information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informationDTO)))
            .andExpect(status().isCreated());

        // Validate the Information in the database
        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeCreate + 1);
        Information testInformation = informationList.get(informationList.size() - 1);
        assertThat(testInformation.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testInformation.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testInformation.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the Information in Elasticsearch
        verify(mockInformationSearchRepository, times(1)).save(testInformation);
    }

    @Test
    @Transactional
    public void createInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = informationRepository.findAll().size();

        // Create the Information with an existing ID
        information.setId(1L);
        InformationDTO informationDTO = informationMapper.toDto(information);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInformationMockMvc.perform(post("/api/information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Information in the database
        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Information in Elasticsearch
        verify(mockInformationSearchRepository, times(0)).save(information);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = informationRepository.findAll().size();
        // set the field null
        information.setTitle(null);

        // Create the Information, which fails.
        InformationDTO informationDTO = informationMapper.toDto(information);


        restInformationMockMvc.perform(post("/api/information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informationDTO)))
            .andExpect(status().isBadRequest());

        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = informationRepository.findAll().size();
        // set the field null
        information.setDate(null);

        // Create the Information, which fails.
        InformationDTO informationDTO = informationMapper.toDto(information);


        restInformationMockMvc.perform(post("/api/information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informationDTO)))
            .andExpect(status().isBadRequest());

        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInformation() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList
        restInformationMockMvc.perform(get("/api/information?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(information.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getInformation() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get the information
        restInformationMockMvc.perform(get("/api/information/{id}", information.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(information.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInformation() throws Exception {
        // Get the information
        restInformationMockMvc.perform(get("/api/information/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInformation() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        int databaseSizeBeforeUpdate = informationRepository.findAll().size();

        // Update the information
        Information updatedInformation = informationRepository.findById(information.getId()).get();
        // Disconnect from session so that the updates on updatedInformation are not directly saved in db
        em.detach(updatedInformation);
        updatedInformation
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);
        InformationDTO informationDTO = informationMapper.toDto(updatedInformation);

        restInformationMockMvc.perform(put("/api/information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informationDTO)))
            .andExpect(status().isOk());

        // Validate the Information in the database
        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeUpdate);
        Information testInformation = informationList.get(informationList.size() - 1);
        assertThat(testInformation.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testInformation.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testInformation.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the Information in Elasticsearch
        verify(mockInformationSearchRepository, times(1)).save(testInformation);
    }

    @Test
    @Transactional
    public void updateNonExistingInformation() throws Exception {
        int databaseSizeBeforeUpdate = informationRepository.findAll().size();

        // Create the Information
        InformationDTO informationDTO = informationMapper.toDto(information);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInformationMockMvc.perform(put("/api/information")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(informationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Information in the database
        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Information in Elasticsearch
        verify(mockInformationSearchRepository, times(0)).save(information);
    }

    @Test
    @Transactional
    public void deleteInformation() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        int databaseSizeBeforeDelete = informationRepository.findAll().size();

        // Delete the information
        restInformationMockMvc.perform(delete("/api/information/{id}", information.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Information> informationList = informationRepository.findAll();
        assertThat(informationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Information in Elasticsearch
        verify(mockInformationSearchRepository, times(1)).deleteById(information.getId());
    }

    @Test
    @Transactional
    public void searchInformation() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        informationRepository.saveAndFlush(information);
        when(mockInformationSearchRepository.search(queryStringQuery("id:" + information.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(information), PageRequest.of(0, 1), 1));

        // Search the information
        restInformationMockMvc.perform(get("/api/_search/information?query=id:" + information.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(information.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
}
