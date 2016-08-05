package com.lufax.ui.test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangcan on 16/8/3.
 */
public class ClassDebugger {

    private static AppiumDriver driver;
//    private static WebDriver driver;

    public static void main(String[] args) throws MalformedURLException {


        File appDir = new File(System.getProperty("user.dir"));
        File app = new File(appDir, "packages/iOS/ljs_debug_43.ipa");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");                //手机os
        capabilities.setCapability("platformVersion", "9.0");            //要启动的手机OS版本
        capabilities.setCapability("deviceName", "iPhone");    //手机类型或模拟器类型，比如MI_2A/Android Emulator/iPhone Simulator
        capabilities.setCapability("udid", "d70b81b783e82e2821ddf72a875b17e47c9755d0");                //物理机ID
        capabilities.setCapability(CapabilityType.PLATFORM, "Mac");        //使用的是Mac平台
        capabilities.setCapability("app", app.getAbsolutePath());        //得到app绝对路径
        capabilities.setCapability("browserName", "safari");
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        File appDir = new File(System.getProperty("user.dir"));
//        File app = new File(appDir, "packages/Android/lufax_lufax_3.3.7.apk");
//        capabilities.setCapability("app", app.getAbsolutePath());
//        capabilities.setCapability("platformName", "Android");                //手机os
//        capabilities.setCapability("platformVersion", "5.1.1");        //真机的Android版本
//        capabilities.setCapability("udid", "323fb432");                //物理机ID
//        capabilities.setCapability(CapabilityType.PLATFORM, "Mac");        //使用的是mac平台
//        capabilities.setCapability("deviceName", "device");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


    }

}
