package wifiRunnablesAndChatlistener;

import java.util.List;

import wifiProtocol.WifiReadDataFormat;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.explain.TableToBinary;
import com.tr.R;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

/**
 * ��λ״̬��ѯ
 * @author ������
 *
 */
public class OptionQueryRunnble implements Runnable {
	
	//���Ϻõ�plc��ַ����ƫ�Ƶĵ�ַ����������
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x10007000, 32);
	private byte[] getData;
	private Activity targetActivity;
	private Runnable pointShowRunnable;	
	private RadioGroup btnSelectRG;
	private List<View> myList;

	//ȷ��һ��ֻ�ܴ���һ��
			public static boolean existFlag=false;
			private  boolean destroyPositionQueryFlag=false;
			private  boolean selfDestroy_flag=true;
			private  boolean chatlistener_flag=true;
	/**
	 * ���캯��
	 * @param targetActivity
	 * @param viewbeingChangedArrayList
	 */
	public OptionQueryRunnble(Activity targetActivity, List<View> tempListView, RadioGroup RG) {
	
			existFlag=true;
			this.targetActivity=targetActivity;	
			btnSelectRG  = RG;
			myList = tempListView;
			pointShowRunnable=new Runnable() {
					@Override
					public void run() {
						
						switch (btnSelectRG.getCheckedRadioButtonId())
						{
							case R.id.gerRadioBtn:
							{
								
							}
								break;
							case R.id.preRadioBtn:
							{
								
							}
								break;
						}
					}
				};
	
	}
	
	
	
	
	
	
	/** ��ȡ�ŷ�����ʱ��ͨ���߳��յ���Ϣʱ�Ĵ�����. */
	private final ChatListener ReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			if(!chatlistener_flag)
				return;
			//���صı�־λSTS1�����ж��Լ���У��
			formatReadMessage.backMessageCheck(message);
			
			if(!formatReadMessage.finishStatus()){

				WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
			
			}else {
				//������ȷ�����
				//�����ص�����	
				getData=new byte[formatReadMessage.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				System.arraycopy(formatReadMessage.getFinalData(),0, getData, 0, formatReadMessage.getLength());
				//�����ռ����������ݽ��д���
				
				targetActivity.runOnUiThread(pointShowRunnable);

			}
		}
	};
	
	//���ٸ��߳�
	public  void destroy() {
		existFlag=false;
		destroyPositionQueryFlag=true;
		selfDestroy_flag=false;
		chatlistener_flag=false;
	}
	
	@Override
	public void run() {
		
		while (!destroyPositionQueryFlag) {			
		try {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			destroy();
		}
	}

	}

	
	
	
}
