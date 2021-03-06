
package com.tr.newpragram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiReadMassiveDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.KeyCodeSend;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.explain.HexDecoding;
import com.explain.TableToBinary;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.TextView.OnEditorActionListener;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class FragmentOne extends Fragment {
    private final String TAG = "FragmentOne";
    private Button positionBtn = null;
    
    private List<String> list = new ArrayList<String>();   
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter =null;
    
    private LinearLayout PositionLayout = null;
    private LinearLayout FpLayout = null;
    
    private List<String> armlist = new ArrayList<String>();   
    private ListView fpListView;
	private static  ArrayList<HashMap<String, Object>> fsplist = new ArrayList<HashMap<String,Object>>();
	private TextView FpTxt;
	private EditText FpEdtTxt;
	private ToggleButton FpSwitchSp;

	private TextView spProductBFTxt;
	private TextView spProductUDTxt;

	private TextView CurSelInfo ;
	
	private int speedint;
	private int aspeedint;
	private int dspeedint;
	private int zxsx1;
	private int zxsx2;
	private int zxsx3;
	private HashMap<String, Object> CurListItem = null;
	private EditText xEditTxt ;
	private EditText yEditTxt ;
	private EditText zEditTxt ;
	private EditText hEditTxt ;
	private EditText lEditTxt ;
	private EditText speed;
	private EditText aspeed;
	private EditText dspeed;
	private String getposname;
    private String posname;
    private EditText FpNumEdit;	
	private Button mCopyBtn;
	
	private CheckBox xCheckBox;
	private	CheckBox yCheckBox;
	private CheckBox zCheckBox;
	private	CheckBox hCheckBox;
	private	CheckBox lCheckBox;
	
	private TextView xTextView ;
	private TextView yTextView ;
	private	TextView zTextView ;
	private	TextView hTextView ;
	private	TextView lTextView; 
	
	private RadioGroup ArmSelect;
	private RadioButton ProductArmBtn;
	private RadioButton FeedArmBtn;
	
	private RadioGroup SpeedSelect;
	private RadioButton SpeedLow;
	private RadioButton SpeedMid;
	private RadioButton SpeedHigh;
	private RadioButton SpeedZero;
	private RadioButton SpeedTen;
	private Button xCopyValueBtn;
	private Button yCopyValueBtn;
	private Button hCopyValueBtn;
	private Button zCopyValueBtn;
	private Button lCopyValueBtn;
	
	private int send_address_speed;
	private int send_address_pos;
	private int send_address_cc;
	private int send_address_acc;
	private int send_address_dcc;
	private int send_address_packcnt;
	private SendDataRunnable sendDataRunnable;
	private FinishRunnable sendDataFinishRunnable;
	private WifiSendDataFormat formatSendMessage;
	private WifiReadDataFormat formatReadMessage;
	private ChatListener SendOverTodo;
	private ChatListener SendOverTodo1;
	private byte[] getData;
	
	private FpAdapter fpmyAdapter;
	private SpAdapter spmyAdapter;
	private int flag=0;
	private int packCnt;
	private WifiReadMassiveDataFormat mformatReadMessage;
	
	private int[] pspfpaxleFlag=new int[5];
	
	private View rootView ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        list.add("取出侧位置");
        list.add("落下侧位置");
        list.add("标准装箱");
        list.add("自由装箱");
        armlist.add("制品臂装箱");
        armlist.add("料道臂装箱");
        
    }

    @Override
    public void onPause(){
        Log.d(TAG, "GeneralSetting onPause");
        NewPragramActivity.PosccalmRunnable.setTextView(null,null,null,null,null);
        super.onPause();
    }

    @Override
    public void onDestroy(){
        Log.d(TAG, "GeneralSetting onDestroy");
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
         rootView = inflater.inflate(R.layout.fragment_item_one, container, false);        
        final InputMethodManager  Inputmanager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);  
        rootView.setOnTouchListener(new OnTouchListener() {
    		
    		@Override
    		public boolean onTouch(View v, MotionEvent event) {
    			// TODO Auto-generated method stub
    			  if(event.getAction() == MotionEvent.ACTION_DOWN){  
    				     if(rootView.getWindowToken()!=null){  
    				    	 Inputmanager.hideSoftInputFromWindow(rootView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
    				     } 							     
    				  }  
    			return false;
    		}
    	});
        return rootView;
    }
    
    private final Runnable packCntbackMessageToDo = new Runnable(){
		@Override
		public void run() {	
			if (WifiSetting_Info.mClient == null) {
					Toast.makeText(getActivity(),"请先连接主机", Toast.LENGTH_SHORT).show();
					return;
				}
			 
				formatReadMessage=new WifiReadDataFormat(send_address_speed, 8*2+6);

				try {
					flag=0;
					sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

					sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕7",readDataFinishTodo1);

					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

					getActivity().runOnUiThread(sendDataRunnable);

				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
				}
		}
	};

	private void getpspfpaxleFlag(byte pspfpaxleFlagbyte){
		pspfpaxleFlag[0]=pspfpaxleFlagbyte&0x01;
		pspfpaxleFlag[1]=pspfpaxleFlagbyte&0x02;
		pspfpaxleFlag[2]=pspfpaxleFlagbyte&0x04;
		pspfpaxleFlag[3]=pspfpaxleFlagbyte&0x08;
		pspfpaxleFlag[4]=pspfpaxleFlagbyte&0x10;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = getArguments();
        byte pspfpaxleFlagbyte=0;
		getposname =  bundle.getString("PosType");
		posname=getposname.split("\\s++")[0];
		if(posname.startsWith("P")){
			pspfpaxleFlagbyte=Config.pspfpaxle[Integer.parseInt(posname.substring(1))-1];
			getpspfpaxleFlag(pspfpaxleFlagbyte);
        }
		PositionLayout = (LinearLayout) getActivity().findViewById(R.id.getoutPositionLayout);
		xEditTxt =(EditText) getActivity().findViewById(R.id.xEditText) ;
	    yEditTxt =(EditText) getActivity().findViewById(R.id.yEditText) ;
		zEditTxt =(EditText) getActivity().findViewById(R.id.zEditText) ;
		hEditTxt =(EditText) getActivity().findViewById(R.id.hEditText) ;
		lEditTxt =(EditText) getActivity().findViewById(R.id.lEditText) ;

		xEditTxt.setOnTouchListener(new PListener(xEditTxt));
		yEditTxt.setOnTouchListener(new PListener(yEditTxt));
		zEditTxt.setOnTouchListener(new PListener(zEditTxt));
		hEditTxt.setOnTouchListener(new PListener(hEditTxt));
		lEditTxt.setOnTouchListener(new PListener(lEditTxt));
	
		mCopyBtn = (Button) getActivity().findViewById(R.id.positionMemryBtn);
		OnClickListener SinglePositionValueCopyListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//20个字节，五轴
				final byte[] temp=new byte[4];
				//发送头地址
				int send_address=AddressPublic.model_P_point_Head
						+(AddressPublic.model_SP_point_Head-AddressPublic.model_P_point_Head)/Define.MAX_STDPOINT_NUM
						*(Integer.parseInt(posname.trim().substring(1))-1);
				         
				String x = xTextView.getText().toString();
				String y = yTextView.getText().toString();
				String z = zTextView.getText().toString();
				String h = hTextView.getText().toString();
				String l = lTextView.getText().toString();
				
				switch(v.getId())
				{
				case R.id.setValueBtn1:
					if(x.equals("*****.*"))
					{
						Toast.makeText(getActivity(),"原点复归未完成,不能位置记忆",Toast.LENGTH_SHORT).show();
						return;
					}
					xEditTxt.setText(x);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(x)*100)), 0, temp, 0, 4);
					send_address=send_address;
					break;
				case R.id.setValueBtn2:
					if(y.equals("*****.*"))
					{
						Toast.makeText(getActivity(),"原点复归未完成,不能位置记忆",Toast.LENGTH_SHORT).show();
						return;
					}
					yEditTxt.setText(y);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(y)*100)), 0, temp, 0, 4);
					send_address=send_address+4;
					break;
				case R.id.setValueBtn3:
					if(h.equals("*****.*"))
					{
						Toast.makeText(getActivity(),"原点复归未完成,不能位置记忆",Toast.LENGTH_SHORT).show();
						return;
					}
					hEditTxt.setText(h);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(h)*100)), 0, temp, 0, 4);
					send_address=send_address+8;
					break;
				case R.id.setValueBtn4:
					if(z.equals("*****.*"))
					{
						Toast.makeText(getActivity(),"原点复归未完成,不能位置记忆",Toast.LENGTH_SHORT).show();
						return;
					}
					zEditTxt.setText(z);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(z)*100)), 0, temp, 0, 4);
					send_address=send_address+12;
					break;
				case R.id.setValueBtn5:
					if(l.equals("*****.*"))
					{
						Toast.makeText(getActivity(),"原点复归未完成,不能位置记忆",Toast.LENGTH_SHORT).show();
						return;
					}
					lEditTxt.setText(l);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(l)*100)), 0, temp, 0, 4);
					send_address=send_address+16;
					break;
				}
				
					WifiSendDataFormat posMemorySendDataFormat = new WifiSendDataFormat(temp, send_address);
					sendDataRunnable = new SendDataRunnable(posMemorySendDataFormat, getActivity());
					FinishRunnable memoryTodo = new FinishRunnable(getActivity(),"位置已记忆");
					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, memoryTodo));
					getActivity().runOnUiThread(sendDataRunnable);
			  
				
			}
		};
		
		xCopyValueBtn = (Button) getActivity().findViewById(R.id.setValueBtn1);
		yCopyValueBtn = (Button) getActivity().findViewById(R.id.setValueBtn2);
		hCopyValueBtn = (Button) getActivity().findViewById(R.id.setValueBtn3);
		zCopyValueBtn = (Button) getActivity().findViewById(R.id.setValueBtn4);
		lCopyValueBtn = (Button) getActivity().findViewById(R.id.setValueBtn5);
		xCopyValueBtn.setOnClickListener(SinglePositionValueCopyListener);
		yCopyValueBtn.setOnClickListener(SinglePositionValueCopyListener);
		hCopyValueBtn.setOnClickListener(SinglePositionValueCopyListener);
		zCopyValueBtn.setOnClickListener(SinglePositionValueCopyListener);
		lCopyValueBtn.setOnClickListener(SinglePositionValueCopyListener);
		
		OnClickListener mCopyBtnListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String x=xEditTxt.getText().toString();
				String y=yEditTxt.getText().toString();
				String z=zEditTxt.getText().toString();
				String h=hEditTxt.getText().toString();
				String l=lEditTxt.getText().toString();
				if(xCheckBox.isChecked()){
					x=xTextView.getText().toString();
				}
				if(yCheckBox.isChecked()){
					y=yTextView.getText().toString();
				}
				if(zCheckBox.isChecked()){
					z=zTextView.getText().toString();
				}
				if(hCheckBox.isChecked()){
					h=hTextView.getText().toString();
				}
				if(lCheckBox.isChecked()){
					l=lTextView.getText().toString();
				}
				if(!x.equals("*****.*")&&!y.equals("*****.*")&&!z.equals("*****.*")&&
						!h.equals("*****.*")&&!l.equals("*****.*")){
					xEditTxt.setText(x);
					yEditTxt.setText(y);
					zEditTxt.setText(z);
					hEditTxt.setText(h);
					lEditTxt.setText(l);
				}
				//20个字节，五轴
				final byte[] temp=new byte[20];
				//发送头地址
				int send_address=AddressPublic.model_P_point_Head
						+(AddressPublic.model_SP_point_Head-AddressPublic.model_P_point_Head)/Define.MAX_STDPOINT_NUM
						*(Integer.parseInt(posname.trim().substring(1))-1);

				if(!x.equals("*****.*")&&!y.equals("*****.*")&&!z.equals("*****.*")&&
					!h.equals("*****.*")&&!l.equals("*****.*"))
				{
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(x)*100)), 0, temp, 0, 4);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(y)*100)), 0, temp, 4, 4);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(h)*100)), 0, temp, 8, 4);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(z)*100)), 0, temp,12, 4);
					System.arraycopy(HexDecoding.int2byteArray4((int)(Float.valueOf(l)*100)), 0, temp,16, 4);


					WifiSendDataFormat posMemorySendDataFormat = new WifiSendDataFormat(temp, send_address);
					sendDataRunnable = new SendDataRunnable(posMemorySendDataFormat, getActivity());
					FinishRunnable memoryTodo = new FinishRunnable(getActivity(),"位置已记忆");
					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, memoryTodo));
					getActivity().runOnUiThread(sendDataRunnable);
			   }else{				   
					Toast.makeText(getActivity(),"原点复归未完成,不能位置记忆",Toast.LENGTH_SHORT).show();
			   }
			}
		};
		mCopyBtn.setOnClickListener(mCopyBtnListener);

		
		FpLayout = (LinearLayout) getActivity().findViewById(R.id.FSPLayout);
		FpLayout.setVisibility(View.GONE);
		fpListView = (ListView) getActivity().findViewById(R.id.listView_sfp_setting);
		FpTxt = (TextView) getActivity().findViewById(R.id.FpNumTxt);
		FpTxt.setVisibility(View.GONE);
		FpEdtTxt = (EditText) getActivity().findViewById(R.id.FpNumEdt);
		FpEdtTxt.setVisibility(View.GONE);
		FpEdtTxt.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					 {
						
						 InputMethodManager m=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						 m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
						  
						 if(FpSwitchSp.isChecked())
		         	        {
			        			FpAdapter myAdapter  =new FpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fpListView);
			        			fpListView.setAdapter(myAdapter);
		         	        }else
		         	        {
		         	        	String Str =FpNumEdit.getText().toString();
		         	        	packCnt = 0;
	         					if(Str.equals("")){
	         						packCnt = 0;
	         					}else{
	         						try{
	         							packCnt = Integer.valueOf(Str).intValue(); 
	         					     }catch(Exception e){
	         							e.printStackTrace();
	         							Toast.makeText(getActivity(),"数据有误，请重新输入",Toast.LENGTH_SHORT).show();
	         							return false;
	         						 }
	         					   
	         					}
	         					
	         					if(packCnt>99){
			   						 Toast.makeText(getActivity(),"自由装箱范围1~99", Toast.LENGTH_SHORT).show();
			   						 return false;
			   					 }
	         					
	         					 byte[] temp=new byte[2];
			   						System.arraycopy(HexDecoding.int2byteArray2(packCnt), 0, temp, 0, 2);

			   						try {

			   							sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(temp, send_address_packcnt), getActivity());

			   							sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据发送完毕",packCntbackMessageToDo);

			   							sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable,sendDataFinishRunnable));

			   							getActivity().runOnUiThread(sendDataRunnable);
			   							

			   						} catch (Exception e) {
			   							// TODO: handle exception
			   							e.printStackTrace();
			   						}
	         					
		         	        }
			                return true;
			            }
//					return false;
				}
			});
		CurSelInfo = (TextView) getActivity().findViewById(R.id.infoText);
		
		speed=(EditText)getActivity().findViewById(R.id.speed);
		aspeed=(EditText)getActivity().findViewById(R.id.aspeed);
		dspeed=(EditText)getActivity().findViewById(R.id.dspeed);
		speed.setOnTouchListener(new PListener(speed));
		aspeed.setOnTouchListener(new PListener(aspeed));
		dspeed.setOnTouchListener(new PListener(dspeed));

		spProductBFTxt = (TextView) getActivity().findViewById(R.id.productBA_etting);
		spProductUDTxt =(TextView) getActivity().findViewById(R.id.productUD_setting);
		
		positionBtn = (Button) getActivity().findViewById(R.id.positionBtn);
	    positionBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					NewPragramActivity.PosccalmRunnable.setTextView(null,null,null,null,null);
					Intent IT =new Intent();
					Bundle bundle = new Bundle();
					bundle.putBoolean("GotoTimer", false);
					IT.putExtras(bundle);
					IT.setAction("goPositionPreview");
					getActivity().sendBroadcast(IT);
					
				}
			});
	        FpSwitchSp = (ToggleButton) getActivity().findViewById(R.id.spfp_switch);
	        FpSwitchSp.setOnCheckedChangeListener(new BtnOnCheckedChangeListener());
	        
	        ArmSelect = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);
	        ProductArmBtn = (RadioButton) getActivity().findViewById(R.id.ProductArm);
	        FeedArmBtn = (RadioButton) getActivity().findViewById(R.id.FeedArm);
	        
	        ArmSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					Config.SelectArmId = checkedId;
					
				}
			});
	        ArmSelect.check(ProductArmBtn.getId()); 
	        if(Config.ArmNum == 3){
	        	FeedArmBtn.setVisibility(View.GONE);
	        }else{
	            FeedArmBtn.setVisibility(View.VISIBLE);
	        }
	        SpeedSelect = (RadioGroup) getActivity().findViewById(R.id.radioGroup2);
	        SpeedLow =  (RadioButton) getActivity().findViewById(R.id.speedBtnOne);
	        SpeedMid =  (RadioButton) getActivity().findViewById(R.id.speedBtnTwo);
	        SpeedHigh =  (RadioButton) getActivity().findViewById(R.id.speedBtnThree);
	        SpeedTen =  (RadioButton) getActivity().findViewById(R.id.speedBtnFour);
	        SpeedZero =  (RadioButton) getActivity().findViewById(R.id.speedBtnFive);	        
	        SpeedSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					Config.SelectSpeedId = checkedId;
					
				}
			});
	        SpeedSelect.check(SpeedLow.getId());
	       
		super.onActivityCreated(savedInstanceState);
	}

    private class BtnOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener{  
        @Override  
        public void onCheckedChanged(CompoundButton button, boolean isChecked){
        	
        	Log.e("mpeng","11111111111111111111111");
        	if(posname.contains("SP")){	
				int readMessageAddr= send_address_speed - 10*Define.MAX_AXIS_NUM;//0x200026f0;
	
				if (WifiSetting_Info.mClient == null) {
					Toast.makeText(getActivity(),"请先连接主机", Toast.LENGTH_SHORT).show();
					return;
				}
				//与下位机通信，获取初始化listview的数据，一次读取36个字节，36个字节用于更新listview
				formatReadMessage=new WifiReadDataFormat(readMessageAddr, 84);
	
				try {
	
					sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());
	
					sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕5",SPreadDataAfterBtnChecked);
	
					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
	
					getActivity().runOnUiThread(sendDataRunnable);
	
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
				}
        	}
            
        }  
    } 

    //P P P P P P P P P P P P P P P P P P P P
    private final Runnable backMessageToDo1 = new Runnable(){
		@Override
		public void run() {	
			speed.setText(String.valueOf(speedint));	
			aspeed.setText(String.valueOf(aspeedint));	
			dspeed.setText(String.valueOf(dspeedint));
		}
	};
	private final Runnable PreadDataFinishTodo = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//对于返回的36字节
          try{
			getData=new byte[formatReadMessage.getLength()];
			Log.e("mpeng","PreadDataFinishTodo 111 ");
			//获取返回的数据，从第八位开始拷贝数据
			if( formatReadMessage.getFinalData() != null)  ////////chenxinhua
			{
				System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
				Log.e("mpeng","PreadDataFinishTodo 222 ");
				if( getActivity() != null)  
				{
					Log.e("mpeng","PreadDataFinishTodo 3333 ");
					Double foot_p=Double.valueOf(HexDecoding.Array4Toint(getData, 0))/100;
					Double productBA_p=Double.valueOf(HexDecoding.Array4Toint(getData, 4))/100;
					Double productUD_p=Double.valueOf(HexDecoding.Array4Toint(getData, 8))/100;
					Double feedertroughBA_p=Double.valueOf(HexDecoding.Array4Toint(getData, 12))/100;
					Double feedertroughUD_p=Double.valueOf(HexDecoding.Array4Toint(getData, 16))/100;
					if(foot_p>99999.9){
						xEditTxt.setText("*****.*");
					}else{
					    xEditTxt.setText(String.format("%1$5.1f",foot_p));
					}
					
					if(productBA_p>99999.9){
						yEditTxt.setText("*****.*");
					}else{
					    yEditTxt.setText(String.format("%1$5.1f",productBA_p));
					}
					
					if(productUD_p>99999.9){
						hEditTxt.setText("*****.*");
					}else{
					    hEditTxt.setText(String.format("%1$5.1f",productUD_p));	
					}
					
					if(feedertroughBA_p>99999.9){
						zEditTxt.setText("*****.*");
					}else{
					    zEditTxt.setText(String.format("%1$5.1f",feedertroughBA_p));
					}
					
					if(feedertroughUD_p>99999.9){
						lEditTxt.setText("*****.*");
					}else{
					    lEditTxt.setText(String.format("%1$5.1f",feedertroughUD_p));
					}
					xEditTxt.setSelection(xEditTxt.getText().length());// 设置光标位置
					yEditTxt.setSelection(yEditTxt.getText().length());// 设置光标位置
					zEditTxt.setSelection(zEditTxt.getText().length());// 设置光标位置
					hEditTxt.setSelection(hEditTxt.getText().length());// 设置光标位置
					lEditTxt.setSelection(lEditTxt.getText().length());// 设置光标位置
					Log.e("mpeng","getData[4*Define.MAX_AXIS_NUM]&0xff:" +(getData[4*Define.MAX_AXIS_NUM+1]&0xff));
					speedint=(int)(getData[4*Define.MAX_AXIS_NUM]&0xff);
					aspeedint=(int)(getData[4*Define.MAX_AXIS_NUM+1]&0xff);
					dspeedint=(int)(getData[4*Define.MAX_AXIS_NUM+2]&0xff);
					speed.setText(String.valueOf(speedint));	
					aspeed.setText(String.valueOf(aspeedint));	
					dspeed.setText(String.valueOf(dspeedint));
					speed.setSelection(speed.getText().length());// 设置光标位置
					aspeed.setSelection(aspeed.getText().length());// 设置光标位置
					dspeed.setSelection(dspeed.getText().length());// 设置光标位置
					if(speedint==0||aspeedint==0||dspeedint==0){
						//获取设定速度，加速度，减速度信息,填写数据
						byte[] temp=new byte[3];
						try {						
							//设定速度
							if(speedint==0){speedint=10;}
							System.arraycopy(HexDecoding.int2byte(speedint), 0, temp, 0, 1);	
								
							//加速度
							if(aspeedint==0){aspeedint=1;}
							System.arraycopy(HexDecoding.int2byte(aspeedint), 0, temp, 1, 1);
							
							//减速度
							if(dspeedint==0){dspeedint=1;}
							System.arraycopy(HexDecoding.int2byte(dspeedint), 0, temp, 2, 1);
							
						    formatSendMessage=new WifiSendDataFormat(temp, send_address_speed);	
							sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
							sendDataFinishRunnable=new FinishRunnable(getActivity(),backMessageToDo1);
							sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
							getActivity().runOnUiThread(sendDataRunnable);	
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				
				}
			}
			

		}catch(Exception e){
			e.printStackTrace();
		}

		}

	};
	private void swithToPosition(){
		PositionLayout.setVisibility(View.VISIBLE);
		FpLayout.setVisibility(View.GONE);
		FpTxt.setVisibility(View.GONE);
		FpEdtTxt.setVisibility(View.GONE);
		CurSelInfo.setText(getposname);
		Log.e("mpeng"," swithto position");
		 xCheckBox=(CheckBox)getActivity().findViewById(R.id.xCcheckBox);
		 yCheckBox=(CheckBox)getActivity().findViewById(R.id.yCheckBox);
		 zCheckBox=(CheckBox)getActivity().findViewById(R.id.zCheckBox);
		 hCheckBox=(CheckBox)getActivity().findViewById(R.id.hCheckBox3);
		 lCheckBox=(CheckBox)getActivity().findViewById(R.id.lCheckBox3);
		 xCheckBox.setClickable(false);
		 yCheckBox.setClickable(false);
		 zCheckBox.setClickable(false);
		 hCheckBox.setClickable(false);
		 lCheckBox.setClickable(false);
		 if(pspfpaxleFlag[0]==1){
			 xCheckBox.setChecked(true);
			 xEditTxt.setEnabled(true);
			 xCopyValueBtn.setEnabled(true);
		 }else{
			 xCheckBox.setChecked(false);
			 xEditTxt.setEnabled(false);
			 xCopyValueBtn.setEnabled(false);
		 }
		
		 if(pspfpaxleFlag[1]==2){
			 yCheckBox.setChecked(true);
			 yEditTxt.setEnabled(true);
			 yCopyValueBtn.setEnabled(true);
		 }else{
			 yCheckBox.setChecked(false);
			 yEditTxt.setEnabled(false);
			 yCopyValueBtn.setEnabled(false);
		 }
		
		 if(pspfpaxleFlag[2]==4){
			 hCheckBox.setChecked(true); 
			 hEditTxt.setEnabled(true);
			 hCopyValueBtn.setEnabled(true);
		 }else{
			 hCheckBox.setChecked(false);
			 hEditTxt.setEnabled(false);
			 hCopyValueBtn.setEnabled(false);
		 }
		 
		 if(pspfpaxleFlag[3]==8){
			 zCheckBox.setChecked(true); 
			 zEditTxt.setEnabled(true);
			 zCopyValueBtn.setEnabled(true);
		 }else{
			 zCheckBox.setChecked(false);
			 zEditTxt.setEnabled(false);
			 zCopyValueBtn.setEnabled(false);
		 }
		
		 if(pspfpaxleFlag[4]==16){
			 lCheckBox.setChecked(true);
			 lEditTxt.setEnabled(true);
			 lCopyValueBtn.setEnabled(true);
		 }else{
			 lCheckBox.setChecked(false);	
			 lEditTxt.setEnabled(false);
			 lCopyValueBtn.setEnabled(false);
		 }		
		
		 xTextView = (TextView) getActivity().findViewById(R.id.xValue);
		 yTextView = (TextView) getActivity().findViewById(R.id.yValue);
		 zTextView = (TextView) getActivity().findViewById(R.id.Zvalue);
		 hTextView = (TextView) getActivity().findViewById(R.id.hValue);
		 lTextView = (TextView) getActivity().findViewById(R.id.lValue);
		 
		 NewPragramActivity.PosccalmRunnable.setTextView(xTextView,yTextView,zTextView,hTextView,lTextView);
		//根据点的信息确定点在数组中的位置，确定发送地址
			int readMessageAddr=send_address_speed-4*Define.MAX_AXIS_NUM;
			
			if (WifiSetting_Info.mClient == null) {
				Toast.makeText(getActivity(),"请先连接主机", Toast.LENGTH_SHORT).show();
				return;
			}
			//与下位机通信，获取初始化listview的数据，一次读取36个字节，36个字节用于更新listview
			formatReadMessage=new WifiReadDataFormat(readMessageAddr, 36);

			try {

				sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

				sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕1",PreadDataFinishTodo);

				sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

				getActivity().runOnUiThread(sendDataRunnable);

			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
			}
	
		
	}
	//P P P P P P P P P P P P P P P P P P P P
	
	//FP FP FP FP FP FP FP FP FP FP FP FP FP FP
	private final Runnable readDataFinishTodo1 = new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//对于返回的36字节
			
				getData=new byte[formatReadMessage.getLength()];
				//获取返回的数据，从第八位开始拷贝数据
				System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());

				initialFList(getData);
				initialPList(getData);
		
				if(packCnt>34){
					if (WifiSetting_Info.mClient!=null) {
						flag=1;
						mformatReadMessage=new WifiReadMassiveDataFormat(send_address_pos, 8*packCnt*4);  
						
						sendDataRunnable=new SendDataRunnable(mformatReadMessage, getActivity());

						sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕2",readDataFinishTodo);

						sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
								
						getActivity().runOnUiThread(sendDataRunnable);
						}else{
							Toast.makeText(getActivity(), "请连接下位机", Toast.LENGTH_SHORT).show();
						}
		   
				}else{
					formatReadMessage=new WifiReadDataFormat(send_address_pos, 8*packCnt*4);
			        try {
						flag=1;
						sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

						sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕3",readDataFinishTodo);

						sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

						getActivity().runOnUiThread(sendDataRunnable);

					} catch (Exception e) {
						// TODO: handle exception 
						//Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG); 
					}
				}
				}

		};
	private final Runnable readDataFinishTodo = new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//对于返回的36字节
				FpEdtTxt.setSelection(FpEdtTxt.getText().length());// 设置光标位置
				if(packCnt>34){
					getData=new byte[mformatReadMessage.getLength()];
					//获取返回的数据，从第八位开始拷贝数据
					System.arraycopy(mformatReadMessage.getFinalData(), 0, getData, 0, mformatReadMessage.getLength());
				}else{
				    getData=new byte[formatReadMessage.getLength()];
				    //获取返回的数据，从第八位开始拷贝数据
				    System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
				}
				
				if (FpSwitchSp.isChecked()) {
					initialFList(getData);
					fpmyAdapter.notifyDataSetChanged();	
				
				}else{
					initialPList(getData);
				    fpmyAdapter.notifyDataSetChanged();
				}
				}

		};
	private void initialFList(byte[] backData) {

			//arrayList.clear();
			if(flag==0){
				fsplist.clear();
				HashMap<String, Object> map0 = new HashMap<String, Object>();
				map0.put("name_sp_setting", "当前位置(mm)");
				map0.put("line_value", "*****.*");
				map0.put("Bf_value", "*****.*");
				map0.put("ud_value", "*****.*");
				fsplist.add( map0);

				HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("name_sp_setting", "补正值(mm)");
				map1.put("line_value", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(backData,0))/100)));
				map1.put("Bf_value",String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(backData,4))/100)));
				map1.put("ud_value", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(backData,8))/100)));
				fsplist.add( map1);

				speedint=(int)(backData[18]&0xff);
				aspeedint=(int)(backData[19]&0xff);
				dspeedint=(int)(backData[20]&0xff);
				packCnt=HexDecoding.Array2Toint(backData, 16);
				speed.setText(String.valueOf(speedint));	
				aspeed.setText(String.valueOf(aspeedint));	
				dspeed.setText(String.valueOf(dspeedint));
				FpEdtTxt.setText(String.valueOf(packCnt));
				if(speedint==0||aspeedint==0||dspeedint==0||packCnt==0){
					//获取设定速度，加速度，减速度信息,填写数据
					byte[] temp=new byte[5];
					try {		
						    if(packCnt==0){packCnt=3;}
							System.arraycopy(HexDecoding.int2byteArray2(packCnt), 0, temp, 0, 2);	
						
						//设定速度
							if(speedint==0){speedint=10;}
							System.arraycopy(HexDecoding.int2byte(speedint), 0, temp, 2, 1);
						//加速度
							if(aspeedint==0){aspeedint=1;}
							System.arraycopy(HexDecoding.int2byte(aspeedint), 0, temp, 3, 1);
						//减速度
							if(dspeedint==0){dspeedint=1;}
							System.arraycopy(HexDecoding.int2byte(dspeedint), 0, temp, 4, 1);

					    formatSendMessage=new WifiSendDataFormat(temp, send_address_speed+16);	
						sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
						sendDataFinishRunnable=new FinishRunnable(getActivity(),backMessageToDo3);
						sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
						getActivity().runOnUiThread(sendDataRunnable);	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}	
			if(flag==1){
				for(int i=5;i<5+packCnt;i++){
				   HashMap<String, Object> map5 = new HashMap<String, Object>();
				   map5.put("name_sp_setting", "位置"+(i-4)+"(mm)");
				   Double foot_p=Double.valueOf(HexDecoding.Array4Toint(backData, 0+(i-5)*32))/100;
					Double productBA_p=Double.valueOf(HexDecoding.Array4Toint(backData, 8+(i-5)*32))/100;
					Double productUD_p=Double.valueOf(HexDecoding.Array4Toint(backData, 16+(i-5)*32))/100;
					if(foot_p>99999.9){
						map5.put("line_value","*****.*");
					}else{
				        map5.put("line_value",String.format("%1$5.1f",foot_p));
				    }
					
					if(productBA_p>99999.9){
						map5.put("Bf_value", "*****.*");
					}else{
				        map5.put("Bf_value", String.format("%1$5.1f",productBA_p));
					}
					
					if(productUD_p>99999.9){
						map5.put("ud_value", "*****.*");
					}else{
				        map5.put("ud_value", String.format("%1$5.1f",productUD_p));
					}
				   fsplist.add(map5);
				 }
			}
				System.out.println("fp empty");

		}
	private final Runnable backMessageToDo3 = new Runnable(){
			@Override
			public void run() {	
				onStart();
			}
		};
	private void initialPList(byte[] backData) {
			if(flag==0){
				fsplist.clear();
				HashMap<String, Object> map0 = new HashMap<String, Object>();
				map0.put("name_sp_setting", "当前位置(mm)");
				map0.put("line_value", "*****.*");
				map0.put("Bf_value", "*****.*");
				map0.put("ud_value", "*****.*");
				fsplist.add( map0);

				HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("name_sp_setting", "补正值(mm)");
				map1.put("line_value", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(backData,0))/100)));
				map1.put("Bf_value",String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(backData,2))/100)));
				map1.put("ud_value", String.format("%1$5.1f",(Double.valueOf(HexDecoding.Array2Toint(backData,6))/100)));
				fsplist.add( map1);
				
				speedint=(int)(backData[18]&0xff);
				aspeedint=(int)(backData[19]&0xff);
				dspeedint=(int)(backData[20]&0xff);
				packCnt=HexDecoding.Array2Toint(backData, 16);
				speed.setText(String.valueOf(speedint));	
				aspeed.setText(String.valueOf(aspeedint));	
				dspeed.setText(String.valueOf(dspeedint));
				FpEdtTxt.setText(String.valueOf(packCnt));
				if(speedint==0||aspeedint==0||dspeedint==0||packCnt==0){
					//获取设定速度，加速度，减速度信息,填写数据
					byte[] temp=new byte[5];
					try {		
						    if(packCnt==0){packCnt=3;}
							System.arraycopy(HexDecoding.int2byteArray2(packCnt), 0, temp, 0, 2);	
						
						//设定速度
							if(speedint==0){speedint=10;}
							System.arraycopy(HexDecoding.int2byte(speedint), 0, temp, 2, 1);
						//加速度
							if(aspeedint==0){aspeedint=1;}
							System.arraycopy(HexDecoding.int2byte(aspeedint), 0, temp, 3, 1);
						//减速度
							if(dspeedint==0){dspeedint=1;}
							System.arraycopy(HexDecoding.int2byte(dspeedint), 0, temp, 4, 1);

					    formatSendMessage=new WifiSendDataFormat(temp, send_address_speed+16);	
						sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
						sendDataFinishRunnable=new FinishRunnable(getActivity(),backMessageToDo3);
						sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
						getActivity().runOnUiThread(sendDataRunnable);	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if(flag==1){
				for(int i=5;i<5+packCnt;i++){
				   HashMap<String, Object> map5 = new HashMap<String, Object>();
				   map5.put("name_sp_setting", "位置"+(i-4)+"(mm)");
				   
				   Double foot_p=Double.valueOf(HexDecoding.Array4Toint(backData, 0+(i-5)*32))/100;
					Double productBA_p=Double.valueOf(HexDecoding.Array4Toint(backData, 4+(i-5)*32))/100;
					Double productUD_p=Double.valueOf(HexDecoding.Array4Toint(backData, 12+(i-5)*32))/100;
					if(foot_p>99999.9){
						map5.put("line_value","*****.*");
					}else{
				        map5.put("line_value",String.format("%1$5.1f",foot_p));
				    }
					
					if(productBA_p>99999.9){
						map5.put("Bf_value", "*****.*");
					}else{
				        map5.put("Bf_value", String.format("%1$5.1f",productBA_p));
					}
					
					if(productUD_p>99999.9){
						map5.put("ud_value", "*****.*");
					}else{
				        map5.put("ud_value", String.format("%1$5.1f",productUD_p));
					}
				   fsplist.add(map5);
				 }
			}
			   System.out.println("fp empty");

		}
	private void swithToFp(){
	    	NewPragramActivity.PosccalmRunnable.setTextView(null,null,null,null,null);
		FpLayout.setVisibility(View.VISIBLE);
		PositionLayout.setVisibility(View.GONE);
		FpTxt.setVisibility(View.VISIBLE);
		FpEdtTxt.setVisibility(View.VISIBLE);	
		CurSelInfo.setText(getposname);
		FpNumEdit = (EditText) getActivity().findViewById(R.id.FpNumEdt);
		
		if(FpSwitchSp.isChecked()){
		}else{
				fpmyAdapter  =new FpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fpListView,send_address_speed,packCnt,posname);
				fpListView.setAdapter(fpmyAdapter);			
		}
		if (WifiSetting_Info.mClient == null) {
			Toast.makeText(getActivity(),"请先连接主机", Toast.LENGTH_SHORT).show();
			return;
		}
		formatReadMessage=new WifiReadDataFormat(send_address_speed, 8*2+6);

		try {
			flag=0;
			sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

			sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕4",readDataFinishTodo1);

			sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

			getActivity().runOnUiThread(sendDataRunnable);

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
		}
	}
	//FP FP FP FP FP FP FP FP FP FP FP FP FP FP
	
	private final Runnable SPreadDataAfterBtnChecked = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//对于返回的36字节

			try{
			getData=new byte[formatReadMessage.getLength()];
			//获取返回的数据，从第八位开始拷贝数据
			System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
			Log.e("mpeng","22222222222222222");
			if (FpSwitchSp.isChecked()) {				
				initialsFList(getData);
			} else {
				initialsPList(getData);
			}

			if(FpSwitchSp.isChecked()){  
            	spProductBFTxt.setText("料道前后(H)");
            	spProductUDTxt.setText("料道上下(L)");
            	if(posname.contains("SP")){
            		spmyAdapter  =new SpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","foot_sp_setting","productBA_sp_setting","productUD_sp_setting"},fpListView,send_address_speed,posname);
            		fpListView.setAdapter(spmyAdapter); 	
            		spmyAdapter.notifyDataSetChanged();
            	}else if(posname.contains("FP")){
            		fpmyAdapter.notifyDataSetChanged();	
            	}
            }else{
            	spProductBFTxt.setText("制品前后(Y)");
            	spProductUDTxt.setText("制品上下(Z)");
            	if(posname.contains("SP")){
            		spmyAdapter  =new SpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fpListView,send_address_speed,posname);
        			fpListView.setAdapter(spmyAdapter);
            	}else if(posname.contains("FP")){
            		fpmyAdapter.notifyDataSetChanged();	
            	}
            }
		
			spmyAdapter.notifyDataSetChanged();	
		}catch(Exception e){
			e.printStackTrace();
		}
		}

	};
	
	//SP SP SP SP SP SP SP SP SP SP SP SP SP SP
	private final Runnable SPreadDataFinishTodo = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//对于返回的36字节

			try{
			getData=new byte[formatReadMessage.getLength()];
			//获取返回的数据，从第八位开始拷贝数据
			System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
			Log.e("mpeng","22222222222222222");
			if (FpSwitchSp.isChecked()) {
				Log.e("mpeng","checked");
				initialsFList(getData);
			} else {
				initialsPList(getData);
			}
			spmyAdapter.notifyDataSetChanged();	
		}catch(Exception e){
			e.printStackTrace();
		}
		}

	};
	private void initialsFList(byte[] backData) {

		try{
			fsplist.clear();
			
			HashMap<String, Object> map0 = new HashMap<String, Object>();
			map0.put("name_sp_setting", "当前位置(mm)");
			map0.put("foot_sp_setting", "*****.*");
			map0.put("productBA_sp_setting", "*****.*");
			map0.put("productUD_sp_setting", "*****.*");
			fsplist.add(map0);

			Double foot_p=HexDecoding.Array4Toint(backData, 0)/100.0;
			Double productBA_p=HexDecoding.Array4Toint(backData, 8)/100.0;
			Double productUD_p=HexDecoding.Array4Toint(backData, 16)/100.0;
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("name_sp_setting", "开始位置(mm)");
			if(foot_p>99999.9){
				map1.put("foot_sp_setting", "*****.*");
			}else{
			    map1.put("foot_sp_setting", String.format("%1$5.1f",foot_p));
			}
			
			if(productBA_p>99999.9){
				map1.put("productBA_sp_setting", "*****.*");
			}else{
			    map1.put("productBA_sp_setting", String.format("%1$5.1f",productBA_p));
			}
			
			if(productUD_p>99999.9){
				map1.put("productUD_sp_setting", "*****.*");
			}else{
			    map1.put("productUD_sp_setting",String.format("%1$5.1f",productUD_p));
			}
			fsplist.add(map1);

			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name_sp_setting", "装箱间距(mm)");
			map2.put("foot_sp_setting", String.format("%1$5.1f",(HexDecoding.Array2Toint(backData, 32)/100.0)));
			map2.put("productBA_sp_setting", String.format("%1$5.1f",(HexDecoding.Array2Toint(backData, 36)/100.0)));
			map2.put("productUD_sp_setting", String.format("%1$5.1f",(HexDecoding.Array2Toint(backData, 40)/100.0)));
			fsplist.add(map2);
			
			speedint=(int)(backData[80]&0xff);
			aspeedint=(int)(backData[81]&0xff);
			dspeedint=(int)(backData[82]&0xff);
			zxsx1=HexDecoding.Array2Toint(backData, 64);
			zxsx2=HexDecoding.Array2Toint(backData, 68);
			zxsx3=HexDecoding.Array2Toint(backData, 72);
			HashMap<String, Object> map7 = new HashMap<String, Object>();
			map7.put("name_sp_setting", "装箱顺序");
			map7.put("foot_sp_setting", zxsx1);
			map7.put("productBA_sp_setting", zxsx2);
			map7.put("productUD_sp_setting",zxsx3);
			fsplist.add(map7);
			
			HashMap<String, Object> map6 = new HashMap<String, Object>();
			map6.put("name_sp_setting", "装箱设定数");
			map6.put("foot_sp_setting", String.valueOf(HexDecoding.Array2Toint(backData, 48)));
			map6.put("productBA_sp_setting", String.valueOf(HexDecoding.Array2Toint(backData, 52)));
			map6.put("productUD_sp_setting", String.valueOf(HexDecoding.Array2Toint(backData, 56)));
			fsplist.add(map6);

			HashMap<String, Object> map5 = new HashMap<String, Object>();
			map5.put("name_sp_setting", "当前装箱数");
			map5.put("foot_sp_setting", "");
			map5.put("productBA_sp_setting", "");
			map5.put("productUD_sp_setting", "");
			fsplist.add(map5);

			speed.setText(String.valueOf(speedint));	
			aspeed.setText(String.valueOf(aspeedint));	
			dspeed.setText(String.valueOf(dspeedint));
			if(speedint==0||aspeedint==0||dspeedint==0||zxsx1==0||zxsx2==0||zxsx3==0){
				//获取设定速度，加速度，减速度信息,填写数据
				byte[] temp=new byte[19];
				try {		
					if(zxsx1==0||zxsx2==0||zxsx3==0){
						zxsx1=1;   zxsx2=2;   zxsx3=3;
					}
						System.arraycopy(HexDecoding.int2byteArray2(zxsx1), 0, temp, 0, 2);										
						System.arraycopy(HexDecoding.int2byteArray2(zxsx2), 0, temp, 4, 2);
						System.arraycopy(HexDecoding.int2byteArray2(zxsx3), 0, temp, 8, 2);
					
					//设定速度
						if(speedint==0){speedint=10;}
						System.arraycopy(HexDecoding.int2byte(speedint), 0, temp, 16, 1);
					//加速度
						if(aspeedint==0){aspeedint=1;}
						System.arraycopy(HexDecoding.int2byte(aspeedint), 0, temp, 17, 1);
					//减速度
						if(dspeedint==0){dspeedint=1;}
						System.arraycopy(HexDecoding.int2byte(dspeedint), 0, temp, 18, 1);

				    formatSendMessage=new WifiSendDataFormat(temp, send_address_speed-16);	
					sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
					sendDataFinishRunnable=new FinishRunnable(getActivity(),backMessageToDo2);
					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
					getActivity().runOnUiThread(sendDataRunnable);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	}	
	private final Runnable backMessageToDo2 = new Runnable(){
		@Override
		public void run() {	
			speed.setText(String.valueOf(speedint));	
			aspeed.setText(String.valueOf(aspeedint));	
			dspeed.setText(String.valueOf(dspeedint));
			fsplist.get(3).put("line_value", zxsx1);
			fsplist.get(3).put("Bf_value", zxsx2);
			fsplist.get(3).put("ud_value", zxsx3);
			spmyAdapter.notifyDataSetChanged();
		}
	};
	private void initialsPList(byte[] backData) {

		try{
			fsplist.clear();
			HashMap<String, Object> map0 = new HashMap<String, Object>();
			map0.put("name_sp_setting", "当前位置(mm)");
			map0.put("line_value", "*****.*");
			map0.put("Bf_value", "*****.*");
			map0.put("ud_value", "*****.*");
			fsplist.add( map0);
			
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("name_sp_setting", "开始位置(mm)");
			
			Double foot_p=HexDecoding.Array4Toint(backData, 0)/100.0;
			Double productBA_p=HexDecoding.Array4Toint(backData, 4)/100.0;
			Double productUD_p=HexDecoding.Array4Toint(backData, 12)/100.0;
			
			if(foot_p>99999.9){
				map1.put("line_value", "*****.*");
			}else{
			    map1.put("line_value", String.format("%1$5.1f",foot_p));
			}
			
			if(productBA_p>99999.9){
				map1.put("Bf_value", "*****.*");
			}else{
			    map1.put("Bf_value",String.format("%1$5.1f",productBA_p));
			}
			
			if(productUD_p>99999.9){
				map1.put("ud_value", "*****.*");
			}else{
			    map1.put("ud_value", String.format("%1$5.1f",productUD_p));
			}
			fsplist.add( map1);

			HashMap<String, Object> map2 = new HashMap<String, Object>();
			map2.put("name_sp_setting", "装箱间距(mm)");
			map2.put("line_value", String.format("%1$5.1f",(HexDecoding.Array2Toint(backData, 32)/100.0)));
			map2.put("Bf_value",String.format("%1$5.1f",(HexDecoding.Array2Toint(backData, 34)/100.0)));
			map2.put("ud_value",String.format("%1$5.1f",(HexDecoding.Array2Toint(backData, 38)/100.0)));
			fsplist.add(map2);
			
			speedint=(int)(backData[80]&0xff);
			aspeedint=(int)(backData[81]&0xff);
			dspeedint=(int)(backData[82]&0xff);
			zxsx1=HexDecoding.Array2Toint(backData, 64);
			zxsx2=HexDecoding.Array2Toint(backData, 66);
			zxsx3=HexDecoding.Array2Toint(backData, 70);
			
			HashMap<String, Object> map7 = new HashMap<String, Object>();
			map7.put("name_sp_setting", "装箱顺序");
			map7.put("line_value", zxsx1);
			map7.put("Bf_value", zxsx2);
			map7.put("ud_value", zxsx3);
			fsplist.add(map7);
			
			HashMap<String, Object> map6 = new HashMap<String, Object>();
			map6.put("name_sp_setting", "装箱设定数");
			map6.put("line_value", String.valueOf(HexDecoding.Array2Toint(backData, 48)));
			map6.put("Bf_value", String.valueOf(HexDecoding.Array2Toint(backData, 50)));
			map6.put("ud_value", String.valueOf(HexDecoding.Array2Toint(backData, 54)));
			fsplist.add(map6);

			HashMap<String, Object> map5 = new HashMap<String, Object>();
			map5.put("name_sp_setting", "当前装箱数");
			map5.put("line_value", "");
			map5.put("Bf_value", "");
			map5.put("ud_value", "");
			fsplist.add(map5);
			
			speed.setText(String.valueOf(speedint));	
			aspeed.setText(String.valueOf(aspeedint));	
			dspeed.setText(String.valueOf(dspeedint));
			if(speedint==0||aspeedint==0||dspeedint==0||zxsx1==0||zxsx2==0||zxsx3==0){
				//获取设定速度，加速度，减速度信息,填写数据
				byte[] temp=new byte[19];
				try {		
					if(zxsx1==0||zxsx2==0||zxsx3==0){
						zxsx1=1;   zxsx2=2;   zxsx3=3;
					}
						System.arraycopy(HexDecoding.int2byteArray2(zxsx1), 0, temp, 0, 2);										
						System.arraycopy(HexDecoding.int2byteArray2(zxsx2), 0, temp, 4, 2);
						System.arraycopy(HexDecoding.int2byteArray2(zxsx3), 0, temp, 8, 2);
					
					//设定速度
						if(speedint==0){speedint=10;}
						System.arraycopy(HexDecoding.int2byte(speedint), 0, temp, 16, 1);
					//加速度
						if(aspeedint==0){aspeedint=1;}
						System.arraycopy(HexDecoding.int2byte(aspeedint), 0, temp, 17, 1);
					//减速度
						if(dspeedint==0){dspeedint=1;}
						System.arraycopy(HexDecoding.int2byte(dspeedint), 0, temp, 18, 1);

				    formatSendMessage=new WifiSendDataFormat(temp, send_address_speed-16);	
					sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
					sendDataFinishRunnable=new FinishRunnable(getActivity(),backMessageToDo2);
					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
					getActivity().runOnUiThread(sendDataRunnable);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}catch(Exception e){
		e.printStackTrace();
	}
	}	
	private void swithToSp(){
		NewPragramActivity.PosccalmRunnable.setTextView(null,null,null,null,null);
		FpLayout.setVisibility(View.VISIBLE);
		PositionLayout.setVisibility(View.GONE);
		FpTxt.setVisibility(View.GONE);
		FpEdtTxt.setVisibility(View.GONE);
		CurSelInfo.setText(getposname);
		if(FpSwitchSp.isChecked()){
//			spmyAdapter  =new SpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fpListView);
//			fpListView.setAdapter(spmyAdapter);

			spmyAdapter  =new SpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","foot_sp_setting","productBA_sp_setting","productUD_sp_setting"},fpListView,send_address_speed,posname);
			fpListView.setAdapter(spmyAdapter);
		}else{
			spmyAdapter  =new SpAdapter(getActivity(), FpSwitchSp, fsplist, new String[]{"name_sp_setting","line_value","Bf_value","ud_value"},fpListView,send_address_speed,posname);
			fpListView.setAdapter(spmyAdapter);
		}
		//根据点的信息确定点在数组中的位置，确定发送地址
				int readMessageAddr= send_address_speed - 10*Define.MAX_AXIS_NUM;//0x200026f0;

				if (WifiSetting_Info.mClient == null) {
					Toast.makeText(getActivity(),"请先连接主机", Toast.LENGTH_SHORT).show();
					return;
				}
				//与下位机通信，获取初始化listview的数据，一次读取36个字节，36个字节用于更新listview
				formatReadMessage=new WifiReadDataFormat(readMessageAddr, 84);

				try {

					sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

					sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕5",SPreadDataFinishTodo);

					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

					getActivity().runOnUiThread(sendDataRunnable);

				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
				}
	}
	//SP SP SP SP SP SP SP SP SP SP SP SP SP SP
  @Override 
    public void onResume() {
        // TODO Auto-generated method stub
    	Log.d("mpeng","onresume");
        if(posname.startsWith("P")){
        	send_address_speed=4*Define.MAX_AXIS_NUM+AddressPublic.model_P_point_Head
    				+(AddressPublic.model_SP_point_Head-AddressPublic.model_P_point_Head)/Define.MAX_STDPOINT_NUM
    				*(Integer.parseInt(posname.trim().substring(1))-1);
        	send_address_cc=send_address_speed;
        	send_address_acc=send_address_speed+1;
    		send_address_dcc=send_address_speed+2;
        	swithToPosition();
        }else if(posname.startsWith("FP")){
        	if(posname.equalsIgnoreCase("FP001")){
        		send_address_speed = 0x20003AB0;
        		send_address_pos=0x200021B0;
			}else if(posname.equalsIgnoreCase("FP002")){
				send_address_speed = 0x20003AC6;
				send_address_pos=0x20002E30;
			}
		send_address_cc=send_address_speed+18;
		send_address_acc=send_address_speed+19;
		send_address_dcc=send_address_speed+20;
		send_address_packcnt=send_address_speed+16;
        	swithToFp();
        }
        else if(posname.startsWith("SP")){
        	send_address_speed=10*Define.MAX_AXIS_NUM+AddressPublic.model_SP_point_Head
    				+(AddressPublic.model_FP_point_Head-AddressPublic.model_SP_point_Head)/Define.MAX_STDPACK_NUM
    				*(Integer.parseInt(posname.trim().substring(2))-1);
        	send_address_cc=send_address_speed;
        	send_address_acc=send_address_speed+1;
    		send_address_dcc=send_address_speed+2;
        	swithToSp();
        }
  super.onResume();
 }
   
    class PListener implements OnTouchListener {
		private  EditText myEt;
		private AlertDialog valueDialog;
		int touch_flag=0;

		PListener(EditText et) {
			myEt = et;
		}

		/* (non-Javadoc)
		 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
		 */
		@Override
		public boolean onTouch(final View v, MotionEvent event) {
			// TODO Auto-generated method stub
			touch_flag++;
			Log.e("mpeng"," the touch is "+touch_flag);
			if(touch_flag==2)
			{
			final EditText etEditText = new EditText(getActivity());
			// 限制只能输入0~9的数字和点号
			//设定位置，限制输入正负浮点数
			
			// 初始化滑动条，调整设定值
			TextView t = (TextView) v;
			String valueString = t.getText().toString();// 设定位置
			etEditText.setText(valueString);
			etEditText.setSelection(valueString.length());// 设置光标位置
			
			double foot_Doublez=0;
			try{
				foot_Doublez=Double.parseDouble(valueString);
		     }catch(Exception e){
		    	foot_Doublez=0;
			 }
			final double foot_Double=foot_Doublez;
			
			 valueDialog = new AlertDialog.Builder(getActivity())
			.setTitle("请添加设定值")
			.setView(etEditText)
			.setPositiveButton(R.string.OK,
					new DialogInterface.OnClickListener() {/* (non-Javadoc)
					 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
					 */
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						
						//写入完成后读取数据，然后更新Ui
						SendOverTodo  = new ChatListener() {
							
							@Override
							public void onChat(byte[] message) {
								// TODO Auto-generated method stub
								 int readMessageAddr=send_address_speed-4*Define.MAX_AXIS_NUM;
								 formatReadMessage=new WifiReadDataFormat(readMessageAddr, 36);
								 SendDataRunnable sendDataRunnable1 = new SendDataRunnable(formatReadMessage, getActivity());
								 sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕6",PreadDataFinishTodo);
								 sendDataRunnable1.setOnlistener(new NormalChatListenner(sendDataRunnable1, sendDataFinishRunnable));
								 getActivity().runOnUiThread(sendDataRunnable1);
							}
						};
						
                        SendOverTodo1  = new ChatListener() {
							@Override
							public void onChat(byte[] message) {
								Runnable speedShowRunnable=new Runnable() {
									public void run() {
										int readMessageAddr= send_address_speed - 10*Define.MAX_AXIS_NUM;//0x200026f0;

										if (WifiSetting_Info.mClient == null) {
											Toast.makeText(getActivity(),"请先连接主机", Toast.LENGTH_SHORT).show();
											return;
										}
										//与下位机通信，获取初始化listview的数据，一次读取36个字节，36个字节用于更新listview
										formatReadMessage=new WifiReadDataFormat(readMessageAddr, 84);

										try {

											sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

											sendDataFinishRunnable=new FinishRunnable(getActivity(), "数据读取完毕5",SPreadDataFinishTodo);

											sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

											getActivity().runOnUiThread(sendDataRunnable);

										} catch (Exception e) {
											// TODO: handle exception
											Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
										}
//										speed.setText(String.valueOf(speedint));	
//										aspeed.setText(String.valueOf(aspeedint));	
//										dspeed.setText(String.valueOf(dspeedint));
//										speed.setSelection(speed.getText().length());// 设置光标位置
//										aspeed.setSelection(aspeed.getText().length());// 设置光标位置
//										dspeed.setSelection(dspeed.getText().length());// 设置光标位置
									}
								};
								getActivity().runOnUiThread(speedShowRunnable);
							}
						};
						if(v.getId() == R.id.speed){//设定速度
							Log.e("mpeng","speedspeedspeedspeedspeedspeed");
							if(etEditText.getText().toString().equals("")){
								 Toast.makeText(getActivity(),"速度为空，请重新输入", Toast.LENGTH_SHORT).show();
								 return;
							 }
							 try{
								 Float.parseFloat(etEditText.getText().toString()); 
							  }catch(Exception e){
									e.printStackTrace();
									Toast.makeText(getActivity(),"数据有误，请重新输入",Toast.LENGTH_SHORT).show();
									return;
							 }
							 int ccint=(int)Float.parseFloat(etEditText.getText().toString());
							 if(ccint>100||ccint==0){
								 Toast.makeText(getActivity(),"速度范围1~100", Toast.LENGTH_SHORT).show();
								 return;
							 }
						
					         byte[] temp=new byte[1];
							System.arraycopy(HexDecoding.int2byte(ccint), 0, temp, 0, 1);
							if(posname.startsWith("P")){
								try {
								   sendDataRunnable = new SendDataRunnable(SendOverTodo, new WifiSendDataFormat(temp, send_address_cc), getActivity());
								   getActivity().runOnUiThread(sendDataRunnable);
							    } catch (Exception e) {
								   e.printStackTrace();
							    }
							}else{
//								speedint=ccint;
//								System.out.println("111111speedint==="+speedint);
								try {
									   sendDataRunnable = new SendDataRunnable(SendOverTodo1, new WifiSendDataFormat(temp, send_address_cc), getActivity());
									   getActivity().runOnUiThread(sendDataRunnable);
								    } catch (Exception e) {
									   e.printStackTrace();
								    }
							}
							
						}else if(v.getId() == R.id.aspeed){  //
							 if(etEditText.getText().toString().equals("")){
								 Toast.makeText(getActivity(),"加速度为空，请重新输入", Toast.LENGTH_SHORT).show();
								 return ;
							 }
							 try{
								 Float.parseFloat(etEditText.getText().toString()); 
							  }catch(Exception e){
									e.printStackTrace();
									Toast.makeText(getActivity(),"数据有误，请重新输入",Toast.LENGTH_SHORT).show();
									return ;
							 }
							 int accint=(int)Float.parseFloat(etEditText.getText().toString());
							 if(accint>5||accint==0){
								 Toast.makeText(getActivity(),"加速度范围1~5", Toast.LENGTH_SHORT).show();
								 return ;
							 }
							//设定速度
							 byte[] temp=new byte[1];
								System.arraycopy(HexDecoding.int2byte(accint), 0, temp, 0, 1);
								if(posname.startsWith("P")){
								try {
									sendDataRunnable=new SendDataRunnable(SendOverTodo,new WifiSendDataFormat(temp, send_address_acc), getActivity());
									getActivity().runOnUiThread(sendDataRunnable);
								} catch (Exception e) {
									e.printStackTrace();
								}
								}else{
									aspeedint=accint;
									try {
										sendDataRunnable=new SendDataRunnable(SendOverTodo1,new WifiSendDataFormat(temp, send_address_acc), getActivity());
										getActivity().runOnUiThread(sendDataRunnable);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							
						}else if(v.getId() == R.id.dspeed){  //
							
							 if(etEditText.getText().toString().equals("")){
								 Toast.makeText(getActivity(),"减速度为空，请重新输入", Toast.LENGTH_SHORT).show();
								 return ;
							 }
							 try{
								 Float.parseFloat(etEditText.getText().toString()); 
							  }catch(Exception e){
									e.printStackTrace();
									Toast.makeText(getActivity(),"数据有误，请重新输入",Toast.LENGTH_SHORT).show();
									return ;
							 }
							 int dccint=(int)Float.parseFloat(etEditText.getText().toString());
							 if(dccint>5||dccint==0){
								 Toast.makeText(getActivity(),"减速度范围1~5", Toast.LENGTH_SHORT).show();
								 return ;
							 }
							//设定速度
							 byte[] temp=new byte[1];
								System.arraycopy(HexDecoding.int2byte(dccint), 0, temp, 0, 1);
								if(posname.startsWith("P")){
								try {
									sendDataRunnable=new SendDataRunnable(SendOverTodo,new WifiSendDataFormat(temp, send_address_dcc), getActivity());
									getActivity().runOnUiThread(sendDataRunnable);
								} catch (Exception e) {
									e.printStackTrace();
								}
								}else{
									dspeedint=dccint;
									try {
										sendDataRunnable=new SendDataRunnable(SendOverTodo1,new WifiSendDataFormat(temp, send_address_dcc), getActivity());
										getActivity().runOnUiThread(sendDataRunnable);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								
						}else
						{
							String SendData = etEditText.getText().toString();
							 if (SendData.equals("") ) {
									Toast.makeText(getActivity(),"数据为空，请重新输入",Toast.LENGTH_SHORT).show();
									SendData = "";
									return ;
								}
							 try{
								 Double.parseDouble(SendData);
						     }catch(Exception e){
								e.printStackTrace();
								Toast.makeText(getActivity(),"数据有误，请重新输入",Toast.LENGTH_SHORT).show();
								return ;
							 }
								double editDouble=0;
								 if (!SendData.startsWith(".")) {
										if (SendData.contains(".")) {
											String[] editStrings = SendData.split("\\.");
											if (2 == editStrings.length) {
												editDouble = Double.parseDouble(SendData);
												if(Config.StopState){
													if (Math.abs(editDouble) >99999.9) {
														Toast.makeText(getActivity(),"数据超过范围，请重新输入",Toast.LENGTH_SHORT).show();
														SendData = "";
														return ;
													} else {
														SendData = String.format("%1$5.1f",editDouble);
													}
												}else{
													if (Math.abs(editDouble-foot_Double) >2.0) {
														Toast.makeText(getActivity(),"机器未处于停止状态，数据超过原位置±2.0mm，请重新输入",Toast.LENGTH_SHORT).show();
														SendData = "";
														return ;
													} else {
														SendData = String.format("%1$5.1f",editDouble);
													}
												}
												
											} else {
												Toast.makeText(getActivity(),"数据格式错误，请重新输入",Toast.LENGTH_SHORT).show();
												SendData = "";
												return ;
											}
										} else {
											editDouble = Double.parseDouble(SendData);
											if(Config.StopState){
												if (Math.abs(editDouble) >99999.9) {
													Toast.makeText(getActivity(),"数据超过范围，请重新输入",Toast.LENGTH_SHORT).show();
													SendData = "";
													return ;
												} else {
													SendData = String.format("%1$5.1f",editDouble);
												}
											}else{
												if (Math.abs(editDouble-foot_Double) >2.0) {
													Toast.makeText(getActivity(),"机器未处于停止状态，数据超过原位置±2.0mm，请重新输入",Toast.LENGTH_SHORT).show();
													SendData = "";
													return ;
												} else {
													SendData = String.format("%1$5.1f",editDouble);
												}
											}
											
										}

									} else {
										Toast.makeText(getActivity(),"数据格式错误，请重新输入",Toast.LENGTH_SHORT).show();
										SendData = "";
										return ;
									}
								
									final byte[] temp=new byte[4];
									
									int send_address=AddressPublic.model_P_point_Head
											+(AddressPublic.model_SP_point_Head-AddressPublic.model_P_point_Head)/Define.MAX_STDPOINT_NUM
											*(Integer.parseInt(posname.trim().substring(1))-1);
							
									 switch (v.getId()) {
										case R.id.xEditText:
											send_address=send_address;
											System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(SendData)*100)), 0, temp, 0, 4);
											break;
										case R.id.yEditText:
											send_address=send_address+4;
											System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(SendData)*100)), 0, temp, 0, 4);
											break;
										case R.id.hEditText:
											send_address=send_address+8;
											System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(SendData)*100)), 0, temp, 0, 4);
											break;
										case R.id.zEditText:
											send_address=send_address+12;
											System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(SendData)*100)), 0, temp, 0, 4);
											break;
				                        case R.id.lEditText:
				                        	send_address=send_address+16;
				                        	System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(SendData)*100)), 0, temp, 0, 4);
											break;
										default:
											break;
										}
									
//									WifiSendDataFormat posMemorySendDataFormat = new WifiSendDataFormat(temp, send_address);
//									sendDataRunnable = new SendDataRunnable(posMemorySendDataFormat, getActivity());
//										FinishRunnable memoryTodo = new FinishRunnable(getActivity(),"数据发送完毕");
//										sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, memoryTodo));
									 sendDataRunnable=new SendDataRunnable(SendOverTodo, new WifiSendDataFormat(temp, send_address), getActivity());
									 getActivity().runOnUiThread(sendDataRunnable);
						}
						
					}
				
			}).setNegativeButton(R.string.CANCEL,null).show();
			valueDialog.setOnDismissListener(new OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					// TODO Auto-generated method stub
					touch_flag=0;
				}
			});
			switch(v.getId())
			{
			case R.id.speed:
				InputFilter[] filters = {new InputFilter.LengthFilter(3)};				
				etEditText.setFilters(filters);					
				etEditText.setHint("支持格式为1-100正数");
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5', '6','7', '8', '9', '0'};
					}
					
					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// 数字键盘
					}
				});
				break;
			case R.id.aspeed:
			case R.id.dspeed:
				InputFilter[] filters1 = {new InputFilter.LengthFilter(1)};				
				etEditText.setFilters(filters1);					
				etEditText.setHint("支持格式为1-5正数");
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5'};
					}
					
					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// 数字键盘
					}
				});
				break;
			default:
				InputFilter[] filters11 = {new InputFilter.LengthFilter(7)};				
				etEditText.setFilters(filters11);	
				etEditText.setHint(             "支持格式为#####.#的正负数，整数最多5位，小数最多1位");
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5', '6', '7',
								'8', '9', '0', '.'  ,'+','-'};
					}

					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// 数字键盘
					}
				});
				break;
			}	
		}
			
			return false;
		}

		}

}
