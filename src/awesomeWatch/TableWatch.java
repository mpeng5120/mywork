package awesomeWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import wifiRunnablesAndChatlistener.PlcDataQueryRunnable;

import com.dbutils.ArrayListBound;
import com.explain.HexDecoding;
import com.explain.TableToBinary;
import com.explain.TableTranslate;
import com.tr.R;
import com.tr.programming.Fragments_Table1;
import com.tr.programming.Fragments_Table2;
import com.tr.programming.Fragments_Table3;
import com.tr.programming.Fragments_Table4;
import com.tr.programming.Fragments_Table5;
import com.tr.programming.Fragments_Table6;
import com.tr.programming.Fragments_Table7;
import com.tr.programming.Fragments_Table8;
import com.tr.programming.TR_Programming_Activity;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TableWatch {//map��С

	public static int start_index;
	//private static int end_index;
	//private static int flag;
	private static HashMap<String, int[]> deviceOffset = new HashMap<String, int[]>();
	private  ArrayList<HashMap<String, Object>> table_edit_list;
	private HashMap<Integer, Integer> map=new HashMap<Integer, Integer>();
	private byte[] plcDataQuerybyte;

	public static ListView listView;
	//public static ArrayList<HashMap<String, Object>> TableEditList;
	public TableWatch(Activity targetActivity,int flag) {
		try{
        if(flag==1){
        	table_edit_list=ArrayListBound.getTableList1Data();
        }
        if(flag==2){
        	table_edit_list=ArrayListBound.getTableList2Data();
        }
        if(flag==3){
        	table_edit_list=ArrayListBound.getTableList3Data();
        }
        if(flag==4){
        	table_edit_list=ArrayListBound.getTableList4Data();
        }
        if(flag==5){
        	table_edit_list=ArrayListBound.getTableList5Data();
        }
        if(flag==6){
        	table_edit_list=ArrayListBound.getTableList6Data();
        }
        if(flag==7){
        	table_edit_list=ArrayListBound.getTableList7Data();
        }
        if(flag==8){
        	table_edit_list=ArrayListBound.getTableList8Data();
        }
       beginWatch(listView,table_edit_list);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	public static void beginWatch(ListView listView,ArrayList<HashMap<String, Object>> TableEditList) {
		try{
		start_index = listView.getFirstVisiblePosition();
		//end_index = listView.getLastVisiblePosition();
		// ������ʽ�ָ�
		Iterator<HashMap<String, Object>> iterator = TableEditList.iterator();
		while (iterator.hasNext()) {
			HashMap<String, Object> hashMap = iterator.next();

			String[] ifStrings = hashMap
					.get("inputEditText")
					.toString()
					.split(",|��|\\||-|#|>=|<=|>|<|=");//",|��|\\||-|>=|<=|>|<|=\\s+\\d+|\\s+\\d+|=\\d+|#\\d+|#\\s+\\d+"
			String[] outStrings = hashMap.get("outputEditText").toString()
					.split(",|��|-|=");

			// ��ȡ�豸��ƫ�Ƶ�ַ
			// ĿǰDX��DY��ͷ��Ϊ4�ֽڣ������Ϊλ

			// ����IF����
			for (int i = 0; i < ifStrings.length; i++) {

				ifStrings[i] = ifStrings[i].trim();

				if ((ifStrings[i].length() != 0)
						&& (!deviceOffset.containsKey(ifStrings[i]))) {

					int temp[] = TableToBinary
							.deviceSearch(ifStrings[i],1);
					deviceOffset.put(ifStrings[i], temp);
					//System.out.println("ir�����м�����" + ifStrings[i]+"  i0="+temp[0]+"  i1="+temp[1]);
				}
			}
			// ����out���� ���뵽!!!!!!!!!!!!!!!!>= ÿ���������
			for (int i = 0; i < outStrings.length; i++) {
				String tempstring = outStrings[i].trim();

				if ((tempstring.length() != 0)
						&& (!deviceOffset.containsKey(tempstring))) {

					int temp[] = TableToBinary.deviceSearch(tempstring,1);
					deviceOffset.put(tempstring, temp);
					//System.out.println("or�����м�����" + tempstring+"  o0="+temp[0]+"  o1="+temp[1]);
				}
			}
		}

		// ���ڵ�ǰҳ����ʾ
		listView.setOnScrollListener(new OnScrollListener() {
             
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				if (scrollState == OnScrollListener.SCROLL_STATE_FLING) {// ��ʼ����
					// ˢ�½���
					    //System.out.println("��ʼ����");
				}
				if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {// ���ڹ���
					// ˢ�½���
					 //System.out.println("���ڹ���");  
				}
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {// ֹͣ����
					// ˢ�½���
					 //System.out.println("ֹͣ����"); 
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
				start_index = firstVisibleItem;
				//end_index = firstVisibleItem + visibleItemCount;
				//if (end_index >= totalItemCount) {
				//	end_index = totalItemCount - 1;
				//} 
				
			}
		});
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	/**
	 * ��ѯ�豸����plcdata�����е����ݵ�ֵ������ƫ�Ƶ�ַ����
	 * 
	 * @param devicename
	 * @return
	 */
	private String queryDeviceInbyteArray(String devicename) {

		// �ж��豸���Ƿ����
		int[] tempint;
		tempint = deviceOffset.get(devicename.trim());
		if(tempint==null){
			return null;
		}
		if(tempint[0]==0xffff){
			return "";
		}
		// ��D��ͷ��Ϊ4�ֽ�����
		if (devicename.charAt(0) == 'D'||devicename.charAt(0) == 'L') {
			byte[] temp = new byte[4];

			System.arraycopy(plcDataQuerybyte, tempint[0], temp, 0, 4);
			String temppString = "("
					+ String.valueOf(HexDecoding.Array4Toint(temp)) + ")";

			return temppString;

		} else {
			
			String temppString="";
			
			byte[] temp = new byte[4];
			
			System.arraycopy(plcDataQuerybyte, tempint[0], temp, 0, 4);
			
			//HexDecoding.printHexString("���ݶ�", temp);
			
			//System.out.println((int)(temp[tempint[1]%32/8]&0xff));
			
			//System.out.println("2����"+Integer.toBinaryString((int)(temp[tempint[1]%32/8]&0xff)).length());
			
			int i=(int)(temp[tempint[1]%32/8]&0xff);
			
			i=(i>>(tempint[1]%32%8))&0x01;
			
			if(i==1){
				temppString= "("+"1"+ ")";
			}else {
				temppString= "("+"0"+ ")";
			}

			return temppString;
		}

	}

	public void devicePlustheirValue(byte[] plcDataByte) {
		try{
		plcDataQuerybyte = new byte[plcDataByte.length];
		System.arraycopy(plcDataByte, 0, plcDataQuerybyte, 0,
				plcDataByte.length);

		// HexDecoding.printHexString("���ص�PLCdata����", plcDataQuerybyte);

		//System.out.println("���ӵ�" + start_index + "��" + end_index);

		for (int i = 0; i < listView.getLastVisiblePosition()-listView.getFirstVisiblePosition()+1; i++) {
			

			String tempStringif =table_edit_list.get(i+start_index).get("inputEditText").toString();
			String tempStringout =table_edit_list.get(i+start_index).get("outputEditText").toString();
			//String tempStringif =((TextView)(listView.getChildAt(i).findViewById(R.id.inputEditText))).getText().toString().trim();
			//String tempStringout=((TextView)(listView.getChildAt(i).findViewById(R.id.outputEditText))).getText().toString().trim();
			if (tempStringif.trim().length() == 0
					|| tempStringout.trim().length() == 0) {
				continue;
			}
			map.clear();
			// +++++++++++++++++++++++++++++++++++++++++++++++
			// ���洦��IF����
			// +++++++++++++++++++++++++++++++++++++++++++++++
			String finalResult = "";
			String[] ifStrings = tempStringif.split(",|��");
			for (int k = 0; k < ifStrings.length; k++) {
				String temp[] = ifStrings[k].split("\\|");
				for (int j = 0; j < temp.length; j++) {
					if (j == 0) {
						finalResult = finalResult + temp[j].trim();
					} else {
						finalResult = finalResult + "/" + "|" + "/"
								+ temp[j].trim();
					}
				}
				if (k != ifStrings.length - 1)
					finalResult = finalResult + "/" + "," + "/";
			}
			ifStrings = finalResult.split("/");

			StringBuilder ifrowWatch = new StringBuilder();
			for (int k = 0; k < ifStrings.length; k++) { // if����
				if (ifStrings[k].contains("|") || ifStrings[k].contains(","))
					ifrowWatch.append(ifStrings[k]);
				else if (ifStrings[k].contains("-")) {
					if(queryDeviceInbyteArray(ifStrings[k].split("-")[0].trim()).equals("("+"1"+ ")")){
						ifrowWatch.append(ifStrings[k].split("-")[0]);
						ifrowWatch.append("-");
						ifrowWatch.append("("+"0"+ ")");
					}else{
						ifrowWatch.append(ifStrings[k].split("-")[0]);
						ifrowWatch.append("-");
						ifrowWatch.append("("+"1"+ ")");
						map.put(ifrowWatch.length()-(ifStrings[k].split("-")[0]+ "("+"1"+ ")").length()-1, ifrowWatch.length());
					}
					
				} else if (ifStrings[k].contains("#")) {
					String rulString=equationsplit("#", ifStrings[k]);
					ifrowWatch.append(rulString);
					addstartend(rulString,ifrowWatch,"#");
					
				} else if (ifStrings[k].contains(">=")) {
					String rulString=equationsplit(">=", ifStrings[k]);
					ifrowWatch.append(rulString);
					addstartend(rulString,ifrowWatch,">=");
					
				} else if (ifStrings[k].contains("<=")) {
					String rulString=equationsplit("<=", ifStrings[k]);
					ifrowWatch.append(rulString);
					addstartend(rulString,ifrowWatch,"<=");
					
				} else if (ifStrings[k].contains(">")) {
					String rulString=equationsplit(">", ifStrings[k]);
					ifrowWatch.append(rulString);
					addstartend(rulString,ifrowWatch,">");
					
				} else if (ifStrings[k].contains("<")) {
					String rulString=equationsplit("<", ifStrings[k]);
					ifrowWatch.append(rulString);
					addstartend(rulString,ifrowWatch,"<");
					
				} else if (ifStrings[k].contains("=")) {
					String rulString=equationsplit("=", ifStrings[k]);
					ifrowWatch.append(rulString);
					addstartend(rulString,ifrowWatch,"=");
					
				} else {
					ifrowWatch.append(ifStrings[k]+ queryDeviceInbyteArray(ifStrings[k].trim()));
					if(queryDeviceInbyteArray(ifStrings[k].trim()).equals("("+"1"+ ")")){
						map.put(ifrowWatch.length()-(ifStrings[k]+ queryDeviceInbyteArray(ifStrings[k].trim())).length(), ifrowWatch.length()); 
					}
				}

			}
			// ��ÿһ��IF��textview���и��� 
			String text = ifrowWatch.toString(); 
		    SpannableStringBuilder style=new SpannableStringBuilder(text); 
		    Set<Integer> keys = map.keySet(); //���м�ֵ�ļ���		
		    for(Integer key:keys) {	
		    	int value=Integer.valueOf(map.get(key)); 
		    	style.setSpan(new BackgroundColorSpan(Color.GREEN),key,value,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //����ָ��λ��textview�ı�����ɫ
		    }
		    //��ʾ���ӽ��
		    ((TextView)(listView.getChildAt(i).findViewById(R.id.inputEditText))).setText(style);
		   

			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			// ���洦��OUT����
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			map.clear();
			String[] outDevices = tempStringout.split(",|��");// OUT�����ֻ��","��������߼���ϵ

			StringBuilder outrowWatch = new StringBuilder();
			for (int k = 0; k < outDevices.length; k++) {

				//System.out.println("ÿ�����" + outDevices[k]);

				// ÿ��OUT�����У����ܴ���=���ޣ�-���ֱ����MOVEָ�OUTON��OUTOFFָ��
				if (outDevices[k].contains("=")) {
					// MOVEָ��
					String rulString=equationsplit("=", outDevices[k]);
					outrowWatch.append(rulString);
					addstartend(rulString,outrowWatch,"=");
				} else if (outDevices[k].contains("-")) {
					// OUTOFFָ��
					if(queryDeviceInbyteArray(outDevices[k].split("-")[0].trim()).equals("("+"1"+ ")")){
						outrowWatch.append(outDevices[k].split("-")[0]);
						outrowWatch.append("-");
						outrowWatch.append("("+"0"+ ")");
					}else{
						outrowWatch.append(outDevices[k].split("-")[0]);
						outrowWatch.append("-");
						outrowWatch.append("("+"1"+ ")");
						map.put(outrowWatch.length()-(outDevices[k].split("-")[0]+ "("+"1"+ ")").length()-1, outrowWatch.length());
					}
					
				} else {
					// OUTONָ��
					outrowWatch.append(outDevices[k]+ queryDeviceInbyteArray(outDevices[k].trim()));
					if(queryDeviceInbyteArray(outDevices[k].trim()).equals("("+"1"+ ")")){
						map.put(outrowWatch.length()-(outDevices[k]+ queryDeviceInbyteArray(outDevices[k].trim())).length(), outrowWatch.length()); 
					}
				}
			}

			// ��ÿһ��IF��textview���и���
			String outtext = outrowWatch.toString(); 
		    SpannableStringBuilder outstyle=new SpannableStringBuilder(outtext); 
		    Set<Integer> outkeys = map.keySet(); //���м�ֵ�ļ���		
		    for(Integer key:outkeys) {	
		    	int value=Integer.valueOf(map.get(key)); 
		    	outstyle.setSpan(new BackgroundColorSpan(Color.GREEN),key,value,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); //����ָ��λ��textview�ı�����ɫ
		    }
		  //��ʾ���ӽ��
			((TextView)(listView.getChildAt(i).findViewById(R.id.outputEditText))).setText(outstyle);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	private String equationsplit(String mark, String equation) {
		String finalString = "";
		String[] tempStrings = equation.split(mark);
		for (int i = 0; i < tempStrings.length; i++) {
			if (i != 0)
				finalString += mark;
			try {
				Integer.valueOf(tempStrings[i].trim());
				finalString += tempStrings[i].trim();
			} catch (NumberFormatException e) {
				// TODO: handle exception
				finalString += tempStrings[i].trim()
						+ queryDeviceInbyteArray(tempStrings[i].trim());
			}
		}
		return finalString;
	}

	private void addstartend(String finalString,StringBuilder ifrowWatch,String mark){
		try{
		String[] tempStrings = finalString.split(mark);
		int onestart=tempStrings[0].trim().indexOf("(");
		int oneend=tempStrings[0].trim().indexOf(")");
		String onetemp=tempStrings[0].trim().substring(onestart+1, oneend);
		String twotemp=tempStrings[1].trim();
		try{
			int twoint=Integer.valueOf(twotemp);
			int oneint=Integer.valueOf(onetemp);
			if (mark.equals("#")) {
				if(oneint!=twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals(">=")) {
				if(oneint>=twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals("<=")) {
				if(oneint<=twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals(">")) {
				if(oneint>twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals("<")) {
				if(oneint<twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals("=")) {
				if(oneint==twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			}
			
		}catch(NumberFormatException e){
			int twostart=tempStrings[1].trim().indexOf("(");
			int twoend=tempStrings[1].trim().indexOf(")");
			twotemp=tempStrings[1].trim().substring(twostart+1, twoend);
			int twoint=Integer.valueOf(twotemp);
			int oneint=Integer.valueOf(onetemp);
			if (mark.equals("#")) {
				if(oneint!=twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals(">=")) {
				if(oneint>=twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals("<=")) {
				if(oneint<=twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals(">")) {
				if(oneint>twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals("<")) {
				if(oneint<twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			} else if (mark.equals("=")) {
				if(oneint==twoint){
					map.put(ifrowWatch.length()-finalString.length(), ifrowWatch.length()); 
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
