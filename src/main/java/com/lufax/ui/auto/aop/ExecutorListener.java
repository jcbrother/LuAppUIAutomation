package com.lufax.ui.auto.aop;

import com.lufax.ui.auto.assertions.AssertUtils;
import com.lufax.ui.auto.caseobj.AssertKey;
import com.lufax.ui.auto.caseobj.Case;
import com.lufax.ui.auto.caseobj.Cases;
import com.lufax.ui.auto.caseobj.Step;
import com.lufax.ui.auto.components.LocatorUtils;
import com.lufax.ui.auto.controller.ExecutorEngine;
import com.lufax.ui.auto.services.ReportingService;
import com.lufax.ui.auto.services.SnapshootService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jc on 16/10/15.
 */

@Aspect
@Component
public class ExecutorListener {

    @Autowired
    public AssertUtils assertUtils;
    @Autowired
    public ReportingService reportingService;
    @Autowired
    public SnapshootService snapshootService;
    @Autowired
    public ExecutorEngine executorEngine;


    @Pointcut("@annotation(com.lufax.ui.auto.anotations.SuiteExecutor)")
    public void executeSuite(){}

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.CaseExecutor)")
    public void executeCase(){}

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.StepExecutor)")
    public void executeStep(){}

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.UIAssertion)")
    public void onFailureOpr(){}


    @Around("executeStep()")
    public Step stepExecutionListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Step result = (Step) proceedingJoinPoint.proceed();
        LinkedList<AssertKey> assertKeys = result.getAssertKeys();
        boolean assertResult = true;
        for(AssertKey assertKey : assertKeys){
            assertResult = assertUtils.assertKey(assertKey);
            if(assertResult == false){
                result.setStepResultPass(false);
                break;
            }
        }
        reportingService.addDetailStep(String.valueOf(result.getId()), result.getStepDesc());
        String resultStyleType = assertResult ? reportingService.testResultStyleType.get("PASS"):reportingService.testResultStyleType.get("FAIL");
        reportingService.addTestResultStep(String.valueOf(result.getId()), result.getExpectResult(),resultStyleType,String.valueOf(assertResult));
        return result;
    }


    @Around("executeCase()")
    public boolean caseExecutionListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        Case aCase = (Case) args[0];
        Boolean result = (Boolean) proceedingJoinPoint.proceed();
        reportingService.addCaseTitle(String.valueOf(aCase.getId()),aCase.getTitle());
        return result;
    }

    @After("executeSuite()")
    public void suiteExecutionListener(JoinPoint joinPoint) throws IOException, DocumentException {
        //生成报告
        System.out.println("generate report");
        Integer countPass = 0;
        Integer countFail = 0;
        LinkedList<Cases> casesList = executorEngine.getTestSuite().getCasesList();
        for(Cases cases:casesList){
            countPass += cases.getBeforeTestCases().countPass() + cases.getTestCases().countPass() + cases.getAfterTestCases().countPass();
            countFail += cases.getBeforeTestCases().countFail() + cases.getTestCases().countFail() + cases.getAfterTestCases().countFail();
        }
        reportingService.generateReport(executorEngine.getTestSuite().getName(),countPass,countFail,0);
    }

    @Around("onFailureOpr()")
    public void oprAfterFailure(){
        //截图
    }

}
