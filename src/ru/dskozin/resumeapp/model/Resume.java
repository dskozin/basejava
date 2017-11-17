package ru.dskozin.resumeapp.model;

import java.util.Comparator;
import java.util.UUID;

public class Resume implements Comparable<Resume>{

    private static final String DEFAULT_EMAIL = "";

    // Unique identifier
    private final String uuid;
    private String fullName;
    private String email;

    private static final Comparator<Resume> NAME_COMPARATOR =
            Comparator.comparing(Resume::getFullName);

    public Resume(String fullName) {
        this(fullName, UUID.randomUUID().toString(), DEFAULT_EMAIL);
    }

    public Resume(String fullName, String uuid) {
        this(fullName, uuid, DEFAULT_EMAIL );
    }

    public Resume(String fullName, String uuid, String email){
        this.uuid = uuid;
        this.fullName = fullName;
        this.email = email;
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

    public String getEmail(){
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        return email != null ? email.equals(resume.email) : resume.email == null;
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
