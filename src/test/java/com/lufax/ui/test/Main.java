package com.lufax.ui.test;

import org.apache.log4j.Logger;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;

/**
 * Created by Jc on 16/8/19.
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public void print(){
        logger.debug("hello world.");
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Main m = new Main();
//        m.print();
//        A b = new B();
//        System.out.println(b.getClass().getName());
        Class clazz = Class.forName("com.lufax.ui.test.A");
        A a = new A();
        Method method = clazz.getMethod("print", new Class[]{String.class, String.class});
//        Method method = clazz.getMethod("print");
        method.invoke(a,new String[]{"a","b"});
//        method.invoke(a);
//        Method[] methods = clazz.getDeclaredMethods();
//        for(Method method:methods){
//            System.out.println(method.getName());
//            System.out.println(method.getDeclaringClass().getName());
//            Class<?>[] params = method.getParameterTypes();
//            for (Class<?> param:params){
//                System.out.println(param.getName());
//            }
//        }
//        System.out.println(int.class);

    }
}
