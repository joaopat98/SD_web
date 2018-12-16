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
        <h2>Artist Menu</h2>
        <p><a href="<s:url action="artistByIDMenu" />">See details by ID</a></p>
        <p><a href="<s:url action="searchArtistMenu" />">Search for a artist</a></p>
        <c:choose>
            <c:when test="${isEditor}">
                <p><a href="<s:url action="createArtistMenu" />">Create a new artist</a></p>
            </c:when>
        </c:choose>
    </body>
</html>
