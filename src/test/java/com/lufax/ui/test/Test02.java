package com.lufax.ui.test;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;

/**
 * Created by Jc on 16/8/19.
 */
public class Test02 {

    @Test(dependsOnGroups = {"Test01.testMethod01"})
    public void testMethod01(){
        System.out.println("[Test02]# this is test method 01...");
    }

    @Test(dependsOnMethods = "testMethod01")
    public void testMethod02(){
        System.out.println("[Test02]# this is test method 02...");
    }


    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG tng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("c:/tests/testng1.xml");//path to xml..
        suites.add("c:/tests/testng2.xml");
        tng.setTestSuites(suites);
        tng.run();
    }
}
