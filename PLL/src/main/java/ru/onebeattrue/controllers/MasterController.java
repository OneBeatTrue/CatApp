package ru.onebeattrue.controllers;

import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.services.IMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;

/**
 * Base controller class for master-related operations.
 */
@RestController
@RequestMapping("/master")
@ComponentScan("Services")
public class MasterController {
    private final IMasterService masterService;

    @Autowired
    public MasterController(IMasterService masterService) {
        this.masterService = masterService;
    }

    @PostMapping("/create")
    public MasterDTO create(@Valid @RequestBody MasterDTO master) {
        return masterService.create(master);
    }

    @GetMapping("/cats/{id}")
    public List<CatDTO> retrieveCats(@PathVariable("id") @Min(1) Long masterId) {
        return masterService.getCats(masterId);
    }

    @GetMapping("/retrieve/{id}")
    public MasterDTO retrieve(@PathVariable("id") @Min(1) Long masterId) {
        return masterService.getMasterById(masterId);
    }
}
