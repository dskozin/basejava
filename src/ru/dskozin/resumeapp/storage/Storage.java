package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void save(Resume r);

    void update(Resume r);

    void delete(String uuid);

    int size();

    Resume[] getAll();

    List<Resume> getAllSorted();

    Resume get(String uuid);

}
