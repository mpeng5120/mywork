package com.tr.newpragram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.DelayCount;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;

import com.dbutils.ArrayListBound;
import com.explain.HexDecoding;
import com.tr.R;
import com.tr.programming.Config;
import com.wifiexchange.WifiSetting_Info;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Fragments_extras extends Fragment {

	private Button btn_usermode;
	private Button btn_userdata;
	private Button btn_allspeed;
	
	byte[] temp=new byte[4];
	ArrayList<HashMap<String, Object>> list_usermode;
	ArrayList<HashMap<String, Object>> list_userdata;
	ArrayList<HashMap<String, Object>> list_allspeed;
	private byte[] getData;
	protected WifiReadDataFormat formatReadusermode;
	protected SendDataRunnable sendDatausermodeRunnable;
	
	ListView listView_usermode;
	MyAdapter_usermode adapter_usermode;
	
	protected WifiReadDataFormat formatReaduserdata;
	protected SendDataRunnable sendDatauserdataRunnable;
	
	ListView listView_userdata;
	MyAdapter_userdata adapter_userdata;
	
	protected WifiReadDataFormat formatReadallspeed;
	protected SendDataRunnable sendDataallspeedRunnable;
	
	ListView listView_allspeed;
	MyAdapter_allspeed adapter_allspeed;

	private WifiSendDataFormat formatSendMessage;
	private SendDataRunnable sendDataRunnable;
	private FinishRunnable sendDataFinishRunnable;
	private  DelayCount delayCount;
	private AlertDialog dialog_usermode;
	private AlertDialog dialog_userdata;
	private AlertDialog dialog_allspeed;
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("destroy");
		Config.isMutiThread = false;
		if(dialog_usermode!=null){
			dialog_usermode.dismiss();
		}
		if(dialog_userdata!=null){
			dialog_userdata.dismiss();
		}
		if(dialog_allspeed!=null){
			dialog_allspeed.dismiss();
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try{
			if (WifiSetting_Info.mClient == null) {
				Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
				return;
			}
		new Thread() {
				public void run() {
					list_usermode = ArrayListBound.getUserModeListData();
					list_userdata = ArrayListBound.getUserDataListData();
					list_allspeed = ArrayListBound.getAllSpeedListData();
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		System.out.println("Fragments_extras   onCreate");
	}
	
	//��ȡ���ص����ݺ���е�UI����
	private final Runnable usermodereadDataFinishTodo = new Runnable(){
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
			//���¸�list_usermode�б�ֵ
			ArrayList<HashMap<String, Object>> listmi=ArrayListBound.getDeviceMiddleInputListData();
			for(int i=0;i<formatReadusermode.getLength()*8-4;i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
			    map.put("number", i+1);
			    map.put("name",listmi.get(i).get("signalNameEditText").toString());
			    map.put("state", (int)((getData[i/8]>>(i%8))&0x01));
			   
			    list_usermode.set(i, map);
			}
			    adapter_usermode= new MyAdapter_usermode(getActivity(), list_usermode,
						R.layout.parameter_setting_other_functions_usermode_item,
						new String[] { "number", "name","state"},
						new int[] { R.id.number, R.id.modename,R.id.state});
				listView_usermode.setAdapter(adapter_usermode);
				Config.isMutiThread = true;
				delayCount=new DelayCount(1000, 100, getActivity(), 
						listView_usermode,"usermode");
				if (delayCount == null) {
					return;
				}
				delayCount.start();
		}
		}

	};
	//��ȡ���ص����ݺ���е�UI����
	private final Runnable userdatareadDataFinishTodo = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//���ڷ��ص�36�ֽ�
			//������ȷ�����
			//�����ص�����	
			getData=new byte[formatReaduserdata.getLength()];
			//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
			if( formatReaduserdata.getFinalData() != null)  
			{
			System.arraycopy(formatReaduserdata.getFinalData(), 0, getData, 0, formatReaduserdata.getLength());
			//���¸�list_usermode�б�ֵ
			ArrayList<HashMap<String, Object>> listdi=ArrayListBound.getDeviceDataInputListData();
			for(int i=0;i<16;i++){
				HashMap<String, Object> map = new HashMap<String, Object>();
			    map.put("number", i+1);
			    map.put("name",listdi.get(i).get("signalNameEditText").toString());
			    map.put("state", String.valueOf(HexDecoding.Array4Toint(getData, i*4)));
			    list_userdata.set(i, map);
			}
			adapter_userdata= new MyAdapter_userdata(getActivity(), list_userdata,
					R.layout.parameter_setting_other_functions_userdata_item,
					new String[] { "number", "name","state"},
					new int[] { R.id.number, R.id.dataname,R.id.state});
			listView_userdata.setAdapter(adapter_userdata);
			Config.isMutiThread = true;
			delayCount=new DelayCount(1000, 100, getActivity(), 
					listView_userdata,"userdata");
			if (delayCount == null) {
				return;
			}
			delayCount.start();
		}
		}

	};
	//��ȡ���ص����ݺ���е�UI����
	private final Runnable allspeedreadDataFinishTodo = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//���ڷ��ص�36�ֽ�
			//������ȷ�����
			//�����ص�����	
			getData=new byte[formatReadallspeed.getLength()];
			//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
			if( formatReadallspeed.getFinalData() != null)  
			{
			System.arraycopy(formatReadallspeed.getFinalData(), 0, getData, 0, formatReadallspeed.getLength());
			//���¸�list_usermode�б�ֵ
	
			    HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("name","�ٶ�");
				map1.put("editname",String.valueOf((int)(getData[0]&0xff)));
				list_allspeed.set(0, map1);
				HashMap<String, Object> map2 = new HashMap<String, Object>();
				map2.put("name","���ٶ�");
				map2.put("editname",String.valueOf((int)(getData[1]&0xff)));
				list_allspeed.set(1, map2);
				HashMap<String, Object> map3 = new HashMap<String, Object>();
				map3.put("name","���ٶ�");
				map3.put("editname",String.valueOf((int)(getData[2]&0xff)));
				list_allspeed.set(2, map3);
			    adapter_allspeed= new MyAdapter_allspeed(getActivity(), list_allspeed,
						R.layout.tab_setting_extras_allspeed_item,
						new String[] { "name", "editname"},
						new int[] { R.id.name, R.id.editname});
				listView_allspeed.setAdapter(adapter_allspeed);
				Config.isMutiThread = true;
				delayCount=new DelayCount(1000, 100, getActivity(), 
						listView_allspeed,"allspeed");
				if (delayCount == null) {
					return;
				}
				delayCount.start();
		}
		}

	};
	@Override
	public void onStart(){
		super.onStart();
		System.out.println("Fragments_extras   onstart");
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		System.out.println("Fragments_extras   onCreateView");
		
		return inflater.inflate(R.layout.tab_setting_extras, container,false);
		
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		System.out.println("Fragments_extras   onActivityCreated");
		try{
		super.onActivityCreated(savedInstanceState);

	
				btn_usermode=(Button) getActivity().findViewById(R.id.usermode);
				if(btn_usermode==null){
					return;
				}
				btn_usermode.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (WifiSetting_Info.mClient == null) {
							Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
							return;
						}
						View view_usermode=View.inflate(getActivity(), R.layout.parameter_setting_other_functions_usermode, null);
						dialog_usermode=new AlertDialog.Builder(getActivity())
						.setTitle("�û�ģʽ�趨").setView(view_usermode)
						.setNegativeButton("ȷ��",null).show();
						//�����˵��Ĺر��¼�  
						dialog_usermode.setOnDismissListener(new OnDismissListener() {              				            
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								Config.isMutiThread = false;
								if(delayCount!=null){
									delayCount.destroy();
								}
							}  
				        });
					    listView_usermode = (ListView) dialog_usermode.findViewById(R.id.listView_usermode);
						if(listView_usermode==null){
							return;
						}
						 
						formatReadusermode = new WifiReadDataFormat(0x200000AC,4);
						try {							
							sendDatausermodeRunnable=new SendDataRunnable(formatReadusermode, getActivity());
							sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",usermodereadDataFinishTodo);
							sendDatausermodeRunnable.setOnlistener(new NormalChatListenner(sendDatausermodeRunnable, sendDataFinishRunnable));
							getActivity().runOnUiThread(sendDatausermodeRunnable);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				});	
				
	
				btn_userdata=(Button) getActivity().findViewById(R.id.userdata);
				if(btn_userdata==null){
					return;
				}
				btn_userdata.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (WifiSetting_Info.mClient == null) {
							Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
							return;
						}
						View view_userdata=View.inflate(getActivity(), R.layout.parameter_setting_other_functions_userdata, null);
						dialog_userdata=new AlertDialog.Builder(getActivity())
						.setTitle("�û������趨").setView(view_userdata)
						.setNegativeButton("ȷ��",null).show();
						//�����˵��Ĺر��¼�  
						dialog_userdata.setOnDismissListener(new OnDismissListener() {              				            
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								Config.isMutiThread = false;
								if(delayCount!=null){
									delayCount.destroy();
								}
							}  
				        });
					    listView_userdata = (ListView) dialog_userdata.findViewById(R.id.listView_userdata);
						if(listView_userdata==null){
							return;
						}
						formatReaduserdata = new WifiReadDataFormat(0x200000B0,16*4);
						try {
							sendDatauserdataRunnable=new SendDataRunnable(formatReaduserdata, getActivity());

							sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",userdatareadDataFinishTodo);

							sendDatauserdataRunnable.setOnlistener(new NormalChatListenner(sendDatauserdataRunnable, sendDataFinishRunnable));

							getActivity().runOnUiThread(sendDatauserdataRunnable);
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
						}
						
						
					}
				});	
		
				btn_allspeed=(Button) getActivity().findViewById(R.id.allspeed);
				if(btn_allspeed==null){
					return;
				}
				btn_allspeed.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (WifiSetting_Info.mClient == null) {
							Toast.makeText(getActivity(),"������������", Toast.LENGTH_SHORT).show();
							return;
						}
						View view_allspeed=View.inflate(getActivity(), R.layout.tab_setting_extras_allspeed, null);
						dialog_allspeed=new AlertDialog.Builder(getActivity())
						.setTitle("ȫ���ٶ��趨").setView(view_allspeed)
						.setNegativeButton("ȷ��",null).show();
						//�����˵��Ĺر��¼�  
						dialog_allspeed.setOnDismissListener(new OnDismissListener() {              				            
							@Override
							public void onDismiss(DialogInterface arg0) {
								// TODO Auto-generated method stub
								Config.isMutiThread = false;
								if(delayCount!=null){
									delayCount.destroy();
								}
							}  
				        });
						 listView_allspeed = (ListView) dialog_allspeed.findViewById(R.id.listView_allspeed);
							if(listView_allspeed==null){
								return;
							}
							formatReadallspeed = new WifiReadDataFormat(0x400241C0,4);
							try {
								
								sendDataallspeedRunnable=new SendDataRunnable(formatReadallspeed, getActivity());

								sendDataFinishRunnable=new FinishRunnable(getActivity(), "���ݶ�ȡ���",allspeedreadDataFinishTodo);

								sendDataallspeedRunnable.setOnlistener(new NormalChatListenner(sendDataallspeedRunnable, sendDataFinishRunnable));

								getActivity().runOnUiThread(sendDataallspeedRunnable);
							} catch (Exception e) {
								// TODO: handle exception
								Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG);
							}
							
					}
					
				});	
	}catch(Exception e){
		e.printStackTrace();
	}
	}

 	public class MyAdapter_usermode extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView number;
 			TextView modename;
 			ToggleButton state;
 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_usermode(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
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
 				holder.number = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.modename = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.state = (ToggleButton) convertView
 						.findViewById(valueViewID[2]);
 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String number = appInfo.get(keyString[0]).toString();
 				String modename = appInfo.get(keyString[1]).toString();
 				String state = appInfo.get(keyString[2]).toString();
 				holder.number.setText(number);
 				holder.modename.setText(modename);
 				//holder.modename.setOnClickListener(new modenameListener(position));
 				if(state.equals("1")){
 				     holder.state.setText("ON");
 				    holder.state.setBackgroundColor(Color.BLUE);
 				}else{
 					 holder.state.setText("OFF");
 					holder.state.setBackgroundColor(Color.GRAY);
 				}
 				final int mposition=position;
 				holder.state.setOnCheckedChangeListener(new OnCheckedChangeListener() {

 					@Override
 		 			public void onCheckedChanged(CompoundButton buttonView,
 		 					boolean isChecked) {
 						if(Config.StopState){
 		 				if (isChecked) {
 							mAppList.get(mposition).put("state", "1");
 		 				} else {
 		 					mAppList.get(mposition).put("state", "0");
 		 				}
 		 				notifyDataSetChanged();
 		 				byte[] temp=new byte[4];
						try {
						for(int i=0;i<4;i++){
							int result=0;
							for(int j=0;j<8;j++){
								if(j+i*8>27){break;}
								if(list_usermode.get(j+i*8).get("state").toString().equals("")){return;}
								result=result|(Integer.valueOf(list_usermode.get(j+i*8).get("state").toString())<<j);//�˴���list_usermode�� ���� �û��޸�
						    }
							System.arraycopy(HexDecoding.int2byte(result), 0, temp, i, 1);
						}
						getData[3]=(byte) (delayCount.getCurrentData3()&0xF0);//ϵͳģʽ���뱣��ԭ״
						temp[3]=(byte) (getData[3]|temp[3]);
						  formatSendMessage=new WifiSendDataFormat(temp, 0x200000AC);
	                      sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());
					      sendDataFinishRunnable=new FinishRunnable(getActivity(), "�û�ģʽ�趨��Ϣ�ɹ����͸���λ��");
						  sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));
						  getActivity().runOnUiThread(sendDataRunnable);
								} catch (Exception e) {
								e.printStackTrace();
							    }
 						}else{
 							new AlertDialog.Builder(getActivity())
							.setTitle("����")
							.setMessage("�����趨�û�ģʽ�����л����ֶ�״̬��")
							.setPositiveButton("֪����",null)
							.show();
 						}
 		 			}
 		 		});
 			}
 			return convertView;
 		}

 	}
 	public class MyAdapter_userdata extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView number;
 			TextView modename;
 			TextView state;
 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_userdata(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
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
 				holder.number = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.modename = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.state = (TextView) convertView
 						.findViewById(valueViewID[2]);
 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String number = appInfo.get(keyString[0]).toString();
 				String modename = appInfo.get(keyString[1]).toString();
 				String state = appInfo.get(keyString[2]).toString();
 				holder.number.setText(number);
 				holder.modename.setText(modename);
 			    holder.state.setText(state);
 			    holder.state.setOnClickListener(new dataListener(position));
 			}
 			return convertView;
 		}

 		class dataListener implements android.view.View.OnClickListener {
			private int position;

			dataListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {
				if(Config.StopState){
				final EditText etEditText = new EditText(getActivity());
				String daString=mAppList.get(position).get(
						keyString[2]).toString();
				etEditText.setText(daString);
				etEditText.setSelection(daString.length());// ���ù��λ��
				etEditText.setHint("��������ֵ");
				//etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '0','1','2','3','4','5','6','7','8','9','.'};
					}

					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
					}
				});
				new AlertDialog.Builder(getActivity())
						.setTitle("�����ֵ")
						.setView(etEditText)
						.setPositiveButton(R.string.OK,//ȷ��
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										
										String editString = etEditText
												.getText().toString().trim();
										if (editString.equals("")) {
											Toast.makeText(getActivity(), "����Ϊ�գ�����������", Toast.LENGTH_SHORT).show();
										    return;
										}
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("number",
												mAppList.get(position)
														.get(keyString[0])
														.toString());
										map.put("name",
												mAppList.get(position)
														.get(keyString[1])
														.toString());
										map.put("state",
												editString);
										mAppList.set(position, map);
										notifyDataSetChanged();
										
										byte[] temp=new byte[16*4];
										 try {
										for(int i=0;i<16;i++){
											if(list_userdata.get(i).get("state").toString().equals("")){
												Toast.makeText(getActivity(), "����Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();
												return;}
										    System.arraycopy(HexDecoding.int2byteArray4(Integer.parseInt(list_userdata.get(i).get("state").toString())), 0, temp, i*4, 4);
										}
										  formatSendMessage=new WifiSendDataFormat(temp, 0x200000B0);
					                       
					                        	sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());

												sendDataFinishRunnable=new FinishRunnable(getActivity(), "�û������趨��Ϣ�ɹ����͸���λ��");

												sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

												getActivity().runOnUiThread(sendDataRunnable);
												
												} catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
												Toast.makeText(getActivity(), "�����������������룡", Toast.LENGTH_SHORT).show();
											    }
									}

								}).setNegativeButton(R.string.CANCEL, null)//ȡ��
								.show();

				notifyDataSetChanged();
				}else{
					new AlertDialog.Builder(getActivity())
					.setTitle("����")
					.setMessage("�����趨�û����ݣ����л����ֶ�״̬��")
					.setPositiveButton("֪����",null)
					.show();
				}
			}

		}
 	}	
 	public class MyAdapter_allspeed extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView name;
 			TextView editname;
 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter_allspeed(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
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
 				holder.name = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.editname = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String name = appInfo.get(keyString[0]).toString();
 				String editname = appInfo.get(keyString[1]).toString();
 				holder.name.setText(name);
 				holder.editname.setText(editname);
 			    holder.editname.setOnClickListener(new dataListener(position));
 			}
 			return convertView;
 		}
 		class dataListener implements android.view.View.OnClickListener {
			private int position;

			dataListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {
				final EditText etEditText = new EditText(getActivity());
				String speedString=mAppList.get(position).get(
						keyString[1]).toString();
				etEditText.setText(speedString);
				etEditText.setSelection(speedString.length());// ���ù��λ��
				switch (position) {
				case 0://�ٶȣ��޶�1~100
					etEditText.setHint("��ֵ��Χ1~100");
					etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
					etEditText.setKeyListener(new NumberKeyListener() {
						@Override
						protected char[] getAcceptedChars() {
							return new char[] {'1', '2', '3', '4', '5', '6', '7',
									'8', '9', '0'};
						}

						@Override
						public int getInputType() {
							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
						}
					});
					break;
				case 1:// ���ٶȣ��޶�1~5
				case 2:// ���ٶȣ��޶�1~5
					etEditText.setHint("��ֵ��Χ1~5");
					etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
					etEditText.setKeyListener(new NumberKeyListener() {
						@Override
						protected char[] getAcceptedChars() {
							return new char[] { '1', '2', '3', '4', '5' };
						}

						@Override
						public int getInputType() {
							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
						}
					});
					break;
				}
				new AlertDialog.Builder(getActivity())
						.setTitle("�����ֵ")
						.setView(etEditText)
						.setPositiveButton(R.string.OK,//ȷ��
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										
										String editString = etEditText
												.getText().toString().trim();
										if (editString.equals("")) {
											Toast.makeText(getActivity(), "����Ϊ�գ�����������", Toast.LENGTH_SHORT).show();
										    return;
										}
										int editInt;
										if (!editString.equals("")) {
											switch (position) {
											case 0://�ٶȣ��޶�1~100
												editInt=Integer.parseInt(editString);
												if (editInt<1||editInt > 100) {
													Toast.makeText(getActivity(),"�ٶȳ�����Χ1~100������������",Toast.LENGTH_SHORT).show();
													editString = "";
													return;
												}
												break;
											case 1:// ���ٶȣ��޶�1~5
												editInt=Integer.parseInt(editString);
												if (editInt <1|| editInt > 5) {
													Toast.makeText(getActivity(),"���ٶȳ�����Χ1~5������������",Toast.LENGTH_SHORT).show();
													editString = "";
													return;
												}
												break;
											case 2:// ���ٶȣ��޶�1~5
												editInt=Integer.parseInt(editString);
												if (editInt<1||editInt>5) {
													Toast.makeText(getActivity(),"���ٶȳ�����Χ1~5������������",Toast.LENGTH_SHORT).show();
													editString = "";
													return;
												}
												break;
											}
											
										}
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("name",
												mAppList.get(position)
														.get(keyString[0])
														.toString());
										map.put("editname",
												editString);
										mAppList.set(position, map);
										notifyDataSetChanged();
										try {
											if(((Map)listView_allspeed.getItemAtPosition(0)).get("editname").toString().equals("")){
												Toast.makeText(getActivity(), "�ٶ�Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();return;}
											if(((Map)listView_allspeed.getItemAtPosition(1)).get("editname").toString().equals("")){
												Toast.makeText(getActivity(), "���ٶ�Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();return;}
											if(((Map)listView_allspeed.getItemAtPosition(2)).get("editname").toString().equals("")){
												Toast.makeText(getActivity(), "���ٶ�Ϊ�գ����������룡", Toast.LENGTH_SHORT).show();return;}
											System.arraycopy(HexDecoding.int2byte(Integer.parseInt(((Map)listView_allspeed.getItemAtPosition(0)).get("editname").toString())), 0, temp, 0, 1);
											System.arraycopy(HexDecoding.int2byte(Integer.parseInt(((Map)listView_allspeed.getItemAtPosition(1)).get("editname").toString())), 0, temp, 1, 1);
											System.arraycopy(HexDecoding.int2byte(Integer.parseInt(((Map)listView_allspeed.getItemAtPosition(2)).get("editname").toString())), 0, temp, 2, 1);
											System.arraycopy(HexDecoding.int2byte(1), 0, temp, 3, 1);
											  formatSendMessage=new WifiSendDataFormat(temp, 0x400241C0);
						                       
						                        	sendDataRunnable=new SendDataRunnable(formatSendMessage, getActivity());

													sendDataFinishRunnable=new FinishRunnable(getActivity(), "ȫ���ٶ��趨��Ϣ�ɹ����͸���λ��");

													sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

													getActivity().runOnUiThread(sendDataRunnable);
													} catch (Exception e) {
													// TODO: handle exception
													e.printStackTrace();
													Toast.makeText(getActivity(), "�����������������룡", Toast.LENGTH_SHORT).show();
												    }
									}

								}).setNegativeButton(R.string.CANCEL, null)//ȡ��
								.show();

				notifyDataSetChanged();
			}

		}
 	}	

}
