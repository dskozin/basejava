package ru.dskozin.resumeapp.model;

import java.util.ArrayList;
import java.util.List;

public class SectionList<T> extends Section{

    final List<T> dataList = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T entry : dataList){
            stringBuilder.append(entry.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
