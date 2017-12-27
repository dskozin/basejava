package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class MapResumeStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new MapResumeStorage();
    }
}
