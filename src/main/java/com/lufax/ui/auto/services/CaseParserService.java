package com.lufax.ui.auto.services;

import com.lufax.ui.auto.caseobj.*;
import com.lufax.ui.auto.components.PropertiesCenter;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Jc on 16/9/10.
 */

//@Service
public class CaseParserService {

    @Autowired
    public PropertiesCenter propertiesCenter;

    public String suiteFileDir = System.getProperty("user.dir") + String.format("%scases-suite", File.separator);


    public String getSuiteFile() throws IOException {
        String suiteFile = propertiesCenter.init().getRunConfigs().get("cases.suite");
        return String.format("%s%s%s",suiteFileDir, File.separator, suiteFile);
    }

    public TestSuite suiteParse() throws IOException {
        File suiteFile = new File(this.getSuiteFile());
        SAXReader saxReader = new SAXReader();
        Document document = null;
        Element suiteElem = null;
        try {
            document = saxReader.read(suiteFile);
            suiteElem = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return (TestSuite) this.elementWrap(suiteElem);
        }


    public BaseSuiteElementObject elementWrap(Element element){
        if(element != null){
            String elemName = element.getName();
            if("suite".equalsIgnoreCase(elemName)) {
                TestSuite testSuite = new TestSuite();
                this.setPropertyFields(element,testSuite);
                LinkedList<Cases> casesLinkedList = new LinkedList<Cases>();
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    casesLinkedList.add((Cases) elementWrap(subElement));
                }
                if(casesLinkedList.size()>0){
                    testSuite.setCasesList(casesLinkedList);
                }
                return testSuite;
            } else if("cases".equalsIgnoreCase(elemName)){
                Cases cases = new Cases();
                this.setPropertyFields(element,cases);
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    String subElemName = subElement.getName();
                    if("before-test-cases".equalsIgnoreCase(subElemName)){
                        cases.setBeforeTestCases((BeforeTestCases) elementWrap(subElement));
                    } else if ("test-cases".equalsIgnoreCase(subElemName)){
                        cases.setTestCases((TestCases) elementWrap(subElement));
                    } else if ("after-test-cases".equalsIgnoreCase(subElemName)){
                        cases.setAfterTestCases((AfterTestCases) elementWrap(subElement));
                    }
                }
                return cases;
            } else if("before-test-cases".equalsIgnoreCase(elemName)){
                BeforeTestCases beforeTestCases = new BeforeTestCases();
                this.setPropertyFields(element,beforeTestCases);
                LinkedList<Case> caseLinkedList = new LinkedList<Case>();
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    caseLinkedList.add((Case) elementWrap(subElement));
                }
                if(caseLinkedList.size()>0){
                    beforeTestCases.setCaseList(caseLinkedList);
                }
                return beforeTestCases;
            } else if("test-cases".equalsIgnoreCase(elemName)){
                TestCases testCases = new TestCases();
                this.setPropertyFields(element,testCases);
                LinkedList<Case> caseLinkedList = new LinkedList<Case>();
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    caseLinkedList.add((Case) elementWrap(subElement));
                }
                if(caseLinkedList.size()>0){
                    testCases.setCaseList(caseLinkedList);
                }
                return testCases;
            } else if("after-test-cases".equalsIgnoreCase(elemName)){
                AfterTestCases afterTestCases = new AfterTestCases();
                this.setPropertyFields(element,afterTestCases);
                LinkedList<Case> caseLinkedList = new LinkedList<Case>();
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    caseLinkedList.add((Case) elementWrap(subElement));
                }
                if(caseLinkedList.size()>0){
                    afterTestCases.setCaseList(caseLinkedList);
                }
                return afterTestCases;
            } else if("case".equalsIgnoreCase(elemName)){
                Case aCase = new Case();
                this.setPropertyFields(element,aCase);
                LinkedList<Step> stepsLinkedList = new LinkedList<Step>();
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    stepsLinkedList.add((Step) elementWrap(subElement));
                }
                if(stepsLinkedList.size()>0){
                    aCase.setSteps(stepsLinkedList);
                }
                return aCase;
            } else if("step".equalsIgnoreCase(elemName)){
                Step step = new Step();
                this.setPropertyFields(element,step);
                LinkedList<MethodParam> methodParamLinkedList = new LinkedList<MethodParam>();
                LinkedList<AssertKey> assertKeyLinkedList = new LinkedList<AssertKey>();
                for(Iterator it=element.elementIterator();it.hasNext();){
                    Element subElement = (Element) it.next();
                    String subElemName = subElement.getName();
                    if("method-param".equalsIgnoreCase(subElemName)){
                        methodParamLinkedList.add((MethodParam) elementWrap(subElement));
                    } else if("assert-key".equalsIgnoreCase(subElemName)){
                        assertKeyLinkedList.add((AssertKey) elementWrap(subElement));
                    }
                }
                if(methodParamLinkedList.size()>0){
                    step.setMethodParams(methodParamLinkedList);
                }
                if(assertKeyLinkedList.size()>0){
                    step.setAssertKeys(assertKeyLinkedList);
                }
                return step;
            } else if("method-param".equalsIgnoreCase(elemName)) {
                MethodParam methodParam = new MethodParam();
                this.setPropertyFields(element, methodParam);
                return methodParam;
            } else if("assert-key".equalsIgnoreCase(elemName)){
                AssertKey assertKey = new AssertKey();
                this.setPropertyFields(element, assertKey);
                return assertKey;
            }
        }
        return null;
    }

    public void setPropertyFields(Element element, BaseSuiteElementObject elementObject) {
        String elemName = element.getName();
        for (Iterator it = element.attributeIterator(); it.hasNext(); ) {
            Attribute attribute = (Attribute) it.next();
            String attrName = attribute.getName();
            String attrValue = attribute.getValue();
            if ("suite".equalsIgnoreCase(elemName)) {
                if ("name".equalsIgnoreCase(attrName)) {
                    ((TestSuite) elementObject).setName(attrValue);
                } else if ("description".equalsIgnoreCase(attrName)) {
                    ((TestSuite) elementObject).setName(attrValue);
                }
            } else if ("cases".equalsIgnoreCase(elemName)) {
                if ("exec-before-test".equalsIgnoreCase(attrName)) {
                    ((Cases) elementObject).setIsExecBeforeTest(Boolean.parseBoolean(attrValue));
                } else if ("exec-after-test".equalsIgnoreCase(attrName)) {
                    ((Cases) elementObject).setIsExecAfterTest(Boolean.parseBoolean(attrValue));
                }
            } else if ("before-test-cases".equalsIgnoreCase(elemName) || "test-cases".equalsIgnoreCase(elemName) || "after-test-cases".equalsIgnoreCase(elemName)) {
                if ("purpose".equalsIgnoreCase(attrName)) {
                    ((BeforeTestCases) elementObject).setPurpose(attrValue);
                } else if ("preserve-order".equalsIgnoreCase(attrName)) {
                    ((BeforeTestCases) elementObject).setIsPreserveOrder(Boolean.parseBoolean(attrValue));
                }
            } else if ("case".equalsIgnoreCase(elemName)) {
                if ("id".equalsIgnoreCase(attrName)) {
                    ((Case) elementObject).setId(Integer.parseInt(attrValue));
                } else if ("title".equalsIgnoreCase(attrName)) {
                    ((Case) elementObject).setTitle(attrValue);
                } else if ("priority".equalsIgnoreCase(attrName)) {
                    ((Case) elementObject).setPriority(attrValue);
                }
            } else if ("step".equalsIgnoreCase(elemName)) {
                if ("id".equalsIgnoreCase(attrName)) {
                    ((Step) elementObject).setId(Integer.parseInt(attrValue));
                } else if ("src-page-name".equalsIgnoreCase(attrName)) {
                    ((Step) elementObject).setSrcPageName(attrValue);
                } else if ("method".equalsIgnoreCase(attrName)) {
                    ((Step) elementObject).setMethod(attrValue);
                } else if ("dest-page-name".equalsIgnoreCase(attrName)) {
                    ((Step) elementObject).setDestPageName(attrValue);
                } else if ("snapshoot".equalsIgnoreCase(attrName)) {
                    ((Step) elementObject).setSnapshoot(Boolean.parseBoolean(attrValue));
                } else if ("step-desc".equalsIgnoreCase(attrName)) {
                    ((Step) elementObject).setStepDesc(attrValue);
                }
            } else if ("method-param".equalsIgnoreCase(elemName)) {
                if ("name".equalsIgnoreCase(attrName)) {
                    ((MethodParam) elementObject).setName(attrValue);
                } else if ("type".equalsIgnoreCase(attrName)) {
                    ((MethodParam) elementObject).setType(attrValue);
                } else if ("value".equalsIgnoreCase(attrName)) {
                    ((MethodParam) elementObject).setValue(attrValue);
                }
            } else if ("assert-key".equalsIgnoreCase(elemName)) {
                if ("value".equalsIgnoreCase(attrName)) {
                    ((AssertKey) elementObject).setValue(attrValue);
                } else if ("location-type".equalsIgnoreCase(attrName)) {
                    ((AssertKey) elementObject).setLocationType(attrValue);
                } else if ("compare-value".equalsIgnoreCase(attrName)) {
                    ((AssertKey) elementObject).setCompareValue(attrValue);
                }
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir") + String.format("%scases-suite", File.separator));
    }





}
