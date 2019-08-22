package com.servlet;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.Fenshu;

import com.dao.FenshuDao;
import com.dao.FenshuDaoImpl;

 
public class BaocunServlet extends HttpServlet {

	private static final long serialVersionUID = -3091952310881268668L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("��������һ��");
		
		
		String callid = request.getParameter("callid"); //�õ�jspҳ�洫�����Ĳ���
		String agentid = request.getParameter("agentid");
		//String begintime = request.getParameter("begintime");
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
        String biaoqian1 = request.getParameter("biaoqian1");

		File filewenzi = new File("D://1/1/"+callid+".lab");
		// �ж��ļ����Ƿ���ڣ��������򴴽�
		if (!filewenzi.exists()) {
			filewenzi.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(filewenzi);
			fileOutputStream.write(biaoqian1.getBytes("gbk"));
			fileOutputStream.flush();
			fileOutputStream.close();
		}

        
		Fenshu fenshu = new Fenshu(); //ʵ����һ��������װ����
		fenshu.setCallid(callid);
		fenshu.setAgentid(agentid);
		//fenshu.setBegintime(begintime);
		fenshu.setA(Integer.parseInt(a));
		fenshu.setB(Integer.parseInt(b));
		fenshu.setC(Integer.parseInt(c));
		fenshu.setD(Integer.parseInt(d));
		fenshu.setBiaoqian1(biaoqian1);
	
		FenshuDao ud = new FenshuDaoImpl();
		
		if(ud.upDate(fenshu)){
			request.setAttribute("xiaoxi", "���³ɹ�");
			
		}else{
			request.setAttribute("xiaoxi", "����ʧ��");
		
		}
		request.getRequestDispatcher("/baocun.jsp").forward(request, response);
		
		
	}
}
