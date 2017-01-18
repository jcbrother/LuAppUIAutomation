package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.anotations.MyAnotation;
import org.springframework.stereotype.Component;

/**
 * Created by Jc on 17/1/16.
 */

@Component
public class MyAspectJDemoClass {

    @MyAnotation(description = "aop")
    public void hello(){
        System.out.println("hello world!");
    }

}
