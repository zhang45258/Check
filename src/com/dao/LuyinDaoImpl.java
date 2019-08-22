package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.entity.Luyin;
import com.entity.User;
import com.util.Oracleconn;

public class LuyinDaoImpl implements LuyinDao{

	public List<Luyin> getLuyinAll(String begintimeqixian, String endtimeqixian, int agentid) {


		List<Luyin> list = new ArrayList<Luyin>();
		String agentidsql;
		//如果没填工号
		if(agentid == 0){
			agentidsql = "";
		}else{
			agentidsql = "and agentid = '"+agentid+"'";
		}
		//看在哪个表查询		
		String tablename;//录音表
		String tablename2;//话务基础表
		
		switch(begintimeqixian.substring(4,6)){
		case "01": tablename = "TRECORDINFO1";tablename2 = "TBILLLOG1";break;
		case "02": tablename = "TRECORDINFO2";tablename2 = "TBILLLOG2";break;
		case "03": tablename = "TRECORDINFO3";tablename2 = "TBILLLOG3";break;
		case "04": tablename = "TRECORDINFO4";tablename2 = "TBILLLOG4";break;
		case "05": tablename = "TRECORDINFO5";tablename2 = "TBILLLOG5";break;
		case "06": tablename = "TRECORDINFO6";tablename2 = "TBILLLOG6";break;
		case "07": tablename = "TRECORDINFO7";tablename2 = "TBILLLOG7";break;
		case "08": tablename = "TRECORDINFO8";tablename2 = "TBILLLOG8";break;
		case "09": tablename = "TRECORDINFO9";tablename2 = "TBILLLOG9";break;
		case "10": tablename = "TRECORDINFO10";tablename2 = "TBILLLOG10";break;
		case "11": tablename = "TRECORDINFO11";tablename2 = "TBILLLOG11";break;
		default:  tablename = "TRECORDINFO12";tablename2 = "TBILLLOG12";break;
		}

		String sql="select luyinbiao2.callid,luyinbiao2.callerno,luyinbiao2.calleeno,luyinbiao2.agentid,luyinbiao2.begintime,luyinbiao2.endtime,luyinbiao2.filename,luyinbiao2.currentskillid,luyinbiao2.content,"+
				tablename2+".releasecause from "+
				"(select luyinbiao.callid,luyinbiao.callerno,luyinbiao.calleeno,luyinbiao.agentid,luyinbiao.begintime,luyinbiao.endtime,luyinbiao.filename,luyinbiao.currentskillid,IVR_RECORD_SATISFACTION.content"+
				" from (select callid,callerno,calleeno,agentid,begintime,endtime,filename,currentskillid from "+tablename+
				" where begintime between to_date('"+begintimeqixian+" 00:00:00','yyyymmdd hh24:mi:ss') and to_date('"+endtimeqixian+" 23:59:59','yyyymmdd hh24:mi:ss') "+
				agentidsql+" order by begintime desc) luyinbiao"+
				" LEFT JOIN IVR_RECORD_SATISFACTION ON luyinbiao.callid=IVR_RECORD_SATISFACTION.callid)luyinbiao2, "+tablename2+
				" where luyinbiao2.callid="+tablename2+".callid and "+tablename2+".devicein = 'DEVICE_AGENT'";



		System.out.println("查询sql："+sql);
		try {
			Oracleconn.init();
			ResultSet rs = Oracleconn.selectSql(sql);
			while(rs.next()){
				Luyin luyin = new Luyin();
				luyin.setCallid(rs.getString("callid").replaceAll("-", "."));
				luyin.setCallerno(rs.getString("callerno"));
				luyin.setCalleeno(rs.getString("calleeno"));
				luyin.setAgentid(rs.getInt("agentid"));
				luyin.setBegintime(rs.getString("begintime"));
				luyin.setEndtime(rs.getString("endtime"));
				luyin.setFilename(rs.getString("filename").replace('\\', '/'));
				luyin.setCurrentskillid(rs.getString("currentskillid"));
				if(rs.getString("content") != null){
					luyin.setContent(rs.getString("content"));
				}else if(rs.getString("releasecause").equals("1281")){
					luyin.setContent("6");//坐席释放
				}else if(rs.getString("releasecause").equals("531")){
					luyin.setContent("5");//用户挂机
				}else{
					luyin.setContent("0");//还有一种是6，都算为默认评价
				}


				switch(luyin.getContent())
				{
				case "6":
					luyin.setContentchina("未评价（坐席强行挂机）");
					break;
				case "1":
					luyin.setContentchina("非常满意");
					break;
				case "2":
					luyin.setContentchina("满意");
					break;  
				case "3":
					luyin.setContentchina("态度不满意");
					break;  
				case "4":
					luyin.setContentchina("处理结果不满意");
					break;  
				case "5":
					luyin.setContentchina("未评价（用户挂机）");
					break;                       
				default:
					luyin.setContentchina( "默认评价（正常挂机）");
				}
				list.add(luyin);
			
			}
			Oracleconn.closeConn();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}




}
