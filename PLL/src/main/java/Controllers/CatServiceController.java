package Controllers;

import Services.ICatService;
import Services.IMasterService;

public class CatServiceController {
    protected ICatService catService;
    protected CatServiceController(ICatService catService) {
        if (catService == null) {
            throw new NullPointerException("Null cat service");
        }

        this.catService = catService;
    }
}