<%--
  Created by IntelliJ IDEA.
  User: Coder Wang
  Date: 2020/7/28
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>首页</title>
  </head>
  <body>
  <c:choose>
    <c:when test="${username==null}">
      <a href="register.jsp">注册</a><br/>
      <a href="login.jsp">登录</a><br/>
    </c:when>
    <c:otherwise>
      <a href="add.jsp">添加学生信息</a><br/>
      <a href="delete.jsp">删除学生信息</a><br/>
      <a href="update.jsp">修改学生信息</a><br/>
      <a href="/look">查看学生信息</a><br/>
    </c:otherwise>
  </c:choose>
  </body>
</html>
