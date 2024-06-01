package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onebeattrue.dto.*;
import ru.onebeattrue.entities.cat.Cat;
import ru.onebeattrue.exceptions.FriendshipException;
import ru.onebeattrue.exceptions.NotFoundException;
import ru.onebeattrue.exceptions.QuarrelException;
import ru.onebeattrue.repositories.CatRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@ComponentScan("Repositories")
@EnableRabbit
public class CatService {
    private final RabbitTemplate rabbitTemplate;
    private final CatRepository catRepository;

    @RabbitListener(queues = "createCatQueue")
    public CatDTO create(CatDTO catDTO) {
        var cat = Cat.builder()
                .name(catDTO.name())
                .birthDate(catDTO.birthDate())
                .breed(catDTO.breed())
                .color(catDTO.color())
                .master(((MasterDTO) rabbitTemplate.convertSendAndReceive("getMasterByIdMasterQueue", catDTO.master())).id())
                .friends(new ArrayList<>())
                .build();

        return new CatDTO(catRepository.save(cat));
    }

    @RabbitListener(queues = "befriendCatQueue")
    public CatDTO befriend(IdPairDTO idPairDTO) {
        Long firstCatId = idPairDTO.firstId();
        Long secondCatId = idPairDTO.secondId();
        var firstCat = catRepository.findById(firstCatId).orElseThrow(() -> new NotFoundException("Cat " + firstCatId + " "));
        var secondCat = catRepository.findById(secondCatId).orElseThrow(() -> new NotFoundException("Cat " + secondCatId + " "));
        if (firstCat.getFriends().contains(secondCat) || secondCat.getFriends().contains(firstCat)) {
            throw new FriendshipException(secondCatId, firstCatId);
        }

        firstCat.getFriends().add(secondCat);
        secondCat.getFriends().add(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
        return new CatDTO(firstCat);
    }

    @RabbitListener(queues = "quarrelCatQueue")
    public CatDTO quarrel(IdPairDTO idPairDTO) {
        Long firstCatId = idPairDTO.firstId();
        Long secondCatId = idPairDTO.secondId();
        var firstCat = catRepository.findById(firstCatId).orElseThrow(() -> new NotFoundException("Cat " + firstCatId + " "));
        var secondCat = catRepository.findById(secondCatId).orElseThrow(() -> new NotFoundException("Cat " + secondCatId + " "));
        if (!firstCat.getFriends().contains(secondCat) || !secondCat.getFriends().contains(firstCat)) {
            throw new QuarrelException(firstCatId, secondCatId);
        }

        firstCat.getFriends().remove(secondCat);
        secondCat.getFriends().remove(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
        return new CatDTO(firstCat);
    }

    @RabbitListener(queues = "getFriendsCatQueue")
    public List<CatDTO> getFriends(Long catId) {
        var cat = catRepository.findById(catId).orElseThrow(() -> new NotFoundException("Cat " + catId + " "));
        List<CatDTO> friends = new ArrayList<>();
        for (Cat friend : cat.getFriends()) {
            friends.add(new CatDTO(friend));
        }

        return friends;
    }

    @RabbitListener(queues = "getAllCatsByColorCatQueue")
    public List<CatDTO> getCatsByColor(ColorDTO color) {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByColor(color.color())) {
            cats.add(new CatDTO(cat));
        }

        return cats;
    }

    @RabbitListener(queues = "getCatsByColorCatQueue")
    public List<CatDTO> getCatsByColor(ColorAndIdDTO colorAndIdDTO) {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByColor(colorAndIdDTO.color())) {
            if (cat.getMaster().equals(colorAndIdDTO.masterId())) {
                cats.add(new CatDTO(cat));
            }
        }

        return cats;
    }

    @RabbitListener(queues = "getCatByIdCatQueue")
    public CatDTO getCatById(Long catId) {
        return new CatDTO(catRepository.findById(catId).orElseThrow(() -> new NotFoundException("Cat " + catId + " ")));
    }

    @RabbitListener(queues = "getAllCatQueue")
    public List<CatDTO> getAll() {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAll()) {
            cats.add(new CatDTO(cat));
        }

        return cats;
    }

    @RabbitListener(queues = "getCatsByMasterCatQueue")
    public List<CatDTO> getCatsByMaster(Long master) {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAll()) {
            if (cat.getMaster().equals(master)) {
                cats.add(new CatDTO(cat));
            }
        }

        return cats;
    }
}