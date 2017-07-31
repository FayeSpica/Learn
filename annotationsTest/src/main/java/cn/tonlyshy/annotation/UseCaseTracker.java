package cn.tonlyshy.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by liaowm5 on 17/7/31.
 * 反射方式
 */
public class UseCaseTracker {

    public static void trackUseCases(List<Integer> useCases, Class<?> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            UseCase uc = method.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("UseCase : id = " + uc.id() + " description = " + uc.description());
                useCases.remove(new Integer(uc.id()));
            }
        }

        for (int i : useCases) {
            System.out.println("Warning: missing useCase - :" + i);
        }
    }

    public static void main(String Args[]) {
        List<Integer> useCases = new ArrayList<Integer>();
        Collections.addAll(useCases, 1, 2, 3, 4, 5);
        trackUseCases(useCases, PasswordUtils.class);
    }
}
