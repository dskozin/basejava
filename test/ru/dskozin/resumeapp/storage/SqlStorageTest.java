package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;
import ru.dskozin.resumeapp.Config;

public class SqlStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new SqlStorage(
                Config.getInstance().getDBUrl(),
                Config.getInstance().getDBUser(),
                Config.getInstance().getDBPassword());
    }
}
