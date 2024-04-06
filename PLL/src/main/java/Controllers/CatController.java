package Controllers;

import DTO.CatDTO;
import Services.ICatService;

import java.util.List;

/**
 * Base controller class for cat-related operations.
 */
public class CatController {
    private final ICatService catService;
    public CatController(ICatService catService) {
        if (catService == null) {
            throw new NullPointerException("Null cat service");
        }

        this.catService = catService;
    }

    public CatDTO create(CatDTO cat) {
        var dto = catService.create(cat);
        return dto;
    }

    public CatDTO befriend(Long firstCatId, Long secondCatId) {
        var dto = catService.befriend(firstCatId, secondCatId);
        return dto;
    }

    public CatDTO quarrel(Long firstCatId, Long secondCatId) {
        var dto = catService.quarrel(firstCatId, secondCatId);
        return dto;
    }

    public List<CatDTO> retrieveFriends(Long catId) {
        List<CatDTO> listDto = catService.getFriends(catId);
        return listDto;
    }
}
