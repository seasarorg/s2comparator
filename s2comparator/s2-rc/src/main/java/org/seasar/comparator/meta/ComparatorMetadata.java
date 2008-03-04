package org.seasar.comparator.meta;

import java.lang.reflect.Method;
import java.util.Comparator;

public interface ComparatorMetadata {

	Comparator lookup(Method method);

}
