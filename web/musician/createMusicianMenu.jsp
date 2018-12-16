<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="createMusician" method="post">
            <s:text name="Musician name:"/>
            <s:textfield name="musicianname"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
