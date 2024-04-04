package Controllers;

import DTO.CatDTO;
import DTO.MasterDTO;
import Services.ICatService;
import Services.IMasterService;

/**
 * Controller class for creating a new cat.
 */
public class CreateCatController extends CatServiceController {
    public CreateCatController(ICatService catService) {
        super(catService);
    }

    public CatDTO execute(CatDTO cat) {
        var dto = catService.create(cat);
        System.out.println(dto);
        return dto;
    }
}
