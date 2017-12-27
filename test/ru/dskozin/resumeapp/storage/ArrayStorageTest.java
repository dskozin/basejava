package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new ArrayStorage();
    }

}