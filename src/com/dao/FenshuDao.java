package com.dao;

import java.util.List;

import com.entity.Fenshu;

public interface FenshuDao {

	public boolean upDate(Fenshu fenshu);//��ӷ�����Ϣ
	public List<Fenshu> getFenshuAll(String begintime,String agentid);//�����û���Ϣ����

}
