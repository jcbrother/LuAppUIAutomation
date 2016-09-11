package com.lufax.ui.auto.caseobj;

import org.springframework.stereotype.Component;

/**
 * Created by Jc on 16/9/11.
 */

@Component
public class TestSuite {

    public String name;
    public String description;
    public AfterTestCases afterTestCases;
    public BeforeTestCases beforeTestCases;
    public TestCases testCases;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AfterTestCases getAfterTestCases() {
        return afterTestCases;
    }

    public void setAfterTestCases(AfterTestCases afterTestCases) {
        this.afterTestCases = afterTestCases;
    }

    public BeforeTestCases getBeforeTestCases() {
        return beforeTestCases;
    }

    public void setBeforeTestCases(BeforeTestCases beforeTestCases) {
        this.beforeTestCases = beforeTestCases;
    }

    public TestCases getTestCases() {
        return testCases;
    }

    public void setTestCases(TestCases testCases) {
        this.testCases = testCases;
    }
}
