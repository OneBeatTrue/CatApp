import Controllers.CatController;
import DTO.CatDTO;
import Services.CatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.GregorianCalendar;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CatControllerTest {
    @InjectMocks
    private CatController catController;
    @Mock
    private CatService catService;

    @Test
    void testCreate() {
        CatDTO firstCat = new CatDTO(1L, "Whiskers", new GregorianCalendar(2023, 1, 15).getTime(), "Maine Coon", "GINGER", 1L);
        CatDTO secondCat = new CatDTO(2L, "Felix", new GregorianCalendar(2023, 1, 17).getTime(), "Siamese", "BLACK", 1L);

        catController.create(firstCat);

        Mockito.verify(catService, times(1)).create(firstCat);
    }

    @Test
    void testBefriend() {
        catController.befriend(1L, 2L);

        Mockito.verify(catService, times(1)).befriend(1L, 2L);
    }

    @Test
    void testQuarrel() {
        catController.quarrel(1L, 2L);

        Mockito.verify(catService, times(1)).quarrel(1L, 2L);
    }

    @Test
    void testRetrieveFriends() {
        catController.retrieveFriends(1L);

        Mockito.verify(catService, times(1)).getFriends(1L);
    }
}
