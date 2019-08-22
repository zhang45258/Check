package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.entity.jiexu;
import com.util.Oracleconn;

public class JiexuDaoImpl implements JiexuDao{
	public List<jiexu> getJiexuAll(String riqi) {

		List<jiexu> list = new ArrayList<jiexu>();

		String sql="select tAgentInfo.name,quanruqianchubiao.AGENTID,quanruqianchubiao.qianrushichang,shimangzhenglibiao.shimangshichang,shimangzhenglibiao.zhenglishichang,tonghuashichangbiao.tonghuashichang"+
" from (select AGENTID,sum(ROUND(TO_NUMBER(ACTEND - ACTBEGIN) * 24 * 60 * 60)) as qianrushichang from t_DayLog_AgentAttendance where ACTBEGIN between to_date('"+riqi+" 00:00:00','yyyymmdd hh24:mi:ss') and to_date('"+riqi+" 23:59:59','yyyymmdd hh24:mi:ss') and operatetype =52 group by AGENTID) quanruqianchubiao"+
","+
"(select AgentID,sum(BUSYNUM),sum(BUSYTIME) as shimangshichang,sum(ARRANGENUM),sum(I_ARRANGETIME) as zhenglishichang from T_DAYLOG_AGENTOPRINFO_D where LogDate between to_date('"+riqi+" 00:00:00','yyyymmdd hh24:mi:ss') and to_date('"+riqi+" 23:59:59','yyyymmdd hh24:mi:ss') group by  AgentID) shimangzhenglibiao"+
","+
"TAGENTINFO"+
","+
"(select AgentID,sum(I_AGENTCALLTIME+I_OUTAGENTCALLTIME) as tonghuashichang from T_DAYLOG_AGENTCALL_D where LogDate between to_date('"+riqi+" 00:00:00','yyyymmdd hh24:mi:ss') and to_date('"+riqi+" 23:59:59','yyyymmdd hh24:mi:ss') group by  AgentID) tonghuashichangbiao"+
" where"+
" quanruqianchubiao.AGENTID = shimangzhenglibiao.AGENTID and quanruqianchubiao.AGENTID=tonghuashichangbiao.AGENTId and quanruqianchubiao.AGENTID=tAgentInfo.AGENTId"+
" ORDER BY AgentID ASC";
		System.out.println(sql);
		try {
			Oracleconn.init();
			ResultSet rs = Oracleconn.selectSql(sql);
			int a=0,b=0;
			while(rs.next()){
				jiexu jiexu = new jiexu();
				jiexu.setAGENTID(rs.getInt("AGENTID"));
				jiexu.setQianrushichang(zhuan(rs.getInt("qianrushichang")));
				jiexu.setTonghuashichang(zhuan(rs.getInt("tonghuashichang")));
				jiexu.setDengdaimiao(rs.getInt("qianrushichang")-rs.getInt("tonghuashichang")-rs.getInt("shimangshichang")-rs.getInt("zhenglishichang"));
				if(jiexu.getDengdaimiao()>0){
				jiexu.setDengdaishichang(zhuan(jiexu.getDengdaimiao()));
				}else{
					jiexu.setDengdaishichang("0:00:00");
				}	
				jiexu.setShimangshichang(zhuan(rs.getInt("shimangshichang")));
				
				jiexu.setZhenglishichang(zhuan(rs.getInt("zhenglishichang")));
				
				if(rs.getInt("qianrushichang")-rs.getInt("shimangshichang")-rs.getInt("zhenglishichang")>0){
					jiexu.setGongzuozongshichang(zhuan(rs.getInt("qianrushichang")-rs.getInt("shimangshichang")-rs.getInt("zhenglishichang")));
					jiexu.setGongshiliyonglv(getPercent(rs.getInt("qianrushichang")-rs.getInt("shimangshichang")-rs.getInt("zhenglishichang"),rs.getInt("qianrushichang")));
					}else{
						jiexu.setGongzuozongshichang("0:00:00");
						jiexu.setGongshiliyonglv("0.00%");
					}
			
				
				jiexu.setFeigongzuoshichang(zhuan(rs.getInt("shimangshichang")+rs.getInt("zhenglishichang")));
				
				
				
				jiexu.setName(rs.getString("name"));
				list.add(jiexu);
			}
			
			Oracleconn.closeConn();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//把秒转为时：分：秒
	public String zhuan(int num) {

	        int hour = 0;
	        int minute = 0;
	        int second = 0;
String minute1;
String second1;
	        second = num % 60;
	        num -= second;
	        if(num > 0) {
	            num /= 60;
	            minute = num % 60;
	            num -= minute;
	            if(num > 0) {
	                hour = num / 60;
	            }          
	        }
	        if(minute<10){
	        	minute1="0"+minute;
	        }else{
	        	minute1=""+minute;
	        }
	        if(second<10){
	        	second1="0"+second;
	        }else{
	        	second1=""+second;
	        }
	         return hour+":"+minute1+":"+second1;
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

