/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pavikumbhar.javaheart.common.dao;

import static org.springframework.util.StringUtils.isEmpty;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author pavikumbhar
 */
@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected Class<E> entityType;

	/**
	 * By defining this class as abstract, we prevent Spring from creating instance
	 * of this class If not defined as abstract, getClass().getGenericSuperClass()
	 * would return Object. There would be exception because Object class does not
	 * have a constructor with parameters.
	 */

	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		entityType = (Class<E>) pt.getActualTypeArguments()[0];

	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public void add(E entity) {
		entityManager.persist(entity);
	}

	@Override
	public void saveOrUpdate(E entity) {

	}

	@Override
	public void update(E entity) {
		entityManager.merge(entity);
	}

	@Override
	public void remove(E entity) {
		entity = entityManager.merge(entity);
		entityManager.remove(entity);
	}

	@Override
	public E find(K key) {
		return (E) entityManager.find(entityType, key);
	}

	@Override
	public List<E> findAll() {
		return entityManager.createQuery("FROM  ".concat(entityType.getSimpleName())).getResultList();
	}

	/*
	 * Use for pagination
	 */
	@Override
	public List<E> findRange(int beginIndex, int pageSize) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(entityType);
		TypedQuery<E> query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(beginIndex).setMaxResults(pageSize);

		return query.getResultList();
	}

	/*
	 * Use for pagination
	 *
	 */
	@Override
	public List<E> findPage(int pageNumber, int pageSize) {
		int beginIndex = pageNumber * pageSize;
		return findRange(beginIndex, pageSize);
	}

	public Long findFilteredCount(String search, List<String> searchProperties) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<E> from = query.from(entityType);
		query.select(builder.count(from));
		if (!isEmpty(search)) {
			Predicate[] buildFilterPredicates = buildFilterPredicates(from, search, searchProperties);
			if (buildFilterPredicates.length > 0) {
				query.where(builder.or(buildFilterPredicates));
			}
		}
		return count(entityManager.createQuery(query));
	}

	protected long count(TypedQuery<Long> query) {
		return query.getSingleResult();
	}

	/**
	 * 
	 * @param root
	 * @param search
	 * @param searchProperties
	 * @return
	 */
	private Predicate[] buildFilterPredicates(Root<E> root, String search, List<String> searchProperties) {
		if (search == null) {
			return new Predicate[] {};
		}
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		EntityType<E> type = entityManager.getMetamodel().entity(entityType);

		String[] split = search.split(",");

		Set<SingularAttribute<? super E, ?>> attributes = type.getSingularAttributes();
		List<Predicate> predicates = new ArrayList<Predicate>(split.length * attributes.size());
		for (String searchElem : split) {
			String searchProperty = null;
			if (searchElem.contains(":")) {
				String[] propSearchs = searchElem.trim().split(":", 2);
				searchElem = propSearchs[1];
				searchProperty = propSearchs[0];
			}

			boolean numeric;
			try {
				Double.parseDouble(searchElem);
				numeric = true;
			} catch (Exception e) {
				numeric = false;
			}
			for (SingularAttribute<? super E, ?> attribute : attributes) {
				if (searchProperties != null && !searchProperties.isEmpty()
						&& !searchProperties.contains(attribute.getName())) {
					continue; // skip this property as its not listed in searchable properties
				}
				if (searchProperty != null && !searchProperty.equals(attribute.getName())) {
					continue; // skip this property as we are searching for specific property
				}
				Class<?> javaType = attribute.getJavaType();
				if (javaType == String.class) {

					Predicate like = builder.like(builder.lower(root.get((SingularAttribute<E, String>) attribute)),
							"%" + searchElem.toLowerCase().trim() + "%");
					predicates.add(like);
				} else if (numeric && (Number.class.isAssignableFrom(javaType) || javaType == int.class
						|| javaType == short.class || javaType == long.class || javaType == float.class
						|| javaType == double.class || javaType == byte.class)) {
					Predicate like = builder.equal(root.get(attribute), searchElem.toLowerCase().trim());
					predicates.add(like);
				}

			}
		}
		return predicates.toArray(new Predicate[] {});
	}

	 @SuppressWarnings({ "rawtypes" })
	public int count() {
		CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
		Root<E> rt = cq.from(entityType);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}


	public List<E> findWithNamedQuery(String namedQueryName, Map<String, ? extends Object> parameters,
			Integer maxResults, Integer firstResult) {

		Query query = getEntityManager().createNamedQuery(namedQueryName);
		if (maxResults != null)
			query.setMaxResults(maxResults);
		if (firstResult != null)
			query.setFirstResult(firstResult);
		for (Map.Entry<String, ? extends Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		List<E> result = null;
		try {
			result = (List<E>) query.getResultList();
		} catch (NoResultException e) {
			return result;
		}
		return result;
	}
}
