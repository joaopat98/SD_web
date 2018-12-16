<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 14-12-2018
  Time: 13:28
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="Studio" type="Server.StudioDetails"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <s:form action="studioEdit" method="post">
            <p>Studio ID: ${Studio.studioID}</p>
            <s:text name="Studio Name:"/>
            <s:textfield name="studioname" value="%{#session.Studio.studioName}"/><br/>
            <s:text name="Description:"/>
            <s:textfield name="description" value="%{#session.Studio.description}"/><br/>
            <s:submit/>
        </s:form>
    </body>
</html>
