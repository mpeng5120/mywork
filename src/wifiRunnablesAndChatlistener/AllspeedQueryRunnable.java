package wifiRunnablesAndChatlistener;


import wifiProtocol.WifiReadDataFormat;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

/**
 * 复位状态查询
 * @author 李婷婷
 *
 */
public class AllspeedQueryRunnable implements Runnable {
	
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x400241C0,4);
	private byte[] getData;	
	private Activity targetActivity;
	private TextView speed;
	private TextView aspeed;
	private TextView dspeed;
	private Runnable pointShowRunnable;
	//确保一次只能存在一个
	public static boolean existFlag=false;
	private  boolean destroyPositionQueryFlag=false;
	private  boolean selfDestroy_flag=true;
	private  boolean chatlistener_flag=true;
	/**
	 * 构造函数
	 * @param targetActivity
	 * @param viewbeingChangedArrayList
	 */
	public AllspeedQueryRunnable(Activity targetActivity,View cc, View acc, View dcc) {
		try{
			existFlag=true;
		this.targetActivity=targetActivity;
		speed=(TextView)cc;
		aspeed=(TextView)acc;
		dspeed=(TextView)dcc;
		
		pointShowRunnable=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				speed.setText(String.valueOf((int)(getData[0]&0xff)));
				aspeed.setText(String.valueOf((int)(getData[1]&0xff)));
				dspeed.setText(String.valueOf((int)(getData[2]&0xff)));
			}
		};
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	/** 读取伺服参数时的通信线程收到信息时的处理函数. */
	private final ChatListener ReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			if(!chatlistener_flag)
				return;
			//返回的标志位STS1处的判断以及和校验
			formatReadMessage.backMessageCheck(message);
			
			if(!formatReadMessage.finishStatus()){

				WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
			
			}else {
				//发送正确且完成
				//处理返回的数据	
				getData=new byte[formatReadMessage.getLength()];
				//获取返回的数据，从第八位开始拷贝数据
				System.arraycopy(formatReadMessage.getFinalData(),0, getData, 0, formatReadMessage.getLength());
				//对所收集到到的数据进行处理
				
				targetActivity.runOnUiThread(pointShowRunnable);

			}
		}
	};
	
	//销毁该线程
	public  void destroy() {
		existFlag=false;
		destroyPositionQueryFlag=true;
		selfDestroy_flag=false;
		chatlistener_flag=false;
	}
	
	@Override
	public void run() {
				
			try {
				while (!destroyPositionQueryFlag) {	
					if(WifiSetting_Info.LOCK == null)
						WifiSetting_Info.LOCK = new Object();
					synchronized(WifiSetting_Info.LOCK)
					{
						WifiSetting_Info.LOCK.notify();
					
						if (WifiSetting_Info.blockFlag&&selfDestroy_flag&&WifiSetting_Info.mClient!=null) {
								Thread.sleep(200);
								Log.d("mpeng","run~~~~~~delay~~~~~~~~~");
								WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
						}		
						if(Config.isMutiThread)	
							 WifiSetting_Info.LOCK.wait();					
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				destroy();
			}
		
	}

	
	
	
}
