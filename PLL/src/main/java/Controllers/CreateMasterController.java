package Controllers;

import DTO.MasterDTO;
import Services.IMasterService;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Controller class for creating a new master.
 */
public class CreateMasterController extends MasterServiceController {
    public CreateMasterController(IMasterService masterService) {
        super(masterService);
    }

    public MasterDTO execute(MasterDTO master) {
        var dto = masterService.create(master);
        System.out.println(dto);
        return dto;
    }
}
