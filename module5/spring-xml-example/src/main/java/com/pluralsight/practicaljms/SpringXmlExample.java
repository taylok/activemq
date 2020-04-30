package com.pluralsight.practicaljms;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class SpringXmlExample {

    public static void main(String args[]) {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        appContext.start();
    }

}