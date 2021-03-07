package com.movie_aggregator.service;

import com.movie_aggregator.entity.Search;
import com.movie_aggregator.repository.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author mturchanov
 */
@org.springframework.stereotype.Service
public class GenericService {

    @Autowired
    private GenericDao genericDao;

    public <T> T save(final T o)
    {
        return (T) genericDao.save(o);
    }

    public <T> void  delete(final Class<T> type, Integer id){
      genericDao.delete(type, id);
    }

    /***/
    public <T> T get(final Class<T> type, final Integer id) {
        return (T) genericDao.get(type, id);
    }

    /***/
    public <T> T merge(final T o) {

        return (T) genericDao.merge(o);
    }

    /***/
    public <T> void saveOrUpdate(final T o) {
       genericDao.saveOrUpdate(o);
    }

    public <T> List<T> getAll(final Class<T> type) {
        return genericDao.getAll(type);
    }

    public <T> List<T> getAllByColumProperty(String propertyName, String searchVal, Class<T> type) {
        return genericDao.getAllByColumProperty(propertyName, searchVal, type);
    }

    public <T, S> void addEntityListToTableIfOtherTableHasntSpecifiedColumnProperty(List<T> entitiesToAdd, String propertyName, String searchVal, Class<S> type) {
        List<S> tableEntriesBySpecifiedColumnProperty = getAllByColumProperty(propertyName, searchVal, type);

        if (!entitiesToAdd.isEmpty() && tableEntriesBySpecifiedColumnProperty.isEmpty()) {
            for (T entityToAdd : entitiesToAdd) {
                genericDao.saveOrUpdate(entityToAdd);
            }
        }
    }
}
