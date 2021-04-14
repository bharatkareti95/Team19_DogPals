package com.dogpals.forum.web.rest;

import com.dogpals.forum.DogPalsForumApp;
import com.dogpals.forum.domain.Forum;
import com.dogpals.forum.repository.ForumRepository;
import com.dogpals.forum.repository.search.ForumSearchRepository;
import com.dogpals.forum.service.ForumService;
import com.dogpals.forum.service.dto.ForumDTO;
import com.dogpals.forum.service.mapper.ForumMapper;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dogpals.forum.domain.enumeration.ListTopic;
/**
 * Integration tests for the {@link ForumResource} REST controller.
 */
@SpringBootTest(classes = DogPalsForumApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ForumResourceIT {

    private static final ListTopic DEFAULT_TOPIC = ListTopic.Toys;
    private static final ListTopic UPDATED_TOPIC = ListTopic.Diet;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private ForumService forumService;

    /**
     * This repository is mocked in the com.dogpals.forum.repository.search test package.
     *
     * @see com.dogpals.forum.repository.search.ForumSearchRepositoryMockConfiguration
     */
    @Autowired
    private ForumSearchRepository mockForumSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restForumMockMvc;

    private Forum forum;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forum createEntity(EntityManager em) {
        Forum forum = new Forum()
            .topic(DEFAULT_TOPIC);
        return forum;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forum createUpdatedEntity(EntityManager em) {
        Forum forum = new Forum()
            .topic(UPDATED_TOPIC);
        return forum;
    }

    @BeforeEach
    public void initTest() {
        forum = createEntity(em);
    }

    @Test
    @Transactional
    public void createForum() throws Exception {
        int databaseSizeBeforeCreate = forumRepository.findAll().size();
        // Create the Forum
        ForumDTO forumDTO = forumMapper.toDto(forum);
        restForumMockMvc.perform(post("/api/forums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isCreated());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeCreate + 1);
        Forum testForum = forumList.get(forumList.size() - 1);
        assertThat(testForum.getTopic()).isEqualTo(DEFAULT_TOPIC);

        // Validate the Forum in Elasticsearch
        verify(mockForumSearchRepository, times(1)).save(testForum);
    }

    @Test
    @Transactional
    public void createForumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forumRepository.findAll().size();

        // Create the Forum with an existing ID
        forum.setId(1L);
        ForumDTO forumDTO = forumMapper.toDto(forum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForumMockMvc.perform(post("/api/forums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeCreate);

        // Validate the Forum in Elasticsearch
        verify(mockForumSearchRepository, times(0)).save(forum);
    }


    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = forumRepository.findAll().size();
        // set the field null
        forum.setTopic(null);

        // Create the Forum, which fails.
        ForumDTO forumDTO = forumMapper.toDto(forum);


        restForumMockMvc.perform(post("/api/forums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isBadRequest());

        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForums() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);

        // Get all the forumList
        restForumMockMvc.perform(get("/api/forums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forum.getId().intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())));
    }
    
    @Test
    @Transactional
    public void getForum() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);

        // Get the forum
        restForumMockMvc.perform(get("/api/forums/{id}", forum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(forum.getId().intValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingForum() throws Exception {
        // Get the forum
        restForumMockMvc.perform(get("/api/forums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForum() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);

        int databaseSizeBeforeUpdate = forumRepository.findAll().size();

        // Update the forum
        Forum updatedForum = forumRepository.findById(forum.getId()).get();
        // Disconnect from session so that the updates on updatedForum are not directly saved in db
        em.detach(updatedForum);
        updatedForum
            .topic(UPDATED_TOPIC);
        ForumDTO forumDTO = forumMapper.toDto(updatedForum);

        restForumMockMvc.perform(put("/api/forums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isOk());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeUpdate);
        Forum testForum = forumList.get(forumList.size() - 1);
        assertThat(testForum.getTopic()).isEqualTo(UPDATED_TOPIC);

        // Validate the Forum in Elasticsearch
        verify(mockForumSearchRepository, times(1)).save(testForum);
    }

    @Test
    @Transactional
    public void updateNonExistingForum() throws Exception {
        int databaseSizeBeforeUpdate = forumRepository.findAll().size();

        // Create the Forum
        ForumDTO forumDTO = forumMapper.toDto(forum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restForumMockMvc.perform(put("/api/forums")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Forum in the database
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Forum in Elasticsearch
        verify(mockForumSearchRepository, times(0)).save(forum);
    }

    @Test
    @Transactional
    public void deleteForum() throws Exception {
        // Initialize the database
        forumRepository.saveAndFlush(forum);

        int databaseSizeBeforeDelete = forumRepository.findAll().size();

        // Delete the forum
        restForumMockMvc.perform(delete("/api/forums/{id}", forum.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Forum> forumList = forumRepository.findAll();
        assertThat(forumList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Forum in Elasticsearch
        verify(mockForumSearchRepository, times(1)).deleteById(forum.getId());
    }

    @Test
    @Transactional
    public void searchForum() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        forumRepository.saveAndFlush(forum);
        when(mockForumSearchRepository.search(queryStringQuery("id:" + forum.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(forum), PageRequest.of(0, 1), 1));

        // Search the forum
        restForumMockMvc.perform(get("/api/_search/forums?query=id:" + forum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forum.getId().intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())));
    }
}
