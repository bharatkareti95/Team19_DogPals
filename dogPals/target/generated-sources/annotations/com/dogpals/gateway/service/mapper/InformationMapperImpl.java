package com.dogpals.gateway.service.mapper;

import com.dogpals.gateway.domain.Information;
import com.dogpals.gateway.service.dto.InformationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-22T17:31:08+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class InformationMapperImpl implements InformationMapper {

    @Override
    public Information toEntity(InformationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Information information = new Information();

        information.setId( dto.getId() );
        information.setTitle( dto.getTitle() );
        information.setContent( dto.getContent() );
        information.setDate( dto.getDate() );

        return information;
    }

    @Override
    public InformationDTO toDto(Information entity) {
        if ( entity == null ) {
            return null;
        }

        InformationDTO informationDTO = new InformationDTO();

        informationDTO.setId( entity.getId() );
        informationDTO.setTitle( entity.getTitle() );
        informationDTO.setContent( entity.getContent() );
        informationDTO.setDate( entity.getDate() );

        return informationDTO;
    }

    @Override
    public List<Information> toEntity(List<InformationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Information> list = new ArrayList<Information>( dtoList.size() );
        for ( InformationDTO informationDTO : dtoList ) {
            list.add( toEntity( informationDTO ) );
        }

        return list;
    }

    @Override
    public List<InformationDTO> toDto(List<Information> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<InformationDTO> list = new ArrayList<InformationDTO>( entityList.size() );
        for ( Information information : entityList ) {
            list.add( toDto( information ) );
        }

        return list;
    }
}
