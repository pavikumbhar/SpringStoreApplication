package com.pavikumbhar.javaheart.common.dao;

import java.util.List;

/**
 *
 * @author pavikumbhar
 * @param <E>
 * @param <PK>

 */
public interface GenericDao<E ,PK> {
    public void add(E entity) ;
    public void saveOrUpdate(E entity) ;
    public void update(E entity) ;
    public void remove(E entity);
    public E find(PK key);
    public List<E> findAll() ;
    public List<E> findRange(int beginIndex, int pageSize);
    public List<E> findPage(int pageNumber, int pageSize ) ;
}
