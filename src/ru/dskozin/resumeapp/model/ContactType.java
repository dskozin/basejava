package ru.dskozin.resumeapp.model;

public enum ContactType {
    EMAIL("Почта"),
    MOBILE_PHONE("Мобильный телефон"),
    FACEBOOK("Facebook"),
    SKYPE("Логин Skype"),
    LINKEDIN("LinkedIn"),
    STACKOVERFLOW("StackOverFlow");

    private String title;

    ContactType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
