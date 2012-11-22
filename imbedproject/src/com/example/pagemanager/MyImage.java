package com.example.pagemanager;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.example.pagemanager.Constants.ANCHOR_TYPE;

//created by 60022495 정민규
//created date : 2012/11/17
//last modify : 2012/11/21
public class MyImage {
	int id;	
	private BitmapDrawable bd;
	private Bitmap bitmap;
	private int x;		//비트맵 위치정보
	private int y;
	private int right;
	private int bottom;
	private int width;
	private int height;
	private int transX;	//회전시 중심점차이
	private int transY;
	private boolean isSelected;	
	private Matrix matrix;
	private ArrayList<MyAnchor> anchors;
	private Paint pnt;
	protected ANCHOR_TYPE selectedAnchor;
	protected MyImageInfo info;	
	
	//constructor
	public MyImage(Resources res, String filename) {
		this(res, filename, 0, 0);	//put to constructor2
	}
	
	//contructor2
	public MyImage(Resources res, String filename, int x, int y) {
		id = res.getIdentifier(filename, "drawable", Constants.PAKAGE_NAME);
		bd = (BitmapDrawable)res.getDrawable(id);
        bitmap = bd.getBitmap();
		this.x = x;
        this.y = y;
        this.right = x + bitmap.getWidth();
        this.bottom = y + bitmap.getHeight();
        width = right - x;
		height = bottom - y;
		transX = 0;
		transY = 0;
        isSelected = false;
        matrix = new Matrix();
        anchors = new ArrayList<MyAnchor>();
		for(int i=0; i < ANCHOR_TYPE.values().length ; i++){
			anchors.add(new MyAnchor());			
		}
        
        pnt = new Paint(Color.BLACK);
        pnt.setStrokeWidth(1);
        pnt.setStyle(Paint.Style.STROKE);
	}
	
	//getter & setter
	public int getX(){
		return this.x;		
	}
	public int getY(){
		return this.y;		
	}
	public int getWidth(){
		return this.width;		
	}
	public int getHeight(){
		return this.height;		
	}
	public int getRight(){
		return this.right;		
	}
	public int getBottom(){
		return this.bottom;		
	}
	public void setX(float x){
		this.x = (int)x;		
	}
	
	public void setY(float y){
		this.y = (int)y;	
	}
	
	public void set(float x, float y, float right, float bottom){	//리사이즈용 위치정보set
		set((int) x, (int) y, (int) right, (int) bottom);
	}	
	public void set(int x, int y, int right, int bottom){			//리사이즈용 위치정보set
		if(x > this.right-Constants.IMG_MIN_SIZE){	//에러방지 & 이미지 최소크기
			x = this.right-Constants.IMG_MIN_SIZE;
		}
		if(y > this.bottom-Constants.IMG_MIN_SIZE){
			y = this.bottom-Constants.IMG_MIN_SIZE;
		}		
		if(right < this.x+Constants.IMG_MIN_SIZE){
			right = this.x+Constants.IMG_MIN_SIZE;
		}
		if(bottom < this.y+Constants.IMG_MIN_SIZE){
			bottom = this.y+Constants.IMG_MIN_SIZE;
		}		
		
		this.x = x;
		this.y = y;
		this.right = right;
		this.bottom = bottom;

	}
	public void setMoving(int mx, int my, int viewWidth, int viewHeight){	//이동용 위치정보set. 이동시 앵커위치이상 해결
		int movX = 0;	//화면밖 넘어갈시 화면안으로 이동시킬 좌표
		int movY = 0;
		
		this.right = mx + this.width;
		this.bottom = my + this.height;
		
		if(mx > viewWidth - Constants.IMG_EDGE_RESTRICT){
			movX += viewWidth - Constants.IMG_EDGE_RESTRICT - mx;
		}
		if(my > viewHeight - Constants.IMG_EDGE_RESTRICT){
			movY += viewHeight - Constants.IMG_EDGE_RESTRICT - my;
		}
		if(right < Constants.IMG_EDGE_RESTRICT){
			movX += Constants.IMG_EDGE_RESTRICT - right;
		}
		if(bottom < Constants.IMG_EDGE_RESTRICT){
			movY += Constants.IMG_EDGE_RESTRICT - bottom;
		}
		
		this.x = mx + movX;
		this.y = my + movY;
		this.right = x + this.width;
		this.bottom = y + this.height;		
	}

	//이 이미지가 선택되었나 set
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;		
	}
	
	//그리기
	public void draw(Canvas canvas){
		canvas.drawBitmap(bitmap,x , y, null);
		
		if(isSelected == true){
			for(int i=0; i < ANCHOR_TYPE.values().length - 1; i++){
				anchors.get(i).setLocation(x, y, right, bottom, ANCHOR_TYPE.values()[i]);
				canvas.drawRect(anchors.get(i), pnt);				
			}
		}
	}
	
	//point is in shape?
	public boolean contains(float x, float y){
		return contains((int) x, (int) y);		
	}
	public boolean contains(int x, int y){
		if (x >= this.x && x <= this.right && y >= this.y && y <= this.bottom){
			return true;
		}		
		return false;		
	}


	//what anchor selected?
	public boolean isOnAnchor(float x, float y){
		return isOnAnchor((int) x, (int) y);
	}
	public boolean isOnAnchor(int x, int y){
		selectedAnchor = ANCHOR_TYPE.MOVE;
		for(int i=0; i < ANCHOR_TYPE.values().length; i++){
			if(anchors.get(i).contains(x,y)){
				selectedAnchor = ANCHOR_TYPE.values()[i]; 
				return true; 
			}
		}
		return false;
	}	

	//resize
	public void resize(int mx, int my){	//mouseX, mouseY		
		switch(selectedAnchor){
		case NW:
			set(mx, my, right, bottom);
//			set(mx, my, width + x - mx, height + y - my);
			break;
		case NN:
			set(x, my, right, bottom);
			break;
		case NE:
			set(x, my, mx, bottom);
			break;
		case WW:
			set(mx, y, right, bottom);
			break;
		case EE:
			set(x, y, mx, bottom);
			break;
		case SW:
			set(mx, y, right, my);
			break;
		case SS:
			set(x, y, right, my);
			break;
		case SE:
			set(x, y, mx, my);
			break;
		}
		
		resizeBitmap(right - x, bottom - y);
		
	}		
	
	//리사이즈
	public void resizeBitmap(float newWidth, float newHeight){
		float w = newWidth / (float)width;
		float h = newHeight / (float)height;		
		bitmap = bd.getBitmap();
		matrix.postScale(w, h);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	}
	
	//회전
	public void rotateBitmap(float angle){
		bitmap = bd.getBitmap();		
		matrix.preRotate(angle, width / 2, height / 2);	//이미지 중심점을 기준으로 Matrix 회전
		
		// 회전된 Matrix로 비트맵 생성
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		transX = (bitmap.getWidth() - width)/2;  //
		transY = (bitmap.getHeight() - height)/2;//
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		x = x - (int)transX;
		y = y - (int)transY;
		right = right + (int)transX;
		bottom = bottom + (int)transY;
	}
	
	//이미지 초기화
	public void resetBitmap(){
		matrix.reset();
		bitmap = bd.getBitmap();
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		right = x + width;
		bottom = y + height;
	}

}
