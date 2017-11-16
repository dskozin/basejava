package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class MapStorageTest extends AbstractStorageTest {

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new MapStorage();
    }
}
