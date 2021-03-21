package com.dogpals.gateway.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dogpals.gateway.domain.Information;
import com.dogpals.gateway.domain.*; // for static metamodels
import com.dogpals.gateway.repository.InformationRepository;
import com.dogpals.gateway.repository.search.InformationSearchRepository;
import com.dogpals.gateway.service.dto.InformationCriteria;
import com.dogpals.gateway.service.dto.InformationDTO;
import com.dogpals.gateway.service.mapper.InformationMapper;

/**
 * Service for executing complex queries for {@link Information} entities in the database.
 * The main input is a {@link InformationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InformationDTO} or a {@link Page} of {@link InformationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InformationQueryService extends QueryService<Information> {

    private final Logger log = LoggerFactory.getLogger(InformationQueryService.class);

    private final InformationRepository informationRepository;

    private final InformationMapper informationMapper;

    private final InformationSearchRepository informationSearchRepository;

    public InformationQueryService(InformationRepository informationRepository, InformationMapper informationMapper, InformationSearchRepository informationSearchRepository) {
        this.informationRepository = informationRepository;
        this.informationMapper = informationMapper;
        this.informationSearchRepository = informationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link InformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InformationDTO> findByCriteria(InformationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Information> specification = createSpecification(criteria);
        return informationMapper.toDto(informationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InformationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InformationDTO> findByCriteria(InformationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Information> specification = createSpecification(criteria);
        return informationRepository.findAll(specification, page)
            .map(informationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InformationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Information> specification = createSpecification(criteria);
        return informationRepository.count(specification);
    }

    /**
     * Function to convert {@link InformationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Information> createSpecification(InformationCriteria criteria) {
        Specification<Information> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Information_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Information_.title));
            }
            if (criteria.getCatagory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCatagory(), Information_.catagory));
            }
            if (criteria.getDateposted() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateposted(), Information_.dateposted));
            }
        }
        return specification;
    }
}
