<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 14-12-2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="Artist" type="Server.ArtistDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="artistEdit" method="post">
            <p>Artist ID: ${Artist.artistID}</p>
            <s:text name="Artist Name:"/>
            <s:textfield name="artistname" value="%{#session.Artist.artistName}"/><br/>
            <s:text name="Description:"/>
            <s:textfield name="description" value="%{#session.Artist.description}"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
