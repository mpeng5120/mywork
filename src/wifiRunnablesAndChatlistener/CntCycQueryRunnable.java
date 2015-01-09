package wifiRunnablesAndChatlistener;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.explain.HexDecoding;
import com.wifiexchange.ChatListener;
import com.wifiexchange.WifiSetting_Info;

import wifiProtocol.WifiReadDataFormat;

public class CntCycQueryRunnable implements Runnable{

	private WifiReadDataFormat formatReadMessage=new WifiReadDataFormat(0x1000B220,20);
	private byte[] getData;
	private Activity targetActivity;
	private boolean destroyPositionQueryFlag=false;
	private boolean selfDestroy_flag=true;
	private boolean chatlistener_flag=true;
	private TextView Cnt1; //ȡ������
	private TextView Cnt2; //����Ʒ����
	private TextView Cyc1; //��������
	private TextView Cyc2; //ȡ������
	private TextView Cyc3; //ȫ����
	private int operation; //0:������1������
    public CntCycQueryRunnable(Activity targetActivity, View Cnt1, View Cnt2,int operation){
    	//System.out.println("CC�߳̿�ʼ");
    	this.targetActivity=targetActivity;
    	this.Cnt1=(TextView)Cnt1;
    	this.Cnt2=(TextView)Cnt2;
    	this.operation=0;
    }

    public CntCycQueryRunnable(Activity targetActivity, View Cyc1, View Cyc2, View Cyc3,int operation){
    	//System.out.println("CC�߳̿�ʼ");
    	this.targetActivity=targetActivity;
    	this.operation=1;
    	this.Cyc1=(TextView)Cyc1;
    	this.Cyc2=(TextView)Cyc2;
    	this.Cyc3=(TextView)Cyc3;
    }
	//���ٸ��߳�
	public void destroy() {
		//System.out.println("CC�̹߳ر�");
		destroyPositionQueryFlag=true;
		selfDestroy_flag=false;
		chatlistener_flag=false;
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
				try{
				//������ȷ�����
				//�����ص�����
				getData=new byte[formatReadMessage.getLength()];
				//��ȡ���ص����ݣ��ӵڰ�λ��ʼ��������
				System.arraycopy(message, 8, getData, 0, formatReadMessage.getLength());
				//�����ռ����������ݽ��д���
				//HexDecoding.printHexString("������Ϣ", getData);
				Runnable infoShowRunnable=new Runnable() {
					public void run() {
						if(operation==0){
						Cnt1.setText(String.valueOf((String.valueOf(HexDecoding.Array4Toint(getData,0)))));
						Cnt2.setText(String.valueOf((String.valueOf(HexDecoding.Array4Toint(getData,4)))));
						}else{
						Cyc1.setText(String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,8)))/100));
						Cyc2.setText(String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,12)))/100));
						Cyc3.setText(String.valueOf(Double.valueOf(String.valueOf(HexDecoding.Array4Toint(getData,16)))/100));
						}
					}
				};
				targetActivity.runOnUiThread(infoShowRunnable);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
	};


	@Override
	public void run() {
		try {
		while (!destroyPositionQueryFlag) {
			while (WifiSetting_Info.blockFlag&&selfDestroy_flag&&WifiSetting_Info.mClient!=null) {
				Thread.sleep(1000);
				WifiSetting_Info.mClient.sendMessage(formatReadMessage.sendDataFormat(), ReadDataFeedback,targetActivity);
			}
		}} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
