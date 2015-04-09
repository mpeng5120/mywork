package com.dbutils;

import java.io.File;
import java.util.HashMap;

import android.os.Environment;

/*
 	����һ�г�ʼ�趨�Ĳ���
 */
public class Constans {

	//device��һ���������ͺ�enum����Ӧ����ȫһ��
	//	public static String[] device_list= { "ʵ������", "ʵ�����", "�м�����", "�м����", "��������",
	//		"�������", "����ɱ��ּĴ���", "����ɱ������ݼĴ���", "��ʱ��", "������", "λ��","����"};
	
	public static enum device_defineEnum{ʵ������, ʵ�����, �м�����, �м����, ��������,
		�������, ����ɱ��ּĴ���, ����ɱ������ݼĴ���, ��ʱ��, ������, λ��, ����, ѡ������;

		public static device_defineEnum getDevice(String device){  
			return valueOf(device.toString());  
		}   	
	}
	public static enum nc_defineEnum{ԭ��, ������, ����1, ����2, ����3,
		����4, ����5, ����6;

		public static nc_defineEnum getDevice(String device){  
			return valueOf(device.toString());  
		}   	
	}
	public static enum table_defineEnum{T�༭1, T�༭2, T�༭3, T�༭4, T�༭5,
		T�༭6, T�༭7, T�༭8;

		public static table_defineEnum getDevice(String device){  
			return valueOf(device.toString());  
		}   	
	}
	
	//order���������ݺ�enum��������ȫһ��
	 public static String[] nc_orders = {"MOVE                          �ƶ�ָ��(�ȴ��ƶ���λ)",
		                                 "MOVEP                       �ƶ�ָ��(���ȴ��ƶ���λ)", 
		                                 "IF                                  ����ָ��", 
		                                 "OUT                             �ź����ָ��", 
		                                 "WAIT                           �ȴ�ָ��",
		                                 "COUNTER                  ������ָ��",
		                                 "SEQUENTIAL           �������ָ��",
		                                 "IFThen                        �������תָ��",
	    	                             "OUTP                          �������ָ��", 
	    	                             "ACC                             �Ӽ���ָ��", 
	    	                             "ALARM                       ����ָ��", 
	    	                             "RET                              ����ָ��",
	    	                             "PAUSE                        ��ָͣ��",
	    	                             "PARALLEL                 ��������ָ��", 
	    	                             "END                             ����ָ��" };
    
    public static enum NCorders_enum {
    	 MOVE,MOVEP,IF, OUT, WAIT, COUNTER, SEQUENTIAL,IFThen,   
    	 OUTP,ACC, ALARM, RET, PAUSE, PARALLEL, END;
    	
    	public static NCorders_enum getOrder(String order){  
    	            return valueOf(order.toString());  
    	        }     
        }
    
    /** The Constant PORT. */
    public static final int PORT = 8080;

	//tableָ���Ӧ����
	public static final int Code_AND=5;
	public static final int Code_OR=6;
	public static final int Code_MOVE_IMM=24;
	public static final int Code_MOVE=23;
	public static final int Code_THROUGH =4;
	
	public static final int Code_CPEQ=9;//���
	public static final int Code_CPEQ_IMM=10;//���������
	public static final int Code_CPNE=11;//������
	public static final int Code_CPNE_IMM=12;
	public static final int Code_CPGT=13;//����
	public static final int Code_CPGT_IMM=14;
	public static final int Code_CPLT=15;//С��
	public static final int Code_CPLT_IMM=16;
	public static final int Code_CPGE=17;//���ڵ���
	public static final int Code_CPGE_IMM=18;
	public static final int Code_CPLE=19;//С�ڵ���
	public static final int Code_CPLE_IMM=20;
	
	public static final int Code_INC=58;
	public static final int Code_DEC=59;
	
	
	public static final int Code_SEQ_END=60;//��ӦNC�����е�END
	public static final int Code_SEQ_RET=55;//��Ӧ�����еĳ��򷵻�RET
	
	public static final int Code_A_1=7;
	public static final int Code_B_1=8;
	public static final int Code_OUT_ON=21;
	public static final int Code_OUT_OFF=22;
	public static final int Code_JUMP=27;
	public static final int Code_JUMP_ON=28;
	public static final int Code_CALL=29;
    public static final int Code_CALL_ON=30;
	public static final int Code_QON=25;
    public static final int Code_QOFF=26;
    public static final int Code_ORDER_LABEL=2;
    public static final int Code_ORDER_SUBRoutine=1;
    public static final int Code_ORDER_Q=3;
    public static final int Label_Offset=65536;
    public static final int Subroutine_Offset=256;
    
    //fragment��־λ
    public static final String[] FRAGMENT_DeviceString={"Device_Edit_1","Device_Edit_2","Device_Edit_3","Device_Edit_4","Device_Edit_5","Device_Edit_6","Device_Edit_7","Device_Edit_8","Device_Edit_9","Device_Edit_10","Device_Edit_11","Device_Edit_12","Device_Edit_13"};
    public static final String[] FRAGMENT_NCString={"NC_1","NC_2","NC_3","NC_4","NC_5","NC_6","NC_7","NC_8"};
    public static final String[] FRAGMENT_TableString={"Table_1","Table_2","Table_3","Table_4","Table_5","Table_6","Table_7","Table_8"};
    
    //seekbard��ǰ״̬��ֵ
    public static int currentSeekbarValue=0;
    
	/**
	 * �ļ�Ŀ¼
	 * ����Ӧת��strings��ȥ
	 */
	/*�ļ�Ŀ¼�ṹ*/
	 public static String trFolder="TR";//һ��Ŀ¼   ��Ŀ¼   
	 	 public static String mechanicalParameterFolder="��е����";//����Ŀ¼  
	 	 	public static String ��е���� ="mechanicalParameter.set";
	 	 	public static String �ŷ����� ="servoParameter.set";
	 	 public static String otherParameterFolder="��������";
	 	 public static String zkDataFolder="�ֿ�";
	 	 public static String alarmDataFolder="������Ϣ";
	     public static String mouldDataFolder="ģ������";
	     	public static String mouldData1Folder="ģ������1";//����Ŀ¼  
	     	public static String mouldData2Folder="ģ������2";
	     	public static String cacheFolder="cache";
	     		public static String table�ı�����="Userlog.trt";//�ļ�Ŀ¼  
	     		public static String nc�ı�����="userprg.trt";
	     		public static String �豸��������="device.trt";
	     		public static String ģ������="mould.trt";
	     		
	     		public static String ListData="Data.xml"; //add by mpeng
	     		
	     		public static String dataToDownloadFolder="��Ҫ���ص�����";
	     			public static String ncToTableFolder="ncת��table���data����"; //�弶Ŀ¼  
	     			public static String tableToDataFolder="tableת�����data����";
	     			public static String positionSetDataFolder="λ���趨������";
	     			public static String otherDataFolder="����ʲô����";
			public static String mouldData3Folder="ģ������3";//����Ŀ¼
			public static String mouldData4Folder="ģ������4";
		 public static String otherData1Folder="��������1";//����Ŀ¼  
		 public static String otherData2Folder="��������2";
		 public static String downloadFolder="�����ع���";//����Ŀ¼  	
		 //public static String messageFolder="��Ϣ����";//����Ŀ¼  	
 
    /*sdPATHȡ�û�����SD��λ�ã�File.separatorΪ�ָ�����/��*/  
	 public final static String sdPATH=Environment.getExternalStorageDirectory()+File.separator;//sd��·����
	 	public final static String trPATH=sdPATH+trFolder+File.separator;//һ��·��
	 		public final static String mechanicalParameterPATH=trPATH+mechanicalParameterFolder+File.separator;//����·��  
	 		public final static String otherParameterPATH=trPATH+otherParameterFolder+File.separator;
	 		public final static String rawPATH=trPATH+"raw"+File.separator;
	 		public final static String getdataPATH=trPATH+"getdata"+File.separator;
	 		public final static String zkDataPATH=trPATH+zkDataFolder+File.separator;
	 		public final static String alarmDataPATH=trPATH+alarmDataFolder+File.separator;
	 		public final static String mouldDataPATH=trPATH+mouldDataFolder+File.separator;
	 			public final static String mouldData1PATH=mouldDataPATH+mouldData1Folder+File.separator;//����·��
	 			public final static String mouldData2PATH=mouldDataPATH+mouldData2Folder+File.separator;
	 			public final static String cachePATH=mouldDataPATH+cacheFolder+File.separator;
//	 				public final static String tableTextPATH=mouldData2PATH+tableTextFolder+File.separator;//�ļ�����
//	 				public final static String ncTextPATH=mouldData2PATH+ncTextFolder+File.separator;
//	 				public final static String deviceNamePATH=mouldData2PATH+deviceNameFolder+File.separator;
//	 				public final static String dataToDownloadPATH=mouldData2PATH+dataToDownloadFolder+File.separator;
//	 					public final static String ncToTablePATH=dataToDownloadPATH+ncToTableFolder+File.separator;//�弶Ŀ¼
//	 					public final static String tableToDataPATH=dataToDownloadPATH+tableToDataFolder+File.separator;
//	 					public final static String positionSetDataPATH=dataToDownloadPATH+positionSetDataFolder+File.separator;
//	 					public final static String otherDataPATH=dataToDownloadPATH+otherDataFolder+File.separator;     					
    			
				public final static String mouldData3PATH=mouldDataPATH+mouldData3Folder+File.separator;//����·��
				public final static String mouldData4PATH=mouldDataPATH+mouldData4Folder+File.separator;
    		
			public final static String otherData1PATH=trPATH+otherData1Folder+File.separator;//����·��  
			public final static String otherData2PATH=trPATH+otherData2Folder+File.separator;
    
			public final static String XmlFolder =  mouldDataPATH+cacheFolder;
			
			
			/*
      * �ļ��½���   
      */
			public static TRFileHandler mechanicalParameter=TRFileHandler.create();
			public static TRFileHandler otherParameter=TRFileHandler.create();
			public static TRFileHandler alarmData=TRFileHandler.create();
			public static TRFileHandler mouldData=TRFileHandler.create();
			public static TRFileHandler cache=TRFileHandler.create();
			public static TRFileHandler mouldData2=TRFileHandler.create();
			public static TRFileHandler tableText=TRFileHandler.create();
			public static TRFileHandler ncText=TRFileHandler.create();
			public static TRFileHandler deviceName=TRFileHandler.create();
			public static TRFileHandler dataToDownload=TRFileHandler.create();
			public static TRFileHandler ncToTable=TRFileHandler.create();
			public static TRFileHandler tableToData=TRFileHandler.create();
			public static TRFileHandler positionSetData=TRFileHandler.create();
			public static TRFileHandler otherData=TRFileHandler.create();
			public static TRFileHandler mouldData3=TRFileHandler.create();
			public static TRFileHandler mouldData4=TRFileHandler.create();
			public static TRFileHandler otherData1=TRFileHandler.create();
			public static TRFileHandler otherData2=TRFileHandler.create();
			public static TRFileHandler raw=TRFileHandler.create();
			public static TRFileHandler getdata=TRFileHandler.create();
			public static TRFileHandler download=TRFileHandler.create();
			//public static TRFileHandler message=TRFileHandler.create();
			/**
			 * ά������
			 */
			public static final int  ERROR_LOG=0;
			public static final int  ERROR_REGIST=1;
			public static final int  LOG=3;
			public static final int  CREATE_AGAIN=6;
			
			/*public static final int sevoparas= Constans.mechanicalParameter
 					.readOutFile(Constans.mechanicalParameterPATH +"��ɾ/"+ Constans.�ŷ�����).length/19+1;*/
			
		
			
}
