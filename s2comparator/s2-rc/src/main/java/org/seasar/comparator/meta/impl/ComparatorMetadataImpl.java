package org.seasar.comparator.meta.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.seasar.comparator.annotation.OrderBy;
import org.seasar.comparator.annotation.RecordType;
import org.seasar.comparator.comparator.ComparisonDelegator;
import org.seasar.comparator.comparator.ComparisonRule;
import org.seasar.comparator.meta.ComparatorMetadata;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.exception.SRuntimeException;

public class ComparatorMetadataImpl implements ComparatorMetadata {

	Map<Method, Comparator> comparators = new HashMap<Method, Comparator>();

	public Comparator lookup(Method method) {
		Comparator comp = comparators.get(method);
		if (comp != null) {
			return comp;
		}
		if (!checkMethodAnnotation(method)) {
			return null;
		}
		comp = createComparator(method);
		comparators.put(method, comp);
		return comp;
	}

	Comparator createComparator(Method method) {
		Class rtnClass = method.getReturnType();
		String orderByClause = extactOrderByCaluse(method);
		Class recordClass = extactRecordType(method);
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(recordClass);
		ComparisonRule[] rules = parseClause(orderByClause, beanDesc);

		return new ComparisonDelegator(rules, beanDesc);
	}

	ComparisonRule[] parseClause(String orderByClause, BeanDesc beanDesc) {

		String[] sortOrders = orderByClause.split("\\s*,\\s*");
		ComparisonRule[] rules = new ComparisonRule[sortOrders.length];
		for (int i = 0; i < sortOrders.length; i++) {
			String[] s = sortOrders[i].split("\\s+");
			String propertyName = s[0];
			PropertyDesc pDesc = beanDesc.getPropertyDesc(propertyName);

			if (!pDesc.isReadable()) {
				throw new SRuntimeException("unreadable property:"
						+ propertyName);
			}
			boolean ascending = true;
			if (s.length > 1) {
				String a = s[1].toLowerCase();
				if ("asc".equals(a)) {
					ascending = true;
				} else if ("desc".equals(a)) {
					ascending = false;
				}
			}
			boolean nullsAreHigh = true;

			if (s.length >= 3 && "nulls".equalsIgnoreCase(s[s.length - 2])) {
				if ("first".equalsIgnoreCase(s[s.length - 1])) {
					nullsAreHigh = !ascending;
				} else if ("last".equalsIgnoreCase(s[s.length - 1])) {
					nullsAreHigh = ascending;
				}
			}

			ComparisonRule rule = new ComparisonRule();
			rule.setAscending(ascending);
			rule.setPropertyName(propertyName);
			rule.setNullsAreHigh(nullsAreHigh);
			// TODO
			rule.setComparator(null);
			rules[i] = rule;
		}

		// TODO Auto-generated method stub
		return rules;
	}

	boolean checkMethodAnnotation(Method method) {
		if (!Comparator.class.equals(method.getReturnType())) {
			return false;
		}
		if (extactOrderByCaluse(method) == null) {
			return false;
		}
		if (extactRecordType(method) == null) {
			return false;
		}
		return true;
	}
	
	String extactOrderByCaluse(Method method) {
		OrderBy antn = method.getAnnotation(OrderBy.class);
		if (antn !=null) {
			return antn.value();
		}
		return null;
	}

	Class extactRecordType(Method method) {
		Class recordType = null;
		RecordType rt = method.getAnnotation(RecordType.class);
		if (rt != null) {
			recordType = rt.value();
		}
		if (recordType != null) {
			return recordType;
		}

		try {
			Type t = ((ParameterizedType) method.getGenericReturnType())
					.getActualTypeArguments()[0];
			return (Class) t;
		} catch (Exception e) {
		}
		return recordType;
	}

}