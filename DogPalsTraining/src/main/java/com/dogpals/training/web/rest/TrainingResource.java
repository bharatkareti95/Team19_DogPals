package com.dogpals.training.web.rest;

import com.dogpals.training.service.TrainingService;
import com.dogpals.training.web.rest.errors.BadRequestAlertException;
import com.dogpals.training.service.dto.TrainingDTO;
import com.dogpals.training.security.SecurityUtils;
import com.dogpals.training.domain.Training;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link com.dogpals.training.domain.Training}.
 */
@RestController
@RequestMapping("/api")
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    private static final String ENTITY_NAME = "dogPalsTrainingTraining";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainingService trainingService;

    public TrainingResource(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    /**
     * {@code POST  /trainings} : Create a new training.
     *
     * @param trainingDTO the trainingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainingDTO, or with status {@code 400 (Bad Request)} if the training has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trainings")
    public ResponseEntity<TrainingDTO> createTraining(@Valid @RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to save Training : {}", trainingDTO);
        if (trainingDTO.getId() != null) {
            throw new BadRequestAlertException("A new training cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        log.debug("REST create set Training to userId : {}", userId.get());
        trainingDTO.setUserId(userId.get().intValue());
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trainings} : Updates an existing training.
     *
     * @param trainingDTO the trainingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainingDTO,
     * or with status {@code 400 (Bad Request)} if the trainingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trainings")
    public ResponseEntity<TrainingDTO> updateTraining(@Valid @RequestBody TrainingDTO trainingDTO) throws URISyntaxException {
        log.debug("REST request to update Training : {}", trainingDTO);
        if (trainingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        log.debug("REST create set Training to userId : {}", userId.get());
        trainingDTO.setUserId(userId.get().intValue());
        TrainingDTO result = trainingService.save(trainingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trainingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trainings} : get all the trainings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainings in body.
     */
    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingDTO>> getAllTrainings(Pageable pageable) {
        log.debug("REST request to get a page of Trainings");
        Page<TrainingDTO> page = trainingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trainings/:id} : get the "id" training.
     *
     * @param id the id of the trainingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trainings/{id}")
    public ResponseEntity<TrainingDTO> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        Optional<TrainingDTO> trainingDTO = trainingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainingDTO);
    }


    //To get all Tranings Under particular ADMIN with its booking details
    @GetMapping("/trainings/bookings")
    public Set<Training> getTrainingBookings() {

        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        log.debug("REST create set Training to userId : {}", userId.get());

        Set<Training> training = trainingService.findByAllBookings(userId.get().intValue());
        log.debug("REST requested list of Booking in Training");
        log.debug(training.toString());
        return training;
    }

    /**
     * {@code DELETE  /trainings/:id} : delete the "id" training.
     *
     * @param id the id of the trainingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trainings/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        try{ 
            trainingService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
            } 
         catch ( Exception e) {
             log.debug("FAILED");
             //return ResponseEntity.noContent().headers(HeaderUtil.createFailureAlert( applicationName, true, ENTITY_NAME,"constrain", e.getMessage())).build();
             throw new BadRequestAlertException("THERE IS BOOKING EXISTS IN ", ENTITY_NAME, "idnull");
         }   
        
    }

    /**
     * {@code SEARCH  /_search/trainings?query=:query} : search for the training corresponding
     * to the query.
     *
     * @param query the query of the training search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/trainings")
    public ResponseEntity<List<TrainingDTO>> searchTrainings(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Trainings for query {}", query);
        Page<TrainingDTO> page = trainingService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

    @GetMapping("/training/userId")
    public ResponseEntity<TrainingDTO> getUserIdForTrainingModule() {
        log.debug("REST request to get userId of ");
        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        log.debug("REST create set Training to userId : {}", userId.get());
        TrainingDTO training = new TrainingDTO();
        Optional<TrainingDTO> trainingDTO  = Optional.ofNullable(training);
        
        return ResponseUtil.wrapOrNotFound(trainingDTO);
        }
}
