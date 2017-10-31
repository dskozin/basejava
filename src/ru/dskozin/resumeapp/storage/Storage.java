package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

public interface Storage {

    void clear();

    void save(Resume r);

    void update(Resume r);

    void delete(String uuid);

    int size();

    Resume[] getAll();

    Resume get(String uuid);

}
