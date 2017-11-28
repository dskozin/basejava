package ru.dskozin.resumeapp;

import ru.dskozin.resumeapp.model.*;

import java.time.LocalDate;
import java.util.Date;

public class MainResumeObject {
    public static void main(String[] args) {
        
        //создаем объект резюме
        Resume resume = new Resume("Дмитрий Козин", "uuid1");

        //накидываем в него контакты
        //переменная contacts финальная, так что ее можно не прятать
        resume.getContacts().put(ContactType.EMAIL, "dskozin@mail.ru");
        resume.getContacts().put(ContactType.FACEBOOK, "http://facebook.com/dskozin");
        resume.getContacts().put(ContactType.LINKEDIN, "http://linkedin.com/dskozin");
        resume.getContacts().put(ContactType.MOBILE_PHONE, "+7 967 099 00 69");
        resume.getContacts().put(ContactType.SKYPE, "dskozin@mail.ru");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "Нету");

        //создаем секцию позиция
        SectionString position = new SectionString("Middle Java Developer - разработчик корпоративного ПО");

        //создаем секцию достижений
        SectionList<String> achievement = new SectionList<>();
        achievement.dataList.add("Курс подготовки BaseJava");
        achievement.dataList.add("Разработка приложения для формирования базы резюме");
        achievement.dataList.add("Кур JavaRush");

        //квалификация
        SectionList<String> qualification = new SectionList<>();
        qualification.dataList.add("Java 8 Core");
        qualification.dataList.add("Spring Boot, Spring WebFlow");
        qualification.dataList.add("HTML, CSS");

        //опыт работы
        SectionList<PeriodicEntry> experience = new SectionList<>();
        experience.dataList.add(new PeriodicEntry(
                "ООО \"Виалек\"",
                LocalDate.of(2015, 5, 15),
                null,
                "Начальник ИТ отдела",
                "Руководил разработкой программного обеспечения компании в частности продуктом для управления регистрационным досье"));

        experience.dataList.add(new PeriodicEntry(
                "ООО \"Данцер\"",
                LocalDate.of(2016, 12, 12),
                LocalDate.of(2017, 6, 13),
                "Разработчик РНР",
                "Разработка модели бизнес-процессов для системы упраления жизнью"));

        experience.dataList.add(new PeriodicEntry(
                "ООО \"АйТи\"",
                LocalDate.of(2016, 9, 15),
                LocalDate.of(2017, 12, 10),
                "Бизнес-архитектор",
                "Аналитика процессов для внедрения ситем на базе платформы Terra-Soft"));

        //образование
        SectionList<PeriodicEntry> education = new SectionList<>();
        education.dataList.add(new PeriodicEntry(
                "Московский Государственный Университет Приборостроения и Информатики",
                LocalDate.of(2007, 9, 1),
                LocalDate.of(2013, 6, 10),
                "Приборостроение"));
        education.dataList.add(new PeriodicEntry(
                "Школа бизнеса МИРБИС",
                LocalDate.of(2013, 9, 1),
                LocalDate.of(2014, 6, 10),
                "Корпоративные финансы"));
        education.dataList.add(new PeriodicEntry(
                "Московский Государственный Гуманитарный Университет",
                LocalDate.of(2014, 9, 1),
                LocalDate.of(2016, 6, 10),
                "Общая психология"));

        //кладем все секции в объект резюме
        resume.getSections().put(SectionType.ACHIEVEMENT, achievement);
        resume.getSections().put(SectionType.QUALIFICATION, qualification);
        resume.getSections().put(SectionType.EXPERIENCE, experience);
        resume.getSections().put(SectionType.EDUCATION, education);
        resume.getSections().put(SectionType.OBJECTIVE, position);

        //выводим резюме на экран
        System.out.println(resume.fullResume());
        
    }
}
