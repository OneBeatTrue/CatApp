package Controllers;

import Services.IMasterService;

/**
 * Base controller class for master-related operations.
 */
public class MasterServiceController {
    protected IMasterService masterService;
    protected MasterServiceController(IMasterService masterService) {
        if (masterService == null) {
            throw new NullPointerException("Null master service");
        }

        this.masterService = masterService;
    }
}
