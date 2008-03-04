package org.seasar.comparator.aop.interceptor;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.comparator.meta.ComparatorMetadata;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.util.MethodUtil;

public class ComparatorInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 889790590344727451L;
	private ComparatorMetadata comparatorMetadata;
	
	public Object invoke(MethodInvocation invocation) throws Throwable {

        final Method method = invocation.getMethod();
        if (MethodUtil.isAbstract(method)) {
        	Comparator comp = comparatorMetadata.lookup(method);
            if (comp != null) {
                return comp;
            }
        }
        return invocation.proceed();
	}

	public void setComparatorMetadata(ComparatorMetadata comparatorMetadata) {
		this.comparatorMetadata = comparatorMetadata;
	}

}
