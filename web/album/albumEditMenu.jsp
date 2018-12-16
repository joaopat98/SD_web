<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 14-12-2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="Album" type="Server.AlbumDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="albumEdit" method="post">
            <p>Album ID: ${Album.albumID}</p>
            <s:text name="Album Name:"/>
            <s:textfield name="albumName" value="%{#session.Album.albumName}"/><br/>
            <s:text name="Artist:"/>
            <s:select list="%{#session.ArtistList}"
                      name="artistID"
                      listKey="artistID"
                      listValue="option"
                      value="%{#session.Album.artistID}"/><br>
            <s:text name="Label:"/>
            <s:select list="%{#session.LabelList}"
                      name="labelID"
                      listKey="labelID"
                      listValue="option"
                      value="%{#session.Album.labelID}"/><br>
            <s:text name="Description:"/>
            <s:textfield name="description" value="%{#session.Album.description}"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
