package com.dogpals.training.service;

import com.dogpals.training.domain.Booking;
import com.dogpals.training.repository.BookingRepository;
import com.dogpals.training.repository.search.BookingSearchRepository;
import com.dogpals.training.service.dto.BookingDTO;
import com.dogpals.training.service.mapper.BookingMapper;
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
 * Service Implementation for managing {@link Booking}.
 */
@Service
@Transactional
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    private final BookingSearchRepository bookingSearchRepository;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper, BookingSearchRepository bookingSearchRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.bookingSearchRepository = bookingSearchRepository;
    }

    /**
     * Save a booking.
     *
     * @param bookingDTO the entity to save.
     * @return the persisted entity.
     */
    public BookingDTO save(BookingDTO bookingDTO) {
        log.debug("Request to save Booking : {}", bookingDTO);
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking = bookingRepository.save(booking);
        BookingDTO result = bookingMapper.toDto(booking);
        bookingSearchRepository.save(booking);
        return result;
    }

    /**
     * Get all the bookings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> findAll() {
        log.debug("Request to get all Bookings");
        return bookingRepository.findAll().stream()
            .map(bookingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one booking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookingDTO> findOne(Long id) {
        log.debug("Request to get Booking : {}", id);
        return bookingRepository.findById(id)
            .map(bookingMapper::toDto);
    }

    /**
     * Delete the booking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Booking : {}", id);
        bookingRepository.deleteById(id);
        bookingSearchRepository.deleteById(id);
    }

    /**
     * Search for the booking corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> search(String query) {
        log.debug("Request to search Bookings for query {}", query);
        return StreamSupport
            .stream(bookingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(bookingMapper::toDto)
        .collect(Collectors.toList());
    }
}
