<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'success.jsp' starting page</title>
  </head>
  <body>
			${xiaoxi} <br>  
			<a href="showall.jsp">查看所有用户</a>
			<a href="luyin.jsp">查看录音质检</a>
			<a href="jitonglv.jsp">客服中心工作数据日分析</a>
  </body>
</html>