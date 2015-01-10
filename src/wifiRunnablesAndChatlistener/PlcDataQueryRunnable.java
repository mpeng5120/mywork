package wifiRunnablesAndChatlistener;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import awesomeWatch.TableWatch;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.PlcData;
import com.explain.HexDecoding;
import com.tr.programming.Fragments_Table1;
import com.tr.programming.Fragments_Table2;
import com.tr.programming.Fragments_Table3;
import com.tr.programming.Fragments_Table4;
import com.tr.programming.Fragments_Table5;
import com.tr.programming.Fragments_Table6;
import com.tr.programming.Fragments_Table7;
import com.tr.programming.Fragments_Table8;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

import wifiProtocol.WifiReadMassiveDataFormat;

/**
 * plcData��ѯ������table����
 * 
 * @author ������
 * 
 */
public class PlcDataQueryRunnable implements Runnable {

	private WifiReadMassiveDataFormat formatReadMessage = new WifiReadMassiveDataFormat(
			AddressPublic.finalPlcData_Head, PlcData.End - PlcData.IN_Head);

	private Activity targetActivity;
	private byte[] getData;

	private long beforeTime;
	private long afterTime;

	private static boolean destroyflag = true;
	private boolean alreadygetMsg = true;
	public static boolean issendStop = false;
	private TableWatch tempTableWatch ;
	/**
	 * ���캯��
	 * 
	 * @param tarActivity
	 */
	public PlcDataQueryRunnable(Activity tarActivity,int flag) {
		targetActivity = tarActivity;
		tempTableWatch = new TableWatch(targetActivity,flag);
		destroyflag = true;
	}

	public static void destroy() {
		destroyflag = false;
		WifiSetting_Info.blockFlag = true;
	}

	private final ChatListener ReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			if (!destroyflag)
				return;
			// ���صı�־λSTS1�����ж��Լ���У��
			formatReadMessage.backMessageCheck(message);
			
		//	HexDecoding.printHexString("ÿ�η��ص�PLCdata����", message);
			
			if (!formatReadMessage.finishStatus()) {
				try {
					WifiSetting_Info.mClient.sendHighPriorMessage(formatReadMessage.sendDataFormat(), targetActivity,ReadDataFeedback);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				afterTime = System.currentTimeMillis();
				//System.out.println("PLC��ѯʱ��ķ�" + (afterTime - beforeTime));
				// ������ȷ�����
				// �����ص�����
				getData = new byte[formatReadMessage.getLength()];
				// ��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				System.arraycopy(formatReadMessage.getFinalData(), 0, getData,
						0, formatReadMessage.getLength());
				// �����ռ����������ݽ��д���

				targetActivity.runOnUiThread(backMessageTodoRunnable);
				
			}
		}
	};

	private Runnable backMessageTodoRunnable = new Runnable() {
		@Override
		public void run() {
			try{
			// �������ݣ��ı����
			tempTableWatch.devicePlustheirValue(getData);
			
			// ��ʼ��һ��ɨ�裬�ı����
			formatReadMessage = new WifiReadMassiveDataFormat(
					AddressPublic.finalPlcData_Head, PlcData.End
							- PlcData.IN_Head);
			alreadygetMsg=true;
			Log.e("mpeng"," plcdata query !!!!! over");
			if(issendStop&&targetActivity instanceof TR_Programming_Activity)
			{
				KeyCodeSend.send(999, targetActivity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	};

	@Override
	public void run() {
		try {
		while (destroyflag) {
			if (alreadygetMsg&&!issendStop) {
				beforeTime = System.currentTimeMillis();
				Log.e("mpeng"," plcdata query !!!!!");
				WifiSetting_Info.mClient.sendHighPriorMessage(formatReadMessage.sendDataFormat(), targetActivity,ReadDataFeedback);
				alreadygetMsg=false;
			}
		}} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
