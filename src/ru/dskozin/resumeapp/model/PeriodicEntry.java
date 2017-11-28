package ru.dskozin.resumeapp.model;

import java.time.LocalDate;

public class PeriodicEntry {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String header;
    private String content;

    //для создания записей без контента (Образование)
    public PeriodicEntry(String title, LocalDate startDate, LocalDate endDate, String header){
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.header = header;
    }

    //Для создания записей с контентом (Опыт работы)
    public PeriodicEntry(String title, LocalDate startDate, LocalDate endDate, String header, String content){
        this(title,startDate,endDate,header);
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(title)
                .append("\n")
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
