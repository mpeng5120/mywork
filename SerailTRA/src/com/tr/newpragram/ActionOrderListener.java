package com.tr.newpragram;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dbutils.ArrayListBound;
import com.dbutils.Constans;
import com.explain.TableToBinary;
import com.tr.R;
import com.tr.crash.CrashApplication;
import com.tr.programming.Config;
import com.tr.programming.TR_Programming_Activity;


import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ActionOrderListener implements OnClickListener {
    private View view;
    private List<CheckBox> boxList ;
    private List<View> itemList ;
    private int position;
    private String order_now;
    private ListView mSourceListView;
    private ArrayList<HashMap<String, Object>> appList;
    private com.tr.newpragram.Fragments_Action.MyAdapter myAdaptertest;
    private String PO="";
    private String axisString = "";
    
    private String axlenote="";
    
    private String morderPreviewString="";
    public static ArrayList<HashMap<String, Object>> posList = new ArrayList<HashMap<String, Object>>();// 表格要绑定的list
    
    

    


    public ActionOrderListener(View orderview, int pos, String order_array,ArrayList<HashMap<String, Object>> NcEditList) {
    	// TODO 自动生成的构造函数存根
    	view = orderview;
    	position = pos;
    	order_now = order_array;
    	appList=NcEditList;
        }

    
    public ActionOrderListener(View orderview, int pos, String order_array,ArrayList<HashMap<String, Object>> NcEditList,
    		com.tr.newpragram.Fragments_Action.MyAdapter myAdapter,ListView Lv) {
	// TODO 自动生成的构造函数存根
	view = orderview;
	position = pos;
	order_now = order_array;
	appList=NcEditList;
	myAdaptertest=myAdapter;	
	mSourceListView = Lv;
    }

    public ActionOrderListener(List<View> List1, int pos, String order_array,ArrayList<HashMap<String, Object>> NcEditList,
    		com.tr.newpragram.Fragments_Action.MyAdapter myAdapter,ListView Lv,boolean a) {
	// TODO 自动生成的构造函数存根
    itemList = List1;
	position = pos;
	order_now = order_array;
	appList=NcEditList;
	myAdaptertest=myAdapter;	
	mSourceListView = Lv;
    }
    public ActionOrderListener(List<CheckBox> list , int pos, String order_array,ArrayList<HashMap<String, Object>> NcEditList,
    		com.tr.newpragram.Fragments_Action.MyAdapter myAdapter,ListView Lv) {
	// TODO 自动生成的构造函数存根
    boxList = list;
	position = pos;
	order_now = order_array;
	Log.e("mpeng"," orderNow is "+order_now);
	appList=NcEditList;
	myAdaptertest=myAdapter;	
	mSourceListView = Lv;
    }


/*    public DetailedOrderListener(View orderview, int pos,
			String order_array, ArrayList<HashMap<String, Object>> NcEditList,
			com.tr.programming.Fragments_NCedit1.MyAdapter myAdapter) {
		// TODO Auto-generated constructor stub
    	view = orderview;
    	position = pos;
    	order_now = order_array;
    	appList=NcEditList;
    	myAdaptertest=myAdapter;
	}
*/

	//move命令界面的操作
    public void ncOrderMOVE(View view) {
	try{
		Log.e("mpeng","ncOrderMOVE!!");

    	CheckBox checkbox1=(CheckBox)view.findViewById(R.id.checkBox1);
    	CheckBox checkbox2=(CheckBox)view.findViewById(R.id.checkBox2);
    	CheckBox checkbox3=(CheckBox)view.findViewById(R.id.checkBox3);
    	CheckBox checkbox4=(CheckBox)view.findViewById(R.id.checkBox4);
    	CheckBox checkbox5=(CheckBox)view.findViewById(R.id.checkBox5);
    	
    	RadioButton radioP_MOVE=(RadioButton) view.findViewById(R.id.radioP_MOVE);
		RadioButton radioSP_MOVE=(RadioButton) view.findViewById(R.id.radioSP_MOVE);
		RadioButton radioFP_MOVE=(RadioButton) view.findViewById(R.id.radioFP_MOVE);
		Spinner spinnerP_MOVE=(Spinner)view.findViewById(R.id.spinnerP_MOVE);
		if(radioSP_MOVE.isChecked()||radioFP_MOVE.isChecked())
		{
			PO = spinnerP_MOVE.getSelectedItem().toString().substring(0, 5);
		}else 
		{
			PO = spinnerP_MOVE.getSelectedItem().toString().substring(0, 4);
		}
    	if (checkbox1.isChecked()) {   
    		 if(axisString.equals("")){
 			    axisString+="A1";
 			    axlenote=axlenote+"X、";
 			}
 			else {
 			    axisString+=" A1";
 			    axlenote=axlenote+" X、";
 			}			
 		  }
     	if (checkbox2.isChecked()) {
  		   if(axisString.equals("")){
  			    axisString+="A2";
  			   axlenote=axlenote+"Y、";
  			}
  			else {
  			    axisString+=" A2";
  			   axlenote=axlenote+" Y、";
  			}			
  		  }
     	if (checkbox3.isChecked()) {
  		   if(axisString.equals("")){
  			    axisString+="A4";
  			   axlenote=axlenote+"Z、";
  			}
  			else {
  			    axisString+=" A4";
  			   axlenote=axlenote+" Z、";
  			}			
  		  }
     	if (checkbox4.isChecked()) {
    		   if(axisString.equals("")){
    			    axisString+="A3";
    			  axlenote=axlenote+"H、";
    			}
    			else {
    			    axisString+=" A3";
    			  axlenote=axlenote+" H、";
    			}			
    	}
     	if (checkbox5.isChecked()) {
  		   if(axisString.equals("")){
  			    axisString+="A5";
  			   axlenote=axlenote+"L、";
  			}
  			else {
  			    axisString+=" A5";
  			   axlenote=axlenote+" L、";
  			}			
  		  }
    	if(!axisString.equals("")){
    		axisString=axisString+" ";
    	}
    	if(axlenote.equals("")){
    		axlenote=axlenote+"  ";
    	}

//		PO=TV.getSelectedItem().toString();
    	Fragments_Action.changeModifyStatus(true);
    }catch(Exception e){
    	Fragments_Action.changeModifyStatus(false);
		e.printStackTrace();
	}
    }
    
    
    public void ncOrderIF(View view) {
    	try{
    		String symbolname="";
    		ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualInputListData();
       		for(int i =0;i<boxList.size();i++)
    		{       	
       			CheckBox cb = boxList.get(i);
    			if(cb.isChecked())
    			{
    				Log.e("mpeng","aaaaal he");
    				int position = Integer.parseInt(Fragments_Action.temp3[i+3]);    				
    				symbolname=inputList.get(position).get("symbolNameEditText").toString().trim();
            		axlenote="如果"+cb.getText().toString();
                	morderPreviewString=symbolname+" ON";
    			}
    		}

    }catch(Exception e){
		e.printStackTrace();
	}
    }

    public void ncOrderOUT(View view) {
        try{
        	Log.e("mpeng","ncOrderOUT 11111  :"+boxList.size());
    		ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
    		
    		for(int i =0;i<boxList.size();i++)
    		{
    			Log.e("mpeng","ncOrderOUT 222222 ");
    			if(boxList.get(i).isChecked())
    			{
    				Log.e("mpeng","the is i "+i);
    				String symbolname = outputList.get(Integer.parseInt(Fragments_Action.temp0[i+2])).get("symbolNameEditText").toString().trim();
    				axlenote="输出"+boxList.get(i).getText().toString();
//    				morderPreviewString=symbolname32+" OFF,"+symbolname33+" ON";    				
    				break;
    			}
    				
    				
    		}
//        	if(zscheckbox1.isChecked()){
//        		axlenote="输出"+zscheckbox1.getText().toString();
//            	morderPreviewString=symbolname32+" ON,"+symbolname33+" OFF";
//        	}
//        	if(zscheckbox2.isChecked()){
//        		axlenote="输出"+zscheckbox2.getText().toString();
//        		morderPreviewString=symbolname32+" OFF,"+symbolname33+" ON";
//        	}
        	
        }catch(Exception e){
    		e.printStackTrace();
    	}
        	
        }
    
    public void ncOrderOUTP(View view) {
        try{
        	
//        	CheckBox zjcheckbox1=(CheckBox)view.findViewById(R.id.checkBox1);
//        	CheckBox zjcheckbox2=(CheckBox)view.findViewById(R.id.checkBox2);
//        	CheckBox zjcheckbox3=(CheckBox)view.findViewById(R.id.checkBox3);
//        	CheckBox zjcheckbox4=(CheckBox)view.findViewById(R.id.checkBox4);
//        	CheckBox zjcheckbox5=(CheckBox)view.findViewById(R.id.checkBox5);
//        	RadioButton radioON_zj1=(RadioButton) view.findViewById(R.id.radio_on1);
//        	RadioButton radioOFF_zj1=(RadioButton) view.findViewById(R.id.radio_off1);
//        	RadioButton radioON_zj2=(RadioButton) view.findViewById(R.id.radio_on2);
//        	RadioButton radioOFF_zj2=(RadioButton) view.findViewById(R.id.radio_off2);
//        	RadioButton radioON_zj3=(RadioButton) view.findViewById(R.id.radio_on3);
//        	RadioButton radioOFF_zj3=(RadioButton) view.findViewById(R.id.radio_off3);
//        	RadioButton radioON_zj4=(RadioButton) view.findViewById(R.id.radio_on4);
//        	RadioButton radioOFF_zj4=(RadioButton) view.findViewById(R.id.radio_off4);
//        	RadioButton radioON_zj5=(RadioButton) view.findViewById(R.id.radio_on5);
//        	RadioButton radioOFF_zj5=(RadioButton) view.findViewById(R.id.radio_off5);
//        	if(zjcheckbox1==null){return;}
//        	if(zjcheckbox2==null){return;}
//        	if(zjcheckbox3==null){return;}
//        	if(zjcheckbox4==null){return;}
//        	if(zjcheckbox5==null){return;}
    		ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
    		String symbolname="";
       		for(int i =0;i<itemList.size();i++)
    		{
       			CheckBox zjcheckbox=(CheckBox)itemList.get(i).findViewById(R.id.checkBox1);
            	RadioButton radioON_zj1=(RadioButton) itemList.get(i).findViewById(R.id.radio_on1);
            	RadioButton radioOFF_zj1=(RadioButton) itemList.get(i).findViewById(R.id.radio_off1);
    			if(zjcheckbox.isChecked())
    			{
    				int position = Integer.parseInt(Fragments_Action.temp1[i+3]);
    				symbolname=outputList.get(position).get("symbolNameEditText").toString().trim();
            		if(radioON_zj1.isChecked()){
            			axlenote+=zjcheckbox.getText()+" ON,";
                    	morderPreviewString+=symbolname+" ON,";
            		}else{
            			axlenote+=zjcheckbox.getText()+" OFF,";
                    	morderPreviewString+=symbolname+" OFF,";
            		}   				
    			}
    		}
        	
        	
//        	String symbolname="";
//        	if(zjcheckbox1.isChecked()){
//        		symbolname=outputList.get(34).get("symbolNameEditText").toString().trim();
//        		if(radioON_zj1.isChecked()){
//        			axlenote+=zjcheckbox1.getText()+" ON,";
//                	morderPreviewString+=symbolname+" ON,";
//        		}else{
//        			axlenote+=zjcheckbox1.getText()+" OFF,";
//                	morderPreviewString+=symbolname+" OFF,";
//        		}
//        	}
//        	if(zjcheckbox2.isChecked()){
//        		symbolname=outputList.get(35).get("symbolNameEditText").toString().trim();
//        		if(radioON_zj2.isChecked()){
//        			axlenote+=zjcheckbox2.getText()+" ON,";
//                	morderPreviewString+=symbolname+" ON,";
//        		}else{
//        			axlenote+=zjcheckbox2.getText()+" OFF,";
//                	morderPreviewString+=symbolname+" OFF,";
//        		}
//        	}
//        	if(zjcheckbox3.isChecked()){
//        		symbolname=outputList.get(38).get("symbolNameEditText").toString().trim();
//        		if(radioON_zj3.isChecked()){
//        			axlenote+=zjcheckbox3.getText()+" ON,";
//                	morderPreviewString+=symbolname+" ON,";
//        		}else{
//        			axlenote+=zjcheckbox3.getText()+" OFF,";
//                	morderPreviewString+=symbolname+" OFF,";
//        		}
//        	}
//        	if(zjcheckbox4.isChecked()){
//        		symbolname=outputList.get(36).get("symbolNameEditText").toString().trim();
//        		if(radioON_zj4.isChecked()){
//        			axlenote+=zjcheckbox4.getText()+" ON,";
//                	morderPreviewString+=symbolname+" ON,";
//        		}else{
//        			axlenote+=zjcheckbox4.getText()+" OFF,";
//                	morderPreviewString+=symbolname+" OFF,";
//        		}
//        	}
//        	if(zjcheckbox5.isChecked()){
//        		symbolname=outputList.get(37).get("symbolNameEditText").toString().trim();
//        		if(radioON_zj5.isChecked()){
//        			axlenote+=zjcheckbox5.getText()+" ON,";
//                	morderPreviewString+=symbolname+" ON,";
//        		}else{
//        			axlenote+=zjcheckbox5.getText()+" OFF,";
//                	morderPreviewString+=symbolname+" OFF,";
//        		}
//        	}
        }catch(Exception e){
    		e.printStackTrace();
    	}
        	
        }

    public void ncOrderPARALLEL(View view) {
    try{
    	
    	ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
		String symbolname="";
   		for(int i =0;i<itemList.size();i++)
		{
   			CheckBox zjcheckbox=(CheckBox)itemList.get(i).findViewById(R.id.checkBox1);
        	RadioButton radioON_zj1=(RadioButton) itemList.get(i).findViewById(R.id.radio_on1);
        	RadioButton radioOFF_zj1=(RadioButton) itemList.get(i).findViewById(R.id.radio_off1);
			if(zjcheckbox.isChecked())
			{
				Log.e("mpeng","aaaaaaaa ");
				int position = Integer.parseInt(Fragments_Action.temp2[i+3]);
				symbolname=outputList.get(position).get("symbolNameEditText").toString().trim();
        		if(radioON_zj1.isChecked()){
        			axlenote+=zjcheckbox.getText()+" ON,";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+=zjcheckbox.getText()+" OFF,";
                	morderPreviewString+=symbolname+" OFF,";
        		}   				
			}
		}
    	

//    	CheckBox zjcheckbox1=(CheckBox)view.findViewById(R.id.checkBox1);
//    	CheckBox zjcheckbox2=(CheckBox)view.findViewById(R.id.checkBox2);
//    	CheckBox zjcheckbox3=(CheckBox)view.findViewById(R.id.checkBox3);
//    	CheckBox zjcheckbox4=(CheckBox)view.findViewById(R.id.checkBox4);
//    	RadioButton radioON_zj1=(RadioButton) view.findViewById(R.id.radio_on1);
//    	RadioButton radioOFF_zj1=(RadioButton) view.findViewById(R.id.radio_off1);
//    	RadioButton radioON_zj2=(RadioButton) view.findViewById(R.id.radio_on2);
//    	RadioButton radioOFF_zj2=(RadioButton) view.findViewById(R.id.radio_off2);
//    	RadioButton radioON_zj3=(RadioButton) view.findViewById(R.id.radio_on3);
//    	RadioButton radioOFF_zj3=(RadioButton) view.findViewById(R.id.radio_off3);
//    	RadioButton radioON_zj4=(RadioButton) view.findViewById(R.id.radio_on4);
//    	RadioButton radioOFF_zj4=(RadioButton) view.findViewById(R.id.radio_off4);
//    	if(zjcheckbox1==null){return;}
//    	if(zjcheckbox2==null){return;}
//    	if(zjcheckbox3==null){return;}
//    	if(zjcheckbox4==null){return;}
//		ArrayList<HashMap<String, Object>> outputList = ArrayListBound.getDeviceActualOutputListData();
//		String symbolname="";
//    	if(zjcheckbox1.isChecked()){
//    		symbolname=outputList.get(16).get("symbolNameEditText").toString().trim();
//    		if(radioON_zj1.isChecked()){
//    			axlenote+=zjcheckbox1.getText()+" ON,";
//            	morderPreviewString+=symbolname+" ON,";
//    		}else{
//    			axlenote+=zjcheckbox1.getText()+" OFF,";
//            	morderPreviewString+=symbolname+" OFF,";
//    		}
//    	}
//    	if(zjcheckbox2.isChecked()){
//    		symbolname=outputList.get(17).get("symbolNameEditText").toString().trim();
//    		if(radioON_zj2.isChecked()){
//    			axlenote+=zjcheckbox2.getText()+" ON,";
//            	morderPreviewString+=symbolname+" ON,";
//    		}else{
//    			axlenote+=zjcheckbox2.getText()+" OFF,";
//            	morderPreviewString+=symbolname+" OFF,";
//    		}
//    	}
//    	if(zjcheckbox3.isChecked()){
//    		symbolname=outputList.get(18).get("symbolNameEditText").toString().trim();
//    		if(radioON_zj3.isChecked()){
//    			axlenote+=zjcheckbox3.getText()+" ON,";
//            	morderPreviewString+=symbolname+" ON,";
//    		}else{
//    			axlenote+=zjcheckbox3.getText()+" OFF,";
//            	morderPreviewString+=symbolname+" OFF,";
//    		}
//    	}
//    	if(zjcheckbox4.isChecked()){
//    		symbolname=outputList.get(37).get("symbolNameEditText").toString().trim();
//    		if(radioON_zj4.isChecked()){
//    			axlenote+=zjcheckbox4.getText()+" ON,";
//            	morderPreviewString+=symbolname+" ON,";
//    		}else{
//    			axlenote+=zjcheckbox4.getText()+" OFF,";
//            	morderPreviewString+=symbolname+" OFF,";
//    		}
//    	}
    }catch(Exception e){
		e.printStackTrace();
	}
    }
    
    public void ncOrderSEQUENTIAL(View view) {
    	try{
    		CheckBox zjcheckbox1=(CheckBox)view.findViewById(R.id.checkBox1);
        	CheckBox zjcheckbox2=(CheckBox)view.findViewById(R.id.checkBox2);
        	CheckBox zjcheckbox3=(CheckBox)view.findViewById(R.id.checkBox3);
        	CheckBox zjcheckbox4=(CheckBox)view.findViewById(R.id.checkBox4);
        	CheckBox zjcheckbox5=(CheckBox)view.findViewById(R.id.checkBox5);
        	CheckBox zjcheckbox6=(CheckBox)view.findViewById(R.id.checkBox6);
        	CheckBox zjcheckbox7=(CheckBox)view.findViewById(R.id.checkBox7);
        	CheckBox zjcheckbox8=(CheckBox)view.findViewById(R.id.checkBox8);
        	CheckBox zjcheckbox9=(CheckBox)view.findViewById(R.id.checkBox9);
        	CheckBox zjcheckbox10=(CheckBox)view.findViewById(R.id.checkBox10);
        	RadioButton radioON_zj1=(RadioButton) view.findViewById(R.id.radio_on1);
        	RadioButton radioOFF_zj1=(RadioButton) view.findViewById(R.id.radio_off1);
        	RadioButton radioON_zj2=(RadioButton) view.findViewById(R.id.radio_on2);
        	RadioButton radioOFF_zj2=(RadioButton) view.findViewById(R.id.radio_off2);
        	RadioButton radioON_zj3=(RadioButton) view.findViewById(R.id.radio_on3);
        	RadioButton radioOFF_zj3=(RadioButton) view.findViewById(R.id.radio_off3);
        	RadioButton radioON_zj4=(RadioButton) view.findViewById(R.id.radio_on4);
        	RadioButton radioOFF_zj4=(RadioButton) view.findViewById(R.id.radio_off4);
        	RadioButton radioON_zj5=(RadioButton) view.findViewById(R.id.radio_on5);
        	RadioButton radioOFF_zj5=(RadioButton) view.findViewById(R.id.radio_off5);
        	RadioButton radioON_zj6=(RadioButton) view.findViewById(R.id.radio_on6);
        	RadioButton radioOFF_zj6=(RadioButton) view.findViewById(R.id.radio_off6);
        	RadioButton radioON_zj7=(RadioButton) view.findViewById(R.id.radio_on7);
        	RadioButton radioOFF_zj7=(RadioButton) view.findViewById(R.id.radio_off7);
        	RadioButton radioON_zj8=(RadioButton) view.findViewById(R.id.radio_on8);
        	RadioButton radioOFF_zj8=(RadioButton) view.findViewById(R.id.radio_off8);
        	RadioButton radioON_zj9=(RadioButton) view.findViewById(R.id.radio_on9);
        	RadioButton radioOFF_zj9=(RadioButton) view.findViewById(R.id.radio_off9);
        	RadioButton radioON_zj10=(RadioButton) view.findViewById(R.id.radio_on10);
        	RadioButton radioOFF_zj10=(RadioButton) view.findViewById(R.id.radio_off10);
        	if(zjcheckbox1==null){return;}
        	if(zjcheckbox2==null){return;}
        	if(zjcheckbox3==null){return;}
        	if(zjcheckbox4==null){return;}
        	if(zjcheckbox5==null){return;}
        	if(zjcheckbox6==null){return;}
        	if(zjcheckbox7==null){return;}
        	if(zjcheckbox8==null){return;}
        	if(zjcheckbox9==null){return;}
        	if(zjcheckbox10==null){return;}
        	ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceMiddleOutputListData();
    		String symbolname="";
        	if(zjcheckbox1.isChecked()){
        		symbolname=inputList.get(96).get("symbolNameEditText").toString().trim();
        		if(radioON_zj1.isChecked()){
        			axlenote+="开始检测"+zjcheckbox1.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox1.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox2.isChecked()){
        		symbolname=inputList.get(97).get("symbolNameEditText").toString().trim();
        		if(radioON_zj2.isChecked()){
        			axlenote+="开始检测"+zjcheckbox2.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox2.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox3.isChecked()){
        		symbolname=inputList.get(98).get("symbolNameEditText").toString().trim();
        		if(radioON_zj3.isChecked()){
        			axlenote+="开始检测"+zjcheckbox3.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox3.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox4.isChecked()){
        		symbolname=inputList.get(99).get("symbolNameEditText").toString().trim();
        		if(radioON_zj4.isChecked()){
        			axlenote+="开始检测"+zjcheckbox4.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox4.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox5.isChecked()){
        		symbolname=inputList.get(100).get("symbolNameEditText").toString().trim();
        		if(radioON_zj5.isChecked()){
        			axlenote+="开始检测"+zjcheckbox5.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox5.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox6.isChecked()){
        		symbolname=inputList.get(101).get("symbolNameEditText").toString().trim();
        		if(radioON_zj6.isChecked()){
        			axlenote+="开始检测"+zjcheckbox6.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox6.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox7.isChecked()){
        		symbolname=inputList.get(102).get("symbolNameEditText").toString().trim();
        		if(radioON_zj7.isChecked()){
        			axlenote+="开始检测"+zjcheckbox7.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox7.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox8.isChecked()){
        		symbolname=inputList.get(103).get("symbolNameEditText").toString().trim();
        		if(radioON_zj8.isChecked()){
        			axlenote+="开始检测"+zjcheckbox8.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox8.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox9.isChecked()){
        		symbolname=inputList.get(104).get("symbolNameEditText").toString().trim();
        		if(radioON_zj9.isChecked()){
        			axlenote+="开始检测"+zjcheckbox9.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox9.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
        	if(zjcheckbox10.isChecked()){
        		symbolname=inputList.get(105).get("symbolNameEditText").toString().trim();
        		if(radioON_zj10.isChecked()){
        			axlenote+="开始检测"+zjcheckbox10.getText()+",";
                	morderPreviewString+=symbolname+" ON,";
        		}else{
        			axlenote+="停止检测"+zjcheckbox10.getText()+",";
                	morderPreviewString+=symbolname+" OFF,";
        		}
        	}
    	}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public void ncOrderWAIT(View view) {
    	try{
        	Spinner spinner_WAIT=(Spinner) view.findViewById(R.id.spinner_WAIT);
        	EditText editText_WAIT=(EditText) view.findViewById(R.id.editText_WAIT);
        	RadioButton radiofixed_WAIT=(RadioButton) view.findViewById(R.id.radiofixed_WAIT);
        	RadioButton radioset_WAIT=(RadioButton) view.findViewById(R.id.radioset_WAIT);
        	RadioButton radiopos_WAIT=(RadioButton) view.findViewById(R.id.radiopos_WAIT);
            if(spinner_WAIT==null){return;}
            if(editText_WAIT==null){return;}
            if(radiofixed_WAIT==null){return;}
            if(radioset_WAIT==null){return;}
            if(radiopos_WAIT==null){return;}
        	if (radiofixed_WAIT.isChecked()) {
        		morderPreviewString=editText_WAIT.getText().toString()+"s";
        		order_now="TWAIT";
    		}else if (radioset_WAIT.isChecked()) {
    			if (spinner_WAIT.getSelectedItem()!=null) {
    				morderPreviewString=spinner_WAIT.getSelectedItem().toString();
    			}
    			order_now="TWAIT";
    		}else {
    			morderPreviewString="";
    			order_now="PWAIT";
    		}
        }catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    @Override
    public void onClick(DialogInterface dialog, int which) {
	// TODO 自动生成的方法存根
    	try{
    		Log.d("mpeng","Constans.NCorders_enum.getOrder(order_now) ");
    		Fragments_Action.changeModifyStatus(true);
	switch (Constans.NCorders_enum.getOrder(order_now)) {	
	case OUTP:
		ncOrderOUTP(view);
		HashMap<String, Object> mapOUTP = new HashMap<String, Object>();
		mapOUTP = (HashMap<String, Object>) appList.get(position).clone();
		mapOUTP.put("orderSpinner","OUT");	
		mapOUTP.put("operatText",morderPreviewString.subSequence(0, morderPreviewString.length()-1));	
		mapOUTP.put("noteEditText","输出"+axlenote.subSequence(0, axlenote.length()-1));
		appList.set(position, mapOUTP);	
		break;
	case MOVE:
	case MOVEP:
		ncOrderMOVE(view);
		if(axisString.equals("")){
			Toast.makeText(CrashApplication.getInstance(), "请选中要操作的轴!",
					Toast.LENGTH_SHORT).show();
			try {//不消失
				Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
				field.setAccessible(true);
				field.set(dialog,false);//将mShowing变量设为false，表示对话框已关闭，欺骗android系统
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
			
//		//修改容器内容
		HashMap<String, Object> mapMOVE = new HashMap<String, Object>();
		Log.d("mpeng"," the app list is "+position);
		mapMOVE = (HashMap<String, Object>) appList.get(position).clone();
				
			mapMOVE.put("orderSpinner",order_now);
			mapMOVE.put("operatText",axisString+PO);	
			mapMOVE.put("noteEditText",axlenote.substring(0, axlenote.length()-1)+"移动到"+PO);
		appList.set(position, mapMOVE);

		try {//消失
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );  
            field.setAccessible( true );  
            field.set(dialog, true );//将mShowing变量设为true，表示对话框未关闭     
        }catch (Exception e) {
        	e.printStackTrace(); 
		}
        dialog.dismiss();
	    break;
	    
	case IF:
		ncOrderIF(view);
		HashMap<String, Object> mapIF = new HashMap<String, Object>();
		mapIF = (HashMap<String, Object>) appList.get(position).clone();
		mapIF.put("orderSpinner","IF");	
		mapIF.put("operatText",morderPreviewString);	
		mapIF.put("noteEditText",axlenote);
		appList.set(position, mapIF);
		//更新显示
        
	    break;
	case OUT:
		ncOrderOUT(view);
		HashMap<String, Object> mapOUT = new HashMap<String, Object>();
		mapOUT = (HashMap<String, Object>) appList.get(position).clone();
		mapOUT.put("orderSpinner","OUT");	
		mapOUT.put("operatText",morderPreviewString);	
		mapOUT.put("noteEditText",axlenote);
		appList.set(position, mapOUT);
	    break;    
	case PARALLEL:
		ncOrderPARALLEL(view);
		HashMap<String, Object> mapPARALLEL = new HashMap<String, Object>();
		mapPARALLEL = (HashMap<String, Object>) appList.get(position).clone();
		mapPARALLEL.put("orderSpinner","OUT");	
		mapPARALLEL.put("operatText",morderPreviewString.subSequence(0, morderPreviewString.length()-1));	
		mapPARALLEL.put("noteEditText","输出"+axlenote.subSequence(0, axlenote.length()-1));
		appList.set(position, mapPARALLEL);
	    break;
	case SEQUENTIAL:
		ncOrderSEQUENTIAL(view);
		HashMap<String, Object> mapSEQUENTIAL = new HashMap<String, Object>();
		mapSEQUENTIAL = (HashMap<String, Object>) appList.get(position).clone();
		mapSEQUENTIAL.put("orderSpinner","OUT");	
		mapSEQUENTIAL.put("operatText",morderPreviewString.subSequence(0, morderPreviewString.length()-1));	
		mapSEQUENTIAL.put("noteEditText",axlenote.subSequence(0, axlenote.length()-1));
		appList.set(position, mapSEQUENTIAL);
	    break;
	case WAIT:
		ncOrderWAIT(view);
		HashMap<String, Object> mapWAIT = new HashMap<String, Object>();
		mapWAIT = (HashMap<String, Object>) appList.get(position).clone();
		mapWAIT.put("orderSpinner",order_now);	
		mapWAIT.put("operatText",morderPreviewString.split("<—-—")[0]);	
		mapWAIT.put("noteEditText","等待"+morderPreviewString.split("<—-—")[0]);
		appList.set(position, mapWAIT);
		break;
	default:
		break;
	}
	if(appList.size()==(position+1)){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("addressText",	String.format("%04d", appList.size()));//
		map.put("numSpinner","");
		map.put("orderSpinner","");
		map.put("operatText","");
		map.put("noteEditText","");
		appList.add(map);
	}
	myAdaptertest.setSelectItem(position+1);	
	myAdaptertest.notifyDataSetChanged();

//	if((mSourceListView.getLastVisiblePosition())>= position)
//	{
	Handler UpdateList = new Handler();
	UpdateList.postDelayed(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//mSourceListView.setSelection(mSourceListView.getFirstVisiblePosition()+2);
				mSourceListView.setSelection(position);
			}
			},10);
		
	//}
	
	
    }catch(Exception e){
		e.printStackTrace();
	}
    	
    }
    
}
