package ru.onebeattrue.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.onebeattrue.dto.*;
import ru.onebeattrue.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
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
    private final RabbitTemplate rabbitTemplate;
    private final UserService userService;

    @PostMapping("/create")
    public CatDTO create(@Valid @RequestBody CatDTO cat) {
        return (CatDTO) rabbitTemplate.convertSendAndReceive("createCatQueue", cat);
    }

    @PutMapping("/befriend")
    public CatDTO befriend(@RequestParam @Min(1) Long firstCatId, @RequestParam @Min(1) Long secondCatId) {
        return (CatDTO) rabbitTemplate.convertSendAndReceive("befriendCatQueue", new IdPairDTO(firstCatId, secondCatId));
    }

    @PutMapping("/quarrel")
    public CatDTO quarrel(@RequestParam @Min(1) Long firstCatId, @RequestParam @Min(1) Long secondCatId) {
        return (CatDTO) rabbitTemplate.convertSendAndReceive("quarrelCatQueue", new IdPairDTO(firstCatId, secondCatId));
    }

    @GetMapping("/friends/{id}")
    public CatListDTO retrieveFriends(@PathVariable("id") @Min(1) Long catId) {
        var catListDTO = (CatListDTO) rabbitTemplate.convertSendAndReceive("getFriendsCatQueue", catId);
        return catListDTO;
    }

    @GetMapping("/bycolor/{color}")
    public CatListDTO retrieveAllByColor(@PathVariable("color") ColorDTO color) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            var catListDTO = (CatListDTO) rabbitTemplate.convertSendAndReceive("getAllCatsByColorCatQueue", color);
            return catListDTO;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Long masterId = userService.getUserByUsername(username).master();
            var catListDTO = (CatListDTO) rabbitTemplate.convertSendAndReceive("getCatsByColorCatQueue", new ColorAndIdDTO(color.color(), masterId));
            return catListDTO;
        }

        return new CatListDTO(new ArrayList<>());
    }

    @GetMapping("/retrieve/{id}")
    public CatDTO retrieve(@PathVariable("id") @Min(1) Long catId) {
        return (CatDTO) rabbitTemplate.convertSendAndReceive("getCatByIdCatQueue", catId);
    }

    @GetMapping("/retrieve/all")
    public CatListDTO retrieveAllCats() {
        var catListDTO = (CatListDTO) rabbitTemplate.convertSendAndReceive("getAllCatQueue");
        return catListDTO;
    }
}
