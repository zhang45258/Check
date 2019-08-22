<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'zhishiku.jsp' starting page</title>
    <meta charset="UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
		*{
			margin:0px;
			padding: 0px;
		}
		#btn{
			width: 60px;
			height:30px;
			line-height: 30px;
			color: white;
			text-align: center;
			background:pink;
		}
		.box{
			height: 200px;
			margin:0 auto;
			width: 227px;
			}
		.items{
			margin:0 auto;
			width: 227px;
			height: 200px;
			overflow: auto;
			border: 1px solid black;
			position: relative;
 
		}
		ul{
			list-style: none;
			content: "";
			clear: both;
			height: 45px;
		}
		.clearfix:after{
			content: "";
			display: block;
			clear: both;
		}
		li{
			list-style: none;
			content: "";
			clear: both;
			
		}
		.clearfix:after{
			content: "";
			display: block;
			clear: both;
		}
		.pointer{

		cursor: pointer;

		}
	</style>
<script src="jquery-3.4.1.min.js"></script>
<script>
var no = time() +""+parseInt(100*Math.random());//问题no，格式是时间+2位的随机数
var textwb = "";//用于点击“发送”按键时，发送的文本
//获取系统时间，用于产生问题no
function time(){
		//判断是否在前面加0
		function getNow(s) {
		return s < 10 ? '0' + s: s;
		}
		var myDate = new Date();             
		var year=myDate.getFullYear();        //获取当前年
		var month=myDate.getMonth()+1;        //获取当前月
		var date=myDate.getDate();            //获取当前日
		var h=myDate.getHours();              //获取当前小时数(0-23)
		var m=myDate.getMinutes();          //获取当前分钟数(0-59)
		var s=myDate.getSeconds();
		var now=year+getNow(month)+getNow(date)+getNow(h)+getNow(m)+getNow(s);
		return now
		}


//上方显示框拉到最下面
function boxScroll(o){
    	o.scrollTop = o.scrollHeight;
    	console.log(o.scrollTop)
}

//添加一个提问者显示框
function tiwen(txt){
	    //添加块
		var Lis = $("<li></li>");     //定义一个<li></li>之间的元素，列表内行样式
		Lis.css({"display":"inline-block"});  //nline-block主要的用处是用来处理行内非替换元素的高宽问题的！
		//行内非替换元素，比如span、a等标签，正常情况下士不能设置宽高的，加上该属性之后，就可以触发让这类标签表现得如块级元素一样，可以设置宽高。
		var ch = $(".items").append(Lis); //所有带有"items"属性的元素，都追加 Lis 元素
		//此处将 “显示框”添加  Lis元素
		
		//添加图片
		var igg = $('<img src="" alt="" class="iii"/>'); //定义一个图片元素
		var img = Lis.append(igg);//Lis元素插入图片
		var arr = 'image/f1.jpg';//定义提问者图片
		igg.attr({src:arr});//为图片设置路径
		igg.css({"width":"40px","height":"40px","float":"left"}); //设置图片的样式
					
		//添加昵称
		var rightdiv = $('<div class="he"></div>'); //右边盒子（所谓盒子，就是放置内容的块，这一块是用来放昵称的）
		Lis.append(rightdiv);  //插入图片右边的盒子
		var brr = "我"; //定义提问者名称
		rightdiv.css({"display":"inline-block","float":"left"});//设置为行内快元素
		var pp = $('<p class="ji"></p>');//创建一个p标签
		pp.css({"color":"blue"});
		rightdiv.append(pp);//插入右边盒子的p
		pp.html(brr);//插入昵称
		
		//添加内容
		var neirong = $("<p class='ps'></p>");//创建昵称盒子下的内容盒子
		$(".he").append(neirong);
		neirong.css({"width":"550px"});
		neirong.html(txt);
		
		
		//文本框内容清空		
		$("#wy").val("");
		}

//添加一个回答者显示框
function huida(txt){
	    //添加块
		var Lis = $("<li></li>");     //定义一个<li></li>之间的元素，列表内行样式
		Lis.css({"display":"inline-block"});  //nline-block主要的用处是用来处理行内非替换元素的高宽问题的！
		//行内非替换元素，比如span、a等标签，正常情况下士不能设置宽高的，加上该属性之后，就可以触发让这类标签表现得如块级元素一样，可以设置宽高。
		var ch = $(".items").append(Lis); //所有带有"items"属性的元素，都追加 Lis 元素
		//此处将 “显示框”添加  Lis元素
		
		//添加图片
		var igg = $('<img src="" alt="" class="iii"/>'); //定义一个图片元素
		var img = Lis.append(igg);//Lis元素插入图片
		var arr = 'image/f2.jpg';//定义提问者图片
		igg.attr({src:arr});//为图片设置路径
		igg.css({"width":"40px","height":"40px","float":"left"}); //设置图片的样式
					
		//添加昵称
		var rightdiv = $('<div class="he"></div>'); //右边盒子（所谓盒子，就是放置内容的块，这一块是用来放昵称的）
		Lis.append(rightdiv);  //插入图片右边的盒子
		rightdiv.css({"display":"inline-block","float":"left"});//设置为行内快元素
		var pp = $('<p class="ji"></p>');//创建一个p标签
		pp.css({"color":"blue"});
		rightdiv.append(pp);//插入右边盒子的p
		var brr = "客服机器人"; //定义提问者名称
		pp.html(brr);//插入昵称
		
		//添加内容
		var neirong = $("<p class='ps'></p>");//创建昵称盒子下的内容盒子
		$(".he").append(neirong);
		neirong.css({"width":"550px"});
		neirong.html(txt);
		
		//显示框的条拉到最下方
		boxScroll(small_box);
		}

//添加一个补充框
function buchong(txt10,txt11,txt12,txt20,txt21,txt22,txt30,txt31,txt32,txt,txt00){
	    //添加块
		var Lis = $("<li></li>");     //定义一个<li></li>之间的元素，列表内行样式
		Lis.css({"display":"inline-block"});
		var ch = $(".items").append(Lis); 
		
 		//添加昵称
		var rightdiv = $('<div class="he"></div>'); 
		Lis.append(rightdiv);  
		rightdiv.css({"display":"inline-block"});//设置为行内快元素
		var pp = $('<p class="ji"></p>');//创建一个p标签
		pp.css({"color":"red"});
		rightdiv.append(pp);//插入右边盒子的p
		var brr = "不是您想询问的？您询问的是不是：（可点击下方问题）"; 
		pp.html(brr);//插入昵称
				
		//添加内容
		var neirong1 = $("<p class='pointer'></p>");//创建昵称盒子下的内容盒子
		$(".he").append(neirong1);
		neirong1.css({"width":"600px"});
		neirong1.html("1."+txt10+"("+txt11+")");
		neirong1.click(function(){
								huida(txt12)
								dianjijilu(txt,txt10,txt00,txt20,txt30)
								});
		//添加一个空白行
		var neirong = $("<p></p>");//
		$(".he").append(neirong);
		neirong.css({"width":"600px","height":"5px"});
				
		//添加内容
		var neirong2 = $("<p class='pointer'></p>");//创建昵称盒子下的内容盒子
		$(".he").append(neirong2);
		neirong2.css({"width":"600px"});
		neirong2.html("2."+txt20+"("+txt21+")");
		neirong2.click(function(){
								huida(txt22)
								dianjijilu(txt,txt20,txt00,txt10,txt30)
								});
		//添加一个空白行
		var neirong = $("<p></p>");
		$(".he").append(neirong);
		neirong.css({"width":"600px","height":"5px"});
		
		//添加内容
		var neirong3 = $("<p class='pointer'></p>");//创建昵称盒子下的内容盒子
		$(".he").append(neirong3);
		neirong3.css({"width":"600px"});
		neirong3.html("3."+txt30+"("+txt31+")");
		neirong3.click(function(){
								huida(txt32)
								dianjijilu(txt,txt30,txt00,txt10,txt20)
								});
		//添加一个空白行
		var neirong = $("<p></p>");
		$(".he").append(neirong);
		neirong.css({"width":"600px","height":"5px"});
							
		//显示框的条拉到最下方
		boxScroll(small_box);
		}

//记录鼠标操作,把鼠标操作反馈给服务器
function dianjijilu(txt,txt1,txt2,txt3,txt4){
$.post(
			"HttpApiBackServlet",
			{no:no,txt:txt,txt1:txt1,txt2:txt2,txt3:txt3,txt4:txt4},
			"json"
	   		);
		}
$(document).ready(function(){

//下面是点击发送按钮的处理
	$("#btn").click(function(){ 
		async: false,
		textwb = $("#wy").val() +"$$$"+textwb;  //把输入的问题保存起来，一起发给后台
		tiwen($("#wy").val())  //获得 输入框 的值，并把输入框清空
		$.post(
			"HttpApiServlet",
			{no:no,txt:textwb},
			function(data){
					huida(data.result02)
					buchong(data.result10,data.result11,data.result12,data.result20,data.result21,data.result22,data.result30,data.result31,data.result32,textwb,data.result00)
					},"json"
	   		);
	});
		//下面是点击发送按钮的处理
});
		


		
		

</script>
  </head>

 <body>
    <!-- 显示框，是一个无序列表 --> 
	<ul class="items .clearfix" id= "small_box" style="width: 600px; height:300px"></ul> 
	
 	<!-- 输入框，是一个textarea  --> 
	<div style="position: relative;display: inline-block;left:30.7%;">
	<textarea name="" id="wy" cols="30" rows="15" style="width: 600px; height: 150px"></textarea>
	
	<!-- 确定按钮  --> 
	<button id="btn" style="position: absolute;right: 0px;bottom: 5px;">发送</button>
	</div>


  </body>
</html>
