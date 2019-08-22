<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
</head>
<body>
<h1>填写班时</h1>
<font size="4" face="arial" color="red">步骤1：</font>
 <a href="http://10.208.10.20:8080/Check/down1.jsp">下载模板填写班时</a> <br> <br> 
<font size="4" face="arial" color="red">步骤2：</font>
<form method="post" action="UploadServlet" enctype="multipart/form-data">
    选择文件:
    <input type="file" name="uploadFile" />

 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <input type="submit" value="上传班时模板" />
</form>
</body>
</html>
