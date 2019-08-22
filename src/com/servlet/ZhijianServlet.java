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
		request.setAttribute("xiaoxi", "׼��ִ��");
		String filename = request.getParameter("filename");
		String callid = request.getParameter("callid");
		String agentid = request.getParameter("agentid");
		String content= request.getParameter("content");
		
		//System.out.println("content:"+content);
		int contentnum = 0;//���۷�
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
		
		request.setAttribute("xiaoxi", "¼�����뱾����ɣ�׼���ϴ�¼����������OSS������");

		
		if(file.exists()){
			FileLoadUtil.upLoadFileToALiyun("1.V3",file);
			request.setAttribute("xiaoxi", "�ϴ�¼����������OSS��ɣ�");
			System.out.println("�ϴ�¼����������OSS���");
			JSONArray text = null;
			try {
				text = FileTransJavaDemo.fileTransJavaDemo();
			} catch (Exception e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			System.out.println("text="+text);

			//��ת���ֽ������ǰ̨
			ZhijianDao zj = new ZhijianDaoImpl();
			List<Zhijian> zhijianAll = zj.getZhijianAll(text);
			request.setAttribute("zhijianAll", zhijianAll);

			//��ת���ֽ�������txt�ļ���׼�����ܴ��ǩ
			String fenxi_text1 = "";
			for(int i =0;i<zhijianAll.size();i++){
			fenxi_text1 = fenxi_text1+zhijianAll.get(i).getText();
             	}
            File filewenzi = new File("D://1/1/"+callid+".txt");
			// �ж��ļ����Ƿ���ڣ��������򴴽�
			if (!filewenzi.exists()) {
				filewenzi.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(filewenzi);
			fileOutputStream.write(fenxi_text1.getBytes("gbk"));
			fileOutputStream.flush();
	        fileOutputStream.close();
			}
			
			
			
			
			//�����Ƿ�������Ϊ1����Ϊ0�����֣��ĸ�˵�Ļ�ӭ/��л�࣬�ͷ�����һ��
			int a=10; //��ͷ����β��׼����֣�����10��
			int b=10; //��׼�������
			int c=10; //̬�����˵�˺ܶ���ˡ��ܶ���ˡ��ȵ�
			int d = 10; //����ֵ��
			//����1��������Ϊ1������ƴ��һ��
			String fenxi_text_1 = "";
			for(int i =0;i<zhijianAll.size();i++){
				if(zhijianAll.get(i).getChannelId()==1){
				fenxi_text_1 = fenxi_text_1+zhijianAll.get(i).getText();
				}
			}
			//������Ϊ0������ƴ��һ��
			String fenxi_text_0 = "";
			for(int i =0;i<zhijianAll.size();i++){
				if(zhijianAll.get(i).getChannelId()==0){
				fenxi_text_0 = fenxi_text_0+zhijianAll.get(i).getText();
				}
			}
			String	fenxi_text =null;
if((isExit("��ӭ",fenxi_text_1)+isExit("��л",fenxi_text_1))>=(isExit("��ӭ",fenxi_text_0)+isExit("��л",fenxi_text_0))){
	fenxi_text=fenxi_text_1;
	}else{
		fenxi_text=fenxi_text_0;
	}
			
			
			System.out.println("ȫ����:"+fenxi_text);
			//����2����������Ϊ1�����������Ƿ������������
			if(isExit("��ӭ",fenxi_text)==0&&isExit("�µ�",fenxi_text)==0){a=a-5;} //��ͷ����  
			if(isExit("��л",fenxi_text)==0&&isExit("����",fenxi_text)==0&&isExit("����",fenxi_text)==0){a=a-5;} //��β����
			//����3���Ƿ�ʹ���ˡ��㡱
			b=b-isExit("��",fenxi_text)*2; //��һ�Ρ��㡱��2��
			if(b<0){b=0;}
			//����4���Ƿ�ʹ���ˡ��ܶ���ˡ�
			c=c-isExit("˵�˺ܶ��",fenxi_text)*5; //��һ�ο�2��
			if(c<0){c=0;}
			//System.out.println("a="+a);
			//System.out.println("b="+b);
			//System.out.println("b="+c);
			request.setAttribute("a", a);
			request.setAttribute("b", b);
			request.setAttribute("c", c);
			
			//������Ϊ1������ֵ����һ��ƽ��ֵ
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
			
			//���淢�����֣�����api�ӿڷ��ص�����
			String params ="{\"no\":\""+"1"+"\",\"text\":\""+fenxi_text1+"\"}";
			String data=Api.post("http://10.208.10.20:6088",params);
			JSONObject obj= JSON.parseObject(data);
			String e=obj.getString("result0");
		        System.out.println("e="+e);
		    
			
			
			request.setAttribute("e", e);
			request.setAttribute("xiaoxi", "�����ɹ����������");
		}else{
			request.setAttribute("xiaoxi", "�����ˣ��޴�¼��");
		}

		request.getRequestDispatcher("/zhijian.jsp").forward(request, response);


	}

	//������һ���жϰ����ַ��������ķ���
	private static int isExit(String compareStr, String str ) {


		//�ַ������ҳ�ʼ��0��ʼ����
		int indexStart = 0;
		//��ȡ�����ַ����ĳ��ȣ������и����ɣ��ڶ��β��ҳ��ַ�������ʼλ�õ��� ��һ��ab�ַ������ֵ�λ��+ab�ĳ���
		int compareStrLength = compareStr.length();
		int count = 0;
		while(true){
			int tm = str.indexOf(compareStr,indexStart);
			if( tm >= 0){
				count ++;
				//  û����һ�ξʹ��¼����´ο�ʼ���ҵ�λ��
				indexStart = tm+compareStrLength;
			}else{
				//ֱ��û��ƥ����Ϊֹ   
				return  count;
			}
		}

	}

	
}
