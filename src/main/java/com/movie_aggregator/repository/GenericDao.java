package com.movie_aggregator.repository;

import com.movie_aggregator.entity.*;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * The type Generic dao.
 */
@Repository
@Transactional
//TODO: when release enable foreign key checks
//     SET FOREIGN_KEY_CHECKS=0;
//     SET GLOBAL FOREIGN_KEY_CHECKS=0;
public class GenericDao {

    @Autowired
    private SessionFactory sessionFactory;

   // private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Save int.
     *
     * @param <T> the type parameter
     * @param o   the o
     * @return the int
     */
    public <T> int save(final T o) {

        return (Integer) sessionFactory.getCurrentSession().save(o);
    }

    public <T> void saveObject(final T o) {
        sessionFactory.getCurrentSession().save(o);
    }

    /**
     * Delete.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @param id   the id
     */
    public <T> void  delete(final Class<T> type, Integer id){
        T object = get(type, id);
        sessionFactory.getCurrentSession().delete(object);
    }

    public <T> void  deleteObject(final T o){
        sessionFactory.getCurrentSession().delete(o);
    }

    /**
     * Get t.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @param id   the id
     * @return the t
     */
    public <T> T get(final Class<T> type, final Integer id) {
        return (T) sessionFactory.getCurrentSession().get(type, id);
    }

    /**
     * Save or update.
     *
     * @param <T> the type parameter
     * @param o   the o
     */
    public <T> void saveOrUpdate(final T o) {
        sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

    /**
     * Gets all.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @return the all
     */
    public <T> List<T> getAll(final Class<T> type) {
        final Session session = sessionFactory.getCurrentSession();
        final Criteria crit = session.createCriteria(type);
        return crit.list();
    }

    /**
     * Gets last search.
     *
     * @return the last search
     */
//TODO: change from specific Type to Generic
    public Search getLastSearch() {
        final Session session = sessionFactory.getCurrentSession();
        Search lastSearch =  (Search) session.createQuery("from Search ORDER BY id DESC")
                .setMaxResults(1).uniqueResult();
        return lastSearch;
    }



    /**
     * Increment search number counter.
     *
     * @param id the id
     */
//TODO: make it generic
    public void incrementSearchNumberCounter(int id) {
        final Session session = sessionFactory.getCurrentSession();
        Query updateHits = session.createQuery(
                "UPDATE Search SET number = number + 1 WHERE id = :searchId" );
        updateHits.setInteger( "searchId", id );
        updateHits.executeUpdate();
    }

    public List<Search> getMostRecentSearches() {
        final Session session = sessionFactory.getCurrentSession();
        Query topSearchesQuery = session.createQuery(
                "SELECT search from Search  search " +
                        "order by search.number desc" );
        topSearchesQuery.setMaxResults(10);
        return topSearchesQuery.list();
    }




    public List<Object[]> getCountForEachReviewSource() {
        final Session session = sessionFactory.getCurrentSession();
        List<Object[]> rows =session.createNativeQuery
                ("select review_source_name, count(review_source_name) as rev_count " +
                "from user_review_source_lookup " +
                "group by review_source_name order by rev_count desc limit 10;").list();
         return rows;
    }


    public List<Movie> getMoviesByProperty(String field, String searchVal, String propertyEntity) {
        final Session session = sessionFactory.getCurrentSession();
        String hquery = String.format("select m from Movie m " +
                "inner join m.%s u " +
                "where u.%s=:%s", propertyEntity, field, field);
        Query movies = session.createQuery(hquery);
        movies.setParameter(field, searchVal);
        return movies.list();
    }

    /**
     * Gets movies based on search name.
     *
     * @param searchName the search name
     * @return the movies based on search name
     */
//TODO: make it generic
    public List<Movie> getMoviesBasedOnSearchName(String searchName) {
        final Session session = sessionFactory.getCurrentSession();
        Query mList = session.createQuery(
                "select movie from Search s join s.movies movie where s.name = :searchName" );
        mList.setParameter("searchName", searchName);
       return mList.list();
    }


    /**
     * Gets first entry based on another table column property.
     *
     * @param <T>          the type parameter
     * @param propertyName the property name
     * @param searchVal    the search val
     * @param type         the type
     * @return the first entry based on another table column property
     */
    public <T> T getFirstEntryBasedOnAnotherTableColumnProperty(String propertyName, String searchVal, Class<T> type) {
        final Session session = sessionFactory.getCurrentSession();
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




    /**
     * Merge t.
     *
     * @param <T> the type parameter
     * @param o   the o
     * @return the t
     */
    public <T> T merge(final T o)   {
        return (T) sessionFactory.getCurrentSession().merge(o);
    }
}
