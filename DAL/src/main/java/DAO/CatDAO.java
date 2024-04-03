package DAO;

import Entities.Cat;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Optional;

public class CatDAO extends AbstractDAO<Cat> {
    public CatDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Cat> get(Long id) {
        return Optional.ofNullable(session.get(Cat.class, id));
    }

    @Override
    public List<Cat> getAll() {
        return session.createQuery("FROM Cat", Cat.class).list();
    }

    @Override
    public Cat save(Cat cat) {
        session.persist(cat);
        return cat;
    }
}
