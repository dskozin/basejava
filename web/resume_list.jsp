<%@ page import="java.util.List" %>
<%@ page import="ru.dskozin.resumeapp.model.Resume" %><%--
  Created by IntelliJ IDEA.
  User: kozin
  Date: 03.02.2018
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    body{
        font-family: sans-serif;
    }
</style>
<head>
    <title>Список резюмешек</title>
</head>
<body>
<%
    List<Resume> resumes = (List<Resume>) request.getAttribute("resumes_list");
    if (resumes.size() == 0) { %>
    <B>Нет ни одного резюме</B>
<% } else { %>
    <table>
        <tbody>
            <% for (Resume r : resumes ) {%>
                <tr>
                    <td>
                        <%="<a href='?uuid=" + r.getUuid() +"'>" + r.getUuid() + "</a>"%>
                    </td>
                    <td>
                        <%=r.getFullName()%>
                    </td>
                </tr>
            <%}%>
        </tbody>
    </table>
<% } %>
</body>
</html>
