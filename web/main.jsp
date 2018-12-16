<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 1:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <p><a href="<s:url action="artistMenu" />">Artist Menu</a></p>
        <p><a href="<s:url action="albumMenu" />">Album Menu</a></p>
        <p><a href="<s:url action="songMenu" />">Song Menu</a></p>
        <p><a href="<s:url action="labelMenu" />">Label Menu</a></p>
        <p><a href="<s:url action="studioMenu" />">Studio Menu</a></p>
        <p><a href="<s:url action="musicianMenu" />">Musician Menu</a></p>
        <p><a href="<s:url action="loadUserMenu" />">User Many</a></p>
    </body>
</html>
