package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.CatListDTO;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.dto.MasterListDTO;
import ru.onebeattrue.entities.master.Master;
import ru.onebeattrue.exceptions.NotFoundException;
import ru.onebeattrue.repositories.MasterRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@ComponentScan("Repositories")
@EnableRabbit
public class MasterService {
    private final RabbitTemplate rabbitTemplate;
    private final MasterRepository masterRepository;

    @RabbitListener(queues = "createMasterQueue")
    public MasterDTO create(MasterDTO masterDTO) {
        var master = Master
                .builder()
                .name(masterDTO.name())
                .birthDate(masterDTO.birthDate())
                .build();
        return new MasterDTO(masterRepository.save(master));
    }

    @RabbitListener(queues = "getCatsMasterQueue")
    public List<CatDTO> getCats(Long masterId) {
        return (List<CatDTO>) rabbitTemplate.convertSendAndReceive("getCatsByMasterCatQueue", masterId);
    }

    @RabbitListener(queues = "getMasterByIdMasterQueue")
    public MasterDTO getMasterById(Long masterId) {
        return new MasterDTO(masterRepository.findById(masterId).orElseThrow(() -> new NotFoundException("Master " + masterId + " ")));
    }

    @RabbitListener(queues = "getAllMasterQueue")
    public List<MasterDTO> getAll() {
        List<MasterDTO> masters = new ArrayList<>();
        for (Master master : masterRepository.findAll()) {
            masters.add(new MasterDTO(master));
        }

        return masters;
    }
}