package com.dogpals.training.service.mapper;


import com.dogpals.training.domain.*;
import com.dogpals.training.service.dto.TrainingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Training} and its DTO {@link TrainingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrainingMapper extends EntityMapper<TrainingDTO, Training> {


    @Mapping(target = "populars", ignore = true)
    @Mapping(target = "removePopular", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "removeBooking", ignore = true)
    Training toEntity(TrainingDTO trainingDTO);

    default Training fromId(Long id) {
        if (id == null) {
            return null;
        }
        Training training = new Training();
        training.setId(id);
        return training;
    }
}
