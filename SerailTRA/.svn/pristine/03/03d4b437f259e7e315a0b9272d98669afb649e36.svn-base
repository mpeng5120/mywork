package wifiRunnablesAndChatlistener;

import java.io.IOException;
import wifiProtocol.WifiReadDataFormat;
import android.app.Activity;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.dataInAddress.PlcData;
import com.explain.HexDecoding;
import com.explain.TableToBinary;
import com.tr.R;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

/**
 * 复位状态查询
 * @author 李婷婷
 *
 */
public class CounterQueryRunnable implements Runnable {
	
	//整合好的plc地址加上偏移的地址（计数器）
	/*private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(
			AddressPublic.finalPlcData_Head+PlcData.getMCNToffsetAddress(0),
			4*Define.MAX_COUNTER_NUM );*/
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x40024000,4*Define.MAX_COUNTER_NUM );
	private byte[] getData;
	private Activity targetActivity;
	private ListView listView;
	private int[] devicelist;
	private TextView[] deviceTextViews;
	private Runnable pointShowRunnable;
	//确保一次只能存在一个
			public static boolean existFlag=false;
			private  boolean destroyPositionQueryFlag=false;
			private  boolean selfDestroy_flag=true;
			private  boolean chatlistener_flag=true;
	/**
	 * 构造函数
	 * @param targetActivity
	 * @param viewbeingChangedArrayList
	 */
	public CounterQueryRunnable(Activity targetActivity, ListView tempListView) {
		try{
		//System.out.println("计数器查询线程");
			existFlag=true;
		System.out.println("计数器existFlag="+existFlag);
		selfDestroy_flag=true;
		
		this.targetActivity=targetActivity;
		
		listView=tempListView;
		
		
		devicelist=new int[listView.getLastVisiblePosition()-listView.getFirstVisiblePosition()+1];
		deviceTextViews=new TextView[devicelist.length];
		
		
		for (int j = 0; j < devicelist.length; j++) {
			devicelist[j]=TableToBinary.searchAddress(
						((TextView)(listView.getChildAt(j).findViewById(R.id.symbolText))).getText().toString().trim(),false);
		    //System.out.println("找到的计数器在平板的序号");
		}
		for (int j = 0; j < deviceTextViews.length; j++) {
			deviceTextViews[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.currentValueText)));
		}
		// 仅在当前页面显示
				listView.setOnScrollListener(new OnScrollListener() {
		             
					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						for (int j = 0; j < devicelist.length; j++) {
							devicelist[j]=TableToBinary.searchAddress(
										((TextView)(listView.getChildAt(j).findViewById(R.id.symbolText))).getText().toString().trim(),false);			
						}
						for (int j = 0; j < deviceTextViews.length; j++) {
							deviceTextViews[j]=((TextView)(listView.getChildAt(j).findViewById(R.id.currentValueText)));
						}
					}
				});
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
	
	
	
	
	
	
	
	/** 读取伺服参数时的通信线程收到信息时的处理函数. */
	private final ChatListener ReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
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
				System.arraycopy(formatReadMessage.getFinalData(),0, getData, 0, formatReadMessage.getLength());
				//对所收集到到的数据进行处理
				
				targetActivity.runOnUiThread(pointShowRunnable);

			}
		}
	};
	
	//销毁该线程
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
			while (WifiSetting_Info.blockFlag&&selfDestroy_flag&&WifiSetting_Info.mClient!=null) {
				Thread.sleep(200);
				WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			destroy();
		}
	}

	
	
	
}
