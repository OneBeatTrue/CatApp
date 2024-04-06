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

        var masterController = new MasterController(masterService);
        var catController = new CatController(catService);

//        masterController.create(new MasterDTO(0L, "Ivan", new GregorianCalendar(1995, 10, 12).getTime()));
//        masterController.create(new MasterDTO(0L, "Oleg", new GregorianCalendar(2005, 1, 15).getTime()));
//        catController.create(new CatDTO(0L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L));
//        catController.create(new CatDTO(0L, "Mittens", new GregorianCalendar(2023, 1, 16).getTime(), "Persian", "WHITE", 2L));
//        catController.create(new CatDTO(0L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L));
//        catController.befriend(1L, 2L);
//        catController.befriend(2L, 3L);
//        catController.quarrel(1L, 2L);
//        catController.befriend(2L, 1L);
//        masterController.retrieveCats(1L);
//        catController.retrieveFriends(2L);
        masterController.retrieveCats(1L);
    }
}