package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.entity.Jietonglv;
import com.util.Oracleconn;

public class JietonglvDaoImpl implements JietonglvDao{
	public List<Jietonglv> getJietonglvAll(String riqi) {

		List<Jietonglv> list = new ArrayList<Jietonglv>();

		String sql="SELECT TO_CHAR(logdate,'YYYY-MM-DD hh24') logdate,sum(AGENTOCCUPYNUM),sum(AGENTCALLSUCCNUM) FROM t_DayLog_CallAnalysis_P where logdate between to_date('"+riqi+" 00:00:00','yyyymmdd hh24:mi:ss') and to_date('"+riqi+" 23:59:59','yyyymmdd hh24:mi:ss') GROUP BY TO_CHAR(logdate,'YYYY-MM-DD hh24') ORDER BY logdate ASC";
		System.out.println(sql);
		try {
			Oracleconn.init();
			ResultSet rs = Oracleconn.selectSql(sql);
			int a=0,b=0;
			while(rs.next()){
				Jietonglv jietonglv = new Jietonglv();
				jietonglv.setShiduan(rs.getString("logdate"));
				jietonglv.setRengongshurushu(rs.getInt("sum(AGENTOCCUPYNUM)"));
				jietonglv.setShijijietongshu(rs.getInt("sum(AGENTCALLSUCCNUM)"));
				if(rs.getInt("sum(AGENTOCCUPYNUM)")==0){
					jietonglv.setRengongjietonglv("100.00%");
				}else{
					jietonglv.setRengongjietonglv(getPercent(rs.getInt("sum(AGENTCALLSUCCNUM)"),rs.getInt("sum(AGENTOCCUPYNUM)")));
				}
				a=a+rs.getInt("sum(AGENTOCCUPYNUM)");
				b=b+rs.getInt("sum(AGENTCALLSUCCNUM)");
				//System.out.println(a+"\\"+b);
				list.add(jietonglv);
			}
			//把合计栏加上		
			Jietonglv jietonglv = new Jietonglv();
			jietonglv.setShiduan("合计");
			jietonglv.setRengongshurushu(a);
			jietonglv.setShijijietongshu(b);
			if(a==0){
				jietonglv.setRengongjietonglv("100.00%");
			}else{
				jietonglv.setRengongjietonglv(getPercent(b,a));
			}
			list.add(jietonglv);
			
			//如果某一个小时没数，加一条空值上去
			Jietonglv jietonglv1 = new Jietonglv();
			for(int i =0;i<24;i++){
				String temp = list.get(i).getShiduan();
				int j =Integer.parseInt(temp.substring(temp.length()-2,temp.length()));
				if(i!=j){
					if(i<10){
						jietonglv1.setShiduan(temp.substring(0,temp.length()-2)+"0"+i);
						}else{
							jietonglv1.setShiduan(temp.substring(0,temp.length()-2)+i);	
						}
					
					jietonglv1.setRengongshurushu(0);
					jietonglv1.setShijijietongshu(0);
					jietonglv1.setRengongjietonglv("100.00%");
					list.add(i,jietonglv1);
				}
				continue;
			}
			
			
			Oracleconn.closeConn();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	//计算百分比
	public String getPercent(int x,int total){
		String result="";//接受百分比的值
		double x_double=x*1.0;
		double tempresult=x_double/total;
		DecimalFormat df1 = new DecimalFormat("0.00%"); //##.00% 百分比格式，后面不足2位的用0补齐
		result= df1.format(tempresult);
		return result;
	}


	
}