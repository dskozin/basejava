package ru.dskozin.resumeapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class SectionList extends Section{

    private static final long serialVersionUID = 1L;

    //его можно открыть, он финальный
    private List<String> dataList = new ArrayList<>();

    public SectionList(){}

    @SafeVarargs
    public SectionList(String... args){
        dataList.addAll(Arrays.asList(args));
    }

    public SectionList(List<String> ts) {
        Objects.requireNonNull(ts, "List cannot be null");
        dataList = ts;
    }

    public void add(String entry){
        dataList.add(entry);
    }

    public List<String> getDataList() {
        return dataList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String entry : dataList){
            if (entry instanceof String)
                stringBuilder.append("- ");

            stringBuilder.append(entry.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionList that = (SectionList) o;
        return Objects.equals(dataList, that.dataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataList);
    }
}
