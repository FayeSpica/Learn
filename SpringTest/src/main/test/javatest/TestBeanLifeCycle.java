package javatest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanLifeCycle extends UnitTestBase {
    public TestBeanLifeCycle() {
        super("classpath:spring-beanlifecycle.xml");
    }

    @Test
    public void test1() {
        super.getBean("beanLifeCycle");
    }
}
