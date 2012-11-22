package com.example.pagemanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

// created by 60062446 박정실
// created date : 2012/11/17
// last modify : 2012/11/21
public class MainActivity extends Activity implements OnClickListener{
	private FrameLayout fl; // main panel 역할을함
	private Integer currentPageNumber; // 현제 페이지
	private Integer maxPageNumber; // 최대 페이지
	private TextView pageNumberView; // 페이지 번호를 보여주는 TextView
	private AlertDialog.Builder alertBuilder; // AlertDialog Builder
	private AlertDialog imgInsertDialog; // 그림 추가 다이얼로그 객체
	private ArrayList<PageView> pages; // ImageView Vector 객체
	protected BookInfo bookInfo;	//책정보 객체(세이브/로드용)
	
	// Buttons
	private Button prev_butten;
	private Button next_button;
	private Button delete_button;
	private Button insert_button;
	private Button save;
	private Button load;
	private Button clear;
	private Button black;
	private Button red;
	private Button green;
	private Button blue;
	private Button yellow;
	
	final CharSequence [] items = {"img1", "img2", "img3"};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Attributes
        currentPageNumber = 1;
        maxPageNumber = 1;

        // Components
        prev_butten = (Button) findViewById(R.id.prev_button);
        next_button = (Button) findViewById(R.id.next_button);
        delete_button = (Button) findViewById(R.id.delete_button);
        insert_button = (Button) findViewById(R.id.insert_button);
        save = (Button) findViewById(R.id.save);               
        load = (Button) findViewById(R.id.load);
        clear = (Button) findViewById(R.id.clear);
        black = (Button) findViewById(R.id.black);
        red = (Button) findViewById(R.id.red);
        green = (Button) findViewById(R.id.green);
        blue = (Button) findViewById(R.id.blue);
        yellow = (Button) findViewById(R.id.yellow);
        
        pageNumberView = (TextView) findViewById(R.id.page_number);
        fl = (FrameLayout) findViewById(R.id.farme);
        pages = new ArrayList<PageView>();
        alertBuilder = new AlertDialog.Builder(this);
        
        bookInfo = new BookInfo();
        
        init();
    }
    
    // Initialization
    public void init() {
    	prev_butten.setOnClickListener(this);
    	next_button.setOnClickListener(this);
    	delete_button.setOnClickListener(this);
    	insert_button.setOnClickListener(this);
    	save.setOnClickListener(this);
        load.setOnClickListener(this);        
        clear.setOnClickListener(this);
        black.setOnClickListener(this);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);        
    	
    	pageNumberView.setText(currentPageNumber.toString() 
        		+ "/" + maxPageNumber.toString());
    	
    	pages.add(new PageView(this));
        fl.addView(pages.get(0));
        
        alertBuilder.setTitle("Select img");
        alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				pages.get(currentPageNumber -1).insertImage(item);
				pages.get(currentPageNumber -1).callOnDraw();
			}
		});
		imgInsertDialog = alertBuilder.create();
    }
    
    public void onStart(){
    	super.onStart();
    	pages.get(0).invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // Button Event
	public void onClick(View v) {
		switch(v.getId()) {
		// 전 페이지로 이동 버튼
		// currentpageNumber가 1인경우 경고메세지 출력
		// 아니면 currentpageNumber를 1 감소시킨후 재출력
		case R.id.prev_button:
			if(!(currentPageNumber == 1)) {
				fl.removeAllViews();
				currentPageNumber--;
				pageNumberView.setText(currentPageNumber.toString() 
						+ "/" + maxPageNumber.toString());
		        fl.addView(pages.get(currentPageNumber - 1));
			} else {
				// 첫페이지일 경우 경고메세지 출력
				Toast.makeText(this, "첫페이지 입니다.", Toast.LENGTH_SHORT).show();
			}
			break;
			
		// 다음 페이지로 이동 버튼
		// currentPageNumber가 maxPageNumber와 같으면
		// maxPageNumber를 1 증가시킨후 images에에 view를 하나 더 추가
		// 아니면 이동후 재출력
		case R.id.next_button:
			if(currentPageNumber == maxPageNumber) {
				// 마지막 페이지일경우 최대페이지를 증가
				maxPageNumber++;
				pages.add(new PageView(this));
			}
			fl.removeAllViews();
			currentPageNumber++;
			pageNumberView.setText(currentPageNumber.toString() + "/"
					+ maxPageNumber.toString());
	        fl.addView(pages.get(currentPageNumber - 1));	        
			break;
			
		// maxPageNumber가 1이면 경고메세지 출력
		// currentPageNumber가  maxPageNumber와 같으면 
		// 해당인덱스와 관련된 images의 요소를 하나 삭제후 
		// maxPageNumber와 currnetPageNumber둘다 1 감소시킨후 재출력
		// 아니면 해당인덱스와 관련된 images의 요소를 하나 삭제후  maxPageNumber를 1 감소시킨후  재출력
		case R.id.delete_button:
			if(maxPageNumber == 1) {
				Toast.makeText(this, "첫페이지는 지울수 없습니다.", Toast.LENGTH_SHORT).show();
			} else if (currentPageNumber == maxPageNumber) {
				pages.remove(currentPageNumber-1);
				maxPageNumber--;
				currentPageNumber--;
				pageNumberView.setText(currentPageNumber.toString() + "/"
						+ maxPageNumber.toString());
				fl.removeAllViews();
		        fl.addView(pages.get(currentPageNumber - 1));
			}else {
				pages.remove(currentPageNumber-1);
				maxPageNumber--;
				pageNumberView.setText(currentPageNumber.toString() + "/"
						+ maxPageNumber.toString());
				fl.removeAllViews();
		        fl.addView(pages.get(currentPageNumber - 1));
			}
			break;
		
		// 삽입 버튼
		// imgInsertDialog 객체를 이용하여 그림을 추가한다.
		case R.id.insert_button:
			imgInsertDialog.show();
			break;
			
		case R.id.save:
			Log.i("msg","save");
//			JFileChooser fileDialog = new JFileChooser(new File("."));	//파일포인터 리턴
//			File file = new File("sdcard/test/1");
//			fileDialog.showSaveDialog(null);
//			File file = fileDialog.getSelectedFile();
//			ObjectOutputStream objectOutputStream = null;
			
//			FileOutputStream fileOutputStream = new FileOutputStream(file);	//데이터 쓸애. 원래 byte단위로 쓰나 stream을 이용해 원하는 방법으로
//			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);	//디스크 바쁠때 대비. 버퍼에 일단쓰는게 안전
			
			try {				
//				FileOutputStream fos = openFileOutput(mediaFilePath, Context.MODE_WORLD_WRITEABLE);
				
//				File file = new File(getDir(getApplicationContext().getPackageName(),MODE_PRIVATE)
//						+ "/" + Constants.SAVE_FILENAME);
//				File file = new File(fileName);
//				FileOutputStream fos = new FileOutputStream(file);
//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
//				fos.write("123".getBytes());
				//getFileStreamPath(mediaFilePath).getAbsolutePath();				
				FileOutputStream fos = openFileOutput(Constants.SAVE_FILENAME.toString(), Context.MODE_WORLD_WRITEABLE);

//				ObjectOutputStream oos = new ObjectOutputStream(fos);
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(fos));
				
				//저장정보 셋팅
				ArrayList<PageViewInfo> pageInfos = new ArrayList<PageViewInfo>();
				for(int i=0; i < maxPageNumber; i++){
					pageInfos.add(pages.get(i).takePageViewInfo());
				}				
				bookInfo.setPageViewInfos(pageInfos);	//bookInfo에 넣음
				
//				oos.writeObject(bookInfo);			//bookInfo객체를 저장
				
				MyPath mp = new MyPath();
				mp.moveTo(100, 100);
				mp.lineTo(200, 200);
				
			   	Paint pnt = new Paint();
			   	pnt.setColor(Color.BLACK);
//		        pnt.setARGB(255, 255, 0, 0);
		    	pnt.setStrokeWidth(3);
		        pnt.setStyle(Paint.Style.STROKE);	//이거안하면 색채워짐
		        mp.setPaint(pnt);
				
				pages.get(0).paths.add(mp);
				oos.writeObject(mp);	
				
				oos.close();
				Toast.makeText(this, "저장되었습니다!", 0).show();
			} catch (Exception e) {
				Log.e("저장실패:", e.getMessage());
				Toast.makeText(this, "저장실패!", 0).show();
			}			
			
			break;
		case R.id.load:
			Log.i("msg","load");
			try{
//				File file = new File(getDir(getApplicationContext().getPackageName(),MODE_PRIVATE)
//						+ "/" + Constants.SAVE_FILENAME);

				FileInputStream fis = openFileInput(Constants.SAVE_FILENAME.toString());
//				FileInputStream fis = new FileInputStream(file);
//				ObjectInputStream ois = new ObjectInputStream(fis);
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(fis));
//				ArrayList<PageView> readedObject = (ArrayList<PageView>)ois.readObject();

//				bookInfo = (BookInfo)ois.readObject();
				
				MyPath mp = (MyPath)ois.readObject();
				
				Paint pnt = new Paint();
			   	pnt.setColor(Color.BLACK);
//		        pnt.setARGB(255, 255, 0, 0);
		    	pnt.setStrokeWidth(3);
		        pnt.setStyle(Paint.Style.STROKE);	//이거안하면 색채워짐
		        mp.setPaint(pnt);				
				
				pages.get(0).paths.clear();
				pages.get(0).paths.add(mp);	
				

				
//				//불러온정보 셋팅
//				ArrayList<PageViewInfo> pageInfos = bookInfo.getPageInfos();
//				maxPageNumber = pageInfos.size();
//				pages.clear();							//페이지정보 클리어
//				for(int i=0; i < pageInfos.size(); i++){	//불러온 페이지정보대로 셋팅
//					PageView pv = new PageView(this);
//					pv.loadedPageView(pageInfos.get(i));
//					pages.add(pv);
//				}
//				currentPageNumber = 1;
				
				fl.removeAllViews();
				pageNumberView.setText(currentPageNumber.toString() 
						+ "/" + maxPageNumber.toString());
				fl.addView(pages.get(currentPageNumber - 1));
				Toast.makeText(this, "불러왔습니다", 0).show();
			}catch(Exception e){
				Log.e("불러오기실패:", e.getMessage());
				Toast.makeText(this, "불러오기실패!", 0).show();
			}
			break;
			
//			JFileChooser fileDialog = new JFileChooser(new File("."));	//"." : 필터인듯. 현재폴더에서 시작됨
//			fileDialog.showOpenDialog(null);	//보이기
//			File file = fileDialog.getSelectedFile();	//파일주소 가리킴. 리턴타입은 파일
//			ObjectInputStream objectInputStream = null;	//길뚫음
//			
//			if(file != null){
//				try{				
//					objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
//					//objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
//					Object obj = objectInputStream.readObject();
//					myPanel.setShapes((Vector<PageView>)obj);	//타입캐스팅
//				} catch (FileNotFoundException e){
//					e.printStackTrace();				
//				} catch (IOException e){
//					e.printStackTrace();
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}		
//			}
			
		case R.id.clear:	//선들 모두 삭제
			pages.get(currentPageNumber-1).removePaths();
			pages.get(currentPageNumber-1).callOnDraw();
			break;
		case R.id.black:
			pages.get(currentPageNumber-1).setPaintColor(Color.BLACK);			
			break;
		case R.id.red:
			pages.get(currentPageNumber-1).setPaintColor(Color.RED);
			break;
		case R.id.green:
			pages.get(currentPageNumber-1).setPaintColor(Color.GREEN);
			break;
		case R.id.blue:
			pages.get(currentPageNumber-1).setPaintColor(Color.BLUE);
			break;
		case R.id.yellow:
			pages.get(currentPageNumber-1).setPaintColor(Color.YELLOW);
			break;

		}
	}
}
