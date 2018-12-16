<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--@elvariable id="Artist" type="Server.ArtistDetails"--%>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h1>Artist Details</h1>
        <h2>Info:</h2>
        <p><a href="<s:url action="artistEditMenu" />">Edit</a></p>
        <p>Artist ID: ${Artist.artistID}</p>
        <p>Name: ${Artist.artistName}</p>
        <p>Description: ${Artist.description}</p>
        <h2>Members:</h2>

        <ul>
            <c:forEach items="${Artist.memberList}" var="member">
                <li>Name: ${member.name}; Musician ID: ${member.musician_id}</li>
            </c:forEach>
        </ul>

        <h2>Albums:</h2>
        <p><a href="<s:url action="createAlbumMenu" />">Add an album</a></p>
        <ul>
            <c:forEach items="${Artist.albumList}" var="album">
                <li>Name: ${album.name}; Album ID: ${album.albumID}</li>
            </c:forEach>
        </ul>

    </body>
</html>
