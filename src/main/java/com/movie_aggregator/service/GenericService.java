package com.movie_aggregator.service;

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
}
