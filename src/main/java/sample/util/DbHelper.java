package sample.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbHelper {
    private static final SessionFactory ourSessionFactory;
    private static Session session;

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

    private static void checkSession() {
        if (session == null || !session.isOpen()) {
            session = getSession();
        }
    }

    public static void saveOrUpdate(Object entity) {
        checkSession();

        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
    }

    // TODO: generic list type
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
