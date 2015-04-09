package wifiRunnablesAndChatlistener;
import com.tr.TR_Welcome_Activity;
import com.tr.crash.CrashApplication;
import com.tr.programming.Config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class WifiStateListenManager extends BroadcastReceiver  {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("mpeng","onReceive "+intent.getAction());
		 String action = intent.getAction();
		 if(action.equals("android.intent.action.BOOT_COMPLETED")) {     // boot  
	        Log.d("mpeng","onReceiveonReceiveonReceiveonReceiveonReceive");
	        Config.firstCreate  =false;	
	    	Intent intent1 = new Intent();
			intent1.setAction("bootComplete");
			context.sendBroadcast(intent1);
	     }
	}   
}
