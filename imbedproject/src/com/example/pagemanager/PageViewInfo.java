package com.example.pagemanager;

import java.io.Serializable;
import java.util.ArrayList;

public class PageViewInfo implements Serializable {	
	private ArrayList<MyPath> paths;	//선들    
	private ArrayList<MyVertex> vertexes;	//선들
//	private ArrayList<MyImage> images;	//이미지들

	//constructor
	public PageViewInfo(PageView pageView) {
		paths = pageView.getPaths();
//		images = pv.images;
	}
	
	//getter&setter
	public ArrayList<MyPath> getPaths() {
		return paths;
	}
	public void setPaths(ArrayList<MyPath> paths){
		this.paths = paths;
	}
	public ArrayList<MyVertex> getVertexes() {
		return vertexes;
	}
	public void setVertexes(ArrayList<MyVertex> vertexes) {
		this.vertexes = vertexes;
	}
}
