package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDAO<T> implements DAO<T> {
    protected SessionFactory sessionFactory;
    protected AbstractDAO(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new NullPointerException("Null session factory");
        }

        this.sessionFactory = sessionFactory;
    }

    @Override
    public void update(T t) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(t);
            transaction.commit();
        }
    }

    @Override
    public void delete(T t) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(t);
            transaction.commit();
        }
    }
}
