package org.seasar.comparator.comparator.impl;

import org.seasar.comparator.comparator.ComparatorDelegator;
import org.seasar.comparator.comparator.ComparisonRule;

public class ComparatorDelegatorImpl implements ComparatorDelegator {

	
	public ComparatorDelegatorImpl() {}

	public ComparatorDelegatorImpl(Class clazz) {
		
		System.out.println("INIT CLASS=" + clazz);		
	}	
	
	public void foo(Object o) {
		System.out.println("FFFOOOOOO = " + o);
	}

	public void setUp(Class clazz) {
		System.out.println("CLASS=" + clazz);
	}
	
//	public void setUp(ComparisonRule[] rules) {
//		
//	}
	
	
	public int compare(Object o1, Object o2) {
System.out.println(this.toString());
		return -999;
	}

}
