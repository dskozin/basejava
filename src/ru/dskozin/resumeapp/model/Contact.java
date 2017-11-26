package ru.dskozin.resumeapp.model;

public class Contact {
    private ContactType type;
    private String contact;

    Contact(ContactType type, String contact){
        this.type = type;
        this.contact = contact;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
