package com.lufax.ui.test;

import org.testng.annotations.Test;

/**
 * Created by Jc on 16/8/19.
 */
public class Test01 {

    @Test(groups = {"Test01.testMethod01"}, testName = "test01.method01")
    public void testMethod01(){
        System.out.println("[Test01]# this is test method 01...");
    }

    @Test(groups = {"Test01.testMethod02"}, testName = "test01.method02")
    public void testMethod02(){
        System.out.println("[Test01]# this is test method 02...");
    }

}
