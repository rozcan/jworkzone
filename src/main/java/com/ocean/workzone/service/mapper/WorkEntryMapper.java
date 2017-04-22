package com.ocean.workzone.service.mapper;

import com.ocean.workzone.domain.*;
import com.ocean.workzone.service.dto.WorkEntryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkEntry and its DTO WorkEntryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, RegionMapper.class, })
public interface WorkEntryMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "region.id", target = "regionId")
    WorkEntryDTO workEntryToWorkEntryDTO(WorkEntry workEntry);

    List<WorkEntryDTO> workEntriesToWorkEntryDTOs(List<WorkEntry> workEntries);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "regionId", target = "region")
    WorkEntry workEntryDTOToWorkEntry(WorkEntryDTO workEntryDTO);

    List<WorkEntry> workEntryDTOsToWorkEntries(List<WorkEntryDTO> workEntryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default WorkEntry workEntryFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkEntry workEntry = new WorkEntry();
        workEntry.setId(id);
        return workEntry;
    }
    

}
