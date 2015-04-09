package com.tr.newpragram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.dbutils.ArrayListBound;
import com.explain.NCTranslate;
import com.tr.R;
import com.tr.programming.TR_Programming_Activity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 列表Fragment
 * @author mining
 *
 */
public class FragmentList extends Fragment {
	
	private ArrayList<HashMap<String, Object>> mDataSourceList = new ArrayList<HashMap<String, Object>>();
	private List<FragmentTransaction> mBackStackList = new ArrayList<FragmentTransaction>();
	private ListView listView;
	private ListAdapter myAdapte;
	private Button DelBtn = null;
	private Button AddBtn = null;
	private Button DownLoadBtn = null;
	private Activity myActivity;
	private int myPos = -1;
	public static boolean IsDetailFragment = false;

	
	/**
	 * @return the isDetailFragment
	 */
	public static boolean isIsDetailFragment() {
		return IsDetailFragment;
	}

	/**
	 * @param isDetailFragment the isDetailFragment to set
	 */
	public static void setIsDetailFragment(boolean isDetailFragment) {
		IsDetailFragment = isDetailFragment;
	}
	ArrayList<HashMap<String, Object>> NcEditList = ArrayListBound.getNCeditList3Data();
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("destroy");

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		return inflater.inflate(R.layout.fragment_list_layout, container, false);

	}
	public  void showNote(){
		NCTranslate.noteList.clear();
		TR_Programming_Activity.noteflag=3;
		int[] test  =NCTranslate.beginTranslate(getActivity(),
				getResources().getStringArray(R.array.IF2));
		try{
			NcEditList= ArrayListBound.getNCeditList3Data();
			//Log.d("mpeng"," ncdedit size is" +NcEditList.get(0).toString());
		for(int i=0;i<NcEditList.size();i++){
			if(NcEditList.get(i).get("operatText").equals("")&&NcEditList.get(i).get("orderSpinner").equals(""))
				continue;
			NcEditList.get(i).put("noteEditText", NCTranslate.noteList.get(i));
//			myAdapte.notifyDataSetChanged();
			
		}
		Message msg  = new Message();
		msg.what = 2;
		mHandler.sendMessage(msg);
		TR_Programming_Activity.noteflag=0;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	public Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Log.d("mpeng","the pos name is "+msg.obj.toString());
				setHighLight(msg.obj.toString().split("\\s++")[0]);
				break;
			case 2:
				myAdapte.notifyDataSetChanged();
				break;
			}
		};
	};
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		 DelBtn= (Button)getActivity().findViewById(R.id.deleteBtn);
		 AddBtn = (Button)getActivity().findViewById(R.id.insertBtn);
		 DownLoadBtn = (Button)getActivity().findViewById(R.id.downLoadBtn);
		//添加数据到ListView
//		for(int i=0, count=20; i<count; i++){
//			mDataSourceList.add("列表数据" + i);
//		}
		 mDataSourceList = ArrayListBound.getNCeditList3Data();
		
		//列表页面的ListView
		listView = (ListView) getActivity().findViewById(R.id.fragment_list);
		myAdapte  = new ListAdapter(getActivity(), mDataSourceList);
		listView.setAdapter(myAdapte);
		myAdapte.notifyDataSetChanged();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int position, long arg3) {
				try{
					
					
					
						myPos = position;		
						myAdapte.setSelectItem(myPos);
						myAdapte.notifyDataSetChanged();
						Intent IT = new Intent();
					    Bundle bundle = new Bundle();
					    Log.d("mpeng","the pos is "+myPos);
					    if(myPos<0){
					    	Toast.makeText(getActivity(), "请选择位置", Toast.LENGTH_SHORT).show();
					    	return;
					    }
					    
					    
					    String order = mDataSourceList.get(myPos).get("orderSpinner").toString().trim();
					    String name = mDataSourceList.get(myPos).get("operatText").toString().trim();
					    if((order.equalsIgnoreCase("MOVE")||order.equalsIgnoreCase("MOVEP"))&&name.contains("SP"))
					    {
					    	String PosName = name.substring(name.indexOf("S"));
					    	String ispos=updateMoveList(PosName);
					    	if(ispos.equals("")){
					    		Toast.makeText(getActivity(), "此位置未定义或未勾选", Toast.LENGTH_SHORT).show();
					    	}else{
					    		bundle.putString("PosType", ispos);//当前是P SP.FP					   
							    IT.putExtras(bundle);
							    IT.setAction("gotoDetailSetting");
								getActivity().sendBroadcast(IT);
					    	}
					    }else if((order.equalsIgnoreCase("MOVE")||order.equalsIgnoreCase("MOVEP"))&&name.contains("FP"))
					    {
					    	String PosName = name.substring(name.indexOf("F"));
					    	String ispos=updateMoveList(PosName);
					    	if(ispos.equals("")){
					    		Toast.makeText(getActivity(), "此位置未定义或未勾选", Toast.LENGTH_SHORT).show();
					    	}else{
					    		bundle.putString("PosType", ispos);//当前是P SP.FP					   
							    IT.putExtras(bundle);
							    IT.setAction("gotoDetailSetting");
								getActivity().sendBroadcast(IT);
					    	}
					    }else if((order.equalsIgnoreCase("MOVE")||order.equalsIgnoreCase("MOVEP"))&&name.contains("P"))
					    {
					    	Log.i("mpeng","goto detail !!!");
					    	String PosName = name.substring(name.indexOf("P"));
					    	String ispos=updateMoveList(PosName);
					    	if(ispos.equals("")){
					    		Toast.makeText(getActivity(), "此位置未定义或未勾选", Toast.LENGTH_SHORT).show();
					    	}else{
					    		bundle.putString("PosType", ispos);//当前是P SP.FP					   
							    IT.putExtras(bundle);
							    IT.setAction("gotoDetailSetting");
								getActivity().sendBroadcast(IT);
					    	}
					    }else if(order.equalsIgnoreCase("TWAIT"))
					    {
					    	String timerName = name.substring(name.indexOf("T"));
					    	String ispos=updateTimerList(timerName);
					    	if(ispos.equals("")){
					    		Toast.makeText(getActivity(), "此定时器未定义或未勾选", Toast.LENGTH_SHORT).show();
					    	}else{
					    		
					    		if(!IsDetailFragment)
								{								
									IT.setAction("goPositionPreview");
									bundle.putBoolean("GotoTimer", true);
									bundle.putString("timerandnote", ispos);
								    IT.putExtras(bundle);								
									getActivity().sendBroadcast(IT);
								}else{
									FragmentManager fm  = getActivity().getFragmentManager();
						    	    FragmentDetail rightFragment = (FragmentDetail) fm.findFragmentById(R.id.detail_container);      
						            rightFragment.setRadioBtnCiclk(ispos);	
						        }
					    	}
					    	 
					    }
//					}
//					else
//					{
//						myPos = position;		
//						myAdapte.setSelectItem(myPos);
//						myAdapte.notifyDataSetChanged();
//						if(!IsDetailFragment)
//						{
//							Intent IT =new Intent();
//							IT.setAction("goPositionPreview");
//							getActivity().sendBroadcast(IT);
//						}
//					}

			}catch(Exception e){
				e.printStackTrace();
			}
			}

		});
	}
	

	public String updateMoveList(String posName) {
		try{
			ArrayList<HashMap<String, Object>> devicePositionList  = ArrayListBound.getDevicePositionListData();
		for (int i = 0; i < devicePositionList.size(); i++) {
			String posString=devicePositionList.get(i).get("symbolNameEditText").toString();
			if (!posString.equals("")&&devicePositionList.get(i).get("setItem").toString().trim().equals("1")) {
				if(posName.equalsIgnoreCase(posString)){
				   String signalstr=devicePositionList.get(i).get("signalNameEditText").toString();
				   signalstr=posString+" "+signalstr;
				   return signalstr;
			    }
			}
			
		 }	
	    }catch(Exception e){
		    e.printStackTrace();
	    }
		return "";
	}
	
	public String updateTimerList(String timerName) {
		try{
			ArrayList<HashMap<String, Object>> deviceTimerList  = ArrayListBound.getDeviceTimerListData();
		for (int i = 0; i < deviceTimerList.size(); i++) {
			String posString=deviceTimerList.get(i).get("symbolNameEditText").toString();
			if (!posString.equals("")&&deviceTimerList.get(i).get("setItem").toString().trim().equals("1")) {
				if(timerName.equalsIgnoreCase(posString)){
				   String signalstr=deviceTimerList.get(i).get("signalNameEditText").toString();
				   signalstr=posString+" "+signalstr;
				   return signalstr;
			    }
			}
			
		 }	
	    }catch(Exception e){
		    e.printStackTrace();
	    }
		return "";
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		try{
//		alarmQueryRunnable = new AlarmQueryRunnable(getActivity());
//		Thread a2 = new Thread(alarmQueryRunnable);
//		a2.start();
//		ledrunnable=new ledRunnable(getActivity());
//		Thread a4 = new Thread(ledrunnable);
//		a4.start();
//		posccalmQueryRunnable = new posccalmQueryRunnable(getActivity());
//		Thread a3 = new Thread(posccalmQueryRunnable);
//		a3.start();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		new Thread(){
			public void run() {
				showNote();	
			};
		}.start();
		myAdapte.notifyDataSetChanged();
//		showNote();		
	}
	/* (non-Javadoc)
	 * @see android.app.Fragment#onAttach(android.app.Activity)
	 */
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myActivity= activity;
		((NewPragramActivity) myActivity).setHandler(mHandler);
		
	}
	/**
	 * 
	 * @param msg
	 */
	private void showTost(String msg){
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	
	private void setHighLight(String name)
	{
		myAdapte  = new ListAdapter(getActivity(), mDataSourceList,name);
		listView.setAdapter(myAdapte);
		for(int i =0;i<mDataSourceList.size();i++)
		{
			if(mDataSourceList.get(i).get("noteEditText").toString().contains(name))
			{
				listView.setSelection(i);				
				break;
			}
		}
		myAdapte.notifyDataSetChanged();

	}
	

}
