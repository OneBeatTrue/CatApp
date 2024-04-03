package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public abstract class AbstractDAO<T> implements DAO<T> {
    protected SessionFactory sessionFactory;
    protected Session session;
    protected Transaction transaction;

    protected AbstractDAO(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new NullPointerException("Null session factory");
        }

        this.sessionFactory = sessionFactory;
    }

    @Override
    public T update(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return getCurrentSession().merge(t);
    }

    @Override
    public void delete(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        getCurrentSession().remove(t);
    }

    @Override
    public void openCurrentSessionWithTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @Override
    public void closeCurrentSessionWithTransaction() {
        transaction.commit();
        session.close();
    }

    protected Session getCurrentSession() {
        if (!session.isOpen()) {
            throw new NullPointerException("Session not opened");
        }

        return session;
    }

    protected Transaction getTransaction() {
        if (!transaction.isActive()) {
            throw new NullPointerException("Transaction not active");
        }

        return transaction;
    }
}