package wifiRunnablesAndChatlistener;

import java.io.IOException;
import wifiProtocol.WifiReadDataFormat;
import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.dataInAddress.PlcData;
import com.explain.HexDecoding;
import com.explain.TableToBinary;
import com.tr.R;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

/**
 * ��λ״̬��ѯ
 * @author ������
 *
 */
public class CounterQueryRunnable implements Runnable {
	
	//���Ϻõ�plc��ַ����ƫ�Ƶĵ�ַ����������
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x40024000,4*Define.MAX_COUNTER_NUM );
	private byte[] getData;
	
	private static boolean selfDestroy_flag;
	
	
	private Activity targetActivity;
	private ListView listView;
	
	private int[] devicelist;
	private TextView[] deviceTextViews;
	private Runnable pointShowRunnable;
	
	//ȷ��һ��ֻ�ܴ���һ��
	public static int existFlag=0;
	private static int flag=0;
	/**
	 * ���캯��
	 * @param targetActivity
	 * @param viewbeingChangedArrayList
	 */
	public CounterQueryRunnable(Activity targetActivity, ListView tempListView) {
		try{
		//System.out.println("��������ѯ�߳�");
		existFlag++;
		System.out.println("������existFlag="+existFlag);
		selfDestroy_flag=true;
		
		this.targetActivity=targetActivity;
		
		listView=tempListView;
		
		
		devicelist=new int[listView.getLastVisiblePosition()-listView.getFirstVisiblePosition()+1];
		deviceTextViews=new TextView[devicelist.length];
		
		
		for (int j = 0; j < devicelist.length; j++) {
			devicelist[j]=TableToBinary.searchAddress(
						((TextView)(listView.getChildAt(j).findViewById(R.id.symbolText))).getText().toString().trim(),false);
		    //System.out.println("�ҵ��ļ�������ƽ������");
		}
		for (int j = 0; j < deviceTextViews.length; j++) {
			deviceTextViews[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.currentValueText)));
		}
		pointShowRunnable=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < devicelist.length; i++) {
					byte[] temp=new byte[4];
					System.arraycopy(getData, devicelist[i]*4, temp, 0, 4);
					
					deviceTextViews[i].setText(String.valueOf(HexDecoding.Array4Toint(temp)));

				}
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
	public static void destroy() {

			System.out.println("�������̹߳ر�");
			
			existFlag=0;
			flag=0;
			selfDestroy_flag=false;
	}
	
	@Override
	public void run() {
		try {
		while (existFlag==1&&WifiSetting_Info.mClient!=null) {
			while (WifiSetting_Info.blockFlag&&selfDestroy_flag) {
				flag=1;
				Thread.sleep(170);
				WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
			}
		}
		if(existFlag!=1&&flag!=1){
			while(WifiSetting_Info.blockFlag&&selfDestroy_flag){
				flag=1;
				Thread.sleep(170);
				WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
            }
		}} catch (Exception e) {
			e.printStackTrace();
			destroy();
		}
	}

	
	
	
}
