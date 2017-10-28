package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    final int STORAGE_SIZE = 10000;
    Resume[] storage = new Resume[STORAGE_SIZE];
    int size = 0;

    public void clear() {
        Arrays.fill(this.storage, null);
        this.size = 0;
    }

    public void update(Resume r){
        int i = getResumePosition(r.getUuid());
        if(i > -1){
            this.storage[i] = r;
            return;
        }

        System.out.println("ERROR UPDATE");
    }

    public void save(Resume r) {
        if( getResumePosition(r.getUuid()) != -1) {
                System.out.println("ERROR SAVE");
        } else if (this.size == this.STORAGE_SIZE) {
            System.out.println("ERROR OVERFLOW");
        } else {
            this.storage[this.size] = r;
            ++this.size;
        }
    }

    public Resume get(String uuid) {
        int i = getResumePosition(uuid);
        //проверяем что резюме есть. Если нет возвращаем ноль
        if (i > -1){
            return this.storage[i];
        }

        return null;
    }

    public void delete(String uuid) {
        int i = getResumePosition(uuid);

        //если находим заданный элемент
        if (i == -1) {
            System.out.println("ERROR DELETE");
            return;
        }

        //ставим на его место последний
        this.storage[i] = this.storage[this.size - 1];
        //последний зануляем, просто освободить ячейку, удалить объект
        this.storage[this.size - 1] = null;
        //и выходим
        --this.size;
    }

    /**
     * В этом методе будем искать и возвращать позицию резюме в массиве
     * что бы избежать дублирования кода. Если не найден - возвращаем -1
     *
     * @param uuid
     * @return
     */
    private int getResumePosition(String uuid){
        //перебираем резюме по uuid, ищем совпадение
        for (int i = 0; i < this.size; i++) {
            if(uuid.equals(this.storage[i].getUuid())){
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {

        return Arrays.copyOfRange(this.storage,0,this.size);
    }

    public int size() {

        return this.size;
    }
}