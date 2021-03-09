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
//TODO: when release enable foreign key checks
//     SET FOREIGN_KEY_CHECKS=0;
//     SET GLOBAL FOREIGN_KEY_CHECKS=0;
public class GenericDao {

    @Autowired
    private SessionFactory sessionFactory;

    public <T> int save(final T o) {
        return (Integer) sessionFactory.getCurrentSession().save(o);
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
    public <T> void saveOrUpdate(final T o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    public <T> List<T> getAll(final Class<T> type) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }

    //TODO: change from specific Type to Generic
    public Search getLastSearch() {
        final Session session = sessionFactory.getCurrentSession();
        Search lastSearch =  (Search) session.createQuery("from Search ORDER BY id DESC")
                .setMaxResults(1).uniqueResult();
        return lastSearch;
    }

    //TODO: make it generic
    public void incrementSearchNumberCounter(int id) {
        final Session session = sessionFactory.getCurrentSession();
        Query updateHits = session.createQuery(
                "UPDATE Search SET number = number + 1 WHERE id = :searchId" );
        updateHits.setInteger( "searchId", id );
        updateHits.executeUpdate();
    }

    //TODO: make it generic
    public List<Movie> getMoviesBasedOnSearchName(String searchName) {
        final Session session = sessionFactory.getCurrentSession();
        Query mList = session.createQuery(
                "select movie from Search s join s.movies movie where s.name = :searchName" );
        mList.setParameter("searchName", searchName);
       return mList.list();
    }


    public <T> T getFirstEntryBasedOnAnotherTableColumnProperty(String propertyName, String searchVal, Class<T> type) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(propertyName), searchVal));
        T tableEntity = session.createQuery(query)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        //session.close();
        return tableEntity;
    }
}
