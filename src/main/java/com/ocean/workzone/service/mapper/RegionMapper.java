package com.ocean.workzone.service.mapper;

import com.ocean.workzone.domain.*;
import com.ocean.workzone.service.dto.RegionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Region and its DTO RegionDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, })
public interface RegionMapper {

    @Mapping(source = "location.id", target = "locationId")
    RegionDTO regionToRegionDTO(Region region);

    List<RegionDTO> regionsToRegionDTOs(List<Region> regions);

    @Mapping(source = "locationId", target = "location")
    Region regionDTOToRegion(RegionDTO regionDTO);

    List<Region> regionDTOsToRegions(List<RegionDTO> regionDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Region regionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Region region = new Region();
        region.setId(id);
        return region;
    }
    

}
