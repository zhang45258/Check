package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.entity.Fenshu;

import com.util.DBconn;
import com.util.Oracleconn;

public class FenshuDaoImpl implements FenshuDao{
	

	public boolean upDate(Fenshu fenshu) {
		boolean flag = false;
		Oracleconn.init();
		//没有则更新，有则插入
		String sql1 ="MERGE INTO zhijian T1";
		String sql2	=" USING (SELECT "+fenshu.getCallid()+" AS callid,"+fenshu.getAgentid()+" AS agentid,"+fenshu.getA()+" AS a,"+fenshu.getB()+" AS b,"+fenshu.getC()+" AS c,"+fenshu.getD()+" AS d,"+fenshu.getBiaoqian1()+" AS biaoqian1 FROM dual) T2"; 
		String sql3	=" ON ( T1. callid=T2.callid)";
		String sql4	=" WHEN MATCHED THEN";
		String sql5	=" UPDATE SET T1.agentid = T2.agentid,T1.a = T2.a,T1.b = T2.b,T1.c = T2.c,T1.d = T2.d,T1.biaoqian1 = T2.biaoqian1";
		String sql6 =" WHEN NOT MATCHED THEN ";
		String sql7 =" INSERT (callid,agentid,a,b,c,d,biaoqian1) VALUES(T2.callid,T2.agentid,T2.a,T2.b,T2.c,T2.d,T2.biaoqian1);";
		String sql = sql1+sql2+sql3+sql4+sql5+sql6+sql7;
	//	String sql = "insert into zhijian(callid,agentid,begintime,a,b,c,d) " +	"values('"+fenshu.getCallid()+"','"+fenshu.getAgentid()+"','"+fenshu.getBegintime()+"','"+fenshu.getA()+"','"+fenshu.getB()+"','"+fenshu.getC()+"','"+fenshu.getD()+"')";
		System.out.println(sql);
		int i =Oracleconn.addUpdDel(sql.replaceAll(";", ""));
		if(i>0){
			flag = true;
		}
		Oracleconn.closeConn();
		return flag;
	}

	
	public List<Fenshu> getFenshuAll(String begintime,String agentid) {

		List<Fenshu> list = new ArrayList<Fenshu>();

		String sql="SELECT callid,agentid,a,b,c,d from zhijian where agentid="+agentid;
		System.out.println(sql);
		try {
			Oracleconn.init();
			ResultSet rs = Oracleconn.selectSql(sql);
			int a=0,b=0;
			while(rs.next()){
				Fenshu fenshu = new Fenshu();
				fenshu.setCallid(rs.getString("callid"));
				fenshu.setAgentid(rs.getString("agentid"));
				//fenshu.setBegintime(rs.getString("begintime"));
				fenshu.setA(rs.getInt("a"));
				fenshu.setB(rs.getInt("b"));
				fenshu.setC(rs.getInt("c"));
				fenshu.setD(rs.getInt("d"));
				fenshu.setBiaoqian1(rs.getString("biaoqian1"));
				list.add(fenshu);
				
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