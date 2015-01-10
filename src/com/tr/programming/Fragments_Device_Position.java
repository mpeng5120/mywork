package com.tr.programming;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import com.dbutils.ArrayListBound;
import com.tr.R;
import com.tr.programming.Fragments_Device_ActualInput.lockListener;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Fragments_Device_Position extends Fragment  {

	private ListView mListView;
	DecimalFormat format = new DecimalFormat("####");// ��ʽת����,0001�ĸ�ʽ����ʱ��ô���ˣ��Ժ��ҵ����õķ��������滻
	ArrayList<HashMap<String, Object>> ActualPositionList=ArrayListBound.getDevicePositionListData();
	public static  MyAdapter DevicePosition_Adapter = null;
	//������еķ����������﷨���
	private ArrayList<String> PositionList_simbolname=new ArrayList<String>();
	private boolean check=false;
	
	HashMap<Integer, Integer> PositionList=new HashMap<Integer, Integer>();
//	private AlarmQueryRunnable alarmQueryRunnable;
//	private ledRunnable ledrunnable;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ActualPositionList=ArrayListBound.getDevicePositionListData();
//		alarmQueryRunnable = new AlarmQueryRunnable(getActivity());
//		Thread a2 = new Thread(alarmQueryRunnable);
//		a2.start();
//		ledrunnable=new ledRunnable(getActivity());
//		Thread a4 = new Thread(ledrunnable);
//		a4.start();
	}
	@Override
	public void onPause(){
		super.onPause();
		//�뿪ҳ���ʱ����ӵ�״̬��ʾ�ı�

	}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        System.out.println("EidtFragment--->onCreate");
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
//        System.out.println("EidtFragment--->onCreateView");
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        return inflater.inflate(R.layout.tab_device_definition_3after, container, false);
    }
    
    @Override
 	public void onActivityCreated(Bundle savedInstanceState) {
 		super.onActivityCreated(savedInstanceState);// ���ø���ķ��������ϵͳ��һЩ����
try{
 		format.setMinimumIntegerDigits(4);// ��ʾ����4λ��
 		mListView = (ListView) getActivity().findViewById(R.id.list);
 		if(mListView==null){
			return;
		}
 		
 		//ʹ��final��ʵ�����Σ���֪����δ�����Ҫ����
 		DevicePosition_Adapter = new MyAdapter(getActivity(),
 				ActualPositionList,// ����Դ
 				R.layout.nc_device_definition_3after_item,// ListItem��XMLʵ��

 				// ��̬������ImageItem��Ӧ������
 				new String[] { "addressText", "symbolNameEditText","signalNameEditText", "noteEditText","setItem" },
 				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
 				new int[] { R.id.addressText, R.id.symbolNameEditText,R.id.signalNameEditText, R.id.noteEditText ,R.id.check});
 		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��

 		// ��Ӳ�����ʾ
 		mListView.setAdapter(DevicePosition_Adapter);
 		// ��ӵ��
 		mListView.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1,
 					final int position, long arg3) {
 				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
 					DevicePosition_Adapter.setSelectItem(position);
 					DevicePosition_Adapter.notifyDataSetChanged();
 					new AlertDialog.Builder(getActivity())
					.setTitle(R.string.programming_lineOperate)//�в���
//					.setItems(new String[] { getResources().getString(R.string.programming_insertLineUp), "ɾ������", "�������һ��" },
					.setItems(getResources().getStringArray(R.array.programming_array_lineOperate),//�в�������
							new DialogInterface.OnClickListener() { // ���ѡ��ĳ���ķ���
								@Override
								public void onClick(DialogInterface dialog,
										int which) { //
									switch (which) {
									case 0:// �������һ��,�Զ�ɾ�����һ�У����������̶�
										HashMap<String, Object> map1 = new HashMap<String, Object>();
										map1.put("addressText", String
												.format("%04d", position+1));//
										map1.put("symbolNameEditText", "");
										map1.put("signalNameEditText", "");
										map1.put("noteEditText", "");
										map1.put("setItem", "0");
										ActualPositionList.add(position, map1);
										// ���µ�ַ��
										for (int restposition = position + 1; restposition < ActualPositionList
												.size(); restposition++) {
											ActualPositionList.get(
													restposition).put(
													"addressText",
													String.format("%04d",
															restposition+1));
										}
										/*// �Զ�ɾ�����һ��
										ActualPositionList
												.remove(ActualPositionList
														.size() - 1);*/
										DevicePosition_Adapter
												.notifyDataSetChanged();
										Toast.makeText(getActivity(),
												"�����Ѳ���һ��",
												Toast.LENGTH_SHORT).show();
										break;

									case 1:// ɾ�����У�����Զ����һ�У����������̶�
										new AlertDialog.Builder(getActivity())
										.setTitle("ɾ������")
										.setMessage("ȷ��ɾ����ַΪ"+String.format("%04d", position)+"��һ���豸��")
										.setPositiveButton("ȷ��ɾ��",
												new OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog,int which) {
												ActualPositionList.remove(position);
												for (int restposition = position; restposition < ActualPositionList
														.size(); restposition++) {
													ActualPositionList.get(
															restposition).put(
															"addressText",
															String.format("%04d",
																	restposition+1));
												}
												/*// ����Զ����һ��
												HashMap<String, Object> mapadd1 = new HashMap<String, Object>();
												mapadd1.put("addressText", String
														.format("%04d",
																ActualPositionList
																		.size()+1));//
												mapadd1.put("symbolNameEditText",
														"");
												mapadd1.put("signalNameEditText",
														"");
												mapadd1.put("noteEditText", "");
												mapadd1.put("setItem", "0");
												ActualPositionList.add(mapadd1);*/

												DevicePosition_Adapter
														.notifyDataSetChanged();
												Toast.makeText(getActivity(),
														"��ɾ������", Toast.LENGTH_SHORT)
														.show();
                                              }
										}).setNegativeButton("ȡ��", null).show();
										break;
									case 2:// �������һ�У��Զ�ɾ�����һ�У����������̶�
										int nextposition = (position + 1);
										HashMap<String, Object> map2 = new HashMap<String, Object>();
										map2.put("addressText", String
												.format("%04d",
														nextposition+1));//
										map2.put("symbolNameEditText", "");
										map2.put("signalNameEditText", "");
										map2.put("noteEditText", "");
										map2.put("setItem", "0");
										ActualPositionList.add(nextposition,
												map2);

										for (int restposition = nextposition + 1; restposition < ActualPositionList
												.size(); restposition++) {
											ActualPositionList.get(
													restposition).put(
													"addressText",
													String.format("%04d",
															restposition+1));
										}
										/*// �Զ�ɾ�����һ��
										ActualPositionList
												.remove(ActualPositionList
														.size() - 1);*/
										DevicePosition_Adapter
												.notifyDataSetChanged();
										Toast.makeText(getActivity(),
												"�����Ѳ���һ��",
												Toast.LENGTH_SHORT).show();
										break;
									default:
										break;
									}
								}
							}).setNegativeButton("ȡ��", null).show();
 				}else {
					Toast.makeText(getActivity(), "��ǰ��������״̬", Toast.LENGTH_SHORT).show();
				}
 			}
 		});

}catch(Exception e){
	e.printStackTrace();
}
 	}



 	// ������Ҫ��listview�����ÿһ�и����ؼ�������Ӧ���������ʹ���Զ����listview�Ľӿ�
 	// �ڲ���
 	public class MyAdapter extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView addressText;
 			TextView symbolNameEditText;
 			TextView signalNameEditText;
 			TextView noteEditText;
 			CheckBox setItem;
 		}

 		private ArrayList<HashMap<String, Object>> mAppList;
 		private LayoutInflater mInflater;
 		private Context mContext;
 		private String[] keyString;
 		private int[] valueViewID;
 		private buttonViewHolder holder;
 		private int mLayoutID;
 		private int selectItem = -1;
 		// MyAdapter�Ĺ��캯��
 		public MyAdapter(Context c, ArrayList<HashMap<String, Object>> appList,int layoutID, String[] from, int[] to) {
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
 		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
		}
 		@Override
 		public View getView(int position, View convertView, ViewGroup parent) {
 			if (convertView != null) {
 				holder = (buttonViewHolder) convertView.getTag();
 			} else {
 				convertView = mInflater.inflate(mLayoutID,null);
 				holder = new buttonViewHolder();
 				holder.addressText = (TextView) convertView
 						.findViewById(valueViewID[0]);
 				holder.symbolNameEditText = (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.signalNameEditText=(TextView)convertView.findViewById(valueViewID[2]);
 				holder.noteEditText = (TextView) convertView
 						.findViewById(valueViewID[3]);
                holder.setItem=(CheckBox)convertView.findViewById(valueViewID[4]);
 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String addressText = (String) appInfo.get(keyString[0]);
 				String symbolNameEditText = (String) appInfo.get(keyString[1]);
 				String signalNameEditText=(String)appInfo.get(keyString[2]);
 				String noteEditText = (String) appInfo.get(keyString[3]);
 				String setItem=(String)appInfo.get(keyString[4]);

 				holder.addressText.setText(addressText);
 				holder.symbolNameEditText.setText(symbolNameEditText);
 				holder.signalNameEditText.setText(signalNameEditText);
 				holder.noteEditText.setText(noteEditText);
 				if(setItem.equals("1")){
 					holder.setItem.setChecked(true);
 				}else{
 					holder.setItem.setChecked(false);
 				}
 			// ѡ�к�ɫ��ʾ
				if (position == selectItem) {
					// �����ǰ���о���ListView��ѡ�е�һ�У��͸�����ʾ��ʽ
					convertView.setBackgroundColor(Color.RED);
				} else {
					convertView.setBackgroundColor(Color.BLACK);
				}

 				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
					holder.symbolNameEditText
							.setOnClickListener(new symbolNameListener(position));
					holder.signalNameEditText.setOnClickListener(new signalNameListener(position));
					holder.noteEditText.setOnClickListener(new notelistener(
							position));
					holder.setItem.setOnClickListener(new setItemListener(position));
				}else {
					holder.symbolNameEditText.setOnClickListener(new lockListener(getActivity()));
					holder.signalNameEditText.setOnClickListener(new lockListener(getActivity()));
					holder.noteEditText.setOnClickListener(new lockListener(getActivity()));
					holder.setItem.setOnClickListener(new setItemlockListener());
				}
 			}
 			return convertView;
 		}
 		class setItemListener implements android.view.View.OnClickListener {
 			private int position;
 			setItemListener(int pos){
 				position = pos;
 			}
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					setSelectItem(position);
					notifyDataSetChanged();
				if(mAppList.get(position)
						.get(keyString[4])
						.toString().equals("1")){
					mAppList.get(position).put("setItem", "0");
						notifyDataSetChanged();
				}else{
					mAppList.get(position).put("setItem", "1");
					notifyDataSetChanged();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			}
 		}
 		class setItemlockListener implements android.view.View.OnClickListener {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "��ǰ��������״̬", Toast.LENGTH_SHORT).show();
					notifyDataSetChanged();
			}
 		}
        
 		class symbolNameListener implements android.view.View.OnClickListener {
 			private int position;

 			symbolNameListener(int pos) {
 				position = pos;
 			}

 			
 			
 			
 			@Override
 			public void onClick(final View v) {
 				try{
 					setSelectItem(position);
 					notifyDataSetChanged();
 				//�ж��Զ�����һ��
 				if (position==(mAppList.size()-1)) {
 					addOneItem();	
 					notifyDataSetChanged();
 				}
 				
 				View posView=View.inflate(getActivity(), R.layout.device_position_symbolname,null );//��xml����������ֻ���������ֺ�λ��
 	 			final RadioGroup radioGroupPos=(RadioGroup) posView.findViewById(R.id.radioGroupPos);
 	 			RadioButton radioP=(RadioButton) posView.findViewById(R.id.radioP);
 	 			RadioButton radioSP=(RadioButton) posView.findViewById(R.id.radioSP);
 	 			RadioButton radioFP=(RadioButton) posView.findViewById(R.id.radioFP);
 				final EditText etEditText=(EditText) posView.findViewById(R.id.editText_pos);
 				if(radioGroupPos==null){return;}
 				if(radioP==null){return;}
 				if(radioSP==null){return;}
 				if(radioFP==null){return;}
 				if(etEditText==null){return;}
 				radioGroupPos.setOnCheckedChangeListener(new OnCheckedChangeListener() {								
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId==R.id.radioP) {
							etEditText.setHint("���뷶Χ1~200");
							etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
						}else if(checkedId==R.id.radioSP){
							etEditText.setHint("���뷶Χ1~8");
							etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
							etEditText.setKeyListener(new NumberKeyListener() {
			 				    @Override
			 				    protected char[] getAcceptedChars() {
			 				        return new char[] { '1', '2', '3', '4', '5', '6', '7', '8'};
			 				    }
			 				    @Override
			 				    public int getInputType() {
			 				        return android.text.InputType.TYPE_CLASS_PHONE;//���ּ���
			 				    }
			 				});
						}else{
							etEditText.setHint("���뷶Χ1~2");
							etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
							etEditText.setKeyListener(new NumberKeyListener() {
			 				    @Override
			 				    protected char[] getAcceptedChars() {
			 				        return new char[] { '1', '2'};
			 				    }
			 				    @Override
			 				    public int getInputType() {
			 				        return android.text.InputType.TYPE_CLASS_PHONE;//���ּ���
			 				    }
			 				});
						}
					}
				});
 				final String symbolNameString=(String) mAppList.get(position).get(keyString[1]);
 				if (symbolNameString.contains("SP")) {
					etEditText.setText(symbolNameString.replace("SP", ""));
					etEditText.setSelection(symbolNameString.replace("SP", "").length());//���ù��λ��	
					radioSP.setChecked(true);
				}else if (symbolNameString.contains("FP")) {
					etEditText.setText(symbolNameString.replace("FP", ""));
					etEditText.setSelection(symbolNameString.replace("FP", "").length());//���ù��λ��	
					radioFP.setChecked(true);
				}else if (symbolNameString.contains("P")) {
					etEditText.setText(symbolNameString.replace("P", ""));
					etEditText.setSelection(symbolNameString.replace("P", "").length());//���ù��λ��	
					radioP.setChecked(true);
				} 	
 				
 				new AlertDialog.Builder(getActivity())
 						.setTitle("����ӷ�����")
 						.setView(posView)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {
 									@Override
 									public void onClick(DialogInterface arg0,int arg1) {
 										// TODO �Զ����ɵķ������
 										String editString=etEditText.getText().toString().trim();
 										String pos="";
 										if (editString.length()!=0) {
 											if (radioGroupPos.getCheckedRadioButtonId()==R.id.radioP) {
 												int data = Integer.parseInt(editString);
												 if(data<1||data>200){
													 Toast.makeText(getActivity(), "���뷶Χ1~200", Toast.LENGTH_SHORT).show(); 
													 return;
												 }
 												pos="P";
 											}else if(radioGroupPos.getCheckedRadioButtonId()==R.id.radioSP) {
 												int data = Integer.parseInt(editString);
												 if(data<1||data>8){
													 Toast.makeText(getActivity(), "���뷶Χ1~8", Toast.LENGTH_SHORT).show(); 
													 return;
												 }
 												pos="SP";
 											}else {
 												int data = Integer.parseInt(editString);
												 if(data<1||data>2){
													 Toast.makeText(getActivity(), "���뷶Χ1~2", Toast.LENGTH_SHORT).show(); 
													 return;
												 }
 												pos="FP";
 											}
 											
											editString=pos+com.tr.programming.Fragments_NCedit1.addZeroForString(editString, 3);
											
 										}
 										if (!symbolNameString.equals(editString)) {
 											Fragments_Device_Timer.checkDevice4List(PositionList_simbolname, ArrayListBound.getDevicePositionListData());
 											
 											if (PositionList_simbolname.contains(editString)) {
 												editString="";
 												Toast.makeText(getActivity(), "��������������������������", Toast.LENGTH_SHORT).show();
 												return;
 											}
 										}
 								
											

 										HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("addressText",
 												mAppList.get(position)
 														.get(keyString[0])
 														.toString());
 										map.put("symbolNameEditText",editString);
 										map.put("signalNameEditText", 
 												mAppList.get(position)
												.get(keyString[2])
												.toString());
 										map.put("noteEditText", 
 												mAppList.get(position)
												.get(keyString[3])
												.toString());
 										map.put("setItem", 
 												mAppList.get(position)
												.get(keyString[4])
												.toString());
 										mAppList.set(position, map);
 										notifyDataSetChanged();
 									}

 								}).setNegativeButton(R.string.CANCEL, null)//ȡ��
 								.show();
 			}catch(Exception e){
 				e.printStackTrace();
 			}
 			}

 		}
 		class signalNameListener implements android.view.View.OnClickListener {

 			private int position;
 			EditText etEditText = new EditText(getActivity());
 			// ���캯��
 			signalNameListener(int pos) {
 				position = pos;
 			}

 			@Override
 			public void onClick(final View v) {
 				try{
 					setSelectItem(position);
 					notifyDataSetChanged();
 				etEditText.setHint("���Ƴ��Ȳ�����24���ַ�");
 				//�������볤��Ϊ20
 				etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24)});
 				final String signalNameString=(String) mAppList.get(position).get(keyString[2]);
			    etEditText.setText(signalNameString);
			    etEditText.setSelection(signalNameString.length());//���ù��λ��	
 				new AlertDialog.Builder(getActivity())
 						.setTitle("������ź���")
 						.setView(etEditText)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {

 									@Override
 									public void onClick(DialogInterface arg0,
 											int arg1) {
 										String editString=etEditText.getText().toString().trim();
 										try {
											byte[] signalbyte=editString.getBytes("gb2312");
											if(signalbyte.length>24){
												editString = "";
												Toast.makeText(getActivity(),
														"�ź������ȴ���24���ַ�������������",
														Toast.LENGTH_SHORT).show();
												return;
											}
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
 									/*	if (!signalNameString.equals(editString)) {
											Fragments_Device_ActualInput.checkDevice8List(DataInputList_simbolname,DataInputList_signalname, ArrayListBound.getDeviceDataInputListData());
											
											if (DataInputList_signalname.contains(editString)) {
												editString="";
												Toast.makeText(getActivity(), "��������������������������", Toast.LENGTH_SHORT).show();
											}
 										}
*/
 										HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("addressText",
 												mAppList.get(position)
 														.get(keyString[0])
 														.toString());//
 										map.put("symbolNameEditText",
 												mAppList.get(position)
 														.get(keyString[1])
														.toString());
 										map.put("signalNameEditText",editString);
 										map.put("noteEditText",
 												mAppList.get(position)
 														.get(keyString[3])
 														.toString());
 										map.put("setItem", 
 												mAppList.get(position)
												.get(keyString[4])
												.toString());
 										mAppList.set(position, map);
 										notifyDataSetChanged();
 									}

 								}).setNegativeButton(R.string.CANCEL, null)//ȡ��
 								.show();
 			}catch(Exception e){
 				e.printStackTrace();
 			}
 			}
 		}
 		class notelistener implements android.view.View.OnClickListener {

 			private int position;

 			// ���캯��
 			notelistener(int pos) {
 				position = pos;
 			}

 			@Override
 			public void onClick(final View v) {
 				try{
 					setSelectItem(position);
 					notifyDataSetChanged();
 				//�ж��Զ�����һ��
 				if (position==(mAppList.size()-1)) {
 					addOneItem();	
 					notifyDataSetChanged();
 				}
 				
 				final EditText etEditText = new EditText(getActivity());
 				etEditText.setHint("ע�ͳ��Ȳ�����80���ַ�");
 				//�������볤��Ϊ40
 				etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(80)});
 				
 				String noteString=(String) mAppList.get(position).get(keyString[3]);
			    etEditText.setText(noteString);
			    etEditText.setSelection(noteString.length());//���ù��λ��	
 				new AlertDialog.Builder(getActivity())
 						.setTitle("�����ע��")
 						.setView(etEditText)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {

 									@Override
 									public void onClick(DialogInterface arg0,
 											int arg1) {
 										String editString=etEditText.getText().toString().trim();
 										try {
											byte[] signalbyte=editString.getBytes("gb2312");
											if(signalbyte.length>80){
												editString = "";
												Toast.makeText(getActivity(),
														"ע�ͳ��ȴ���80���ַ�������������",
														Toast.LENGTH_SHORT).show();
												return;
											}
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
 										//�ų��Ƿ��ַ�"/"
 										if (editString.contains("/")) {
 											editString=editString.replace("/", "\\");
 											Toast.makeText(getActivity(), "/Ϊ�Ƿ��ַ������滻Ϊ\\", Toast.LENGTH_SHORT).show();
										}
 										HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("addressText",mAppList.get(position)
 														.get(keyString[0])
 														.toString());//
 										map.put("symbolNameEditText",mAppList.get(position)
													.get(keyString[1])
													.toString());
 										map.put("signalNameEditText", 
 												mAppList.get(position)
												.get(keyString[2])
												.toString());
 										map.put("noteEditText",editString);
 										map.put("setItem", 
 												mAppList.get(position)
												.get(keyString[4])
												.toString());
 										mAppList.set(position, map);
 										notifyDataSetChanged();
 									}

 								}).setNegativeButton(R.string.CANCEL, null)//ȡ��
 								.show();
 			}catch(Exception e){
 				e.printStackTrace();
 			}
 			}

 		}
 		private void addOneItem() {
 			try{
			HashMap<String, Object> mapadd = new HashMap<String, Object>();
			mapadd.put("addressText",	String.format("%04d", mAppList.size()+1));//
			mapadd.put("symbolNameEditText","");
			mapadd.put("signalNameEditText","");
			mapadd.put("noteEditText","");
			mapadd.put("setItem", "0");
			mAppList.add(mAppList.size(),mapadd);
 		}catch(Exception e){
			e.printStackTrace();
		}
		}

 	}




  
}