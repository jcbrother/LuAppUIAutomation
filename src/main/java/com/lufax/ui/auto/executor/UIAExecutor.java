package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.controller.ExecutorEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Jc on 16/8/7.
 */
public class UIAExecutor {

    public static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) throws IOException, InterruptedException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException {
        ExecutorEngine caseExecutor = (ExecutorEngine) ctx.getBean("executorEngine");
        caseExecutor.execute();
    }
}
