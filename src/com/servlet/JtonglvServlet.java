package com.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cectsims.util.DocConverter;
import com.dao.JietonglvDao;
import com.dao.JietonglvDaoImpl;
import com.dao.JiexuDao;
import com.dao.JiexuDaoImpl;
import com.entity.Jietonglv;
import com.entity.jiexu;

import makeXls.MakeXls;
import winToWIN.WinToWin;
import yejicesuan.Banci;

public class JtonglvServlet  extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String riqi = request.getParameter("riqi"); //得到jsp页面传过来的参数
		JietonglvDao jtl = new JietonglvDaoImpl();
		List<Jietonglv> jietonglvAll = jtl.getJietonglvAll(riqi);
		request.setAttribute("jitonglvAll", jietonglvAll);

		JiexuDao jx = new JiexuDaoImpl();
		List<jiexu> jiexuAll = jx.getJiexuAll(riqi);
		request.setAttribute("jiexuAll", jiexuAll);

		//下面制作导出的xls表格,话务员接续情况表
/*
		try {
			WinToWin.winToWin();
		} catch (Exception e1) {

			System.out.println("模板复制失败");
		}
*/
		File file2 =new File ("C:/MyEclipse 2017 CI/Check/WebRoot/download/jiexu.xls");
		File file1 =new File ("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls");
		
		try {
		    Files.copy(file2.toPath(), file1.toPath(),
		            StandardCopyOption.REPLACE_EXISTING);
		} catch(FileAlreadyExistsException e) {
		    //destination file already exists
		} catch (IOException e) {
		    //something else went wrong
		    e.printStackTrace();
		}
		
		
		MakeXls makexls = new MakeXls();
		String[][] jiexu = null;
		try {
			jiexu = makexls.readExcelData(file1,3,32,(short) 12,(short) 21);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		int k =1;
		for(int i=0;i<jiexu.length;i++){
			if(jiexu[i][0]!=""){
				for(int j=0;j<jiexuAll.size();j++) {
					if(jiexu[i][0]!=""&jiexuAll.get(j).getAGENTID()==Integer.parseInt(jiexu[i][0])){
						String[] str = new String[7];
						str[0]=jiexuAll.get(j).getQianrushichang();
						str[1]=jiexuAll.get(j).getTonghuashichang();
						str[2]=jiexuAll.get(j).getDengdaishichang();
						str[3]=jiexuAll.get(j).getZhenglishichang();
						str[4]=jiexuAll.get(j).getShimangshichang();
						str[5]=jiexuAll.get(j).getGongshiliyonglv();
						
						//System.out.println(str[0]+"||"+str[1]+"||"+str[2]+"||"+str[3]+"||"+str[4]+"||"+str[5]);	
						//计算绩效分
						if(jiexu[i][2] != null&&jiexu[i][2] != ""){
						DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
						//绩效公式：（签入时长+通话时长+等待时长）/工作时长/2，满分100分
						int a =change1(str[0])+change1(str[1])+jiexuAll.get(j).getDengdaimiao(); //全部转为秒
						int b =Integer.parseInt(jiexu[i][2])*3600;//也转为秒
						str[6]=df.format((float)a/b/2*100);
						}				

						makexls.setData(str,i+3);


						String[] str1 = new String[6];
						str1[0]=jiexuAll.get(j).getName();
						str1[1]=jiexuAll.get(j).getTonghuashichang();
						str1[2]=jiexuAll.get(j).getQianrushichang();
						str1[3]=jiexuAll.get(j).getGongzuozongshichang();
						str1[4]=jiexuAll.get(j).getFeigongzuoshichang();
						str1[5]=jiexuAll.get(j).getGongshiliyonglv();
						makexls.setData1(str1,k);
						k++;


					}
				}
			}
		}


		MakeXls makexls1 = new MakeXls();
		for(int i=0;i<jietonglvAll.size();i++){
			String[] jietong = new String[4];
			jietong[0]=jietonglvAll.get(i).getShiduan();
			jietong[1]=""+jietonglvAll.get(i).getRengongshurushu();
			jietong[2]=""+jietonglvAll.get(i).getShijijietongshu();
			jietong[3]=""+jietonglvAll.get(i).getRengongjietonglv();
			//System.out.println(jietong[0]+"\\"+jietong[1]+jietong[2]+jietong[3]);
			makexls1.setData2(jietong, i+1);
		}

		for(int i=0;i<=12;i++){
			String[] jietong = new String[4];
			jietong[0]=jietonglvAll.get(i).getShiduan();
			jietong[1]=""+jietonglvAll.get(i).getRengongshurushu();
			jietong[2]=""+jietonglvAll.get(i).getShijijietongshu();
			jietong[3]=""+jietonglvAll.get(i).getRengongjietonglv();
			//System.out.println(jietong[0]+jietong[1]+jietong[2]+jietong[3]);
			makexls1.setData3(jietong, i+3);
		}
		for(int i=13;i<=24;i++){
			String[] jietong = new String[4];
			jietong[0]=jietonglvAll.get(i).getShiduan();
			jietong[1]=""+jietonglvAll.get(i).getRengongshurushu();
			jietong[2]=""+jietonglvAll.get(i).getShijijietongshu();
			jietong[3]=""+jietonglvAll.get(i).getRengongjietonglv();
			//System.out.println(jietong[0]+jietong[1]+jietong[2]+jietong[3]);
			makexls1.setData4(jietong, i-13+3);
		}
		
		//写入日期
		String riqi2 = riqi.substring(0,4)+"年"+riqi.substring(5,6)+"月"+riqi.substring(7,8)+"日";
		makexls1.setData5(riqi2);
		
		
		
		
		
		/*
		//写入班次班时等信息
		String[][] bancishuzu = null;
		try {
			bancishuzu = makexls1.readExcelData(file1,3,32,(short) 10,(short) 15);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for(int i=0;i<bancishuzu.length;i++){
			System.out.println("换行");
			for(int j=0;j<bancishuzu[i].length;j++) {
				System.out.println(bancishuzu[i][j]+"|");
			}	
		}
		
		for(int i=0;i<bancishuzu.length;i++){
			Banci bancijisuan =new Banci();
			String banci= "";
			try {
				banci = bancijisuan.banci(riqi, bancishuzu[i][0]);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			bancishuzu[i][1]=banci;
			bancishuzu[i][4]=String.valueOf(bancijisuan.banshi(banci));
			bancishuzu[i][5]=bancijisuan.shangbanshijian(banci);
			makexls.setData6(bancishuzu[i],i+3);
		}
		*/
		/*
		//xls转为pdf
		DocConverter d = new DocConverter("C:/MyEclipse 2017 CI/Check/WebRoot/jiexu.xls");
		d.conver();
		
		//pdf改名
		File oldFile = new File("C:/MyEclipse 2017 CI/Check/WebRoot/pdf.xls");
		  if(!oldFile.exists())
		  {
		   oldFile.createNewFile();
		  }
		  
		  String rootPath = oldFile.getParent();
		  
		  File newFile = new File(rootPath + File.separator + riqi+".pdf");
		  System.out.println("修改后文件名称是："+newFile.getName());
		  if (oldFile.renameTo(newFile)) 
		  {
		   System.out.println("修改成功!");
		  } 
		  else 
		  {
		   System.out.println("修改失败");
		  }
		  request.setAttribute("URL", newFile.getName());
		  
		  */
		request.setAttribute("xiaoxi", "查询成功");
		request.getRequestDispatcher("/jitonglv.jsp").forward(request, response);
	}
	
	//时分秒换算成秒
	public int change1(String time){
		
		int time2=0;
		String[]  strs=time.split(":");
		for(int i=0,len=strs.length;i<len;i++){
		  // System.out.println(strs[i]);
		    time2=time2*60+Integer.parseInt(strs[i]);
		}
	
		//System.out.println(time+"="+time2);
		return time2;
		
	}

}
