<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: joaopat98
  Date: 13-12-2018
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <h2>Search for a label</h2>
        <s:form action="searchLabel" method="post">
            <s:text name="Query:"/>
            <s:textfield name="query"/><br/>
            <s:submit/>
        </s:form>
        <%--@elvariable id="LabelSearch" type="Server.SearchLabels"--%>
        <c:forEach items="${LabelSearch.labelList}" var="label">
            <p>Name: <c:out value="${label.name}"/>; Label ID: <c:out value="${label.labelID}"/>
                <a href="
                    <s:url action="labelDetail">
                        <s:param name="labelID">${label.labelID}</s:param>
                    </s:url>
                ">...</a>
            </p>
        </c:forEach>
    </body>
</html>
