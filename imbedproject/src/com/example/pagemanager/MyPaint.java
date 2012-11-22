package com.example.pagemanager;

import java.io.Serializable;

import android.graphics.Paint;

public class MyPaint extends Paint implements Cloneable, Serializable{	//깊은복사가능시도(현재안됨)
	
	//constructor
	public MyPaint() {
		super();
	}
	public MyPaint(int flag) {
		super(flag);
	}
	public MyPaint(Paint paint) {
		super(paint);
	}
	
	//클론가능시도(현재안됨)
	public Paint clone()throws CloneNotSupportedException {
		return (Paint) super.clone();		
	}
}
