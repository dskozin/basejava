package ru.dskozin.resumeapp.storage;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.dskozin.resumeapp.model.Resume;

import static org.junit.Assert.assertEquals;

public class MapNameStorageTest extends MapStorageTest{

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new MapNameStorage();
    }

    //проверка на обновление стоража (здесь друге данные потому что другой ключ)
    @Override
    @Test
    public void update() throws Exception {

        //здесь пересекаются ключи. Должен быть и неизменный ююид и имя.
        // Поэтому проверка символичная...
        Resume resume = new Resume(UUID_2, NAME_2);

        //устанавливаем новое значение элемента
        storage.update(resume);

        //элементы должны быть равны
        assertEquals(storage.get(UUID_2), resume);
    }

}
