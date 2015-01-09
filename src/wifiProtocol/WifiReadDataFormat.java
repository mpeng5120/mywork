package wifiProtocol;

import android.app.Activity;
import android.util.Log;

import com.explain.HexDecoding;
import com.wifiexchange.ChatListener;

public class WifiReadDataFormat {

	// ���ڶ�ȡ���ݣ�ֻ��һ�Σ�һ�η�����ɺ�ֻҪȷ�Ϸ��ͳɹ����㲻�ٷ���
	// ÿһ����ȡ���ݹ��̱����½���ͬ�����ʵ�������ҵ��ù��캯�����г�ʼ��
	// ���ͽ�����־
	private boolean finish_status = false;

	// ��ȡ���ݵĵ�ַ
	private int readDataAddress;
	// ��ȡ���ݵĳ���
	private int byteLength;
	// ��У����λ��Ĭ����ȷ����У�����ʱ���˱�����Ϊfalse
	private boolean error_flag = true;

	private byte[] getData = null;
	
	private Activity targetActivity ; //�������ݶ�Ӧ��Ŀ��Activity
	private ChatListener listener ; //��Ӧ��λ���������ݵĴ������
	private boolean isHighPriority; //�Ƿ����ȷ���
	
	private final String TAG ="WifiReadDataFormat";

	public boolean isHighPriority() {
		return isHighPriority;
	}

	public void setHighPriority(boolean isHighPriority) {
		this.isHighPriority = isHighPriority;
	}

	public Activity getTargetActivity() {
		return targetActivity;
	}

	public void setTargetActivity(Activity targetActivity) {
		this.targetActivity = targetActivity;
	}

	public ChatListener getListener() {
		return listener;
	}

	public void setListener(ChatListener listener) {
		this.listener = listener;
	}
	
	public int getLength() {
		return this.byteLength;
	}

	/**
	 * ���캯�������������ݷ�װ����ֻ��Ҫһ�ξ��ܶ������ݣ�
	 * ��˲���Ҫÿ�ν���ͨѶ��������һ������
	 * 
	 * @param readDataAddress
	 *            ��Ҫ��ȡ���ݵĵ�ַ
	 * @param byteLength
	 *            ��Ҫ��ȡ���ݵĳ���
	 */
	public WifiReadDataFormat(int readDataAddress, int byteLength) {
		// ��ֵ
		this.readDataAddress = readDataAddress;
		this.byteLength = byteLength;
	}

	/**
	 * ����ɼ��Ŀ����������÷�����Ϣ�ĺ���
	 * 
	 * @return
	 */
	public byte[] sendDataFormat() {
		// ����У���Ƿ���ȷ
		if (error_flag) {
			return sendReadFormat();
		} else {
			return sendAskAgainDataFormat();
		}
	}

	/**
	 * �κεĶ�ȡ���ݣ�������Э���װ������д��ַ
	 * 
	 * @return
	 */
	private byte[] sendReadFormat() {
		// һ�η��ͣ�data��������240���ֽ�
		/*
		 * ���ݰ�ͷ8�ֽ�д��
		 */
		byte[] temp8 = new byte[8];
		// ʶ��λ ID+TYPE
		temp8[0] = WifiSendDataFormat.padTomain_read;

		// STS1 ������ȷ��־λ(��ʱ�Ȳ��ܣ�Ŀǰ��ƽ�巢����λ����)��0��ʾ�޴�(�����ط�)
		// STS2 �����Ƿ������־��0��ʾδ����
		// STS1��STS2��ͬʹ��һ���ֽ�
		temp8[1] = (byte)0x02; //WifiSendDataFormat.STS_Send_Begin;
		// д�볤�ȣ���������Ϊ2�ֽ�
		// �����ڳ�ʼ��ʱ�Ѿ�ȷ��
		System.arraycopy(HexDecoding.int2byteArray2(byteLength), 0, temp8, 2, 2);

		// С�˷�д������ƫ�Ƶ�ַ��4���ֽ�
		System.arraycopy(HexDecoding.int2byteArray4(readDataAddress), 0, temp8,
				4, 4);
		/*
		 * ͷ8�ֽ�д����ɣ�����д����
		 */
		byte sumTemp = 0;
		for (int k = 0; k < temp8.length; k++) {
			sumTemp += temp8[k];
		}

		byte[] sumBytes = new byte[1];
		sumBytes[0] = (byte) (0xff - (byte) (sumTemp));

		// д�����շ�������
		// �����޸�
		byte[] finalResultBytes = new byte[temp8.length + sumBytes.length];

		System.arraycopy(temp8, 0, finalResultBytes, 0, temp8.length);

		System.arraycopy(sumBytes, 0, finalResultBytes, temp8.length,
				sumBytes.length);
		return finalResultBytes;

	}

	/**
	 * �����ط�����
	 * 
	 * @return
	 */
	private byte[] sendAskAgainDataFormat() {
		System.out.println("ʹ���ط�����");
		// ͷ�˸��ֽ�����
		byte[] temp8 = new byte[8];

		// ʶ��λ ID+TYPE���˴�����ν
		temp8[0] = WifiSendDataFormat.padTomain_read;
		// STS1 ������ȷ��־λ(��ʱ�Ȳ��ܣ�Ŀǰ��ƽ�巢����λ����)��0��ʾ�޴�(�����ط�)
		// STS2 �����Ƿ������־�������������Ϊ1
		// STS1��STS2��ͬʹ��һ�ֽ�
		temp8[1] = (byte) WifiSendDataFormat.STS_Error_Back;
		// д�볤�ȣ���������Ϊ2�ֽ�
		// �����ڳ�ʼ��ʱ�Ѿ�ȷ��
		System.arraycopy(HexDecoding.int2byteArray2(byteLength), 0, temp8, 2, 2);

		// С�˷�д������ƫ�Ƶ�ַ��4���ֽ�
		System.arraycopy(HexDecoding.int2byteArray4(readDataAddress), 0, temp8,
				4, 4);
		/*
		 * ͷ8�ֽ�д����ɣ�����д����
		 */
		byte sumTemp = 0;
		for (int k = 0; k < temp8.length; k++) {
			sumTemp += temp8[k];
		}

		byte[] sumBytes = new byte[1];
		sumBytes[0] = (byte) (0xff - (byte) (sumTemp));

		// д�����շ�������
		// �����޸�
		byte[] finalResultBytes = new byte[temp8.length + sumBytes.length];

		System.arraycopy(temp8, 0, finalResultBytes, 0, temp8.length);

		System.arraycopy(sumBytes, 0, finalResultBytes, temp8.length,
				sumBytes.length);
		return finalResultBytes;
	}

	/**
	 * ����Ƿ��Ѿ��������
	 * 
	 * @return true��ʾ������ϣ�false ��ʾ��û���
	 */
	public boolean finishStatus() {
		if (finish_status == true)
			return true;
		else
			return false;
	}

	/**
	 * ���к�У�飬����ط���־λ
	 * 
	 * @param backmessage
	 */
	public void backMessageCheck(byte[] backmessage) {

		// ���Ƚ��к�У�飬Ȼ���鷵���������Ƿ����ط���־

		// 1. �������ݵĳ��ȣ�����Ϊ0����ʾ������ΪӦ������
		byte[] temp_length = new byte[2];
		System.arraycopy(backmessage, 2, temp_length, 0, 2);
		//System.out.println("byteLength="+byteLength+"     HexDecoding.Array2Toint(temp_length)="+HexDecoding.Array2Toint(temp_length));
		if (byteLength != HexDecoding.Array2Toint(temp_length)) {
			Log.e("mpeng","the bytelength is "+byteLength+"HexDecoding.Array2Toint(temp_length) "+HexDecoding.Array2Toint(temp_length));
			System.out.println("����ƫ�ƴ���!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			error_flag = false;
			return;
		}
		getData = new byte[HexDecoding.Array2Toint(temp_length)];
		System.arraycopy(backmessage, 8, getData, 0,
				HexDecoding.Array2Toint(temp_length));
		// 2. �����ݼ������У����
		byte sumTemp = 0;
		for (int k = 0; k < 8 + HexDecoding.Array2Toint(temp_length); k++) {
			sumTemp += backmessage[k];
		}

		sumTemp = (byte) (0xff - (byte) (sumTemp));

		// 3. ����У���Ƿ���ȷ
		if (backmessage[8 + HexDecoding.Array2Toint(temp_length)] == sumTemp) {
			// ��У����ȷ
			// ���Է��������ݣ�ȷ���Ƿ���Ҫ��λ���ط�
			error_flag = true;

			if ((backmessage[1] & 0x01) == 0) {
				// STS1Ϊ��ȷ������Ҫ�ط�
				finish_status = true;
			}

		} else {
			// ��У�����
			Log.e("mpeng","the sumTemp is "+(sumTemp&0xff)+"backmessage[8 + HexDecoding.Array2Toint(temp_length)] "
			+(backmessage[8 + HexDecoding.Array2Toint(temp_length)]&0xff));
			System.out.println("һ���Զ�ȡ��У�����");
			error_flag = false;
		}

	}

	/**
	 * �������յõ�������
	 * 
	 * @return
	 */
	public byte[] getFinalData() {
		return getData;
	}

}
