package org.example;

import Context.DataContext;
import Context.IDataContext;
import Controllers.*;
import DTO.CatDTO;
import DTO.MasterDTO;
import Services.CatService;
import Services.ICatService;
import Services.IMasterService;
import Services.MasterService;

import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        IDataContext context = new DataContext();

        ICatService catService = new CatService(context);
        IMasterService masterService = new MasterService(context);

        var createMasterController = new CreateMasterController(masterService);
        var retrieveCatsController = new RetrieveCatsController(masterService);
        var createCatController = new CreateCatController(catService);
        var befriendController = new BefriendController(catService);
        var quarrelController = new QuarrelController(catService);
        var retrieveFriendsController = new RetrieveFriendsController(catService);

//        createMasterController.execute(new MasterDTO(0L, "Ivan", new GregorianCalendar(1995, 10, 12).getTime()));
//        createMasterController.execute(new MasterDTO(0L, "Oleg", new GregorianCalendar(2005, 1, 15).getTime()));
//        createCatController.execute(new CatDTO(0L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L));
//        createCatController.execute(new CatDTO(0L, "Mittens", new GregorianCalendar(2023, 1, 16).getTime(), "Persian", "WHITE", 2L));
//        createCatController.execute(new CatDTO(0L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L));
//        befriendController.execute(1L, 2L);
//        befriendController.execute(2L, 3L);
//        quarrelController.execute(1L, 2L);
//        befriendController.execute(2L, 1L);
//        retrieveCatsController.execute(1L);
//        retrieveFriendsController.execute(2L);
        retrieveCatsController.execute(1L);
    }
}