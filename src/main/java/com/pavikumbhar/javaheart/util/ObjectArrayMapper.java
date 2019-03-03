package com.pavikumbhar.javaheart.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * 
 *@author pavikumbhar</br></br>
 *  
 *	<b>The usage is quite forward but be aware of:</b>
 *  <ul>
 *  	<li>The Constructor must have the same number of arguments as the result of the SQL query</li>
 *  	<li>The result types must match the constructor arguments types</li>
    </ul>
 */
public class ObjectArrayMapper  {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_BOX_TYPE_MAP = new HashMap<>();

    static {
        PRIMITIVE_TO_BOX_TYPE_MAP.put(int.class, Integer.class);
        PRIMITIVE_TO_BOX_TYPE_MAP.put(long.class, Long.class);
        PRIMITIVE_TO_BOX_TYPE_MAP.put(byte.class, Byte.class);
        PRIMITIVE_TO_BOX_TYPE_MAP.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_BOX_TYPE_MAP.put(char.class, Character.class);
        PRIMITIVE_TO_BOX_TYPE_MAP.put(float.class, Float.class);
        PRIMITIVE_TO_BOX_TYPE_MAP.put(double.class, Double.class);
    }

    @SuppressWarnings(value = "unchecked")
    protected <T> T createInstance(Constructor<?> ctor, Object[] args) {
        try {
            return (T) ctor.newInstance(args);
        } catch (IllegalArgumentException e) {
            StringBuilder sb = new StringBuilder("no constructor taking:\n");
            for (Object object : args) {
                if (object != null) {
                    sb.append("\t").append(object.getClass().getName()).append("\n");
                }
            }
            throw new RuntimeException(sb.toString(), e);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }
    
    /**
     * Returns a list of objects 
     *
     * @param rawResults
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalArgumentException
     */

    public <T> List<T> getResultList(List<?> rawResults, Class<T> clazz)
            throws IllegalArgumentException {
        List<T> result = new ArrayList<>();

        List<Object[]> list = postProcessResultList(rawResults);

        Constructor<?> ctor = null;
        if (list != null && !list.isEmpty()) {
            ctor = findConstructor(clazz, list.get(0));
        }
        for (Object[] obj : list) {
            result.add(createInstance(ctor, obj));
        }
        return result;
    }
    
    
    /**
     * Returns a list of clazz 
     *
     * @param rawResults
     * @param clazz
     * @param <T>
     * @return
     * @throws IllegalArgumentException
     */

    public <T> List<T> getResultList(List<?> rawResults, Class<T> clazz,int constructorIndex)
            throws IllegalArgumentException {
    	   List<T> result = new ArrayList<>();

           Constructor<?> ctor = null;
           Optional<Constructor<?>> ctorOp = Arrays.stream(clazz.getDeclaredConstructors())
           		.filter(con -> con.getAnnotation(MapperConstructor.class)!=null)
   		        .filter(con ->constructorIndex==con.getAnnotation(MapperConstructor.class).index())
   		        .findFirst();
           
           if(!ctorOp.isPresent()) {
           	 throw new RuntimeException("No constructor annotated with  MapperConstructor and index :"+constructorIndex);
           }
          
           List<Object[]> list = postProcessResultList(rawResults);

          
           if (list != null && !list.isEmpty()) {
               ctor = ctorOp.get();
           }
           for (Object[] obj : list) {
               result.add(createInstance(ctor, obj));
           }
           return result;
    }
    
    
    public <T> T uniqueResult(List<?> rawResults, Class<T> clazz) {
        Object[] rec = postProcessSingleResult(rawResults);
        Constructor<?> ctor = findConstructor(clazz, rec);

        return createInstance(ctor, rec);
    }
    
   
   


    @SuppressWarnings("unchecked")
    private List<Object[]> postProcessResultList(List<?> rawResults) {
        List<Object[]> result = new ArrayList<>();

        if (rawResults.size() == 1) {
            for (Object rawResult : rawResults) {
                result.add(postProcessSingleResult(rawResult));
            }
        } else {
            result = (List<Object[]>) rawResults;
        }

        return result;
    }

    private Object[] postProcessSingleResult(Object rawResult) {
        return rawResult instanceof Object[] ? (Object[]) rawResult
                : new Object[]{rawResult};
    }

    private Constructor<?> findConstructor(Class<?> clazz, Object... args) {
        Constructor<?> result = null;
        final Constructor<?>[] ctors = clazz.getDeclaredConstructors();

        // More stable check
        if (ctors.length == 1 && ctors[0].getParameterTypes().length == args.length) {
            // If there is only one constructor we take that
            result = ctors[0];
        }
        if (ctors.length > 1) {
            NEXT_CONSTRUCTOR:
            for (Constructor<?> ctor : ctors) {
                final Class<?>[] parameterTypes = postProcessConstructorParameterTypes(ctor
                        .getParameterTypes());
                if (parameterTypes.length != args.length) {
                    continue NEXT_CONSTRUCTOR;
                } else {
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (args[i] != null) {
                            Class<?> argType = convertToBoxTypeIfPrimitive(args[i]
                                    .getClass());
                            if (!parameterTypes[i].isAssignableFrom(argType)) {
                                continue NEXT_CONSTRUCTOR;
                            }
                        }
                    }
                    result = ctor;
                    break;
                }
            }
        }
        if (null == result) {
            StringBuilder sb = new StringBuilder("No constructor taking:\n");
            for (Object object : args) {
                if (object != null) {
                    sb.append("\t").append(object.getClass().getName())
                            .append("\n");
                }
            }
            throw new RuntimeException(sb.toString());
        }
        return result;
    }

    /**
     * <p>
     * According to the JLS primitive types are not assignable to their box type
     * counterparts. E. g. int.class.isAssignableFrom(Integer.class) returns
     * false.
     * </p>
     * <p>
     * In order to make the isAssignable check in findConstructors work with
     * primitives, the check uses this method to convert possible primitive
     * constructor argument types to their box type counterparts.
     * </p>
     */
    private Class<?>[] postProcessConstructorParameterTypes(
            Class<?>[] rawParameterTypes) {
        Class<?>[] result = new Class<?>[rawParameterTypes.length];
        for (int i = 0; i < rawParameterTypes.length; i++) {
            Class<?> currentType = rawParameterTypes[i];
            result[i] = convertToBoxTypeIfPrimitive(currentType);
        }

        return result;
    }

    /**
     * @return The box type matching the provided primitive type or
     * <code>primitiveType</code> if no match could be found (e. g. the provided
     * value was not a primitive type).
     */
    private Class<?> convertToBoxTypeIfPrimitive(Class<?> primitiveType) {
        return PRIMITIVE_TO_BOX_TYPE_MAP.getOrDefault(primitiveType, primitiveType);
    }
}