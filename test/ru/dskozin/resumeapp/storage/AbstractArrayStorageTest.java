package ru.dskozin.resumeapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest{

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
        storage.save(new Resume(NAME_3, UUID_3));
    }
}