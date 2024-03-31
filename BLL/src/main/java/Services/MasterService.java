package Services;

import Context.IDataContext;
import DTO.CatDTO;
import DTO.MasterDTO;
import Entities.Cat;
import Entities.Master;
import Exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MasterService extends AbstractService implements IMasterService {

    public MasterService(IDataContext context) {
        super(context);
    }

    public MasterDTO create(MasterDTO masterDTO) {
        var master = new Master();
        master.setName(masterDTO.name());
        master.setBirthDate(masterDTO.birthDate());
        master.setCats(new ArrayList<>());
        long id = context.getMasterDAO().save(master);
        return new MasterDTO(id, master.getName(), master.getBirthDate());
    }

    public List<CatDTO> getCats(Long masterId) {
        var masterOpt = context.getMasterDAO().get(masterId);
        if (masterOpt.isEmpty()) {
            throw new NotFoundException("Master " + masterId + " ");
        }

        var master = masterOpt.get();
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : master.getCats()) {
            cats.add(new CatDTO(cat.getId(), cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor().toString(), cat.getMaster().getId()));
        }
        return cats;
    }
}