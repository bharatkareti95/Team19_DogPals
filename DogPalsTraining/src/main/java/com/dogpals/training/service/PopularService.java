package com.dogpals.training.service;

import com.dogpals.training.domain.Popular;
import com.dogpals.training.repository.PopularRepository;
import com.dogpals.training.repository.search.PopularSearchRepository;
import com.dogpals.training.service.dto.PopularDTO;
import com.dogpals.training.service.mapper.PopularMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Popular}.
 */
@Service
@Transactional
public class PopularService {

    private final Logger log = LoggerFactory.getLogger(PopularService.class);

    private final PopularRepository popularRepository;

    private final PopularMapper popularMapper;

    private final PopularSearchRepository popularSearchRepository;

    public PopularService(PopularRepository popularRepository, PopularMapper popularMapper, PopularSearchRepository popularSearchRepository) {
        this.popularRepository = popularRepository;
        this.popularMapper = popularMapper;
        this.popularSearchRepository = popularSearchRepository;
    }

    /**
     * Save a popular.
     *
     * @param popularDTO the entity to save.
     * @return the persisted entity.
     */
    public PopularDTO save(PopularDTO popularDTO) {
        log.debug("Request to save Popular : {}", popularDTO);
        Popular popular = popularMapper.toEntity(popularDTO);
        popular = popularRepository.save(popular);
        PopularDTO result = popularMapper.toDto(popular);
        popularSearchRepository.save(popular);
        return result;
    }

    /**
     * Get all the populars.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PopularDTO> findAll() {
        log.debug("Request to get all Populars");
        return popularRepository.findAll().stream()
            .map(popularMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one popular by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PopularDTO> findOne(Long id) {
        log.debug("Request to get Popular : {}", id);
        return popularRepository.findById(id)
            .map(popularMapper::toDto);
    }

    /**
     * Delete the popular by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Popular : {}", id);
        popularRepository.deleteById(id);
        popularSearchRepository.deleteById(id);
    }

    /**
     * Search for the popular corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PopularDTO> search(String query) {
        log.debug("Request to search Populars for query {}", query);
        return StreamSupport
            .stream(popularSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(popularMapper::toDto)
        .collect(Collectors.toList());
    }
}
