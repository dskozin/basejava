package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class ListStorageTest extends AbstractArrayStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new ListStorage();
    }
}
