package ru.onebeattrue.services;

import lombok.RequiredArgsConstructor;
import ru.onebeattrue.dto.CatDTO;
import ru.onebeattrue.dto.ColorDTO;
import ru.onebeattrue.entities.Cat;
import ru.onebeattrue.exceptions.FriendshipException;
import ru.onebeattrue.exceptions.NotFoundException;
import ru.onebeattrue.exceptions.QuarrelException;
import ru.onebeattrue.models.Color;
import ru.onebeattrue.repositories.CatRepository;
import ru.onebeattrue.repositories.MasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@ComponentScan("Repositories")
public class CatService {
    private final CatRepository catRepository;

    private final MasterRepository masterRepository;

    public CatDTO create(CatDTO catDTO) {
        var cat = Cat.builder()
                .name(catDTO.name())
                .birthDate(catDTO.birthDate())
                .breed(catDTO.breed())
                .color(catDTO.color())
                .master(masterRepository.findById(catDTO.master()).orElseThrow(() -> new NotFoundException("Master " + catDTO.master() + " ")))
                .friends(new ArrayList<>())
                .build();

        return new CatDTO(catRepository.save(cat));
    }

    public CatDTO befriend(Long firstCatId, Long secondCatId) {
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

    public CatDTO quarrel(Long firstCatId, Long secondCatId) {
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

    public List<CatDTO> getFriends(Long catId) {
        var cat = catRepository.findById(catId).orElseThrow(() -> new NotFoundException("Cat " + catId + " "));
        List<CatDTO> friends = new ArrayList<>();
        for (Cat friend : cat.getFriends()) {
            friends.add(new CatDTO(friend));
        }
        return friends;
    }

    public List<CatDTO> getAll() {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAll()) {
            cats.add(new CatDTO(cat));
        }

        return cats;
    }

    public List<CatDTO> getCatsByColor(ColorDTO color) {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByColor(color.color())) {
            cats.add(new CatDTO(cat));
        }

        return cats;
    }

    public List<CatDTO> getCatsByColor(ColorDTO color, Long masterId) {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByColor(color.color())) {
            if (cat.getMaster().getId().equals(masterId)) {
                cats.add(new CatDTO(cat));
            }
        }

        return cats;
    }

    public CatDTO getCatById(Long catId) {
        return new CatDTO(catRepository.findById(catId).orElseThrow(() -> new NotFoundException("Cat " + catId + " ")));
    }
}