package com.lufax.ui.auto.pagesobj;

import com.lufax.ui.auto.interfaces.PageActions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebElement;

/**
 * Created by Jc on 16/8/7.
 */

public class BaseMobilePage implements PageActions{

    private String currentPageName = "";
    private AppiumDriver driver = null;
    private int pageWidth = 0;
    private int pageHeight = 0;
    private int pageSwipeDuration = 1000;
    private int longTapDuration = 2000;


    //设置页面对象属性
    public BaseMobilePage setCurrentPageName(String currentPageName) {
        this.currentPageName = currentPageName;
        return this;
    }

    public BaseMobilePage setDriver(AppiumDriver driver) {
        this.driver = driver;
        return this;
    }

    public BaseMobilePage setPageSize(){
        this.pageWidth = driver.manage().window().getSize().width;
        this.pageHeight = driver.manage().window().getSize().height;
        return this;
    }

    //获取页面对象属性
    public WebElement getElementById(String byId){
        return driver.findElementById(byId);
    }

    public WebElement getElementByXpath(String xpath){
        return driver.findElementByXPath(xpath);
    }

    public AppiumDriver getDriver(){
        return driver;
    }

    public String getCurrentPageName(){
        return currentPageName;
    }

    public int getPageWidth(){
        return pageWidth;
    }

    public int getPageHeight(){
        return pageHeight;
    }

    //重写页面对象方法
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
        driver.swipe(pageWidth*4/5, pageHeight/2, pageWidth/5, pageHeight/2, pageSwipeDuration);
    }

    @Override
    public void swipeRight() {
        driver.swipe(pageWidth/5, pageHeight/2, pageWidth*4/5, pageHeight/2, pageSwipeDuration);
    }

    @Override
    public void swipeForBack() {
        driver.swipe(0, pageHeight/2, pageWidth*4/5, pageHeight/2, pageSwipeDuration);
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
