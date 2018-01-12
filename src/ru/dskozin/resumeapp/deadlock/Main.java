package ru.dskozin.resumeapp.deadlock;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int TIME_TO_EAT = 50;
    public static final int TIME_TO_CHAT = 10;
    public static final int PHILOSOPHER_NUMBER = 3;

    public static void main(String[] args) {

        List<Philosopher> philosopherList = new ArrayList<>();

        //создаем философов и раздаем им вилки
        for(int i = 0; i < PHILOSOPHER_NUMBER; i++){
            philosopherList.add(new Philosopher("" + i));
        }

        //раздаем им вилки
        Fork leftHand = new Fork();
        Fork rightHand = new Fork();
        Fork tmp = leftHand;
        for (Philosopher philosopher : philosopherList){
            philosopher.setLeftHand(leftHand);
            philosopher.setRightHand(rightHand);
            leftHand = rightHand;
            rightHand = new Fork();
        }

        //и последнему вручим вилку первого
        philosopherList.get(philosopherList.size() - 1).setRightHand(tmp);

        //запускаем и ждем блокировки
        for (Philosopher philosopher : philosopherList){
            new Thread(philosopher).start();
        }

        while (true){
            //пока не заблокируется...
        }

    }
}
