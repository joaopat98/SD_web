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
        <h2>Get a artist by ID</h2>
        <s:form action="artistDetail" method="post">
            <s:text name="Artist ID:"/>
            <s:textfield name="artistID"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
