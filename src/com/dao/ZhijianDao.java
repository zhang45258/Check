package com.dao;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

//import com.entity.User;

import com.entity.Zhijian;
public interface ZhijianDao {

	public List<Zhijian> getZhijianAll(JSONArray text);//返回转文字的信息集合
}

