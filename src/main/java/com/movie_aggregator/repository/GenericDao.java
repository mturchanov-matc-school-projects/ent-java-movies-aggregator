package com.movie_aggregator.repository;

import com.movie_aggregator.entity.Movie;
import com.movie_aggregator.entity.Search;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class GenericDao {

    @Autowired
    private SessionFactory sessionFactory;

    public <T> T save(final T o) {
        return (T) sessionFactory.getCurrentSession().save(o);
    }



    public <T> void  delete(final Class<T> type, Integer id){
        T object = get(type, id);
        sessionFactory.getCurrentSession().delete(object);
    }

    /***/
    public <T> T get(final Class<T> type, final Integer id) {
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    /***/
    public <T> T merge(final T o) {
        return (T) sessionFactory.getCurrentSession().merge(o);
    }

    /***/
    public <T> void saveOrUpdate(final T o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public <T> List<T> getAll(final Class<T> type) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }

    public <T> List<T> getAllByColumProperty(String propertyName, String searchVal, Class<T> type) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(propertyName), searchVal));
        List<T> tableEntities = session.createQuery(query)
                .getResultList();

        //session.close();
        return tableEntities;
    }
}
