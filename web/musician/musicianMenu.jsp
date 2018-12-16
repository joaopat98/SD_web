<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h2>Musician Menu</h2>
        <p><a href="<s:url action="musicianByIDMenu" />">See details by ID</a></p>
        <p><a href="<s:url action="searchMusicianMenu" />">Search for a musician</a></p>
        <c:choose>
            <c:when test="${isEditor}">
                <p><a href="<s:url action="createMusicianMenu" />">Create a new musician</a></p>
            </c:when>
        </c:choose>
    </body>
</html>
