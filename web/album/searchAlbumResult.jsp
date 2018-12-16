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
        <h2>Search for a album</h2>
        <s:form action="searchAlbum" method="post">
            <s:text name="Query:"/>
            <s:textfield name="query"/><br/>
            <s:submit/>
        </s:form>
        <%--@elvariable id="AlbumSearch" type="Server.SearchAlbums"--%>
        <c:forEach items="${AlbumSearch.albumList}" var="album">
            <p>Name: <c:out value="${album.name}"/>; Album ID: <c:out value="${album.albumID}"/>
                <a href="
                    <s:url action="albumDetail">
                        <s:param name="albumID">${album.albumID}</s:param>
                    </s:url>
                ">...</a>
            </p>
        </c:forEach>
    </body>
</html>
