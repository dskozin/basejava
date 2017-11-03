package ru.dskozin.resumeapp.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new ArrayStorage();
    }

}