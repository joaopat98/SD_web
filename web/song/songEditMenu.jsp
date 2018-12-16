<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 14-12-2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="Song" type="Server.SongDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="songEdit" method="post">
            <p>Song ID: ${Song.songID}</p>
            <s:text name="Song Name:"/>
            <s:textfield name="songname" value="%{#session.Song.name}"/><br/>
            <s:text name="Length:"/>
            <s:textfield name="length" value="%{#session.Song.length}"/><br/>
            <s:text name="Lyrics:"/>
            <s:textfield name="lyrics" value="%{#session.Song.lyrics}"/><br/>
            <s:text name="Genre:"/>
            <s:textfield name="genre" value="%{#session.Song.genre}"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
