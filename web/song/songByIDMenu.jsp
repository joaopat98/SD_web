<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h2>Get a song by ID</h2>
        <s:form action="songDetail" method="post">
            <s:text name="Song ID:"/>
            <s:textfield name="songID"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
