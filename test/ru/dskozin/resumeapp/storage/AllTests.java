package ru.dskozin.resumeapp.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapResumeStorageTest.class,
        MapStorageTest.class,
        SortedArrayStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class,
        FileToJSONStorageTest.class,
        FileToXMLStorageTest.class,
        FileToDataStorageTest.class
})
public class AllTests {
}
