package Controllers;

import DTO.CatDTO;
import Services.ICatService;

public class BefriendController extends CatServiceController {
    public BefriendController(ICatService catService) {
        super(catService);
    }

    public CatDTO execute(Long firstCatId, Long secondCatId) {
        var dto = catService.befriend(firstCatId, secondCatId);
        System.out.println(dto);
        return dto;
    }
}