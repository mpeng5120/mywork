package com.explain;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.R.integer;
import android.app.Activity;
import android.widget.Toast;

import com.dataInAddress.Machinedata;

public class MachineDataGenerate {

	/**
	 * �Ӽ��ٶ���д
	 * @param axisNo
	 * @param value
	 * @param mdata
	 */
	private void accdccSave(int axisNo,int value,int i,Machinedata mdata) {
		switch (i) {
		case 1:
			mdata.motoParas[axisNo].accTimeTbl[0]=value;
			break;
		case 2:
			mdata.motoParas[axisNo].accTimeTbl[1]=value;
			break;
		case 3:
			mdata.motoParas[axisNo].accTimeTbl[2]=value;
			break;
		case 4:
			mdata.motoParas[axisNo].accTimeTbl[3]=value;
			break;
		case 5:
			mdata.motoParas[axisNo].accTimeTbl[4]=value;
			break;
		case 6:
			mdata.motoParas[axisNo].dccTimeTbl[0]=value;
			break;
		case 7:
			mdata.motoParas[axisNo].dccTimeTbl[1]=value;
			break;
		case 8:
			mdata.motoParas[axisNo].dccTimeTbl[2]=value;
			break;
		case 9:
			mdata.motoParas[axisNo].dccTimeTbl[3]=value;
			break;
		case 10:
			mdata.motoParas[axisNo].dccTimeTbl[4]=value;
			break;
		default:
			break;
		}
	}

	/**
	 * 1 ԭ�㸴����ز���
	 * @param axisNo
	 * @param value
	 * @param i
	 * @param mdata
	 */
	private void orgParaSave(int axisNo,int value,int i,Machinedata mdata) {
		switch (i) {
		case 0:
			//ԭ��ƫ��
			mdata.orgParas[axisNo].orgOffset=value;
			break;
		case 1:
			//ԭ�㸴���ٶ�1
			mdata.orgParas[axisNo].orgSpeedPre=value;
			break;
		case 2:
			//ԭ�㸴���ٶ�2
			mdata.orgParas[axisNo].orgSpeedAft=value;
			break;
		case 3:
			//ԭ�㷽��
			mdata.orgParas[axisNo].orgDir=value;
			break;
		case 4:
			//ԭ����λ�źű��
			mdata.orgParas[axisNo].orgLmtNo=value;
			break;		
		default:
			break;
		}
	}


	public void DataGenerate (ArrayList<HashMap<String, Object>> read_arraylist,Machinedata mdata,String version,Activity window) {

		HashMap<String, Object> mapadd=new HashMap<String, Object>();

		Iterator<HashMap<String, Object>> it=read_arraylist.iterator();
		
		try {
			System.arraycopy(version.trim().getBytes("iso-8859-1"), 0, mdata.version, 0, version.trim().getBytes("iso-8859-1").length);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int index=1;
		while (it.hasNext()) {
			HashMap<String,Object> map=it.next();

			int value;
			//String sss=map.get("setValueText").toString();
			
			try {
				value=Integer.valueOf(map.get("setValueText").toString());
			} catch (NumberFormatException e) {
				// TODO: handle exception
				value=0;
			}

			if (index<=120&&index>=1) {
				if (index%15==1) {
					//ÿһȦ������
					mdata.motoParas[index/15].plsPerCirclel=value;	
				}else if (index%15==2) {
					//ÿһȦ����
					mdata.motoParas[index/15].maxPRM=value;
				}else if (index%15==3) {
					//�趨100%ʱ�������ٶ�
					mdata.motoParas[index/15].um10PerPls=value;	
				}else if (index%15>=4&&index%15<=13) {
					//��ļ��ٶȼ��ٶ���Ϣ��ȡ
					accdccSave(index/15,value,index%15-3,mdata);	
				}else if (index%15==14) {
					//����ʼ����
					mdata.motoParas[index/15].initDir=value;
				}else if (index%15==0) {
					//ͻȻֹͣʱ�����ʱ��
					mdata.motoParas[index/15-1].stopDecTime=value;				
				}
			}else if (index>=121&&index<=128) {
				//IO���Լ�IO���
				mdata.ioIDPara[index-121]=value;
			}else if (index>=129&&index<=136) {
				//�ŷ������ŷ����
				mdata.servoIDPara[index-129]=value;
			}else if (index==137) {
				//IO��ADʹ�ñ��
				mdata.ADuseFlg=value;
			}else if (index>=138&&index<=139) {
				//wifi�趨

			}else if (index>=140&&index<=179) {
				//ԭ�㸴����ز���
				orgParaSave((index-140)/5, value, (index-140)%5, mdata);
			}else if(index>=180&&index<=195){
				//�����С�г�
				if(index%2==0){
					//��С
					mdata.axisStrokes[(index-180)/2].minStroke=value;
				}else {
					//���
					mdata.axisStrokes[(index-180)/2].maxStroke=value;
				}
			}else if (index>=196&&index<=203) {
				//�����ֹ���
				if((index-196)%4==0){
					//�Ƿ�ʹ�ø����ֹ
					mdata.collisionOpts[(index-196)/4].useFlag=value;
				}else if ((index-196)%4==1||(index-196)%4==2) {
					//�����
					mdata.collisionOpts[(index-196)/4].axisNo[(index+1)%2]=value;
				}else if ((index-196)%4==3) {
					//��С����
					mdata.collisionOpts[(index-196)/4].minPitch=value;
				}			
			}else if (index>=204&&index<=208) {
				//5���ٶȱ�
				mdata.allSpeed[index%204]=value;			
			}else if (index>=209&&index<=232) {
				//JOG�ٶ�
				mdata.jogSpeed[(index-209)/3][(index-209)%3]=value;
			}

			//��һ��
			index++;

		}
	}

	
	
	
}
