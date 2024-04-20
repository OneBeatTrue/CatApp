package ru.onebeattrue.services;

import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.entities.Cat;
import ru.onebeattrue.entities.Master;
import ru.onebeattrue.exceptions.NotFoundException;
import ru.onebeattrue.repositories.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@ComponentScan("Repositories")
public class MasterService implements IMasterService {

    private final MasterRepository masterRepository;

    @Autowired
    public MasterService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    public MasterDTO create(MasterDTO masterDTO) {
        var master = new Master();
        master.setName(masterDTO.name());
        master.setBirthDate(masterDTO.birthDate());
        master.setCats(new ArrayList<>());
        long id = masterRepository.save(master).getId();
        return new MasterDTO(id, master.getName(), master.getBirthDate());
    }

    public List<CatDTO> getCats(Long masterId) {
        var masterOpt = masterRepository.findById(masterId);
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

    public MasterDTO getMasterById(Long masterId) {
        var masterOptional = masterRepository.findById(masterId);
        if (masterOptional.isEmpty()) {
            throw new NotFoundException("Master " + masterId + " ");
        }

        var master = masterOptional.get();
        return new MasterDTO(masterId, master.getName(), master.getBirthDate());
    }
}