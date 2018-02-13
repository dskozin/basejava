package ru.dskozin.resumeapp.model;

import ru.dskozin.resumeapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable{

    private Link link;

    private List<PeriodicEntry> entries = new ArrayList<>();

    public Organization(){}

    public Organization(Link link) {
        this.link = link;
    }

    public Organization(Link link, List<PeriodicEntry> entries) {
        this(link);
        this.entries = entries;
    }

    public Organization(Link link, PeriodicEntry... entries){
        this(link, Arrays.asList(entries));
    }

    public Link getLink() {
        return link;
    }

    public List<PeriodicEntry> getEntries() {
        return entries;
    }

    public void add(PeriodicEntry entry){
        entries.add(entry);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(link.getName()).append("\n");

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
        Organization that = (Organization) o;
        return Objects.equals(link, that.link) &&
                Objects.equals(entries, that.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, entries);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PeriodicEntry implements Serializable {

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
            this.content = "";
        }

        //Для создания записей с контентом (Опыт работы)
        public PeriodicEntry(LocalDate startDate, LocalDate endDate, String header, String content){
            this(startDate,endDate,header);
            this.content = content == null ? "" : content;
        }

        //Для создания записей с отсутствующей датой окончания
        public PeriodicEntry(LocalDate startDate, String header, String content){
            this(startDate,LocalDate.MAX,header, content);
        }

        public PeriodicEntry(){}

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getHeader() {
            return header;
        }

        public String getContent() {
            return content;
        }

        public String getFormattedStartDate(){
            return startDate.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
        }

        public String getFormattedEndDate(){
            if (endDate.equals(LocalDate.MAX))
                return "По нынешнее время";

            return endDate.format(DateTimeFormatter.ofPattern("d MMM uuuu"));
        }

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
        public String toString() {
            return "PeriodicEntry(" + startDate +
                    "," + endDate +
                    "," + header +
                    "," + content +
                    ')';
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, header, content);
        }
    }
}
