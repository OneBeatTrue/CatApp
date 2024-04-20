import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.entities.Cat;
import ru.onebeattrue.entities.Master;
import ru.onebeattrue.exceptions.FriendshipException;
import ru.onebeattrue.exceptions.QuarrelException;
import ru.onebeattrue.models.Color;
import ru.onebeattrue.repositories.CatRepository;
import ru.onebeattrue.repositories.MasterRepository;
import ru.onebeattrue.services.CatService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {

    @InjectMocks
    private CatService catService;
    @Mock
    private CatRepository catRepository;

    @BeforeEach
    void setUp() {
        Master master = createMaster();
        Cat firstCat = createCat(master);
        Cat secondCat = createAnotherCat(master);
        Mockito.when(catRepository.findById(1L)).thenReturn(Optional.of(firstCat));
        Mockito.when(catRepository.findById(2L)).thenReturn(Optional.of(secondCat));
    }

    @Test
    void testBefriendCats() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        CatDTO befriendedCat = catService.befriend(1L, 2L);

        Assertions.assertEquals(firstCat, befriendedCat);
        Mockito.verify(catRepository, times(1)).findById(1L);
        Mockito.verify(catRepository, times(1)).findById(2L);
    }

    @Test
    void testWrongBefriendCats() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        catService.befriend(1L, 2L);

        Assertions.assertThrowsExactly(FriendshipException.class, () -> catService.befriend(2L, 1L));
    }

    @Test
    void testQuarrelCats() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        catService.befriend(1L, 2L);
        CatDTO quarrelledCat = catService.quarrel(2L, 1L);

        Assertions.assertEquals(secondCat, quarrelledCat);
    }

    @Test
    void testWrongQuarrelCats() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        Assertions.assertThrowsExactly(QuarrelException.class, () -> catService.quarrel(2L, 1L));
    }

    @Test
    void testGetFriends() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        catService.befriend(1L, 2L);

        Assertions.assertIterableEquals(List.of(firstCat), catService.getFriends(2L));

        catService.quarrel(2L, 1L);

        Assertions.assertIterableEquals(List.of(), catService.getFriends(2L));
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