package com.servlet;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;  


public class HttpApiBackServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String no = request.getParameter("no"); //���������no
		String txt = request.getParameter("txt"); //��������
		String txt1 = request.getParameter("txt1"); //�������
		String txt2 = request.getParameter("txt2"); //�ǵ������
		String txt3 = request.getParameter("txt3"); //�ǵ������
		String txt4 = request.getParameter("txt4"); //�ǵ������
		/*
		System.out.println("���⣺"+txt);
		System.out.println("��ȷ��"+txt1);
		System.out.println("����1��"+txt2);
		System.out.println("����2��"+txt3);
		System.out.println("����3��"+txt4);
		*/
		/* д��Txt�ļ� */  
		try {
            File writeName = new File("C:\\train\\Fine-tuning\\"+no+".txt"); //���û����Ҫ����һ���µ�txt�ļ�
            writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeName),"utf-8")));
            ) {
                out.write(txt+"\t"+txt1+"\t"+txt2+"\t"+txt3+"\t"+txt4); 
                out.flush(); //�ѻ���������ѹ���ļ�
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
	
	
	
}