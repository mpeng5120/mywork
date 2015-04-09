package com.tr.programming;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import wifiRunnablesAndChatlistener.WatchRunnable;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.dbutils.ArrayListBound;
import com.tr.R;
import com.tr.programming.Fragments_Device_ActualInput.MyAdapter;
import com.tr.programming.Fragments_Device_ActualInput.lockListener;
import com.tr.programming.Fragments_Device_ActualInput.MyAdapter.notelistener;
import com.tr.programming.Fragments_Device_ActualInput.MyAdapter.signalNameListener;
import com.tr.programming.Fragments_Device_ActualInput.MyAdapter.symbolNameListener;


public class Fragments_Device_MiddleOutput extends Fragment  {

	private ListView mListView;
	DecimalFormat format = new DecimalFormat("####");// ��ʽת����,0001�ĸ�ʽ����ʱ��ô���ˣ��Ժ��ҵ����õķ��������滻

	ArrayList<HashMap<String, Object>> MiddleOutputList=ArrayListBound.getDeviceMiddleOutputListData();
	public static  MyAdapter MiddleOutput_Adapter = null;
	//�ֱ������еķ��������ź����������﷨���
	private ArrayList<String> MiddleOutputList_simbolname=new ArrayList<String>();
	private ArrayList<String> MiddleOutputList_signalname=new ArrayList<String>();
	private boolean check=false;
//	private AlarmQueryRunnable alarmQueryRunnable;
//	private ledRunnable ledrunnable;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MiddleOutputList=ArrayListBound.getDeviceMiddleOutputListData();
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
//		try{
//			alarmQueryRunnable.destroy();
//			ledrunnable.destroy();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
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
        System.out.println("EidtFragment--->onCreateView");
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        return inflater.inflate(R.layout.tab_device_definition_8before, container, false);
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
 		MiddleOutput_Adapter = new MyAdapter(getActivity(),
 				MiddleOutputList,// ����Դ
 				R.layout.nc_device_definition_8before_item,// ListItem��XMLʵ��

 				// ��̬������ImageItem��Ӧ������
 				new String[] { "addressText", "symbolNameEditText", "signalNameEditText", "noteEditText" },
 				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
 				new int[] { R.id.addressText, R.id.symbolNameEditText,R.id.signalNameEditText, R.id.noteEditText });
 		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��

 		// ��Ӳ�����ʾ
 		mListView.setAdapter(MiddleOutput_Adapter);
 		// ��ӵ��
 		mListView.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> arg0, View arg1,
 					final int position, long arg3) {
 				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
 					MiddleOutput_Adapter.setSelectItem(position);
 					MiddleOutput_Adapter.notifyDataSetChanged();
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
 				holder.signalNameEditText = (TextView) convertView
 						.findViewById(valueViewID[2]);
 				holder.noteEditText = (TextView) convertView
 						.findViewById(valueViewID[3]);

 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String numText = (String) appInfo.get(keyString[0]);
 				String symbolNameEditText = (String) appInfo.get(keyString[1]);
 				String signalNameEditText = (String) appInfo.get(keyString[2]);
 				String noteEditText = (String) appInfo.get(keyString[3]);

 				holder.addressText.setText(numText);
 				holder.symbolNameEditText.setText(symbolNameEditText);
 				holder.signalNameEditText.setText(signalNameEditText);
 				holder.noteEditText.setText(noteEditText);
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
					holder.signalNameEditText
							.setOnClickListener(new signalNameListener(position));
					holder.noteEditText.setOnClickListener(new notelistener(
							position));
				}else {
					holder.symbolNameEditText.setOnClickListener(new lockListener(getActivity()));
					holder.signalNameEditText.setOnClickListener(new lockListener(getActivity()));
					holder.noteEditText.setOnClickListener(new lockListener(getActivity()));
				}
 			}
 			return convertView;
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
 				final EditText etEditText = new EditText(getActivity());
 				etEditText.setHint("���ƿ��԰�����ĸ�����ֺ��»��ߣ����Ȳ�����20���ַ�");
 				//�������볤��Ϊ8
 				etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
 				//����ֻ�������ַ����ֺ��»���
 				etEditText.setKeyListener(new NumberKeyListener() {
 				    @Override
 				    protected char[] getAcceptedChars() {
 				        return new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
 				        					'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
 				        					'1', '2', '3', '4', '5', '6', '7', '8','9', '0' ,'_',};
 				        }
 				    
					@Override
					public int getInputType() {
						// TODO Auto-generated method stub
						return android.text.InputType.TYPE_CLASS_TEXT;
					}
 				    
 				});	
 				final String symbolNameString=(String) mAppList.get(position).get(keyString[1]);
			    etEditText.setText(symbolNameString.replace("MY", ""));
			    etEditText.setSelection(symbolNameString.replace("MY", "").length());//���ù��λ��	
 				new AlertDialog.Builder(getActivity())
 						.setTitle("������м����������")
 						.setView(etEditText)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {

 									@Override
 									public void onClick(DialogInterface arg0,int arg1) {
 										String editString=etEditText.getText().toString().trim();
 										if (editString.length()!=0) {
 											editString="MY"+editString;
										}
 										if (!symbolNameString.equals(editString)) {
 											Fragments_Device_ActualInput.checkDevice8List(MiddleOutputList_simbolname,MiddleOutputList_signalname, ArrayListBound.getDeviceMiddleOutputListData());
											
 											if (MiddleOutputList_simbolname.contains(editString)) {
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
 				etEditText.setHint("���Ƴ��Ȳ�����40���ַ�");
 				//�������볤��Ϊ20
 				etEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
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
											if(signalbyte.length>40){
												editString = "";
												Toast.makeText(getActivity(),
														"�ź������ȴ���40���ַ�������������",
														Toast.LENGTH_SHORT).show();
												return;
											}
										} catch (UnsupportedEncodingException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
 										if (!signalNameString.equals(editString)) {
 											Fragments_Device_ActualInput.checkDevice8List(MiddleOutputList_simbolname,MiddleOutputList_signalname, ArrayListBound.getDeviceMiddleOutputListData());
											
 											/*if (MiddleOutputList_signalname.contains(editString)) {
 												editString="";
 												Toast.makeText(getActivity(), "��������������������������", Toast.LENGTH_SHORT).show();
 											}*/
 										}
											
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
 										map.put("signalNameEditText",mAppList.get(position)
												.get(keyString[2]));
 										map.put("noteEditText",editString);
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

 	}










  
}