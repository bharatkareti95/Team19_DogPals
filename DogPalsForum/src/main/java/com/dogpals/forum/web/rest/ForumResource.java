package com.dogpals.forum.web.rest;

import com.dogpals.forum.service.ForumService;
import com.dogpals.forum.web.rest.errors.BadRequestAlertException;
import com.dogpals.forum.service.dto.ForumDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
 * REST controller for managing {@link com.dogpals.forum.domain.Forum}.
 */
@RestController
@RequestMapping("/api")
public class ForumResource {

    private final Logger log = LoggerFactory.getLogger(ForumResource.class);

    private static final String ENTITY_NAME = "dogPalsForumForum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ForumService forumService;

    public ForumResource(ForumService forumService) {
        this.forumService = forumService;
    }

    /**
     * {@code POST  /forums} : Create a new forum.
     *
     * @param forumDTO the forumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forumDTO, or with status {@code 400 (Bad Request)} if the forum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forums")
    public ResponseEntity<ForumDTO> createForum(@Valid @RequestBody ForumDTO forumDTO) throws URISyntaxException {
        log.debug("REST request to save Forum : {}", forumDTO);
        if (forumDTO.getId() != null) {
            throw new BadRequestAlertException("A new forum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForumDTO result = forumService.save(forumDTO);
        return ResponseEntity.created(new URI("/api/forums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forums} : Updates an existing forum.
     *
     * @param forumDTO the forumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forumDTO,
     * or with status {@code 400 (Bad Request)} if the forumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forums")
    public ResponseEntity<ForumDTO> updateForum(@Valid @RequestBody ForumDTO forumDTO) throws URISyntaxException {
        log.debug("REST request to update Forum : {}", forumDTO);
        if (forumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ForumDTO result = forumService.save(forumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, forumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forums} : get all the forums.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forums in body.
     */
    @GetMapping("/forums")
    public ResponseEntity<List<ForumDTO>> getAllForums(Pageable pageable) {
        log.debug("REST request to get a page of Forums");
        Page<ForumDTO> page = forumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /forums/:id} : get the "id" forum.
     *
     * @param id the id of the forumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forums/{id}")
    public ResponseEntity<ForumDTO> getForum(@PathVariable Long id) {
        log.debug("REST request to get Forum : {}", id);
        Optional<ForumDTO> forumDTO = forumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(forumDTO);
    }

    /**
     * {@code DELETE  /forums/:id} : delete the "id" forum.
     *
     * @param id the id of the forumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forums/{id}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long id) {
        log.debug("REST request to delete Forum : {}", id);
        forumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/forums?query=:query} : search for the forum corresponding
     * to the query.
     *
     * @param query the query of the forum search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/forums")
    public ResponseEntity<List<ForumDTO>> searchForums(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Forums for query {}", query);
        Page<ForumDTO> page = forumService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
