package com.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import linuxToWindows.DownLoadFile;
import UpDown.*;
import ai.Api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nls.client.example.*;
import com.dao.LuyinDao;
import com.dao.LuyinDaoImpl;
import com.dao.ZhijianDao;
import com.dao.ZhijianDaoImpl;
import com.entity.Luyin;
import com.entity.Zhijian;


public class ZhijianServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("xiaoxi", "准备执行");
		String filename = request.getParameter("filename");
		String callid = request.getParameter("callid");
		String agentid = request.getParameter("agentid");
		String content= request.getParameter("content");
		
		//System.out.println("content:"+content);
		int contentnum = 0;//评价分
		switch(content)
		{
		case "6":
			contentnum =0;
			break;
		case "1":
			contentnum=10;
			break;
		case "2":
			contentnum=8;
			break;  
		case "3":
			contentnum=2;
			break;  
		case "4":
			contentnum=6;
			break;  
		case "5":
			contentnum=6;
			break;                       
		default:
			contentnum=8;
		}

		request.setAttribute("callid",callid);
		request.setAttribute("agentid",agentid);
		request.setAttribute("contentnum",contentnum);
		String linuxFilename =null;
		String url =null;
		String id =null;
		String pw =null;
		if (filename.charAt(0) =='M'){
			linuxFilename =	"/home/icd/icddir/bin/X"+filename.substring(1);
			url="10.208.11.78";
			id="icd";
			pw="Huawei12#$";
		}else{
			linuxFilename ="/share2"+filename.substring(2);
			url="10.208.11.64";
			id="icd";
			pw="Huawei12#$";
		}

		System.out.println("linuxFilename="+linuxFilename);
		request.setAttribute("xiaoxi", "准备导出录音");
		//本地文件路径
		String dir ="D://"+filename.substring(3,21);
		System.out.println("录音文件保存目录："+dir);
		// 判断文件夹是否存在，不存在则创建
		File filedir = new File(dir);
		if (!filedir.exists()) {
			filedir.mkdirs();
		}
		File file = new File("D://"+filename.substring(3));
		if (!file.exists()) {
			DownLoadFile.downLoadFile(url,id,pw,linuxFilename,dir);
			System.out.println("录音导入本地完成:D://"+filename.substring(3));
		}else{
			System.out.println("已有该录音，不需重复下载");
		}
		
		request.setAttribute("xiaoxi", "录音导入本地完成！准备上传录音到阿里云OSS。。。");

		
		if(file.exists()){
			FileLoadUtil.upLoadFileToALiyun("1.V3",file);
			request.setAttribute("xiaoxi", "上传录音到阿里云OSS完成！");
			System.out.println("上传录音到阿里云OSS完成");
			JSONArray text = null;
			try {
				text = FileTransJavaDemo.fileTransJavaDemo();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			System.out.println("text="+text);

			//把转文字结果给到前台
			ZhijianDao zj = new ZhijianDaoImpl();
			List<Zhijian> zhijianAll = zj.getZhijianAll(text);
			request.setAttribute("zhijianAll", zhijianAll);

			//把转文字结果输出到txt文件，准备智能打标签
			String fenxi_text1 = "";
			for(int i =0;i<zhijianAll.size();i++){
			fenxi_text1 = fenxi_text1+zhijianAll.get(i).getText();
             	}
            File filewenzi = new File("D://1/1/"+callid+".txt");
			// 判断文件夹是否存在，不存在则创建
			if (!filewenzi.exists()) {
				filewenzi.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(filewenzi);
			fileOutputStream.write(fenxi_text1.getBytes("gbk"));
			fileOutputStream.flush();
	        fileOutputStream.close();
			}
			
			
			
			
			//下面是分析音轨为1或者为0的文字，哪个说的欢迎/感谢多，就分析哪一个
			int a=10; //开头、结尾标准用语分，满分10分
			int b=10; //标准用语“您”
			int c=10; //态度用语“说了很多次了”很多遍了”等等
			int d = 10; //情绪值分
			//步骤1：把音轨为1的文字拼在一起
			String fenxi_text_1 = "";
			for(int i =0;i<zhijianAll.size();i++){
				if(zhijianAll.get(i).getChannelId()==1){
				fenxi_text_1 = fenxi_text_1+zhijianAll.get(i).getText();
				}
			}
			//把音轨为0的文字拼在一起
			String fenxi_text_0 = "";
			for(int i =0;i<zhijianAll.size();i++){
				if(zhijianAll.get(i).getChannelId()==0){
				fenxi_text_0 = fenxi_text_0+zhijianAll.get(i).getText();
				}
			}
			String	fenxi_text =null;
if((isExit("欢迎",fenxi_text_1)+isExit("感谢",fenxi_text_1))>=(isExit("欢迎",fenxi_text_0)+isExit("感谢",fenxi_text_0))){
	fenxi_text=fenxi_text_1;
	}else{
		fenxi_text=fenxi_text_0;
	}
			
			
			System.out.println("全文字:"+fenxi_text);
			//步骤2：分析音轨为1的文字里面是否包含以下文字
			if(isExit("欢迎",fenxi_text)==0&&isExit("致电",fenxi_text)==0){a=a-5;} //开头用语  
			if(isExit("感谢",fenxi_text)==0&&isExit("来电",fenxi_text)==0&&isExit("接听",fenxi_text)==0){a=a-5;} //结尾用语
			//步骤3：是否使用了“你”
			b=b-isExit("你",fenxi_text)*2; //用一次“你”扣2分
			if(b<0){b=0;}
			//步骤4：是否使用了“很多次了”
			c=c-isExit("说了很多次",fenxi_text)*5; //用一次扣2分
			if(c<0){c=0;}
			//System.out.println("a="+a);
			//System.out.println("b="+b);
			//System.out.println("b="+c);
			request.setAttribute("a", a);
			request.setAttribute("b", b);
			request.setAttribute("c", c);
			
			//把音轨为1的情绪值计算一个平均值
			int f = 0,j=0;
			for(int i =0;i<zhijianAll.size();i++){
				if(zhijianAll.get(i).getChannelId()==1){
					f = f+zhijianAll.get(i).getEmotionValue();
					j++;
				}
			}
			//System.out.println("f="+f);
			//System.out.println("j="+j);
			if(j!=0){
			d=f/j;}
if(d>4||d<8){
	d=10;
}else{
	d=10-Math.abs(d-6);
}
			request.setAttribute("d", d);
			
			//下面发送文字，接收api接口返回的数据
			String params ="{\"no\":\""+"1"+"\",\"text\":\""+fenxi_text1+"\"}";
			String data=Api.post("http://10.208.10.20:6088",params);
			JSONObject obj= JSON.parseObject(data);
			String e=obj.getString("result0");
		        System.out.println("e="+e);
		    
			
			
			request.setAttribute("e", e);
			request.setAttribute("xiaoxi", "分析成功！结果如下");
		}else{
			request.setAttribute("xiaoxi", "出错了！无此录音");
		}

		request.getRequestDispatcher("/zhijian.jsp").forward(request, response);


	}

	//以下是一个判断包含字符串次数的方法
	private static int isExit(String compareStr, String str ) {


		//字符串查找初始从0开始查找
		int indexStart = 0;
		//获取查找字符串的长度，这里有个规律：第二次查找出字符串的起始位置等于 第一次ab字符串出现的位置+ab的长度
		int compareStrLength = compareStr.length();
		int count = 0;
		while(true){
			int tm = str.indexOf(compareStr,indexStart);
			if( tm >= 0){
				count ++;
				//  没查找一次就从新计算下次开始查找的位置
				indexStart = tm+compareStrLength;
			}else{
				//直到没有匹配结果为止   
				return  count;
			}
		}

	}

	
}
