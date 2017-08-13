package javatest;

import cn.tonlyshy.pojo.annotation.BeanAnnotation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanAnnotation extends UnitTestBase {
    public TestBeanAnnotation() {
        super("classpath:spring-beanannotation.xml");
    }

//    @Test
//    public void say() {
//        BeanAnnotation bean = super.getBean("bean");
//        bean.say("test");
//    }

    @Test
    public void testScope(){
        BeanAnnotation bean = super.getBean("bean");
        bean.myHashCode();

        BeanAnnotation bean2 = super.getBean("bean");
        bean2.myHashCode();
    }
}
