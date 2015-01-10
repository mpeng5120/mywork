package com.explain;

import java.text.DecimalFormat;

import android.R;
import android.R.raw;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Binder;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.explain.TableToBinary;
import com.tr.programming.Fragments_NCedit1;
import com.tr.programming.TR_Programming_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dataInAddress.Define;
import com.dataInAddress.PlcData;
import com.dbutils.ArrayListBound;
import com.dbutils.Constans;

public class NCTranslate {
	private static boolean explainError = true;
	// �ݶ������ϢΪSDO[64],SDI[16]
	private static int axis_startIndex_sdo = 64;
	private static int axis_startIndex_sdi = 16;
	private static String[] yunsuanfu;
	public static String  dname=null;
	public static String  ncindex;
	public static ArrayList<String> noteList = new ArrayList<String>();

	//
	class GotoStack {

		public int[] gotoNo;

		public int[] gotoLabel;

	}

	class CallStack {

		public int[] callNo;

		public int[] callSub;
	}

	// ������ÿһ����¼Ϊ����12��byte����
	private static ArrayList<byte[]> binary_code = new ArrayList<byte[]>(){

		@Override
		public boolean add(byte[] object) {
			// TODO Auto-generated method stub
			HexDecoding.printHexString("ÿ�����ķ���", object);
			return super.add(object);
		}
		
	};

	// ��ʽת��
	static DecimalFormat format = new DecimalFormat("####");

	/**
	 * �������յ�byte���飬���͸���λ��
	 * 
	 * @return ���ݵ�byte����
	 */
	public static byte[] getMesHexCode() {
		Iterator<byte[]> it = binary_code.iterator();
		// �������ɵ������Ĵ�С��ȷ�����͵�byte����
		byte[] message = new byte[binary_code.size() * 12];
		// ������
		int counter = 0;

		while (it.hasNext()) {

			System.arraycopy(it.next(), 0, message, counter, 12);
			counter += 12;
		}
		return message;
	}

	/**
	 * ��������ǰ������ô˺�������nc�����ͳɿ��Է��͸���λ��������
	 */
	public static int[] beginTranslate(Activity temp,String[] yunsuanfutemp) {
		// checkNCCode(temp);

		 int  index=0;
		 int and1=ArrayListBound.getNCeditList1Data().size();
		 int and2=and1+ArrayListBound.getNCeditList2Data().size();
		 int and3=and2+ArrayListBound.getNCeditList3Data().size();
		 int and4=and3+ArrayListBound.getNCeditList4Data().size();
		 int and5=and4+ArrayListBound.getNCeditList5Data().size();
		 int and6=and5+ArrayListBound.getNCeditList6Data().size();
		 int and7=and6+ArrayListBound.getNCeditList7Data().size();
		 int[]  alllength=new int[and7+ArrayListBound.getNCeditList8Data().size()];
		if (true) {
			yunsuanfu=yunsuanfutemp;
			
			format.setMinimumIntegerDigits(8);// ��ʾ����8λ��

			if (!binary_code.isEmpty()) {
				binary_code.clear();
			}

			if(TR_Programming_Activity.noteflag==1||TR_Programming_Activity.noteflag==0){		
			Iterator<HashMap<String, Object>> it1 = ArrayListBound
					.getNCeditList1Data().iterator();
			while (it1.hasNext()) {
				index++;
				HashMap<String, Object> map = it1.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��1�е�ַΪ"+String.format("%04d", (index-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��1�е�ַΪ"+String.format("%04d", (index-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��1�е�ַΪ"+String.format("%04d", (index-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��1�е�ַΪ"+String.format("%04d", (index-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}

			}
			if(TR_Programming_Activity.noteflag==2||TR_Programming_Activity.noteflag==0){
			Iterator<HashMap<String, Object>> it2 = ArrayListBound
					.getNCeditList2Data().iterator();

			while (it2.hasNext()) {
				index++;
				//System.out.println("index="+index);
				HashMap<String, Object> map = it2.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��2�е�ַΪ"+String.format("%04d", (index-and1-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��2�е�ַΪ"+String.format("%04d", (index-and1-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��2�е�ַΪ"+String.format("%04d", (index-and1-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��2�е�ַΪ"+String.format("%04d", (index-and1-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
			if(TR_Programming_Activity.noteflag==3||TR_Programming_Activity.noteflag==0){
			Iterator<HashMap<String, Object>> it3 = ArrayListBound
					.getNCeditList3Data().iterator();

			while (it3.hasNext()) {
				index++;
				HashMap<String, Object> map = it3.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��3�е�ַΪ"+String.format("%04d", (index-and2-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��3�е�ַΪ"+String.format("%04d", (index-and2-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��3�е�ַΪ"+String.format("%04d", (index-and2-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��3�е�ַΪ"+String.format("%04d", (index-and2-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
			if(TR_Programming_Activity.noteflag==4||TR_Programming_Activity.noteflag==0){	
			Iterator<HashMap<String, Object>> it4 = ArrayListBound
					.getNCeditList4Data().iterator();

			while (it4.hasNext()) {
				index++;
				HashMap<String, Object> map = it4.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��4�е�ַΪ"+String.format("%04d", (index-and3-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��4�е�ַΪ"+String.format("%04d", (index-and3-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��4�е�ַΪ"+String.format("%04d", (index-and3-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��4�е�ַΪ"+String.format("%04d", (index-and3-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
			if(TR_Programming_Activity.noteflag==5||TR_Programming_Activity.noteflag==0){
				Log.d("mpeng","translate 55555555555555555");
			Iterator<HashMap<String, Object>> it5 = ArrayListBound
					.getNCeditList5Data().iterator();

			while (it5.hasNext()) {
				index++;
				HashMap<String, Object> map = it5.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��5�е�ַΪ"+String.format("%04d", (index-and4-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��5�е�ַΪ"+String.format("%04d", (index-and4-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��5�е�ַΪ"+String.format("%04d", (index-and4-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��5�е�ַΪ"+String.format("%04d", (index-and4-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
			if(TR_Programming_Activity.noteflag==6||TR_Programming_Activity.noteflag==0){
				Log.d("mpeng","translate 666666666666666666");
			Iterator<HashMap<String, Object>> it6 = ArrayListBound
					.getNCeditList6Data().iterator();

			while (it6.hasNext()) {
				index++;
				HashMap<String, Object> map = it6.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��6�е�ַΪ"+String.format("%04d", (index-and5-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��6�е�ַΪ"+String.format("%04d", (index-and5-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��6�е�ַΪ"+String.format("%04d", (index-and5-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��6�е�ַΪ"+String.format("%04d", (index-and5-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
			if(TR_Programming_Activity.noteflag==7||TR_Programming_Activity.noteflag==0){
				Log.d("mpeng","translate   777777777777777");
			Iterator<HashMap<String, Object>> it7 = ArrayListBound
					.getNCeditList7Data().iterator();

			while (it7.hasNext()) {
				index++;
				HashMap<String, Object> map = it7.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��7�е�ַΪ"+String.format("%04d", (index-and6-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��7�е�ַΪ"+String.format("%04d", (index-and6-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��7�е�ַΪ"+String.format("%04d", (index-and6-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��7�е�ַΪ"+String.format("%04d", (index-and6-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
			if(TR_Programming_Activity.noteflag==8||TR_Programming_Activity.noteflag==0){
			Log.d("mpeng","translate 88888888888");
			Iterator<HashMap<String, Object>> it8 = ArrayListBound
					.getNCeditList8Data().iterator();

			while (it8.hasNext()) {
				index++;
				HashMap<String, Object> map = it8.next();
				int flag=codeExplain(map.get("numSpinner").toString(),
						map.get("orderSpinner").toString(),
						map.get("operatText").toString());
				if(flag==-1){
					ncindex="ҳ��8�е�ַΪ"+String.format("%04d", (index-and7-1));
					alllength[0]=-1;
					return alllength;
				}else if(flag==1){
					ncindex="ҳ��8�е�ַΪ"+String.format("%04d", (index-and7-1));
					alllength[0]=1;
					return alllength;
				}else if(flag==-2){
					ncindex="ҳ��8�е�ַΪ"+String.format("%04d", (index-and7-1));
					alllength[0]=-2;
					return alllength;
				}else if(flag==-3){
					ncindex="ҳ��8�е�ַΪ"+String.format("%04d", (index-and7-1));
					alllength[0]=-3;
					return alllength;
				}
				alllength[index-1]=binary_code.size() * 12;
				//System.out.println("8alllength["+(index-1)+"]="+alllength[index-1]);
			}
			}
		}
 		return alllength;

	}

	/**
	 * @param temp
	 * 
	 ********************************************************** 
	 * @Title: checkNCCode
	 * @Description: ����д��NC������б�Ҫ���߼��﷨���
	 * @return void
	 * @throws
	 ********************************************************** 
	 */
	private static boolean checkNCCode(Activity temp) {
		// ���־λ
		explainError = true;

		ArrayList<Integer> ArraList_num_S = new ArrayList<Integer>();// ��Ű���S��������index
		ArrayList<Integer> ArrayList_order_RET = new ArrayList<Integer>();// ��Ű���RET��������index
		HashMap<String, Integer> Map_num_L = new HashMap<String, Integer>();// ���END֮ǰ��Ű���L��������index
		ArrayList<String> ArrayList_operat_L = new ArrayList<String>();// ���END֮ǰ��������L��������index

		ArrayList<String> ArrayList_operat = new ArrayList<String>();

		ArrayList<HashMap<String, Object>> NcEditList1 = ArrayListBound
				.getNCeditList1Data();
		ArrayList<HashMap<String, Object>> NcEditList2 = ArrayListBound
				.getNCeditList2Data();
		ArrayList<HashMap<String, Object>> NcEditList3 = ArrayListBound
				.getNCeditList3Data();
		ArrayList<HashMap<String, Object>> NcEditList4 = ArrayListBound
				.getNCeditList4Data();
		ArrayList<HashMap<String, Object>> NcEditList5 = ArrayListBound
				.getNCeditList5Data();
		ArrayList<HashMap<String, Object>> NcEditList6 = ArrayListBound
				.getNCeditList6Data();
		ArrayList<HashMap<String, Object>> NcEditList7 = ArrayListBound
				.getNCeditList7Data();
		ArrayList<HashMap<String, Object>> NcEditList8 = ArrayListBound
				.getNCeditList8Data();
		int indexOfEND = 0;
		
		for (int i = 0; i < NcEditList1.size(); i++) {
			String num = NcEditList1.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList1.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList2.size(); i++) {
			String num = NcEditList2.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList2.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList3.size(); i++) {
			String num = NcEditList3.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList3.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList4.size(); i++) {
			String num = NcEditList4.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList4.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList5.size(); i++) {
			String num = NcEditList5.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList5.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList6.size(); i++) {
			String num = NcEditList6.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList6.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList7.size(); i++) {
			String num = NcEditList7.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList7.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		for (int i = 0; i < NcEditList8.size(); i++) {
			String num = NcEditList8.get(i).get("numSpinner").toString().trim();// ���
			String order = NcEditList8.get(i).get("orderSpinner").toString().trim();// ����
            if (!order.equals("")) {
				ArrayList_operat.add(order);
			}
            if (num.startsWith("S")) {
				ArraList_num_S.add(i);
			} else if (num.startsWith("L")) {
				Map_num_L.put(num, i);
			}
            if (order.equals("RET")) {
				ArrayList_order_RET.add(i);
			}
         }
		if (!ArrayList_operat.contains("END")) {
			System.out.println("����ȱ��END");
			return false;
		} else {
			indexOfEND = ArrayList_operat.indexOf("END");
			System.out.println(indexOfEND);
		}

		for (int i = 0; i < NcEditList1.size(); i++) {
			String operat = NcEditList1.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList2.size(); i++) {
			String operat = NcEditList2.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList3.size(); i++) {
			String operat = NcEditList3.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList4.size(); i++) {
			String operat = NcEditList4.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList5.size(); i++) {
			String operat = NcEditList5.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList6.size(); i++) {
			String operat = NcEditList6.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList7.size(); i++) {
			String operat = NcEditList7.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		for (int i = 0; i < NcEditList8.size(); i++) {
			String operat = NcEditList8.get(i).get("operatText").toString()
					.trim();// ����
			if (i < indexOfEND && operat.startsWith("L")) {
				ArrayList_operat_L.add(operat);
			}
		}
		

		// ��ʼ������
		if (indexOfEND > ArraList_num_S.get(0).intValue()) {
			Toast.makeText(temp, "����S��END֮ǰ", Toast.LENGTH_SHORT).show();
			System.out.println("����S��END֮ǰ");
			return false;
		}

		if (ArraList_num_S.size() != ArrayList_order_RET.size()) {
			Toast.makeText(temp, "����S��RET��Ŀ��ƥ��", Toast.LENGTH_SHORT).show();
			System.out.println("����S��RET��Ŀ��ƥ��");
			return false;
		} else {
			for (int i = 0; i < ArraList_num_S.size(); i++) {

				if (ArraList_num_S.get(i).intValue() > ArrayList_order_RET.get(
						i).intValue()) {
					System.out.println("����" + "ĳ��S����RET֮��");
					return false;
				} else {
					for (int j = i + 1; j < ArraList_num_S.size(); j++) {
						if (ArraList_num_S.get(j).intValue() <= ArrayList_order_RET
								.get(i).intValue()) {
							Toast.makeText(temp,
									"����" + "��" + i + "��" + "S RET����Ƕ��",
									Toast.LENGTH_SHORT).show();
							System.out.println("����" + "��" + i + "��"
									+ "S RET����Ƕ��");
							return false;
						}
					}
				}

			}
		}

		for (int i = 0; i < ArrayList_operat_L.size(); i++) {
			if (Map_num_L.get(ArrayList_operat_L.get(i)).intValue() > indexOfEND) {
				Toast.makeText(temp, "���� ������GOTO����END֮��", Toast.LENGTH_SHORT)
						.show();
				System.out.println("���� ������GOTO����END֮��");
				return false;
			}
		}
		return true;

	}

	// label��Subroutine������к�
	/**
	 * �ж��Ƿ����кű�ǣ��еĻ�����һ��tableָ��
	 * 
	 * @param numorder
	 *            ncָ��ǰ���кź�����ţ�������ת���
	 */
	private static int labelOrSubroutine_Check(String numorder) {
		try{
		if (!numorder.equals("") || numorder != null) {
			if (numorder.contains("L")) {
				binary_code.add(TableToBinary.getLABELbytes(Integer
						.parseInt(numorder.substring(1))));
			} else if (numorder.contains("S")) {
				binary_code.add(TableToBinary.getSUBRoutinebytes(Integer
						.parseInt(numorder.substring(1))));
			}else if (numorder.contains("Q")) {
				binary_code.add(TableToBinary.getQbytes(Integer
						.parseInt(numorder.substring(1))));
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		return -2;
	}
		return 0;
	}

	private static String replace_nc(String rege,String str,String value){
		try{
		   String regex = "[^A-Za-z0-9]"+rege+"[^A-Za-z0-9]";
		   str=" "+str+" ";
		   Pattern pat = Pattern.compile(regex);  
		   Matcher matcher = pat.matcher(str); 
		   int count=0;
		   while (matcher.find()) { 
			  int length=value.length()-rege.length();
			 /* System.out.println("matcher.start()+count*length=="+(matcher.start()+count*length));
			  System.out.println("matcher.end()+count*length=="+(matcher.end()+count*length));*/
			  String qtemp = str.substring(0,matcher.start()+count*length);
			  String htemp = str.substring(matcher.end()+count*length,str.length());
		     String temp = str.substring(matcher.start()+count*length,matcher.end()+count*length);
		     String rptemp=temp.charAt(0)+value+temp.charAt(temp.length()-1);
		     str = qtemp+rptemp+htemp;
		    /* System.out.println("qtemp=="+qtemp);
		     System.out.println("rptemp=="+rptemp);
		     System.out.println("htemp=="+htemp);
		     System.out.println("str=="+str);*/
		     count++;
		   } 
	} catch (Exception e) {
		e.printStackTrace();
	}
		   return str.trim();
	}
	private static int codeExplain(String numorder, String order,
			String operation) {
		numorder=numorder.trim();
		order=order.trim();
		operation=operation.trim();
		String alloperation=operation;
		String note="";
		// ����Ƿ�����кţ�������
		int labelOrSubroutine=labelOrSubroutine_Check(numorder);
		if(labelOrSubroutine!=0){
			dname=numorder;
			return labelOrSubroutine;
		}
		if (!order.equals("") && order != null) {
			try {
				switch (Constans.NCorders_enum.getOrder(order)) {
				// MOVEָ����ͷ���
				case MOVE:
					// through true ���п�ʼ
					operation = operation.trim();
					char[] chs=operation.toCharArray();
					char ch=' ';
					int allA=0;
					String axlenote="";
					if (operation.contains("SP")) {
						for(int i=0;i<chs.length;i++){
							if(chs[i]=='A'){
								ch=chs[i+1];
								int index=(int)ch-49;
								if(index==0){
									allA=allA+(int) (1*Math.pow(2, 24));
									axlenote=axlenote+"X��";
								}
								if(index==1){
									allA=allA+(int) (1*Math.pow(2, 25));
									axlenote=axlenote+"Y��";
								}
								if(index==2){
									allA=allA+(int) (1*Math.pow(2, 26));
									axlenote=axlenote+"H��";
								}
								if(index==3){
									allA=allA+(int) (1*Math.pow(2, 27));
									axlenote=axlenote+"Z��";
								}
								if(index==4){
									allA=allA+(int) (1*Math.pow(2, 28));
									axlenote=axlenote+"L��";
								}
							}
							if(chs[i]=='S'||chs[i]=='s'){
								ch=chs[i];
								operation=operation.substring(i);
								break;
							}
						}
					}else if (operation.contains("FP")) {
						for(int i=0;i<chs.length;i++){
							if(chs[i]=='A'){
								ch=chs[i+1];
								int index=(int)ch-49;
								if(index==0){
									allA=allA+(int) (1*Math.pow(2, 24));
									axlenote=axlenote+"X��";
								}
								if(index==1){
									allA=allA+(int) (1*Math.pow(2, 25));
									axlenote=axlenote+"Y��";
								}
								if(index==2){
									allA=allA+(int) (1*Math.pow(2, 26));
									axlenote=axlenote+"H��";
								}
								if(index==3){
									allA=allA+(int) (1*Math.pow(2, 27));
									axlenote=axlenote+"Z��";
								}
								if(index==4){
									allA=allA+(int) (1*Math.pow(2, 28));
									axlenote=axlenote+"L��";
								}
							}
							if(chs[i]=='F'||chs[i]=='f'){
								ch=chs[i];
								operation=operation.substring(i);
								break;
							}
						}
					}else if (operation.contains("P")) {
						for(int i=0;i<chs.length;i++){
							if(chs[i]=='A'){
								ch=chs[i+1];
								int index=(int)ch-49;
								if(index==0){
									allA=allA+(int) (1*Math.pow(2, 24));
									axlenote=axlenote+"X��";
								}
								if(index==1){
									allA=allA+(int) (1*Math.pow(2, 25));
									axlenote=axlenote+"Y��";
								}
								if(index==2){
									allA=allA+(int) (1*Math.pow(2, 26));
									axlenote=axlenote+"H��";
								}
								if(index==3){
									allA=allA+(int) (1*Math.pow(2, 27));
									axlenote=axlenote+"Z��";
								}
								if(index==4){
									allA=allA+(int) (1*Math.pow(2, 28));
									axlenote=axlenote+"L��";
								}
							}
							if(chs[i]=='P'||chs[i]=='p'){
								ch=chs[i];
								operation=operation.substring(i);
								break;
							}
						}
					}
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation, null);
						String noteText=TableToBinary.noteText;
						//String note1=replace_nc(operation,alloperation,noteText);
						if(axlenote.length()==0){
							noteList.add("�ƶ���"+noteText);
						}else{
							noteList.add(axlenote.substring(0, axlenote.length()-1)+"�ƶ���"+noteText);
						}
						
						return 0;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(true));
					// move�㸳ֵ,move������Ԫ�������ֻ��һ���豸����λ�ã�
					int pointno=0;
					if(operation.startsWith("F")||operation.startsWith("f")){
						pointno=1;
					}else{
						pointno=Integer.parseInt(operation.trim().substring(operation.indexOf("P")+1));
					}
					if(pointno==-1){
						dname=operation;
						return -1;
					}
					if(pointno==0xffff){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getMOVE_IMMBytes(pointno,
							PlcData.getSDOoffsetAddress(axis_startIndex_sdo),ch,allA));
					// through true ����δ����
					binary_code.add(TableToBinary.getTHROUGHBytes(true));
					// CP=(IMM) �����Ϣд�� SDI
					binary_code.add(TableToBinary.getCP_Bytes(
							Constans.Code_CPEQ_IMM,
							PlcData.getSDIoffsetAddress(axis_startIndex_sdi),
							pointno,ch,allA));
					// through false ���н���
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					// �����λ����Ϣ
					binary_code.add(TableToBinary.getMOVE_IMMBytes(0,
							PlcData.getSDOoffsetAddress(axis_startIndex_sdo),ch,0));
					// operation.indexOf(0);
					break;
				case MOVEP:
					// through false һ�仰����
					operation = operation.trim();
					chs=operation.toCharArray();
					ch=' ';
					allA=0;
					axlenote="";
					if (operation.contains("SP")) {
						for(int i=0;i<chs.length;i++){
							if(chs[i]=='A'){
								ch=chs[i+1];
								int index=(int)ch-49;
								if(index==0){
									allA=allA+(int) (1*Math.pow(2, 24));
									axlenote=axlenote+"X��";
								}
								if(index==1){
									allA=allA+(int) (1*Math.pow(2, 25));
									axlenote=axlenote+"Y��";
								}
								if(index==2){
									allA=allA+(int) (1*Math.pow(2, 26));
									axlenote=axlenote+"H��";
								}
								if(index==3){
									allA=allA+(int) (1*Math.pow(2, 27));
									axlenote=axlenote+"Z��";
								}
								if(index==4){
									allA=allA+(int) (1*Math.pow(2, 28));
									axlenote=axlenote+"L��";
								}
							}
							if(chs[i]=='S'||chs[i]=='s'){
								ch=chs[i];
								operation=operation.substring(i);
								break;
							}
						}
					}else if (operation.contains("FP")) {
						for(int i=0;i<chs.length;i++){
							if(chs[i]=='A'){
								ch=chs[i+1];
								int index=(int)ch-49;
								if(index==0){
									allA=allA+(int) (1*Math.pow(2, 24));
									axlenote=axlenote+"X��";
								}
								if(index==1){
									allA=allA+(int) (1*Math.pow(2, 25));
									axlenote=axlenote+"Y��";
								}
								if(index==2){
									allA=allA+(int) (1*Math.pow(2, 26));
									axlenote=axlenote+"H��";
								}
								if(index==3){
									allA=allA+(int) (1*Math.pow(2, 27));
									axlenote=axlenote+"Z��";
								}
								if(index==4){
									allA=allA+(int) (1*Math.pow(2, 28));
									axlenote=axlenote+"L��";
								}
							}
							if(chs[i]=='F'||chs[i]=='f'){
								ch=chs[i];
								operation=operation.substring(i);
								break;
							}
						}
					}else if (operation.contains("P")) {
						for(int i=0;i<chs.length;i++){
							if(chs[i]=='A'){
								ch=chs[i+1];
								int index=(int)ch-49;
								if(index==0){
									allA=allA+(int) (1*Math.pow(2, 24));
									axlenote=axlenote+"X��";
								}
								if(index==1){
									allA=allA+(int) (1*Math.pow(2, 25));
									axlenote=axlenote+"Y��";
								}
								if(index==2){
									allA=allA+(int) (1*Math.pow(2, 26));
									axlenote=axlenote+"H��";
								}
								if(index==3){
									allA=allA+(int) (1*Math.pow(2, 27));
									axlenote=axlenote+"Z��";
								}
								if(index==4){
									allA=allA+(int) (1*Math.pow(2, 28));
									axlenote=axlenote+"L��";
								}
							}
							if(chs[i]=='P'||chs[i]=='p'){
								ch=chs[i];
								operation=operation.substring(i);
								break;
							}
						}
					}
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation, null);
						String noteText=TableToBinary.noteText;
						//String note1=replace_nc(operation,alloperation,noteText);
						if(axlenote.length()==0){
							noteList.add("�ƶ���"+noteText);
						}else{
							noteList.add(axlenote.substring(0, axlenote.length()-1)+"�ƶ���"+noteText);
						}
						return 0;
					}
					int temp2=0;
					if(operation.startsWith("F")||operation.startsWith("f")){
						temp2=1;
					}else{
						temp2=Integer.parseInt(operation.trim().substring(operation.indexOf("P")+1));
					}
					
					if(temp2==-1){
						dname=operation;
						return -1;
					}
					if(temp2==0xffff){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					// move�㸳ֵ
					binary_code.add(TableToBinary.getMOVE_IMMBytes(
							TableToBinary.searchAddress(operation, false),
							PlcData.getSDOoffsetAddress(axis_startIndex_sdo),ch,allA));
					break;
				case OUT:
					// through false һ�仰����
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					note="";
					String[] outDevices = operation.split(",");// OUT�����ֻ��","��������߼���ϵ
					for (int i = 0; i < outDevices.length; i++) {
						// ÿ��OUT�����У����ܴ���=���ޣ�-���ֱ����MOVEָ�OUTON��OUTOFFָ��
						outDevices[i]=outDevices[i].trim();
						if (outDevices[i].contains("=")) {
							// MOVEָ��Ƿ������������ж�ʹ��trycatch
							try {
								int temp = Integer.valueOf(outDevices[i]
										.split("=")[1].trim());
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(outDevices[i].split("=")[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(outDevices[i].split("=")[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(outDevices[i].split("=")[0].trim(),note, noteText);
									}
									
								}else if(TR_Programming_Activity.noteflag==0){
								int temp1=TableToBinary.searchAddress(
										outDevices[i].split("=")[0]
												.trim(), true);
								if(temp1==-1){
									dname=outDevices[i]
											.split("=")[0].trim();
									return -1;
								}
								if(temp1==0xffff){
									dname=outDevices[i]
											.split("=")[0].trim();
									return 1;
								}
								// ˵��Ϊ������
								binary_code.add(TableToBinary.getMOVE_IMMBytes(
										temp, TableToBinary.searchAddress(
												outDevices[i].split("=")[0]
														.trim(), true)));
								}
							} catch (NumberFormatException e) {
								// TODO: handle exception
								// ˵���Ƿ�������
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(outDevices[i].split("=")[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(outDevices[i].split("=")[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(outDevices[i].split("=")[0].trim(),note, noteText);
									}
									TableToBinary.searchAddress(outDevices[i].split("=")[1].trim(), null);
									String noteText1=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(outDevices[i].split("=")[1].trim(),alloperation, noteText1);
									}else{
										note=replace_nc(outDevices[i].split("=")[1].trim(),note, noteText1);
									}
									
								}else if(TR_Programming_Activity.noteflag==0){
								int temp1=TableToBinary.searchAddress(
										outDevices[i].split("=")[0]
												.trim(), true);
								if(temp1==-1){
									dname=outDevices[i]
											.split("=")[0].trim();
									return -1;
								}
								if(temp1==0xffff){
									dname=outDevices[i]
											.split("=")[0].trim();
									return 1;
								}
								int temp12=TableToBinary.searchAddress(
										outDevices[i].split("=")[1]
												.trim(), true);
								if(temp12==-1){
									dname=outDevices[i]
											.split("=")[1].trim();
									return -1;
								}
								if(temp12==0xffff){
									dname=outDevices[i]
											.split("=")[1].trim();
									return 1;
								}
								binary_code.add(TableToBinary.getMOVE_Bytes(
										TableToBinary.searchAddress(
												outDevices[i].split("=")[1]
														.trim(), true),
										TableToBinary.searchAddress(
												outDevices[i].split("=")[0]
														.trim(), true)));
							}
							}
						} else if (outDevices[i].contains("OFF")) {
							// OUTOFFָ��
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								if(outDevices.length==2){
				  	  	    		  int address1=TableToBinary.searchAddress(outDevices[0].split("\\s+")[0].trim(), false);
				  	  	    	      int address2=TableToBinary.searchAddress(outDevices[1].split("\\s+")[0].trim(), false);
				  	  	    	     if((address1==32&&address2==33)||(address1==33&&address2==32)){ 
				  	  	    	    	  continue;
				  	  	    	      }
				  	  	    		}
								
								TableToBinary.searchAddress(outDevices[i].split("\\s+")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								int addressjc=TableToBinary.searchAddress(outDevices[i].split("\\s+")[0].trim(), false);
								if(addressjc==96||addressjc==97||addressjc==98||addressjc==99||addressjc==100
										||addressjc==101||addressjc==102||addressjc==103||addressjc==104||addressjc==105){
									note=note+"ֹͣ���"+noteText+",";
									continue;
								}
								if(note.equals("")){
									note=replace_nc(outDevices[i].split("\\s+")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(outDevices[i].split("\\s+")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int[] temp = TableToBinary
									.deviceSearchAddress(outDevices[i]
											.split("\\s+")[0].trim());
							
							if(temp[0]==-1&&temp[1]==-1){
								dname=outDevices[i]
										.split("\\s+")[0].trim();
								return -1;
							}
							if(temp[0]==0xffff&&temp[1]==0xffff){
								dname=outDevices[i]
										.split("\\s+")[0].trim();
								return 1;
							}
							binary_code.add(TableToBinary.getOUT_OFFBytes(
									temp[0], temp[1] % 32));
							}
						} else {
							// OUTONָ��
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(outDevices[i].split("\\s+")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(outDevices.length==2){
				  	  	    		  int address1=TableToBinary.searchAddress(outDevices[0].split("\\s+")[0].trim(), false);
				  	  	    	      int address2=TableToBinary.searchAddress(outDevices[1].split("\\s+")[0].trim(), false);
				  	  	    	     if((address1==32&&address2==33)||(address1==33&&address2==32)){  	  	    	    	 
				  	  	    	    	note= noteText;
				  	  	    	    	  break;
				  	  	    	      }
				  	  	    		}
								int addressjc=TableToBinary.searchAddress(outDevices[i].split("\\s+")[0].trim(), false);
								if(addressjc==96||addressjc==97||addressjc==98||addressjc==99||addressjc==100
										||addressjc==101||addressjc==102||addressjc==103||addressjc==104||addressjc==105){
									note=note+"��ʼ���"+noteText+",";
									continue;
								}
								
								if(note.equals("")){
									note=replace_nc(outDevices[i].split("\\s+")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(outDevices[i].split("\\s+")[0].trim(),note, noteText);
								}
								//System.out.println("note==="+note);
							}else if(TR_Programming_Activity.noteflag==0){
							int[] temp = TableToBinary
									.deviceSearchAddress(outDevices[i]
											.split("\\s+")[0].trim());
							if(temp[0]==-1&&temp[1]==-1){
								dname=outDevices[i]
										.split("\\s+")[0].trim();
								return -1;
							}
							if(temp[0]==0xffff&&temp[1]==0xffff){
								dname=outDevices[i]
										.split("\\s+")[0].trim();
								return 1;
							}
							binary_code.add(TableToBinary.getOUT_ONBytes(
									temp[0], temp[1] % 32));
							}
						}
					}
					if(note.contains("��ʼ���")||note.contains("ֹͣ���")){
						noteList.add(note.substring(0, note.length()-1));
					}else{
						noteList.add("���"+note);
					}
					
					break;
				case IF:
					// through false һ�仰����
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					note="";
					// ���������
					// ת��Ϊ�ַ�����,�ַ��������ѭ������
					/*char[] operationChar = new char[operation.toCharArray().length];
					System.arraycopy(operation.toCharArray(), 0, operationChar,
							0, operation.toCharArray().length);
					int block_start = 0;
					String strtemp = "";
					int i = 0;
					for (i = 0; i < operationChar.length; i++) {
						// AND�߼�����
						if (operationChar[i] == ',') {
							strtemp += String.copyValueOf(operationChar,
									block_start, i - block_start - 1);
							block_start = i + 2;// ȥ����ǰ��Ŀո�
							strtemp += ":,:";
							// or�߼�����
						} else if (operationChar[i] == 'o'
								&& operationChar[i + 1] == 'r') {
							strtemp += String.copyValueOf(operationChar,
									block_start, i - block_start - 1);
							block_start = i + 3;// ȥ��orǰ��Ŀո�
							strtemp += ":or:";
						}
					}
					strtemp += String.copyValueOf(operationChar, block_start, i
							- block_start);
					//System.out.println("strtemp1="+strtemp);

					String[] tempStrings = strtemp.split(":");// ���︳ֵ��ֻ��һ������
*/	
					String finalResults = "";
					String[] tempStrings = operation.split(",");

					for (int k = 0; k < tempStrings.length; k++) {
						String temp[] = tempStrings[k].split("or");
						for (int j = 0; j < temp.length; j++) {
							if (j == 0) {
								finalResults = finalResults + temp[j].trim();
							} else {
								finalResults = finalResults + "/" + "or" + "/"
										+ temp[j].trim();
							}
						}
						if (k != tempStrings.length - 1)
							finalResults = finalResults + "/" + "," + "/";
					}
					tempStrings = finalResults.split("/");
					
					/********************************************************************/
					//System.out.println("strtemp2="+strtemp);
					for (int j = 0; j < tempStrings.length; j++) {
						//System.out.println("tempStrings[j]="+tempStrings[j]);
						tempStrings[j]=tempStrings[j].trim();
						
						if (tempStrings[j].contains("or")) {
							binary_code.add(TableToBinary.getORBytes());
						} else if (tempStrings[j].contains(",")) {
							binary_code.add(TableToBinary.getANDBytes());
						} else if (tempStrings[j].contains("=")) {
							//yunsuanfu[0]Ϊ���ں�=
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split("=")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split("=")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split("=")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							 int temp=cpExplain("=", tempStrings[j], Constans.Code_CPEQ,
									Constans.Code_CPEQ_IMM);
							 if(temp==-1){
								 return -1;
							 }
							 if(temp==1){
								 return 1;
							 }
							}
						}else if (tempStrings[j].contains(yunsuanfu[0])) {
							//yunsuanfu[0]Ϊ���ں�=
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split(yunsuanfu[0])[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split(yunsuanfu[0])[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split(yunsuanfu[0])[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							 int temp=cpExplain(yunsuanfu[0], tempStrings[j], Constans.Code_CPEQ,
									Constans.Code_CPEQ_IMM);
							 if(temp==-1){
								 return -1;
							 }
							 if(temp==1){
								 return 1;
							 }
							}
						} else if (tempStrings[j].contains("!=")) {
							//!=
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress( tempStrings[j].split("!=")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split("!=")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split("!=")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int temp=cpExplain("!=", tempStrings[j], Constans.Code_CPNE,
									Constans.Code_CPNE_IMM);
							if(temp==-1){
								 return -1;
							 }
							if(temp==1){
								 return 1;
							 }
							}}else if (tempStrings[j].contains(yunsuanfu[1])) {
							//!=
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress( tempStrings[j].split(yunsuanfu[1])[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split(yunsuanfu[1])[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split(yunsuanfu[1])[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int temp=cpExplain(yunsuanfu[1], tempStrings[j], Constans.Code_CPNE,
									Constans.Code_CPNE_IMM);
							if(temp==-1){
								 return -1;
							 }
							if(temp==1){
								 return 1;
							 }
							}

						} else if (tempStrings[j].contains(">")) {

							//>
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split(">")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split(">")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split(">")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int temp=cpExplain(">", tempStrings[j], Constans.Code_CPGT,
									Constans.Code_CPGT_IMM);
							if(temp==-1){
								 return -1;
							 }
							if(temp==1){
								 return 1;
							 }
							}


						} else if (tempStrings[j].contains(yunsuanfu[2])) {
							//>
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split(yunsuanfu[2])[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split(yunsuanfu[2])[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split(yunsuanfu[2])[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int temp=cpExplain(yunsuanfu[2], tempStrings[j], Constans.Code_CPGT,
									Constans.Code_CPGT_IMM);
							if(temp==-1){
								 return -1;
							 }
							if(temp==1){
								 return 1;
							 }
							}


						}else if (tempStrings[j].contains("<")) {
							//<
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split("<")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split("<")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split("<")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int temp=cpExplain("<", tempStrings[j], Constans.Code_CPLT,
									Constans.Code_CPLT_IMM);
							if(temp==-1){
								 return -1;
							 }
							if(temp==1){
								 return 1;
							 }
							}
						}else if (tempStrings[j].contains(yunsuanfu[3])) {
							//<
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split(yunsuanfu[3])[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split(yunsuanfu[3])[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split(yunsuanfu[3])[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int temp=cpExplain(yunsuanfu[3], tempStrings[j], Constans.Code_CPLT,
									Constans.Code_CPLT_IMM);
							if(temp==-1){
								 return -1;
							 }
							if(temp==1){
								 return 1;
							 }
							}
						} else if (tempStrings[j].contains("ON")) {
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								if(tempStrings.length==1){
				  	  	    		  int address1=TableToBinary.searchAddress(tempStrings[0].split("\\s+")[0].trim(), false);
				  	  	    		  ArrayList<HashMap<String, Object>> inputList = ArrayListBound.getDeviceActualInputListData(); 
				  	  	    	      if(address1==16){
				  	  	    	    	note= inputList.get(16).get("signalNameEditText").toString().trim();
				  	  	    	    	  break;
				  	  	    	      }
				  	  	    	      if(address1==19){
				  	  	    	    	note=inputList.get(19).get("signalNameEditText").toString().trim();
			  	  	    	    	     break;
			  	  	    	    	  }
				  	  	    		}
								TableToBinary.searchAddress(tempStrings[j].split("\\s+")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split("\\s+")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split("\\s+")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){  
							int[] temp = TableToBinary
									.deviceSearchAddress(tempStrings[j]
											.split("\\s+")[0].trim());
							if(temp[0]==-1&&temp[1]==-1){
								dname=tempStrings[j]
										.split("\\s+")[0].trim();
								return -1;
							}
							if(temp[0]==0xffff&&temp[1]==0xffff){
								dname=tempStrings[j]
										.split("\\s+")[0].trim();
								return 1;
							}
							binary_code.add(TableToBinary.getA_1Bytes(temp[0],
									temp[1] % 32));
							}
						} else if (tempStrings[j].contains("OFF")) {
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempStrings[j].split("\\s+")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempStrings[j].split("\\s+")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempStrings[j].split("\\s+")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int[] temp = TableToBinary
									.deviceSearchAddress(tempStrings[j]
											.split("\\s+")[0].trim());
							if(temp[0]==-1&&temp[1]==-1){
								dname=tempStrings[j]
										.split("\\s+")[0].trim();
								return -1;
							}
							if(temp[0]==0xffff&&temp[1]==0xffff){
								dname=tempStrings[j]
										.split("\\s+")[0].trim();
								return 1;
							}
							binary_code.add(TableToBinary.getB_1Bytes(temp[0],
									temp[1] % 32));
						}
						}
						}
					noteList.add("���"+note);
					break;
				case END:
					if(TR_Programming_Activity.noteflag!=0){
						noteList.add(alloperation+"����");
					}else if(TR_Programming_Activity.noteflag==0){
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getSEQ_ENDBytes());}
					break;
				case IFThen:
					// IFThen ��û��д��ȫ����Ҫ����
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					String finalResult = "";
					String[] tempString = operation.split(",");

					for (int k = 0; k < tempString.length; k++) {
						String temp[] = tempString[k].split("or");
						for (int j = 0; j < temp.length; j++) {
							if (j == 0) {
								finalResult = finalResult + temp[j].trim();
							} else {
								finalResult = finalResult + "/" + "or" + "/"
										+ temp[j].trim();
							}
						}
						if (k != tempString.length - 1)
							finalResult = finalResult + "/" + "," + "/";
					}
					tempString = finalResult.split("/");
					for (int zz = 0; zz < tempString.length; zz++) {
						//System.out.println("IFTHEN�ָ�" + tempString[zz]);
					}

					/*****************************************************/

					for (int j = 0; j < tempString.length; j++) {
						if (tempString[j].contains(",")
								&& j != tempString.length - 2) {
							binary_code.add(TableToBinary.getANDBytes());
						} else if (tempString[j].contains(",")
								&& j == tempString.length - 2) {

					/*		binary_code.add(TableToBinary
									.getTHROUGHBytes(false));*/

							if (tempString[j + 1].contains("GOTO")) {

								binary_code.add(TableToBinary
										.getJUMPON(tempString[j + 1]
												.split("GOTO")[1].trim()));

							} else if (tempString[j + 1].contains("CALL")) {
								
								binary_code.add(TableToBinary
										.getCALLON(tempString[j + 1]
												.split("CALL")[1].trim()));
							}
						} else if (tempString[j].contains("or")) {
							binary_code.add(TableToBinary.getORBytes());
						} else if (tempString[j].contains("ON")) {
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempString[j].split("ON")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempString[j].split("ON")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempString[j].split("ON")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int[] temp = TableToBinary
									.deviceSearchAddress(tempString[j]
											.split("ON")[0]);
							if(temp[0]==-1&&temp[1]==-1){
								dname=tempString[j]
										.split("ON")[0].trim();
								return -1;
							}
							if(temp[0]==0xffff&&temp[1]==0xffff){
								dname=tempString[j]
										.split("ON")[0].trim();
								return 1;
							}
							binary_code.add(TableToBinary.getA_1Bytes(temp[0],
									temp[1] % 32));
							}
						} else if (tempString[j].contains("OFF")) {
							if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
								TableToBinary.searchAddress(tempString[j].split("OFF")[0].trim(), null);
								String noteText=TableToBinary.noteText;
								if(note.equals("")){
									note=replace_nc(tempString[j].split("OFF")[0].trim(),alloperation, noteText);
								}else{
									note=replace_nc(tempString[j].split("OFF")[0].trim(),note, noteText);
								}
							}else if(TR_Programming_Activity.noteflag==0){
							int[] temp = TableToBinary
									.deviceSearchAddress(tempString[j]
											.split("OFF")[0]);
							if(temp[0]==-1&&temp[1]==-1){
								dname=tempString[j]
										.split("OFF")[0].trim();
								return -1;
							}
							if(temp[0]==0xffff&&temp[1]==0xffff){
								dname=tempString[j]
										.split("OFF")[0].trim();
								return 1;
							}
							binary_code.add(TableToBinary.getB_1Bytes(temp[0],
									temp[1] % 32));
							}
						} else {
							if (tempString[j].contains(yunsuanfu[0])) {
								//=
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split(yunsuanfu[0])[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split(yunsuanfu[0])[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split(yunsuanfu[0])[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain(yunsuanfu[0], tempString[j].trim(),
										Constans.Code_CPEQ,
										Constans.Code_CPEQ_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }}
							} else if (tempString[j].contains(yunsuanfu[1])) {
								//!=
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split(yunsuanfu[1])[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split(yunsuanfu[1])[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split(yunsuanfu[1])[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain(yunsuanfu[1], tempString[j].trim(),
										Constans.Code_CPNE,
										Constans.Code_CPNE_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }}
							}else if (tempString[j].contains(yunsuanfu[2])) {
								//>
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split(yunsuanfu[2])[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split(yunsuanfu[2])[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split(yunsuanfu[2])[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain(yunsuanfu[2], tempString[j],
										Constans.Code_CPGT,
										Constans.Code_CPGT_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }}
							} else if (tempString[j].contains(yunsuanfu[3])) {
								//<
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split(yunsuanfu[3])[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split(yunsuanfu[3])[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split(yunsuanfu[3])[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain(yunsuanfu[3], tempString[j],
										Constans.Code_CPLT,
										Constans.Code_CPLT_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }
								}
							}
							if (tempString[j].contains("=")) {
								//=
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split("=")[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split("=")[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split("=")[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain("=", tempString[j].trim(),
										Constans.Code_CPEQ,
										Constans.Code_CPEQ_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }}
							} else if (tempString[j].contains("!=")) {
								//!=
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split("!=")[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split("!=")[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split("!=")[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain("!=", tempString[j].trim(),
										Constans.Code_CPNE,
										Constans.Code_CPNE_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }}
							}else if (tempString[j].contains(">")) {
								//>
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split(">")[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split(">")[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split(">")[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain(">", tempString[j],
										Constans.Code_CPGT,
										Constans.Code_CPGT_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }}
							} else if (tempString[j].contains("<")) {
								//<
								if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
									TableToBinary.searchAddress(tempString[j].split("<")[0].trim(), null);
									String noteText=TableToBinary.noteText;
									if(note.equals("")){
										note=replace_nc(tempString[j].split("<")[0].trim(),alloperation, noteText);
									}else{
										note=replace_nc(tempString[j].split("<")[0].trim(),note, noteText);
									}
								}else if(TR_Programming_Activity.noteflag==0){
								int temp=cpExplain("<", tempString[j],
										Constans.Code_CPLT,
										Constans.Code_CPLT_IMM);
								if(temp==-1){
									 return -1;
								 }
								if(temp==1){
									 return 1;
								 }
								}
							}
						}

						/***************************************************/

					}
					note=note.replace("GOTO ", "����ת��");
					noteList.add("���"+note);
					break;
				case RET:
					if(TR_Programming_Activity.noteflag!=0){
						noteList.add(alloperation+"����");
					}else if(TR_Programming_Activity.noteflag==0){
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getRet());}
					break;
				case ALARM:
					//alarmָ��
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					//��ֵALM(DYNCALM,��������72��)
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation, null);
						String noteText=TableToBinary.noteText;
						if(note.equals("")){
							note=replace_nc(operation,alloperation, noteText);
						}else{
							note=replace_nc(operation,note, noteText);
						}
					}else if(TR_Programming_Activity.noteflag==0){
					int temp=TableToBinary.searchAddress(operation, true);
					if(temp==-1){
						dname=operation;
						return -1;
					}
					if(temp==0xffff){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getMOVE_IMMBytes(
							Integer.valueOf(operation), 
							PlcData.getSDOoffsetAddress(72)));}
					noteList.add("����������"+note);
					break;
				default:
					break;
				}
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				// call���ú�����Ҫ����
				ArrayList<String> NumList_name=new ArrayList<String>();
				Fragments_NCedit1.checkNumnameList(NumList_name);
				
				if (order.equals("CALL")) {
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						noteList.add("��������ת��"+alloperation);
					}else if(TR_Programming_Activity.noteflag==0){
					if (!(NumList_name.contains(operation))){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getCALL(operation));
					}
				} else if (order.equals("GOTO")) {
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						noteList.add("��������ת��"+alloperation);
					}else if(TR_Programming_Activity.noteflag==0){
					if (!(NumList_name.contains(operation))){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					// ���ú�������Ҫ����
					binary_code.add(TableToBinary.getJUMP(operation));
					}
				}else if (order.equals("QON")) {
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						noteList.add("��������"+alloperation);
					}else if(TR_Programming_Activity.noteflag==0){
					if (!(NumList_name.contains(operation))){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					// ���ú�������Ҫ����
					binary_code.add(TableToBinary.getQON(operation));
					}
				}else if (order.equals("QOFF")) {
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						noteList.add("�رս���"+alloperation);
					}else if(TR_Programming_Activity.noteflag==0){
					if (!(NumList_name.contains(operation))){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					// ���ú�������Ҫ����
					binary_code.add(TableToBinary.getQOFF(operation));
					}
				} else if (order.equals("TWAIT")) {
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation, null);
						String noteText=TableToBinary.noteText;
						if(note.equals("")){
							note=replace_nc(operation,alloperation, noteText);
						}else{
							note=replace_nc(operation,note, noteText);
						}
						if(alloperation.endsWith("s")){
							note=alloperation;
						}
						noteList.add("�ȴ�"+note);
					}else if(TR_Programming_Activity.noteflag==0){
					binary_code.add(TableToBinary.getTHROUGHBytes(false,true));
					// binary_code.add(TableToBinary.getOUT_OFFBytes(offset))
					int temp = TableToBinary.searchAddress(operation, false);
					if(!alloperation.endsWith("s")){
                        if(temp==-1){
						dname=operation;
						return -1;
					    }
					    if(temp==0xffff){
						dname=operation;
						return 1;
					    }
					}
					
					binary_code.add(TableToBinary.getOUT_OFFBytes(
							PlcData.getMTMExoffsetAddress(temp), temp % 32));
					binary_code.add(TableToBinary.getTHROUGHBytes(false,false));
					binary_code.add(TableToBinary.getOUT_ONBytes(
							PlcData.getMTMExoffsetAddress(temp), temp % 32));
					binary_code.add(TableToBinary.getTHROUGHBytes(false,false));
					binary_code.add(TableToBinary.getA_1Bytes(
							PlcData.getMTMUpoffsetAddress(temp), temp % 32));
					}
				}else if (order.equals("CINC")) {
					//��������1
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation.trim(), null);
						String noteText=TableToBinary.noteText;
						if(note.equals("")){
							note=replace_nc(operation.trim(),alloperation, noteText);
						}else{
							note=replace_nc(operation.trim(),note, noteText);
						}
						noteList.add(note+"��1");
					}else if(TR_Programming_Activity.noteflag==0){
					int temp=TableToBinary.searchAddress(operation.trim(),false);
					if(temp==-1){
						dname=operation.trim();
						return -1;
					}
					if(temp==0xffff){
						dname=operation.trim();
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getINC(operation.trim()));
					}
				}else if (order.equals("CDEC")) {
					//��������1
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation.trim(), null);
						String noteText=TableToBinary.noteText;
						if(note.equals("")){
							note=replace_nc(operation.trim(),alloperation, noteText);
						}else{
							note=replace_nc(operation.trim(),note, noteText);
						}
						noteList.add(note+"��1");
					}else if(TR_Programming_Activity.noteflag==0){
					int temp=TableToBinary.searchAddress(operation.trim(),false);
					if(temp==-1){
						dname=operation.trim();
						return -1;
					}
					if(temp==0xffff){
						dname=operation.trim();
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getDEC(operation.trim()));
					}
				}else if (order.equals("CCLR")) {
					//��������0
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation, null);
						String noteText=TableToBinary.noteText;
						if(note.equals("")){
							note=replace_nc(operation,alloperation, noteText);
						}else{
							note=replace_nc(operation,note, noteText);
						}
						noteList.add("���"+note);
					}else if(TR_Programming_Activity.noteflag==0){
					int temp=TableToBinary.searchAddress(operation, true);
					if(temp==-1){
						dname=operation;
						return -1;
					}
					if(temp==0xffff){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getMOVE_IMMBytes(0, 
							PlcData.getMCNToffsetAddress(TableToBinary.searchAddress(operation, true))));
					}
				}else if (order.equals("CSET")) {
					//��������ֵ����������˵��������ֵӦ����У�й����а���
					if(TR_Programming_Activity.noteflag!=0&&(!alloperation.equals(""))){
						TableToBinary.searchAddress(operation.split("=")[0], null);
						String noteText=TableToBinary.noteText;
						if(note.equals("")){
							note=replace_nc(operation.split("=")[0],alloperation, noteText);
						}else{
							note=replace_nc(operation.split("=")[0],note, noteText);
						}
						noteList.add("�趨"+note);
					}else if(TR_Programming_Activity.noteflag==0){
					int temp=TableToBinary.searchAddress(operation.split("=")[0], true);
					if(temp==-1){
						dname=operation.split("=")[0];
						return -1;
					}
					if(temp==0xffff){
						dname=operation.split("=")[0];
						return 1;
					}
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					binary_code.add(TableToBinary.getMOVE_IMMBytes(
							Integer.valueOf(operation.split("=")[1]), 
							PlcData.getMCNToffsetAddress(TableToBinary.searchAddress(operation.split("=")[0], true))));
					}
				}else if (order.equals("PWAIT")) {
					//PWAITָ��
					if(TR_Programming_Activity.noteflag!=0){
						noteList.add(alloperation);
					}else if(TR_Programming_Activity.noteflag==0){
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					//DXATEN#0(���������16��)
					binary_code.add(TableToBinary.getCP_Bytes(Constans.Code_CPNE_IMM, 
							PlcData.getSDIoffsetAddress(16), 0));
					}
				}else{
					dname=order;
					return -3;
				}
				/*else if (order.equals("ALARM")) {
					//alarmָ��
					binary_code.add(TableToBinary.getTHROUGHBytes(false));
					//��ֵALM(DYNCALM,��������72��)
					int temp=TableToBinary.searchAddress(operation, true);
					if(temp==0xffff){
						dname=operation;
						return 1;
					}
					binary_code.add(TableToBinary.getMOVE_IMMBytes(
							Integer.valueOf(operation), 
							PlcData.getSDOoffsetAddress(72)));
				}*/
			}

		}else{
			noteList.add(alloperation);
		}
		return 0;
	}

	/**
	 * ���Ͳ�ͬ��ֵCP��һ���м亯������Ҫ��Ϊ�˴�������
	 * 
	 * @param expressString
	 *            >,<,>=,<=,=,!=
	 * @param formula
	 *            CP�ļ���ʽ
	 * @param codeOffset
	 *            ƫ�Ƶ�ַָ��
	 * @param codeIMM
	 *            ������ָ��
	 */
	private static int cpExplain(String expressString, String formula,
			int codeOffset, int codeIMM) {

		// ��ʱ�������õ�ַ�������Ķ����㣬��ʵ������
		int tempint = TableToBinary.searchAddress(
				formula.split(expressString)[0].trim(), true);
		if(tempint==-1){
			dname=formula.split(expressString)[0].trim();
			return -1;
		}
		if(tempint==0xffff){
			dname=formula.split(expressString)[0].trim();
			return 1;
		}
		try {

			int immdata = Integer.valueOf(formula.split(expressString)[1]
					.trim());
			binary_code.add(TableToBinary
					.getCP_Bytes(codeIMM, tempint, immdata));

		} catch (NumberFormatException e) {
			// TODO: handle exception
			int tempint1 = TableToBinary.searchAddress(
					formula.split(expressString)[1].trim(), true);
			if(tempint1==-1){
				dname=formula.split(expressString)[1].trim();
				return -1;
			}
			if(tempint1==0xffff){
				dname=formula.split(expressString)[1].trim();
				return 1;
			}
			binary_code.add(TableToBinary.getCP_Bytes(
					codeOffset,
					tempint,
					TableToBinary.searchAddress(
							formula.split(expressString)[1].trim(), true)));
		}
		 return 0;
	}
            
}
