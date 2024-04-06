package Controllers;

import DTO.CatDTO;
import DTO.MasterDTO;
import Services.IMasterService;

import java.util.List;

/**
 * Base controller class for master-related operations.
 */
public class MasterController {
    private final IMasterService masterService;
    public MasterController(IMasterService masterService) {
        if (masterService == null) {
            throw new NullPointerException("Null master service");
        }

        this.masterService = masterService;
    }

    public MasterDTO create(MasterDTO master) {
        var dto = masterService.create(master);
        System.out.println(dto);
        return dto;
    }

    public List<CatDTO> retrieveCats(Long masterId) {
        List<CatDTO> listDto = masterService.getCats(masterId);
        for (var dto : listDto) {
            System.out.println(dto);
        }
        return listDto;
    }
}
