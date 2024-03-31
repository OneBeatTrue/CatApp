package DAO;

import Entities.Cat;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class CatDAO extends AbstractDAO<Cat> {
    public CatDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Cat> get(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Cat cat = session.get(Cat.class, id);
            transaction.commit();
            session.close();
            return Optional.ofNullable(cat);
        }
        catch (HibernateException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Cat> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            var list = session.createQuery("FROM Cat", Cat.class).list();
            transaction.commit();
            session.close();
            return list;
        }
        catch (HibernateException e) {
            return List.of();
        }
    }

    @Override
    public Long save(Cat cat) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(cat);
            Long catId = cat.getId();
            transaction.commit();
            session.close();
            return catId;
        }
        catch (HibernateException e) {
            return null;
        }
    }
}
