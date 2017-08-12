package javatest;

import cn.tonlyshy.interfaces.OneInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestOneInterface extends UnitTestBase{
    public TestOneInterface(){
        super("classpath*:spring-ioc.xml");
    }

    @Test
    public void testHello(){
        OneInterface oneInterface=super.getBean("OneInterface");
        System.out.println(oneInterface.hello("我的输入参数"));
    }
}
