package com.lufax.ui.auto.caseobj;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * Created by Jc on 16/9/11.
 */

@Component
public class BaseTestCases {

    public boolean isExecBeforeTest;
    public boolean isExecAfterTest;
    public LinkedList<Case> caseList;

    public boolean isExecBeforeTest() {
        return isExecBeforeTest;
    }

    public BaseTestCases setIsExecBeforeTest(boolean isExecBeforeTest) {
        this.isExecBeforeTest = isExecBeforeTest;
        return this;
    }

    public boolean isExecAfterTest() {
        return isExecAfterTest;
    }

    public BaseTestCases setIsExecAfterTest(boolean isExecAfterTest) {
        this.isExecAfterTest = isExecAfterTest;
        return this;
    }

    public LinkedList<Case> getCaseList() {
        return caseList;
    }

    public BaseTestCases setCaseList(LinkedList<Case> caseList) {
        this.caseList = caseList;
        return this;
    }
}
