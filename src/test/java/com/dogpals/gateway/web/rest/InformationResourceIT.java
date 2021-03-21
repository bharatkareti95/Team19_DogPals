package com.dogpals.gateway.web.rest;

import com.dogpals.gateway.DogpalsPresentationApp;
import com.dogpals.gateway.domain.Information;
import com.dogpals.gateway.repository.InformationRepository;
import com.dogpals.gateway.repository.search.InformationSearchRepository;
import com.dogpals.gateway.service.InformationService;
import com.dogpals.gateway.service.dto.InformationDTO;
import com.dogpals.gateway.service.mapper.InformationMapper;
import com.dogpals.gateway.service.dto.InformationCriteria;
import com.dogpals.gateway.service.InformationQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
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
@SpringBootTest(classes = DogpalsPresentationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class InformationResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CATAGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATAGORY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATEPOSTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEPOSTED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATEPOSTED = LocalDate.ofEpochDay(-1L);

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
    private InformationQueryService informationQueryService;

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
            .catagory(DEFAULT_CATAGORY)
            .dateposted(DEFAULT_DATEPOSTED);
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
            .catagory(UPDATED_CATAGORY)
            .dateposted(UPDATED_DATEPOSTED);
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
        assertThat(testInformation.getCatagory()).isEqualTo(DEFAULT_CATAGORY);
        assertThat(testInformation.getDateposted()).isEqualTo(DEFAULT_DATEPOSTED);

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
    public void checkCatagoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = informationRepository.findAll().size();
        // set the field null
        information.setCatagory(null);

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
    public void checkDatepostedIsRequired() throws Exception {
        int databaseSizeBeforeTest = informationRepository.findAll().size();
        // set the field null
        information.setDateposted(null);

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
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY)))
            .andExpect(jsonPath("$.[*].dateposted").value(hasItem(DEFAULT_DATEPOSTED.toString())));
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
            .andExpect(jsonPath("$.catagory").value(DEFAULT_CATAGORY))
            .andExpect(jsonPath("$.dateposted").value(DEFAULT_DATEPOSTED.toString()));
    }


    @Test
    @Transactional
    public void getInformationByIdFiltering() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        Long id = information.getId();

        defaultInformationShouldBeFound("id.equals=" + id);
        defaultInformationShouldNotBeFound("id.notEquals=" + id);

        defaultInformationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInformationShouldNotBeFound("id.greaterThan=" + id);

        defaultInformationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInformationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInformationByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where title equals to DEFAULT_TITLE
        defaultInformationShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the informationList where title equals to UPDATED_TITLE
        defaultInformationShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllInformationByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where title not equals to DEFAULT_TITLE
        defaultInformationShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the informationList where title not equals to UPDATED_TITLE
        defaultInformationShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllInformationByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultInformationShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the informationList where title equals to UPDATED_TITLE
        defaultInformationShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllInformationByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where title is not null
        defaultInformationShouldBeFound("title.specified=true");

        // Get all the informationList where title is null
        defaultInformationShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformationByTitleContainsSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where title contains DEFAULT_TITLE
        defaultInformationShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the informationList where title contains UPDATED_TITLE
        defaultInformationShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllInformationByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where title does not contain DEFAULT_TITLE
        defaultInformationShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the informationList where title does not contain UPDATED_TITLE
        defaultInformationShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllInformationByCatagoryIsEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where catagory equals to DEFAULT_CATAGORY
        defaultInformationShouldBeFound("catagory.equals=" + DEFAULT_CATAGORY);

        // Get all the informationList where catagory equals to UPDATED_CATAGORY
        defaultInformationShouldNotBeFound("catagory.equals=" + UPDATED_CATAGORY);
    }

    @Test
    @Transactional
    public void getAllInformationByCatagoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where catagory not equals to DEFAULT_CATAGORY
        defaultInformationShouldNotBeFound("catagory.notEquals=" + DEFAULT_CATAGORY);

        // Get all the informationList where catagory not equals to UPDATED_CATAGORY
        defaultInformationShouldBeFound("catagory.notEquals=" + UPDATED_CATAGORY);
    }

    @Test
    @Transactional
    public void getAllInformationByCatagoryIsInShouldWork() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where catagory in DEFAULT_CATAGORY or UPDATED_CATAGORY
        defaultInformationShouldBeFound("catagory.in=" + DEFAULT_CATAGORY + "," + UPDATED_CATAGORY);

        // Get all the informationList where catagory equals to UPDATED_CATAGORY
        defaultInformationShouldNotBeFound("catagory.in=" + UPDATED_CATAGORY);
    }

    @Test
    @Transactional
    public void getAllInformationByCatagoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where catagory is not null
        defaultInformationShouldBeFound("catagory.specified=true");

        // Get all the informationList where catagory is null
        defaultInformationShouldNotBeFound("catagory.specified=false");
    }
                @Test
    @Transactional
    public void getAllInformationByCatagoryContainsSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where catagory contains DEFAULT_CATAGORY
        defaultInformationShouldBeFound("catagory.contains=" + DEFAULT_CATAGORY);

        // Get all the informationList where catagory contains UPDATED_CATAGORY
        defaultInformationShouldNotBeFound("catagory.contains=" + UPDATED_CATAGORY);
    }

    @Test
    @Transactional
    public void getAllInformationByCatagoryNotContainsSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where catagory does not contain DEFAULT_CATAGORY
        defaultInformationShouldNotBeFound("catagory.doesNotContain=" + DEFAULT_CATAGORY);

        // Get all the informationList where catagory does not contain UPDATED_CATAGORY
        defaultInformationShouldBeFound("catagory.doesNotContain=" + UPDATED_CATAGORY);
    }


    @Test
    @Transactional
    public void getAllInformationByDatepostedIsEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted equals to DEFAULT_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.equals=" + DEFAULT_DATEPOSTED);

        // Get all the informationList where dateposted equals to UPDATED_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.equals=" + UPDATED_DATEPOSTED);
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted not equals to DEFAULT_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.notEquals=" + DEFAULT_DATEPOSTED);

        // Get all the informationList where dateposted not equals to UPDATED_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.notEquals=" + UPDATED_DATEPOSTED);
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsInShouldWork() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted in DEFAULT_DATEPOSTED or UPDATED_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.in=" + DEFAULT_DATEPOSTED + "," + UPDATED_DATEPOSTED);

        // Get all the informationList where dateposted equals to UPDATED_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.in=" + UPDATED_DATEPOSTED);
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsNullOrNotNull() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted is not null
        defaultInformationShouldBeFound("dateposted.specified=true");

        // Get all the informationList where dateposted is null
        defaultInformationShouldNotBeFound("dateposted.specified=false");
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted is greater than or equal to DEFAULT_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.greaterThanOrEqual=" + DEFAULT_DATEPOSTED);

        // Get all the informationList where dateposted is greater than or equal to UPDATED_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.greaterThanOrEqual=" + UPDATED_DATEPOSTED);
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted is less than or equal to DEFAULT_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.lessThanOrEqual=" + DEFAULT_DATEPOSTED);

        // Get all the informationList where dateposted is less than or equal to SMALLER_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.lessThanOrEqual=" + SMALLER_DATEPOSTED);
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsLessThanSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted is less than DEFAULT_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.lessThan=" + DEFAULT_DATEPOSTED);

        // Get all the informationList where dateposted is less than UPDATED_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.lessThan=" + UPDATED_DATEPOSTED);
    }

    @Test
    @Transactional
    public void getAllInformationByDatepostedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        informationRepository.saveAndFlush(information);

        // Get all the informationList where dateposted is greater than DEFAULT_DATEPOSTED
        defaultInformationShouldNotBeFound("dateposted.greaterThan=" + DEFAULT_DATEPOSTED);

        // Get all the informationList where dateposted is greater than SMALLER_DATEPOSTED
        defaultInformationShouldBeFound("dateposted.greaterThan=" + SMALLER_DATEPOSTED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInformationShouldBeFound(String filter) throws Exception {
        restInformationMockMvc.perform(get("/api/information?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(information.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY)))
            .andExpect(jsonPath("$.[*].dateposted").value(hasItem(DEFAULT_DATEPOSTED.toString())));

        // Check, that the count call also returns 1
        restInformationMockMvc.perform(get("/api/information/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInformationShouldNotBeFound(String filter) throws Exception {
        restInformationMockMvc.perform(get("/api/information?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInformationMockMvc.perform(get("/api/information/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
            .catagory(UPDATED_CATAGORY)
            .dateposted(UPDATED_DATEPOSTED);
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
        assertThat(testInformation.getCatagory()).isEqualTo(UPDATED_CATAGORY);
        assertThat(testInformation.getDateposted()).isEqualTo(UPDATED_DATEPOSTED);

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
            .andExpect(jsonPath("$.[*].catagory").value(hasItem(DEFAULT_CATAGORY)))
            .andExpect(jsonPath("$.[*].dateposted").value(hasItem(DEFAULT_DATEPOSTED.toString())));
    }
}
