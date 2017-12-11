package ru.dskozin.resumeapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionList<T> extends Section{

    //его можно открыть, он финальный
    private final List<T> dataList = new ArrayList<>();

    @SafeVarargs
    public SectionList(T... args){
        dataList.addAll(Arrays.asList(args));
    }

    public void add(T entry){
        dataList.add(entry);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T entry : dataList){
            if (entry instanceof String)
                stringBuilder.append("- ");

            stringBuilder.append(entry.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
