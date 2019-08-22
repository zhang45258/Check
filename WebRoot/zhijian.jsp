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
<style type="text/css">
input.text{text-align:center;}
</style>
  </head>
  
  <body>
	<h1>${xiaoxi}</h1>

   <form action="ZhijianServlet" method="post">


  <table  width="1020" border="1" cellpadding="0" >
  		<tr>
  			<th width="60">音轨ID</th>
  			<th width="60">情绪值</th>
  			<th width="60">语速</th>
	  		<th width="840">内容</th>
</tr>

      <c:forEach var="U" items="${zhijianAll}"  >
       <tr>
	       <td width="60"><input class="text" type="text" value="${U.channelId}" name="ChannelId" size="7" ></td>
			<td width="60"><input class="text" type="text" value="${U.emotionValue}" name="EmotionValue" size="7" ></td>
			<td width="60"><input class="text" type="text" value="${U.speechRate}" name="SpeechRate" size="7" ></td>
	       <td width="840"><input type="text" value="${U.text}" name="Text" size="130" > </td>
      </tr>
    </c:forEach>
    </table>
    
     <table id="tb" width="540" border="1" cellpadding="0" >
  		<tr>
  			<th width="60">呼叫ID</th>
  			<th width="60">工号</th>
  			
  			<th width="60">开头、结尾标准用语分</th>
  			<th width="60">标准用语“您”</th>
  			<th width="60">态度用语</th>
  			<th width="60">情绪分</th>
  			<th width="60">评价分</th>
  			<th width="60">分类标签</th>
	  		<th width="60">操作</th>
</tr>

       <tr>
	      <td width="60" id="td1"><input class="text" type="text" value="${callid}" name="callid" size="7" ></td>
	      <td width="60" id="td2"><input class="text" type="text" value="${agentid}" name="agentid" size="7" ></td>
	      
	       <td width="60" id="td4"><input class="text" type="text" value="${a}" name="a" size="7" ></td>
			<td width="60" id="td5"><input class="text" type="text" value="${b}" name="b" size="7" ></td>
			<td width="60" id="td6"><input class="text" type="text" value="${c}" name="c" size="7" ></td>
			<td width="60" id="td7"><input class="text" type="text" value="${d}" name="d" size="7" ></td>
		
			<td width="60" id="td7"><input class="text" type="text" value="${contentnum}" name="contentnum" size="7" ></td>
			<td width="60">
			<select  id="i1" name="biaoqian1">
<option value ="1111">1111.咨询—客运—车票—余票信息</option>
<option value ="1112">1112.咨询—客运—车票—预售期</option>    
<option value ="1113">1113.咨询—客运—车票—起售时间</option> 
<option value ="1114">1114.咨询—客运—车票—核验状态</option> 
<option value ="1115">1115.咨询—客运—车票—支付状态</option> 
<option value ="1116">1116.咨询—客运—车票—退票状态</option> 
<option value ="1117">1117.咨询—客运—车票—票价信息</option> 
<option value ="1118">1118.咨询—客运—车票—车票业务其他</option> 

<option value ="1121">1121.咨询—客运—代售点—位置查询</option> 
<option value ="1122">1122.咨询—客运—代售点—服务内容</option> 
<option value ="1123">1123.咨询—客运—代售点—营业时间</option>
<option value ="1124">1124.咨询—客运—代售点—代售点其他</option>  

<option value ="1131">1131.咨询—客运—车站—营业时间</option> 
<option value ="1132">1132.咨询—客运—车站—服务内容</option> 
<option value ="1133">1133.咨询—客运—车站—位置查询</option> 
<option value ="1134">1134.咨询—客运—车站—交通信息</option> 
<option value ="1135">1135.咨询—客运—车站—车站其他</option> 

<option value ="1141">1141.咨询—客运—列车—正晚点</option> 
<option value ="1142">1142.咨询—客运—列车—服务内容</option> 
<option value ="1143">1143.咨询—客运—列车—列车时刻</option> 
<option value ="1144">1144.咨询—客运—列车—列车其他</option> 

<option value ="1151">1151.咨询—客运—其他—客运其他咨询</option> 
<option value ="1152">1152.咨询—客运—其他—手机解绑</option> 
<option value ="1153">1153.咨询—客运—其他—骚扰短信</option> 
<option value ="1154">1154.咨询—客运—其他—手机验证码</option> 

<option value ="1211">1211.咨询—规章—客运—携带品</option> 
<option value ="1212">1212.咨询—规章—客运—儿童票</option> 
<option value ="1213">1213.咨询—规章—客运—学生票</option> 
<option value ="1214">1214.咨询—规章—客运—残疾人票</option> 
<option value ="1215">1215.咨询—规章—客运—改签退票</option> 
<option value ="1216">1216.咨询—规章—客运—行包运输</option> 
<option value ="1217">1217.咨询—规章—客运—团体票</option> 
<option value ="1218">1218.咨询—规章—客运—票务常识</option> 
<option value ="1219">1219.咨询—规章—客运—客运规章其他</option> 

<option value ="1221">1221.咨询—规章—电子支付—互联网购票</option> 
<option value ="1222">1222.咨询—规章—电子支付—TVM、POS机</option> 
<option value ="1223">1223.咨询—规章—电子支付—卡务信息</option> 

<option value ="1231">1231.咨询—规章—实名制—购检票</option> 
<option value ="1232">1232.咨询—规章—实名制—临时证明</option> 
<option value ="1233">1233.咨询—规章—实名制—身份核验</option> 
<option value ="1234">1234.咨询—规章—实名制—挂失补</option>    
<option value ="1235">1235.咨询—规章—实名制—实名制其他</option>  
  
<option value ="1241">1241.咨询—规章—电话订票—订取票</option>   
<option value ="1242">1242.咨询—规章—电话订票—信息查询</option>   
<option value ="1243">1243.咨询—规章—电话订票—电话订票其他</option>  
  
<option value ="1251">1251.咨询—规章—其他—规章制度其他</option> 

<option value ="1311">1311.咨询—延伸服务—车站—VIP候车</option> 
<option value ="1312">1312.咨询—延伸服务—车站—集团客户</option>  
<option value ="1313">1313.咨询—延伸服务—车站—行包运输</option> 
<option value ="1314">1314.咨询—延伸服务—车站—其他</option> 

<option value ="1321">1321.咨询—延伸服务—列车—VIP服务</option> 
<option value ="1322">1322.咨询—延伸服务—列车—餐饮</option> 
<option value ="1323">1323.咨询—延伸服务—列车—列车其他</option> 

<option value ="1331">1331.咨询—延伸服务—票务—票配送</option>
<option value ="1332">1332.咨询—延伸服务—票务—机票</option> 
<option value ="1333">1333.咨询—延伸服务—票务—团体票</option>   
<option value ="1334">1334.咨询—延伸服务—票务—其他</option>  

<option value ="1341">1341.咨询—延伸服务—预订—租车</option> 
<option value ="1342">1342.咨询—延伸服务—预订—旅行社</option> 
<option value ="1343">1343.咨询—延伸服务—预订—酒店</option> 
<option value ="1344">1344.咨询—延伸服务—预订—就医</option> 
<option value ="1345">1345.咨询—延伸服务—预订—预订其他</option> 

<option value ="1351">1351.咨询—延伸服务—服务—延伸服务其他</option> 

<option value ="1411">1411.咨询—常旅客—注册申请—申请</option>    
<option value ="1421">1421.咨询—常旅客—帐户信息查询—查询</option>   
<option value ="1431">1431.咨询—常旅客—受让人设置—受让人</option> 
<option value ="1441">1441.咨询—常旅客—积分兑换—积分</option> 

<option value ="15">15.咨询—其他服务咨询</option> 

<option value ="2111">2111.投诉—旅客运输—购票—服务态度</option> 
<option value ="2112">2112.投诉—旅客运输—购票—业务差错</option> 
<option value ="2113">2113.投诉—旅客运输—购票—标准落实</option> 
<option value ="2114">2114.投诉—旅客运输—购票—设备设施</option> 
<option value ="2115">2115.投诉—旅客运输—购票—违规收费</option> 
<option value ="2116">2116.投诉—旅客运输—购票—现场秩序</option> 
<option value ="2117">2117.投诉—旅客运输—购票—其他</option> 

<option value ="2121">2121.投诉—旅客运输—验证—服务态度</option> 
<option value ="2122">2122.投诉—旅客运输—验证—现场秩序</option> 
<option value ="2123">2123.投诉—旅客运输—验证—排队等候</option> 
<option value ="2124">2124.投诉—旅客运输—验证—其他</option> 

<option value ="2131">2131.投诉—旅客运输—安检—服务态度</option> 
<option value ="2132">2132.投诉—旅客运输—安检—标准落实</option> 
<option value ="2133">2133.投诉—旅客运输—安检—现场秩序</option> 
<option value ="2134">2134.投诉—旅客运输—安检—其他</option> 

<option value ="2141">2141.投诉—旅客运输—候车—服务态度</option> 
<option value ="2142">2142.投诉—旅客运输—候车—标准落实</option> 
<option value ="2143">2143.投诉—旅客运输—候车—设备设施</option> 
<option value ="2144">2144.投诉—旅客运输—候车—现场秩序</option> 
<option value ="2145">2145.投诉—旅客运输—候车—环境卫生</option> 
<option value ="2146">2146.投诉—旅客运输—候车—安全伤害</option> 
<option value ="2147">2147.投诉—旅客运输—候车—餐饮经营</option> 
<option value ="2148">2148.投诉—旅客运输—候车—售货经营</option> 
<option value ="2149">2149.投诉—旅客运输—候车—违规收费</option> 
<option value ="21410">21410.投诉—旅客运输—候车—售货其他</option> 

<option value ="2151">2151.投诉—旅客运输—检票—服务态度</option> 
<option value ="2152">2152.投诉—旅客运输—检票—业务差错</option> 
<option value ="2153">2153.投诉—旅客运输—检票—标准落实</option> 
<option value ="2154">2154.投诉—旅客运输—检票—设备设施</option> 
<option value ="2155">2155.投诉—旅客运输—检票—现场秩序</option> 
<option value ="2156">2156.投诉—旅客运输—检票—其他</option> 

<option value ="2161">2161.投诉—旅客运输—上车—服务态度</option> 
<option value ="2162">2162.投诉—旅客运输—上车—业务差错</option> 
<option value ="2163">2163.投诉—旅客运输—上车—标准落实</option> 
<option value ="2164">2164.投诉—旅客运输—上车—设备设施</option> 
<option value ="2165">2165.投诉—旅客运输—上车—现场秩序</option> 
<option value ="2166">2166.投诉—旅客运输—上车—安全伤害</option> 
<option value ="2167">2167.投诉—旅客运输—上车—售货经营</option> 
<option value ="2168">2168.投诉—旅客运输—上车—安全其他</option> 

<option value ="2171">2171.投诉—旅客运输—乘车—服务态度</option> 
<option value ="2172">2172.投诉—旅客运输—乘车—业务差错</option> 
<option value ="2173">2173.投诉—旅客运输—乘车—标准落实</option> 
<option value ="2174">2174.投诉—旅客运输—乘车—设备设施</option> 
<option value ="2175">2175.投诉—旅客运输—乘车—现场秩序</option> 
<option value ="2176">2176.投诉—旅客运输—乘车—环境卫生</option> 
<option value ="2177">2177.投诉—旅客运输—乘车—安全伤害</option> 
<option value ="2178">2178.投诉—旅客运输—乘车—餐饮经营</option> 
<option value ="2179">2179.投诉—旅客运输—乘车—售货经营</option> 
<option value ="21710">21710.投诉—旅客运输—乘车—违规收费</option> 
<option value ="21711">21711.投诉—旅客运输—乘车—其他</option> 

<option value ="2181">2181.投诉—旅客运输—下车—服务态度</option> 
<option value ="2182">2182.投诉—旅客运输—下车—业务差错</option> 
<option value ="2183">2183.投诉—旅客运输—下车—标准落实</option> 
<option value ="2184">2184.投诉—旅客运输—下车—设备设施</option> 
<option value ="2185">2185.投诉—旅客运输—下车—现场秩序</option> 
<option value ="2186">2186.投诉—旅客运输—下车—安全伤害</option> 
<option value ="2187">2187.投诉—旅客运输—下车—售货经营</option> 
<option value ="2188">2188.投诉—旅客运输—下车—其他</option> 

<option value ="2191">2191.投诉—旅客运输—客服中心—服务态度</option> 
<option value ="2192">2192.投诉—旅客运输—客服中心—业务差错</option> 
<option value ="2193">2193.投诉—旅客运输—客服中心—标准落实</option> 
<option value ="2194">2194.投诉—旅客运输—客服中心—设备设施</option> 
<option value ="2195">2195.投诉—旅客运输—客服中心—现场秩序</option> 
<option value ="2196">2196.投诉—旅客运输—客服中心—其他</option> 

<option value ="221">221.投诉—营销服务投诉—商旅服务</option> 
<option value ="222">222.投诉—营销服务投诉—电话购物</option> 
<option value ="223">223.投诉—营销服务投诉—人工订票</option> 
<option value ="224">224.投诉—营销服务投诉—其他</option> 

<option value ="2311">2311.投诉—电子支付—互联网购票—支付成功、购票不成功</option> 
<option value ="2312">2312.投诉—电子支付—互联网购票—网购重复支付</option> 
<option value ="2313">2313.投诉—电子支付—互联网购票—网购网退不到账</option> 
<option value ="2314">2314.投诉—电子支付—互联网购票—网购网改不到账</option> 
<option value ="2315">2315.投诉—电子支付—互联网购票—网购站退不到账</option> 
<option value ="2316">2316.投诉—电子支付—互联网购票—网购站改不到账</option> 

<option value ="2321">2321.投诉—电子支付—POS/TVM购票—支付成功，购票不成功</option> 
<option value ="2322">2322.投诉—电子支付—POS/TVM购票—站购重复支付</option> 
<option value ="2323">2323.投诉—电子支付—POS/TVM购票—站购站退不到账</option> 
<option value ="2324">2324.投诉—电子支付—POS/TVM购票—站购站改不到账</option> 

<option value ="24">24.投诉—客运其他投诉</option> 

<option value ="31">31.求助—寻物</option> 
<option value ="32">32.求助—寻人</option> 
<option value ="33">33.求助—特重预约</option> 
<option value ="34">34.求助—客运其它求助</option> 

<option value ="41">41.表扬—车站表扬</option> 
<option value ="42">42.表扬—列车表扬</option> 
<option value ="43">43.表扬—客运其他表扬</option>

<option value ="51">51.建议—设备设施</option>
<option value ="52">52.建议—运行图</option>
<option value ="53">53.建议—票务</option>
<option value ="54">54.建议—安全</option>
<option value ="55">55.建议—网站</option>
<option value ="56">56.建议—营销</option>
<option value ="57">57.建议—服务质量</option>
<option value ="58">58.建议—客运其他建议</option>

<option value ="61">61.其他—客运其他</option>

<option value ="7111">7111.行包—行包咨询—普通包裹—普通包裹办理范围</option> 
<option value ="7112">7112.行包—行包咨询—普通包裹—普通包裹包装要求</option> 
<option value ="7113">7113.行包—行包咨询—普通包裹—普通包裹运输费用</option> 
<option value ="7114">7114.行包—行包咨询—普通包裹—普通包裹保价运输</option> 
<option value ="7115">7115.行包—行包咨询—普通包裹—普通包裹运到期限</option> 
<option value ="7116">7116.行包—行包咨询—普通包裹—普通包裹到达保管</option> 
<option value ="7117">7117.行包—行包咨询—普通包裹—普通包裹装卸交付</option> 
<option value ="7118">7118.行包—行包咨询—普通包裹—普通包裹理赔处理</option> 
<option value ="7119">7119.行包—行包咨询—普通包裹—普通包裹变更运输</option> 
<option value ="71110">71110.行包—行包咨询—普通包裹—普通包裹押运规定</option> 
<option value ="71111">71111.行包—行包咨询—普通包裹—普通包裹其它</option> 

<option value ="7121">7121.行包—行包咨询—行李包裹—行李包裹办理范围</option> 
<option value ="7122">7122.行包—行包咨询—行李包裹—行李包裹包装要求</option> 
<option value ="7123">7123.行包—行包咨询—行李包裹—行李包裹运输费用</option> 
<option value ="7124">7124.行包—行包咨询—行李包裹—行李包裹保价运输</option> 
<option value ="7125">7125.行包—行包咨询—行李包裹—行李包裹运到期限</option> 
<option value ="7126">7126.行包—行包咨询—行李包裹—行李包裹到达保管</option> 
<option value ="7127">7127.行包—行包咨询—行李包裹—行李包裹理赔处理</option> 
<option value ="7128">7128.行包—行包咨询—行李包裹—行李包裹变更运输</option> 
<option value ="7129">7129.行包—行包咨询—行李包裹—行李包裹其他</option> 

<option value ="7131">7131.行包—行包咨询—快运包裹—快运包裹办理范围</option> 
<option value ="7132">7132.行包—行包咨询—快运包裹—快运包裹包装要求</option> 
<option value ="7133">7133.行包—行包咨询—快运包裹—快运包裹运输费用</option> 
<option value ="7134">7134.行包—行包咨询—快运包裹—快运包裹保价运输</option> 
<option value ="7135">7135.行包—行包咨询—快运包裹—快运包裹到达保管</option> 
<option value ="7136">7136.行包—行包咨询—快运包裹—快运包裹运到期限</option> 
<option value ="7137">7137.行包—行包咨询—快运包裹—快运包裹装卸交付</option> 
<option value ="7138">7138.行包—行包咨询—快运包裹—快运包裹理赔处理</option> 
<option value ="7139">7139.行包—行包咨询—快运包裹—快运包裹变更运输</option> 
<option value ="71310">71310.行包—行包咨询—快运包裹—快运包裹时限快运</option> 
<option value ="71311">71311.行包—行包咨询—快运包裹—快运包裹同城快递</option> 
<option value ="71312">71312.行包—行包咨询—快运包裹—快运包裹其他</option> 

<option value ="7141">7141.行包—行包咨询—高铁快运—高铁快运咨询</option> 
<option value ="7142">7142.行包—行包咨询—高铁快运—高铁快运其他咨询</option> 

<option value ="7151">7151.行包—行包咨询—其他咨询—其他咨询</option> 

<option value ="721">721.行包—行包查询—营业网点查询</option> 
<option value ="722">722.行包—行包查询—运输价格查询</option> 
<option value ="723">723.行包—行包查询—货物追踪查询</option> 
<option value ="724">724.行包—行包查询—其他查询</option> 

<option value ="7311">7311.行包—行包投诉—普通包裹投诉—普通包裹服务态度</option> 
<option value ="7312">7312.行包—行包投诉—普通包裹投诉—普通包裹违规收费</option> 
<option value ="7313">7313.行包—行包投诉—普通包裹投诉—普通包裹作业规范</option> 
<option value ="7314">7314.行包—行包投诉—普通包裹投诉—普通包裹滞留逾期</option> 
<option value ="7315">7315.行包—行包投诉—普通包裹投诉—普通包裹事故理赔</option> 
<option value ="7316">7316.行包—行包投诉—普通包裹投诉—普通包裹其他投诉</option> 

<option value ="7321">7321.行包—行包投诉—行李包裹投诉—行李包裹服务态度</option> 
<option value ="7322">7322.行包—行包投诉—行李包裹投诉—行李包裹违规收费</option> 
<option value ="7323">7323.行包—行包投诉—行李包裹投诉—行李包裹作业规范</option> 
<option value ="7324">7324.行包—行包投诉—行李包裹投诉—行李包裹滞留逾期</option> 
<option value ="7325">7325.行包—行包投诉—行李包裹投诉—行李包裹事故理赔</option> 
<option value ="7326">7326.行包—行包投诉—行李包裹投诉—行李包裹其他投诉</option> 

<option value ="7331">7331.行包—行包投诉—快运包裹投诉—快运包裹其他投诉</option> 
<option value ="7332">7332.行包—行包投诉—快运包裹投诉—快运包裹违规收费</option> 
<option value ="7333">7333.行包—行包投诉—快运包裹投诉—快运包裹作业规范</option> 
<option value ="7334">7334.行包—行包投诉—快运包裹投诉—快运包裹滞留逾期</option> 
<option value ="7335">7335.行包—行包投诉—快运包裹投诉—快运包裹事故理赔</option> 
<option value ="7336">7336.行包—行包投诉—快运包裹投诉—快运包裹其他投诉</option> 

<option value ="734">734.行包—行包投诉—其他投诉—其他</option> 

<option value ="74">74.行包—行包其他</option> 


            </select>
             </td>
			  <td width="60"><input type="button" value="保存" onclick="ulrHtml();">
<script type="text/javascript">
    //打开一个新的页面并传递参数
 function ulrHtml() {
  
 var callid = document.getElementById("td1").getElementsByTagName("INPUT")[0].value;
 var agentid = document.getElementById("td2").getElementsByTagName("INPUT")[0].value;

 var a = document.getElementById("td4").getElementsByTagName("INPUT")[0].value;
 var b = document.getElementById("td5").getElementsByTagName("INPUT")[0].value;
 var c = document.getElementById("td6").getElementsByTagName("INPUT")[0].value;
 var d = document.getElementById("td7").getElementsByTagName("INPUT")[0].value;
 
                     //首先获得下拉框的节点对象；
        var select = document.getElementById("i1");
        
        //1.如何获得当前选中的值？：
        var value = select.value;
       
        //2.如何获得该下拉框所有的option的节点对象
        var options = select.options;
        //注意：得到的options是一个对象数组
        
        //3.如何获得第几个option的value值?比如我要获取第一option的value,可以这样：
        var value1 = options[0].value;
        //4.如何获得第几个option的文本内容?比如我要获取第一option的文本,可以这样：
        var text1 = options[0].text;
        
        //5.如何获得当前选中的option的索引？
        var index = select.selectedIndex;
     
        //6.如何获得当前选中的option的文本内容？
        //从第2个问题，我们已经获得所有的option的对象数组options了
        //又从第5个问题，我们获取到了当前选中的option的索引值
        //所以我们只要同options[index]下标的方法得到当前选中的option了
        var selectedText = options[index].text;
        var selectedvalue = options[index].value;
       
             var toUrl = "BaocunServlet?callid="+callid+"&agentid="+agentid+"&a="+a+"&b="+b+"&c="+c+"&d="+d+"&biaoqian1="+selectedvalue;
             window.open(toUrl);
         }
</script>

	       </td>
       </tr>

    </table>
    
 <p><font size="5" face="arial" color="green">默认分类为"${e}"</font></p>  
     </form>

<p><font size="5" face="arial" color="red">打分说明</font></p>
<p><font size="3" face="arial" color="red">1.只为客服代表的语音打分。</font></p>
<p><font size="3" face="arial" color="red">2.开头、结尾标准用语分，检测是否说了"欢迎（或致电）/感谢（或来电，接听）"，检测出说了其中一个词就不扣分。</font></p>
<p><font size="3" face="arial" color="red">3.标准用语“您”，检测是否说了“你”，每一次扣2分。</font></p>
<p><font size="3" face="arial" color="red">4.态度用语，检测是否说了“说了很多次”，每一次扣2分。</font></p>
<p><font size="3" face="arial" color="red">5.情绪分，情绪平均值在5-7中，低于或超出这个范围就扣分。</font></p>
  <p><font size="3" face="arial" color="red">7.评价分，坐席非正常挂机导致旅客未评价0分，用户主动挂机导致未评价6分。旅客对处理结果不满意6分，对态度不满意2分，满意8分，非常满意10分。坐席正常挂机，旅客默认评价（实际上没有按键）8分。</font></p>

 
  
  
  
  </body>
  
  
</html>