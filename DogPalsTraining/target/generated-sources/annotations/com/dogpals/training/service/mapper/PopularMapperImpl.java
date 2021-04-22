package com.dogpals.training.service.mapper;

import com.dogpals.training.domain.Popular;
import com.dogpals.training.domain.Training;
import com.dogpals.training.service.dto.PopularDTO;
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
public class PopularMapperImpl implements PopularMapper {

    @Autowired
    private TrainingMapper trainingMapper;

    @Override
    public List<Popular> toEntity(List<PopularDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Popular> list = new ArrayList<Popular>( dtoList.size() );
        for ( PopularDTO popularDTO : dtoList ) {
            list.add( toEntity( popularDTO ) );
        }

        return list;
    }

    @Override
    public List<PopularDTO> toDto(List<Popular> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PopularDTO> list = new ArrayList<PopularDTO>( entityList.size() );
        for ( Popular popular : entityList ) {
            list.add( toDto( popular ) );
        }

        return list;
    }

    @Override
    public PopularDTO toDto(Popular popular) {
        if ( popular == null ) {
            return null;
        }

        PopularDTO popularDTO = new PopularDTO();

        popularDTO.setTrainingId( popularTrainingId( popular ) );
        popularDTO.setId( popular.getId() );
        popularDTO.setLikeOrDislike( popular.getLikeOrDislike() );
        popularDTO.setUserId( popular.getUserId() );

        return popularDTO;
    }

    @Override
    public Popular toEntity(PopularDTO popularDTO) {
        if ( popularDTO == null ) {
            return null;
        }

        Popular popular = new Popular();

        popular.setTraining( trainingMapper.fromId( popularDTO.getTrainingId() ) );
        popular.setId( popularDTO.getId() );
        popular.setLikeOrDislike( popularDTO.getLikeOrDislike() );
        popular.setUserId( popularDTO.getUserId() );

        return popular;
    }

    private Long popularTrainingId(Popular popular) {
        if ( popular == null ) {
            return null;
        }
        Training training = popular.getTraining();
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
