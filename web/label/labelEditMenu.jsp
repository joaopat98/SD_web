<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 14-12-2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="Label" type="Server.LabelDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="labelEdit" method="post">
            <p>Label ID: ${Label.labelID}</p>
            <s:text name="Label Name:"/>
            <s:textfield name="labelname" value="%{#session.Label.labelName}"/><br/>
            <s:text name="Description:"/>
            <s:textfield name="description" value="%{#session.Label.description}"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
