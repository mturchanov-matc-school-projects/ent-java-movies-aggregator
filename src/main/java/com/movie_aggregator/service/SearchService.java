package com.movie_aggregator.service;

import com.movie_aggregator.entity.Search;
import com.movie_aggregator.repository.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mturchanov
 */
@Service
public class SearchService {
    @Autowired
    private GenericDao dao;

    //@Transactional(readOnly = true)
    public List<Search> getAll() {
        return dao.getAll(Search.class);
    }

    public void delete(Search search) {
       //dao.delete(search);
    }

    /***/
    public Search get(Search search, Integer id) {
        return dao.get(search.getClass(), id);
    }

    /***/
    public Search merge(Search search) {
        return dao.merge(search);
    }

    /***/
    public void saveOrUpdate(Search search) {
        dao.saveOrUpdate(search);
    }
}
