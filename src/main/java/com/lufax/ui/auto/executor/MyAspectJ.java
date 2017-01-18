package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.anotations.MyAnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Jc on 17/1/16.
 */

@Aspect
@Component
public class MyAspectJ {

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.MyAnotation)")
    public void sayHello() {
    }

    @Before("sayHello()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("this is before saying hello!");
    }

    @After("sayHello()")
    public void doAfter(JoinPoint joinPoint){
        System.out.println("this is after saying hello!");
    }
}