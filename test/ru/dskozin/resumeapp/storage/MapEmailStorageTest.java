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

public class MapEmailStorageTest extends MapStorageTest{

    static final String EMAIL_1 = "v.zaitsev@mail.ru";
    static final String EMAIL_2 = "d.kozin@mail.ru";
    static final String EMAIL_3 = "e.khrapova@mail.ru";

    @BeforeClass
    public static void init(){
        //инициализовать сторадж
        storage = new MapEmailStorage();
    }

    //перед тестом мы наполняем стораж
    @Before
    public void setUp() throws Exception {
        storage.save(new Resume(NAME_1, UUID_1, EMAIL_1));
        storage.save(new Resume(NAME_2, UUID_2, EMAIL_2));
    }

    //проверка на обновление стоража (здесь друге данные потому что другой ключ)
    @Override
    @Test
    public void update() throws Exception {

        //здесь пересекаются ключи. Должен быть и неизменный ююид и емэйл
        //Можно обновить имя...
        Resume resume = new Resume(NAME_2, UUID_2, EMAIL_2);

        //устанавливаем новое значение элемента
        storage.update(resume);

        //элементы должны быть равны
        assertEquals(storage.get(UUID_2), resume);
    }

    //проверка получения массива элементов
    @Test
    public void getAll() throws Exception {

        List<Resume> arr;
        //должен быть получен [] из двух элементов
        assertTrue((arr = storage.getAllSorted()).size() == 2);

        //там лежат объекты с UUID_1 и UUID_2
        assertTrue(arr.containsAll(Arrays.asList(new Resume(NAME_1, UUID_1, EMAIL_1), new Resume(NAME_2, UUID_2, EMAIL_2))));
    }
    //Проверка получения сортированного значения
    //Поле получения значения должны идти UUID_2, потом UUID_1
    @Test
    public void getAllSorted() throws Exception {
        assertArrayEquals(new Resume[]{new Resume(NAME_2, UUID_2, EMAIL_2), new Resume(NAME_1, UUID_1, EMAIL_1)},
                storage.getAllSorted().toArray(new Resume[storage.size()]));
    }

    //проверка получения конкретного элемента
    @Test
    public void get() throws Exception {
        assertEquals(storage.get(UUID_1), new Resume(NAME_1, UUID_1, EMAIL_1));
        assertEquals(storage.get(UUID_2), new Resume(NAME_2, UUID_2, EMAIL_2));
    }


}
