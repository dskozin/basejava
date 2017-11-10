package ru.dskozin.resumeapp.storage;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.dskozin.resumeapp.exception.ExistStorageException;
import ru.dskozin.resumeapp.exception.NotExistStorageException;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    static Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    //перед каждым тестом мы наполняем стораж
    @Before
    public void setUp() throws Exception {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
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
        Resume resume = new Resume(UUID_3);
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

    //проверка на переполнение стораджа
    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception{
        //очищаем хранилище
        storage.clear();

        //если получаем исключение во время наполнения, то тест не пройден
        try {
            //добавляем элементы начиная с текущего индекса и до max_size (т.е. под завязку)
            for (int i = 0; i < AbstractArrayStorage.STORAGE_SIZE; i++) {
                storage.save(new Resume("uuid_" + i));
            }
            //проверяем что storage забит
            assertTrue(storage.size() == AbstractArrayStorage.STORAGE_SIZE);
        } catch (StorageException e) {
            Assert.fail();
        }

        //пробуем добавить еще элемент сверх размера
        storage.save(new Resume(UUID_3));
    }

    //проверка на обновление стоража
    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_2);

        //устанавливаем новое значение элемента
        //больше обновлять нечего
        storage.update(resume);

        //элементы должны быть равны
        assertEquals(storage.get(UUID_2), resume);
    }

    //проверка попытки обновления при несуществующем элементе
    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resume = new Resume(UUID_3);
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
                new Resume[]{new Resume(UUID_1), new Resume(UUID_2)});
    }

    //проверка получения конкретного элемента
    @Test
    public void get() throws Exception {
        assertEquals(storage.get(UUID_1), new Resume(UUID_1));
        assertEquals(storage.get(UUID_2), new Resume(UUID_2));
    }

    //проверка получения конкретного элемента если он не существует
    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_3);
    }

}