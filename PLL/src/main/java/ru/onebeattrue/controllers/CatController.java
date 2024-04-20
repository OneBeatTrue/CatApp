package ru.onebeattrue.controllers;

import org.springframework.validation.annotation.Validated;
import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.services.ICatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Base controller class for cat-related operations.
 */
@RestController
@RequestMapping("/cat")
@ComponentScan("Services")
@Validated
public class CatController {

    private final ICatService catService;
    @Autowired
    public CatController(ICatService catService) {
        this.catService = catService;
    }

    @PostMapping("/create")
    public CatDTO create(@Valid @RequestBody CatDTO cat) {
        return catService.create(cat);
    }

    @PutMapping("/befriend")
    public CatDTO befriend(@RequestParam @Min(1) Long firstCatId, @RequestParam @Min(1) Long secondCatId) {
        return catService.befriend(firstCatId, secondCatId);
    }

    @PutMapping("/quarrel")
    public CatDTO quarrel(@RequestParam @Min(1) Long firstCatId, @RequestParam @Min(1) Long secondCatId) {
        return catService.quarrel(firstCatId, secondCatId);
    }

    @GetMapping("/friends/{id}")
    public List<CatDTO> retrieveFriends(@PathVariable("id") @Min(1) Long catId) {
        return catService.getFriends(catId);
    }

    @GetMapping("/bycolor/{color}")
    public List<CatDTO> retrieveAllByColor(@PathVariable("color") String color) {
        return catService.getCatsByColor(color);
    }

    @GetMapping("/retrieve/{id}")
    public CatDTO retrieve(@PathVariable("id") @Min(1) Long catId) {
        return catService.getCatById(catId);
    }
}
