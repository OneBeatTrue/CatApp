package Controllers;

import DTO.CatDTO;
import Services.ICatService;

import java.util.List;

public class RetrieveFriendsController extends CatServiceController {
    public RetrieveFriendsController(ICatService catService) {
        super(catService);
    }

    public List<CatDTO> execute(Long catId) {
        List<CatDTO> listDto = catService.getFriends(catId);
        for (var dto : listDto) {
            System.out.println(dto);
        }
        return listDto;
    }
}