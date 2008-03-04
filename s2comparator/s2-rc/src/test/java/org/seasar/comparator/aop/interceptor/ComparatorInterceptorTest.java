package org.seasar.comparator.aop.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.seasar.comparator.annotation.OrderBy;
import org.seasar.comparator.annotation.RecordType;
import org.seasar.framework.unit.S2FrameworkTestCase;

public class ComparatorInterceptorTest extends S2FrameworkTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include(getClass().getName().replace('.', '/') + ".dicon");
    }
    
    public void testInterTyping() throws Exception {
    	
    	EmpBeanComparator cmp = (EmpBeanComparator)this.getComponent(EmpBeanComparator.class);
    	
    	System.out.println(cmp.getEnameComparator());
    	System.out.println(cmp.getHiredateComparator());
    	System.out.println(cmp.getSalaryComparator());
    	
    	EmpBean e1 = new EmpBean("1", "scott", "19800101", 2000);
    	EmpBean e2 = new EmpBean("2", "jason", "19820101", 3000);
    	EmpBean e3 = new EmpBean("3", "john", "19750101", 4000);
    	EmpBean e4 = new EmpBean("4", "king", "19770101", 5000);
    	EmpBean e5 = new EmpBean("5", null, null, 5000);

    	List<EmpBean> list = new ArrayList<EmpBean>();
    	list.add(e4);
    	list.add(e2);
    	list.add(e3);
    	list.add(e1);
    	list.add(e5);
    	
    	Collections.sort(list, cmp.getEnameComparator());
    	assertEquals("1",list.get(0).getId());
    	assertEquals("5",list.get(4).getId());
    	System.out.println(list);

    	Collections.sort(list, cmp.getHiredateComparator());
    	System.out.println(list);
    	assertEquals("2",list.get(0).getId());
    	assertEquals("5",list.get(4).getId());

    	Collections.sort(list, cmp.getSalaryComparator());
    	System.out.println(list);
    	assertEquals("5",list.get(0).getId());
    	assertEquals("4",list.get(1).getId());
 	
    }
    
    public interface EmpBeanComparator {
        @OrderBy("id , ename, hiredate, salary desc")
        @RecordType(EmpBean.class)
    	Comparator getEnameComparator();
        @OrderBy("hiredate desc nulls last")
        @RecordType(EmpBean.class)        
    	Comparator getHiredateComparator();
        @OrderBy("salary desc, ename asc nulls first")
        @RecordType(EmpBean.class)        
    	Comparator getSalaryComparator();
    } 
    
    public class EmpBean implements Serializable {
		private static final long serialVersionUID = -8718383275173771283L;
		String id;
    	String ename;
    	String hiredate;
    	long salary;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}
		public String getHiredate() {
			return hiredate;
		}
		public void setHiredate(String hiredate) {
			this.hiredate = hiredate;
		}
		public long getSalary() {
			return salary;
		}
		public void setSalary(long salary) {
			this.salary = salary;
		}
		public EmpBean(String id, String ename, String hiredate, long salary) {
			super();
			this.id = id;
			this.ename = ename;
			this.hiredate = hiredate;
			this.salary = salary;
		}
		public String toString() {
			return id + " " + ename + " " + hiredate + " " + salary;
		}
    }

}
