package ru.dskozin.resumeapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable{

    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume() {
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

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(ContactType type){
        return contacts.get(type);
    }

    public Section getSection(SectionType type){
        return sections.get(type);
    }

    public void addContact(ContactType type, String contact){
        contacts.put(type,contact);
    }

    public void deleteContact(ContactType type){
        contacts.remove(type);
    }

    public void addSection(SectionType type, Section section){
        sections.put(type,section);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, Section> getSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
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

    public String fullResume(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fullName).append("\n");
        for (ContactType ct : ContactType.values()){
            if (sections.get(ct) == null)
                continue;

            stringBuilder.append(ct.getTitle())
                    .append(": ")
                    .append(contacts.get(ct))
                    .append("\n");
        }
        stringBuilder.append("\n");
        for (SectionType st : SectionType.values()){
            if (sections.get(st) == null)
                continue;

            stringBuilder.append(st.getTitle())
                    .append("\n")
                    .append(sections.get(st).toString())
                    .append("\n");
        }

        return stringBuilder.toString();
    }

}
