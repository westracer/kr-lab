package sample.util;

import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbHelper {
    public static Session session;

    public static void saveOrUpdate(Object entity) {
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
    }

    // TODO: generic list type
    public static List getAllEntitiesFromTable(Class entityClass) {
        CriteriaBuilder cb = DbHelper.session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(entityClass);
        Root rootEntry = cq.from(entityClass);
        CriteriaQuery all = cq.select(rootEntry);

        TypedQuery allQuery = DbHelper.session.createQuery(all);
        return allQuery.getResultList();
    }
}
