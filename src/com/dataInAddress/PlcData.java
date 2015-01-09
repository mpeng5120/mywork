package com.dataInAddress;

public class PlcData {

	// plcData����λ�������ݽṹ����˳�򲻵����󣬷����ı䣬�˴�Ӧ����ʱ�޸�
	// ����Ϊ���ṹ��ĸ��Ե�ͷ��ַ(��Ϊƫ�Ƶ�ַ������Ӧ��λ����ʵ�ʵ�ַ)

	public static int IN_Head = 0;

	private static int OUT_Head = IN_Head + (Define.MAX_IOS_NUM + 31) / 32 * 4;
	private static int MLR_Head = OUT_Head + (Define.MAX_IOS_NUM + 31) / 32 * 4;

	private static int MRI_Head = MLR_Head + 4;

	private static int MRO_Head = MRI_Head + 8 * 4;

	private static int MCNTInc_Head = MRO_Head + 8 * 4;
	private static int MCNTUp_Head = MCNTInc_Head   ////////////////////////////
			+ (Define.MAX_COUNTER_NUM + 31) / 32 * 4;
	private static int MCNTClr_Head = MCNTUp_Head
			+ (Define.MAX_COUNTER_NUM + 31) / 32 * 4;

	private static int MTMUp_Head = MCNTClr_Head + (Define.MAX_COUNTER_NUM + 31)
			/ 32 * 4;
	private static int MTMEx_Head = MTMUp_Head + (Define.MAX_TIMER_NUM + 31)
			/ 32 * 4;

	private static int SDI_Head = MTMEx_Head + (Define.MAX_TIMER_NUM + 31) / 32
			* 4;
	private static int SDO_Head = SDI_Head + 128 * 4;

	private static int MLD_Head = SDO_Head + 128 * 4;
	private static int MCNT_Head = MLD_Head + 32 * 4;// ������

	private static int MTM_Head = MCNT_Head + Define.MAX_TIMER_NUM * 4;// ��ʱ��

	public static int End = MTM_Head + Define.MAX_TIMER_NUM * 4;

	/**
	 * X��ͷ��ʵ������
	 * 
	 * @param n
	 * @return
	 */
	public static int getINoffsetAddress(int n) {
		return (n / 32) * 4 + IN_Head;
	}

	public static int getOUToffsetAddress(int n) {
		return (n / 32) * 4 + OUT_Head;
	}

	public static int getMRIoffsetAddress(int n) {
		return (n / 32) * 4 + MRI_Head;
	}

	public static int getMROoffsetAddress(int n) {
		return (n / 32) * 4 + MRO_Head;
	}

	public static int getMTMExoffsetAddress(int n) {
		return (n / 32) * 4 + MTMEx_Head;
	}

	public static int getMTMUpoffsetAddress(int n) {
		return (n / 32) * 4 + MTMUp_Head;
	}
	
	public static int getMLRoffsetAddress(int n) {
		return (n / 32) * 4 + MLR_Head;
	}
	
	public static int getCNToffsetAddress(int n) {
		return (n / 32) * 4 + MTMUp_Head;
	}
	
	public static int getMCNTUpoffsetAddress(int n) {
		return (n / 32) * 4 + MCNTUp_Head;
	}

	/**
	 * �˴�ÿ����ַ��Ӧ�ĸ��ֽڣ������Ƕ�Ӧĳһ��bit
	 * 
	 * @param n
	 * @return
	 */
	public static int getSDIoffsetAddress(int n) {
		return n * 4 + SDI_Head;
	}

	/**
	 * �˴�ÿ����ַ��Ӧ�ĸ��ֽڣ������Ƕ�Ӧĳһ��bit
	 * 
	 * @param n
	 * @return
	 */
	public static int getSDOoffsetAddress(int n) {

		return n * 4 + SDO_Head;
	}



	public static int getMLDoffsetAddress(int n) {
		return n * 4 + MLD_Head;
	}

	/**
	 * ��ȡ�������豸�ĵ�ַ
	 * 
	 * @param n
	 *            λ�ñ��
	 * @return
	 */
	public static int getMCNToffsetAddress(int n) {
		return n * 4 + MCNT_Head;
	}

	/**
	 * ��ȡ��ʱ���豸�ĵ�ַ
	 * 
	 * @param n
	 *            λ�ñ��
	 * @return
	 */
	public static int getMTMoffsetAddress(int n) {
		return n * 4 + MTM_Head;
	}
	
/*	public static void printAllAddress(){
		System.out.println("OUT head"+OUT_Head+"OUT End"+MLR_Head);
		System.out.println("MRO head"+MRO_Head+"MRo End"+MCNTInc_Head);
		System.out.println("MCNTINC head"+MCNTInc_Head+"MRo End"+MCNTUp_Head);
		System.out.println("MCNTCLR head"+MCNTClr_Head+"MCNTCLR End"+MTMUp_Head);
		System.out.println("SDO head"+SDO_Head+"SDO End"+MLD_Head);
	}*/

}
