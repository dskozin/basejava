<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="resume" class="ru.dskozin.resumeapp.model.Resume" scope="request"/>
<html>
<head>
    <link rel="stylesheet" href="../../css/style.css">
    <title>${resume.fullName}</title>
</head>
<jsp:include page="fragment/header.jsp"/>
<body>
<h1>${resume.fullName}</h1>
<table>
    <tbody>
    <tr>
        <td>uuid</td>
        <td>${resume.uuid}</td>
    </tr>
    <tr>
        <td colspan="2"><h3>Контакты</h3></td>
    </tr>
    <c:forEach items="${resume.contacts.entrySet()}" var="contact">
        <tr>
            <td>${contact.getKey().getTitle()}</td>
            <td>${contact.getValue()}</td>
        </tr>
    </c:forEach>
    <c:forEach items="${resume.sections.entrySet()}" var="section">
        <tr>
            <td colspan="2"><h3>${section.getKey().getTitle()}</h3></td>
        </tr>
        <tr>
            <td colspan="2">${section.getValue().toString().replaceAll("\\n", "<br>")}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="#" onclick="window.history.back()">Назад</a>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
