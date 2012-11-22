package com.example.pagemanager;

import com.example.pagemanager.Constants.ANCHOR_TYPE;

import android.graphics.RectF;

// created by 60022495 정민규
// created date : 2012/11/17
// last modify : 2012/11/21
public class MyAnchor extends RectF{
	
	//constructor
	public MyAnchor() {
		super();
	}

	public void setLocation(int x, int y, int right, int bottom, ANCHOR_TYPE anchorType){
		switch(anchorType){
		case NW:
			super.set(x - Constants.ANCHOR_SIZE, y - Constants.ANCHOR_SIZE,
					x + Constants.ANCHOR_SIZE, y + Constants.ANCHOR_SIZE);
			break;
		case NN:
			super.set((right + x)/2 - Constants.ANCHOR_SIZE, y - Constants.ANCHOR_SIZE,
					(right + x)/2 + Constants.ANCHOR_SIZE, y + Constants.ANCHOR_SIZE);
			break;
		case NE:
			super.set(right - Constants.ANCHOR_SIZE, y - Constants.ANCHOR_SIZE,
					right + Constants.ANCHOR_SIZE, y + Constants.ANCHOR_SIZE);
			break;
		case WW:
			super.set(x - Constants.ANCHOR_SIZE, (bottom + y)/2 - Constants.ANCHOR_SIZE,
					x + Constants.ANCHOR_SIZE, (bottom + y)/2 + Constants.ANCHOR_SIZE);
			break;
		case EE:
			super.set(right - Constants.ANCHOR_SIZE, (bottom + y)/2 - Constants.ANCHOR_SIZE,
					right + Constants.ANCHOR_SIZE, (bottom + y)/2 + Constants.ANCHOR_SIZE);
			break;
		case SW:
			super.set(x - Constants.ANCHOR_SIZE, bottom - Constants.ANCHOR_SIZE,
					x + Constants.ANCHOR_SIZE, bottom + Constants.ANCHOR_SIZE);
			break;
		case SS:
			super.set((right + x)/2 - Constants.ANCHOR_SIZE, bottom - Constants.ANCHOR_SIZE,
					(right + x)/2 + Constants.ANCHOR_SIZE, bottom + Constants.ANCHOR_SIZE);
			break;
		case SE:
			super.set(right - Constants.ANCHOR_SIZE, bottom - Constants.ANCHOR_SIZE,
					right + Constants.ANCHOR_SIZE, bottom + Constants.ANCHOR_SIZE);
			break;
		case ROTATE:
			super.set((right + x)/2 - Constants.ANCHOR_SIZE, y - Constants.ANCHOR_SIZE*3 - Constants.ANCHOR_SIZE,
					(right + x)/2 + Constants.ANCHOR_SIZE, y - Constants.ANCHOR_SIZE*3 + Constants.ANCHOR_SIZE);
			break;
		default:
			super.set(0,0,0,0);				
		}
	}
}
