package com.pavikumbhar.javaheart.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

public interface PersistanceDao {

	/**
	 * This method is used to save Entity in DB
	 * 
	 * @param t class type in which you want save
	 * @return
	 */
	public <T> T save(T t);

	/**
	 * This method is used to get Entity from DB
	 * 
	 * @param t  Entity class type which want to fetch
	 * @param id primary key
	 * @return
	 */
	public <T, PK> T read(Class<T> t, PK id);

	/**
	 * This method is used to update Entity
	 * 
	 * @param t Entity class type which want to update
	 */
	public <T> T update(T t);

	/**
	 * This method is used to delete Entity
	 * 
	 * @param t Entity class type which want to update
	 */
	public <T> void delete(T t);

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
	public List<Object[]> getNativeQueryResultList(String queryString, Object... bindVariables);

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
	 *    String queryString ="Select e from Entity e where e.name='%s'";
	 *    String name="NAME";
	 *	  List<Entity> entityList = persistanceDao.getEntityList(queryString,Entity.class name);
	 * }
	 *         </pre>
	 */
	public <T> List<T> getEntityList(String queryString, Class<T> typeKey, Object... bindVariables);

	/**
	 * @param queryString
	 * @param queryParamValue
	 * @return
	 */
	public <T> List<T> getEntityList(String queryString, HashMap<String, Object> queryParamValue);

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
	public <T> T getEntityByName(Class<T> typeKey, String name);

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
	public <T> T getNativeSingleColumnValue(String queryString, Class<T> typeKey, Object... bindVariables);
	
	/**
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	public <T> List<T> getNative(String queryString, Class<T> typeKey, Object... bindVariables);

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
	public <T> T getSingleColumnValue(String queryString, Class<T> typeKey, Object... bindVariables);
	
	
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
	public <T> List<T> getNativeResultListUsingMapper(String queryString, Class<T> typeKey, Object... bindVariables);
	
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
	public <T> List<T> getNativeResultListUsingMapper(String queryString,  Class<T> typeKey,Map<String, Object> queryParamValue) ;
	
	
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
	public <T> List<T> getNativeResultListUsingMapper(String queryString,  int constructorIndex,Class<T> typeKey,Object... bindVariables) ;
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	public  <T> List<T> getNativeSingleColumnValueList(String queryString, Class<T> typeKey, Object... bindVariables) ;
	
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	public  <T> List<T> getSingleColumnValueList(String queryString, Class<T> typeKey, Object... bindVariables) ;
	
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	public  <T> T getSingleResult(String queryString, Class<T> typeKey, Object... bindVariables) ;
	
	
	/**
	 * 
	 * 
	 * @param queryString
	 * @param typeKey
	 * @param bindVariables
	 * @return
	 */
	public  <T> T getNativeSingleResult(String queryString, Class<T> typeKey, Object... bindVariables) ;
	


}