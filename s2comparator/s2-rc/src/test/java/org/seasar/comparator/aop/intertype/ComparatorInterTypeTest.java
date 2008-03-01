package org.seasar.comparator.aop.intertype;

import org.seasar.framework.unit.S2FrameworkTestCase;

public class ComparatorInterTypeTest extends S2FrameworkTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        include(getClass().getName().replace('.', '/') + ".dicon");
    }

}
