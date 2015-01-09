package com.tr.newpragram;
import java.util.ArrayList;
import java.util.HashMap;
import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.KeyCodeSend;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.PositionQueryRunnable;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ToggleButton;

import com.dataInAddress.Define;
import com.explain.HexDecoding;
import com.tr.R;
import com.tr.programming.Config;


		public class SpAdapter extends BaseAdapter {
			private class buttonViewHolder {
				TextView name_sp;
				TextView line_sp_setting;
				TextView BA_sp_setting;
				TextView UD_sp_setting;
			}

			private ArrayList<HashMap<String, Object>> mAppList;
			private LayoutInflater mInflater;
			private Activity mContext;
			private String[] keyString;
			private buttonViewHolder holder;
			private int mLayoutID;
			private int mselectedItem = -1;
			private static int selectedItem = -1;
			private int id = -1;
			private int[] valueViewID;
			private String[] oldstrings=new String[3];
			private  ToggleButton toggBtn;
			//public PositionQueryRunnable positionQueryRunnable;
			private int start_index=0;
			private ListView fSpListView;
			
			private SendDataRunnable sendDataRunnable;
			private WifiSendDataFormat formatSendMessage;
			private FinishRunnable sendDataFinishRunnable;
			private View tempV;
			private int tempposition;
			private String tempeditString;
			private int send_address_speed=-1;
			private String posname;
			//private int[] pspfpaxleFlag;
		// MyAdapter�Ĺ��캯��
		public SpAdapter(Activity c,ToggleButton v, ArrayList<HashMap<String, Object>> appList,
				 String[] from,ListView fSpListView) {
			mAppList = appList;
			toggBtn= v;
			mContext = c;
			
			mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			keyString = new String[from.length];
			System.arraycopy(from, 0, keyString, 0, from.length);
			mLayoutID=R.layout.sfp_list_item;
			valueViewID=new int[] {R.id.sfp_name, R.id.value_line,
					R.id.value_BF,R.id.value_UD };
			//this.positionQueryRunnable=positionQueryRunnable;
			this.fSpListView=fSpListView;
			send_address_speed=-1;
		}

		public SpAdapter(Activity c,ToggleButton v, ArrayList<HashMap<String, Object>> appList,
				 String[] from,ListView fSpListView,int send_address_speed,String posname) {
			mAppList = appList;
			toggBtn= v;
			mContext = c;
			
			mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			keyString = new String[from.length];
			System.arraycopy(from, 0, keyString, 0, from.length);
			mLayoutID=R.layout.sfp_list_item;
			valueViewID=new int[] {R.id.sfp_name, R.id.value_line,
					R.id.value_BF,R.id.value_UD };
			this.fSpListView=fSpListView;
			this.send_address_speed=send_address_speed;
			this.posname=posname;
			//this.pspfpaxleFlag=pspfpaxleFlag;
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
				holder.name_sp = (TextView) convertView
						.findViewById(valueViewID[0]);
				holder.line_sp_setting = (TextView) convertView
						.findViewById(valueViewID[1]);
				holder.BA_sp_setting = (TextView) convertView
						.findViewById(valueViewID[2]);
				holder.UD_sp_setting = (TextView) convertView
						.findViewById(valueViewID[3]);
				convertView.setTag(holder);
			}

			HashMap<String, Object> appInfo = mAppList.get(position);
			if (appInfo != null) {
				String name_sp =  appInfo.get(keyString[0]).toString();
				Log.d("mpeng","the keyString 1 " +keyString[1]+"pos is"+position);
				String line_sp_setting =  appInfo.get(keyString[1]).toString();
				String BA_sp_setting =  appInfo
						.get(keyString[2]).toString();
				String UD_sp_setting =  appInfo
						.get(keyString[3]).toString();

				holder.name_sp.setText(name_sp);
				holder.line_sp_setting.setText(line_sp_setting);
				holder.BA_sp_setting.setText(BA_sp_setting);
				holder.UD_sp_setting.setText(UD_sp_setting);

				if (position == 0) {
					System.out.println("sp positionqueryrunnable");
					int tmp = 0;
					if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
						tmp = Define.POSITION_MATERIAL;
					}else{
						tmp = Define.POSITION_GOODS;
					}
					if (!NewPragramActivity.positionQueryRunnable.existFlag){ //��һ����ʾʱ�����̣߳������������߳��޹�
						NewPragramActivity.positionQueryRunnable = new PositionQueryRunnable(
								mContext,holder.line_sp_setting,holder.BA_sp_setting,holder.UD_sp_setting,tmp);
						Thread a1=new Thread(NewPragramActivity.positionQueryRunnable);
						a1.start();
					}
					
					// ���ڵ�ǰҳ����ʾ
					fSpListView.setOnScrollListener(new OnScrollListener() {

						@Override
						public void onScrollStateChanged(AbsListView view, int scrollState) {
							// TODO Auto-generated method stub
							if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {// listֹͣ����ʱ����ͼƬ
								// ˢ�½���
								// tempaAdapter.notifyDataSetChanged();
							}
						}

						@Override
						public void onScroll(AbsListView view, int firstVisibleItem,
								int visibleItemCount, int totalItemCount) {
							// TODO Auto-generated method stub
							start_index = fSpListView.getFirstVisiblePosition();
							int tmp = 0;
							//����һ������ʱ�����̣߳������������ݵ�������
							if (start_index != 0) {
								if(NewPragramActivity.positionQueryRunnable!=null){
									NewPragramActivity.positionQueryRunnable.destroy();
								}
							}else{
								if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
									tmp = Define.POSITION_MATERIAL;
								}else{
									tmp = Define.POSITION_GOODS;
								}
								if (!NewPragramActivity.positionQueryRunnable.existFlag){ //��һ����ʾʱ�����̣߳������������߳��޹�
									NewPragramActivity.positionQueryRunnable = new PositionQueryRunnable(
											mContext,holder.line_sp_setting,holder.BA_sp_setting,holder.UD_sp_setting,tmp);
									Thread a1=new Thread(NewPragramActivity.positionQueryRunnable);
									a1.start();
								}
							}
						}
					});
				}

				if((position!=0)&&(position!=5))
				{
					holder.line_sp_setting.setOnClickListener(new SPListener(position));
		            holder.BA_sp_setting.setOnClickListener(new SPListener(position));
				    holder.UD_sp_setting.setOnClickListener(new SPListener(position));
				}
				/**
				 * ���ñ���ɫ������ѡ������ɫ��ʾ
				 */
				holder.name_sp.setBackgroundColor(Color.GRAY);
				if (position == 0 || position == 5) {
					holder.line_sp_setting.setBackgroundColor(Color.GRAY);
					holder.BA_sp_setting.setBackgroundColor(Color.GRAY);
					holder.UD_sp_setting.setBackgroundColor(Color.GRAY);
				} else if (position == mselectedItem) {// ѡ����ɫ��ʾ
					convertView.findViewById(id).setBackgroundColor(Color.BLUE);
					for (int i = 1; i < valueViewID.length; i++) {
						if (id != valueViewID[i]) {
							convertView.findViewById(valueViewID[i])
									.setBackgroundColor(Color.WHITE);
						}
					}
				} else {
					holder.line_sp_setting.setBackgroundColor(Color.WHITE);
					holder.BA_sp_setting.setBackgroundColor(Color.WHITE);
					holder.UD_sp_setting.setBackgroundColor(Color.WHITE);
					convertView.setBackgroundColor(Color.BLACK);
				}
				
				/*if (position == 1) {
					if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
						if(pspfpaxleFlag[0]==1){
							holder.line_sp_setting.setBackgroundColor(Color.WHITE);
						}else{
							holder.line_sp_setting.setBackgroundColor(Color.GRAY);
						}
						
                        if(pspfpaxleFlag[2]==1){
                        	holder.BA_sp_setting.setBackgroundColor(Color.WHITE);
						}else{
							holder.BA_sp_setting.setBackgroundColor(Color.GRAY);
						}

                        if(pspfpaxleFlag[4]==1){
                        	holder.UD_sp_setting.setBackgroundColor(Color.WHITE);
                        }else{
                        	holder.UD_sp_setting.setBackgroundColor(Color.GRAY);
                        }							
					}else{
						if(pspfpaxleFlag[0]==1){
							holder.line_sp_setting.setBackgroundColor(Color.WHITE);
						}else{
							holder.line_sp_setting.setBackgroundColor(Color.GRAY);
						}
						
                        if(pspfpaxleFlag[1]==1){
                        	holder.BA_sp_setting.setBackgroundColor(Color.WHITE);
						}else{
							holder.BA_sp_setting.setBackgroundColor(Color.GRAY);
						}

                        if(pspfpaxleFlag[3]==1){
                        	holder.UD_sp_setting.setBackgroundColor(Color.WHITE);
                        }else{
                        	holder.UD_sp_setting.setBackgroundColor(Color.GRAY);
                        }
					}
				}*/
			}
			

			return convertView;
		}





		/**
		 * ������Ӧ�࣬ʵ�ֿɱ༭ֱ������ı���ֵ����
		 * 
		 * @author shea
		 * 
		 */
		class SPListener implements android.view.View.OnClickListener {
			private int position;

			SPListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {

				final EditText etEditText = new EditText(mContext);
				setSelectItemBlue(position, v.getId());
				selectedItem = position;
				notifyDataSetChanged();
				switch (position) {
				case 1:
					etEditText.setHint("֧�ָ�ʽΪ#####.#���������������5λ��С�����1λ");

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
					break;
				case 2:// ���ڿ�ʼλ�ú�װ����
					etEditText.setHint("��ֵ��Χ-327.6~327.6");

					etEditText.setKeyListener(new NumberKeyListener() {
						@Override
						protected char[] getAcceptedChars() {
							return new char[] { '1', '2', '3', '4', '5', '6', '7',
									'8', '9', '0', '.' , '-'};
						}

						@Override
						public int getInputType() {
							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
						}
					});
					break;
				case 4:	//װ���趨��������Ϊ0���޶�1~999��
					etEditText.setHint("��ֵ��Χ1~999");
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
				case 3:	// ����װ��˳������ֻ������123�е�һ��������������Ƿ��ظ�
					etEditText.setHint("ֻ����������1 2 3");
	 				etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
					etEditText.setKeyListener(new NumberKeyListener() {
						@Override
						protected char[] getAcceptedChars() {
							return new char[] { '1', '2', '3'};
						}

						@Override
						public int getInputType() {
							return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
						}
					});
					break;
				default:
					break;
				}

				 String foot_String = "";
				switch (v.getId()) {
				case R.id.value_line:
					if(send_address_speed!=-1){
					KeyCodeSend.send(111, mContext);
					}
					foot_String =  mAppList.get(position).get(
							keyString[1]).toString();
					break;
				case R.id.value_BF:
					if(send_address_speed!=-1){
					KeyCodeSend.send(112, mContext);
					}
					foot_String =  mAppList.get(position).get(
							keyString[2]).toString();
					break;
				case R.id.value_UD:
					if(send_address_speed!=-1){
					KeyCodeSend.send(114, mContext);
					}
					foot_String =  mAppList.get(position).get(
							keyString[3]).toString();
					break;
				default:
					break;
				}
				/*
				 * ��ʼ���������������趨ֵ
				 */
				TextView t = (TextView) v;
				String valueString = t.getText().toString().trim();

				
				etEditText.setText(foot_String);
				if (foot_String != null) {
					etEditText.setSelection(foot_String.length());// ���ù��λ��
				}

				final String  oldstring=foot_String.equals("")?"0":foot_String;
				new AlertDialog.Builder(mContext)
						.setTitle("������趨ֵ")
						.setView(etEditText)
						.setPositiveButton(R.string.OK,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {

										String editString = etEditText
												.getText().toString().trim();

										if (!editString.equals("")) {
													
											double editDouble=0;
													switch (position) {
													case 1://��ʼλ�ã�������С��
														if (editString.contains(".")) {
															String[] editStrings=editString.split("\\.");
															if (2==editStrings.length) {
																editDouble=Double.parseDouble(editString);
																if (editDouble>=100000) {
																	Toast.makeText(mContext, "���ݳ�����Χ������������", Toast.LENGTH_SHORT).show();
																	editString = "";
																	return;
																}else {
																	editString=String.format("%1$5.1f", editDouble);
																}
															}else{
																Toast.makeText(mContext, "���ݸ�ʽ��������������", Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															}
														}else {
															editDouble=Double.parseDouble(editString);
															if (editDouble>=100000) {
																Toast.makeText(mContext, "���ݳ�����Χ������������", Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															}else{
																editString=String.format("%1$5.1f", editDouble);
															}
														}
														break;
													case 2://װ���࣬������С��
														if (editString.contains(".")) {
															String[] editStrings=editString.split("\\.");
															if (2==editStrings.length) {
																editDouble=Double.parseDouble(editString);
																if (Math.abs(editDouble)>=327.6) {
																	Toast.makeText(mContext, "���ݳ�����Χ������������", Toast.LENGTH_SHORT).show();
																	editString = "";
																	return;
																}else {
																	editString=String.format("%1$5.1f", editDouble);
																}
															}else{
																Toast.makeText(mContext, "���ݸ�ʽ��������������", Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															}
														}else {
															editDouble=Double.parseDouble(editString);
															if (Math.abs(editDouble)>=327.6) {
																Toast.makeText(mContext, "���ݳ�����Χ������������", Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															}else{
																editString=String.format("%1$5.1f", editDouble);
															}
														}
														break;
													case 4://װ���趨��������Ϊ0,�޶�1~999
														editDouble=Double.parseDouble(editString);
														if (editDouble==0) {
															Toast.makeText(mContext, "���ݳ�����Χ1~999������������", Toast.LENGTH_SHORT).show();
															editString="";
															return;
														}
														break;
													case 3://װ��˳��ֻ����1 2 3�����
														if (oldstring.equals(editString)) {
															//Toast.makeText(getActivity(), "����˳��"+"\""+editString+"\","+"����������", Toast.LENGTH_SHORT).show();
															Toast.makeText(mContext, "�����ظ�������������", Toast.LENGTH_SHORT).show();
															editString="";
															return;
														}										
														break;
													default:
														break;
													}

										} else {
											Toast.makeText(mContext,"����Ϊ�գ�����������",Toast.LENGTH_SHORT).show();
											editString = "";
											return;
										}
										
										String valueString = editString;
                                   if(send_address_speed!=-1){
								//===================================		
										int send_address= send_address_speed - 10*Define.MAX_AXIS_NUM;// 0x200026f0;// ��׼װ��ͷ��ַ
										//��ȡ�趨�ٶȣ����ٶȣ����ٶ���Ϣ,��д����
										byte[] temp=new byte[4*8+8*6+4]; //
										//�ѽ����ϵ�ֵ��һ�飬����8���ֽڵ��ֽ�����
										try {
											if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
											//��ֹ
											if (!mAppList.get(1).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(1).get(keyString[1]).toString())*100)), 0, temp, 0, 4);					
											}
											if (!mAppList.get(1).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(1).get(keyString[2]).toString())*100)), 0, temp, 8, 4);

											}
											if (!mAppList.get(1).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(1).get(keyString[3]).toString())*100)), 0, temp, 16, 4);

											}
											
											if (!mAppList.get(2).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(2).get(keyString[1]).toString())*100)), 0, temp, 32, 2);					
											}
											if (!mAppList.get(2).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(2).get(keyString[2]).toString())*100)), 0, temp, 36, 2);

											}
											if (!mAppList.get(2).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(2).get(keyString[3]).toString())*100)), 0, temp, 40, 2);

											}
											if (!mAppList.get(4).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(4).get(keyString[1]).toString()))), 0, temp, 48, 2);					
											}
											if (!mAppList.get(4).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(4).get(keyString[2]).toString()))), 0, temp, 52, 2);

											}
											if (!mAppList.get(4).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(4).get(keyString[3]).toString()))), 0, temp, 56, 2);

											}
											
											if (!mAppList.get(3).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(3).get(keyString[1]).toString()))), 0, temp, 64, 2);					
											}
											if (!mAppList.get(3).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(3).get(keyString[2]).toString()))), 0, temp, 68, 2);

											}
											if (!mAppList.get(3).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(3).get(keyString[3]).toString()))), 0, temp, 72, 2);

											}
										}else{
											//��ֹ
											if (!mAppList.get(1).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(1).get(keyString[1]).toString())*100)), 0, temp, 0, 4);					
											}
											if (!mAppList.get(1).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(1).get(keyString[2]).toString())*100)), 0, temp, 4, 4);

											}
											if (!mAppList.get(1).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(1).get(keyString[3]).toString())*100)), 0, temp, 12, 4);

											}
											
											if (!mAppList.get(2).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(2).get(keyString[1]).toString())*100)), 0, temp, 32, 2);					
											}
											if (!mAppList.get(2).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(2).get(keyString[2]).toString())*100)), 0, temp, 34, 2);

											}
											if (!mAppList.get(2).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(2).get(keyString[3]).toString())*100)), 0, temp, 38, 2);

											}
											if (!mAppList.get(4).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(4).get(keyString[1]).toString()))), 0, temp, 48, 2);					
											}
											if (!mAppList.get(4).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(4).get(keyString[2]).toString()))), 0, temp, 50, 2);

											}
											if (!mAppList.get(4).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(4).get(keyString[3]).toString()))), 0, temp, 54, 2);

											}
											
											if (!mAppList.get(3).get(keyString[1]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(3).get(keyString[1]).toString()))), 0, temp, 64, 2);					
											}
											if (!mAppList.get(3).get(keyString[2]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(3).get(keyString[2]).toString()))), 0, temp, 66, 2);

											}
											if (!mAppList.get(3).get(keyString[3]).toString().equals("")) {
												System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(3).get(keyString[3]).toString()))), 0, temp, 70, 2);

											}
										}
											
											//�����趨ֵ�޸�����
											if(position==3){
												System.out.println("  oldstring=="+oldstring);
												settingValueCopy7(v,oldstring, editString, position, temp);
											}else{
											 settingValueCopy(v, editString, position, temp);
											}
											//type�ݶ�Ϊ1,���ڹ���Ҫ�Ľ���
											System.arraycopy(HexDecoding.int2byte(1), 0, temp, 83, 1);
									
											formatSendMessage=new WifiSendDataFormat(temp, send_address);
		
											tempV=v;
											tempeditString=editString;
											tempposition=SPListener.this.position;
											try {
												sendDataRunnable=new SendDataRunnable(formatSendMessage, mContext);

												sendDataFinishRunnable=new FinishRunnable(mContext, "���ݷ������",backMessageToRunnable);

												sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

												mContext.runOnUiThread(sendDataRunnable);	


											} catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
												//Toast.makeText(getActivity(), "������������", Toast.LENGTH_LONG).show();
											}
										}catch (Exception e) {
											// TODO: handle exception

										}
											
							//===========================================		
                                   }	
										HashMap<String, Object> map = new HashMap<String, Object>();
										switch (v.getId()) {
										case R.id.value_line:
											map.put(keyString[0],
													mAppList.get(position)
															.get(keyString[0])
															.toString());
											map.put(keyString[1],
													editString);
											map.put(keyString[2],
													mAppList.get(position)
															.get(keyString[2])
															.toString());
											map.put(keyString[3],
													mAppList.get(position)
															.get(keyString[3])
															.toString());

											mAppList.set(position, map);
											break;
										case R.id.value_BF:
											map.put(keyString[0],
													mAppList.get(position)
															.get(keyString[0])
															.toString());
											map.put(keyString[1],
													mAppList.get(position)
															.get(keyString[1])
															.toString());
											map.put(keyString[2],
													editString);
											map.put(keyString[3],
													mAppList.get(position)
															.get(keyString[3])
															.toString());

											mAppList.set(position, map);
											break;
										case R.id.value_UD:
											map.put(keyString[0],
													mAppList.get(position)
															.get(keyString[0])
															.toString());
											map.put(keyString[1],
													mAppList.get(position)
															.get(keyString[1])
															.toString());
											map.put(keyString[2],
													mAppList.get(position)
															.get(keyString[2])
															.toString());
											map.put(keyString[3],
													editString);

											mAppList.set(position, map);
											break;
										default:
											break;
										}

										notifyDataSetChanged();
									
										
									}

								}).setNegativeButton(R.string.CANCEL, null).show();
				return;

			}
			private void settingValueCopy7(View v,String oldString, String editString,int position,byte[] temp) {
               try{
				oldstrings[0]="";
				oldstrings[1]="";
				oldstrings[2]="";
				//��ֹ�����ı�Ϊ���ֶΣ����ֶ�Ϊ0��
				if(editString==""){
					editString="0";
				}
				switch (v.getId()) {
				case R.id.value_line:
						if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
						    if (position==3) {
						      System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 64, 2);
					        }
						    if (mAppList.get(3).get(keyString[1]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 64, 2);					
							}
							if (mAppList.get(3).get(keyString[2]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 68, 2);
								oldstrings[1]=oldString;
								oldstrings[2]=mAppList.get(3).get(keyString[3]).toString();
								//mapCopy7.put("productBA_sp_setting",oldString);
								//mapCopy7.put("productUD_sp_setting",mAppList.get(7).get(keyString[3]).toString());
	                        }
							if (mAppList.get(3).get(keyString[3]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 72, 2);
								//mapCopy7.put("productBA_sp_setting",mAppList.get(7).get(keyString[2]).toString());
								//mapCopy7.put("productUD_sp_setting",oldString);
								oldstrings[1]=mAppList.get(3).get(keyString[2]).toString();
								oldstrings[2]=oldString;
	                        }
							//mapCopy7.put("name_sp_setting",mAppList.get(7).get(keyString[0]).toString());
							//mapCopy7.put("foot_sp_setting",editString);
                            //mAppList.set(7, mapCopy7);	
						}else{
							if (position==3) {
							  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 64, 2);
							}
							if (mAppList.get(3).get(keyString[1]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 64, 2);					
							}
							if (mAppList.get(3).get(keyString[2]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 66, 2);
								//mapCopy7.put("productBA_sp_setting",oldString);
								//mapCopy7.put("productUD_sp_setting",mAppList.get(7).get(keyString[3]).toString());
								oldstrings[1]=oldString;
								oldstrings[2]=mAppList.get(3).get(keyString[3]).toString();
                            }
							if (mAppList.get(3).get(keyString[3]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 70, 2);
								//mapCopy7.put("productBA_sp_setting",mAppList.get(7).get(keyString[2]).toString());
								//mapCopy7.put("productUD_sp_setting",oldString);
								oldstrings[1]=mAppList.get(3).get(keyString[2]).toString();
								oldstrings[2]=oldString;
                            }
							//mapCopy7.put("name_sp_setting",mAppList.get(7).get(keyString[0]).toString());
							//mapCopy7.put("foot_sp_setting",editString);

							//mAppList.set(7, mapCopy7);
						}
					System.out.println("oldstrings[1]="+oldstrings[1]+"     oldstrings[2]="+oldstrings[2]);
					System.out.println("oldstrings[1]="+oldstrings[1]+"     oldstrings[2]="+oldstrings[2]);
					break;
				case R.id.value_BF:
					if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
					  if (position==3) {
						System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 68, 2);
					  } 
					  if (mAppList.get(3).get(keyString[1]).toString().equals(editString)) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 64, 2);					
							//mapCopy7.put("foot_sp_setting",oldString);
							//mapCopy7.put("productUD_sp_setting",mAppList.get(7).get(keyString[3]).toString());
							oldstrings[0]=oldString;
							oldstrings[2]=mAppList.get(3).get(keyString[3]).toString();
					  }
						if (mAppList.get(3).get(keyString[2]).toString().equals(editString)) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 68, 2);
                      }
						if (mAppList.get(3).get(keyString[3]).toString().equals(editString)) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 72, 2);
							//mapCopy7.put("foot_sp_setting",mAppList.get(7).get(keyString[1]).toString());
							//mapCopy7.put("productUD_sp_setting",oldString);
							oldstrings[0]=mAppList.get(3).get(keyString[1]).toString();
							oldstrings[2]=oldString;
						}
						//mapCopy7.put("name_sp_setting",mAppList.get(7).get(keyString[0]).toString());
						//mapCopy7.put("productBA_sp_setting",editString);

						//mAppList.set(7, mapCopy7);
					}else{
					  if (position==3) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 66, 2);
					  }
					  if (mAppList.get(3).get(keyString[1]).toString().equals(editString)) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 64, 2);					
							//mapCopy7.put("foot_sp_setting",oldString);
							//mapCopy7.put("productUD_sp_setting",mAppList.get(7).get(keyString[3]).toString());
							oldstrings[0]=oldString;
							oldstrings[2]=mAppList.get(3).get(keyString[3]).toString();
					  }
						if (mAppList.get(3).get(keyString[2]).toString().equals(editString)) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 66, 2);
                      }
						if (mAppList.get(3).get(keyString[3]).toString().equals(editString)) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 70, 2);
							//mapCopy7.put("foot_sp_setting",mAppList.get(7).get(keyString[1]).toString());
							//mapCopy7.put("productUD_sp_setting",oldString);
							oldstrings[0]=mAppList.get(3).get(keyString[1]).toString();
							oldstrings[2]=oldString;
						}
						//mapCopy7.put("name_sp_setting",mAppList.get(7).get(keyString[0]).toString());
						//mapCopy7.put("productBA_sp_setting",editString);

						//mAppList.set(7, mapCopy7);
					}
					System.out.println("oldstrings[0]="+oldstrings[0]+"     oldstrings[2]="+oldstrings[2]);
					break;
				case R.id.value_UD:
					
						if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
						  if (position==3) {
						    System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 72, 2);
					      }
						  if (mAppList.get(3).get(keyString[1]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 64, 2);					
								//mapCopy7.put("foot_sp_setting",oldString);
								//mapCopy7.put("productBA_sp_setting",mAppList.get(7).get(keyString[2]).toString());
								oldstrings[0]=oldString;
								oldstrings[1]=mAppList.get(3).get(keyString[2]).toString();
						  }
							if (mAppList.get(3).get(keyString[2]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 68, 2);
								//mapCopy7.put("foot_sp_setting",mAppList.get(7).get(keyString[1]).toString());
								//mapCopy7.put("productBA_sp_setting",oldString);
								oldstrings[0]=mAppList.get(3).get(keyString[1]).toString();
								oldstrings[1]=oldString;
							}
							if (mAppList.get(3).get(keyString[3]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 72, 2);
	                      }
							//mapCopy7.put("name_sp_setting",mAppList.get(7).get(keyString[0]).toString());
							//mapCopy7.put("productUD_sp_setting",editString);

							//mAppList.set(7, mapCopy7);
						}else {
						  if (position==3) {
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 70, 2);
						  }
						  if (mAppList.get(3).get(keyString[1]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 64, 2);					
								//mapCopy7.put("foot_sp_setting",oldString);
								//mapCopy7.put("productBA_sp_setting",mAppList.get(7).get(keyString[2]).toString());
								oldstrings[0]=oldString;
								oldstrings[1]=mAppList.get(3).get(keyString[2]).toString();
						  }
							if (mAppList.get(3).get(keyString[2]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 66, 2);
								//mapCopy7.put("foot_sp_setting",mAppList.get(7).get(keyString[1]).toString());
								//mapCopy7.put("productBA_sp_setting",oldString);
								oldstrings[0]=mAppList.get(3).get(keyString[1]).toString();
								oldstrings[1]=oldString;
							}
							if (mAppList.get(3).get(keyString[3]).toString().equals(editString)) {
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(oldString))), 0, temp, 70, 2);
                          }
							//mapCopy7.put("name_sp_setting",mAppList.get(7).get(keyString[0]).toString());
							//mapCopy7.put("productUD_sp_setting",editString);

							//mAppList.set(7, mapCopy7);
					    }
						System.out.println("oldstrings[0]="+oldstrings[0]+"     oldstrings[1]="+oldstrings[1]);
					break;
				default:
					break;
				}} catch (Exception e) {
					e.printStackTrace();
				}
			}
						private void settingValueCopy(View v, String editString,int position,byte[] temp) {
                         try{
							HashMap<String, Object> map = new HashMap<String, Object>();

							//��ֹ�����ı�Ϊ���ֶΣ����ֶ�Ϊ0��
							if(editString==""){
								editString="0";
							}

							switch (v.getId()) {
							case R.id.value_line:
									if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
									if(position==1){
									  System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 0, 4);
								    }else if (position==2) {
									  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 32, 2);
								    }else if (position==4) {
									  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 48, 2);
								    }else if (position==3) {
									  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 64, 2);
								    }
									}else{
										if(position==1){
											  System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 0, 4);
										    }else if (position==2) {
											  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 32, 2);
										    }else if (position==4) {
											  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 48, 2);
										    }else if (position==3) {
											  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 64, 2);
										    }
											}
								
								break;
							case R.id.value_BF:
								if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
								if(position==1){
									System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 8, 4);
								}else if (position==2) {
									System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 36, 2);
								}else if (position==4) {
									System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 52, 2);
								}else if (position==3) {
									System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 68, 2);
								}
								}else{
									if(position==1){
										System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 4, 4);
									}else if (position==2) {
										System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 34, 2);
									}else if (position==4) {
										System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 50, 2);
									}else if (position==3) {
										System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 66, 2);
									}
									}
								break;
							case R.id.value_UD:
								
									if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
									if(position==1){
									  System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 16, 4);
								    }else if (position==2) {
									  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 40, 2);
								    }else if (position==4) {
									  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 56, 2);
								    }else if (position==3) {
									  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 72, 2);
								    }
									}else {
										if(position==1){
											  System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 12, 4);
										    }else if (position==2) {
											  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 38, 2);
										    }else if (position==4) {
											  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 54, 2);
										    }else if (position==3) {
											  System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*1)), 0, temp, 70, 2);
										    }
											}
								
								break;
							default:
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						}
						
		}
		
		private void backMessageToDo(View v, String editString,int position,String keyString[],ArrayList<HashMap<String, Object>> mAppList) {

			try{
				if(tempposition==3){
					HashMap<String, Object> map = new HashMap<String, Object>();

					switch (v.getId()) {
					case R.id.value_line:
						map.put(keyString[0],
								mAppList.get(position)
										.get(keyString[0])
										.toString());
						map.put(keyString[1],
								editString);
					    map.put(keyString[2],oldstrings[1]);
						map.put(keyString[3],oldstrings[2]);
						mAppList.set(position, map);
						break;
					case R.id.value_BF:
						map.put(keyString[0],
								mAppList.get(position)
										.get(keyString[0])
										.toString());
						map.put(keyString[1],oldstrings[0]);
						map.put(keyString[2],editString);
						map.put(keyString[3],oldstrings[2]);

						mAppList.set(position, map);
						break;
					case R.id.value_UD:
						map.put(keyString[0],
								mAppList.get(position)
										.get(keyString[0])
										.toString());
						map.put(keyString[1],oldstrings[0]);
						map.put(keyString[2],oldstrings[1]);
						map.put(keyString[3],editString);

						mAppList.set(position, map);
						break;
					default:
						break;
					}
				}else{
			HashMap<String, Object> map = new HashMap<String, Object>();

			switch (v.getId()) {
			case R.id.value_line:
				map.put(keyString[0],
						mAppList.get(position)
								.get(keyString[0])
								.toString());
				map.put(keyString[1],
						editString);
				map.put(keyString[2],
						mAppList.get(position)
								.get(keyString[2])
								.toString());
				map.put(keyString[3],
						mAppList.get(position)
								.get(keyString[3])
								.toString());

				mAppList.set(position, map);
				break;
			case R.id.value_BF:
				map.put(keyString[0],
						mAppList.get(position)
								.get(keyString[0])
								.toString());
				map.put(keyString[1],
						mAppList.get(position)
								.get(keyString[1])
								.toString());
				map.put(keyString[2],
						editString);
				map.put(keyString[3],
						mAppList.get(position)
								.get(keyString[3])
								.toString());

				mAppList.set(position, map);
				break;
			case R.id.value_UD:
				map.put(keyString[0],
						mAppList.get(position)
								.get(keyString[0])
								.toString());
				map.put(keyString[1],
						mAppList.get(position)
								.get(keyString[1])
								.toString());
				map.put(keyString[2],
						mAppList.get(position)
								.get(keyString[2])
								.toString());
				map.put(keyString[3],
						editString);

				mAppList.set(position, map);
				break;
			default:
				break;
			}
				}
			notifyDataSetChanged();
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		
		//���������߳�,���������ȡ��λ�����ݳ�ʼ�����½�������Ϣ
		private Runnable backMessageToRunnable=new Runnable() {
			
			@Override
			public void run() {
				backMessageToDo(tempV,tempeditString,tempposition,keyString,mAppList);
	 			 
			}
			
		};
		}

	