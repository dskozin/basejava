<%@ page import="ru.dskozin.resumeapp.model.Resume" %>
<%@ page import="ru.dskozin.resumeapp.model.Section" %>
<%@ page import="ru.dskozin.resumeapp.model.ContactType" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.dskozin.resumeapp.model.SectionType" %><%--
  Created by IntelliJ IDEA.
  User: kozin
  Date: 03.02.2018
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body{
        font-family: sans-serif;
    }

    h3{
        margin: 20px 0 0 0;
    }
</style>
    <head>
        <% Resume resume = (Resume) request.getAttribute("resume");%>
        <title><%=resume.getFullName()%></title>
    </head>
    <body>
        <h1><%=resume.getFullName()%></h1>
        <table>
            <tbody>
                <tr>
                    <td>uuid</td>
                    <td><%=resume.getUuid()%></td>
                </tr>
                <tr>
                    <td colspan="2"><h3>Контакты</h3></td>
                </tr>
                <% for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) { %>
                    <tr>
                        <td><%=contact.getKey().getTitle()%></td>
                        <td><%=contact.getValue()%></td>
                    </tr>
                <%}%>
                <% for (Map.Entry<SectionType, Section> section : resume.getSections().entrySet()) { %>
                    <tr><td colspan="2"><h3><%=section.getKey().getTitle()%></h3></td></tr>
                    <tr><td colspan="2"><%=section.getValue().toString().replaceAll("\n", "<br>") %></td></tr>
                <%}%>
            </tbody>
        </table>


        <a href="/">Назад</a>
    </body>
</html>
