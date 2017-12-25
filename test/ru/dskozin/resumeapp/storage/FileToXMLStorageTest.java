package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;
import ru.dskozin.resumeapp.storage.Serialization.XMLSerializationStrategy;

public class FileToXMLStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new FileStorage(STORAGE_DIR, new XMLSerializationStrategy());
    }
}
