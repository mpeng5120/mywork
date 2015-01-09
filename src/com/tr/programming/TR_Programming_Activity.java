package com.tr.programming;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.KeyCodeSend;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.PlcDataQueryRunnable;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.WatchRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;

import com.dataInAddress.AddressPublic;
import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.dbutils.OpenFiles;
import com.dbutils.TRFileHandler;
import com.explain.HexDecoding;
import com.explain.NCTranslate;
import com.explain.TableToBinary;
import com.explain.TableTranslate;
import com.tr.ExitTR;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.main.VerticalSeekBar;
import com.tr.main.VerticalSeekBarListener;
import com.tr.maintainguide.TR_MaintainGuide_Activity;
import com.tr.newpragram.NewPragramActivity;
import com.tr.parameter_setting.TR_Parameter_Setting_Activity;
import com.wifiexchange.ChatListener;
import com.wifiexchange.Client;
import com.wifiexchange.ServerUtils;
import com.wifiexchange.WifiSetting_Info;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @ClassName: TR_Programming_Activity
 * @Description: ��������
 * @author ������
 * @date 2013-4-11 ����9:19:17
 */

public class TR_Programming_Activity extends Activity {
	public static Menu menu;

	private  int[] alength;

	MenuItem menu_note;
	MenuItem menu_saveCache;
	MenuItem menu_download;
	MenuItem menu_watchStart;
	MenuItem menu_wantchStop;	
	MenuItem menu_lock;
	MenuItem menu_unlock;
	
	 private String ioallString="";
	 private byte[] iobyteArray=null;
	 public  static WatchRunnable watchRunnable;
	private boolean watchflag=false;
	private boolean newsaveflag = true;// ���水ť�ж����µı��滹�Ǳ���򿪵�
	private String foldername_opened = null;// ���水ť�жϴ򿪵��ļ����Ա㱣��
	//private AlarmQueryRunnable alarmQueryRunnable;
	public VerticalSeekBar seekbar;

	private String errorString;

	private boolean hasError;

	private SharedPreferences sharedPreference_password;
	public  static Boolean alreadyChecked_programmingPassword = false;
	private String password_programming;
	private static String title_lock_programming = "������";

	/*******************************************************/
	// ��ȡ����ʱ�ĵȴ���ʾ
	private int[] overflag=new int[]{0,0,0,0,0,0,0,0};
	// ��д����������ԣ���ʱʹ�õ�һ���Ȳ����ѵı�־λ
	//private boolean dataflag = true;

	HashMap<Integer, Integer> PositionList=new HashMap<Integer, Integer>();
	private SendDataRunnable sendDataRunnable;
	private WifiReadDataFormat formatReadMessage;
	private  ChatListener DataFeedback ;
	//private FinishRunnable finishRunnable;
	private PlcDataQueryRunnable plcquery;
	private ArrayList<SendDataRunnable> sendDataRunnables=new ArrayList<SendDataRunnable>();
	/*******************************************************/

	public static int noteflag=0;
	/*******************************************************/

	//��̽�����F1��ֹͣ��ť����Ӧ
	//private Button f1Button;
	//private ToggleButton btn_stop = null;
	private int m_count = 0;
	public static Button wifi_led;
	private Thread PoccQueryThread ;
	private posccalmQueryRunnable  PosccalmRunnable;
	
	
	private AlertDialog KeyMsgDialog = null;
	private KeyEventReceiver KER;
	/*******************************************************/
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("TR_Programming_Activity  onPause");
		Config.isMutiThread = false; //add by mpeng 20141231
		PosccalmRunnable.destroy();
		if(watchRunnable!=null){
			try{
				//menu_watchStart.setVisible(true);
				//menu_wantchStop.setVisible(false);
				//menu_saveCache.setEnabled(true);
				//menu_download.setEnabled(true);
				watchRunnable.destroy();
				if (getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1) {
					 Fragments_NCedit1.NCedit_Adapter.setSelectItem(-1);
				   Fragments_NCedit1.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2) {
					 Fragments_NCedit2.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit2.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3) {
					Fragments_NCedit3.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit3.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4) {
					Fragments_NCedit4.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit4.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5) {
					Fragments_NCedit5.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit5.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6) {
					Fragments_NCedit6.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit6.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7) {
					Fragments_NCedit7.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit7.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8) {
					Fragments_NCedit8.NCedit_Adapter.setSelectItem(-1);
					Fragments_NCedit8.NCedit_Adapter.notifyDataSetChanged();
				}
				 
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		unregisterReceiver(KER);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(WifiSetting_Info.mClient!=null){
			wifi_led.setVisibility(View.VISIBLE);
        }
		if(PosccalmRunnable.existFlag)
			PosccalmRunnable.destroy();
		PosccalmRunnable = new posccalmQueryRunnable(TR_Programming_Activity.this);
		PoccQueryThread = new Thread(PosccalmRunnable);
		PoccQueryThread.start();
		KER = new KeyEventReceiver();		
        IntentFilter filter = new IntentFilter();
        filter.addAction("KeyMsg");
        registerReceiver(KER, filter);
		System.out.println("TR_Programming_Activity  onResume");
	}
	
	/**
	 * setContentView��lauout�е���class"Device_ListFragment.java"��ʵ��ѡ����л�
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		super.onCreate(savedInstanceState);
		System.out.println("TR_Programming_Activity  onCreate");
		ExitTR.getInstance().addActivity(this);
		sharedPreference_password = getSharedPreferences("password",
				Context.MODE_PRIVATE);
		// password_programming=sharedPreference_password.getString("password_programming",
		// "");

		setContentView(R.layout.tr_programming);// lauout�е���class"Device_ListFragment.java"
		// ������������
		seekbar = (VerticalSeekBar) findViewById(R.id.seekbar);
		if(seekbar==null){return;}
		seekbar.setMax(500);// ���λ
		seekbar.initProgress();// Ĭ�ϵ�λ
		seekbar.setOnSeekBarChangeListener(new VerticalSeekBarListener(
				TR_Programming_Activity.this));// ���԰���Щȫ��������һ���ļ���

		new ArrayListBound();

		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setSubtitle(title_lock_programming);

		// ֱ�Ӷ�ȡ�������ļ���ʾ
		/*if (firstOpen) {// ������һ�ν���ý���
			openAllData("cache",0,TR_Programming_Activity.this);
		}*/

		wifi_led=(Button)findViewById(R.id.wifi_led);
		if(wifi_led==null){return;}
		
		ImageButton btn_stop = (ImageButton) findViewById(R.id.stop);
		if(btn_stop==null){return;}
		ButtonListener b = new ButtonListener();           
		btn_stop.setOnClickListener(b);  
		btn_stop.setOnTouchListener(b);  
		
		ImageButton btn_f1 = (ImageButton) findViewById(R.id.f1);
		if(btn_f1==null){return;}
		btn_f1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openThisFileFromSD("programmingactivity.pdf");
				KeyCodeSend.send(6, TR_Programming_Activity.this);
			}
		});
		
		DataFeedback = new ChatListener() {
			@Override
			public void onChat(byte[] message) {

				//���صı�־λSTS1�����ж��Լ���У��
				formatReadMessage.backMessageCheck(message);
				if(!formatReadMessage.finishStatus()){
					runOnUiThread(sendDataRunnable);			
				}else {
					//������ȷ�����
					//�������ص�����	
					WifiSetting_Info.blockFlag = true;
					byte[] getData=new byte[formatReadMessage.getLength()];
					//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
					System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
				
							// TODO Auto-generated method stub
					/*		for(int i=0;i<formatReadMessage.getLength();i++){
								System.out.println("�ٶ�"+i+"��ֵ��"+getData[i]);
							}
							System.out.println("��ϵͳ����ٶȵ�ֵ��"+HexDecoding.Array4Toint(getData));*/
							if(HexDecoding.Array2Toint(getData)==1){
								Constans.currentSeekbarValue=0;
							    seekbar.setProgress(0);
							}
							if(HexDecoding.Array2Toint(getData)==2){
								Constans.currentSeekbarValue=125;
								seekbar.setProgress(125);
								}
							if(HexDecoding.Array2Toint(getData)==3){
								Constans.currentSeekbarValue=250;
								seekbar.setProgress(250);
								}
							if(HexDecoding.Array2Toint(getData)==4){
								Constans.currentSeekbarValue=375;
								seekbar.setProgress(375);
								}
							if(HexDecoding.Array2Toint(getData)==5){
								Constans.currentSeekbarValue=500;
								seekbar.setProgress(500);
								}
						}
					}
			
		};
		
	}
	
	class ButtonListener implements OnClickListener, OnTouchListener{  
		   public boolean onTouch(View v, MotionEvent event) {  
		            if(v.getId() == R.id.stop){    
		                if(event.getAction() == MotionEvent.ACTION_DOWN){ 
		                	KeyCodeSend.send(999, TR_Programming_Activity.this);
		                }  
		            }  
		            return false;  
		        }

		@Override
		public void onClick(View v) {
		}  
		          
	}
	/**
	 * 
	* @Title: openThisFile 
	* @Description: ͨ������OpenFiles�෵�ص�Intent������Ӧ���ļ�;�ļ���ʱ��Ĭ�Ϸ��� rawPATH��
	* @param string    
	* @return void    
	* @throws
	 */
	public void openThisFileFromSD(String filename) {

		File currentPath = new File(Constans.rawPATH + filename);
		
		if (currentPath != null && currentPath.isFile()){// ���ڸ��ļ�
			String fileName = currentPath.toString();
			Intent intent;
			try{
			if (checkEndsWithInStringArray(fileName, getResources()
					.getStringArray(R.array.fileEndingPdf))) {
				intent = OpenFiles.getPdfFileIntent(currentPath);
				startActivity(intent);
			} else {
				Toast.makeText(TR_Programming_Activity.this, "�޷��򿪸��ļ����밲װ��Ӧ��������",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
				// TODO: handle exception
			Toast.makeText(TR_Programming_Activity.this, "�޷��򿪸��ļ����밲װ��Ӧ��������",
					Toast.LENGTH_SHORT).show();
			}
		} else {// �����ڸ��ļ�
			Toast.makeText(TR_Programming_Activity.this, "�Բ��𣬲����ڸ��ļ���", Toast.LENGTH_SHORT)
					.show();
		}
	}
	/**
	 * 
	* @Title: checkEndsWithInStringArray 
	* @Description: �������ڼ��Ҫ�򿪵��ļ��ĺ�׺�Ƿ��ڱ�����׺������
	* @param checkItsEnd
	* @param fileEndings
	* @return    
	* @return boolean    
	* @throws
	 */
	private boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
	/**
	 * �˵�
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.programming_activity, menu);// �˵�
		try{
			menu_note= menu.findItem(R.id.menu_note);
			menu_saveCache= menu.findItem(R.id.menu_saveCache);
			menu_download= menu.findItem(R.id.menu_download);
			menu_watchStart=menu.findItem(R.id.menu_watchStart);
			menu_wantchStop=menu.findItem(R.id.menu_watchStop);	
			menu_lock = menu.findItem(R.id.menu_lock);
			menu_unlock = menu.findItem(R.id.menu_unlock);
		    }catch(Exception e){
			   e.printStackTrace();
		    }
		if (getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1 ||
				getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2 ||
				getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3 ||
				getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4 ||
				getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5 ||
				getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6 ||
				getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7 ||
				getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8 ||
				getFragmentManager().findFragmentByTag("Table_1") instanceof Fragments_Table1 ||
				getFragmentManager().findFragmentByTag("Table_2") instanceof Fragments_Table2 ||
				getFragmentManager().findFragmentByTag("Table_3") instanceof Fragments_Table3 ||
				getFragmentManager().findFragmentByTag("Table_4") instanceof Fragments_Table4 ||
				getFragmentManager().findFragmentByTag("Table_5") instanceof Fragments_Table5 ||
				getFragmentManager().findFragmentByTag("Table_6") instanceof Fragments_Table6 ||
				getFragmentManager().findFragmentByTag("Table_7") instanceof Fragments_Table7 ||
				getFragmentManager().findFragmentByTag("Table_8") instanceof Fragments_Table8) {
			menu_note.setVisible(true);
			menu_saveCache.setVisible(true);
			menu_download.setVisible(true);
			menu_watchStart.setVisible(true);
			menu_unlock.setVisible(true);
		}else{
			menu_note.setVisible(false);
			menu_saveCache.setVisible(true);
			menu_download.setVisible(false);
			menu_watchStart.setVisible(false);
			menu_unlock.setVisible(true);
		}
		menu_wantchStop.setVisible(false);
		menu_lock.setVisible(false);// �������������˵���Ϊ����

		this.menu=menu;
		/******************************/
		return true;

	}
	private void getmessage(List<HashMap<String, Object>> message,int flag){
		Iterator<HashMap<String, Object>> its =message.iterator();
		if(flag==4){
			String name1 = "";
			String name2 = "";
			String name3 = "";
			try {
			while (its.hasNext()) {
				HashMap<String, Object> map = its.next();
				name1 = map.get("name1").toString().trim();
				name2 = map.get("name2").toString().trim();
				name3 = map.get("name3").toString().trim();
				int name2ads=0;
				int name3ads=0;
				if(name2.equals("")){
					name2ads=0xffff;
				}else{
					name2ads=TableToBinary.searchAddress(name2,false);
				}
				if(name3.equals("")){
					name3ads=0xffff;
				}else{
					name3ads=TableToBinary.searchAddress(name3,false);
					name3ads=name3ads+16*16;
				}
				
				byte[] name2adsbyte=HexDecoding.int2byteArray2(name2ads);
				byte[] name3adsbyte=HexDecoding.int2byteArray2(name3ads);
				
					String name2adsstring = new String(name2adsbyte,"GBK");
				    String name3adsstring = new String(name3adsbyte,"GBK");
				    
					if(name1.getBytes("gb2312").length==0){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==1){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==2){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==3){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==4){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==5){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==6){name1=name1+"\0\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==7){name1=name1+"\0\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==8){name1=name1+"\0\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==9){name1=name1+"\0\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==10){name1=name1+"\0\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==11){name1=name1+"\0\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==12){name1=name1+"\0\0\0\0";}
					else if(name1.getBytes("gb2312").length==13){name1=name1+"\0\0\0";}
					else if(name1.getBytes("gb2312").length==14){name1=name1+"\0\0";}
					else if(name1.getBytes("gb2312").length==15){name1=name1+"\0";}
					ioallString=ioallString+name1+name2adsstring+name3adsstring;
			}} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else if(flag==5){
			try{
			String[] alarmlist=new String[200];
			String symbolString ="";
			String noteString ="";
			while (its.hasNext()) {
				HashMap<String, Object> map = its.next();
				ioallString="";
				symbolString = map.get("symbolNameEditText")
						.toString().trim();
				noteString = map.get("noteEditText")
						.toString().trim();
				if(!symbolString.equals("")){
					int symbolint=Integer.parseInt(symbolString);
					symbolString="��������"+symbolint;
				//byte[] symbolbyte=null;
				//byte[] notebyte=null;
				
					//symbolbyte=symbolString.getBytes("gb2312");
					//notebyte=noteString.getBytes("gb2312");
					if(symbolString.getBytes("gb2312").length==9){symbolString=symbolString+"\0\0\0\0\0\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==10){symbolString=symbolString+"\0\0\0\0\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==11){symbolString=symbolString+"\0\0\0\0\0\0\0\0\0";}
			
				
				int allbyte=0;
				noteString=noteString.replace("\0", "");
				String notebf=noteString;
				//System.out.println("notebf==="+notebf.length());
				int note1flag=0;
				for(int n=0;n<noteString.length()-1;n++){
					char ch=noteString.charAt(n);
					String chstr=ch+"";
					//byte[] chbyte=null;
					
						//chbyte=chstr.getBytes("gb2312");
						allbyte=allbyte+chstr.getBytes("gb2312").length;
					
					
					//System.out.println("chstr="+chstr);
					//System.out.println("allbyte="+allbyte);
					if(allbyte==19||allbyte==39||allbyte==59){
						
						 notebf=notebf.substring(0, n+1+note1flag)+"\0"+notebf.substring(n+1+note1flag, notebf.length());
					 
						allbyte=0;
						note1flag++;
					}
				}
				noteString=notebf;
			/*	try {
					notebyte=noteString.getBytes("gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				//HexDecoding.printHexString("��������", notebyte);
				for(int j=0;j<=60;j++){
				   if(noteString.getBytes("gb2312").length==j){
					for(int k=1;k<=60-j;k++){noteString=noteString+"\0";}
				   }
				}
	            
				ioallString=ioallString+symbolString+noteString;
				alarmlist[symbolint-1]=ioallString;
				}
			}
			ioallString="";
			for(int m=0;m<200;m++){
				if(alarmlist[m]==null){
					alarmlist[m]="\0\0\0\0\0\0\0\0\0\0"+"\0\0\0\0\0\0\0\0\0\0"+"\0\0\0\0\0\0\0\0\0\0"+"\0\0\0\0\0\0\0\0\0\0"
							+"\0\0\0\0\0\0\0\0\0\0"+"\0\0\0\0\0\0\0\0\0\0"+"\0\0\0\0\0\0\0\0\0\0"+"\0\0\0\0\0\0\0\0\0\0";
				}
				ioallString=ioallString+alarmlist[m];
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}else if(flag==3){
			String symbolString ="";
			String signalString ="";
			String[] ioallStrings=new String[210];
			try {
			while (its.hasNext()) {
				HashMap<String, Object> map = its.next();
				if(map.get("setItem").toString().trim().equals("1")){
				symbolString = map.get("symbolNameEditText")
						.toString().trim();
				signalString = map.get("signalNameEditText")
						.toString().trim();
					if(symbolString.getBytes("gb2312").length==0){symbolString=symbolString+"\0\0\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==1){symbolString=symbolString+"\0\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==2){symbolString=symbolString+"\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==3){symbolString=symbolString+"\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==4){symbolString=symbolString+"\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==5){symbolString=symbolString+"\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==6){symbolString=symbolString+"\0\0";}
					else if(symbolString.getBytes("gb2312").length==7){symbolString=symbolString+"\0";}
					
					if(signalString.getBytes("gb2312").length==0){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==1){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==2){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==3){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==4){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==5){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==6){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==7){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==8){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==9){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==10){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==11){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==12){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==13){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==14){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==15){signalString=signalString+"\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==16){signalString=signalString+"\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==17){signalString=signalString+"\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==18){signalString=signalString+"\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==19){signalString=signalString+"\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==20){signalString=signalString+"\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==21){signalString=signalString+"\0\0\0";}
					else if(signalString.getBytes("gb2312").length==22){signalString=signalString+"\0\0";}
					else if(signalString.getBytes("gb2312").length==23){signalString=signalString+"\0";}
					if(symbolString.startsWith("S")||symbolString.startsWith("s")){
						ioallStrings[Integer.parseInt(symbolString.substring(2,5))-1+200]=symbolString+signalString;
					}else if(symbolString.startsWith("F")||symbolString.startsWith("f")){
						ioallStrings[Integer.parseInt(symbolString.substring(2,5))-1+208]=symbolString+signalString;
					}else if(symbolString.startsWith("P")||symbolString.startsWith("p")){
						ioallStrings[Integer.parseInt(symbolString.substring(1,4))-1]=symbolString+signalString;
					}
					
					
			}
			}} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0;i<ioallStrings.length;i++){
				if(ioallStrings[i]!=null){
				   ioallString=ioallString+ioallStrings[i];
				}
			}
		
		}else{
			String symbolString ="";
			String signalString ="";
			try {
			while (its.hasNext()) {
				HashMap<String, Object> map = its.next();
				if((flag==1)||map.get("setItem").toString().trim().equals("1")){
				symbolString = map.get("symbolNameEditText")
						.toString().trim();
				signalString = map.get("signalNameEditText")
						.toString().trim();
				//byte[] symbolbyte=null;
				//byte[] signalbyte=null;
				
					//symbolbyte=symbolString.getBytes("gb2312");
					//signalbyte=signalString.getBytes("gb2312");
					if(symbolString.getBytes("gb2312").length==0){symbolString=symbolString+"\0\0\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==1){symbolString=symbolString+"\0\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==2){symbolString=symbolString+"\0\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==3){symbolString=symbolString+"\0\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==4){symbolString=symbolString+"\0\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==5){symbolString=symbolString+"\0\0\0";}
					else if(symbolString.getBytes("gb2312").length==6){symbolString=symbolString+"\0\0";}
					else if(symbolString.getBytes("gb2312").length==7){symbolString=symbolString+"\0";}
					
					if(signalString.getBytes("gb2312").length==0){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==1){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==2){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==3){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==4){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==5){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==6){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==7){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==8){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==9){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==10){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==11){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==12){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==13){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==14){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==15){signalString=signalString+"\0\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==16){signalString=signalString+"\0\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==17){signalString=signalString+"\0\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==18){signalString=signalString+"\0\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==19){signalString=signalString+"\0\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==20){signalString=signalString+"\0\0\0\0";}
					else if(signalString.getBytes("gb2312").length==21){signalString=signalString+"\0\0\0";}
					else if(signalString.getBytes("gb2312").length==22){signalString=signalString+"\0\0";}
					else if(signalString.getBytes("gb2312").length==23){signalString=signalString+"\0";}
				
					ioallString=ioallString+symbolString+signalString;
			}
			}} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void addmessage(String date){
		long beforeTime=System.currentTimeMillis();
		ioallString="";
		long beforeTimeqian=System.currentTimeMillis();
        getmessage(ArrayListBound.getDeviceActualInputListData().subList(0, 32),1);
        getmessage(ArrayListBound.getDeviceActualInputListData().subList(32, 64),1);
        getmessage(ArrayListBound.getDeviceActualInputListData().subList(64, 96),1);
        getmessage(ArrayListBound.getDeviceActualInputListData().subList(96, 128),1);
        
        getmessage(ArrayListBound.getDeviceActualOutputListData().subList(0, 32),1);
        getmessage(ArrayListBound.getDeviceActualOutputListData().subList(32, 64),1);
        getmessage(ArrayListBound.getDeviceActualOutputListData().subList(64, 96),1);
        getmessage(ArrayListBound.getDeviceActualOutputListData().subList(96, 128),1);
    	long beforeTimehou=System.currentTimeMillis();
        System.out.println("ʵ�����롢ʵ�������Ϣ12345="+(beforeTimehou-beforeTimeqian));
        try {
        	iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080E0000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E0000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
        long beforeTime1=System.currentTimeMillis();
        System.out.println("ʵ�����롢ʵ�������Ϣ="+(beforeTime1-beforeTime));
        
        ioallString="";
        getmessage(ArrayListBound.getDeviceMiddleInputListData().subList(0, 28),1);
        try {
			iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080EF000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080EF000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
        
        ioallString="";
        getmessage(ArrayListBound.getDeviceTimerListData(),2);
        try {
			iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080E2000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E2000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
        long beforeTime2=System.currentTimeMillis();
        System.out.println("��ʱ����Ϣ="+(beforeTime2-beforeTime1));
        ioallString="";
        getmessage(ArrayListBound.getDeviceCounterListData(),2);
        try {
			iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080E2800="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E2800), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
        long beforeTime3=System.currentTimeMillis();
        System.out.println("��������Ϣ="+(beforeTime3-beforeTime2));
        ioallString="";
        getmessage(ArrayListBound.getDevicePositionListData(),3);
        try {
			iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080E3000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E3000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
        long beforeTime4=System.currentTimeMillis();
        System.out.println("λ����Ϣ="+(beforeTime4-beforeTime3));
        ioallString="";
        getmessage(ArrayListBound.getDeviceOptionalListData(),4);
        try {
			iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080E5000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E5000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
        long beforeTime5=System.currentTimeMillis();
        System.out.println("ѡ��������Ϣ="+(beforeTime5-beforeTime4));
        //String msg=Constans.mouldData.readAlarmOutFile(Constans.trPATH+"������Ϣ"+File.separator+"servoalarm.trt");
		byte[] byteArray=null;
		try {
			Constans.alarmData.createFolder(Constans.trPATH,Constans.alarmDataFolder).createFile("servoalarmz.trt");
			byteArray=Constans.alarmData.readFileToByte("servoalarmz.trt");
			//byteArray = msg.getBytes("gb2312");System.out.println("0x080E6000="+byteArray.length);
			sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(byteArray, 0x080E6000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e.printStackTrace();
		}
		long beforeTime6=System.currentTimeMillis();
        System.out.println("�ŷ�����="+(beforeTime6-beforeTime5));
		//msg=Constans.mouldData.readAlarmOutFile(Constans.trPATH+"������Ϣ"+File.separator+"sysalarm.trt");
		byteArray=null;
		try {
			Constans.alarmData.createFolder(Constans.trPATH,Constans.alarmDataFolder).createFile("sysalarmz.trt");
			byteArray=Constans.alarmData.readFileToByte("sysalarmz.trt");
			//byteArray = msg.getBytes("gb2312");System.out.println("0x080E7000="+byteArray.length);
			System.out.println("byteArraywifi��ӡ����===="+byteArray);
			sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(byteArray, 0x080E7000), TR_Programming_Activity.this);
			sendDataRunnables.add(sendDataRunnable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e.printStackTrace();
		}
		long beforeTime7=System.currentTimeMillis();
       System.out.println("ϵͳ����="+(beforeTime7-beforeTime6));
		ioallString="";
        getmessage(ArrayListBound.getDeviceAlarmListData(),5);
        try {
			iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080EB000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080EB000), TR_Programming_Activity.this);
		    sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e1.printStackTrace();
		}
		/*msg=Constans.mouldData.readAlarmOutFile(Constans.trPATH+"������Ϣ"+File.separator+"zsfb.trt");
		byteArray=null;
		try {
			byteArray = msg.getBytes("gb2312");
			sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(byteArray, 0x080EE000), TR_Programming_Activity.this);
			sendDataRunnables.add(sendDataRunnable);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			if(WifiSetting_Info.progressDialog!=null){
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
			e.printStackTrace();
		}*/
        long beforeTime8=System.currentTimeMillis();
        System.out.println("��������="+(beforeTime8-beforeTime7));
	 
        byte[] getData=new byte[48];
        Iterator<HashMap<String, Object>> it1 =ArrayListBound.getDeviceTimerListData().iterator();
        while (it1.hasNext()) {
			HashMap<String, Object> map = it1.next();
			int address=Integer.valueOf(map.get("addressText").toString());
			int item=Integer.valueOf(map.get("setItem").toString());
			getData[address/8]=(byte)(getData[address/8]|(item<<(address%8)));
		}
        Iterator<HashMap<String, Object>> it2 =ArrayListBound.getDeviceCounterListData().iterator();
        while (it2.hasNext()) {
			HashMap<String, Object> map = it2.next();
			int address=Integer.valueOf(map.get("addressText").toString());
			int item=Integer.valueOf(map.get("setItem").toString());
			getData[(64+address)/8]=(byte)(getData[(64+address)/8]|(item<<((64+address)%8)));
		}
        Iterator<HashMap<String, Object>> it3 =ArrayListBound.getDevicePositionListData().iterator();
        while (it3.hasNext()) {
        	HashMap<String, Object> map = it3.next();
			String posname=map.get("symbolNameEditText").toString().trim();
			if(posname.contains("SP")){
				int item=Integer.valueOf(map.get("setItem").toString());
				int valueint=Integer.parseInt(posname.substring(2))-1;
				getData[(64+64+212+valueint)/8]=(byte)(getData[(64+64+212+valueint)/8]|(item<<((64+64+212+valueint)%8)));
			}else if(posname.contains("FP")){
				int item=Integer.valueOf(map.get("setItem").toString());
				int valueint=Integer.parseInt(posname.substring(2))-1;
				getData[(64+64+222+valueint)/8]=(byte)(getData[(64+64+222+valueint)/8]|(item<<((64+64+222+valueint)%8)));
			}else if(posname.contains("P")){
				int item=Integer.valueOf(map.get("setItem").toString());
				int valueint=Integer.parseInt(posname.substring(1))-1;
				getData[(64+64+valueint)/8]=(byte)(getData[(64+64+valueint)/8]|(item<<((64+64+valueint)%8)));
			}
        }
        
    	byte[] getData1=new byte[28];
		String strNum = TR_Main_Activity.sharedPreference_openedDir.getString("dir_num","0");
		String strName = TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", "");
		TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", date).commit();
		String strTime = date;
		try {
			if(strName.getBytes("gb2312").length==0){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==1){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==2){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==3){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==4){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==5){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==6){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==7){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==8){strName=strName+"\0\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==9){strName=strName+"\0\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==10){strName=strName+"\0\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==11){strName=strName+"\0\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==12){strName=strName+"\0\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==13){strName=strName+"\0\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==14){strName=strName+"\0\0\0\0";}
			else if(strName.getBytes("gb2312").length==15){strName=strName+"\0\0\0";}
			else if(strName.getBytes("gb2312").length==16){strName=strName+"\0\0";}
			else if(strName.getBytes("gb2312").length==17){strName=strName+"\0";}
			System.arraycopy(HexDecoding.int2byteArray2(Integer.parseInt(strNum)),0, getData1, 0,2);
			System.arraycopy(strName.getBytes("gb2312"),0, getData1, 2,18);
			String[] timeStrings =strTime.split("\\s+|:|-");
			for(int i=0;i<timeStrings.length;i++){
				if(i==0){
					System.arraycopy(HexDecoding.int2byte(Integer.parseInt(timeStrings[i])-2000),0, getData1, 20+i,1);
				}else{
					System.arraycopy(HexDecoding.int2byte(Integer.parseInt(timeStrings[i])),0, getData1, 20+i,1);
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] getData2=new byte[76];
		System.arraycopy(getData1,0, getData2, 0x00000000, 28);
		System.arraycopy(getData,0, getData2, 0x0000001C, 48);System.out.println("0x20000000="+getData2.length);
        sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(getData2,0x20000000), TR_Programming_Activity.this);
        sendDataRunnables.add(sendDataRunnable);
        long beforeTime9=System.currentTimeMillis();
        System.out.println("ģ�߱�š����ơ�����޸�ʱ���ģ������="+(beforeTime9-beforeTime8));
		System.out.println("beforeTime9-beforeTime="+(beforeTime9-beforeTime));
		System.out.println("beforeTime9-WifiSetting_Info.wifiTimeOut="+(beforeTime9-WifiSetting_Info.wifiTimeOut));
	}
	
	private void fromsd(){
		if (WifiSetting_Info.mClient!=null) {
			try{
			sendDataRunnable=sendDataRunnables.get(0);
			sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable,new FinishRunnable(TR_Programming_Activity.this,backMessageTodo1)));
		    runOnUiThread(sendDataRunnable);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if(WifiSetting_Info.progressDialog!=null){
	    			WifiSetting_Info.progressDialog.dismiss();
	    			WifiSetting_Info.progressDialog=null;
	    		}
				return;
			}
		
		}else{
			if(WifiSetting_Info.progressDialog!=null){
				overflag[5]=1;
				WifiSetting_Info.progressDialog.dismiss();
				WifiSetting_Info.progressDialog=null;
			}
		}
		 
	}
	private Runnable backMessageTodo1=new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// ���صı�־λSTS1�����ж��Լ���У��
			sendDataRunnables.remove(0);
			 if(sendDataRunnables.size()>0){
				 fromsd(); 
			 }
			 if(WifiSetting_Info.progressDialog!=null&&sendDataRunnables.size()==0){
				 overflag[0]=1;
				 WifiSetting_Info.progressDialog.dismiss();
				 WifiSetting_Info.progressDialog=null;
				 
			 }
		}
	};
	
	/*****
	 * wifi����ʧ��
	 */
	private Runnable errorRunnable=new Runnable() {
		@Override
		public void run() {
			WifiSetting_Info.blockFlag=false;
			Toast.makeText(TR_Programming_Activity.this,R.string.T_wificonnfail,Toast.LENGTH_LONG).show();
		}
	};
	
	/***
	 * ���ӳɹ�
	 */
	private Runnable successRunnable=new Runnable() {
		@Override
		public void run() {
			if(TR_Programming_Activity.wifi_led!=null){
	        	TR_Programming_Activity.wifi_led.setVisibility(View.VISIBLE);
	        }
			Toast.makeText(TR_Programming_Activity.this,R.string.T_wificonnsuccess,Toast.LENGTH_SHORT).show();
			formatReadMessage=new WifiReadDataFormat(AddressPublic.model_allspeed,2);
			try {
				sendDataRunnable = new SendDataRunnable(DataFeedback, formatReadMessage,
						TR_Programming_Activity.this);
				runOnUiThread(sendDataRunnable);
			} catch (Exception e) {
				Toast.makeText(TR_Programming_Activity.this, e.toString(), Toast.LENGTH_LONG);
			}
		}
		
	};
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		// ��Ӧÿ���˵���(ͨ���˵����ID)

		case R.id.menu_note:
			int index=0;
			if (getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1){
				NCTranslate.noteList.clear();
				noteflag=1;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit1().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2){
				NCTranslate.noteList.clear();
				noteflag=2;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit2().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3){
				NCTranslate.noteList.clear();
				noteflag=3;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit3().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4){
				NCTranslate.noteList.clear();
				noteflag=4;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit4().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5){
				NCTranslate.noteList.clear();
				noteflag=5;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit5().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6){
				NCTranslate.noteList.clear();
				noteflag=6;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit6().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7){
				NCTranslate.noteList.clear();
				noteflag=7;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit7().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8){
				NCTranslate.noteList.clear();
				noteflag=8;
				try{
				alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
						getResources().getStringArray(R.array.IF2));
				new Fragments_NCedit8().showNote();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "NC������ڴ���",
							Toast.LENGTH_SHORT).show();
				}
				noteflag=0;
			}
			 
			if (getFragmentManager().findFragmentByTag("Table_1") instanceof Fragments_Table1){
				TableTranslate.tnoteList.clear();
				noteflag=1;
				index=TableTranslate.beginTranslate();
				new Fragments_Table1().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_2") instanceof Fragments_Table2){
				TableTranslate.tnoteList.clear();
				noteflag=2;
				index=TableTranslate.beginTranslate();
				new Fragments_Table2().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_3") instanceof Fragments_Table3){
				TableTranslate.tnoteList.clear();
				noteflag=3;
				index=TableTranslate.beginTranslate();
				new Fragments_Table3().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_4") instanceof Fragments_Table4){
				TableTranslate.tnoteList.clear();
				noteflag=4;
				index=TableTranslate.beginTranslate();
				new Fragments_Table4().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_5") instanceof Fragments_Table5){
				TableTranslate.tnoteList.clear();
				noteflag=5;
				index=TableTranslate.beginTranslate();
				new Fragments_Table5().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_6") instanceof Fragments_Table6){
				TableTranslate.tnoteList.clear();
				noteflag=6;
				index=TableTranslate.beginTranslate();
				new Fragments_Table6().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_7") instanceof Fragments_Table7){
				TableTranslate.tnoteList.clear();
				noteflag=7;
				index=TableTranslate.beginTranslate();
				new Fragments_Table7().showNote();
				noteflag=0;
			}
			if (getFragmentManager().findFragmentByTag("Table_8") instanceof Fragments_Table8){
				TableTranslate.tnoteList.clear();
				noteflag=8;
				index=TableTranslate.beginTranslate();
				new Fragments_Table8().showNote();
				noteflag=0;
			}
			break;
		case R.id.menu_saveCache:// ���浽��ʱ�ļ���

			// ��ʼ������Ϣ�ļ��������ж������߱��滹�����߱���
			if(menu_wantchStop.isVisible()){
				Toast.makeText(TR_Programming_Activity.this, "���ȹرռ��Ӻ��ٱ���", Toast.LENGTH_SHORT).show();
				break;
			}
			
			new AlertDialog.Builder(TR_Programming_Activity.this)
			.setTitle("��ʾ").setMessage("ȷ�����浽��ʱ�ļ���cache")
			.setPositiveButton("ȷ��",
            new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) {
					WifiSetting_Info.progressDialog = ProgressDialog.show(TR_Programming_Activity.this, "���������","��ȴ�", true, false);
		            
		              new Thread()
		              {
		                  public void run()
		                  {
		                     final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(Calendar.getInstance().getTime());// ��ǰʱ��
		             TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", date).commit();
					creatFolderSysytem();

					saveAs_DeviceDefine("cache", date);
					saveAs_NcEdit("cache", date);
					saveAs_TableEdit("cache", date);
					
					 WifiSetting_Info.progressDialog.cancel();
		                  }
		              }.start();
		              WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {              
				            
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								Toast.makeText(TR_Programming_Activity.this, "�ѱ��浽��ʱ�ļ���cache", Toast.LENGTH_SHORT).show();
							}  
				        });
				}
			}).setNegativeButton("ȡ��",null).show();
			
			break;
		case R.id.menu_download:// ����
			//nc����
				final String date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(Calendar.getInstance().getTime());// ��ǰʱ��

			if (WifiSetting_Info.mClient == null) {
				//Toast.makeText(getApplicationContext(), "��ǰ����״̬���޷����س���", Toast.LENGTH_SHORT).show();
				if(ServerUtils.isUsingWifi(TR_Programming_Activity.this)){
					new AlertDialog.Builder(TR_Programming_Activity.this)
					.setTitle("��ʾ").setMessage("������wifi")
					.setPositiveButton("ȷ��",
		            new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) {
							new Thread() {
								public void run() {
									try {
										WifiSetting_Info.mClient = Client.connect();
										new Thread(WifiSetting_Info.mClient).start();
										runOnUiThread(successRunnable);
									} catch (IOException e) {
										e.printStackTrace();
										runOnUiThread(errorRunnable);
									}
								}
							}.start();
						}
					}).setNegativeButton("ȡ��",null).show();
				}else{
					Toast.makeText(TR_Programming_Activity.this, "�����쳣��ͨѶ�жϣ��������磡", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			
			//if(TR_Main_Activity.zd_led.getBackground().equals(TR_Main_Activity.drawablezdled)){
			if(Config.AutoState)
			{
				new AlertDialog.Builder(TR_Programming_Activity.this)
				.setTitle("����")
				.setMessage("���������Զ�״̬���������أ����л����ֶ�״̬��")
				.setPositiveButton("֪����",null)
				.show();
				break;
		    }
			new AlertDialog.Builder(TR_Programming_Activity.this)
			.setTitle("��ʾ").setMessage("ȷ��Ҫ������")
			.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) {
					if (getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1 ||
							getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2 ||
							getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3 ||
							getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4 ||
							getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5 ||
							getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6 ||
							getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7 ||
							getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8) {
						// �ȴ���ʾ
						WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
							WifiSetting_Info.progressDialog = ProgressDialog.show(TR_Programming_Activity.this, "��д������","��ȴ�", true, false);
							PosccalmRunnable.destroy(); //add by mpeng
							 new Thread()
				              {
				                  public void run()
				                  {
							try {
								alength=NCTranslate.beginTranslate(TR_Programming_Activity.this,
										getResources().getStringArray(R.array.IF2));// ��ʼ����
								 if(NCTranslate.dname!=null)
					 		      {
								   if(alength[0]==-1){
									   overflag[1]=1;
									   if(WifiSetting_Info.progressDialog!=null){
											WifiSetting_Info.progressDialog.dismiss();
											WifiSetting_Info.progressDialog=null;
										}
								   }else if(alength[0]==1){
									   overflag[2]=1;
									   if(WifiSetting_Info.progressDialog!=null){
											WifiSetting_Info.progressDialog.dismiss();
											WifiSetting_Info.progressDialog=null;
										}
								   }else if(alength[0]==-2){
										   overflag[6]=1;
										   if(WifiSetting_Info.progressDialog!=null){
												WifiSetting_Info.progressDialog.dismiss();
												WifiSetting_Info.progressDialog=null;
										}
								   }else if(alength[0]==-3){
											   overflag[7]=1;
											   if(WifiSetting_Info.progressDialog!=null){
													WifiSetting_Info.progressDialog.dismiss();
													WifiSetting_Info.progressDialog=null;
										}
								   }
								   return;
					 		     }
								
							} catch (Exception e) {
								// TODO: handle exception
								overflag[3]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
							}
							System.out.println("ab1234");
							// ��������ݸ�ʽ����г�ʼ��������᷵��һ����ָ��Ĵ���
							// ����һ���жϣ���ֹnc����Ϊ����������
							if (NCTranslate.getMesHexCode().length != 0
									&& !NCTranslate.getMesHexCode().equals(null)) {
								System.out.println("nc="+NCTranslate.getMesHexCode().length);
					                	  sendDataRunnable = new SendDataRunnable(
										  new WifiSendDataFormat(
												NCTranslate.getMesHexCode(),
												AddressPublic.NCProg_Head),
												TR_Programming_Activity.this);
								          sendDataRunnables.add(sendDataRunnable);
					                	  addmessage(date1);
					  					  fromsd();
								 
							}else {
								overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
							}
				                  }
				              }.start();
							WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {              
					            
								@Override
								public void onDismiss(DialogInterface arg0) {
									// TODO Auto-generated method stub
									//add by mpeng
									if(PosccalmRunnable.existFlag)
										PosccalmRunnable.destroy();
									PosccalmRunnable = new posccalmQueryRunnable(TR_Programming_Activity.this);
									PoccQueryThread =new Thread(PosccalmRunnable);
									PoccQueryThread.start();
									
									 if(WifiSetting_Info.mClient==null){
										 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
										   .setMessage("�����쳣��ͨѶ�жϣ��������磡")
										   .setPositiveButton("ȷ��", null).show();
						                	//Toast.makeText(TR_Programming_Activity.this,"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
										}else if(overflag[0]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage("���ݷ������")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[0]=0;
										}else if(overflag[1]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage(NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸δѡ���趨")
											   .setPositiveButton("ȷ��", null).show();
											   NCTranslate.dname=null;
											   overflag[1]=0;
										}else if(overflag[2]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage("û���ҵ�"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸")
											   .setPositiveButton("ȷ��", null).show();
											   NCTranslate.dname=null;
											   overflag[2]=0;
										}else if(overflag[6]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage(NCTranslate.ncindex+"�ı��"+NCTranslate.dname+"���ڴ���")
											   .setPositiveButton("ȷ��", null).show();
											 NCTranslate.dname=null;
											 overflag[6]=0;
										}else if(overflag[7]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage(NCTranslate.ncindex+"������"+NCTranslate.dname+"���ڴ���")
											   .setPositiveButton("ȷ��", null).show();
											 NCTranslate.dname=null;
											 overflag[7]=0;
										}else if(overflag[3]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage("NC������ڴ���")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[3]=0;
										}else if(overflag[4]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage("NC����Ϊ�գ�����дNC����")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[4]=0;
										}else if(overflag[5]==1){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage("�����쳣�����ز�����")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[5]=0;
											//Toast.makeText(TR_Programming_Activity.this,"�����쳣�����ز�����", Toast.LENGTH_SHORT).show();
										}
								}  
					        });
							//table����
						} else if (getFragmentManager().findFragmentByTag("Table_1") instanceof Fragments_Table1 ||
								getFragmentManager().findFragmentByTag("Table_2") instanceof Fragments_Table2 ||
								getFragmentManager().findFragmentByTag("Table_3") instanceof Fragments_Table3 ||
								getFragmentManager().findFragmentByTag("Table_4") instanceof Fragments_Table4 ||
								getFragmentManager().findFragmentByTag("Table_5") instanceof Fragments_Table5 ||
								getFragmentManager().findFragmentByTag("Table_6") instanceof Fragments_Table6 ||
								getFragmentManager().findFragmentByTag("Table_7") instanceof Fragments_Table7 ||
								getFragmentManager().findFragmentByTag("Table_8") instanceof Fragments_Table8) {
							// �ȴ���ʾ
							WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
							WifiSetting_Info.progressDialog = ProgressDialog.show(TR_Programming_Activity.this, "��д������","��ȴ�", true, false);
							PosccalmRunnable.destroy();
							// table�Ľ�������
							 new Thread()
				              {
				                  public void run()
				                  {
							int index1=TableTranslate.beginTranslate();
							 if(TableTranslate.dname!=null)
				 		      {
								 if(index1==-1){
									 overflag[1]=1;
									 if(WifiSetting_Info.progressDialog!=null){
											WifiSetting_Info.progressDialog.dismiss();
											WifiSetting_Info.progressDialog=null;
										}
								 }else{
									 overflag[2]=1;
									 if(WifiSetting_Info.progressDialog!=null){
											WifiSetting_Info.progressDialog.dismiss();
											WifiSetting_Info.progressDialog=null;
										}
								 }
								 return;
				 		     }
							HexDecoding.printHexString("table��������", TableTranslate.getMesHexCode());
			               if (TableTranslate.getMesHexCode().length != 0
									&& !TableTranslate.getMesHexCode().equals(null)) {
			            	   System.out.println("table="+TableTranslate.getMesHexCode().length);
					                	  sendDataRunnable = new SendDataRunnable(
										  new WifiSendDataFormat(
												TableTranslate.getMesHexCode(),
												AddressPublic.SysTable_Head),
												TR_Programming_Activity.this);
								          sendDataRunnables.add(sendDataRunnable);
					                	  addmessage(date1);
					  					  fromsd(); 
					               
								
							}else {
								overflag[3]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
							}
					              }
				              }.start();
							 WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {              
						            
									@Override
									public void onDismiss(DialogInterface arg0) {
										// TODO Auto-generated method stub
										if(PosccalmRunnable.existFlag)
											PosccalmRunnable.destroy();
										PosccalmRunnable = new posccalmQueryRunnable(TR_Programming_Activity.this);
										PoccQueryThread =new Thread(PosccalmRunnable);
										PoccQueryThread.start();
										 if(WifiSetting_Info.mClient==null){
											 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
											   .setMessage("�����쳣��ͨѶ�жϣ��������磡")
											   .setPositiveButton("ȷ��", null).show();
							                	//Toast.makeText(TR_Programming_Activity.this,"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
											}else if(overflag[0]==1){
												 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
												   .setMessage("���ݷ������")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[0]=0;
											}else if(overflag[1]==1){
												 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
												   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
												   .setPositiveButton("ȷ��", null).show();
												   TableTranslate.dname=null;
												   overflag[1]=0;
											}else if(overflag[2]==1){
												 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
												   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
												   .setPositiveButton("ȷ��", null).show();
												   TableTranslate.dname=null; 
												   overflag[2]=0;
											}else if(overflag[3]==1){
												 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
												   .setMessage("table����Ϊ�գ�����дtable����")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[3]=0;
											}else if(overflag[5]==1){
												 new AlertDialog.Builder(TR_Programming_Activity.this).setTitle("��ʾ")
												   .setMessage("�����쳣�����ز�����")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[5]=0;
											}
									}  
						        });
						}
				}
			}).setNegativeButton("ȡ��",null).show();
			break;

		case R.id.menu_watchStart:// ��ʼ����

			/******************* PLCdata��ѯ *************************************/
			if (WifiSetting_Info.mClient == null) {
				//Toast.makeText(getApplicationContext(), "��ǰ����״̬���޷����ӳ���", Toast.LENGTH_SHORT).show();
				if(ServerUtils.isUsingWifi(TR_Programming_Activity.this)){
					new AlertDialog.Builder(TR_Programming_Activity.this)
					.setTitle("��ʾ").setMessage("������wifi")
					.setPositiveButton("ȷ��",
		            new DialogInterface.OnClickListener() 
					{
						public void onClick(DialogInterface dialog, int which) {
							new Thread() {
								public void run() {
									try {
										WifiSetting_Info.mClient = Client.connect();
										new Thread(WifiSetting_Info.mClient).start();
										runOnUiThread(successRunnable);
									} catch (IOException e) {
										e.printStackTrace();
										runOnUiThread(errorRunnable);
									}
								}
							}.start();
						}
					}).setNegativeButton("ȡ��",null).show();
				}else{
					Toast.makeText(TR_Programming_Activity.this, "�����쳣��ͨѶ�жϣ��������磡", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			menu_watchStart.setVisible(false);
			menu_wantchStop.setVisible(true);
			menu_saveCache.setEnabled(false);
			menu_download.setEnabled(false);

			if (getFragmentManager().findFragmentByTag("Table_1") instanceof Fragments_Table1 ){
				plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,1);
				// ��Ҫ����UI�̣߳�����UI����
				int index1=TableTranslate.beginTranslate();
				 if(TableTranslate.dname!=null)
	 		      {
					 if(index1==-1){
				   new AlertDialog.Builder(this).setTitle("��ʾ")
				   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
				   .setPositiveButton("֪����", null).show();
				   TableTranslate.dname=null;
					 }else{
						 new AlertDialog.Builder(this).setTitle("��ʾ")
						   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
						   .setPositiveButton("֪����", null).show();
						   TableTranslate.dname=null; 
					 }
					 menu_watchStart.setVisible(true);
						menu_wantchStop.setVisible(false);
						menu_saveCache.setEnabled(true);
						menu_download.setEnabled(true);
				   break;
	 		     }
				new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_2") instanceof Fragments_Table2 ){
				plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,2);
				int index1=TableTranslate.beginTranslate();
				 if(TableTranslate.dname!=null)
	 		      {
					 if(index1==-1){
				   new AlertDialog.Builder(this).setTitle("��ʾ")
				   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
				   .setPositiveButton("֪����", null).show();
				   TableTranslate.dname=null;
					 }else{
						 new AlertDialog.Builder(this).setTitle("��ʾ")
						   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
						   .setPositiveButton("֪����", null).show();
						   TableTranslate.dname=null; 
					 }
					 menu_watchStart.setVisible(true);
						menu_wantchStop.setVisible(false);
						menu_saveCache.setEnabled(true);
						menu_download.setEnabled(true);
				   break;
	 		     }
				new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_3") instanceof Fragments_Table3) {
					plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,3);
					int index1=TableTranslate.beginTranslate();
					 if(TableTranslate.dname!=null)
		 		      {
						 if(index1==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   TableTranslate.dname=null;
						 }else{
							 new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
							   .setPositiveButton("֪����", null).show();
							   TableTranslate.dname=null; 
						 }
						 menu_watchStart.setVisible(true);
							menu_wantchStop.setVisible(false);
							menu_saveCache.setEnabled(true);
							menu_download.setEnabled(true);
					   break;
		 		     }
					new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_4") instanceof Fragments_Table4) {
					plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,4);
					int index1=TableTranslate.beginTranslate();
					 if(TableTranslate.dname!=null)
		 		      {
						 if(index1==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   TableTranslate.dname=null;
						 }else{
							 new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
							   .setPositiveButton("֪����", null).show();
							   TableTranslate.dname=null; 
						 }
						 menu_watchStart.setVisible(true);
							menu_wantchStop.setVisible(false);
							menu_saveCache.setEnabled(true);
							menu_download.setEnabled(true);
					   break;
		 		     }
					new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_5") instanceof Fragments_Table5) {
					plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,5);
					int index1=TableTranslate.beginTranslate();
					 if(TableTranslate.dname!=null)
		 		      {
						 if(index1==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   TableTranslate.dname=null;
						 }else{
							 new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
							   .setPositiveButton("֪����", null).show();
							   TableTranslate.dname=null; 
						 }
						 menu_watchStart.setVisible(true);
							menu_wantchStop.setVisible(false);
							menu_saveCache.setEnabled(true);
							menu_download.setEnabled(true);
					   break;
		 		     }
					new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_6") instanceof Fragments_Table6) {
					plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,6);
					int index1=TableTranslate.beginTranslate();
					 if(TableTranslate.dname!=null)
		 		      {
						 if(index1==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   TableTranslate.dname=null;
						 }else{
							 new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
							   .setPositiveButton("֪����", null).show();
							   TableTranslate.dname=null; 
						 }
						 menu_watchStart.setVisible(true);
							menu_wantchStop.setVisible(false);
							menu_saveCache.setEnabled(true);
							menu_download.setEnabled(true);
					   break;
		 		     }
					new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_7") instanceof Fragments_Table7) {
					plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,7);
					int index1=TableTranslate.beginTranslate();
					 if(TableTranslate.dname!=null)
		 		      {
						 if(index1==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   TableTranslate.dname=null;
						 }else{
							 new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
							   .setPositiveButton("֪����", null).show();
							   TableTranslate.dname=null; 
						 }
						 menu_watchStart.setVisible(true);
							menu_wantchStop.setVisible(false);
							menu_saveCache.setEnabled(true);
							menu_download.setEnabled(true);
					   break;
		 		     }
					new Thread(plcquery).start();
			}
			if (getFragmentManager().findFragmentByTag("Table_8") instanceof Fragments_Table8) {
				    plcquery = new PlcDataQueryRunnable(TR_Programming_Activity.this,8);
					int index1=TableTranslate.beginTranslate();
					 if(TableTranslate.dname!=null)
		 		      {
						 if(index1==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   TableTranslate.dname=null;
						 }else{
							 new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage("û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
							   .setPositiveButton("֪����", null).show();
							   TableTranslate.dname=null; 
						 }
						 menu_watchStart.setVisible(true);
						 menu_wantchStop.setVisible(false);
						 menu_saveCache.setEnabled(true);
							menu_download.setEnabled(true);
					   break;
		 		     }
					new Thread(plcquery).start();
			}
			 if(getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1 ||
						getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2 ||
						getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3 ||
						getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4 ||
						getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5 ||
						getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6 ||
						getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7 ||
						getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8) {
					try{
			    	alength = NCTranslate.beginTranslate(
							TR_Programming_Activity.this, getResources()
									.getStringArray(R.array.IF2));
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getApplicationContext(), "NC������ڴ���",
								Toast.LENGTH_SHORT).show();
					}
					 if(NCTranslate.dname!=null)
		 		      {
					   if(alength[0]==-1){
					   new AlertDialog.Builder(this).setTitle("��ʾ")
					   .setMessage(NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸δѡ���趨")
					   .setPositiveButton("֪����", null).show();
					   NCTranslate.dname=null;
						 }else if(alength[0]==1){
						   new AlertDialog.Builder(this).setTitle("��ʾ")
						   .setMessage("û���ҵ�"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸")
						   .setPositiveButton("֪����", null).show();
						   NCTranslate.dname=null;
						}else if(alength[0]==-2){
							   new AlertDialog.Builder(this).setTitle("��ʾ")
							   .setMessage(NCTranslate.ncindex+"�ı��"+NCTranslate.dname+"���ڴ���")
							   .setPositiveButton("֪����", null).show();
							   NCTranslate.dname=null;
						}else if(alength[0]==-3){
								   new AlertDialog.Builder(this).setTitle("��ʾ")
								   .setMessage(NCTranslate.ncindex+"������"+NCTranslate.dname+"���ڴ���")
								   .setPositiveButton("֪����", null).show();
								   NCTranslate.dname=null;
						}
					   menu_watchStart.setVisible(true);
						menu_wantchStop.setVisible(false);
						menu_saveCache.setEnabled(true);
						menu_download.setEnabled(true);
					   break;
		 		     }
					watchRunnable = new WatchRunnable(TR_Programming_Activity.this,
							alength);
					Device_ListFragment.firstOpen=true;
					Config.isMutiThread = true; //add by mpeng 20141231
					watchflag=true;
					Thread a1 = new Thread(watchRunnable);
					a1.start();
					
				}
		   
			menu_lock.setVisible(false);
			menu_unlock.setVisible(true);// ������������������˵���Ϊ���أ����ý����˵���Ϊ��ʾ

			if (alreadyChecked_programmingPassword) {
				alreadyChecked_programmingPassword = false;
				updateAdapter();
			}
			title_lock_programming = "������";
			getActionBar().setSubtitle(title_lock_programming);
			Toast.makeText(getApplicationContext(), "���ӿ�ʼ", Toast.LENGTH_SHORT)
					.show();
			break;

		case R.id.menu_watchStop://ֹͣ����
			menu_watchStart.setVisible(true);
			menu_wantchStop.setVisible(false);
			menu_saveCache.setEnabled(true);
			menu_download.setEnabled(true);

			if (getFragmentManager().findFragmentByTag("Table_1") instanceof Fragments_Table1 ||
					getFragmentManager().findFragmentByTag("Table_2") instanceof Fragments_Table2 ||
					getFragmentManager().findFragmentByTag("Table_3") instanceof Fragments_Table3 ||
					getFragmentManager().findFragmentByTag("Table_4") instanceof Fragments_Table4 ||
					getFragmentManager().findFragmentByTag("Table_5") instanceof Fragments_Table5 ||
					getFragmentManager().findFragmentByTag("Table_6") instanceof Fragments_Table6 ||
					getFragmentManager().findFragmentByTag("Table_7") instanceof Fragments_Table7 ||
					getFragmentManager().findFragmentByTag("Table_8") instanceof Fragments_Table8) {
				try{
				PlcDataQueryRunnable.destroy();
				if (getFragmentManager().findFragmentByTag("Table_1") instanceof Fragments_Table1) {
				     Fragments_Table1.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_2") instanceof Fragments_Table2) {
				     Fragments_Table2.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_3") instanceof Fragments_Table3) {
				     Fragments_Table3.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_4") instanceof Fragments_Table4) {
				     Fragments_Table4.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_5") instanceof Fragments_Table5) {
				     Fragments_Table5.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_6") instanceof Fragments_Table6) {
				     Fragments_Table6.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_7") instanceof Fragments_Table7) {
				     Fragments_Table7.TableEdit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("Table_8") instanceof Fragments_Table8) {
				     Fragments_Table8.TableEdit_Adapter.notifyDataSetChanged();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
				//Fragments_Table.TableEdit_Adapter.notifyDataSetChanged();
			} else if (getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1 ||
					getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2 ||
					getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3 ||
					getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4 ||
					getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5 ||
					getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6 ||
					getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7 ||
					getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8) {
				try{
					if(watchRunnable!=null)	
						watchRunnable.destroy();
				if (getFragmentManager().findFragmentByTag("NC_1") instanceof Fragments_NCedit1) {
					 Fragments_NCedit1.NCedit_Adapter.setSelectItem(-1);
				   Fragments_NCedit1.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_2") instanceof Fragments_NCedit2) {
					 Fragments_NCedit2.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit2.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_3") instanceof Fragments_NCedit3) {
					Fragments_NCedit3.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit3.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_4") instanceof Fragments_NCedit4) {
					Fragments_NCedit4.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit4.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_5") instanceof Fragments_NCedit5) {
					Fragments_NCedit5.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit5.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_6") instanceof Fragments_NCedit6) {
					Fragments_NCedit6.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit6.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_7") instanceof Fragments_NCedit7) {
					Fragments_NCedit7.NCedit_Adapter.setSelectItem(-1);
				 Fragments_NCedit7.NCedit_Adapter.notifyDataSetChanged();
				}
				if (getFragmentManager().findFragmentByTag("NC_8") instanceof Fragments_NCedit8) {
					Fragments_NCedit8.NCedit_Adapter.setSelectItem(-1);
					Fragments_NCedit8.NCedit_Adapter.notifyDataSetChanged();
				}
				 
				}catch(Exception e){
					e.printStackTrace();
				}
				//2013.8.21
			}

			Config.isMutiThread = false; //add by mpeng 20141231
			Toast.makeText(getApplicationContext(), "����ֹͣ", Toast.LENGTH_SHORT)
					.show();



			break;
		case R.id.menu_lock:// ������幦��
			menu_lock.setVisible(false);
			menu_unlock.setVisible(true);// ������������������˵���Ϊ���أ����ý����˵���Ϊ��ʾ

			if (alreadyChecked_programmingPassword) {
				alreadyChecked_programmingPassword = false;
				updateAdapter();
				Toast.makeText(this, "�����ɹ�", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "��ǰ�Ѵ�������״̬", Toast.LENGTH_SHORT).show();
			}
			title_lock_programming = "������";
			getActionBar().setSubtitle(title_lock_programming);

			break;
		case R.id.menu_unlock:// ������幦��
			menu_lock.setVisible(true);
			menu_unlock.setVisible(false);// ������������ý����˵���Ϊ���أ����������˵���Ϊ��ʾ

			if (alreadyChecked_programmingPassword) {// �Ѿ�����
				Toast.makeText(this, "��ǰ���ڽ���״̬", Toast.LENGTH_SHORT).show();
			} else {// ���������������
				password_programming = sharedPreference_password.getString(
						"password_programming", "");
				if (password_programming.equals("")) {// ���������Ϊ�գ��ı��־λ
					alreadyChecked_programmingPassword = true;
					new AlertDialog.Builder(this).setTitle("����")
					.setMessage("��ǰ�����룬���ڽ���״̬��Ҫ��������ǰ��ά���������ñ������")
					.setPositiveButton("֪����", null).show();
					updateAdapter();
				} else {// ���벻Ϊ��
					// ��������������
					final EditText editText = new EditText(this);
					editText.setHint("���������룬20λ����");
					editText.setTransformationMethod(PasswordTransformationMethod
							.getInstance());// ��������Ϊ���ɼ���
					editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
							20) });
					editText.setKeyListener(new NumberKeyListener() {
						@Override
						protected char[] getAcceptedChars() {
							return new char[] { 'a', 'b', 'c', 'd', 'e', 'f',
									'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
									'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
									'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
									'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
									'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
									'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2',
									'3', '4', '5', '6', '7', '8', '9', '0' };
						}

						@Override
						public int getInputType() {
							return android.text.InputType.TYPE_CLASS_TEXT;
						}
					});
					new AlertDialog.Builder(this)
					.setTitle("�������������Ի��Ȩ��")
					.setView(editText)
					.setCancelable(false)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {
							String password_input = editText
									.getText().toString()
									.trim();
							if (password_programming
									.equals(password_input)) {// ������ȷ�͸ı��־λ���������رմ���

								Toast.makeText(
										TR_Programming_Activity.this,
										"�����ɹ�",
										Toast.LENGTH_SHORT)
										.show();
								alreadyChecked_programmingPassword = true;// ��־λ��Ϊ��
								updateAdapter();
								try {// ��ʧ
									Field field = dialog
											.getClass()
											.getSuperclass()
											.getDeclaredField(
													"mShowing");
									field.setAccessible(true);
									field.set(dialog, true);// ��mShowing������Ϊtrue����ʾ�Ի���δ�ر�
								} catch (Exception e) {
									e.printStackTrace();
								}
								dialog.dismiss();

							} else {// ����ȷ�����䣬���ڱ���
								try {// ����ʧ
									Field field = dialog
											.getClass()
											.getSuperclass()
											.getDeclaredField(
													"mShowing");
									field.setAccessible(true);
									field.set(dialog, false);// ��mShowing������Ϊfalse����ʾ�Ի����ѹرգ���ƭandroidϵͳ
								} catch (Exception e) {
									e.printStackTrace();
								}

								editText.setText("");
								editText.setHint("����������벻��ȷ��������");

							}

						}
					})
					.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialog,
								int which) {

							try {// ��ʧ
								Field field = dialog
										.getClass()
										.getSuperclass()
										.getDeclaredField(
												"mShowing");
								field.setAccessible(true);
								field.set(dialog, true);// ��mShowing������Ϊtrue����ʾ�Ի���δ�ر�
							} catch (Exception e) {
								e.printStackTrace();
							}
							dialog.dismiss();
						}
					}).show();
				}
			}
			title_lock_programming = "δ����";
			getActionBar().setSubtitle(title_lock_programming);
			break;
			// case R.id.menu_newfile:// �½��ļ��й���
			// filenew();
			// break;
			// case R.id.menu_open:// ��ȡ����
			// File mouldData = new File(Constans.mouldDataPATH);
			// final String[] readfolders = mouldData.list();// �õ��ļ�����
			// new AlertDialog.Builder(this)
			// .setTitle("��ѡ����ļ���")
			// .setItems(readfolders,
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// readAllFiles(readfolders[which]);
			// }
			// }).setNegativeButton("ȡ��", null).show();
			// // fileRead();
			// break;
			// case R.id.menu_save:// ���湦��
			// fileSave();
			// break;
			// case R.id.menu_save_as:// ����Ϊ����
			// final EditText etEditText = new EditText(this);
			// new AlertDialog.Builder(this)
			// .setTitle("�����������ļ�����")
			// .setView(etEditText)
			// .setPositiveButton("ȷ��",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// String foldername = etEditText.getText()
			// .toString().trim();
			// if (foldername != null
			// && foldername.length() != 0) {
			// saveAllFilesAs(foldername); // ����Ϊ����
			// } else {
			// Toast.makeText(
			// TR_Programming_Activity.this,
			// "����Ϊ�գ���������������",
			// Toast.LENGTH_SHORT).show();
			// }
			//
			// }
			// }).setNegativeButton("ȡ��", null).show();
			//
			// break;
			// case R.id.menu_delete:// ɾ���ļ��й���
			// File delete = new File(Constans.mouldDataPATH);
			// final String[] deletefolders = delete.list();// �õ��ļ�����
			// new AlertDialog.Builder(this)
			// .setTitle("��ѡ��ɾ���ļ���")
			// .setItems(deletefolders,
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// Constans.mouldData
			// .deleteFolder(Constans.mouldDataPATH
			// + deletefolders[which]);
			//
			// updateAdapter();
			//
			// Toast.makeText(
			// TR_Programming_Activity.this,
			// deletefolders[which] + "�ļ���ɾ���ɹ�",
			// Toast.LENGTH_SHORT).show();
			// }
			// }).setNegativeButton("ȡ��", null).show();
			//
			// break;
			// IAP����
			/*	case R.id.menu_sendHexFile:

			AlarmQueryRunnable temp = new AlarmQueryRunnable();

			new Thread(temp).start();*/

			/*
			 * WifiSetting_Info.progressDialog = WifiSetting_Info.progressDialog.show(this, "��д������", "��ȴ�", true,
			 * false);
			 * 
			 * try { //�ļ����õĵ�ַ String
			 * fileName=Constans.mechanicalParameterPATH+"APP_To_Bin.bin";
			 * //ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
			 * DataInputStream dis = new DataInputStream(new
			 * FileInputStream(fileName));
			 * 
			 * byte[] buftemp = new byte[1]; //��̬���飬���ڲ�֪������������С��ֻ���˷���Դʹ������
			 * ArrayList<Byte> tempArrayList=new ArrayList<Byte>(); while
			 * ((dis.read(buftemp)!=-1)) { Byte tempByte=new Byte(buftemp[0]);
			 * tempArrayList.add(tempByte); } dis.close();
			 * 
			 * byte[] hexSendData=new byte[tempArrayList.size()];
			 * 
			 * Iterator<Byte> it =tempArrayList.iterator();
			 * 
			 * int offset=0; while (it.hasNext()) { byte[] temp=new byte[1];
			 * temp[0]=(Byte) it.next();
			 * 
			 * System.arraycopy(temp, 0, hexSendData, offset, 1); offset++; }
			 * //��ӡ���ݣ�������� //HexDecoding.printHexString("hex����",hexSendData);
			 * 
			 * //��������ݸ�ʽ����г�ʼ��������᷵��һ����ָ��Ĵ��� formatMessage=new
			 * WifiSendDataFormat(hexSendData, AddressPublic.sysProg_Head);
			 * 
			 * } catch (FileNotFoundException e) { Toast.makeText(this,
			 * "��ȡ�ļ�ʧ��",Toast.LENGTH_SHORT).show(); e.printStackTrace(); } catch
			 * (IOException e) { e.printStackTrace(); }
			 * 
			 * //�趨�������ݵ���Ӧ���� Client.setOnChatListener(hexFileChatlistener);
			 * 
			 * try { runOnUiThread(hexfileDownload); } catch (Exception e) { //
			 * TODO Auto-generated catch block e.printStackTrace();
			 * Toast.makeText(getApplicationContext(), e.toString(),
			 * Toast.LENGTH_SHORT).show(); //δ��������������ʾ
			 * runOnUiThread(nullErrordialogdissmiss); }
			 */

			//		break;

			/*case R.id.menu_keycode:

			// ����keycode����

			// ������Ϣ
			try {
				// ���趨�߳̿���listener
				sendDataRunnable = new SendDataRunnable(new WifiSendDataFormat(
						HexDecoding.int2byteArray4(1),
						AddressPublic.keycode_Head),
						TR_Programming_Activity.this);

				finishRunnable = new FinishRunnable(
						TR_Programming_Activity.this, "���ݷ������");
				// ���ַ�ʽֻ������д���ݣ�������ʱ��ʱ��ʹ�����ַ�ʽ
				sendDataRunnable.setOnlistener(new NormalChatListenner(
						sendDataRunnable, finishRunnable));

				runOnUiThread(sendDataRunnable);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), e.toString(),
						Toast.LENGTH_SHORT).show();
			}

			break;
			 */
		case R.id.menu_activity_main:
			// Intent intent_main=new Intent();
			// intent_main.setClass(TR_Programming_Activity.this,
			// TR_Main_Activity.class);
			if(watchflag==true&&watchRunnable!=null)
				watchRunnable.destroy();
			startActivity(new Intent().setClass(TR_Programming_Activity.this,
					TR_Main_Activity.class));
			Device_ListFragment.fragmentncStatusFragment=null;
			Device_ListFragment.j=1;
			break;
		case R.id.menu_activity_setting:
			if(watchflag==true&&watchRunnable!=null)
			watchRunnable.destroy();
			startActivity(new Intent().setClass(TR_Programming_Activity.this,
					NewPragramActivity.class));
			Device_ListFragment.fragmentncStatusFragment=null;
			Device_ListFragment.j=1;
			break;
		case R.id.menu_activity_parameter_setting:
			if(watchflag==true&&watchRunnable!=null)
			watchRunnable.destroy();
			startActivity(new Intent().setClass(TR_Programming_Activity.this,
					TR_Parameter_Setting_Activity.class));
			Device_ListFragment.fragmentncStatusFragment=null;
			Device_ListFragment.j=1;
			break;
		case R.id.menu_activity_maintainGuide:
			if(watchflag==true&&watchRunnable!=null)
			watchRunnable.destroy();
			startActivity(new Intent().setClass(TR_Programming_Activity.this,
					TR_MaintainGuide_Activity.class));
			Device_ListFragment.fragmentncStatusFragment=null;
			Device_ListFragment.j=1;
			break;
		default:
			// ��û�д������¼�����������������
			return super.onOptionsItemSelected(item);
		}
		// ����true��ʾ������˵�����¼�������Ҫ�����¼�����������ȥ��
		return true;
	}

	/**
	 * 
	 * @Title: filenew
	 * @Description: �½�ģ���ļ��ṹ�������豸�����ļ���NCEdit�ļ���TableEdit�ļ�
	 * @return void ��������
	 * @throws
	 */
	private void filenew() {
		final EditText etEditText = new EditText(this);
		new AlertDialog.Builder(this).setTitle("���������ļ�����").setView(etEditText)
		.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String foldername = etEditText.getText().toString()
						.trim();
				if (foldername != null && foldername.length() != 0) {
					creatFolderSysytem();
					newsaveflag = false;
					foldername_opened = foldername;

					Constans.mouldData
					.createFolder(Constans.mouldDataPATH,
							foldername)
							.createFile(Constans.�豸��������)
							.createFile(Constans.nc�ı�����)
							.createFile(Constans.table�ı�����);
					TRFileHandler newFolder = TRFileHandler.create()
							.createFolder(
									Constans.mouldDataPATH + foldername
									+ File.separator,
									Constans.dataToDownloadFolder);

					// �����ʾ����������
					new_11before(ArrayListBound
							.getDeviceActualInputListData());
					new_11before(ArrayListBound
							.getDeviceActualOutputListData());
					new_11before(ArrayListBound
							.getDeviceMiddleInputListData());
					new_11before(ArrayListBound
							.getDeviceMiddleOutputListData());
					new_11before(ArrayListBound
							.getDeviceDataInputListData());
					new_11before(ArrayListBound
							.getDeviceDataOutputListData());
					new_11before(ArrayListBound
							.getDeviceRegisterListData());
					new_11before(ArrayListBound
							.getDeviceDataRegisterListData());
					new_11before(ArrayListBound.getDeviceTimerListData());
					new_11before(ArrayListBound
							.getDeviceCounterListData());
					new_11before(ArrayListBound
							.getDevicePositionListData());
					new_after(ArrayListBound.getDeviceAlarmListData());
					new_NCEdit1(ArrayListBound.getNCeditList1Data());
					new_NCEdit2(ArrayListBound.getNCeditList2Data());
					new_NCEdit3(ArrayListBound.getNCeditList3Data());
					new_NCEdit4(ArrayListBound.getNCeditList4Data());
					new_NCEdit5(ArrayListBound.getNCeditList5Data());
					new_NCEdit6(ArrayListBound.getNCeditList6Data());
					new_NCEdit7(ArrayListBound.getNCeditList7Data());
					new_NCEdit8(ArrayListBound.getNCeditList8Data());
					new_TableEdit1(ArrayListBound.getTableList1Data());
					new_TableEdit2(ArrayListBound.getTableList2Data());
					new_TableEdit3(ArrayListBound.getTableList3Data());
					new_TableEdit4(ArrayListBound.getTableList4Data());
					new_TableEdit5(ArrayListBound.getTableList5Data());
					new_TableEdit6(ArrayListBound.getTableList6Data());
					new_TableEdit7(ArrayListBound.getTableList7Data());
					new_TableEdit8(ArrayListBound.getTableList8Data());

					updateAdapter();
					Toast.makeText(TR_Programming_Activity.this,
							"���½��ļ���" + foldername, Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(TR_Programming_Activity.this,
							"����Ϊ�գ���������������", Toast.LENGTH_SHORT).show();
				}

			}

		}).setNegativeButton("ȡ��", null).show();
	}

	/**
	 * 
	 * @Title: new_8before
	 * @Description: �½��豸����ǰ����
	 * @param ArrayList
	 * @return void ��������
	 * @throws
	 */
	private void new_11before(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("symbolNameEditText", "");
			ArrayList.get(i).put("signalNameEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}

	/**
	 * 
	 * @Title: new_4after
	 * @Description: �½��豸���������
	 * @param ArrayList
	 * @return void ��������
	 * @throws
	 */
	private void new_after(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("symbolNameEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}

	/**
	 * 
	 * @Title: new_NCEdit
	 * @Description: �½�NCEdit
	 * @param ArrayList
	 * @return void ��������
	 * @throws
	 */
	private void new_NCEdit1(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit2(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit3(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit4(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit5(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit6(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit7(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_NCEdit8(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("numSpinner", "");
			ArrayList.get(i).put("orderSpinner", "");
			ArrayList.get(i).put("operatText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}

	/**
	 * 
	 * @Title: new_TableEdit
	 * @Description: �½�TableEdit
	 * @param ArrayList
	 * @return void
	 * @throws
	 */
	private void new_TableEdit1(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit2(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit3(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit4(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit5(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit6(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit7(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	private void new_TableEdit8(ArrayList<HashMap<String, Object>> ArrayList) {
		for (int i = 0; i < ArrayList.size(); i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
	}
	/**
	 * @param foldername
	 * @Title: openAllData
	 * @Description: ��ȡ�����ļ�����ʾ��֮ǰ���뽨���ļ���ϵͳ ��ͬʱ��ȡ�������ļ���
	 * @return void
	 * @throws
	 */
	public void openAllData(String foldername,int flag,Activity targetActivity) {

		errorString = "";
		hasError = false;
		creatFolderSysytem();

		// ÿ�γ��������
		int step_DeviceActualInput = 0;
		int step_DeviceActualOutput = 0;
		int step_DeviceMiddleInput = 0;
		int step_DeviceMiddleOutput = 0;
		int step_DeviceDataInput = 0;
		int step_DeviceDataOutput = 0;
		int step_DeviceRegister = 0;
		int step_DeviceDataRegister = 0;
		int step_DeviceTimer = 0;
		int step_DeviceCounter = 0;
		int step_DevicePosition = 0;
		int step_DeviceAlarm = 0;
		int step_DeviceOptional = 0;

		//int step_NCEdit = 0;
		int step_NCEdit1 = 0;
		int step_NCEdit2 = 0;
		int step_NCEdit3 = 0;
		int step_NCEdit4 = 0;
		int step_NCEdit5 = 0;
		int step_NCEdit6 = 0;
		int step_NCEdit7 = 0;
		int step_NCEdit8 = 0;
		//int step_TableEdit = 0;
		int step_TableEdit1 = 0;
		int step_TableEdit2 = 0;
		int step_TableEdit3 = 0;
		int step_TableEdit4 = 0;
		int step_TableEdit5 = 0;
		int step_TableEdit6 = 0;
		int step_TableEdit7 = 0;
		int step_TableEdit8 = 0;

		// ��ʼ��mark_list ����ֵΪstep��Ϊ0ʱ��index
		ArrayList<HashMap<String, Integer>> mark_list = new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> map_mark = new HashMap<String, Integer>();
		map_mark.put("DeviceActualInput", 4);// ȷ����λ��
		map_mark.put("DeviceActualOutput", 9);// �����ǳ�������Ϊ0ʱ��λ��
		map_mark.put("DeviceMiddleInput", 14);
		map_mark.put("DeviceMiddleOutput", 19);
		map_mark.put("DeviceDataInput", 24);
		map_mark.put("DeviceDataOutput", 29);
		map_mark.put("DeviceRegister", 34);
		map_mark.put("DeviceDataRegister", 39);
		map_mark.put("DeviceTimer", 44);
		map_mark.put("DeviceCounter", 49);
		map_mark.put("DevicePosition", 54);
		map_mark.put("DeviceAlarm", 59);
		map_mark.put("DeviceOptional", 64);

		//map_mark.put("NCEdit", 4);
		map_mark.put("NCEdit1", 4);// ȷ����λ��
		map_mark.put("NCEdit2", 9);// �����ǳ�������Ϊ0ʱ��λ��
		map_mark.put("NCEdit3", 14);
		map_mark.put("NCEdit4", 19);
		map_mark.put("NCEdit5", 24);
		map_mark.put("NCEdit6", 29);
		map_mark.put("NCEdit7", 34);
		map_mark.put("NCEdit8", 39);
		
		//map_mark.put("TableEdit", 4);
		map_mark.put("TableEdit1", 4);// ȷ����λ��
		map_mark.put("TableEdit2", 9);// �����ǳ�������Ϊ0ʱ��λ��
		map_mark.put("TableEdit3", 14);
		map_mark.put("TableEdit4", 19);
		map_mark.put("TableEdit5", 24);
		map_mark.put("TableEdit6", 29);
		map_mark.put("TableEdit7", 34);
		map_mark.put("TableEdit8", 39);

		mark_list.add(map_mark);

		// ��ȡDevice������
		String[] readlist_Device = Constans.mouldData
				.readOutFile(Constans.mouldDataPATH + foldername
						+ File.separator + Constans.�豸��������);// ����֮�������Ҫ������·��
		//readlist_Device=new String[]{};
		for (int i = 0; i < readlist_Device.length; i++) {
			readlist_Device[i] = readlist_Device[i].replace("\n", "").trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
			//System.out.println("readlist_Device"+i+"="+readlist_Device[i].replace("\n", "").trim());
		}

		// ��ȡNCEdit������
		String[] readlist_NCEdit = Constans.mouldData
				.readOutFile(Constans.mouldDataPATH + foldername
						+ File.separator + Constans.nc�ı�����);// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist_NCEdit.length; i++) {
			readlist_NCEdit[i] = readlist_NCEdit[i].replace("\n", "").trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
		}

		// ��ȡTableEdit������
		String[] readlist_TableEdit = Constans.mouldData
				.readOutFile(Constans.mouldDataPATH + foldername
						+ File.separator + Constans.table�ı�����);// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist_TableEdit.length; i++) {
			readlist_TableEdit[i] = readlist_TableEdit[i].replace("\n", "")
					.trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
		}

		if (readlist_Device.length < 59) {
			// errorString = "�豸�����ļ���ͷ������";
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
			   .setMessage("δ���룡�豸�����ļ���ͷ�������������ļ���")
			   .setPositiveButton("֪����", null).show();
		}else if (readlist_NCEdit.length < 39) {
			// errorString = "�豸�����ļ���ͷ������";
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
			   .setMessage("δ���룡NC�ļ���ͷ�������������ļ���")
			   .setPositiveButton("֪����", null).show();
		}else if (readlist_TableEdit.length <39) {
			// errorString = "�豸�����ļ���ͷ������";
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
			   .setMessage("δ���룡Table�ļ���ͷ�������������ļ���")
			   .setPositiveButton("֪����", null).show();
		} else {
			// �õ�markname��index�͸��εĳ�������
			// step_DeviceActualInput = Integer.parseInt(readlist[3].replace(
			// "Step=", ""));

			mark_list.get(0).put("DeviceActualInput", 4);
			for (int i = 0; i < readlist_Device.length; i++) {
				if (readlist_Device[i].equals("[DeviceActualOutput]")) {
					mark_list.get(0).put("DeviceActualOutput", i);
					// step_DeviceActualOutput = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceMiddleInput]")) {
					mark_list.get(0).put("DeviceMiddleInput", i);
					// step_DeviceMiddleInput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceMiddleOutput]")) {
					mark_list.get(0).put("DeviceMiddleOutput", i);
					// step_DeviceMiddleOutput = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceDataInput]")) {
					mark_list.get(0).put("DeviceDataInput", i);
					// step_DeviceDataInput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceDataOutput]")) {
					mark_list.get(0).put("DeviceDataOutput", i);
					// step_DeviceDataOutput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceRegister]")) {
					mark_list.get(0).put("DeviceRegister", i);
					// step_DeviceRegister = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceDataRegister]")) {
					mark_list.get(0).put("DeviceDataRegister", i);
					// step_DeviceDataRegister = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceTimer]")) {
					mark_list.get(0).put("DeviceTimer", i);
					// step_DeviceTimer = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceCounter]")) {
					mark_list.get(0).put("DeviceCounter", i);
					// step_DeviceCounter = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DevicePosition]")) {
					mark_list.get(0).put("DevicePosition", i);
					// step_DevicePosition = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_Device[i].equals("[DeviceAlarm]")) {
					mark_list.get(0).put("DeviceAlarm", i);
					// step_DeviceAlarm = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				}else if (readlist_Device[i].equals("[DeviceOptional]")) {
					mark_list.get(0).put("DeviceOptional", i);
					// step_DeviceAlarm = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				}
			}
			mark_list.get(0).put("NCEdit1", 4);
			for (int i = 0; i < readlist_NCEdit.length; i++) {
				if (readlist_NCEdit[i].equals("[NCEdit1]")) {
					mark_list.get(0).put("NCEdit1", i);
					// step_DeviceActualOutput = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit2]")) {
					mark_list.get(0).put("NCEdit2", i);
					// step_DeviceMiddleInput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit3]")) {
					mark_list.get(0).put("NCEdit3", i);
					// step_DeviceMiddleOutput = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit4]")) {
					mark_list.get(0).put("NCEdit4", i);
					// step_DeviceDataInput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit5]")) {
					mark_list.get(0).put("NCEdit5", i);
					// step_DeviceDataOutput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit6]")) {
					mark_list.get(0).put("NCEdit6", i);
					// step_DeviceRegister = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit7]")) {
					mark_list.get(0).put("NCEdit7", i);
					// step_DeviceDataRegister = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_NCEdit[i].equals("[NCEdit8]")) {
					mark_list.get(0).put("NCEdit8", i);
					// step_DeviceTimer = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} 
			}
			
			mark_list.get(0).put("TableEdit1", 4);
			for (int i = 0; i < readlist_TableEdit.length; i++) {
				if (readlist_TableEdit[i].equals("[TableEdit1]")) {
					mark_list.get(0).put("TableEdit1", i);
					// step_DeviceActualOutput = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit2]")) {
					mark_list.get(0).put("TableEdit2", i);
					// step_DeviceMiddleInput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit3]")) {
					mark_list.get(0).put("TableEdit3", i);
					// step_DeviceMiddleOutput = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit4]")) {
					mark_list.get(0).put("TableEdit4", i);
					// step_DeviceDataInput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit5]")) {
					mark_list.get(0).put("TableEdit5", i);
					// step_DeviceDataOutput = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit6]")) {
					mark_list.get(0).put("TableEdit6", i);
					// step_DeviceRegister = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit7]")) {
					mark_list.get(0).put("TableEdit7", i);
					// step_DeviceDataRegister = Integer.parseInt(readlist[i -
					// 1]
					// .replace("Step=", ""));
				} else if (readlist_TableEdit[i].equals("[TableEdit8]")) {
					mark_list.get(0).put("TableEdit8", i);
					// step_DeviceTimer = Integer.parseInt(readlist[i - 1]
					// .replace("Step=", ""));
				} 
			}
			// ����������Ĳ������������ڼ�����
			step_DeviceActualInput = (mark_list.get(0)
					.get("DeviceActualOutput").intValue()
					- 4 - mark_list.get(0).get("DeviceActualInput").intValue() - 1) / 4;
			step_DeviceActualOutput = (mark_list.get(0)
					.get("DeviceMiddleInput").intValue()
					- 4 - mark_list.get(0).get("DeviceActualOutput").intValue() - 1) / 4;
			step_DeviceMiddleInput = (mark_list.get(0)
					.get("DeviceMiddleOutput").intValue()
					- 4 - mark_list.get(0).get("DeviceMiddleInput").intValue() - 1) / 4;
			step_DeviceMiddleOutput = (mark_list.get(0).get("DeviceDataInput")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceMiddleOutput").intValue() - 1) / 4;
			step_DeviceDataInput = (mark_list.get(0).get("DeviceDataOutput")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceDataInput").intValue() - 1) / 4;
			step_DeviceDataOutput = (mark_list.get(0).get("DeviceRegister")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceDataOutput").intValue() - 1) / 4;
			step_DeviceRegister = (mark_list.get(0).get("DeviceDataRegister")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceRegister").intValue() - 1) / 4;
			step_DeviceDataRegister = (mark_list.get(0).get("DeviceTimer")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceDataRegister").intValue() - 1) / 4;
			step_DeviceTimer = (mark_list.get(0).get("DeviceCounter")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceTimer").intValue() - 1) / 5;
			step_DeviceCounter = (mark_list.get(0).get("DevicePosition")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceCounter").intValue() - 1) / 5;
			step_DevicePosition = (mark_list.get(0).get("DeviceAlarm")
					.intValue()
					- 4 - mark_list.get(0).get("DevicePosition").intValue() - 1) / 5;
			step_DeviceAlarm = (mark_list.get(0).get("DeviceOptional")
					.intValue()
					- 4 - mark_list.get(0).get("DeviceAlarm").intValue() - 1) / 3;
			step_DeviceOptional = (readlist_Device.length - mark_list.get(0)
					.get("DeviceOptional").intValue()) / 4;

			/*step_NCEdit = (readlist_NCEdit.length - mark_list.get(0)
					.get("NCEdit").intValue()) / 5;DeviceOptional*/
			step_NCEdit1 = (mark_list.get(0)
					.get("NCEdit2").intValue()
					- 4 - mark_list.get(0).get("NCEdit1").intValue() - 1)  / 5;
			step_NCEdit2 = (mark_list.get(0)
					.get("NCEdit3").intValue()
					- 4 - mark_list.get(0).get("NCEdit2").intValue() - 1)  / 5;
			step_NCEdit3 = (mark_list.get(0)
					.get("NCEdit4").intValue()
					- 4 - mark_list.get(0).get("NCEdit3").intValue() - 1)  / 5;
			step_NCEdit4 = (mark_list.get(0)
					.get("NCEdit5").intValue()
					- 4 - mark_list.get(0).get("NCEdit4").intValue() - 1)  / 5;
			step_NCEdit5 = (mark_list.get(0)
					.get("NCEdit6").intValue()
					- 4 - mark_list.get(0).get("NCEdit5").intValue() - 1)  / 5;
			step_NCEdit6 = (mark_list.get(0)
					.get("NCEdit7").intValue()
					- 4 - mark_list.get(0).get("NCEdit6").intValue() - 1)  / 5;
			step_NCEdit7 = (mark_list.get(0)
					.get("NCEdit8").intValue()
					- 4 - mark_list.get(0).get("NCEdit7").intValue() - 1)  / 5;
			step_NCEdit8 = (readlist_NCEdit.length
					- 4 - mark_list.get(0).get("NCEdit8").intValue() - 1)  / 5;

			/*step_TableEdit = (readlist_TableEdit.length - mark_list.get(0)
					.get("TableEdit").intValue()) / 4;*/
			step_TableEdit1 = (mark_list.get(0)
					.get("TableEdit2").intValue()
					- 4 - mark_list.get(0).get("TableEdit1").intValue() - 1)  / 4;
			step_TableEdit2 = (mark_list.get(0)
					.get("TableEdit3").intValue()
					- 4 - mark_list.get(0).get("TableEdit2").intValue() - 1)  / 4;
			step_TableEdit3 = (mark_list.get(0)
					.get("TableEdit4").intValue()
					- 4 - mark_list.get(0).get("TableEdit3").intValue() - 1)  / 4;
			step_TableEdit4 = (mark_list.get(0)
					.get("TableEdit5").intValue()
					- 4 - mark_list.get(0).get("TableEdit4").intValue() - 1)  / 4;
			step_TableEdit5 = (mark_list.get(0)
					.get("TableEdit6").intValue()
					- 4 - mark_list.get(0).get("TableEdit5").intValue() - 1)  / 4;
			step_TableEdit6 = (mark_list.get(0)
					.get("TableEdit7").intValue()
					- 4 - mark_list.get(0).get("TableEdit6").intValue() - 1)  / 4;
			step_TableEdit7 = (mark_list.get(0)
					.get("TableEdit8").intValue()
					- 4 - mark_list.get(0).get("TableEdit7").intValue() - 1)  / 4;
			step_TableEdit8 = (readlist_TableEdit.length
					- 4 - mark_list.get(0).get("TableEdit8").intValue() - 1)  / 4;

			// �ȼ�����û�д�������ʾ
			hasError = checkError_Device8before(hasError,
					step_DeviceActualInput, readlist_Device, mark_list,
					"DeviceActualInput", "X");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceActualOutput, readlist_Device, mark_list,
					"DeviceActualOutput", "Y");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceMiddleInput, readlist_Device, mark_list,
					"DeviceMiddleInput", "MX");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceMiddleOutput, readlist_Device, mark_list,
					"DeviceMiddleOutput", "MY");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError, step_DeviceDataInput,
					readlist_Device, mark_list, "DeviceDataInput", "DX");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceDataOutput, readlist_Device, mark_list,
					"DeviceDataOutput", "DY");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError, step_DeviceRegister,
					readlist_Device, mark_list, "DeviceRegister", "LM");
			System.out.println(hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceDataRegister, readlist_Device, mark_list,
					"DeviceDataRegister", "LD");
			System.out.println(hasError);
			hasError = checkError_Device2after(hasError, step_DeviceTimer,
					readlist_Device, mark_list, "DeviceTimer", "T");
			System.out.println(hasError);
			hasError = checkError_Device2after(hasError, step_DeviceCounter,
					readlist_Device, mark_list, "DeviceCounter", "C");
			System.out.println(hasError);
			hasError = checkError_DevicePosition(hasError, step_DevicePosition,
					readlist_Device, mark_list);
			System.out.println(hasError);
			hasError = checkError_DeviceAlarm(hasError, step_DeviceAlarm,
					readlist_Device, mark_list);
			System.out.println(hasError);
			hasError = checkError_DeviceOptional(hasError, step_DeviceOptional,
					readlist_Device, mark_list);
			System.out.println(hasError);
			
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit1, mark_list,"1");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit2, mark_list,"2");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit3, mark_list,"3");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit4, mark_list,"4");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit5, mark_list,"5");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit6, mark_list,"6");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit7, mark_list,"7");
			System.out.println(hasError);
			hasError = checkError_NCEdit(hasError, readlist_NCEdit,
					step_NCEdit8, mark_list,"8");
			System.out.println(hasError);
			
			/*hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit, mark_list);
			System.out.println(hasError);*/

			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit1, mark_list,"1");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit2, mark_list,"2");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit3, mark_list,"3");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit4, mark_list,"4");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit5, mark_list,"5");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit6, mark_list,"6");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit7, mark_list,"7");
			System.out.println(hasError);
			hasError = checkError_TableEdit(hasError, readlist_TableEdit,
					step_TableEdit8, mark_list,"8");
			System.out.println(hasError);
			// û��������ʾ
			if (!hasError) {
				read_8before(ArrayListBound.getDeviceActualInputListData(),
						"DeviceActualInput", readlist_Device,
						step_DeviceActualInput, mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceActualOutputListData(),
						"DeviceActualOutput", readlist_Device,
						step_DeviceActualOutput, mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceMiddleInputListData(),
						"DeviceMiddleInput", readlist_Device,
						step_DeviceMiddleInput, mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceMiddleOutputListData(),
						"DeviceMiddleOutput", readlist_Device,
						step_DeviceMiddleOutput, mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceDataInputListData(),
						"DeviceDataInput", readlist_Device,
						step_DeviceDataInput, mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceDataOutputListData(),
						"DeviceDataOutput", readlist_Device,
						step_DeviceDataOutput, mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceRegisterListData(),
						"DeviceRegister", readlist_Device, step_DeviceRegister,
						mark_list,targetActivity);

				read_8before(ArrayListBound.getDeviceDataRegisterListData(),
						"DeviceDataRegister", readlist_Device,
						step_DeviceDataRegister, mark_list,targetActivity);

				read_4after(ArrayListBound.getDeviceTimerListData(),
						"DeviceTimer", readlist_Device, step_DeviceTimer,
						mark_list,targetActivity);

				read_4after(ArrayListBound.getDeviceCounterListData(),
						"DeviceCounter", readlist_Device, step_DeviceCounter,
						mark_list,targetActivity);

				read_4after(ArrayListBound.getDevicePositionListData(),
						"DevicePosition", readlist_Device, step_DevicePosition,
						mark_list,targetActivity);

				read_4after(ArrayListBound.getDeviceAlarmListData(),
						"DeviceAlarm", readlist_Device, step_DeviceAlarm,
						mark_list,targetActivity);
				
				read_Optional(ArrayListBound.getDeviceOptionalListData(),
						"DeviceOptional", readlist_Device, step_DeviceOptional,
						mark_list,targetActivity);

				//read_NCEdit(readlist_NCEdit, step_NCEdit);
				read_NCEdit(ArrayListBound.getNCeditList1Data(),
						"NCEdit1", readlist_NCEdit, step_NCEdit1,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList2Data(),
						"NCEdit2", readlist_NCEdit, step_NCEdit2,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList3Data(),
						"NCEdit3", readlist_NCEdit, step_NCEdit3,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList4Data(),
						"NCEdit4", readlist_NCEdit, step_NCEdit4,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList5Data(),
						"NCEdit5", readlist_NCEdit, step_NCEdit5,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList6Data(),
						"NCEdit6", readlist_NCEdit, step_NCEdit6,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList7Data(),
						"NCEdit7", readlist_NCEdit, step_NCEdit7,
						mark_list,targetActivity);
				read_NCEdit(ArrayListBound.getNCeditList8Data(),
						"NCEdit8", readlist_NCEdit, step_NCEdit8,
						mark_list,targetActivity);

				//read_TableEdit(readlist_TableEdit, step_TableEdit);
				read_TableEdit(ArrayListBound.getTableList1Data(),
						"TableEdit1", readlist_TableEdit, step_TableEdit1,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList2Data(),
						"TableEdit2", readlist_TableEdit, step_TableEdit2,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList3Data(),
						"TableEdit3", readlist_TableEdit, step_TableEdit3,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList4Data(),
						"TableEdit4", readlist_TableEdit, step_TableEdit4,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList5Data(),
						"TableEdit5", readlist_TableEdit, step_TableEdit5,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList6Data(),
						"TableEdit6", readlist_TableEdit, step_TableEdit6,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList7Data(),
						"TableEdit7", readlist_TableEdit, step_TableEdit7,
						mark_list,targetActivity);
				read_TableEdit(ArrayListBound.getTableList8Data(),
						"TableEdit8", readlist_TableEdit, step_TableEdit8,
						mark_list,targetActivity);

				// byteArray=readMouldData2();//�ṩ����Ľӿ�

				// // ������ʾ
				// updateAdapter();
				// // �������
				// foldername_opened = foldername;
				// newsaveflag = false;

				// Toast.makeText(this, "�ļ���ȡ�ɹ�", Toast.LENGTH_SHORT).show();
				if(flag==0){
				new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
				.setMessage("�ѳɹ���ȡ�ϴδ򿪵��ļ�")
				.setPositiveButton("֪����", null).show();}
				//firstOpen = false;

			} else {
				new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
				.setMessage(errorString)
				.setPositiveButton("֪����", null).show();
				//Toast.makeText(targetActivity, "�ļ����ڴ���" + errorString, Toast.LENGTH_SHORT).show();
				System.out.println("�ļ����ڴ���" + errorString);
			}

		}

	}

	/**
	 * 
	 * @Title: checkError_Device8before
	 * @Description: ����豸������ǰ8�����
	 * @param hasError
	 *            ÿ���һ�ȷ���Ƿ���ڴ���
	 * @param step_Device
	 *            �豸�����и���ĳ�������
	 * @param readlist
	 *            ֱ�Ӵ��ļ��ж�ȡ�Ĵ�����
	 * @param mark_list
	 *            ���ÿ��ı�־
	 * @param markName
	 *            ÿ��ı�־��
	 * @param headString
	 *            ����������ʼ��ĸ
	 * @return boolean
	 * @throws
	 */
	private boolean checkError_Device8before(boolean hasError, int step_Device,
			String[] readlist, ArrayList<HashMap<String, Integer>> mark_list,
			String markName, String headString) {
		try {
		if (!hasError) {
			if(step_Device<0){
					errorString += "�豸����"+markName+"�ĺ��漸����ڴ��������ļ���";
					hasError = true;
				}
			for (int i = 0; i < step_Device; i++) {
				try{
				    Integer.valueOf(readlist[4 * i+ mark_list.get(0).get(markName) + 1]);
				}catch(Exception e) {
					// TODO: handle exception
					errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
					hasError = true;
				    break;
				}
				String stringToCheck = readlist[4 * i+ mark_list.get(0).get(markName) + 2].toString().trim();
				if (!stringToCheck.equals("")) {
					if(markName.equals("DeviceActualInput")||markName.equals("DeviceActualOutput")){
					if ((!stringToCheck.startsWith(headString))
							|| ((stringToCheck.getBytes("gb2312")).length > 8)) {
						try{
						    errorString += "�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[4 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨���󣬷���������";
						}catch(Exception e) {
							// TODO: handle exception
							errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
						}
						hasError = true;
						System.out.println("�ļ����ڴ���  " + errorString);
						break;// �д��������ѭ��
					} else if (((readlist[4 * i + mark_list.get(0).get(markName)+ 3].getBytes("gb2312")).length > 24)
					                     || ((readlist[4 * i + mark_list.get(0).get(markName)+ 4].getBytes("gb2312")).length > 80)) {
						
						try{
						     errorString +="�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[4 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨�����ź�����ע�ͳ��ȴ���";
						}catch(Exception e) {
							// TODO: handle exception
							errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
						}
						hasError = true;
						System.out.println("�ļ����ڴ���  " + errorString);
						break;
					}
					}else{
						if ((!stringToCheck.startsWith(headString))
								|| ((stringToCheck.getBytes("gb2312")).length > 20)) {
							try{
							    errorString += "�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[4 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨���󣬷���������";
							}catch(Exception e) {
								// TODO: handle exception
								errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
							}
							hasError = true;
							System.out.println("�ļ����ڴ���  " + errorString);
							break;// �д��������ѭ��
						} else if (((readlist[4 * i + mark_list.get(0).get(markName)+ 3].getBytes("gb2312")).length >40)
						                     || ((readlist[4 * i + mark_list.get(0).get(markName)
						                                  + 4].getBytes("gb2312")).length > 80)) {
							
							try{
							     errorString +="�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[4 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨�����ź�����ע�ͳ��ȴ���";
							}catch(Exception e) {
								// TODO: handle exception
								errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
							}
							hasError = true;
							System.out.println("�ļ����ڴ���  " + errorString);
							break;
						}
					}
				}

			}
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasError;
	}

	/**
	 * 
	 * @Title: checkError_Device2after
	 * @Description: ����豸���嶨ʱ���ͼ������Ĵ���
	 * @param hasError
	 * @param step_Device
	 * @param readlist
	 * @param mark_list
	 * @param markName
	 * @param headString
	 * @return boolean
	 * @throws
	 */
	private boolean checkError_Device2after(boolean hasError, int step_Device,
			String[] readlist, ArrayList<HashMap<String, Integer>> mark_list,
			String markName, String headString) {
		try{
		if (!hasError) {
			if(step_Device<0){
				errorString += "�豸����"+markName+"�ĺ��漸����ڴ��������ļ���";
				hasError = true;
			}
			for (int i = 0; i < step_Device; i++) {
				 try{
					   Integer.valueOf(readlist[5 * i+ mark_list.get(0).get(markName) + 1]);
					}catch(Exception e) {
						// TODO: handle exception
						errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
						hasError = true;
					    break;
					}
				String stringToCheck = readlist[5 * i
				                                + mark_list.get(0).get(markName) + 2].toString().trim();
				if (!stringToCheck.equals("")) {
					if ((!stringToCheck.startsWith(headString))
							|| ((stringToCheck.getBytes("gb2312")).length > 8)) {
						try{
						    errorString += "�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[5 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨���󣬷���������";
						}catch(Exception e) {
							// TODO: handle exception
							errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
						}
						hasError = true;
						break;// �д��������ѭ��
					} else if ((readlist[5 * i + mark_list.get(0).get(markName)+ 3].getBytes("gb2312")).length > 24||
							(readlist[5 * i + mark_list.get(0).get(markName)+ 4].getBytes("gb2312")).length > 80) {
						try{
						    errorString += "�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[5 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨�����ź�����ע�ͳ��ȴ���";
						}catch(Exception e) {
							// TODO: handle exception
							errorString += "�豸����"+markName+"�е�"+i+"�и������ڴ���";
						}
						hasError = true;
						break;
					}
				}
			}
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasError;
	}

	/**
	 * 
	 * @Title: checkError_DevicePosition
	 * @Description: ��������豸����λ����Ĵ��� ����ΪheadString��Sp��FP��P����
	 * @param hasError
	 * @param step_DevicePosition
	 * @param readlist
	 * @param mark_list
	 * @return boolean
	 * @throws
	 */
	private boolean checkError_DevicePosition(boolean hasError,
			int step_DevicePosition, String[] readlist,
			ArrayList<HashMap<String, Integer>> mark_list) {
		try{
		if (!hasError) {
			if(step_DevicePosition<0){
				errorString += "�豸����λ�õĺ��漸����ڴ��������ļ���";
				hasError = true;
			}
			for (int i = 0; i < step_DevicePosition; i++) {
				try{
				    Integer.valueOf(readlist[5 * i + mark_list.get(0).get("DevicePosition")+ 1]);
				}catch(Exception e) {
					// TODO: handle exception
					errorString += "�豸����λ���е�"+i+"�и������ڴ���";
					hasError = true;
				    break;
				}
				String stringToCheck=readlist[5 * i + mark_list.get(0).get("DevicePosition")+ 2].toString().trim();
				if (!stringToCheck.equals("")) {
				if (!(readlist[5 * i + mark_list.get(0).get("DevicePosition")
				               + 2].startsWith("SP")
				               || readlist[5 * i
				                           + mark_list.get(0).get("DevicePosition") + 2]
				                        		   .startsWith("FP") || readlist[5 * i
				                        		                                 + mark_list.get(0).get("DevicePosition") + 2]
				                        		                                		 .startsWith("P"))
				                        		                                		 || ((readlist[5 * i+ mark_list.get(0).get("DevicePosition") + 2].getBytes("gb2312")).length > 8)) {
					try{
					    errorString += "�豸����λ�������Ϊ"+Integer.valueOf(readlist[5 * i+ mark_list.get(0).get("DevicePosition") + 1])+"��һ�д����﷨���󣬷���������";
					}catch(Exception e) {
						// TODO: handle exception
						errorString += "�豸����λ���е�"+i+"�и������ڴ���";
					}
					hasError = true;
					break;// �д��������ѭ��
				} else if ((readlist[5 * i+ mark_list.get(0).get("DevicePosition") + 3].getBytes("gb2312")).length > 24||
						(readlist[5 * i+ mark_list.get(0).get("DevicePosition") + 4].getBytes("gb2312")).length > 80) {
					try{
					errorString += "�豸����λ�������Ϊ"+Integer.valueOf(readlist[5 * i+ mark_list.get(0).get("DevicePosition") + 1])+"��һ�д����﷨�����ź�����ע�ͳ��ȴ���";
					}catch(Exception e) {
						// TODO: handle exception
						errorString += "�豸����λ���е�"+i+"�и������ڴ���";
					}
					hasError = true;
					break;
				}
				}
			}
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasError;
	}

	/**
	 * 
	 * @Title: checkError_DeviceAlarm
	 * @Description: ��������豸���徯���� �Ĵ�����Ϊ�����������Ϊ������
	 * @param hasError
	 * @param step_DeviceAlarm
	 * @param readlist
	 * @param mark_list
	 * @return boolean
	 * @throws
	 */
	private boolean checkError_DeviceAlarm(boolean hasError,
			int step_DeviceAlarm, String[] readlist,
			ArrayList<HashMap<String, Integer>> mark_list) {
       try{
		if (!hasError) {
			if(step_DeviceAlarm<0){
				errorString += "�豸���徯���ĺ��漸����ڴ��������ļ���";
				hasError = true;
			}
			for (int i = 0; i < step_DeviceAlarm; i++) {
				try{
				    Integer.valueOf(readlist[3 * i + mark_list.get(0).get("DeviceAlarm") + 1]);
				}catch(Exception e) {
					// TODO: handle exception
					errorString += "�豸���徯���е�"+i+"�и������ڴ���";
					hasError = true;
				    break;
				}
				String stringToCheck=readlist[3 * i + mark_list.get(0).get("DeviceAlarm") + 2].toString().trim();
				if (!stringToCheck.equals("")) {
				if (!(readlist[3 * i + mark_list.get(0).get("DeviceAlarm") + 2]
						.matches("[0-9]+"))// �������ʽ���ж��Ƿ�ȫ��������
						|| (readlist[3 * i + mark_list.get(0).get("DeviceAlarm")
						            + 2].getBytes("gb2312")).length > 8) {
					try{
					    errorString += "�豸���徯�������Ϊ"+Integer.valueOf(readlist[3 * i+ mark_list.get(0).get("DeviceAlarm") + 1])+"��һ�д����﷨���󣬷���������";
					}catch(Exception e) {
						// TODO: handle exception
						errorString += "�豸���徯���е�"+i+"�и������ڴ���";
					}
					hasError = true;
					break;// �д��������ѭ��
				} else if ((readlist[3 * i + mark_list.get(0).get("DeviceAlarm")+ 3].getBytes("gb2312")).length > 60) {
					try{
					    errorString += "�豸���徯�������Ϊ"+Integer.valueOf(readlist[3 * i+ mark_list.get(0).get("DeviceAlarm") + 1])+"��һ�д����﷨����ע�ͳ��ȴ���";
					}catch(Exception e) {
						// TODO: handle exception
						errorString += "�豸���徯���е�"+i+"�и������ڴ���";
					}
					hasError = true;
					break;
				}
				}
			}
		}
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return hasError;
	}
	private boolean checkError_DeviceOptional(boolean hasError, int step_Device,
			String[] readlist, ArrayList<HashMap<String, Integer>> mark_list) {
		try{
		if (!hasError) {
			if(step_Device<0){
				errorString += "�豸����ѡ�������ĺ��漸����ڴ��������ļ���";
				hasError = true;
			}
			for (int i = 0; i < step_Device; i++) {
				try{
				    Integer.valueOf(readlist[4 * i + mark_list.get(0).get("DeviceOptional") + 1]);
				}catch(Exception e) {
					// TODO: handle exception
					errorString += "�豸����ѡ�������е�"+i+"�и������ڴ���";
					hasError = true;
				    break;
				}
				String name1=readlist[4 * i + mark_list.get(0).get("DeviceOptional") + 2].toString().trim();
				String name2=readlist[4 * i + mark_list.get(0).get("DeviceOptional") + 3].toString().trim();
				String name3=readlist[4 * i + mark_list.get(0).get("DeviceOptional") + 4].toString().trim();
				if ((!name1.equals(""))||(!name2.equals(""))||(!name3.equals(""))) {
					if(((name1.getBytes("gb2312")).length > 16)||((name2.getBytes("gb2312")).length > 8)||((name3.getBytes("gb2312")).length > 8)) {
						try{
						errorString += "�豸����ѡ�����������Ϊ"+Integer.valueOf(readlist[4 * i+ mark_list.get(0).get("DeviceOptional") + 1])+"��һ�д��ڳ���Խ�����";
						}catch(Exception e) {
							// TODO: handle exception
							errorString += "�豸����ѡ�������е�"+i+"�и������ڴ���";
						}
						hasError = true;
						break;// �д��������ѭ��
					}
				}
			}
		}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasError;
	}
	/**
	 * 
	 * @Title: checkError_NCEdit
	 * @Description: ���NCEdit����
	 * @param hasError
	 * @param readlist_NCEdit
	 * @param step_NCEdit
	 * @param mark_list
	 * @return boolean
	 * @throws
	 */
	private boolean checkError_NCEdit(boolean hasError,
			String[] readlist_NCEdit, int step_NCEdit,
			ArrayList<HashMap<String, Integer>> mark_list,String flag) {

		if (!hasError) {
			if (readlist_NCEdit == null || readlist_NCEdit.length < 5) {
				errorString += "NC��������﷨���󣬱�ͷ����";
				hasError = true;
			} else {
				String index="NCEdit"+flag;
				if(step_NCEdit<0){
					errorString += index+"�ĺ��漸����ڴ��������ļ���";
					hasError = true;
				}
				for (int i = 0; i < step_NCEdit; i++) {
					try{
					    Integer.valueOf(readlist_NCEdit[5 * i + mark_list.get(0).get(index)+ 1]);
					}catch(Exception e) {
						// TODO: handle exception
						errorString += index+"�е�"+i+"�и������ڴ���";
						hasError = true;
					    break;
					}
				}
			}

		}
		return hasError;
	}

	/**
	 * 
	 * @Title: checkError_TableEdit
	 * @Description: ���TableEdit����
	 * @param hasError
	 * @param readlist_TableEdit
	 * @param step_TableEdit
	 * @param mark_list
	 * @return boolean
	 * @throws
	 */
	private boolean checkError_TableEdit(boolean hasError,
			String[] readlist_TableEdit, int step_TableEdit,
			ArrayList<HashMap<String, Integer>> mark_list,String flag) {

		if (!hasError) {
			if (readlist_TableEdit == null || readlist_TableEdit.length < 5) {
				errorString += "Table��������﷨���󣬱�ͷ����";
				hasError = true;
			} else {
				String index="TableEdit"+flag;
				if(step_TableEdit<0){
					errorString += index+"�ĺ��漸����ڴ��������ļ���";
					hasError = true;
				}
				for (int i = 0; i < step_TableEdit; i++) {
					try{
					    Integer.valueOf(readlist_TableEdit[4 * i+ mark_list.get(0).get(index) + 1]);
					}catch(Exception e) {
						// TODO: handle exception
						errorString += index+"�е�"+i+"�и������ڴ���";
						hasError = true;
					    break;
					}
				}
			}

		}
		return hasError;
	}

	/**
	 * 
	 * @Title: updateAdapter
	 * @Description: ��ȡ�ļ�����Ҫ���ݵ�ǰҳ�����ж���Ӧ��adapter��������ʾ���������
	 * @return void
	 * @throws
	 */
	private void updateAdapter() {
		// ������ݵ�ǰ��ʾ��fragment������adapter����,������Ҳ���view�ɳ���
		String tab_current = getActionBar().getSelectedTab().getText()
				.toString().trim();
		if (tab_current.equals("�豸����")) {
			ListView lv = com.tr.programming.Device_ListFragment.listView_device;
			String child_current = lv.getAdapter()
					.getItem(lv.getCheckedItemPosition()).toString().trim();
			if (child_current.equals("ʵ������")) {
				Fragments_Device_ActualInput.ActualInput_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("ʵ�����")) {
				Fragments_Device_ActualOutput.ActualOutput_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("�м�����")) {
				Fragments_Device_MiddleInput.MiddleInput_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("�м����")) {
				Fragments_Device_MiddleOutput.MiddleOutput_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("��������")) {
				Fragments_Device_DataInput.DeviceDataInput_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("�������")) {
				com.tr.programming.Fragments_Device_DataOutput.DeviceDataOutput_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("����ɱ��ּĴ���")) {
				Fragments_Device_Register.DeviceRegister_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("����ɱ������ݼĴ���")) {
				Fragments_Device_DataRegister.DeviceDataRegister_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("��ʱ��")) {
				Fragments_Device_Timer.DeviceTimer_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("������")) {
				Fragments_Device_Counter.DeviceCounter_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("λ��")) {
				Fragments_Device_Position.DevicePosition_Adapter
				.notifyDataSetChanged();
			} else if (child_current.equals("����")) {
				Fragments_Device_Alarm.DeviceAlarm_Adapter
				.notifyDataSetChanged();
			}else if (child_current.equals("ѡ������")) {
				Fragments_Device_Optional.DeviceOptional_Adapter
				.notifyDataSetChanged();
			}
		} else if (tab_current.equals("NC�༭")) {
			//Fragments_NCedit.NCedit_Adapter.notifyDataSetChanged();
			ListView lv = com.tr.programming.Device_ListFragment.listView_nc;
			String child_current = lv.getAdapter()
					.getItem(lv.getCheckedItemPosition()).toString().trim();
			if (child_current.equals("ԭ��")) {
				Fragments_NCedit1.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("������")) {
				Fragments_NCedit2.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("����1")) {
				Fragments_NCedit3.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("����2")) {
				Fragments_NCedit4.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("����3")) {
				Fragments_NCedit5.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("����4")) {
				Fragments_NCedit6.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("����5")) {
				Fragments_NCedit7.NCedit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("����6")) {
				Fragments_NCedit8.NCedit_Adapter.notifyDataSetChanged();
			}  
		} else if (tab_current.equals("Table�༭")) {
			ListView lv = com.tr.programming.Device_ListFragment.listView_table;
			String child_current = lv.getAdapter()
					.getItem(lv.getCheckedItemPosition()).toString().trim();
			if (child_current.equals("T�༭1")) {
				Fragments_Table1.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭2")) {
				Fragments_Table2.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭3")) {
				Fragments_Table3.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭4")) {
				Fragments_Table4.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭5")) {
				Fragments_Table5.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭6")) {
				Fragments_Table6.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭7")) {
				Fragments_Table7.TableEdit_Adapter.notifyDataSetChanged();
			} else if (child_current.equals("T�༭8")) {
				Fragments_Table8.TableEdit_Adapter.notifyDataSetChanged();
			}  
		}
		/*
		else {
			Fragments_Table.TableEdit_Adapter.notifyDataSetChanged();
		}
*/
	}

	/**
	 * 
	 * @Title: read_8before
	 * @Description: ��ȡ�豸����ǰ8��
	 * @param ArrayList
	 * @param markname
	 * @param readlist
	 * @param step
	 * @param mark_list
	 * @return void
	 * @throws
	 */
	private void read_8before(ArrayList<HashMap<String, Object>> ArrayList,
			String markname, String[] readlist, int step,
			ArrayList<HashMap<String, Integer>> mark_list,Activity targetActivity) {
   
		int j = ArrayList.size();
		// ����������
		for (int i = 0; i < j; i++) {
			ArrayList.get(i).put("symbolNameEditText", "");
			ArrayList.get(i).put("signalNameEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}

		if (step == 0) {
			// Toast.makeText(targetActivity, markname+"����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
		     .setMessage( "�豸����"+markname+"�ĺ��漸����ڴ��������ļ���")
		     .setPositiveButton("֪����", null).show();
		} else {
			// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
			int k = step - j;

			// ��ʱ�������Ӳ������У����ù����ȱ���
			// if (k>0) {//���Ӳ�������
			// for (int i = 0; i <k+1 ; i++) {
			// HashMap<String, Object> mapadd = new HashMap<String, Object>();
			// mapadd.put("addressText", String.format("%04d", j+i));//
			// mapadd.put("symbolNameEditText","");
			// mapadd.put("signalNameEditText","");
			// mapadd.put("noteEditText","");
			// ArrayList.add(j+i,mapadd);
			//
			// }
			// }

			// ��ʱ������ȥ������У�ֻ��Ҫ�Ѷ�������ã��ù����ȱ���
			// else {
			// for ( int m=j-1; m>step; m--) {//��ȥ�������
			// ArrayList.remove(m);
			// /**
			// *
			// ֻ������һ��adapter����Ȼ�������Ҳ���getActivity()�����;������������������Ϳ���ʵʱˢ�£��������������ֻ�ܽ���һ���л���ˢ�µ�ǰ����
			// */
			// com.tr.programming.Fragments_Device_ActualInput.ActualInput_Adapter.notifyDataSetChanged();
			//
			// }
			// }

			// ��ȡ��ʾ,��ʾ�ڸ��Ե�λ����
			for (int i = 0; i < step; i++) {
				
				try{
					int pos = Integer.parseInt(readlist[4 * i+ mark_list.get(0).get(markname) + 1]);// �õ�ʵ��λ�õ�ֵַ
				
				ArrayList.get(pos).put("symbolNameEditText",
						readlist[4 * i + mark_list.get(0).get(markname) + 2]);
				ArrayList.get(pos).put("signalNameEditText",
						readlist[4 * i + mark_list.get(0).get(markname) + 3]);
				ArrayList.get(pos).put("noteEditText",
						readlist[4 * i + mark_list.get(0).get(markname) + 4]);
				} catch (Exception e) {
			// TODO: handle exception
			      new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
			     .setMessage( "�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���")
			     .setPositiveButton("֪����", null).show();
			      return;
		        }
			}

			// Toast.makeText(this, markname + "����������",
			// Toast.LENGTH_SHORT).show();

		} 
	}

	/**
	 * 
	 * @Title: read_4after
	 * @Description: ��ȡ�豸�����4��
	 * @param ArrayList
	 * @param markname
	 * @param readlist
	 * @param step
	 * @param mark_list
	 * @return void
	 * @throws
	 */
	private void read_4after(ArrayList<HashMap<String, Object>> ArrayList,
			String markname, String[] readlist, int step,
			ArrayList<HashMap<String, Integer>> mark_list,Activity targetActivity) {
		
		int j = ArrayList.size();
		// ����������
		if (markname.equals("DeviceAlarm")) {
		  for (int i = 0; i < j; i++) {
			ArrayList.get(i).put("symbolNameEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		  }
		}
		if (!markname.equals("DeviceAlarm")) {
			 for (int i = 0; i < j; i++) {
					ArrayList.get(i).put("symbolNameEditText", "");
					ArrayList.get(i).put("signalNameEditText", "");
					ArrayList.get(i).put("noteEditText", "");
				  }	
		}

		if (step == 0) {
			//Toast.makeText(targetActivity, markname + "����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
		     .setMessage( "�豸����"+markname+"�ĺ��漸����ڴ��������ļ���")
		     .setPositiveButton("֪����", null).show();
		}  else {
			// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
			int k = step - j;
			// ���Ӳ�������
			if (k > 0&&markname.equals("DeviceAlarm")) {
				for (int i = 0; i < k + 1; i++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%03d", j + i+1));
					mapadd.put("symbolNameEditText", "");
					mapadd.put("noteEditText", "");
					ArrayList.add(j + i, mapadd);

				}
			}

			if (k > 0&&markname.equals("DevicePosition")) {
				for (int i = 0; i < k + 1; i++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%04d", j + i+1));
					mapadd.put("symbolNameEditText", "");
					mapadd.put("signalNameEditText", "");
					mapadd.put("noteEditText", "");
					mapadd.put("setItem", "0");
					ArrayList.add(j + i, mapadd);

				}
			}

			if(k > 0&&(markname.equals("DeviceTimer")||markname.equals("DeviceCounter"))){
				for (int i = 0; i < k + 1; i++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%04d", j + i));
					mapadd.put("symbolNameEditText", "");
					mapadd.put("signalNameEditText", "");
					mapadd.put("noteEditText", "");
					mapadd.put("setItem", "0");
					ArrayList.add(j + i, mapadd);

				}
			}
			// ��ʱ������ȥ������У�ֻ��Ҫ�Ѷ�������ã��ù��ܱ���
			// else { //��ȥ�������
			// for ( int m=j-1; m>step; m--) {
			// ArrayList.remove(m);
			// }
			// }

			// ��ȡ��ʾ,�ӵ�һ�п�ʼ��ʾ
			// stepΪ0�Ļ��Ͳ���ʾ
//			for (int i = 0; i < step; i++) {
//				ArrayList.get(i).put("symbolNameEditText",
//						readlist[3 * i + mark_list.get(0).get(markname) + 2]);
//				ArrayList.get(i).put("noteEditText",
//						readlist[3 * i + mark_list.get(0).get(markname) + 3]);
//			}
			if (markname.equals("DeviceAlarm")) {
				for (int i = 0; i < step; i++) {
					try{
					int lineToAdd=Integer.valueOf(readlist[3 * i + mark_list.get(0).get(markname) + 1]);
					// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
					int m = lineToAdd- ArrayList.size();
					// ���Ӳ�������
					if (m > 0) {
						for (int n = 0; n < m + 1; n++) {
							HashMap<String, Object> mapadd = new HashMap<String, Object>();
							mapadd.put("addressText", String.format("%03d", ArrayList.size()+1));// //============λ�ñ�������
							mapadd.put("symbolNameEditText", "");
							mapadd.put("noteEditText", "");
							ArrayList.add(ArrayList.size() , mapadd);
						}
					}
					ArrayList.get(lineToAdd-1).put("symbolNameEditText",
							readlist[3 * i + mark_list.get(0).get(markname) + 2]);
					ArrayList.get(lineToAdd-1).put("noteEditText",
							readlist[3 * i + mark_list.get(0).get(markname) + 3]);
					} catch (Exception e) {
						// TODO: handle exception
						 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
						   .setMessage("�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���")
						   .setPositiveButton("֪����", null).show();
						 return;
					}
					
				}
			}else if (markname.equals("DevicePosition")) {
				for (int i = 0; i < step; i++) {
					try{
					int lineToAdd=Integer.valueOf(readlist[5 * i + mark_list.get(0).get(markname) + 1]);
					// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
					int m = lineToAdd- ArrayList.size();
					// ���Ӳ�������
					if (m > 0) {
						for (int n = 0; n < m + 1; n++) {
							HashMap<String, Object> mapadd = new HashMap<String, Object>();
							mapadd.put("addressText", String.format("%04d", ArrayList.size()+1));
							mapadd.put("symbolNameEditText", "");
							mapadd.put("signalNameEditText", "");
							mapadd.put("noteEditText", "");
							mapadd.put("setItem", "0");
							ArrayList.add(ArrayList.size(), mapadd);
						}
					}
					ArrayList.get(lineToAdd-1).put("symbolNameEditText",
							readlist[5 * i + mark_list.get(0).get(markname) + 2]);
					ArrayList.get(lineToAdd-1).put("signalNameEditText",
							readlist[5 * i + mark_list.get(0).get(markname) + 3]);
					ArrayList.get(lineToAdd-1).put("noteEditText",
							readlist[5 * i + mark_list.get(0).get(markname) + 4]);
					if(readlist[5 * i + mark_list.get(0).get(markname) + 5].equals("")){
						readlist[5 * i + mark_list.get(0).get(markname) + 5]="0";
					}
					ArrayList.get(lineToAdd-1).put("setItem",
							readlist[5 * i + mark_list.get(0).get(markname) + 5]);
					} catch (Exception e) {
						// TODO: handle exception
						 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
						   .setMessage("�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���")
						   .setPositiveButton("֪����", null).show();
						 return;
					}
				}
			}else {
				for (int i = 0; i < step; i++) {
					try{
					int lineToAdd=Integer.valueOf(readlist[5 * i + mark_list.get(0).get(markname) + 1]);
					// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
					int m = lineToAdd+1- ArrayList.size();
					// ���Ӳ�������
					if (m > 0) {
						for (int n = 0; n < m + 1; n++) {
							HashMap<String, Object> mapadd = new HashMap<String, Object>();
							mapadd.put("addressText", String.format("%04d", ArrayList.size()));
							mapadd.put("symbolNameEditText", "");
							mapadd.put("signalNameEditText", "");
							mapadd.put("noteEditText", "");
							mapadd.put("setItem", "0");
							ArrayList.add(ArrayList.size(), mapadd);
						}
					}
					ArrayList.get(lineToAdd).put("symbolNameEditText",
							readlist[5 * i + mark_list.get(0).get(markname) + 2]);
					ArrayList.get(lineToAdd).put("signalNameEditText",
							readlist[5 * i + mark_list.get(0).get(markname) + 3]);
					ArrayList.get(lineToAdd).put("noteEditText",
							readlist[5 * i + mark_list.get(0).get(markname) + 4]);
					if(readlist[5 * i + mark_list.get(0).get(markname) + 5].equals("")){
						readlist[5 * i + mark_list.get(0).get(markname) + 5]="0";
					}
					ArrayList.get(lineToAdd).put("setItem",
							readlist[5 * i + mark_list.get(0).get(markname) + 5]);
					} catch (Exception e) {
						// TODO: handle exception
						 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
						   .setMessage("�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���")
						   .setPositiveButton("֪����", null).show();
						 return;
					}
				}
			}
			
			// Toast.makeText(this, markname + "����������",
			// Toast.LENGTH_SHORT).show();

		}
	}
	private void read_Optional(ArrayList<HashMap<String, Object>> ArrayList,
			String markname, String[] readlist, int step,
			ArrayList<HashMap<String, Integer>> mark_list,Activity targetActivity) {
		
		int j = ArrayList.size();
		// ����������
		if (markname.equals("DeviceOptional")) {
			 for (int i = 0; i < j; i++) {
					ArrayList.get(i).put("name1", "");
					ArrayList.get(i).put("name2", "");
					ArrayList.get(i).put("name3", "");
				  }	
		}
		if (step== 0) {
			//Toast.makeText(targetActivity, markname + "����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
		     .setMessage( "�豸����"+markname+"�ĺ��漸����ڴ��������ļ���")
		     .setPositiveButton("֪����", null).show();
		}  else {
			if (markname.equals("DeviceOptional")) {
				for (int i = 0; i < step; i++) {
					try{
					int lineToAdd=Integer.valueOf(readlist[4 * i + mark_list.get(0).get(markname) + 1]);
					ArrayList.get(lineToAdd-1).put("name1",
							readlist[4 * i + mark_list.get(0).get(markname) + 2]);
					ArrayList.get(lineToAdd-1).put("name2",
							readlist[4 * i + mark_list.get(0).get(markname) + 3]);
					ArrayList.get(lineToAdd-1).put("name3",
							readlist[4 * i + mark_list.get(0).get(markname) + 4]);
					} catch (Exception e) {
						// TODO: handle exception
						 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
						   .setMessage("�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���")
						   .setPositiveButton("֪����", null).show();
						 return;
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Title: read_NCEdit
	 * @Description: ��ȡNCEdit
	 * @param readlist_NCEdit
	 * @param step_NCEdit
	 * @return void
	 * @throws
	 */
	private void read_NCEdit(ArrayList<HashMap<String, Object>> ArrayList,
			String markname, String[] readlist, int step,
			ArrayList<HashMap<String, Integer>> mark_list,Activity targetActivity) {
       
		// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
		int j = ArrayList.size();
		 for (int i = 0; i < j; i++) {
			 ArrayList.get(i).put("numSpinner", "");
				ArrayList.get(i).put("orderSpinner", "");
				ArrayList.get(i).put("operatText", "");
				ArrayList.get(i).put("noteEditText", "");
		 }
		if (step == 0) {
			//Toast.makeText(targetActivity, markname + "����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
		     .setMessage( markname+"�ĺ��漸����ڴ��������ļ���")
		     .setPositiveButton("֪����", null).show();
		}  else {
		int k = step- j;
		if (k > 0) {// ���Ӳ�������
			for (int i = 0; i < k + 1; i++) {
				HashMap<String, Object> mapadd = new HashMap<String, Object>();
				mapadd.put("addressText", String.format("%04d", j + i));//
				mapadd.put("numSpinner", "");
				mapadd.put("orderSpinner", "");
				mapadd.put("operatText", "");
				mapadd.put("noteEditText", "");
				ArrayList.add(j + i, mapadd);
			}
		}

				for (int i = step; i < j; i++) {
					ArrayList.get(i).put("numSpinner", "");
					ArrayList.get(i).put("orderSpinner", "");
					ArrayList.get(i).put("operatText", "");
					ArrayList.get(i).put("noteEditText", "");
				}
		// ��ȡ��ʾ,�ӵ�һ�п�ʼ��ʾ
		for (int i = 0; i < step; i++) {
			try{
			int pos = Integer.parseInt(readlist[5 * i
			                                    + mark_list.get(0).get(markname) + 1]);// �õ�ʵ��λ�õ�ֵַ
			// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
			int m = pos+1- ArrayList.size();
			// ���Ӳ�������
			if (m > 0) {
				for (int n = 0; n < m + 1; n++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%04d", ArrayList.size()));//
					mapadd.put("numSpinner", "");
					mapadd.put("orderSpinner", "");
					mapadd.put("operatText", "");
					mapadd.put("noteEditText", "");
					ArrayList.add(ArrayList.size(), mapadd);
				}
			}
			ArrayList.get(pos)
			.put("numSpinner", readlist[5 * i + mark_list.get(0).get(markname) + 2]);
			ArrayList.get(pos)
			.put("orderSpinner", readlist[5 * i  + mark_list.get(0).get(markname) + 3]);
			ArrayList.get(pos)
			.put("operatText", readlist[5 * i  + mark_list.get(0).get(markname) + 4]);
			ArrayList.get(pos)
			.put("noteEditText", readlist[5 * i + mark_list.get(0).get(markname) + 5]);
			} catch (Exception e) {
				// TODO: handle exception
				 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
				   .setMessage(markname+"�е�"+i+"�и������ڴ��������ļ���")
				   .setPositiveButton("֪����", null).show();
				 return;
			}
			// com.tr.programming.Fragments_NCedit.NCedit_Adapter.notifyDataSetChanged();
		}
		// Toast.makeText(this, "nc�ı�����������", Toast.LENGTH_SHORT).show();
		}
   	}

	

	/**
	 * 
	 * @Title: read_TableEdit
	 * @Description: ��ȡTableEdit
	 * @param readlist_TableEdit
	 * @param step_TableEdit
	 * @return void
	 * @throws
	 */
	private void read_TableEdit(ArrayList<HashMap<String, Object>> ArrayList,
			String markname, String[] readlist, int step,
			ArrayList<HashMap<String, Integer>> mark_list,Activity targetActivity) {
      
		// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
		int j = ArrayList.size();
		for (int i = 0; i < j; i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
		if (step== 0) {
			//Toast.makeText(targetActivity, markname + "����Ϊ��", Toast.LENGTH_SHORT).show();
		} else if (step <0) {
			 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
		     .setMessage( markname+"�ĺ��漸����ڴ��������ļ���")
		     .setPositiveButton("֪����", null).show();
		} else {
		int k = step - j;

		if (k > 0) {// ���Ӳ�������
			for (int i = 0; i < k + 1; i++) {
				HashMap<String, Object> mapadd = new HashMap<String, Object>();
				mapadd.put("numText", String.format("%04d", j + i));//
				mapadd.put("inputEditText", "");
				mapadd.put("outputEditText", "");
				mapadd.put("noteEditText", "");
				ArrayList.add(j + i, mapadd);
				// com.tr.programming.Fragments_Table.TableEdit_Adapter.notifyDataSetChanged();
			}
		}

		// ��ȡ��ʾ,�ӵ�һ�п�ʼ��ʾ
		for (int i = 0; i < step; i++) {
           try{
			int pos = Integer.parseInt(readlist[4 * i+ mark_list.get(0).get(markname) + 1]);// �õ�ʵ��λ�õ�ֵַ
			// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
						int m = pos+1- ArrayList.size();
						// ���Ӳ�������
						if (m > 0) {
							for (int n = 0; n < m + 1; n++) {
								HashMap<String, Object> mapadd = new HashMap<String, Object>();
								mapadd.put("numText", String.format("%04d", ArrayList.size()));//
								mapadd.put("inputEditText", "");
								mapadd.put("outputEditText", "");
								mapadd.put("noteEditText", "");
								ArrayList.add(ArrayList.size(), mapadd);
							}
						}
			ArrayList.get(pos)
			.put("inputEditText", readlist[4 * i + mark_list.get(0).get(markname) + 2]);
			ArrayList.get(pos)
			.put("outputEditText", readlist[4 * i  + mark_list.get(0).get(markname) + 3]);
			ArrayList.get(pos)
			.put("noteEditText", readlist[4 * i  + mark_list.get(0).get(markname) + 4]);
         } catch (Exception e) {
	      // TODO: handle exception
	       new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
	      .setMessage(markname+"�е�"+i+"�и������ڴ��������ļ���")
	      .setPositiveButton("֪����", null).show();
	       return;
         }
		
		}
		for (int i = step; i < j; i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
		}
	}

	/**
	 * 
	 * @Title: fileSave
	 * @Description: �����ļ��ṹ ��Ҫ�ж����½����ļ��ĵ�һ�α��滹�Ǵ򿪵��ļ��ı���
	 * @return void
	 * @throws
	 */
	// private void fileSave() {
	// // ����pad�ϱ༭�ĳ���
	// creatFolderSysytem();
	// final String date = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());//
	// ��ǰʱ��
	//
	// if (newsaveflag) {// Ϊ�����ǿ������±༭���ļ�
	//
	// final EditText editText = new EditText(this);
	// new AlertDialog.Builder(this)
	// .setTitle("�����±༭���ļ��������뱣����ļ���")
	// .setView(editText)
	// .setPositiveButton("ȷ��",
	// new DialogInterface.OnClickListener() {// �������ֺ�ȷ��
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	//
	// final String foldername = editText
	// .getText().toString().trim();
	// if (foldername != null
	// && foldername.length() != 0) {
	// newsaveflag = false;
	// foldername_opened = foldername;
	// File mouldData = new File(
	// Constans.mouldDataPATH);
	// final String[] folders = mouldData
	// .list();// �õ��ļ�����
	// boolean alreadExist = false;
	//
	// for (int i = 0; i < folders.length; i++) {// �ж�����������Ƿ����Ѵ��ڵ��ļ�������
	// if (folders[i].equals(foldername)) {
	// alreadExist = true;
	// }
	// }
	//
	// if (alreadExist) {// ��������ѡ���Ƿ񸲸�ԭ�ļ�
	// new AlertDialog.Builder(
	// TR_Programming_Activity.this)
	// .setTitle("ע�⣡")
	// .setMessage(
	// "�Ѵ��ڸ��ļ��У��Ƿ�Ҫ���ǣ�")
	// .setPositiveButton(
	// "ȷ��",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(
	// DialogInterface dialog,
	// int which) {// ȷ������
	// Constans.mouldData
	// .createFolder(
	// Constans.mouldDataPATH,
	// foldername)
	// .createFile(
	// Constans.�豸��������);
	// Constans.mouldData
	// .cleanFile(Constans.�豸��������);
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceActualInputListData(),
	// "[DeviceActualInput]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceActualOutputListData(),
	// "[DeviceActualOutput]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceMiddleInputListData(),
	// "[DeviceMiddleInput]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceMiddleOutputListData(),
	// "[DeviceMiddleOutput]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceDataInputListData(),
	// "[DeviceDataInput]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceDataOutputListData(),
	// "[DeviceDataOutput]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceRegisterListData(),
	// "[DeviceRegister]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceDataRegisterListData(),
	// "[DeviceDataRegister]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDeviceTimerListData(),
	// "[DeviceTimer]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDeviceCounterListData(),
	// "[DeviceCounter]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDevicePositionListData(),
	// "[DevicePosition]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDeviceAlarmListData(),
	// "[DeviceAlarm]",
	// date);
	// Constans.mouldData
	// .writeFile(
	// Constans.�豸��������,
	// "\r\n");// ��ͬ��֮���һ��
	//
	// saveAs_NcEdit(
	// foldername,
	// date);
	// saveAs_TableEdit(
	// foldername,
	// date);
	// Toast.makeText(
	// TR_Programming_Activity.this,
	// "�Ѹ����ļ���"
	// + foldername,
	// Toast.LENGTH_LONG)
	// .show();
	// }
	//
	// })
	// .setNegativeButton(
	// "ȡ��",
	// new DialogInterface.OnClickListener() {// �����ǣ���ʾ�����������ֱ���
	// @Override
	// public void onClick(
	// DialogInterface dialog,
	// int which) {
	// Toast.makeText(
	// TR_Programming_Activity.this,
	// "�����벻ͬ�����ٴα���",
	// Toast.LENGTH_SHORT)
	// .show();
	// }
	// }).show();
	// } else {// û������������ֱ�ӱ���
	//
	// Constans.mouldData.createFolder(
	// Constans.mouldDataPATH,
	// foldername).createFile(
	// Constans.�豸��������);
	// Constans.mouldData
	// .cleanFile(Constans.�豸��������);
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceActualInputListData(),
	// "[DeviceActualInput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceActualOutputListData(),
	// "[DeviceActualOutput]",
	// date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceMiddleInputListData(),
	// "[DeviceMiddleInput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceMiddleOutputListData(),
	// "[DeviceMiddleOutput]",
	// date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceDataInputListData(),
	// "[DeviceDataInput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceDataOutputListData(),
	// "[DeviceDataOutput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceRegisterListData(),
	// "[DeviceRegister]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(
	// foldername,
	// ArrayListBound
	// .getDeviceDataRegisterListData(),
	// "[DeviceDataRegister]",
	// date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDeviceTimerListData(),
	// "[DeviceTimer]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDeviceCounterListData(),
	// "[DeviceCounter]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDevicePositionListData(),
	// "[DevicePosition]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(
	// foldername,
	// ArrayListBound
	// .getDeviceAlarmListData(),
	// "[DeviceAlarm]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	//
	// saveAs_NcEdit(foldername, date);
	// saveAs_TableEdit(foldername, date);
	// Toast.makeText(
	// TR_Programming_Activity.this,
	// "�ѱ����ļ���" + foldername,
	// Toast.LENGTH_SHORT).show();
	// }
	// } else {
	// Toast.makeText(
	// TR_Programming_Activity.this,
	// "����Ϊ�գ���������������",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// }
	// }).setNegativeButton("ȡ��", null).show();
	// } else {// Ϊ�������Ѵ򿪹����Ա���ĵ��ļ�
	//
	// Constans.mouldData.createFolder(Constans.mouldDataPATH,
	// foldername_opened).createFile(Constans.�豸��������);
	// Constans.mouldData.cleanFile(Constans.�豸��������);
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceActualInputListData(),
	// "[DeviceActualInput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceActualOutputListData(),
	// "[DeviceActualOutput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceMiddleInputListData(),
	// "[DeviceMiddleInput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceMiddleOutputListData(),
	// "[DeviceMiddleOutput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceDataInputListData(),
	// "[DeviceDataInput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceDataOutputListData(),
	// "[DeviceDataOutput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceRegisterListData(),
	// "[DeviceRegister]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername_opened,
	// ArrayListBound.getDeviceDataRegisterListData(),
	// "[DeviceDataRegister]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername_opened,
	// ArrayListBound.getDeviceTimerListData(), "[DeviceTimer]",
	// date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername_opened,
	// ArrayListBound.getDeviceCounterListData(),
	// "[DeviceCounter]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername_opened,
	// ArrayListBound.getDevicePositionListData(),
	// "[DevicePosition]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername_opened,
	// ArrayListBound.getDeviceAlarmListData(), "[DeviceAlarm]",
	// date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	//
	// saveAs_NcEdit(foldername_opened, date);
	// saveAs_TableEdit(foldername_opened, date);
	// Toast.makeText(TR_Programming_Activity.this,
	// "�ѱ����ļ���" + foldername_opened, Toast.LENGTH_LONG).show();
	//
	// }
	// }

	/**
	 * 
	 * @Title: saveAllFilesAs
	 * @Description: �����ļ�����
	 * @param foldername
	 *            �����ļ��� ��Ҫ�ж��Ƿ��������Ƿ񸲸�
	 * @return void
	 * @throws
	 */
	// private void saveAllFilesAs(final String foldername) {// ����Ϊ����
	// // ����pad�ϱ༭�ĳ���
	// foldername_opened = foldername;
	// newsaveflag = false;
	// creatFolderSysytem();
	// final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	// .format(Calendar.getInstance().getTime());// ��ǰʱ��
	// boolean alreadExist = false;
	//
	// File mouldData = new File(Constans.mouldDataPATH);
	// final String[] folders = mouldData.list();// �õ��ļ�����
	//
	// for (int i = 0; i < folders.length; i++) {
	// if (folders[i].equals(foldername)) {
	// alreadExist = true;
	// }
	// }
	//
	// if (alreadExist) {
	// new AlertDialog.Builder(this)
	// .setTitle("ע�⣡")
	// .setMessage("�Ѵ��ڸ��ļ��У��Ƿ�Ҫ���ǣ�")
	// .setPositiveButton("ȷ��",
	// new DialogInterface.OnClickListener() {
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// Constans.mouldData.createFolder(
	// Constans.mouldDataPATH, foldername)
	// .createFile(Constans.�豸��������);
	// Constans.mouldData
	// .cleanFile(Constans.�豸��������);
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceActualInputListData(),
	// "[DeviceActualInput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceActualOutputListData(),
	// "[DeviceActualOutput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceMiddleInputListData(),
	// "[DeviceMiddleInput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceMiddleOutputListData(),
	// "[DeviceMiddleOutput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceDataInputListData(),
	// "[DeviceDataInput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceDataOutputListData(),
	// "[DeviceDataOutput]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceRegisterListData(),
	// "[DeviceRegister]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername, ArrayListBound
	// .getDeviceDataRegisterListData(),
	// "[DeviceDataRegister]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername, ArrayListBound
	// .getDeviceTimerListData(),
	// "[DeviceTimer]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername, ArrayListBound
	// .getDeviceCounterListData(),
	// "[DeviceCounter]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername, ArrayListBound
	// .getDevicePositionListData(),
	// "[DevicePosition]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername, ArrayListBound
	// .getDeviceAlarmListData(),
	// "[DeviceAlarm]", date);
	// Constans.mouldData.writeFile(
	// Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	//
	// saveAs_NcEdit(foldername, date);
	// saveAs_TableEdit(foldername, date);
	// }
	// }).setNegativeButton("ȡ��", null).show();
	// Toast.makeText(this, "�Ѹ����ļ���" + foldername, Toast.LENGTH_LONG)
	// .show();
	//
	// } else {
	// Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername)
	// .createFile(Constans.�豸��������);
	// Constans.mouldData.cleanFile(Constans.�豸��������);
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceActualInputListData(),
	// "[DeviceActualInput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceActualOutputListData(),
	// "[DeviceActualOutput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceMiddleInputListData(),
	// "[DeviceMiddleInput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceMiddleOutputListData(),
	// "[DeviceMiddleOutput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceDataInputListData(),
	// "[DeviceDataInput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceDataOutputListData(),
	// "[DeviceDataOutput]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceRegisterListData(),
	// "[DeviceRegister]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_8before(foldername,
	// ArrayListBound.getDeviceDataRegisterListData(),
	// "[DeviceDataRegister]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername, ArrayListBound.getDeviceTimerListData(),
	// "[DeviceTimer]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername,
	// ArrayListBound.getDeviceCounterListData(),
	// "[DeviceCounter]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername,
	// ArrayListBound.getDevicePositionListData(),
	// "[DevicePosition]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	// saveAs_4later(foldername, ArrayListBound.getDeviceAlarmListData(),
	// "[DeviceAlarm]", date);
	// Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	//
	// saveAs_NcEdit(foldername, date);
	// saveAs_TableEdit(foldername, date);
	// Toast.makeText(this, "���½��ļ���" + foldername, Toast.LENGTH_SHORT)
	// .show();
	// }
	//
	// }

	/**
	 * 
	 * @Title: saveAs_DeviceDefine
	 * @Description: �����豸�����ļ�
	 * @param foldername
	 * @param date
	 * @return void
	 * @throws
	 */
	private void saveAs_DeviceDefine(String foldername, String date) {
		Constans.cache.cleanFile(Constans.�豸��������);

		saveAs_8before(foldername,
				ArrayListBound.getDeviceActualInputListData(),
				"[DeviceActualInput]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceActualOutputListData(),
				"[DeviceActualOutput]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceMiddleInputListData(),
				"[DeviceMiddleInput]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceMiddleOutputListData(),
				"[DeviceMiddleOutput]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername, ArrayListBound.getDeviceDataInputListData(),
				"[DeviceDataInput]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceDataOutputListData(),
				"[DeviceDataOutput]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername, ArrayListBound.getDeviceRegisterListData(),
				"[DeviceRegister]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceDataRegisterListData(),
				"[DeviceDataRegister]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername, ArrayListBound.getDeviceTimerListData(),
				"[DeviceTimer]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername, ArrayListBound.getDeviceCounterListData(),
				"[DeviceCounter]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername, ArrayListBound.getDevicePositionListData(),
				"[DevicePosition]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername, ArrayListBound.getDeviceAlarmListData(),
				"[DeviceAlarm]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_Optional(foldername, ArrayListBound.getDeviceOptionalListData(),
				"[DeviceOptional]", date);
		Constans.cache.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	}

	/**
	 * 
	 * @Title: saveAs_8before
	 * @Description: �����豸����ǰ8��
	 * @param foldername
	 * @param ArrayList
	 * @param markname
	 * @param date
	 *            ����ʱ��
	 * @return void
	 * @throws
	 */
	private void saveAs_8before(String foldername,
			ArrayList<HashMap<String, Object>> ArrayList, String markname,
			String date) {
		int step1 = ArrayList.size();
		for (int i = 0; i < ArrayList.size(); i++) {
			String symbolString = ArrayList.get(i).get("symbolNameEditText")
					.toString().trim();
			String signalString = ArrayList.get(i).get("signalNameEditText")
					.toString().trim();
			String notesString = ArrayList.get(i).get("noteEditText")
					.toString().trim();
			if (symbolString.length() == 0 && signalString.length() == 0
					&& notesString.length() == 0) {
				step1--;
			}
		}
		final int step = step1;

		// д���ļ�ͷ
		Constans.cache.writeFile(Constans.�豸��������, "[Time]" + "/��" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, date + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, "[Step]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, markname + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayList.size(); i++) {
			String addresssString = ArrayList.get(i).get("addressText")
					.toString().trim();
			String symbolString = ArrayList.get(i).get("symbolNameEditText")
					.toString().trim();
			String signalString = ArrayList.get(i).get("signalNameEditText")
					.toString().trim();
			String notesString = ArrayList.get(i).get("noteEditText")
					.toString().trim();

			if (symbolString.length() == 0 && signalString.length() == 0
					&& notesString.length() == 0) {
				// do nothing
			} else {
				if (symbolString.length() == 0) {
					symbolString = " ";
				}
				if (signalString.length() == 0) {
					signalString = " ";
				}
				if (notesString.length() == 0) {
					notesString = " ";
				}
				Constans.cache.writeFile(Constans.�豸��������, addresssString + "/"
						+ symbolString + "/" + signalString + "/" + notesString
						+ "/" + "\r\n");
			}

		}

	}

	/**
	 * 
	 * @Title: saveAs_4later
	 * @Description: �����豸�����4��
	 * @param foldername
	 * @param ArrayList
	 * @param markname
	 * @param date
	 * @return void
	 * @throws 
	 */
	private void saveAs_4later(String foldername,
			ArrayList<HashMap<String, Object>> ArrayList, String markname,
			String date) {
		int step1 = ArrayList.size();
		String setString="";
		String signalString="";
		if(markname.equals("[DeviceAlarm]")){
		for (int i = 0; i < ArrayList.size(); i++) {
			String symbolString = ArrayList.get(i).get("symbolNameEditText")
					.toString().trim();
			String notesString = ArrayList.get(i).get("noteEditText")
					.toString().trim();
			if (symbolString.length() == 0 && notesString.length() == 0) {
				step1--;
			}
		}
		}
		if(!markname.equals("[DeviceAlarm]")){
			for (int i = 0; i < ArrayList.size(); i++) {
				String symbolString = ArrayList.get(i).get("symbolNameEditText")
						.toString().trim();
				signalString = ArrayList.get(i).get("signalNameEditText")
						.toString().trim();
				String notesString = ArrayList.get(i).get("noteEditText")
						.toString().trim();
				if (symbolString.length() == 0&& signalString.length() == 0 && notesString.length() == 0) {
					step1--;
				}
			}
			}
		final int step = step1;

		// д���ļ�ͷ
		Constans.cache.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, date + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, "[Step]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, markname + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayList.size(); i++) {
			String addresssString = ArrayList.get(i).get("addressText")
					.toString().trim();
			String symbolString = ArrayList.get(i).get("symbolNameEditText")
					.toString().trim();
			String notesString = ArrayList.get(i).get("noteEditText")
					.toString().trim();
			if(!markname.equals("[DeviceAlarm]")){
				signalString = ArrayList.get(i).get("signalNameEditText")
					.toString().trim();
				 setString = ArrayList.get(i).get("setItem")
							.toString().trim();
			}
			
			if (symbolString.length() == 0 && notesString.length() == 0&&signalString.length() == 0) {
				// do nothing
			} else {
				if (symbolString.length() == 0) {
					symbolString = " ";
				}
				if (notesString.length() == 0) {
					notesString = " ";
				}
				if(markname.equals("[DeviceAlarm]")){
					Constans.cache.writeFile(Constans.�豸��������, addresssString + "/"
							+ symbolString + "/" + notesString + "/"+ "\r\n");
				}else{
					Constans.cache.writeFile(Constans.�豸��������, addresssString + "/"
						+ symbolString + "/" + signalString + "/" + notesString + "/"+ setString + "/" + "\r\n");
				}
				
			}

		}

	}
	private void saveAs_Optional(String foldername,
			ArrayList<HashMap<String, Object>> ArrayList, String markname,
			String date) {
		// д���ļ�ͷ
		Constans.cache.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, date + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, "[Step]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������,
				"Step" + "=" + 32 + "/" + "\r\n");
		Constans.cache.writeFile(Constans.�豸��������, markname + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayList.size(); i++) {
			String addresssString = ArrayList.get(i).get("addressText")
					.toString().trim();
			String name1 = ArrayList.get(i).get("name1")
					.toString().trim();
			String name2 = ArrayList.get(i).get("name2")
					.toString().trim();
			String name3 = ArrayList.get(i).get("name3")
					.toString().trim();
			Constans.cache.writeFile(Constans.�豸��������, addresssString + "/"
					+ name1 + "/" + name2 + "/" + name3 + "/" + "\r\n");
			
		}

	}

	/**
	 * 
	 * @Title: saveAs_NcEdit
	 * @Description: ����NcEdit
	 * @param foldername
	 * @param date
	 * @return void
	 * @throws
	 */
	private static void saveAs_NcEdit(String foldername, String date) {
		Constans.cache.cleanFile(Constans.nc�ı�����);

		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList1Data(),
				"[NCEdit1]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList2Data(),
				"[NCEdit2]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList3Data(),
				"[NCEdit3]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList4Data(),
				"[NCEdit4]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList5Data(),
				"[NCEdit5]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList6Data(),
				"[NCEdit6]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList7Data(),
				"[NCEdit7]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList8Data(),
				"[NCEdit8]", date);
		Constans.cache.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		

	}
	private static void saveAs_8nc(String foldername,
			ArrayList<HashMap<String, Object>> ArrayList, String markname,
			String date) {
		int step1 = ArrayList.size();
		for (int i = 0; i < ArrayList.size(); i++) {
			String numString = ArrayList.get(i)
					.get("numSpinner").toString().trim();
			String orderString = ArrayList.get(i)
					.get("orderSpinner").toString().trim();
			String operatString =ArrayList.get(i)
					.get("operatText").toString().trim();
			String notesString = ArrayList.get(i)
					.get("noteEditText").toString().trim();
			if (numString.length() == 0 && orderString.length() == 0
					&& operatString.length() == 0 && notesString.length() == 0) {
				step1--;// ȥ����������
			}
		}
		final int step = step1;

		// д���ļ�ͷ
		Constans.cache.writeFile(Constans.nc�ı�����, "[Time]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.nc�ı�����, date + "/" + "\r\n");
		Constans.cache.writeFile(Constans.nc�ı�����, "[Step]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.nc�ı�����,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.cache.writeFile(Constans.nc�ı�����, markname + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayList.size(); i++) {
			String addresssString =ArrayList.get(i)
					.get("addressText").toString().trim();
			String numString = ArrayList.get(i)
					.get("numSpinner").toString().trim();
			String orderString = ArrayList.get(i)
					.get("orderSpinner").toString().trim();
			String operatString = ArrayList.get(i)
					.get("operatText").toString().trim();
			String notesString = ArrayList.get(i)
					.get("noteEditText").toString().trim();

			if (numString.length() == 0 && orderString.length() == 0
					&& notesString.length() == 0) {
				// do nothing
			} else {
				if (numString.length() == 0) {
					numString = " ";
				}
				if (orderString.length() == 0) {
					orderString = " ";
				}
				if (operatString.length() == 0
						|| operatString.split("\\W+").length == 0) {
					operatString = " ";
				}
				if (notesString.length() == 0) {
					notesString = " ";
				}
				Constans.cache.writeFile(Constans.nc�ı�����, addresssString + "/"
						+ numString + "/" + orderString + "/" + operatString
						+ "/" + notesString + "/" + "\r\n");
			}

		}

	}

	/**
	 * 
	 * @Title: saveAs_TableEdit
	 * @Description: ����TableEdit
	 * @param foldername
	 * @param date
	 * @return void
	 * @throws
	 */
	private void saveAs_TableEdit(String foldername, String date) {
		Constans.cache.cleanFile(Constans.table�ı�����);

		saveAs_8table(foldername,
				ArrayListBound.getTableList1Data(),
				"[TableEdit1]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList2Data(),
				"[TableEdit2]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList3Data(),
				"[TableEdit3]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList4Data(),
				"[TableEdit4]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList5Data(),
				"[TableEdit5]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList6Data(),
				"[TableEdit6]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList7Data(),
				"[TableEdit7]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList8Data(),
				"[TableEdit8]", date);
		Constans.cache.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		

	}
	private void saveAs_8table(String foldername,
			ArrayList<HashMap<String, Object>> ArrayList, String markname,
			String date) {
		int step1 = ArrayList.size();
		for (int i = 0; i < ArrayList.size(); i++) {
			String inputString = ArrayList.get(i)
					.get("inputEditText").toString().trim();
			String outputString = ArrayList.get(i)
					.get("outputEditText").toString().trim();
			String notesString = ArrayList.get(i)
					.get("noteEditText").toString().trim();
			if (inputString.length() == 0 && outputString.length() == 0
					&& notesString.length() == 0) {
				step1--;
			}
		}
		final int step = step1;

		//Constans.cache.cleanFile(Constans.table�ı�����);
		// д���ļ�ͷ
		Constans.cache.writeFile(Constans.table�ı�����, "[Time]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.table�ı�����, date + "/" + "\r\n");
		Constans.cache.writeFile(Constans.table�ı�����, "[Step]" + "/" + "\r\n");
		Constans.cache.writeFile(Constans.table�ı�����,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.cache.writeFile(Constans.table�ı�����, markname + "/"
				+ "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayList.size(); i++) {
			String numString = ArrayList.get(i)
					.get("numText").toString().trim();
			String inputString =ArrayList.get(i)
					.get("inputEditText").toString().trim();
			String outputString = ArrayList.get(i)
					.get("outputEditText").toString().trim();
			String notesString =ArrayList.get(i)
					.get("noteEditText").toString().trim();

			if (inputString.length() == 0 && outputString.length() == 0
					&& notesString.length() == 0) {
				// do nothing
			} else {
				if (inputString.length() == 0) {
					inputString = " ";// ����һ���ո�ռλ
				}
				if (outputString.length() == 0) {
					outputString = " ";
				}
				if (notesString.length() == 0) {
					notesString = " ";
				}
				Constans.cache.writeFile(Constans.table�ı�����, numString + "/"
						+ inputString + "/" + outputString + "/" + notesString
						+ "/" + "\r\n");
			}

		}

	}

	/**
	 * ����ģ�����ݣ�xyjͬѧ��ɣ�
	 * 
	 * @param foldername
	 * @param date
	 */
	private void saveMouldData(String foldername, String date) {

		/*
		 * Constans.mouldData.createFolder(Constans.trPATH,Constans.mouldDataFolder
		 * ).createFile(Constans.ģ������);//mould��trt
		 * Constans.mouldData.cleanFile(Constans.ģ������);
		 * 
		 * if (position>=0) {
		 * ArrayList.get(position).put("note_mould_setting",date); }
		 * 
		 * // ѭ��д��ÿһ�д��뵽�ļ��� for (int i = 0; i < ArrayList.size(); i++) { String
		 * numString = ArrayList.get(i).get("num") .toString().trim(); String
		 * num_mould_settingString = ArrayList.get(i).get("num_mould_setting")
		 * .toString().trim(); String name_mould_settingString =
		 * ArrayList.get(i).get("name_mould_setting") .toString().trim(); String
		 * note_mould_settingString = ArrayList.get(i).get("note_mould_setting")
		 * .toString().trim();
		 * 
		 * 
		 * 
		 * if (num_mould_settingString.length() == 0 &&
		 * name_mould_settingString.length() == 0 &&
		 * note_mould_settingString.length() == 0) { // do nothing } else { if
		 * (num_mould_settingString.length() == 0) { num_mould_settingString =
		 * " "; } if (name_mould_settingString.length() == 0) {
		 * name_mould_settingString = " "; } if
		 * (note_mould_settingString.length() == 0) { note_mould_settingString =
		 * " "; } Constans.cache.writeFile(Constans.ģ������, numString + "/" +
		 * num_mould_settingString + "/" + name_mould_settingString + "/" +
		 * note_mould_settingString + "/" + "\r\n"); }
		 * 
		 * }
		 */
	}

	/**
	 * 
	 * @Title: creatFolderSysytem
	 * @Description: ʵ�ֱ���д�ļ�ǰ��Ҫ��֤Ŀ¼����
	 * @return void
	 * @throws
	 */
	private void creatFolderSysytem() {
		// TODO Auto-generated method stub
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		// �����ļ���Ŀ¼
		if (!sdCardExist) {
			Toast.makeText(getApplicationContext(), "������ⲿSD�洢��",
					Toast.LENGTH_SHORT).show();// ���������SD����������ʾ
		} else {
			/* �ּ�Ŀ¼����ֿ�����������Ƕ�״�����������Ŀ¼���ܲ����� */

			File trFile = new File(Constans.trPATH);// �½�һ��Ŀ¼
			if (!trFile.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				trFile.mkdir();// ����������򴴽�
				Toast.makeText(getApplicationContext(),
						"�´����ļ��нṹ" + Constans.trFolder, Toast.LENGTH_SHORT)
						.show();// ��ʾ�´���
			} else {
				// Toast.makeText(getApplicationContext(),
				// "�Ѵ����ļ���" + Constans.trFolder, Toast.LENGTH_SHORT)
				// .show();// ��ʾ�Ѵ���
			}

			// �õ�
			// Constans.mechanicalParameter.createFolder(Constans.mechanicalParameterPATH).createFile("mechanicalParameter.set");
			// Constans.otherParameter.createFolder(Constans.otherParameterPATH).createFile("otherParameter.trt");
			// Constans. mouldData.createFolder(Constans.mouldDataPATH);
			// Constans.mouldData1.createFolder(Constans.mouldData1PATH).createFile("mouldData.trt");//�½�
			// ����Ŀ¼
			// Constans.mouldData2.createFolder(Constans.mouldData2PATH);
			// Constans.tableText.createFolder(Constans.tableTextPATH).createFile("Userlog.trt");
			// Constans.tableText.createFile("syslog.trd");
			// Constans.ncText.createFolder(Constans.ncTextPATH).createFile("userprg.trt");
			// Constans.deviceName.createFolder(Constans.deviceNamePATH).createFile("device.trt");
			// Constans.dataToDownload.createFolder(Constans.dataToDownloadPATH);
			// Constans.ncToTable.createFolder(Constans.ncToTablePATH).createFile("userprg.trd");
			// Constans.tableToData.createFolder(Constans.tableToDataPATH).createFile("Userlog.trd");
			// Constans.tableToData.createFile("syslog.trd");
			// Constans.positionSetData.createFolder(Constans.positionSetDataPATH).createFile("prdpara.set");
			// Constans.otherData.createFolder(Constans.otherDataPATH).createFile("otherData.trt");
			// Constans.mouldData3.createFolder(Constans.mouldData3PATH).createFile("mouldData3.trt");
			// Constans.mouldData4.createFolder(Constans.mouldData4PATH).createFile("mouldData4.trt");
			// Constans.otherData1.createFolder(Constans.otherData1PATH).createFile("otherData1.trt");
			// Constans.otherData2.createFolder(Constans.otherData2PATH).createFile("otherData2.trt");

			// �õ�
			// Constans.mechanicalParameter.createFolder(Constans.trPATH,
			// Constans.mechanicalParameterFolder).createFile("mechanicalParameter.set");
			// Constans.otherParameter.createFolder(Constans.trPATH,
			// Constans.otherParameterFolder).createFile("otherParameter.trt");
			// Constans.mouldData.createFolder(Constans.trPATH,
			// Constans.mouldDataFolder);
			// Constans.mouldData1.createFolder(Constans.mouldDataPATH,
			// Constans.mouldData1Folder).createFile(Constans.nc�ı�����)
			// .createFile(Constans.table�ı�����)
			// .createFile(Constans.�豸��������);
			// Constans.dataToDownload.createFolder(Constans.mouldData1PATH,
			// Constans.dataToDownloadFolder);
			// Constans.mouldData2.createFolder(Constans.mouldDataPATH,
			// Constans.mouldData2Folder).createFile(Constans.nc�ı�����)
			// .createFile(Constans.table�ı�����)
			// .createFile(Constans.�豸��������);
			// Constans.dataToDownload.createFolder(Constans.mouldData2PATH,
			// Constans.dataToDownloadFolder);
			Constans.mechanicalParameter.createFolder(Constans.trPATH,
					Constans.mechanicalParameterFolder);
			Constans.otherParameter.createFolder(Constans.trPATH,
					Constans.otherParameterFolder).createFile(
							"otherParameter.trt");
			Constans.mouldData.createFolder(Constans.trPATH,
					Constans.mouldDataFolder);
			Constans.cache.createFolder(Constans.mouldDataPATH, "cache")
			.createFile("device.trt").createFile("userprg.trt")
			.createFile("Userlog.trt");
			Constans.dataToDownload.createFolder(Constans.mouldDataPATH
					+ "cache" + File.separator, Constans.dataToDownloadFolder);

		}
	}


	
	public static void autoSaveAfterCrash()
	{
		final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(Calendar.getInstance().getTime());// ��ǰʱ��
		saveAs_NcEdit("cache", date);
	}	

	/**
	 * ���ؼ�ֱ�ӻص�������
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(WifiSetting_Info.progressDialog!=null){
			  WifiSetting_Info.progressDialog.setCanceledOnTouchOutside(true);
			}
			Device_ListFragment.fragmentncStatusFragment=null;
			Device_ListFragment.j=1;
			startActivity(new Intent().setClass(this, TR_Main_Activity.class));
			
		}

		return super.onKeyDown(keyCode, event);
	}
	  private class KeyEventReceiver extends BroadcastReceiver
	    {
	        @Override
	        public void onReceive(Context context, Intent intent)
	        {
	            String action = intent.getAction();
	            Log.d("mpeng","the KeyEventReceiver");
	            if(action.equals("KeyMsg"))
	            {
	            	Log.d("mpeng","KeyEventReceiver 1111111");
	            	 byte[] keycode = new byte[2];
	            	 keycode = intent.getExtras().getByteArray("keycode");
	            	 KeyMsgHandle(keycode,context);            
	            }
	            
	        }
	    }
	
	private void KeyMsgHandle(byte[]  kc ,Context context)
	{
			byte [] temp= new byte[2];
			System.arraycopy(kc, 0, temp, 0, 2);
		 int Key_Value =HexDecoding.Array2Toint(temp);//255
		 int Key_Function = kc[2] & 0xFF;//255
//		 if(KeyMsgDialog != null)
//       	KeyMsgDialog.dismiss();
//        KeyMsgDialog = new AlertDialog.Builder(context).setTitle("��ʾ")
//       .setMessage("keyvalue:"+ Key_Value+"keyfunction :"+Key_Function)
//       .setPositiveButton("OK", null).show();
//		
		 switch(Key_Value)
		 {
		 	 case 14:	
		 		KeyCodeSend.send(999, TR_Programming_Activity.this);
			 break;
		 	 case 11:  //HOME		 		 	
		 	 case 12:			 		
		 	 case 34:		 		
		 	 case 13:		 	
		 	 case 24:		 	
		 	 case 33:		 	
		 	 case 23:		 	
		 	 //case 153:
		 		 if(Key_Function==0)
		 		 {
		 			 Log.e("mpeng","SHOW key msg dialog");
			        if(KeyMsgDialog != null)
		            	KeyMsgDialog.dismiss();
		             KeyMsgDialog = new AlertDialog.Builder(context).setTitle("��ʾ")
		            .setMessage("�Ƿ�Ҫ�������ɲ�����"+ Key_Value)
		            .setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent it = new Intent();
							it.setClass(TR_Programming_Activity.this,NewPragramActivity.class);
							it.setFlags(3);
							startActivity(it);
						}
					})
		            .setPositiveButton("ȡ��", null)
		            .show();
		             KeyMsgDialog.setCanceledOnTouchOutside(false);
		 		 }
		 		 break;
		 		 
		 		 
		 	 case 32:
		 		 KeyCodeSend.send(26, TR_Programming_Activity.this);		 		 
		 		 break;
		 	 case 22:
		 		KeyCodeSend.send(25, TR_Programming_Activity.this);
		 		 break;
		 	 case 31:
		 		KeyCodeSend.send(24, TR_Programming_Activity.this);
		 		 break;
		 	 case 21:
		 		KeyCodeSend.send(23, TR_Programming_Activity.this);
		 		 break;	 
		 }
		
	}
}