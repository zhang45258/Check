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
		request.setAttribute("xiaoxi", "׼��ִ��");
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
		request.setAttribute("xiaoxi", "׼������¼��");
		//�����ļ�·��
		String dir ="D://"+filename.substring(3,21);
		System.out.println("¼���ļ�����Ŀ¼��"+dir);
		// �ж��ļ����Ƿ���ڣ��������򴴽�
		File filedir = new File(dir);
		if (!filedir.exists()) {
			filedir.mkdirs();
		}
		File file = new File("D://"+filename.substring(3));
		if (!file.exists()) {
			DownLoadFile.downLoadFile(url,id,pw,linuxFilename,dir);
			System.out.println("¼�����뱾�����:D://"+filename.substring(3));
		}else{
			System.out.println("���и�¼���������ظ�����");
		}
		try{
			copyFile("D://"+filename.substring(3),"C:/MyEclipse 2017 CI/Check/WebRoot/1.wav");
		} catch (Exception e) {
			System.out.println("����¼����1.wav׼�����س���");
		}
		request.getRequestDispatcher("/down2.jsp").forward(request, response);


	}
	/** 
	* ���Ƶ����ļ� 
	* @param oldPath String ԭ�ļ�·�� �磺c:/fqf.txt 
	* @param newPath String ���ƺ�·�� �磺f:/fqf.txt 
	* @return boolean 
	*/ 
	public static void copyFile(String oldPath, String newPath) { 
	try { 
	int bytesum = 0; 
	int byteread = 0; 
	File oldfile = new File(oldPath); 
	if (oldfile.exists()) { //�ļ�����ʱ 
	InputStream inStream = new FileInputStream(oldPath); //����ԭ�ļ� 
	FileOutputStream fs = new FileOutputStream(newPath); 
	byte[] buffer = new byte[1444]; 
	int length; 
	while ( (byteread = inStream.read(buffer)) != -1) { 
	bytesum += byteread; //�ֽ��� �ļ���С 
	//System.out.println(bytesum); 
	fs.write(buffer, 0, byteread); 
	} 
	inStream.close(); 
	} 
	} 
	catch (Exception e) { 
	System.out.println("���Ƶ����ļ���������"); 
	e.printStackTrace(); 

	} 
	} 
	
}
