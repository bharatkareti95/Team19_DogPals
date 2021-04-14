package com.dogpals.training.service.mapper;


import com.dogpals.training.domain.*;
import com.dogpals.training.service.dto.PopularDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Popular} and its DTO {@link PopularDTO}.
 */
@Mapper(componentModel = "spring", uses = {TrainingMapper.class})
public interface PopularMapper extends EntityMapper<PopularDTO, Popular> {

    @Mapping(source = "training.id", target = "trainingId")
    PopularDTO toDto(Popular popular);

    @Mapping(source = "trainingId", target = "training")
    Popular toEntity(PopularDTO popularDTO);

    default Popular fromId(Long id) {
        if (id == null) {
            return null;
        }
        Popular popular = new Popular();
        popular.setId(id);
        return popular;
    }
}
