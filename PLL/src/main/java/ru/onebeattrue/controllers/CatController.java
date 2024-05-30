package ru.onebeattrue.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import ru.onebeattrue.dto.ColorDTO;
import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.MasterDTO;
import ru.onebeattrue.models.Color;
import ru.onebeattrue.services.CatService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import ru.onebeattrue.services.UserService;

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
@RequiredArgsConstructor
public class CatController {

    private final CatService catService;
    private final UserService userService;

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
    public List<CatDTO> retrieveAllByColor(@PathVariable("color") ColorDTO color) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return catService.getCatsByColor(color);
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Long masterId = userService.getUserByUsername(username).master();
            return catService.getCatsByColor(color, masterId);
        }

        return List.of();
    }

    @GetMapping("/retrieve/{id}")
    public CatDTO retrieve(@PathVariable("id") @Min(1) Long catId) {
        return catService.getCatById(catId);
    }

    @GetMapping("/retrieve/all")
    public List<CatDTO> retrieveAllCats() {
        return catService.getAll();
    }
}
