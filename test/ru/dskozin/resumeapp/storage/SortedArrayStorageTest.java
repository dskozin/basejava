package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new SortedArrayStorage();
    }

}