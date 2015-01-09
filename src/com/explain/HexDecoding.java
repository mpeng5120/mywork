package com.explain;


public class HexDecoding {

	/**
	 * С�˷����ݴ���ת����ʽ���ֽ�������
	 * @param n ����ֱ�������ʽΪ16��������ʮ����Ҳ�У�
	 * @return ����һ��1���ֽڳ��ȵ�����
	 */
	public static byte[] int2byte(int n) {
		byte[] temp=new byte[1];

		temp[0]=(byte)(n%256);

		return temp;
	}
	/**
	 * С�˷����ݴ���ת����ʽ���ֽ�������
	 * @param n ����ֱ�������ʽΪ16��������ʮ����Ҳ�У�
	 * @return ����һ��2���ֽڳ��ȵ�����
	 */
	public static byte[] int2byteArray2(int n) {
		byte[] temp=new byte[2];
		if(n>=0){
		    temp[0]=(byte)(n%256);
		    temp[1]=(byte) ((n/(256))%256);
		    //System.out.println("��temp[0]="+temp[0]+"         ��temp[1]="+temp[1]);
		}else{
			
				n=n-256;
				temp[0]=(byte)(n%256);
				temp[1]=(byte) ((n/(256))%256);
			
			//System.out.println("1��temp[0]="+temp[0]+"         1��temp[1]="+temp[1]);
	    }
		
		return temp;
	}

	/**
	 * С�˷����ݴ���ת����ʽ���ֽ�������
	 * @param n ����ֱ�������ʽΪ16��������ʮ����Ҳ�У�
	 * @return ����һ���ĸ��ֽڳ��ȵ�����
	 */
	public static byte[] int2byteArray4(int n) {
		byte[] temp=new byte[4];
		
		if(n>=0){
			temp[0]=(byte)(n%256);
		    temp[1]=(byte)((n/(256))%256);
		    temp[2]=(byte)((n/(0xFFFF+1))%256);
		    temp[3]=(byte)(n/(0xFFFFFF+1));
		}else{
				n=n-16843008;
			temp[0]=(byte)(n%256);
		    temp[1]=(byte)((n/(256))%256);
		    temp[2]=(byte)((n/(0xFFFF+1))%256);
		    temp[3]=(byte)(n/(0xFFFFFF+1));
		    //System.out.println("temp[0]="+temp[0]+"         temp[1]="+temp[1]+"         temp[2]="+temp[2]+"         temp[3]="+temp[3]);
		}
		return temp;
	}


	/**
	 * 2�ֽ�ת��Ϊint�ͣ���2�ֽ������벻Ҫʹ�øú���
	 * @param temp
	 * @return
	 */
	public static int Array2Toint(byte[] temp) {

		int value1=(int)(temp[0]&0xff);
		int value2=(int)(temp[1]&0xff);
		int value=value1+(value2<<8);
	
		if(value>32767){
			//System.out.println("value1111111="+value);
			value=value-65536;
		}
		return value;
	}
	
	public static int Array2Toint(byte[] temp, int beginIndex) {

		int value1=(int)(temp[beginIndex]&0xff);
		int value2=(int)(temp[beginIndex+1]&0xff);
		int value=value1+(value2<<8);
		//System.out.println("value2222222="+value);
		if(value>32767){
			value=value-65536;
		}
		return value;
	}


	/**
	 * 4�ֽ�ת��Ϊint�ͣ���4�ֽ������벻Ҫʹ�øú���
	 * @param temp
	 * @return
	 */
	public static int Array4Toint(byte[] temp) {

		int value1=(int)(temp[0]&0xff);
		int value2=(int)(temp[1]&0xff);
		int value3=(int)(temp[2]&0xff);
		int value4=(int)(temp[3]&0xff);
		//System.out.println("value1="+value1+"       value2="+value2+"       value3="+value3+"       value4="+value4);
		//System.out.println("value1="+value1+"       (value2<<8)="+(value2<<8)+"       (value3<<16)="+(value3<<16)+"       (value4<<24)="+(value4<<24));
		int value=value1+(value2<<8)+(value3<<16)+(value4<<24);
		
		return value;
	}

	/**
	 * 4�ֽ�ת��Ϊint�ͣ���4�ֽ������벻Ҫʹ�øú���
	 * @param temp
	 * @return
	 */
	public static int Array4Toint(byte[] temp,int beginIndex) {

		int value1=(int)(temp[beginIndex]&0xff);
		int value2=(int)(temp[beginIndex+1]&0xff);
		int value3=(int)(temp[beginIndex+2]&0xff);
		int value4=(int)(temp[beginIndex+3]&0xff);
		int value=value1+(value2<<8)+(value3<<16)+(value4<<24);
		
		return value;
	}

	/**
	 * ʮ�����ַ�����ʮ�������ַ���
	 * @param strPart
	 * @return
	 */
	public static String stringToHexString(String strPart) {

		String hexString = "";

		for (int i = 0; i < strPart.length(); i++) {

			int ch = (int) strPart.charAt(i);

			String strHex = Integer.toHexString(ch);

			hexString = hexString + strHex;

		}
		return hexString;
	}



	private static byte uniteBytes(byte src0, byte src1) {

		byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();

		_b0 = (byte) (_b0 << 4);

		byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();

		byte ret = (byte) (_b0 | _b1);

		return ret;
	}

	/**
	 * ʮ�������ַ���תΪʮ�����ֽ�����
	 * @param src
	 * @return
	 */
	public static byte[] HexString2Bytes(String src)
	{
		byte[] ret = new byte[6];
		byte[] tmp = src.getBytes();
		for(int i=0; i<6; ++i )
		{
			ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
		}

		return ret;

	}

	public static String bytesToHexString(byte[] src){

		StringBuilder stringBuilder = new StringBuilder("");

		if (src == null || src.length <= 0) {

			return null;

		}
		for (int i = 0; i < src.length; i++) {

			int v = src[i] & 0xFF;

			String hv = Integer.toHexString(v);

			if (hv.length() < 2) {

				stringBuilder.append(0);

			}

			stringBuilder.append(hv);

		}

		return stringBuilder.toString();

	}

	/**
	 * byte����ʮ�����ƴ�ӡ
	 * @param hint
	 * @param b
	 */
	public static void printHexString(String hint, byte[] b)

	{

		//System.out.println(hint);

		for (int i = 0; i < b.length; i++)

		{

			String hex = Integer.toHexString(b[i] & 0xFF);

			if (hex.length() == 1)

			{

				hex = '0' + hex;

			}

			//System.out.print(hex.toUpperCase() + " ");

		}

		//System.out.println("");

	}





}
