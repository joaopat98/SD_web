<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 12:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Register</title>
    </head>
    <body>
        <s:form action="register" method="post">
            <s:text name="Username:"/>
            <s:textfield name="username"/><br/>
            <s:text name="Password:"/>
            <s:textfield name="password"/><br/>
            <s:submit/>
        </s:form>
        <p><a href="<s:url action="index" />">Login</a></p>
    </body>
</html>
