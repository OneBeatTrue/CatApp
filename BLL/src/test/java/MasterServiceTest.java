import Context.IDataContext;
import DAO.DAO;
import DTO.CatDTO;
import Entities.Cat;
import Entities.Master;
import Models.Color;
import Services.CatService;
import Services.ICatService;
import Services.IMasterService;
import Services.MasterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class MasterServiceTest {
    private IMasterService masterService;
    @Mock
    private DAO<Master> masterDAO;
    @Mock
    private IDataContext context;

    @BeforeEach
    void setUp() {
        Master master = createMaster();
        Cat firstCat = createCat(master);
        Cat secondCat = createAnotherCat(master);

        Mockito.doNothing().when(masterDAO).openCurrentSessionWithTransaction();
        Mockito.doNothing().when(masterDAO).closeCurrentSessionWithTransaction();
        Mockito.when(masterDAO.get(1L)).thenReturn(Optional.of(master));
        Mockito.when(context.getMasterDAO()).thenReturn(masterDAO);

        masterService = new MasterService(context);
    }

    @Test
    void testGetCats() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        Assertions.assertIterableEquals(List.of(firstCat, secondCat), masterService.getCats(1L));
    }

    private Master createMaster() {
        Master master = new Master();
        master.setId(1L);
        master.setName("Ivan");
        master.setBirthDate(new GregorianCalendar(1995, 10, 12).getTime());
        master.setCats(new ArrayList<>());
        return master;
    }

    private Cat createCat(Master master) {
        Cat cat = new Cat();
        cat.setId(1L);
        cat.setName("Whiskers");
        cat.setBirthDate(new GregorianCalendar(2023, 1, 15).getTime());
        cat.setBreed("Maine Coon");
        cat.setColor(Color.valueOf("GINGER"));
        cat.setMaster(master);
        cat.getMaster().getCats().add(cat);
        cat.setFriends(new ArrayList<>());
        return cat;
    }

    private Cat createAnotherCat(Master master) {
        Cat cat = new Cat();
        cat.setId(2L);
        cat.setName("Felix");
        cat.setBirthDate(new GregorianCalendar(2023, 1, 17).getTime());
        cat.setBreed("Siamese");
        cat.setColor(Color.valueOf("BLACK"));
        cat.setMaster(master);
        cat.getMaster().getCats().add(cat);
        cat.setFriends(new ArrayList<>());
        return cat;
    }
}
