package Context;

import DAO.CatDAO;
import DAO.DAO;
import DAO.MasterDAO;
import Entities.Cat;
import Entities.Master;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DataContext implements IDataContext{
    @Getter
    private DAO<Cat> catDAO;

    @Getter
    private DAO<Master> masterDAO;
    private SessionFactory sessionFactory;
    public DataContext() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            this.catDAO = new CatDAO(this.sessionFactory);
            this.masterDAO = new MasterDAO(this.sessionFactory);
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
