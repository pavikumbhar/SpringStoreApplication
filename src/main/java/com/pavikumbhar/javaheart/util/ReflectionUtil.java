package com.pavikumbhar.javaheart.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

public class ReflectionUtil {
	
	/**
	 * 
	 * @param type
	 * @param tuple
	 * @return
	 */
	public static <T> T mapArrayToClassType(Class<T> type, Object[] tuple){
		   List<Class<?>> tupleTypes = new ArrayList<>();
		   for(Object field : tuple){
		      tupleTypes.add(field.getClass());
		   }
		   try {
		      Constructor<T> ctor = type.getConstructor(tupleTypes.toArray(new Class<?>[tuple.length]));
		      return ctor.newInstance(tuple);
		   } catch (Exception e) {
		      throw new RuntimeException(e);
		   }
		}
	
	/**
	 * 
	 * @param type
	 * @param records
	 * @return
	 */
	public static <T> List<T> mapArrayListToClassTypeList(Class<T> type, List<Object[]> records){
		   List<T> result = new LinkedList<>();
		   for(Object[] record : records){
		      result.add(mapArrayToClassType(type, record));
		   }
		   return result;
		}

	/**
	 * 
	 * @param query
	 * @param type
	 * @return
	 */
	public static <T> List<T> getResultList(Query query, Class<T> type){
		  @SuppressWarnings("unchecked")
		  List<Object[]> records = query.getResultList();
		  return mapArrayListToClassTypeList(type, records);
		}
}
