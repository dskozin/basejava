package ru.dskozin.resumeapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations = new ArrayList<>();

    public OrganizationSection(){};

    public OrganizationSection(Organization... incomeEntries){
        Collections.addAll(organizations, incomeEntries);
    }

    public OrganizationSection(List<Organization> list){
        organizations.addAll(list);
    }

    public void add(Organization organization) {
        organizations.add(organization);
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Iterator<Organization> iterator = organizations.iterator(); iterator.hasNext();){
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
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }
}
