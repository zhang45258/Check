<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
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
	</style>
</head>
<body>
 	<!-- 显示框，是一个无序列表 --> 
	<ul class="items .clearfix" id= "small_box" style="width: 600px; height:300px"></ul> 
	
 	<!-- 输入框，是一个textarea  --> 
	<div style="position: relative;display: inline-block;left:24.7%;">
	<textarea name="" id="wy" cols="30" rows="15" style="width: 600px; height: 150px"></textarea>
	
	<!-- 确定按钮  --> 
	<button id="btn" style="position: absolute;right: 0px;bottom: 5px;">发送</button>
	</div>
</body>

	<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
	<script type="text/javascript">
		//这是把框往下拉，显示最下面的
		function boxScroll(o){
    		o.scrollTop = o.scrollHeight;
    		console.log(o.scrollTop)
		}
 
        //下面是点击发送按钮的处理
		$("#btn").click(function(){ 
			var textwb = $("#wy").val();  // 获得 输入框 的值
			
			//添加一个提问者显示框
			//1.先添加一个块
			//2.添加图片
			//3.添加名称
			//4.添加说话内容
			
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
			neirong.css({"width":"550px","border-radius":"10%","background":"gray"});
			neirong.html(textwb);

			//添加回答内容
			//1.post方式发送json到HttpApiServlet
			//2.接收json
			//3.添加回答
           $.post(
			"HttpApiServlet",
			{txt:txt2},
			function(data){
			
			//添加块
			var Lis2 = $("<li></li>");     //定义一个<li></li>之间的元素，列表内行样式
			Lis2.css({"display":"inline-block"});  //nline-block主要的用处是用来处理行内非替换元素的高宽问题的！
			//行内非替换元素，比如span、a等标签，正常情况下士不能设置宽高的，加上该属性之后，就可以触发让这类标签表现得如块级元素一样，可以设置宽高。
			var ch2 = $(".items").append(Lis2); //所有带有"items"属性的元素，都追加 Lis 元素
			//此处将 “显示框”添加  Lis元素
			
			//添加图片
			var igg2 = $('<img src="" alt="" class="iii"/>'); //定义一个图片元素
			var img2 = Lis2.append(igg2);//Lis元素插入图片
			var arr2 = 'image/f2.jpg';//定义提问者图片
			igg2.attr({src:arr2});//为图片设置路径
			igg2.css({"width":"40px","height":"40px","float":"right"}); //设置图片的样式
						
			//添加昵称
			var rightdiv2 = $('<div class="he2"></div>'); //右边盒子（所谓盒子，就是放置内容的块，这一块是用来放昵称的）
			Lis2.append(rightdiv2);  //插入图片右边的盒子
			var brr2 = "自动问答机器人"; //定义提问者名称
			rightdiv2.css({"display":"inline-block","float":"right"});//设置为行内快元素
			var pp2 = $('<p class="ji"></p>');//创建一个p标签
			pp2.css({"color":"blue"});
			rightdiv2.append(pp);//插入右边盒子的p
			pp2.html(brr2);//插入昵称
			
			//添加内容
			var neirong2 = $("<p class='ps'></p>");//创建昵称盒子下的内容盒子
			$(".he2").append(neirong2);
			neirong.css({"width":"550px","border-radius":"10%","background":"gray"});
			neirong.html(textwb);
			
			
			//$("#d1").append("\n"+"问："+txt2);
			//$("#d1").append("\n"+"答："+data.result01);
			//$("#d1").append("\n"+"不是您想询问的？您询问的是不是：");
			//$("#d1").append("\n"+"1."+data.result10);
			//$("#d1").append("\n"+"2."+data.result20);
			//$("#d1").append("\n"+"3."+data.result30+"\n");
			
			},"json"
		   );







			//最后文本框内容清空
			$("#wy").val("");
			boxScroll(small_box)
			

			
			
		});
	</script>
</html>
