package ru.dskozin.resumeapp.model;

import java.util.Objects;

public class SectionString extends Section{

    private String content;

    public SectionString(String content){
        Objects.requireNonNull(content, " content must not be null");
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
