package sample.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.JDBCConnectionException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class DbHelper {
    private static final SessionFactory ourSessionFactory;
    public static Session session;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void checkSession() {
        if (session == null || !session.isConnected() || !session.isOpen()) {
            session = getSession();
        }

        try {
            session.beginTransaction().commit();
        } catch (JDBCConnectionException ex) {
            session = getSession();
        }
    }

    private static void commitUpdate(Object entity) throws JDBCConnectionException {
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
    }

    public static void saveOrUpdate(Object entity) {
        checkSession();
        commitUpdate(entity);
    }

    public static void remove(Object entity) {
        checkSession();

        session.beginTransaction();
        session.remove(entity);
        session.getTransaction().commit();
    }

    public static LocalDate getLocalDate(java.sql.Date date) {
        java.util.Date d = new java.util.Date(date.getTime());
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // TODO: generic list type
    @SuppressWarnings("unchecked")
    public static List getAllEntitiesFromTable(Class entityClass) {
        checkSession();

        CriteriaBuilder cb = DbHelper.session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root rootEntry = cq.from(entityClass);
        CriteriaQuery all = cq.select(rootEntry);

        TypedQuery allQuery = DbHelper.session.createQuery(all);
        return allQuery.getResultList();
    }
}
