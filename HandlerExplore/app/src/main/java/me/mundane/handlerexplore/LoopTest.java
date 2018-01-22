package me.mundane.handlerexplore;

/**
 * Created by mundane on 2018/1/20 下午4:09
 */

public class LoopTest {
    public static void main(String[] args) {
        loop();
        System.out.println("跳出循环2");
    }

    private static void loop() {
        for (;;) {
            System.out.println("正在循环");
            if (true) {
                System.out.println("跳出循环1");
                return;
            }
        }
    }
}
