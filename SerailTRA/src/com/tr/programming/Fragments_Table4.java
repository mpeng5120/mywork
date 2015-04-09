package com.tr.programming;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wifiRunnablesAndChatlistener.PlcDataQueryRunnable;
import com.dbutils.ArrayListBound;
import com.explain.TableTranslate;
import com.tr.R;
import com.tr.programming.Fragments_Device_ActualInput.lockListener;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import awesomeWatch.TableWatch;

public class Fragments_Table4 extends Fragment {

	private ListView mListView;
	DecimalFormat format = new DecimalFormat("####");// ��ʽת����,0001�ĸ�ʽ����ʱ��ô���ˣ��Ժ��ҵ����õķ��������滻
	ArrayList<HashMap<String, Object>> TableEditList = ArrayListBound.getTableList4Data();
	public static MyAdapter TableEdit_Adapter = null;

	public static ArrayList<String> ActualInputList_symbol = new ArrayList<String>();// ��Ҫ��ʼ������Ȼ��ָ�����
	public static ArrayList<String> ActualOutputList_symbol = new ArrayList<String>();
	public static ArrayList<String> MiddleInputList_symbol = new ArrayList<String>();
	public static ArrayList<String> MiddleOutputList_symbol = new ArrayList<String>();
	public static ArrayList<String> DataInputList_symbol = new ArrayList<String>();
	public static ArrayList<String> DataOutputList_symbol = new ArrayList<String>();
	public static ArrayList<String> RegisterList_symbol = new ArrayList<String>();
	public static ArrayList<String> DataRegisterList_symbol = new ArrayList<String>();
	public static ArrayList<String> TimerList_symbol = new ArrayList<String>();
	public static ArrayList<String> CounterList_symbol = new ArrayList<String>();
	
	public static ArrayList<String> InputList_symbol_Table = new ArrayList<String>();
	public static ArrayList<String> OutputList_symbol_Table = new ArrayList<String>();
	/***************************************************************/
	//FUCK��һ��Ҫѡ���̰߳�ȫ�ļ�ֵ��
	//public static Hashtable<Integer,TextView[]> temphHashTable = new Hashtable<Integer,TextView[]>();
	// public static HashMap<Integer, View> temphHashMap=new HashMap<Integer,View>();
	private int counter = 0;
	private MultiAutoCompleteTextView layoutfind; 
	private MultiAutoCompleteTextView layoutreplace;
	public static ArrayList<String> opList_symbol_NC = new ArrayList<String>();//�洢�豸�����е������ź���
	//ArrayList<HashMap<String, Object>> selectNcEditList=new ArrayList<HashMap<String, Object>>();//��ѡ�д�ճ���Ĵ���
	public static List<ArrayList<HashMap<String, Object>>> oldNcEditLists=new ArrayList<ArrayList<HashMap<String, Object>>>();//�������ָ�ʹ��
	public static List<ArrayList<HashMap<String, Object>>> newNcEditLists=new ArrayList<ArrayList<HashMap<String, Object>>>();//�������ָ�ʹ��
	public void showNote(){
		try{
			TableEditList = ArrayListBound.getTableList4Data();
		for(int i=0;i<TableEditList.size();i++){
			if(TableEditList.get(i).get("inputEditText").equals("")||TableEditList.get(i).get("outputEditText").equals(""))
				continue;
			TableEditList.get(i).put("noteEditText", TableTranslate.tnoteList.get(i));
			TableEdit_Adapter.notifyDataSetChanged();		
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	/***************************************************************/
	@Override
	public void onPause(){
		super.onPause();
		//��δ�رռ��ӵ�������뿪��fragmentʱ�ر��߳�
		try{
		PlcDataQueryRunnable.destroy();

		//�뿪ҳ���ʱ����ӵ�״̬��ʾ�ı�
		TR_Programming_Activity.menu.findItem(R.id.menu_watchStop).setVisible(false);
		if (TR_Programming_Activity.menu.findItem(R.id.menu_note).isVisible()) {
			TR_Programming_Activity.menu.findItem(R.id.menu_watchStart).setVisible(true);
			TR_Programming_Activity.menu.findItem(R.id.menu_saveCache).setEnabled(true);
			TR_Programming_Activity.menu.findItem(R.id.menu_download).setEnabled(true);
		}else{
			TR_Programming_Activity.menu.findItem(R.id.menu_watchStart).setVisible(false);
			TR_Programming_Activity.menu.findItem(R.id.menu_saveCache).setEnabled(true);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/***************************************************************/
		try{
			TableEditList= ArrayListBound.getTableList4Data();
		mListView = (ListView) getActivity().findViewById(R.id.list);
		if(mListView==null){
			return;
		}
		//TableWatch.beginWatch(mListView,ArrayListBound.getTableList4Data());
		TableWatch.listView=mListView;
		//TableWatch.TableEditList=ArrayListBound.getTableList4Data();
		/***************************************************************/
		System.out.println("Fragments_Table4  alarmQueryRunnable");

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public  ListView getListView(){
		return mListView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("Fragments_Table4--->onCreate");
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("Fragments_Table4--->onCreateView");
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		return inflater.inflate(R.layout.tab_table_edit, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println("Fragments_Table4--->onActivityCreated");
		super.onActivityCreated(savedInstanceState);// ���ø���ķ��������ϵͳ��һЩ����

		try{
		format.setMinimumIntegerDigits(4);// ��ʾ����4λ��

		initialList_Table(ActualInputList_symbol,
				ArrayListBound.getDeviceActualInputListData());
		initialList_Table(ActualOutputList_symbol,
				ArrayListBound.getDeviceActualOutputListData());
		initialList_Table(MiddleInputList_symbol,
				ArrayListBound.getDeviceMiddleInputListData());
		initialList_Table(MiddleOutputList_symbol,
				ArrayListBound.getDeviceMiddleOutputListData());
		initialList_Table(DataInputList_symbol,
				ArrayListBound.getDeviceDataInputListData());
		initialList_Table(DataOutputList_symbol,
				ArrayListBound.getDeviceDataOutputListData());
		initialList_Table(RegisterList_symbol,
				ArrayListBound.getDeviceRegisterListData());
		initialList_Table(DataRegisterList_symbol,
				ArrayListBound.getDeviceDataRegisterListData());
		initialList_Table(TimerList_symbol,
				ArrayListBound.getDeviceTimerListData());
		initialList_Table(CounterList_symbol,
				ArrayListBound.getDeviceCounterListData());

		if (!InputList_symbol_Table.isEmpty()) {
			InputList_symbol_Table.clear();
		}
		InputList_symbol_Table.addAll(ActualInputList_symbol);
		InputList_symbol_Table.addAll(ActualOutputList_symbol);
		InputList_symbol_Table.addAll(MiddleInputList_symbol);
		InputList_symbol_Table.addAll(MiddleOutputList_symbol);
		InputList_symbol_Table.addAll(DataInputList_symbol);
		InputList_symbol_Table.addAll(DataOutputList_symbol);
		InputList_symbol_Table.addAll(RegisterList_symbol);
		InputList_symbol_Table.addAll(DataRegisterList_symbol);
		InputList_symbol_Table.addAll(TimerList_symbol);
		InputList_symbol_Table.addAll(CounterList_symbol);

		if (!OutputList_symbol_Table.isEmpty()) {
			OutputList_symbol_Table.clear();
		}
		OutputList_symbol_Table.addAll(ActualOutputList_symbol);
		OutputList_symbol_Table.addAll(MiddleOutputList_symbol);
		OutputList_symbol_Table.addAll(DataOutputList_symbol);
		OutputList_symbol_Table.addAll(RegisterList_symbol);
		OutputList_symbol_Table.addAll(DataRegisterList_symbol);
		OutputList_symbol_Table.addAll(TimerList_symbol);
		OutputList_symbol_Table.addAll(CounterList_symbol);

		if(!opList_symbol_NC.isEmpty()){
			opList_symbol_NC.clear();
		}
		opList_symbol_NC.addAll(InputList_symbol_Table);
		
		mListView = (ListView) getActivity().findViewById(R.id.list);
		if(mListView==null){
			return;
		}

		
		
		// ʹ��final��ʵ�����Σ���֪����δ�����Ҫ����
		TableEdit_Adapter = new MyAdapter(getActivity(), TableEditList,
				R.layout.nc_table_item, new String[] { "numText",
						"inputEditText", "outputEditText", "noteEditText" },
				new int[] { R.id.numText, R.id.inputEditText,
						R.id.outputEditText, R.id.noteEditText });

		mListView.setAdapter(TableEdit_Adapter);
		// ��ӵ��
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
					final int position, long arg3) {
				
				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
						new AlertDialog.Builder(getActivity())
						.setTitle(R.string.programming_batchoperation)//��������
						.setItems(getResources().getStringArray(R.array.programming_array_batchoperation),//������������
								new DialogInterface.OnClickListener() { // ���ѡ��ĳ���ķ���
									@Override
									public void onClick(DialogInterface dialog,int which) { 
										int selectcount=0;
											int[] selects=new int[TableEditList.size()];
											 for(int i=0;i<TableEditList.size();i++){
												 if(TableEdit_Adapter.getListselected().size()-1<i){
													 for(int j=0;j<i-(TableEdit_Adapter.getListselected().size()-1);j++){
														 TableEdit_Adapter.getListselected().add(false);
													 }
												 }else{
 												     if(TableEdit_Adapter.getListselected().get(i)==true){
 													    selects[selectcount]=i;
 													    selectcount++;
 												     }
												 }
											 }
										switch (which) {
										case 0:	//����
											ArrayList<HashMap<String, Object>> oldNcEditList=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
											for(int m=0;m<TableEditList.size();m++){
												HashMap<String, Object> map2 = new HashMap<String, Object>();
												map2.put("numText",	String.format("%04d", m));
												map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
												map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
												map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
												oldNcEditList.add(m,map2);
											}
											oldNcEditLists.add(oldNcEditList);
											if(oldNcEditLists.size()>30){
												oldNcEditLists.remove(0);
											}
											if(selectcount==0){
												if(Fragments_Table1.selectNcEditList==null){return;}
												Fragments_Table1.selectNcEditList.clear();
												Fragments_Table1.selectNcEditList.add(TableEditList.get(position));
												
												TableEditList.remove(position);
												
													//���µ�ַ��
													for ( int restposition=position ; restposition < TableEditList.size(); restposition++) {
														TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
													}
												
												TableEdit_Adapter.notifyDataSetChanged();
												Toast.makeText(getActivity(),"�Ѽ��е�ǰ��", Toast.LENGTH_SHORT).show();
											 }else{
											if(Fragments_Table1.selectNcEditList==null){return;}
											Fragments_Table1.selectNcEditList.clear();
											for(int i=0;i<selectcount;i++){
												Fragments_Table1.selectNcEditList.add(TableEditList.get(selects[i]));
											}
											
											for(int i=0;i<selectcount;i++){
													TableEditList.remove(selects[i]);
													for(int j=i+1;j<selectcount;j++){
														selects[j]=selects[j]-1;
													}
												
														//���µ�ַ��
														for ( int restposition=selects[i] ; restposition < TableEditList.size(); restposition++) {
															TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
														}
													
											}
											 for(int j=0;j<TableEditList.size();j++){
												 if(TableEdit_Adapter.getListselected().get(j)==true){
													TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
												    TableEdit_Adapter.getView(j, arg1, mListView);
												 }
												}
												TableEdit_Adapter.notifyDataSetChanged();
											Toast.makeText(getActivity(),"�Ѽ���ѡ����", Toast.LENGTH_SHORT).show();
											 }
											HashMap<String, Object> map = new HashMap<String, Object>();
											map.put("numText",	String.format("%04d", TableEditList.size()));
											map.put("inputEditText","");
											map.put("outputEditText","");
											map.put("noteEditText","");
											TableEditList.add(map);
											break;
										case 1://����
											if(selectcount==0){
												if(Fragments_Table1.selectNcEditList==null){return;}
												Fragments_Table1.selectNcEditList.clear();
												Fragments_Table1.selectNcEditList.add(TableEditList.get(position));
												Toast.makeText(getActivity(),"�Ѹ��Ƶ�ǰ��", Toast.LENGTH_SHORT).show();
											 }else{
											if(Fragments_Table1.selectNcEditList==null){return;}
											Fragments_Table1.selectNcEditList.clear();
											for(int i=0;i<selectcount;i++){
												Fragments_Table1.selectNcEditList.add(TableEditList.get(selects[i]));
											}
											Toast.makeText(getActivity(),"�Ѹ���ѡ����", Toast.LENGTH_SHORT).show();
											 }
											break;
										case 2://ճ��
											 for(int j=0;j<TableEditList.size();j++){
												 if(TableEdit_Adapter.getListselected().get(j)==true){
													TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
												    TableEdit_Adapter.getView(j, arg1, mListView);
												 }
												}
											if(Fragments_Table1.selectNcEditList.size()==0){
												Toast.makeText(getActivity(),"���ȸ��ƴ�ճ���Ĵ���", Toast.LENGTH_SHORT).show();
											}else{
												ArrayList<HashMap<String, Object>> oldNcEditList1=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
												for(int m=0;m<TableEditList.size();m++){
													HashMap<String, Object> map2 = new HashMap<String, Object>();
													map2.put("numText",	String.format("%04d", m));
													map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
													map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
													map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
													oldNcEditList1.add(m,map2);		
												}
												oldNcEditLists.add(oldNcEditList1);
												if(oldNcEditLists.size()>30){
													oldNcEditLists.remove(0);
												}
											for(int i=0;i<Fragments_Table1.selectNcEditList.size();i++){
											int nextposition=(position+i);
											HashMap<String, Object> map2 = new HashMap<String, Object>();
											map2.put("numText",	String.format("%04d", nextposition));//
											map2.put("inputEditText",Fragments_Table1.selectNcEditList.get(i).get("inputEditText"));
											map2.put("outputEditText",Fragments_Table1.selectNcEditList.get(i).get("outputEditText"));
											map2.put("noteEditText",Fragments_Table1.selectNcEditList.get(i).get("noteEditText"));
											TableEditList.add(nextposition,map2);		
											//����֮��ĵ�ַ��
											for ( int restposition=nextposition+1 ; restposition < TableEditList.size(); restposition++) {
												TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
											}
											if(TableEdit_Adapter.getListselected().size()-1<(nextposition+1)){
												 for(int k=0;k<(nextposition+1)-(TableEdit_Adapter.getListselected().size()-1);k++){
													 TableEdit_Adapter.getListselected().add(false);
												 }
											 }
											if(TableEdit_Adapter.getListselected().get(nextposition)==false){
											  TableEdit_Adapter.modifyStates(nextposition);//���ݵ����λ�øı���е�ѡ��״̬
											  TableEdit_Adapter.getView(nextposition, arg1, mListView);
											}								
											}
											TableEdit_Adapter.notifyDataSetChanged();
											Toast.makeText(getActivity(),"��ճ��ѡ����", Toast.LENGTH_SHORT).show();
											}
											
											break;
										case 3://ɾ��
											 ArrayList<HashMap<String, Object>> oldNcEditList2=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
											 for(int m=0;m<TableEditList.size();m++){
												 HashMap<String, Object> map2 = new HashMap<String, Object>();
													map2.put("numText",	String.format("%04d", m));
													map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
													map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
													map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
													oldNcEditList2.add(m,map2);
												}
											 oldNcEditLists.add(oldNcEditList2);
											 if(oldNcEditLists.size()>30){
													oldNcEditLists.remove(0);
												}
											 if(selectcount==0){
													TableEditList.remove(position);
													
														//���µ�ַ��
														for ( int restposition=position ; restposition < TableEditList.size(); restposition++) {
															TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
														}
													
													TableEdit_Adapter.notifyDataSetChanged();
													Toast.makeText(getActivity(),"��ɾ����ǰ��", Toast.LENGTH_SHORT).show();
											 }else{
												 for(int i=0;i<selectcount;i++){
														TableEditList.remove(selects[i]);
														for(int j=i+1;j<selectcount;j++){
															selects[j]=selects[j]-1;
														}
														
															//���µ�ַ��
															for ( int restposition=selects[i] ; restposition < TableEditList.size(); restposition++) {
																TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
															}
													
												}
												 for(int j=0;j<TableEditList.size();j++){
													 if(TableEdit_Adapter.getListselected().get(j)==true){
														TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
													    TableEdit_Adapter.getView(j, arg1, mListView);
													 }
													}
													TableEdit_Adapter.notifyDataSetChanged();
												Toast.makeText(getActivity(),"��ɾ��ѡ����", Toast.LENGTH_SHORT).show();
												
											 }
											    map = new HashMap<String, Object>();
												map.put("numText",	String.format("%04d", TableEditList.size()));
												map.put("inputEditText","");
												map.put("outputEditText","");
												map.put("noteEditText","");
												TableEditList.add(map);
											break;
										case 4://����
											 ArrayList<HashMap<String, Object>> oldNcEditList3=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
											 for(int m=0;m<TableEditList.size();m++){
												 HashMap<String, Object> map2 = new HashMap<String, Object>();
													map2.put("numText",	String.format("%04d", m));
													map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
													map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
													map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
													oldNcEditList3.add(m,map2);
												}
											 oldNcEditLists.add(oldNcEditList3);
											 if(oldNcEditLists.size()>30){
													oldNcEditLists.remove(0);
												}
											if(selectcount==0){
												HashMap<String, Object> map1 = new HashMap<String, Object>();
												map1.put("numText", String.format("%04d",position));//
												map1.put("inputEditText", "");
												map1.put("outputEditText", "");
												map1.put("noteEditText", "");
												TableEditList.add(position,map1);		
												//���µ�ַ��
												for ( int restposition=position+1 ; restposition < TableEditList.size(); restposition++) {
													TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
												}								
												Toast.makeText(getActivity(),"�����Ѳ���һ��", Toast.LENGTH_SHORT).show();	
											 }else{
												 for(int i=0;i<selectcount;i++){
													    HashMap<String, Object> map1 = new HashMap<String, Object>();
													    map1.put("numText", String.format("%04d",position));//
														map1.put("inputEditText", "");
														map1.put("outputEditText", "");
														map1.put("noteEditText", "");
														TableEditList.add(position,map1);		
														//���µ�ַ��
														for ( int restposition=position+1 ; restposition < TableEditList.size(); restposition++) {
															TableEditList.get(restposition).put("numText", String.format("%04d", restposition));
														}									
													}
											    Toast.makeText(getActivity(),"�Ѳ���հ���", Toast.LENGTH_SHORT).show();
														
											 }
											for(int j=0;j<TableEditList.size();j++){
												 if(TableEdit_Adapter.getListselected().size()-1<j){
													 for(int k=0;k<j-(TableEdit_Adapter.getListselected().size()-1);k++){
														 TableEdit_Adapter.getListselected().add(false);
													 }
												 }else{
													 if(TableEdit_Adapter.getListselected().get(j)==true){
															TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
														    TableEdit_Adapter.getView(j, arg1, mListView);
														   
														 }
												 }
												
												} 
											TableEdit_Adapter.notifyDataSetChanged();
											
											break;
										case 5://����/�滻
											final View view_findandreplace = View.inflate(getActivity(),
													R.layout.tab_nc_findandreplace, null);
											final AlertDialog dialog_findandreplace = new AlertDialog.Builder(
													getActivity())
													.setTitle("����/�滻")
													.setView(view_findandreplace)
													.setPositiveButton("�滻",
															new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog,int which) {
																	ArrayList<HashMap<String, Object>> oldNcEditList4=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
																	for(int m=0;m<TableEditList.size();m++){
																		HashMap<String, Object> map2 = new HashMap<String, Object>();
																		map2.put("numText",	String.format("%04d", m));
																		map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
																		map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
																		map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
																		oldNcEditList4.add(m,map2);	
																	}
																	oldNcEditLists.add(oldNcEditList4);
																	if(oldNcEditLists.size()>30){
																		oldNcEditLists.remove(0);
																	}
																	String layoutfindstr=layoutfind.getText().toString().trim();
																	String layoutreplacestr=layoutreplace.getText().toString().trim();
																	int layoutreplacecount=0;
																	 for(int j=0;j<TableEditList.size();j++){
																		 String inputstr=TableEditList.get(j).get("inputEditText").toString();
																		 String outputstr=TableEditList.get(j).get("outputEditText").toString();
																		 if((find_table(layoutfindstr,inputstr)||find_table(layoutfindstr,outputstr))
																				 &&TableEdit_Adapter.getListselected().get(j)==true){
																			 inputstr=replace_table(layoutfindstr,inputstr,layoutreplacestr);
																			 TableEditList.get(j).put("inputEditText", inputstr);
 
																			 outputstr=replace_table(layoutfindstr,outputstr,layoutreplacestr);
																			 TableEditList.get(j).put("outputEditText", outputstr);
																		    TableEdit_Adapter.notifyDataSetChanged();
																		    layoutreplacecount++;
																		 }
																		}
																	 if(layoutreplacecount==0){
																		 oldNcEditLists.remove(oldNcEditLists.size()-1);
																		 Toast.makeText(getActivity(),"���ź������滻", Toast.LENGTH_SHORT).show(); 
																	 }else{
																		 
																		 Toast.makeText(getActivity(),layoutreplacecount+"�д���������ź������滻", Toast.LENGTH_SHORT).show();
																	 }
																}
															}).setNegativeButton("����",
															new DialogInterface.OnClickListener() {
																public void onClick(DialogInterface dialog,int which) {
																	String layoutfindstr=layoutfind.getText().toString().trim();
																	for(int j=0;j<TableEditList.size();j++){
																		 if(TableEdit_Adapter.getListselected().get(j)==true){
																			TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
																		    TableEdit_Adapter.getView(j, arg1, mListView);
																		    TableEdit_Adapter.notifyDataSetChanged();
																		 }
																		}
																	if(layoutfindstr.equals("")){
																	}else{
																		int layoutfindcount=0;
																	 for(int j=0;j<TableEditList.size();j++){
																		 String inputstr=TableEditList.get(j).get("inputEditText").toString();
																		 String outputstr=TableEditList.get(j).get("outputEditText").toString();
																		 String ioputstr=inputstr+","+outputstr;
																		 if(find_table(layoutfindstr,ioputstr)){
																			    TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
																	            TableEdit_Adapter.getView(j, arg1, mListView);
																	            TableEdit_Adapter.notifyDataSetChanged();
																	            layoutfindcount++;
																		 }
																		}
																	 if(layoutfindcount==0){
																		 Toast.makeText(getActivity(),"�޴��ź���", Toast.LENGTH_SHORT).show(); 
																	 }else{
																		 Toast.makeText(getActivity(),layoutfindcount+"�д���������ź���", Toast.LENGTH_SHORT).show();
																	 }
																	 }
																}
															}).show();
											layoutfind = (MultiAutoCompleteTextView) dialog_findandreplace.findViewById(R.id.editText_find);
											if (layoutfind== null) {
												return;
											}
											layoutfind.setTokenizer(new SemicolonTokenizer(' '));// ���ÿո�Ϊ�ָ���
											layoutfind.setCompletionHint("��ѡ��һ��");
											layoutfind.setThreshold(1);// ����һ���ַ��Ϳ�ʼ��⣬��������ѡ��
											layoutfind.setDropDownHeight(300);
											ArrayAdapter<String> adapter = new ArrayAdapter<String>(
													getActivity(),
													android.R.layout.simple_dropdown_item_1line,
													opList_symbol_NC);
											layoutfind.setAdapter(adapter);
											layoutfind.addTextChangedListener(new TextWatcher() {
										           @Override
										            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
										                    int arg3) {
										            }
										             @Override
										            public void onTextChanged(CharSequence s, int arg1, int arg2,
										                    int arg3) {
										            	
										            }
													@Override
													public void afterTextChanged(Editable s) {
														// TODO Auto-generated method stub
														
														 String str=s.toString();
														 String str1="";
														 String str2="";
														 if(str.contains("<��-��")||str.contains("��-��>")){
														 str1=str.split("<��-��")[0].trim();
														 str2=str.split("��-��>")[1].trim();
														 str=str1+str2;
														 }
														 if(!layoutfind.getText().toString().equals(str)){
															 layoutfind.setText(str);
															 layoutfind.setSelection(str1.length());// ���ù��λ�� 
														 }
														
													}
												});
											layoutreplace = (MultiAutoCompleteTextView) dialog_findandreplace.findViewById(R.id.editText_replace);
											if (layoutreplace== null) {
												return;
											}
											layoutreplace.setTokenizer(new SemicolonTokenizer(' '));// ���ÿո�Ϊ�ָ���
											layoutreplace.setCompletionHint("��ѡ��һ��");
											layoutreplace.setThreshold(1);// ����һ���ַ��Ϳ�ʼ��⣬��������ѡ��
											layoutreplace.setDropDownHeight(300);
											ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
													getActivity(),
													android.R.layout.simple_dropdown_item_1line,
													opList_symbol_NC);
											layoutreplace.setAdapter(adapter1);
											layoutreplace.addTextChangedListener(new TextWatcher() {
										           @Override
										            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
										                    int arg3) {
										            }
										             @Override
										            public void onTextChanged(CharSequence s, int arg1, int arg2,
										                    int arg3) {
										            	
										            }
													@Override
													public void afterTextChanged(Editable s) {
														// TODO Auto-generated method stub
														
														 String str=s.toString();
														 String str1="";
														 String str2="";
														 if(str.contains("<��-��")||str.contains("��-��>")){
														 str1=str.split("<��-��")[0].trim();
														 str2=str.split("��-��>")[1].trim();
														 str=str1+str2;
														 }
														 if(!layoutreplace.getText().toString().equals(str)){
															 layoutreplace.setText(str);
															 layoutreplace.setSelection(str1.length());// ���ù��λ�� 
														 }
														
													}
												});
											break;
										case 8://ȫѡ
											 for(int j=0;j<TableEditList.size();j++){
												 if(TableEdit_Adapter.getListselected().get(j)==false){
													TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
												    TableEdit_Adapter.getView(j, arg1, mListView);
												    TableEdit_Adapter.notifyDataSetChanged();
												 }
												}
											break;
										case 9://ȫ��ѡ
											 for(int j=0;j<TableEditList.size();j++){
												 if(TableEdit_Adapter.getListselected().get(j)==true){
													TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
												    TableEdit_Adapter.getView(j, arg1, mListView);
												    TableEdit_Adapter.notifyDataSetChanged();
												 }
												}
											break;
										case 10://��ѡ
											 for(int j=0;j<TableEditList.size();j++){
													TableEdit_Adapter.modifyStates(j);//���ݵ����λ�øı���е�ѡ��״̬
												    TableEdit_Adapter.getView(j, arg1, mListView);
												    TableEdit_Adapter.notifyDataSetChanged();
												}
											break;
										case 6://����
											if(oldNcEditLists.size()<=0){
												Toast.makeText(getActivity(), "û�д������Ĳ���", Toast.LENGTH_SHORT).show();
											}else{
												ArrayList<HashMap<String, Object>> newNcEditList=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
												for(int m=0;m<TableEditList.size();m++){
													HashMap<String, Object> map2 = new HashMap<String, Object>();
													map2.put("numText",	String.format("%04d", m));
													map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
													map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
													map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
													newNcEditList.add(m,map2);
												}
												newNcEditLists.add(newNcEditList);
												
													TableEditList.clear();
													for(int m=0;m<(oldNcEditLists.get(oldNcEditLists.size()-1)).size();m++){
														HashMap<String, Object> map2 = new HashMap<String, Object>();
														map2.put("numText",	String.format("%04d", m));
														map2.put("inputEditText",(oldNcEditLists.get(oldNcEditLists.size()-1)).get(m).get("inputEditText"));
														map2.put("outputEditText",(oldNcEditLists.get(oldNcEditLists.size()-1)).get(m).get("outputEditText"));
														map2.put("noteEditText",(oldNcEditLists.get(oldNcEditLists.size()-1)).get(m).get("noteEditText"));
														TableEditList.add(m,map2);
													}
												
												TableEdit_Adapter.notifyDataSetChanged();
												oldNcEditLists.remove(oldNcEditLists.size()-1);
											}
											break;
										case 7://�ָ�
											if(newNcEditLists.size()<=0){
												Toast.makeText(getActivity(), "û�д��ָ��Ĳ���", Toast.LENGTH_SHORT).show();
											}else{
											ArrayList<HashMap<String, Object>> oldNcEditList5=new ArrayList<HashMap<String, Object>>();//�������ָ�ʹ��
											for(int m=0;m<TableEditList.size();m++){
												HashMap<String, Object> map2 = new HashMap<String, Object>();
												map2.put("numText",	String.format("%04d", m));
												map2.put("inputEditText",TableEditList.get(m).get("inputEditText"));
												map2.put("outputEditText",TableEditList.get(m).get("outputEditText"));
												map2.put("noteEditText",TableEditList.get(m).get("noteEditText"));
												oldNcEditList5.add(m,map2);
											}
											oldNcEditLists.add(oldNcEditList5);
											
											TableEditList.clear();
											for(int m=0;m<(newNcEditLists.get(newNcEditLists.size()-1)).size();m++){
												HashMap<String, Object> map2 = new HashMap<String, Object>();
												map2.put("numText",	String.format("%04d", m));
												map2.put("inputEditText",(newNcEditLists.get(newNcEditLists.size()-1)).get(m).get("inputEditText"));
												map2.put("outputEditText",(newNcEditLists.get(newNcEditLists.size()-1)).get(m).get("outputEditText"));
												map2.put("noteEditText",(newNcEditLists.get(newNcEditLists.size()-1)).get(m).get("noteEditText"));
												TableEditList.add(m,map2);
											}
											TableEdit_Adapter.notifyDataSetChanged();
											newNcEditLists.remove(newNcEditLists.size()-1);
											}
											break;
										default:
											break;
										}
									}
								}).setNegativeButton("ȡ��", null).show();
				}else {
					try{
						Toast.makeText(getActivity(), "��ǰ��������״̬", Toast.LENGTH_SHORT).show();
						}catch(Exception e){
							e.printStackTrace();
						}
			}
				return false;
			}
		});
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//����Ϊ��ѡ
		// ��ӵ��
				mListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							final int position, long arg3) {
						try{
						if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
								TableEdit_Adapter.modifyStates(position);//���ݵ����λ�øı���е�ѡ��״̬
								TableEdit_Adapter.getView(position, arg1, mListView);
								TableEdit_Adapter.notifyDataSetChanged();
						}else {
						Toast.makeText(getActivity(), "��ǰ��������״̬", Toast.LENGTH_SHORT).show();
					    }}catch(Exception e){
							e.printStackTrace();
						}
					}
				});
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	private String replace_table(String rege,String str,String value){
		 String regex = "[^A-Za-z0-9]"+rege+"[^A-Za-z0-9]";
		   str=" "+str+" ";
		   Pattern pat = Pattern.compile(regex);  
		   Matcher matcher = pat.matcher(str); 
		   int count=0;
		   while (matcher.find()) { 
			  int length=value.length()-rege.length();
			  String qtemp = str.substring(0,matcher.start()+count*length);
			  String htemp = str.substring(matcher.end()+count*length,str.length());
		     String temp = str.substring(matcher.start()+count*length,matcher.end()+count*length);
		     String rptemp=temp.charAt(0)+value+temp.charAt(temp.length()-1);
		     str = qtemp+rptemp+htemp;
		     count++;
		   } 
		   return str.trim();
	}
	private boolean find_table(String rege,String str){
		   String regex = "[^A-Za-z0-9]"+rege+"[^A-Za-z0-9]";
		   str=" "+str+" ";
		   boolean result=false;
		   Pattern pat = Pattern.compile(regex);  
		   Matcher matcher = pat.matcher(str); 
		   while (matcher.find()) { 
			   result=true;
		   } 
		   return result;
	}
	private void initialList_Table(ArrayList<String> symbolList,
			ArrayList<HashMap<String, Object>> arrayList) {
		try{
		symbolList.clear();
		for (int i = 0; i < arrayList.size(); i++) {
			String symbolstr = (String) arrayList.get(i).get(
					"symbolNameEditText");
			String signalstr = (String) arrayList.get(i).get(
					"signalNameEditText");
			if (!symbolstr.equals("")) {
				//symbolList.add(symbolstr);
				symbolList.add(symbolstr+"<��-��"+signalstr+"��-��>");
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
			TextView numText;
			TextView inputEditText;
			TextView outputEditText;
			TextView noteEditText;

		}

		private ArrayList<HashMap<String, Object>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String[] keyString;
		private int[] valueViewID;
		private buttonViewHolder holder;
		private int mLayoutID;
		private List<Boolean> listselected;//�ò����͵�list��¼ÿһ�е�ѡ��״̬
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
			
			 this.setListselected(new ArrayList<Boolean>(getCount()));
		        for(int i=0;i<getCount();i++)
		        	getListselected().add(false);//��ʼΪfalse�����Ⱥ�listviewһ��
		}

	public List<Boolean> getListselected() {
            return listselected;
    }
    public void setListselected(List<Boolean> listselected) {
            this.listselected = listselected;
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

		 public void modifyStates(int position){ 
			 if(getListselected().size()-1<position){
				 for(int i=0;i<position-(getListselected().size()-1);i++){
				   getListselected().add(false);
				 }
			 }else{
	            if(getListselected().get(position)==false){
	                 getListselected().set(position, true);//�����Ӧposition�ļ�¼��δ��ѡ��������Ϊѡ�У�true��
	                 //notifyDataSetChanged();
	            }else{
	                getListselected().set(position, false);//������Ӧposition�ļ�¼�Ǳ�ѡ��������Ϊδѡ�У�false��
	                 //notifyDataSetChanged();
	                }
			 }
	      }
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (buttonViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(mLayoutID, null);
				holder = new buttonViewHolder();
				holder.numText = (TextView) convertView
						.findViewById(valueViewID[0]);
				holder.inputEditText = (TextView) convertView
						.findViewById(valueViewID[1]);
				holder.outputEditText = (TextView) convertView
						.findViewById(valueViewID[2]);
				holder.noteEditText = (TextView) convertView
						.findViewById(valueViewID[3]);

				convertView.setTag(holder);
			}

			HashMap<String, Object> appInfo = mAppList.get(position);
			if (appInfo != null) {
				String numText = appInfo.get(keyString[0]).toString();
				String inputEditText = appInfo.get(keyString[1]).toString();
				String outputEditText = appInfo.get(keyString[2]).toString();
				String noteEditText = appInfo.get(keyString[3]).toString();

				holder.numText.setText(numText);
				holder.inputEditText.setText(inputEditText);
				holder.outputEditText.setText(outputEditText);
				holder.noteEditText.setText(noteEditText);

				/**************** �����й� **************************************************/
				/*System.out.println("����" + position);
				System.out.println("map��С" + temphHashTable.size());
				TextView[] textViewsarray=new TextView[2];
				textViewsarray[0]=holder.inputEditText;
				textViewsarray[1]=holder.outputEditText;
				temphHashTable.put(position, textViewsarray);*/

				// ѡ�к�ɫ��ʾ
				if(getListselected().size()-1<position){
					 for(int i=0;i<position-(getListselected().size()-1);i++){
					   getListselected().add(false);
					 }
				 }else{
			      if(getListselected().get(position)==false){//���δ��ѡ�У�����Ϊ��ɫ
			    	    holder.numText.setTextColor(Color.BLACK);
			    	    holder.inputEditText.setTextColor(Color.BLACK);
			    	    holder.outputEditText.setTextColor(Color.BLACK);
			    	    holder.noteEditText.setTextColor(Color.BLACK);
			    	  convertView.setBackgroundColor(Color.BLACK);
	                }else{//�����ѡ�У�����Ϊ��ɫ
	                	holder.numText.setTextColor(Color.RED);
	                	holder.inputEditText.setTextColor(Color.RED);
	                	holder.outputEditText.setTextColor(Color.RED);
	                	holder.noteEditText.setTextColor(Color.RED);
	                	convertView.setBackgroundColor(Color.RED);
			    	    }
				 }
				/******************************************************************/
				
				if (TR_Programming_Activity.alreadyChecked_programmingPassword) {
					holder.inputEditText.setOnClickListener(new inputListener(
							position));
					holder.outputEditText
							.setOnClickListener(new outputListener(position));
					holder.noteEditText.setOnClickListener(new notelistener(
							position));
				} else {
					holder.inputEditText.setOnClickListener(new lockListener(
							getActivity()));
					holder.outputEditText.setOnClickListener(new lockListener(
							getActivity()));
					holder.noteEditText.setOnClickListener(new lockListener(
							getActivity()));
				}
			}
			return convertView;
		}

		class inputListener implements android.view.View.OnClickListener {
			private int position;
			// ������Щ��������Ϊʹ�ú�����ʱֻ�ܴ���ֵ���ﲻ���޸�ʵ��ֵ��Ŀ�ģ������Ϳ����޸�ֵ
			private String editString;
			private String illegalString;
			private boolean first_legal;
			private boolean first_illegal;
			private boolean has;

			inputListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {
				// �ж��Զ�����һ��
				try{
				if (position == (mAppList.size() - 1)) {
					addOneItem();
					notifyDataSetChanged();
				}
				View orderView=View.inflate(getActivity(), R.layout.tab_yunsuanfu,null );
				final MultiAutoCompleteTextView etEditText =(MultiAutoCompleteTextView) orderView.findViewById(R.id.multi_txt);
				
				final Button btn_yu=(Button) orderView.findViewById(R.id.btn_yu);
				final Button btn_huo=(Button) orderView.findViewById(R.id.btn_huo);
				final Button btn_fei=(Button) orderView.findViewById(R.id.btn_fei);
				final Button btn_jinghao=(Button) orderView.findViewById(R.id.btn_jinghao);
				final Button btn_dengyu=(Button) orderView.findViewById(R.id.btn_dengyu);
				final Button btn_dayu=(Button) orderView.findViewById(R.id.btn_dayu);
				final Button btn_xiaoyu=(Button) orderView.findViewById(R.id.btn_xiaoyu);
				final Button btn_dayudeng=(Button) orderView.findViewById(R.id.btn_dayudeng);
				final Button btn_xiaoyudeng=(Button) orderView.findViewById(R.id.btn_xiaoyudeng);
				//final MultiAutoCompleteTextView etEditText = new MultiAutoCompleteTextView(getActivity());// ��ֵ�Զ���������
				etEditText.setHint("�������豸�����е������й���");
				// etEditText.setTokenizer(new
				// MultiAutoCompleteTextView.CommaTokenizer());//���ö���Ϊ�ָ���
				etEditText.setTokenizer(new SemicolonTokenizer(' '));// ���ÿո�Ϊ�ָ���
				etEditText.setCompletionHint("��ѡ��һ��");
				etEditText.setThreshold(1);// ����һ���ַ��Ϳ�ʼ��⣬��������ѡ��
				etEditText.setDropDownHeight(300);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						InputList_symbol_Table);
				etEditText.setAdapter(adapter);

				String inputString = (String) mAppList.get(position).get(
						keyString[1]);
				etEditText.setText(inputString);
				etEditText.setSelection(inputString.length());// ���ù��λ��
				new AlertDialog.Builder(getActivity()).setTitle("���������")
						.setView(orderView).setPositiveButton(R.string.OK,// ȷ��
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										editString = etEditText.getText()
												.toString().trim();

										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("numText",
												mAppList.get(position)
														.get(keyString[0])
														.toString());
										map.put("inputEditText", editString);
										map.put("outputEditText",
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

								}).setNegativeButton(R.string.CANCEL, null).show();
				
				etEditText.addTextChangedListener(new TextWatcher() {
		           @Override
		            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
		                    int arg3) {
		            }
		             @Override
		            public void onTextChanged(CharSequence s, int arg1, int arg2,
		                    int arg3) {
		            	
		            }
					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub
						
						 String str=s.toString();
						 String str1="";
						 String str2="";
						 if(str.contains("<��-��")||str.contains("��-��>")){
						 str1=str.split("<��-��")[0].trim();
						 str2=str.split("��-��>")[1].trim();
						 str=str1+str2;
						 }
						 if(!etEditText.getText().toString().equals(str)){
							 etEditText.setText(str);
							 etEditText.setSelection(str1.length());// ���ù��λ�� 
						 }
						
					}
				});
				
				btn_yu.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, ", ");
						}
				});
				btn_huo.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "| ");
						}
				});
				btn_fei.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "- ");
						}
				});
				btn_jinghao.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "# ");
						}
				});
				btn_dengyu.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "= ");
						}
				});
				btn_dayu.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "> ");
						}
				});
				btn_xiaoyu.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "< ");
						}
				});
				btn_dayudeng.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, ">= ");
						}
				});
				btn_xiaoyudeng.setOnClickListener(new android.view.View.OnClickListener(){
					@Override
					public void onClick(final View v) {
						int index=etEditText.getSelectionStart();
						Editable editable=etEditText.getText();
						editable.insert(index, "<= ");
						}
				});
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			}

		}

		class outputListener implements android.view.View.OnClickListener {

			private int position;

			private String editString;
			private String illegalString;
			private boolean first_legal;
			private boolean first_illegal;
			private boolean has;

			// ���캯��
			outputListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {
				// �ж��Զ�����һ��
				try{
				if (position == (mAppList.size() - 1)) {
					addOneItem();
					notifyDataSetChanged();
				}
				View orderView=View.inflate(getActivity(), R.layout.tab_yunsuanfu,null );
				final MultiAutoCompleteTextView etEditText =(MultiAutoCompleteTextView) orderView.findViewById(R.id.multi_txt);
				
				final Button btn_yu=(Button) orderView.findViewById(R.id.btn_yu);
				final Button btn_huo=(Button) orderView.findViewById(R.id.btn_huo);
				btn_huo.setVisibility(View.GONE);
				final Button btn_fei=(Button) orderView.findViewById(R.id.btn_fei);
				final Button btn_jinghao=(Button) orderView.findViewById(R.id.btn_jinghao);
				final Button btn_dengyu=(Button) orderView.findViewById(R.id.btn_dengyu);
				final Button btn_dayu=(Button) orderView.findViewById(R.id.btn_dayu);
				final Button btn_xiaoyu=(Button) orderView.findViewById(R.id.btn_xiaoyu);
				final Button btn_dayudeng=(Button) orderView.findViewById(R.id.btn_dayudeng);
				final Button btn_xiaoyudeng=(Button) orderView.findViewById(R.id.btn_xiaoyudeng);
				btn_jinghao.setVisibility(View.GONE);
				btn_dayu.setVisibility(View.GONE);
				btn_xiaoyu.setVisibility(View.GONE);
				btn_dayudeng.setVisibility(View.GONE);
				btn_xiaoyudeng.setVisibility(View.GONE);
				//final MultiAutoCompleteTextView etEditText = new MultiAutoCompleteTextView(getActivity());// ��ֵ�Զ���������
				etEditText.setHint("������豸�����е�����й���");
				// etEditText.setTokenizer(new
				// MultiAutoCompleteTextView.CommaTokenizer());//���ö���Ϊ�ָ���
				etEditText.setTokenizer(new SemicolonTokenizer(' '));// ���ÿո�Ϊ�ָ���
				etEditText.setThreshold(1);// ����һ���ַ��Ϳ�ʼ��⣬��������ѡ��
				etEditText.setCompletionHint("��ѡ��һ��");
				etEditText.setDropDownHeight(300);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_dropdown_item_1line,
						OutputList_symbol_Table);
				etEditText.setAdapter(adapter);

				String outputString = (String) mAppList.get(position).get(
						keyString[2]);
				etEditText.setText(outputString);
				etEditText.setSelection(outputString.length());// ���ù��λ��
				new AlertDialog.Builder(getActivity()).setTitle("��������")
						.setView(orderView).setPositiveButton(R.string.OK,// ȷ��
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										editString = etEditText.getText()
												.toString().trim();

										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("numText",
												mAppList.get(position)
														.get(keyString[0])
														.toString());//
										map.put("inputEditText",
												mAppList.get(position)
														.get(keyString[1])
														.toString());
										map.put("outputEditText", editString);
										map.put("noteEditText",
												mAppList.get(position)
														.get(keyString[3])
														.toString());
										mAppList.set(position, map);
										notifyDataSetChanged();
									}

								}).setNegativeButton(R.string.CANCEL, null)// ȡ��
						.show();
				etEditText.addTextChangedListener(new TextWatcher() {
			           @Override
			            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
			                    int arg3) {
			            }
			             @Override
			            public void onTextChanged(CharSequence s, int arg1, int arg2,
			                    int arg3) {
			            	
			            }
						@Override
						public void afterTextChanged(Editable s) {
							// TODO Auto-generated method stub
							
							 String str=s.toString();
							 String str1="";
							 String str2="";
							 if(str.contains("<��-��")||str.contains("��-��>")){
							 str1=str.split("<��-��")[0].trim();
							 str2=str.split("��-��>")[1].trim();
							 str=str1+str2;
							 }
							 if(!etEditText.getText().toString().equals(str)){
								 etEditText.setText(str);
								 etEditText.setSelection(str1.length());// ���ù��λ�� 
							 }
							
						}
					});
					
					btn_yu.setOnClickListener(new android.view.View.OnClickListener(){
						@Override
						public void onClick(final View v) {
							int index=etEditText.getSelectionStart();
							Editable editable=etEditText.getText();
							editable.insert(index, ", ");
							}
					});
					btn_fei.setOnClickListener(new android.view.View.OnClickListener(){
						@Override
						public void onClick(final View v) {
							int index=etEditText.getSelectionStart();
							Editable editable=etEditText.getText();
							editable.insert(index, "- ");
							}
					});
					btn_dengyu.setOnClickListener(new android.view.View.OnClickListener(){
						@Override
						public void onClick(final View v) {
							int index=etEditText.getSelectionStart();
							Editable editable=etEditText.getText();
							editable.insert(index, "= ");
							}
					});
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
				// �ж��Զ�����һ��
				try{
				if (position == (mAppList.size() - 1)) {
					addOneItem();
					notifyDataSetChanged();
				}

				final EditText etEditText = new EditText(getActivity());
				etEditText.setHint("ע�ͳ��Ȳ�����4000���ַ�");
				// �������볤��Ϊ40
				etEditText
						.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
								4000) });
				String noteString = (String) mAppList.get(position).get(
						keyString[3]);
				etEditText.setText(noteString);
				etEditText.setSelection(noteString.length());// ���ù��λ��
				new AlertDialog.Builder(getActivity()).setTitle("�����ע��")
						.setView(etEditText).setPositiveButton(R.string.OK,// ȷ��
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										String editString = etEditText
												.getText().toString().trim();
										// �ų��Ƿ��ַ�"/"
										if (editString.contains("/")) {
											editString = editString.replace(
													"/", "\\");
											Toast.makeText(getActivity(),
													"/Ϊ�Ƿ��ַ������滻Ϊ\\",
													Toast.LENGTH_SHORT).show();
										}
										HashMap<String, Object> map = new HashMap<String, Object>();
										map.put("numText",
												mAppList.get(position)
														.get(keyString[0])
														.toString());//
										map.put("inputEditText",
												mAppList.get(position)
														.get(keyString[1])
														.toString());
										map.put("outputEditText",
												mAppList.get(position).get(
														keyString[2]));
										map.put("noteEditText", editString);
										mAppList.set(position, map);
										notifyDataSetChanged();
									}

								}).setNegativeButton(R.string.CANCEL, null)// ȡ��
						.show();
			}catch(Exception e){
				e.printStackTrace();
			}
			}

		}

		public void addOneItem() {
			HashMap<String, Object> mapadd = new HashMap<String, Object>();
			mapadd.put("numText", String.format("%04d", mAppList.size()));//
			mapadd.put("inputEditText", "");
			mapadd.put("outputEditText", "");
			mapadd.put("noteEditText", "");
			mAppList.add(mAppList.size(), mapadd);

		}

	}

}