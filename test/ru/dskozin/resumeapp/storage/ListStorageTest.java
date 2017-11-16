package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class ListStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new ListStorage();
    }
}
