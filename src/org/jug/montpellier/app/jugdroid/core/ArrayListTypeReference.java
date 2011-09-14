/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.codehaus.jackson.type.TypeReference;

/**
 * This class overrides TypeReference to implement a ParameterizedType.  
 * @author etaix
 */
@SuppressWarnings("unchecked")
public class ArrayListTypeReference extends TypeReference<Object> {

	// Class type contained in the array list
	private Class clazz;

	/**
	 * Constructor which initialize the class contained into the array list
	 * @param cP
	 */
	public ArrayListTypeReference(Class cP) {
		super();
		clazz = cP;
	}

	/**
	 * Return the type. This type is always a ParameterizedType which contains:<br/>
	 * <ul>rawtype = ArrayList</ul>
	 * <ul>actualTypeArguments = the class attribut</ul> 
	 */
	public Type getType() {
		Type result = new ParameterizedType() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
			 */
			@Override
			public Type[] getActualTypeArguments() {
				return new Type[] { clazz };
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.reflect.ParameterizedType#getOwnerType()
			 */
			@Override
			public Type getOwnerType() {
				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.reflect.ParameterizedType#getRawType()
			 */
			@Override
			public Type getRawType() {
				return ArrayList.class;
			}
		};
		return result;
	}
}
