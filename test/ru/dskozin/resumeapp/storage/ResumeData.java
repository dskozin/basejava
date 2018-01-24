package ru.dskozin.resumeapp.storage;

import ru.dskozin.resumeapp.model.*;

import java.time.LocalDate;

public class ResumeData {
    public static Resume getResume(String name, String uuid){

        //создаем объект резюме
        Resume resume = new Resume(name, uuid);

        //накидываем в него контакты
        //переменная contacts финальная, так что ее можно не прятать
        resume.addContact(ContactType.EMAIL, "dskozin@mail.ru");
        resume.addContact(ContactType.FACEBOOK, "http://facebook.com/dskozin");
        resume.addContact(ContactType.LINKEDIN, "http://linkedin.com/dskozin");
        resume.addContact(ContactType.MOBILE_PHONE, "+7 967 099 00 69");
        resume.addContact(ContactType.SKYPE, "dskozin@mail.ru");
        resume.addContact(ContactType.STACKOVERFLOW, "Нету");

        //создаем секцию позиция
        SectionString position = new SectionString("Middle Java Developer - разработчик корпоративного ПО");

        //создаем секцию достижений
        SectionList achievement = new SectionList();
        achievement.add("Курс подготовки BaseJava");
        achievement.add("Разработка приложения для формирования базы резюме");
        achievement.add("Кур JavaRush");

        //квалификация
        SectionList qualification = new SectionList();
        qualification.add("Java 8 Core");
        qualification.add("Spring Boot, Spring WebFlow");
        qualification.add("HTML, CSS");

        //опыт работы
        OrganizationSection experience = new OrganizationSection();
        experience.add(new Organization(
                new Link("ООО \"Виалек\"","www.vialek.ru"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2015, 5, 15),
                        "Начальник ИТ отдела",
                        "Руководил разработкой программного обеспечения компании в частности продуктом для управления регистрационным досье"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2014, 6, 15),
                        LocalDate.of(2015, 5, 15),
                        "Заместитель начальника",
                        "Руководил всеми аспектами разработки программного обеспечения"
                )));

        experience.add(new Organization(
                new Link("ООО \"Данцер\"","www.dantser.ru"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2016, 12, 12),
                        LocalDate.of(2017, 6, 13),
                        "Разработчик РНР",
                        "Разработка модели бизнес-процессов для системы упраления жизнью"
                )));

        experience.add(new Organization(
                new Link("ООО \"АйТи\"","www.it.ru"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2016, 9, 15),
                        LocalDate.of(2017, 12, 10),
                        "Бизнес-архитектор",
                        "Аналитика процессов для внедрения ситем на базе платформы Terra-Soft")));

        //образование
        OrganizationSection education = new OrganizationSection();
        education.add(new Organization(
                new Link("Московский Государственный Университет Приборостроения и Информатики","www.mgupi.ru"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2007, 9, 1),
                        LocalDate.of(2013, 6, 10),
                        "Приборостроение")));
        education.add(new Organization(
                new Link("Школа бизнеса МИРБИС","www.mirbis.ru"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2013, 9, 1),
                        LocalDate.of(2014, 6, 10),
                        "Корпоративные финансы")));
        education.add(new Organization(
                new Link("Московский Государственный Гуманитарный Университет","www.rsuh.ru"),
                new Organization.PeriodicEntry(
                        LocalDate.of(2014, 9, 1),
                        LocalDate.of(2016, 6, 10),
                        "Общая психология")));

        //кладем все секции в объект резюме
//        resume.addSection(SectionType.ACHIEVEMENT, achievement);
//        resume.addSection(SectionType.QUALIFICATION, qualification);
//        resume.addSection(SectionType.EXPERIENCE, experience);
//        resume.addSection(SectionType.EDUCATION, education);
//        resume.addSection(SectionType.OBJECTIVE, position);

        //выводим резюме на экран
        return resume;
    }
}
