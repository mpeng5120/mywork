/*    Copyright 2012 Brayden (headdetect) Lopez
 *    
 *    Dual-licensed under the Educational Community License, Version 2.0 and
 *	the GNU General Public License Version 3 (the "Licenses"); you may
 *	not use this file except in compliance with the Licenses. You may
 *	obtain a copy of the Licenses at
 *
 *		http://www.opensource.org/licenses/ecl2.php
 *		http://www.gnu.org/licenses/gpl-3.0.html
 *
 *		Unless required by applicable law or agreed to in writing
 *	software distributed under the Licenses are distributed on an "AS IS"
 *	BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *	or implied. See the Licenses for the specific language governing
 *	permissions and limitations under the Licenses.
 * 
 */

package com.wifiexchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tr.TR_Welcome_Activity;
import com.tr.SerailPort.SerailPortOpt;
import com.tr.crash.CrashApplication;
import com.tr.main.TR_Main_Activity;
import com.tr.maintainguide.TR_MaintainGuide_Activity;
import com.tr.parameter_setting.TR_Parameter_Setting_Activity;
import com.tr.programming.TR_Programming_Activity;

// TODO: Auto-generated Javadoc
/**
 * The Class Client. �Ѽ���ȴ����ƣ�ȷ��ÿ�η�����Դ��ռ
 */
public class Client implements Runnable {

	// ===========================================================
	// Constants
	// ===========================================================
	/******** �������Ƶı��� *********/
	// private MyCount counttime;
	/******** �������ݵȴ����湦�� *********/
	// ����״̬��־λ,Ĭ������¿���
	protected static boolean himaflag_high = true;
	protected static boolean himaflag_low = true;
	private int errorWaitTimes;
	byte[] returnmessagebyte = new byte[1100];
	//add by mpeng 0929
	private SerailPortOpt serialPort;
	protected OutputStream mOutputStream;
	private String TAG = "Client";

	// ===========================================================
	// Fields
	// ===========================================================
	//private Handler mHandler;
	//private Thread cClockThread;
	/** The reader. */
	private InputStream reader;

	/** The writer. */
	private OutputStream writer;

	private static Socket thissocket;

	/** The disconnecting. */
	private boolean disconnecting;

	/** The chat listener. */
	private  static ChatListener chatListener;

	/** The connection listener. */
	private static ConnectionListener connectionListener;
	private static Activity targetActivity;
	private static byte[] message;
	private static int HighPriorMsgErrorTime = 0;
	private static int ResendNum = 5;
	
	public Activity getActivity() {
		return targetActivity;
	}
	public static void setSendNum(int num)
	{
		ResendNum = num;
	}
	private static Handler myhandler = new Handler(){
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == LittleDownTimer.TIME_OUT_MSG)
			{
				Log.e("mpeng","time out !!!");
				
				if(!himaflag_high)
				{// �����ȼ���ʱ�ط�
					
					if(HighPriorMsgErrorTime<ResendNum)
					{
						HighPriorMsgErrorTime++;
						reSendHighPriorMessage();
						LittleDownTimer.resetMenu();
						
					}
					else
					{
						if(ResendNum == 30)
						{
						new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
					       .setMessage("���Main���ʼ��״̬��ʱ~~ ��ѯ30�� ��ʱ 1����,��OK�������������ֶ�����")
					       .setPositiveButton("OK", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO �Զ����ɵķ������
								
								targetActivity.startActivity(new Intent(targetActivity.getApplication(),TR_Main_Activity.class));
								
						
							}
						}).show();
						}else{
							new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
						       .setMessage("�������ݳ�ʱ���ط�5�� ��ʱ10S")
						       .setPositiveButton("OK", null).show();
							if(WifiSetting_Info.progressDialog!=null)
							{
								WifiSetting_Info.progressDialog.dismiss();
	                			WifiSetting_Info.progressDialog = null;
							}
						}
						
					}
						
				}
				
			}
		};
	};
	// ===========================================================
	// Constructors
	// ===========================================================

	/**
	 * Instantiates a new client.
	 * 
	 * @param s
	 *            the socket
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Client() throws IOException {
					disconnecting = false;
					errorWaitTimes = 0;
		            serialPort = new SerailPortOpt();
		            openSerialPort();

	}

	/**
	 * Connect to the specified address, and returns a client.
	 * 
	 * @param address
	 *            the address
	 * @return the connected client
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static Client connect() throws IOException {
		Client client = new Client();
		himaflag_high = true;
		himaflag_low = true;
		LittleDownTimer.getInstance().start();
		LittleDownTimer.setHandler(myhandler);		
		LittleDownTimer.stopMenu();
		return client;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	private void setOnChatListener(ChatListener listener) {
		chatListener = listener;
	}
	
	public InputStream getReader() {
		return reader;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (connectionListener != null) {
			connectionListener.onJoin(this);
		}
		while (!disconnecting) {
			try{
		
				int count = 0;
				count = serialPort.readBytes(returnmessagebyte);
				if(count > 0){  // �յ����ص�����   
						Log.d("mpeng","the return count is ["+count);
//					for(int i=0;i<count;i++)
//						Log.d("mpeng","the return message is ["+i+"] is :" +returnmessagebyte[i]);
						if (himaflag_high == false) {		
							// ״̬λ��Ϊ����
							himaflag_high = true;
							//��ʱ��ֹͣ
							LittleDownTimer.stopMenu();	
							HighPriorMsgErrorTime = 0;
							errorWaitTimes = 0;
							//System.out.println("high״̬λ��Ϊ����");
							// ���ܵ�����Ϣ���еĴ�������ֻ��һ���ӿڣ������ڲ�ͬҳ��������ã�setOnChatListener��
							// �Ա��ò�ͬ�Ĵ�����ʽ,һ��Ҫ���ã�Ҫ��Ȼ����ֿ�ָ��Ĵ���
							chatListener.onChat(returnmessagebyte);
		
							Arrays.fill(returnmessagebyte, (byte) 0);// ���Buffer
						} else if (himaflag_high == true && himaflag_low == false) {
							// ״̬λ��Ϊ����
							himaflag_low = true;
							//��ʱ��ֹͣ
							LittleDownTimer.stopMenu();	
							
							errorWaitTimes = 0;
							//System.out.println("low״̬λ��Ϊ����");
							// ���ܵ�����Ϣ���еĴ�������ֻ��һ���ӿڣ������ڲ�ͬҳ��������ã�setOnChatListener��
							// �Ա��ò�ͬ�Ĵ�����ʽ
							// һ��Ҫ���ã�Ҫ��Ȼ����ֿ�ָ��Ĵ���
							
							chatListener.onChat(returnmessagebyte);
		
							Arrays.fill(returnmessagebyte, (byte) 0);// ���Buffer
							//length=0;
					} 
					}
				//}
			} catch (Exception e) {
				System.out.println("read���Ͽ�");
				e.printStackTrace();
				if(WifiSetting_Info.progressDialog!=null){
					
					WifiSetting_Info.progressDialog.dismiss();
					WifiSetting_Info.progressDialog=null;
					disconnect();
					System.out.println("WifiSetting_Info.progressDialog�����ڿ�   dismiss");
				}
				// ����ͻ�����Ϣ����֤�´μ�������
				if (thissocket != null && thissocket.isConnected() && !thissocket.isClosed()){
					reConnectionIO();
				}else{
					disconnect();
				}
			}
		}
	}

	/***
	 * ����������ʱʱ���´�socket�л�ȡ������ʵ��
	 */
	public void reConnectionIO(){
		System.out.println("**********����read/write����**********");
		reader = serialPort.getInputStream();
		writer = serialPort.getOutputStream();		
		errorWaitTimes = 0;
		himaflag_high = true;
		himaflag_low = true;
		himaflag_low = true;
		//��ʱ��ֹͣ
		LittleDownTimer.stopMenu();
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * �������ݺ���
	 * 
	 * @param message
	 *            the message
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 * @throws Exception
	 *             the exception
	 */
	private static void reSendHighPriorMessage()
	{
		if(!himaflag_high)
			himaflag_high = true;
		if(WifiSetting_Info.mClient!=null)
			WifiSetting_Info.mClient.sendHighPriorMessage(message,targetActivity,chatListener);
	}
	public void sendHighPriorMessage(byte[] message, Activity targetActivity,ChatListener lsitener) {
		this.targetActivity = targetActivity;
		this.message = message;
//		System.out.println("himaflag_high111h="+himaflag_high+"      himaflag_low111h="+himaflag_low);
//		System.out.println("himaflag_low111h="+himaflag_low);
		if (himaflag_high == true) {
			// �������ȼ����߳��Ƿ��Ѿ�������
			if (himaflag_low == true) {
				// �رյ����ȼ��߳�			
				WifiSetting_Info.blockFlag = false;				
				setOnChatListener(lsitener);
				// ��״̬λΪæµ ������ʱ��
				LittleDownTimer.resetMenu();
				himaflag_high = false;
				if (message == null)
					return;
				try {
					synchronized(writer) {
						writer.write(message, 0, message.length);
					}
				//System.out.println("himaflag_low111h writerǰbbbb");
				} catch (IOException e) {
					// TODO: handle exception
					System.out.println(" sendHighPriorMessage sendHighPriorMessage()==socket �Ͽ�");
					disconnect();

				}
			//	System.out.println("himaflag_low111h writer��");
			} else {
					errorWaitTimes++;
				if (errorWaitTimes > 15) {//15
					himaflag_low = true;
					//��ʱ��ֹͣ
					LittleDownTimer.stopMenu();
				}
				if (errorWaitTimes > 35) {//20
					nulldisconnect();
					System.out.println("sendHighPriorMessage shm����ռ&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				}
				// �رյ����ȼ��߳�
				WifiSetting_Info.blockFlag = false;

				System.out.println("sendHighPriorMessage 1shm����ռ"+ errorWaitTimes);

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (errorWaitTimes <= 35) {//20
					
					sendHighPriorMessage(message, targetActivity, lsitener);
					LittleDownTimer.resetMenu();
				}
			}
		} else {
			errorWaitTimes++;
			System.out.println("sendHighPriorMessage 2shm����ռ" + errorWaitTimes);
			if (errorWaitTimes > 35) {//20
				System.out.println("sendHighPriorMessage ��λ��������-_-!!");
				nulldisconnect();
				Toast.makeText(targetActivity, "ͨ�Ŵ�����λ��δ����Ϣ",
						Toast.LENGTH_SHORT);
			}
		}
	}
	
	/**
	 * �÷������ڲ��ϲ�ѯ��ͨ���߳�
	 * 
	 * @param message
	 * @param lsitener
	 * @throws IOException
	 */
	public  void sendMessage(byte[] message, ChatListener lsitener,Activity targetActivity) {
		this.targetActivity = targetActivity;
//		System.out.println("himaflag_high*******"+himaflag_high+"*******himaflag_low********"+himaflag_low);
		if (message == null)
			return;
		if (himaflag_high == true && himaflag_low == true) {
			setOnChatListener(lsitener);
			// ��״̬λΪæµ
			himaflag_low = false;
			try {
				if (null == writer) {
					System.out.println("writer�ѹرգ�����");
				} else {
					synchronized(writer) {											
							writer.write(message, 0, message.length);
						}
				} 
				//length=HexDecoding.Array2Toint(message,2)+9;
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("sendMessage()==socket �Ͽ�");
				disconnect();
			}
		} else {
			errorWaitTimes++;
			System.out.println("sendMessage sm����ռ" + errorWaitTimes);
			try {
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (errorWaitTimes > 35) {//20
				System.out.println("sendMessage sendMessage()��λ��������-_-!!");
				nulldisconnect();
			}
			if (errorWaitTimes <= 35&&himaflag_high) {//20
				Log.e("mpeng","��ͨ������ռ��ǿ�ƴ�ͨ��");
				himaflag_low = true;
				
			}
			/*if (errorWaitTimes > 80) {
				System.out.println("sm��λ��������-_-!!");
				disconnect();
			}*/
		
		}
	}

	/**
	 * Disconnects the client.
	 */
	public void disconnect() {
		disconnecting = true;
		WifiSetting_Info.blockFlag=false;
		System.out.println("disconnect()ͨѶ�ж�");

		if (connectionListener != null) {
			connectionListener.onDisconnect(this);
		}
		reader = null;
		writer = null;
//		try {
//			Log.d("mpeng","55555");
//			thissocket.close();
//			Log.d("mpeng","6666");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
		WifiSetting_Info.mClient = null;
		 Runnable wifiledRunnable=new Runnable() {
				public void run() {
					if(TR_Main_Activity.wifi_led!=null){
			        	TR_Main_Activity.wifi_led.setVisibility(View.GONE);
			        }
			        if(TR_MaintainGuide_Activity.wifi_led!=null){
			        	TR_MaintainGuide_Activity.wifi_led.setVisibility(View.GONE);
			        }
			        if(TR_Parameter_Setting_Activity.wifi_led!=null){
			        	TR_Parameter_Setting_Activity.wifi_led.setVisibility(View.GONE);
			        }
			        if(TR_Programming_Activity.wifi_led!=null){
			        	TR_Programming_Activity.wifi_led.setVisibility(View.GONE);
			        }
				}
			};
			if(targetActivity!=null){
			   targetActivity.runOnUiThread(wifiledRunnable);
			}
 
		/*WriterUtils writeUtils = WriterUtils.getInstance();
		writeUtils.shutdown();*/
		/*
		 * final Runnable finishRunnable=new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * Toast.makeText(getActivity(), "���ӳɹ�", Toast.LENGTH_LONG).show(); }
		 * 
		 * }; final Runnable errorRunnable=new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * Toast.makeText(getActivity(), "����ʧ�ܣ���ȷ��������ȷ������",
		 * Toast.LENGTH_LONG).show(); } }; Runnable tempRunnable=new Runnable()
		 * {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub try
		 * { WifiSetting_Info.mClient = Client.connect("192.168.1.1"); new
		 * Thread(WifiSetting_Info.mClient).start();
		 * 
		 * getActivity().runOnUiThread(finishRunnable); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace();
		 * 
		 * getActivity().runOnUiThread(errorRunnable); } } }; new
		 * Thread(tempRunnable).start();
		 */
		
	}
	
	private void openSerialPort(){
		if(serialPort.mFd == null)
		{
			serialPort.mDevNum = 0;
			serialPort.mSpeed = 115200;
			serialPort.mDataBits = 8 ;
			serialPort.mStopBits = 1 ;
			serialPort.mParity = 'n';
			serialPort.openDev(serialPort.mDevNum);
			serialPort.setSpeed(serialPort.mFd,serialPort.mSpeed );
			serialPort.setParity(serialPort.mFd, serialPort.mDataBits, serialPort.mStopBits, serialPort.mParity);
		    Log.i(TAG,"OPENG SERIALPORT !!!");
		    reader = serialPort.getInputStream();
		    writer = serialPort.getOutputStream();
		    Log.i(TAG,"SERIALPORT 2222 ");
		    
		  
		}
		else 
		{
			Log.i(TAG,"the port is open ");
		}
	}
    private void closeSerialPort()
    {
    	
    	if(serialPort.mFd != null)
    		serialPort.closeDev(serialPort.mFd);
    	 Log.i(TAG,"closeSerialPort");    	
    }
	
	
	/**
	 * 
	 */
	public void nulldisconnect() {
		Log.d(TAG,"nulldisconnect !!");
		disconnecting = true;
		closeSerialPort();
		Toast.makeText(targetActivity, "�߳���ռ�����Զ��ر��߳�",
			      Toast.LENGTH_LONG).show();
		  
				 
		if (connectionListener != null) {
			connectionListener.onDisconnect(this);
		}
		if(WifiSetting_Info.progressDialog!=null){
			
			WifiSetting_Info.progressDialog.dismiss();
			WifiSetting_Info.progressDialog=null;
		}
		if(reader==null){
			return;
		}
		if(writer==null){
			return;
		}
		try {
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================

}