<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 14-12-2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="Musician" type="Server.MusicianDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="musicianEdit" method="post">
            <p>Musician ID: ${Musician.musicianID}</p>
            <s:text name="Musician Name:"/>
            <s:textfield name="musicianname" value="%{#session.Musician.musicianName}"/><br/>
            <s:text name="Description:"/>
            <s:textfield name="description" value="%{#session.Musician.description}"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
