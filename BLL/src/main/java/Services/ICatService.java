package Services;

import DTO.CatDTO;

import java.util.List;

/**
 * Interface representing the service layer for managing cat entities.
 */
public interface ICatService {

    /**
     * Creates a new cat entity based on the provided cat DTO.
     *
     * @param catDTO The cat DTO containing information about the new cat.
     * @return The DTO representation of the created cat entity.
     */
    CatDTO create(CatDTO catDTO);

    /**
     * Establishes a friendship between two cats based on their IDs.
     *
     * @param firstCatId  The ID of the first cat.
     * @param secondCatId The ID of the second cat.
     * @return The DTO representation of one of the cats involved in the friendship.
     */
    CatDTO befriend(Long firstCatId, Long secondCatId);

    /**
     * Ends the friendship between two cats based on their IDs.
     *
     * @param firstCatId  The ID of the first cat.
     * @param secondCatId The ID of the second cat.
     * @return The DTO representation of one of the cats involved in the quarrel.
     */
    CatDTO quarrel(Long firstCatId, Long secondCatId);

    /**
     * Retrieves the list of friends for a given cat based on its ID.
     *
     * @param catId The ID of the cat.
     * @return The list of DTO representations of the cat's friends.
     */
    List<CatDTO> getFriends(Long catId);
}
