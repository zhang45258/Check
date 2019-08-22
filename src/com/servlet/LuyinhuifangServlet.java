package com.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.nls.client.example.FileTransJavaDemo;
import com.dao.ZhijianDao;
import com.dao.ZhijianDaoImpl;
import com.entity.Zhijian;

import UpDown.FileLoadUtil;
import linuxToWindows.DownLoadFile;

public class LuyinhuifangServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("xiaoxi", "准备执行");
		String filename = request.getParameter("filename");

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
		try{
			copyFile("D://"+filename.substring(3),"C:/MyEclipse 2017 CI/Check/WebRoot/1.wav");
		} catch (Exception e) {
			System.out.println("复制录音到1.wav准备下载出错");
		}
		request.getRequestDispatcher("/down2.jsp").forward(request, response);


	}
	/** 
	* 复制单个文件 
	* @param oldPath String 原文件路径 如：c:/fqf.txt 
	* @param newPath String 复制后路径 如：f:/fqf.txt 
	* @return boolean 
	*/ 
	public static void copyFile(String oldPath, String newPath) { 
	try { 
	int bytesum = 0; 
	int byteread = 0; 
	File oldfile = new File(oldPath); 
	if (oldfile.exists()) { //文件存在时 
	InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	FileOutputStream fs = new FileOutputStream(newPath); 
	byte[] buffer = new byte[1444]; 
	int length; 
	while ( (byteread = inStream.read(buffer)) != -1) { 
	bytesum += byteread; //字节数 文件大小 
	//System.out.println(bytesum); 
	fs.write(buffer, 0, byteread); 
	} 
	inStream.close(); 
	} 
	} 
	catch (Exception e) { 
	System.out.println("复制单个文件操作出错"); 
	e.printStackTrace(); 

	} 
	} 
	
}
