package com.tr.newpragram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.KeyCodeSend;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.dbutils.ArrayListBound;
import com.explain.HexDecoding;
import com.explain.NCTranslate;
import com.explain.TableToBinary;
import com.tr.R;
import com.tr.programming.Config;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.WifiSetting_Info;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ����Fragment
 * @author mining
 *
 */

public class FragmentDetail extends Fragment {
	private final String TAG = "FragmentDetail";
	private RadioGroup genderGroup = null ;
	private RadioButton PositonBtn = null;
	private RadioButton TimerBtn = null;
	private RadioButton CounterBtn = null;	
	private Button DetailBtn = null;
	private ListView DetailList = null;
	private int pos = -1;
	private LinearLayout Positionlayout ;
	private LinearLayout Counterlayout ;
	private LinearLayout Timerlayout ;
	private WifiReadDataFormat formatReadMessage;	
	//private TableAdapter PostionAdapter ;
	private CounterAdapter myCounteerAdapter;
	private TimerAdapter myTimerAdapter;

	public static ArrayList<HashMap<String, Object>> P_posList_setting = new ArrayList<HashMap<String, Object>>();// ����Ҫ�󶨵�list
	public static MyAdapter P_posList_Adapter = null;
	private View tempV;
	private int tempposition;
	private String tempeditString;
	private static int selectedItem = -1;
	private static int selectedID = -1;
	private static String selectedKey = "foot_p_setting";
	private ArrayList<String> allpositionList_setting = new ArrayList<String>();//���λ��
	//������ͨѶ��ر�������ɾ��
	private WifiSendDataFormat formatSendMessage;
	private SendDataRunnable sendDataRunnable;
	private FinishRunnable sendDataFinishRunnable;	
	
	private String whichBtnChecked  = null;
	private TextView name_all_setting;
	private TextView foot_all_setting;
	private TextView productBA_all_setting;
	private TextView productUD_all_setting;
	private TextView feedertroughBA_all_setting;
	private TextView feedertroughUD_all_setting;
	private int index = 0;
	
	private byte[] getData;
	ArrayList<Integer> toaddress=new ArrayList<Integer>();
	ArrayList<Integer> tolength=new ArrayList<Integer>();
	int posindex=0;
	private boolean isComeFromList = false;
	private String TimerName = "";
	private ArrayList<HashMap<String, Object>> TimerList_setting = new ArrayList<HashMap<String, Object>>();
	private ArrayList<HashMap<String, Object>> PresetCounterList_setting = new ArrayList<HashMap<String, Object>>();
	
	private ArrayList<String> list_pname=new ArrayList<String>();
	private ArrayList<String> list_timername=new ArrayList<String>();
	private ArrayList<String> list_countername=new ArrayList<String>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_detail_layout, container, false);
		
	}

	 public static void setListViewHeightBasedOnChildren(ListView listView) {  
		 MyAdapter listAdapter = (MyAdapter) listView.getAdapter();   
	        if (listAdapter == null) {  
	            // pre-condition  
	            return;  
	        }  
	  
	        int totalHeight = 0;  
	        for (int i = 0; i < listAdapter.getCount(); i++) {  
	            View listItem = listAdapter.getView(i, null, listView);  
	            listItem.measure(0, 0);  
	            totalHeight += listItem.getMeasuredHeight();  
	        }  
	  
	        ViewGroup.LayoutParams params = listView.getLayoutParams();  
	        Log.e("mpeng","totalHeight :"+totalHeight+"listView.getDividerHeight()"+listView.getDividerHeight());
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	        listView.setLayoutParams(params);  
	        Log.e("mpeng","11111111111111 params.height:" +params.height);
	    }  


	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		whichBtnChecked = bundle.getString("changeStatus");
		initData();
		P_posList_setting.clear();
		toaddress.clear();
		tolength.clear();
		posindex=0;
		/*allpositionList_setting.clear();
		ArrayList<HashMap<String, Object>> it = ArrayListBound.getDevicePositionListData();
		for(int i=0;i<list_pname.size();i++){
			for(int j=0;j<it.size();j++){
			HashMap<String, Object> map = it.get(j);
			if (map.get("symbolNameEditText").toString().equalsIgnoreCase(list_pname.get(i))) {
				allpositionList_setting.add(list_pname.get(i)+" "+map.get("signalNameEditText").toString());
				System.out.println(list_pname.get(i)+" "+map.get("signalNameEditText").toString());
				break;
			}
		    }
		}*/
		
//		for(int i=0;i<allpositionList_setting.size();i++){
//			Log.e("peng","size :         "+allpositionList_setting.get(i));
//			HashMap<String, Object> map1 = new HashMap<String, Object>();
//			map1.put("name_p_setting","");
//			map1.put("foot_p_setting", "");
//			map1.put("productBA_p_setting","");
//			map1.put("productUD_p_setting", "");
//			map1.put("feedertroughBA_p_setting","");
//			map1.put("feedertroughUD_p_setting", "");
//			P_posList_setting.add( map1);
//		}
		for(int i=0;i<allpositionList_setting.size();i++){
			if(allpositionList_setting.get(i).contains("SP")){
				int send_SPaddress=AddressPublic.model_SP_point_Head
						+(AddressPublic.model_FP_point_Head-AddressPublic.model_SP_point_Head)/Define.MAX_STDPACK_NUM
						*(Integer.parseInt((allpositionList_setting.get(i).toString().split("\\s++")[0]).trim().substring(2))-1);				
				toaddress.add(send_SPaddress);
				tolength.add(20);
			}else if(allpositionList_setting.get(i).contains("FP")){
				String devicename=allpositionList_setting.get(i).toString().split("\\s++")[0];
				devicename=devicename.trim();
				int send_FPaddress=0;
				if(devicename.equalsIgnoreCase("FP001")){
					send_FPaddress = 0x20003AB0;
				}else if(devicename.equalsIgnoreCase("FP002")){
					send_FPaddress = 0x20003AC6;
				}
				toaddress.add(send_FPaddress);
				tolength.add(16);
			}else if(allpositionList_setting.get(i).contains("P")){
				int send_Paddress=AddressPublic.model_P_point_Head
						+(AddressPublic.model_SP_point_Head-AddressPublic.model_P_point_Head)/Define.MAX_STDPOINT_NUM
						*(Integer.parseInt((allpositionList_setting.get(i).toString().split("\\s++")[0]).trim().substring(1))-1);
				toaddress.add(send_Paddress);
				tolength.add(20);				
			}else{
				
			}
		}
		if (WifiSetting_Info.mClient == null) {
			Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
			return;
		}
		if(toaddress.size()>0)
			tosd();
		Log.e("mpeng","toaddress.size()==="+toaddress.size()+"    tolength.size()==="+tolength.size());
	}

	class PListener111 implements OnClickListener {
		private AlertDialog valueDialog;
		int touch_flag=0;

		PListener111() {
			
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
			
//			final int position  = findPositionByName(name);		
//			String valueString = DevicePositionList.get(position).get("signalNameEditText").toString();
			
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
						//changename(etEditstr,position);
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
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try{

		name_all_setting=(TextView) getActivity().findViewById(R.id.name_all_setting);
		foot_all_setting=(TextView) getActivity().findViewById(R.id.foot_all_setting);
		productBA_all_setting=(TextView) getActivity().findViewById(R.id.productBA_all_setting);
		productUD_all_setting=(TextView) getActivity().findViewById(R.id.productUD_all_setting);
		feedertroughBA_all_setting=(TextView) getActivity().findViewById(R.id.feedertroughBA_all_setting);
		feedertroughUD_all_setting=(TextView) getActivity().findViewById(R.id.feedertroughUD_all_setting);			
		name_all_setting.setText("��ǰλ��(mm)");
		Positionlayout = (LinearLayout) getActivity().findViewById(R.id.positiontitle);		
		Counterlayout = (LinearLayout) getActivity().findViewById(R.id.countertitle);		
		Timerlayout = (LinearLayout) getActivity().findViewById(R.id.timertitle);		
		Positionlayout.setVisibility(View.GONE) ;
		Counterlayout.setVisibility(View.GONE) ;
		Timerlayout.setVisibility(View.GONE) ;
//		final ScrollView sv = (ScrollView) getActivity().findViewById(R.id.scrollView1);
		getActivity().findViewById(R.id.detail_container);		
		DetailList = (ListView) getActivity().findViewById(R.id.fragment_detail);
//		Button test = (Button) getActivity().findViewById(R.id.test);
//		test.setOnClickListener(new PListener111());
//		DetailList.setOnTouchListener(new OnTouchListener() {
//			 
//			 public boolean onTouch(View v, MotionEvent event) {
//			 if (event.getAction() == MotionEvent.ACTION_MOVE) {
//			 sv.requestDisallowInterceptTouchEvent(true);
//			   }
//			   return false;
//			 }
//			});
			
		//��ȡ�б������Ĳ���
		
		//Radio Group
	
		genderGroup = (RadioGroup)getActivity().findViewById(R.id.genderGroup);
		
		PositonBtn = (RadioButton) getActivity().findViewById(R.id.positionbutton);
		TimerBtn = (RadioButton) getActivity().findViewById(R.id.timerbutton);
		CounterBtn = (RadioButton) getActivity().findViewById(R.id.counterbutton);
	    DetailBtn = (Button)getActivity().findViewById(R.id.detailbutton);
	    DetailBtn.setClickable(false);
	    DetailBtn.setOnClickListener(DetailBtnListener); 
		Log.e("mpeng","the which button is "+whichBtnChecked);
		genderGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(group.getCheckedRadioButtonId())
				{
					case R.id.positionbutton:
						Log.d(TAG," positionbutton");
						break;
					case R.id.timerbutton:
						Log.d(TAG," timerbutton ");						
						break;
					case R.id.counterbutton:
						Log.d(TAG,"counterbutton ");						
						break;
					default:
						break;
				}
				updateListView();
			}
		});	
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentList.setIsDetailFragment(true);
		if(whichBtnChecked.equals("isPosition"))
		{
			Log.e("mpeng","the which button is 1111 "+whichBtnChecked);
			PositonBtn.setChecked(true);	
			
			NewPragramActivity.PosccalmRunnable.setTextView(foot_all_setting,productBA_all_setting,productUD_all_setting,
					feedertroughBA_all_setting,feedertroughUD_all_setting);
		
		}
		else if(whichBtnChecked.equals("iscounter"))
		{
			CounterBtn.setChecked(true);
			Log.e("mpeng","the which button is 222222 "+whichBtnChecked);
//			genderGroup.check(R.id.counterbutton);
		}
		else if (whichBtnChecked.equals("istimer"))
		{
			TimerBtn.setChecked(true);
			Log.e("mpeng","the which button is 3333 "+whichBtnChecked);
//			genderGroup.check(R.id.timerbutton);
		}
	}

	private void backMessageToDo1(View v, String editString,int position,String keyString[],ArrayList<HashMap<String, Object>> mAppList) {
	       try{
			HashMap<String, Object> map = new HashMap<String, Object>();

			switch (v.getId()) {
			case R.id.foot_p_setting:
				map.put("name_p_setting",
						mAppList.get(position).get(keyString[0]).toString());
				map.put("foot_p_setting", editString);
				map.put("productBA_p_setting",
						mAppList.get(position).get(keyString[2]).toString());
				map.put("productUD_p_setting",
						mAppList.get(position).get(keyString[3]).toString());
				map.put("feedertroughBA_p_setting", mAppList.get(position)
						.get(keyString[4]).toString());
				map.put("feedertroughUD_p_setting", mAppList.get(position)
						.get(keyString[5]).toString());

				mAppList.set(position, map);
				break;
			case R.id.productBA_p_setting:
				map.put("name_p_setting",
						mAppList.get(position).get(keyString[0]).toString());
				map.put("foot_p_setting",
						mAppList.get(position).get(keyString[1]).toString());
				map.put("productBA_p_setting", editString);
				map.put("productUD_p_setting",
						mAppList.get(position).get(keyString[3]).toString());
				map.put("feedertroughBA_p_setting", mAppList.get(position)
						.get(keyString[4]).toString());
				map.put("feedertroughUD_p_setting", mAppList.get(position)
						.get(keyString[5]).toString());

				mAppList.set(position, map);
				break;
			case R.id.productUD_p_setting:
				map.put("name_p_setting",
						mAppList.get(position).get(keyString[0]).toString());
				map.put("foot_p_setting",
						mAppList.get(position).get(keyString[1]).toString());
				map.put("productBA_p_setting",
						mAppList.get(position).get(keyString[2]).toString());
				map.put("productUD_p_setting", editString);
				map.put("feedertroughBA_p_setting", mAppList.get(position)
						.get(keyString[4]).toString());
				map.put("feedertroughUD_p_setting", mAppList.get(position)
						.get(keyString[5]).toString());

				mAppList.set(position, map);
				break;
			case R.id.feedertroughBA_p_setting:
				map.put("name_p_setting",
						mAppList.get(position).get(keyString[0]).toString());
				map.put("foot_p_setting",
						mAppList.get(position).get(keyString[1]).toString());
				map.put("productBA_p_setting",
						mAppList.get(position).get(keyString[2]).toString());
				map.put("productUD_p_setting",
						mAppList.get(position).get(keyString[3]).toString());
				map.put("feedertroughBA_p_setting", editString);
				map.put("feedertroughUD_p_setting", mAppList.get(position)
						.get(keyString[5]).toString());

				mAppList.set(position, map);
				break;
			case R.id.feedertroughUD_p_setting:
				map.put("name_p_setting",
						mAppList.get(position).get(keyString[0]).toString());
				map.put("foot_p_setting",
						mAppList.get(position).get(keyString[1]).toString());
				map.put("productBA_p_setting",
						mAppList.get(position).get(keyString[2]).toString());
				map.put("productUD_p_setting",
						mAppList.get(position).get(keyString[3]).toString());
				map.put("feedertroughBA_p_setting", mAppList.get(position)
						.get(keyString[4]).toString());
				map.put("feedertroughUD_p_setting", editString);

				mAppList.set(position, map);
				break;

			default:
				break;
			}
			P_posList_Adapter.notifyDataSetChanged();
		}catch(Exception e){
			e.printStackTrace();
		}
		}

		private final Runnable backMessageToDo1 = new Runnable(){
			@Override
			public void run() {	
				backMessageToDo1(tempV,tempeditString,tempposition,P_posList_Adapter.keyString,P_posList_Adapter.mAppList);
				//Toast.makeText(getActivity(), "���ݷ������", Toast.LENGTH_LONG).show();
			}
		};
	
		private void tosd(){
		try{
			Log.e("mpeng","tosd 11111111111");
			formatReadMessage=new WifiReadDataFormat(toaddress.get(0),tolength.get(0));

				sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

				sendDataFinishRunnable=new FinishRunnable(getActivity(),backMessageTodo);

				sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

				getActivity().runOnUiThread(sendDataRunnable);				
		}catch(Exception e){
			if(WifiSetting_Info.mClient!=null){
			  //P_posList_setting.remove(posindex-2);
			  //P_posList_Adapter.notifyDataSetChanged();
			}
			e.printStackTrace();
		}
	}
	private Runnable backMessageTodo=new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// ���صı�־λSTS1�����ж��Լ���У��
			 try{
					getData=new byte[formatReadMessage.getLength()];
					//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
					if( formatReadMessage.getFinalData() != null) 
					{
						System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
						
						System.out.println("posindex======"+posindex);
						if(allpositionList_setting.get(posindex).contains("SP")){
							HashMap<String, Object> map1 = new HashMap<String, Object>();
							map1.put("name_p_setting",allpositionList_setting.get(posindex));
							Double foot_p=Double.valueOf(HexDecoding.Array4Toint(getData, 0))/100;
							Double productBA_p=Double.valueOf(HexDecoding.Array4Toint(getData, 4))/100;
							Double productUD_p=Double.valueOf(HexDecoding.Array4Toint(getData, 12))/100;
							Double feedertroughBA_p=Double.valueOf(HexDecoding.Array4Toint(getData, 8))/100;
							Double feedertroughUD_p=Double.valueOf(HexDecoding.Array4Toint(getData, 16))/100;
							
							if(foot_p>99999.9){
								map1.put("foot_p_setting", "*****.*");
							}else{
								map1.put("foot_p_setting", String.format("%1$5.1f",foot_p));
							}
							
							if(productBA_p>99999.9){
								map1.put("productBA_p_setting", "*****.*");
							}else{
								map1.put("productBA_p_setting", String.format("%1$5.1f",productBA_p));
							}
							
							if(productUD_p>99999.9){
								map1.put("productUD_p_setting", "*****.*");
							}else{
								map1.put("productUD_p_setting", String.format("%1$5.1f",productUD_p));
							}
							
							if(feedertroughBA_p>99999.9){
								map1.put("feedertroughBA_p_setting", "*****.*");
							}else{
								map1.put("feedertroughBA_p_setting", String.format("%1$5.1f",feedertroughBA_p));
							}
							
							if(feedertroughUD_p>99999.9){
								map1.put("feedertroughUD_p_setting", "*****.*");
							}else{
								map1.put("feedertroughUD_p_setting", String.format("%1$5.1f",feedertroughUD_p));
							}
							P_posList_setting.add( map1);
						}else if(allpositionList_setting.get(posindex).contains("FP")){
							HashMap<String, Object> map1 = new HashMap<String, Object>();
							map1.put("name_p_setting",allpositionList_setting.get(posindex));
							map1.put("foot_p_setting", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(getData,0))/100)));
							map1.put("productBA_p_setting",String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(getData,2))/100)));
							map1.put("productUD_p_setting", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(getData,6))/100)));
							map1.put("feedertroughBA_p_setting",String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(getData,4))/100)));
							map1.put("feedertroughUD_p_setting", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(getData,8))/100)));
							P_posList_setting.add(map1);
						}else if(allpositionList_setting.get(posindex).contains("P")){
							HashMap<String, Object> map1 = new HashMap<String, Object>();
							map1.put("name_p_setting", allpositionList_setting.get(posindex));
							Double foot_p=Double.valueOf(HexDecoding.Array4Toint(getData, 0))/100;
							Double productBA_p=Double.valueOf(HexDecoding.Array4Toint(getData, 4))/100;
							Double productUD_p=Double.valueOf(HexDecoding.Array4Toint(getData, 12))/100;
							Double feedertroughBA_p=Double.valueOf(HexDecoding.Array4Toint(getData, 8))/100;
							Double feedertroughUD_p=Double.valueOf(HexDecoding.Array4Toint(getData, 16))/100;
							if(foot_p>99999.9){
								map1.put("foot_p_setting", "*****.*");
							}else{
								map1.put("foot_p_setting", String.format("%1$5.1f",foot_p));
							}
							
							if(productBA_p>99999.9){
								map1.put("productBA_p_setting", "*****.*");
							}else{
								map1.put("productBA_p_setting", String.format("%1$5.1f",productBA_p));
							}
							
							if(productUD_p>99999.9){
								map1.put("productUD_p_setting", "*****.*");
							}else{
								map1.put("productUD_p_setting", String.format("%1$5.1f",productUD_p));
							}
							
							if(feedertroughBA_p>99999.9){
								map1.put("feedertroughBA_p_setting", "*****.*");
							}else{
								map1.put("feedertroughBA_p_setting", String.format("%1$5.1f",feedertroughBA_p));
							}
							
							if(feedertroughUD_p>99999.9){
								map1.put("feedertroughUD_p_setting", "*****.*");
							}else{
								map1.put("feedertroughUD_p_setting", String.format("%1$5.1f",feedertroughUD_p));
							}
							P_posList_setting.add(map1);
						}
					
						++posindex;
					
					}
			 toaddress.remove(0);
			 tolength.remove(0);
			 if(toaddress.size()>0){
				 index++; //add by mpeng
				 tosd(); 
			 }else{
				 index = 0;
				 Log.e("mpeng","P_posList_settingP_posList_settingP_posList_settingsize is"+P_posList_setting.size());
					P_posList_Adapter.notifyDataSetChanged();
//				 if(PositonBtn.isChecked())
//					 setListViewHeightBasedOnChildren(DetailList);
				 Toast.makeText(getActivity(), "���ݶ�ȡ���", Toast.LENGTH_SHORT).show();
			 }
			
			 }catch(Exception e){
					e.printStackTrace();
				}
		}
	};
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	private void initialTimerList(byte[] getData) {
		try{
			TimerList_setting.clear();
		 ArrayList<HashMap<String, Object>> timerlists=ArrayListBound.getDeviceTimerListData();
		 byte[] temp=new byte[4];
			for (int i = 0; i < timerlists.size(); i++) {
				String timerNameString =timerlists.get(i).get("symbolNameEditText").toString().trim();
				if(!list_timername.contains(timerNameString)){					
					continue;
				}
				if (!timerNameString.equals("")){	
					System.arraycopy(getData, i*4, temp, 0, 4);
					HashMap<String, Object> map0 = new HashMap<String, Object>();
					map0.put("numText", timerNameString+" "+timerlists.get(i).get("signalNameEditText").toString().trim());
					map0.put("value", String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(temp)))/100));
										
					TimerList_setting.add(map0);
				}
			}
			if (TimerList_setting.size() == 0) {
				Toast.makeText(getActivity(), "û�ж�ʱ��������Ϊ��", Toast.LENGTH_SHORT).show();
			}
	}catch(Exception e){
		e.printStackTrace();
	}

	}
	//��ȡ���ص����ݺ���е�UI����
		private final Runnable readDataFinishTodo = new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//���ڷ��ص�36�ֽ�

				getData=new byte[formatReadMessage.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				if( formatReadMessage.getFinalData() != null)  
				{
					System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
		
					if( getActivity() != null)  
					{
								//initialTimerList(TimerList_setting, timernoteList_setting,getData);// ���Կ��Ƿŵ����������spinnerfragment��ȥ�������Ͳ���ÿ��ˢ��
								initialTimerList(getData);
								
								if(isComeFromList)
								{
									
									for(int i =0;i<TimerList_setting.size();i++)
									{
										Log.e("mpeng","the TimerList_setting is"+TimerList_setting.get(i).toString());
										if(TimerName.equals(TimerList_setting.get(i).get("numText").toString().trim()))
										{
											Log.e("mpeng"," the index is "+i);
											myTimerAdapter = new TimerAdapter(getActivity(), TimerList_setting, 
													new String []{"numText", "value"});
										    DetailList.setAdapter(myTimerAdapter);
											myTimerAdapter.setSelectItem(i);
											DetailList.setSelection(i);
											isComeFromList = false;
											TimerName = "";
											break;
										}
									}
								}
								Log.e("mpeng","aaaaaaaaaaaaaaaaaaaaaaaa");
								myTimerAdapter.notifyDataSetChanged();
							
						
					}
				}
				


			}

		};
private void updateListView()
{
	if(PositonBtn.isChecked()){	  
		Log.e("mpeng","aaaaaaaaaaaaaa P_posList_setting size is"+P_posList_setting.size());
		P_posList_Adapter = new MyAdapter(getActivity(), P_posList_setting,
				R.layout.position_list_item, new String[] { "name_p_setting",
			"foot_p_setting", "productBA_p_setting",
			"productUD_p_setting", "feedertroughBA_p_setting",
		"feedertroughUD_p_setting" }, new int[] {
			R.id.name_p_setting, R.id.foot_p_setting,
			R.id.productBA_p_setting, R.id.productUD_p_setting,
			R.id.feedertroughBA_p_setting,
			R.id.feedertroughUD_p_setting });
		DetailList.setAdapter(P_posList_Adapter);
//		setListViewHeightBasedOnChildren(DetailList);
		P_posList_Adapter.notifyDataSetChanged();
		
		DetailBtn.setVisibility(View.VISIBLE);
		Positionlayout.setVisibility(View.VISIBLE) ;
		Counterlayout.setVisibility(View.GONE) ;
		Timerlayout.setVisibility(View.GONE) ;
		DetailList.setClickable(true);
		DetailList.setItemsCanFocus(true);
		DetailList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				  pos = arg2;
				  P_posList_Adapter.setSelectItem(pos);
				  P_posList_Adapter.notifyDataSetChanged();
				  BroadToHighLight(allpositionList_setting.get(pos).toString());	
				  
			}
		});
		
		if (WifiSetting_Info.mClient == null) {
			Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
			return;
		}
		if(toaddress.size()>0)
			tosd();
	
	}else if(TimerBtn.isChecked()){
		Log.d(TAG,"updateListView TimerBtn");
		 myTimerAdapter = new TimerAdapter(getActivity(), TimerList_setting, 
				new String []{"numText", "value"});
		DetailList.setAdapter(myTimerAdapter);
		Positionlayout.setVisibility(View.GONE) ;
		Counterlayout.setVisibility(View.GONE) ;
		Timerlayout.setVisibility(View.VISIBLE) ;
		DetailBtn.setVisibility(View.GONE);
		DetailList.setClickable(true);
		DetailList.setItemsCanFocus(true);
		DetailList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				  pos = arg2;
				  myTimerAdapter.setSelectItem(pos);
				  myTimerAdapter.notifyDataSetChanged();
				  BroadToHighLight(TimerList_setting.get(pos).get("numText").toString());			
			}
		});
		
			
			if (WifiSetting_Info.mClient == null) {
				Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
				return;
			}
	    	//����λ��ͨ�ţ���ȡ��ʼ��listview�����ݣ�һ�ζ�ȡ36���ֽڣ�36���ֽ����ڸ���listview
			formatReadMessage=new WifiReadDataFormat(AddressPublic.model_Timer_Head, Define.MAX_TIMER_NUM*4);

			try {

				sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

				sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",readDataFinishTodo);

				sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

				getActivity().runOnUiThread(sendDataRunnable);

			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
			}
	}else if(CounterBtn.isChecked()){
		Log.d(TAG,"updateListView CounterBtn");
		myCounteerAdapter = new CounterAdapter(getActivity(),PresetCounterList_setting, 
				new String []{"numText", "value"});
		DetailList.setAdapter(myCounteerAdapter);
		Positionlayout.setVisibility(View.GONE) ;
		Counterlayout.setVisibility(View.VISIBLE) ;
		Timerlayout.setVisibility(View.GONE) ;
		DetailBtn.setVisibility(View.GONE);
		DetailList.setClickable(true);
		DetailList.setItemsCanFocus(true);
		DetailList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				  pos = arg2;
				  myCounteerAdapter.setSelectItem(pos);
				  myCounteerAdapter.notifyDataSetChanged();
				 // BroadToHighLight(Config.list_countername.get(pos).toString());

			}
		});
	}
	
}

OnClickListener DetailBtnListener = new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent IT = new Intent();
	    Bundle bundle = new Bundle();
	    Log.d("mpeng","the pos is "+pos);
	    //bundle.putInt("pos",pos);    // ��ǰ�����λ��
	    if(pos<0){
	    	Toast.makeText(getActivity(), "��ѡ��λ��", Toast.LENGTH_SHORT).show();
	    	return;
	    }
	    String name = allpositionList_setting.get(pos).toString().trim();
	    bundle.putString("PosType", name);//��ǰ��P SP.FP
	    /*if(name.equals("P"))
	    	bundle.putString("PosType", "PLIST");//��ǰ��P SP.FP
	    else if(name.equals("S"))
	    	bundle.putString("PosType", "SPLIST");//��ǰ��P SP.FP
	    else if(name.equals("F"))
	    	bundle.putString("PosType", "FPLIST");//��ǰ��P SP.FP
*/	    
	    IT.putExtras(bundle);
	    IT.setAction("gotoDetailSetting");
		getActivity().sendBroadcast(IT);
	}
};
	
private void BroadToHighLight(String str)
{
	Intent IT = new Intent();
    IT.setAction("highlight");
    IT.putExtra("name", str);
	getActivity().sendBroadcast(IT);

}

private void initData()
{
	allpositionList_setting.clear();
	list_pname.clear();
	list_timername.clear();
	list_countername.clear();
	Config.pspfpaxle=new byte[200];
	
	char[] chs={};
	char ch=' ';
	int allA=0;
	try {
		ArrayList<HashMap<String, Object>> List = ArrayListBound.getNCeditList3Data();
		ArrayList<HashMap<String, Object>> it = ArrayListBound.getDevicePositionListData();
		
		for(int i=0;i<List.size();i++){
			String order_MOVE=List.get(i).get("orderSpinner").toString();
			String operatstr=List.get(i).get("operatText").toString();
			if((order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP"))&&operatstr.contains("SP")){
				String posstr=operatstr.substring(operatstr.indexOf("S"), operatstr.length());
				if(!list_pname.contains(posstr)){
					list_pname.add(posstr);
					for(int j=0;j<it.size();j++){
						if (it.get(j).get("symbolNameEditText").toString().equalsIgnoreCase(posstr)) {
							allpositionList_setting.add(posstr+" "+it.get(j).get("signalNameEditText").toString());
							break;
						}
					}
				}
			}else if((order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP"))&&operatstr.contains("FP")){
				String posstr=operatstr.substring(operatstr.indexOf("F"), operatstr.length());
				if(!list_pname.contains(posstr)){
					list_pname.add(posstr);
					for(int j=0;j<it.size();j++){
						if (it.get(j).get("symbolNameEditText").toString().equalsIgnoreCase(posstr)) {
							allpositionList_setting.add(posstr+" "+it.get(j).get("signalNameEditText").toString());
							break;
						}
					}
				}
			}else if((order_MOVE.contains("MOVE")||order_MOVE.contains("MOVEP"))&&operatstr.contains("P")){
				String posstr=operatstr.substring(operatstr.indexOf("P"), operatstr.length());
				if(!list_pname.contains(posstr)){
					list_pname.add(posstr);
					for(int j=0;j<it.size();j++){
						if (it.get(j).get("symbolNameEditText").toString().equalsIgnoreCase(posstr)) {
							allpositionList_setting.add(posstr+" "+it.get(j).get("signalNameEditText").toString());
							break;
						}
					}
				}
				
				chs=operatstr.toCharArray();
				 ch=' ';
				 allA=0;
					for(int i1=0;i1<chs.length;i1++){
						if(chs[i1]=='A'){
							ch=chs[i1+1];
							int index=(int)ch-49;
							if((Config.pspfpaxle[Integer.parseInt(operatstr.substring(operatstr.indexOf("P")+1))-1]&(int)(Math.pow(2, index)))==(int)(Math.pow(2, index))){
								continue;
							}
							if(index>=0&&index<=4){
								allA=allA+(int) (Math.pow(2, index));
							}
						}else if(chs[i1]=='P'||chs[i1]=='p'){
							Config.pspfpaxle[Integer.parseInt(operatstr.substring(i1+1))-1]+=HexDecoding.int2byte(allA)[0];
							break;
						}
					}
				
			}else if(order_MOVE.contains("TWAIT")){
				if(!list_timername.contains(operatstr)){
					list_timername.add(operatstr);
				}
			}else if(order_MOVE.contains("CINC")||order_MOVE.contains("CDEC")||order_MOVE.contains("CCLR")||order_MOVE.contains("CSET")){
				operatstr=operatstr.split("=")[0];
				if(!list_countername.contains(operatstr)){
					list_countername.add(operatstr);
					}
					}
			
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}	

public void setRadioBtnCiclk(String name)
{
	isComeFromList = true;
	TimerName = name;
	if(!TimerBtn.isChecked())
	{
		TimerBtn.setChecked(true);
		PositonBtn.setChecked(false);		
		CounterBtn.setChecked(false);
	}	
	for(int i =0;i<TimerList_setting.size();i++)
	{
		Log.e("mpeng","the TimerList_setting is"+TimerList_setting.get(i).get("numText").toString()+"name:   "+name);
		if(TimerName.toString().trim().equals(TimerList_setting.get(i).get("numText").toString().trim()))
		{
			Log.e("mpeng"," the index is "+i);	
			 myTimerAdapter = new TimerAdapter(getActivity(), TimerList_setting, 
						new String []{"numText", "value"});
			DetailList.setAdapter(myTimerAdapter);
			myTimerAdapter.setSelectItem(i);			
			DetailList.setSelection(i);
			myTimerAdapter.notifyDataSetChanged();
			isComeFromList = false;
			TimerName = "";
			break;
		}
	}
}
/* (non-Javadoc)
 * @see android.app.Fragment#onPause()
 */
@Override
public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	Log.d("FragmentDetail"," on Pause !");
	NewPragramActivity.PosccalmRunnable.setTextView(null,null,null,null,null);
	PositonBtn.setChecked(false);
	TimerBtn.setChecked(false);	
	CounterBtn.setChecked(false);
	FragmentList.setIsDetailFragment(false);
}

public class MyAdapter extends BaseAdapter {
	private class buttonViewHolder {
		TextView name_p_setting;
		TextView foot_p_setting;
		TextView productBA_p_setting;
		TextView productUD_p_setting;
		TextView feedertroughBA_p_setting;
		TextView feedertroughUD_p_setting;

	}

	private ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private buttonViewHolder holder;
	private int mLayoutID;
	private int mselectedItem = -1;
	private int id = -1;
	private boolean isFromClick = false;

	// MyAdapter�Ĺ��캯��
	public MyAdapter(Context c, ArrayList<HashMap<String, Object>> appList,
			int layoutID, String[] from, int[] to) {
		mAppList = appList;
		mContext = c;
		mLayoutID = layoutID;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];
		valueViewID = new int[to.length];

		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
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
		mselectedItem = selectItem;

	}
	// ѡ����ɫ��ʾ
	public void setSelectItemBlue(int selectedItem, int id) {
		this.mselectedItem = selectedItem;
		this.id = id;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if (convertView != null) {
			holder = (buttonViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(mLayoutID, null);
			holder = new buttonViewHolder();
			holder.name_p_setting = (TextView) convertView
					.findViewById(valueViewID[0]);
			holder.foot_p_setting = (TextView) convertView
					.findViewById(valueViewID[1]);
			holder.productBA_p_setting = (TextView) convertView
					.findViewById(valueViewID[2]);
			holder.productUD_p_setting = (TextView) convertView
					.findViewById(valueViewID[3]);
			holder.feedertroughBA_p_setting = (TextView) convertView
					.findViewById(valueViewID[4]);
			holder.feedertroughUD_p_setting = (TextView) convertView
					.findViewById(valueViewID[5]);

			convertView.setTag(holder);
		}

		HashMap<String, Object> appInfo = mAppList.get(position);
		if (appInfo != null) {
			String name_p_setting =  appInfo.get(keyString[0]).toString();
			String foot_p_setting = appInfo.get(keyString[1]).toString();
			String productBA_p_setting = appInfo.get(keyString[2]).toString();
			String productUD_p_setting = appInfo.get(keyString[3]).toString();
			String feedertroughBA_p_setting =appInfo.get(keyString[4]).toString();
			String feedertroughUD_p_setting =  appInfo.get(keyString[5]).toString();

			holder.name_p_setting.setText(name_p_setting);
			holder.foot_p_setting.setText(foot_p_setting);
			holder.productBA_p_setting.setText(productBA_p_setting);
			holder.productUD_p_setting.setText(productUD_p_setting);
			holder.feedertroughBA_p_setting.setText(feedertroughBA_p_setting);
			holder.feedertroughUD_p_setting.setText(feedertroughUD_p_setting);
             /**
			 * �̰���Ӧ
			 */
			if(name_p_setting.startsWith("P")||name_p_setting.startsWith("p")){
				byte pspfpaxleFlagbyte=Config.pspfpaxle[Integer.parseInt(name_p_setting.substring(1,4))-1];
				if((pspfpaxleFlagbyte&0x01)==1){
					
					holder.foot_p_setting.setOnClickListener(new PListener(position));	
					if(isFromClick&&selectedItem== position&&holder.foot_p_setting.getId() ==selectedID )
						holder.foot_p_setting.setBackgroundColor(Color.BLUE);
					else
					holder.foot_p_setting.setBackgroundColor(Color.WHITE);		
					
				}else{
					holder.foot_p_setting.setOnClickListener(new GrayViewClickListener(position));	
					holder.foot_p_setting.setBackgroundColor(Color.GRAY);
				}
				
				if((pspfpaxleFlagbyte&0x02)==2){
					holder.productBA_p_setting.setOnClickListener(new PListener(position));
					if(isFromClick&&selectedItem== position&&holder.productBA_p_setting.getId() ==selectedID )
						holder.productBA_p_setting.setBackgroundColor(Color.BLUE);
					else
					holder.productBA_p_setting.setBackgroundColor(Color.WHITE);
				}else{
					holder.productBA_p_setting.setOnClickListener(new GrayViewClickListener(position));	
					holder.productBA_p_setting.setBackgroundColor(Color.GRAY);
				}
				
				if((pspfpaxleFlagbyte&0x08)==8){
					holder.productUD_p_setting.setOnClickListener(new PListener(position));
				    if(isFromClick&&selectedItem== position&&holder.productUD_p_setting.getId() ==selectedID )
						holder.productUD_p_setting.setBackgroundColor(Color.BLUE);
					else
						holder.productUD_p_setting.setBackgroundColor(Color.WHITE);
				}else{
					holder.productUD_p_setting.setOnClickListener(new GrayViewClickListener(position));	
					holder.productUD_p_setting.setBackgroundColor(Color.GRAY);
				}
			
				if((pspfpaxleFlagbyte&0x04)==4){
					holder.feedertroughBA_p_setting.setOnClickListener(new PListener(position));
					if(isFromClick&&selectedItem== position&&holder.feedertroughBA_p_setting.getId() ==selectedID )
						holder.feedertroughBA_p_setting.setBackgroundColor(Color.BLUE);
					else
					    holder.feedertroughBA_p_setting.setBackgroundColor(Color.WHITE);
				}else{
					holder.feedertroughBA_p_setting.setOnClickListener(new GrayViewClickListener(position));
					holder.feedertroughBA_p_setting.setBackgroundColor(Color.GRAY);
				}
			
				if((pspfpaxleFlagbyte&0x10)==16){
					holder.feedertroughUD_p_setting.setOnClickListener(new PListener(position));
					if(isFromClick&&selectedItem== position&&holder.feedertroughUD_p_setting.getId() ==selectedID )
						holder.feedertroughUD_p_setting.setBackgroundColor(Color.BLUE);
					else
						holder.feedertroughUD_p_setting.setBackgroundColor(Color.WHITE);
					
				}else{
					holder.feedertroughUD_p_setting.setOnClickListener(new GrayViewClickListener(position));
					holder.feedertroughUD_p_setting.setBackgroundColor(Color.GRAY);
				}
		
			holder.name_p_setting.setBackgroundColor(Color.GRAY);
		}else{
				holder.foot_p_setting.setOnClickListener(new PListener(position));
				holder.productBA_p_setting.setOnClickListener(new PListener(position));
				holder.productUD_p_setting.setOnClickListener(new PListener(position));
				holder.feedertroughBA_p_setting.setOnClickListener(new PListener(position));
				holder.feedertroughUD_p_setting.setOnClickListener(new PListener(position));

			
			/**
			 * ���ñ���ɫ������ѡ������ɫ��ʾ,������ѡ����
			 */
			holder.name_p_setting.setBackgroundColor(Color.GRAY);
				if (mselectedItem==position&&selectedID != -1) {
					convertView.findViewById(selectedID).setBackgroundColor(
							Color.BLUE);
					for (int i = 1; i < valueViewID.length; i++) {
						if (selectedID != valueViewID[i]) {
							convertView.findViewById(valueViewID[i])
							.setBackgroundColor(Color.WHITE);
						}
					}					
				} else {
					holder.foot_p_setting.setBackgroundColor(Color.WHITE);
					holder.productBA_p_setting
					.setBackgroundColor(Color.WHITE);
					holder.productUD_p_setting
					.setBackgroundColor(Color.WHITE);
					holder.feedertroughBA_p_setting
					.setBackgroundColor(Color.WHITE);
					holder.feedertroughUD_p_setting
					.setBackgroundColor(Color.WHITE);
					convertView.setBackgroundColor(Color.BLACK);
				}

			}
	
			if(mselectedItem == position)
			{
				
				convertView.setBackgroundColor(Color.RED);
			}
			else
			{
				
				convertView.setBackgroundColor(Color.BLACK);
			}

		}
		return convertView;
	}


	/**
	 * �̰���Ӧ�࣬ʵ�ָı䱳��ɫ���ı�seekbar��ֵ����
	 * 
	 * @author shea
	 * 
	 */

	class GrayViewClickListener implements OnClickListener{
			private int position;
			GrayViewClickListener(int pos){
				position = pos;
			}

		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			BroadToHighLight(allpositionList_setting.get(position).toString());
			setSelectItem(position);
			notifyDataSetChanged();
			
			
		}
		
	}
	
	class PListener implements OnClickListener {
		private  int position;

		PListener(int pos) {
			position = pos;
		}

		@Override
			public void onClick(final View v) {
			final EditText etEditText = new EditText(getActivity());
			isFromClick = true;		
			tempV=v;
			tempposition=position;
			setSelectItemBlue(position, v.getId());
			selectedItem = position;
			selectedID = v.getId();
			pos = position;
			BroadToHighLight(allpositionList_setting.get(position).toString());	
			notifyDataSetChanged();
			// ����ֻ������0~9�����ֺ͵��
			//�趨λ�ã�������������������
				etEditText.setHint("֧�ָ�ʽΪ#####.#�����������������5λ��С�����1λ");
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5', '6', '7',
								'8', '9', '0', '.' ,'+','-'};
					}

					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
					}
				});
			/**
			 * �жϵõ�map����Ҫʹ�õ�keyֵ
			 */
			String foot_String = "";
			switch (v.getId()) {
			case R.id.foot_p_setting:
				selectedKey = "foot_p_setting";
				//����keycode
//				KeyCodeSend.send(111, getActivity());
				foot_String =  mAppList.get(position).get(
						keyString[1]).toString();
				break;
			case R.id.productBA_p_setting:
				selectedKey = "productBA_p_setting";
//				KeyCodeSend.send(112, getActivity());
				foot_String =  mAppList.get(position).get(
						keyString[2]).toString();
				break;
			case R.id.productUD_p_setting:
				selectedKey = "productUD_p_setting";
//				KeyCodeSend.send(113, getActivity());
				foot_String =  mAppList.get(position).get(
						keyString[3]).toString();
				break;
			case R.id.feedertroughBA_p_setting:
				selectedKey = "feedertroughBA_p_setting";
//				KeyCodeSend.send(114, getActivity());
				foot_String =  mAppList.get(position).get(
						keyString[4]).toString();
				break;
			case R.id.feedertroughUD_p_setting:
				selectedKey = "feedertroughUD_p_setting";
//				KeyCodeSend.send(115, getActivity());
				foot_String =  mAppList.get(position).get(
						keyString[5]).toString();
				break;

			default:
				break;
			}
			// ��ʼ���������������趨ֵ
			TextView t = (TextView) v;
			String valueString = t.getText().toString();// �趨λ��
			

			etEditText.setText(foot_String);
			etEditText.setSelection(foot_String.length());// ���ù��λ��
			double foot_Doublez=0;
			try{
				foot_Doublez=Double.parseDouble(foot_String);
		     }catch(Exception e){
		    	foot_Doublez=0;
			 }
			final double foot_Double=foot_Doublez;
			new AlertDialog.Builder(getActivity())
			.setTitle("�������趨ֵ")
			.setView(etEditText)
			.setPositiveButton(R.string.OK,
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0,int arg1) {
					String editString = etEditText.getText().toString().trim();
					try{
					    Double.parseDouble(editString);
					}catch(Exception e){
						return;
					}
					double editDouble=0;

					if (!editString.equals("") ) {//��Ϊ���Ҳ��Ե�ſ�ʼ���������
                            //�趨λ��
							if (!editString.startsWith(".")) {
								if (editString.contains(".")) {
									String[] editStrings = editString.split("\\.");
									if (2 == editStrings.length) {
										editDouble = Double.parseDouble(editString);
										if(Config.StopState){
											if (Math.abs(editDouble) >99999.9) {
												Toast.makeText(getActivity(),"���ݳ�����Χ������������",Toast.LENGTH_SHORT).show();
												editString = "";
												return;
											} else {
												editString = String.format("%1$5.1f",editDouble);
											}
										}else{
											if (Math.abs(editDouble-foot_Double) >2.0) {
												Toast.makeText(getActivity(),"����δ����ֹͣ״̬�����ݳ���ԭλ�á�2.0mm������������",Toast.LENGTH_SHORT).show();
												editString = "";
												return;
											} else {
												editString = String.format("%1$5.1f",editDouble);
											}
										}
										
									} else {
										Toast.makeText(getActivity(),"���ݸ�ʽ��������������",Toast.LENGTH_SHORT).show();
										editString = "";
										return;
									}
								} else {
									editDouble = Double.parseDouble(editString);
									if(Config.StopState){
										if (Math.abs(editDouble) >99999.9) {
											Toast.makeText(getActivity(),"���ݳ�����Χ������������",Toast.LENGTH_SHORT).show();
											editString = "";
											return;
										} else {
											editString = String.format("%1$5.1f",editDouble);
										}
									}else{
										if (Math.abs(editDouble-foot_Double) >2.0) {
											Toast.makeText(getActivity(),"����δ����ֹͣ״̬�����ݳ���ԭλ�á�2.0mm������������",Toast.LENGTH_SHORT).show();
											editString = "";
											return;
										} else {
											editString = String.format("%1$5.1f",editDouble);
										}
									}
									
								}

							} else {
								Toast.makeText(getActivity(),"���ݸ�ʽ��������������",Toast.LENGTH_SHORT).show();
								editString = "";
								return;
							}


					} else {//����Ϊ��
						Toast.makeText(getActivity(),"����Ϊ�գ�����������",Toast.LENGTH_SHORT).show();
						editString = "";
						return;
					}
					String valueString=editString;
					
					// ÿ����д�����Ҫ�����ݷ��͸���λ��������ܸ���UI
					//���ݵ����Ϣȷ�����������е�λ�ã�ȷ�����͵�ַ
					String name_String =  mAppList.get(position).get(keyString[0]).toString();
					int send_address=0;
					//��ȡ�趨�ٶȣ����ٶȣ����ٶ���Ϣ,��д����
					byte[] temp=new byte[20];
					if(name_String.contains("SP")){
						send_address=AddressPublic.model_SP_point_Head
								+(AddressPublic.model_FP_point_Head-AddressPublic.model_SP_point_Head)/Define.MAX_STDPACK_NUM
								*(Integer.parseInt((name_String.split("\\s++")[0]).trim().substring(2))-1);
						//��ȡ�趨�ٶȣ����ٶȣ����ٶ���Ϣ,��д����
						 temp=new byte[20];
						 System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 0, 4);
					        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 4, 4);
					        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 8, 4);
					        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 12, 4);
					        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 16, 4);
						//�ѽ����ϵ�ֵ��һ�飬����8���ֽڵ��ֽ�����
						try {						
							//��ֹ
							if (!mAppList.get(position).get("foot_p_setting").toString().equals("*****.*")) {
								System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("foot_p_setting").toString())*100)), 0, temp, 0, 4);	
							}
							if (!mAppList.get(position).get("productBA_p_setting").toString().equals("*****.*")) {
								System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("productBA_p_setting").toString())*100)), 0, temp, 4, 4);

							}
							if (!mAppList.get(position).get("productUD_p_setting").toString().equals("*****.*")) {
								System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("productUD_p_setting").toString())*100)), 0, temp, 12, 4);

							}
							if (!mAppList.get(position).get("feedertroughBA_p_setting").toString().equals("*****.*")) {
								System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("feedertroughBA_p_setting").toString())*100)), 0, temp, 8, 4);

							}
							if(!mAppList.get(position).get("feedertroughUD_p_setting").toString().equals("*****.*")){
								System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("feedertroughUD_p_setting").toString())*100)), 0, temp, 16, 4);

							}
							//�����趨ֵ�޸�����
							settingValueCopy(v, editString, position, temp);
						  } catch (Exception e) {
							// TODO: handle exception
						  }
					}else if(name_String.contains("FP")){
						send_address=0;
						String devicename=name_String.split("\\s++")[0];
						devicename=devicename.trim();
						if(devicename.equalsIgnoreCase("FP001")){
							send_address = 0x20003AB0;
						}else if(devicename.equalsIgnoreCase("FP002")){
							send_address = 0x20003AC6;
						}
						//��ȡ�趨�ٶȣ����ٶȣ����ٶ���Ϣ,��д����
						 temp=new byte[16];
						//�ѽ����ϵ�ֵ��һ�飬����8���ֽڵ��ֽ�����
						try {						
							//��ֹ
							if (!mAppList.get(position).get("foot_p_setting").toString().equals("")) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(position).get("foot_p_setting").toString())*100)), 0, temp, 0,2);	
							}
							if (!mAppList.get(position).get("productBA_p_setting").toString().equals("")) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(position).get("productBA_p_setting").toString())*100)), 0, temp, 2,2);

							}
							if (!mAppList.get(position).get("productUD_p_setting").toString().equals("")) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(position).get("productUD_p_setting").toString())*100)), 0, temp, 6,2);

							}
							if (!mAppList.get(position).get("feedertroughBA_p_setting").toString().equals("")) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(position).get("feedertroughBA_p_setting").toString())*100)), 0, temp, 4, 2);

							}
							if(!mAppList.get(position).get("feedertroughUD_p_setting").toString().equals("")){
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(position).get("feedertroughUD_p_setting").toString())*100)), 0, temp, 8, 2);

							}
							//�����趨ֵ�޸�����
							settingValueCopy1(v, editString, position, temp);
						  } catch (Exception e) {
							// TODO: handle exception
						  }
					}else if(name_String.contains("P")){
						send_address=AddressPublic.model_P_point_Head
								+(AddressPublic.model_SP_point_Head-AddressPublic.model_P_point_Head)/Define.MAX_STDPOINT_NUM
								*(Integer.parseInt((name_String.split("\\s++")[0]).trim().substring(1))-1);
					//��ȡ�趨�ٶȣ����ٶȣ����ٶ���Ϣ,��д����
					 temp=new byte[20];
					//�ѽ����ϵ�ֵ��һ�飬����8���ֽڵ��ֽ�����
					try {						
						//��ֹ
						if (!mAppList.get(position).get("foot_p_setting").toString().equals("*****.*")) {
							System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("foot_p_setting").toString())*100)), 0, temp, 0, 4);	
						}
						if (!mAppList.get(position).get("productBA_p_setting").toString().equals("*****.*")) {
							System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("productBA_p_setting").toString())*100)), 0, temp, 4, 4);

						}
						if (!mAppList.get(position).get("productUD_p_setting").toString().equals("*****.*")) {
							System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("productUD_p_setting").toString())*100)), 0, temp, 12, 4);

						}
						if (!mAppList.get(position).get("feedertroughBA_p_setting").toString().equals("*****.*")) {
							System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("feedertroughBA_p_setting").toString())*100)), 0, temp, 8, 4);

						}
						if(!mAppList.get(position).get("feedertroughUD_p_setting").toString().equals("*****.*")){
							System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(position).get("feedertroughUD_p_setting").toString())*100)), 0, temp, 16, 4);

						}
						//�����趨ֵ�޸�����
						settingValueCopy(v, editString, position, temp);
					  } catch (Exception e) {
						// TODO: handle exception
					  }
					}
					
					formatSendMessage=new WifiSendDataFormat(temp, send_address);

					tempV=v;
					tempeditString=editString;
					tempposition=PListener.this.position;		

					try {


						sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());

						sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݷ������",backMessageToDo1);

						sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

						getActivity().runOnUiThread(sendDataRunnable);	
						


					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						//Toast.makeText(getActivity(), "������������", Toast.LENGTH_LONG).show();
					}
				}

			}).setNegativeButton(R.string.CANCEL, null).show();
			return;
			
		}

		private void settingValueCopy(View v, String editString,int position,byte[] temp) {
         try{
			HashMap<String, Object> map = new HashMap<String, Object>();

			//��ֹ�����ı�Ϊ���ֶΣ����ֶ�Ϊ0��
			if(editString==""){
				editString="0";
			}

			switch (v.getId()) {
			case R.id.foot_p_setting:
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 0, 4);
				break;
			case R.id.productBA_p_setting:
				System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 4, 4);
				break;
			case R.id.productUD_p_setting:
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 12,4);
				break;
			case R.id.feedertroughBA_p_setting:
				System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 8,4);
				break;
			case R.id.feedertroughUD_p_setting:
				System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 16,4);
				break;

			default:
				break;
			}

			notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		private void settingValueCopy1(View v, String editString,int position,byte[] temp) {
         try{
			HashMap<String, Object> map = new HashMap<String, Object>();

			//��ֹ�����ı�Ϊ���ֶΣ����ֶ�Ϊ0��
			if(editString==""){
				editString="0";
			}

			switch (v.getId()) {
			case R.id.foot_p_setting:
					System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,0, 2);
				break;
			case R.id.productBA_p_setting:
				System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,2, 2);
				break;
			case R.id.productUD_p_setting:
					System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,6,2);
				break;
			case R.id.feedertroughBA_p_setting:
				System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,4,2);
				break;
			case R.id.feedertroughUD_p_setting:
				System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,8,2);
				break;

			default:
				break;
			}

			notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

}

}   
