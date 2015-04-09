package com.tr;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import wifiProtocol.WifiReadDataFormat;
import wifiRunnablesAndChatlistener.SendDataRunnable;

import com.dataInAddress.AddressPublic;
import com.dbutils.Constans;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.Client;
import com.wifiexchange.WifiSetting_Info;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class TR_Welcome_Activity extends Activity{
	  // private ImageView  imageView = null;    
	private BootCastReceiver BCR = null;
	private boolean startMainActivity= false;	
	private WifiReadDataFormat formatReadMessage;
	private SendDataRunnable sendDataRunnable;
	private byte[] getData;
	private  ChatListener MainInitStatusFeedback ;
	private  ChatListener QueryArmNumFeedback;
	private Handler  myHandler = new Handler();
	private boolean SDcardIsOk = false;
    @Override
	public void onCreate(Bundle savedInstanceState) {
//    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		super.onCreate(savedInstanceState);
		//无title     
		  requestWindowFeature(Window.FEATURE_NO_TITLE);  

		//全屏     
		  getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  

		//设置布局文件，在布局文件里设置个图片
		setContentView(R.layout.tr_welcome);
		
		if(WifiSetting_Info.mClient == null)
		{
			try {
				WifiSetting_Info.mClient = Client.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block				
				e.printStackTrace();
			}
			new Thread(WifiSetting_Info.mClient).start();	
		}		

         SDcardIsOk = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);

			if(!startMainActivity)
        	{
				Handler x=new Handler();
				Log.e("mpeng","welcome create  ");
				
				x.postDelayed(new welcomehandler(), 200);
        	}

    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		BCR = new BootCastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("bootComplete");
        registerReceiver(BCR, filter);
		Log.e("mpeng","welcome on resume ");
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.e("mpeng","welcome on pause ");
		unregisterReceiver(BCR);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.e("mpeng","welcome on destroy ");	
		super.onDestroy();
	}

	class welcomehandler implements Runnable{
		public void run(){
			while(!SDcardIsOk)
			{
	
					SDcardIsOk = Environment.getExternalStorageState().equals(
							android.os.Environment.MEDIA_MOUNTED);			
			}
			if(SDcardIsOk)
			{
				startMainActivity = true;
				Log.e("mpeng","start main");
				startActivity(new Intent(getApplication(),TR_Main_Activity.class));
//				TR_Welcome_Activity.this.finish();
			}
			
		}
	}
	
	 //利用广播+全局状态位来控制  LED的显示
    private class BootCastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();          
            if(action.equals("bootComplete"))
            {
//            	收到开机完成的广播后再去初始这个
            	Log.d("mpeng","start thread");
            	Config.firstCreate  =false;	
            	if(WifiSetting_Info.mClient == null)
            	{
				try {
					 Log.e("mpeng"," new mclient  receiver !!!!");
					 WifiSetting_Info.mClient = Client.connect();
				} catch (IOException e) {
					// TODO Auto-generated catch block				
						e.printStackTrace();
					}
					new Thread(WifiSetting_Info.mClient).start();	
					
            	}            	
            	
        		WifiSetting_Info.mClient.setSendNum(30);
        		MainInitStatusFeedback = new ChatListener() {
        			@Override
        			public void onChat(byte[] message) {
        				Log.e("mpeng","the maindata feed back !");
        				//返回的标志位STS1处的判断以及和校验
        				formatReadMessage.backMessageCheck(message);
        				if(!formatReadMessage.finishStatus()){
        					runOnUiThread(sendDataRunnable);			
        				}else {
        					//发送正确且完成
        					//处理返回的数据	
        					getData=new byte[formatReadMessage.getLength()];
        					//获取返回的数据，从第八位开始拷贝数据
        					System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
        					// TODO Auto-generated method stub
        					//1:下位机初始化完成 0：下位机未初始化完成
        					Log.e("mpeng","formatReadMessage 222 !");
        					  if(!startMainActivity&&((getData[0]&0xff) ==1))
        				       {							
        							Log.e("mpeng","welcome 11111");	
        							WifiSetting_Info.mClient.setSendNum(5);
        							myHandler.postDelayed(new welcomehandler(), 20);
        				       }
        					
        					}
        				
        					}
        			
        		};	
        		formatReadMessage=new WifiReadDataFormat(0x2000D008,1);
        		Log.e("mpeng","boot  complete !");
        		try {
        			sendDataRunnable = new SendDataRunnable(MainInitStatusFeedback, formatReadMessage,
        					TR_Welcome_Activity.this);
        			runOnUiThread(sendDataRunnable);
        		} catch (Exception e) {
        			Toast.makeText(TR_Welcome_Activity.this, e.toString(), Toast.LENGTH_LONG);
        		}

            }
            
        }
    }

}
