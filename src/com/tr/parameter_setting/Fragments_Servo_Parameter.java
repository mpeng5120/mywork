package com.tr.parameter_setting;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.DelayCount;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import com.dataInAddress.AddressPublic;
import com.dataInAddress.Machinedata;
import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.explain.HexDecoding;
import com.explain.ServoDataGenerate;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.ChatListener;
import com.wifiexchange.Client;
import com.wifiexchange.ServerUtils;
import com.wifiexchange.WifiSetting_Info;

import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Fragments_Servo_Parameter extends Fragment {
	ListView listView_Servo_Parameter;
	MyAdapter Servo_Parameter_Adapter;
	Spinner spinner_servo;
	Button button_saveMain;
	Button button_EEPROM;
	ArrayAdapter<String> axleSpinnerAdapter;
	private static String selectedItem_Servo;
	ArrayList<String> axles = new ArrayList<String>();
	ArrayList<HashMap<String, Object>> parameter_setting_servo_list;
	//= ArrayListBound.getServoParameterListData();
	public static ArrayList<HashMap<String, Object>> read_arraylist_servo = new ArrayList<HashMap<String, Object>>();

	// �������ݷ�װ��
	private WifiSendDataFormat formatSendMessage;
	private WifiReadDataFormat formatReadMessage;

	// servodata��������
	ServoDataGenerate mGenerate;
	Machinedata mccMachinedata;

	// ui�ı䱣������
	private int uiChangePosition;
	private byte[] getData;

	private SendDataRunnable servoDataSend;
	private FinishRunnable sendDataFinish;

	private SendDataRunnable servoDataSend1;
//	private AlarmQueryRunnable alarmQueryRunnable;
//	private ledRunnable ledrunnable;
	private  ChatListener DataFeedback ;
	private SendDataRunnable sendDataRunnable;
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("Fragments_Servo_Parameter onPause");
//		alarmQueryRunnable.destroy();
//		ledrunnable.destroy();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("Fragments_Servo_Parameter onResume");
//		alarmQueryRunnable = new AlarmQueryRunnable(getActivity());
//		Thread a2 = new Thread(alarmQueryRunnable);
//		a2.start();
//		ledrunnable=new ledRunnable(getActivity());
//		Thread a4 = new Thread(ledrunnable);
//		a4.start();
	}
	/** ��ȡ�ŷ�����ʱ��ͨ���߳���д��Ҫ�������ݺ���ת������Ӧ��ַ�ĺ��� */
	private final ChatListener servoReadJump = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			// ���صı�־λSTS1�����ж��Լ���У��
			formatSendMessage.backMessageCheck(message);

			if (!formatSendMessage.finishStatus()) {
				getActivity().runOnUiThread(servoDataSend);
			} else {
				try {
					Thread.sleep(30);

					servoDataSend1 = new SendDataRunnable(
							servoReadDataFeedback, formatReadMessage,
							getActivity());

					getActivity().runOnUiThread(servoDataSend1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// д������֮��ʼ������

			}
		}
	};

	/** ��ȡ�ŷ�����ʱ��ͨ���߳��յ���Ϣʱ�Ĵ�����. */
	private final ChatListener servoReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {

			// ���صı�־λSTS1�����ж��Լ���У��
			formatReadMessage.backMessageCheck(message);
			if (!formatReadMessage.finishStatus()) {
				getActivity().runOnUiThread(servoDataSend1);
			} else {
				// ������ȷ�����
				// �����ص�����
				System.out.println("WifiSetting_Info.blockFlag = "+WifiSetting_Info.blockFlag );
				WifiSetting_Info.blockFlag = true;
				getData = new byte[formatReadMessage.getLength()];
				// ��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				System.arraycopy(message, 8, getData, 0,
						formatReadMessage.getLength());
				// �����ռ����������ݽ��д���
				getActivity().runOnUiThread(backMessageTodo);

				// WifiSetting_Info.blockFlag=true;
			}
		}
	};

	// ��ȡ���ص����ݺ���е�UI����
	private final Runnable backMessageTodo = new Runnable() {
		@Override
		public void run() {

			HashMap<String, Object> map = new HashMap<String, Object>();

			map.put("numText",
					Servo_Parameter_Adapter.getmAppList().get(uiChangePosition)
							.get(Servo_Parameter_Adapter.getkeyString()[0])
							.toString());//
			map.put("contentText",
					Servo_Parameter_Adapter.getmAppList().get(uiChangePosition)
							.get(Servo_Parameter_Adapter.getkeyString()[1])
							.toString());

			// ������ݷ��ص�ֵ�����޸���ʾ
			map.put("currentValueText", HexDecoding.Array4Toint(getData));

			map.put("setValueText",
					Servo_Parameter_Adapter.getmAppList().get(uiChangePosition)
							.get(Servo_Parameter_Adapter.getkeyString()[3])
							.toString());

			map.put("noteText",
					Servo_Parameter_Adapter.getmAppList().get(uiChangePosition)
							.get(Servo_Parameter_Adapter.getkeyString()[4])
							.toString());
			Servo_Parameter_Adapter.getmAppList().set(uiChangePosition, map);
			// ���½���
			Servo_Parameter_Adapter.notifyDataSetChanged();

			Toast.makeText(getActivity(), "�����Ѹ���", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Fragments_Servo_Parameter onCreate");
		DataFeedback = new ChatListener() {
			@Override
			public void onChat(byte[] message) {

				//���صı�־λSTS1�����ж��Լ���У��
				formatReadMessage.backMessageCheck(message);
				if(!formatReadMessage.finishStatus()){
					getActivity().runOnUiThread(sendDataRunnable);			
				}else {
					//������ȷ�����
					//�����ص�����	
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
								TR_Parameter_Setting_Activity.seekbar.setProgress(0);
							}
							if(HexDecoding.Array2Toint(getData)==2){
								Constans.currentSeekbarValue=125;
								TR_Parameter_Setting_Activity.seekbar.setProgress(125);
								}
							if(HexDecoding.Array2Toint(getData)==3){
								Constans.currentSeekbarValue=250;
								TR_Parameter_Setting_Activity.seekbar.setProgress(250);
								}
							if(HexDecoding.Array2Toint(getData)==4){
								Constans.currentSeekbarValue=375;
								TR_Parameter_Setting_Activity.seekbar.setProgress(375);
								}
							if(HexDecoding.Array2Toint(getData)==5){
								Constans.currentSeekbarValue=500;
								TR_Parameter_Setting_Activity.seekbar.setProgress(500);
								}
						}
					}
			
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("Fragments_Servo_Parameter onCreateView");
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		return inflater.inflate(R.layout.tab_parameter_setting_servo,
				container, false);
		

	}
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
			if(TR_Parameter_Setting_Activity.wifi_led!=null){
	        	TR_Parameter_Setting_Activity.wifi_led.setVisibility(View.VISIBLE);
	        }
			Toast.makeText(getActivity(),R.string.T_wificonnsuccess,Toast.LENGTH_SHORT).show();
			formatReadMessage=new WifiReadDataFormat(AddressPublic.model_allspeed,2);
			try {
				sendDataRunnable = new SendDataRunnable(DataFeedback, formatReadMessage,
						getActivity());
				getActivity().runOnUiThread(sendDataRunnable);
			} catch (Exception e) {
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
			}
		}
		
	};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		System.out.println("Fragments_Servo_Parameter onActivityCreated");
		
		parameter_setting_servo_list= ArrayListBound.getServoParameterListData();
		spinner_servo = (Spinner) getActivity().findViewById(R.id.spinner_servo);
		if (spinner_servo == null) {
			return;
		}
		button_EEPROM=(Button)getActivity().findViewById(R.id.button_eeprom);
		if(button_EEPROM==null){
			return;
		}
		button_saveMain = (Button) getActivity().findViewById(
				R.id.button_saveMain_servoPara);
		if (button_saveMain == null) {
			return;
		}
		listView_Servo_Parameter = (ListView) getActivity().findViewById(
				R.id.listView_servopara);
		if (listView_Servo_Parameter == null) {
			return;
		}
		System.out.println("Fragments_Servo_Parameter onActivityCreated sm="+Fragments_Mechanical_Parameter.selectedItem);
		/*selectedItem_Servo=Fragments_Mechanical_Parameter.selectedItem;
		if (read_arraylist_servo.isEmpty()) {
			read_arraylist_servo = read_servo_Parameter();
		}*/
		if (read_arraylist_servo.isEmpty()||!selectedItem_Servo.equals(Fragments_Mechanical_Parameter.selectedItem)) {
			selectedItem_Servo=Fragments_Mechanical_Parameter.selectedItem;
			read_arraylist_servo = read_servo_Parameter();
		}
		selectedItem_Servo=Fragments_Mechanical_Parameter.selectedItem;
		
		Servo_Parameter_Adapter = new MyAdapter(
				getActivity(),
				parameter_setting_servo_list,
				R.layout.parameter_setting_servo_item,
				new String[] { "numText", "contentText", "currentValueText",
						"setValueText", "noteText", "readButton", "writeButton" },
				new int[] { R.id.numText, R.id.contentText,
						R.id.currentValueText, R.id.setValueText,
						R.id.noteText, R.id.readButton, R.id.writeButton });
		listView_Servo_Parameter.setAdapter(Servo_Parameter_Adapter);

		if (axles.isEmpty()) {
			// axles.add("��ѡ����");
			axles.add("��1");
			axles.add("��2");
			axles.add("��3");
			axles.add("��4");
			axles.add("��5");
			axles.add("��6");
			axles.add("��7");
			axles.add("��8");
		} else {

		}

		axleSpinnerAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, axles);
		axleSpinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_servo.setAdapter(axleSpinnerAdapter);
		/**
		 * ѡ��ͬ����ʾ��ͬ����Ϣ
		 */
		spinner_servo.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0) {
					show("1��");
				} else if (arg2 == 1) {
					show("2��");
				} else if (arg2 == 2) {
					show("3��");
				} else if (arg2 == 3) {
					show("4��");
				} else if (arg2 == 4) {
					show("5��");
				} else if (arg2 == 5) {
					show("6��");
				} else if (arg2 == 6) {
					show("7��");
				} else if (arg2 == 7) {
					show("8��");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		button_EEPROM.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final int[] overflag=new int[]{0,0};
				if(WifiSetting_Info.mClient==null){
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
				WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
				 WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(),"д���ŷ�EEPROM��", "��ȴ�", true, false);
			       new Thread()
		              {
		                  public void run()
		                  {
		                	  if(WifiSetting_Info.mClient!=null){
		                		for(int i=0;i<=72;i++){
						int address=0;	
						byte[] temp;
						// ��ȡ������Ϣ
						int axis = Integer.valueOf(spinner_servo.getSelectedItem()
								.toString().substring(1));
						if(i==72){
							temp=HexDecoding.int2byteArray4(1);
							address = address+4;
						}else{
							// ����趨ֵ����ת��Ϊ�ֽ�����
							temp = HexDecoding.int2byteArray4(Integer
									.parseInt(parameter_setting_servo_list.get(i).get("setValueText")
											.toString()));
							address = AddressPublic.machineRAMData_Head
									+ 1024
									* (axis - 1)
									+ 4
									* (Integer.valueOf(parameter_setting_servo_list.get(i).get("numText")
											.toString()) - 1);
						}
						// ��ȡ����
						// Integer.parseInt(mAppList.get(position).get(keyString[0]).toString());
						// ����Ӧ�����͵ĵ�ַ

						

						formatSendMessage = new WifiSendDataFormat(temp, address);

						try {
							servoDataSend = new SendDataRunnable(formatSendMessage,getActivity(),1);
							if(WifiSetting_Info.mClient==null){
								if(WifiSetting_Info.progressDialog!=null){
		                			WifiSetting_Info.progressDialog.dismiss();
		                			WifiSetting_Info.progressDialog=null;
		                		}
								return;
							}
							sendDataFinish = new FinishRunnable(getActivity());
							servoDataSend.setOnlistener(new NormalChatListenner(
									servoDataSend, sendDataFinish));

							getActivity().runOnUiThread(servoDataSend);
							
							Thread.sleep(150);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[1]=1;
	                			WifiSetting_Info.progressDialog.dismiss();
	                			WifiSetting_Info.progressDialog=null;
	                		}
							//Toast.makeText(getActivity(), "������������", Toast.LENGTH_LONG).show();
							return;
						}
						} 
		                		}
		                	  
		                		if(WifiSetting_Info.progressDialog!=null){
		                			overflag[0]=1;
		                			WifiSetting_Info.progressDialog.dismiss();
		                			WifiSetting_Info.progressDialog=null;
		                			
		                		}
		                  }
		              }.start(); 
		              WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {  
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								 if(WifiSetting_Info.mClient==null){
									 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
									   .setMessage("�����쳣��ͨѶ�жϣ��������磡")
									   .setPositiveButton("ȷ��", null).show();
					                	//Toast.makeText(getActivity(),"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
									}else if(overflag[0]==1){
										new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("���ݷ������")
										   .setPositiveButton("ȷ��", null).show();
										overflag[0]=0;
									}else if(overflag[1]==1){
										new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("�����쳣�����ز�����")
										   .setPositiveButton("ȷ��", null).show();
										overflag[1]=0;
										//Toast.makeText(getActivity(),"�����쳣�����ز�����", Toast.LENGTH_SHORT).show();
									}
							}  
				        });
		                
			}
			
		});
		button_saveMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			/*	WifiSetting_Info.progressDialog = WifiSetting_Info.progressDialog.show(getActivity(),
						"�����ļ�������λ��", "��ȴ�", true, false);*/

				saveToMain_Servo_Paramete();

				// ��ס��ʹ�����ݸ�ʽת��ʱ�����Ҫ�½�ת���࣬���������Խ��
				ServoDataGenerate mGenerate = new ServoDataGenerate();

				Machinedata mccMachinedata = new Machinedata();

				if (mGenerate.dataGenerate(read_arraylist_servo,
						Fragments_Servo_Parameter.this, mccMachinedata)) {

					// HexDecoding.printHexString("�ŷ�ȫ������",
					// mccMachinedata.structServoByteArray());

					formatSendMessage = new WifiSendDataFormat(mccMachinedata
							.structServoByteArray(),
							AddressPublic.machineROMParam_Head);// ����ͨѶЭ�鹹�캯������ʼ��
					if(WifiSetting_Info.mClient==null){
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
					}else {
					try {

						servoDataSend = new SendDataRunnable(formatSendMessage,
								getActivity());

						sendDataFinish = new FinishRunnable("�ŷ������ļ��ѳɹ����͵���λ��",getActivity());

						servoDataSend.setOnlistener(new NormalChatListenner(
								servoDataSend, sendDataFinish));

						getActivity().runOnUiThread(servoDataSend);

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

						Toast.makeText(getActivity().getApplicationContext(),
								e.toString(), Toast.LENGTH_SHORT).show();
					}
					}
				}

			}
		});
	}

	protected void saveToMain_Servo_Paramete() {

		creatFolderSysytem();

		Constans.mechanicalParameter.cleanFile(selectedItem_Servo+File.separator+Constans.�ŷ�����);
		// ����Ҫ�����read_arraylist_servo
		if (spinner_servo.getSelectedItemPosition() == 0) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				// read_arraylist_servo.get(i).put("numText",
				// ArrayListBound.getServoParameterListData().get(i).get("numText"));
				read_arraylist_servo.get(i).put(
						"currentValueText1",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText1",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 1) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText2",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText2",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 2) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText3",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText3",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 3) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText4",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText4",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 4) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText5",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText5",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 5) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText6",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText6",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 6) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText7",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText7",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		} else if (spinner_servo.getSelectedItemPosition() == 7) {
			for (int i = 0; i < ArrayListBound.getServoParameterListData()
					.size(); i++) {
				read_arraylist_servo.get(i).put(
						"currentValueText8",
						ArrayListBound.getServoParameterListData().get(i)
								.get("currentValueText"));
				read_arraylist_servo.get(i).put(
						"setValueText8",
						ArrayListBound.getServoParameterListData().get(i)
								.get("setValueText"));
			}
		}

		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayListBound.getServoParameterListData().size(); i++) {
			Constans.mechanicalParameter.writeFile(selectedItem_Servo+File.separator+Constans.�ŷ�����,

			read_arraylist_servo.get(i).get("numText") + "/"
					+ read_arraylist_servo.get(i).get("contentText") + "/"
					+ read_arraylist_servo.get(i).get("noteText") + "/"
					/**
					 * 1����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText1")
					+ "/" + read_arraylist_servo.get(i).get("setValueText1")
					+ "/"
					/**
					 * 2����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText2")
					+ "/" + read_arraylist_servo.get(i).get("setValueText2")
					+ "/"
					/**
					 * 3����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText3")
					+ "/" + read_arraylist_servo.get(i).get("setValueText3")
					+ "/"
					/**
					 * 4����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText4")
					+ "/" + read_arraylist_servo.get(i).get("setValueText4")
					+ "/"
					/**
					 * 5����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText5")
					+ "/" + read_arraylist_servo.get(i).get("setValueText5")
					+ "/"
					/**
					 * 6����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText6")
					+ "/" + read_arraylist_servo.get(i).get("setValueText6")
					+ "/"
					/**
					 * 7����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText7")
					+ "/" + read_arraylist_servo.get(i).get("setValueText7")
					+ "/"
					/**
					 * 8����Ϣ
					 */
					+ read_arraylist_servo.get(i).get("currentValueText8")
					+ "/" + read_arraylist_servo.get(i).get("setValueText8")
					+ "/"

					+ "\r\n");

		}

		Toast.makeText(getActivity(), "�ѱ����ŷ������ļ�", Toast.LENGTH_SHORT).show();

	}

	private void creatFolderSysytem() {

		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		// �����ļ���Ŀ¼
		if (!sdCardExist) {
			Toast.makeText(getActivity().getApplicationContext(), "������ⲿSD�洢��",
					Toast.LENGTH_SHORT).show();// ���������SD����������ʾ
		} else {
			/* �ּ�Ŀ¼����ֿ�����������Ƕ�״�����������Ŀ¼���ܲ����� */

			File trFile = new File(Constans.trPATH);// �½�һ��Ŀ¼
			if (!trFile.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				trFile.mkdir();// ����������򴴽�
				/*Toast.makeText(getActivity().getApplicationContext(),
						"�´����ļ���" + Constans.trFolder, Toast.LENGTH_SHORT)
						.show();// ��ʾ�´���
*/			} else {
				/*Toast.makeText(getActivity().getApplicationContext(),
						"�Ѵ����ļ���" + Constans.trFolder, Toast.LENGTH_SHORT)
						.show();// ��ʾ�Ѵ���
*/			}

			Constans.mechanicalParameter.createFolder(Constans.trPATH,
					Constans.mechanicalParameterFolder);

		}
	}

	// protected void unshow() {
	// for (int i = 0; i < ArrayListBound.getServoParameterListData().size();
	// i++) {
	// ArrayListBound.getServoParameterListData().get(i)
	// .put("contentText", "");
	// ArrayListBound.getServoParameterListData().get(i)
	// .put("currentValueText", "");
	// ArrayListBound.getServoParameterListData().get(i)
	// .put("setValueText", "");
	// ArrayListBound.getServoParameterListData().get(i)
	// .put("noteText", "");
	// Servo_Parameter_Adapter.notifyDataSetChanged();
	// }
	//
	// }

	protected void show(String axle) {

		if (axle.equals("1��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText1"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText1"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}

		} else if (axle.equals("2��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText2"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText2"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		} else if (axle.equals("3��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText3"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText3"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		} else if (axle.equals("4��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText4"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText4"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		} else if (axle.equals("5��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText5"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText5"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		} else if (axle.equals("6��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText6"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText6"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		} else if (axle.equals("7��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText7"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText7"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		} else if (axle.equals("8��")) {
			for (int i = 0; i < read_arraylist_servo.size(); i++) {
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("contentText",
								read_arraylist_servo.get(i).get("contentText"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("currentValueText",
								read_arraylist_servo.get(i).get(
										"currentValueText8"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("setValueText",
								read_arraylist_servo.get(i)
										.get("setValueText8"));
				ArrayListBound
						.getServoParameterListData()
						.get(i)
						.put("noteText",
								read_arraylist_servo.get(i).get("noteText"));
				Servo_Parameter_Adapter.notifyDataSetChanged();
			}
		}

	}

	private ArrayList<HashMap<String, Object>> read_servo_Parameter() {
		ArrayList<HashMap<String, Object>> read_arraylist = new ArrayList<HashMap<String, Object>>();
		try {
		String[] readlist = Constans.mechanicalParameter
				.readOutFile(Constans.mechanicalParameterPATH +selectedItem_Servo+File.separator+Constans.�ŷ�����);// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist.length; i++) {
			readlist[i] = readlist[i].trim();
		}
		for (int i = 0; i < readlist.length / 19; i++) {
			HashMap<String, Object> mapadd = new HashMap<String, Object>();
			mapadd.put("numText", readlist[19 * i]);
			mapadd.put("contentText", readlist[19 * i + 1]);
			mapadd.put("noteText", readlist[19 * i + 2]);
			mapadd.put("currentValueText1", readlist[19 * i + 3]);
			mapadd.put("setValueText1", readlist[19 * i + 4]);
			mapadd.put("currentValueText2", readlist[19 * i + 5]);
			mapadd.put("setValueText2", readlist[19 * i + 6]);
			mapadd.put("currentValueText3", readlist[19 * i + 7]);
			mapadd.put("setValueText3", readlist[19 * i + 8]);
			mapadd.put("currentValueText4", readlist[19 * i + 9]);
			mapadd.put("setValueText4", readlist[19 * i + 10]);
			mapadd.put("currentValueText5", readlist[19 * i + 11]);
			mapadd.put("setValueText5", readlist[19 * i + 12]);
			mapadd.put("currentValueText6", readlist[19 * i + 13]);
			mapadd.put("setValueText6", readlist[19 * i + 14]);
			mapadd.put("currentValueText7", readlist[19 * i + 15]);
			mapadd.put("setValueText7", readlist[19 * i + 16]);
			mapadd.put("currentValueText8", readlist[19 * i + 17]);
			mapadd.put("setValueText8", readlist[19 * i + 18]);

			read_arraylist.add(mapadd);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		return read_arraylist;
	}

	private class MyAdapter extends BaseAdapter {
		private class buttonViewHolder {
			TextView numText;
			TextView contentText;
			TextView currentValueText;
			TextView setValueText;
			TextView noteText;
			Button readButton;
			Button writeButton;
		}

		private ArrayList<HashMap<String, Object>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String[] keyString;
		private int[] valueViewID;
		private buttonViewHolder holder;
		private int mLayoutID;

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

		public String[] getkeyString() {
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

		public ArrayList<HashMap<String, Object>> getmAppList() {
			return mAppList;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (buttonViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(mLayoutID, null);
				;
				holder = new buttonViewHolder();
				holder.numText = (TextView) convertView
						.findViewById(valueViewID[0]);
				holder.contentText = (TextView) convertView
						.findViewById(valueViewID[1]);
				holder.currentValueText = (TextView) convertView
						.findViewById(valueViewID[2]);
				holder.setValueText = (TextView) convertView
						.findViewById(valueViewID[3]);
				holder.noteText = (TextView) convertView
						.findViewById(valueViewID[4]);
				holder.readButton = (Button) convertView
						.findViewById(valueViewID[5]);
				holder.writeButton = (Button) convertView
						.findViewById(valueViewID[6]);
				convertView.setTag(holder);
			}

			HashMap<String, Object> map_item = mAppList.get(position);

			if (map_item != null) {
				String numText = map_item.get(keyString[0]).toString();
				String contentText = map_item.get(keyString[1]).toString();
				String currentValueText = map_item.get(keyString[2]).toString();
				String setValueText = map_item.get(keyString[3]).toString();
				String noteText = map_item.get(keyString[4]).toString();

				holder.numText.setText(numText);
				holder.contentText.setText(contentText);
				holder.currentValueText.setText(currentValueText);
				holder.setValueText.setText(setValueText);
				holder.noteText.setText(noteText);

				// holder.setValueText.setClickable(true);
				// holder.setValueText.setFocusable(true);
				holder.setValueText.setOnClickListener(new setValueListener(
						position));// ����趨ֵ�м�����

				holder.readButton.setOnClickListener(new readButtonListener(
						position));
				holder.writeButton.setOnClickListener(new writeButtonListener(
						position));
			}
			return convertView;
		}

		// �趨ֵ�м�����
		class setValueListener implements android.view.View.OnClickListener {

			private int position;

			// ���캯��
			setValueListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {

				final EditText etEditText = new EditText(getActivity());
				etEditText.setHint("����������");
				String setValueString =  mAppList.get(position).get(
						keyString[3]).toString();

				etEditText.setText(setValueString);
				etEditText.setSelection(setValueString.length());// ���ù��λ��

				// ����ֻ������0~9�����ֺ͵��
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5', '6', '7',
								'8', '9', '0', '.' };
					}

					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
					}
				});
				new AlertDialog.Builder(getActivity())
						.setTitle("������趨ֵ")
						.setView(etEditText)
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										String editString = etEditText
												.getText().toString();

										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("numText",
												mAppList.get(position)
														.get(keyString[0])
														.toString());//
										map.put("contentText",
												mAppList.get(position)
														.get(keyString[1])
														.toString());

										map.put("currentValueText", mAppList
												.get(position)
												.get(keyString[2]).toString());
										map.put("setValueText", editString);

										map.put("noteText",
												mAppList.get(position)
														.get(keyString[4])
														.toString());
										mAppList.set(position, map);
										notifyDataSetChanged();
									}

								}).setNegativeButton("ȡ��", null).show();

			}
		}

		class readButtonListener implements android.view.View.OnClickListener {
			private int position;

			// ���캯��
			readButtonListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {
				// д����λ��[Define.MAX_AXIS_NUM][256]�е�ĳһ����
				// ��ȡ������Ϣ
				int axis = Integer.parseInt(spinner_servo.getSelectedItem()
						.toString().substring(1));
				// ��ȡ����
				// Integer.parseInt(mAppList.get(position).get(keyString[0]).toString());
				// ����Ӧ�����͵ĵ�ַ
				// uint32_t servoRdNo;
				// ��Ϊ��ֵ��ʱ���ʾ��Ч��PPU��������ֵ�����Ĵ�SPU��ȡ
				// ���õ�ַд��servoRdNo
				int address_data_send = (axis - 1)
						* 256
						+ (Integer.parseInt(mAppList.get(position)
								.get(keyString[0]).toString()));

				int address_read = AddressPublic.SysSts_servoPara
						+ (axis - 1)
						* 1024
						+ 4
						* (Integer.parseInt(mAppList.get(position)
								.get(keyString[0]).toString()) - 1);

				Fragments_Servo_Parameter.this.uiChangePosition = position;

				formatSendMessage = new WifiSendDataFormat(
						HexDecoding.int2byteArray4(address_data_send),
						AddressPublic.SysSts_servoRdNo);
				formatReadMessage = new WifiReadDataFormat(address_read, 4);

				try {

					servoDataSend = new SendDataRunnable(servoReadJump,
							formatSendMessage, getActivity());

					getActivity().runOnUiThread(servoDataSend);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Toast.makeText(getActivity(), e.toString(),
							Toast.LENGTH_SHORT).show();
				}

				/*
				 * Toast.makeText( getActivity(), "��ȡ" + "\"" +
				 * mAppList.get(position).get(keyString[1]) .toString() + "\"" +
				 * "��ǰֵ", Toast.LENGTH_SHORT).show();
				 */

			}
		}

		class writeButtonListener implements android.view.View.OnClickListener {
			private int position;

			// ���캯��
			writeButtonListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {

				// ����趨ֵ����ת��Ϊ�ֽ�����
				byte[] temp = HexDecoding.int2byteArray4(Integer
						.parseInt(mAppList.get(position).get(keyString[3])
								.toString()));

				// ��ȡ������Ϣ
				int axis = Integer.valueOf(spinner_servo.getSelectedItem()
						.toString().substring(1));

				// ��ȡ����
				// Integer.parseInt(mAppList.get(position).get(keyString[0]).toString());
				// ����Ӧ�����͵ĵ�ַ

				int address = AddressPublic.machineRAMData_Head
						+ 1024
						* (axis - 1)
						+ 4
						* (Integer.valueOf(mAppList.get(position)
								.get(keyString[0]).toString()) - 1);

				formatSendMessage = new WifiSendDataFormat(temp, address);

				try {

					servoDataSend = new SendDataRunnable(formatSendMessage,
							getActivity());

					sendDataFinish = new FinishRunnable(getActivity(), "���ݷ������");

					servoDataSend.setOnlistener(new NormalChatListenner(
							servoDataSend, sendDataFinish));

					getActivity().runOnUiThread(servoDataSend);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Toast.makeText(getActivity(), "������������", Toast.LENGTH_LONG)
							.show();
				}

			}
		}

	}

}
