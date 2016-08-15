package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.services.DriverGeneratorService;
import io.appium.java_client.AppiumDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jc on 16/8/7.
 */
public class AutomationExecutor {

    public static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) throws IOException, InterruptedException {
        DriverGeneratorService driverGeneratorService = (DriverGeneratorService) ctx.getBean("driverGeneratorService");
        AppiumDriver driver = driverGeneratorService.setLuCapabilities().getAppiumDriver();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        int witdh = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        for (int i = 0; i < 3; i++) {
            driver.swipe(witdh * 9 / 10, height / 2, witdh / 10, height / 2, 1000);
            Thread.sleep(3000);
        }

    }
}
