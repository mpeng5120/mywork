package wifiRunnablesAndChatlistener;

import wifiProtocol.WifiReadDataFormat;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.PlcData;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

public class FinishRunnable implements Runnable{
	
	private Activity tempActivity;
	private String tempString;
	private ProgressDialog progressTemp;
	private Runnable runnable;
	private int i=0;
	
	
	private byte[] getData;
	private  ChatListener QueryArmDataFeedback;
	private boolean isFromModelD =false;	
	private SendDataRunnable sendDataRunnable;
	private WifiReadDataFormat formatReadMessage;
	
	public FinishRunnable(Activity target,String appearText,ProgressDialog progressTemp){
		tempActivity=target;
		tempString=appearText;

		this.progressTemp=progressTemp;
		i=2;
	}
	public FinishRunnable(String appearText,Activity target){
		tempActivity=target;
		tempString=appearText;
		i=2;
	}
	
	public FinishRunnable(String appearText,Activity target,boolean isFromM ){
		tempActivity=target;
		tempString=appearText;
		i=2;
		this.isFromModelD = isFromM;
	}
	public FinishRunnable(Activity target,String appearText){
		tempActivity=target;
		tempString=appearText;
		i=0;
	}
	public FinishRunnable(Activity target,String appearText,int index){
		tempActivity=target;
		tempString=appearText;
		i=index;
	}
	public FinishRunnable(Activity target){//防止在界面上重复输出“数据发送完毕”
		tempActivity=target;
		i=1;
    }
	public Activity getActivity() {
		return tempActivity;
		
	}
	/**
	 * 带有界面处理的构造函数
	 * @param target
	 * @param appearText
	 * @param runnable
	 */
	public FinishRunnable(Activity target,String appearText,ProgressDialog progressTemp,Runnable runnable){
		tempActivity=target;
		tempString=appearText;
		
		this.progressTemp=progressTemp;
		this.runnable=runnable;
		i=2;
	}
	public FinishRunnable(Activity target,ProgressDialog progressTemp,Runnable runnable){
		tempActivity=target;
		
		this.progressTemp=progressTemp;
		this.runnable=runnable;
		i=1;
	}
	public FinishRunnable(Activity target,String appearText,Runnable runnable){
		tempActivity=target;
		tempString=appearText;
		
		this.runnable=runnable;
		i=0;
	}
	public FinishRunnable(Activity target,Runnable runnable){
		tempActivity=target;
		this.runnable=runnable;
		i=1;
	}
	@Override
	public void run() {
		
		try{
		
		if(progressTemp!=null){
			progressTemp.dismiss();
			WifiSetting_Info.progressDialog=null;
			progressTemp=null;
		}
		
		//界面响应线程
		if(runnable!=null){
			if(tempActivity==null){
				return;
			}
			tempActivity.runOnUiThread(runnable);
		}
		
		if(i==0){
		   Toast.makeText(tempActivity, tempString, Toast.LENGTH_SHORT).show();
		}
//		else if(i==55)
//		{
//			Toast.makeText(tempActivity, tempString, Toast.LENGTH_SHORT).show();
//			PlcDataQueryRunnable.issendStop = false;
//		}
		WifiSetting_Info.blockFlag=true;
		//WifiSetting_Info.sendProgrammingfinish=1;
		if(i==2){
			new AlertDialog.Builder(tempActivity).setTitle("提示")
			   .setMessage(tempString)
			   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					if(isFromModelD)
					{
					// 保存完机械参数后读取一下 
					QueryArmDataFeedback = new ChatListener() {
						
						@Override
						public void onChat(byte[] message) {
							// TODO Auto-generated method stub
							Log.e("mpeng","QueryArmDataFeedback");

							//返回的标志位STS1处的判断以及和校验
							formatReadMessage.backMessageCheck(message);
							if(!formatReadMessage.finishStatus()){
								getActivity().runOnUiThread(sendDataRunnable);			
							}else {
									//发送正确且完成
									//处理返回的数据	
									getData=new byte[formatReadMessage.getLength()];
									//获取返回的数据，从第八位开始拷贝数据
									System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
									// TODO Auto-generated method stub
									// 取出轴数，并初始化CONFIG
									if((getData[0]&0xff) == 1&&(getData[1]&0xff) == 2&&(getData[2]&0xff) == 3&&(getData[3]&0xff) == 4&&(getData[4]&0xff) == 5)
									{	
									  Config.ArmNum = 5;
									}
									else if((getData[0]&0xff) == 1&&(getData[1]&0xff) == 2&&(getData[2]&0xff) == 255&&(getData[3]&0xff) == 4&&(getData[4]&0xff) == 255)
									{
									  Config.ArmNum = 3;
									}
									  Log.e("mpeng","the get data is X: " +(getData[0]&0xff)+"y :"+(getData[1]&0xff)+" h:"+(getData[2]&0xff)+"z:"+(getData[3]&0xff)+"l:"+(getData[4]&0xff)); 
							        formatReadMessage=new WifiReadDataFormat(AddressPublic.model_Head,28);
									
								}
								
						}
					};
					
					formatReadMessage=new WifiReadDataFormat(0x200061B8,8);
				
					SendDataRunnable	sendArmDataRunnable = new SendDataRunnable(QueryArmDataFeedback, formatReadMessage,
								getActivity());
					getActivity().runOnUiThread(sendArmDataRunnable);
					
					}
					
				}
			}).show();
			   //Toast.makeText(tempActivity, tempString, Toast.LENGTH_SHORT).show();
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

}
