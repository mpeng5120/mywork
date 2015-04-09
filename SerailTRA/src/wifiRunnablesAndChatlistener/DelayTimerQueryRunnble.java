package wifiRunnablesAndChatlistener;

import wifiProtocol.WifiReadDataFormat;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.explain.HexDecoding;
import com.explain.TableToBinary;
import com.tr.R;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

public class DelayTimerQueryRunnble implements Runnable {

	private ListView tglistView;
	private WifiReadDataFormat timeQueryDataFormat;
	private Activity targetActivity;
	private byte[] getData;
	private int[] idevicelist;
	private int[] odevicelist;
	private TextView[] ideviceTextViews;
	private TextView[] odeviceTextViews;
	
	//确保一次只能存在一个
			public static boolean existFlag=false;
			private  boolean destroyPositionQueryFlag=false;
			private  boolean selfDestroy_flag=true;
			private  boolean chatlistener_flag=true;
	/**
	 * 构造函数
	 */
	public DelayTimerQueryRunnble(Activity targetActivity,ListView targetListView) {
		try{
		// TODO Auto-generated constructor stub
		//System.out.println("构造IO监视");
			existFlag=true;
		this.targetActivity=targetActivity;
		this.tglistView=targetListView;

		if(targetActivity == null){
			System.out.println("targetActivity == null ");
			return;
		}
		if(tglistView == null){
			System.out.println("targetListView == null ");
			return;
		}
		timeQueryDataFormat=new WifiReadDataFormat(0x10007000, 32);
		idevicelist=new int[tglistView.getLastVisiblePosition()-tglistView.getFirstVisiblePosition()+1];
		odevicelist=new int[tglistView.getLastVisiblePosition()-tglistView.getFirstVisiblePosition()+1];
		ideviceTextViews=new TextView[idevicelist.length];
		odeviceTextViews=new TextView[odevicelist.length];
		for (int j = 0; j < idevicelist.length; j++) {
			    idevicelist[j]=Integer.parseInt(((TextView)(tglistView.getChildAt(j).findViewById(R.id.io_address))).getText().toString().trim());
			    odevicelist[j]=Integer.parseInt(((TextView)(tglistView.getChildAt(j).findViewById(R.id.io_address))).getText().toString().trim());;
				ideviceTextViews[j]=((TextView)(tglistView.getChildAt(j).findViewById(R.id.i_state)));
				odeviceTextViews[j]=((TextView)(tglistView.getChildAt(j).findViewById(R.id.o_state)));
		}
		// 仅在当前页面显示
		tglistView.setOnScrollListener(new OnScrollListener() {
		             
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						for (int j = 0; j < idevicelist.length; j++) {
						    idevicelist[j]=Integer.parseInt(((TextView)(tglistView.getChildAt(j).findViewById(R.id.io_address))).getText().toString().trim());
						    odevicelist[j]=Integer.parseInt(((TextView)(tglistView.getChildAt(j).findViewById(R.id.io_address))).getText().toString().trim());;
							ideviceTextViews[j]=((TextView)(tglistView.getChildAt(j).findViewById(R.id.i_state)));
							odeviceTextViews[j]=((TextView)(tglistView.getChildAt(j).findViewById(R.id.o_state)));
					}
					}
				});
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	private ChatListener readDataFeedback=new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			// TODO Auto-generated method stub
			if(!chatlistener_flag)
				return;
			timeQueryDataFormat.backMessageCheck(message);
			if(!timeQueryDataFormat.finishStatus()){
				try {
					Thread.sleep(20);
					WifiSetting_Info.mClient.sendMessage(timeQueryDataFormat.sendDataFormat(), readDataFeedback,targetActivity);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				//发送正确且完成
				//处理返回的数据	
				getData=new byte[timeQueryDataFormat.getLength()];
				//获取返回的数据，从第八位开始拷贝数据
				System.arraycopy(message, 8, getData, 0, timeQueryDataFormat.getLength());
				//对所收集到到的数据进行处理
				Runnable pointShowRunnable=new Runnable() {
					@Override
					public void run() {
						try{
						// TODO Auto-generated method stub
							System.out.println("idevicelist.length===="+idevicelist.length);
						for (int i = 0; i < idevicelist.length; i++) {
							
								byte[] itemp=new byte[1];
								System.arraycopy(getData, (idevicelist[i])/8, itemp, 0, 1);
							    if(((int)((itemp[0]>>(idevicelist[i]%8))&0x01))==1){
							    	ideviceTextViews[i].setText("ON");
									ideviceTextViews[i].setBackgroundColor(Color.RED);
							    }else{
							    	//System.out.println("输入设备OFF");
							    	ideviceTextViews[i].setText("OFF");
									ideviceTextViews[i].setBackgroundColor(Color.GRAY);
							    }
						
								byte[] otemp=new byte[1];
							    System.arraycopy(getData, (odevicelist[i])/8+16, otemp, 0, 1);
							    if(((int)((otemp[0]>>(odevicelist[i]%8))&0x01))==1){
							    	odeviceTextViews[i].setText("ON");
									odeviceTextViews[i].setBackgroundColor(Color.RED);
							    }else{
							    	//System.out.println("输出设备OFF");
							    	odeviceTextViews[i].setText("OFF");
									odeviceTextViews[i].setBackgroundColor(Color.GRAY);
							    }
							
							
						}} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				 if( targetActivity != null )
					targetActivity.runOnUiThread(pointShowRunnable);
			}
		}
	};
	/**
	 * 线程关闭函数
	 */
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
					try {
						if (WifiSetting_Info.blockFlag&&selfDestroy_flag&&WifiSetting_Info.mClient!=null) {
						
							Thread.sleep(200);
							WifiSetting_Info.mClient.sendMessage(timeQueryDataFormat.sendDataFormat(),readDataFeedback,targetActivity);
						}
					} catch (Exception e) {
						e.printStackTrace();
						destroy();
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
