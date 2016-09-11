package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.controller.ExecutorEngine;
import com.lufax.ui.auto.services.DriverGeneratorService;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jc on 16/8/7.
 */
public class UIAExecutor {

    public static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorEngine caseExecutor = (ExecutorEngine) ctx.getBean("executorEngine");
        caseExecutor.execute();
    }
}
