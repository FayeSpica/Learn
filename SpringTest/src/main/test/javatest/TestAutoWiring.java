package javatest;

import cn.tonlyshy.pojo.autowiring.AutoWiringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAutoWiring extends UnitTestBase {
    public TestAutoWiring() {
        super("classpath:spring-autowiring.xml");
    }

    @Test
    public void testSay(){
        AutoWiringService service=super.getBean("autoWiringSerivce");
        service.say("helloßß");
    }
}
