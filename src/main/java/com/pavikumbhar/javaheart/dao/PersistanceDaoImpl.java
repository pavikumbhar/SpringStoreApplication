package com.pavikumbhar.javaheart.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author pavikumbhar
 *
 */
@Repository
@Transactional
public class PersistanceDaoImpl implements PersistanceDao {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This method is used to save Entity in DB
	 * 
	 * @param t class type in which you want save
	 * @return
	 */
	@Override
	public <T> T save(T t) {
		this.entityManager.persist(t);
		return t;
	}

	/**
	 * This method is used to get Entity from DB
	 * 
	 * @param t  Entity class type which want to fetch
	 * @param id primary key
	 * @return
	 */
	@Override
	public <T, PK> T read(Class<T> t, PK id) {
		return this.entityManager.find(t, id);
	}

	/**
	 * This method is used to update Entity
	 * 
	 * @param t Entity class type which want to update
	 */
	@Override
	public <T> T update(T t) {
		return this.entityManager.merge(t);
	}

	/**
	 * This method is used to delete Entity
	 * 
	 * @param t Entity class type which want to update
	 */
	@Override
	public <T> void delete(T t) {
		t = this.entityManager.merge(t);
		this.entityManager.remove(t);
	}

	/**
	 * This method is used to fire native query
	 * 
	 * @param queryString   template queryString
	 * @param bindVariables queryString Bind variables to be replaced in template
	 *                      queryString.
	 * @return List<Object[]>
	 * 
	 *         <pre>
	* eg.
	* {@code
	      * 	
	      *    String queryString ="SELECT e.id,e.name FROM Entity e  e.name='%s'";
	      *    String name="NAME";
	      *	  List<Object[]> entityList = persistanceDao.getNativeQueryResultList(queryString, name);
	      * }
	 *         </pre>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getNativeQueryResultList(String queryString, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createNativeQuery(queryFormated);
		List<Object[]> records = query.getResultList();
		return records;

	}

	/**
	 * This method queries DB based on the input queryString and bindVariables and
	 * Cast the result Class typekey provided in input. Returns list of Class Type
	 * 
	 * @param bindVariables queryString Bind variables to be replaced in template
	 *                      query.
	 * @param typeKey       class type in which you want result
	 * @return <T> List<T> List Returns list with results of the query.
	 * 
	 *         <pre>
	 * eg.
	 * {@code
	 * 	
	 *    String queryString ="Select e from Entity e where e.name='%s";
	 *    String name="NAME";
	 *	  List<Entity> entityList = persistanceDao.getEntityList(queryString,Entity.class name);
	 * }
	 *         </pre>
	 */
	@Override
	public <T> List<T> getEntityList(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		List<T> entityList = new ArrayList<T>();
		TypedQuery<T> query = this.entityManager.createQuery(queryFormated, typeKey);
		entityList = query.getResultList();
		return entityList;
	}

	/**
	 * @param queryString
	 * @param queryParamValue
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getEntityList(String queryString, HashMap<String, Object> queryParamValue) {
		List<T> entityList = new ArrayList<T>();
		Query query = this.entityManager.createNativeQuery(queryString);
		for (Map.Entry<String, Object> pair : queryParamValue.entrySet()) {
			query.setParameter(pair.getKey(), pair.getValue());
		}
		entityList = query.getResultList();
		return entityList;
	}

	/**
	 * This method queries DB based on name and Cast the result Class typeKey
	 * provided in input. Returns object of Class Type
	 * 
	 * @param name    based on name fire query.
	 * @param typeKey class type in which you want result
	 * @return <T> T Returns object with results of the query.
	 * 
	 *         <pre>
	 * eg.
	 * {@code
	 *    String name="NAME";
	 *	  Entity entity= persistanceDao.getEntityByName(Entity.class, name);
	 * }
	 *         </pre>
	 */
	@Override
	public <T> T getEntityByName(Class<T> typeKey, String name) {
		List<T> entityList = new ArrayList<T>();
		TypedQuery<T> query = this.entityManager.createQuery("from " + typeKey.getName() + " where name = :name",
				typeKey);
		query.setParameter("name", name);
		entityList = query.getResultList();
		return entityList.stream().findFirst().orElse(null);

	}

	/**
	 * This method queries get Single column value and Cast the result Class typeKey
	 * 
	 * @param queryString
	 * @param typeKey       class type in which you want result
	 * @param bindVariables queryString Bind variables to be replaced in template
	 *                      query.
	 * @return <T> T Returns object with results of the query.
	 * 
	 *         <pre>
	 * eg.
	 * {@code
	 *    String queryString ="Select name from Entity  where id='%s";
	 *    Long id=100; 
	 *	  Entity entity= persistanceDao.getNativeSingleColumnValue(queryString, String.class, id);
	 * }
	 *         </pre>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getNativeSingleColumnValue(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createNativeQuery(queryFormated);
		T t = (T) query.getResultList().stream().findFirst().orElse(null);
		return t;
	}

	/**
	 * This method queries get Single column value and Cast the result Class typeKey
	 * 
	 * @param queryString
	 * @param typeKey       class type in which you want result
	 * @param bindVariables queryString Bind variables to be replaced in template
	 *                      query.
	 * @return <T> T Returns object with results of the query.
	 * 
	 *         <pre>
	 * eg.
	 * {@code
	 *    String queryString ="Select name from Entity  where id='%s";
	 *    Long id=100; 
	 *	  Entity entity= persistanceDao.getSingleColumnValue(queryString, String.class, id);
	 * }
	 *         </pre>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSingleColumnValue(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createQuery(queryFormated);
		T t = (T) query.getResultList().stream().findFirst().orElse(null);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getNative(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createNativeQuery(queryFormated);
		return (List<T>) query.unwrap(org.hibernate.query.NativeQuery.class).addEntity(typeKey).list();
	}
	
	/**
	 *   
     *	<b>Note:The usage is quite simple but be aware of:</b>
	 *  <ul>
	 *  	<li>The Constructor must have the same number of arguments as the result of the SQL query</li>
	 *  	<li>The result types must match the constructor arguments types</li>
	    </ul>
	    
	 * @see JpaResultMapper
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	@Override
	public <T> List<T> getNativeResultListUsingMapper(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createNativeQuery(queryFormated);
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		return jpaResultMapper.list(query, typeKey);
	}
	
	/**
	 *   
     *	<b>Note:The usage is quite simple but be aware of:</b>
	 *  <ul>
	 *  	<li>The Constructor must have the same number of arguments as the result of the SQL query</li>
	 *  	<li>The result types must match the constructor arguments types</li>
	    </ul>
	    
	 * @see JpaResultMapper
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param queryParamValue :
	 *  <pre>
	 * eg.
	 * 
	 * {@code
		    String queryString = "SELECT count(*) FROM Entity where columnNameOne= :columnOneParam  AND columnNameTwo= :columnOneParam ";
	        Map<String, Object> queryParamValue = new HashMap<>();
	        queryParamValue.put("columnOneParam", valueOne);
	        queryParamValue.put("columnTwoParam", ValueTwo);
	        persistanceDao.getNativeResultListUsingMapper(queryString,BigDecimal.class, queryParamValue);
        }
	 *  </pre>
	 * @return
	 */
	@Override
	public <T> List<T> getNativeResultListUsingMapper(String queryString,  Class<T> typeKey,Map<String, Object> queryParamValue) {
		Query query = this.entityManager.createNativeQuery(queryString);
		for (Map.Entry<String, Object> pair : queryParamValue.entrySet()) {
			query.setParameter(pair.getKey(), pair.getValue());
		}
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		return jpaResultMapper.list(query, typeKey);
	}
	
	
	/**
	 *   
     *	<b>Note:The usage is quite simple but be aware of:</b>
	 *  <ul>
	 *  	<li>The Constructor must have the same number of arguments as the result of the SQL query</li>
	 *  	<li>The result types must match the constructor arguments types</li>
	    </ul>
	    
	 * @see JpaResultMapper
	 * 
	 * @param queryString
	 * @param constructorIndex
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	@Override
	public <T> List<T> getNativeResultListUsingMapper(String queryString,  int constructorIndex,Class<T> typeKey,Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createNativeQuery(queryFormated);
		JpaResultMapper jpaResultMapper = new JpaResultMapper();
		return jpaResultMapper.list(query, typeKey,constructorIndex);
	}
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public  <T> List<T> getNativeSingleColumnValueList(String queryString, Class<T> typeKey, Object... bindVariables) {
		List<T> entityList = new ArrayList<T>();
		String queryFormated = String.format(queryString, bindVariables);
		Query query = this.entityManager.createNativeQuery(queryFormated);
		entityList = query.getResultList();
		return entityList;
	}
	
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	@Override
	public  <T> List<T> getSingleColumnValueList(String queryString, Class<T> typeKey, Object... bindVariables) {
		List<T> entityList = new ArrayList<T>();
		String queryFormated = String.format(queryString, bindVariables);
		TypedQuery<T>  query = this.entityManager.createQuery(queryFormated,typeKey);
		entityList =query.getResultList();
		return entityList;
	}
	
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	@Override
	public  <T> T getSingleResult(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		TypedQuery<T>  query = this.entityManager.createQuery(queryFormated,typeKey);
		return query.getSingleResult();
	}
	
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	@Override
	public  <T> T getNativeSingleResult(String queryString, Class<T> typeKey, Object... bindVariables) {
		String queryFormated = String.format(queryString, bindVariables);
		Query  query = this.entityManager.createNativeQuery(queryFormated);
		return (T) query.getSingleResult();
	}
	
	
	
	
}
