package com.dogpals.training.service.mapper;


import com.dogpals.training.domain.*;
import com.dogpals.training.service.dto.BookingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrainingMapper.class})
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {

    @Mapping(source = "training.id", target = "trainingId")
    BookingDTO toDto(Booking booking);

    @Mapping(source = "trainingId", target = "training")
    Booking toEntity(BookingDTO bookingDTO);

    default Booking fromId(Long id) {
        if (id == null) {
            return null;
        }
        Booking booking = new Booking();
        booking.setId(id);
        return booking;
    }
}
