package ru.dskozin.resumeapp.exception;

public class StorageException extends RuntimeException{

        private final String uuid;


    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception cause){
        super(message,cause);
        this.uuid = uuid;
    }
}
