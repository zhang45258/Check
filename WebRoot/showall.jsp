<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>所有用户页面</title>
  </head>
  
  <body>
  <h1>${xiaoxi}</h1>
  
  
   <form action="SearchallServlet" method="post"> 
   <input type="submit"value="显示"name="denglu">
  
  <table  width="600" border="1" cellpadding="0" >
  		<tr>
  			<th>ID</th>
	  		<th>姓名</th>
	  		<th>性别</th>
	  		<th>密码</th>
	  		<th>家乡</th>
	  		<th>备注</th>
	  		<th>操作</th>
  		</tr>
     
     
      <c:forEach var="U" items="${userAll}"  > 
      
       
       <tr>
	       <td><input type="text" value="${U.id}" name="id" ></td>
	       <td><input type="text" value="${U.name}" name="name"></td>
	       <td><input type="text" value="${U.sex}" name="sex"></td>
	       <td><input type="text" value="${U.pwd}" name="pwd"></td>
	       <td><input type="text" value="${U.home}" name="home"></td>
	       <td><input type="text" value="${U.info}" name="info"></td>
	       <td>
	       <a href="DeleteServlet?id=${U.id}">删除</a>  
	       <a href="UpdateServlet?id=${U.id}&name=${U.name}&sex=${U.sex}&pwd=${U.pwd}&home=${U.home}&info=${U.info}"></a>
	       </td>
	   </tr>
    
    </c:forEach>  
   
    </table>
     </form> 
  </body>
</html>