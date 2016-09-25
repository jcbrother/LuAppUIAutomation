package com.lufax.ui.auto.controller;

import com.lufax.ui.auto.caseobj.*;
import com.lufax.ui.auto.components.PropertiesCenter;
import com.lufax.ui.auto.pageobj.BaseMobilePage;
import com.lufax.ui.auto.services.CaseParserService;
import com.lufax.ui.auto.services.DriverGeneratorService;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jc on 16/9/10.
 */

@Controller
public class ExecutorEngine {

    @Autowired
    private CaseParserService caseParserService;
    @Autowired
    private PropertiesCenter propertiesCenter;
    @Autowired
    private DriverGeneratorService driverGeneratorService;

    private AppiumDriver oprDriver;
    private String osType;
    private String pageObjsPackage = "com.lufax.ui.auto.pageobj";

    //所有case执行过程中只能有一个AppiumDriver实例
    public ExecutorEngine init() throws IOException {
        oprDriver = driverGeneratorService.getAppiumDriver();
        osType = propertiesCenter.init().getRunConfigs().get("mobile.os.type");
        return this;
    }


    public void execute() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
        TestSuite testSuite = caseParserService.suiteParse();
        LinkedList<Cases> casesList = testSuite.getCasesList();
        for(Iterator<Cases> it = casesList.iterator();it.hasNext();){
            Cases cases = it.next();
            boolean isExecBeforeTest = cases.isExecBeforeTest();
            boolean isExecAfterTest = cases.isExecAfterTest();
            if(isExecBeforeTest == true){
                BeforeTestCases beforeTestCases = cases.getBeforeTestCases();
                executeBaseTestCases(beforeTestCases);
            }
            TestCases testCases = cases.getTestCases();
            executeBaseTestCases(testCases);
            if(isExecAfterTest == true){
                AfterTestCases afterTestCases = cases.getAfterTestCases();
                executeBaseTestCases(afterTestCases);
            }
        }

    }

    public void executeBaseTestCases(BaseTestCases baseTestCases) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException {
        boolean isPreserveOrder = baseTestCases.isPreserveOrder();
        LinkedList<Case> caseList = baseTestCases.getCaseList();
        if(isPreserveOrder = false){
            Collections.sort(caseList);
        }
        for (Iterator<Case> it = caseList.iterator();it.hasNext();){
            Case aCase = it.next();
            String casePriority = aCase.getPriority();
            String priorityToRun = propertiesCenter.init().getRunConfigs().get("run.for.case.priority");
            if(casePriority.equals(priorityToRun)) {
                executeCase(aCase, isPreserveOrder);
            }
        }
    }

    public void executeCase(Case aCase, boolean isPreserveOrder) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
        LinkedList<Step> stepList = aCase.getSteps();
        if(isPreserveOrder = false){
            Collections.sort(stepList);
        }
        for (Iterator<Step> it = stepList.iterator();it.hasNext();){
            Step step = it.next();
            executeStep(step);
        }
    }

    public void executeStep(Step step) throws ClassNotFoundException, IllegalAccessException, InstantiationException, MalformedURLException, NoSuchMethodException {
        Class clazz = Class.forName(fullClassName(step.getSrcPageName()));
        BaseMobilePage srcPage = ((BaseMobilePage) clazz.newInstance()).bindToDriver(oprDriver);
        String oprMethod = step.getMethod();
        Method method = clazz.getMethod(oprMethod);
        LinkedList<MethodParam> methodParams = step.getMethodParams();


    }

    public String fullClassName(String className){
        return StringUtils.join(new String[]{this.pageObjsPackage, className},".");
    }

}
