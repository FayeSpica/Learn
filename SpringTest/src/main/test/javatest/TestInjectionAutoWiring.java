package javatest;

import cn.tonlyshy.pojo.annotation.service.InjectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestInjectionAutoWiring extends UnitTestBase {
    public TestInjectionAutoWiring() {
        super("classpath:spring-beanannotation.xml");
    }

    @Test
    public void testAutowired(){
        InjectionService service=super.getBean("injectionServiceImpl");
        service.save("test autowiring");
    }
}