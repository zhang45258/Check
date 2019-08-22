<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ page import="java.net.URLEncoder" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  <script language="javascript" type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>

     <base href="<%=basePath%>">
      <title>录音页面</title>
</head>

<body>
  <font size="4" face="arial" color="red">步骤1：</font>
  <a href="http://10.208.10.20:8080/Check/upload.jsp" target="_blank">点击填写班时（否则无绩效分数）</a><br><br>
  
    <font size="4" face="arial" color="red">步骤2：查询数据</font>
    <form action="JtonglvServlet" method="post"  style="padding-top:-1200px;"> 
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   日期：<input type="text" name="riqi"value="" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMMdd',
minDate:'2008-03-08'})">&nbsp;&nbsp;&nbsp;
    <input type="submit"value="查询" name="chaxun"><br><br>
   <font size="4" face="arial" color="red">步骤3：</font>
<a href="http://10.208.10.20:8080/Check/down.jsp">点击下载接续情况报表</a> 
    
    <br />


 <h3>时段接通率表</h3>
  
   <table  width="600" border="1" cellpadding="0" >
  		
  		<tr>
  			<th>时段</th>
	  		<th>人工呼入数</th>
	  		<th>实际接通数</th>
	  		<th>人工接通率</th>

  		</tr>  


 <c:forEach var="U" items="${jitonglvAll}"  > 

       <tr>
	       <td><input type="text" value="${U.shiduan}" name="shiduan" ></td>
	       <td><input type="text" value="${U.rengongshurushu}" name="rengonghurushu"></td>
	       <td><input type="text" value="${U.shijijietongshu}" name="shijijietongshu"></td>
	       <td><input type="text" value="${U.rengongjietonglv}" name="rengongjietonglv"></td>
</tr>
   
   </c:forEach>  
      </table>
        <h3>客服代表接续情况表</h3>

    <table  width="600" border="1" cellpadding="0" >
  		<tr>
  			<th>工号</th>
	  		<th>签入时长</th>
	  		<th>通话时长</th>
	  		<th>等待时长</th>
<th>示忙时长</th>
<th>整理时长</th>
<th>工时利用率</th>
  		</tr>


 <c:forEach var="J" items="${jiexuAll}"  > 
           
       <tr>
        <td><input type="text" value="${J.AGENTID}" name="AGENTID" ></td>
	       <td><input type="text" value="${J.qianrushichang}" name="qianrushichang" ></td>
	       <td><input type="text" value="${J.tonghuashichang}" name="tonghuashichang"></td>
	       <td><input type="text" value="${J.dengdaishichang}" name="dengdaishichang"></td>
	       <td><input type="text" value="${J.shimangshichang}" name="shimangshichang"></td>
	       <td><input type="text" value="${J.zhenglishichang}" name="zhenglishichang"></td>
	       <td><input type="text" value="${J.gongshiliyonglv}" name="gongshiliyonglv"></td>
</tr>
   
    </c:forEach>  
   
   
    </table>
     </form> 
  </body>

  
</html>