package Context;

import DAO.DAO;
import Entities.Cat;
import Entities.Master;
import lombok.Getter;

public interface IDataContext {
    DAO<Cat> getCatDAO();
    DAO<Master> getMasterDAO();
}
