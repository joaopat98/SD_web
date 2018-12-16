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
        <h2>Search for a song</h2>
        <s:form action="searchSong" method="post">
            <s:text name="Query:"/>
            <s:textfield name="query"/><br/>
            <s:submit/>
        </s:form>
        <%--@elvariable id="SongSearch" type="Server.SearchSongs"--%>
        <c:forEach items="${SongSearch.songList}" var="song">
            <p>Name: <c:out value="${song.name}"/>; Song ID: <c:out value="${song.songID}"/>
                <a href="
                    <s:url action="songDetail">
                        <s:param name="songID">${song.songID}</s:param>
                    </s:url>
                ">...</a>
            </p>
        </c:forEach>
    </body>
</html>
