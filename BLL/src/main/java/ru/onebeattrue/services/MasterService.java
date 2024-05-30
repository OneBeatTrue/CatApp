package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@ComponentScan("Repositories")
public class MasterService {

    private final MasterRepository masterRepository;

    public MasterDTO create(MasterDTO masterDTO) {
        var master = Master
                .builder()
                .name(masterDTO.name())
                .birthDate(masterDTO.birthDate())
                .cats(new ArrayList<>())
                .build();
        return new MasterDTO(masterRepository.save(master));
    }

    public List<CatDTO> getCats(Long masterId) {
        var master = masterRepository.findById(masterId).orElseThrow(() -> new NotFoundException("Master " + masterId + " "));
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : master.getCats()) {
            cats.add(new CatDTO(cat));
        }
        return cats;
    }

    public MasterDTO getMasterById(Long masterId) {
        return new MasterDTO(masterRepository.findById(masterId).orElseThrow(() -> new NotFoundException("Master " + masterId + " ")));
    }

    public List<MasterDTO> getAll() {
        List<MasterDTO> masters = new ArrayList<>();
        for (Master master : masterRepository.findAll()) {
            masters.add(new MasterDTO(master));
        }

        return masters;
    }
}