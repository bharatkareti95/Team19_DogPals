package com.dogpals.training.web.rest;

import com.dogpals.training.service.PopularService;
import com.dogpals.training.web.rest.errors.BadRequestAlertException;
import com.dogpals.training.service.dto.PopularDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.dogpals.training.domain.Popular}.
 */
@RestController
@RequestMapping("/api")
public class PopularResource {

    private final Logger log = LoggerFactory.getLogger(PopularResource.class);

    private static final String ENTITY_NAME = "dogPalsTrainingPopular";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PopularService popularService;

    public PopularResource(PopularService popularService) {
        this.popularService = popularService;
    }

    /**
     * {@code POST  /populars} : Create a new popular.
     *
     * @param popularDTO the popularDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new popularDTO, or with status {@code 400 (Bad Request)} if the popular has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/populars")
    public ResponseEntity<PopularDTO> createPopular(@Valid @RequestBody PopularDTO popularDTO) throws URISyntaxException {
        log.debug("REST request to save Popular : {}", popularDTO);
        if (popularDTO.getId() != null) {
            throw new BadRequestAlertException("A new popular cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PopularDTO result = popularService.save(popularDTO);
        return ResponseEntity.created(new URI("/api/populars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /populars} : Updates an existing popular.
     *
     * @param popularDTO the popularDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated popularDTO,
     * or with status {@code 400 (Bad Request)} if the popularDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the popularDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/populars")
    public ResponseEntity<PopularDTO> updatePopular(@Valid @RequestBody PopularDTO popularDTO) throws URISyntaxException {
        log.debug("REST request to update Popular : {}", popularDTO);
        if (popularDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PopularDTO result = popularService.save(popularDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, popularDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /populars} : get all the populars.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of populars in body.
     */
    @GetMapping("/populars")
    public List<PopularDTO> getAllPopulars() {
        log.debug("REST request to get all Populars");
        return popularService.findAll();
    }

    /**
     * {@code GET  /populars/:id} : get the "id" popular.
     *
     * @param id the id of the popularDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the popularDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/populars/{id}")
    public ResponseEntity<PopularDTO> getPopular(@PathVariable Long id) {
        log.debug("REST request to get Popular : {}", id);
        Optional<PopularDTO> popularDTO = popularService.findOne(id);
        return ResponseUtil.wrapOrNotFound(popularDTO);
    }

    /**
     * {@code DELETE  /populars/:id} : delete the "id" popular.
     *
     * @param id the id of the popularDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/populars/{id}")
    public ResponseEntity<Void> deletePopular(@PathVariable Long id) {
        log.debug("REST request to delete Popular : {}", id);
        popularService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/populars?query=:query} : search for the popular corresponding
     * to the query.
     *
     * @param query the query of the popular search.
     * @return the result of the search.
     */
    @GetMapping("/_search/populars")
    public List<PopularDTO> searchPopulars(@RequestParam String query) {
        log.debug("REST request to search Populars for query {}", query);
        return popularService.search(query);
    }
}
