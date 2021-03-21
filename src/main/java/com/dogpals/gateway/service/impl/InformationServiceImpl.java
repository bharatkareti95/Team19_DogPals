package com.dogpals.gateway.service.impl;

import com.dogpals.gateway.service.InformationService;
import com.dogpals.gateway.domain.Information;
import com.dogpals.gateway.repository.InformationRepository;
import com.dogpals.gateway.repository.search.InformationSearchRepository;
import com.dogpals.gateway.service.dto.InformationDTO;
import com.dogpals.gateway.service.mapper.InformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Information}.
 */
@Service
@Transactional
public class InformationServiceImpl implements InformationService {

    private final Logger log = LoggerFactory.getLogger(InformationServiceImpl.class);

    private final InformationRepository informationRepository;

    private final InformationMapper informationMapper;

    private final InformationSearchRepository informationSearchRepository;

    public InformationServiceImpl(InformationRepository informationRepository, InformationMapper informationMapper, InformationSearchRepository informationSearchRepository) {
        this.informationRepository = informationRepository;
        this.informationMapper = informationMapper;
        this.informationSearchRepository = informationSearchRepository;
    }

    @Override
    public InformationDTO save(InformationDTO informationDTO) {
        log.debug("Request to save Information : {}", informationDTO);
        Information information = informationMapper.toEntity(informationDTO);
        information = informationRepository.save(information);
        InformationDTO result = informationMapper.toDto(information);
        informationSearchRepository.save(information);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Information");
        return informationRepository.findAll(pageable)
            .map(informationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InformationDTO> findOne(Long id) {
        log.debug("Request to get Information : {}", id);
        return informationRepository.findById(id)
            .map(informationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Information : {}", id);
        informationRepository.deleteById(id);
        informationSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InformationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Information for query {}", query);
        return informationSearchRepository.search(queryStringQuery(query), pageable)
            .map(informationMapper::toDto);
    }
}
