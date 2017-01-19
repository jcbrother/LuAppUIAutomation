package com.lufax.ui.auto.aop;

import com.lufax.ui.auto.caseobj.Step;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by Jc on 16/10/15.
 */

@Component
@Aspect
public class ExecutorListener {

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.SuiteExecutor)")
    public void executeSuite(){}

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.CaseExecutor)")
    public void executeCase(){}

    @Pointcut("@annotation(com.lufax.ui.auto.anotations.StepExecutor)")
    public void executeStep(){}


    @Around("executeStep()")
    public Step stepExecutionListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        return (Step) result;
    }

    @Around("executeCase()")
    public boolean caseExecutionListener(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        return (Boolean) result;
    }

}
