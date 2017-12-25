package ru.dskozin.resumeapp.storage.Serialization;

import ru.dskozin.resumeapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DataSerializationStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream out) throws IOException {
        try (DataOutputStream stream = new DataOutputStream(out)) {
            stream.writeUTF(r.getUuid());
            stream.writeUTF(r.getFullName());

            //записать контакты
            writeContacts(stream, r);

            //записать количество секций
            stream.writeInt(r.getSections().size());

            //перебрать секции для записи
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                stream.writeUTF(e.getKey().name());

                switch (e.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        stream.writeUTF(((SectionString) e.getValue()).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeStringList(e.getValue(), stream);
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeOrganizationSection(e.getValue(), stream);
                        break;
                }
            }
        }
    }


    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (DataInputStream stream = new DataInputStream(in)){
            String uuid = stream.readUTF(), fullName = stream.readUTF();
            Resume resume = new Resume(fullName, uuid);

            //получить контакты
            readContacts(stream, resume);

            //секции
            int sectSize = stream.readInt();
            for (int i = 0; i < sectSize; i++) {
                SectionType type = SectionType.valueOf(stream.readUTF());
                switch (type){
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new SectionString(stream.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        readStringList(stream, resume, type);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        readOrganizationSection(stream, resume, type);
                        break;
                }
            }

            return resume;
        }
    }

    //функция записи контактов
    private void writeContacts(DataOutputStream stream, Resume r) throws IOException {
        stream.writeInt(r.getContacts().size());

        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            stream.writeUTF(e.getKey().name());
            stream.writeUTF(e.getValue());
        }
    }

    //функция записи строковых списков секций
    private void writeStringList(Section e, DataOutputStream stream) throws IOException {
        //получаем список значений из секции
        List<String> sectionList = ((SectionList) e).getDataList();

        //указываем количество записей в списке
        stream.writeInt(sectionList.size());

        //пишем строки
        for (String s : sectionList){
            stream.writeUTF(s);
        }
    }

    private void writeOrganizationSection(Section e, DataOutputStream stream) throws IOException {

        List<Organization> stringList = ((OrganizationSection) e).getOrganizations();

        int orgSize;
        //записать количество организаций
        stream.writeInt(orgSize = stream.size());

        //перечислить организации
        for (Organization org : stringList) {
            //записать наименование организации
            stream.writeUTF(org.getName());

            List<Organization.PeriodicEntry> periodicEntries = org.getEntries();

            //записать количество позиций
            stream.writeInt(periodicEntries.size());

            //записать позиции
            for (Organization.PeriodicEntry periodicEntry : periodicEntries) {
                stream.writeUTF(periodicEntry.getStartDate().toString());
                stream.writeUTF(periodicEntry.getEndDate().toString());
                stream.writeUTF(periodicEntry.getHeader());
                stream.writeUTF(periodicEntry.getContent());
            }
        }

    }

    //функция чтения контактов
    private void readContacts(DataInputStream stream, Resume resume) throws IOException {
        int size = stream.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(stream.readUTF()), stream.readUTF());
        }
    }

    //функция чтения строковых списков контактов
    private void readStringList(DataInputStream stream, Resume r, SectionType type) throws IOException{

        //создаем список
        SectionList list = new SectionList();

        int size = stream.readInt();
        for (int i = 0; i < size; i++) {
            list.add(stream.readUTF());
        }

        r.addSection(type, list);
    }

    //функция восстановления секции организации
    private void readOrganizationSection(DataInputStream stream, Resume resume, SectionType type) throws IOException {
        //создаем секцию
        OrganizationSection organizationSection = new OrganizationSection();

        //считаем организации
        int orgSize = stream.readInt();

        for (int i = 0; i < orgSize; i++) {
            Organization organization = new Organization(stream.readUTF());

            //сколько позиций
            int posSize = stream.readInt();

            for (int j = 0; j < posSize; j++) {
                LocalDate startDate = LocalDate.parse(stream.readUTF()),
                    endDate = LocalDate.parse(stream.readUTF());
                String header = stream.readUTF(),
                    content = stream.readUTF();

                Organization.PeriodicEntry periodicEntry =
                        new Organization.PeriodicEntry(startDate, endDate, header, content);

                organization.add(periodicEntry);
            }
        }
    }
}
