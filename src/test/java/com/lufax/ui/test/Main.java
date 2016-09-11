package com.lufax.ui.test;

import org.apache.log4j.Logger;

/**
 * Created by Jc on 16/8/19.
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);

    public void print(){
        logger.debug("hello world.");
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.print();
    }
}
