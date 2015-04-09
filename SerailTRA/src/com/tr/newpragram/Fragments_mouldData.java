package com.tr.newpragram;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiReadMassiveDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
//import android.content.DialogInterface.OnClickListener;
import android.view.View.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.NumberKeyListener;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.dataInAddress.AddressPublic;
import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.explain.HexDecoding;
import com.explain.NCTranslate;
import com.explain.TableToBinary;
import com.explain.TableTranslate;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.tr.programming.Fragments_NCedit1;
import com.tr.programming.Fragments_NCedit2;
import com.tr.programming.Fragments_NCedit3;
import com.tr.programming.Fragments_NCedit4;
import com.tr.programming.Fragments_NCedit5;
import com.tr.programming.Fragments_NCedit6;
import com.tr.programming.Fragments_NCedit7;
import com.tr.programming.Fragments_NCedit8;
import com.tr.programming.Fragments_Table1;
import com.tr.programming.Fragments_Table2;
import com.tr.programming.Fragments_Table3;
import com.tr.programming.Fragments_Table4;
import com.tr.programming.Fragments_Table5;
import com.tr.programming.Fragments_Table6;
import com.tr.programming.Fragments_Table7;
import com.tr.programming.Fragments_Table8;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;
/**
 * �������ļ�ģ���ļ��н��в���
 * @author ������
 *
 */
public class Fragments_mouldData extends Fragment {

	private ListView listView_mould_setting;
	private MyAdapter adapter_mould_setting;
	private ArrayList<HashMap<String, Object>> list_mould_setting = ArrayListBound.getMouldDataListData();
	private Button newcreate;
	private Button read;
	private Button save;
	private Button saveas;
	private Button delete;
	private Button USBin;
	private Button USBout;
	private USBBroadCastReceiver mBroadcastReceiver;
	private String ioallString="";
	private TextView text_num_mould;
	private int listselectposition=0;

	private String errorString;
	private String readerrorString;
	private boolean hasError;
	//������е����ƽ����﷨���
	private ArrayList<String> list_mouldname=new ArrayList<String>();
	private ArrayList<String> list_mouldnum=new ArrayList<String>();
	
	private byte[] iobyteArray=null;

	private int[] overflag=new int[]{0,0,0,0,0,0,0,0,0,0,0,0};
	//private SharedPreferences sharedPreference_openedDir;
	//����������ͨ���й�

	private WifiSendDataFormat formatSendMessage;
	private WifiReadMassiveDataFormat formatReadMessage;
	private WifiReadDataFormat timeformatReadMessage;
	private  ChatListener DataFeedback1 ;	
	private int saveposition;
	private String saveStrname;
	private SendDataRunnable modelReadDataRunnable;
	private FinishRunnable readDataFinishRunnable;
	private  FinishRunnable sendDataFinishRunnable;
	private  WifiReadDataFormat formatReadusermode;
	private  SendDataRunnable sendDatausermodeRunnable;
	private WifiReadDataFormat mouldformatReadMessage;
	private SendDataRunnable sendDataRunnable;
	private byte[] getData;
	long afterTime;
	long beforeTime;
	ArrayList<SendDataRunnable> sendDataRunnables=new ArrayList<SendDataRunnable>();
	private int Downindex = 0;
	private boolean DownStatus = false;
	private boolean startDownLoad = false;

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
	
	
	int step_NCEdit1 = 0;
	int step_NCEdit2 = 0;
	int step_NCEdit3 = 0;
	int step_NCEdit4 = 0;
	int step_NCEdit5 = 0;
	int step_NCEdit6 = 0;
	int step_NCEdit7 = 0;
	int step_NCEdit8 = 0;
	
	int step_TableEdit1 = 0;
	int step_TableEdit2 = 0;
	int step_TableEdit3 = 0;
	int step_TableEdit4 = 0;
	int step_TableEdit5 = 0;
	int step_TableEdit6 = 0;
	int step_TableEdit7 = 0;
	int step_TableEdit8 = 0;
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try{
			
			list_mould_setting = ArrayListBound.getMouldDataListData();
			System.out.println("Fragments_mouldData   alarmQueryRunnable ");
	    	adapter_mould_setting.setSelectItem(listselectposition);
			adapter_mould_setting.notifyDataSetChanged();
		if(WifiSetting_Info.mClient==null){
		    text_num_mould.setText("     ��������λ����");//ģ�߱��
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private Runnable backMessageTodo=new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (null!=formatReadMessage.getFinalData()) {
				saveAsMouldData(saveStrname,formatReadMessage.getFinalData());
			}
			
			//		Constans.mouldData.writeFileFromByte(foldername, byteArray);
			adapter_mould_setting.notifyDataSetChanged();
			//saveAllData(saveStrname,list_mould_setting,saveposition,formatReadMessage.getFinalData());//�������ݵ��ö�
	        afterTime=System.currentTimeMillis();

	        System.out.println("���ջ���ʱ��"+(afterTime-beforeTime));
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Fragments_mouldData   onCreate ");
		IntentFilter iFilter = new IntentFilter();
	       iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
	       iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
	       iFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
	       iFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
	       iFilter.addDataScheme("file");
	       mBroadcastReceiver = new USBBroadCastReceiver();
	       getActivity().registerReceiver(mBroadcastReceiver, iFilter);

	}

	private class USBBroadCastReceiver extends BroadcastReceiver {
	      @Override
	      public void onReceive(Context context, Intent intent) {
	       String action = intent.getAction();

	       if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
              USBin.setEnabled(false);
              USBout.setEnabled(false);
	          //USB�豸�Ƴ�������UI     
	       } else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
	    	   USBin.setEnabled(true);
	           USBout.setEnabled(true);
	          //USB�豸���أ�����UI
	        }
	      }
	 }


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("Fragments_mouldData   onCreateView ");
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		return inflater.inflate(R.layout.tab_setting_mould_new, container,false);
	}

	private void WriteHeadfile(String filename,String date,String markname){
		// д���ļ�ͷ
		Constans.mouldData.writeFile(filename, "[Time]" + "/" + "\r\n");
		Constans.mouldData.writeFile(filename, date + "/" + "\r\n");
		Constans.mouldData.writeFile(filename, "[Step]" + "/" + "\r\n");
		Constans.mouldData.writeFile(filename,"Step=0"+ "/" + "\r\n");
		Constans.mouldData.writeFile(filename, markname + "/" + "\r\n");
		Constans.mouldData.writeFile(filename, "\r\n");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try{
		System.out.println("Fragments_mouldData   onActivityCreated ");
		newcreate=(Button) getActivity().findViewById(R.id.newcreate);
		read=(Button) getActivity().findViewById(R.id.read);
		save=(Button) getActivity().findViewById(R.id.save);
		saveas=(Button) getActivity().findViewById(R.id.saveas);
		delete=(Button) getActivity().findViewById(R.id.clear);
		USBin=(Button) getActivity().findViewById(R.id.leadin);
		USBout=(Button) getActivity().findViewById(R.id.leadout);
		
		newcreate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final View view_newcreate=View.inflate(getActivity(), R.layout.tab_setting_mould_newcreate, null);
				new AlertDialog.Builder(getActivity())
				.setTitle(R.string.programming_newcreate).setView(view_newcreate)
				.setPositiveButton("ȷ��",
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						EditText editmouldnum=(EditText)view_newcreate.findViewById(R.id.editText_mouldnum);
						EditText editmouldname=(EditText)view_newcreate.findViewById(R.id.editText_mouldname);
						String editmouldnumstr=editmouldnum.getText().toString();
						String editmouldnamestr=editmouldname.getText().toString();
						if(editmouldnumstr.equals("")){
							Toast.makeText(getActivity(), "ģ�����ݱ��Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						checkMouldList(list_mouldnum, list_mould_setting,"num_mould_setting");
						if (list_mouldnum.contains(String.format("%1$04d",Integer.parseInt(editmouldnumstr)))) {
							Toast.makeText(getActivity(), "ģ�����ݱ���ظ�������������",Toast.LENGTH_SHORT).show();
							return;
						}
						if(editmouldnamestr.equals("")){
							Toast.makeText(getActivity(), "ģ����������Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						checkMouldList(list_mouldname, list_mould_setting,"name_mould_setting");
						if (list_mouldname.contains(editmouldnamestr.toLowerCase())) {
							Toast.makeText(getActivity(), "ģ�����������ظ�������������",Toast.LENGTH_SHORT).show();
							return;
						}
						try {
							if (editmouldnamestr.getBytes("gb2312").length>18) {
								Toast.makeText(getActivity(), "ģ���������Ƴ��ȳ�����Χ������������",Toast.LENGTH_SHORT).show();
								return;
							}
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						
						//����Զ����һ��
						HashMap<String, Object> mapadd = new HashMap<String, Object>();
						mapadd.put("num",	String.format("%1$04d", list_mould_setting.size()+1));//
						mapadd.put("num_mould_setting","");
						mapadd.put("name_mould_setting","");
						mapadd.put("note_mould_setting","");
						list_mould_setting.add(mapadd);
						int intAll=checkAllNum(list_mould_setting);
						final String date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
						list_mould_setting.get(intAll).put("num_mould_setting",String.format("%1$04d",Integer.parseInt(editmouldnumstr)));
						list_mould_setting.get(intAll).put("name_mould_setting", editmouldnamestr);
						list_mould_setting.get(intAll).put("note_mould_setting", date2);
						
						listselectposition=intAll;
				    	adapter_mould_setting.setSelectItem(intAll);
						adapter_mould_setting.notifyDataSetChanged();
						
						saveMouldDataList(list_mould_setting,date2,intAll);
						Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString()).createFile(Constans.�豸��������);
						Constans.mouldData.cleanFile(Constans.�豸��������);
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceActualInput]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceActualOutput]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceMiddleInput]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceMiddleOutput]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceDataInput]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceDataOutput]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceRegister]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceDataRegister]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceTimer]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceCounter]");
						WriteHeadfile(Constans.�豸��������,date2,"[DevicePosition]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceAlarm]");
						WriteHeadfile(Constans.�豸��������,date2,"[DeviceOptional]");
						Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString()).createFile(Constans.nc�ı�����);
						Constans.mouldData.cleanFile(Constans.nc�ı�����);
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit1]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit2]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit3]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit4]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit5]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit6]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit7]");
						WriteHeadfile(Constans.nc�ı�����,date2,"[NCEdit8]");
						
						Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString()).createFile(Constans.table�ı�����);
						Constans.mouldData.cleanFile(Constans.table�ı�����);
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit1]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit2]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit3]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit4]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit5]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit6]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit7]");
						WriteHeadfile(Constans.table�ı�����,date2,"[TableEdit8]");
						copyFile(Constans.mouldDataPATH+editmouldnamestr.toString(),"ncdata");
						copyFile(Constans.mouldDataPATH+editmouldnamestr.toString(),"tabledata");
					}
				}).setNegativeButton("ȡ��",null).show();
				
			}
		});	
		
		read.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim().equals("")
						&&!list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().equals("")) {
				//�����������ݣ�����ģ�����ݺ��趨���ݣ�ͬʱ��ȡ�������ļ���
				//i=0;
				try {
					if(list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().getBytes("gb2312").length>18){
						Toast.makeText(getActivity(), "ģ���������Ƴ��ȳ�����Χ�����޸ģ�",Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				new AlertDialog.Builder(getActivity())
			    .setTitle("��ʾ").setMessage("ȷ����ȡ��"+list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim()+"����")
			    .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() 
			    {
				 public void onClick(DialogInterface dialog, int which) {
				    openAllData(list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim());				
				 }
			    }).setNegativeButton("ȡ��",null).show();
				//				//����list,���ļ���ֱ����ת��λ�ú��ٶ�ѡ���������²�������ת����ʾ�仯
//				Fragment_posV_spinner.positionList_setting=Fragment_posV_spinner.updatePositionList_setting(ArrayListBound.getDevicePositionListData());
//			
//				Fragment_posV_spinner.fragmentShowing_posV_setting=null;//ȥ������һ��������ʾ��fragment
//				Fragment_posV_spinner.newOpenMouldData=true;//�´򿪵ı�־λ
				//mpeng
			}else{
				Toast.makeText(getActivity(),"���뽫ģ�߱�ź�˵����д�����󣻲��ܽ��ж�ȡ����", Toast.LENGTH_SHORT).show();
			}
			}
		});

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim().equals("")
						&&!list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().equals("")) {
				//����Ҫ������ʾ��ȷ��
				String titlemsg="�Ƿ񱣴����ݵ���"+list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim()+"��?";
				SpannableStringBuilder style=new SpannableStringBuilder(titlemsg);
				if (!list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", "").trim())
						&&!TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", "").trim().equals("")) {
					titlemsg="��ǰģ�������ǡ�"+TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", "").trim()+"��,\n�Ƿ񸲸Ǳ��浽��"+list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim()+"��?";
					 style=new SpannableStringBuilder(titlemsg); 
					 style.setSpan(new BackgroundColorSpan(Color.RED),0,titlemsg.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //����ָ��λ��textview�ı�����ɫ
					 
				}
				new AlertDialog.Builder(
						getActivity())
				.setTitle("����")
				.setMessage(style)
				.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						
						//��ȡ���ݣ���ȡ��λ��ģ������
						//ȷ����λ��

						WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
						WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(), "���������", "��ȴ�", true, false); 
						
						final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��

						list_mould_setting.get(listselectposition).put("note_mould_setting", date);
						adapter_mould_setting.notifyDataSetChanged();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_num", list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_name", list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", date).commit();
						//TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_all", strAll).commit();

						saveStrname=list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim();
						saveposition=listselectposition;
//						Intent it = new Intent();
//						it.setAction("ThreadOption");
//						it.putExtra("Option", "StopThread");
//						getActivity().sendBroadcast(it);
						creatFolderSysytem();
						
						 new Thread()
			              {
			                  public void run()
			                  {
								saveAs_DeviceDefine(saveStrname,date);
								saveAs_NcEdit(saveStrname,date);
								saveAs_TableEdit(saveStrname,date);
		
								saveMouldDataList(list_mould_setting,date,listselectposition);
								//��ʼ������Ϣ�ļ��������ж������߱��滹�����߱���
								if(WifiSetting_Info.mClient==null){
									saveAsMouldData(saveStrname,new byte[76]);
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
									//Toast.makeText(getActivity(),"�����ߣ�mould.trt�޷����Ǳ��浽"+saveStrname , Toast.LENGTH_LONG).show();
								}else {
									try {
										//��ʼ����λ��������ʵ��
										formatReadMessage=new WifiReadMassiveDataFormat(AddressPublic.model_Head,AddressPublic.model_End-AddressPublic.model_Head);
										modelReadDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());
		                                readDataFinishRunnable=new FinishRunnable(getActivity(), "�ѳɹ���ȡ��λ����ǰģ����Ϣ�����桶"+saveStrname+"��",WifiSetting_Info.progressDialog,backMessageTodo );
		                                modelReadDataRunnable.setOnlistener(new NormalChatListenner(modelReadDataRunnable, readDataFinishRunnable));
										getActivity().runOnUiThread(modelReadDataRunnable);
										//����д���˴��������ȡ����λ�������д��
										//backmessagetodo�߳��������saveAlldata
										//saveAllData(saveStrname,list_mould_setting,saveposition,byteArrayBaoCun);//�������ݵ��ö�
									} catch (Exception e) {
										// TODO: handle exception
										if(WifiSetting_Info.progressDialog!=null){
											overflag[0]=1;
											WifiSetting_Info.progressDialog.dismiss();
											WifiSetting_Info.progressDialog=null;
										}
										e.printStackTrace();
									}
								}
					                  }
					              }.start();
								 WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {
								@Override
								public void onDismiss(DialogInterface arg0) {
									// TODO Auto-generated method stub
									 if(WifiSetting_Info.mClient==null){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("�����߱���ɹ���mould.trt�޷����Ǳ��浽��"+saveStrname+"��")
										   .setPositiveButton("ȷ��", null).show();
										}else if(overflag[0]==1){
											new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("�����쳣�����ز�����")
											   .setPositiveButton("ȷ��", null).show();
											overflag[0]=0;
										}
								}  
					        });
					}
				}).setNegativeButton("ȡ��", null).show();
			}else{
				Toast.makeText(getActivity(),"���뽫ģ�߱�ź�˵����д�����󣻲��ܽ��б������", Toast.LENGTH_SHORT).show();
			}
			}
		});
		
		saveas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim().equals("")
						&&!list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().equals("")) {
				final View view_newcreate=View.inflate(getActivity(), R.layout.tab_setting_mould_newcreate, null);
				new AlertDialog.Builder(getActivity())
				.setTitle("���Ϊ����").setView(view_newcreate)
				.setPositiveButton("ȷ��",
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						EditText editmouldnum=(EditText)view_newcreate.findViewById(R.id.editText_mouldnum);
						EditText editmouldname=(EditText)view_newcreate.findViewById(R.id.editText_mouldname);
						String editmouldnumstr=editmouldnum.getText().toString();
						String editmouldnamestr=editmouldname.getText().toString();
						if(editmouldnumstr.equals("")){
							Toast.makeText(getActivity(), "ģ�����ݱ��Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						checkMouldList(list_mouldnum, list_mould_setting,"num_mould_setting");
						if (list_mouldnum.contains(String.format("%1$04d",Integer.parseInt(editmouldnumstr)))) {
							Toast.makeText(getActivity(), "ģ�����ݱ���ظ�������������",Toast.LENGTH_SHORT).show();
							return;
						}
						if(editmouldnamestr.equals("")){
							Toast.makeText(getActivity(), "ģ����������Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
							return;
						}
						checkMouldList(list_mouldname, list_mould_setting,"name_mould_setting");
						if (list_mouldname.contains(editmouldnamestr.toLowerCase())) {
							Toast.makeText(getActivity(), "ģ�����������ظ�������������",Toast.LENGTH_SHORT).show();
							return;
						}
						try {
							if (editmouldnamestr.getBytes("gb2312").length>18) {
								Toast.makeText(getActivity(), "ģ���������Ƴ��ȳ�����Χ������������",Toast.LENGTH_SHORT).show();
								return;
							}
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//Long longtime=Calendar.getInstance().getTimeInMillis();
						//String date2 = new Date(longtime).toLocaleString();// ��ǰʱ��
						//����Զ����һ��
						HashMap<String, Object> mapadd = new HashMap<String, Object>();
						mapadd.put("num",	String.format("%1$04d", list_mould_setting.size()+1));//
						mapadd.put("num_mould_setting","");
						mapadd.put("name_mould_setting","");
						mapadd.put("note_mould_setting","");
						list_mould_setting.add(mapadd);
						
						int intAll=checkAllNum(list_mould_setting);
						final String date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
						list_mould_setting.get(intAll).put("num_mould_setting",String.format("%1$04d",Integer.parseInt(editmouldnumstr)));
						list_mould_setting.get(intAll).put("name_mould_setting", editmouldnamestr);
						list_mould_setting.get(intAll).put("note_mould_setting", date2);
						
						saveMouldDataList(list_mould_setting,date2,intAll);
						listselectposition=intAll;
				    	adapter_mould_setting.setSelectItem(intAll);
						adapter_mould_setting.notifyDataSetChanged();
						//Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString()).createFile(Constans.�豸��������);
						//Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString()).createFile(Constans.nc�ı�����);
						//Constans.mouldData.createFolder(Constans.mouldDataPATH, editmouldnamestr.toString()).createFile(Constans.table�ı�����);
					
						//Constans.mouldData.copyDirectiory(Constans.mouldDataPATH + list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim() + File.separator
							//	,Constans.mouldDataPATH + editmouldnamestr.toString() + File.separator);//ͬʱѡ�е��ļ��и��Ƶ��½��ļ�
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
						saveAs_DeviceDefine(saveStrname,date2);
						saveAs_NcEdit(saveStrname,date2);
						saveAs_TableEdit(saveStrname,date2);

						//��ʼ������Ϣ�ļ��������ж������߱��滹�����߱���
						if(WifiSetting_Info.mClient==null){
							saveAsMouldData(saveStrname,new byte[76]);
							WifiSetting_Info.progressDialog.dismiss();
							WifiSetting_Info.progressDialog=null;
							//Toast.makeText(getActivity(),"�����ߣ�mould.trt�޷����Ǳ��浽"+saveStrname , Toast.LENGTH_LONG).show();
						}else {
							try {
								//��ʼ����λ��������ʵ��
								formatReadMessage=new WifiReadMassiveDataFormat(AddressPublic.model_Head,AddressPublic.model_End-AddressPublic.model_Head);
								modelReadDataRunnable=new SendDataRunnable(formatReadMessage, getActivity());
                                readDataFinishRunnable=new FinishRunnable(getActivity(), "�ѳɹ���ȡ��λ����ǰģ����Ϣ�����桶"+saveStrname+"��",WifiSetting_Info.progressDialog,backMessageTodo );
                                modelReadDataRunnable.setOnlistener(new NormalChatListenner(modelReadDataRunnable, readDataFinishRunnable));
								getActivity().runOnUiThread(modelReadDataRunnable);
								//����д���˴��������ȡ����λ�������д��
								//backmessagetodo�߳��������saveAlldata
								//saveAllData(saveStrname,list_mould_setting,saveposition,byteArrayBaoCun);//�������ݵ��ö�
							} catch (Exception e) {
								// TODO: handle exception
								if(WifiSetting_Info.progressDialog!=null){
									overflag[0]=1;
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								e.printStackTrace();
							}
						}
			                  }
			              }.start();
						 WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {
								@Override
								public void onDismiss(DialogInterface arg0) {
									// TODO Auto-generated method stub
									 if(WifiSetting_Info.mClient==null){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("�����߱���ɹ���mould.trt�޷����Ǳ��浽��"+saveStrname+"��")
										   .setPositiveButton("ȷ��", null).show();
										}else if(overflag[0]==1){
											new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("�����쳣�����ز�����")
											   .setPositiveButton("ȷ��", null).show();
											overflag[0]=0;
										}
								}  
					        });
					}
				}).setNegativeButton("ȡ��",null).show();
				}else{
					Toast.makeText(getActivity(),"���뽫ģ�߱�ź�˵����д�����󣻲��ܽ������Ϊ����", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim().equals("")
						&&!list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().equals("")) {
					if (list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim().equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", "").trim())) {
						new AlertDialog.Builder(getActivity())
						.setTitle("ɾ������")
						.setMessage("����ɾ����ǰ�Ѵ򿪵��ļ�����������ļ�����ɾ��")
						.setPositiveButton("֪����",null)
						.show();
					}else {
						new AlertDialog.Builder(getActivity())
						.setTitle("ɾ������")
						.setMessage("ȷ��ɾ����"+list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim()+"����ɾ�����ļ��в��ɻָ�")
						.setPositiveButton("ȷ��ɾ��",
								new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,int which) {
								String deletename=list_mould_setting.get(listselectposition).get("name_mould_setting").toString().trim();
								list_mould_setting.remove(listselectposition);
								for ( int restposition=listselectposition ; restposition < list_mould_setting.size(); restposition++) {
									list_mould_setting.get(restposition).put("num", String.format("%1$04d", restposition+1));
								}
								//����Զ����һ��,��֤������20��
								HashMap<String, Object> mapadd = new HashMap<String, Object>();
								mapadd.put("num",	String.format("%1$04d", list_mould_setting.size()+1));//
								mapadd.put("num_mould_setting","");
								mapadd.put("name_mould_setting","");
								mapadd.put("note_mould_setting","");
								list_mould_setting.add(mapadd);
								adapter_mould_setting.notifyDataSetChanged();

								//ɾ�����ݣ���Ļ��ʾΪ�գ�������mouldList.trt�ļ�
								clearAllData(deletename);

								//strAll=checkAllNum(list_mould_setting);
								//��ʾ��ǰ������ģ������
								//text_all_mould.setText(strAll);
								//TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_all", strAll).commit();

							}
						}).setNegativeButton("ȡ��", null).show();
					}
				}else{
					Toast.makeText(getActivity(),"���뽫ģ�߱�ź�˵����д�����󣻲��ܽ���ɾ������", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		readMouldDataList(list_mould_setting);//��ȡmouldList��trt�ļ�������list_mould_setting
		listView_mould_setting=(ListView) getActivity().findViewById(R.id.listView_mould_setting);
		if(listView_mould_setting==null){return;}
		adapter_mould_setting = new MyAdapter(getActivity(),list_mould_setting,R.layout.setting_mould_item,
				new String[] { "num","num_mould_setting", "name_mould_setting", "note_mould_setting" },
				new int[] { R.id.num,R.id.num_mould_setting, R.id.name_mould_setting,R.id.note_mould_setting });
		listView_mould_setting.setAdapter(adapter_mould_setting);
		
		new ChatListener() {
			@Override
			public void onChat(byte[] message) {

				//���صı�־λSTS1�����ж��Լ���У��
				mouldformatReadMessage.backMessageCheck(message);
				if(!mouldformatReadMessage.finishStatus()){
					getActivity().runOnUiThread(sendDataRunnable);			
				}else {
					//������ȷ�����
					//�����ص�����	
					getData=new byte[mouldformatReadMessage.getLength()];
					//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
					System.arraycopy(message, 8, getData, 0, mouldformatReadMessage.getLength());
					// TODO Auto-generated method stub
					WifiSetting_Info.blockFlag=true;
						}
					}
			
		};

		
		text_num_mould=(TextView) getActivity().findViewById(R.id.text_num_mould);

		if(text_num_mould==null){return;}

				DataFeedback1 = new ChatListener() {
				@Override
				public void onChat(byte[] message) {

					//���صı�־λSTS1�����ж��Լ���У��
					timeformatReadMessage.backMessageCheck(message);
					if(!timeformatReadMessage.finishStatus()){
						getActivity().runOnUiThread(sendDataRunnable);			
					}else {
						//������ȷ�����
						//�����ص�����	
						WifiSetting_Info.blockFlag = true;
						byte[] numbyte=new byte[2];
						byte[] namebyte=new byte[18];
						byte[] timebyte=new byte[8];
						//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
						System.arraycopy(message, 8, numbyte, 0,2);
						System.arraycopy(message, 10, namebyte, 0,18);
						System.arraycopy(message, 28, timebyte, 0,8);
						try {
							final String wifiNum = String.format("%04d",HexDecoding.Array2Toint(numbyte)).trim();
						    final String wifiName = new String(namebyte,"GBK").toString().trim();
						
						String year=String.valueOf((int)(timebyte[0]&0xff));
						String month=String.valueOf((int)(timebyte[1]&0xff));
						String day=String.valueOf((int)(timebyte[2]&0xff));
						String hour=String.valueOf((int)(timebyte[3]&0xff));
						String miun=String.valueOf((int)(timebyte[4]&0xff));
						String mill=String.valueOf((int)(timebyte[5]&0xff));
						if(month.length()==1){month="0"+month;}
						if(day.length()==1){day="0"+day;}
						if(hour.length()==1){hour="0"+hour;}
						if(miun.length()==1){miun="0"+miun;}
						if(mill.length()==1){mill="0"+mill;}
						final String wifiTime ="20"+year+"-"+month+"-"+day+" "+hour+":"+miun+":"+mill;
						
	                    Runnable barShowRunnable=new Runnable() {
	    					public void run() {
	    						
	    						for(int i=0;i<list_mould_setting.size();i++){
	    							String bdstrNum=list_mould_setting.get(i).get("num_mould_setting").toString().trim();//ģ�߱��
	    							String bdstrName=list_mould_setting.get(i).get("name_mould_setting").toString().trim();//ģ������
	    							String bdstrTime=list_mould_setting.get(i).get("note_mould_setting").toString().trim();//ʱ��
	    						    System.out.println("wifiNum="+wifiNum+"   wifiName="+wifiName+"   wifiTime="+wifiTime);
	    						    System.out.println("strNum="+bdstrNum+"   strName="+bdstrName+"   strTime="+bdstrTime);
	    						    if(bdstrNum.equals(wifiNum)&&bdstrName.equals(wifiName)&&bdstrTime.equals(wifiTime)){
	    						    	listselectposition=i;
	    						    	adapter_mould_setting.setSelectItem(i);
	    								adapter_mould_setting.notifyDataSetChanged();
	    								text_num_mould.setText("     ��ǰģ�����:"+list_mould_setting.get(i).get("num").toString().trim());//ģ�߱��
	    								break;
	    						    }
	    						    if(i==list_mould_setting.size()-1){
	    						    	text_num_mould.setText("     ��λ����ǰ��ģ��������ƽ�嵱ǰ��ģ�����鲻һ�£������������ԣ��������������ģ�����飡");
	    						    }
	    						}
	    						
	    					}
	    				};
	    			
	    				getActivity().runOnUiThread(barShowRunnable);
	    				

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  }
					}
				
			};
			if (WifiSetting_Info.mClient != null) {
			timeformatReadMessage=new WifiReadDataFormat(AddressPublic.model_Head,28);
			try {
				sendDataRunnable = new SendDataRunnable(DataFeedback1, timeformatReadMessage,getActivity());
				getActivity().runOnUiThread(sendDataRunnable);
			} catch (Exception e) {
				Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
			}
			}


		// ��ӵ��
		listView_mould_setting.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,final int position, long arg3) {
				     listselectposition=position;
					// ѡ�к�ɫ��ʾ
					adapter_mould_setting.setSelectItem(position);
					adapter_mould_setting.notifyDataSetChanged();
					//ֻ��ģ���������Ʋ�Ϊ�յĲſ��Խ�����ز�������Ȼ������ʾ
					//strNum=list_mould_setting.get(position).get("num_mould_setting").toString().trim();//ģ�߱��
					//strName=list_mould_setting.get(position).get("name_mould_setting").toString().trim();//ģ������
					//strTime=list_mould_setting.get(position).get("note_mould_setting").toString().trim();//ʱ��
             } 
		});

	}catch(Exception e){
		e.printStackTrace();
	} 
	}
	
	/**
	 * 
	**********************************************************
	* @Title: checkAllNum 
	* @Description: �õ�ģ�����ݵ�����
	* @param list_mould_setting
	* @return    
	* @return String    
	* @throws 
	**********************************************************
	 */
	public static int checkAllNum(ArrayList<HashMap<String, Object>> list_mould_setting) {
		int allNum=0;
		for (int i = 0; i < list_mould_setting.size(); i++) {
			String num=list_mould_setting.get(i).get("num_mould_setting").toString().trim();//ģ�߱��
			String name=list_mould_setting.get(i).get("name_mould_setting").toString().trim();//ģ������
			if (!num.equals("") || !name.equals("")) {
				allNum++;
			}
		}
		return allNum;
	}
	/**
	 * 
	**********************************************************
	* @Title: getByteArray_mould_head ��ǰ���ڽ���״̬
	* @Description: �ṩ�ɸ���ĥ�����ݵı�š����ơ�ʱ��ת����byt����
	* @param strNum
	* @param strName
	* @param strTime
	* @return    
	* @return Byte[]    
	* @throws 
	**********************************************************
	 */
	private byte[] getByteArray_mould_head(String strNum,String strName,String strTime) {
		//"UTF-8"���룬�Ǻ���ת����Ŷ1���ֽڣ�ÿ������ת��Ϊ3���ֽ�
//		unsigned short		Block;				// Block No.  ���뷶Χֻ�������֣�0~32767���ݶ�6���ֽڣ�����λ����0���
//		unsigned char		Comment[18];			// Comment ����    ����18x3���ֽ�
//		unsigned char		date[8];				// ������  NNNNYYRR ����8x3���ֽ�
		
		byte[] byteArray_head_num = null;
		byte[] byteArray_head_name = null;
		byte[] byteArray_head_time = null;
		byte[] byteArray_head=new byte[84];
		
		try {
			byteArray_head_num=strNum.getBytes("UTF-8");
			byteArray_head_name=strName.getBytes("UTF-8");
			byteArray_head_time=strTime.getBytes("UTF-8");
			
//			System.out.println(byteArray_head_num.length);
//			for (int i = 0; i < byteArray_head_num.length; i++) {
//				System.out.println(byteArray_head_num[i]);
//			}
//			System.out.println(byteArray_head_name.length);
//			for (int i = 0; i < byteArray_head_name.length; i++) {
//				System.out.println(byteArray_head_name[i]);
//			}
//			System.out.println(byteArray_head_time.length);
//			for (int i = 0; i < byteArray_head_time.length; i++) {
//				System.out.println(byteArray_head_time[i]);
//			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "byteArray_head_num ����", Toast.LENGTH_SHORT).show();
		}
		
		System.arraycopy(byteArray_head_num, 0, byteArray_head, 0, byteArray_head_num.length);//0~5
		System.arraycopy(byteArray_head_name, 0, byteArray_head, 6, byteArray_head_name.length);//6~59
		System.arraycopy(byteArray_head_time, 0, byteArray_head, 60, byteArray_head_time.length);//60~83
		
		System.out.println(byteArray_head.length);
//		for (int i = 0; i < byteArray_head.length; i++) {
//			System.out.println(byteArray_head[i]);
//		}
		
		try {
			System.out.println(new String(byteArray_head,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Toast.makeText(getActivity(), "byteArray_head  ����", Toast.LENGTH_SHORT).show();
		}
		
//	
		
		
		return byteArray_head;
	}
	/**
	 * 
	**********************************************************
	* @Title: getStrNumFromByteArray 
	* @Description: ��byteArray�еõ�ģ�߱�ţ���ûд��
	* @param byteArray_mould_head
	* @return    
	* @return String    
	* @throws 
	**********************************************************
	 */
	private String getStrNumFromByteArray(byte[] byteArray_mould_head) {

//		byte[] byteArray_head_num_2 = new byte[6];
//		byte[] byteArray_head_name_2 = new byte[54];
//		byte[] byteArray_head_time_2 = new byte[24];
//		
//		System.arraycopy(byteArray_head, 0, byteArray_head_num_2, 0, 6);//0~5
//		System.arraycopy(byteArray_head, 6, byteArray_head_name_2, 0, 54);//6~59
//		System.arraycopy(byteArray_head, 60, byteArray_head_time_2, 0, 24);//60~83
//		
//		byte[]  byteArray_head_name_3 = null;
//		for (int i = 0; i < byteArray_head_name_2.length; i++) {
//			if (byteArray_head_name_2[i]==0) {
//				System.arraycopy(byteArray_head_name_2, 0, byteArray_head_name_3, 0, i);
//			}
//		}
//		
//		
//		try {
//			System.out.println(new String(byteArray_head_num_2,"UTF-8"));
//			System.out.println(new String(byteArray_head_name_2,"UTF-8"));
//			System.out.println(new String(byteArray_head_name_3,"UTF-8"));
//			System.out.println(new String(byteArray_head_time_2,"UTF-8"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			Toast.makeText(getActivity(), "byteArray_head_num_2  ����", Toast.LENGTH_SHORT).show();
//		}
		
		return null;
	}

	/**
	 * 
	 * @Title: readMouldData 
	 * @Description: ��ȡmouldList��trt�ļ�������list_mould_setting����ȡ֮ǰ���뽨���ļ���ϵͳ
	 * @param ArrayList    
	 * @return void    
	 * @throws
	 */
	private void readMouldDataList(ArrayList<HashMap<String, Object>> ArrayList) {
		creatFolderSysytem();
		
		String[] readlist_mould = Constans.mouldData.readOutFile(Constans.mouldDataPATH + File.separator + "mouldList.trt");// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist_mould.length; i++) {
			readlist_mould[i] = readlist_mould[i].replace("\r\n", "").trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
			//			System.out.println(readlist_mould[i]);
		}

		//���readlist_mould.length/4�ĳ��ȱ�ArrayList�ĳ��������ʵʱ���ӳ���,�����ӵ���һ��
		int lineToAdd=readlist_mould.length/4-ArrayList.size();

		if (lineToAdd>=0) {//
			for (int i = 0; i < lineToAdd+1; i++) {
				HashMap<String, Object> mapadd = new HashMap<String, Object>();
				mapadd.put("num", String.format("%04d", ArrayList.size()+1));
				mapadd.put("num_mould_setting", "");
				mapadd.put("name_mould_setting", "");
				mapadd.put("note_mould_setting", "");		
				ArrayList.add(ArrayList.size(), mapadd);

			}
		}



		for (int i = 0; i < readlist_mould.length/4; i++) {
			ArrayList.get(i).put("num_mould_setting",String.format("%04d",Integer.parseInt(readlist_mould[4*i+1])));
			ArrayList.get(i).put("name_mould_setting",readlist_mould[4*i+2]);
			ArrayList.get(i).put("note_mould_setting",readlist_mould[4*i+3]);
		}


	}
	/**
	 * @param position 
	 * 
	 * @Title: saveAllData 
	 * @Description: �豸��������ݡ�nc�༭�����ݡ�table�༭�����ݣ�mould��trt�ļ�
	 * @param foldername
	 * @param ArrayList    
	 * @return void    
	 * @throws
	 */
/*	private void saveAllData(String foldername,ArrayList<HashMap<String, Object>> ArrayList, int position, byte[] bytetoPinban) {

		final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
		creatFolderSysytem();

		
		saveAs_DeviceDefine(saveStrname,date);
		saveAs_NcEdit(saveStrname,date);
		saveAs_TableEdit(saveStrname,date);

		saveMouldDataList(list_mould_setting,date,position);
		
		if (null!=bytetoPinban) {
			saveAsMouldData(foldername,bytetoPinban);
		}
		
		//		Constans.mouldData.writeFileFromByte(foldername, byteArray);
		adapter_mould_setting.notifyDataSetChanged();

	}*/
//	private void saveAllData(String foldername,ArrayList<HashMap<String, Object>> ArrayList, int position) {
//
//		final String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
//		creatFolderSysytem();
//
//		saveAs_DeviceDefine(foldername,date);
//		saveAs_NcEdit(foldername,date);
//		saveAs_TableEdit(foldername,date);
//
//		saveMouldData(list_mould_setting,date,position);
//
//		//saveAsMouldData(foldername,byteArray);
//		//		Constans.mouldData.writeFileFromByte(foldername, byteArray);
//		adapter_mould_setting.notifyDataSetChanged();
//
//	}
	/**
	 * 
	 * @Title: saveAsMouldData 
	 * @Description: ����byteArray���ļ�mould.trt
	 * @param foldername
	 * @param byteArray2    
	 * @return void    
	 * @throws
	 */
	private void saveAsMouldData(String foldername, byte[] byteArray2) {
		try {
		byte[] byteArray=new byte[AddressPublic.model_End-AddressPublic.model_Head];
		Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername).createFile("mould.trt");
		byteArray=readMouldData2(foldername);//�ṩ����Ľӿ�
	    Constans.mouldData.cleanFile("mould.trt");
		    
		if(byteArray2.length!=76){
			System.arraycopy(byteArray,0, byteArray2, 0x00000000, 76);
			Constans.mouldData.writeFileFromByte("mould.trt", byteArray2);
		}else{
			Constans.mouldData.writeFileFromByte("mould.trt", byteArray);
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * 
	 * @Title: saveAs_DeviceDefine 
	 * @Description: �����豸�����ļ�
	 * @param foldername
	 * @param date    
	 * @return void    
	 * @throws
	 */
	public static  void saveAs_DeviceDefine(String foldername, String date) {
		Constans.dataToDownload.createFolder(Constans.mouldDataPATH+ foldername + File.separator, Constans.dataToDownloadFolder);

		Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername).createFile(Constans.�豸��������);
		Constans.mouldData.cleanFile(Constans.�豸��������);
		
		saveAs_8before(foldername,
				ArrayListBound.getDeviceActualInputListData(),
				"[DeviceActualInput]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceActualOutputListData(),
				"[DeviceActualOutput]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceMiddleInputListData(),
				"[DeviceMiddleInput]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceMiddleOutputListData(),
				"[DeviceMiddleOutput]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceDataInputListData(),
				"[DeviceDataInput]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceDataOutputListData(),
				"[DeviceDataOutput]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceRegisterListData(),
				"[DeviceRegister]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_8before(foldername,
				ArrayListBound.getDeviceDataRegisterListData(),
				"[DeviceDataRegister]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername, ArrayListBound.getDeviceTimerListData(),
				"[DeviceTimer]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername,
				ArrayListBound.getDeviceCounterListData(),
				"[DeviceCounter]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername,
				ArrayListBound.getDevicePositionListData(),
				"[DevicePosition]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_4later(foldername, ArrayListBound.getDeviceAlarmListData(),
				"[DeviceAlarm]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
		saveAs_Optional(foldername, ArrayListBound.getDeviceOptionalListData(),
				"[DeviceOptional]", date);
		Constans.mouldData.writeFile(Constans.�豸��������, "\r\n");// ��ͬ��֮���һ��
	}
	/**
	 * 
	 * @Title: saveAs_8before 
	 * @Description: �����豸�����ļ���ǰ8��
	 * @param foldername
	 * @param ArrayList
	 * @param markname
	 * @param date    
	 * @return void    
	 * @throws
	 */
	private static  void saveAs_8before(String foldername,
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
		Constans.mouldData.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, date + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, "[Step]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, markname + "/" + "\r\n");
		
		//Constans.mouldData.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n"+date + "/" + "\r\n"
		//+"[Step]" + "/" + "\r\n"+"Step" + "=" + String.valueOf(step) + "/" + "\r\n"+markname + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		//int num=0;
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
				Constans.mouldData.writeFile(Constans.�豸��������, addresssString
						+ "/" + symbolString + "/" + signalString + "/"
						+ notesString + "/" + "\r\n");
			}
		}
	}
	/**
	 * 
	 * @Title: saveAs_4later 
	 * @Description: �����豸�����ļ��ĺ�4��
	 * @param foldername
	 * @param ArrayList
	 * @param markname
	 * @param date    
	 * @return void    
	 * @throws
	 */
	private static void saveAs_4later(String foldername,
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
		Constans.mouldData.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, date + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, "[Step]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, markname + "/" + "\r\n");
		
		//Constans.mouldData.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n"+date + "/" + "\r\n"+
			//	"[Step]" + "/" + "\r\n"+"Step" + "=" + String.valueOf(step) + "/" + "\r\n"+markname + "/" + "\r\n");
		// ѭ��д��ÿһ�д��뵽�ļ���
		//int num=0;
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
					Constans.mouldData
					.writeFile(Constans.�豸��������, addresssString + "/"
							+ symbolString + "/" + notesString + "/"
							+ "\r\n");
				}else{
					Constans.mouldData.writeFile(Constans.�豸��������, addresssString + "/"
						+ symbolString + "/"  + signalString + "/"+ notesString + "/"+ setString + "/" + "\r\n");
				}
			}
		}
		
	}
	
	private static void saveAs_Optional(String foldername,
			ArrayList<HashMap<String, Object>> ArrayList, String markname,
			String date) {
		// д���ļ�ͷ
		Constans.mouldData.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, date + "/" + "\r\n");
	    Constans.mouldData.writeFile(Constans.�豸��������, "[Step]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������,
				"Step" + "=" + 32 + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.�豸��������, markname + "/" + "\r\n");
		
		//Constans.mouldData.writeFile(Constans.�豸��������, "[Time]" + "/" + "\r\n"+date + "/" + "\r\n"
		//+"[Step]" + "/" + "\r\n"+"Step" + "=" + 9 + "/" + "\r\n"+markname + "/" + "\r\n");
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
			Constans.mouldData.writeFile(Constans.�豸��������, addresssString + "/"
					+ name1 + "/" + name2 + "/" + name3 + "/" + "\r\n");
			
		}

	}
	/**
	 * 
	 * @Title: saveAs_NcEdit 
	 * @Description: ����NcEdit�ļ�
	 * @param foldername
	 * @param date    
	 * @return void    
	 * @throws
	 */
	private static  void saveAs_NcEdit(String foldername, String date) {

		Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername).createFile(Constans.nc�ı�����);
		//		Constans.dataToDownload.createFolder(Constans.mouldDataPATH+ foldername + File.separator, Constans.dataToDownloadFolder);
		Constans.mouldData.cleanFile(Constans.nc�ı�����);
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList1Data(),
				"[NCEdit1]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList2Data(),
				"[NCEdit2]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList3Data(),
				"[NCEdit3]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList4Data(),
				"[NCEdit4]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList5Data(),
				"[NCEdit5]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList6Data(),
				"[NCEdit6]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList7Data(),
				"[NCEdit7]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8nc(foldername,
				ArrayListBound.getNCeditList8Data(),
				"[NCEdit8]", date);
		Constans.mouldData.writeFile(Constans.nc�ı�����, "\r\n");// ��ͬ��֮���һ��
		
	}
	public static void saveAs_8nc(String foldername,
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
		Constans.mouldData.writeFile(Constans.nc�ı�����, "[Time]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.nc�ı�����, date + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.nc�ı�����, "[Step]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.nc�ı�����,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.nc�ı�����, markname + "/" + "\r\n");
		
		//Constans.mouldData.writeFile(Constans.nc�ı�����, "[Time]" + "/" + "\r\n"+date + "/" + "\r\n"+
				//"[Step]" + "/" + "\r\n"+"Step" + "=" + String.valueOf(step) + "/" + "\r\n"+markname + "/" + "\r\n");
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
				Constans.mouldData.writeFile(Constans.nc�ı�����, addresssString + "/"
						+ numString + "/" + orderString + "/" + operatString
						+ "/" + notesString + "/" + "\r\n");
			}

		}

	}
	/**
	 * 
	 * @Title: saveAs_TableEdit 
	 * @Description: ����TableEdit�ļ�
	 * @param foldername
	 * @param date    
	 * @return void    
	 * @throws
	 */
	public static void saveAs_TableEdit(String foldername, String date) {
		Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername).createFile(Constans.table�ı�����);
		//		Constans.dataToDownload.createFolder(Constans.mouldDataPATH+ foldername + File.separator, Constans.dataToDownloadFolder);
		Constans.mouldData.cleanFile(Constans.table�ı�����);

		saveAs_8table(foldername,
				ArrayListBound.getTableList1Data(),
				"[TableEdit1]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList2Data(),
				"[TableEdit2]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList3Data(),
				"[TableEdit3]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList4Data(),
				"[TableEdit4]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList5Data(),
				"[TableEdit5]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList6Data(),
				"[TableEdit6]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList7Data(),
				"[TableEdit7]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		saveAs_8table(foldername,
				ArrayListBound.getTableList8Data(),
				"[TableEdit8]", date);
		Constans.mouldData.writeFile(Constans.table�ı�����, "\r\n");// ��ͬ��֮���һ��
		

	}
	private static void saveAs_8table(String foldername,
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

		// д���ļ�ͷ
		Constans.mouldData.writeFile(Constans.table�ı�����, "[Time]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.table�ı�����, date + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.table�ı�����, "[Step]" + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.table�ı�����,
				"Step" + "=" + String.valueOf(step) + "/" + "\r\n");
		Constans.mouldData.writeFile(Constans.table�ı�����, markname + "/"+ "\r\n");
		
		//Constans.mouldData.writeFile(Constans.table�ı�����, "[Time]" + "/" + "\r\n"+date + "/" + "\r\n"+
				//"[Step]" + "/" + "\r\n"+"Step" + "=" + String.valueOf(step) + "/" + "\r\n"+markname + "/"+ "\r\n");
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
				Constans.mouldData.writeFile(Constans.table�ı�����, numString + "/"
						+ inputString + "/" + outputString + "/" + notesString
						+ "/" + "\r\n");
			}

		}

	}
	/**
	 * @param position 
	 * @param date 
	 * 
	 * @Title: saveMouldData 
	 * @Description: ����mouldList.trt�ļ����������ж�����ģ����Ϣ
	 * @param ArrayList    
	 * @return void    
	 * @throws
	 */
	public static void saveMouldDataList(ArrayList<HashMap<String, Object>> ArrayList, String date, int position){

		Constans.mouldData.createFolder(Constans.trPATH,Constans.mouldDataFolder).createFile("mouldList.trt");//mouldList.trt
		Constans.mouldData.cleanFile("mouldList.trt");

//		if (position>=0) {
//			ArrayList.get(position).put("note_mould_setting",date);
//		}

		// ѭ��д��ÿһ�д��뵽�ļ���
		for (int i = 0; i < ArrayList.size(); i++) {
			String numString = ArrayList.get(i).get("num")
					.toString().trim();
			String num_mould_settingString = ArrayList.get(i).get("num_mould_setting")
					.toString().trim();
			String name_mould_settingString = ArrayList.get(i).get("name_mould_setting")
					.toString().trim();
			String note_mould_settingString = ArrayList.get(i).get("note_mould_setting")
					.toString().trim();



			if (num_mould_settingString.length() == 0 && name_mould_settingString.length() == 0
					&& note_mould_settingString.length() == 0) {
				// do nothing
			} else {
				if (num_mould_settingString.length() == 0) {
					num_mould_settingString = " ";
				}
				if (name_mould_settingString.length() == 0) {
					name_mould_settingString = " ";
				}
				if (note_mould_settingString.length() == 0) {
					note_mould_settingString = " ";
				}
				Constans.mouldData.writeFile("mouldList.trt", numString
						+ "/" + num_mould_settingString + "/" + name_mould_settingString + "/"
						+ note_mould_settingString + "/" + "\r\n");
			}

		}


	}


	/**
	 * @param foldername 
	 * 
	 * @Title: clearAllData 
	 * @Description: ɾ�����ļ���  ��������mouldList.trt�ļ�  
	 * @return void    
	 * @throws
	 */
	private void clearAllData(String foldername) {
		
		File file=new File(Constans.mouldDataPATH+ foldername);
		if (file.exists()) {
			Constans.mouldData.deleteFolder(Constans.mouldDataPATH+ foldername);
		}
		saveMouldDataList(list_mould_setting,"",-1);
		Toast.makeText(getActivity(),"��"+foldername + "��ɾ���ɹ�",Toast.LENGTH_SHORT).show();
}
	/**
	 * @param foldername 
	 * @Title: openAllData 
	 * @Description: ��ȡ�����ļ�����ʾ��֮ǰ���뽨���ļ���ϵͳ    ��ͬʱ��ȡ�������ļ���
	 * @return void    
	 * @throws
	 */
	private void openAllData(final String foldername) {
		File file=new File(Constans.mouldDataPATH + foldername);
		if (file.exists()) {
		errorString = "";
		readerrorString = "";
		hasError = false;
		creatFolderSysytem();

		// ÿ�γ��������
		step_DeviceActualInput = 0;
		 step_DeviceActualOutput = 0;
		 step_DeviceMiddleInput = 0;
		 step_DeviceMiddleOutput = 0;
		 step_DeviceDataInput = 0;
		 step_DeviceDataOutput = 0;
		 step_DeviceRegister = 0;
		 step_DeviceDataRegister = 0;
		 step_DeviceTimer = 0;
		 step_DeviceCounter = 0;
		 step_DevicePosition = 0;
		 step_DeviceAlarm = 0;
		 step_DeviceOptional = 0;
		
		
		 step_NCEdit1 = 0;
		 step_NCEdit2 = 0;
		 step_NCEdit3 = 0;
		 step_NCEdit4 = 0;
		 step_NCEdit5 = 0;
		 step_NCEdit6 = 0;
		 step_NCEdit7 = 0;
		 step_NCEdit8 = 0;
		
		 step_TableEdit1 = 0;
		 step_TableEdit2 = 0;
		 step_TableEdit3 = 0;
		 step_TableEdit4 = 0;
		 step_TableEdit5 = 0;
		 step_TableEdit6 = 0;
		 step_TableEdit7 = 0;
		 step_TableEdit8 = 0;
		// ��ʼ��mark_list ����ֵΪstep��Ϊ0ʱ��index
		final ArrayList<HashMap<String, Integer>> mark_list = new ArrayList<HashMap<String, Integer>>();
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
		final String[] readlist_Device = Constans.mouldData
				.readOutFile(Constans.mouldDataPATH + foldername
						+ File.separator + Constans.�豸��������);// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist_Device.length; i++) {
			readlist_Device[i] = readlist_Device[i].replace("\n", "").trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
		}

		// ��ȡNCEdit������
		final String[] readlist_NCEdit = Constans.mouldData
				.readOutFile(Constans.mouldDataPATH + foldername
						+ File.separator + Constans.nc�ı�����);// ����֮�������Ҫ������·��
		for (int i = 0; i < readlist_NCEdit.length; i++) {
			readlist_NCEdit[i] = readlist_NCEdit[i].replace("\n", "").trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
		}

		// ��ȡTableEdit������
		final String[] readlist_TableEdit = Constans.mouldData
				.readOutFile(Constans.mouldDataPATH + foldername
						+ File.separator + Constans.table�ı�����);// ����֮�������Ҫ������·��
		
		for (int i = 0; i < readlist_TableEdit.length; i++) {
			readlist_TableEdit[i] = readlist_TableEdit[i].replace("\n", "")
					.trim();// ȥ�����ַ��еĻ��У������ַ�תintʱ�����
		}

		if (readlist_Device.length < 59) {
			// errorString = "�豸�����ļ���ͷ������";
			//Toast.makeText(getActivity(), "δ���룡�豸�����ļ���ͷ�������������ļ���", Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
			   .setMessage("δ���룡�豸�����ļ���ͷ�������������ļ���")
			   .setPositiveButton("֪����", null).show();
		}else if (readlist_NCEdit.length < 39) {
			// errorString = "�豸�����ļ���ͷ������";
			 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
			   .setMessage("δ���룡NC�ļ���ͷ�������������ļ���")
			   .setPositiveButton("֪����", null).show();
		}else if (readlist_TableEdit.length <39) {
			// errorString = "�豸�����ļ���ͷ������";
			 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
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
			.get("NCEdit").intValue()) / 5;*/
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
			System.out.println("X"+hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceActualOutput, readlist_Device, mark_list,
					"DeviceActualOutput", "Y");
			System.out.println("Y"+hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceMiddleInput, readlist_Device, mark_list,
					"DeviceMiddleInput", "MX");
			System.out.println("MX"+hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceMiddleOutput, readlist_Device, mark_list,
					"DeviceMiddleOutput", "MY");
			System.out.println("MY"+hasError);
			hasError = checkError_Device8before(hasError, step_DeviceDataInput,
					readlist_Device, mark_list, "DeviceDataInput", "DX");
			System.out.println("DX"+hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceDataOutput, readlist_Device, mark_list,
					"DeviceDataOutput", "DY");
			System.out.println("DY"+hasError);
			hasError = checkError_Device8before(hasError, step_DeviceRegister,
					readlist_Device, mark_list, "DeviceRegister", "LM");
			System.out.println("LM"+hasError);
			hasError = checkError_Device8before(hasError,
					step_DeviceDataRegister, readlist_Device, mark_list,
					"DeviceDataRegister", "LD");
			System.out.println("LD"+hasError);
			hasError = checkError_Device2after(hasError, step_DeviceTimer,
					readlist_Device, mark_list, "DeviceTimer", "T");
			System.out.println("T"+hasError);
			hasError = checkError_Device2after(hasError, step_DeviceCounter,
					readlist_Device, mark_list, "DeviceCounter", "C");
			System.out.println("C"+hasError);
			hasError = checkError_DevicePosition(hasError, step_DevicePosition,
					readlist_Device, mark_list);
			System.out.println(hasError);
			hasError = checkError_DeviceAlarm(hasError, step_DeviceAlarm,
					readlist_Device, mark_list);
			System.out.println(hasError);
			hasError = checkError_DeviceOptional(hasError, step_DeviceOptional,
					readlist_Device, mark_list);
			System.out.println(hasError);
			

			/*hasError = checkError_NCEdit(hasError, readlist_NCEdit,
			step_NCEdit, mark_list);
			System.out.println(hasError);*/
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

			//if (WifiSetting_Info.mClient!=null) {
			// û��������ʾ
			if (!hasError) {
				if (WifiSetting_Info.mClient==null) {
					new AlertDialog.Builder(getActivity())
				    .setTitle("��ʾ").setMessage("��ǰ�����ߣ��Ƿ񱾵ش򿪣�")
				    .setPositiveButton("ȷ��",new DialogInterface.OnClickListener() 
				    {
					 public void onClick(DialogInterface dialog, int which) {


							TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_num", list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim()).commit();
							TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_name", foldername).commit();
							TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", list_mould_setting.get(listselectposition).get("note_mould_setting").toString().trim()).commit();
							//TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_all", strAll).commit();
							WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
							WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(),"��ȡ������", "��ȴ�", true, false);
					        new Thread()
					          {
					              public void run()
					              { 
							
							long beforeTime00=System.currentTimeMillis();
						
							read_8before(ArrayListBound.getDeviceActualInputListData(),
									"DeviceActualInput", readlist_Device,
									step_DeviceActualInput, mark_list);
						    if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_8before(ArrayListBound.getDeviceActualOutputListData(),
									"DeviceActualOutput", readlist_Device,
									step_DeviceActualOutput, mark_list);	
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							
							long beforeTime1=System.currentTimeMillis();
					        System.out.println("ʵ�����롢ʵ�������Ϣ="+(beforeTime1-beforeTime00));
							read_8before(ArrayListBound.getDeviceMiddleInputListData(),
									"DeviceMiddleInput", readlist_Device,
									step_DeviceMiddleInput, mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_8before(ArrayListBound.getDeviceMiddleOutputListData(),
									"DeviceMiddleOutput", readlist_Device,
									step_DeviceMiddleOutput, mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_8before(ArrayListBound.getDeviceDataInputListData(),
									"DeviceDataInput", readlist_Device,
									step_DeviceDataInput, mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_8before(ArrayListBound.getDeviceDataOutputListData(),
									"DeviceDataOutput", readlist_Device,
									step_DeviceDataOutput, mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_8before(ArrayListBound.getDeviceRegisterListData(),
									"DeviceRegister", readlist_Device, step_DeviceRegister,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_8before(ArrayListBound.getDeviceDataRegisterListData(),
									"DeviceDataRegister", readlist_Device,
									step_DeviceDataRegister, mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							long beforeTime2=System.currentTimeMillis();
						
							read_4after(ArrayListBound.getDeviceTimerListData(),
									"DeviceTimer", readlist_Device, step_DeviceTimer,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
						
							long beforeTime3=System.currentTimeMillis();
					        System.out.println("��ʱ����Ϣ="+(beforeTime3-beforeTime2));
						
							read_4after(ArrayListBound.getDeviceCounterListData(),
									"DeviceCounter", readlist_Device, step_DeviceCounter,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							
							long beforeTime4=System.currentTimeMillis();
					        System.out.println("��������Ϣ="+(beforeTime4-beforeTime3));
						
							read_4after(ArrayListBound.getDevicePositionListData(),
									"DevicePosition", readlist_Device, step_DevicePosition,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							
							long beforeTime5=System.currentTimeMillis();
					        System.out.println("λ����Ϣ="+(beforeTime5-beforeTime4));
							
							read_4after(ArrayListBound.getDeviceAlarmListData(),
									"DeviceAlarm", readlist_Device, step_DeviceAlarm,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							
							long beforeTime6=System.currentTimeMillis();
					        System.out.println("��������="+(beforeTime6-beforeTime5));
						
							read_Optional(ArrayListBound.getDeviceOptionalListData(),
									"DeviceOptional", readlist_Device, step_DeviceOptional,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
						
							long beforeTime7=System.currentTimeMillis();
					        System.out.println("ѡ��������Ϣ="+(beforeTime7-beforeTime6));
							//ͨ���˺���ʹ��ArrayListBound.getNCeditListData()�����Ѿ����˵�ǰ�ļ����µ�NC������Ϣ
//							read_NCEdit(readlist_NCEdit, step_NCEdit);
							//ͨ���˺���ʹ��ArrayListBound.getNCeditListData()�����Ѿ����˵�ǰ�ļ����µ�NC������Ϣ
							//read_NCEdit(readlist_NCEdit, step_NCEdit);
							read_NCEdit(ArrayListBound.getNCeditList1Data(),
									"NCEdit1", readlist_NCEdit, step_NCEdit1,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList2Data(),
									"NCEdit2", readlist_NCEdit, step_NCEdit2,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList3Data(),
									"NCEdit3", readlist_NCEdit, step_NCEdit3,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList4Data(),
									"NCEdit4", readlist_NCEdit, step_NCEdit4,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList5Data(),
									"NCEdit5", readlist_NCEdit, step_NCEdit5,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList6Data(),
									"NCEdit6", readlist_NCEdit, step_NCEdit6,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList7Data(),
									"NCEdit7", readlist_NCEdit, step_NCEdit7,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_NCEdit(ArrayListBound.getNCeditList8Data(),
									"NCEdit8", readlist_NCEdit, step_NCEdit8,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }

							//ͨ���˺���ʹ��ArrayListBound.getTableListData()�����Ѿ����˵�ǰ�ļ����µ�table������Ϣ
							read_TableEdit(ArrayListBound.getTableList1Data(),
									"TableEdit1", readlist_TableEdit, step_TableEdit1,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList2Data(),
									"TableEdit2", readlist_TableEdit, step_TableEdit2,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList3Data(),
									"TableEdit3", readlist_TableEdit, step_TableEdit3,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList4Data(),
									"TableEdit4", readlist_TableEdit, step_TableEdit4,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList5Data(),
									"TableEdit5", readlist_TableEdit, step_TableEdit5,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList6Data(),
									"TableEdit6", readlist_TableEdit, step_TableEdit6,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList7Data(),
									"TableEdit7", readlist_TableEdit, step_TableEdit7,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							read_TableEdit(ArrayListBound.getTableList8Data(),
									"TableEdit8", readlist_TableEdit, step_TableEdit8,
									mark_list);
							if(!readerrorString.equals("")){
						    	overflag[4]=1;
								if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
								return;
						    }
							Constans.mouldData.deleteFolder(Constans.cachePATH);
							Constans.mouldData.copyDirectiory(Constans.mouldDataPATH + foldername + File.separator,Constans.cachePATH);//ͬʱ���Ƶ������ļ���
							
							if (WifiSetting_Info.mClient!=null) {}else{
								   if(WifiSetting_Info.progressDialog!=null){
										WifiSetting_Info.progressDialog.dismiss();
										WifiSetting_Info.progressDialog=null;
									}
							   }
					              }
					          }.start();
					          WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {              
						            
									@Override
									public void onDismiss(DialogInterface arg0) {
										// TODO Auto-generated method stub
										 if(WifiSetting_Info.mClient==null){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("��"+foldername+"�������߶�ȡ�ɹ�\n�����쳣��ͨѶ�жϣ��������磡")
											   .setPositiveButton("ȷ��", null).show();
							                	//Toast.makeText(getActivity(),"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
											}else if(overflag[0]==1){
												new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("��"+foldername+"����ȡ�ɹ�")
												   .setPositiveButton("ȷ��", null).show();
												overflag[0]=0;
											}else if(overflag[1]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("NC����Ϊ�գ�")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[1]=0;
											}else if(overflag[2]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("Table����Ϊ�գ�")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[2]=0;
											}else if(overflag[3]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("NC������ڴ���")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[3]=0;
											}else if(overflag[4]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage(readerrorString)
												   .setPositiveButton("ȷ��", null).show();
												 overflag[4]=0;
											}else if(overflag[5]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("�����쳣�����ز�����")
												   .setPositiveButton("ȷ��", null).show();
												 overflag[5]=0;
												//Toast.makeText(getActivity(),"�����쳣�����ز�����", Toast.LENGTH_SHORT).show();
											}else if(overflag[6]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("NC��"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸δѡ���趨")
												   .setPositiveButton("ȷ��", null).show();
												   NCTranslate.dname=null;
												   overflag[6]=0;
											}else if(overflag[7]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("NC��û���ҵ�"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸")
												   .setPositiveButton("ȷ��", null).show();
												   NCTranslate.dname=null;
												   overflag[7]=0;
											}else if(overflag[10]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("NC��"+NCTranslate.ncindex+"�ı��"+NCTranslate.dname+"���ڴ���")
												   .setPositiveButton("ȷ��", null).show();
												   NCTranslate.dname=null;
												   overflag[10]=0;
											}else if(overflag[11]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("NC��"+NCTranslate.ncindex+"������"+NCTranslate.dname+"���ڴ���")
												   .setPositiveButton("ȷ��", null).show();
												   NCTranslate.dname=null;
												   overflag[11]=0;
											}else if(overflag[8]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("Table��"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
												   .setPositiveButton("ȷ��", null).show();
												   TableTranslate.dname=null;
												   overflag[8]=0;
											}else if(overflag[9]==1){
												 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
												   .setMessage("Table��û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
												   .setPositiveButton("ȷ��", null).show();
												   TableTranslate.dname=null; 
												   overflag[9]=0;
											}
									}  
						        });
					        //��ʾ��ǰ������ģ������
								for(int i=0;i<list_mould_setting.size();i++){
									String bdstrNum=list_mould_setting.get(i).get("num_mould_setting").toString().trim();//ģ�߱��
									String bdstrName=list_mould_setting.get(i).get("name_mould_setting").toString().trim();//ģ������
									String bdstrTime=list_mould_setting.get(i).get("note_mould_setting").toString().trim();//ʱ��
	    						    if(bdstrNum.equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_num", "0"))
	    						    		&&bdstrName.equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", ""))
	    						    		&&bdstrTime.equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_time", ""))){
	    						    	listselectposition=i;
	    						    	adapter_mould_setting.setSelectItem(i);
	    								adapter_mould_setting.notifyDataSetChanged();
	    								text_num_mould.setText("     ��ǰģ�����:"+list_mould_setting.get(i).get("num").toString().trim());//ģ�߱��
	    								break;
	    						    }
	    						    if(i==list_mould_setting.size()-1){
	    						    	text_num_mould.setText("     ��λ����ǰ��ģ��������ƽ�嵱ǰ��ģ�����鲻һ�£������������ԣ��������������ģ�����飡");
	    						    }
	    						}
							Fragments_NCedit1.oldNcEditLists.clear();
							Fragments_NCedit1.newNcEditLists.clear();
							Fragments_NCedit2.oldNcEditLists.clear();
							Fragments_NCedit2.newNcEditLists.clear();
							Fragments_NCedit3.oldNcEditLists.clear();
							Fragments_NCedit3.newNcEditLists.clear();
							Fragments_NCedit4.oldNcEditLists.clear();
							Fragments_NCedit4.newNcEditLists.clear();
							Fragments_NCedit5.oldNcEditLists.clear();
							Fragments_NCedit5.newNcEditLists.clear();
							Fragments_NCedit6.oldNcEditLists.clear();
							Fragments_NCedit6.newNcEditLists.clear();
							Fragments_NCedit7.oldNcEditLists.clear();
							Fragments_NCedit7.newNcEditLists.clear();
							Fragments_NCedit8.oldNcEditLists.clear();
							Fragments_NCedit8.newNcEditLists.clear();
							
							Fragments_Table1.oldNcEditLists.clear();
							Fragments_Table1.newNcEditLists.clear();
							Fragments_Table2.oldNcEditLists.clear();
							Fragments_Table2.newNcEditLists.clear();
							Fragments_Table3.oldNcEditLists.clear();
							Fragments_Table3.newNcEditLists.clear();
							Fragments_Table4.oldNcEditLists.clear();
							Fragments_Table4.newNcEditLists.clear();
							Fragments_Table5.oldNcEditLists.clear();
							Fragments_Table5.newNcEditLists.clear();
							Fragments_Table6.oldNcEditLists.clear();
							Fragments_Table6.newNcEditLists.clear();
							Fragments_Table7.oldNcEditLists.clear();
							Fragments_Table7.newNcEditLists.clear();
							Fragments_Table8.oldNcEditLists.clear();
							Fragments_Table8.newNcEditLists.clear();
							
						
						
					 }
				    }).setNegativeButton("ȡ��",null).show();
				}else{
//					if(TR_Main_Activity.zd_led.getBackground().equals(TR_Main_Activity.drawablezdled)){
					if(Config.StopState){
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_num", list_mould_setting.get(listselectposition).get("num_mould_setting").toString().trim()).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_name", foldername).commit();
						TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_time", list_mould_setting.get(listselectposition).get("note_mould_setting").toString().trim()).commit();
						System.out.println("1���="+TR_Main_Activity.sharedPreference_openedDir.getString("dir_num","").trim());
						System.out.println("1����="+TR_Main_Activity.sharedPreference_openedDir.getString("dir_name","").trim());
						System.out.println("1�޸�ʱ��="+TR_Main_Activity.sharedPreference_openedDir.getString("dir_time","").trim());
						WifiSetting_Info.wifiTimeOut=System.currentTimeMillis();
						WifiSetting_Info.progressDialog = ProgressDialog.show(getActivity(),"��ȡ������", "��ȴ�", true, false);
				        Intent it = new Intent();
				        it.setAction("ThreadOption");
				        it.putExtra("Option", "StopThread");
				        getActivity().sendBroadcast(it);
				        Downindex = 0;
				          DownStatus = true;
				          startDownLoad = true;
				          new Thread(){
				        	  public void run(){				        	
				        		  while(startDownLoad&&Downindex<=12)
				        		  {				        	
				        		  if (WifiSetting_Info.mClient!=null) {
				        			try{				        				
				        				if((sendDataRunnables.size()>0)&&DownStatus)
				        				{		
				        					Log.e("mpeng","the  index is "+Downindex+"size is "+sendDataRunnables.size());
				        					DownStatus = false;
						        			modelReadDataRunnable=sendDataRunnables.get(0);				        							        		
						        		    modelReadDataRunnable.setOnlistener(new NormalChatListenner(modelReadDataRunnable,new FinishRunnable(getActivity(),backMessageTodo1)));
						        		    getActivity().runOnUiThread(modelReadDataRunnable);
				        				}
				        				} catch (Exception e) {
				        					// TODO: handle exception
				        					e.printStackTrace();
				        					if(WifiSetting_Info.progressDialog!=null){
				        						overflag[5]=1;
				        		    			WifiSetting_Info.progressDialog.dismiss();
				        		    			WifiSetting_Info.progressDialog=null;
				        		    		}
				        					return;
				        				}
				        			
				        			}else{
				        				if(WifiSetting_Info.progressDialog!=null){
				        					WifiSetting_Info.progressDialog.dismiss();
				        					WifiSetting_Info.progressDialog=null;
				        				}
				        			}
				        			 
				        	  }
				        	  }
				          }.start();
						new Thread()
				          {
				              public void run()
				              { 
						
						long beforeTime00=System.currentTimeMillis();
						ioallString="";
						read_8before(ArrayListBound.getDeviceActualInputListData(),
								"DeviceActualInput", readlist_Device,
								step_DeviceActualInput, mark_list);
					    if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_8before(ArrayListBound.getDeviceActualOutputListData(),
								"DeviceActualOutput", readlist_Device,
								step_DeviceActualOutput, mark_list);	
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E0000), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						long beforeTime1=System.currentTimeMillis();
				        System.out.println("ʵ�����롢ʵ�������Ϣ="+(beforeTime1-beforeTime00));
				        
				        ioallString="";
				        read_8before(ArrayListBound.getDeviceMiddleInputListData(),
								"DeviceMiddleInput", readlist_Device,
								step_DeviceMiddleInput, mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080EF000), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						
						read_8before(ArrayListBound.getDeviceMiddleInputListData(),
								"DeviceMiddleInput", readlist_Device,
								step_DeviceMiddleInput, mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_8before(ArrayListBound.getDeviceMiddleOutputListData(),
								"DeviceMiddleOutput", readlist_Device,
								step_DeviceMiddleOutput, mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_8before(ArrayListBound.getDeviceDataInputListData(),
								"DeviceDataInput", readlist_Device,
								step_DeviceDataInput, mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_8before(ArrayListBound.getDeviceDataOutputListData(),
								"DeviceDataOutput", readlist_Device,
								step_DeviceDataOutput, mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_8before(ArrayListBound.getDeviceRegisterListData(),
								"DeviceRegister", readlist_Device, step_DeviceRegister,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_8before(ArrayListBound.getDeviceDataRegisterListData(),
								"DeviceDataRegister", readlist_Device,
								step_DeviceDataRegister, mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						long beforeTime2=System.currentTimeMillis();
						ioallString="";
						read_4after(ArrayListBound.getDeviceTimerListData(),
								"DeviceTimer", readlist_Device, step_DeviceTimer,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E2000), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						long beforeTime3=System.currentTimeMillis();
				        System.out.println("��ʱ����Ϣ="+(beforeTime3-beforeTime2));
						ioallString="";
						read_4after(ArrayListBound.getDeviceCounterListData(),
								"DeviceCounter", readlist_Device, step_DeviceCounter,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E2800), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						long beforeTime4=System.currentTimeMillis();
				        System.out.println("��������Ϣ="+(beforeTime4-beforeTime3));
						ioallString="";
						read_4after(ArrayListBound.getDevicePositionListData(),
								"DevicePosition", readlist_Device, step_DevicePosition,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E3000), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						long beforeTime5=System.currentTimeMillis();
				        System.out.println("λ����Ϣ="+(beforeTime5-beforeTime4));
						ioallString="";
						read_4after(ArrayListBound.getDeviceAlarmListData(),
								"DeviceAlarm", readlist_Device, step_DeviceAlarm,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080EB000), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						long beforeTime6=System.currentTimeMillis();
				        System.out.println("��������="+(beforeTime6-beforeTime5));
						ioallString="";
						read_Optional(ArrayListBound.getDeviceOptionalListData(),
								"DeviceOptional", readlist_Device, step_DeviceOptional,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						try {
							iobyteArray=ioallString.getBytes("gb2312");
						    sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(iobyteArray,0x080E5000), getActivity());
						    sendDataRunnables.add(sendDataRunnable);
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							if(WifiSetting_Info.progressDialog!=null){
								overflag[5]=1;
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
						}
						long beforeTime7=System.currentTimeMillis();
				        System.out.println("ѡ��������Ϣ="+(beforeTime7-beforeTime6));
						//ͨ���˺���ʹ��ArrayListBound.getNCeditListData()�����Ѿ����˵�ǰ�ļ����µ�NC������Ϣ
//						read_NCEdit(readlist_NCEdit, step_NCEdit);
						//ͨ���˺���ʹ��ArrayListBound.getNCeditListData()�����Ѿ����˵�ǰ�ļ����µ�NC������Ϣ
						//read_NCEdit(readlist_NCEdit, step_NCEdit);
						read_NCEdit(ArrayListBound.getNCeditList1Data(),
								"NCEdit1", readlist_NCEdit, step_NCEdit1,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList2Data(),
								"NCEdit2", readlist_NCEdit, step_NCEdit2,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList3Data(),
								"NCEdit3", readlist_NCEdit, step_NCEdit3,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList4Data(),
								"NCEdit4", readlist_NCEdit, step_NCEdit4,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList5Data(),
								"NCEdit5", readlist_NCEdit, step_NCEdit5,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList6Data(),
								"NCEdit6", readlist_NCEdit, step_NCEdit6,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList7Data(),
								"NCEdit7", readlist_NCEdit, step_NCEdit7,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_NCEdit(ArrayListBound.getNCeditList8Data(),
								"NCEdit8", readlist_NCEdit, step_NCEdit8,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }

						//ͨ���˺���ʹ��ArrayListBound.getTableListData()�����Ѿ����˵�ǰ�ļ����µ�table������Ϣ
						read_TableEdit(ArrayListBound.getTableList1Data(),
								"TableEdit1", readlist_TableEdit, step_TableEdit1,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList2Data(),
								"TableEdit2", readlist_TableEdit, step_TableEdit2,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList3Data(),
								"TableEdit3", readlist_TableEdit, step_TableEdit3,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList4Data(),
								"TableEdit4", readlist_TableEdit, step_TableEdit4,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList5Data(),
								"TableEdit5", readlist_TableEdit, step_TableEdit5,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList6Data(),
								"TableEdit6", readlist_TableEdit, step_TableEdit6,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList7Data(),
								"TableEdit7", readlist_TableEdit, step_TableEdit7,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						read_TableEdit(ArrayListBound.getTableList8Data(),
								"TableEdit8", readlist_TableEdit, step_TableEdit8,
								mark_list);
						if(!readerrorString.equals("")){
					    	overflag[4]=1;
							if(WifiSetting_Info.progressDialog!=null){
								WifiSetting_Info.progressDialog.dismiss();
								WifiSetting_Info.progressDialog=null;
							}
							return;
					    }
						Constans.mouldData.deleteFolder(Constans.cachePATH);
						Constans.mouldData.copyDirectiory(Constans.mouldDataPATH + foldername + File.separator,Constans.cachePATH);//ͬʱ���Ƶ������ļ���
						
						if (WifiSetting_Info.mClient!=null) {//�ȴ���ʾ��
							long beforeTime08=System.currentTimeMillis();
								//ncģ�߷�����Ϣ�жϣ�����������
								try {
									int[] alength=NCTranslate.beginTranslate(getActivity(),getResources().getStringArray(R.array.IF2));// ��ʼ����
									 if(NCTranslate.dname!=null)
						 		      {
									   if(alength[0]==-1){
										   overflag[6]=1;
										   if(WifiSetting_Info.progressDialog!=null){
												WifiSetting_Info.progressDialog.dismiss();
												WifiSetting_Info.progressDialog=null;
											}
										 }else if(alength[0]==1){
										   overflag[7]=1;
										   if(WifiSetting_Info.progressDialog!=null){
												WifiSetting_Info.progressDialog.dismiss();
												WifiSetting_Info.progressDialog=null;
											}
										}else if(alength[0]==-2){
											   overflag[10]=1;
											   if(WifiSetting_Info.progressDialog!=null){
													WifiSetting_Info.progressDialog.dismiss();
													WifiSetting_Info.progressDialog=null;
												}
											}else if(alength[0]==-3){
												   overflag[11]=1;
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
									//Toast.makeText(getActivity(), "������ڴ���",Toast.LENGTH_SHORT).show();
									if(WifiSetting_Info.progressDialog!=null){
										WifiSetting_Info.progressDialog.dismiss();
										WifiSetting_Info.progressDialog=null;
									}
									return;
								}
								/*if (NCTranslate.getMesHexCode().length == 0
										||NCTranslate.getMesHexCode().equals(null)) {
									// ��������
									overflag[1]=1;
									//Toast.makeText(getActivity(), "Nc����Ϊ��",Toast.LENGTH_SHORT).show();
									if(WifiSetting_Info.progressDialog!=null){
										WifiSetting_Info.progressDialog.dismiss();
										WifiSetting_Info.progressDialog=null;
									}
									return;
								}*/
								//ϵͳtable��Ϣ�жϣ�����������
								int index1=TableTranslate.beginTranslate();
								if(TableTranslate.dname!=null)
					 		      {
									 if(index1==-1){
										 overflag[8]=1;
										 if(WifiSetting_Info.progressDialog!=null){
												WifiSetting_Info.progressDialog.dismiss();
												WifiSetting_Info.progressDialog=null;
											}
									 }else{
										 overflag[9]=1;
										 if(WifiSetting_Info.progressDialog!=null){
												WifiSetting_Info.progressDialog.dismiss();
												WifiSetting_Info.progressDialog=null;
											}
									 }
									 return;
					 		     }
								/*if (TableTranslate.getMesHexCode().length == 0
										|| TableTranslate.getMesHexCode().equals(null)) {
									overflag[2]=1;
									//Toast.makeText(getActivity(), "table����Ϊ��",Toast.LENGTH_SHORT).show();
									if(WifiSetting_Info.progressDialog!=null){
										WifiSetting_Info.progressDialog.dismiss();
										WifiSetting_Info.progressDialog=null;
									}
									return;
								}*/
								
								//nc���뷢�������ʼ��
								byte[] ncbye = NCTranslate.getMesHexCode();
								Constans.mouldData.writeFileFromByte("mouldaaa.trt", ncbye);
								SendDataRunnable ncDataSendRunnable =new SendDataRunnable(
										new WifiSendDataFormat(ncbye, 
												AddressPublic.NCProg_Head), getActivity());
								sendDataRunnables.add(ncDataSendRunnable);
								//table���뷢�������ʼ��
								SendDataRunnable	tableDataRunnable = new SendDataRunnable(
											new WifiSendDataFormat(
													TableTranslate.getMesHexCode(),
													AddressPublic.SysTable_Head),getActivity());
								sendDataRunnables.add(tableDataRunnable);
								
								SendDataRunnable axleDataSendRunnable =new SendDataRunnable(
										new WifiSendDataFormat(Config.pspfpaxle, 
												0x080E4F00), getActivity());
								sendDataRunnables.add(axleDataSendRunnable);
								
								long beforeTime8=System.currentTimeMillis();
								System.out.println("nc table����="+(beforeTime8-beforeTime08));
								//String msg=Constans.mouldData.readAlarmOutFile(Constans.trPATH+"������Ϣ"+File.separator+"servoalarm.trt");
								System.out.println("�ŷ�����="+(System.currentTimeMillis()-beforeTime8));
						        
								byte[] byteArray=null;
								try {
									Constans.alarmData.createFolder(Constans.trPATH,Constans.alarmDataFolder).createFile("servoalarmz.trt");
									byteArray=Constans.alarmData.readFileToByte("servoalarmz.trt");
									//byteArray = msg.getBytes("gb2312");
									
									//��������õ��ŷ�������Ϣ
									//Constans.alarmData.createFolder(Constans.trPATH,Constans.alarmDataFolder).createFile("servoalarmz.trt");
									//Constans.alarmData.cleanFile("servoalarmz.trt");
									//Constans.alarmData.writeFileFromByte("servoalarmz.trt", byteArray);
									
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
									return;
								}
								long beforeTime9=System.currentTimeMillis();
								//msg=Constans.mouldData.readAlarmOutFile(Constans.trPATH+"������Ϣ"+File.separator+"sysalarm.trt");
								System.out.println("ϵͳ����="+(System.currentTimeMillis()-beforeTime9));
								byteArray=null;
								try {
									//byteArray = msg.getBytes("gb2312");
									Constans.alarmData.createFolder(Constans.trPATH,Constans.alarmDataFolder).createFile("sysalarmz.trt");
									byteArray=Constans.alarmData.readFileToByte("sysalarmz.trt");
									//��������õ�ϵͳ������Ϣ
									//Constans.alarmData.createFolder(Constans.trPATH,Constans.alarmDataFolder).createFile("sysalarmz.trt");
									//Constans.alarmData.cleanFile("sysalarmz.trt");
									//Constans.alarmData.writeFileFromByte("sysalarmz.trt", byteArray);
									
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
									return;
								}
							/*	msg=Constans.mouldData.readAlarmOutFile(Constans.trPATH+"������Ϣ"+File.separator+"zsfb.trt");
								byteArray=null;
								try {
									byteArray = msg.getBytes("gb2312");
									sendDataRunnable=new SendDataRunnable(new WifiSendDataFormat(byteArray, 0x080EE000),getActivity());
									sendDataRunnables.add(sendDataRunnable);
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									if(WifiSetting_Info.progressDialog!=null){
										WifiSetting_Info.progressDialog.dismiss();
										WifiSetting_Info.progressDialog=null;
									}
									e.printStackTrace();
									return;
								}*/


								byteArray=readMouldData2(foldername);//�ṩ����Ľӿ�
								System.out.println("ƽ��������ֽ������С");
								System.out.println(byteArray.length);
								//��ȡ������byteģ�����ݸ���λ��
								//��ʼ���������ݵ�ʵ��//ģ�����ݴ��뷢�������ʼ��
								formatSendMessage=new WifiSendDataFormat(byteArray, AddressPublic.userModelData_Head);
								modelReadDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
								sendDataRunnables.add(modelReadDataRunnable);
								 long nowTime=System.currentTimeMillis();
								System.out.println("123nowTime-WifiSetting_Info.wifiTimeOut="+(nowTime-WifiSetting_Info.wifiTimeOut));
//								fromsd();
							
								copyFile(Constans.mouldDataPATH+foldername,"ncdata");//		
			        		  	copyFile(Constans.mouldDataPATH+foldername,"tabledata");//				        		  	
								getFileFromBytes(NCTranslate.getMesHexCode(), Constans.mouldDataPATH+foldername+"/"+"ncdata.trt");
								getFileFromBytes(TableTranslate.getMesHexCode(), Constans.mouldDataPATH+foldername+"/"+"tabledata.trt");
								
						   }else{
							   if(WifiSetting_Info.progressDialog!=null){
									WifiSetting_Info.progressDialog.dismiss();
									WifiSetting_Info.progressDialog=null;
								}
						   }
				              }
				          }.start();
				          WifiSetting_Info.progressDialog.setOnDismissListener(new OnDismissListener() {              
					            
								@Override
								public void onDismiss(DialogInterface arg0) {
									// TODO Auto-generated method stub
									  Downindex = 0;
							          DownStatus = false;
							          startDownLoad = false;
							          if(sendDataRunnables.size()>0)
							        	  sendDataRunnables.clear();		
							           long CompleteTime=System.currentTimeMillis();
							            Intent it = new Intent();
								        it.setAction("ThreadOption");
								        it.putExtra("Option", "StartThread");
								        getActivity().sendBroadcast(it);
									 if(WifiSetting_Info.mClient==null){
										 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
										   .setMessage("�����߶�ȡ�ɹ�\n�����쳣��ͨѶ�жϣ��������磡")
										   .setPositiveButton("ȷ��", null).show();
						                	//Toast.makeText(getActivity(),"�����쳣�������жϣ��������磡", Toast.LENGTH_SHORT).show();
										}else if(overflag[0]==1){
											new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("���ݷ������   ���ع���ʱ :"+(CompleteTime - WifiSetting_Info.wifiTimeOut)+"ms")
											   .setPositiveButton("ȷ��", null).show();
											overflag[0]=0;
											formatReadusermode = new WifiReadDataFormat(0x200000AF,1);
											try {
												sendDatausermodeRunnable=new SendDataRunnable(formatReadusermode,getActivity());
												sendDataFinishRunnable=new FinishRunnable(getActivity(),stopDataFinishTodo2);
												sendDatausermodeRunnable.setOnlistener(new NormalChatListenner(sendDatausermodeRunnable, sendDataFinishRunnable));
												getActivity().runOnUiThread(sendDatausermodeRunnable);
											} catch (Exception e) {
												// TODO: handle exception
											}
										}else if(overflag[1]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("NC����Ϊ�գ�")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[1]=0;
										}else if(overflag[2]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("Table����Ϊ�գ�")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[2]=0;
										}else if(overflag[3]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("NC������ڴ���")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[3]=0;
										}else if(overflag[4]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage(readerrorString)
											   .setPositiveButton("ȷ��", null).show();
											 overflag[4]=0;
										}else if(overflag[5]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("�����쳣�����ز�����")
											   .setPositiveButton("ȷ��", null).show();
											 overflag[5]=0;
											//Toast.makeText(getActivity(),"�����쳣�����ز�����", Toast.LENGTH_SHORT).show();
										}else if(overflag[6]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("NC��"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸δѡ���趨")
											   .setPositiveButton("ȷ��", null).show();
											   NCTranslate.dname=null;
											   overflag[6]=0;
										}else if(overflag[7]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("NC��û���ҵ�"+NCTranslate.ncindex+"��"+NCTranslate.dname+"�豸")
											   .setPositiveButton("ȷ��", null).show();
											   NCTranslate.dname=null;
											   overflag[7]=0;
										}else if(overflag[10]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("NC��"+NCTranslate.ncindex+"�ı��"+NCTranslate.dname+"���ڴ���")
											   .setPositiveButton("ȷ��", null).show();
											   NCTranslate.dname=null;
											   overflag[10]=0;
										}else if(overflag[11]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("NC��"+NCTranslate.ncindex+"������"+NCTranslate.dname+"���ڴ���")
											   .setPositiveButton("ȷ��", null).show();
											   NCTranslate.dname=null;
											   overflag[11]=0;
										}else if(overflag[8]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("Table��"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸δѡ���趨")
											   .setPositiveButton("ȷ��", null).show();
											   TableTranslate.dname=null;
											   overflag[8]=0;
										}else if(overflag[9]==1){
											 new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
											   .setMessage("Table��û���ҵ�"+TableTranslate.tableindex+"��"+TableTranslate.dname+"�豸")
											   .setPositiveButton("ȷ��", null).show();
											   TableTranslate.dname=null; 
											   overflag[9]=0;
										}
								}  
					        });
				        //��ʾ��ǰ������ģ������
							for(int i=0;i<list_mould_setting.size();i++){
    							String bdstrNum=list_mould_setting.get(i).get("num_mould_setting").toString().trim();//ģ�߱��
    							String bdstrName=list_mould_setting.get(i).get("name_mould_setting").toString().trim();//ģ������
    							String bdstrTime=list_mould_setting.get(i).get("note_mould_setting").toString().trim();//ʱ��
    						    if(bdstrNum.equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_num", "0"))
    						    		&&bdstrName.equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", ""))
    						    		&&bdstrTime.equals(TR_Main_Activity.sharedPreference_openedDir.getString("dir_time", ""))){
    						    	listselectposition=i;
    						    	adapter_mould_setting.setSelectItem(i);
    								adapter_mould_setting.notifyDataSetChanged();
    								text_num_mould.setText("     ��ǰģ�����:"+list_mould_setting.get(i).get("num").toString().trim());//ģ�߱��
    								break;
    						    }
    						    if(i==list_mould_setting.size()-1){
    						    	text_num_mould.setText("     ��λ����ǰ��ģ��������ƽ�嵱ǰ��ģ�����鲻һ�£������������ԣ��������������ģ�����飡");
    						    }
    						}
						Fragments_NCedit1.oldNcEditLists.clear();
						Fragments_NCedit1.newNcEditLists.clear();
						Fragments_NCedit2.oldNcEditLists.clear();
						Fragments_NCedit2.newNcEditLists.clear();
						Fragments_NCedit3.oldNcEditLists.clear();
						Fragments_NCedit3.newNcEditLists.clear();
						Fragments_NCedit4.oldNcEditLists.clear();
						Fragments_NCedit4.newNcEditLists.clear();
						Fragments_NCedit5.oldNcEditLists.clear();
						Fragments_NCedit5.newNcEditLists.clear();
						Fragments_NCedit6.oldNcEditLists.clear();
						Fragments_NCedit6.newNcEditLists.clear();
						Fragments_NCedit7.oldNcEditLists.clear();
						Fragments_NCedit7.newNcEditLists.clear();
						Fragments_NCedit8.oldNcEditLists.clear();
						Fragments_NCedit8.newNcEditLists.clear();
						
						Fragments_Table1.oldNcEditLists.clear();
						Fragments_Table1.newNcEditLists.clear();
						Fragments_Table2.oldNcEditLists.clear();
						Fragments_Table2.newNcEditLists.clear();
						Fragments_Table3.oldNcEditLists.clear();
						Fragments_Table3.newNcEditLists.clear();
						Fragments_Table4.oldNcEditLists.clear();
						Fragments_Table4.newNcEditLists.clear();
						Fragments_Table5.oldNcEditLists.clear();
						Fragments_Table5.newNcEditLists.clear();
						Fragments_Table6.oldNcEditLists.clear();
						Fragments_Table6.newNcEditLists.clear();
						Fragments_Table7.oldNcEditLists.clear();
						Fragments_Table7.newNcEditLists.clear();
						Fragments_Table8.oldNcEditLists.clear();
						Fragments_Table8.newNcEditLists.clear();
				    }else{
						new AlertDialog.Builder(getActivity())
						.setTitle("����")
						.setMessage("�����ȡģ�����ݣ����л����ֶ�״̬��")
						.setPositiveButton("֪����",null)
						.show();
				    }
				}
				} else {
				new AlertDialog.Builder(getActivity()).setTitle("��ʾ")
				.setMessage(errorString)
				.setPositiveButton("֪����", null).show();
				//Toast.makeText(getActivity(), "�ļ����ڴ���" + errorString, Toast.LENGTH_SHORT).show();
				System.out.println("�ļ����ڴ���" + errorString);
			         }
				
				
		}
		}else {
			Toast.makeText(getActivity(), "�����ڸ��ļ��л���ļ�������Ϊ��" , Toast.LENGTH_SHORT).show();
			System.out.println("�����ڸ��ļ��л���ļ�������Ϊ��");
		}

	}

	private  Runnable stopDataFinishTodo2 = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//���ڷ��ص�36�ֽ�
			//������ȷ�����
			//�����ص�����	
			getData=new byte[formatReadusermode.getLength()];
			//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
			if( formatReadusermode.getFinalData() != null)  
			{
			   System.arraycopy(formatReadusermode.getFinalData(), 0, getData, 0, formatReadusermode.getLength());
			   int zjz=(int)(getData[0]&0x0F);
			   getData=HexDecoding.int2byte((int)(zjz|0x10));
			   try{
			    formatSendMessage=new WifiSendDataFormat(getData, 0x200000AF);
                sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
				sendDataFinishRunnable=new FinishRunnable(getActivity());
				sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
				getActivity().runOnUiThread(sendDataRunnable);
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
		    }
		}
	};
	private void fromsd(){
		if (WifiSetting_Info.mClient!=null) {
			try{
				if(DownStatus&&(sendDataRunnables.size()>Downindex))
				{
					modelReadDataRunnable=sendDataRunnables.get(Downindex);		
					Log.e("mpeng"," the index :"+Downindex);
					Downindex ++;
					DownStatus = false;
				    modelReadDataRunnable.setOnlistener(new NormalChatListenner(modelReadDataRunnable,new FinishRunnable(getActivity(),backMessageTodo1)));
					getActivity().runOnUiThread(modelReadDataRunnable);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				if(WifiSetting_Info.progressDialog!=null){
					overflag[5]=1;
	    			WifiSetting_Info.progressDialog.dismiss();
	    			WifiSetting_Info.progressDialog=null;
	    		}
				return;
			}
		
		}else{
			if(WifiSetting_Info.progressDialog!=null){
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
//			sendDataRunnables.remove(0);
//			 if(sendDataRunnables.size()>0){
//				 fromsd(); 
//			 }
				sendDataRunnables.remove(0);					
				Log.e("mpeng","download sendDataRunnables.size() !!!" +sendDataRunnables.size());	
				 if(WifiSetting_Info.progressDialog!=null&&sendDataRunnables.size()==0&&Downindex>=12){
					 Log.e("mpeng","download over!!!");	
					    Downindex = 0;
					 	DownStatus = false;
					 	startDownLoad = false;
					 	sendDataRunnables.clear();
					 	overflag[0]=1;
						 WifiSetting_Info.progressDialog.dismiss();
						 WifiSetting_Info.progressDialog=null;
				 }
				 else
				 {	 
					 Downindex++;				 
					 DownStatus = true;	
				 }
			 if(WifiSetting_Info.progressDialog!=null&&sendDataRunnables.size()==0&&Downindex>=12){
				 overflag[0]=1;
				 WifiSetting_Info.progressDialog.dismiss();
				 WifiSetting_Info.progressDialog=null;
				 
			 }
		}
	};

	/**
	 * @param foldername 
	 * 
	 * @Title: readMouldData2 
	 * @Description: ��mould���ݶ�ȡΪһ��byte����
	 * @return    
	 * @return byte[]    
	 * @throws
	 */
	private byte[] readMouldData2(String foldername) {
		Constans.mouldData.createFolder(Constans.mouldDataPATH, foldername).createFile("mould.trt");
		byte[] byteArray2=Constans.mouldData.readFileToByte("mould.trt");
		if(byteArray2.length<76){
			byteArray2=new byte[76];
		}
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
        byte[] temp=new byte[4*8];
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 0, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 4, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 8, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 12, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 16, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 20, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 24, 4);
        System.arraycopy(HexDecoding.int2byteArray4(0x7FFFFFFF), 0, temp, 28, 4);
        byte[] temfp=new byte[3200];
        for(int i=0;i<100;i++){
        	 System.arraycopy(temp, 0, temfp, i*32, 32);
        }
        byte[] ptempspeed=new byte[3];
        System.arraycopy(HexDecoding.int2byte(10), 0, ptempspeed, 0, 1);
        System.arraycopy(HexDecoding.int2byte(1), 0, ptempspeed, 1, 1);
        System.arraycopy(HexDecoding.int2byte(1), 0, ptempspeed, 2, 1);
        byte[] sptempspeed=new byte[19];
        System.arraycopy(HexDecoding.int2byteArray2(1), 0, sptempspeed, 0, 2);										
		System.arraycopy(HexDecoding.int2byteArray2(2), 0, sptempspeed, 2, 2);
		System.arraycopy(HexDecoding.int2byteArray2(3), 0, sptempspeed, 6, 2);
		System.arraycopy(HexDecoding.int2byte(10), 0, sptempspeed, 16, 1);
		System.arraycopy(HexDecoding.int2byte(1), 0, sptempspeed, 17, 1);
		System.arraycopy(HexDecoding.int2byte(1), 0, sptempspeed, 18, 1);
		byte[] fptempspeed=new byte[5];
		System.arraycopy(HexDecoding.int2byteArray2(3), 0, fptempspeed, 0, 2);	
		System.arraycopy(HexDecoding.int2byte(10), 0, fptempspeed, 2, 1);
		System.arraycopy(HexDecoding.int2byte(1), 0, fptempspeed, 3, 1);
		System.arraycopy(HexDecoding.int2byte(1), 0, fptempspeed, 4, 1);
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
		System.arraycopy(getData,0, byteArray2, 0x0000001C, 48);
		if(byteArray2.length>=0x00003AEC){
			for(int i=0;i<200;i++){
				if(((getData[(64+64+i)/8]>>((64+64+i)%8))&0x01)==0){
					System.arraycopy(temp,0, byteArray2, 0x000002F0+36*i, 32);
					System.arraycopy(ptempspeed,0, byteArray2, 0x000002F0+36*i+32, 3);
				}else{
					System.arraycopy(HexDecoding.int2byte(1), 0, byteArray2, 0x000002F0+36*i+35, 1);
				}
			}
			for(int i=0;i<8;i++){
				if(((getData[(64+64+212+i)/8]>>((64+64+212+i)%8))&0x01)==0){
					System.arraycopy(temp,0, byteArray2, 0x00001F10+84*i, 32);
					System.arraycopy(sptempspeed,0, byteArray2, 0x00001F10+84*i+64, 19);
				}else{
					System.arraycopy(HexDecoding.int2byte(1), 0, byteArray2, 0x00001F10+84*i+83, 1);
				}
			}
			for(int i=0;i<2;i++){
				if(((getData[(64+64+222+i)/8]>>((64+64+222+i)%8))&0x01)==0){
					System.arraycopy(temfp,0, byteArray2, 0x000021B0+3200*i, 3200);
					System.arraycopy(fptempspeed,0, byteArray2, 0x00003AB0+22*i+16, 5);
				}else{
					System.arraycopy(HexDecoding.int2byte(1), 0, byteArray2, 0x00003AB0+22*i+21, 1);
				}
			}
		}
	    //��ǰģ������
		byte[] getData1=new byte[28];
		String strNum = TR_Main_Activity.sharedPreference_openedDir.getString("dir_num","0");
		String strName = TR_Main_Activity.sharedPreference_openedDir.getString("dir_name", "");
		String strTime = TR_Main_Activity.sharedPreference_openedDir.getString("dir_time", "");
		try {
			if(strName.getBytes("gb2312").length==0){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==1){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==2){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==3){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==4){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==5){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==6){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==7){strName=strName+"\0\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==8){strName=strName+"\0\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==9){strName=strName+"\0\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==10){strName=strName+"\0\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==11){strName=strName+"\0\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==12){strName=strName+"\0\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==13){strName=strName+"\0\0\0\0\0";}
			if(strName.getBytes("gb2312").length==14){strName=strName+"\0\0\0\0";}
			if(strName.getBytes("gb2312").length==15){strName=strName+"\0\0\0";}
			if(strName.getBytes("gb2312").length==16){strName=strName+"\0\0";}
			if(strName.getBytes("gb2312").length==17){strName=strName+"\0";}
			System.arraycopy(HexDecoding.int2byteArray2(Integer.parseInt(strNum)),0, getData1, 0,2);
			System.arraycopy(strName.getBytes("gb2312"),0, getData1, 2,18);
			String[] timeStrings =strTime.split("\\s+|:|-");
			for(int i=0;i<timeStrings.length;i++){
				System.out.println("timeStrings====="+timeStrings );
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
		System.arraycopy(getData1,0, byteArray2, 0x00000000, 28);
		return byteArray2;
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
		try{
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
				String stringToCheck=readlist[4 * i + mark_list.get(0).get(markName) + 2].toString().trim();
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
					} else if (((readlist[4 * i + mark_list.get(0).get(markName)
					                     + 3].getBytes("gb2312")).length > 24)
					                     || ((readlist[4 * i + mark_list.get(0).get(markName)
									                     + 4].getBytes("gb2312")).length >80)) {
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
						} else if (((readlist[4 * i + mark_list.get(0).get(markName)
						                     + 3].getBytes("gb2312")).length > 40)
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
				String stringToCheck=readlist[5 * i + mark_list.get(0).get(markName) + 2].toString().trim();
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
					} else if ((readlist[5 * i + mark_list.get(0).get(markName)
					                    + 3].getBytes("gb2312")).length > 24||(readlist[5 * i + mark_list.get(0).get(markName)
					                            					                    + 4].getBytes("gb2312")).length > 80) {
						try{ 
						errorString +="�豸����"+markName+"�����Ϊ"+Integer.valueOf(readlist[5 * i+ mark_list.get(0).get(markName) + 1])+"��һ�д����﷨�����ź�����ע�ͳ��ȴ���";
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
				if (!(stringToCheck.startsWith("SP")|| stringToCheck.startsWith("FP") 
						|| stringToCheck.startsWith("P"))|| ((stringToCheck.getBytes("gb2312")).length > 8)) {
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
				if (!(stringToCheck.matches("[0-9]+"))// ������ʽ���ж��Ƿ�ȫ��������
						|| (stringToCheck.getBytes("gb2312")).length > 8) {
					try{
					errorString +="�豸���徯�������Ϊ"+Integer.valueOf(readlist[3 * i+ mark_list.get(0).get("DeviceAlarm") + 1])+"��һ�д����﷨���󣬷���������";
					}catch(Exception e) {
						// TODO: handle exception
						errorString += "�豸���徯���е�"+i+"�и������ڴ���";
					}
					hasError = true;
					break;// �д��������ѭ��
				} else if ((readlist[3 * i + mark_list.get(0).get("DeviceAlarm")+ 3].getBytes("gb2312")).length> 60) {
					try{
					errorString +="�豸���徯�������Ϊ"+Integer.valueOf(readlist[3 * i+ mark_list.get(0).get("DeviceAlarm") + 1])+"��һ�д����﷨����ע�ͳ��ȴ���";
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
			ArrayList<HashMap<String, Integer>> mark_list) {
		
		int j = ArrayList.size();
		// ����������
		for (int i = 0; i < j; i++) {
			ArrayList.get(i).put("symbolNameEditText", "");
			ArrayList.get(i).put("signalNameEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}

		if (step == 0) {
			// Toast.makeText(getActivity(), markname+"����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			readerrorString="�豸����"+markname+"�ĺ��漸����ڴ��������ļ���";
		}  else {
			// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
			//int k = step - j;
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
				if(markname.equals("DeviceMiddleInput")){
					if(i>27){
						continue;
					}
				}
				if(markname.equals("DeviceActualInput")||markname.equals("DeviceActualOutput")||markname.equals("DeviceMiddleInput")){
					
					String symbolString=readlist[4 * i + mark_list.get(0).get(markname) + 2];
					String signalString=readlist[4 * i + mark_list.get(0).get(markname) + 3];
					byte[] symbolbyte=null;
					byte[] signalbyte=null;
					try {
						symbolbyte=symbolString.getBytes("gb2312");
						signalbyte=signalString.getBytes("gb2312");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					if(symbolbyte.length==0){symbolString=symbolString+"\0\0\0\0\0\0\0\0";}
					else if(symbolbyte.length==1){symbolString=symbolString+"\0\0\0\0\0\0\0";}
					else if(symbolbyte.length==2){symbolString=symbolString+"\0\0\0\0\0\0";}
					else if(symbolbyte.length==3){symbolString=symbolString+"\0\0\0\0\0";}
					else if(symbolbyte.length==4){symbolString=symbolString+"\0\0\0\0";}
					else if(symbolbyte.length==5){symbolString=symbolString+"\0\0\0";}
					else if(symbolbyte.length==6){symbolString=symbolString+"\0\0";}
					else if(symbolbyte.length==7){symbolString=symbolString+"\0";}
					
					if(signalbyte.length==0){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==1){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==2){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==3){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==4){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==5){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==6){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==7){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==8){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==9){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==10){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==11){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==12){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==13){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==14){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==15){signalString=signalString+"\0\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==16){signalString=signalString+"\0\0\0\0\0\0\0\0";}
					else if(signalbyte.length==17){signalString=signalString+"\0\0\0\0\0\0\0";}
					else if(signalbyte.length==18){signalString=signalString+"\0\0\0\0\0\0";}
					else if(signalbyte.length==19){signalString=signalString+"\0\0\0\0\0";}
					else if(signalbyte.length==20){signalString=signalString+"\0\0\0\0";}
					else if(signalbyte.length==21){signalString=signalString+"\0\0\0";}
					else if(signalbyte.length==22){signalString=signalString+"\0\0";}
					else if(signalbyte.length==23){signalString=signalString+"\0";}
					ioallString=ioallString+symbolString+signalString;
			}
			
				} catch (Exception e) {
					// TODO: handle exception
					readerrorString="�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���";
					 break;
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
			ArrayList<HashMap<String, Integer>> mark_list) {
		
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
			//Toast.makeText(getActivity(), markname + "����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			readerrorString="�豸����"+markname+"�ĺ��漸����ڴ��������ļ���";
		}  else {
			// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
			int k = step - j;
			// ���Ӳ�������
			if (k > 0&&markname.equals("DeviceAlarm")) {
				for (int i = 0; i < k + 1; i++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%03d", j + i+1));// //============λ�ñ�������
					mapadd.put("symbolNameEditText", "");
					mapadd.put("noteEditText", "");
					ArrayList.add(j + i , mapadd);

				}
			}
			if (k > 0&&markname.equals("DevicePosition")) {
				for (int i = 0; i < k + 1; i++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%04d", j + i+1));// //============λ�ñ�������
					mapadd.put("symbolNameEditText", "");
					mapadd.put("signalNameEditText", "");
					mapadd.put("noteEditText", "");
					mapadd.put("setItem", "0");
					ArrayList.add(j + i , mapadd);
				}
			}
			if (k > 0&&(markname.equals("DeviceTimer")||markname.equals("DeviceCounter"))) {
				for (int i = 0; i < k + 1; i++) {
					HashMap<String, Object> mapadd = new HashMap<String, Object>();
					mapadd.put("addressText", String.format("%04d", j + i));// //============λ�ñ�������
					mapadd.put("symbolNameEditText", "");
					mapadd.put("signalNameEditText", "");
					mapadd.put("noteEditText", "");
					mapadd.put("setItem", "0");
					ArrayList.add(j + i , mapadd);
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
				String[] alarmlist=new String[200];
			  for (int i = 0; i < step; i++) {
				  try{
			    int lineToAdd = Integer.parseInt(readlist[3 * i+ mark_list.get(0).get(markname) + 1]);// �õ�ʵ��λ�õ�ֵַ
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
				ioallString="";
				String symbolString =readlist[3 * i + mark_list.get(0).get(markname) + 2].toString().trim();
				String noteString =readlist[3 * i + mark_list.get(0).get(markname) + 3].toString().trim();
				if(!symbolString.equals("")){
					int symbolint=Integer.parseInt(symbolString);
					symbolString="��������"+symbolint;
				byte[] symbolbyte=null;
				byte[] notebyte=null;
				try {
					symbolbyte=symbolString.getBytes("gb2312");
					notebyte=noteString.getBytes("gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(symbolbyte.length==9){symbolString=symbolString+"\0\0\0\0\0\0\0\0\0\0\0";}
				if(symbolbyte.length==10){symbolString=symbolString+"\0\0\0\0\0\0\0\0\0\0";}
				if(symbolbyte.length==11){symbolString=symbolString+"\0\0\0\0\0\0\0\0\0";}
				int allbyte=0;
				noteString=noteString.replace("\0", "");
				String notebf=noteString;
				int note1flag=0;
				for(int n=0;n<noteString.length()-1;n++){
					char ch=noteString.charAt(n);
					String chstr=ch+"";
					byte[] chbyte=null;
					try {
						chbyte=chstr.getBytes("gb2312");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					allbyte=allbyte+chbyte.length;
					if(allbyte==19||allbyte==39||allbyte==59){
						try{
						  notebf=notebf.substring(0, n+1+note1flag)+"\0"+notebf.substring(n+1+note1flag, notebf.length());
					    } catch (Exception e) {
						  e.printStackTrace();
					    }
					allbyte=0;
						note1flag++;
					}
				}
				noteString=notebf;
				try {
					notebyte=noteString.getBytes("gb2312");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int j1=0;j1<=60;j1++){
				   if(notebyte.length==j1){
					for(int k1=1;k1<=60-j1;k1++){
					  noteString=noteString+"\0";
					}
				   }
				}
	            
				ioallString=ioallString+symbolString+noteString;
				alarmlist[symbolint-1]=ioallString;
				}
				  } catch (Exception e) {
						// TODO: handle exception
					  readerrorString="�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���";
						 break;
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
			}else if (markname.equals("DevicePosition")) {
				String[] ioallStrings=new String[210];
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
					if(readlist[5 * i + mark_list.get(0).get(markname) + 5].equals("1")){
					String symbolString=readlist[5 * i + mark_list.get(0).get(markname) + 2];
					String signalString=readlist[5 * i + mark_list.get(0).get(markname) + 3];
					byte[] symbolbyte=null;
					byte[] signalbyte=null;
					try {
						symbolbyte=symbolString.getBytes("gb2312");
						signalbyte=signalString.getBytes("gb2312");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					if(symbolbyte.length==0){symbolString=symbolString+"\0\0\0\0\0\0\0\0";}
					if(symbolbyte.length==1){symbolString=symbolString+"\0\0\0\0\0\0\0";}
					if(symbolbyte.length==2){symbolString=symbolString+"\0\0\0\0\0\0";}
					if(symbolbyte.length==3){symbolString=symbolString+"\0\0\0\0\0";}
					if(symbolbyte.length==4){symbolString=symbolString+"\0\0\0\0";}
					if(symbolbyte.length==5){symbolString=symbolString+"\0\0\0";}
					if(symbolbyte.length==6){symbolString=symbolString+"\0\0";}
					if(symbolbyte.length==7){symbolString=symbolString+"\0";}
					
					if(signalbyte.length==0){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==1){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==2){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==3){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==4){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==5){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==6){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==7){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==8){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==9){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==10){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==11){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==12){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==13){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==14){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==15){signalString=signalString+"\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==16){signalString=signalString+"\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==17){signalString=signalString+"\0\0\0\0\0\0\0";}
					if(signalbyte.length==18){signalString=signalString+"\0\0\0\0\0\0";}
					if(signalbyte.length==19){signalString=signalString+"\0\0\0\0\0";}
					if(signalbyte.length==20){signalString=signalString+"\0\0\0\0";}
					if(signalbyte.length==21){signalString=signalString+"\0\0\0";}
					if(signalbyte.length==22){signalString=signalString+"\0\0";}
					if(signalbyte.length==23){signalString=signalString+"\0";}
					if(symbolString.startsWith("S")||symbolString.startsWith("s")){
						ioallStrings[Integer.parseInt(symbolString.substring(2,5))-1+200]=symbolString+signalString;
					}else if(symbolString.startsWith("F")||symbolString.startsWith("f")){
						ioallStrings[Integer.parseInt(symbolString.substring(2,5))-1+208]=symbolString+signalString;
					}else if(symbolString.startsWith("P")||symbolString.startsWith("p")){
						ioallStrings[Integer.parseInt(symbolString.substring(1,4))-1]=symbolString+signalString;
					}
					}
					} catch (Exception e) {
						// TODO: handle exception
						readerrorString="�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���";
						 break;
					}
				}
				for(int i=0;i<ioallStrings.length;i++){
					if(ioallStrings[i]!=null){
					   ioallString=ioallString+ioallStrings[i];
					}
				}
			}else{
				for (int i = 0; i < step; i++) {
					try{
					int lineToAdd = Integer.parseInt(readlist[5 * i+ mark_list.get(0).get(markname) + 1]);// �õ�ʵ��λ�õ�ֵַ
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
					if(readlist[5 * i + mark_list.get(0).get(markname) + 5].equals("1")){
					String symbolString=readlist[5 * i + mark_list.get(0).get(markname) + 2];
					String signalString=readlist[5 * i + mark_list.get(0).get(markname) + 3];
					byte[] symbolbyte=null;
					byte[] signalbyte=null;
					try {
						symbolbyte=symbolString.getBytes("gb2312");
						signalbyte=signalString.getBytes("gb2312");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					if(symbolbyte.length==0){symbolString=symbolString+"\0\0\0\0\0\0\0\0";}
					if(symbolbyte.length==1){symbolString=symbolString+"\0\0\0\0\0\0\0";}
					if(symbolbyte.length==2){symbolString=symbolString+"\0\0\0\0\0\0";}
					if(symbolbyte.length==3){symbolString=symbolString+"\0\0\0\0\0";}
					if(symbolbyte.length==4){symbolString=symbolString+"\0\0\0\0";}
					if(symbolbyte.length==5){symbolString=symbolString+"\0\0\0";}
					if(symbolbyte.length==6){symbolString=symbolString+"\0\0";}
					if(symbolbyte.length==7){symbolString=symbolString+"\0";}
					
					if(signalbyte.length==0){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==1){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==2){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==3){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==4){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==5){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==6){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==7){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==8){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==9){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==10){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==11){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==12){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==13){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==14){signalString=signalString+"\0\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==15){signalString=signalString+"\0\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==16){signalString=signalString+"\0\0\0\0\0\0\0\0";}
					if(signalbyte.length==17){signalString=signalString+"\0\0\0\0\0\0\0";}
					if(signalbyte.length==18){signalString=signalString+"\0\0\0\0\0\0";}
					if(signalbyte.length==19){signalString=signalString+"\0\0\0\0\0";}
					if(signalbyte.length==20){signalString=signalString+"\0\0\0\0";}
					if(signalbyte.length==21){signalString=signalString+"\0\0\0";}
					if(signalbyte.length==22){signalString=signalString+"\0\0";}
					if(signalbyte.length==23){signalString=signalString+"\0";}
					ioallString=ioallString+symbolString+signalString;
					}
					} catch (Exception e) {
						// TODO: handle exception
						readerrorString="�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���";
						 break;
					}
				}
			}
		}
		
	}
	private void read_Optional(ArrayList<HashMap<String, Object>> ArrayList,
			String markname, String[] readlist, int step,
			ArrayList<HashMap<String, Integer>> mark_list) {
		
		int j = ArrayList.size();
		// ����������
		if (markname.equals("DeviceOptional")) {
			 for (int i = 0; i < j; i++) {
					ArrayList.get(i).put("name1", "");
					ArrayList.get(i).put("name2", "");
					ArrayList.get(i).put("name3", "");
				  }	
		}
		if (step == 0) {
			// Toast.makeText(getActivity(), markname+"����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			readerrorString="�豸����"+markname+"�ĺ��漸����ڴ��������ļ���";
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
					String name1 = readlist[4 * i + mark_list.get(0).get(markname) + 2].toString().trim();
					String name2 = readlist[4 * i + mark_list.get(0).get(markname) + 3].toString().trim();
					String name3 = readlist[4 * i + mark_list.get(0).get(markname) + 4].toString().trim();
					byte[] name1byte=null;
						name1byte=name1.getBytes("gb2312");
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
						if(name1byte.length==0){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==1){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==2){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==3){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==4){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==5){name1=name1+"\0\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==6){name1=name1+"\0\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==7){name1=name1+"\0\0\0\0\0\0\0\0\0";}
						if(name1byte.length==8){name1=name1+"\0\0\0\0\0\0\0\0";}
						if(name1byte.length==9){name1=name1+"\0\0\0\0\0\0\0";}
						if(name1byte.length==10){name1=name1+"\0\0\0\0\0\0";}
						if(name1byte.length==11){name1=name1+"\0\0\0\0\0";}
						if(name1byte.length==12){name1=name1+"\0\0\0\0";}
						if(name1byte.length==13){name1=name1+"\0\0\0";}
						if(name1byte.length==14){name1=name1+"\0\0";}
						if(name1byte.length==15){name1=name1+"\0";}
						
						ioallString=ioallString+name1+name2adsstring+name3adsstring;
					} catch (Exception e) {
						// TODO: handle exception
						readerrorString="�豸����"+markname+"�е�"+i+"�и������ڴ��������ļ���";
						 break;
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
			ArrayList<HashMap<String, Integer>> mark_list) {
     
		// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
		int j = ArrayList.size();
		 for (int i = 0; i < j; i++) {
			 ArrayList.get(i).put("numSpinner", "");
				ArrayList.get(i).put("orderSpinner", "");
				ArrayList.get(i).put("operatText", "");
				ArrayList.get(i).put("noteEditText", "");
		 }
		if (step == 0) {
			// Toast.makeText(getActivity(), markname+"����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			readerrorString=markname+"�ĺ��漸����ڴ��������ļ���";
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

		// ��ʱ������ȥ������У�ֻ��Ҫ�Ѷ�������ã��ù��ܱ���
		// else {
		// for ( int m=j-1; m>step; m--) {//��ȥ�������
		// ArrayListBound.getNCeditListData().remove(m);
		//
		// //
		// com.tr.programming.Fragments_NCedit.NCedit_Adapter.notifyDataSetChanged();
		// }
		// }

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
				readerrorString=markname+"�е�"+i+"�и������ڴ��������ļ���";
				 break;
			}
			// com.tr.programming.Fragments_NCedit.NCedit_Adapter.notifyDataSetChanged();
		}
		// Toast.makeText(this, "nc�ı�����������", Toast.LENGTH_SHORT).show();

		// ����ȥ�����������ö����У�������ȥ�����й���ʱ�������ɾ��
//		for (int i = step; i < j; i++) {
//			ArrayList.get(i).put("numSpinner", "");
//			ArrayList.get(i).put("orderSpinner", "");
//			ArrayList.get(i).put("operatText", "");
//			ArrayList.get(i).put("noteEditText", "");
//		}
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
			ArrayList<HashMap<String, Integer>> mark_list) {
      
		// �ж��ļ��г�����������ʾ�������Ĵ�С��ϵ
		int j = ArrayList.size();
		for (int i = 0; i < j; i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}
		if (step == 0) {
			// Toast.makeText(getActivity(), markname+"����Ϊ��", Toast.LENGTH_SHORT).show();
		}else if (step <0) {
			readerrorString=markname+"�ĺ��漸����ڴ��������ļ���";
		}  else {
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
				readerrorString=markname+"�е�"+i+"�и������ڴ��������ļ���";
				 break;
			}
		
		}
	/*	for (int i = step; i < j; i++) {
			ArrayList.get(i).put("inputEditText", "");
			ArrayList.get(i).put("outputEditText", "");
			ArrayList.get(i).put("noteEditText", "");
		}*/
		}
	}

	/**
	 * �����ļ���ϵͳ����ֹ��ȡΪ�մ���ͬʱ����mould�ļ�
	 */
	private  void creatFolderSysytem() {
		// TODO Auto-generated method stub
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		// �����ļ���Ŀ¼
		if (!sdCardExist) {
			Toast.makeText(getActivity(), "������ⲿSD�洢��",
					Toast.LENGTH_SHORT).show();// ���������SD����������ʾ
		} else {
			/* �ּ�Ŀ¼����ֿ�����������Ƕ�״�����������Ŀ¼���ܲ����� */

			File trFile = new File(Constans.trPATH);// �½�һ��Ŀ¼
			if (!trFile.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				trFile.mkdir();// ����������򴴽�
				Toast.makeText(getActivity(),
						"�´����ļ��нṹ" + Constans.trFolder, Toast.LENGTH_SHORT)
						.show();// ��ʾ�´���
			} else {
				// Toast.makeText(getApplicationContext(),
				// "�Ѵ����ļ���" + Constans.trFolder, Toast.LENGTH_SHORT)
				// .show();// ��ʾ�Ѵ���
			}

			Constans.mechanicalParameter
			.createFolder(Constans.trPATH,Constans.mechanicalParameterFolder);
			Constans.mouldData.createFolder(Constans.trPATH,
					Constans.mouldDataFolder).createFile("mouldList.trt")//mouldList��trt
												.createFile("mould.trt");

//			updateMouldFile();//���mouldList.trtΪ�գ������һ��

			Constans.otherParameter.createFolder(Constans.trPATH,
					Constans.otherParameterFolder).createFile(
							"otherParameter.trt");

		}
	}
	
	private  File getFileFromBytes(byte[] b, String outputFile) { 
        File ret = null; 
        BufferedOutputStream stream = null; 
        try {
        	Log.d("mpeng","the b size is"+b.length);
               ret = new File(outputFile); 
               FileOutputStream fstream = new FileOutputStream(ret); 
               stream = new BufferedOutputStream(fstream); 
               stream.write(b);
         
        } catch (Exception e) { 
//            log.error("helper:get file from byte process error!"); 
            e.printStackTrace(); 
        } finally { 
            if (stream != null) { 
                try { 
                    stream.close(); 
                } catch (IOException e) { 
//                    log.error("helper:get file from byte process error!"); 
                    e.printStackTrace(); 
                } 
            } 
        } 
        return ret; 
    }
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
	            	 else if(name.equals("ncdata"))
            			 is = getResources().openRawResource(R.raw.userlog);
	            	 else if(name.equals("tabledata"))
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
	/**
	 * 
	 * @Title: updateMouldFile 
	 * @Description: ��ȡģ�������ļ��������е�ģ�����������¸��ļ�    
	 * @return void    
	 * @throws
	 */
	private void updateMouldFile() {

		Constans.mouldData.cleanFile("mouldList��trt");
		//		Toast.makeText(getActivity(), "mouldList.trt�ļ��½��Ѹ���", Toast.LENGTH_SHORT).show();
		File mouldData = new File(Constans.mouldDataPATH);
		String[] readMouldFolders = mouldData.list();// �õ��ļ�����
		//Ҫ�ж��Ƿ�Ϊ�ļ��У���Ϊ�ļ�����ʾ

		for (int i = 0; i < readMouldFolders.length; i++) {
			File file = new File(Constans.mouldDataPATH+readMouldFolders[i]);
			if (file.isDirectory()) {
				Constans.mouldData.writeFile("mouldList��trt",
						String.format("%04d", i + 1) + "/" + "" + "/"
								+ readMouldFolders[i] + "/" + new Date(file.lastModified()).toLocaleString() + "/" + "\r\n");
			}
		}


		//		for (int i = 0; i < readMouldFolders.length; i++) {
		//			Constans.mouldData.writeFile(Constans.ģ������,
		//					String.format("%04d", i + 1) + "/" + "" + "/"
		//							+ readMouldFolders[i] + "/" + "" + "/" + "\r\n");
		//		}

	}

	private class MyAdapter extends BaseAdapter {
		private class buttonViewHolder {
			TextView num;
			TextView num_mould_setting;
			TextView name_mould_setting;
			TextView note_mould_setting;
		}

		private ArrayList<HashMap<String, Object>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String[] keyString;
		private int[] valueViewID;
		private buttonViewHolder holder;
		private int mLayoutID;
		private int mselectItem=-1;

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

		private void setSelectItem(int selectItem) {
			mselectItem = selectItem;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (buttonViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(mLayoutID, null);
				;
				holder = new buttonViewHolder();
				holder.num = (TextView) convertView
						.findViewById(valueViewID[0]);
				holder.num_mould_setting = (TextView) convertView
						.findViewById(valueViewID[1]);
				holder.name_mould_setting = (TextView) convertView
						.findViewById(valueViewID[2]);
				holder.note_mould_setting = (TextView) convertView
						.findViewById(valueViewID[3]);
				convertView.setTag(holder);
			}

			HashMap<String, Object> map_item = mAppList.get(position);
			if (map_item != null) {
				String num =  map_item.get(keyString[0]).toString();
				String num_mould_setting =  map_item.get(keyString[1]).toString();
				String name_mould_setting =  map_item.get(keyString[2]).toString();
				String note_mould_setting =  map_item.get(keyString[3]).toString();

				holder.num.setText(num);
				holder.num_mould_setting.setText(num_mould_setting);
				holder.name_mould_setting.setText(name_mould_setting);
				holder.note_mould_setting.setText(note_mould_setting);

				if (!num_mould_setting.equals("")&&!name_mould_setting.equals("")) {
					holder.num_mould_setting.setOnClickListener(new setValueListener(
							position));
					holder.name_mould_setting.setOnClickListener(new setValueListener(
							position));
				}

			}

			// ѡ�к�ɫ��ʾ
			if (position == mselectItem) {
				// �����ǰ���о���ListView��ѡ�е�һ�У��͸�����ʾ��ʽ
				holder.num.setBackgroundColor(Color.GREEN);
				holder.num_mould_setting.setBackgroundColor(Color.GREEN);
				holder.name_mould_setting.setBackgroundColor(Color.GREEN);
				holder.note_mould_setting.setBackgroundColor(Color.GREEN);
				//convertView.setBackgroundColor(Color.RED);
			} else {
				holder.num.setBackgroundColor(Color.GRAY);
				holder.num_mould_setting.setBackgroundColor(Color.WHITE);
				holder.name_mould_setting.setBackgroundColor(Color.WHITE);
				holder.note_mould_setting.setBackgroundColor(Color.GRAY);
				//convertView.setBackgroundColor(Color.BLACK);
			}
			return convertView;
		}

		/**
		 * 
		 * @ClassName: setValueListener 
		 * @Description: ���ڱ�ź�ע�ͣ�ȷ����Ҫ���µ�mouldList.trt�ļ����������ƣ���Ҫ�ı��ļ��е�����
		 * @author shea
		 * @date 2013-4-21 ����1:33:43 
		 *
		 */
		class setValueListener implements android.view.View.OnClickListener {

			private int position;
			String setValueString="";
			// ���캯��
			setValueListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {

				// ѡ�к�ɫ��ʾ
				listselectposition=position;
				setSelectItem(position);
				notifyDataSetChanged();

				//�ж��Զ�����һ��
				if (position==(mAppList.size()-1)) {
					addOneItem();	
					notifyDataSetChanged();
				}

				final EditText etEditText = new EditText(getActivity());

				setValueString="";
				switch (v.getId()) {
				case R.id.num_mould_setting:
					etEditText.setHint("�����ģ�����ݱ��");
	 				//�������볤��Ϊ5
	 				etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
	 				//����ֻ������0~9������
	 				etEditText.setKeyListener(new NumberKeyListener() {
	 				    @Override
	 				    protected char[] getAcceptedChars() {
	 				        return new char[] { '1', '2', '3', '4', '5', '6', '7', '8','9', '0' };
	 				    }
	 				    @Override
	 				    public int getInputType() {
	 				        return android.text.InputType.TYPE_CLASS_PHONE;//���ּ���
	 				    }
	 				});	
					setValueString =  mAppList.get(position).get(
							keyString[1]).toString().trim();
					break;
				case R.id.name_mould_setting:
					etEditText.setHint("�����ģ����������");
					setValueString =  mAppList.get(position).get(
							keyString[2]).toString().trim();
					break;
				default:
					break;
				}


				etEditText.setText(setValueString);
				etEditText.setSelection(setValueString.length());//���ù��λ��	


				new AlertDialog.Builder(getActivity())
				.setTitle("������趨ֵ")
				.setView(etEditText)
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						Long longtime=Calendar.getInstance().getTimeInMillis();
						//						String date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
						//String date2 = new Date(longtime).toLocaleString();// ��ǰʱ��
						final String date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());// ��ǰʱ��
						String editString = etEditText.getText().toString().trim();
						HashMap<String, Object> map = new HashMap<String, Object>();

						map.put("num",
								mAppList.get(position)
								.get(keyString[0])
								.toString().trim());

						switch (v.getId()) {
						case R.id.num_mould_setting://�޸�ĥ�����ݱ��
							String name_mould_setting=mAppList
									.get(position)
									.get(keyString[2])
									.toString().trim();
							//�½��ı��Ҫ�ж��Ƿ���Ѵ��ڵ��ظ�	
							if (!editString.equals(setValueString)) {
								checkMouldList(list_mouldnum, list_mould_setting,"num_mould_setting");
                                if (editString.equals("")) {
									editString=setValueString;//Ϊ�գ���ԭΪԭ��������
									Toast.makeText(getActivity(), "ģ�����ݱ�Ų���Ϊ�գ�����������",Toast.LENGTH_SHORT).show();
								    return;
								}else if (list_mouldnum.contains(String.format("%1$04d",Integer.parseInt(editString)))) {
									editString=setValueString;//���ظ�����ԭΪԭ��������
									Toast.makeText(getActivity(), "ģ�����ݱ���ظ�������������",Toast.LENGTH_SHORT).show();
									return;
								}
							}else{
								return;
							}
							
							map.put("num_mould_setting",String.format("%1$04d",Integer.parseInt(editString)));
							map.put("name_mould_setting",name_mould_setting);
							
							if (editString.equals("") && name_mould_setting.equals("")) {
								map.put("note_mould_setting", "");
							}else {
								map.put("note_mould_setting", date2);
							}
		
							if (!name_mould_setting.equals("")) {
								File file = new File(Constans.mouldDataPATH+name_mould_setting);
								file.setLastModified(longtime);//�޸��ļ�����޸�ʱ��
							}
							
							Toast.makeText(getActivity(), "�ɹ�����ģ�����ݱ��",Toast.LENGTH_SHORT).show();
							break;
						case R.id.name_mould_setting://�޸�ĥ���������ƣ���Ҫ����sd�����ļ�����
							String num_mould_setting=mAppList.get(position).get(keyString[1]).toString().trim();
							try {
								if (editString.getBytes("gb2312").length>18) {
									Toast.makeText(getActivity(), "ģ���������Ƴ��ȳ�����Χ������������",Toast.LENGTH_SHORT).show();
									return;
								}
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//�½�������Ҫ�ж��Ƿ���Ѵ��ڵ��ظ�	
							if (!editString.equals(setValueString)) {
								checkMouldList(list_mouldname, list_mould_setting,"name_mould_setting");

								if (list_mouldname.contains(editString.toLowerCase())) {
									editString=setValueString;//���ظ�����ԭΪԭ��������
									Toast.makeText(getActivity(), "ģ�����������ظ�������������",Toast.LENGTH_SHORT).show();
									return;
								}else if (editString.equals("")) {
									editString=setValueString;//Ϊ�գ���ԭΪԭ��������
									Toast.makeText(getActivity(), "ģ���������Ʋ���Ϊ�գ�����������",Toast.LENGTH_SHORT).show();
									return;
								}
							}else{
								return;
							}


							map.put("num_mould_setting",num_mould_setting);
							map.put("name_mould_setting",editString);
							
							if (editString.equals("") && num_mould_setting.equals("")) {
								map.put("note_mould_setting", "");
							}else {
								map.put("note_mould_setting", date2);
							}

							if (!setValueString.equals(editString)) {
								Constans.mouldData.renameFolder(setValueString,editString);//�������ļ�����
								File file2 = new File(Constans.mouldDataPATH+editString);
								file2.setLastModified(longtime);//�޸��ļ�����޸�ʱ��
								Toast.makeText(getActivity(), "�ɹ�����ģ����������",Toast.LENGTH_SHORT).show();
							}


							break;
						default:
							break;
						}

						mAppList.set(position, map);
						notifyDataSetChanged();

						saveMouldDataList(list_mould_setting,date2,position);//��Ҫ����mouldList.trt�ļ�

						notifyDataSetChanged();
						
						//strAll=checkAllNum(list_mould_setting);
						//��ʾ��ǰ������ģ������
						//text_all_mould.setText(strAll);
						//TR_Main_Activity.sharedPreference_openedDir.edit().putString("dir_all", strAll).commit();
					}

				}).setNegativeButton("ȡ��", null).show();

			}
		}

		private void addOneItem() {
			HashMap<String, Object> mapadd = new HashMap<String, Object>();
			mapadd.put("num",	String.format("%04d", mAppList.size()+1));
			mapadd.put("num_mould_setting","");
			mapadd.put("name_mould_setting","");
			mapadd.put("note_mould_setting","");
			mAppList.add(mAppList.size(),mapadd);

		}


	}

	/**
	 * �����е�mouldname����list
	 * @param list_mouldname
	 * @param arrayList 
	 * @param markString 
	 */
	public static void checkMouldList(
			ArrayList<String> list_name,
			ArrayList<HashMap<String, Object>> arrayList, String markString) {
		// ֱ����������
		list_name.clear();
		for (int i = 0; i < arrayList.size(); i++) {											
			String mouldstr=arrayList.get(i).get(markString).toString().trim();
			mouldstr=mouldstr.toLowerCase();
			if (!mouldstr.equals("")) {
				list_name.add(mouldstr);
			}	
		}			

	}



}
