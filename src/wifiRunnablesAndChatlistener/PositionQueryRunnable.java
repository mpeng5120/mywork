package wifiRunnablesAndChatlistener;

import wifiProtocol.WifiReadDataFormat;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dataInAddress.AddressPublic;
import com.dataInAddress.Define;
import com.explain.HexDecoding;
import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

/**
 * ��ǰλ�õ�ļ����߳�
 * @author ������
 *
 */
public class PositionQueryRunnable implements Runnable{

	//��ʱֻҪǰ������Ϣ
	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(AddressPublic.SysSts_curPosition, 4*8*5);
	private byte[] getData;
	private boolean destroyPositionQueryFlag=false;
	private boolean selfDestroy_flag=true;
	private boolean chatlistener_flag=true;
	
	private Activity targetActivity;
	
	private TextView X; //����
	private TextView Y; //��Ʒǰ��
	private TextView Z; //�ϵ�ǰ��
	private TextView Ys; //��Ʒ����
	private TextView Zs; //�ϵ�����
	private Button movingled;//�ƶ���ָʾ��
	
	private int operation = 0; //0:ȫ����1����Ʒ��2���ϵ�
	
	//ȷ��һ��ֻ�ܴ���һ��
	public static boolean existFlag=false;
	private long beforetime;
	private long aftertime;
	/***
	 * �������λ����ʾ
	 * @param targetActivity
	 * @param X
	 * @param Y
	 * @param Z
	 * @param Ys
	 * @param Zs
	 */

	public PositionQueryRunnable(Activity targetActivity, View X, View Y, View Z, View Ys, View Zs,Button moving_led) {
		
		existFlag=true;
		this.targetActivity=targetActivity;
		this.operation = Define.POSITION_ALL;
		
		this.X=(TextView)X;
		this.Y=(TextView)Y;
		this.Z=(TextView)Z;
		this.Ys=(TextView)Ys;
		this.Zs=(TextView)Zs;
		this.movingled=moving_led;
	}
	public PositionQueryRunnable(Activity targetActivity, View X, View Y, View Z, View Ys, View Zs) {
		
		existFlag=true;
		this.targetActivity=targetActivity;
		this.operation = Define.POSITION_ALL;
		
		this.X=(TextView)X;
		this.Y=(TextView)Y;
		this.Z=(TextView)Z;
		this.Ys=(TextView)Ys;
		this.Zs=(TextView)Zs;
	}
	
	/****
	 * �ϵ�����Ʒ��λ��(ֻ��ȡֵ��ͬ����Ҫ��ֵ��textView����ͬ��)
	 * @param targetActivity
	 * @param X
	 * @param Y
	 * @param Z
	 * @param cmd
	 */
	public PositionQueryRunnable(Activity targetActivity, View X, View Y,View Z,int cmd) {
		//System.out.println("PositionQueryRunnable�߳�====="+cmd);
		existFlag=true;
		this.targetActivity=targetActivity;
		this.operation = cmd;
		
		this.X=(TextView)X;
		this.Y=(TextView)Y;
		this.Z=(TextView)Z;
	}
	
	/**
	 * ��ȡ��ǰλ�õ���Ϣ
	 * @return
	 */
	public String[] getCurrentPos() {
		
		String[] temp = new String[5];
		temp[0]=X.getText().toString();
		temp[1]=Y.getText().toString();
		temp[2]=Z.getText().toString();
		temp[3]=Ys.getText().toString();
		temp[4]=Zs.getText().toString();
		return temp;
	}

	/**
	 * ��ȡ��ǰλ�õ���Ϣ(��Ʒ�����ϵ�)
	 * @return
	 */
	public String[] getCurrentPos(int cmd) {
		
		String[] temp = new String[5];
		temp[0]=X.getText().toString();
		if (operation == Define.POSITION_MATERIAL){  //�ϵ�
			temp[2]=Y.getText().toString();
			temp[4]=Z.getText().toString();
		}else if (operation == Define.POSITION_GOODS){ // ��Ʒ
			temp[1]=Y.getText().toString();
			temp[3]=Z.getText().toString();
		}
		return temp;
	}


	//���ٸ��߳�
	public void destroy() {
		existFlag=false;
		destroyPositionQueryFlag=true;
		selfDestroy_flag=false;
		chatlistener_flag=false;
	}

	public byte[] getData() {
		return getData;
	}
	
	
	/***
	 * ��ȡ�ŷ�����ʱ��ͨ���߳��յ���Ϣʱ�Ĵ�����. 
	 */
	private final ChatListener ReadDataFeedback = new ChatListener() {
		@Override
		public void onChat(byte[] message) {
			if(!chatlistener_flag)
				return;
			//���صı�־λSTS1�����ж��Լ���У��
			//formatReadMessage.backMessageCheck(message);
			formatReadMessage.backMessageCheck(message);
			if(!formatReadMessage.finishStatus()){
				try {
					Thread.sleep(20);
					WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try{
				//������ȷ�����
				//�����ص�����	
				//getData=new byte[formatReadMessage.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				//System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
				//�����ռ����������ݽ��д���
				getData=new byte[formatReadMessage.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
				aftertime=System.currentTimeMillis();
//				System.out.println("���ѯʱ��"+(aftertime-beforetime));
				
				Runnable pointShowRunnable=new Runnable() {
					public void run() {
						String x=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,0))))/100));
						String y=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,4*8))))/100));
						String z=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,2*4*8))))/100));
						String ys=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,3*4*8))))/100));
						String zs=String.valueOf(((Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,4*4*8))))/100));
						if(movingled!=null){
						if(!X.getHint().toString().trim().equals(x.trim())||!Y.getHint().toString().trim().equals(y.trim())||
								!Z.getHint().toString().trim().equals(z.trim())||!Ys.getHint().toString().trim().equals(ys.trim())||
								!Zs.getHint().toString().trim().equals(zs.trim())){
							if(!(X.getHint().toString().trim().equals("*****.*"))){
//								movingled.setBackgroundDrawable(TR_Main_Activity.drawablemovingled);
//								targetActivity.sendBroadcast(intent);
								if(!Config.MoveState){
								Config.MoveState = true;
								Intent intent = new Intent();
								intent.setAction("updateBtnBg");
								intent.putExtra("button", "move");
						    	targetActivity.sendBroadcast(intent);
								}
							}
						}else{
//								movingled.setBackgroundDrawable(TR_Main_Activity.drawablenoled);
//							targetActivity.sendBroadcast(intent);
							if(Config.MoveState){
							Config.MoveState = false;
							Intent intent = new Intent();
							intent.setAction("updateBtnBg");
							intent.putExtra("button", "move");
					    	targetActivity.sendBroadcast(intent);
							}
							
						}
						}
						X.setHint(x);
						if (operation == Define.POSITION_ALL){
							Y.setHint(y);
							Z.setHint(z);
							Ys.setHint(ys);
							Zs.setHint(zs);
						}else if (operation == Define.POSITION_MATERIAL){  //�ϵ�
							Y.setHint(z);
							Z.setHint(zs);
						}else if (operation == Define.POSITION_GOODS){ // ��Ʒ
							Y.setHint(y);
							Z.setHint(ys);
						}
						
						if(WifiSetting_Info.ydfgFlag[0]==1){
						    X.setText(x);
						}else{
						    X.setText("*****.*");
						}
						if (operation == Define.POSITION_ALL){
							if(WifiSetting_Info.ydfgFlag[1]==1){
								Y.setText(y);
							}else{
								Y.setText("*****.*");
							}
							
							if(WifiSetting_Info.ydfgFlag[2]==1){
								Z.setText(z);
							}else{
								Z.setText("*****.*");
							}
							if(WifiSetting_Info.ydfgFlag[3]==1){
								Ys.setText(ys);
							}else{
								Ys.setText("*****.*");
							}
							if(WifiSetting_Info.ydfgFlag[4]==1){
								Zs.setText(zs);
							}else{
								Zs.setText("*****.*");
							}
							
						}else if (operation == Define.POSITION_MATERIAL){  //�ϵ�
							if(WifiSetting_Info.ydfgFlag[2]==1){
								Y.setText(z);
							}else{
								Y.setText("*****.*");
							}
							
							if(WifiSetting_Info.ydfgFlag[4]==1){
								Z.setText(zs);
							}else{
								Z.setText("*****.*");
							}
							
						}else if (operation == Define.POSITION_GOODS){ // ��Ʒ
							if(WifiSetting_Info.ydfgFlag[1]==1){
								Y.setText(y);
							}else{
								Y.setText("*****.*");
							}
							
							if(WifiSetting_Info.ydfgFlag[3]==1){
								Z.setText(ys);
							}else{
								Z.setText("*****.*");
							}
							
							
						}
						
					}
				};
				targetActivity.runOnUiThread(pointShowRunnable);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	};
	
	
	@Override
	public void run() {
		while (!destroyPositionQueryFlag) {
			if(WifiSetting_Info.LOCK == null)
				WifiSetting_Info.LOCK = new Object();
			synchronized(WifiSetting_Info.LOCK)
			{
				WifiSetting_Info.LOCK.notify();
				try {			
					if (WifiSetting_Info.blockFlag&&selfDestroy_flag&&WifiSetting_Info.mClient!=null) {
						Thread.sleep(170);
						Log.d("mpeng","run~~~~~~position~~~~");
						WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
					}
				} catch (Exception e) {
					e.printStackTrace();
					destroy();
				}			
				try {				
					 WifiSetting_Info.LOCK.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
		}
	}

}
