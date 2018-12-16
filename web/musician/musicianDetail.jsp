<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--@elvariable id="Musician" type="Server.MusicianDetails"--%>
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
        <h1>Musician Details</h1>
        <h2>Info:</h2>
        <p><a href="<s:url action="musicianEditMenu" />">Edit</a></p>
        <p>Musician ID: ${Musician.musicianID}</p>
        <p>Name: ${Musician.name}</p>
        <p>Description: ${Musician.description}</p>

    </body>
</html>
