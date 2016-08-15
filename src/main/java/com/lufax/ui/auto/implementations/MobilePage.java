package com.lufax.ui.auto.implementations;

import com.lufax.ui.auto.interfaces.PageActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;

/**
 * Created by Jc on 16/8/7.
 */

public class MobilePage implements PageActions{

    public String currentPageName = "";
    public AppiumDriver driver = null;
    public int pageWidth = 0;
    public int pageHeight = 0;
    public int pageSwipeDuration = 1000;
    public int longTapDuration = 1000;


    public MobilePage setCurrentPageName(String currentPageName) {
        this.currentPageName = currentPageName;
        return this;
    }

    public MobilePage setDriver(AppiumDriver driver) {
        this.driver = driver;
        return this;
    }

    public MobilePage setPageSize(){
        this.pageWidth = driver.manage().window().getSize().width;
        this.pageHeight = driver.manage().window().getSize().height;
        return this;
    }


    @Override
    public void swipeUp() {
        driver.swipe(pageWidth/2, pageHeight*3/4, pageWidth/2, pageHeight/4, pageSwipeDuration);
    }

    @Override
    public void swipeDown() {
        driver.swipe(pageWidth/2, pageHeight/4, pageWidth/2, pageHeight*3/4, pageSwipeDuration);
    }

    @Override
    public void swipeLeft() {
        driver.swipe(pageWidth*3/4, pageHeight/2, pageWidth/4, pageHeight/2, pageSwipeDuration);
    }

    @Override
    public void swipeRight() {
        driver.swipe(pageWidth/4, pageHeight/2, pageWidth*3/4, pageHeight/2, pageSwipeDuration);
    }

    @Override
    public void swipeForBack() {
        driver.swipe(0, pageHeight/2, pageWidth*3/4, pageHeight/2, pageSwipeDuration/2);
    }

    @Override
    public void shortTapById(String byId) {
        WebElement element = driver.findElementById(byId);
        element.click();
    }

    @Override
    public void longTapById(String byId) {
        TouchAction action = new TouchAction(driver);
        WebElement element = driver.findElementById(byId);
        action.longPress(element,longTapDuration).perform();
    }

    @Override
    public void shortTapByXpath(String xpath) {
        WebElement element = driver.findElementByXPath(xpath);
        element.click();
    }

    @Override
    public void longTapByXpath(String xpath) {
        TouchAction action = new TouchAction(driver);
        WebElement element = driver.findElementByXPath(xpath);
        action.longPress(element, longTapDuration).perform();
    }

}
