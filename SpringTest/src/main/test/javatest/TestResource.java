package javatest;

import cn.tonlyshy.pojo.resource.MyResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.IOException;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestResource extends UnitTestBase {

    public TestResource() {
        super("classpath:spring-resource.xml");
    }

    @Test
    public void test() {
        MyResource resource = super.getBean("myResource");
        try {
            resource.resource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
