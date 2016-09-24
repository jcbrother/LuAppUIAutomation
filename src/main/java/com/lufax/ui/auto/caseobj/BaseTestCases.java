package com.lufax.ui.auto.caseobj;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * Created by Jc on 16/9/11.
 */

@Component
public class BaseTestCases extends BaseSuiteElementObject{

    private String purpose;
    private boolean isPreserveOrder;
    private LinkedList<Case> caseList;

    public String getPurpose() {
        return purpose;
    }

    public BaseTestCases setPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public boolean isPreserveOrder() {
        return isPreserveOrder;
    }

    public BaseTestCases setIsPreserveOrder(boolean isPreserveOrder) {
        this.isPreserveOrder = isPreserveOrder;
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
