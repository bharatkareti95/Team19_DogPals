package com.dogpals.forum.service;

import com.dogpals.forum.domain.Forum;
import com.dogpals.forum.repository.ForumRepository;
import com.dogpals.forum.repository.search.ForumSearchRepository;
import com.dogpals.forum.service.dto.ForumDTO;
import com.dogpals.forum.service.mapper.ForumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Forum}.
 */
@Service
@Transactional
public class ForumService {

    private final Logger log = LoggerFactory.getLogger(ForumService.class);

    private final ForumRepository forumRepository;

    private final ForumMapper forumMapper;

    private final ForumSearchRepository forumSearchRepository;

    public ForumService(ForumRepository forumRepository, ForumMapper forumMapper, ForumSearchRepository forumSearchRepository) {
        this.forumRepository = forumRepository;
        this.forumMapper = forumMapper;
        this.forumSearchRepository = forumSearchRepository;
    }

    /**
     * Save a forum.
     *
     * @param forumDTO the entity to save.
     * @return the persisted entity.
     */
    public ForumDTO save(ForumDTO forumDTO) {
        log.debug("Request to save Forum : {}", forumDTO);
        Forum forum = forumMapper.toEntity(forumDTO);
        forum = forumRepository.save(forum);
        ForumDTO result = forumMapper.toDto(forum);
        forumSearchRepository.save(forum);
        return result;
    }

    /**
     * Get all the forums.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ForumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Forums");
        return forumRepository.findAll(pageable)
            .map(forumMapper::toDto);
    }


    /**
     * Get one forum by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ForumDTO> findOne(Long id) {
        log.debug("Request to get Forum : {}", id);
        return forumRepository.findById(id)
            .map(forumMapper::toDto);
    }

    /**
     * Delete the forum by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Forum : {}", id);
        forumRepository.deleteById(id);
        forumSearchRepository.deleteById(id);
    }

    /**
     * Search for the forum corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ForumDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Forums for query {}", query);
        return forumSearchRepository.search(queryStringQuery(query), pageable)
            .map(forumMapper::toDto);
    }
}
