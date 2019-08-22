<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="styles/basic.css" rel="stylesheet" type="text/css" />
    <title>download</title>
</head>
<%
   response.setCharacterEncoding("gb2312");
   request.setCharacterEncoding("gb2312");
   if ("C:/MyEclipse 2017 CI/Check/WebRoot/1.wav"!= null) {
     OutputStream os = null;
     FileInputStream fis = null;
    try {
       String file = "C:/MyEclipse 2017 CI/Check/WebRoot/1.wav";
      if (!(new File(file)).exists()) {
         System.out.println("没有文件");
        return;
       }
       System.out.println("文件名为："+file);
       os = response.getOutputStream();
       response.setHeader("content-disposition", "attachment;filename=" + file);
       response.setContentType("application/audio/wav");//此项内容随文件类型而异
      byte temp[] = new byte[1000];
       fis = new FileInputStream(file);
      int n = 0;
      while ((n = fis.read(temp)) != -1) {
         os.write(temp, 0, n);
       }
     } catch (Exception e) {
       out.print("出错");
     } finally {
      if (os != null)
         os.close();
      if (fis != null)
         fis.close();
     }
     out.clear();
     out = pageContext.pushBody();
   }
%>
<form action="" method="post">
   <select name="file">
     <option value="D:\Program Files\apache-tomcat-6.0.18\webapps\StarAttendance\upload/temp.wav">
       冷山sky_snow
     </option>
   </select>
   <input type="submit"/>
</form>  
</html>
