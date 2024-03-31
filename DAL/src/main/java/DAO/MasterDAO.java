package DAO;

import Entities.Master;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class MasterDAO extends AbstractDAO<Master> {
    public MasterDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Master> get(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Master master = session.get(Master.class, id);
            transaction.commit();
            session.close();
            return Optional.ofNullable(master);
        }
        catch (HibernateException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Master> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            var list = session.createQuery("FROM Master", Master.class).list();
            transaction.commit();
            session.close();
            return list;
        }
        catch (HibernateException e) {
            return List.of();
        }
    }

    @Override
    public Long save(Master master) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(master);
            Long masterId = master.getId();
            transaction.commit();
            session.close();
            return masterId;
        }
        catch (HibernateException e) {
            return null;
        }
    }
}
