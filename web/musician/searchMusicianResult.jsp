<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h2>Search for a musician</h2>
        <s:form action="searchMusician" method="post">
            <s:text name="Query:"/>
            <s:textfield name="query"/><br/>
            <s:submit/>
        </s:form>
        <%--@elvariable id="MusicianSearch" type="Server.SearchMusicians"--%>
        <c:forEach items="${MusicianSearch.musicianList}" var="musician">
            <p>Name: <c:out value="${musician.name}"/>; Musician ID: <c:out value="${musician.musicianID}"/>
                <a href="
                    <s:url action="musicianDetail">
                        <s:param name="musicianID">${musician.musicianID}</s:param>
                    </s:url>
                ">...</a>
            </p>
        </c:forEach>
    </body>
</html>
