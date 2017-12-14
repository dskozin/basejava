package ru.dskozin.resumeapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class OrganizationBlock implements Serializable{
    private static final long serialVersionUID = 1L;

    private final String name;
    private final List<PeriodicEntry> entries = new ArrayList<>();

    public OrganizationBlock(String name){
        this.name = name;
    }

    public OrganizationBlock(String name, PeriodicEntry... incomeEntries){
        this(name);
        Collections.addAll(entries, incomeEntries);
    }

    public OrganizationBlock(String name, List<PeriodicEntry> list){
        this(name);
        entries.addAll(list);
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

    public static class PeriodicEntry implements Serializable{
        private static final long serialVersionUID = 1L;
        private LocalDate startDate;
        private LocalDate endDate;
        private String header;
        private String content;

        //для создания записей без контента (Образование)
        public PeriodicEntry(LocalDate startDate, LocalDate endDate, String header){
            Objects.requireNonNull(startDate, " startDate must not be null");
            Objects.requireNonNull(header, " header must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.header = header;
        }

        //Для создания записей с контентом (Опыт работы)
        public PeriodicEntry(LocalDate startDate, LocalDate endDate, String header, String content){
            this(startDate,endDate,header);
            this.content = content;
        }

        //Для создания записей с отсутствующей датой окончания
        public PeriodicEntry(LocalDate startDate, String header, String content){
            this(startDate,LocalDate.MAX,header);
            this.content = content;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder
                    .append(startDate)
                    .append(" - ")
                    .append(endDate == null ? "Сейчас" : endDate)
                    .append("    ")
                    .append(header);
            if (content != null)
                    stringBuilder.append("\n").append(content);

            return stringBuilder.toString();
        }
    }
}
