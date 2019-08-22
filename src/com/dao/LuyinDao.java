package com.dao;

import java.util.List;
import com.entity.Luyin;

public interface LuyinDao {

	public List<Luyin> getLuyinAll(String begintimeqixian,String endtimeqixian,int agentid);//返回录音信息集合
}

