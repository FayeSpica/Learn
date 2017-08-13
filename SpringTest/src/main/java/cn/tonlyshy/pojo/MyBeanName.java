package cn.tonlyshy.pojo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyBeanName implements BeanNameAware, ApplicationContextAware {
    String beanName;

    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("MyBeanName = " + beanName);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext : " + applicationContext.getBean(this.beanName).hashCode());
    }
}
