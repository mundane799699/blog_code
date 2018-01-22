package me.mundane.handlerexplore;


/**
 * Created by mundane on 2018/1/19 下午4:20
 */

public class ThreadLocalTest {

    private static ThreadLocal<Boolean> sBooleanThreadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.println("[Thread#main]sBooleanThreadLocal = " + sBooleanThreadLocal.get());
        sBooleanThreadLocal.set(true);
        System.out.println("[Thread#main]sBooleanThreadLocal = " + sBooleanThreadLocal.get());
        new Thread("Thread#1") {
            @Override
            public void run() {
                sBooleanThreadLocal.set(false);
                System.out.println("[Thread#1]sBooleanThreadLocal = " + sBooleanThreadLocal.get());
            }
        }.start();
        new Thread("Thread#2") {
            @Override
            public void run() {
                sBooleanThreadLocal.set(true);
                System.out.println("[Thread#2]sBooleanThreadLocal = " + sBooleanThreadLocal.get());
            }
        }.start();
    }
}
