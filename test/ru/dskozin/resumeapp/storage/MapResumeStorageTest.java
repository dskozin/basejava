package ru.dskozin.resumeapp.storage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapResumeStorageTest extends AbstractStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new MapResumeStorage();
    }
}
