package ru.dskozin.resumeapp.model;

import java.util.*;

public class Resume implements Comparable<Resume>{

    // Unique identifier
    private final String uuid;
    private String fullName;

    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();
    {
        this.sections.put(SectionType.PERSONAL, new SectionString());
        this.sections.put(SectionType.OBJECTIVE, new SectionString());
        this.sections.put(SectionType.ACHIEVEMENT, new SectionList<String>());
        this.sections.put(SectionType.QUALIFICATION, new SectionList<String>());
        this.sections.put(SectionType.EXPERIENCE, new SectionList<PeriodicEntry>());
        this.sections.put(SectionType.EDUCATION, new SectionList<PeriodicEntry>());
    }

    public Resume(String fullName) {
        this(fullName, UUID.randomUUID().toString());
    }

    public Resume(String fullName, String uuid) {
        Objects.requireNonNull(fullName, "fullName can't be null");
        Objects.requireNonNull(uuid, "UUID can't be null");
        this.fullName = fullName;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }


    //Добавление и получение контактов через объект резюме.
    public String getContactData(ContactType type){
        return contacts.get(type);
    }

    public List<String> getContacts(){
        List<String> contactList = new ArrayList<>();
        contactList.addAll(contacts.values());
        return contactList;
    }

    public void addContact(ContactType type, String data){
        contacts.put(type,data);
    }
    // --------- конец секции контактов.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    @Override
    public String toString() {
        return uuid + "(" + fullName + ")";
    }

}
