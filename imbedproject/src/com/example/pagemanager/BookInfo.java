package com.example.pagemanager;

import java.io.Serializable;
import java.util.ArrayList;

//책정보 저장 객체(세이브/로드 용)
public class BookInfo implements Serializable {
	String bookName;	//책제목
	ArrayList<PageViewInfo> pageInfos;	//페이지정보 객체 배열
	
	//constructor
	public BookInfo() {
		bookName = ""; 
		pageInfos = new ArrayList<PageViewInfo>();
	}
	
	//getter&setter
	public void setBookName(String name){
		this.bookName = name;
	}
	public ArrayList<PageViewInfo> getPageInfos() {
		return pageInfos;
	}
	public void setPageViewInfos(ArrayList<PageViewInfo> pageViewInfos) {
		this.pageInfos = pageViewInfos;
	}
	public String getBookName() {
		return bookName;
	}
}
