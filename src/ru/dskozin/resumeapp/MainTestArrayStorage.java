package ru.dskozin.resumeapp;

import ru.dskozin.resumeapp.model.Resume;
import ru.dskozin.resumeapp.storage.SortedArrayStorage;
import ru.dskozin.resumeapp.storage.Storage;

/**
 * Test for com.urise.webapp.storage.ru.dskozin.resumeapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r4 = new Resume("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r4);
        //не сохраняет, потому что есть
        ARRAY_STORAGE.save(r1);

        //проверяем что update обновляет
        ARRAY_STORAGE.update(r2);
        //проверяем что update не обновляет если нет
        Resume r3 = new Resume("uuid3");
        ARRAY_STORAGE.update(r3);

        //проверяем что вставка в середину работает
        ARRAY_STORAGE.save(r3);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
