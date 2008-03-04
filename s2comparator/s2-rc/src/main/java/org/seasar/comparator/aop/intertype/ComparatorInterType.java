package org.seasar.comparator.aop.intertype;

import java.lang.reflect.Modifier;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import javassist.CtField.Initializer;

import org.seasar.comparator.comparator.impl.ComparatorDelegatorImpl;
import org.seasar.framework.aop.intertype.AbstractInterType;

public class ComparatorInterType extends AbstractInterType {

	static final Class[] PARAM_TYPES = {Object.class, Object.class};
	static final Class RETURN_TYPE = int.class;
	
	@Override
	protected void introduce() throws CannotCompileException, NotFoundException {
		// TODO Auto-generated method stub
		Class clazz = this.getTargetClass();
		
		
		this.addField(Modifier.PRIVATE, Class.class, "targetClass", 
				this.getTargetClass().getName() + ".class");
		
		this.addField(Modifier.PRIVATE, ComparatorDelegatorImpl.class, "delegator"
				,"new " + ComparatorDelegatorImpl.class.getName() + "(targetClass)"
				);

		String src = "return delegator.compare($1,$2);";
		this.addMethod(RETURN_TYPE, "compare", PARAM_TYPES,  src);
		
		
	}

}
