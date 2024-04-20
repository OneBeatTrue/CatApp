package ru.onebeattrue.services;

import ru.onebeattrue.dto.CatDTO;
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
@ComponentScan("Repositories")
public class CatService implements ICatService {
    private final CatRepository catRepository;

    private final MasterRepository masterRepository;

    @Autowired
    public CatService(CatRepository catRepository,
                      MasterRepository masterRepository) {
        this.catRepository = catRepository;
        this.masterRepository = masterRepository;
    }

    public CatDTO create(CatDTO catDTO) {
        var cat = new Cat();
        cat.setName(catDTO.name());
        cat.setBirthDate(catDTO.birthDate());
        cat.setBreed(catDTO.breed());
        cat.setColor(Color.valueOf(catDTO.color()));
        var master = masterRepository.findById(catDTO.master());
        if (master.isEmpty()) {
            throw new NotFoundException("Master " + catDTO.master() + " ");
        }

        cat.setMaster(master.get());
        cat.setFriends(new ArrayList<>());
        long id = catRepository.save(cat).getId();
        return new CatDTO(id, cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor().toString(), cat.getMaster().getId());

    }

    public CatDTO befriend(Long firstCatId, Long secondCatId) {
        var firstCatOpt = catRepository.findById(firstCatId);
        if (firstCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + firstCatId + " ");
        }

        var secondCatOpt = catRepository.findById(secondCatId);
        if (secondCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + secondCatId + " ");
        }

        var firstCat = firstCatOpt.get();
        var secondCat = secondCatOpt.get();

        if (firstCat.getFriends().contains(secondCat) || secondCat.getFriends().contains(firstCat)) {
            throw new FriendshipException(secondCatId, firstCatId);
        }

        firstCat.getFriends().add(secondCat);
        secondCat.getFriends().add(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
        return new CatDTO(firstCat.getId(), firstCat.getName(), firstCat.getBirthDate(), firstCat.getBreed(), firstCat.getColor().toString(), firstCat.getMaster().getId());
    }

    public CatDTO quarrel(Long firstCatId, Long secondCatId) {
        var firstCatOpt = catRepository.findById(firstCatId);
        if (firstCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + firstCatId + " ");
        }

        var secondCatOpt = catRepository.findById(secondCatId);
        if (secondCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + secondCatId + " ");
        }

        var firstCat = firstCatOpt.get();
        var secondCat = secondCatOpt.get();

        if (!firstCat.getFriends().contains(secondCat) || !secondCat.getFriends().contains(firstCat)) {
            throw new QuarrelException(firstCatId, secondCatId);
        }

        firstCat.getFriends().remove(secondCat);
        secondCat.getFriends().remove(firstCat);
        catRepository.save(firstCat);
        catRepository.save(secondCat);
        return new CatDTO(firstCat.getId(), firstCat.getName(), firstCat.getBirthDate(), firstCat.getBreed(), firstCat.getColor().toString(), firstCat.getMaster().getId());
    }

    public List<CatDTO> getFriends(Long catId) {
        var catOpt = catRepository.findById(catId);
        if (catOpt.isEmpty()) {
            throw new NotFoundException("Cat " + catId + " ");
        }

        var cat = catOpt.get();
        List<CatDTO> friends = new ArrayList<>();
        for (Cat friend : cat.getFriends()) {
            friends.add(new CatDTO(friend.getId(), friend.getName(), friend.getBirthDate(), friend.getBreed(), friend.getColor().toString(), friend.getMaster().getId()));
        }
        return friends;
    }

    public List<CatDTO> getCatsByColor(String color) {
        List<CatDTO> cats = new ArrayList<>();
        for (Cat cat : catRepository.findAllByColor(Color.valueOf(color))) {
            cats.add(new CatDTO(cat.getId(), cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor().toString(), cat.getMaster().getId()));
        }

        return cats;
    }

    public CatDTO getCatById(Long catId) {
        var catOptional = catRepository.findById(catId);
        if (catOptional.isEmpty()) {
            throw new NotFoundException("Cat " + catId + " ");
        }

        var cat = catOptional.get();
        return new CatDTO(catId, cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor().toString(), cat.getMaster().getId());
    }
}