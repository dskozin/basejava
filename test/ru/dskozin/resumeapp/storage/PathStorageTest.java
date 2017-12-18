package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;
import ru.dskozin.resumeapp.storage.Serialization.ObjectSerialization;

public class PathStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new PathStorage(STORAGE_DIR, new ObjectSerialization());
    }
}
