package com.dao;

import java.util.List;

import com.entity.Fenshu;

public interface FenshuDao {

	public boolean upDate(Fenshu fenshu);//添加分数信息
	public List<Fenshu> getFenshuAll(String begintime,String agentid);//返回用户信息集合

}
