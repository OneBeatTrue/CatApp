package Controllers;

import DTO.CatDTO;
import Services.ICatService;

/**
 * Controller class for handling the quarreling operation between cats.
 */
public class QuarrelController extends CatServiceController {
    public QuarrelController(ICatService catService) {
        super(catService);
    }

    public CatDTO execute(Long firstCatId, Long secondCatId) {
        var dto = catService.quarrel(firstCatId, secondCatId);
        System.out.println(dto);
        return dto;
    }
}