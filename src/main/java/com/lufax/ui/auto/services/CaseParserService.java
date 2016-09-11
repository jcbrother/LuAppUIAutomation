package com.lufax.ui.auto.services;

import com.lufax.ui.auto.components.PropertiesCenter;
import com.lufax.ui.auto.components.PropertiesFetcher;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Jc on 16/9/10.
 */

//@Service
public class CaseParserService {

    @Autowired
    public PropertiesCenter propertiesCenter;

    public String suiteFileDir = System.getProperty("user.dir") + String.format("%pagesobj-suite", File.separator);


    public String getSuiteFile() throws IOException {
        String suiteFile = propertiesCenter.runConfigs.get("cases.suite");
        return String.format("%s%s%s",suiteFileDir, File.separator, suiteFile);
    }

    public void suiteParse() throws IOException {
        File suiteFile = new File(this.getSuiteFile());
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(suiteFile);
            Element root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {

        }

    }


    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir") + String.format("%scases-suite", File.separator));
    }





}
