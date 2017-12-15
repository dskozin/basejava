package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class PathStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new PathStorage(STORAGE_DIR, new ObjectSerialization());
    }
}
