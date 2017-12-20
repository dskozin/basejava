package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.exception.StorageException;
import ru.dskozin.resumeapp.model.Resume;
import ru.dskozin.resumeapp.storage.Serialization.ObjectSerialization;
import ru.dskozin.resumeapp.storage.Serialization.SerializationStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path>{

    private final Path storage;
    private final SerializationStrategy strategy;

    public PathStorage(String directory, SerializationStrategy strategy) {
        Objects.requireNonNull(directory, "Directory can not be null");
        Objects.requireNonNull(strategy, "Strategy can not be null");
        Path dir = Paths.get(directory);
        if(!Files.isDirectory(dir))
            throw new IllegalArgumentException(directory + " is not directory");

        if(!Files.isReadable(dir) || !Files.isWritable(dir))
            throw new IllegalArgumentException(dir.toAbsolutePath() + " is not writable/readable");

        this.storage = dir;
        this.strategy = strategy;
    }

    public PathStorage(String directory){
        this(directory, new ObjectSerialization());
    }

    @Override
    void storageUpdate(Resume r, Path file) {
        try{
            strategy.doWrite(r, Files.newOutputStream(file));
        } catch (IOException e){
            throw new StorageException("Update Error", file.getFileName().toString(), e);
        }
    }

    @Override
    void storageDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e){
            throw new StorageException("Delete error", file.getFileName().toString(), e);
        }
    }

    @Override
    Resume storageGet(Path file) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e){
            throw new StorageException("File read error", file.getFileName().toString(), e);
        }
    }

    @Override
    Path getIndex(String uuid) {
        return storage.resolve(uuid);
    }

    @Override
    void storageSave(Resume r, Path file) {
        try {
            if (!Files.exists(file))
                Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getFileName().toString(), e);
        }
        storageUpdate(r, file);
    }

    @Override
    List<Resume> getStorageAsList() {
        return fileList().map(this::storageGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        fileList().forEach(this::storageDelete);
    }

    @Override
    public int size() {
        return (int)fileList().count();
    }

    private Stream<Path> fileList(){
        try{
           return Files.list(storage);
        } catch (IOException e){
            throw new StorageException("Storage directory list error", "none");
        }
    }

    boolean found(Path index){
        return Files.isRegularFile(index);
    }
}
