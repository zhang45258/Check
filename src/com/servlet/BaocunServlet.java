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
		System.out.println("到不了这一步");
		
		
		String callid = request.getParameter("callid"); //得到jsp页面传过来的参数
		String agentid = request.getParameter("agentid");
		//String begintime = request.getParameter("begintime");
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
        String biaoqian1 = request.getParameter("biaoqian1");

		File filewenzi = new File("D://1/1/"+callid+".lab");
		// 判断文件夹是否存在，不存在则创建
		if (!filewenzi.exists()) {
			filewenzi.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(filewenzi);
			fileOutputStream.write(biaoqian1.getBytes("gbk"));
			fileOutputStream.flush();
			fileOutputStream.close();
		}

        
		Fenshu fenshu = new Fenshu(); //实例化一个对象，组装属性
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
			request.setAttribute("xiaoxi", "更新成功");
			
		}else{
			request.setAttribute("xiaoxi", "更新失败");
		
		}
		request.getRequestDispatcher("/baocun.jsp").forward(request, response);
		
		
	}
}
