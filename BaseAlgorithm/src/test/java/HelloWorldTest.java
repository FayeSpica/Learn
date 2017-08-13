import org.junit.Assert;
import org.junit.Test;

/**
 * Created by liaowm5 on 17/8/12.
 */
public class HelloWorldTest {
    @Test
    public void testHello() {
        Assert.assertEquals("Hello wolrd!", new HelloWorld().sayhello());
        System.out.println("sdf");
    }
}
