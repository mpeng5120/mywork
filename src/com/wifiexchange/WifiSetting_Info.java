package com.wifiexchange;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tr.R;


public class WifiSetting_Info {
	//�ͻ�����Ϣ
	public static Client mClient;
	public static ProgressDialog progressDialog;
	public static long wifiTimeOut;
	public static  String almMSG="";//�ϴξ�����Ϣ 
	public static  String[] almMSGs=new String[]{"","","","","","","","","",""};//�ϴξ�����Ϣ 
	public static int alarmFlag=0;//0���޾�����1���о���
	//������������õ��ķ��ص�����
	public static byte[] getBackData;
	
	public static boolean blockFlag=false;
	public static int[] ydfgFlag=new int[]{0,0,0,0,0,0,0,0};//ԭ�㸴���־λ
			
	public static boolean destroyPositionQueryFlag=true;
	
	//wifi�Զ�������ر�������
	//��Ѱ��Ӧ���֣�����wifi���ӣ����ҽ���socket����
	public static String wifiname="WIFI_ZJX";
	public static String wifipassword="teat";

	public static String connnectStatus="δ����";
	
	public static Object LOCK ;

	

}
