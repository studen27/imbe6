package com.example.pagemanager;

import android.app.Application;

public class Constants extends Application {	
	public static int ANCHOR_SIZE = 20;	//이것의 두배가 앵커크기
	public static int IMG_EDGE_RESTRICT = 20;
	public static int IMG_MIN_SIZE = 40;
	public static String PAKAGE_NAME = "com.example.pagemanager";
	public static String SAVE_FILENAME = "pages.dat";
	
	
	public static enum ANCHOR_TYPE {
		NW, NN, NE, WW, EE, SW, SS, SE, ROTATE, MOVE
	}

	public static enum DRAWING_STATE{
		idle,
		drawing,
		moving,
		resizing,
		rotating,
	}
}
