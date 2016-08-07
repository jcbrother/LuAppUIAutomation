package com.lufax.ui.auto.executor;

import com.lufax.ui.auto.services.DriverGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jc on 16/8/7.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        DriverGenerator driverGenerator = (DriverGenerator) beanFactory.getBean("driverGenerator");
        driverGenerator.setLuCapabilities().getAppiumDriver();
        System.out.println(System.getProperty("user.dir") + String.format("%spackages", File.separator));
    }
}
