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
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Machinedata;
import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.explain.HexDecoding;
import com.explain.MachineDataGenerate;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.ChatListener;
import com.wifiexchange.Client;
import com.wifiexchange.ServerUtils;
import com.wifiexchange.WifiSetting_Info;

public class Fragments_Mechanical_Parameter extends Fragment {

	public ListView listView_Mechanical_Parameter;
	public MyAdapter Mechanical_Parameter_Adapter;
	
	static Spinner spinner_mechanicalpara;
	private  ArrayList<String> mechanicalparaList_setting = new ArrayList<String>();//����ļ�����������spinner
	ArrayAdapter<String> mechanicalpara_setting_Adapter;
	private SharedPreferences selectedItemShared_Spinner;//��ű�ѡ����ļ��±�
	public  static String selectedItem;//��ű�ѡ����ļ���
	public SharedPreferences selectedItemShared;//��ű�ѡ����ļ���
	private static boolean firstopen=true;
	
	CheckBox checkBox_axle1;
	CheckBox checkBox_axle2;
	CheckBox checkBox_axle3;
	CheckBox checkBox_axle4;
	CheckBox checkBox_axle5;
	CheckBox checkBox_axle6;
	CheckBox checkBox_axle7;
	CheckBox checkBox_axle8;
	Button button_saveMain;
	Button button_saveasMain;
	Button button_deleteMain;

	public static ArrayList<HashMap<String, Object>> read_arraylist_mechanical = new ArrayList<HashMap<String, Object>>();

	boolean axle1_unchecked = true;// Ӧ��ÿ���л�ҳ������󶼻�ֱ����Ӧ��ѡ�ж��ظ��������Ϣ��bug
	boolean axle2_unchecked = true;
	boolean axle3_unchecked = true;
	boolean axle4_unchecked = true;
	boolean axle5_unchecked = true;
	boolean axle6_unchecked = true;
	boolean axle7_unchecked = true;
	boolean axle8_unchecked = true;

	private HashMap<String, Boolean> map_selectedAxle = new HashMap<String, Boolean>();

	// �洢�˻�е�����汾��Ϣ�����ڰ汾��Ϣ����ʵ�ڽ�����
	private String version;
	
	//���������ݴ����й�
	// ��������Э���װ��
	private WifiSendDataFormat formatMessage;
	// machinedata��������
	MachineDataGenerate mGenerate;
	Machinedata mdMachinedata;
	//�������ݽ���
	private SendDataRunnable sendDataRunnable;
	private FinishRunnable sendFinishRunnable;
	private WifiReadDataFormat formatReadMessage;
	private  ChatListener DataFeedback ;
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("Fragments_Mechanical_Parameter onPause");
//		alarmQueryRunnable.destroy();
//		ledrunnable.destroy();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("Fragments_Mechanical_Parameter onResume");
//		alarmQueryRunnable = new AlarmQueryRunnable(getActivity());
//		Thread a2 = new Thread(alarmQueryRunnable);
//		a2.start();
//		ledrunnable=new ledRunnable(getActivity());
//		Thread a4 = new Thread(ledrunnable);
//		a4.start();
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Fragments_Mechanical_Parameter onCreate");
		selectedItemShared=getActivity().getSharedPreferences("selectedItem",0);
		selectedItemShared_Spinner=getActivity().getSharedPreferences("selectedItem_Spinner",0);
		//selectedItemShared_num=getActivity().getSharedPreferences("selectedItemShared_num",0);

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
		System.out.println("Fragments_Mechanical_Parameter onCreateView");
		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//		InputMethodManager m=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		View view = inflater.inflate(R.layout.tab_parameter_setting_mechanical,
				container, false);
		return view;
	}
	public OnItemSelectedListener mechanicalChoose_Listener=new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View arg1, int pos,long arg3) {
			
			selectedItem=parent.getItemAtPosition(pos).toString();
			if(!selectedItemShared.getString("selectedItem", "").equals(selectedItem)){
			   read_arraylist_mechanical.clear();
		    }
			selectedItemShared.edit().putString("selectedItem",selectedItem).commit();
			selectedItemShared_Spinner.edit().putInt("selectedItem_Spinner",pos).commit();
			
			show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
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
		// init_Mechanical_Parameter();
		System.out.println("Fragments_Mechanical_Parameter onActivityCreated");
		spinner_mechanicalpara=(Spinner) getActivity().findViewById(R.id.spinner_mechanicalpara);
		if(spinner_mechanicalpara==null){
			return;
		}
		mechanicalparaList_setting=Constans.mechanicalParameter.getfiles("/mnt/sdcard/TR/��е����/");
		mechanicalpara_setting_Adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,mechanicalparaList_setting);
		mechanicalpara_setting_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_mechanicalpara.setAdapter(mechanicalpara_setting_Adapter);
		if(mechanicalparaList_setting.size()==0){
			Toast.makeText(getActivity(), "δ�����е�����ļ�",Toast.LENGTH_LONG).show();
			return;
		}
		for(int i=0;i<mechanicalparaList_setting.size();i++){
			if(selectedItemShared.getString("selectedItem", "").equals(mechanicalparaList_setting.get(i))){
				if(firstopen){
				  Toast.makeText(getActivity(), "�ɹ����ϴ���ѡ��е�����ļ�",Toast.LENGTH_LONG).show();
				  firstopen=false;
				}
				spinner_mechanicalpara.setSelection(i);
			    break;
		    }else{
		    	if(i==mechanicalparaList_setting.size()-1){
		    	Toast.makeText(getActivity(), "�ϴ���ѡ��е�����ļ���ɾ��Ĭ��ѡ�����ļ�",Toast.LENGTH_LONG).show();
		    	spinner_mechanicalpara.setSelection(0);
		    	}
		    }
		}
		
		spinner_mechanicalpara.setOnItemSelectedListener(mechanicalChoose_Listener);
		listView_Mechanical_Parameter = (ListView) getActivity().findViewById(
				R.id.listView_mechanicalpara);
		if(listView_Mechanical_Parameter==null){
			return;
		}
		Mechanical_Parameter_Adapter = new MyAdapter(getActivity(),
				ArrayListBound.getMechanicalParameterListData(),
				R.layout.parameter_setting_mechanical_item, new String[] {
			"numText", "contentText", "setValueText", "noteText" },
			new int[] { R.id.numText, R.id.contentText, R.id.setValueText,
			R.id.noteText });
		listView_Mechanical_Parameter.setAdapter(Mechanical_Parameter_Adapter);
		checkBox_axle1 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle1);
		if(checkBox_axle1==null){
			return;
		}
		checkBox_axle2 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle2);
		if(checkBox_axle2==null){
			return;
		}
		checkBox_axle3 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle3);
		if(checkBox_axle3==null){
			return;
		}
		checkBox_axle4 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle4);
		if(checkBox_axle4==null){
			return;
		}
		checkBox_axle5 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle5);
		if(checkBox_axle5==null){
			return;
		}
		checkBox_axle6 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle6);
		if(checkBox_axle6==null){
			return;
		}
		checkBox_axle7 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle7);
		if(checkBox_axle7==null){
			return;
		}
		checkBox_axle8 = (CheckBox) getActivity().findViewById(
				R.id.checkBox_axle8);
		if(checkBox_axle8==null){
			return;
		}
		button_saveMain = (Button) getActivity().findViewById(
				R.id.button_saveMain_MechanicalPara);
		if(button_saveMain==null){
			return;
		}
		button_saveasMain = (Button) getActivity().findViewById(
				R.id.button_saveasMain_MechanicalPara);
		if(button_saveasMain==null){
			return;
		}
		button_deleteMain = (Button) getActivity().findViewById(
				R.id.button_deleteMain_MechanicalPara);
		if(button_deleteMain==null){
			return;
		}
		//show();

		map_selectedAxle.put("1��", true);
		map_selectedAxle.put("2��", true);
		map_selectedAxle.put("3��", true);
		map_selectedAxle.put("4��", true);
		map_selectedAxle.put("5��", true);
		map_selectedAxle.put("6��", false);//678��Ҫ����
		map_selectedAxle.put("7��", false);
		map_selectedAxle.put("8��", false);

		checkBox_axle1
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("1��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("1��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		checkBox_axle2
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("2��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("2��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});

		checkBox_axle3
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("3��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("3��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		checkBox_axle4
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("4��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("4��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		checkBox_axle5
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("5��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("5��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		checkBox_axle6
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("6��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("6��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		checkBox_axle7
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("7��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("7��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		checkBox_axle8
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					map_selectedAxle.put("8��", true);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				} else {
					map_selectedAxle.put("8��", false);
					Mechanical_Parameter_Adapter.notifyDataSetChanged();
				}

			}
		});
		button_saveMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				saveToMain_Mechanical_Paramete();
				Toast.makeText(getActivity(), "�ѱ����е�����ļ�", Toast.LENGTH_SHORT).show();
				
				/* ����Ķ������ݷ��ʹ����й� */

				mGenerate = new MachineDataGenerate();
				mdMachinedata = new Machinedata();

				// �ӱ����ռ���������machineData���ݸ�ʽ����
				mGenerate.DataGenerate(ArrayListBound.getMechanicalParameterListData(),
						mdMachinedata, version,
						Fragments_Mechanical_Parameter.this.getActivity());
				// ���ú�������2�������ݣ�����ʹ��Э���װ����з�װ
				// ע�⣬����Ҫʹ������Ļ�е������ַ
				formatMessage = new WifiSendDataFormat(mdMachinedata
						.structMechanicalByteArray(),
						AddressPublic.machineROMData_mechanical_Head);// ����ͨѶЭ�鹹�캯������ʼ��

				// ��������
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

					//if(TR_Main_Activity.zd_led.getBackground().equals(TR_Main_Activity.drawablezdled)){
					if(Config.AutoState){
						new AlertDialog.Builder(getActivity())
						.setTitle("����")
						.setMessage("���������Զ�״̬���������ػ�е�����ļ������л����ֶ�״̬��")
						.setPositiveButton("֪����",null)
						.show();
				    }else{
				    	sendDataRunnable=new SendDataRunnable(formatMessage, getActivity());
						
						sendFinishRunnable=new FinishRunnable("��е�����ļ��ѳɹ����͵���λ��",getActivity());
						
						sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendFinishRunnable));

						
						getActivity().runOnUiThread(sendDataRunnable);	
				    }
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(getActivity().getApplicationContext(),
							e.toString(), Toast.LENGTH_SHORT).show();
				}
				}
			}
		});
		button_saveasMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final EditText etEditText = new EditText(getActivity());
				etEditText.setHint("�������ļ���");
				new AlertDialog.Builder(getActivity())
				.setTitle("�������ļ���")
				.setView(etEditText)
				.setPositiveButton(R.string.OK,
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						String editString = etEditText.getText().toString().trim();
						if(editString.equals("")){
							Toast.makeText(getActivity(), "��е�����ļ�������Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						if(mechanicalparaList_setting.contains(editString)){
							Toast.makeText(getActivity(), "��е�����ļ����ظ������������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						Constans.mechanicalParameter.copyDirectiory(Constans.mechanicalParameterPATH +selectedItemShared.getString("selectedItem", "")+ File.separator,Constans.mechanicalParameterPATH +editString+ File.separator);//ͬʱ�����ļ��и��Ƶ��½��ļ�
						
						mechanicalparaList_setting=Constans.mechanicalParameter.getfiles("/mnt/sdcard/TR/��е����/");
	            		mechanicalpara_setting_Adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,mechanicalparaList_setting);
	    				mechanicalpara_setting_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    				spinner_mechanicalpara.setAdapter(mechanicalpara_setting_Adapter);
	    				
	    				selectedItemShared.edit().putString("selectedItem",editString).commit();
						saveToMain_Mechanical_Paramete();
						Toast.makeText(getActivity(), "��ǰ��е�����ļ�����浽"+editString,Toast.LENGTH_LONG).show();

						for(int i=0;i<mechanicalparaList_setting.size();i++){
	    					if(spinner_mechanicalpara.getItemAtPosition(i).toString().equals(editString)){
	    						spinner_mechanicalpara.setSelection(i);
	    						break;
	    					}
	    				}
						
						/* ����Ķ������ݷ��ʹ����й� */

						mGenerate = new MachineDataGenerate();
						mdMachinedata = new Machinedata();

						// �ӱ����ռ���������machineData���ݸ�ʽ����
						mGenerate.DataGenerate(ArrayListBound.getMechanicalParameterListData(),
								mdMachinedata, version,
								Fragments_Mechanical_Parameter.this.getActivity());
						// ���ú�������2�������ݣ�����ʹ��Э���װ����з�װ
						// ע�⣬����Ҫʹ������Ļ�е������ַ
						formatMessage = new WifiSendDataFormat(mdMachinedata
								.structMechanicalByteArray(),
								AddressPublic.machineROMData_mechanical_Head);// ����ͨѶЭ�鹹�캯������ʼ��

						// ��������
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
							//if(TR_Main_Activity.zd_led.getBackground().equals(TR_Main_Activity.drawablezdled)){
							if(Config.AutoState){	
							new AlertDialog.Builder(getActivity())
								.setTitle("����")
								.setMessage("���������Զ�״̬���������ػ�е�����ļ������л����ֶ�״̬��")
								.setPositiveButton("֪����",null)
								.show();
						    }else{
						    	sendDataRunnable=new SendDataRunnable(formatMessage, getActivity());
								
								sendFinishRunnable=new FinishRunnable("��е�����ļ��ѳɹ����͵���λ��",getActivity());
								
								sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendFinishRunnable));

								
								getActivity().runOnUiThread(sendDataRunnable);
						    }
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
	            	
					}
				}).setNegativeButton("ȡ��",null).show();
				
			}
		});
		
		button_deleteMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mechanicalparaList_setting.size()==1){
					Toast.makeText(getActivity(), "����ɾ���һ����е�����ļ�",Toast.LENGTH_LONG).show();
					return;
				}
				new AlertDialog.Builder(getActivity())
				.setTitle("��ʾ").setMessage("ȷ��ɾ����ǰ��е�����ļ���")
				.setPositiveButton("ȷ��",
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						
								try {
		    	                    File file=new File(Constans.mechanicalParameterPATH +selectedItemShared.getString("selectedItem", ""));
		    	            		if (file.exists()) {
		    	            			Constans.mechanicalParameter.deleteFolder(Constans.mechanicalParameterPATH +selectedItemShared.getString("selectedItem", ""));
		    	            		}
		    	            		mechanicalparaList_setting=Constans.mechanicalParameter.getfiles("/mnt/sdcard/TR/��е����/");
		    	            		mechanicalpara_setting_Adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,mechanicalparaList_setting);
		    	    				mechanicalpara_setting_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    	    				spinner_mechanicalpara.setAdapter(mechanicalpara_setting_Adapter);
		    	                	spinner_mechanicalpara.setSelection(0);
		    	                	Toast.makeText(getActivity(), "��ǰ��е�����ļ���ɾ����Ĭ��ѡ�����ļ�",Toast.LENGTH_LONG).show();
		    	            	} catch (Exception e) {
									e.printStackTrace();
								}
							
					}
				}).setNegativeButton("ȡ��",null).show();
			}
		});
		
		
	}

	private void show() {
		if (read_arraylist_mechanical.isEmpty()) {
			read_arraylist_mechanical = read_Mechanical_Parameter();
			for (int i = 0; i < read_arraylist_mechanical.size() - 1; i++) {
				ArrayListBound
				.getMechanicalParameterListData()
				.get(i)
				.put("contentText",
						read_arraylist_mechanical.get(i + 1).get(
								"contentText"));
				ArrayListBound
				.getMechanicalParameterListData()
				.get(i)
				.put("setValueText",
						read_arraylist_mechanical.get(i + 1).get(
								"setValueText"));
				ArrayListBound
				.getMechanicalParameterListData()
				.get(i)
				.put("noteText",
						read_arraylist_mechanical.get(i + 1).get(
								"noteText"));

				Mechanical_Parameter_Adapter.notifyDataSetChanged();
			}
		}

	}

	/**
	 * ��ȡ��е�����ļ�
	 */

	private ArrayList<HashMap<String, Object>> read_Mechanical_Parameter() {
		ArrayList<HashMap<String, Object>> read_arraylist = new ArrayList<HashMap<String, Object>>();
		System.out.println("��е����·����"+Constans.mechanicalParameterPATH +selectedItemShared.getString("selectedItem", "")+File.separator+ Constans.��е����);
		String[] readlist = Constans.mechanicalParameter
				.readOutFile(Constans.mechanicalParameterPATH +selectedItemShared.getString("selectedItem", "")+File.separator+ Constans.��е����);// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist.length; i++) {
			readlist[i] = readlist[i].trim();
		}
		for (int i = 0; i < readlist.length / 4; i++) {
			HashMap<String, Object> mapadd = new HashMap<String, Object>();
			mapadd.put("numText", readlist[4 * i]);
			mapadd.put("contentText", readlist[4 * i + 1]);
			mapadd.put("setValueText", readlist[4 * i + 2]);
			mapadd.put("noteText", readlist[4 * i + 3]);
			read_arraylist.add(mapadd);
		}
		return read_arraylist;
	}

	/**
	 * ��Ҫ����û����ʾ������
	 */
	private void saveToMain_Mechanical_Paramete() {

		final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(Calendar.getInstance().getTime());// ��ǰʱ��
		try{
		version = String.format(
				"%1$.1f",
				Double.parseDouble(read_arraylist_mechanical.get(0)
						.get("setValueText").toString().substring(1)) + 0.1);
	    } catch (Exception e) {
		   e.printStackTrace();
	    }
		System.out.println(version);
		creatFolderSysytem();

		Constans.mechanicalParameter.cleanFile(selectedItemShared.getString("selectedItem", "")+File.separator+Constans.��е����);

		// д���ļ�ͷ
		Constans.mechanicalParameter.writeFile(selectedItemShared.getString("selectedItem", "")+File.separator+Constans.��е����, "0000" + "/"
				+ "��е�����汾" + "/" + "v" + version + "/" + date + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayListBound.getMechanicalParameterListData()
				.size(); i++) {
			Constans.mechanicalParameter.writeFile(
					selectedItemShared.getString("selectedItem", "")+File.separator+Constans.��е����,
					ArrayListBound.getMechanicalParameterListData().get(i)
					.get("numText").toString()
					+ "/"
					+ ArrayListBound.getMechanicalParameterListData()
					.get(i).get("contentText").toString()
					+ "/"
					+ ArrayListBound.getMechanicalParameterListData()
					.get(i).get("setValueText").toString()
					+ "/"
					+ ArrayListBound.getMechanicalParameterListData()
					.get(i).get("noteText").toString()
					+ "/"
					+ "\r\n");

		}

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
			Constans.otherParameter.createFolder(Constans.trPATH,
					Constans.otherParameterFolder).createFile(
							"otherParameter.trt");
			Constans.mouldData.createFolder(Constans.trPATH,
					Constans.mouldDataFolder);

		}
	}

	public class MyAdapter extends BaseAdapter {
		private class buttonViewHolder {
			TextView numText;
			TextView contentText;
			TextView setValueText;
			TextView noteText;

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
				holder.setValueText = (TextView) convertView
						.findViewById(valueViewID[2]);
				holder.noteText = (TextView) convertView
						.findViewById(valueViewID[3]);
				convertView.setTag(holder);
			}

			HashMap<String, Object> map_item = mAppList.get(position);
			if (map_item != null) {
				String numText =  map_item.get(keyString[0]).toString();
				String contentText = map_item.get(keyString[1]).toString();
				String setValueText =  map_item.get(keyString[2]).toString();
				String noteText = map_item.get(keyString[3]).toString();

				holder.numText.setText(numText);
				holder.contentText.setText(contentText);
				holder.setValueText.setText(setValueText);
				holder.noteText.setText(noteText);

				holder.setValueText.setClickable(true);
				holder.setValueText.setFocusable(true);
				holder.setValueText.setOnClickListener(new setValueListener(
						position));// ����趨ֵ�м�����

				/**
				 * ʵ��������Ŀ����
				 */
				if (holder.contentText.getText().toString().contains("1��")) {
					if (map_selectedAxle.get("1��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("2��")) {
					if (map_selectedAxle.get("2��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("3��")) {
					if (map_selectedAxle.get("3��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("4��")) {
					if (map_selectedAxle.get("4��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("5��")) {
					if (map_selectedAxle.get("5��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("6��")) {
					if (map_selectedAxle.get("6��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("7��")) {
					if (map_selectedAxle.get("7��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else if (holder.contentText.getText().toString()
						.contains("8��")) {
					if (map_selectedAxle.get("8��")) {
						holder.numText.setVisibility(View.VISIBLE);
						holder.contentText.setVisibility(View.VISIBLE);
						holder.setValueText.setVisibility(View.VISIBLE);
						holder.noteText.setVisibility(View.VISIBLE);
					} else {
						holder.numText.setVisibility(View.GONE);
						holder.contentText.setVisibility(View.GONE);
						holder.setValueText.setVisibility(View.GONE);
						holder.noteText.setVisibility(View.GONE);
					}
				} else {
					holder.numText.setVisibility(View.VISIBLE);
					holder.contentText.setVisibility(View.VISIBLE);
					holder.setValueText.setVisibility(View.VISIBLE);
					holder.noteText.setVisibility(View.VISIBLE);
				}

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
				etEditText.setInputType(InputType.TYPE_NULL);
				String setValueString = mAppList.get(position).get(
						keyString[2]).toString();
				etEditText.setHint("����������");
				etEditText.setText(setValueString);
				etEditText.setSelection(setValueString.length());// ���ù��λ��
				// ����ֻ������0~9�����ֺ͵��
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5', '6', '7',
								'8', '9', '0', '.','+','-' };
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
						double editDouble=0;
						if (!editString.startsWith(".")) {
							if (editString.contains(".")) {
								String[] editStrings = editString.split("\\.");
								if (2 == editStrings.length) {
									editDouble = Double.parseDouble(editString);
									/*if (Math.abs(editDouble) >= 100000) {
										Toast.makeText(getActivity(),"��ֵ������Χ������������",Toast.LENGTH_SHORT).show();
										editString = "";
									} else {*/
										editString = String.format("%1$+5.1f",editDouble);
									//}
								} else {
									Toast.makeText(getActivity(),"���ָ�ʽ��������������",Toast.LENGTH_SHORT).show();
									editString = "";
								}
							} /*else {
								editDouble = Double.parseDouble(editString);
								if (Math.abs(editDouble) >= 100000) {
									Toast.makeText(getActivity(),"��ֵ������Χ������������",Toast.LENGTH_SHORT).show();
									editString = "";
								} else {
									//editString = String.format("%1$+5.1f",editDouble);
								}
							}
*/
						} else {
							Toast.makeText(getActivity(),"���ָ�ʽ��������������",Toast.LENGTH_SHORT).show();
							editString = "";
						}
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("numText",
								mAppList.get(position)
								.get(keyString[0])
								.toString());//
						map.put("contentText",
								mAppList.get(position)
								.get(keyString[1])
								.toString());
						map.put("setValueText", editString);
						map.put("noteText",
								mAppList.get(position)
								.get(keyString[3])
								.toString());
						mAppList.set(position, map);
						notifyDataSetChanged();
					}

				}).setNegativeButton("ȡ��", null).show();

			}
		}

	}

}
