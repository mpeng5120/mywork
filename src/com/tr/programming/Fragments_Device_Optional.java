
package com.tr.programming;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import wifiRunnablesAndChatlistener.AlarmQueryRunnable;
import wifiRunnablesAndChatlistener.ledRunnable;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.dbutils.ArrayListBound;
import com.explain.TableToBinary;
import com.tr.R;
import com.tr.programming.Fragments_Device_ActualInput.lockListener;


public class Fragments_Device_Optional extends Fragment  {

	private ListView mListView;
	DecimalFormat format = new DecimalFormat("####");// ��ʽת����,0001�ĸ�ʽ����ʱ��ô���ˣ��Ժ��ҵ����õķ��������滻
	ArrayList<HashMap<String, Object>> OptionalList=ArrayListBound.getDeviceOptionalListData();
	public static  MyAdapter DeviceOptional_Adapter = null;
	//������еķ����������﷨���
	private ArrayList<String> OptionalList_name=new ArrayList<String>();
	private boolean check=false;

	private static ArrayList<String> ActualInputList_symbol = new ArrayList<String>();// ��Ҫ��ʼ������Ȼ��ָ�����
	private static ArrayList<String> ActualOutputList_symbol = new ArrayList<String>();
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		OptionalList=ArrayListBound.getDeviceOptionalListData();
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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        return inflater.inflate(R.layout.tab_device_definition_optional, container, false);
    }
    
    @Override
 	public void onActivityCreated(Bundle savedInstanceState) {
 		super.onActivityCreated(savedInstanceState);// ���ø���ķ��������ϵͳ��һЩ����
try{
	initialList_Table(ActualInputList_symbol,
			ArrayListBound.getDeviceActualInputListData());
	initialList_Table(ActualOutputList_symbol,
			ArrayListBound.getDeviceActualOutputListData());
 		format.setMinimumIntegerDigits(4);// ��ʾ����4λ��

 		mListView = (ListView) getActivity().findViewById(R.id.list);
 		if(mListView==null){
			return;
		}
 		//ʹ��final��ʵ�����Σ���֪����δ�����Ҫ����
 		DeviceOptional_Adapter = new MyAdapter(getActivity(),
 				OptionalList,// ����Դ
 				R.layout.nc_device_definition_optional_item,// ListItem��XMLʵ��

 				// ��̬������ImageItem��Ӧ������
 				new String[] { "addressText", "name1", "name2", "name3" },
 				// ImageItem��XML�ļ������һ��ImageView,����TextView ID
 				new int[] { R.id.addressText, R.id.name1, R.id.name2, R.id.name3});
 		// ������������Item�Ͷ�̬�����Ӧ��Ԫ��

 		// ��Ӳ�����ʾ
 		mListView.setAdapter(DeviceOptional_Adapter);
 	// ��ӵ��
 			mListView.setOnItemClickListener(new OnItemClickListener() {
 				@Override
 				public void onItemClick(AdapterView<?> arg0, View arg1,
 						final int position, long arg3) {
 					if (TR_Programming_Activity.alreadyChecked_programmingPassword) {

 						// ѡ�к�ɫ��ʾ
 						DeviceOptional_Adapter.setSelectItem(position);
 						DeviceOptional_Adapter.notifyDataSetChanged();

 					}else {
 						Toast.makeText(getActivity(), "��ǰ��������״̬", Toast.LENGTH_SHORT).show();
 					}

 				}

 			});
}catch(Exception e){
	e.printStackTrace();
}
 	}

	private void initialList_Table(ArrayList<String> symbolList,
			ArrayList<HashMap<String, Object>> arrayList) {
		try{
		symbolList.clear();
		for (int i = 0; i < arrayList.size(); i++) {
			String symbolstr = (String) arrayList.get(i).get(
					"symbolNameEditText");
			if (!symbolstr.equals("")) {
				symbolList.add(symbolstr);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

 	// ������Ҫ��listview�����ÿһ�и����ؼ�������Ӧ���������ʹ���Զ����listview�Ľӿ�
 	// �ڲ���
 	public class MyAdapter extends BaseAdapter {
 		private class buttonViewHolder {
 			TextView addressText;
 			TextView name1;
 			TextView name2;
 			TextView name3;
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

 		private void setSelectItem(int selectItem) {
			mselectItem = selectItem;
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
 				holder.name1= (TextView) convertView
 						.findViewById(valueViewID[1]);
 				holder.name2= (TextView) convertView
 						.findViewById(valueViewID[2]);
 				holder.name3= (TextView) convertView
 						.findViewById(valueViewID[3]);
 				convertView.setTag(holder);
 			}

 			HashMap<String, Object> appInfo = mAppList.get(position);
 			if (appInfo != null) {
 				String addressText = (String) appInfo.get(keyString[0]);
 				String name1 = (String) appInfo.get(keyString[1]);
 				String name2 = (String) appInfo.get(keyString[2]);
 				String name3 = (String) appInfo.get(keyString[3]);
 				holder.addressText.setText(addressText);
 				holder.name1.setText(name1);
 				holder.name2.setText(name2);
 				holder.name3.setText(name3);

 				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
					holder.name1.setOnClickListener(new nameListener(position));
					holder.name2.setOnClickListener(new inameListener(position));
					holder.name3.setOnClickListener(new onameListener(position));
				}else {
					holder.name1.setOnClickListener(new lockListener(getActivity()));
					holder.name2.setOnClickListener(new lockListener(getActivity()));
					holder.name3.setOnClickListener(new lockListener(getActivity()));
				}
 			}
 			// ѡ�к�ɫ��ʾ
			if (position == mselectItem) {
				// �����ǰ���о���ListView��ѡ�е�һ�У��͸�����ʾ��ʽ
				convertView.setBackgroundColor(Color.RED);
			} else {
				convertView.setBackgroundColor(Color.BLACK);
			}
 			return convertView;
 		}

 		class nameListener implements android.view.View.OnClickListener {
 			private int position;
 			String nameString="";
 			nameListener(int pos) {
 				position = pos;
 			}

 			@Override
 			public void onClick(final View v) {
 			// ѡ�к�ɫ��ʾ
 				try{
				setSelectItem(position);
				notifyDataSetChanged();
				
 				final EditText etEditText = new EditText(getActivity());
 				nameString="";
					etEditText.setHint("keycode="+(position+23));
					nameString =  mAppList.get(position).get(
							keyString[1]).toString().trim();
			    etEditText.setText(nameString);
			    etEditText.setSelection(nameString.length());//���ù��λ��	
 				
 				new AlertDialog.Builder(getActivity())
 						.setTitle("���������")
 						.setView(etEditText)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {
 									@Override
 									public void onClick(DialogInterface arg0,int arg1) {
 										String editString=etEditText.getText().toString().trim();
 											try {
 												byte[] signalbyte=editString.getBytes("gb2312");
 												if(signalbyte.length>16){
 													editString = "";
 													Toast.makeText(getActivity(),
 															"�ź������ȴ���16���ַ�������������",
 															Toast.LENGTH_SHORT).show();
 													return;
 												}
 											} catch (UnsupportedEncodingException e) {
 												// TODO Auto-generated catch block
 												e.printStackTrace();
 											}
 	 										if (!nameString.equals(editString)) {
 	 											checkOptionalList(OptionalList_name, ArrayListBound.getDeviceOptionalListData());
 	 											
 	 											if (OptionalList_name.contains(editString)) {
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
 											map.put("name1",editString);
 	 										map.put("name2", 
 	 												mAppList.get(position)
 													.get(keyString[2])
 													.toString());
 	 										map.put("name3", 
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
 		class inameListener implements android.view.View.OnClickListener {
 			private int position;
 			String nameString="";
 			inameListener(int pos) {
 				position = pos;
 			}

 			@Override
 			public void onClick(final View v) {
 			// ѡ�к�ɫ��ʾ
 				try{
				setSelectItem(position);
				notifyDataSetChanged();
				
				final MultiAutoCompleteTextView etEditText = new MultiAutoCompleteTextView(
						getActivity());// ��ֵ�Զ���������
				etEditText.setHint("�������豸�����е������й���");
				// etEditText.setTokenizer(new
				// MultiAutoCompleteTextView.CommaTokenizer());//���ö���Ϊ�ָ���
				etEditText.setTokenizer(new SemicolonTokenizer(' '));// ���ÿո�Ϊ�ָ���
				etEditText.setCompletionHint("��ѡ��һ��");
				etEditText.setThreshold(1);// ����һ���ַ��Ϳ�ʼ��⣬��������ѡ��
				etEditText.setDropDownHeight(260);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						ActualInputList_symbol);
				etEditText.setAdapter(adapter);
					nameString =  mAppList.get(position).get(
							keyString[2]).toString().trim();
			    etEditText.setText(nameString);
			    etEditText.setSelection(nameString.length());//���ù��λ��
 				new AlertDialog.Builder(getActivity())
 						.setTitle("���������")
 						.setView(etEditText)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {
 									@Override
 									public void onClick(DialogInterface arg0,int arg1) {
 										String editString=etEditText.getText().toString().trim();
 											try {
 												byte[] signalbyte=editString.getBytes("gb2312");
 												if(signalbyte.length>8){
 													editString = "";
 													Toast.makeText(getActivity(),
 															"���������ȴ���8���ַ�������������",
 															Toast.LENGTH_SHORT).show();
 													return;
 												}
 											} catch (UnsupportedEncodingException e) {
 												// TODO Auto-generated catch block
 												e.printStackTrace();
 											}
 										HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("addressText",
 												mAppList.get(position)
 														.get(keyString[0])
 														.toString());
 											map.put("name2",editString);
 	 										map.put("name1", 
 	 												mAppList.get(position)
 													.get(keyString[1])
 													.toString());
 	 										map.put("name3", 
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
 		class onameListener implements android.view.View.OnClickListener {
 			private int position;
 			String nameString="";
 			onameListener(int pos) {
 				position = pos;
 			}

 			@Override
 			public void onClick(final View v) {
 			// ѡ�к�ɫ��ʾ
 				try{
				setSelectItem(position);
				notifyDataSetChanged();
				
				final MultiAutoCompleteTextView etEditText = new MultiAutoCompleteTextView(
						getActivity());// ��ֵ�Զ���������
				etEditText.setHint("�������豸�����е�����й���");
				// etEditText.setTokenizer(new
				// MultiAutoCompleteTextView.CommaTokenizer());//���ö���Ϊ�ָ���
				etEditText.setTokenizer(new SemicolonTokenizer(' '));// ���ÿո�Ϊ�ָ���
				etEditText.setCompletionHint("��ѡ��һ��");
				etEditText.setThreshold(1);// ����һ���ַ��Ϳ�ʼ��⣬��������ѡ��
				etEditText.setDropDownHeight(260);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						ActualOutputList_symbol);
				etEditText.setAdapter(adapter);
					nameString =  mAppList.get(position).get(
							keyString[3]).toString().trim();
			    etEditText.setText(nameString);
			    etEditText.setSelection(nameString.length());//���ù��λ��	
 				
 				new AlertDialog.Builder(getActivity())
 						.setTitle("���������")
 						.setView(etEditText)
 						.setPositiveButton(R.string.OK,//ȷ��
 								new DialogInterface.OnClickListener() {
 									@Override
 									public void onClick(DialogInterface arg0,int arg1) {
 										String editString=etEditText.getText().toString().trim();
 											try {
 												byte[] signalbyte=editString.getBytes("gb2312");
 												if(signalbyte.length>8){
 													editString = "";
 													Toast.makeText(getActivity(),
 															"���������ȴ���8���ַ�������������",
 															Toast.LENGTH_SHORT).show();
 													return;
 												}
 											} catch (UnsupportedEncodingException e) {
 												// TODO Auto-generated catch block
 												e.printStackTrace();
 											}
 										HashMap<String, Object> map = new HashMap<String, Object>();
 										map.put("addressText",
 												mAppList.get(position)
 														.get(keyString[0])
 														.toString());
 											map.put("name3",editString);
 	 										map.put("name1", 
 	 												mAppList.get(position)
 													.get(keyString[1])
 													.toString());
 	 										map.put("name2", 
 	 												mAppList.get(position)
 													.get(keyString[2])
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


 	}
	public static void checkOptionalList(
			ArrayList<String> list_name,
			ArrayList<HashMap<String, Object>> arrayList) {
		// ֱ����������
		try{
		list_name.clear();
		for (int i = 0; i < arrayList.size(); i++) {											
			String mouldstr=arrayList.get(i).get("name1").toString().trim();
			if (!mouldstr.equals("")) {
				list_name.add(mouldstr);
			}
			mouldstr=arrayList.get(i).get("name2").toString().trim();
			if (!mouldstr.equals("")) {
				list_name.add(mouldstr);
			}
			mouldstr=arrayList.get(i).get("name3").toString().trim();
			if (!mouldstr.equals("")) {
				list_name.add(mouldstr);
			}
		}			
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}