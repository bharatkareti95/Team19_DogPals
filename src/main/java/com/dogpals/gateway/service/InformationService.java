package com.dogpals.gateway.service;

import com.dogpals.gateway.service.dto.InformationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dogpals.gateway.domain.Information}.
 */
public interface InformationService {

    /**
     * Save a information.
     *
     * @param informationDTO the entity to save.
     * @return the persisted entity.
     */
    InformationDTO save(InformationDTO informationDTO);

    /**
     * Get all the information.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" information.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InformationDTO> findOne(Long id);

    /**
     * Delete the "id" information.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the information corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformationDTO> search(String query, Pageable pageable);
}
