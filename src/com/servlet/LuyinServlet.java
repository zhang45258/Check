package com.servlet;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.dao.LuyinDao;
import com.dao.LuyinDaoImpl;
import com.entity.Luyin;
 
public class LuyinServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String begintimeqixian = request.getParameter("begintimeqixian"); //得到jsp页面传过来的参数
		String endtimeqixian = request.getParameter("endtimeqixian");
		int agentid;
		if( request.getParameter("agentid")==""){
		agentid = 0;
		}else{
		String agentid1 = request.getParameter("agentid");
		agentid = Integer.parseInt(agentid1);
		}
		LuyinDao ly = new LuyinDaoImpl();
		List<Luyin> luyinAll = ly.getLuyinAll(begintimeqixian,endtimeqixian,agentid);
				
		
		request.setAttribute("luyinAll", luyinAll);
		request.getRequestDispatcher("/luyin.jsp").forward(request, response);
	}
}
