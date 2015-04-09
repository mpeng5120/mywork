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
 * ��λ״̬��ѯ
 * @author ������
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
