package com.treefinance.saas.management.console.web.controller;

/**
 * @author chengtong
 * @date 18/5/18 17:01
 */
public class MainClass {



    public static void main(String...args){

        ClassLoader classLoader = MainClass.class.getClassLoader();

        System.err.println(classLoader);
        System.err.println(classLoader.getParent());
        System.err.println(classLoader.getParent().getParent());

    }

}
