package ru.dskozin.resumeapp.model;

public class SectionString extends Section{

    private String content;

    SectionString(){};

    SectionString(String content){
        this.content = content;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
