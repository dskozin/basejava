package ru.dskozin.resumeapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class PeriodicEntry {
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
