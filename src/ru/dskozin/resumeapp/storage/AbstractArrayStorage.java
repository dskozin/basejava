package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    final int STORAGE_SIZE = 5;
    protected Resume[] storage = new Resume[STORAGE_SIZE];
    int size = 0;

    @Override
    public void clear() {

        Arrays.fill(this.storage, 0, this.size,null);
        this.size = 0;
    }

    @Override
    public void update(Resume r){
        int i = getIndex(r.getUuid());
        if(i < 0){
            System.out.println("Resume " + r.getUuid() + " not exist!");
            return;
        }
        this.storage[i] = r;
    }

    @Override
    public int size() {

        return this.size;
    }

    @Override
    public Resume[] getAll() {

        return Arrays.copyOfRange(this.storage,0,this.size);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        //binarySearch возвращает отрицательный lowIndex, а не -1
        //поэтому укажем в условии что меньше 0, а не == -1
        if(index < 0){
            System.out.println("Resume " + uuid + " not exist!");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);
}
