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
        <h2>Search for a studio</h2>
        <s:form action="searchStudio" method="post">
            <s:text name="Query:"/>
            <s:textfield name="query"/><br/>
            <s:submit/>
        </s:form>
        <%--@elvariable id="StudioSearch" type="Server.SearchStudios"--%>
        <c:forEach items="${StudioSearch.studioList}" var="studio">
            <p>Name: <c:out value="${studio.name}"/>; Studio ID: <c:out value="${studio.studioID}"/>
                <a href="
                    <s:url action="studioDetail">
                        <s:param name="studioID">${studio.studioID}</s:param>
                    </s:url>
                ">...</a>
            </p>
        </c:forEach>
    </body>
</html>
