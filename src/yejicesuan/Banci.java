package yejicesuan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Banci {
	public String banci(String riqi,String banzu) throws ParseException{
		//算两个日期间隔多少天
		String dbtime2 = "20190315"; 
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date date1 = format.parse(riqi);
		Date date2 = format.parse(dbtime2);
		int a = (int) ((date1.getTime() - date2.getTime()) / (1000*3600*24));
String banci ="";
		if(banzu.equals("甲班")){
			switch(a%6){
			case 0: banci= "大休2";break;
			case 1: banci= "白班1";break;
			case 2: banci= "白班2";break;
			case 3: banci= "夜班1";break;
			case 4: banci= "夜班2";break;
			case 5: banci= "大休1";break;
			}
		}else if(banzu.equals("乙班")){
			switch(a%6){
			case 0: banci= "白班2";break;
			case 1: banci= "夜班1";break;
			case 2: banci= "夜班2";break;
			case 3: banci= "大休1";break;
			case 4: banci= "大休2";break;
			case 5: banci= "白班1";break;
			}
		}else if(banzu.equals("丙班")){
			switch(a%6){
			case 0: banci= "夜班2";break;
			case 1: banci= "大休1";break;
			case 2: banci= "大休2";break;
			case 3: banci= "白班1";break;
			case 4: banci= "白班2";break;
			case 5: banci= "夜班1";break;
			}
		}else if(banzu.equals("日勤")){
			
			banci= "日勤";

			}else if(banzu.equals("助勤")){
				
				banci= "助勤";

				}
		
		return banci;
	}
	public int banshi(String banci){
		int banshi = 0;
		switch(banci){
		case "白班2": banshi= 12;break;
		case "夜班1": banshi= 4 ;break;
		case "夜班2": banshi= 12;break;
		case "大休1": banshi= 8 ;break;
		case "大休2": banshi= 0 ;break;
		case "白班1": banshi= 12;break;
		case "日勤": banshi= 8;break;
		case "助勤": banshi= 8;break;
		}
		return banshi;
	}
	
	public String shangbanshijian(String banci){
		String shangbanshijian = "";
		switch(banci){
		case "白班2": shangbanshijian= "8:00-20:00";break;
		case "夜班1": shangbanshijian= "20:00-24:00";break;
		case "夜班2": shangbanshijian= "0:00-8:00,20:00-24:00";break;
		case "大休1": shangbanshijian= "0:00-8:00";break;
		case "大休2": shangbanshijian= "";break;
		case "白班1": shangbanshijian= "8:00-20:00";break;
		case "日勤": shangbanshijian= "8:00-16:00";break;
		case "助勤": shangbanshijian= "8:00-16:00";break;
		}
		return shangbanshijian;
	}
	
}
