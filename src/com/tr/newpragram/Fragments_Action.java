package com.tr.newpragram;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiReadMassiveDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.WatchRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.explain.HexDecoding;
import com.explain.NCTranslate;
import com.explain.TableToBinary;
import com.tr.R;
import com.tr.R.color;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.Client;
import com.wifiexchange.ServerUtils;
import com.wifiexchange.WifiSetting_Info;

public class Fragments_Action extends Fragment {
	String[] nc_ordersall=Constans.nc_orders;
	String[] nc_orders=new String[15];
	Constans.NCorders_enum  NCorders_enum;
	//private Activity targetActivity=getActivity();
	public static ListView mListView;
	DecimalFormat format = new DecimalFormat("####");// ��ʽת����,0001�ĸ�ʽ����ʱ��ô���ˣ��Ժ��ҵ����õķ��������滻
	public static ArrayList<HashMap<String, Object>> NcEditList;
	public ArrayList<HashMap<String, Object>> nc3List ;
	public ArrayList<HashMap<String, Object>> nc3ListBackup ;
	
	public static  MyAdapter NCedit_Adapter = null;
	
	public static Hashtable<Integer,View> temphHashTable = new Hashtable<Integer,View>();
   //add by mpeng
	
	private Button move= null;
	private Button qdbtn= null;
	private Button zjbtn= null;
	private Button zsjxhbtn= null;
	private Button wait= null;
	private Button jcbtn= null;
	private Button delaytime= null;
	
	private int myPos = -1;
	private Handler myHandler = null;
	private Button insertBtn = null;
	private Button delBtn = null;
	private Button DownBtn = null;
	private Button backupBtn = null;
	
	//for move 
	private LinearLayout PositionLayout =null ;
	private Button PositionAddBtn = null;
	private LinearLayout fspLayout =null ;
	private Spinner curPosition = null;
	private Spinner MoveSeclectSpinner = null; 
	private ArrayAdapter<String> PosSelAdapter  = null;
	private ArrayAdapter<String> TimerSelAdapter  = null;
	
	private ToggleButton FSpSwitchBtn = null;
	private TextView spProductUDTxt;
	private TextView spProductBFTxt;
	private CheckBox xbox;
	private CheckBox ybox;
	private CheckBox hbox;
	private CheckBox zbox;
	private CheckBox lbox;
	
	private TextView xTxt;
	private TextView yTxt;
	private TextView hTxt;
	private TextView zTxt;
	private TextView lTxt;
	
	private TextView xCurTxt;
	private TextView yCurTxt;
	private TextView hCurTxt;
	private TextView zCurTxt;
	private TextView lCurTxt;
	
	private EditText xEdTxt;
	private EditText yEdTxt;
	private EditText hEdTxt;
	private EditText zEdTxt;
	private EditText lEdTxt;
	
	private CheckBox zscheckbox1;
	private CheckBox zscheckbox2;
	
	private LinearLayout  layoutArmH =null ;
	private LinearLayout  layoutArmL =null ;
	
	
	
	private RadioButton radioON_zj1;
	private RadioButton radioOFF_zj1;
	private RadioButton radioON_zj2;
	private RadioButton radioOFF_zj2;
	private RadioButton radioON_zj3;
	private RadioButton radioOFF_zj3;
	private RadioButton radioON_zj4;
	private RadioButton radioOFF_zj4;
	private RadioButton radioON_zj5;
	private RadioButton radioOFF_zj5;
	private RadioButton radioON_zj6;
	private RadioButton radioOFF_zj6;
	private RadioButton radioON_zj7;
	private RadioButton radioOFF_zj7;
	private RadioButton radioON_zj8;
	private RadioButton radioOFF_zj8;
	private RadioButton radioON_zj9;
	private RadioButton radioOFF_zj9;
	private RadioButton radioON_zj10;
	private RadioButton radioOFF_zj10;
	private Button posRemBtn;
	
	private ListView fSpListView;
	private TextView FpNumText;
	private EditText FpNumEdit;
	public static ArrayList<HashMap<String, Object>> posList = new ArrayList<HashMap<String, Object>>();// ���Ҫ�󶨵�list
	//public static PositionQueryRunnable positionQueryRunnable;
	private EditText speed;
	private EditText aspeed;
	private EditText dspeed;
	private  List<String> PositionList  ; //position  list
	private  List<String> StandPackList  ; //position  list
	private  List<String> FreeList  ; //position  list
	
	private  List<String> TimerList ;  //TIMER  list
	
	public static ArrayList<HashMap<String, Object>> SpList_productshow=new ArrayList<HashMap<String, Object>>();
	public static ArrayList<HashMap<String, Object>> SpList_feedertroughshow=new ArrayList<HashMap<String, Object>>();
	public static ArrayList<HashMap<String, Object>> FpList_productshow=new ArrayList<HashMap<String, Object>>();
	public static ArrayList<HashMap<String, Object>> FpList_feedertroughshow=new ArrayList<HashMap<String, Object>>();
	private SendDataRunnable sendDataRunnable;
	private int[] overflag=new int[]{0,0,0,0,0,0,0,0};
	private  int[] alength;
	private ArrayList<SendDataRunnable> sendDataRunnables=new ArrayList<SendDataRunnable>();
	private String ioallString="";
	private byte[] iobyteArray=null;
	private String PosNumStr = null;
	private Activity myActivity;
	
	//������е����ƽ����﷨���
	private ArrayList<String> list_mouldname=new ArrayList<String>();
	private ArrayList<String> list_mouldnum=new ArrayList<String>();
	private ArrayList<HashMap<String, Object>> list_mould_setting = ArrayListBound.getMouldDataListData();
	private int saveposition;
	private String saveStrname;
	private int listselectposition=0;
	private boolean DownLoadSucess = false;
	private static boolean isModify = false;
	public static void changeModifyStatus(boolean change)
	{
		isModify = change;
	}
	public static boolean getModifyStatus()
	{
		return isModify;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		((NewPragramActivity) myActivity).nctestlist = NcEditList;
		super.onPause();
		System.out.println("destroy");	
		isModify = false;
	}


	public void showNote(){
		NCTranslate.noteList.clear();
		TR_Programming_Activity.noteflag=3;
		NCTranslate.beginTranslate(getActivity(),
				getResources().getStringArray(R.array.IF2));
		try{
//			NcEditList= ArrayListBound.getNCeditList3Data();
		for(int i=0;i<NcEditList.size();i++){
			if(NcEditList.get(i).get("operatText").equals("")&&NcEditList.get(i).get("orderSpinner").equals(""))
				continue;
			NcEditList.get(i).put("noteEditText", NCTranslate.noteList.get(i));
			NCedit_Adapter.notifyDataSetChanged();
			
		}
		TR_Programming_Activity.noteflag=0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void enableButton()
	{
		insertBtn.setEnabled(true);
    	delBtn.setEnabled(true);
    	//DownBtn.setEnabled(true);
    	//backupBtn.setEnabled(true);
	}
	private void disableButton(){
		insertBtn.setEnabled(false);
		delBtn.setEnabled(false);
		//DownBtn.setEnabled(false);
		//backupBtn.setEnabled(false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try{
		for(int i=0;i<nc_ordersall.length;i++){
			nc_orders[i]=nc_ordersall[i].split("\\s+")[0];
		}
		//nc3List �������get�������ݣ�
//		ncEditList ������ŵ�ǰACTIVIty��������
// backupNC ��������GET�������ݣ���������ʧ�ܺ�ԭ
		nc3List= ArrayListBound.getNCeditList3Data();
		NcEditList = new ArrayList<HashMap<String,Object>>(nc3List.size());  
		nc3ListBackup  = new ArrayList<HashMap<String,Object>>(nc3List.size());  
	        Iterator<HashMap<String,Object>> iterator = nc3List.iterator();
	        while (iterator.hasNext()) {
	        	HashMap<String, Object>map = (HashMap<String, Object>) iterator.next().clone();
	        	NcEditList.add(map);
	        	nc3ListBackup.add(map);
	        }	
	}catch(Exception e){
		e.printStackTrace();
	}
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("Fragments_Action--->onCreateView");
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		return inflater.inflate(R.layout.tab_action, container, false);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/***************************************************************/
		((NewPragramActivity) myActivity).nctestlist = NcEditList;
		try{
//			NcEditList = ArrayListBound.getNCeditList3Data();
		WatchRunnable.NCbeginWatch(mListView);
		/***************************************************************/
		System.out.println("Fragments_Action  alarmQueryRunnable");
		}catch(Exception e){
			e.printStackTrace();
		}
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
		ArrayList<HashMap<String, Object>> inlist = ArrayListBound.getDeviceActualInputListData();
		ArrayList<HashMap<String, Object>> outlist = ArrayListBound.getDeviceActualOutputListData();
        getmessage(inlist.subList(0, 32),1);
        getmessage(inlist.subList(32, 64),1);
        getmessage(inlist.subList(64, 96),1);
        getmessage(inlist.subList(96, 128),1);
        
        getmessage(outlist.subList(0, 32),1);
        getmessage(outlist.subList(32, 64),1);
        getmessage(outlist.subList(64, 96),1);
        getmessage(outlist.subList(96, 128),1);
    	long beforeTimehou=System.currentTimeMillis();
        System.out.println("ʵ�����롢ʵ�������Ϣ12345="+(beforeTimehou-beforeTimeqian));
        try {
        	iobyteArray=ioallString.getBytes("gb2312");System.out.println("0x080E0000="+iobyteArray.length);
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E0000), getActivity());
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
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080EF000), getActivity());
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
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E2000), getActivity());
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
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E2800), getActivity());
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
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E3000), getActivity());
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
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E5000), getActivity());
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
			sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(byteArray, 0x080E6000), getActivity());
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
			sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(byteArray, 0x080E7000), getActivity());
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
		    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080EB000), getActivity());
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
        
        byte[] getData3=new byte[200];
		char[] chs={};
		char ch=' ';
		int allA=0;
		try {
			for(int i=0;i<ArrayListBound.getNCeditList3Data().size();i++){
				String order_MOVE=ArrayListBound.getNCeditList3Data().get(i).get("orderSpinner").toString();
				String operatstr=ArrayListBound.getNCeditList3Data().get(i).get("operatText").toString();
				if((order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP"))&&operatstr.contains("SP")){
					
				}else if((order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP"))&&operatstr.contains("FP")){
					
				}else if((order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP"))&&operatstr.contains("P")){
					
					chs=operatstr.toCharArray();
					 ch=' ';
					 allA=0;
						for(int i1=0;i1<chs.length;i1++){
							if(chs[i1]=='A'){
								ch=chs[i1+1];
								int index=(int)ch-49;
								if((getData3[Integer.parseInt(operatstr.substring(operatstr.indexOf("P")+1))-1]&(int)(Math.pow(2, index)))==(int)(Math.pow(2, index))){
									continue;
								}
								if(index>=0&&index<=4){
									allA=allA+(int) (Math.pow(2, index));
								}
							}else if(chs[i1]=='P'||chs[i1]=='p'){
								getData3[Integer.parseInt(operatstr.substring(i1+1))-1]+=HexDecoding.int2byte(allA)[0];
								break;
							}
						}
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(getData3,0x080E4F00), getActivity());
		sendDataRunnables.add(sendDataRunnable);
		
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
		System.arraycopy(getData,0, getData2, 0x0000001C, 48);
        sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(getData2,0x20000000), getActivity());
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
			sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable,new FinishRunnable(getActivity(),backMessageTodo1)));
			getActivity().runOnUiThread(sendDataRunnable);
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
			Toast.makeText(getActivity(),R.string.T_wificonnfail,Toast.LENGTH_LONG).show();
		}
	};
	
	/***
	 * ���ӳɹ�
	 */
	private Runnable successRunnable=new Runnable() {
		@Override
		public void run() {
			Toast.makeText(getActivity(),R.string.T_wificonnsuccess,Toast.LENGTH_SHORT).show();
		}
		
	};
	
	
	private void  copyFile(String path,String name)
	{
		 try{
			String FileName =  path + "/" + name+".trt";
             File dir = new File(path);            
             if (!dir.exists())
                 dir.mkdir(); 
	             if (!(new File(FileName)).exists())
	             {
	            	 InputStream is = null;
	            	 if(name.equals("device"))
	            		     is = getResources().openRawResource(R.raw.device);
	            	 else if(name.equals("userprg"))
	            		 	 is = getResources().openRawResource(R.raw.userprg);
	            	 else if(name.equals("Userlog"))
	            			 is = getResources().openRawResource(R.raw.userlog);
	            	 
	            	 if(is == null)
	            		 return;
	            			 
	                 FileOutputStream fos = new FileOutputStream(FileName);
	                 byte[] buffer = new byte[8192];
	                 int count = 0;
	                 while ((count = is.read(buffer)) > 0)
	                 {
	                     fos.write(buffer, 0, count);
	                 }
	                 fos.close();
	                 is.close();
	             }
             }
		 catch (Exception e)
		{ 
			 new AlertDialog.Builder(getActivity())
			 	  .setTitle("���󱨸�")
			      .setMessage("�޷����ƣ�")
			      .setPositiveButton("ȷ��",
			                     new DialogInterface.OnClickListener(){
			                             public void onClick(DialogInterface dialoginterface, int i){
			                                                                        }
			                      })
			      .show();
		}
	}
	// �ο�Fragment����������
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("Fragments_Action--->onActivityCreated");
		super.onActivityCreated(savedInstanceState);// ���ø���ķ��������ϵͳ��һЩ����
        try{
		format.setMinimumIntegerDigits(4);// ��ʾ����4λ��
		
		mListView = (ListView) getActivity().findViewById(R.id.program_list);
		if(mListView==null){
			return;
		}
		myHandler =new Handler(){

			/* (non-Javadoc)
			 * @see android.os.Handler#handleMessage(android.os.Message)
			 */
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				switch (msg.what)
				{
				case 1:  //���ݣ�����ʱ���ݱ����SD������ͬ����NC3LIST��ȥ
				{
				final View view_newcreate=View.inflate(getActivity(), R.layout.tab_setting_mould_newcreate, null);
				new AlertDialog.Builder(getActivity())
				.setTitle("���Ϊ����").setView(view_newcreate)
				.setPositiveButton("ȷ��",
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						//1 �½��ļ���
						EditText editmouldnum=(EditText)view_newcreate.findViewById(R.id.editText_mouldnum);
						EditText editmouldname=(EditText)view_newcreate.findViewById(R.id.editText_mouldname);
						String editmouldnumstr=editmouldnum.getText().toString();
						String editmouldnamestr=editmouldname.getText().toString();
						if(editmouldnumstr.equals("")){
							Toast.makeText(getActivity(), "ģ�����ݱ��Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						Fragments_mouldData.checkMouldList(list_mouldnum, list_mould_setting,"num_mould_setting");
						if (list_mouldnum.contains(String.format("%1$04d",Integer.parseInt(editmouldnumstr)))) {
							Toast.makeText(getActivity(), "ģ�����ݱ���ظ�������������",Toast.LENGTH_SHORT).show();
							return;
						}
						if(editmouldnamestr.equals("")){
							Toast.makeText(getActivity(), "ģ����������Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						Fragments_mouldData.checkMouldList(list_mouldname, list_mould_setting,"name_mould_setting");
						if (list_mouldname.contains(editmouldnamestr.toLowerCase())) {
							Toast.makeText(getActivity(), "ģ�����������ظ�������������",Toast.LENGTH_SHORT).show();
							return;
						}
						if (editmouldnamestr.length()>18) {
							Toast.makeText(getActivity(), "ģ���������Ƴ��ȳ�����Χ������������",Toast.LENGTH_SHORT).show();
							return;
						}
						Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString());
						
						//2����RAW�����ģ�帴�ƽ��ļ���

						copyFile(Constans.mouldDataPATH+editmouldnamestr.toString(),"device");						
						copyFile(Constans.mouldDataPATH+editmouldnamestr.toString(),"userprg");						
						copyFile(Constans.mouldDataPATH+editmouldnamestr.toString(),"Userlog");
						
						HashMap<String, Object> mapadd = new HashMap<String, Object>();
						mapadd.put("num",	String.format("%1$04d", list_mould_setting.size()+1));//
						mapadd.put("num_mould_setting","");
						mapadd.put("name_mould_setting","");
						mapadd.put("note_mould_setting","");
						list_mould_setting.add(mapadd);
						
						int intAll=Fragments_mouldData.checkAllNum(list_mould_setting);
						final String date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
						list_mould_setting.get(intAll).put("num_mould_setting",String.format("%1$04d",Integer.parseInt(editmouldnumstr)));
						list_mould_setting.get(intAll).put("name_mould_setting", editmouldnamestr);
						list_mould_setting.get(intAll).put("note_mould_setting", date2);
						
						Fragments_mouldData.saveMouldDataList(list_mould_setting,date2,intAll);
						listselectposition=intAll;
						WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
						WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(), "��������", "��ȴ�", true, false); 
						
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_num", editmouldnumstr.toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_name", editmouldnamestr.toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", date2).commit();
						

						saveStrname=editmouldnamestr.toString().trim();
						saveposition=listselectposition;
						
						 new Thread()
			              {
			                  public void run()
			                  {
			                	//3����NC�����ģ������ �ص�PROGRESSDIALOG
			                	  	backupProgarm( saveStrname);			                	  
			                		if(WifiSetting_Info.progressDialog !=null)
			                		{
			                			isModify = false;
			                			WifiSetting_Info.progressDialog.dismiss();
			                			WifiSetting_Info.progressDialog = null;
			                			
			                		}
			                  }
			              }.start();
						
					}
				}).setNegativeButton("ȡ��",null).show();
			}
			
				break;
				case 2:  //���أ�����ʱ���ݱ����NC3
						copyDataToNc3();
				break;
				case 3: 				
						 //����ʧ�ܣ���NC3��ԭ��
						 restoreDataToNC3();					 
					break;
			}
			}
		};
		 NCedit_Adapter = new MyAdapter(getActivity(),
				NcEditList,// ����Դ
				R.layout.action_item,// ListItem��XMLʵ��
				// ��̬������ImageItem��Ӧ������
				 "noteEditText" ,
				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
				 R.id.noteEditText,myHandler);	
		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��
		 
		// ��Ӳ�����ʾ
		mListView.setAdapter(NCedit_Adapter);


		  mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//����Ϊ��ѡ
			// ��ӵ��
					mListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								final int position, long arg3) {
							try{
									myPos = position;
									if(NcEditList.size()==(position+1)){
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("addressText",	String.format("%04d", NcEditList.size()));//
										map.put("numSpinner","");
										map.put("orderSpinner","");
										map.put("operatText","");
										map.put("noteEditText","");
										NcEditList.add(map);
									}
									NCedit_Adapter.setSelectItem(position);
									NCedit_Adapter.notifyDataSetChanged();
									enableButton();
						}catch(Exception e){
							e.printStackTrace();
						}
						}
			});
	move = (Button) getActivity().findViewById(R.id.moveBtn);
	move.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("mpeng"," the my pos is "+myPos);
			if(myPos != -1)
			{
				NCedit_Adapter.moveoperate("MOVE", myPos);
				//mpeng
			}
		}
	});
	
	qdbtn = (Button) getActivity().findViewById(R.id.qdBtn);
	qdbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(myPos != -1)
			{NCedit_Adapter.zsoperate(myPos);}
		}
	});
	zjbtn = (Button) getActivity().findViewById(R.id.zjBtn);
	zjbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(myPos != -1)
			{NCedit_Adapter.zjoperate( myPos);}
		}
	});
	zsjxhbtn = (Button) getActivity().findViewById(R.id.zsjxhtBtn);
	zsjxhbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(myPos != -1)
			{NCedit_Adapter.zsjxhoperate( myPos);}
		}
	});
	jcbtn = (Button) getActivity().findViewById(R.id.jcBtn);
	jcbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(myPos != -1)
			{NCedit_Adapter.jcoperate( myPos);}
		}
	});
	delaytime = (Button) getActivity().findViewById(R.id.delaytimeBtn);
	delaytime.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(myPos != -1)
			{NCedit_Adapter.delaytimeoperate( myPos);}
		}
	});
	
	wait = (Button)getActivity().findViewById(R.id.waitBtn);
	wait.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(myPos != -1)
			{NCedit_Adapter.ifwaitoperate( myPos);}
		}
	});
	  insertBtn =  (Button)getActivity().findViewById(R.id.insertBtn);
	  delBtn = (Button)getActivity().findViewById(R.id.deleteBtn);
	  DownBtn = (Button)getActivity().findViewById(R.id.downLoadBtn);

		  backupBtn = (Button) getActivity().findViewById(R.id.backupBtn);
		  backupBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Message Msg = new Message();
				Msg.what = 1;
				myHandler.sendMessage(Msg);
			}
		});
	  insertBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			NCedit_Adapter.addOneItem(myPos);
			NCedit_Adapter.notifyDataSetChanged();			
		}
	});
	  delBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			NCedit_Adapter.removeItem(myPos);
			NCedit_Adapter.notifyDataSetChanged();
			
		}
	});
	  DownBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			final String date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
			.format(Calendar.getInstance().getTime());// ��ǰʱ��

		if (WifiSetting_Info.mClient == null) {
			if(ServerUtils.isUsingWifi(getActivity())){
				new AlertDialog.Builder(getActivity())
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
									getActivity().runOnUiThread(successRunnable);
								} catch (IOException e) {
									e.printStackTrace();
									getActivity().runOnUiThread(errorRunnable);
								}
							}
						}.start();
					}
				}).setNegativeButton("ȡ��",null).show();
			}else{
				Toast.makeText(getActivity(), "�����쳣��ͨѶ�жϣ��������磡", Toast.LENGTH_SHORT).show();
			}
			return;
		}
		
		//if(TR_Main_Activity.zd_led.getBackground().equals(TR_Main_Activity.drawablezdled)){
		if(Config.AutoState){
			new AlertDialog.Builder(getActivity())
			.setTitle("����")
			.setMessage("���������Զ�״̬���������أ����л����ֶ�״̬��")
			.setPositiveButton("֪����",null)
			.show();
			return;
	    }
		new AlertDialog.Builder(getActivity())
		.setTitle("��ʾ").setMessage("ȷ��Ҫ������")
		.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) {
					// �ȴ���ʾ
					WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
						WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(), "��д������","��ȴ�", true, false);

						 	Intent it = new Intent();
					        it.setAction("ThreadOption");
					        it.putExtra("Option", "StopThread");
					        getActivity().sendBroadcast(it);
					        
						 new Thread()
			              {
			                  public void run()
			                  {
						try {
							// ����֮ǰ��֪ͨHANDLERȥ����ʱ���ݱ����NC3��
							Message Msg = new Message();
//							Msg.what = 2;
//							myHandler.sendMessage(Msg);
							copyDataToNc3();
							
							alength=NCTranslate.beginTranslate(getActivity(),
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
							   
							   if(DownLoadSucess == false)
								 {
									 //����ʧ�ܣ���NC3��ԭ��
									
									Msg.what = 3;
									myHandler.sendMessage(Msg);
								 }
							   return;
				 		     }
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							overflag[3]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						//System.out.println("ab1234");
						// ��������ݸ�ʽ����г�ʼ��������᷵��һ����ָ��Ĵ���
						// ����һ���жϣ���ֹnc����Ϊ����������
						//if (NCTranslate.getMesHexCode().length != 0
								//&& !NCTranslate.getMesHexCode().equals(null)) {
							//System.out.println("nc="+NCTranslate.getMesHexCode().length);
				                	  sendDataRunnable = new SendDataRunnable(
									  new WifiSendDataFormat(
											NCTranslate.getMesHexCode(),
											AddressPublic.NCProg_Head),
											getActivity());
							          sendDataRunnables.add(sendDataRunnable);
							          
				                	  addmessage(date1);
				  					  fromsd();
							 
						/*}else {
							overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}*/
			                  }
			              }.start();
						WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {              
				            
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								Intent it = new Intent();
						        it.setAction("ThreadOption");
						        it.putExtra("Option", "StartThread");
						        getActivity().sendBroadcast(it);
						        
								 if(WifiSetting_Info.mClient==null){
									 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
									   .setMessage("�����쳣��ͨѶ�жϣ��������磡")
									   .setPositiveButton("ȷ��", null).show();
									 DownLoadSucess = false;
					                	//Toast.makeText(TR_Programming_Activity.this,"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
									}else if(overflag[0]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("���ݷ������")
										   .setPositiveButton("ȷ��", null).show();
										 DownLoadSucess = true;
										 overflag[0]=0;
									}else if(overflag[1]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage(NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸δѡ���趨")
										   .setPositiveButton("ȷ��", null).show();
										   NCTranslate.dname=null;
										   overflag[1]=0;
										   DownLoadSucess = false;
									}else if(overflag[2]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("û���ҵ�"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸")
										   .setPositiveButton("ȷ��", null).show();
										   NCTranslate.dname=null;
										   overflag[2]=0;
										   DownLoadSucess = false;
									}else if(overflag[6]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage(NCTranslate.ncindex+"�ı��"+NCTranslate.dname+"���ڴ���")
										   .setPositiveButton("ȷ��", null).show();
										 NCTranslate.dname=null;
										 overflag[6]=0;
										 DownLoadSucess = false;
									}else if(overflag[7]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage(NCTranslate.ncindex+"������"+NCTranslate.dname+"���ڴ���")
										   .setPositiveButton("ȷ��", null).show();
										 NCTranslate.dname=null;
										 overflag[7]=0;
										 DownLoadSucess = false;
									}else if(overflag[3]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("NC������ڴ���")
										   .setPositiveButton("ȷ��", null).show();
										 overflag[3]=0;
										 DownLoadSucess = false;
									}else if(overflag[4]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("NC����Ϊ�գ�����дNC����")
										   .setPositiveButton("ȷ��", null).show();
										 overflag[4]=0;
										 DownLoadSucess = false;
									}else if(overflag[5]==1){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("�����쳣�����ز�����")
										   .setPositiveButton("ȷ��", null).show();
										 overflag[5]=0;
										 DownLoadSucess = false;
										//Toast.makeText(TR_Programming_Activity.this,"�����쳣�����ز�����", Toast.LENGTH_SHORT).show();
									}
								 if(DownLoadSucess == false)
								 {
									 //����ʧ�ܣ���NC3��ԭ��
									    
										Message Msg = new Message();
										Msg.what = 3;
										myHandler.sendMessage(Msg);
								 }
								 else
								 {
									 isModify = false;
								 }
							}  
				        });
					 
			}
		}).setNegativeButton("ȡ��",null).show();
		
		}
	  });
		
	}catch(Exception e){
		e.printStackTrace();
	}
    	showNote();
	}

	
	public void initSpList_product(String operate){  
		 SpList_productshow.clear();
		
				 speed.setText("");
				 aspeed.setText("");
				 dspeed.setText("");
				
				 HashMap<String, Object> map0 = new HashMap<String, Object>();
					map0.put("name_sp_setting", "��ǰλ��(mm)");
					map0.put("line_value", "*****.*");
					map0.put("Bf_value", "*****.*");
					map0.put("ud_value", "*****.*");
					SpList_productshow.add(map0);
					
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("name_sp_setting", "��ʼλ��(mm)");
					map1.put("line_value","");
					map1.put("Bf_value", "");
					map1.put("ud_value", "");
					SpList_productshow.add(map1);
					
					HashMap<String, Object> map2 = new HashMap<String, Object>();
					map2.put("name_sp_setting", "װ����(mm)");
					map2.put("line_value", "");
					map2.put("Bf_value", "");
					map2.put("ud_value", "");
					SpList_productshow.add(map2);
					
					HashMap<String, Object> map3 = new HashMap<String, Object>();
					map3.put("name_sp_setting", "װ��˳��");
					map3.put("line_value","");
					map3.put("Bf_value", "");
					map3.put("ud_value", "");
					SpList_productshow.add(map3);
					
					HashMap<String, Object> map4 = new HashMap<String, Object>();
					map4.put("name_sp_setting", "װ���趨��");
					map4.put("line_value", "");
					map4.put("Bf_value", "");
					map4.put("ud_value", "");
					SpList_productshow.add(map4);
					
					HashMap<String, Object> map5 = new HashMap<String, Object>();
					map5.put("name_sp_setting", "��ǰװ����");
					map5.put("line_value", "");
					map5.put("Bf_value", "");
					map5.put("ud_value", "");
					SpList_productshow.add(map5);
					
					return;
		
		    
	    }

	public void initSpList_feedertrough(String operate){  
	    
    	SpList_feedertroughshow.clear();
		
				 speed.setText("");
					aspeed.setText("");
					dspeed.setText("");
				
					
				 HashMap<String, Object> map0 = new HashMap<String, Object>();
					map0.put("name_sp_setting", "��ǰλ��(mm)");
					map0.put("line_value", "*****.*");
					map0.put("Bf_value", "*****.*");
					map0.put("ud_value", "*****.*");
					SpList_feedertroughshow.add(map0);
					
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("name_sp_setting", "��ʼλ��(mm)");
					map1.put("line_value", "");
					map1.put("Bf_value", "");
					map1.put("ud_value", "");
					SpList_feedertroughshow.add(map1);
					
					HashMap<String, Object> map2 = new HashMap<String, Object>();
					map2.put("name_sp_setting", "װ����(mm)");
					map2.put("line_value", "");
					map2.put("Bf_value", "");
					map2.put("ud_value", "");
					SpList_feedertroughshow.add(map2);
					
					HashMap<String, Object> map3 = new HashMap<String, Object>();
					map3.put("name_sp_setting", "װ��˳��");
					map3.put("line_value", "");
					map3.put("Bf_value", "");
					map3.put("ud_value", "");
					SpList_feedertroughshow.add(map3);
					
					HashMap<String, Object> map4 = new HashMap<String, Object>();
					map4.put("name_sp_setting", "װ���趨��");
					map4.put("line_value", "");
					map4.put("Bf_value", "");
					map4.put("ud_value", "");
					SpList_feedertroughshow.add(map4);
					
					HashMap<String, Object> map5 = new HashMap<String, Object>();
					map5.put("name_sp_setting", "��ǰװ����");
					map5.put("line_value", "");
					map5.put("Bf_value", "");
					map5.put("ud_value", "");
					SpList_feedertroughshow.add(map5);
					
					return;
	
		
    }
	public void initFpList_product(String operate,boolean sta){
    	FpList_productshow.clear();
    	if(operate.equals("FP001"))
    	{

			 speed.setText("");
			 aspeed.setText("");
			 dspeed.setText(""); 
			 if(!sta)
			    FpNumEdit.setText(""); 
			 String Str =FpNumEdit.getText().toString();
			
				int num = 0;
				if(Str.equals("")){
					num = 0;
				}else{
				    num = Integer.valueOf(Str).intValue(); 
				}
			 HashMap<String, Object> map0 = new HashMap<String, Object>();
				map0.put("name_sp_setting", "��ǰλ��(mm)");
				map0.put("line_value", "*****.*");
				map0.put("Bf_value", "*****.*");
				map0.put("ud_value", "*****.*");
				FpList_productshow.add(map0);
				
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("name_sp_setting", "У��(mm)");
					map1.put("line_value","");
					map1.put("Bf_value", "");
					map1.put("ud_value", "");
					FpList_productshow.add( map1);
					
					for(int j = 0;j<num;j++)
					{
						HashMap<String, Object> map2 = new HashMap<String, Object>();
						map2.put("name_sp_setting", ""+(j+1));
						map2.put("line_value", "");
						map2.put("Bf_value", "");
						map2.put("ud_value","");
						FpList_productshow.add(map2);
					}	
				
				
    	}
    	else if(operate.equals("FP002"))
    	{

			 speed.setText("");
			 aspeed.setText("");
			 dspeed.setText(""); 
			 if(!sta)
			    FpNumEdit.setText(""); 
			 String Str =FpNumEdit.getText().toString();
			
				int num = 0;
				if(Str.equals("")){
					num = 0;
				}else{
				    num = Integer.valueOf(Str).intValue(); 
				}
			 HashMap<String, Object> map0 = new HashMap<String, Object>();
				map0.put("name_sp_setting", "��ǰλ��(mm)");
				map0.put("line_value", "*****.*");
				map0.put("Bf_value", "*****.*");
				map0.put("ud_value", "*****.*");
				FpList_productshow.add(map0);
					
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("name_sp_setting", "У��(mm)");
					map1.put("line_value", "");
					map1.put("Bf_value","");
					map1.put("ud_value", "");
					FpList_productshow.add( map1);
					
					for(int j = 0;j<num;j++)
					{
						HashMap<String, Object> map2 = new HashMap<String, Object>();
						map2.put("name_sp_setting", ""+(j+1));
						map2.put("line_value", "");
						map2.put("Bf_value", "");
						map2.put("ud_value",  "");
						FpList_productshow.add(map2);
					}	
				
				
    	}

		
		
    }
    
    public void initFpList_feedertrough(String operate ,boolean sta){
	    FpList_feedertroughshow.clear();
	    if(operate.equals("FP001"))
    	{

			 speed.setText("");
			 aspeed.setText("");
			 dspeed.setText(""); 
			 if(!sta)
			    FpNumEdit.setText(""); 
			 String Str =FpNumEdit.getText().toString();
			
				int num = 0;
				if(Str.equals("")){
					num = 0;
				}else{
				    num = Integer.valueOf(Str).intValue(); 
				}
			 HashMap<String, Object> map0 = new HashMap<String, Object>();
				map0.put("name_sp_setting", "��ǰλ��(mm)");
				map0.put("line_value", "*****.*");
				map0.put("Bf_value", "*****.*");
				map0.put("ud_value", "*****.*");
				FpList_feedertroughshow.add(map0);
				
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("name_sp_setting", "У��(mm)");
					map1.put("line_value","");
					map1.put("Bf_value", "");
					map1.put("ud_value", "");
					FpList_feedertroughshow.add( map1);
					
					for(int j = 0;j<num;j++)
					{
						HashMap<String, Object> map2 = new HashMap<String, Object>();
						map2.put("name_sp_setting", ""+(j+1));
						map2.put("line_value", "");
						map2.put("Bf_value", "");
						map2.put("ud_value","");
						FpList_feedertroughshow.add(map2);
					}	
				
				
    	}
    	else if(operate.equals("FP002"))
    	{

			 speed.setText("");
			 aspeed.setText("");
			 dspeed.setText(""); 
			 if(!sta)
			    FpNumEdit.setText(""); 
			 String Str =FpNumEdit.getText().toString();
			
				int num = 0;
				if(Str.equals("")){
					num = 0;
				}else{
				    num = Integer.valueOf(Str).intValue(); 
				}
			 HashMap<String, Object> map0 = new HashMap<String, Object>();
				map0.put("name_sp_setting", "��ǰλ��(mm)");
				map0.put("line_value", "*****.*");
				map0.put("Bf_value", "*****.*");
				map0.put("ud_value", "*****.*");
				FpList_feedertroughshow.add(map0);
					
					HashMap<String, Object> map1 = new HashMap<String, Object>();
					map1.put("name_sp_setting", "У��(mm)");
					map1.put("line_value", "");
					map1.put("Bf_value","");
					map1.put("ud_value", "");
					FpList_feedertroughshow.add( map1);
					
					for(int j = 0;j<num;j++)
					{
						HashMap<String, Object> map2 = new HashMap<String, Object>();
						map2.put("name_sp_setting", ""+(j+1));
						map2.put("line_value", "");
						map2.put("Bf_value", "");
						map2.put("ud_value",  "");
						FpList_feedertroughshow.add(map2);
					}	
				
				
    	}
    }

	// ������Ҫ��listview�����ÿһ�и����ؼ�������Ӧ���������ʹ���Զ����listview�Ľӿ�
	// �ڲ���
	public class MyAdapter extends BaseAdapter {
		private class buttonViewHolder {
			TextView addressText;
			TextView noteEditText;

		}

		private ArrayList<HashMap<String, Object>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String keyString;
		private buttonViewHolder holder;
		private int mselectItem= -1;
		private CheckBox checkboxx;
    	private CheckBox checkboxy;
    	private CheckBox checkboxh;
    	private CheckBox checkboxz;
    	private CheckBox checkboxl;
		
		private String opTxt = "";
		// MyAdapter�Ĺ��캯��
		public MyAdapter(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String from, int to,Handler hd) {
			mAppList = appList;
			mContext = c;
			mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			keyString = from; //
     		new Message();
			
			/* this.setListselected(new ArrayList<Boolean>(getCount()));
		        for(int i=0;i<getCount();i++)
		        	getListselected().add(false);//��ʼΪfalse�����Ⱥ�listviewһ��
*/
		}


		public String getkeyString() {
		    return this.keyString;
		}

		@Override
		public int getCount() {
			return mAppList.size();
		}

		@Override
		public Object getItem(int position) {
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		public void setSelectItem(int selectItem) {
			mselectItem = selectItem;
		}
		// ɾ��ĳһ��
		public void removeItem(int position) {
			mAppList.remove(position);
			if(mAppList.size()<position+1){
				myPos--;
				NCedit_Adapter.setSelectItem(myPos);
			}			
			this.notifyDataSetChanged();
		}
		/* public void modifyStates(int position){ 
			 if(getListselected().size()-1<position){
				 for(int i=0;i<position-(getListselected().size()-1);i++){
				   getListselected().add(false);
				 }
			 }else{
	            if(getListselected().get(position)==false){
	                 getListselected().set(position, true);//�����Ӧposition�ļ�¼��δ��ѡ��������Ϊѡ�У�true��
	                 //notifyDataSetChanged();
	            }else{
	                getListselected().set(position, false);//������Ӧposition�ļ�¼�Ǳ�ѡ��������Ϊδѡ�У�false��
	                 //notifyDataSetChanged();
	                }
			 }
	      }*/

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (buttonViewHolder) convertView.getTag();
				
			} else {
//				convertView = mInflater.inflate(R.layout.nc_edit_item,null);
				convertView= mInflater.inflate(R.layout.action_item,null);
				holder = new buttonViewHolder();
				holder.addressText = (TextView) convertView.findViewById(R.id.LineText);
				holder.noteEditText = (TextView) convertView
						.findViewById(R.id.praEditText);

				convertView.setTag(holder);
			}
			temphHashTable.put(position, convertView);

			HashMap<String, Object> appInfo = mAppList.get(position);
			if (appInfo != null) {

				String noteEditText = (String) appInfo.get(keyString);
				String numText = appInfo.get("addressText").toString();
				holder.addressText.setText(String.valueOf(position));
				holder.noteEditText.setText(noteEditText);
				// ѡ�к�ɫ��ʾ
				Log.d("mpeng"," get view "+position);
				/*if(getListselected().size()-1<position){
					 for(int i=0;i<position-(getListselected().size()-1);i++){
					   getListselected().add(false);
					 }
				 }else{
					
		        if(getListselected().get(position)==false){//���δ��ѡ�У�����Ϊ��ɫ
	
			    	    holder.noteEditText.setTextColor(Color.BLACK);
			    	    convertView.setBackgroundColor(Color.BLACK);
	                }else{//�����ѡ�У�����Ϊ��ɫ

	                	holder.noteEditText.setTextColor(Color.RED);
	                	convertView.setBackgroundColor(Color.RED);
			    	    }
				 }*/
				//holder.noteEditText.setOnClickListener(new notelistener(position));
//				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
//					holder.orderSpinner.setOnClickListener(new orderListener(position));
//					holder.numSpinner.setOnClickListener(new numlistener(position));
//					holder.noteEditText.setOnClickListener(new notelistener(position));
//				}else {
//					holder.orderSpinner.setOnClickListener(new lockListener(getActivity()));
//					holder.numSpinner.setOnClickListener(new lockListener(getActivity()));
//					holder.noteEditText.setOnClickListener(new lockListener(getActivity()));
//				}
			}
			
			if (position == mselectItem) {
				holder.noteEditText.setTextColor(Color.RED);
	    	    convertView.setBackgroundColor(Color.RED);
            }else{//�����ѡ�У�����Ϊ��ɫ

            	holder.noteEditText.setTextColor(Color.BLACK);
            	convertView.setBackgroundColor(Color.BLACK);
			}
			return convertView;
		}

	
		
		
//LIST���� ȡֻȡNOTEEDITTEXT����
		public  void addOneItem(int position) {
			HashMap<String, Object> mapadd = new HashMap<String, Object>();
			mapadd.put("addressText",	String.format("%04d", mAppList.size()));//
			mapadd.put("numSpinner","");
			mapadd.put("orderSpinner","");
			mapadd.put("operatText","");
			mapadd.put("noteEditText","");
			mAppList.add(position,mapadd);
			
		}
	    private class BtnOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
	        @Override  
	        public void onCheckedChanged(CompoundButton button, boolean isChecked){  
	            if(isChecked){  
	            	spProductBFTxt.setText("�ϵ�ǰ��(Ys)");
	            	spProductUDTxt.setText("�ϵ�����(Zs)");
	            	if(curPosition.getSelectedItem().toString().startsWith("SP")){
	            		//�ϵ���װ��        
    		        initSpList_feedertrough(curPosition.getSelectedItem().toString());	 
    		        SpAdapter myadapter =new SpAdapter(getActivity(), FSpSwitchBtn, SpList_feedertroughshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
    		        fSpListView.setAdapter(myadapter); 
	            	}else if(curPosition.getSelectedItem().toString().startsWith("FP")){
	            		//�ϵ���װ��        
    		        initFpList_feedertrough(curPosition.getSelectedItem().toString(),false);	 
    		        FpAdapter myadapter =new FpAdapter(getActivity(), FSpSwitchBtn, FpList_feedertroughshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
    		        fSpListView.setAdapter(myadapter); 
	            	}
	            }  
	            else  {
	            	spProductBFTxt.setText("��Ʒǰ��(Y)");
	            	spProductUDTxt.setText("��Ʒ����(Z)");
	            	if(curPosition.getSelectedItem().toString().startsWith("SP")){
	            		//��Ʒ��װ��	 
           		    initSpList_product(curPosition.getSelectedItem().toString().toString());	 
    		        SpAdapter myadapter =new SpAdapter(getActivity(), FSpSwitchBtn, SpList_productshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
    		        fSpListView.setAdapter(myadapter);
	            	}else if(curPosition.getSelectedItem().toString().toString().startsWith("FP")){
	            		//��Ʒ��װ��	 
	           		    initFpList_product(curPosition.getSelectedItem().toString(),false);	 
	    		        FpAdapter myadapter =new FpAdapter(getActivity(), FSpSwitchBtn, FpList_productshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
	    		        fSpListView.setAdapter(myadapter);
		            }
	            }
	     
	        }  
	    }  
	    
	    private class CheckBoxOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
	        @Override  
	        public void onCheckedChanged(CompoundButton button, boolean isChecked){  
	            if(isChecked){  
	            	if(button.getId()== R.id.checkBox1){
	            		xbox.setChecked(true);
	            		xEdTxt.setEnabled(true);
	            	}
	            	else if(button.getId()== R.id.checkBox2){
	            		ybox.setChecked(true);
	            		yEdTxt.setEnabled(true);
	            	}	            		
	            	else if(button.getId()== R.id.checkBox3){
	            		zbox.setChecked(true);
	            		zEdTxt.setEnabled(true);
	            	}
	            	else if(button.getId()== R.id.checkBox4){
	            		hbox.setChecked(true);
	            		hEdTxt.setEnabled(true);
	            	}else if(button.getId()== R.id.checkBox5){
	            		lbox.setChecked(true);
	            		lEdTxt.setEnabled(true);
	            	}
	            					
	            		
	            }  
	            else  {	           
	            	if(button.getId()== R.id.checkBox1){
	            		xbox.setChecked(false);
	            		xEdTxt.setEnabled(false);
	            	}
	            	else if(button.getId()== R.id.checkBox2){
	            		ybox.setChecked(false);
	            		yEdTxt.setEnabled(false);
	            	}	            		
	            	else if(button.getId()== R.id.checkBox3){
	            		zbox.setChecked(false);
	            		zEdTxt.setEnabled(false);
	            	}
	            	else if(button.getId()== R.id.checkBox4){
	            		hbox.setChecked(false);
	            		hEdTxt.setEnabled(false);
	            	}else if(button.getId()== R.id.checkBox5){
	            		lbox.setChecked(false);
	            		lEdTxt.setEnabled(false);
	            }
	     
	        }  
	    }  
	    }
	    private class zsCheckBoxOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
	        @Override  
	        public void onCheckedChanged(CompoundButton button, boolean isChecked){  
	            if(isChecked){  
	            	if(button.getId()== R.id.checkBox1){
	            		zscheckbox1.setChecked(true);
	            		zscheckbox2.setChecked(false);
	            	}else if(button.getId()== R.id.checkBox2){
	            		zscheckbox1.setChecked(false);
	            		zscheckbox2.setChecked(true);
	            	}	
	            			
	            }else{	           
	            	if(button.getId()== R.id.checkBox1){
	            		zscheckbox1.setChecked(false);
	            		zscheckbox2.setChecked(true);
	            	}
	            	else if(button.getId()== R.id.checkBox2){
	            		zscheckbox1.setChecked(true);
	            		zscheckbox2.setChecked(false);
	            	}	
	            }  
	        }  
	    }
	    private class zjCheckBoxOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
	        @Override  
	        public void onCheckedChanged(CompoundButton button, boolean isChecked){  
	            if(isChecked){  
	            	if(button.getId()== R.id.checkBox1){
	            		radioOFF_zj1.setChecked(true);
	            		radioON_zj1.setChecked(true);
	            		//radioGroup1.setClickable(true);
	            	}
	            	else if(button.getId()== R.id.checkBox2){
	            		radioOFF_zj2.setChecked(true);
	            		radioON_zj2.setChecked(true);
	            		//radioGroup2.setClickable(true);
	            	}	            		
	            	else if(button.getId()== R.id.checkBox3){
	            		radioOFF_zj3.setChecked(true);
	            		radioON_zj3.setChecked(true);
	            		//radioGroup3.setClickable(true);
	            	}
	            	else if(button.getId()== R.id.checkBox4){
	            		radioOFF_zj4.setChecked(true);
	            		radioON_zj4.setChecked(true);
	            		//radioGroup4.setClickable(true);
	            	}else if(button.getId()== R.id.checkBox5){
	            		radioOFF_zj5.setChecked(true);
	            		radioON_zj5.setChecked(true);
	            		//radioGroup5.setClickable(true);
	            	}else if(button.getId()== R.id.checkBox6){
	            		radioOFF_zj6.setChecked(true);
	            		radioON_zj6.setChecked(true);
	            		//radioGroup1.setClickable(true);
	            	}
	            	else if(button.getId()== R.id.checkBox7){
	            		radioOFF_zj7.setChecked(true);
	            		radioON_zj7.setChecked(true);
	            		//radioGroup2.setClickable(true);
	            	}	            		
	            	else if(button.getId()== R.id.checkBox8){
	            		radioOFF_zj8.setChecked(true);
	            		radioON_zj8.setChecked(true);
	            		//radioGroup3.setClickable(true);
	            	}
	            	else if(button.getId()== R.id.checkBox9){
	            		radioOFF_zj9.setChecked(true);
	            		radioON_zj9.setChecked(true);
	            		//radioGroup4.setClickable(true);
	            	}else if(button.getId()== R.id.checkBox10){
	            		radioOFF_zj10.setChecked(true);
	            		radioON_zj10.setChecked(true);
	            		//radioGroup5.setClickable(true);
	            	}
	            					
	            		
	            }  
	            else  {	           
	            	if(button.getId()== R.id.checkBox1){
	            		radioON_zj1.setChecked(false);
	            		radioOFF_zj1.setChecked(false);
	            		//radioGroup1.setClickable(false);
	            	}
	            	else if(button.getId()== R.id.checkBox2){
	            		radioON_zj2.setChecked(false);
	            		radioOFF_zj2.setChecked(false);
	            		//radioGroup2.setClickable(false);
	            	}	            		
	            	else if(button.getId()== R.id.checkBox3){
	            		radioON_zj3.setChecked(false);
	            		radioOFF_zj3.setChecked(false);
	            		//radioGroup3.setClickable(false);
	            	}
	            	else if(button.getId()== R.id.checkBox4){
	            		radioON_zj4.setChecked(false);
	            		radioOFF_zj4.setChecked(false);
	            		//radioGroup4.setClickable(false);
	            	}else if(button.getId()== R.id.checkBox5){
	            		radioON_zj5.setChecked(false);
	            		radioOFF_zj5.setChecked(false);
	            		//radioGroup5.setClickable(false);
	                }else if(button.getId()== R.id.checkBox6){
	            		radioON_zj6.setChecked(false);
	            		radioOFF_zj6.setChecked(false);
	            		//radioGroup1.setClickable(false);
	            	}
	            	else if(button.getId()== R.id.checkBox7){
	            		radioON_zj7.setChecked(false);
	            		radioOFF_zj7.setChecked(false);
	            		//radioGroup2.setClickable(false);
	            	}	            		
	            	else if(button.getId()== R.id.checkBox8){
	            		radioON_zj8.setChecked(false);
	            		radioOFF_zj8.setChecked(false);
	            		//radioGroup3.setClickable(false);
	            	}
	            	else if(button.getId()== R.id.checkBox9){
	            		radioON_zj9.setChecked(false);
	            		radioOFF_zj9.setChecked(false);
	            		//radioGroup4.setClickable(false);
	            	}else if(button.getId()== R.id.checkBox10){
	            		radioON_zj10.setChecked(false);
	            		radioOFF_zj10.setChecked(false);
	            		//radioGroup5.setClickable(false);
	                }
	     
	        }  
	    }  
	    }
	    
	    private void curPositionSpinnerSwitch()
	    {
	    	//1223
        	if(MoveSeclectSpinner.getSelectedItemPosition() == 1)
        	{
        		 
        		//ArrayList<String> list2 = Config.list_spname;
    			if(!StandPackList.isEmpty())
    				StandPackList.clear();
    			for(int i =1;i<=8;i++)
    			{       				
    				//String map = list2.get(i).toString();
    				StandPackList.add("SP00"+i);
    				
    			}
    		
    			PosSelAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, StandPackList);  
       	        PosSelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
       	        curPosition.setAdapter(PosSelAdapter);  
       	        PosSelAdapter.notifyDataSetChanged();      	        
       	    
       	    
        	}else if(MoveSeclectSpinner.getSelectedItemPosition() == 2)
        	{       
        		Log.d("mpeng"," init move select Fp");
    			if(!FreeList.isEmpty())
    				FreeList.clear();
    	
    			{       				
    				String map = "FP001";
    				FreeList.add(map);
    				String map1 = "FP002";
    				FreeList.add(map1);
    			}
    		
    			PosSelAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, FreeList);  
       	        PosSelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
       	        curPosition.setAdapter(PosSelAdapter); 
       	        PosSelAdapter.notifyDataSetChanged();
        	}else {
        		Log.d("mpeng"," init move select p");
        		ArrayList<String>	list1 = Config.list_pname;
        		int maxmap=0;
        		int mapint=0;
        			if(!PositionList.isEmpty())
        				PositionList.clear();
        			for(int i =0;i<list1.size();i++)
        			{   
        				String map = list1.get(i).toString().trim();
        				mapint=Integer.parseInt(map.substring(1, 4));
        				maxmap=maxmap>mapint?maxmap:mapint;
        			}
        			for(int i=1;i<=maxmap;i++){
        				PositionList.add("P"+String.format("%03d", i));
        			}
        			PosSelAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, PositionList);  
           	        PosSelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
           	        curPosition.setAdapter(PosSelAdapter);    
           	     
           	     if((!opTxt.equals(""))&&(opTxt.indexOf("P")!=-1))
    	    			{	
    	    				PosNumStr = opTxt.substring(opTxt.indexOf("P"), opTxt.indexOf("P")+4);
    	    				for(int pos =0;pos<PositionList.size();pos++){

    		    				if(PositionList.get(pos).toString().contains(PosNumStr))
    		    				{    		    				
    		    					curPosition.setSelection(pos);
    		    					break;
    		    				}			    			
    	    				}
    	    			
    	    			}
        		}
	    }
	    public void moveoperate(String oper,int position){
			final int qxposition=position;
			//������޸�λ�û�������ӣ��޸�λ����ҪLOADλ��ֵ		
			
			final View orderView=View.inflate(getActivity(), R.layout.new_order_move,null );//��moveһ��
			
			PositionLayout = (LinearLayout) orderView.findViewById(R.id.PositionLayout);
			PositionAddBtn = (Button) orderView.findViewById(R.id.PositionAdd);
			fspLayout = (LinearLayout) orderView.findViewById(R.id.FSPLayout);
			curPosition =  (Spinner) orderView.findViewById(R.id.CerrectInfo);//@+id/CerrectInfo
			FSpSwitchBtn = (ToggleButton) orderView.findViewById(R.id.sfp_switch);
			FSpSwitchBtn.setOnCheckedChangeListener(new BtnOnCheckedChangeListener());
			spProductBFTxt= (TextView) orderView.findViewById(R.id.productBA_sfp_setting);
        	spProductUDTxt= (TextView) orderView.findViewById(R.id.productUD_sfp_setting);
        	
	    	 checkboxx=(CheckBox)orderView.findViewById(R.id.checkBox1);
	    	 checkboxy=(CheckBox)orderView.findViewById(R.id.checkBox2);
	    	 checkboxh=(CheckBox)orderView.findViewById(R.id.checkBox4);
	    	 checkboxz=(CheckBox)orderView.findViewById(R.id.checkBox3);
	    	 checkboxl=(CheckBox)orderView.findViewById(R.id.checkBox5);
	    	//CheckBoxOnCheckedChangeListener
	    	checkboxx.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
	    	checkboxy.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
	    	checkboxh.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
	    	checkboxz.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
	    	checkboxl.setOnCheckedChangeListener(new CheckBoxOnCheckedChangeListener());
	    	
	    	speed=(EditText)orderView.findViewById(R.id.speed);
	  	    aspeed=(EditText)orderView.findViewById(R.id.aspeed);
	  	    dspeed=(EditText)orderView.findViewById(R.id.dspeed);
	  	  
        	  xbox = (CheckBox) orderView.findViewById(R.id.xCheckBox);
        	  ybox = (CheckBox) orderView.findViewById(R.id.yCheckBox);
        	  hbox = (CheckBox) orderView.findViewById(R.id.hCheckBox);
        	  zbox = (CheckBox) orderView.findViewById(R.id.zCheckBox);
        	  lbox = (CheckBox) orderView.findViewById(R.id.lCheckBox3);
        	  xbox.setClickable(false);
        	  ybox.setClickable(false);
        	  hbox.setClickable(false);
        	  zbox.setClickable(false);
        	  lbox.setClickable(false);
        	
        	  xTxt = (TextView) orderView.findViewById(R.id.xValue);
        	  yTxt = (TextView) orderView.findViewById(R.id.yValue);
        	  hTxt = (TextView) orderView.findViewById(R.id.hValue);
        	  zTxt = (TextView) orderView.findViewById(R.id.Zvalue);
        	  lTxt = (TextView) orderView.findViewById(R.id.lValue);
        	  
			  xCurTxt= (TextView) orderView.findViewById(R.id.xValue);
			  yCurTxt= (TextView) orderView.findViewById(R.id.yValue);
			  hCurTxt= (TextView) orderView.findViewById(R.id.hvalue);
			  zCurTxt= (TextView) orderView.findViewById(R.id.Zvalue);
			  lCurTxt= (TextView) orderView.findViewById(R.id.lValue);
        	
        	  xEdTxt =  (EditText) orderView.findViewById(R.id.xEditText);
        	  yEdTxt =  (EditText) orderView.findViewById(R.id.yEditText);
        	  hEdTxt =  (EditText) orderView.findViewById(R.id.hEditText);
        	  zEdTxt =  (EditText) orderView.findViewById(R.id.zEditText);
        	  lEdTxt =  (EditText) orderView.findViewById(R.id.lEditText);
        	  
        	  
        	  if(Config.ArmNum==3)//add by mpeng 20141225
        	  {
//        		  layoutArmH =orderView.findViewById(R.id.armHlayout);
//        		  layoutArmL = orderView.findViewById(R.id.armLlayout);
        		  checkboxh.setClickable(false);
        		  checkboxl.setClickable(false);
        		  checkboxh.setBackgroundColor(color.red);
        		  checkboxl.setBackgroundColor(color.red);
        	  }  
        	  /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
        	  CheckBox[] checkboxs={checkboxx,checkboxy,checkboxh,checkboxz,checkboxl};
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP")) {
  	    		if (operat_MOVE.contains("SP")) {
  					operat_MOVE = operat_MOVE.trim();
  					char[] chs=operat_MOVE.toCharArray();
  					char ch=' ';
  					for(int i=0;i<chs.length;i++){
  						if(chs[i]=='A'){
  							ch=chs[i+1];
  							int index=(int)ch-49;
  							checkboxs[index].setChecked(true);
  						}
  						if(chs[i]=='S'||chs[i]=='s'){
  							operat_MOVE=operat_MOVE.substring(i);
  							break;
  						}
  					}
  				}else if (operat_MOVE.contains("FP")) {
  					operat_MOVE = operat_MOVE.trim();
  					char[] chs=operat_MOVE.toCharArray();
  					char ch=' ';
  					for(int i=0;i<chs.length;i++){
  						if(chs[i]=='A'){
  							ch=chs[i+1];
  							int index=(int)ch-49;
  							checkboxs[index].setChecked(true);
  						}
  						if(chs[i]=='F'||chs[i]=='f'){
  							operat_MOVE=operat_MOVE.substring(i);
  							break;
  						}
  					}
  				}else if (operat_MOVE.contains("P")) {
  					operat_MOVE = operat_MOVE.trim();
  					char[] chs=operat_MOVE.toCharArray();
  					char ch=' ';
  					for(int i=0;i<chs.length;i++){
  						if(chs[i]=='A'){
  							ch=chs[i+1];
  							int index=(int)ch-49;
  							checkboxs[index].setChecked(true);
  						}
  						if(chs[i]=='P'||chs[i]=='p'){
  							operat_MOVE=operat_MOVE.substring(i);
  							break;
  						}
  					}
  				}										    													    		
  			}
        	  posRemBtn =  (Button) orderView.findViewById(R.id.positionMemryBtn);
        	  posRemBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(xbox.isChecked()){
						xEdTxt.setText(xCurTxt.getText().toString());
					}
					if(ybox.isChecked()){
						yEdTxt.setText(yCurTxt.getText().toString());
					}
					if(hbox.isChecked()){
						hEdTxt.setText(hCurTxt.getText().toString());
					}
					if(zbox.isChecked()){
						zEdTxt.setText(zCurTxt.getText().toString());
					}
					if(lbox.isChecked()){
						lEdTxt.setText(lCurTxt.getText().toString());
					}
						
					
				}
			});
        	 
        	  if(xbox.isChecked())
        		  xEdTxt.setEnabled(true);
        	  else
        		  xEdTxt.setEnabled(false);
        	  
        	  if(ybox.isChecked())
        		  yEdTxt.setEnabled(true);
        	  else
        		  yEdTxt.setEnabled(false);
        	  
        	  if(zbox.isChecked())
        		  zEdTxt.setEnabled(true);
        	  else
        		  zEdTxt.setEnabled(false);
        	  
        	  if(hbox.isChecked())
        		  hEdTxt.setEnabled(true);
        	  else
        		  hEdTxt.setEnabled(false);
        	  
        	  if(lbox.isChecked())
        		  lEdTxt.setEnabled(true);
        	  else
        		  lEdTxt.setEnabled(false);
        	  
        	
   	            
        	  
			MoveSeclectSpinner = (Spinner) orderView.findViewById(R.id.SelectSpinner);
		    List<String> list = new ArrayList<String>();   
	        list.add("��׼λ��");
	        list.add("��׼װ��");
	        list.add("����װ��");
	        FpNumText = (TextView) orderView.findViewById(R.id.FpNumTxt);
	        FpNumEdit = (EditText) orderView.findViewById(R.id.FpNumEdt);	        
	        FpNumEdit.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					Log.d("mpeng","the key actionId is "+actionId);
					 {

						 InputMethodManager m=(InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
						 m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
						  
						 if(FSpSwitchBtn.isChecked())
		         	        {
		         	        	initFpList_feedertrough(curPosition.getSelectedItem().toString().toString(),true);	 
		         		        FpAdapter myadapter =new FpAdapter(getActivity(), FSpSwitchBtn, FpList_feedertroughshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
		         		        fSpListView.setAdapter(myadapter);
		         	        }else
		         	        {
		         	        	initFpList_product(curPosition.getSelectedItem().toString().toString(),true);	 
		         		        FpAdapter myadapter =new FpAdapter(getActivity(), FSpSwitchBtn, FpList_productshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
		         		        fSpListView.setAdapter(myadapter);
		         	        }
			                return true;
			            }
//					return false;
				}
			});
	    	HashMap<String, Object> item = (HashMap<String, Object>) Fragments_Action.mListView.getItemAtPosition(qxposition);
	    	String orderTxt=item.get("orderSpinner").toString();
			if(orderTxt.contains("MOVE")||orderTxt.contains("MOVEP")){
				opTxt = item.get("operatText").toString();	
			}
	        fSpListView = (ListView) orderView.findViewById(R.id.fpsp_listview);
	        ArrayAdapter<String> adapter  = null;
	        //�ڶ�����Ϊ�����б���һ����������������õ���ǰ�涨���list��  
	        adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);  
	        //��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ��  
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	        //���Ĳ�������������ӵ������б���  
	        MoveSeclectSpinner.setAdapter(adapter);  
	        //���岽��Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��  
	        MoveSeclectSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){  
	            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {  
	                // TODO Auto-generated method stub  
	                /* ��mySpinner ��ʾ*/  
	            
	            	int i = Config.list_pname.size()+1;
	            	PosNumStr = null ;       			
	                if(arg2 == 0)
	                {
	                	
	                	PositionAddBtn.setEnabled(true);
      
	                	//ƥ�䵽��Ӧ��λ��
	                	
	                    curPositionSpinnerSwitch();
	                	PositionLayout.setVisibility(View.VISIBLE);
	                	fspLayout.setVisibility(View.GONE);
//	                	curPosition.setText("P"+PosNumStr);
	                	FpNumText.setVisibility(View.GONE);
	        	        FpNumEdit.setVisibility(View.GONE); 
	        	        
	        	        for(int i1=0;i1<Config.list_pname.size();i1++){
	        	        	if(Config.list_pname.get(i1).toString().equals("P"+PosNumStr)){
	        	        		String myXvalue = "";
	        	        		String myYvalue = "";
	        	        		String myZvalue = "";
	        	        		String myHvalue = "";
	        	        		String myLvalue = "";
	        	        		if(!myXvalue.equals(""))
	        	        		{
	        	        			checkboxx.setChecked(true);
	        	        		}
	        	        		if(!myYvalue.equals(""))
	        	        		{
	        	        			checkboxy.setChecked(true);
	        	        		}

	        	        		if(!myZvalue.equals(""))
	        	        		{
	        	        			checkboxz.setChecked(true);
	        	        		}

	        	        		if(!myHvalue.equals(""))
	        	        		{
	        	        			checkboxh.setChecked(true);
	        	        		}

	        	        		if(!myLvalue.equals(""))
	        	        		{
	        	        			checkboxl.setChecked(true);
	        	        		}


	        	        		xEdTxt.setText(myXvalue);
						        yEdTxt.setText(myYvalue);
						        zEdTxt.setText(myZvalue);
						        hEdTxt.setText(myHvalue);
						        lEdTxt.setText(myLvalue);
						        
						        speed.setText("");
						        aspeed.setText("");
						        dspeed.setText("");
						        break;
	        	        	}
	        	        	if(i1==Config.list_pname.size()-1){
	        	        		
						        xEdTxt.setText("");
						        yEdTxt.setText("");
						        zEdTxt.setText("");
						        hEdTxt.setText("");
						        lEdTxt.setText("");
						        speed.setText("10");
						        aspeed.setText("1");
						        dspeed.setText("1");
	        	        	}
	        	        }
	        	        
	        	        if(NewPragramActivity.PosccalmRunnable!=null){
	        	        	NewPragramActivity.PosccalmRunnable.destroy();
						}
	    			 if(NewPragramActivity.PosccalmRunnable.existFlag==false){
	    				 NewPragramActivity.PosccalmRunnable=new posccalmQueryRunnable(
	    							getActivity(),xCurTxt,yCurTxt,hCurTxt,zCurTxt,lCurTxt,null,null,null,null,null);
	    					Thread a1=new Thread(NewPragramActivity.PosccalmRunnable);
	    					a1.start();
	    				}
	                }
	                else if(arg2 ==1)
	                {
	                	
	                	PositionAddBtn.setEnabled(false);
	                	PositionLayout.setVisibility(View.GONE);
	                	fspLayout.setVisibility(View.VISIBLE);
//	                	InfoText.setText("SP"+ PosNumStr);
	                	FpNumText.setVisibility(View.GONE);
	        	        FpNumEdit.setVisibility(View.GONE); 
	        	        
	        	        curPositionSpinnerSwitch();
	                	if((!opTxt.equals(""))&&(opTxt.indexOf("SP")!=-1))
		    			{	
		    				for(int pos = 0;pos<8;pos++){
		    					if(StandPackList.get(pos).toString().indexOf(opTxt)==0){
		    						curPosition.setSelection(pos);
		    						break;
		    					}
		    				}
		    			
		    			}else
		    			{
		    			    
		    				curPosition.setSelection(0);
		    			}
	        	       
	        	        if(FSpSwitchBtn.isChecked())
	        	        {
	               		 //�ϵ���װ��        
	        		        initSpList_feedertrough(curPosition.getSelectedItem().toString());	 
	        		        SpAdapter myadapter =new SpAdapter(getActivity(), FSpSwitchBtn, SpList_feedertroughshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
	        		        fSpListView.setAdapter(myadapter);  
	        	        }else
	        	        {
	        	        	//��Ʒ��װ��	 
	        	        	Log.d("mpeng","curPosition.getSelectedItem().");
	        	        	curPosition.setSelection(0);
	               		    initSpList_product(curPosition.getSelectedItem().toString());	 
	        		        SpAdapter myadapter =new SpAdapter(getActivity(), FSpSwitchBtn, SpList_productshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
	        		        fSpListView.setAdapter(myadapter);
	        	        }
	                
	                }else if(arg2 == 2)
	                {
	                	//����װ��ֻ��FP001 FP002
	                	PositionAddBtn.setEnabled(false);
	                	if(NewPragramActivity.PosccalmRunnable!=null){
	                		NewPragramActivity.PosccalmRunnable.destroy();
						}
	                	PositionLayout.setVisibility(View.GONE);
	                	fspLayout.setVisibility(View.VISIBLE);
//	                	InfoText.setText("FP"+"001");
	                	FpNumText.setVisibility(View.VISIBLE);
	        	        FpNumEdit.setVisibility(View.VISIBLE); 
	        	        
	        	        curPositionSpinnerSwitch();
	                	if((!opTxt.equals(""))&&(opTxt.indexOf("FP001")!=-1))
		    			{	
	                		curPosition.setSelection(0);
		    			
		    			}
	                	else if((!opTxt.equals(""))&&(opTxt.indexOf("FP002")!=-1))
		    			{	
	                		curPosition.setSelection(1);
		    			
		    			}
	                		else
		    			{
		    			    
		    				curPosition.setSelection(0);
		    			}
	        	        if(FSpSwitchBtn.isChecked())
	         	        {
	         	        	initFpList_feedertrough(curPosition.getSelectedItem().toString(),false);	 
	         		        FpAdapter myadapter =new FpAdapter(getActivity(), FSpSwitchBtn, FpList_feedertroughshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
	         		        fSpListView.setAdapter(myadapter);
	         	        }else
	         	        {       	        	
	         	      
	         	        	initFpList_product(curPosition.getSelectedItem().toString(),false);	 
	         		        FpAdapter myadapter =new FpAdapter(getActivity(), FSpSwitchBtn, FpList_productshow, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fSpListView);
	         		        fSpListView.setAdapter(myadapter);
	         	        	
	         	        }
	                }
	        }
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
	        }); 
	        
	        //
	        PositionAddBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(MoveSeclectSpinner.getSelectedItemPosition()!=0)
						return;
					int i = PositionList.size()+1;
					String PosNumStr = null;
					if (i<10)
	            		PosNumStr = "P00"+i;
	            	else if(i<100)
	            		PosNumStr = "P0"+i;
	            	else 
	            		PosNumStr = "P"+i;
					PositionList.add(PosNumStr);
					PosSelAdapter.notifyDataSetChanged();
					curPosition.setSelection(PositionList.size()-1);					
				}
			});
	        
	        if (operat_MOVE.contains("SP")) {	    
	        	MoveSeclectSpinner.setSelection(1);
	        }
	        else if (operat_MOVE.contains("FP")) {

	        	MoveSeclectSpinner.setSelection(2);
	        }
	        else if (operat_MOVE.contains("P")) {

	        	MoveSeclectSpinner.setSelection(0);
	        }										    													    		
		
	        PositionList = new ArrayList<String>();   
	        StandPackList = new ArrayList<String>();   
	        FreeList = new ArrayList<String>();  	       
	        curPositionSpinnerSwitch();
   	        
        	 	   
   	        curPosition.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
//				checkboxy
				String selectItem = curPosition.getSelectedItem().toString();
				if(selectItem.contains("FP"))
				{
					Log.d("mpeng","fpppp "+selectItem);
				}
				else if(selectItem.contains("SP"))
				{
					Log.d("mpeng","spppp "+selectItem);
				}
				else if(selectItem.contains("P"))
				{
					Log.d("mpeng","pppp "+selectItem);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}  
   	        	
   	        });
	        if(!opTxt.equals(""))
			{
				if(opTxt.indexOf("SP")!=-1)
				{
					Log.d("mpeng"," ");
					MoveSeclectSpinner.setSelection(1,true);
				}
				else if(opTxt.indexOf("FP")!=-1)
				{				
					MoveSeclectSpinner.setSelection(2,true);
	
				}
				else if(opTxt.indexOf("P")!=-1)
				{	  
					 MoveSeclectSpinner.setSelection(0,true);
				}
				
			}
	        
	        Log.d("mpeng","NcEditList size "+NcEditList.size());
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("���ƶ�ָ��("+oper+")")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener(orderView,position,oper,NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
           
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*4/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
			 final InputMethodManager  Inputmanager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);  
				orderView.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						  if(event.getAction() == MotionEvent.ACTION_DOWN){  
							     if(orderView.getWindowToken()!=null){  
							    	 Inputmanager.hideSoftInputFromWindow(orderView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
							     } 							     
							  }  
						return false;
					}
				});
			//�����˵��Ĺر��¼�  
			 dialog2.setOnDismissListener(new OnDismissListener() {              
		            
					@Override
					public void onDismiss(DialogInterface arg0) {
						// TODO Auto-generated method stub
						if(NewPragramActivity.PosccalmRunnable!=null){
							NewPragramActivity.PosccalmRunnable.destroy();
						}
						 opTxt = "";
					}  
					
		        });

		
		}
	    public void zsoperate(int position){
			final int qxposition=position;
			View orderView=View.inflate(getActivity(), R.layout.new_order_zs,null );
			zscheckbox1=(CheckBox)orderView.findViewById(R.id.checkBox1);
	    	zscheckbox2=(CheckBox)orderView.findViewById(R.id.checkBox2);
	    	ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
	    	zscheckbox1.setText(outputList.get(32).get("signalNameEditText").toString().trim());
	    	zscheckbox2.setText(outputList.get(33).get("signalNameEditText").toString().trim());
	    	zscheckbox1.setOnCheckedChangeListener(new zsCheckBoxOnCheckedChangeListener());
	    	zscheckbox2.setOnCheckedChangeListener(new zsCheckBoxOnCheckedChangeListener());
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	  	    		if(address==32){
  	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	  	    			   zscheckbox1.setChecked(true);
  	  	    			}else{
  	  	    			   zscheckbox2.setChecked(true);
  	  	    			}
  	  	    		}
  	  	    	   if(address==33){
	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			   zscheckbox2.setChecked(true);
	  	    			}else{
	  	    			   zscheckbox1.setChecked(true);
	  	    			}
	  	    		}
  	  	    	}
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("���ƶ���")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"OUT",NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
            
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*2/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
	    	
		}
		public void zjoperate(int position){
			final int qxposition=position;
			View orderView=View.inflate(getActivity(), R.layout.new_order_zj,null );
			CheckBox zjcheckbox1=(CheckBox)orderView.findViewById(R.id.checkBox1);
			CheckBox zjcheckbox2=(CheckBox)orderView.findViewById(R.id.checkBox2);
			CheckBox zjcheckbox3=(CheckBox)orderView.findViewById(R.id.checkBox3);
			CheckBox zjcheckbox4=(CheckBox)orderView.findViewById(R.id.checkBox4);
			CheckBox zjcheckbox5=(CheckBox)orderView.findViewById(R.id.checkBox5);
			ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
			zjcheckbox1.setText(outputList.get(34).get("signalNameEditText").toString().trim());
			zjcheckbox2.setText(outputList.get(35).get("signalNameEditText").toString().trim());
			zjcheckbox3.setText(outputList.get(38).get("signalNameEditText").toString().trim());
			zjcheckbox4.setText(outputList.get(36).get("signalNameEditText").toString().trim());
			zjcheckbox5.setText(outputList.get(37).get("signalNameEditText").toString().trim());
			zjcheckbox1.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox2.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox3.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox4.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox5.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			
			radioON_zj1=(RadioButton) orderView.findViewById(R.id.radio_on1);
			radioOFF_zj1=(RadioButton) orderView.findViewById(R.id.radio_off1);
			radioON_zj2=(RadioButton) orderView.findViewById(R.id.radio_on2);
			radioOFF_zj2=(RadioButton) orderView.findViewById(R.id.radio_off2);
			radioON_zj3=(RadioButton) orderView.findViewById(R.id.radio_on3);
			radioOFF_zj3=(RadioButton) orderView.findViewById(R.id.radio_off3);
			radioON_zj4=(RadioButton) orderView.findViewById(R.id.radio_on4);
			radioOFF_zj4=(RadioButton) orderView.findViewById(R.id.radio_off4);
			radioON_zj5=(RadioButton) orderView.findViewById(R.id.radio_on5);
			radioOFF_zj5=(RadioButton) orderView.findViewById(R.id.radio_off5);
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	  	    		if(address==34){
  	  	    			zjcheckbox1.setChecked(true);
  	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	  	    			  radioON_zj1.setChecked(true);
  	  	    			}else{
  	  	    			  radioOFF_zj1.setChecked(true);
  	  	    			}
  	  	    		}
  	  	    		if(address==35){
  	  	    			zjcheckbox2.setChecked(true);
  	  	    		if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj2.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj2.setChecked(true);
	  	    			}
  	  	    		}
  	  	    	    if(address==38){
	  	    			zjcheckbox3.setChecked(true);
	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	  	    			  radioON_zj3.setChecked(true);
	  	  	    			}else{
	  	  	    			  radioOFF_zj3.setChecked(true);
	  	  	    			}
	  	    		}
  	  	            if(address==36){
	    			    zjcheckbox4.setChecked(true);
	    			    if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	  	    			  radioON_zj4.setChecked(true);
	  	  	    			}else{
	  	  	    			  radioOFF_zj4.setChecked(true);
	  	  	    			}
	    		    }
  	  	            if(address==37){
    			        zjcheckbox5.setChecked(true);
    			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
    	  	    			  radioON_zj5.setChecked(true);
    	  	    			}else{
    	  	    			  radioOFF_zj5.setChecked(true);
    	  	    			}
    		        }
  	    		}
  	    		
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("�ξ�")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"OUTP",NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*2/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
	    	
		}
		public void zsjxhoperate(int position){
			final int qxposition=position;
			View orderView=View.inflate(getActivity(), R.layout.new_order_zsjxh,null );
			CheckBox zjcheckbox1=(CheckBox)orderView.findViewById(R.id.checkBox1);
			CheckBox zjcheckbox2=(CheckBox)orderView.findViewById(R.id.checkBox2);
			CheckBox zjcheckbox3=(CheckBox)orderView.findViewById(R.id.checkBox3);
			CheckBox zjcheckbox4=(CheckBox)orderView.findViewById(R.id.checkBox4);
			ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
			zjcheckbox1.setText(outputList.get(16).get("signalNameEditText").toString().trim());
			zjcheckbox2.setText(outputList.get(17).get("signalNameEditText").toString().trim());
			zjcheckbox3.setText(outputList.get(18).get("signalNameEditText").toString().trim());
			zjcheckbox4.setText(outputList.get(37).get("signalNameEditText").toString().trim());
			zjcheckbox1.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox2.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox3.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			zjcheckbox4.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			
			radioON_zj1=(RadioButton) orderView.findViewById(R.id.radio_on1);
			radioOFF_zj1=(RadioButton) orderView.findViewById(R.id.radio_off1);
			radioON_zj2=(RadioButton) orderView.findViewById(R.id.radio_on2);
			radioOFF_zj2=(RadioButton) orderView.findViewById(R.id.radio_off2);
			radioON_zj3=(RadioButton) orderView.findViewById(R.id.radio_on3);
			radioOFF_zj3=(RadioButton) orderView.findViewById(R.id.radio_off3);
			radioON_zj4=(RadioButton) orderView.findViewById(R.id.radio_on4);
			radioOFF_zj4=(RadioButton) orderView.findViewById(R.id.radio_off4);
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	  	    		if(address==16){
  	  	    			zjcheckbox1.setChecked(true);
  	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	  	    			  radioON_zj1.setChecked(true);
  	  	    			}else{
  	  	    			  radioOFF_zj1.setChecked(true);
  	  	    			}
  	  	    		}
  	  	    		if(address==17){
  	  	    			zjcheckbox2.setChecked(true);
  	  	    		if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj2.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj2.setChecked(true);
	  	    			}
  	  	    		}
  	  	    	    if(address==18){
	  	    			zjcheckbox3.setChecked(true);
	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	  	    			  radioON_zj3.setChecked(true);
	  	  	    			}else{
	  	  	    			  radioOFF_zj3.setChecked(true);
	  	  	    			}
	  	    		}
  	  	            if(address==37){
	    			    zjcheckbox4.setChecked(true);
	    			    if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	  	    			  radioON_zj4.setChecked(true);
	  	  	    			}else{
	  	  	    			  radioOFF_zj4.setChecked(true);
	  	  	    			}
	    		    }
  	    		}
  	    		
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("ע�ܻ��ź�")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"PARALLEL",NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*2/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
		}
		public void jcoperate(int position){
			final int qxposition=position;
			View orderView=View.inflate(getActivity(), R.layout.new_order_jc,null );
			CheckBox zjcheckbox1=(CheckBox)orderView.findViewById(R.id.checkBox1);
			CheckBox zjcheckbox2=(CheckBox)orderView.findViewById(R.id.checkBox2);
			CheckBox zjcheckbox3=(CheckBox)orderView.findViewById(R.id.checkBox3);
			CheckBox zjcheckbox4=(CheckBox)orderView.findViewById(R.id.checkBox4);
			CheckBox zjcheckbox5=(CheckBox)orderView.findViewById(R.id.checkBox5);
			CheckBox zjcheckbox6=(CheckBox)orderView.findViewById(R.id.checkBox6);
			CheckBox zjcheckbox7=(CheckBox)orderView.findViewById(R.id.checkBox7);
			CheckBox zjcheckbox8=(CheckBox)orderView.findViewById(R.id.checkBox8);
			CheckBox zjcheckbox9=(CheckBox)orderView.findViewById(R.id.checkBox9);
			CheckBox zjcheckbox10=(CheckBox)orderView.findViewById(R.id.checkBox10);
			ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceMiddleOutputListData();
			LinearLayout linearLayout1 = (LinearLayout) orderView.findViewById(R.id.LinearLayout1);	
			LinearLayout linearLayout2 = (LinearLayout) orderView.findViewById(R.id.LinearLayout2);
			LinearLayout linearLayout3 = (LinearLayout) orderView.findViewById(R.id.LinearLayout3);
			LinearLayout linearLayout4 = (LinearLayout) orderView.findViewById(R.id.LinearLayout4);
			LinearLayout linearLayout5 = (LinearLayout) orderView.findViewById(R.id.LinearLayout5);
			LinearLayout linearLayout6 = (LinearLayout) orderView.findViewById(R.id.LinearLayout6);
			LinearLayout linearLayout7 = (LinearLayout) orderView.findViewById(R.id.LinearLayout7);
			LinearLayout linearLayout8 = (LinearLayout) orderView.findViewById(R.id.LinearLayout8);
			LinearLayout linearLayout9 = (LinearLayout) orderView.findViewById(R.id.LinearLayout9);
			LinearLayout linearLayout10 = (LinearLayout) orderView.findViewById(R.id.LinearLayout10);
			if(inputList.get(96).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout1.setVisibility(View.GONE);
			}else{
			    zjcheckbox1.setText(inputList.get(96).get("signalNameEditText").toString().trim());
			    zjcheckbox1.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(97).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout2.setVisibility(View.GONE);
			}else{
			    zjcheckbox2.setText(inputList.get(97).get("signalNameEditText").toString().trim());
			    zjcheckbox2.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(98).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout3.setVisibility(View.GONE);
			}else{
			    zjcheckbox3.setText(inputList.get(98).get("signalNameEditText").toString().trim());
			    zjcheckbox3.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(99).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout4.setVisibility(View.GONE);
			}else{
			    zjcheckbox4.setText(inputList.get(99).get("signalNameEditText").toString().trim());
			    zjcheckbox4.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(100).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout5.setVisibility(View.GONE);
			}else{
			    zjcheckbox5.setText(inputList.get(100).get("signalNameEditText").toString().trim());
			    zjcheckbox5.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(101).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout6.setVisibility(View.GONE);
			}else{
			    zjcheckbox6.setText(inputList.get(101).get("signalNameEditText").toString().trim());
			    zjcheckbox6.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(102).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout7.setVisibility(View.GONE);
			}else{
			    zjcheckbox7.setText(inputList.get(102).get("signalNameEditText").toString().trim());
			    zjcheckbox7.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(103).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout8.setVisibility(View.GONE);
			}else{
			    zjcheckbox8.setText(inputList.get(103).get("signalNameEditText").toString().trim());
			    zjcheckbox8.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(104).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout9.setVisibility(View.GONE);
			}else{
				zjcheckbox9.setText(inputList.get(104).get("signalNameEditText").toString().trim());
				zjcheckbox9.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			if(inputList.get(105).get("symbolNameEditText").toString().trim().equals("")){
				linearLayout10.setVisibility(View.GONE);
			}else{
				zjcheckbox10.setText(inputList.get(105).get("signalNameEditText").toString().trim());
				zjcheckbox10.setOnCheckedChangeListener(new zjCheckBoxOnCheckedChangeListener());
			}
			
			radioON_zj1=(RadioButton) orderView.findViewById(R.id.radio_on1);
			radioOFF_zj1=(RadioButton) orderView.findViewById(R.id.radio_off1);
			radioON_zj2=(RadioButton) orderView.findViewById(R.id.radio_on2);
			radioOFF_zj2=(RadioButton) orderView.findViewById(R.id.radio_off2);
			radioON_zj3=(RadioButton) orderView.findViewById(R.id.radio_on3);
			radioOFF_zj3=(RadioButton) orderView.findViewById(R.id.radio_off3);
			radioON_zj4=(RadioButton) orderView.findViewById(R.id.radio_on4);
			radioOFF_zj4=(RadioButton) orderView.findViewById(R.id.radio_off4);
			radioON_zj5=(RadioButton) orderView.findViewById(R.id.radio_on5);
			radioOFF_zj5=(RadioButton) orderView.findViewById(R.id.radio_off5);
			radioON_zj6=(RadioButton) orderView.findViewById(R.id.radio_on6);
			radioOFF_zj6=(RadioButton) orderView.findViewById(R.id.radio_off6);
			radioON_zj7=(RadioButton) orderView.findViewById(R.id.radio_on7);
			radioOFF_zj7=(RadioButton) orderView.findViewById(R.id.radio_off7);
			radioON_zj8=(RadioButton) orderView.findViewById(R.id.radio_on8);
			radioOFF_zj8=(RadioButton) orderView.findViewById(R.id.radio_off8);
			radioON_zj9=(RadioButton) orderView.findViewById(R.id.radio_on9);
			radioOFF_zj9=(RadioButton) orderView.findViewById(R.id.radio_off9);
			radioON_zj10=(RadioButton) orderView.findViewById(R.id.radio_on10);
			radioOFF_zj10=(RadioButton) orderView.findViewById(R.id.radio_off10);
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	  	    		if(address==96){
  	  	    			zjcheckbox1.setChecked(true);
  	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	  	    			  radioON_zj1.setChecked(true);
  	  	    			}else{
  	  	    			  radioOFF_zj1.setChecked(true);
  	  	    			}
  	  	    		}
  	  	    		if(address==97){
  	  	    			zjcheckbox2.setChecked(true);
  	  	    		if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj2.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj2.setChecked(true);
	  	    			}
  	  	    		}
  	  	    	    if(address==98){
	  	    			zjcheckbox3.setChecked(true);
	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	  	    			  radioON_zj3.setChecked(true);
	  	  	    			}else{
	  	  	    			  radioOFF_zj3.setChecked(true);
	  	  	    			}
	  	    		}
  	  	            if(address==99){
	    			    zjcheckbox4.setChecked(true);
	    			    if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	  	    			  radioON_zj4.setChecked(true);
	  	  	    			}else{
	  	  	    			  radioOFF_zj4.setChecked(true);
	  	  	    			}
	    		    }
  	  	            if(address==100){
    			        zjcheckbox5.setChecked(true);
    			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
    	  	    			  radioON_zj5.setChecked(true);
    	  	    			}else{
    	  	    			  radioOFF_zj5.setChecked(true);
    	  	    			}
    		        }
  	  	            if(address==101){
			        zjcheckbox6.setChecked(true);
			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj6.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj6.setChecked(true);
	  	    			}
		            }
  	  	           if(address==102){
			        zjcheckbox7.setChecked(true);
			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj7.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj7.setChecked(true);
	  	    			}
		            }
  	  	           if(address==103){
			        zjcheckbox8.setChecked(true);
			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj8.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj8.setChecked(true);
	  	    			}
		            }
  	  	           if(address==104){
			        zjcheckbox9.setChecked(true);
			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj9.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj9.setChecked(true);
	  	    			}
		            }
  	  	           if(address==105){
			        zjcheckbox10.setChecked(true);
			        if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    			  radioON_zj10.setChecked(true);
	  	    			}else{
	  	    			  radioOFF_zj10.setChecked(true);
	  	    			}
		            }
  	    		}
  	    		
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("���")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"SEQUENTIAL",NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*4/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
	    	
		}

		public void ifwaitoperate(int position){
			final int qxposition=position;
			View orderView=View.inflate(getActivity(), R.layout.new_order_ifwait,null );
			zscheckbox1=(CheckBox)orderView.findViewById(R.id.checkBox1);
	    	zscheckbox2=(CheckBox)orderView.findViewById(R.id.checkBox2);
	    	ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualInputListData();
	    	zscheckbox1.setText(inputList.get(16).get("signalNameEditText").toString().trim());
	    	zscheckbox2.setText(inputList.get(19).get("signalNameEditText").toString().trim());
	    	zscheckbox1.setOnCheckedChangeListener(new zsCheckBoxOnCheckedChangeListener());
	    	zscheckbox2.setOnCheckedChangeListener(new zsCheckBoxOnCheckedChangeListener());
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("IF")) {
  	    		int address=TableToBinary.searchAddress(operat_MOVE.split("\\s+")[0].trim(), false);
  	    		if(address==16){
  	    			zscheckbox1.setChecked(true);
  	    		}
  	    		if(address==19){
  	    			zscheckbox2.setChecked(true);
  	    		}
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("�ȴ�")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"IF",NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*2/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
		}

		public void delaytimeoperate(int position){
	    	final int qxposition=position;
	    	 TimerList = new ArrayList<String>();
	    	View orderView=View.inflate(getActivity(), R.layout.new_order_wait,null );
			final EditText editText_WAIT=(EditText) orderView.findViewById(R.id.editText_WAIT);
			Spinner InfoText =  (Spinner) orderView.findViewById(R.id.CerrectInfo);//@+id/CerrectInfo
	    	if(editText_WAIT==null){return;}
	    	if(InfoText==null){return;}
	    	Button TimerAdd = (Button) orderView.findViewById(R.id.addTimer);
	    	int i = Config.list_timername.size()+1;
	    	HashMap<String, Object>item = (HashMap<String, Object>) Fragments_Action.mListView.getItemAtPosition(qxposition);
	    	String orderTxt=item.get("orderSpinner").toString();
			if(orderTxt.contains("TWAIT")){
				opTxt = item.get("operatText").toString();
			}
        	String PosNumStr = null ;       			
        	{
    		ArrayList<String>	list1 = Config.list_timername;
    		int maxmap=0;
    		int mapint=0;
    			if(!TimerList.isEmpty())
    				TimerList.clear();
    			for(int i1 =0;i1<list1.size();i1++)
    			{   
    				String map = list1.get(i1).toString().trim();
    				mapint=Integer.parseInt(map.substring(1, 4));
    				maxmap=maxmap>mapint?maxmap:mapint;
    			}
    			for(int i1=1;i1<=maxmap;i1++){
    				if (i1<10)
	            		PosNumStr = "00"+i1;
	            	else if(i1<100)
	            		PosNumStr = "0"+i1;
	            	else 
	            		PosNumStr = ""+i1;
    				TimerList.add("T"+PosNumStr);
    			}
    			TimerSelAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, TimerList);  
    			TimerSelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
    			InfoText.setAdapter(TimerSelAdapter);    
       	     
       	     	if((!opTxt.equals(""))&&(opTxt.indexOf("T")!=-1))
	    		{	
	    				PosNumStr = opTxt.substring(opTxt.indexOf("T"), opTxt.indexOf("T")+4);
	    				for(int pos =0;pos<TimerList.size();pos++){

		    				if(TimerList.get(pos).toString().contains(PosNumStr))
		    				{    		    				
		    					InfoText.setSelection(pos);
		    					break;
		    				}			    			
	    				}
	    			
	    		}
       	     	else // ��ǰѡ����û��TIMER ��TIMER����1��������Ϊ��ǰ��ʾ
       	     	{
       	     		int size = TimerList.size()+1;
		       	     	if (size<10)
		            		PosNumStr = "00"+size;
		            	else if(size<100)
		            		PosNumStr = "0"+size;
		            	else 
		            		PosNumStr = ""+size;
       	     		TimerList.add("T"+PosNumStr);
       	     		TimerSelAdapter.notifyDataSetChanged();
       	     		InfoText.setSelection(size);
       	     	}
        	}
//        	Log.d("mpeng","the optxt is "+opTxt);
//            	if(!opTxt.equals("")&&(opTxt.indexOf("T")!=-1))
//    			{	
//    				PosNumStr = opTxt.substring(opTxt.indexOf("T")+1, opTxt.indexOf("T")+4);
//    				
//    			}else
//    			{
//    				if (i<10)
//	            		PosNumStr = "00"+i;
//	            	else if(i<100)
//	            		PosNumStr = "0"+i;
//	            	else 
//	            		PosNumStr = ""+i;
//    			}
//            	InfoText.setText("T"+PosNumStr);
    	        
    	        for(int i1=0;i1<Config.list_timername.size();i1++){
    	        	if(Config.list_timername.get(i1).toString().equals("T"+PosNumStr)){
    	        		editText_WAIT.setText("");
				        break;
    	        	}
    	        	if(i1==Config.list_timername.size()-1){
    	        		editText_WAIT.setText("");
    	        	}
    	        }
           
			AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("�ȴ�ָ��(WAIT)")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"WAIT",NcEditList,MyAdapter.this))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
          
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*2/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
			 opTxt = "";
	    }
	}
	/**
	 * ���format����ĺ�����������Զ���λ��0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForString(String str, int strLength) {
	    int strLen = str.length();
	    if (strLen < strLength) {
	        while (strLen < strLength) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(str);// ��0
	            // sb.append(str).append("0");//�Ҳ�0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }

	    return str;
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myActivity= activity;
		((NewPragramActivity) myActivity).setHandler(myHandler);
		
	}	
	
	private static  void saveAs_NcEdit(String foldername, String date) {

		Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername).createFile(Constans.nc�ı�����);
		//		Constans.dataToDownload.createFolder(Constans.mouldDataPATH+ foldername + File.separator, Constans.dataToDownloadFolder);
		Constans.mouldData.cleanFile(Constans.nc�ı�����);
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList1Data(),
				"[NCEdit1]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList2Data(),
				"[NCEdit2]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				NcEditList,
				"[NCEdit3]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList4Data(),
				"[NCEdit4]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList5Data(),
				"[NCEdit5]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList6Data(),
				"[NCEdit6]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList7Data(),
				"[NCEdit7]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		Fragments_mouldData.saveAs_8nc(foldername,
				ArrayListBound.getNCeditList8Data(),
				"[NCEdit8]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		
	}	
	//���ݣ�����ʱ���򱣴��SD��
	private void backupProgarm(String foldername)
	{
		final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
		saveAs_NcEdit(foldername, date);
	}
	private void copyDataToNc3()
	{	
			int i = 0;
	        Iterator<HashMap<String,Object>> iterator = NcEditList.iterator();
	        while (iterator.hasNext()) { 
	        	if(i>=nc3List.size())
	        		nc3List.add((HashMap<String, Object>) iterator.next().clone());
	        	else
	        		nc3List.set(i, (HashMap<String, Object>) iterator.next().clone());			      
	        	i++;
	        }  
	        
	        Log.e("mpeng"," over !");
		
	}
	private void restoreDataToNC3()
	{
		int i = 0;
        Iterator<HashMap<String,Object>> iterator = nc3ListBackup.iterator();
        nc3List.removeAll(nc3List);
        while (iterator.hasNext()) {         	
        		nc3List.add((HashMap<String, Object>) iterator.next().clone());       	
   
     
		 }
	}
	
	
	
}


