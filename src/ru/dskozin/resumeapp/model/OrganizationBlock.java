package ru.dskozin.resumeapp.model;

import ru.dskozin.resumeapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationBlock implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private List<PeriodicEntry> entries = new ArrayList<>();

    public OrganizationBlock(){};

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationBlock that = (OrganizationBlock) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, entries);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PeriodicEntry implements Serializable{

        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

        public PeriodicEntry(){}

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PeriodicEntry that = (PeriodicEntry) o;
            return Objects.equals(startDate, that.startDate) &&
                    Objects.equals(endDate, that.endDate) &&
                    Objects.equals(header, that.header) &&
                    Objects.equals(content, that.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, header, content);
        }
    }
}
