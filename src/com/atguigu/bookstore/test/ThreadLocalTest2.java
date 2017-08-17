package com.atguigu.bookstore.test;

/**
 * Created by lenovo on 2017/8/14.
 */
public class ThreadLocalTest2 implements Runnable {

    ThreadLocal<String> threadLocal = new ThreadLocal<>();

    int i = 0;

    @Override
    public void run() {
        for(;i < 10;i++){
            threadLocal.set(Thread.currentThread().getName());
            try{
                Thread.sleep(10);
            } catch(Exception e){}

            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
        }
    }

    public static void main(String[] args){
        ThreadLocalTest tlt = new ThreadLocalTest();

        new Thread(tlt,"AAA").start();
        new Thread(tlt,"BBB").start();
    }
}
