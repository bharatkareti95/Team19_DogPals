package com.dogpals.training.web.rest;

import com.dogpals.training.DogPalsTrainingApp;
import com.dogpals.training.domain.Popular;
import com.dogpals.training.repository.PopularRepository;
import com.dogpals.training.repository.search.PopularSearchRepository;
import com.dogpals.training.service.PopularService;
import com.dogpals.training.service.dto.PopularDTO;
import com.dogpals.training.service.mapper.PopularMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dogpals.training.domain.enumeration.LikeDisLike;
/**
 * Integration tests for the {@link PopularResource} REST controller.
 */
@SpringBootTest(classes = DogPalsTrainingApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PopularResourceIT {

    private static final LikeDisLike DEFAULT_LIKE_OR_DISLIKE = LikeDisLike.Like;
    private static final LikeDisLike UPDATED_LIKE_OR_DISLIKE = LikeDisLike.Dislike;

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    @Autowired
    private PopularRepository popularRepository;

    @Autowired
    private PopularMapper popularMapper;

    @Autowired
    private PopularService popularService;

    /**
     * This repository is mocked in the com.dogpals.training.repository.search test package.
     *
     * @see com.dogpals.training.repository.search.PopularSearchRepositoryMockConfiguration
     */
    @Autowired
    private PopularSearchRepository mockPopularSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPopularMockMvc;

    private Popular popular;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Popular createEntity(EntityManager em) {
        Popular popular = new Popular()
            .likeOrDislike(DEFAULT_LIKE_OR_DISLIKE)
            .userId(DEFAULT_USER_ID);
        return popular;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Popular createUpdatedEntity(EntityManager em) {
        Popular popular = new Popular()
            .likeOrDislike(UPDATED_LIKE_OR_DISLIKE)
            .userId(UPDATED_USER_ID);
        return popular;
    }

    @BeforeEach
    public void initTest() {
        popular = createEntity(em);
    }

    @Test
    @Transactional
    public void createPopular() throws Exception {
        int databaseSizeBeforeCreate = popularRepository.findAll().size();
        // Create the Popular
        PopularDTO popularDTO = popularMapper.toDto(popular);
        restPopularMockMvc.perform(post("/api/populars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularDTO)))
            .andExpect(status().isCreated());

        // Validate the Popular in the database
        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeCreate + 1);
        Popular testPopular = popularList.get(popularList.size() - 1);
        assertThat(testPopular.getLikeOrDislike()).isEqualTo(DEFAULT_LIKE_OR_DISLIKE);
        assertThat(testPopular.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the Popular in Elasticsearch
        verify(mockPopularSearchRepository, times(1)).save(testPopular);
    }

    @Test
    @Transactional
    public void createPopularWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = popularRepository.findAll().size();

        // Create the Popular with an existing ID
        popular.setId(1L);
        PopularDTO popularDTO = popularMapper.toDto(popular);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPopularMockMvc.perform(post("/api/populars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Popular in the database
        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeCreate);

        // Validate the Popular in Elasticsearch
        verify(mockPopularSearchRepository, times(0)).save(popular);
    }


    @Test
    @Transactional
    public void checkLikeOrDislikeIsRequired() throws Exception {
        int databaseSizeBeforeTest = popularRepository.findAll().size();
        // set the field null
        popular.setLikeOrDislike(null);

        // Create the Popular, which fails.
        PopularDTO popularDTO = popularMapper.toDto(popular);


        restPopularMockMvc.perform(post("/api/populars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularDTO)))
            .andExpect(status().isBadRequest());

        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = popularRepository.findAll().size();
        // set the field null
        popular.setUserId(null);

        // Create the Popular, which fails.
        PopularDTO popularDTO = popularMapper.toDto(popular);


        restPopularMockMvc.perform(post("/api/populars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularDTO)))
            .andExpect(status().isBadRequest());

        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPopulars() throws Exception {
        // Initialize the database
        popularRepository.saveAndFlush(popular);

        // Get all the popularList
        restPopularMockMvc.perform(get("/api/populars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popular.getId().intValue())))
            .andExpect(jsonPath("$.[*].likeOrDislike").value(hasItem(DEFAULT_LIKE_OR_DISLIKE.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getPopular() throws Exception {
        // Initialize the database
        popularRepository.saveAndFlush(popular);

        // Get the popular
        restPopularMockMvc.perform(get("/api/populars/{id}", popular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(popular.getId().intValue()))
            .andExpect(jsonPath("$.likeOrDislike").value(DEFAULT_LIKE_OR_DISLIKE.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPopular() throws Exception {
        // Get the popular
        restPopularMockMvc.perform(get("/api/populars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePopular() throws Exception {
        // Initialize the database
        popularRepository.saveAndFlush(popular);

        int databaseSizeBeforeUpdate = popularRepository.findAll().size();

        // Update the popular
        Popular updatedPopular = popularRepository.findById(popular.getId()).get();
        // Disconnect from session so that the updates on updatedPopular are not directly saved in db
        em.detach(updatedPopular);
        updatedPopular
            .likeOrDislike(UPDATED_LIKE_OR_DISLIKE)
            .userId(UPDATED_USER_ID);
        PopularDTO popularDTO = popularMapper.toDto(updatedPopular);

        restPopularMockMvc.perform(put("/api/populars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularDTO)))
            .andExpect(status().isOk());

        // Validate the Popular in the database
        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeUpdate);
        Popular testPopular = popularList.get(popularList.size() - 1);
        assertThat(testPopular.getLikeOrDislike()).isEqualTo(UPDATED_LIKE_OR_DISLIKE);
        assertThat(testPopular.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the Popular in Elasticsearch
        verify(mockPopularSearchRepository, times(1)).save(testPopular);
    }

    @Test
    @Transactional
    public void updateNonExistingPopular() throws Exception {
        int databaseSizeBeforeUpdate = popularRepository.findAll().size();

        // Create the Popular
        PopularDTO popularDTO = popularMapper.toDto(popular);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPopularMockMvc.perform(put("/api/populars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(popularDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Popular in the database
        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Popular in Elasticsearch
        verify(mockPopularSearchRepository, times(0)).save(popular);
    }

    @Test
    @Transactional
    public void deletePopular() throws Exception {
        // Initialize the database
        popularRepository.saveAndFlush(popular);

        int databaseSizeBeforeDelete = popularRepository.findAll().size();

        // Delete the popular
        restPopularMockMvc.perform(delete("/api/populars/{id}", popular.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Popular> popularList = popularRepository.findAll();
        assertThat(popularList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Popular in Elasticsearch
        verify(mockPopularSearchRepository, times(1)).deleteById(popular.getId());
    }

    @Test
    @Transactional
    public void searchPopular() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        popularRepository.saveAndFlush(popular);
        when(mockPopularSearchRepository.search(queryStringQuery("id:" + popular.getId())))
            .thenReturn(Collections.singletonList(popular));

        // Search the popular
        restPopularMockMvc.perform(get("/api/_search/populars?query=id:" + popular.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(popular.getId().intValue())))
            .andExpect(jsonPath("$.[*].likeOrDislike").value(hasItem(DEFAULT_LIKE_OR_DISLIKE.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }
}
