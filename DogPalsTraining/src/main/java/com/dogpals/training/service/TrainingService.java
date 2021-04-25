package com.dogpals.training.service;

import com.dogpals.training.domain.Training;
import com.dogpals.training.repository.TrainingRepository;
import com.dogpals.training.repository.search.TrainingSearchRepository;
import com.dogpals.training.service.dto.TrainingDTO;
import com.dogpals.training.service.mapper.TrainingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.stream.Collectors;
import java.util.LinkedList;
import java.util.List;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Training}.
 */
@Service
@Transactional
public class TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingRepository trainingRepository;

    private final TrainingMapper trainingMapper;

    private final TrainingSearchRepository trainingSearchRepository;

    public TrainingService(TrainingRepository trainingRepository, TrainingMapper trainingMapper, TrainingSearchRepository trainingSearchRepository) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
        this.trainingSearchRepository = trainingSearchRepository;
    }

    /**
     * Save a training.
     *
     * @param trainingDTO the entity to save.
     * @return the persisted entity.
     */
    public TrainingDTO save(TrainingDTO trainingDTO) {
        log.debug("Request to save Training : {}", trainingDTO);
        Training training = trainingMapper.toEntity(trainingDTO);
        training = trainingRepository.save(training);
        TrainingDTO result = trainingMapper.toDto(training);
        trainingSearchRepository.save(training);
        return result;
    }


    //findByUserId

    @Transactional(readOnly = true)
    public List<Training> findByUserId(Integer userId) {
        log.debug("Request to get all Training for user : {}", userId);
        return trainingRepository.findByUserId(userId).stream()
            //.map(trainingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get all the trainings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll(pageable)
            .map(trainingMapper::toDto);
    }


    /**
     * Get one training by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TrainingDTO> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id)
            .map(trainingMapper::toDto);
    }

    /**
     * Delete the training by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
        trainingSearchRepository.deleteById(id);
    }

    /**
     * Search for the training corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TrainingDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Trainings for query {}", query);
        return trainingSearchRepository.search(queryStringQuery(query), pageable)
            .map(trainingMapper::toDto);
    }
}
