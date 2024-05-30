package ru.onebeattrue.controllers;

import lombok.RequiredArgsConstructor;
import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.MasterDTO;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import ru.onebeattrue.services.MasterService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;

/**
 * Base controller class for master-related operations.
 */
@RestController
@RequestMapping("/master")
@ComponentScan("Services")
@RequiredArgsConstructor
public class MasterController {
    private final MasterService masterService;

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

    @GetMapping("/retrieve/all")
    public List<MasterDTO> retrieveAllMasters() {
        return masterService.getAll();
    }
}
