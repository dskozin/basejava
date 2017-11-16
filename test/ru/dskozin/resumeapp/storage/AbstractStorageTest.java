package ru.dskozin.resumeapp.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractStorageTest {

    static Storage storage;
    static final String UUID_1 = "uuid1";
    static final String NAME_1 = "Vasiliy Zaytsev";
    static final String UUID_2 = "uuid2";
    static final String NAME_2 = "Dmitriy Kozin";
    static final String UUID_3 = "uuid3";
    static final String NAME_3 = "ELena Khrapova";

    //перед каждым тестом мы наполняем стораж
    @Before
    public void setUp() throws Exception {
        storage.save(new Resume(UUID_1, NAME_1));
        storage.save(new Resume(UUID_2, NAME_2));
    }

    //после теста очищаем сторадж
    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    //проверить размер стораджа после очистки
    @Test
    public void clear() throws Exception {
        storage.clear();
        assertTrue(0 == storage.size());
    }

    //проверка сохранения. Элемент должен быть найден после сохранения
    @Test
    public void save() throws Exception{

        int size = storage.size();
        Resume resume = new Resume(UUID_3, NAME_3);
        storage.save(resume);

        //элемент должен находиться в сторадже
        assertEquals(storage.get(UUID_3), resume);

        //сайз должен увеличиться на единицу
        assertTrue(storage.size() == size + 1);
    }

    //проверка сохранения, если элемент уже есть
    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    //проверка на обновление стоража
    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2, NAME_3);

        //устанавливаем новое значение элемента
        storage.update(resume);

        //элементы должны быть равны
        assertEquals(storage.get(UUID_2), resume);
    }

    //проверка попытки обновления при несуществующем элементе
    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resume = new Resume(UUID_3, NAME_3);
        storage.update(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {

        //текущий размер стораджа
        int size = storage.size();

        //пробуем удалить. Если исключение во время удаления - это ошибка
        try {
            storage.delete(UUID_2);
        } catch (NotExistStorageException e){
            Assert.fail();
        }

        //размер на единицу меньше после удаления
        assertTrue(storage.size() == size - 1);

        //этого элемента больше быть не должно
        storage.get(UUID_2);
    }

    //проверка попытки удаления несуществующего элемента
    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_3);
    }

    //проверка размера стораджа
    @Test
    public void size() throws Exception {
        //должен быть равен двойке
        assertTrue(2 == storage.size());
    }

    //проверка получения массива элементов
    @Test
    public void getAll() throws Exception {

        Resume[] arr;
        //должен быть получен [] из двух элементов
        assertTrue((arr = storage.getAll()).length == 2);

        Arrays.sort(arr);
        //там лежат объекты с UUID_1 и UUID_2
        assertArrayEquals(arr,
                new Resume[]{new Resume(UUID_1, NAME_1), new Resume(UUID_2, NAME_2)});
    }

    //проверка получения конкретного элемента
    @Test
    public void get() throws Exception {
        assertEquals(storage.get(UUID_1), new Resume(UUID_1, NAME_1));
        assertEquals(storage.get(UUID_2), new Resume(UUID_2, NAME_2));
    }

    //проверка получения конкретного элемента если он не существует
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_3);
    }

    //Проверка получения сортированного значения
    //Поле получения значения должны идти UUID_2, потом UUID_1
    @Test
    public void getAllSorted() throws Exception {
        assertArrayEquals(new Resume[]{new Resume(UUID_2, NAME_2), new Resume(UUID_1, NAME_1)},
                storage.getAllSorted().toArray(new Resume[storage.size()]));
    }
}
