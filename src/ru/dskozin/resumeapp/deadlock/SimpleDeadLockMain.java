package ru.dskozin.resumeapp.deadlock;

import java.util.concurrent.TimeUnit;

public class SimpleDeadLockMain {

    //будет счетчик попыток блокировки
    private volatile static int COUNTER = 0;

    public static void main(String[] args) {

        //создаем ресурсы для взаимной блокировки
        Object resource1 = new Object();
        Object resource2 = new Object();

        //объявляем анонимные потоки. Один блокирует сначала ресурс1 потом ресурс2, второй наоборот
        //когда заблокируется перестанет выводится надпись и счетчик
        deadlock(resource2, resource1);
        deadlock(resource1, resource2);
    }

    private static void deadlock(Object resource1, Object resource2) {
        new Thread(() -> {
            while (true) {
                synchronized (resource2) {
                    synchronized (resource1) {
                        System.out.println("Blocking trying count:" + ++COUNTER);
                    }
                }
            }
        }).start();
    }
}
