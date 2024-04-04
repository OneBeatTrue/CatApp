package Context;

import DAO.DAO;
import Entities.Cat;
import Entities.Master;
import lombok.Getter;

/**
 * Represents a data context interface responsible for providing access to DAO instances.
 */
public interface IDataContext {
    /**
     * Retrieves the DAO instance for managing Cat entities.
     *
     * @return The DAO instance for Cat entities.
     */
    DAO<Cat> getCatDAO();

    /**
     * Retrieves the DAO instance for managing Master entities.
     *
     * @return The DAO instance for Master entities.
     */
    DAO<Master> getMasterDAO();
}
