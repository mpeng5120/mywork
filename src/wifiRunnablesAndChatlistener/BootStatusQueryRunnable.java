package wifiRunnablesAndChatlistener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.dbutils.ArrayListBound;
import com.explain.HexDecoding;
import com.explain.NCTranslate;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;
import wifiProtocol.WifiReadDataFormat;
public class BootStatusQueryRunnable implements Runnable{

	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x2000D008,1);//0x10007053
	private byte[] getData;
	private Activity targetActivity;
    //private  SharedPreferences sPinfo;
	private boolean destroyPositionQueryFlag=false;
	private boolean selfDestroy_flag=true;
	private boolean chatlistener_flag=true;
	private Handler  myHandler = null;
	
    public BootStatusQueryRunnable(Activity targetActivity,Handler myhand){
    	this.targetActivity=targetActivity;
    	this.myHandler = myhand;    	
    }
	//销毁该线程
	public void destroy() {
		//System.out.println("警报线程关闭");
		destroyPositionQueryFlag=true;
		selfDestroy_flag=false;
		chatlistener_flag=false;
	}

	/** 读取伺服参数时的通信线程收到信息时的处理函数. */
	private final ChatListener ReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			try{
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
				System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
				//对所收集到到的数据进行处理
				//HexDecoding.printHexString("警报信息", getData);	
				  boolean sdCardExist = Environment.getExternalStorageState().equals(
							android.os.Environment.MEDIA_MOUNTED);
						if(((int)(getData[0]&0xFF))==1&&sdCardExist){
							myHandler.sendEmptyMessage(0000);
					    }
				
			}} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!destroyPositionQueryFlag) {

					if (WifiSetting_Info.blockFlag&&selfDestroy_flag&&WifiSetting_Info.mClient!=null) {
						try {							
							Thread.sleep(350);								
							WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
					
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	

			
			}
	}


}

