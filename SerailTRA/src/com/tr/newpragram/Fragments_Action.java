package com.tr.newpragram;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
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
import wifiRunnablesAndChatlistener.KeyCodeSend;
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
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
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
import com.tr.R.drawable;
import com.tr.bottomview.BottomButton;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.tr.programming.DetailedOrderListener1;
import com.tr.programming.TR_Programming_Activity;
import com.tr.programming.Fragments_NCedit1.MyAdapter;
import com.tr.widget.PositionItemWidget;
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
	public static ArrayList<HashMap<String, Object>> nc3List ;
	public ArrayList<HashMap<String, Object>> nc3ListBackup ;
	
	public static  MyAdapter NCedit_Adapter = null;
	
	public static Hashtable<Integer,View> temphHashTable = new Hashtable<Integer,View>();
   //add by mpeng
	private Button editPraBtn= null;
	private Button move= null;
	private Button qdbtn= null;
	private Button zjbtn= null;
	private Button zsjxhbtn= null;
	private Button wait= null;
	private Button jcbtn= null;
	private Button delaytime= null;
	
	private Handler myHandler = null;
	private Button insertBtn = null;
	private Button delBtn = null;
	public static Button DownBtn = null;
	private Button backupBtn = null;
	
	private Spinner curPosition = null;
	private Spinner MoveSeclectSpinner = null; 
	private ArrayAdapter<String> PosSelAdapter  = null;
	private ArrayAdapter<String> TimerSelAdapter  = null;
	
	private ToggleButton FSpSwitchBtn = null;
	private TextView spProductUDTxt;
	private TextView spProductBFTxt;

	
	private TextView xCurTxt;
	private TextView yCurTxt;
	private TextView hCurTxt;
	private TextView zCurTxt;
	private TextView lCurTxt;
	
	private CheckBox zscheckbox1;
	private CheckBox zscheckbox2;
	
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
	private ListView fSpListView;
	private EditText FpNumEdit;
	public static ArrayList<HashMap<String, Object>> posList = new ArrayList<HashMap<String, Object>>();// ����Ҫ�󶨵�list
	private EditText speed;
	private EditText aspeed;
	private EditText dspeed;
	private  List<String> PositionList  ; //position  list
	private  List<String> StandPackList  ; //position  list
	private  List<String> FreeList  ; //position  list
	
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
	private String saveStrname;
	private boolean DownLoadSucess = false;
	private static boolean isModify = false;
	private AlertDialog unFoucsDialog ;
	
	private  ArrayList<String> symbolList_WAIT=new ArrayList<String>();
	
	private  ArrayList<String> PPositionList=new ArrayList<String>();
	private  ArrayList<String> SPositionList=new ArrayList<String>();
	private  ArrayList<String> FPositionList=new ArrayList<String>();
	private  ArrayList<HashMap<String, Object>> DevicePositionList  = ArrayListBound.getDevicePositionListData();
	private Spinner spinnerP_MOVE;
	private ArrayAdapter<String> PAdapter;
	private ArrayAdapter<String> SPAdapter;
	private ArrayAdapter<String> FPAdapter;
	public static String [] temp0 = null; 
	public static String [] temp1 = null; 
	public static String [] temp2 = null; 
	public static String [] temp3 = null; 
	private String orderTemp = "";
	private List<CheckBox> boxList ;
	private List<View> itemList;
	public void updateMoveList(ArrayList<String> ppositionList,ArrayList<String> spositionList,ArrayList<String> fpositionList,ArrayList<HashMap<String, Object>> arrayList) {
		try{
		ppositionList.clear();
		spositionList.clear();
		fpositionList.clear();
		for (int i = 0; i < arrayList.size(); i++) {
			
			String posString=(String) arrayList.get(i).get("symbolNameEditText");
			String signalstr=(String) arrayList.get(i).get("signalNameEditText");
			if (posString.contains("SP")&&arrayList.get(i).get("setItem").toString().equals("1")) {
				spositionList.add(posString+"<��-��"+signalstr+"��-��>");
			}else if (posString.contains("FP")&&arrayList.get(i).get("setItem").toString().equals("1")) {
				fpositionList.add(posString+"<��-��"+signalstr+"��-��>");
			}else if (posString.contains("P")&&arrayList.get(i).get("setItem").toString().equals("1")) {
				ppositionList.add(posString+"<��-��"+signalstr+"��-��>");
			}
		}	
	}catch(Exception e){
		e.printStackTrace();
	}
	}	
	
	@SuppressWarnings("null")
	public void updateTIMEList(ArrayList<String> symbolList,ArrayList<HashMap<String, Object>> arrayList) {
		// Ҳ����ֱ�����������
	try{
		symbolList.clear();
	for (int i = 0; i <arrayList.size(); i++) {
		String symbolString_WAIT= arrayList.get(i).get("symbolNameEditText").toString().trim();
		String signalstr=arrayList.get(i).get("signalNameEditText").toString().trim();
		if (!symbolString_WAIT.equals("")&&arrayList.get(i).get("setItem").toString().trim().equals("1")) {
			symbolList.add(symbolString_WAIT+"<��-��"+signalstr+"��-��>");
		}
	}
   }catch(Exception e){
	e.printStackTrace();
   }
   }
	
	public int  findPositionByName(String PositionName)
	{
		int position = -1;
		int size =DevicePositionList.size();
		  for(int i = 0;i<size;i++)
		  {
			  if(DevicePositionList.get(i).get("symbolNameEditText").toString().equals(PositionName))
			  {
				  return i ;
			  }
		  }
		return position;
	}
	public int  findSpinerPositionByName(String PositionName)
	{
		int position = -1;
		int size =PPositionList.size();
		  for(int i = 0;i<size;i++)
		  {
			  if(PPositionList.get(i).toString().contains(PositionName))
			  {
				  return i ;
			  }
		  }
		return position;
	}
	public  void downPragram()
   {
	
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
	

	if(!NewPragramActivity.isDownloadOrNot()){  
		if(!Config.StopState)
		{
	    	new AlertDialog.Builder(getActivity())
			.setTitle("����")
			.setMessage("�������أ����л����ֶ�״̬��")
			.setPositiveButton("֪����",null)
			.show();  
		}
    	return;
    }
	new AlertDialog.Builder(getActivity())
	.setTitle("��ʾ").setMessage("ȷ���л����ֶ�״̬��������")
	.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() 
	{
		public void onClick(DialogInterface dialog, int which) {
				// �ȴ���ʾ
				WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
					WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(), "��д������","��ȴ�", true, false);
						//�Զ��л����ֶ�״̬
					    if(NewPragramActivity.isDownloadOrNot())
					    {
					    	KeyCodeSend.send(999, getActivity());
					    }
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
//						Msg.what = 2;
//						myHandler.sendMessage(Msg);
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
//					        if(NewPragramActivity.isDownloadOrNot())
//					        	NewPragramActivity.setDownloadOrNot(false);
					       Log.e("mpeng","onDismissonDismiss");
							 if(WifiSetting_Info.mClient==null){
								 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
								   .setMessage("�����쳣��ͨѶ�жϣ��������磡")
								   .setPositiveButton("ȷ��", null).show();
								 DownLoadSucess = false;
				                	//Toast.makeText(TR_Programming_Activity.this,"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
								}else if(overflag[0]==1){
									 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
									   .setMessage("���ݷ������")
									   .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											NewPragramActivity.reselctBar();
										}
									}).show();
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
							 
							 
						}  
			        });
				 
		}
	}).setNegativeButton("ȡ��",null).show();
	
	
   
   }
	public void addPosition()
	{
		String name = PPositionList.get(PPositionList.size()-1).toString().substring(0, 4);
		int number = Integer.parseInt(name.substring(1));
		if(number>=200){
			Toast.makeText(getActivity(),"��׼λ�÷�ΧΪ1~200�����ɼ�������",Toast.LENGTH_SHORT).show();
			return;
		}
		int position  = findPositionByName(name);
		String newname = "P"+String.format("%03d", number+1);
		int newpos = findPositionByName(newname);
		int index = -1;
		Log.e("mpeng","the new name "+newname+" new pos "+newpos);
		Log.e("mpeng","name "+name+"number "+number+" position "+position);
		if(newpos!=-1)
		{
			Log.e("mpeng", "11111111111111");
			HashMap<String, Object> map1 = (HashMap<String, Object>) DevicePositionList.get(newpos).clone();			
			map1.put("setItem", "1");
			DevicePositionList.set(newpos, map1);
			index = newpos;
		}	
		else
		{			
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("addressText", String
					.format("%04d", number+1));//
			map1.put("symbolNameEditText", newname);
			map1.put("signalNameEditText", "");
			map1.put("noteEditText", "");
			map1.put("setItem", "1");
			DevicePositionList.add(number, map1);
			index = number;
			for (int restposition = position + 1; restposition < DevicePositionList
					.size(); restposition++) {
				DevicePositionList.get(
						restposition).put(
						"addressText",
						String.format("%04d",
								restposition+1));
			}
		}
		// ���µ�ַ��
		
		updateMoveList(PPositionList,SPositionList,FPositionList,DevicePositionList);
		PAdapter.notifyDataSetChanged();		
		spinnerP_MOVE.setSelection(index);
		index = -1;
	}
	public void changename(String text,int  position)
	{		
		
		HashMap<String, Object>map = (HashMap<String, Object>) DevicePositionList.get(position).clone();
		map.put("signalNameEditText", text);
		DevicePositionList.set(position, map);
		updateMoveList(PPositionList,SPositionList,FPositionList,DevicePositionList);
		int mypos  = findSpinerPositionByName(text);
		spinnerP_MOVE.setSelection(mypos);
		PAdapter.notifyDataSetChanged();
		SPAdapter.notifyDataSetChanged();
		FPAdapter.notifyDataSetChanged();
		
	}
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
//			NCedit_Adapter.notifyDataSetChanged();
		}
		Message Msg = new Message();
		Msg.what = 4;
		myHandler.sendMessage(Msg);
		TR_Programming_Activity.noteflag=0;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void enableButton()
	{
		insertBtn.setEnabled(true);
    	delBtn.setEnabled(true);
    	editPraBtn.setEnabled(true);
    	//backupBtn.setEnabled(true);
	}
	private void disableButton(){
		insertBtn.setEnabled(false);
		delBtn.setEnabled(false);
		editPraBtn.setEnabled(false);
		//backupBtn.setEnabled(false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try{
		for(int i=0;i<nc_ordersall.length;i++){
			nc_orders[i]=nc_ordersall[i].split("\\s+")[0];
		}
		updateMoveList(PPositionList,SPositionList,FPositionList,DevicePositionList);
		updateTIMEList(symbolList_WAIT,ArrayListBound.getDeviceTimerListData());
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
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
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

		SendDataRunnable axleDataSendRunnable =new SendDataRunnable(
				new WifiSendDataFormat(Config.pspfpaxle, 
						0x080E4F00), getActivity());
		sendDataRunnables.add(axleDataSendRunnable);
		
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
		readFileOnLine();
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
				.setTitle("����Ϊ����").setView(view_newcreate)
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
						WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
						WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(), "���������", "��ȴ�", true, false); 
						
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_num", editmouldnumstr.toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_name", editmouldnamestr.toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", date2).commit();
						

						saveStrname=editmouldnamestr.toString().trim();
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
				case 4:  //����LIST
					NCedit_Adapter.notifyDataSetChanged();
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
		 
		// ���Ӳ�����ʾ
		mListView.setAdapter(NCedit_Adapter);


		  mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//����Ϊ��ѡ
			// ���ӵ��
					mListView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								final int position, long arg3) {
							try{
								Log.e("mpeng","nceditget selectitem :"+NCedit_Adapter.getSelectItem()
										+"position :"+position);
								if(NCedit_Adapter.getSelectItem()==position)
								{	
									String Order = NcEditList.get(NCedit_Adapter.getSelectItem()).get("orderSpinner").toString();
									String Operate = NcEditList.get(NCedit_Adapter.getSelectItem()).get("operatText").toString();
									
									if(Order.equalsIgnoreCase("MOVE")||Order.equalsIgnoreCase("MOVEP"))
									{
										move.performClick();
									}else if(Order.equalsIgnoreCase("TWAIT"))
									{
										delaytime.performClick();
									}else if(Order.equalsIgnoreCase("IF"))
									{
										if(checkIF(Operate)){
											wait.performClick();
										}
									}else if(Order.equalsIgnoreCase("OUT"))
									{
										if(checkqd(Operate)){
										  qdbtn.performClick();
										}else if(checkzsjxh(Operate)){
										  zsjxhbtn.performClick();
										}else if(checkzj(Operate)){
										  zjbtn.performClick();
										}else if(checkjc(Operate)){
										  jcbtn.performClick();
										}
									}
								}
								else
								{
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
									}
						}catch(Exception e){
							e.printStackTrace();
						}
						}
			});
				
	editPraBtn =(Button) getActivity().findViewById(R.id.editBtn);
	editPraBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int index = NCedit_Adapter.getSelectItem();
			if(index!=-1)
			{
				String Order = NcEditList.get(index).get("orderSpinner").toString();
				String Operate = NcEditList.get(index).get("operatText").toString();
				
				if(Order.equalsIgnoreCase("MOVE")||Order.equalsIgnoreCase("MOVEP"))
				{
					move.performClick();
				}else if(Order.equalsIgnoreCase("TWAIT"))
				{
					delaytime.performClick();
				}else if(Order.equalsIgnoreCase("IF"))
				{
					if(checkIF(Operate)){
						wait.performClick();
					}
				}else if(Order.equalsIgnoreCase("OUT"))
				{
					if(checkqd(Operate)){
					  qdbtn.performClick();
					}else if(checkzsjxh(Operate)){
					  zsjxhbtn.performClick();
					}else if(checkzj(Operate)){
					  zjbtn.performClick();
					}else if(checkjc(Operate)){
					  jcbtn.performClick();
					}
				}
			}
			else{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
				 }
		}
	});

	move = (Button) getActivity().findViewById(R.id.moveBtn);
	move.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.d("mpeng"," the my pos is "+NCedit_Adapter.getSelectItem());
			if(NCedit_Adapter.getSelectItem() > -1)
			{
				NCedit_Adapter.moveoperate("MOVE", NCedit_Adapter.getSelectItem());
				//mpeng
			}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
		}
	});
	
	qdbtn = (Button) getActivity().findViewById(R.id.qdBtn);
	qdbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(NCedit_Adapter.getSelectItem() >-1)
			{NCedit_Adapter.zsoperate(NCedit_Adapter.getSelectItem());}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
		}
		
	});
	zjbtn = (Button) getActivity().findViewById(R.id.zjBtn);
	zjbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(NCedit_Adapter.getSelectItem() > -1)
			{NCedit_Adapter.zjoperate(  NCedit_Adapter.getSelectItem());}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
		}
	});
	zsjxhbtn = (Button) getActivity().findViewById(R.id.zsjxhtBtn);
	zsjxhbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(NCedit_Adapter.getSelectItem() > -1)
			{NCedit_Adapter.zsjxhoperate(  NCedit_Adapter.getSelectItem());}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
		}
	});
	jcbtn = (Button) getActivity().findViewById(R.id.jcBtn);
	jcbtn.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(NCedit_Adapter.getSelectItem() >-1)
			{NCedit_Adapter.jcoperate(  NCedit_Adapter.getSelectItem());}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
		}
	});
	delaytime = (Button) getActivity().findViewById(R.id.delaytimeBtn);
	delaytime.setOnClickListener(new OnClickListener() {	
		public void onClick(View v) {
			if(NCedit_Adapter.getSelectItem() > -1)
			{NCedit_Adapter.delaytimeoperate(  NCedit_Adapter.getSelectItem());}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
		}
	});
	
	wait = (Button)getActivity().findViewById(R.id.waitBtn);
	wait.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(NCedit_Adapter.getSelectItem() >-1)
			{NCedit_Adapter.ifwaitoperate(  NCedit_Adapter.getSelectItem());}
			else
			{
				if(unFoucsDialog!=null)
					unFoucsDialog.dismiss();
				 unFoucsDialog = new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				            .setMessage("��ѡ�г����е�ĳһ���ٽ��д˲���")				            
				            .setPositiveButton("ȡ��", null)
				            .show();
			}
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
			NCedit_Adapter.addOneItem( NCedit_Adapter.getSelectItem());
			isModify = true;
			NCedit_Adapter.notifyDataSetChanged();			
		}
	});
	  delBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			NCedit_Adapter.removeItem( NCedit_Adapter.getSelectItem());
			isModify = true;
			NCedit_Adapter.notifyDataSetChanged();
			
		}
	});
	  
	  DownBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			downPragram();
		}
	  });
		
	}catch(Exception e){
		e.printStackTrace();
	}
        new Thread(){
        	public void run() {
        		showNote();
        	};
        }.start();
        NCedit_Adapter.notifyDataSetChanged();
	}

	public boolean checkIF(String posName) {
			ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualInputListData();
			String sym16=inputList.get(16).get("symbolNameEditText").toString().trim();
			String sym19=inputList.get(19).get("symbolNameEditText").toString().trim();
			posName=posName.split("\\s+")[0].trim();
			if(posName.equalsIgnoreCase(sym16)||posName.equalsIgnoreCase(sym19)){
				return true;
			}else{
				return false;
			}
	}	
	public boolean checkqd(String posName) {
		ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualOutputListData();
		String sym32=inputList.get(32).get("symbolNameEditText").toString().trim();
		String sym33=inputList.get(33).get("symbolNameEditText").toString().trim();
		String[] posNames=posName.split(",");
		if(posNames.length!=2){
			return false;
		}
		String posName0=posNames[0].split("\\s+")[0].trim();
		String posName1=posNames[1].split("\\s+")[0].trim();
		if((posName0.equalsIgnoreCase(sym32)&&posName1.equalsIgnoreCase(sym33))||
				(posName0.equalsIgnoreCase(sym33)&&posName1.equalsIgnoreCase(sym32))){
			return true;
		}else{
			return false;
		}
   }
	public boolean checkzj(String posName) {
		ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualOutputListData();
		String sym34=inputList.get(34).get("symbolNameEditText").toString().trim();
		String sym35=inputList.get(35).get("symbolNameEditText").toString().trim();
		String sym36=inputList.get(36).get("symbolNameEditText").toString().trim();
		String sym37=inputList.get(37).get("symbolNameEditText").toString().trim();
		String sym38=inputList.get(38).get("symbolNameEditText").toString().trim();
		String[] posNames=posName.split(",");
		String posName0="";
		for(int i=0;i<posNames.length;i++){
			posName0=posNames[i].split("\\s+")[0].trim();
			System.out.println("posNames="+posNames+"   posName0="+posName0);
			if(posName0.equalsIgnoreCase(sym34)||posName0.equalsIgnoreCase(sym35)||
				posName0.equalsIgnoreCase(sym36)||posName0.equalsIgnoreCase(sym37)||posName0.equalsIgnoreCase(sym38)){			
				return true;
		    }
		}
		
		return false;
   }
	
	public boolean checkzsjxh(String posName) {
		ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualOutputListData();
		String sym16=inputList.get(16).get("symbolNameEditText").toString().trim();
		String sym17=inputList.get(17).get("symbolNameEditText").toString().trim();
		String sym18=inputList.get(18).get("symbolNameEditText").toString().trim();
		String sym37=inputList.get(37).get("symbolNameEditText").toString().trim();
		String[] posNames=posName.split(",");
		String posName0="";
		for(int i=0;i<posNames.length;i++){
			posName0=posNames[i].split("\\s+")[0].trim();
			if(posName0.equalsIgnoreCase(sym16)||posName0.equalsIgnoreCase(sym17)||
				posName0.equalsIgnoreCase(sym18)){
			return true;
		}
		}
		
		return false;
   }
	
	public boolean checkjc(String posName) {
		ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceMiddleOutputListData();
		String sym96=inputList.get(96).get("symbolNameEditText").toString().trim();
		String sym97=inputList.get(97).get("symbolNameEditText").toString().trim();
		String sym98=inputList.get(98).get("symbolNameEditText").toString().trim();
		String sym99=inputList.get(99).get("symbolNameEditText").toString().trim();
		String sym100=inputList.get(100).get("symbolNameEditText").toString().trim();
		String sym101=inputList.get(101).get("symbolNameEditText").toString().trim();
		String sym102=inputList.get(102).get("symbolNameEditText").toString().trim();
		String sym103=inputList.get(103).get("symbolNameEditText").toString().trim();
		String sym104=inputList.get(104).get("symbolNameEditText").toString().trim();
		String sym105=inputList.get(105).get("symbolNameEditText").toString().trim();
		String[] posNames=posName.split(",");
		String posName0="";
		for(int i=0;i<posNames.length;i++){
			posName0=posNames[i].split("\\s+")[0].trim();
			if(posName0.equalsIgnoreCase(sym96)||posName0.equalsIgnoreCase(sym97)||
				posName0.equalsIgnoreCase(sym98)||posName0.equalsIgnoreCase(sym99)||
				posName0.equalsIgnoreCase(sym100)||posName0.equalsIgnoreCase(sym101)||
				posName0.equalsIgnoreCase(sym102)||posName0.equalsIgnoreCase(sym103)||
				posName0.equalsIgnoreCase(sym104)||posName0.equalsIgnoreCase(sym105)){
			return true;
		}
		}
		
		return false;
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
		public int getSelectItem() {
			return mselectItem;
		}
		// ɾ��ĳһ��
		public void removeItem(int position) {
			System.out.println("ɾ��position="+position);
			if(position<0){
				Toast.makeText(getActivity(),"��ȫ��ɾ��", Toast.LENGTH_SHORT).show();
			}else{
				mAppList.remove(position);
			if(mAppList.size()<position+1){
				NCedit_Adapter.setSelectItem(NCedit_Adapter.getSelectItem()-1);
			} 	
			for ( int restposition=position ; restposition < mAppList.size(); restposition++) {
				mAppList.get(restposition).put("addressText", String.format("%04d", restposition));
			}
			this.notifyDataSetChanged();
			}
			
		}
		
		//LIST���� ȡֻȡNOTEEDITTEXT����
				public  void addOneItem(int position) {
					if(position<0){
						HashMap<String, Object> mapadd = new HashMap<String, Object>();
						mapadd.put("addressText",	String.format("%04d", 0));//
						mapadd.put("numSpinner","");
						mapadd.put("orderSpinner","");
						mapadd.put("operatText","");
						mapadd.put("noteEditText","");
						mAppList.add(mapadd);
						NCedit_Adapter.setSelectItem(0);
					}else{
						HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText",	String.format("%04d", mAppList.size()));//
					mapadd.put("numSpinner","");
					mapadd.put("orderSpinner","");
					mapadd.put("operatText","");
					mapadd.put("noteEditText","");
					
					mAppList.add(position,mapadd);
					for ( int restposition=position ; restposition < mAppList.size(); restposition++) {
						mAppList.get(restposition).put("addressText", String.format("%04d", restposition));
					}
					}
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
				String addText = appInfo.get("addressText").toString();
				holder.addressText.setText(addText);
				holder.addressText.setGravity(Gravity.CENTER);
//				appInfo.get("addressText").toString();
//				holder.addressText.setText(String.valueOf(position));
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

	     class zsCheckBoxOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
	    			private int position;
	    			zsCheckBoxOnCheckedChangeListener(int pos){
	    				position = pos;
	    			}	    
	    	@Override  
	        public void onCheckedChanged(CompoundButton button, boolean isChecked){  
	            if(isChecked){
	            	if(position == 0)
	            	{
	            		boxList.get(0).setChecked(true);
	            		boxList.get(1).setChecked(false);
	            	}
	            	else if(position == 1)
	            	{
	            		boxList.get(0).setChecked(false);
	            		boxList.get(1).setChecked(true);
	            	}
	            	else
	            	{
	            		boxList.get(position).setChecked(true);
	            	}
	            }else{
	            	if(position == 0)
	            	{
	            		boxList.get(0).setChecked(false);
	            		boxList.get(1).setChecked(true);
	            	}
	            	else if(position == 1)
	            	{
	            		boxList.get(0).setChecked(true);
	            		boxList.get(1).setChecked(false);
	            	}
	            	else
	            	{
	            		boxList.get(position).setChecked(false);
	            	}
	            }  
	        }  
	    }
	    private class newCheckBoxOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
	       
	    	private int position;
	    	CheckBox cb;
        	RadioButton radioOFF ;
        	RadioButton radioON;
        	newCheckBoxOnCheckedChangeListener(int pos){
				position = pos;
				cb =(CheckBox) itemList.get(position).findViewById(R.id.checkBox1);
				radioON =(RadioButton) itemList.get(position).findViewById(R.id.radio_on1);
				radioOFF = (RadioButton) itemList.get(position).findViewById(R.id.radio_off1);
			}	
	    	
	    	@Override  
	        public void onCheckedChanged(CompoundButton button, boolean isChecked){  
	            if(isChecked){	
	            	radioOFF.setChecked(true);
	            	radioON.setChecked(true);
	            }  
	            else  {	           
	            	radioON.setChecked(false);
	            	radioOFF.setChecked(false);
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
		public void moveoperate(String oper,int position){
			final View orderView=View.inflate(getActivity(), R.layout.new_order_move,null );//��moveһ��
			
			RadioGroup groupMOVE = (RadioGroup) orderView.findViewById(R.id.radioGroupMOVE);
			final RadioButton radioP_MOVE=(RadioButton) orderView.findViewById(R.id.radioP_MOVE);
			final RadioButton radioSP_MOVE=(RadioButton) orderView.findViewById(R.id.radioSP_MOVE);
			final RadioButton radioFP_MOVE=(RadioButton) orderView.findViewById(R.id.radioFP_MOVE);
	    	spinnerP_MOVE=(Spinner)orderView.findViewById(R.id.spinnerP_MOVE);
//	    	final Spinner spinnerSP_MOVE=(Spinner)orderView.findViewById(R.id.spinnerSP_MOVE);
//	    	final Spinner spinnerFP_MOVE=(Spinner)orderView.findViewById(R.id.spinnerFP_MOVE);
	    	CheckBox checkbox1=(CheckBox)orderView.findViewById(R.id.checkBox1);
	    	CheckBox checkbox2=(CheckBox)orderView.findViewById(R.id.checkBox2);
	    	CheckBox checkbox3=(CheckBox)orderView.findViewById(R.id.checkBox4);
	    	CheckBox checkbox4=(CheckBox)orderView.findViewById(R.id.checkBox3);
	    	CheckBox checkbox5=(CheckBox)orderView.findViewById(R.id.checkBox5);
	    	final Button addBtn = (Button) orderView.findViewById(R.id.positionAdd);
	    	final Button editBtn = (Button) orderView.findViewById(R.id.positionEdit);

	    	CheckBox[] checkboxs={checkbox1,checkbox2,checkbox3,checkbox4,checkbox5};
//	    	final ArrayAdapter<String> NullAdapter= new ArrayAdapter<String>(orderView.getContext(),android.R.layout.simple_spinner_item,NullList);
	    	PAdapter= new ArrayAdapter<String>(orderView.getContext(),R.layout.simple_spinner_item,PPositionList);	
	    	SPAdapter= new ArrayAdapter<String>(orderView.getContext(),R.layout.simple_spinner_item,SPositionList);
	    	FPAdapter= new ArrayAdapter<String>(orderView.getContext(),R.layout.simple_spinner_item,FPositionList);
	    	PAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	SPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	FPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	
	    	/**
	    	 * �ٴε�������ٴ�����,�Զ����룻
	    	 */
	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
	    	if (order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP")) {
	    		if (operat_MOVE.contains("SP")) {
					radioSP_MOVE.setChecked(true);
					spinnerP_MOVE.setAdapter(SPAdapter);
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
					for(int m=0;m<SPositionList.size();m++){
						if(SPositionList.get(m).startsWith(operat_MOVE)){
							spinnerP_MOVE.setSelection(m);
						}
					}
				}else if (operat_MOVE.contains("FP")) {
					radioFP_MOVE.setChecked(true);
					spinnerP_MOVE.setAdapter(FPAdapter);
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
					for(int m=0;m<FPositionList.size();m++){
						if(FPositionList.get(m).startsWith(operat_MOVE)){
							spinnerP_MOVE.setSelection(m);
						}
					}
				}else if (operat_MOVE.contains("P")) {
					radioP_MOVE.setChecked(true);
					spinnerP_MOVE.setAdapter(PAdapter);
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
					for(int m=0;m<PPositionList.size();m++){
						if(PPositionList.get(m).startsWith(operat_MOVE)){
							spinnerP_MOVE.setSelection(m);
						}
					}
				}										    													    		
			}else if (radioP_MOVE.isChecked()) {
				spinnerP_MOVE.setAdapter(PAdapter);
	    	}
	    	addBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v){
					// TODO Auto-generated method stub				
					addPosition();					
				}
			});
	    	editBtn.setOnClickListener(new PListener());
	    	/**
	    	 * �ս���
	    	 */

	    	//���õ�ѡ����
	    	groupMOVE.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {

					
					if (checkedId==R.id.radioP_MOVE) {
						System.out.println("ѡ��P");
						spinnerP_MOVE.setAdapter(PAdapter);
						addBtn.setEnabled(true);
						editBtn.setEnabled(true);
					}else if(checkedId==R.id.radioSP_MOVE) {
						System.out.println("ѡ��SP");
						spinnerP_MOVE.setAdapter(SPAdapter);
						addBtn.setEnabled(false);
						editBtn.setEnabled(false);
					}else{
						System.out.println("ѡ��FP");
						spinnerP_MOVE.setAdapter(FPAdapter);
						addBtn.setEnabled(false);
						editBtn.setEnabled(false);
					}
				}
			});
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("���ƶ�ָ��("+oper+")")
			 .setView(orderView)
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener(orderView,position,oper,NcEditList,MyAdapter.this,mListView))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						
						try {//��ʧ
		                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
		                    field.setAccessible( true );  
		                    field.set(dialog, true );//��mShowing������Ϊtrue����ʾ�Ի���δ�ر�     
		                }catch (Exception e) {
		                	e.printStackTrace(); 
						}
		                dialog.dismiss();
					}
				}).show();
          
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
						 opTxt = "";
					}  
					
		        });
		}
		//temp0 �������ŵ�����
	    public void zsoperate(int position){
			View orderView=View.inflate(getActivity(), R.layout.new_order_zs,null );
			ArrayList<HashMap<String, Object>> outputList = LoadIOList(temp0[1]);
			 boxList = new ArrayList<CheckBox>();
			LinearLayout ll = (LinearLayout) orderView.findViewById(R.id.newOrderZS);
			int size = temp0.length-3;
			if(size>0)
			{
				for(int i = 0;i<size;i++)
				{
					View item = mInflater.inflate(R.layout.new_order_item, null);
					ll.addView(item);
					CheckBox  cb =  (CheckBox) item.findViewById(R.id.checkBox2);					
					cb.setText(outputList.get(Integer.parseInt(temp0[i+3])).get("signalNameEditText").toString().trim());
					
						cb.setOnCheckedChangeListener(new zsCheckBoxOnCheckedChangeListener(i));
					boxList.add(cb);
				}
				
			}
			else
			{
				
			}
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	  	    		if(address==Integer.parseInt(temp0[3])){
  	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	  	    			boxList.get(0).setChecked(true);
  	  	    			}else{
  	  	    			boxList.get(1).setChecked(true);
  	  	    			}
  	  	    		}
  	  	    	   if(address==Integer.parseInt(temp0[4])){
	  	    			if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
	  	    				boxList.get(1).setChecked(true);
	  	    			}else{
	  	    				boxList.get(0).setChecked(true);
	  	    			}
	  	    		}
  	  	    	}
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("���ƶ���")
			 .setView(orderView)
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (boxList,position,"OUT",NcEditList,MyAdapter.this,mListView))//ʹ�õ����������������ϸ����������Ӧ!!!											
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
			View orderView=View.inflate(getActivity(), R.layout.new_order_zj,null );
			ArrayList<HashMap<String, Object>> outputList = LoadIOList(temp1[1]);
			itemList = new ArrayList<View>();
			LinearLayout ll = (LinearLayout) orderView.findViewById(R.id.newOrderZj);
			int size = temp1.length-3;
			if(size>0)
			{
				for(int i = 0;i<size;i++)
				{
					View item = mInflater.inflate(R.layout.new_order_zj_item, null);
					ll.addView(item);
					itemList.add(item);
					CheckBox  cb =  (CheckBox) item.findViewById(R.id.checkBox1);	
					cb.setOnCheckedChangeListener(new newCheckBoxOnCheckedChangeListener(i));
					cb.setText(outputList.get(Integer.parseInt(temp1[size+2])).get("signalNameEditText").toString().trim());
				
				}
				
			}		

	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	    			for(int j =0;j<size;j++)
  	    			{
  	    				if(address ==Integer.parseInt(temp1[j+3]))
  	    				{
  	    					View v = itemList.get(j);
  	    					CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
  	    					RadioButton rBtnOn = (RadioButton) v.findViewById(R.id.radio_on1);	
  	    					RadioButton rBtnOFF = (RadioButton) v.findViewById(R.id.radio_off1);	
  	    					cb.setChecked(true);
  	    					if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	    						rBtnOn.setChecked(true);
    	  	    			}else{
    	  	    				rBtnOFF.setChecked(true);
    	  	    			}
  	    				}
  	    			}
  	    		}
  	    		
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("�ξ�")
			 .setView(orderView)
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener(itemList,position,"OUTP",NcEditList,MyAdapter.this,mListView,false))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
	    	
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*3/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
	    	
		}
		public void zsjxhoperate(int position){
			View orderView=View.inflate(getActivity(), R.layout.new_order_zj,null );
			ArrayList<HashMap<String, Object>> outputList = LoadIOList(temp2[1]);
			itemList = new ArrayList<View>();
			LinearLayout ll = (LinearLayout) orderView.findViewById(R.id.newOrderZj);
			int size = temp2.length-3;
			if(size>0)
			{
				for(int i = 0;i<size;i++)
				{
					View item = mInflater.inflate(R.layout.new_order_zj_item, null);
					ll.addView(item);
					itemList.add(item);
					CheckBox  cb =  (CheckBox) item.findViewById(R.id.checkBox1);					
					cb.setText(outputList.get(Integer.parseInt(temp2[size+2])).get("signalNameEditText").toString().trim());
					cb.setOnCheckedChangeListener(new newCheckBoxOnCheckedChangeListener(i));
					
				}
				
			}	
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
  	    	if (order_MOVE.contains("OUT")) {
  	    		String[] operat_MOVEs=operat_MOVE.split(",");
  	    		for(int i=0;i<operat_MOVEs.length;i++){
  	    			int address=TableToBinary.searchAddress(operat_MOVEs[i].split("\\s+")[0].trim(), false);
  	    			for(int j =0;j<size;j++)
  	    			{
  	    				if(address ==Integer.parseInt(temp2[j+3]))
  	    				{
  	    					View v = itemList.get(j);
  	    					CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox1);
  	    					RadioButton rBtnOn = (RadioButton) v.findViewById(R.id.radio_on1);	
  	    					RadioButton rBtnOFF = (RadioButton) v.findViewById(R.id.radio_off1);	
  	    					cb.setChecked(true);
  	    					Log.e("mpeng","operat_MOVEs[i].split()[1].trim() "+operat_MOVEs[i].split("\\s+")[1].trim());
  	    					if("ON".equals(operat_MOVEs[i].split("\\s+")[1].trim())){
  	    						rBtnOn.setChecked(true);
    	  	    			}else{
    	  	    				rBtnOFF.setChecked(true);
    	  	    			}
  	    				}
  	    			}
  	    		}

  	    		}

	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("ע�ܻ��ź�")
			 .setView(orderView)
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (itemList,position,"PARALLEL",NcEditList,MyAdapter.this,mListView,false))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*3/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
		}
		public void jcoperate(int position){
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
			TextView nosetting=(TextView) orderView.findViewById(R.id.nosetting);
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
			
			if(inputList.get(96).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(97).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(98).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(99).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(100).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(101).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(102).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(103).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(104).get("symbolNameEditText").toString().trim().equals("")&&
			   inputList.get(105).get("symbolNameEditText").toString().trim().equals("")){
				nosetting.setVisibility(View.VISIBLE);
				nosetting.setText("�����豸�����ж���������źţ�");
			}else{
				nosetting.setVisibility(View.GONE);
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
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (orderView,position,"SEQUENTIAL",NcEditList,MyAdapter.this,mListView))//ʹ�õ����������������ϸ����������Ӧ!!!											
			 .setNegativeButton("ȡ��", null).show();
			 //.setContentView(R.layout.nc_listrowcontent);
         
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*5/7; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ
	    	
		}

		public void ifwaitoperate(int position){
			View orderView=View.inflate(getActivity(), R.layout.new_order_zs,null );
			ArrayList<HashMap<String, Object>> outputList = LoadIOList(temp3[1]);
			List<CheckBox> boxList = new ArrayList<CheckBox>();
			LinearLayout ll = (LinearLayout) orderView.findViewById(R.id.newOrderZS);
			int size = temp3.length-3;
			if(size>0)
			{
				for(int i = 0;i<size;i++)
				{
					View item = mInflater.inflate(R.layout.new_order_item, null);
					ll.addView(item);
					CheckBox  cb =  (CheckBox) item.findViewById(R.id.checkBox2);	
					Log.e("mpeng"," size is "+size+"i :"+i);
					cb.setText(outputList.get(Integer.parseInt(temp3[i+3])).get("signalNameEditText").toString().trim());
					cb.setOnCheckedChangeListener(new zsCheckBoxOnCheckedChangeListener(i));
					boxList.add(cb);
				}
				if(size>=2)
				{
//					boxList.get(0).setOnCheckedChangeListener();
				}
			}
			else
			{
				
			}
	    	 /**
  	    	 * �ٴε�������ٴ�����,�Զ����룻
  	    	 */
  	    	String order_MOVE=mAppList.get(position).get("orderSpinner").toString();
  	    	String operat_MOVE=mAppList.get(position).get("operatText").toString();
//  	    	if (order_MOVE.contains("IF")) {
//  	    		int address=TableToBinary.searchAddress(operat_MOVE.split("\\s+")[0].trim(), false);

  	    		//  	    		if(address==16){
//  	    			zscheckbox1.setChecked(true);
//  	    		}
//  	    		if(address==19){
//  	    			zscheckbox2.setChecked(true);
//  	    		}
  	    	if (order_MOVE.contains("IF")) {
  	    		int address=TableToBinary.searchAddress(operat_MOVE.split("\\s+")[0].trim(), false);
  	    		for(int j =0;j<size;j++)
	    			{
	    				if(address ==Integer.parseInt(temp2[j+3]))
	    				{	    					
	    					CheckBox cb = boxList.get(j);	    						
	    					cb.setChecked(true);	    				
	    				}
	    			}
  
  			}
	    	AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("�ȴ�")
			 .setView(orderView)
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener (boxList,position,"IF",NcEditList,MyAdapter.this,mListView))//ʹ�õ����������������ϸ����������Ӧ!!!											
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
	    	View orderView=View.inflate(getActivity(), R.layout.nc_order_wait,null );
			RadioGroup radioGroup_WAIT = (RadioGroup) orderView.findViewById(R.id.radioGroup_WAIT);
			final EditText editText_WAIT=(EditText) orderView.findViewById(R.id.editText_WAIT);
			final Spinner spinner_WAIT=(Spinner) orderView.findViewById(R.id.spinner_WAIT);
			RadioButton radiofixed_WAIT=(RadioButton) orderView.findViewById(R.id.radiofixed_WAIT);
	    	RadioButton radioset_WAIT=(RadioButton) orderView.findViewById(R.id.radioset_WAIT);
	    	RadioButton radiopos_WAIT=(RadioButton) orderView.findViewById(R.id.radiopos_WAIT);
	    	if(radioGroup_WAIT==null){return;}
	    	if(editText_WAIT==null){return;}
	    	if(spinner_WAIT==null){return;}
	    	if(radiofixed_WAIT==null){return;}
	    	if(radioset_WAIT==null){return;}
	    	if(radiopos_WAIT==null){return;}
	    	editText_WAIT.setFocusable(false);
	    	radioset_WAIT.setChecked(true);
			
	    	TimerSelAdapter= new ArrayAdapter<String>(orderView.getContext(),android.R.layout.simple_spinner_item,symbolList_WAIT);
			TimerSelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_WAIT.setAdapter(TimerSelAdapter);
			
			/**
	    	 * �ٴε�������ٴ�����,�Զ����룻
	    	 */
	    	String order_WAIT=mAppList.get(position).get("orderSpinner").toString().trim();
	    	String operat_WAIT=mAppList.get(position).get("operatText").toString().trim();
	    	if (order_WAIT.contains("WAIT")) {
	    		if (operat_WAIT.contains("s")) {
	    			radiofixed_WAIT.setChecked(true);
	    			editText_WAIT.setFocusableInTouchMode(true);
	    			editText_WAIT.setText(operat_WAIT.replace("s", ""));
				}else if (operat_WAIT.contains("T")) {
					radioset_WAIT.setChecked(true);
					for(int m=0;m<symbolList_WAIT.size();m++){
						if(symbolList_WAIT.get(m).startsWith(operat_WAIT)){
							spinner_WAIT.setSelection(m);	
						}
					}	
				}else {
					radiopos_WAIT.setChecked(true);
				}
			}
			/**
			 * �ս���
			 */
			radioGroup_WAIT.setOnCheckedChangeListener(new OnCheckedChangeListener() {	
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId==R.id.radiofixed_WAIT) {
						editText_WAIT.setFocusableInTouchMode(true);
						spinner_WAIT.setClickable(false);
					}else if (checkedId==R.id.radioset_WAIT) {
						editText_WAIT.setFocusable(false);
						spinner_WAIT.setClickable(true);
					}else {
						editText_WAIT.setFocusable(false);
						spinner_WAIT.setClickable(false);
					} 										
				}
			});	
			AlertDialog dialog2=new AlertDialog.Builder(getActivity())
			 .setTitle("�ȴ�ָ��(WAIT)")
			 .setView(orderView)
			 .setCancelable(false)//������ⲻ��ʧ
			 .setPositiveButton(R.string.OK,//ȷ�� 
					 new ActionOrderListener(orderView,position,"WAIT",NcEditList,MyAdapter.this,mListView))//ʹ�õ����������������ϸ����������Ӧ!!!											
			        .setNegativeButton("ȡ��", null).show();
          
			 WindowManager manager = getActivity().getWindowManager(); 
			 Display display = manager.getDefaultDisplay(); 
			 Rect picRect=new Rect();
			 display.getRectSize(picRect); 
			  
			 WindowManager.LayoutParams params = dialog2.getWindow().getAttributes(); 
			 params.width = picRect.width()*4/5; 
			 params.height =picRect.height()*4/5 ; 
			 dialog2.getWindow().setAttributes(params);//�����������ڵĴ�С����Ӧ��Ļ 		
	    }
		
		
		class PListener implements OnClickListener {
		private AlertDialog valueDialog;
		int touch_flag=0;

		PListener() {
			
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
		 */
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub			

			final EditText etEditText = new EditText(getActivity());
			InputFilter[] filters2 = {new InputFilter.LengthFilter(24)};				
			etEditText.setFilters(filters2);					
			etEditText.setHint("�������ź���");			
			String name = spinnerP_MOVE.getSelectedItem().toString();		
			if (name.contains("SP")){
				name = name.substring(0,5);
			}else if (name.contains("FP")) {
				name = name.substring(0,5);
			}else if (name.contains("P")) {
				name = name.substring(0,4);
			}
			final int position  = findPositionByName(name);		
			String valueString = DevicePositionList.get(position).get("signalNameEditText").toString();
			etEditText.setText(valueString);
			etEditText.setSelection(valueString.length());// ���ù��λ��
			valueDialog = new AlertDialog.Builder(getActivity())
			.setTitle("����������")
			.setView(etEditText)
			.setPositiveButton(R.string.OK,
					new DialogInterface.OnClickListener() {/* (non-Javadoc)
					 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
					 */
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						String etEditstr=etEditText.getText().toString();
						changename(etEditstr,position);
					}
				
			}).setNegativeButton(R.string.CANCEL,null).show();
			valueDialog.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					touch_flag=0;
				}
			});			

			}


		}
//		class PListener implements OnTouchListener {
//			private  EditText myEt;
//			private AlertDialog valueDialog;
//			int touch_flag=0;
//
//			PListener(EditText et) {
//				myEt = et;
//			}
//
//			/* (non-Javadoc)
//			 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
//			 */
//			@Override
//			public boolean onTouch(final View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				touch_flag++;
//				Log.e("mpeng"," the touch is "+touch_flag);
//				if(touch_flag==2)
//				{
//				final EditText etEditText = new EditText(getActivity());
//				// ����ֻ������0~9�����ֺ͵��
//				//�趨λ�ã�������������������
//				switch(v.getId())
//				{
//				case R.id.speed:
//					InputFilter[] filters = {new InputFilter.LengthFilter(2)};				
//					etEditText.setFilters(filters);					
//					etEditText.setHint("֧�ָ�ʽΪ1-10����");
//					etEditText.setKeyListener(new NumberKeyListener() {
//						@Override
//						protected char[] getAcceptedChars() {
//							return new char[] { '1', '2', '3', '4', '5', '6','7', '8', '9', '0'};
//						}
//						
//						@Override
//						public int getInputType() {
//							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
//						}
//					});
//					break;
//				case R.id.aspeed:
//				case R.id.dspeed:
//					InputFilter[] filters1 = {new InputFilter.LengthFilter(1)};				
//					etEditText.setFilters(filters1);					
//					etEditText.setHint("֧�ָ�ʽΪ1-5����");
//					etEditText.setKeyListener(new NumberKeyListener() {
//						@Override
//						protected char[] getAcceptedChars() {
//							return new char[] { '1', '2', '3', '4', '5'};
//						}
//						
//						@Override
//						public int getInputType() {
//							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
//						}
//					});
//					break;
//				case R.id.noteTxt:
//					InputFilter[] filters2 = {new InputFilter.LengthFilter(24)};				
//					etEditText.setFilters(filters2);					
//					etEditText.setHint("�������ź���");
//					break;
//				default:
//					InputFilter[] filters11 = {new InputFilter.LengthFilter(7)};				
//					etEditText.setFilters(filters11);	
//					etEditText.setHint("֧�ָ�ʽΪ#####.#�����������������5λ��С�����1λ");
//					etEditText.setKeyListener(new NumberKeyListener() {
//						@Override
//						protected char[] getAcceptedChars() {
//							return new char[] { '1', '2', '3', '4', '5', '6', '7',
//									'8', '9', '0', '.'  ,'+','-'};
//						}
//
//						@Override
//						public int getInputType() {
//							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
//						}
//					});
//					break;
//				}	
//				// ��ʼ���������������趨ֵ
//				TextView t = (TextView) v;
//				String valueString = t.getText().toString();// �趨λ��
//				etEditText.setText(valueString);
//				etEditText.setSelection(valueString.length());// ���ù��λ��
//				valueDialog = new AlertDialog.Builder(getActivity())
//				.setTitle("�������趨����")
//				.setView(etEditText)
//				.setPositiveButton(R.string.OK,
//						new DialogInterface.OnClickListener() {/* (non-Javadoc)
//						 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
//						 */
//						@Override
//						public void onClick(DialogInterface dialog,
//								int which) {
//							// TODO Auto-generated method stub
//							String etEditstr=etEditText.getText().toString();
//							switch(v.getId())
//							{
//							case R.id.noteTxt:
//								Iterator<HashMap<String, Object>> it = ArrayListBound.getDevicePositionListData().iterator();
//								while (it.hasNext()) {
//									HashMap<String, Object> map = it.next();
//									if (map.get("symbolNameEditText").toString().equals("")) {
//										continue;
//									}
//									if (map.get("symbolNameEditText").toString().equalsIgnoreCase(curPosition.getSelectedItem().toString().trim())) {
//										map.put("signalNameEditText", etEditstr);
//										break;
//									}
//								}
//								break;
//							}
//							myEt.setText(etEditstr);
//						}
//					
//				}).setNegativeButton(R.string.CANCEL,null).show();
//				valueDialog.setOnDismissListener(new OnDismissListener() {
//					
//					@Override
//					public void onDismiss(DialogInterface dialog) {
//						// TODO Auto-generated method stub
//						touch_flag=0;
//					}
//				});
//			}
//				
//				return false;
//			}
//
//			}
		
	}
	/**
	 * ���format�����ĺ�����������Զ���λ��0
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
		Iterator<HashMap<String,Object>> iterator = nc3ListBackup.iterator();
        nc3List.removeAll(nc3List);
        while (iterator.hasNext()) {         	
        		nc3List.add((HashMap<String, Object>) iterator.next().clone());   
		 }
	}
	 
	private void readFileOnLine(){  
	 try {
	 	String strFileName = "Filename.txt";  
//		String strFileName = "ncdata.trt"; 
	 	FileInputStream fis = new  FileInputStream(Constans.trPATH+strFileName);  
	 	StringBuffer sBuffer = new StringBuffer();  
	 	DataInputStream dataIO = new DataInputStream(fis);  
	 	String strLine = null; 	 	
	 	while((strLine =  dataIO.readLine()) != null) {  
	 	    sBuffer.append(strLine + "\n");  
	 	   Log.e("mpeng"," str line is 000000000 "+strLine);
	 	    if(strLine.startsWith("QD"))
	 	    { 	    	 
	 	        temp0 = strLine.split("\\|"); 	     
	 	    }else if(strLine.startsWith("ZJ"))
	 	    {
	 	    	temp1 = strLine.split("\\|");  
	 	    }
	 	    else if(strLine.startsWith("ZSJXH"))
		    {
	 	    	temp2 = strLine.split("\\|");  
		    }
	 	    else if(strLine.startsWith("DD"))
		    {
	 	    	temp3 = strLine.split("\\|");  
		    }
	 	}  
	 	dataIO.close();  
	 	fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	 
		
	}  
	private ArrayList<HashMap<String, Object>>  LoadIOList(String name)
	{
		if(name.equals("A"))
		{
			return ArrayListBound.getDeviceActualOutputListData();
			//return ArrayListBound.getDeviceActualInputListData();
		}else if(name.equals("B"))
		{
			orderTemp = "OUT";
			return ArrayListBound.getDeviceActualOutputListData();
		}else if(name.equals("C"))
		{
			return ArrayListBound.getDeviceActualOutputListData();
//			return ArrayListBound.getDeviceMiddleInputListData();
		}else if(name.equals("D"))
		{
			return ArrayListBound.getDeviceActualOutputListData();
//			return ArrayListBound.getDeviceMiddleOutputListData();
		}
		return ArrayListBound.getDeviceActualOutputListData();
	}
	
}

