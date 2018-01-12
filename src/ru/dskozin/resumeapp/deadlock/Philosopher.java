package ru.dskozin.resumeapp.deadlock;

import java.util.Random;

public class Philosopher implements Runnable{
    private Fork leftHand;
    private Fork rightHand;
    private String name;

    Philosopher(String name){
        this.name = name;
    }

    public void setLeftHand(Fork leftHand) {
        this.leftHand = leftHand;
        System.out.println(name + " take " + leftHand + " on left");
    }

    public void setRightHand(Fork rightHand) {
        this.rightHand = rightHand;
        System.out.println(name + " take " + rightHand + " on right");
    }

    public void eat(int timeToEat) throws InterruptedException {
        leftHand.take();
        rightHand.take();
        System.out.println("Philosopher " + name + " start eating.");
        Thread.sleep(timeToEat);
        System.out.println("Philosopher " + name + " end eating.");
        leftHand.drop();
        rightHand.drop();
    }

    public void chat(int timeToChat) throws InterruptedException {
        Thread.sleep(timeToChat);
    }

    @Override
    public void run() {
        Random rand = new Random();
        System.out.println(name + " running");
        while (!Thread.interrupted()){
            try {
                this.chat(rand.nextInt(Main.TIME_TO_CHAT));
                this.eat(rand.nextInt(Main.TIME_TO_EAT));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
