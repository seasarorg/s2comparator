package org.seasar.comparator.comparator;

import java.util.Arrays;
import java.util.Comparator;

import org.seasar.framework.beans.BeanDesc;

public class ComparisonDelegator implements Comparator {

	ComparisonRule[] rules;
	BeanDesc beanDesc;
	
	public ComparisonDelegator(ComparisonRule[] rules, BeanDesc beanDesc) {
		this.rules = rules;
		this.beanDesc = beanDesc;
	}

	public int compare(Object o1, Object o2) {
		if (o1 == o2) {
			return 0;
		}
		int r = 0;
		for (ComparisonRule rule : rules) {
			Object v1 = beanDesc.getPropertyDesc(rule.getPropertyName()).getValue(o1);
			Object v2 = beanDesc.getPropertyDesc(rule.getPropertyName()).getValue(o2);
			if ((r=compare0(v1,v2,rule))!=0) {
				return r;
			}
		}
		return 0;
	}

	int compare0(Object o1, Object o2, ComparisonRule rule) {
//		int r = 0;
		if (o1 == null) {
			if (rule.isAscending()) {
				return rule.isNullsAreHigh() ? 1 : -1;
			} else {
				return rule.isNullsAreHigh() ? -1 : 1;
			}
		}
		if (o2 == null) {
			if (rule.isAscending()) {
				return rule.isNullsAreHigh() ? -1 : 1;
			} else {
				return rule.isNullsAreHigh() ? 1 : -1;
			}
		}
		Comparator comp = rule.getComparator();
		if (comp != null) {
			return rule.isAscending() ? comp.compare(o1, o2) : - comp.compare(o1, o2);
		} else if (o1 instanceof Comparable) {
			return rule.isAscending() ? ((Comparable) o1).compareTo(o2) :
				- ((Comparable) o1).compareTo(o2);
		} else {
			return 0;
		}
	}

	//TODO
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "RecordType:" + beanDesc.getBeanClass() + " " + Arrays.toString(rules);
	}

}
