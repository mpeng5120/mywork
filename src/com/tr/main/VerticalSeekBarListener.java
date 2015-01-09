package com.tr.main;
import java.io.IOException;

import com.dataInAddress.AddressPublic;
import com.dbutils.Constans;
import com.explain.HexDecoding;
import com.tr.R;
import com.tr.programming.Fragments_NCedit1;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.Client;
import com.wifiexchange.ServerUtils;
import com.wifiexchange.WifiSetting_Info;

import wifiProtocol.WifiSendDataFormat;
import wifiRunnablesAndChatlistener.FinishRunnable;
import wifiRunnablesAndChatlistener.NormalChatListenner;
import wifiRunnablesAndChatlistener.SendDataRunnable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VerticalSeekBarListener implements OnSeekBarChangeListener{
	
	private Activity targetActivity;
	
	public VerticalSeekBarListener(Activity targetActivity){
		this.targetActivity=targetActivity;
	}
	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		// ��λ���ã�Ϊ������ʹ�̶Ⱦ��ȷֲ�����ʵ�ʴ�����ʱ��Ҫת�����ݣ�
					//��Ҫ�������ֵΪ4��Ȼ��ת��
					if (progress < 60) {
						Constans.currentSeekbarValue=0;
						seekBar.setProgress(0);
					} else if (progress >= 60 && progress < 185) {
						Constans.currentSeekbarValue=125;
						seekBar.setProgress(125);
					} else if (progress >= 185 && progress < 310) {
						
						Constans.currentSeekbarValue=250;
						seekBar.setProgress(250);
					} else if (progress >= 310 && progress < 435) {
						
						Constans.currentSeekbarValue=375;
						seekBar.setProgress(375);
					} else {
						
						Constans.currentSeekbarValue=500;
						seekBar.setProgress(500);
					}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		System.out.println("�ٶ��趨��ʼ");
	}
	/*****
	 * wifi����ʧ��
	 */
	private Runnable errorRunnable=new Runnable() {
		@Override
		public void run() {
			WifiSetting_Info.blockFlag=false;
			Toast.makeText(targetActivity,R.string.T_wificonnfail,Toast.LENGTH_LONG).show();
		}
	};
	
	/***
	 * ���ӳɹ�
	 */
	private Runnable successRunnable=new Runnable() {
		@Override
		public void run() {
			WifiSetting_Info.blockFlag=true;
			Toast.makeText(targetActivity,R.string.T_wificonnsuccess,Toast.LENGTH_SHORT).show();
		}
		
	};
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		System.out.println("�ٶ��趨ֹͣ");
		// TODO Auto-generated method stub
		//��λ����ֹͣʱ��������Ϣ����λ��
	/*	if (WifiSetting_Info.mClient == null) {
			if(ServerUtils.isUsingWifi(targetActivity)){
				new AlertDialog.Builder(targetActivity)
				.setTitle("��ʾ").setMessage("������wifi")
				.setPositiveButton("ȷ��",
	            new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) {
						new Thread() {
							public void run() {
								try {
									WifiSetting_Info.mClient = Client.connect("192.168.1.1");
									new Thread(WifiSetting_Info.mClient).start();
									targetActivity.runOnUiThread(successRunnable);
									if (targetActivity.getFragmentManager().findFragmentByTag("Timer_Setting") instanceof Fragments_timer){
										System.out.println("��ʱ��������¡�����");
										Fragments_timer.timerList_Adapter.notifyDataSetChanged();
									}
								} catch (IOException e) {
									e.printStackTrace();
									targetActivity.runOnUiThread(errorRunnable);
								}
							}
						}.start();
					}
				}).setNegativeButton("ȡ��",null).show();
			}else{
				Toast.makeText(targetActivity, "�����쳣��ͨѶ�жϣ��������磡", Toast.LENGTH_SHORT).show();
			}
			return;
		}*/
		int data=0;
		if(seekBar.getProgress()==0){
			data=1;
		}else if(seekBar.getProgress()==125){
			data=2;
		}else if(seekBar.getProgress()==250){
			data=3;
		}else if(seekBar.getProgress()==375){
			data=4;
		}else if(seekBar.getProgress()==500){
			data=5;
		}
		WifiSendDataFormat sendDataFormat=new WifiSendDataFormat(
				HexDecoding.int2byteArray2(data), AddressPublic.model_allspeed);
/*		byte[] temp=new byte[4];
		temp=HexDecoding.int2byteArray4(data);
		 MainApp myApp = (MainApp)new Application();

		   myApp.setStr(temp[0]);
*/

		SendDataRunnable sendDataRunnable=new SendDataRunnable(sendDataFormat, targetActivity);
		FinishRunnable finishRunnable=new FinishRunnable(targetActivity, "��λ��Ϣ�ɹ����͸���λ��");
		NormalChatListenner listenner=new NormalChatListenner(sendDataRunnable, finishRunnable);
		sendDataRunnable.setOnlistener(listenner);
		targetActivity.runOnUiThread(sendDataRunnable);

	}

}
