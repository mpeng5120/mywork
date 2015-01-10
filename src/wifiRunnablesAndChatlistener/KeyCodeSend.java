package wifiRunnablesAndChatlistener;

import wifiProtocol.WifiSendDataFormat;
import android.app.Activity;
import android.widget.Toast;

import com.dataInAddress.AddressPublic;
import com.explain.HexDecoding;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.WifiSetting_Info;

public class KeyCodeSend {
	private static Activity targetActivity;
	private static FinishRunnable finishRunnable ;
	/**
	 * 输入要发送的keycode值
	 * 
	 * @param i
	 *            keycode值
	 */
	public static void send(int data, Activity targetActivity1) {
		targetActivity=targetActivity1;
		if (WifiSetting_Info.mClient != null) {
			SendDataRunnable sendDataRunnable = new SendDataRunnable(
					new WifiSendDataFormat(HexDecoding.int2byteArray4(data),
							AddressPublic.keycode_Head), targetActivity);
			if(data==999&&targetActivity instanceof TR_Programming_Activity)
			{
				 finishRunnable = new FinishRunnable(targetActivity,
						"keycode"+data+"发送完毕",55);
			}
			else
			{
				 finishRunnable = new FinishRunnable(targetActivity,
							"keycode"+data+"发送完毕");
			}
			// 这种方式只是用于写数据，读数据时暂时不使用这种方式
			sendDataRunnable.setOnlistener(new NormalChatListenner(
					sendDataRunnable, finishRunnable));
			targetActivity.runOnUiThread(sendDataRunnable);
			
		} else {
			Toast.makeText(targetActivity,"请先连接主机", Toast.LENGTH_SHORT).show();
		}

	}

}
