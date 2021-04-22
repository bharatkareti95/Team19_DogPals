package com.dogpals.training.service.mapper;

import com.dogpals.training.domain.Training;
import com.dogpals.training.service.dto.TrainingDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T17:33:17+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class TrainingMapperImpl implements TrainingMapper {

    @Override
    public TrainingDTO toDto(Training entity) {
        if ( entity == null ) {
            return null;
        }

        TrainingDTO trainingDTO = new TrainingDTO();

        trainingDTO.setId( entity.getId() );
        trainingDTO.setTitle( entity.getTitle() );
        trainingDTO.setDate( entity.getDate() );
        trainingDTO.setDetails( entity.getDetails() );
        trainingDTO.setLocation( entity.getLocation() );
        trainingDTO.setPrice( entity.getPrice() );
        trainingDTO.setAgency( entity.getAgency() );
        trainingDTO.setStartTime( entity.getStartTime() );
        trainingDTO.setEndTime( entity.getEndTime() );
        trainingDTO.setCapacity( entity.getCapacity() );
        trainingDTO.setPopularity( entity.getPopularity() );

        return trainingDTO;
    }

    @Override
    public List<Training> toEntity(List<TrainingDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Training> list = new ArrayList<Training>( dtoList.size() );
        for ( TrainingDTO trainingDTO : dtoList ) {
            list.add( toEntity( trainingDTO ) );
        }

        return list;
    }

    @Override
    public List<TrainingDTO> toDto(List<Training> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TrainingDTO> list = new ArrayList<TrainingDTO>( entityList.size() );
        for ( Training training : entityList ) {
            list.add( toDto( training ) );
        }

        return list;
    }

    @Override
    public Training toEntity(TrainingDTO trainingDTO) {
        if ( trainingDTO == null ) {
            return null;
        }

        Training training = new Training();

        training.setId( trainingDTO.getId() );
        training.setTitle( trainingDTO.getTitle() );
        training.setDate( trainingDTO.getDate() );
        training.setDetails( trainingDTO.getDetails() );
        training.setLocation( trainingDTO.getLocation() );
        training.setPrice( trainingDTO.getPrice() );
        training.setAgency( trainingDTO.getAgency() );
        training.setStartTime( trainingDTO.getStartTime() );
        training.setEndTime( trainingDTO.getEndTime() );
        training.setCapacity( trainingDTO.getCapacity() );
        training.setPopularity( trainingDTO.getPopularity() );

        return training;
    }
}
