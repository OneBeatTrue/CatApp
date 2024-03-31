package Controllers;

import DTO.CatDTO;
import Services.ICatService;
import Services.IMasterService;

import java.util.List;

public class RetrieveCatsController extends MasterServiceController {
    public RetrieveCatsController(IMasterService masterService) {
        super(masterService);
    }

    public List<CatDTO> execute(Long masterId) {
        List<CatDTO> listDto = masterService.getCats(masterId);
        for (var dto : listDto) {
            System.out.println(dto);
        }
        return listDto;
    }
}