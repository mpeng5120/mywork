package com.explain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Fragment;
import android.widget.Toast;

import com.dataInAddress.Machinedata;

public class ServoDataGenerate {
	
	/**
	 * �ŷ������������ɷ��͸���λ��
	 * @param dataArrayList
	 * @param ft
	 * @param mdata
	 * @return
	 */
	public boolean dataGenerate(ArrayList<HashMap<String, Object>> dataArrayList,Fragment ft,Machinedata mdata) {

		Iterator< HashMap<String, Object>> it=dataArrayList.iterator();

		int index=0;

		while (it.hasNext()) {
			HashMap<String, Object> map =  it.next();
			for(int i=0;i<mdata.servoIDPara.length;i++){
				//ÿ�����Ϣ
				try {
					int axis=i+1;

					mdata.servoPara[i][index]=Integer.parseInt(map.get("setValueText"+axis).toString());

				} catch (NumberFormatException e) {
					// TODO: handle exception
					int tempint=index+1;
					int axis=i+1;
					Toast.makeText(ft.getActivity(), axis+"��"+tempint+"���ݴ���", Toast.LENGTH_SHORT).show();
					return false;
				}
			}			
			index++;
		}
		
		return true;
	}





}
