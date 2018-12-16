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
        <h2>Search for an artist</h2>
        <s:form action="searchArtist" method="post">
            <s:text name="Query:"/>
            <s:textfield name="query"/><br/>
            <s:submit/>
        </s:form>
        <%--@elvariable id="ArtistSearch" type="Server.SearchArtists"--%>
        <c:forEach items="${ArtistSearch.artistList}" var="artist">
            <p>Name: <c:out value="${artist.name}"/>; Artist ID: <c:out value="${artist.artistID}"/>
                <a href="
                    <s:url action="artistDetail">
                        <s:param name="artistID">${artist.artistID}</s:param>
                    </s:url>
                ">...</a>
            </p>
        </c:forEach>
    </body>
</html>
