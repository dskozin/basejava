package ru.dskozin.resumeapp.model;

import java.util.Comparator;
import java.util.UUID;

public class Resume implements Comparable<Resume>{
    private final static String DEFAULT_NAME = "";

    // Unique identifier
    private final String uuid;
    private String fullName;
    private static final Comparator<Resume> NAME_COMPARATOR =
            Comparator.comparing(Resume::getFullName);

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this(uuid, DEFAULT_NAME);
    }

    public Resume(String uuid, String fullName){
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public static Comparator<Resume> nameComparator(){
        return NAME_COMPARATOR;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        return fullName != null ? fullName.equals(resume.fullName) : resume.fullName == null;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int compareTo(Resume o) {
        return this.uuid.compareTo(o.uuid);
    }

    @Override
    public String toString() {
        return uuid;
    }

}
