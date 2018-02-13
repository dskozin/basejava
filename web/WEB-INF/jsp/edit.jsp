<%@ page import="ru.dskozin.resumeapp.model.ContactType" %>
<%@ page import="ru.dskozin.resumeapp.model.SectionType" %>
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
<form action="resume" method="post" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="uuid" value="${resume.uuid}">
    <dl>
        <dt>Имя</dt>
        <dd><input type="text" name="fullName" value="${resume.fullName}"></dd>
    </dl>
    <h3>Контакты</h3>
    <c:forEach items="<%=ContactType.values()%>" var="type">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" value="${resume.getContact(type)}"></dd>
        </dl>
    </c:forEach>
    <h3>Секции</h3>
    <c:forEach items="<%=SectionType.values()%>" var="section">
        <dl>
            <dt>${section.title}</dt>
            <dd>
                <c:if test="${section.name().equals(\"ACHIEVEMENT\") || section.name().equals(\"QUALIFICATION\") }">
                    <textarea name="${section.name()}">${resume.getSection(section)}</textarea>
                </c:if>
                <c:if test="${section.name().equals(\"PERSONAL\") || section.name().equals(\"OBJECTIVE\") }">
                    <input type="text" name="${section.name()}" value="${resume.getSection(section)}">
                </c:if>
                <c:if test="${section.name().equals(\"EXPERIENCE\") || section.name().equals(\"EDUCATION\")}">
                    <table>
                        <c:forEach items="${resume.getSection(section).organizations}" var="organization">
                            <jsp:useBean id="organization" class="ru.dskozin.resumeapp.model.Organization"/>
                            <tr>
                                <td colspan="2"><a href="${organization.link.url}">${organization.link.name}</a></td>
                            </tr>
                            <c:forEach items="${organization.entries}" var="entry">
                                <tr>
                                    <td colspan="2" style="font-weight: 600">${entry.header}
                                        (С ${entry.getFormattedStartDate()} по ${entry.getFormattedEndDate()})
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">${entry.content}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </table>
                </c:if>
            </dd>
        </dl>
    </c:forEach>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()">Отмена</button>
</form>
<jsp:include page="fragment/footer.jsp"/>
</body>
<script type="text/javascript">

</script>
</html>
