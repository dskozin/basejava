package ru.dskozin.resumeapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class OrganizationBlock {
    private final String name;
    private final ArrayList<PeriodicEntry> entries = new ArrayList<>();

    public OrganizationBlock(String name){
        this.name = name;
    }

    public OrganizationBlock(String name, PeriodicEntry... incomeEntries){
        this(name);
        Collections.addAll(entries, incomeEntries);
    }

    public OrganizationBlock(String name, List<PeriodicEntry> list){
        this(name);
        Collections.addAll(list);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name).append("\n");

        for (Iterator<PeriodicEntry> iterator = entries.iterator(); iterator.hasNext();){
            stringBuilder.append(iterator.next());
            if (iterator.hasNext())
                stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
