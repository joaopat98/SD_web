<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--@elvariable id="Song" type="Server.SongDetails"--%>
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
        <h1>Song Details</h1>
        <h2>Info:</h2>
        <p><a href="<s:url action="songEditMenu" />">Edit</a></p>
        <p>Song ID: ${Song.songID}</p>
        <p>Name: ${Song.name}</p>
        <p>Lyric Writer ID: ${Song.lyricWriterID}</p>
        <p>Lyric Writer Name: ${Song.lyricWriterName}</p>
        <p>Length: ${Song.lengthFormatted}</p>
        <p>Lyrics: ${Song.lyrics}</p>
        <p>Genre: ${Song.genre}</p>
        <h2>Composers:</h2>
        <ul>
            <c:forEach items="${Song.memberList}" var="composer">
                <li>Name: ${composer.name}; Musician ID: ${composer.musician_id}</li>
            </c:forEach>
        </ul>

        <h2>Appears in:</h2>
        <ul>
            <c:forEach items="${Song.albumList}" var="album">
                <li>Name: ${album.name}; Album ID: ${album.albumID}</li>
            </c:forEach>
        </ul>

    </body>
</html>
