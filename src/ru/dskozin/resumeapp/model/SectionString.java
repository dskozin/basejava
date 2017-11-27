package ru.dskozin.resumeapp.model;

public class SectionString extends Section{

    private String content;

    public SectionString(){};

    public SectionString(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "- " + content + "\n";
    }
}
