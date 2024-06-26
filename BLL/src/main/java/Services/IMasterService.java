package Services;

import DTO.CatDTO;
import DTO.MasterDTO;

import java.util.List;

/**
 * Interface representing the service layer for managing master entities.
 */
public interface IMasterService {

    /**
     * Creates a new master entity based on the provided master DTO.
     *
     * @param masterDTO The master DTO containing information about the new master.
     * @return The DTO representation of the created master entity.
     */
    MasterDTO create(MasterDTO masterDTO);

    /**
     * Retrieves the list of cats owned by a master based on the master's ID.
     *
     * @param masterId The ID of the master.
     * @return The list of DTO representations of the master's cats.
     */
    List<CatDTO> getCats(Long masterId);
}