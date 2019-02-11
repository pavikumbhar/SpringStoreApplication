/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavikumbhar.javaheart.common.service;

import java.util.List;

/**
 *
 * @author pavikumbhar
 */
public interface GenericService<E, PK> {
    public void saveOrUpdate(E entity);
    public List<E> findAll();
    public E findById(PK id);
    public void add(E entity);
    public void update(E entity);
    public void remove(E entity);
    public List<E> findRange(int beginIndex, int pageSize);
    public List<E> findPage(int pageNumber, int pageSize ) ;
    
    
    
}