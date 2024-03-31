package Services;

import DTO.CatDTO;
import DTO.MasterDTO;

import java.util.List;

public interface IMasterService {
    MasterDTO create(MasterDTO masterDTO);
    List<CatDTO> getCats(Long masterId);
}
