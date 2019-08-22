<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传结果</title>
</head>
<body onload="clock();">
    <center>
        <h2>${message}</h2>
    </center>
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