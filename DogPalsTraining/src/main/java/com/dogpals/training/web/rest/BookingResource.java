package com.dogpals.training.web.rest;

import com.dogpals.training.service.BookingService;
import com.dogpals.training.web.rest.errors.BadRequestAlertException;
import com.dogpals.training.service.dto.BookingDTO;
import com.dogpals.training.security.SecurityUtils;
import com.dogpals.training.domain.Booking;

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

/**
 * REST controller for managing {@link com.dogpals.training.domain.Booking}.
 */
@RestController
@RequestMapping("/api")
public class BookingResource {

    private final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private static final String ENTITY_NAME = "dogPalsTrainingBooking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookingService bookingService;
    public BookingResource(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    /**
     * {@code POST  /bookings} : Create a new booking.
     *
     * @param bookingDTO the bookingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookingDTO, or with status {@code 400 (Bad Request)} if the booking has already an ID.
     * @throws URISyntaxException
     * @throws Exception
     */
    @PostMapping("/bookings")
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) throws URISyntaxException{
        log.debug("REST request to save Booking : {}", bookingDTO);
        if (bookingDTO.getId() != null) {
            throw new BadRequestAlertException("A new booking cannot already have an ID", ENTITY_NAME, "idexists");
        }


        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        log.debug("REST create set Bookings to userId : {}", userId.get());
        bookingDTO.setUserId(userId.get().intValue());
        if ( bookingService.checkExists("userId", userId.get().intValue(), bookingDTO )){
            throw new BadRequestAlertException("A new booking cannot already have an USER ID", ENTITY_NAME, "idexists");
        }
        if ( bookingDTO.getUserId() == 60 ){
            throw new BadRequestAlertException("There is an issue while processing booking", ENTITY_NAME, "problemowner");
        }
        // if ( bookingService.checkCapacity(bookingDTO)){
        //     throw new BadRequestAlertException("The Training Capacity is full" , ENTITY_NAME , "fullcapacity");
        // }
        BookingDTO result = bookingService.save(bookingDTO);

        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bookings} : Updates an existing booking.
     *
     * @param bookingDTO the bookingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingDTO,
     * or with status {@code 400 (Bad Request)} if the bookingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bookings")
    public ResponseEntity<BookingDTO> updateBooking(@Valid @RequestBody BookingDTO bookingDTO) throws URISyntaxException {
        log.debug("REST request to update Booking : {}", bookingDTO);
        if (bookingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        log.debug("REST update set Bookings to userId : {}", userId.get());
        bookingDTO.setUserId(userId.get().intValue());
        BookingDTO result = bookingService.save(bookingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bookings} : get all the bookings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookings in body.
     */
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        log.debug("REST request to get all Bookings");
        Optional<Long> userId = SecurityUtils.getUserId();
        if (userId.isPresent()) {
            log.info("User Id--->{}", userId.get());
        }else {
            log.info("No userId present.");
        }
        return bookingService.findAll(userId.get().intValue());
        // return ResponseUtil.wrapOrNotFound(booking);
    }

    /**
     * {@code GET  /bookings/:id} : get the "id" booking.
     *
     * @param id the id of the bookingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) {
        log.debug("REST request to get Booking : {}", id);
        Optional<BookingDTO> bookingDTO = bookingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookingDTO);
    }

    //To get all the booking under TrainingId
    @GetMapping("/bookings/training")
    public List<BookingDTO> findAllbyTrainingId(Long trainingId){
        log.debug("REST request to get Booking : {}", trainingId);
        return bookingService.findAllbyTrainingId(trainingId);
    }

    /**
     * {@code DELETE  /bookings/:id} : delete the "id" booking.
     *
     * @param id the id of the bookingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        log.debug("REST request to delete Booking : {}", id);
        bookingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/bookings?query=:query} : search for the booking corresponding
     * to the query.
     *
     * @param query the query of the booking search.
     * @return the result of the search.
     */
    @GetMapping("/_search/bookings")
    public List<BookingDTO> searchBookings(@RequestParam String query) {
        log.debug("REST request to search Bookings for query {}", query);
        return bookingService.search(query);
    }
}
