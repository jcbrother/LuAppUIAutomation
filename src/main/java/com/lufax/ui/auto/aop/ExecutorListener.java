package com.lufax.ui.auto.aop;

import com.lufax.ui.auto.assertions.AssertUtils;
import com.lufax.ui.auto.caseobj.AssertKey;
import com.lufax.ui.auto.caseobj.Step;
import com.lufax.ui.auto.components.LocatorUtils;
import com.lufax.ui.auto.services.ReportingService;
import com.lufax.ui.auto.services.SnapshootService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        boolean assertResult;
        for(AssertKey assertKey : assertKeys){
            assertResult = assertUtils.assertKey(assertKey);
            if(assertResult == false){
                result.setStepResultPass(false);
            }
        }
        return result;
    }


    @Around("executeCase()")
    public boolean caseExecutionListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Boolean result = (Boolean) proceedingJoinPoint.proceed();
        return result;
    }

    @After("executeSuite()")
    public void suiteExecutionListener(JoinPoint joinPoint){
        //生成报告
        System.out.println("gen report");
    }

    @Around("onFailureOpr()")
    public void oprAfterFailure(){
        //截图
    }

}
