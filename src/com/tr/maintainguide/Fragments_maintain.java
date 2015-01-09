package com.tr.maintainguide;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.tr.ExitTR;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.main.TR_Main_Activity.MyAdapter_Alarm;
import com.tr.programming.Config;
import com.tr.programming.TR_Programming_Activity;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiReadMassiveDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.AlarmQueryRunnable;
import wifiRunnablesAndChatlistener.CntCycQueryRunnable;
import wifiRunnablesAndChatlistener.DelayCount;
import wifiRunnablesAndChatlistener.DelayTimerQueryRunnble;
import wifiRunnablesAndChatlistener.DelayoptionQueryRunnble;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.ledRunnable;
import wifiRunnablesAndChatlistener.maintimeRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;

import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;
 
import com.explain.HexDecoding;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class Fragments_maintain extends Fragment{
	
	private Button btn_version;
	private Button btn_alarm;
	private Button btn_passwordSet;
	private Button btn_io;
	private Button btn_alarmzb;
	private Button btn_zqcx;
	private Button btn_maintimeset;
	
	private ListView listView_version;
	private ListView listView_alarm;
	private ListView listView_io;
	private ListView listView_alarmzb;
	
	private MyAdapter_Version adapter_version;
	private MyAdapter_Alarm adapter_alarm;
	private MyAdapter_IO adapter_io;
	private MyAdapter_Alarmzb adapter_alarmzb;
	
	private AlertDialog dialog_io;
	private AlertDialog dialog_timeset;
	ArrayList<HashMap<String, Object>> list_version ;
	ArrayList<HashMap<String, Object>> list_alarm ;
	ArrayList<HashMap<String, Object>> list_io ;
	ArrayList<HashMap<String, Object>> list_alarmzb;
	
	private int thisyear;
	private int thismonth;
	private int thisdate;
	private int thisweek;
	private int thishour;
	private int thismin;
	private int thissec;
	
	private TextView edityear;
	private TextView editmonth;
	private TextView editdate;
	private TextView textweek;
	private TextView edithour;
	private TextView editmin;
	private TextView editsec;
	private NumberPicker secPicker;
	protected WifiReadDataFormat formatReadtime;
	protected SendDataRunnable sendDatatimeRunnable;
	
	private SharedPreferences sharedPreference_password;
	RadioGroup radioGroup_password;
	Button btn_create_password;
	Button btn_change_password;
	Button btn_delete_password;
	private String password_operate;
	private String password_setting;
	private String password_programming;
	String password;
	String title_create;
	String title_delete;
	String title_change;
	
	String passwordToDelete;
    private SendDataRunnable sendDataRunnable;
	private FinishRunnable sendDataFinishRunnable;
	private WifiReadDataFormat formatReadMessage;
	private byte[] getData;
	private  ChatListener DataFeedback ;

	private SendDataRunnable sendDataAlmRunnable;
//	private FinishRunnable sendDataAlmFinishRunnable;
	private WifiReadMassiveDataFormat formatReadAlmmsg;
	private WifiReadDataFormat formatReadAlm;
	//private byte[] getData;
	private  ChatListener AlmDataFeedback ;	
	private ChatListener AlmCPFeedback;
	private int Almallnum;// ��ǰ�ŷ�����
	private int AlmCP;// ��ǰ�ŷ��������
	
	private SendDataRunnable sendDataioRunnable;
	private WifiReadDataFormat formatReadio;
	private  ChatListener ioDataFeedback ;
		
	
	private WifiSendDataFormat formatSendMessage;
	String password_old;
	String passwordToChange;
	
	private ledRunnable ledrunnable;
	private AlarmQueryRunnable alarmQueryRunnable;
	private CntCycQueryRunnable cntCycQueryRunnable;
	private maintimeRunnable maintimerunnable;
	
	private posccalmQueryRunnable PosccalmRunnable;
	private Thread PoccQueryThread;
	private Handler myHandler;
	private Thread DelayTimerQueryThread;
	private DelayTimerQueryRunnble DTQR;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Thread() {
			public void run() {
				
				list_version = ArrayListBound.getVersionListData();
				list_alarm = ArrayListBound.getAlarmListData();
				list_io = ArrayListBound.getIOListData();
			    list_alarmzb= ArrayListBound.getAlarmzbListData();
			}

		}.start();
		System.out.println(" Fragments_maintain alarmQueryRunnable11111");
//		alarmQueryRunnable = new AlarmQueryRunnable(getActivity());
//		Thread a2 = new Thread(alarmQueryRunnable);
//		a2.start();
//		ledrunnable=new ledRunnable(getActivity());
//		Thread a4 = new Thread(ledrunnable);
//		a4.start();
		if(PosccalmRunnable.existFlag)
			PosccalmRunnable.destroy();
		PosccalmRunnable = new posccalmQueryRunnable(getActivity());
		PoccQueryThread = new Thread(PosccalmRunnable);
		PoccQueryThread.start();
	}
	
//	String password_create;
//	AlertDialog dialog_create_again;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Fragments_maintain   onCreate ");
		myHandler = new Handler(){

			/* (non-Javadoc)
			 * @see android.os.Handler#dispatchMessage(android.os.Message)
			 */
			@Override
			public void dispatchMessage(Message msg) {
				// TODO Auto-generated method stub
				super.dispatchMessage(msg);
				if(!DelayTimerQueryThread.isAlive())
				{

				DTQR = new DelayTimerQueryRunnble(getActivity(), listView_io);
				DelayTimerQueryThread=new Thread(DTQR) ;
			    DelayTimerQueryThread.start();
				}
				else 
				{
					myHandler.sendEmptyMessageDelayed(111, 500);
				}
			}
			
			
		};
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//DelayTimerQueryRunnble.destroy();
		if(dialog_timeset!=null){
			dialog_timeset.dismiss();
		}
		if(dialog_io!=null){
			dialog_io.dismiss();
		}
//		
//		alarmQueryRunnable.destroy();
//		ledrunnable.destroy();
		PosccalmRunnable.destroy();
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("Fragments_maintain   onCreateView ");
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		return inflater.inflate(R.layout.tab_maintainguide_maintain, container,false);
	}

	public void getAlmdata(){
		System.out.println("Almallnum="+Almallnum+"       AlmCP======="+AlmCP);
		formatReadAlmmsg = new WifiReadMassiveDataFormat(0x40024304,12*128);
		try {
			sendDataAlmRunnable=new SendDataRunnable(formatReadAlmmsg, getActivity());

			sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",readAlmDataFinishTodo);

			sendDataAlmRunnable.setOnlistener(new NormalChatListenner(sendDataAlmRunnable, sendDataFinishRunnable));

			getActivity().runOnUiThread(sendDataAlmRunnable);
	
			} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
		}
	}
	//��ȡ���ص����ݺ���е�UI����
	private final Runnable readAlmDataFinishTodo = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//���ڷ��ص�36�ֽ�
			//������ȷ�����
			//�����ص�����
			try{
			getData=new byte[formatReadAlmmsg.getLength()];
			//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
			if( formatReadAlmmsg.getFinalData() != null)  
			{
			System.arraycopy(formatReadAlmmsg.getFinalData(), 0, getData, 0, formatReadAlmmsg.getLength());
			int i = AlmCP;
			// ����ʾ������˵ 
			for (int j = 0; j < AlmCP; j++) { // ��ô��̬ȥ��
				String str;
				i = i - 1;
				int alm = (int)getData[12*i];
				switch( alm )
				{
				case 0:
					str = " �� ";
					break;
				case 1:		// ϵͳ����
					str = " ϵͳ����";
					break;
				case 2:		// �ŷ�����
					str = " �ŷ�����";
					break;
				case 3:		// 	��������
					str = " ��������";
					break;
				default:
					str = "��������";
					break;
				}
				if( alm == 0)
					continue;

				alm = HexDecoding.Array2Toint(getData, 12*i+2);
				if(str.equals(" �ŷ�����")){
					int seralm=alm/256;
					switch( seralm )
					{
					case 0:
						str = " �ŷ�һ����";
						break;
					case 1:		
						str = " �ŷ�������";
						break;
					case 2:		
						str = " �ŷ�������";
						break;
					case 3:		
						str = " �ŷ��ľ���";
						break;
					case 4:
						str = " �ŷ��徯��";
						break;
					case 5:		
						str = " �ŷ�������";
						break;
					case 6:		
						str = " �ŷ��߾���";
						break;
					case 7:		
						str = " �ŷ��˾���";
						break;
					default:
						break;
					}
					str += alm%256;
				}else{
					str += String.valueOf(alm);	
				}
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("num_alarm","����"+ (j+1) );
 				map.put("time_alarm", str);
 				
 				str = "20";
 				alm = getData[12*i+4];
 				str += String.valueOf(alm);
 				alm = getData[12*i+5];
 				str += "-";
 				str += String.valueOf(alm);
 				str += "-";
 				alm = getData[12*i+6];
 				str += String.valueOf(alm);
 				
 				str += "  ";
 				alm = getData[12*i+8];
 				str += String.valueOf(alm);
 				
 				str += ":";
 				alm = getData[12*i+9];
 				str += String.valueOf(alm);
 				
 				str += ":";
 				alm = getData[12*i+10];
 				str += String.valueOf(alm);
 				
 				
 				map.put("content_alarm", str);
 				list_alarm.add(map);
			}
			int k = Almallnum-AlmCP;
			for (int j = 0; j < k; j++) { // ��ô��̬ȥ��
				String str;
				int alm = (int)getData[12*(128-1-j)];
				switch( alm )
				{
				case 0:
					str = " �� ";
					break;
				case 1:		// ϵͳ����
					str = " ϵͳ����";
					break;
				case 2:		// �ŷ�����
					str = " �ŷ�����";
					break;
				case 3:		// 	��������
					str = " ��������";
					break;
				default:
					str = "��������";
					break;
				}
				if( alm == 0)
					continue;

				alm = HexDecoding.Array2Toint(getData, 12*(128-1-j)+2);
				if(str.equals(" �ŷ�����")){
					int seralm=alm/256;
					switch( seralm )
					{
					case 0:
						str = " �ŷ�һ����";
						break;
					case 1:		
						str = " �ŷ�������";
						break;
					case 2:		
						str = " �ŷ�������";
						break;
					case 3:		
						str = " �ŷ��ľ���";
						break;
					case 4:
						str = " �ŷ��徯��";
						break;
					case 5:		
						str = " �ŷ�������";
						break;
					case 6:		
						str = " �ŷ��߾���";
						break;
					case 7:		
						str = " �ŷ��˾���";
						break;
					default:
						break;
					}
					str += alm%256;
				}else{
					str += String.valueOf(alm);	
				}
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("num_alarm","����"+ (j+1+AlmCP) );
 				map.put("time_alarm", str);
 				
 				str = "20";
 				alm = getData[12*(128-1-j)+4];
 				str += String.valueOf(alm);
 				alm = getData[12*(128-1-j)+5];
 				str += "-";
 				str += String.valueOf(alm);
 				str += "-";
 				alm = getData[12*(128-1-j)+6];
 				str += String.valueOf(alm);
 				
 				str += "  ";
 				alm = getData[12*(128-1-j)+8];
 				str += String.valueOf(alm);
 				
 				str += ":";
 				alm = getData[12*(128-1-j)+9];
 				str += String.valueOf(alm);
 				
 				str += ":";
 				alm = getData[12*(128-1-j)+10];
 				str += String.valueOf(alm);
 				
 				
 				map.put("content_alarm", str);
 				list_alarm.add(map);
			}
			    adapter_alarm=new MyAdapter_Alarm(getActivity(), list_alarm, R.layout.maintain_guide_alarm_item, 
						 new String[] { "num_alarm", "time_alarm", "content_alarm"},
						 new int[] { R.id.num_alarm, R.id.time_alarm,R.id.content_alarm});
				 listView_alarm.setAdapter(adapter_alarm);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		}

	};
	//��ȡ���ص����ݺ���е�UI����
		private final Runnable readversionDataFinishTodo = new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//���ڷ��ص�36�ֽ�
				//������ȷ�����
				//�����ص�����	
				getData=new byte[formatReadMessage.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				if( formatReadMessage.getFinalData() != null)  
				{
				System.arraycopy(formatReadMessage.getFinalData(), 0, getData, 0, formatReadMessage.getLength());
				//���¸�list_usermode�б�ֵ
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("boot", "�����ݰ汾��");
				map.put("boot_version", "");
				map.put("data", "");
				map.put("data_version", "");
				list_version.set(0,map);
				
				HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("boot", "ϵͳTable�汾");
				map1.put("boot_version", "");
				map1.put("data", "��е�����汾");
				map1.put("data_version", "");	
				list_version.set(1, map1);
			
				HashMap<String, Object> map2 = new HashMap<String, Object>();
				map2.put("boot", "");
				map2.put("boot_version", "");
				map2.put("data", "");
				map2.put("data_version", "");
				list_version.set(2, map2);
				
				HashMap<String, Object> map3 = new HashMap<String, Object>();
				map3.put("boot", "��boot�汾��");
				map3.put("boot_version", "");
				map3.put("data", "");
				map3.put("data_version", "");
				list_version.set(3, map3);
				
				HashMap<String, Object> map4 = new HashMap<String, Object>();
				map4.put("boot", "CPU");
				int ver = HexDecoding.Array4Toint(getData, 0);
				String str = String.valueOf(ver);
				map4.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 8);
				str = String.valueOf(ver);
				map4.put("data", "PPU");
				map4.put("data_version", str);
				list_version.set(4, map4);
				
				
				HashMap<String, Object> map5 = new HashMap<String, Object>();
				map5.put("boot", "");
				map5.put("boot_version", "");
				map5.put("data", "");
				map5.put("data_version", "");
				list_version.set(5, map5);
				
				HashMap<String, Object> map6 = new HashMap<String, Object>();
				map6.put("boot", "��ϵͳ����汾��");
				map6.put("boot_version", "");
				map6.put("data", "");
				map6.put("data_version", "");
				list_version.set(6, map6);
				
				HashMap<String, Object> map7 = new HashMap<String, Object>();
				map7.put("boot", "CPU");
				ver = HexDecoding.Array4Toint(getData, 4);
				str = String.valueOf(ver);
				map7.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 12);
				str = String.valueOf(ver);
				map7.put("data", "PPU");
				map7.put("data_version", str);
				list_version.set(7, map7);
				
				HashMap<String, Object> map8 = new HashMap<String, Object>();
				map8.put("boot", "ƽ�����汾");
				map8.put("boot_version", "14100010(�ڲ�ʹ��)");
				ver = HexDecoding.Array4Toint(getData, 112);
				str = String.valueOf(ver);
				map8.put("data", "�����г���汾");
				map8.put("data_version", str);
				list_version.set(8,map8);
				
				HashMap<String, Object> map9 = new HashMap<String, Object>();
				ver = HexDecoding.Array4Toint(getData, 116);
				str = String.valueOf(ver);
				map9.put("boot", "�����������汾");
				map9.put("boot_version", str);
				map9.put("data", "");
				map9.put("data_version", "");
				list_version.set(9,map9);
				
				HashMap<String, Object> map10 = new HashMap<String, Object>();
				map10.put("boot", "");
				map10.put("boot_version", "");
				map10.put("data", "");
				map10.put("data_version", "");
				list_version.set(10, map10);
				
				HashMap<String, Object> map11 = new HashMap<String, Object>();
				map11.put("boot", "��IOϵͳ�汾��");
				map11.put("boot_version", "");
				map11.put("data", "");
				map11.put("data_version", "");
				list_version.set(11, map11);
				
				HashMap<String, Object> map12 = new HashMap<String, Object>();
				map12.put("boot", "IO1�汾");
				ver = HexDecoding.Array4Toint(getData, 16);
				str = String.valueOf(ver);
				map12.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 20);
				str = String.valueOf(ver);
				map12.put("data", "IO2�汾");
				map12.put("data_version", str);
				list_version.set(12, map12);
				
				HashMap<String, Object> map13 = new HashMap<String, Object>();
				map13.put("boot", "IO3�汾");
				ver = HexDecoding.Array4Toint(getData, 24);
				str = String.valueOf(ver);
				map13.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 28);
				str = String.valueOf(ver);
				map13.put("data", "IO4�汾");
				map13.put("data_version", str);
				list_version.set(13, map13);
				
				HashMap<String, Object> map14 = new HashMap<String, Object>();
				map14.put("boot", "IO5�汾");
				ver = HexDecoding.Array4Toint(getData, 32);
				str = String.valueOf(ver);
				map14.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 36);
				str = String.valueOf(ver);
				map14.put("data", "IO6�汾");
				map14.put("data_version", str);
				list_version.set(14, map14);
				
				HashMap<String, Object> map15 = new HashMap<String, Object>();
				map15.put("boot", "IO7�汾");
				ver = HexDecoding.Array4Toint(getData, 40);
				str = String.valueOf(ver);
				map15.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 44);
				str = String.valueOf(ver);
				map15.put("data", "IO8�汾");
				map15.put("data_version", str);
				list_version.set(15, map15);
				
				HashMap<String, Object> map16 = new HashMap<String, Object>();
				map16.put("boot", "");
				map16.put("boot_version", "");
				map16.put("data", "");
				map16.put("data_version", "");
				list_version.set(16,map16);
				
				HashMap<String, Object> map17 = new HashMap<String, Object>();
				map17.put("boot", "��SPUϵͳ�汾��");
				map17.put("boot_version", "");
				map17.put("data", "");
				map17.put("data_version", "");
				list_version.set(17,map17);
				
				HashMap<String, Object> map18 = new HashMap<String, Object>();
				map18.put("boot", "SPU1�汾");
				ver = HexDecoding.Array4Toint(getData, 48);
				str = String.valueOf(ver);
				map18.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 52);
				str = String.valueOf(ver);
				map18.put("data", "SPU2�汾");
				map18.put("data_version", str);
				list_version.set(18, map18);
				
				HashMap<String, Object> map19 = new HashMap<String, Object>();
				map19.put("boot", "SPU3�汾");
				ver = HexDecoding.Array4Toint(getData, 56);
				str = String.valueOf(ver);
				map19.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 60);
				str = String.valueOf(ver);
				map19.put("data", "SPU4�汾");
				map19.put("data_version", str);
				list_version.set(19, map19);
				
				HashMap<String, Object> map20 = new HashMap<String, Object>();
				map20.put("boot", "SPU5�汾");
				ver = HexDecoding.Array4Toint(getData, 64);
				str = String.valueOf(ver);
				map20.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 68);
				str = String.valueOf(ver);
				map20.put("data", "SPU6�汾");
				map20.put("data_version", str);
				list_version.set(20, map20);
				
				HashMap<String, Object> map21 = new HashMap<String, Object>();
				map21.put("boot", "SPU7�汾");
				ver = HexDecoding.Array4Toint(getData, 72);
				str = String.valueOf(ver);
				map21.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 76);
				str = String.valueOf(ver);
				map21.put("data", "SPU8�汾");
				map21.put("data_version", str);
				list_version.set(21, map21);
				
				HashMap<String, Object> map22 = new HashMap<String, Object>();
				map22.put("boot", "");
				map22.put("boot_version", "");
				map22.put("data", "");
				map22.put("data_version", "");
				list_version.set(22, map22);
				
				HashMap<String, Object> map23 = new HashMap<String, Object>();
				map23.put("boot", "���ŷ��汾��");
				map23.put("boot_version", "");
				map23.put("data", "");
				map23.put("data_version", "");
				list_version.set(23, map23);
				
				HashMap<String, Object> map24 = new HashMap<String, Object>();
				map24.put("boot", "�ŷ�1�汾");
				ver = HexDecoding.Array4Toint(getData, 80);
				str = String.valueOf(ver);
				map24.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 84);
				str = String.valueOf(ver);
				map24.put("data", "�ŷ�2�汾");
				map24.put("data_version", str);
				list_version.set(24, map24);
				
				HashMap<String, Object> map25 = new HashMap<String, Object>();
				map25.put("boot", "�ŷ�3�汾");
				ver = HexDecoding.Array4Toint(getData, 88);
				str = String.valueOf(ver);
				map25.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 92);
				str = String.valueOf(ver);
				map25.put("data", "�ŷ�4�汾");
				map25.put("data_version", str);
				list_version.set(25, map25);
				
				HashMap<String, Object> map26 = new HashMap<String, Object>();
				map26.put("boot", "�ŷ�5�汾");
				ver = HexDecoding.Array4Toint(getData, 96);
				str = String.valueOf(ver);
				map26.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 100);
				str = String.valueOf(ver);
				map26.put("data", "�ŷ�6�汾");
				map26.put("data_version", str);
				list_version.set(26, map26);
				
				HashMap<String, Object> map27 = new HashMap<String, Object>();
				map27.put("boot", "�ŷ�7�汾");
				ver = HexDecoding.Array4Toint(getData, 104);
				str = String.valueOf(ver);
				map27.put("boot_version", str);
				ver = HexDecoding.Array4Toint(getData, 108);
				str = String.valueOf(ver);
				map27.put("data", "�ŷ�8�汾");
				map27.put("data_version", str);
				list_version.set(27, map27);
				adapter_version.notifyDataSetChanged();
					
			}
			}

		};
		
		//��ȡ���ص����ݺ���е�UI����
		private final Runnable readioDataFinishTodo = new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//���ڷ��ص�36�ֽ�
				//������ȷ�����
				//�����ص�����	
				getData=new byte[formatReadio.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				if( formatReadio.getFinalData() != null)  
				{
				System.arraycopy(formatReadio.getFinalData(), 0, getData, 0, formatReadio.getLength());
				//���¸�list_usermode�б�ֵ
				for (int i = 0; i < 128; i++) {
	 				HashMap<String, Object> map = new HashMap<String, Object>();
	 				map.put("io_address",i);
	 				map.put("i_symbolName", ArrayListBound.getDeviceActualInputListData().get(i).get("symbolNameEditText"));
	 				map.put("i_signalName", ArrayListBound.getDeviceActualInputListData().get(i).get("signalNameEditText"));
	 				map.put("o_symbolName", ArrayListBound.getDeviceActualOutputListData().get(i).get("symbolNameEditText"));
	 				map.put("o_signalName", ArrayListBound.getDeviceActualOutputListData().get(i).get("signalNameEditText")); 
	 				 for(int j=0;j<8;j++){
				    	 if(i%8==j){
				    		 map.put("i_state", (int)((getData[i/8]>>j)&0x01));
				    		 map.put("o_state", (int)((getData[i/8+16]>>j)&0x01));
				    	 }	
				    }
	 				if(map.get("i_symbolName").equals("")&&map.get("o_symbolName").equals("")){
	 				}else{
	 				list_io.add(map);
	 				}
				}
					 adapter_io=new MyAdapter_IO(getActivity(), list_io, R.layout.maintainguide_maintain_io_item, 
							 new String[] { "io_address", "i_symbolName", "i_signalName","i_state", "o_symbolName", "o_signalName","o_state"},
							 new int[] { R.id.io_address, R.id.i_symbolName,R.id.i_signalName,R.id.i_state, R.id.o_symbolName,R.id.o_signalName,R.id.o_state});
					 listView_io.setAdapter(adapter_io);
//					 DelayCountTimeQuery tempCount=new DelayCountTimeQuery(500, 100);
//					  tempCount.start();
//					 myHandler.sendEmptyMessageDelayed(111, 150);
					 DTQR = new DelayTimerQueryRunnble(getActivity(), listView_io);
						DelayTimerQueryThread=new Thread(DTQR) ;
					    DelayTimerQueryThread.start();
					    Config.isMutiThread = true;
					 listView_io.setOnScrollListener(new AbsListView.OnScrollListener() {
							
							@Override
							public void onScrollStateChanged(AbsListView view, int scrollState) {
								// TODO Auto-generated method stub
								if(scrollState==OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
									System.out.print("�ִ�����");
								}
	                            if(scrollState==OnScrollListener.SCROLL_STATE_FLING){
	                            	System.out.print("���Ի���");
								}
	                            if(scrollState==OnScrollListener.SCROLL_STATE_IDLE){
	                            	System.out.print("����ֹͣ");
	                            }
	                            DTQR.destroy();
	                            myHandler.sendEmptyMessageDelayed(111, 150);
							}
							
							@Override
							public void onScroll(AbsListView view, int firstVisibleItem,
									int visibleItemCount, int totalItemCount) {
								// TODO Auto-generated method stub
								//��ʱ��ʼ��ѯ����listview������ɺ�ʼ��ѯ
								  
							}
						});
			}
			}

		};

	@Override
	public void onStart(){
		super.onStart();
		 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.out.println("Fragments_maintain   onActivityCreated ");
		
		sharedPreference_password = getActivity().getSharedPreferences("password",Context.MODE_PRIVATE);// passwordΪxml�ļ���
		//��ʱ����洢��ֵ
//		sharedPreference_password.edit().putString("password_operate", "").commit();
//		sharedPreference_password.edit().putString("password_setting", "").commit();
//		sharedPreference_password.edit().putString("password_programing", "").commit();
		// �õ��ļ��е�name��ǩֵ���ڶ���������ʾ���û�������ǩ�Ļ������ص�Ĭ��ֵ
//		password_operate = sharedPreference_password.getString("password_operate", "");// ���languageΪ���Դ��ţ�����һ����ͬ
//		password_setting = sharedPreference_password.getString("password_setting", "");
//		password_programming = sharedPreference_password.getString("password_programming", "");
		btn_maintimeset=(Button) getActivity().findViewById(R.id.btn_maintimeSet);
		if(btn_maintimeset==null){
			return;
		}
		btn_maintimeset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				View view_timeset=View.inflate(getActivity(), R.layout.maintainguide_maintain_maintimeset, null);
				dialog_timeset=new AlertDialog.Builder(getActivity())
				.setTitle("MAIN����ʱ���趨").setView(view_timeset)
				.setPositiveButton("�趨",
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						byte[] temp=new byte[8];
						try {
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thisyear)-2000), 0, temp,0, 1);
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thismonth)), 0, temp,1, 1);
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thisdate)), 0, temp,2, 1);
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thisweek)), 0, temp,3, 1);
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thishour)), 0, temp,4, 1);
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thismin)), 0, temp,5, 1);
						System.arraycopy(HexDecoding.int2byte(Integer.valueOf(thissec)), 0, temp,6, 1);
						  formatSendMessage=new WifiSendDataFormat(temp,0x1000B100);
	                        
	                        	sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());

								sendDataFinishRunnable=new FinishRunnable(getActivity(), "MAIN����ʱ���趨��Ϣ�ɹ����͸���λ��");

								sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

								getActivity().runOnUiThread(sendDataRunnable);
								} catch (Exception e) {
								// TODO: handle exception
								  e.printStackTrace();
								  Toast.makeText(getActivity(), "�����������������룡", Toast.LENGTH_SHORT).show();
							    }
						 try {
			                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			                    field.setAccessible(true);
			                    field.set(dialog, false);
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }

				    }
				}).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						try {
							 Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
							 field.setAccessible(true);
							 field.set(dialog, true);
							 } catch (Exception e) {
							 e.printStackTrace();
							 } 
					}
				}).show();
				        edityear=(TextView)dialog_timeset.findViewById(R.id.editText_year);
						editmonth=(TextView)dialog_timeset.findViewById(R.id.editText_month);
						editdate=(TextView)dialog_timeset.findViewById(R.id.editText_date);
						textweek=(TextView)dialog_timeset.findViewById(R.id.week);
						edithour=(TextView)dialog_timeset.findViewById(R.id.editText_hour);
						editmin=(TextView)dialog_timeset.findViewById(R.id.editText_min);
						editsec=(TextView)dialog_timeset.findViewById(R.id.editText_sec);
						secPicker=(NumberPicker)dialog_timeset.findViewById(R.id.date_picker);
						if(secPicker==null){return;}
						secPicker.setMinValue(0);
						secPicker.setMaxValue(59);
					    
						if(edityear==null){return;}
						if(editmonth==null){return;}
						if(editdate==null){return;}
						if(textweek==null){return;}
						if(edithour==null){return;}
						if(editmin==null){return;}
						if(editsec==null){return;}
						
						DatePicker datePicker=(DatePicker)dialog_timeset.findViewById(R.id.datePicker);
				        TimePicker timePicker=(TimePicker)dialog_timeset.findViewById(R.id.timePicker);
				        if(datePicker==null){return;}
						if(timePicker==null){return;}
				        final Calendar calendar=Calendar.getInstance();
				        int cyear=calendar.get(Calendar.YEAR);
				        int monthOfYear=calendar.get(Calendar.MONTH);
				        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
				        thisyear=cyear;
		            	thismonth=monthOfYear+1;
		            	thisdate=dayOfMonth;
		                calendar.set(cyear,monthOfYear,dayOfMonth);
		                thisweek = calendar.get(Calendar.DAY_OF_WEEK);
		                thishour=calendar.get(Calendar.HOUR_OF_DAY);
		            	thismin=calendar.get(Calendar.MINUTE);
		            	thissec=calendar.get(Calendar.SECOND);
				        datePicker.init(cyear, monthOfYear, dayOfMonth, new OnDateChangedListener(){

				            public void onDateChanged(DatePicker view, int year,
				                    int monthOfYear, int dayOfMonth) {
				            	thisyear=year;
				            	thismonth=monthOfYear+1;
				            	thisdate=dayOfMonth;
				                calendar.set(year,monthOfYear,dayOfMonth);
				                thisweek = calendar.get(Calendar.DAY_OF_WEEK);
				            }
				            
				        });
				        timePicker.setIs24HourView(true);// �����Ƿ�Ϊ24Сʱ��    
				        timePicker.setOnTimeChangedListener(new OnTimeChangedListener(){
				            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				            	thishour=hourOfDay;
				            	thismin=minute;
				            }
				            
				        });
				        secPicker.setValue(thissec);
				        secPicker.setOnValueChangedListener(new OnValueChangeListener(){

				            @Override
				            public void onValueChange(NumberPicker picker, int oldVal,int newVal) {
				                // TODO Auto-generated method stub
				            	thissec=newVal;
				            }
				            
				        });

				      //�����˵��Ĺر��¼�  
				        dialog_timeset.setOnDismissListener(new OnDismissListener() {              
				            
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								maintimerunnable.destroy();
								Config.isMutiThread= false;
							}  
				        }); 
				        Config.isMutiThread= true;
				        maintimerunnable = new maintimeRunnable(
								getActivity(), edityear,editmonth,editdate,textweek,edithour,editmin,editsec);
						
						Thread a3 = new Thread(maintimerunnable);
						a3.start();
						
						
			}
		});	
		
		
		btn_alarmzb=(Button) getActivity().findViewById(R.id.button3);
		if(btn_alarmzb==null){
			return;
		}
		btn_alarmzb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				View view_alarmzb=View.inflate(getActivity(), R.layout.maintainguide_maintain_alarmzb, null);
				AlertDialog dialog_alarmzb=new AlertDialog.Builder(getActivity())
				.setTitle("�����ܱ�").setView(view_alarmzb).setNegativeButton("ȡ��",null).show();
				listView_alarmzb = (ListView) dialog_alarmzb
						.findViewById(R.id.listView_alarmzb);
				if(listView_alarmzb==null){
					return;
				}
				//ArrayList<HashMap<String, Object>> list_alarmzb= ArrayListBound.getAlarmzbListData();
				adapter_alarmzb = new MyAdapter_Alarmzb(getActivity(), list_alarmzb,
						R.layout.maintainguide_maintain_alarmzb_item,
						new String[] { "alarmzbnum", "alarmzbName", "alarmzbmsg" },
						new int[] { R.id.alarmzbnum, R.id.alarmzbName, R.id.alarmzbmsg});
				listView_alarmzb.setAdapter(adapter_alarmzb);
			}
		});	
		
		
			
		btn_version=(Button) getActivity().findViewById(R.id.btn_version);
		if(btn_version==null){
			return;
		}
		btn_version.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				View view_version = View.inflate(getActivity(),R.layout.maintain_guide_version_dispaly, null);
				
				// �ȷ������ݣ�Ȼ��ȴ�20ms���Ȱ汾���ݶ����ˣ��ٵ��뵽��ʾ�ĶԻ���
				//========================	

					//int readAddr = 0x1000B180;  //!!!!!!ע��õ�ַ�ı仯
					
					//================
						AlertDialog dialog_version = new AlertDialog.Builder(
						getActivity()).setTitle("�汾��ʾ").setView(view_version)
						.setNegativeButton("ȡ��",null).show();
				listView_version = (ListView) dialog_version
						.findViewById(R.id.listView_version);
				if(listView_version==null){
					return;
				}
				
				adapter_version = new MyAdapter_Version(getActivity(), list_version,
						R.layout.maintain_guide_version_item,
						new String[] { "boot", "boot_version", "data","data_version" },
						new int[] { R.id.boot, R.id.boot_version, R.id.data,R.id.data_version });
				listView_version.setAdapter(adapter_version);
				if (WifiSetting_Info.mClient == null) {
					Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
					return;
				}
				formatReadMessage = new WifiReadDataFormat(0x1000B110,30*4);
				try {
					sendDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());

					sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",readversionDataFinishTodo);

					sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

					getActivity().runOnUiThread(sendDataRunnable);

				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
				}


			}
		});
		/**
		 * ******************************
		 * ��������С����
		 * ******************************
		 */
		/** ��ȡ�ŷ��������ʱ��ͨ���߳��յ���Ϣʱ�Ĵ�����. */
		AlmCPFeedback = new ChatListener() {
			@Override
			public void onChat(byte[] message) {

				//���صı�־λSTS1�����ж��Լ���У��
				formatReadAlm.backMessageCheck(message);
				if(!formatReadAlm.finishStatus()){
					getActivity().runOnUiThread(sendDataAlmRunnable);			
				}else {
					//������ȷ�����
					//�����ص�����	
					getData=new byte[formatReadAlm.getLength()];
					//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
					System.arraycopy(message, 8, getData, 0, formatReadAlm.getLength());
					Almallnum= HexDecoding.Array4Toint(getData, 0);
					// ��ǰ�ŷ��������
					AlmCP= HexDecoding.Array4Toint(getData, 4);
					}
				getAlmdata();
			}
		};
		
		btn_alarm=(Button) getActivity().findViewById(R.id.btn_alarm);
		if(btn_alarm==null){
			return;
		}
		btn_alarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (WifiSetting_Info.mClient == null) {
					Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
					return;
				}
				list_alarm.clear();
				View view_alarm=View.inflate(getActivity(), R.layout.maintain_guide_alarm, null);
				AlertDialog dialog_alarm=new AlertDialog.Builder(getActivity()).setTitle("��������").setView(view_alarm).setNegativeButton("ȡ��",null).show();
						
				 listView_alarm=(ListView) dialog_alarm.findViewById(R.id.listView_alarm);
					if(listView_alarm==null){
						return;
					}
					formatReadAlm = new WifiReadDataFormat(0x400242FC,8);
					try {

						sendDataAlmRunnable = new SendDataRunnable(AlmCPFeedback, formatReadAlm,getActivity());
						getActivity().runOnUiThread(sendDataAlmRunnable);
					
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
					}
				 
				 
			}
		});
		
		btn_zqcx=(Button) getActivity().findViewById(R.id.button2);
		if(btn_zqcx==null){
			return;
		}
		btn_zqcx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				View view_io=View.inflate(getActivity(), R.layout.maintain_guide_periodicsurveillance, null);
				dialog_io=new AlertDialog.Builder(getActivity()).setTitle("���ڼ��ӣ�s��").setView(view_io).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
//						cntCycQueryRunnable.destroy();
						PosccalmRunnable.destroy();
					}
				}).show();
				//�����˵��Ĺر��¼�  
				dialog_io.setOnDismissListener(new OnDismissListener() {              
		            
					@Override
					public void onDismiss(DialogInterface arg0) {
						// TODO Auto-generated method stub
//						cntCycQueryRunnable.destroy();
						if(PosccalmRunnable.existFlag)
							PosccalmRunnable.destroy();
						PosccalmRunnable = new posccalmQueryRunnable(getActivity());
						PoccQueryThread = new Thread(PosccalmRunnable);
						PoccQueryThread.start();
					}  
		        }); 
				if(PosccalmRunnable.existFlag)
					PosccalmRunnable.destroy();
				PosccalmRunnable = new posccalmQueryRunnable(getActivity(), dialog_io.findViewById(R.id.periodic_modeling),
						dialog_io.findViewById(R.id.periodic_out), dialog_io.findViewById(R.id.periodic_full));
				PoccQueryThread = new Thread(PosccalmRunnable);
				PoccQueryThread.start();
				
//				cntCycQueryRunnable = new CntCycQueryRunnable(
//						getActivity(), dialog_io.findViewById(R.id.periodic_modeling),
//						dialog_io.findViewById(R.id.periodic_out), dialog_io.findViewById(R.id.periodic_full),1);
//				
//				Thread a3 = new Thread(cntCycQueryRunnable);
//				a3.start();
			}
		});
		
		btn_io=(Button) getActivity().findViewById(R.id.button5);
		if(btn_io==null){
			return;
		}
		btn_io.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (WifiSetting_Info.mClient == null) {
					Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
					return;
				}
				list_io.clear();
				View view_io=View.inflate(getActivity(), R.layout.maintainguide_maintain_io, null);
				dialog_io=new AlertDialog.Builder(getActivity()).setTitle("IO����").setView(view_io).setNegativeButton("ȡ��",new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
//						 DelayTimerQueryRunnble.destroy();
						 DTQR.destroy();
						 Config.isMutiThread = false;
					}
				}).show();
				//�����˵��Ĺر��¼�  
				dialog_io.setOnDismissListener(new OnDismissListener() {              
		            
					@Override
					public void onDismiss(DialogInterface arg0) {
						// TODO Auto-generated method stub
						 DTQR.destroy();
						 Config.isMutiThread = false;
					}  
		        });  

				 listView_io=(ListView) dialog_io.findViewById(R.id.listView_io);
					if(listView_io==null){
						return;
					}
					
					formatReadio= new WifiReadDataFormat(0x10007000,32);

					try {
						sendDataioRunnable=new SendDataRunnable(formatReadio, getActivity());

						sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",readioDataFinishTodo);

						sendDataioRunnable.setOnlistener(new NormalChatListenner(sendDataioRunnable, sendDataFinishRunnable));

						getActivity().runOnUiThread(sendDataioRunnable);
				
						} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
					}
			
				 

			}
		});
		/**
		 * ******************************
		 * �����趨С����
		 * ******************************
		 */
		btn_passwordSet=(Button) getActivity().findViewById(R.id.btn_passwordSet);
		if(btn_passwordSet==null){
			return;
		}
		btn_passwordSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				View view_password=View.inflate(getActivity(), R.layout.maintain_guide_password, null);
				AlertDialog dialog_password = new AlertDialog.Builder(getActivity())
						.setTitle("�����趨")
						.setView(view_password)
						.setCancelable(false)
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										dialog.dismiss();
									}
								}).show();
				
				radioGroup_password=(RadioGroup) dialog_password.findViewById(R.id.radioGroup_password);
				if(radioGroup_password==null){
					return;
				}
				btn_create_password=(Button) dialog_password.findViewById(R.id.btn_create_password);
				if(btn_create_password==null){
					return;
				}
				btn_change_password=(Button) dialog_password.findViewById(R.id.btn_change_password);
				if(btn_change_password==null){
					return;
				}
				btn_delete_password=(Button) dialog_password.findViewById(R.id.btn_delete_password);
				if(btn_delete_password==null){
					return;
				}
				//���봰�ھ͸��µõ�����
				password_operate = sharedPreference_password.getString("password_operate", "");
				password_setting = sharedPreference_password.getString("password_setting", "");
				password_programming = sharedPreference_password.getString("password_programming", "");
				
				//���ν��룬�õ������õ����룬�ж��Ƿ����
				if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_operate_password) {
					password = password_operate;
				}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_setting_password) {
					password = password_setting;
				}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_programming_password) {
					password = password_programming;
				}
					
				if (password.equals("")) {//˵����û�д������룬���Խ��봴���������
					btn_create_password.setEnabled(true);
					btn_change_password.setEnabled(false);
					btn_delete_password.setEnabled(false);
				}else {//�Ѿ����������룬�һ��������밴ť
					btn_create_password.setEnabled(false);
					btn_change_password.setEnabled(true);
					btn_delete_password.setEnabled(true);
				}
				
				radioGroup_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

						if (checkedId==R.id.radio_operate_password) {
							if (password_operate.equals("")) {
								btn_create_password.setEnabled(true);
								btn_change_password.setEnabled(false);
								btn_delete_password.setEnabled(false);
							}else {
								btn_create_password.setEnabled(false);
								btn_change_password.setEnabled(true);
								btn_delete_password.setEnabled(true);
							}
						}else if (checkedId==R.id.radio_setting_password) {
							if (password_setting.equals("")) {
								btn_create_password.setEnabled(true);
								btn_change_password.setEnabled(false);
								btn_delete_password.setEnabled(false);
							}else {
								btn_create_password.setEnabled(false);
								btn_change_password.setEnabled(true);
								btn_delete_password.setEnabled(true);
							}
						}else if (checkedId==R.id.radio_programming_password) {
							if (password_programming.equals("")) {
								btn_create_password.setEnabled(true);
								btn_change_password.setEnabled(false);
								btn_delete_password.setEnabled(false);
							}else {
								btn_create_password.setEnabled(false);
								btn_change_password.setEnabled(true);
								btn_delete_password.setEnabled(true);
							}
						}
						
						
					}
				});
																
				/**
				 * ******************************
				 *  1 .���봴��
				 * ******************************
				 */
				btn_create_password.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						password_create="";
						//�жϱ���
						title_create="";
						if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_operate_password) {
							title_create="�������봴��";
						}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_setting_password) {
							title_create="�趨���봴��";
						}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_programming_password) {
							title_create="������봴��";
						}
						

						//��һ����������������
						final EditText editText =new EditText(getActivity());
						editText.setHint("���������룬20λ����");
						editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
						editText.setKeyListener(new NumberKeyListener() {
		 				    @Override
		 				    protected char[] getAcceptedChars() {
		 				        return new char[] { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				        					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				        					'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
		 				    }
		 				    @Override
		 				    public int getInputType() {
		 				        return android.text.InputType.TYPE_CLASS_TEXT;
		 				    }
		 				});	
					
						//��һ��������
						AlertDialog dialog_create = new AlertDialog.Builder(getActivity())
								.setTitle(title_create)
								.setView(editText)
								.setCancelable(false)
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												final String password_create=editText.getText().toString().trim();
												
												if (password_create.equals("")) {//����Ϊ�գ�������Ҫ��Ҫ��������
													try {//����ʧ
														Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
														field.setAccessible(true);
														field.set(dialog,false);//��mShowing������Ϊfalse����ʾ�Ի����ѹرգ���ƭandroidϵͳ
													} catch (Exception e) {
														e.printStackTrace();
													}
													
													editText.setText("");
													editText.setHint("����Ϊ�գ�������Ҫ������������");
													
												}else {//���벻Ϊ�գ������ڶ�������,���õ�һ�ε�dialog��ʧ
													
													//�ڶ�����������������
													final EditText editText2 =new EditText(getActivity());
													editText2.setHint("���������룬20λ����");
													editText2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
													editText2.setKeyListener(new NumberKeyListener() {
														    @Override
														    protected char[] getAcceptedChars() {
														        return new char[] { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
											        					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
											        					'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
														    }
														    @Override
														    public int getInputType() {
														        return android.text.InputType.TYPE_CLASS_TEXT;
														    }
														});	
													
													//�ڶ���������
													AlertDialog dialog_create_again = new AlertDialog.Builder(getActivity())
													 		.setTitle("���ٴ�����"+title_create.substring(0, 4))
															.setView(editText2)
															.setCancelable(false)
													 		.setPositiveButton("ȷ��",
																	new DialogInterface.OnClickListener() {
																		@Override
																		public void onClick(DialogInterface dialog,int which) {
																			if (editText2.getText().toString().trim().equals(password_create)) {
																				
																				//����������ͬ���ŵ���Ӧ��sharedPreference����
																				if (title_create.contains("����")) {
																					sharedPreference_password.edit().putString("password_operate", password_create).commit();
																					password_operate=password_create;//�������룬����ǰ��radiogroup�ĸı��ж�
																				}else if (title_create.contains("�趨")) {
																					sharedPreference_password.edit().putString("password_setting", password_create).commit();
																					password_setting=password_create;
																				}else if (title_create.contains("���")) {
																					sharedPreference_password.edit().putString("password_programming", password_create).commit();
																					password_programming=password_create;
																				}
																				
																				Toast.makeText(getActivity(), title_create.subSequence(0, 2)+"���봴���ɹ�", Toast.LENGTH_SHORT).show();
																				try {//��ʧ
																                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
																                    field.setAccessible( true );  
																                    field.set(dialog, true );//��mShowing������Ϊtrue����ʾ�Ի���δ�ر�     
																                }catch (Exception e) {
																                	e.printStackTrace(); 
																				}
																                dialog.dismiss();
																                //�������봴���ɹ���ʹ�޸ĺ�ɾ��ʹ�ܣ�ʹ�����һ�
																                btn_create_password.setEnabled(false);
																				btn_change_password.setEnabled(true);
																				btn_delete_password.setEnabled(true);
																				
																				
																			} else {//�������벻ͬ�����ȷ������ʧ����ʾ��������
																				
																				try {//����ʧ
																					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
																					field.setAccessible(true);
																					field.set(dialog,false);//��mShowing������Ϊfalse����ʾ�Ի����ѹرգ���ƭandroidϵͳ
																				} catch (Exception e) {
																					e.printStackTrace();
																				}
																				
																				editText2.setText("");
																				editText2.setHint("�������벻��������������");
																			}
																		}
																	})
															.setNegativeButton("ȡ��",
																	new DialogInterface.OnClickListener() {
																		@Override
																		public void onClick(DialogInterface dialog,int which) {

																			try { // ���ȡ�� ����ʧ
																				Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
																				field.setAccessible(true);
																				field.set(dialog,true);// ��mShowing������Ϊtrue����ʾ�Ի���δ�ر�
																			} catch (Exception e) {
																				e.printStackTrace();
																			}

																			dialog.dismiss();
																		}
																	}).show();
													
													try { //����ڶ����Ի���ʱ�õ�һ����ʧ
														Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
														field.setAccessible(true);
														field.set(dialog,true);// ��mShowing������Ϊtrue����ʾ�Ի���δ�ر�
													} catch (Exception e) {
														e.printStackTrace();
													}

													dialog.dismiss();
													
													
												}
												
											}
										})
								.setNegativeButton("ȡ��",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												try { //���ȡ�� ����ʧ
								                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
								                    field.setAccessible( true );  
								                    field.set(dialog, true );//��mShowing������Ϊtrue����ʾ�Ի���δ�ر�    
								                }catch (Exception e) {
								                	e.printStackTrace(); 
												}
												dialog.dismiss();
											}
										}).show();
						
						
						
					}
				});//btn_create_password
				/**
				 * ******************************
				 *  2 .����ɾ��  
				 *  �趨��һ����������tr2013�����ǵ�����ʱ����ʹ��
				 * ******************************
				 */
				btn_delete_password.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						//�жϱ���
						title_delete="";
						passwordToDelete="";
						if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_operate_password) {
							title_delete="��������ɾ��";
							passwordToDelete=password_operate;
						}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_setting_password) {
							title_delete="�趨����ɾ��";
							passwordToDelete=password_setting;
						}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_programming_password) {
							title_delete="�������ɾ��";
							passwordToDelete=password_programming;
						}
						
						//ɾ��������������
						final EditText editText_delete =new EditText(getActivity());
						editText_delete.setHint("������ԭ���룬20λ����");
						editText_delete.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
						editText_delete.setKeyListener(new NumberKeyListener() {
		 				    @Override
		 				    protected char[] getAcceptedChars() {
		 				        return new char[] { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				        					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				        					'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
		 				    }
		 				    @Override
		 				    public int getInputType() {
		 				        return android.text.InputType.TYPE_CLASS_TEXT;
		 				    }
		 				});	
						
						//����ԭ����Ի���
						AlertDialog dialog_delete = new AlertDialog.Builder(getActivity())
								.setTitle(title_delete)
								.setView(editText_delete)
								.setCancelable(false)
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												String passwordHere = editText_delete.getText().toString().trim();
												
												if (passwordHere.equals(passwordToDelete)||passwordHere.equals("tr2013")) {//���ԭ����������ȷ�����������������룬�򵯳�ɾ����ʾ
													//����ȷ��ɾ���Ի���
													new AlertDialog.Builder(getActivity())
															.setTitle("����")
															.setMessage("ȷ��ɾ�����룿")
															.setPositiveButton("ȷ��",
																	new DialogInterface.OnClickListener() {
																		@Override
																		public void onClick(DialogInterface dialog,int which) {
																			
																			if (title_delete.contains("����")) {
																				sharedPreference_password.edit().putString("password_operate", "").commit();
																				password_operate="";//��������
																			}else if (title_delete.contains("�趨")) {
																				sharedPreference_password.edit().putString("password_setting", "").commit();
																				password_setting="";
																			}else if (title_delete.contains("���")) {
																				sharedPreference_password.edit().putString("password_programming", "").commit();
																				password_programming="";
																			}
																			
															                //��������ɾ���ɹ���ʹ�޸ĺ�ɾ���һ���ʹ����ʹ��
															                btn_create_password.setEnabled(true);
																			btn_change_password.setEnabled(false);
																			btn_delete_password.setEnabled(false);
																			
																			Toast.makeText(getActivity(), title_delete.subSequence(0, 2)+"����ɾ���ɹ�", Toast.LENGTH_SHORT).show();
																		}
																	})
															.setNegativeButton("ȡ��", null)
															.show();
													
													try {//��ʧ
									                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
									                    field.setAccessible( true );  
									                    field.set(dialog, true );//��mShowing������Ϊtrue����ʾ�Ի���δ�ر�     
									                }catch (Exception e) {
									                	e.printStackTrace(); 
													}
									                dialog.dismiss();
													
												}else {//���ԭ�������벻��ȷ������������
													
													try {//����ʧ
														Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
														field.setAccessible(true);
														field.set(dialog,false);//��mShowing������Ϊfalse����ʾ�Ի����ѹرգ���ƭandroidϵͳ
													} catch (Exception e) {
														e.printStackTrace();
													}
													editText_delete.setText("");
													editText_delete.setHint("ԭ�������벻��ȷ������������");
													
												}
												
											}
										})
								.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {
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
						
						
						
						
						
						
					}
				});
				/**
				 * ******************************
				 *  3 .�����޸�
				 * ******************************
				 */
				btn_change_password.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						//�жϱ���
						title_change="";
						password_old="";
						if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_operate_password) {
							title_change="���������޸�";
							password_old=password_operate;
						}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_setting_password) {
							title_change="�趨�����޸�";
							password_old=password_setting;
						}else if (radioGroup_password.getCheckedRadioButtonId()==R.id.radio_programming_password) {
							title_change="��������޸�";
							password_old=password_programming;
						}
						
						//�޸����������ԭ�����������
						final EditText editText_change =new EditText(getActivity());
						editText_change.setHint("������ԭ���룬20λ����");
						editText_change.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
						editText_change.setKeyListener(new NumberKeyListener() {
		 				    @Override
		 				    protected char[] getAcceptedChars() {
		 				        return new char[] { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
				        					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				        					'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
		 				    }
		 				    @Override
		 				    public int getInputType() {
		 				        return android.text.InputType.TYPE_CLASS_TEXT;
		 				    }
		 				});	
						//�����޸ģ�������ԭ����
						AlertDialog dialog_change = new AlertDialog.Builder(getActivity())
								.setTitle(title_change)
								.setView(editText_change)
								.setCancelable(false)
								.setPositiveButton("ȷ��",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												if (editText_change.getText().toString().trim().equals(password_old)) {//���ԭ����������ȷ���򵯳��޸ĶԻ����˳�ԭ����Ի���
													
													//�޸�����������������������
													final EditText editText_change_again =new EditText(getActivity());
													editText_change_again.setHint("�����������룬20λ����");
													editText_change_again.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
													editText_change_again.setKeyListener(new NumberKeyListener() {
									 				    @Override
									 				    protected char[] getAcceptedChars() {
									 				        return new char[] { 'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
											        					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
											        					'1', '2', '3', '4', '5', '6', '7', '8','9', '0'};
									 				    }
									 				    @Override
									 				    public int getInputType() {
									 				        return android.text.InputType.TYPE_CLASS_TEXT;
									 				    }
									 				});	
													
													//�����޸ģ�����������
													AlertDialog dialog_change_again = new AlertDialog.Builder(getActivity())
															.setTitle(title_change)
															.setView(editText_change_again)
															.setCancelable(false)
															.setPositiveButton("ȷ��",
																	new DialogInterface.OnClickListener() {
																		@Override
																		public void onClick(DialogInterface dialog,int which) {
																			//�õ�������
																			passwordToChange=editText_change_again.getText().toString().trim();
																			//�ж��������Ƿ�Ϊ��
																			if (passwordToChange.equals("")) {//Ϊ�ղ����ϣ��Ի�����ʧ��������ʾ
																				
																				try {//����ʧ
																					Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
																					field.setAccessible(true);
																					field.set(dialog,false);//��mShowing������Ϊfalse����ʾ�Ի����ѹرգ���ƭandroidϵͳ
																				} catch (Exception e) {
																					e.printStackTrace();
																				}
																				
																				editText_change_again.setText("");
																				editText_change_again.setHint("����������Ϊ�գ�����������");
																				
																			}else {
																				//����������
																				if (title_change.contains("����")) {
																					sharedPreference_password.edit().putString("password_operate", passwordToChange).commit();
																					password_operate=passwordToChange;//�������룬����ǰ��radiogroup�ĸı��ж�
																				}else if (title_change.contains("�趨")) {
																					sharedPreference_password.edit().putString("password_setting", passwordToChange).commit();
																					password_setting=passwordToChange;
																				}else if (title_change.contains("���")) {
																					sharedPreference_password.edit().putString("password_programming", passwordToChange).commit();
																					password_programming=passwordToChange;
																				}
																				
																				Toast.makeText(getActivity(), title_change.subSequence(0, 2)+"�����޸ĳɹ�", Toast.LENGTH_SHORT).show();
																				try {//��ʧ
																                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
																                    field.setAccessible( true );  
																                    field.set(dialog, true );//��mShowing������Ϊtrue����ʾ�Ի���δ�ر�     
																                }catch (Exception e) {
																                	e.printStackTrace(); 
																				}
																                dialog.dismiss();
																                //���������޸ĳɹ���ʹ�޸ĺ�ɾ��ʹ�ܣ�ʹ�����һ�
																                btn_create_password.setEnabled(false);
																				btn_change_password.setEnabled(true);
																				btn_delete_password.setEnabled(true);
																			}

																		}
																	})
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
													
													//���ȷ����ԭ������ȷ������ԭ���������ʧ
													try {//��ʧ
									                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
									                    field.setAccessible( true );  
									                    field.set(dialog, true );//��mShowing������Ϊtrue����ʾ�Ի���δ�ر�     
									                }catch (Exception e) {
									                	e.printStackTrace(); 
													}
									                dialog.dismiss();
												
												
												}else {//ԭ�������벻��ȷ�����˳��Ի�����������
													
													try {//����ʧ
														Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
														field.setAccessible(true);
														field.set(dialog,false);//��mShowing������Ϊfalse����ʾ�Ի����ѹرգ���ƭandroidϵͳ
													} catch (Exception e) {
														e.printStackTrace();
													}
													
													editText_change.setText("");
													editText_change.setHint("ԭ�������벻��ȷ������������");
												}

											}
										})
								.setNegativeButton("ȡ��",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {

												dialog.dismiss();
												
											}
										}).show();
					}
				});
				
				
				
			}
		});//btn_passwordSet
		
		
		
		
		
	}

//	class DelayCountTimeQuery extends CountDownTimer {
//		public DelayCountTimeQuery(long millisInFuture, long countDownInterval) {
//			super(millisInFuture, countDownInterval);
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		public void onFinish() {
//			// TODO Auto-generated method stub
//			System.out.println("��ʱʱ�䵽");
//			 DelayTimerQueryRunnble tempRunnble=new DelayTimerQueryRunnble(getActivity(), listView_io);
//			 new Thread(tempRunnble).start();
//		}
//
//		@Override
//		public void onTick(long millisUntilFinished) {
//			// TODO Auto-generated method stub
//			
//		}  
//		
//	}
 	public class MyAdapter_Alarmzb extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView alarmzbnum;
 			TextView alarmzbName;
 			TextView alarmzbmsg;

 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_Alarmzb(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
 			mAppList = appList;
 			mContext = c;
 			mLayoutID=layoutID;
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

 		// ɾ��ĳһ��
 		public void removeItem(int position) {
 			mAppList.remove(position);
 			this.notifyDataSetChanged();
 		}

 		@Override
 		public View getView(int position, View convertView, ViewGroup parent) {
 			if (convertView != null) {
 				holder = (buttonViewHolder) convertView.getTag();
 			} else {
 				convertView = mInflater.inflate(mLayoutID,null);
 				holder = new buttonViewHolder();
 				holder.alarmzbnum = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.alarmzbName = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.alarmzbmsg = (TextView) convertView
 						.findViewById(valueViewID[2]);

 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String alarmzbnum =  appInfo.get(keyString[0]).toString();
 				String alarmzbName = appInfo.get(keyString[1]).toString();
 				String alarmzbmsg =  appInfo.get(keyString[2]).toString();

 				holder.alarmzbnum.setText(alarmzbnum);
 				holder.alarmzbName.setText(alarmzbName);
 				holder.alarmzbmsg.setText(alarmzbmsg);

 			}
 			return convertView;
 		}

 	}
	
 	public class MyAdapter_Version extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView bootText;
 			TextView bootVersionText;
 			TextView dataText;
 			TextView dataVersionText;

 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_Version(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
 			mAppList = appList;
 			mContext = c;
 			mLayoutID=layoutID;
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

 		// ɾ��ĳһ��
 		public void removeItem(int position) {
 			mAppList.remove(position);
 			this.notifyDataSetChanged();
 		}

 		@Override
 		public View getView(int position, View convertView, ViewGroup parent) {
 			if (convertView != null) {
 				holder = (buttonViewHolder) convertView.getTag();
 			} else {
 				convertView = mInflater.inflate(mLayoutID,null);
 				holder = new buttonViewHolder();
 				holder.bootText = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.bootVersionText = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.dataText = (TextView) convertView
 						.findViewById(valueViewID[2]);
 				holder.dataVersionText = (TextView) convertView
 						.findViewById(valueViewID[3]);

 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String bootText =  appInfo.get(keyString[0]).toString();
 				String bootVersionText = appInfo.get(keyString[1]).toString();
 				String dataText =  appInfo.get(keyString[2]).toString();
 				String dataVersionText =  appInfo.get(keyString[3]).toString();

 				holder.bootText.setText(bootText);
 				holder.bootVersionText.setText(bootVersionText);
 				holder.dataText.setText(dataText);
 				holder.dataVersionText.setText(dataVersionText);

 			}
 			return convertView;
 		}

 	}

 	public class MyAdapter_Alarm extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView num_alarm;
 			TextView time_alarm;
 			TextView content_alarm;

 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_Alarm(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
 			mAppList = appList;
 			mContext = c;
 			mLayoutID=layoutID;
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

 		// ɾ��ĳһ��
 		public void removeItem(int position) {
 			mAppList.remove(position);
 			this.notifyDataSetChanged();
 		}

 		@Override
 		public View getView(int position, View convertView, ViewGroup parent) {
 			if (convertView != null) {
 				holder = (buttonViewHolder) convertView.getTag();
 			} else {
 				convertView = mInflater.inflate(mLayoutID,null);
 				holder = new buttonViewHolder();
 				holder.num_alarm = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.time_alarm = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.content_alarm = (TextView) convertView
 						.findViewById(valueViewID[2]);

 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String num_alarm = appInfo.get(keyString[0]).toString();
 				String time_alarm = appInfo.get(keyString[1]).toString();
 				String content_alarm = appInfo.get(keyString[2]).toString();

 				holder.num_alarm.setText(num_alarm);
 				holder.time_alarm.setText(time_alarm);
 				holder.content_alarm.setText(content_alarm);

 				holder.num_alarm.setOnClickListener(new AlarmListener(position));
				holder.time_alarm.setOnClickListener(new AlarmListener(position));
				holder.content_alarm.setOnClickListener(new AlarmListener(position));
 			}
 			return convertView;
 		}
	class AlarmListener implements OnClickListener {
			
			private int position;

			// ���캯��
			AlarmListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {
			try{
 				String setValueString=mAppList.get(position).get(keyString[1]).toString().trim();
 				String showmsg="����ϸ������Ϣ";
 				if(setValueString.contains("����")){
 					for(int i=0;i<ArrayListBound.getDeviceAlarmListData().size();i++){
						if(ArrayListBound.getDeviceAlarmListData().get(i).get("symbolNameEditText").toString().trim().equals("")){
							continue;
						}
						if(String.valueOf(Integer.parseInt(ArrayListBound.getDeviceAlarmListData().get(i).get("symbolNameEditText").toString().trim())).equals(setValueString.substring(4,setValueString.length()))){
						  String notemsg=ArrayListBound.getDeviceAlarmListData().get(i).get("noteEditText").toString().trim();
 	 					
 	 					  showmsg=setValueString+"\n"+notemsg;
 	 					  System.out.println("showmsg=="+showmsg);
 	 					  break;
 	 					}
 	 				}
 				}else{
 					if(setValueString.contains("�ŷ�")){
 						setValueString=setValueString.substring(0, 2)+setValueString.substring(3, setValueString.length());
 					}
 					for(int i=0;i<list_alarmzb.size();i++){
 	 					System.out.println(list_alarmzb.get(i).get("alarmzbnum").toString().trim());
 	 					System.out.println(setValueString);
 	 					if(list_alarmzb.get(i).get("alarmzbnum").toString().trim().equals(setValueString)){
 	 						showmsg=setValueString+"\n"+list_alarmzb.get(i).get("alarmzbName").toString().trim()
 	 								+"\n"+list_alarmzb.get(i).get("alarmzbmsg").toString().trim();
 	 						System.out.println("showmsg=="+showmsg);
 	 						break;
 	 					}
 	 				}	
 				}
 				
 				new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				   .setMessage(showmsg)
				   .setPositiveButton("֪����", null).show();
			}catch(Exception e){
				e.printStackTrace();
			}
			}

		}
 	}
 	public class MyAdapter_IO extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView io_address;
 			TextView i_symbolName;
 			TextView i_signalName;
 			TextView i_state;
 			TextView o_symbolName;
 			TextView o_signalName;
 			TextView o_state;
 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_IO(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
 			mAppList = appList;
 			mContext = c;
 			mLayoutID=layoutID;
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

 		// ɾ��ĳһ��
 		public void removeItem(int position) {
 			mAppList.remove(position);
 			this.notifyDataSetChanged();
 		}

 		@Override
 		public View getView(int position, View convertView, ViewGroup parent) {
 			if (convertView != null) {
 				holder = (buttonViewHolder) convertView.getTag();
 			} else {
 				convertView = mInflater.inflate(mLayoutID,null);
 				holder = new buttonViewHolder();
 				holder.io_address = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.i_symbolName = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.i_signalName = (TextView) convertView
 						.findViewById(valueViewID[2]);
 				holder.i_state = (TextView) convertView
 						.findViewById(valueViewID[3]);
 				holder.o_symbolName = (TextView) convertView
 						.findViewById(valueViewID[4]);
 				holder.o_signalName = (TextView) convertView
 						.findViewById(valueViewID[5]);
 				holder.o_state = (TextView) convertView
 						.findViewById(valueViewID[6]);

 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String io_address = appInfo.get(keyString[0]).toString();
 				String i_symbolName =  appInfo.get(keyString[1]).toString();
 				String i_signalName =  appInfo.get(keyString[2]).toString();
 				String i_state = appInfo.get(keyString[3]).toString();
 				String o_symbolName =  appInfo.get(keyString[4]).toString();
 				String o_signalName =  appInfo.get(keyString[5]).toString();
 				String o_state = appInfo.get(keyString[6]).toString();
 			
 				holder.io_address.setText(io_address);
 				holder.i_symbolName.setText(i_symbolName);
 				holder.i_signalName.setText(i_signalName);
 				
 				holder.o_symbolName.setText(o_symbolName);
 				holder.o_signalName.setText(o_signalName);
 				
 				if(i_state.equals("1")){
 					holder.i_state.setText("ON");
 					holder.i_state.setBackgroundColor(Color.RED);
 				}else{
 					holder.i_state.setText("OFF");
 					holder.i_state.setBackgroundColor(Color.GRAY);
 				}
 				if(o_state.equals("1")){
 					holder.o_state.setText("ON");
 					holder.o_state.setBackgroundColor(Color.RED);
 				}else{
 					holder.o_state.setText("OFF");
 					holder.o_state.setBackgroundColor(Color.GRAY);
 				}
 			}
 			return convertView;
 		}

 	}
 	
 	


}
