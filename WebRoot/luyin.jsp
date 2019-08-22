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
  <h1>${xiaoxi}</h1>


	 
<form action="LuyinServlet" method="post"  style="padding-top:-1200px;"> 
 起始日期：<input  type="text" name="begintimeqixian" value="" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMMdd',
minDate:'2008-03-08'})"><br><br>

 结束日期：<input type="text" name="endtimeqixian"value="" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyyMMdd',
minDate:'2008-03-08'})"><br><br> 
   工号：<input type="text" name="agentid"value=""><br><br> 
 <input type="submit"value="查询"name="chaxun">
<table  width="1260" border="1" cellpadding="0" >
  		<tr>
  			<th width="100">呼叫ID</th>
	  		<th width="120">主叫号码</th>
	  		<th width="90">被叫号码</th>
	  		<th width="90">座席工号</th>
	  		<th width="140">开始时间</th>
	  		<th width="140">结束时间</th>
	  		<th width="90">录音文件名</th>
	  		<th width="90">技能队列</th>
	  		<th width="190">旅客评价</th>
	  		<th width="100">录音回放</th>
	  		<th width="110">质检操作</th>	  		
  		</tr>
     
     
      <c:forEach var="U" items="${luyinAll}"  > 
         
       <tr>
	       <td width="100"><input type="text" value="${U.callid}" name="callid" style="width:100px"></td>
	       <td width="120"><input type="text" value="${U.callerno}" name="callerno"style="width:120px"></td>
	       <td width="90"><input type="text" value="${U.calleeno}" name="calleeno"style="width:90px"></td>
	       <td width="90"><input type="text" value="${U.agentid}" name="agentid"style="width:90px"></td>
	       <td width="140"><input type="text" value="${U.begintime}" name="begintime"style="width:140px"></td>
	       <td width="140"><input type="text" value="${U.endtime}" name="endtime"style="width:140px"></td>
	       <td width="90"><input type="text" value="${U.filename}" name="filename"style="width:90px"></td>
	       <td width="90"><input type="text" value="${U.currentskillid}" name="currentskillid"style="width:90px"></td>
	        <td width="190"><input id = "content" type="text" value="${U.contentchina}" name="contentchina" style="width:190px"></td>

	      <td width="100">
	       <a style="width:100px" href="LuyinhuifangServlet?filename=${U.filename}"  target="_blank">回放</a>
      	    </td>
	       <td width="110">
	       <a style="width:110px" href="ZhijianServlet?filename=${U.filename}&callid=${U.callid}&agentid=${U.agentid}&content=${U.content}"  target="_blank">质检分析</a>
      	    </td>
	   </tr>
   
    </c:forEach>  
   
    </table>
     </form> 
        <p><font size="3" face="arial" color="red">质检分析时间较长（需要抓取录音，上传阿里云服务、转文字），点击后请等待约1分钟（如语音长则时间更久）</font></p>
       
        
  </body>
  
</html>