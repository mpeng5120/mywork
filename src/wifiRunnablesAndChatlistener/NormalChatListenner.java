package wifiRunnablesAndChatlistener;

import com.dataInAddress.Define;
import com.explain.HexDecoding;
import com.explain.TableTranslate;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

import wifiProtocol.WifiReadDataFormat;
import wifiProtocol.WifiReadMassiveDataFormat;
import wifiProtocol.WifiSendDataFormat;
import android.app.Activity;
import android.app.AlertDialog;
import android.widget.Toast;


/**
 * �˼���������ֻ�����ڷ������ݣ������ڶ���λ������ʱ�����޷���ȡ���ص����ݲ�������д���
 * @author ������
 *
 */
public class NormalChatListenner implements ChatListener {

	private SendDataRunnable sendRunnable;
	private FinishRunnable finishRunnable;
	
	//�������������´���
	private SendDataRunnable sendRunnable1;
	
	private Object formatMessage;

	private Activity targetActivity;
	
	//ģʽ�л���־����������1����������0��
	private int modeChange;
	/**
	 * ������������������һ�����ݷ���
	 * @param sendRunnable
	 * @param finishRunnable
	 */
	public NormalChatListenner(SendDataRunnable sendRunnable,FinishRunnable finishRunnable) {
		// TODO Auto-generated constructor stub
		this.sendRunnable=sendRunnable;
		this.finishRunnable=finishRunnable;
		
		this.targetActivity=finishRunnable.getActivity();
		this.formatMessage=sendRunnable.getWifiSendDataFormat();
		
		modeChange=0;
	}

	/**
	 * ��������ת���ƣ����sendRunnable���л�����SendRunnable
	 * @param sendRunnable
	 * @param sendRunnable1
	 */
	public NormalChatListenner(SendDataRunnable sendRunnable,SendDataRunnable sendRunnable1,Activity targetActivity) {
		// TODO Auto-generated constructor stub
		this.sendRunnable=sendRunnable;
		this.sendRunnable1=sendRunnable1;
		
		this.targetActivity=targetActivity;
		this.formatMessage=sendRunnable.getWifiSendDataFormat();
		
		modeChange=1;
	}
	
	@Override
	public void onChat(byte[] message) {
		// TODO Auto-generated method stub
		try{
		//���صı�־λSTS1�����ж��Լ���У��
		if(formatMessage instanceof WifiReadDataFormat){
			HexDecoding.printHexString("dian", message);
			WifiReadDataFormat formatReadMessage=((WifiReadDataFormat)formatMessage);
			
			formatReadMessage.backMessageCheck(message);

			if(!formatReadMessage.finishStatus()){
				if ((System.currentTimeMillis() - WifiSetting_Info.wifiTimeOut) > 60000*2&&WifiSetting_Info.progressDialog!=null) {
					WifiSetting_Info.progressDialog.dismiss();
					WifiSetting_Info.progressDialog=null;
					
					Runnable wifiTimeOutRunnable=new Runnable() {
						public void run() {
							 new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
							   .setMessage("ͨѶ��ʱ�����ز�����")
							   .setPositiveButton("֪����", null).show();
							//Toast.makeText(targetActivity, "ͨѶ��ʱ�����ز�����", Toast.LENGTH_SHORT).show();
						}
					};
					targetActivity.runOnUiThread(wifiTimeOutRunnable);
					
				} else {
					targetActivity.runOnUiThread(sendRunnable);	
				}
				
				
			}else {
				//WifiSetting_Info.wifiTimeOut=0;
				//WifiSetting_Info.getBackData=formatReadMessage.getFinalData();
				WifiSetting_Info.getBackData=message;
				
				targetActivity.runOnUiThread(finishRunnable);		

			}
		}else if(formatMessage instanceof WifiReadMassiveDataFormat){
			WifiReadMassiveDataFormat formatReadMessage=(WifiReadMassiveDataFormat)formatMessage;
			
			formatReadMessage.backMessageCheck(message);

			if(!formatReadMessage.finishStatus()){
				if ((System.currentTimeMillis() - WifiSetting_Info.wifiTimeOut) > 60000*2&&WifiSetting_Info.progressDialog!=null) {
					WifiSetting_Info.progressDialog.dismiss();
					WifiSetting_Info.progressDialog=null;
					
					Runnable wifiTimeOutRunnable=new Runnable() {
						public void run() {
							new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
							   .setMessage("ͨѶ��ʱ�����ز�����")
							   .setPositiveButton("֪����", null).show();
						}
					};
					targetActivity.runOnUiThread(wifiTimeOutRunnable);
				} else {
				targetActivity.runOnUiThread(sendRunnable);	
				}
			}else {
				//WifiSetting_Info.wifiTimeOut=0;
				WifiSetting_Info.getBackData=formatReadMessage.getFinalData();
				
				targetActivity.runOnUiThread(finishRunnable);		

			}
		}else if (formatMessage instanceof WifiSendDataFormat) {
			WifiSendDataFormat formatSendMessage=(WifiSendDataFormat)formatMessage;
			//���صı�־λSTS1�����ж�
			formatSendMessage.backMessageCheck(message);
			if(!formatSendMessage.finishStatus()){
				if ((System.currentTimeMillis() - WifiSetting_Info.wifiTimeOut) > 60000*2&&WifiSetting_Info.progressDialog!=null) {
					WifiSetting_Info.progressDialog.dismiss();
					WifiSetting_Info.progressDialog=null;
					
					Runnable wifiTimeOutRunnable=new Runnable() {
						public void run() {
							new AlertDialog.Builder(targetActivity).setTitle("��ʾ")
							   .setMessage("ͨѶ��ʱ�����ز�����")
							   .setPositiveButton("֪����", null).show();
						}
					};
					targetActivity.runOnUiThread(wifiTimeOutRunnable);
				} else {
				targetActivity.runOnUiThread(sendRunnable);	
				}
			}else {		
				//WifiSetting_Info.wifiTimeOut=0;
				if(modeChange==0){
					System.out.println("������ϣ�����������������������������������������������");
					targetActivity.runOnUiThread(finishRunnable);	
				}else if(modeChange==1){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("˳����ת������������������������������������������������");
					targetActivity.runOnUiThread(sendRunnable1);	
				}
						
			}
		}} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
