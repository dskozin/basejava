package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class ObjectStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new ObjectStreamStorage(STORAGE_DIR);
    }
}
