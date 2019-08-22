package com.dao;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

//import com.alibaba.fastjson.JSONArray;

import com.entity.Zhijian;

public class ZhijianDaoImpl implements ZhijianDao{
	public List<Zhijian> getZhijianAll(JSONArray text1){
		List<Zhijian> list = new ArrayList<Zhijian>();

			for(int i = 0;i<text1.size()-1;i++){
				Zhijian zhijian = new Zhijian();
				if(text1.getJSONObject(i).getString("ChannelId").equals(text1.getJSONObject(i+1).getString("ChannelId"))){
					zhijian.setChannelId(text1.getJSONObject(i).getIntValue("ChannelId"));
					zhijian.setText(text1.getJSONObject(i).getString("Text")+"|"+text1.getJSONObject(i+1).getString("Text"));
					zhijian.setEmotionValue(text1.getJSONObject(i).getIntValue("EmotionValue"));
					zhijian.setSpeechRate(text1.getJSONObject(i).getIntValue("SpeechRate"));
					i++;
				}else {
					zhijian.setChannelId(text1.getJSONObject(i).getIntValue("ChannelId"));
					zhijian.setText(text1.getJSONObject(i).getString("Text"));
					zhijian.setEmotionValue(text1.getJSONObject(i).getIntValue("EmotionValue"));
					zhijian.setSpeechRate(text1.getJSONObject(i).getIntValue("SpeechRate"));
				}
			list.add(zhijian);
				//System.out.println(text1.getJSONObject(i).getString("ChannelId")+":"+text1.getJSONObject(i).getString("Text"));
		}
			if(!text1.getJSONObject(text1.size()-1).getString("ChannelId").equals(text1.getJSONObject(text1.size()-2).getString("ChannelId"))){
				Zhijian zhijian = new Zhijian();
				zhijian.setChannelId(text1.getJSONObject(text1.size()-1).getIntValue("ChannelId"));
				zhijian.setText(text1.getJSONObject(text1.size()-1).getString("Text"));
				zhijian.setEmotionValue(text1.getJSONObject(text1.size()-1).getIntValue("EmotionValue"));
				zhijian.setSpeechRate(text1.getJSONObject(text1.size()-1).getIntValue("SpeechRate"));
				
				list.add(zhijian);
			}
			//上面是把音轨一样的文字拼在一起
			//下面是再拼一遍
			for(int i = 0;i<list.size()-1;i++){
				if(list.get(i).getChannelId()==list.get(i+1).getChannelId()){
					list.get(i).setText(list.get(i).getText()+"|"+list.get(i+1).getText());
					list.remove(i+1);
				}
				
			}
			
			
			return list;
	}
}	
