package ru.onebeattrue.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.CatListDTO;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.dto.MasterListDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.List;

/**
 * Base controller class for master-related operations.
 */
@RestController
@RequestMapping("/master")
@ComponentScan("Services")
@RequiredArgsConstructor
public class MasterController {
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/create")
    public MasterDTO create(@Valid @RequestBody MasterDTO master) {
        return (MasterDTO) rabbitTemplate.convertSendAndReceive("createMasterQueue", master);
    }

    @GetMapping("/cats/{id}")
    public CatListDTO retrieveCats(@PathVariable("id") @Min(1) Long masterId) {
        var catListDTO = (CatListDTO) rabbitTemplate.convertSendAndReceive("getCatsMasterQueue", masterId);
        return catListDTO;
    }

    @GetMapping("/retrieve/{id}")
    public MasterDTO retrieve(@PathVariable("id") @Min(1) Long masterId) {
        return (MasterDTO) rabbitTemplate.convertSendAndReceive("getMasterByIdMasterQueue", masterId);

    }

    @GetMapping("/retrieve/all")
    public MasterListDTO retrieveAllMasters() {
        var masterListDTO = (MasterListDTO) rabbitTemplate.convertSendAndReceive("getAllMasterQueue");
        return masterListDTO;
    }
}
