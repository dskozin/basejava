package ru.dskozin.resumeapp.exception;

public class FileIOStorageException extends Exception{
    public FileIOStorageException(String fileName){
        super("There is problem with file: " + fileName);
    }
}
