package javatest;

import cn.tonlyshy.pojo.annotation.service.InjectionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestInjection extends UnitTestBase {
    public TestInjection() {
        super("classpath:spring-injection.xml");
    }

    @Test
    public void testSetter() {
        InjectionService service = super.getBean("injectionService");
        service.save("要保存的数据");
    }
}
