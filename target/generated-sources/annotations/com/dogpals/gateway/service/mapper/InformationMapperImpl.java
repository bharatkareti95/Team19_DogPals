package com.dogpals.gateway.service.mapper;

import com.dogpals.gateway.domain.Information;
import com.dogpals.gateway.service.dto.InformationDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-22T22:44:05+0800",
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
        information.setCatagory( dto.getCatagory() );
        information.setDateposted( dto.getDateposted() );

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
        informationDTO.setCatagory( entity.getCatagory() );
        informationDTO.setDateposted( entity.getDateposted() );

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
