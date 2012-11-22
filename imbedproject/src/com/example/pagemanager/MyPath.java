package com.example.pagemanager;

import java.io.Serializable;

import android.graphics.Paint;
import android.graphics.Path;

public class MyPath extends Path implements Serializable {
	protected MyPaint pnt;	
	
	//constructor
	public MyPath() {
		super();
		pnt = new MyPaint();
	}
	//constructor2
	public MyPath(Path p) {
		super(p);
		pnt = new MyPaint();
	}

	//getter&setter
	public Paint getPaint(){
		return this.pnt;
	}	
	public void setPaint (Paint p){		
//		this.pnt = p.clone();	//깊은복사 현재안되어 아래로 함		
		pnt.setColor(p.getColor());
    	pnt.setStrokeWidth(p.getStrokeWidth());
        pnt.setStyle(p.getStyle());		
	}
}
