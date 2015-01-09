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
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
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

		public class FpAdapter extends BaseAdapter {
		
				private class buttonViewHolder {
					TextView name_sp;
					TextView line_fp_setting;
					TextView BA_fp_setting;
					TextView UD_fp_setting;
				}


			private ArrayList<HashMap<String, Object>> mAppList;
			private LayoutInflater mInflater;
			private Activity mContext;
			private String[] keyString;
			private int[] valueViewID;
			private buttonViewHolder holder;
			private int mLayoutID;
			private int mselectedItem = -1;
			private int id = -1;
			private  ToggleButton toggBtn;
			private ListView fSpListView;
			private int start_index=0;
			private static int selectedItem = -1;
			private int flagtemp=0;
			private int packCnt;//װ���趨��
			private View tempV;
			private int tempposition;
			private String tempeditString;
			private WifiSendDataFormat formatSendMessage;
			private SendDataRunnable sendDataRunnable;
			private FinishRunnable sendDataFinishRunnable;
			private int send_address_speed;
			private int send_address_pos;
			private String posname;
			//private int[] pspfpaxleFlag;
			// MyAdapter�Ĺ��캯��
			public FpAdapter(Activity c,ToggleButton v, ArrayList<HashMap<String, Object>> appList,
					 String[] from,ListView fSpListView)  {
				mAppList = appList;
				mContext = c;	
				toggBtn=v;
				if(mContext==null){
					return;
				}
				mInflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				keyString = new String[from.length];
				System.arraycopy(from, 0, keyString, 0, from.length);
				this.fSpListView=fSpListView;
				mLayoutID=R.layout.sfp_list_item;
				valueViewID=new int[] {R.id.sfp_name, R.id.value_line,
						R.id.value_BF,R.id.value_UD };
				send_address_speed=-1;
			}
			
			public FpAdapter(Activity c,ToggleButton v, ArrayList<HashMap<String, Object>> appList,
					 String[] from,ListView fSpListView,int send_address_speed,int packCnt,String posname)  {
				mAppList = appList;
				mContext = c;	
				toggBtn=v;
				if(mContext==null){
					return;
				}
				mInflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				keyString = new String[from.length];
				System.arraycopy(from, 0, keyString, 0, from.length);
				this.fSpListView=fSpListView;
				mLayoutID=R.layout.sfp_list_item;
				valueViewID=new int[] {R.id.sfp_name, R.id.value_line,
						R.id.value_BF,R.id.value_UD };
				this.packCnt=packCnt;
				this.send_address_speed=send_address_speed;
				if(send_address_speed==0x20003AB0){
	    			send_address_pos=0x200021B0;
	    		}
	    		if(send_address_speed==0x20003AC6){
	    			send_address_pos=0x20002E30;
	    		}
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

			// ɾ��ĳһ��
			public void removeItem(int position) {
				mAppList.remove(position);
				this.notifyDataSetChanged();
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
					holder.line_fp_setting = (TextView) convertView
							.findViewById(valueViewID[1]);
					holder.BA_fp_setting = (TextView) convertView
							.findViewById(valueViewID[2]);
					holder.UD_fp_setting = (TextView) convertView
							.findViewById(valueViewID[3]);
					convertView.setTag(holder);
				}

				HashMap<String, Object> appInfo = mAppList.get(position);
				if (appInfo != null) {
					String name_sp =  appInfo.get(keyString[0]).toString();
					Log.d("mpeng","the keyString 1 " +keyString[1]+"pos is"+position);
					String line_fp_setting =  appInfo.get(keyString[1]).toString();
					String BA_fp_setting =  appInfo
							.get(keyString[2]).toString();
					String UD_fp_setting =  appInfo
							.get(keyString[3]).toString();

					holder.name_sp.setText(name_sp);
					holder.line_fp_setting.setText(line_fp_setting);
					holder.BA_fp_setting.setText(BA_fp_setting);
					holder.UD_fp_setting.setText(UD_fp_setting);

					/**
					 * �̰���Ӧ
					 */
					
					if (position == 0) {
						System.out.println("fp positionqueryrunnable");
						int tmp = 0;
						if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
							tmp = Define.POSITION_MATERIAL;
						}else{
							tmp = Define.POSITION_GOODS;
						}
						if (!NewPragramActivity.positionQueryRunnable.existFlag){ //��һ����ʾʱ�����̣߳������������߳��޹�
							NewPragramActivity.positionQueryRunnable = new PositionQueryRunnable(
									mContext,holder.line_fp_setting,holder.BA_fp_setting,holder.UD_fp_setting,tmp);
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
												mContext,holder.line_fp_setting,holder.BA_fp_setting,holder.UD_fp_setting,tmp);
										Thread a1=new Thread(NewPragramActivity.positionQueryRunnable);
										a1.start();
									}
								}
							}
						});

					}


					if(position!=0)
					{
					   holder.line_fp_setting.setOnClickListener(new FPListener(position));
			           holder.BA_fp_setting.setOnClickListener(new FPListener(position));
					   holder.UD_fp_setting.setOnClickListener(new FPListener(position));
					}
					/**
					 * ���ñ���ɫ������ѡ������ɫ��ʾ,������ѡ����
					 */
					holder.name_sp.setBackgroundColor(Color.GRAY);
					
					if (position == 0) {
						holder.line_fp_setting.setBackgroundColor(Color.GRAY);
						holder.BA_fp_setting.setBackgroundColor(Color.GRAY);
						holder.UD_fp_setting.setBackgroundColor(Color.GRAY);
					}else if (position == mselectedItem) {// ѡ����ɫ��ʾ
//							if (id == selectAll) {
//								convertView.setBackgroundColor(Color.BLUE);
//							} else {
//								convertView.setBackgroundColor(Color.BLACK);
								convertView.findViewById(id).setBackgroundColor(
										Color.BLUE);
								for (int i = 1; i < valueViewID.length; i++) {
									if (id != valueViewID[i]) {
										convertView.findViewById(valueViewID[i])
												.setBackgroundColor(Color.WHITE);
									}
								}
//							}
						notifyDataSetChanged();
					} else {
						holder.line_fp_setting.setBackgroundColor(Color.WHITE);
						holder.BA_fp_setting.setBackgroundColor(Color.WHITE);
						holder.UD_fp_setting.setBackgroundColor(Color.WHITE);
						convertView.setBackgroundColor(Color.BLACK);
					}

					/*if (position ==1) {
						if (toggBtn.isChecked()) {// ѡ��Ϊ�ϵ���
							if(pspfpaxleFlag[0]==1){
								holder.line_fp_setting.setBackgroundColor(Color.WHITE);
							}else{
								holder.line_fp_setting.setBackgroundColor(Color.GRAY);
							}
							
							if(pspfpaxleFlag[2]==1){
								holder.BA_fp_setting.setBackgroundColor(Color.WHITE);
							}else{
								holder.BA_fp_setting.setBackgroundColor(Color.GRAY);
							}
							
							if(pspfpaxleFlag[4]==1){
								holder.UD_fp_setting.setBackgroundColor(Color.WHITE);
							}else{
								holder.UD_fp_setting.setBackgroundColor(Color.GRAY);
							}
							
						}else{
							if(pspfpaxleFlag[0]==1){
								holder.line_fp_setting.setBackgroundColor(Color.WHITE);
							}else{
								holder.line_fp_setting.setBackgroundColor(Color.GRAY);
							}
							
							if(pspfpaxleFlag[1]==1){
								holder.BA_fp_setting.setBackgroundColor(Color.WHITE);
							}else{
								holder.BA_fp_setting.setBackgroundColor(Color.GRAY);
							}
							
							if(pspfpaxleFlag[3]==1){
								holder.UD_fp_setting.setBackgroundColor(Color.WHITE);
							}else{
								holder.UD_fp_setting.setBackgroundColor(Color.GRAY);
							}
						}
					}*/
				}

				return convertView;
			}

			/**
			 * �̰���Ӧ�࣬ʵ�ָı䱳��ɫ���ı�seekbar��ֵ����
			 * 
			 * @author shea
			 * 
			 */

			class FPListener implements android.view.View.OnClickListener {
				private int position;

				FPListener(int pos) {
					position = pos;
				}

				@Override
					public void onClick(final View v) {
					final EditText etEditText = new EditText(mContext);
					setSelectItemBlue(position, v.getId());
					selectedItem = position;
					notifyDataSetChanged();
					switch (position) {
					case 1://����ֵ
						etEditText.setHint("��ֵ��Χ-327.6~327.6");
						etEditText.setKeyListener(new NumberKeyListener() {
							@Override
							protected char[] getAcceptedChars() {
								return new char[] { '1', '2', '3', '4', '5', '6', '7',
										'8', '9', '0', '.','+','-'};
							}

							@Override
							public int getInputType() {
								return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
							}
						});
						break;
					default:// ����λ�ã�����������С��
						etEditText.setHint("֧�ָ�ʽΪ#####.#�����������������5λ��С�����1λ");
						etEditText.setKeyListener(new NumberKeyListener() {
							@Override
							protected char[] getAcceptedChars() {
								return new char[] { '1', '2', '3', '4', '5', '6', '7',
										'8', '9', '0', '.','+','-'};
							}

							@Override
							public int getInputType() {
								return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
							}
						});
						break;
					}
					String foot_String = "";
					switch (v.getId()) {
					case R.id.value_line:
						foot_String =  mAppList.get(position).get(
								keyString[1]).toString();
						break;
					case R.id.value_BF:
						foot_String = mAppList.get(position).get(
								keyString[2]).toString();
						break;
					case R.id.value_UD:
						foot_String = mAppList.get(position).get(
								keyString[3]).toString();
						break;
					default:
						break;
					}
					// ��ʼ���������������趨ֵ
					TextView t = (TextView) v;
					String valueString = t.getText().toString();
					etEditText.setText(foot_String);
				    etEditText.setSelection(foot_String.length());//���ù��λ��	
					new AlertDialog.Builder(mContext)
							.setTitle("������趨ֵ")
							.setView(etEditText)
							.setPositiveButton(R.string.OK,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface arg0,int arg1) {
											String editString = etEditText.getText().toString().trim();
											double editDouble=0;
											if (!editString.equals("")) {
												switch (position) {
												case 1:// ����ֵ
													if (!editString.startsWith(".")) {
														if (editString.contains(".")) {
															String[] editStrings = editString.split("\\.");
															if (2 == editStrings.length) {
																editDouble = Double.parseDouble(editString);
																if (Math.abs(editDouble) >= 327.6) {
																	Toast.makeText(mContext,"���ݳ�����Χ������������",Toast.LENGTH_SHORT).show();
																	editString = "";
																	return;
																} else {
																	editString = String.format("%1$5.1f",editDouble);
																}
															} else {
																Toast.makeText(mContext,"���ݸ�ʽ��������������",Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															}
														} else {
															editDouble = Double.parseDouble(editString);
															if (Math.abs(editDouble) >= 327.6) {
																Toast.makeText(mContext,"���ݳ�����Χ������������",Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															} else {
																editString = String.format("%1$5.1f",editDouble);
															}
														}

													} else {
														Toast.makeText(mContext,"���ݸ�ʽ��������������",Toast.LENGTH_SHORT).show();
														editString = "";
														return;
													}
													break;
												default:// ����λ�ã�С��
													flagtemp=1;
													if (!editString.startsWith(".")) {
														if (editString.contains(".")) {
															String[] editStrings = editString.split("\\.");
															if (2 == editStrings.length) {
																editDouble = Double.parseDouble(editString);
																if (Math.abs(editDouble) >= 100000) {
																	Toast.makeText(mContext,"���ݳ�����Χ������������",Toast.LENGTH_SHORT).show();
																	editString = "";
																	return;
																} else {
																	editString = String.format("%1$5.1f",editDouble);
																}
															} else {
																Toast.makeText(mContext,"���ݸ�ʽ��������������",Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															}
														} else {
															editDouble = Double.parseDouble(editString);
															if (Math.abs(editDouble) >= 100000) {
																Toast.makeText(mContext,"���ݳ�����Χ������������",Toast.LENGTH_SHORT).show();
																editString = "";
																return;
															} else {
																editString = String.format("%1$5.1f",editDouble);
															}
														}

													} else {
														Toast.makeText(mContext,"���ݸ�ʽ��������������",Toast.LENGTH_SHORT).show();
														editString = "";
														return;
													}
													
													//stapackCnt=Integer.parseInt(packCnt.getString("cnt", ""));
													break;
												}
												
											} else {
												Toast.makeText(mContext,"����Ϊ�գ�����������",Toast.LENGTH_SHORT).show();
												editString = "";
												return;
											}
											if(send_address_speed!=-1){
											//===================================		
											String valueString=editString;
											//��ȡ�趨�ٶȣ����ٶȣ����ٶ���Ϣ,��д����
										
											byte[] temp=new byte[8*2+6];
											byte[] temppos=new byte[4*packCnt*8];
											//�ѽ����ϵ�ֵ��һ�飬����8���ֽڵ��ֽ�����
											try {//λ��
												if (toggBtn.isChecked()) {
													for(int i=2;i<2+packCnt;i++){
														if (!mAppList.get(i).get(keyString[1]).toString().equals("")) {
															System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(i).get(keyString[1]).toString())*100)), 0, temppos, 0+(i-2)*32, 4);					
														}
														if (!mAppList.get(i).get(keyString[2]).toString().equals("")) {
															System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(i).get(keyString[2]).toString())*100)), 0, temppos, 8+(i-2)*32, 4);

														}
														if (!mAppList.get(i).get(keyString[3]).toString().equals("")) {
															System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(i).get(keyString[3]).toString())*100)), 0, temppos, 16+(i-2)*32, 4);
														}
														}
														//����ֵ
														if (!mAppList.get(1).get(keyString[1]).toString().equals("")) {
															System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(1).get(keyString[1]).toString())*100)), 0, temp, 0, 2);					
														}
														if (!mAppList.get(1).get(keyString[2]).toString().equals("")) {
															System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(1).get(keyString[2]).toString())*100)), 0, temp, 4, 2);

														}
														if (!mAppList.get(1).get(keyString[3]).toString().equals("")) {
															System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(1).get(keyString[3]).toString())*100)), 0, temp, 8, 2);

														}
												}else{
													for(int i=2;i<2+packCnt;i++){
													if (!mAppList.get(i).get(keyString[1]).toString().equals("")) {
														System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(i).get(keyString[1]).toString())*100)), 0, temppos, 0+(i-2)*32, 4);					
													}
													if (!mAppList.get(i).get(keyString[2]).toString().equals("")) {
														System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(i).get(keyString[2]).toString())*100)), 0, temppos, 4+(i-2)*32, 4);

													}
													if (!mAppList.get(i).get(keyString[3]).toString().equals("")) {
														System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(mAppList.get(i).get(keyString[3]).toString())*100)), 0, temppos, 12+(i-2)*32, 4);
													}
													}
													//����ֵ
													if (!mAppList.get(1).get(keyString[1]).toString().equals("")) {
														System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(1).get(keyString[1]).toString())*100)), 0, temp, 0, 2);					
													}
													if (!mAppList.get(1).get(keyString[2]).toString().equals("")) {
														System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(1).get(keyString[2]).toString())*100)), 0, temp, 2, 2);

													}
													if (!mAppList.get(1).get(keyString[3]).toString().equals("")) {
														System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(mAppList.get(1).get(keyString[3]).toString())*100)), 0, temp, 6, 2);

													}
												}
												
												System.arraycopy(HexDecoding.int2byte(1), 0, temp, 21, 1);
												//�����趨ֵ�޸�����
												tempV=v;
												tempeditString=editString;
												tempposition=FPListener.this.position;	
												 if(flagtemp==1){
													flagtemp=0;
												    settingValueCopy(v, editString, position, temppos);
												    formatSendMessage=new WifiSendDataFormat(temppos, send_address_pos);
			                                        try {
			                                        	sendDataRunnable=new SendDataRunnable(formatSendMessage, mContext);

														sendDataFinishRunnable=new FinishRunnable(mContext, "���ݷ������",backMessageToDo);

														sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

														mContext.runOnUiThread(sendDataRunnable);
														} catch (Exception e) {
														// TODO: handle exception
														e.printStackTrace();
														//Toast.makeText(getActivity(), "������������", Toast.LENGTH_LONG).show();
													    }

												    
												 }else{
													 settingValueCopy(v, editString, position, temp);
													//type�ݶ�Ϊ1,���ڹ���Ҫ�Ľ���
													 
													 formatSendMessage=new WifiSendDataFormat(temp, send_address_speed);
													 try {
														    sendDataRunnable=new SendDataRunnable(formatSendMessage, mContext);

															sendDataFinishRunnable=new FinishRunnable(mContext, "���ݷ������",backMessageToDo);

															sendDataRunnable.setOnlistener(new NormalChatListenner(sendDataRunnable, sendDataFinishRunnable));

															mContext.runOnUiThread(sendDataRunnable);
														  } catch (Exception e) {
															// TODO: handle exception
															e.printStackTrace();
															//Toast.makeText(getActivity(), "������������", Toast.LENGTH_LONG).show();
														}

												 }
												}catch (Exception e) {
												// TODO: handle exception
												}
											//==================================
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
				private void settingValueCopy(View v, String editString,int position,byte[] temp) {
	              try{
					HashMap<String, Object> map = new HashMap<String, Object>();
					
					//��ֹ�����ı�Ϊ���ֶΣ����ֶ�Ϊ0��
					if(editString==""){
						editString="0";
					}

					switch (v.getId()) {
					case R.id.value_line:
						if(position==1){
							System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 0, 2);
						}else{
							if (toggBtn.isChecked()) {
							for(int i=2;i<2+packCnt;i++){
								if(position==i){
									System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 0+(i-2)*32, 4);
								}
							 }
							}else{
								for(int i=2;i<2+packCnt;i++){
									if(position==i){
										System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 0+(i-2)*32, 4);
									}
								 }
								}
						}
						break;
					case R.id.value_BF:
						if(position==1){
							if (toggBtn.isChecked()) {
							    System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,4, 2);
					        }else{
					        	System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp,2, 2);
					        }
						}else{
							if (toggBtn.isChecked()) {
							for(int i=2;i<2+packCnt;i++){
								if(position==i){
									System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 8+(i-2)*32, 4);
								}
							 }
							}else{
								for(int i=2;i<2+packCnt;i++){
									if(position==i){
										System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 4+(i-2)*32, 4);
									}
								 }
								}
						}
						break;
					case R.id.value_UD:
						if(position==1){
							if (toggBtn.isChecked()) {
							    System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 8, 2);
							}else{
								System.arraycopy(HexDecoding.int2byteArray2((int)(Float.parseFloat(editString)*100)), 0, temp, 6, 2);
							}
						}else{
							if (toggBtn.isChecked()) {
							for(int i=2;i<2+packCnt;i++){
								if(position==i){
									System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 16+(i-2)*32, 4);
								}
							 }
							}else{
								for(int i=2;i<2+packCnt;i++){
									if(position==i){
										System.arraycopy(HexDecoding.int2byteArray4((int)(Float.parseFloat(editString)*100)), 0, temp, 12+(i-2)*32, 4);
									}
								 }
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
			/**
			 * ���ܵ���λ�����ص���ȷ���ݺ�Ÿ������ݵķ�����������λ������λ����ͬ��������
			 * @param v
			 * @param editString
			 * @param position
			 * @param keyString
			 * @param mAppList
			 */
			private void backMessageToDo(View v, String editString,int position,String keyString[],ArrayList<HashMap<String, Object>> mAppList) {
		       try{
				HashMap<String, Object> map = new HashMap<String, Object>();

				switch (v.getId()) {
				case R.id.value_line:
					map.put(keyString[0],
							mAppList.get(position).get(keyString[0]).toString());
					map.put(keyString[1], editString);
					map.put(keyString[2],
							mAppList.get(position).get(keyString[2]).toString());
					map.put(keyString[3],
							mAppList.get(position).get(keyString[3]).toString());

					mAppList.set(position, map);
					break;
				case R.id.value_BF:
					map.put(keyString[0],
							mAppList.get(position).get(keyString[0]).toString());
					map.put(keyString[1],
							mAppList.get(position).get(keyString[1]).toString());
					map.put(keyString[2], editString);
					map.put(keyString[3],
							mAppList.get(position).get(keyString[3]).toString());

					mAppList.set(position, map);
					break;
				case R.id.value_UD:
					map.put(keyString[0],
							mAppList.get(position).get(keyString[0]).toString());
					map.put(keyString[1],
							mAppList.get(position).get(keyString[1]).toString());
					map.put(keyString[2],
							mAppList.get(position).get(keyString[2]).toString());
					map.put(keyString[3], editString);
					
					mAppList.set(position, map);
					break;

				default:
					break;
				}
			
				notifyDataSetChanged();
			}catch(Exception e){
				e.printStackTrace();
			}
			}

			private final Runnable backMessageToDo = new Runnable(){
				@Override
				public void run() {	
					
					backMessageToDo(tempV,tempeditString,tempposition,keyString,mAppList);
					
				}
			};

		}

	