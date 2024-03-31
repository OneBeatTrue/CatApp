package Controllers;

import Services.IMasterService;

public class MasterServiceController {
    protected IMasterService masterService;
    protected MasterServiceController(IMasterService masterService) {
        if (masterService == null) {
            throw new NullPointerException("Null master service");
        }

        this.masterService = masterService;
    }
}
