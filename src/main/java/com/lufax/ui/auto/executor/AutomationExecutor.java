package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.services.DriverGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by Jc on 16/8/7.
 */
public class AutomationExecutor {

    public static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static void main(String[] args) throws IOException {
        DriverGenerator driverGenerator = (DriverGenerator) ctx.getBean("driverGenerator");
        driverGenerator.setLuCapabilities().getAppiumDriver();
    }

}
