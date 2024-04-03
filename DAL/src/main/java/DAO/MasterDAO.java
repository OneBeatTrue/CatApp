package DAO;

import Entities.Master;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

public class MasterDAO extends AbstractDAO<Master> {
    public MasterDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Master> get(Long id) {
        return Optional.ofNullable(session.get(Master.class, id));
    }

    @Override
    public List<Master> getAll() {
        return session.createQuery("FROM Master", Master.class).list();
    }

    @Override
    public Master save(Master master) {
        session.persist(master);
        return master;
    }
}
