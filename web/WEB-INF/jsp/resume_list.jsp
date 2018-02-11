<%@ page import="ru.dskozin.resumeapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="../../css/style.css">
    <title>Список резюмешек</title>
</head>
<jsp:include page="fragment/header.jsp"/>
<body>
<c:if test="${resumes_list.size() == 0}">
    <B>Нет ни одного резюме</B>
</c:if>
<c:if test="${resumes_list.size() > 0}">
    <table>
        <tbody>
        <c:forEach items="${resumes_list}" var="resume">
            <tr>
                <td>
                    ${resume.fullName}
                </td>
                <td>
                    ${resume.getContact(ContactType.EMAIL)}
                </td>
                <td>
                    <a href="?uuid=${resume.uuid}&action=view">Просмотр</a>
                </td>
                <td>
                    <a href="?uuid=${resume.uuid}&action=edit">Редактировать</a>
                </td>
                <td>
                    <a href="?uuid=${resume.uuid}&action=delete">Удалить</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<jsp:include page="fragment/footer.jsp"/>
</body>
</html>
