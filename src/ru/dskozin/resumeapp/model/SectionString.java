package ru.dskozin.resumeapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class SectionString extends Section{

    private static final long serialVersionUID = 1L;

    private String content;

    public SectionString(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionString that = (SectionString) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
