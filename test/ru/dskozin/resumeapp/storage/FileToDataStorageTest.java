package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;
import ru.dskozin.resumeapp.storage.Serialization.DataSerializationStrategy;

public class FileToDataStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new FileStorage(STORAGE_DIR, new DataSerializationStrategy());
    }
}
