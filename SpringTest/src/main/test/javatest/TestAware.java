package javatest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAware extends UnitTestBase {
    public TestAware() {
        super("classpath:spring-aware.xml");
    }

    @Test
    public void test() {
//        System.out.println("test :" + super.getBean("myApplicationContext").hashCode());
        System.out.println("test :" + super.getBean("myBeanName").hashCode());
    }
}
