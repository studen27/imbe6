package com.example.pagemanager;

import java.io.Serializable;

import android.graphics.Paint;

//정점 하나에 대한 정보를 가지는 클래스
public class MyVertex implements Serializable{
	private float x;
	private float y;
	private boolean doDraw;	
	private MyPaint pnt;	

	//constructor
	MyVertex(float ax, float ay, boolean doDraw, Paint p) {			
		x = ax;
		y = ay;
		this.doDraw = doDraw;
		pnt = new MyPaint();
		setPaint(p);
	}
	
	//getter&setter
	public Paint getPaint(){
		return this.pnt;
	}	
	public void setPaint (Paint p){
		pnt.setColor(p.getColor());
    	pnt.setStrokeWidth(p.getStrokeWidth());
        pnt.setStyle(p.getStyle());		
	}
	public boolean isDraw() {
		return doDraw;
	}
	public void setDraw(boolean draw) {
		this.doDraw = draw;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}