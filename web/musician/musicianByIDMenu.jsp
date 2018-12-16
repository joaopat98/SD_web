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
        <h2>Get a musician by ID</h2>
        <s:form action="musicianDetail" method="post">
            <s:text name="Musician ID:"/>
            <s:textfield name="musicianID"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
