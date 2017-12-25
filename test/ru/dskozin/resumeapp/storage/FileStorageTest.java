package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;

public class FileStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new FileStorage(STORAGE_DIR);
    }
}
