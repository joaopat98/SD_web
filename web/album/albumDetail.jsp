<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--@elvariable id="Album" type="Server.AlbumDetails"--%>
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
        <h1>Album Details</h1>
        <h2>Info:</h2>
        <p><a href="<s:url action="albumEditMenu" />">Edit</a></p>
        <p>Album ID: ${Album.albumID}</p>
        <p>Album Name: ${Album.albumName}</p>
        <p>Artist ID: ${Album.artistID}</p>
        <p>Artist Name: ${Album.artistName}</p>
        <p>Label ID: ${Album.labelID}</p>
        <p>Label Name: ${Album.labelName}</p>
        <p>Description: ${Album.description}</p>
        <h2>Songs: </h2>
        <s:form action="addSongToAlbum" method="POST">
            <s:select list="%{#session.SongList}"
                      name="songID"
                      listKey="songID"
                      listValue="name"/>
            <s:submit/>
        </s:form>
        <ul>
            <c:forEach items="${Album.songList}" var="song">
                <li>Name: ${song.name}; Song ID: ${song.songID}</li>
            </c:forEach>
        </ul>
        <h2>Recorded in: </h2>
        <ul>
            <c:forEach items="${Album.studioList}" var="studio">
                <li>Name: ${studio.name}; Studio ID: ${studio.name}</li>
            </c:forEach>
        </ul>

    </body>
</html>
