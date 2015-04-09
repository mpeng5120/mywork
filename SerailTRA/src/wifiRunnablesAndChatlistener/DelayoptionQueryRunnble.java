package wifiRunnablesAndChatlistener;

import wifiProtocol.WifiReadDataFormat;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
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
public class DelayoptionQueryRunnble implements Runnable {
	
	//���Ϻõ�plc��ַ����ƫ�Ƶĵ�ַ����������
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x10007000, 32);
	private byte[] getData;
	private Activity targetActivity;
	private ListView listView;
	
	private int[] idevicelist1;
	private int[] odevicelist1;
	private TextView[] ideviceTextViews1;
	private TextView[] odeviceTextViews1;
	
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
	public DelayoptionQueryRunnble(Activity targetActivity, ListView tempListView) {
		try{
			existFlag=true;
		this.targetActivity=targetActivity;		
		listView=tempListView;
		
		idevicelist1=new int[listView.getLastVisiblePosition()-listView.getFirstVisiblePosition()+1];
		odevicelist1=new int[listView.getLastVisiblePosition()-listView.getFirstVisiblePosition()+1];
		ideviceTextViews1=new TextView[idevicelist1.length];
		odeviceTextViews1=new TextView[odevicelist1.length];
		for (int j = 0; j < idevicelist1.length; j++) {
			
			if((((TextView)(listView.getChildAt(j).findViewById(R.id.isymbol1))).getText().toString().trim()).equals("")){
				idevicelist1[j]=0xffff;
			}else{
				idevicelist1[j]=TableToBinary.searchAddress(((TextView)(listView.getChildAt(j).findViewById(R.id.isymbol1))).getText().toString().trim(),false);
			}
			if((((TextView)(listView.getChildAt(j).findViewById(R.id.osymbol1))).getText().toString().trim()).equals("")){
				odevicelist1[j]=0xffff;
			}else{
				odevicelist1[j]=TableToBinary.searchAddress(((TextView)(listView.getChildAt(j).findViewById(R.id.osymbol1))).getText().toString().trim(),false);
			}
			ideviceTextViews1[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.isymbol1)));
			odeviceTextViews1[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.osymbol1)));	
		}
		// ���ڵ�ǰҳ����ʾ
				listView.setOnScrollListener(new OnScrollListener() {
		             
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						for (int j = 0; j < idevicelist1.length; j++) {
							
							if((((TextView)(listView.getChildAt(j).findViewById(R.id.isymbol1))).getText().toString().trim()).equals("")){
								idevicelist1[j]=0xffff;
							}else{
								idevicelist1[j]=TableToBinary.searchAddress(((TextView)(listView.getChildAt(j).findViewById(R.id.isymbol1))).getText().toString().trim(),false);
							}
							if((((TextView)(listView.getChildAt(j).findViewById(R.id.osymbol1))).getText().toString().trim()).equals("")){
								odevicelist1[j]=0xffff;
							}else{
								odevicelist1[j]=TableToBinary.searchAddress(((TextView)(listView.getChildAt(j).findViewById(R.id.osymbol1))).getText().toString().trim(),false);
							}
							ideviceTextViews1[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.isymbol1)));
							odeviceTextViews1[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.osymbol1)));	
						}
					}
				});
				pointShowRunnable=new Runnable() {
					@Override
					public void run() {
						try{
							// TODO Auto-generated method stub
							for (int i = 0; i < idevicelist1.length; i++) {
								if(idevicelist1[i]==0xffff){
									ideviceTextViews1[i].setBackgroundColor(Color.GRAY);
									//break;
								}else{
									byte[] itemp1=new byte[1];
									System.arraycopy(getData, (idevicelist1[i])/8, itemp1, 0, 1);
								    if(((int)((itemp1[0]>>(idevicelist1[i]%8))&0x01))==1){
										ideviceTextViews1[i].setBackgroundColor(Color.RED);
								    }else{
										ideviceTextViews1[i].setBackgroundColor(Color.GRAY);
								    }
								}
								if(odevicelist1[i]==0xffff){
									odeviceTextViews1[i].setBackgroundColor(Color.GRAY);
									//break;
								}else{
									byte[] otemp1=new byte[1];
								    System.arraycopy(getData, (odevicelist1[i])/8+16, otemp1, 0, 1);
								    if(((int)((otemp1[0]>>(odevicelist1[i]%8))&0x01))==1){
										odeviceTextViews1[i].setBackgroundColor(Color.RED);
								    }else{
										odeviceTextViews1[i].setBackgroundColor(Color.GRAY);
								    }
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
