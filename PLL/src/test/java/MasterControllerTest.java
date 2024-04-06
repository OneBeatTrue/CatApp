import Controllers.CatController;
import Controllers.MasterController;
import DTO.CatDTO;
import DTO.MasterDTO;
import Services.CatService;
import Services.MasterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.GregorianCalendar;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MasterControllerTest {
    @InjectMocks
    private MasterController masterController;
    @Mock
    private MasterService masterService;

    @Test
    void testCreate() {
        MasterDTO master = new MasterDTO(0L, "Ivan", new GregorianCalendar(1995, 10, 12).getTime());

        masterController.create(master);

        Mockito.verify(masterService, times(1)).create(master);
    }

    @Test
    void testRetrieveFriends() {
        masterController.retrieveCats(1L);

        Mockito.verify(masterService, times(1)).getCats(1L);
    }
}