package com.lufax.ui.auto.controller;

import com.lufax.ui.auto.anotations.CaseExecutor;
import com.lufax.ui.auto.anotations.StepExecutor;
import com.lufax.ui.auto.anotations.SuiteExecutor;
import com.lufax.ui.auto.caseobj.*;
import com.lufax.ui.auto.components.PropertiesCenter;
import com.lufax.ui.auto.pageobj.BaseMobilePage;
import com.lufax.ui.auto.services.CaseParserService;
import com.lufax.ui.auto.services.DriverGeneratorService;
import io.appium.java_client.AppiumDriver;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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


    /*
    所有case执行过程中只能有一个AppiumDriver实例
     */
    public ExecutorEngine init() throws IOException {
        oprDriver = driverGeneratorService.setLuCapabilities().getAppiumDriver();
        oprDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        osType = propertiesCenter.init().getRunConfigs().get("mobile.os.type");
        return this;
    }

    /*
    用例执行入口函数
     */
    public void execute() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, NotFoundException, InvocationTargetException {
        TestSuite testSuite = caseParserService.suiteParse();
        LinkedList<Cases> casesList = testSuite.getCasesList();
        for(Iterator<Cases> it = casesList.iterator();it.hasNext();){
            Cases cases = it.next();
            boolean isExecBeforeTest = cases.isExecBeforeTest();
            boolean isExecAfterTest = cases.isExecAfterTest();
            if(isExecBeforeTest == true){
                BeforeTestCases beforeTestCases = cases.getBeforeTestCases();
                executeBaseTestCases(beforeTestCases);  //执行测试前用例集
            }
            TestCases testCases = cases.getTestCases();
            executeBaseTestCases(testCases);  //执行测试用例集
            if(isExecAfterTest == true){
                AfterTestCases afterTestCases = cases.getAfterTestCases();
                executeBaseTestCases(afterTestCases);   //执行测试后用例集
            }
        }
    }

    /*
    执行基础测试用例集BeforeTestCases、BaseTestCases和AfterTestCases的函数
     */
    @SuiteExecutor(description = "执行用例集")
    public void executeBaseTestCases(BaseTestCases baseTestCases) throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException, NotFoundException, InvocationTargetException {
        boolean isPreserveOrder = baseTestCases.isPreserveOrder();
        LinkedList<Case> caseList = baseTestCases.getCaseList();
        if(isPreserveOrder == false){
            Collections.sort(caseList);  //preserve_order为false时，按照用例id升序执行
        }
        for (Iterator<Case> it = caseList.iterator();it.hasNext();){
            Case aCase = it.next();
            String casePriority = aCase.getPriority();
            String priorityToRun = propertiesCenter.init().getRunConfigs().get("run.for.case.priority");
            if(casePriority.equals(priorityToRun) || priorityToRun.equals("0")) {
                executeCase(aCase, isPreserveOrder); //执行一条用例
            }
        }
    }

    /*
    执行用例函数
     */
    @CaseExecutor(description = "执行用例")
    public void executeCase(Case aCase, boolean isPreserveOrder) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, NotFoundException, InvocationTargetException {
        LinkedList<Step> stepList = aCase.getSteps();
        if(isPreserveOrder == false){
            Collections.sort(stepList);  //preserve_order为false时，按照步骤id升序执行
        }
        for (Iterator<Step> it = stepList.iterator();it.hasNext();){
            Step step = it.next();
            executeStep(step);  //执行用例步骤
        }
    }

    /*
    执行用例步骤函数
     */
    @StepExecutor(description = "执行用例步骤")
    public void executeStep(Step step) throws ClassNotFoundException, IllegalAccessException, InstantiationException, MalformedURLException, NoSuchMethodException, NotFoundException, InvocationTargetException {
        Class clazz = Class.forName(fullClassName(step.getSrcPageName()));
        BaseMobilePage srcPage = ((BaseMobilePage) clazz.newInstance()).bindToDriver(oprDriver);
        String oprMethod = step.getMethod();
        LinkedList<MethodParam> methodParams = step.getMethodParams();
        Method method = filterMethod(clazz, oprMethod);
        LinkedList<Object> paramValues = getParameterValues(method,methodParams);
        method.invoke(srcPage,paramValues.toArray());
    }

    /*
    生成全类名
     */
    public String fullClassName(String className){
        return StringUtils.join(new String[]{this.pageObjsPackage, className},".");
    }

    /*
    获取方法
     */
    public Method filterMethod(Class clazz, String oprMethod){
        Method[] methods = clazz.getMethods();
        for (Method method:methods){
            String methodName = method.getName();
            if (oprMethod.equals(methodName)){
                return method;
            }
        }
        return null;
    }


    public LinkedList<Object> getParameterValues(Method method, LinkedList<MethodParam> methodParams) throws NotFoundException {
        LinkedList<Object> parameterValues = new LinkedList<Object>();
        Class<?>[] paramTypes = method.getParameterTypes();
        LinkedList<String> paramStrValues = getParameterStrValues(method, methodParams);
        int paramNum = paramStrValues.size();
        for(int i=0;i<paramNum;i++){
            Class<?> paramType = paramTypes[i];
            String paramStrValue = paramStrValues.get(i);
            if(paramType == Integer.class){
                Integer paramValue = Integer.parseInt(paramStrValue);
                parameterValues.add(paramValue);
            }else if(paramType == int.class){
                int paramValue = Integer.parseInt(paramStrValue);
                parameterValues.add(paramValue);
            } else {
                String paramValue = paramStrValue;
                parameterValues.add(paramValue);
            }
        }
        return parameterValues;
    }


    public LinkedList<String> getParameterStrValues(Method method, LinkedList<MethodParam> methodParams) throws NotFoundException {
        LinkedList<String> paramStrValues = new LinkedList<String>();
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(method.getDeclaringClass().getName());
        CtMethod cm = cc.getDeclaredMethod(method.getName());
        MethodInfo mehtodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = mehtodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if(attr == null){
            return null;
        }
        String[] paramNames = new String[method.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers())?0:1;
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + pos);
        }
        for (int i=0;i<paramNames.length;i++){
            for(int j=0;j<methodParams.size();j++){
                MethodParam methodParam = methodParams.get(j);
                String methodParamName = methodParam.getName();
                if(paramNames[i].equals(methodParamName)){
                    paramStrValues.add(methodParam.getValue());
                }
            }
        }
        return paramStrValues;
    }


}
