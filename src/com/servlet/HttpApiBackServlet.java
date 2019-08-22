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
		String no = request.getParameter("no"); //输入问题的no
		String txt = request.getParameter("txt"); //输入问题
		String txt1 = request.getParameter("txt1"); //点击问题
		String txt2 = request.getParameter("txt2"); //非点击问题
		String txt3 = request.getParameter("txt3"); //非点击问题
		String txt4 = request.getParameter("txt4"); //非点击问题
		/*
		System.out.println("问题："+txt);
		System.out.println("正确："+txt1);
		System.out.println("错误1："+txt2);
		System.out.println("错误2："+txt3);
		System.out.println("错误3："+txt4);
		*/
		/* 写入Txt文件 */  
		try {
            File writeName = new File("C:\\train\\Fine-tuning\\"+no+".txt"); //如果没有则要建立一个新的txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeName),"utf-8")));
            ) {
                out.write(txt+"\t"+txt1+"\t"+txt2+"\t"+txt3+"\t"+txt4); 
                out.flush(); //把缓存区内容压入文件
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

		
	}
	
	
	
}