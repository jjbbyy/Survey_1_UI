package com.atguigu.survey.model;

import java.util.List;

public class Page<T> {
	
	//封装的集合数据
	private List<T> list;
	
	//当前页页码
	private int pageNo;
	
	//总页数
	private int totalPageNo;
	
	//总记录数
	private int totalRecordNo;
	
	//每页显示的数据数量
	private int pageSize = 5;
	
	public Page(String pageNoStr, int totalRecordNo) {
		
		//一、计算总页数
		//1.给totalRecordNo赋值
		this.totalRecordNo = totalRecordNo;
		
		//2.计算总页数
		//总记录数能被pageSize整除：totalRecordNo / pageSize + 0
		//总记录数不能被pageSize整除：totalRecordNo / pageSize + 1
		this.totalPageNo = 
			(totalRecordNo / pageSize) + ((totalRecordNo % pageSize == 0)?0:1);
		
		//二、修正pageNo
		//1.给pageNo设置默认值，保证不论类型转换是否成功，pageNo都有一个有效的值s
		this.pageNo = 1;
		
		//2.尝试执行类型转换
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {/*什么都不用做，如果转换失败则保持默认值*/}
		
		//3.在1~总页数之间修正pageNo
		if(pageNo > totalPageNo) {
			this.pageNo = totalPageNo;
		}
		
		if(pageNo < 1) {
			this.pageNo = 1;
		}
		
	}
	
	public boolean isHasPrev() {
		return pageNo > 1;
	}
	
	public boolean isHasNext() {
		return pageNo < totalPageNo;
	}
	
	public int getPrev() {
		return pageNo - 1;
	}
	
	public int getNext() {
		return pageNo + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}

	public int getTotalRecordNo() {
		return totalRecordNo;
	}

	public int getPageSize() {
		return pageSize;
	}

}
