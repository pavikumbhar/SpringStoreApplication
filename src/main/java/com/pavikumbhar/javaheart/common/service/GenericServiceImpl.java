
package com.pavikumbhar.javaheart.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pavikumbhar.javaheart.common.dao.GenericDao;

/**
 *
 * @author pavikumbhar
 */

public abstract class GenericServiceImpl<E, PK> implements GenericService<E, PK> {

	@Autowired
	private GenericDao<E, PK> genericDao;

	protected Class<E> clazz;

	public GenericServiceImpl(GenericDao<E, PK> genericDao) {
		this.genericDao = genericDao;
	}

	public GenericServiceImpl() {
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdate(E entity) {
		genericDao.saveOrUpdate(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<E> findAll() {
		return genericDao.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public E findById(PK id) {
		return genericDao.find(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(E entity) {
		genericDao.add(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(E entity) {
		genericDao.update(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(E entity) {
		genericDao.remove(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)

	public List<E> findRange(int beginIndex, int pageSize) {
		return genericDao.findRange(beginIndex, pageSize);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<E> findPage(int pageNumber, int pageSize) {
		return genericDao.findPage(pageNumber, pageSize);
	}
}