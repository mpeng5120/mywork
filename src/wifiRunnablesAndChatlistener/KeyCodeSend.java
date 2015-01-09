package wifiRunnablesAndChatlistener;

import wifiProtocol.WifiSendDataFormat;

import android.R;
import android.app.Activity;
import android.widget.Toast;

import com.dataInAddress.AddressPublic;
import com.explain.HexDecoding;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.WifiSetting_Info;

public class KeyCodeSend {

	/**
	 * ����Ҫ���͵�keycodeֵ
	 * 
	 * @param i
	 *            keycodeֵ
	 */
	public static void send(int data, Activity targetActivity) {

		if (WifiSetting_Info.mClient != null) {
			
			SendDataRunnable sendDataRunnable = new SendDataRunnable(
					new WifiSendDataFormat(HexDecoding.int2byteArray4(data),
							AddressPublic.keycode_Head), targetActivity);
			FinishRunnable finishRunnable = new FinishRunnable(targetActivity,
					"keycode"+data+"�������");
			// ���ַ�ʽֻ������д���ݣ�������ʱ��ʱ��ʹ�����ַ�ʽ
			sendDataRunnable.setOnlistener(new NormalChatListenner(
					sendDataRunnable, finishRunnable));
			targetActivity.runOnUiThread(sendDataRunnable);
			
		} else {
			Toast.makeText(targetActivity,"������������", Toast.LENGTH_SHORT).show();
		}

	}

}
