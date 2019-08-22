<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <base href="<%=basePath%>">
    <title>单项分析结果页</title>

  </head>
  
  <body onload="clock();">
	<h1>${xiaoxi}</h1>
	<script>

function clock()
{i=i-1 
document.title="本窗口将在"+i+"秒后自动关闭!"; 
if(i>0)setTimeout("clock();",1000); 
else self.close();
} 
var i=3; 
clock(); 
</script>

  </body>
</html>