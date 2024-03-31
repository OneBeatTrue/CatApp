package Services;

import DTO.CatDTO;

import java.util.List;

public interface ICatService {
    CatDTO create(CatDTO catDTO);
    CatDTO befriend(Long firstCatId, Long secondCatId);
    CatDTO quarrel(Long firstCatId, Long secondCatId);
    List<CatDTO> getFriends(Long catId);
}
