package Services;

import Context.IDataContext;
import DTO.CatDTO;
import Entities.Cat;
import Exceptions.FriendshipException;
import Exceptions.NotFoundException;
import Exceptions.QuarrelException;
import Models.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatService extends AbstractService implements ICatService {

    public CatService(IDataContext context) {
        super(context);
    }

    public CatDTO create(CatDTO catDTO) {
        var cat = new Cat();
        cat.setName(catDTO.name());
        cat.setBirthDate(catDTO.birthDate());
        cat.setBreed(catDTO.breed());
        cat.setColor(Color.valueOf(catDTO.color()));
        var master = context.getMasterDAO().get(catDTO.master());
        if (master.isEmpty()) {
            throw new NotFoundException("Master " + catDTO.master() + " ");
        }

        cat.setMaster(master.get());
        cat.setFriends(new ArrayList<>());
        long id = context.getCatDAO().save(cat);
        return new CatDTO(id, cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor().toString(), cat.getMaster().getId());

    }

    public CatDTO befriend(Long firstCatId, Long secondCatId) {
        var firstCatOpt = context.getCatDAO().get(firstCatId);
        if (firstCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + firstCatId + " ");
        }

        var secondCatOpt = context.getCatDAO().get(secondCatId);
        if (secondCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + secondCatId + " ");
        }

        var firstCat = firstCatOpt.get();
        var secondCat = secondCatOpt.get();
        if (verifyFriendship(secondCat, firstCat.getFriends())) {
            throw new FriendshipException(firstCatId, secondCatId);
        }

//        if (verifyFriendship(firstCat, secondCat.getFriends())) {
//            throw new FriendshipException(secondCatId, firstCatId);
//        }

        firstCat.getFriends().add(secondCat);
        context.getCatDAO().update(firstCat);
//        secondCat.getFriends().add(firstCat);
//        context.getCatDAO().update(secondCat);
        return new CatDTO(firstCat.getId(), firstCat.getName(), firstCat.getBirthDate(), firstCat.getBreed(), firstCat.getColor().toString(), firstCat.getMaster().getId());
    }

    public CatDTO quarrel(Long firstCatId, Long secondCatId) {
        var firstCatOpt = context.getCatDAO().get(firstCatId);
        if (firstCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + firstCatId + " ");
        }

        var secondCatOpt = context.getCatDAO().get(secondCatId);
        if (secondCatOpt.isEmpty()) {
            throw new NotFoundException("Cat " + secondCatId + " ");
        }

        var firstCat = firstCatOpt.get();
        var secondCat = secondCatOpt.get();
        if (!verifyFriendship(secondCat, firstCat.getFriends())) {
            throw new QuarrelException(firstCatId, secondCatId);
        }

//        if (!verifyFriendship(firstCat, secondCat.getFriends())) {
//            throw new QuarrelException(secondCatId, firstCatId);
//        }

        deleteFriend(secondCat, firstCat.getFriends());
        context.getCatDAO().update(firstCat);
//        deleteFriend(firstCat, secondCat.getFriends());
//        context.getCatDAO().update(secondCat);
        return new CatDTO(firstCat.getId(), firstCat.getName(), firstCat.getBirthDate(), firstCat.getBreed(), firstCat.getColor().toString(), firstCat.getMaster().getId());
    }

    public List<CatDTO> getFriends(Long catId) {
        var catOpt = context.getCatDAO().get(catId);
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

    private boolean verifyFriendship(Cat cat, List<Cat> friends) {
        for (var friend : friends) {
            if (Objects.equals(friend.getId(), cat.getId())) {
                return true;
            }
        }

        return false;
    }

    private void deleteFriend(Cat cat, List<Cat> friends) {
        var iterator = friends.iterator();
        while (iterator.hasNext()) {
            Cat friend = iterator.next();
            if (friend.getId().equals(cat.getId())) {
                iterator.remove();
                break;
            }
        }
    }
}