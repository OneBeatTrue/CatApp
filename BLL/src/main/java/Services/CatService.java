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
        context.getCatDAO().openCurrentSessionWithTransaction();
        long id = context.getCatDAO().save(cat).getId();
        context.getCatDAO().closeCurrentSessionWithTransaction();
        return new CatDTO(id, cat.getName(), cat.getBirthDate(), cat.getBreed(), cat.getColor().toString(), cat.getMaster().getId());

    }

    public CatDTO befriend(Long firstCatId, Long secondCatId) {
        context.getCatDAO().openCurrentSessionWithTransaction();
        var firstCatOpt = context.getCatDAO().get(firstCatId);
        if (firstCatOpt.isEmpty()) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new NotFoundException("Cat " + firstCatId + " ");
        }

        var secondCatOpt = context.getCatDAO().get(secondCatId);
        if (secondCatOpt.isEmpty()) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new NotFoundException("Cat " + secondCatId + " ");
        }

        var firstCat = firstCatOpt.get();
        var secondCat = secondCatOpt.get();

        if (firstCat.getFriends().contains(secondCat) || secondCat.getFriends().contains(firstCat)) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new FriendshipException(secondCatId, firstCatId);
        }

        firstCat.getFriends().add(secondCat);
        secondCat.getFriends().add(firstCat);
        context.getCatDAO().update(firstCat);
        context.getCatDAO().update(secondCat);
        context.getCatDAO().closeCurrentSessionWithTransaction();
        return new CatDTO(firstCat.getId(), firstCat.getName(), firstCat.getBirthDate(), firstCat.getBreed(), firstCat.getColor().toString(), firstCat.getMaster().getId());
    }

    public CatDTO quarrel(Long firstCatId, Long secondCatId) {
        context.getCatDAO().openCurrentSessionWithTransaction();
        var firstCatOpt = context.getCatDAO().get(firstCatId);
        if (firstCatOpt.isEmpty()) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new NotFoundException("Cat " + firstCatId + " ");
        }

        var secondCatOpt = context.getCatDAO().get(secondCatId);
        if (secondCatOpt.isEmpty()) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new NotFoundException("Cat " + secondCatId + " ");
        }

        var firstCat = firstCatOpt.get();
        var secondCat = secondCatOpt.get();

        if (!firstCat.getFriends().contains(secondCat) || !secondCat.getFriends().contains(firstCat)) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new QuarrelException(firstCatId, secondCatId);
        }

        firstCat.getFriends().remove(secondCat);
        secondCat.getFriends().remove(firstCat);
        context.getCatDAO().update(firstCat);
        context.getCatDAO().update(secondCat);
        context.getCatDAO().closeCurrentSessionWithTransaction();
        return new CatDTO(firstCat.getId(), firstCat.getName(), firstCat.getBirthDate(), firstCat.getBreed(), firstCat.getColor().toString(), firstCat.getMaster().getId());
    }

    public List<CatDTO> getFriends(Long catId) {
        context.getCatDAO().openCurrentSessionWithTransaction();
        var catOpt = context.getCatDAO().get(catId);
        if (catOpt.isEmpty()) {
            context.getCatDAO().closeCurrentSessionWithTransaction();
            throw new NotFoundException("Cat " + catId + " ");
        }

        var cat = catOpt.get();
        List<CatDTO> friends = new ArrayList<>();
        for (Cat friend : cat.getFriends()) {
            friends.add(new CatDTO(friend.getId(), friend.getName(), friend.getBirthDate(), friend.getBreed(), friend.getColor().toString(), friend.getMaster().getId()));
        }
        context.getCatDAO().closeCurrentSessionWithTransaction();
        return friends;
    }
}