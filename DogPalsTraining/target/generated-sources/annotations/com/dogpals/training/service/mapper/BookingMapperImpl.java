package com.dogpals.training.service.mapper;

import com.dogpals.training.domain.Booking;
import com.dogpals.training.domain.Training;
import com.dogpals.training.service.dto.BookingDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T17:33:16+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Autowired
    private TrainingMapper trainingMapper;

    @Override
    public List<Booking> toEntity(List<BookingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Booking> list = new ArrayList<Booking>( dtoList.size() );
        for ( BookingDTO bookingDTO : dtoList ) {
            list.add( toEntity( bookingDTO ) );
        }

        return list;
    }

    @Override
    public List<BookingDTO> toDto(List<Booking> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BookingDTO> list = new ArrayList<BookingDTO>( entityList.size() );
        for ( Booking booking : entityList ) {
            list.add( toDto( booking ) );
        }

        return list;
    }

    @Override
    public BookingDTO toDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setTrainingId( bookingTrainingId( booking ) );
        bookingDTO.setId( booking.getId() );
        bookingDTO.setStatus( booking.getStatus() );
        bookingDTO.setUserId( booking.getUserId() );

        return bookingDTO;
    }

    @Override
    public Booking toEntity(BookingDTO bookingDTO) {
        if ( bookingDTO == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setTraining( trainingMapper.fromId( bookingDTO.getTrainingId() ) );
        booking.setId( bookingDTO.getId() );
        booking.setStatus( bookingDTO.getStatus() );
        booking.setUserId( bookingDTO.getUserId() );

        return booking;
    }

    private Long bookingTrainingId(Booking booking) {
        if ( booking == null ) {
            return null;
        }
        Training training = booking.getTraining();
        if ( training == null ) {
            return null;
        }
        Long id = training.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
