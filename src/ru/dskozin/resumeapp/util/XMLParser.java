package ru.dskozin.resumeapp.util;

import ru.dskozin.resumeapp.model.Resume;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.Reader;
import java.io.Writer;

public class XMLParser {

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLParser(Class... objects){
        try {

            JAXBContext jbc = JAXBContext.newInstance(objects);

            marshaller = jbc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");

            unmarshaller = jbc.createUnmarshaller();

        } catch (JAXBException e){
            e.printStackTrace();
        }
    }

    public void marshall(Object instance, Writer writer){
        try {
            marshaller.marshal(instance, writer);
        } catch (JAXBException e){
            throw new IllegalStateException();
        }
    }

    public <T> T unmarshall(Reader r){
        try {
            return (T) unmarshaller.unmarshal(r);
        } catch (JAXBException e){
            throw new IllegalStateException();
        }
    }
}
