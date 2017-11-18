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

public class MapResumeStorageTest extends MapStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new MapResumeStorage();
    }

    //проверка на обновление стоража (здесь друге данные потому что другой ключ)
    @Override
    @Test
    public void update() throws Exception {

        //здесь пересекаются ключи. Должен быть и неизменный ююид
        //Можно обновить имя...
        Resume resume = new Resume(NAME_2, UUID_2);

        //устанавливаем новое значение элемента
        storage.update(resume);

        //элементы должны быть равны
        assertEquals(storage.get(UUID_2), resume);
    }
}
