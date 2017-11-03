package ru.dskozin.resumeapp;

import ru.dskozin.resumeapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {

        Resume r = new Resume();
        //получаем поле класса
        Field field = r.getClass().getDeclaredFields()[0];
        //разрешаем доступ к нему
        field.setAccessible(true);
        System.out.println(field.getName());
        //получаем значение поля в объекте
        field.get(r);
        System.out.println(r);
        //меняем поле
        field.set(r, "dummy");
        //получаем метод класса и вызываем его
        Method method = r.getClass().getDeclaredMethod("toString", null);
        System.out.println(method.invoke(r, null));
    }
}
