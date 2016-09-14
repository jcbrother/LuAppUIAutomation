package com.lufax.ui.auto.services;

import com.lufax.ui.auto.caseobj.BaseSuiteElementObject;
import com.lufax.ui.auto.caseobj.TestSuite;
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

/**
 * Created by Jc on 16/9/10.
 */

//@Service
public class CaseParserService {

    @Autowired
    public PropertiesCenter propertiesCenter;

    public String suiteFileDir = System.getProperty("user.dir") + String.format("%scases-suite", File.separator);


    public String getSuiteFile() throws IOException {
        String suiteFile = propertiesCenter.runConfigs.get("cases.suite");
        return String.format("%s%s%s",suiteFileDir, File.separator, suiteFile);
    }

    public void suiteParse() throws IOException {
        File suiteFile = new File(this.getSuiteFile());
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(suiteFile);
            Element suiteElem = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {

        }

    }

    public TestSuite suiteWrap(Element suiteElem){
        TestSuite testSuite = new TestSuite();
        for(Iterator it=suiteElem.attributeIterator();it.hasNext();) {
            Attribute attribute = (Attribute) it.next();
            String text = attribute.getText();
            System.out.println(text);
        }
        return null;
    }

    public BaseSuiteElementObject suiteElementWrap(){

        return null;
    }



    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir") + String.format("%scases-suite", File.separator));
    }





}
