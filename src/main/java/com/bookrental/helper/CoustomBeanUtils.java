package com.bookrental.helper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CoustomBeanUtils {

	private CoustomBeanUtils() {}
	
	public static void copyNonNullProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target, getNUllPropertyNames(source));
	}

	private static String[] getNUllPropertyNames(Object source) {
		
		final BeanWrapper src = new BeanWrapperImpl(source);
		Set<String> emptyNames = new HashSet<>();
		
		for(var propertyDescriptor:src.getPropertyDescriptors()) {
			String name = propertyDescriptor.getName();
			Object propertyValue = src.getPropertyValue(name);
			if(propertyValue == null) {
				emptyNames.add(name);
			}
		}
		return emptyNames.toArray(new String[0]);
	}

}
