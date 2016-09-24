package com.lufax.ui.auto.controller;

import com.lufax.ui.auto.caseobj.*;
import com.lufax.ui.auto.components.PropertiesCenter;
import com.lufax.ui.auto.services.CaseParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jc on 16/9/10.
 */

@Controller
public class ExecutorEngine {

    @Autowired
    public CaseParserService caseParserService;

    @Autowired
    public PropertiesCenter propertiesCenter;

    public void execute() throws IOException {
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

    public void executeBaseTestCases(BaseTestCases baseTestCases) throws IOException {
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

    public void executeCase(Case aCase, boolean isPreserveOrder) throws IOException {
        LinkedList<Step> stepList = aCase.getSteps();
        if(isPreserveOrder = false){
            Collections.sort(stepList);
        }
        for (Iterator<Step> it = stepList.iterator();it.hasNext();){
            Step step = it.next();
            executeStep(step);
        }
    }

    public void executeStep(Step step){

    }

}
