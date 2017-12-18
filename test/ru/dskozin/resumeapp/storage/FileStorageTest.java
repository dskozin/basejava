package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;
import ru.dskozin.resumeapp.storage.Serialization.ObjectSerialization;

public class FileStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new FileStorage(STORAGE_DIR, new ObjectSerialization());
    }
}
