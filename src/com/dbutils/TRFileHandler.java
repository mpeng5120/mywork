package com.dbutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;


import com.explain.HexDecoding;



public class TRFileHandler {
	 String mpath;
	 String mfileName;
	 String mstr;
	 File mfolder;
	 File mfile;
	 
	 
	 private TRFileHandler(){
	 }
	    
	 public static TRFileHandler create(){
	     return new TRFileHandler();
	 }
//�õ�	 
//	 public TRFileHandler  createFolder(String pathString){
//		 this.path=pathString;
//		 File folder=new File(path);
//      		if (!folder.exists()) {//�ж��ļ���Ŀ¼�Ƿ����  
//      			folder.mkdir();//����������򴴽��ļ���	
//      			this.file=folder;
//      		}
//		this.file=folder;
//		return this;
//		
//	 }
	 public TRFileHandler  createFolder(String parentpath,String foldername){
		 this.mpath=parentpath+foldername+File.separator;
		 File folder=new File(mpath);
      		if (!folder.exists()) {//�ж��ļ���Ŀ¼�Ƿ����  
      			folder.mkdir();//����������򴴽��ļ���	
      			this.mfolder=folder;
      		}
		this.mfolder=folder;
		return this;
		
	 }
//�õ�
//	 public TRFileHandler createFile(String fileNameString) {
//		 this.fileName=fileNameString;
//		 File file2=new File(path+fileName); 
//		 try {
//			file2.createNewFile();//�����ļ�
//			
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		 this.file=file2;
//		return this;	
//	}
	 //�������createFolderʹ��
	 public TRFileHandler createFile(String filename) {
//	 this.mfileName=filename;
		
	 File file=new File(mpath+filename); 
	 if (file.exists()) {
		 return this;		
	 }else {
		 try {
			 file.createNewFile();//�����ļ�
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		 this.mfile=file;
		
	}	
	
	return this;	
}
	 //���Ե���ʹ��
//	 public TRFileHandler createFile(String parentpath,String filename) {
////		 this.mfileName=filename;
//		 File file=new File(parentpath+filename); 
//		 try {
//			 file.createNewFile();//�����ļ�
//			
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		 this.mfile=file;
//		return this;	
//	}
//	 
	//�õ�   (public void writeFile(String filePath,String data))	 
//	 public TRFileHandler writeFile(String data)  {
//		 try {
//			 //Ҫ��Ҫfilepath��
//			 FileWriter fileWriter=new FileWriter(file,true);//true��ʾ�ں�����Ӷ����Ǹ���
//			 BufferedWriter bufferwriter = new BufferedWriter(fileWriter);
//			 bufferwriter.write(data);
//			 bufferwriter.close();
//		 } catch (Exception e) {
//			 // TODO: handle exception
//		 }
//		return this;
//       
//	 }
//	 public TRFileHandler writeFile(String data)  {
//		 try {
//			 FileWriter fileWriter=new FileWriter(mfile,true);//true��ʾ�ں�����Ӷ����Ǹ���
//			 BufferedWriter bufferwriter = new BufferedWriter(fileWriter);
//			 bufferwriter.write(data);
//			 bufferwriter.close();
//		 } catch (Exception e) {
//			 // TODO: handle exception
//		 }
//		return this;
//       
//	 }
	//�������createFolderʹ��
	 public TRFileHandler writeFile(String filename,String data)  {
		 String filepath=mpath+filename;
		 File file=new File(filepath);
		 if (!file.exists()) {
			 return this;		
		 }else {
			 try {
				 FileWriter fileWriter=new FileWriter(file,true);//true��ʾ�ں�����Ӷ����Ǹ���
				 BufferedWriter bufferwriter = new BufferedWriter(fileWriter);
				 bufferwriter.write(data);
				 bufferwriter.close();
			 } catch (Exception e) {
				 // TODO: handle exception
			 }
			return this;
		}	
	 }
	 //д���ֽ���ʽ�����ݣ�Ŀǰ����mould.h
	 public TRFileHandler writeFileFromByte(String filename,byte[] data){
		 String filepath=mpath+filename;
		 FileOutputStream fos = null;
		 try{
			 //����������
			 fos = new FileOutputStream(filepath,true);
			 //����д���ļ�
			 fos.write(data);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }finally{
			 try {
				 fos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		 }
		 return this;
	 }
	 //���ļ�����Ϊbyte���飬Ŀǰ����mould.h
	public byte[] readFileToByte(String filename) {
		String filepath=mpath+filename;
		FileInputStream in =null;
		byte[] bytes = null;
		ByteArrayOutputStream out=null;
		try {
			in=new FileInputStream(filepath);
			out = new ByteArrayOutputStream(1024);
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				bytes = out.toByteArray();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bytes;
//		InputStream is = null;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        try {
//            is = new FileInputStream(pathStr);// pathStr �ļ�·��
//            byte[] b = new byte[1024];
//            int n;
//            while ((n = is.read(b)) != -1) {
//                out.write(b, 0, n);
//            }// end while
//        } catch (Exception e) {
//            log.error(getText("TimingMmsService.error") + e.getMessage());
//            throw new Exception("System error,SendTimingMms.getBytesFromFile", e);
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (Exception e) {
//                    log.error(e);// TODO
//                }// end try
//            }// end if
//        }// end try
//
//        return out.toByteArray();

		

	
	}
	 
//		//�õ� 
//	 public TRFileHandler cleanFile()  {
//		 try {
//			 //Ҫ��Ҫfilepath��
//			 FileWriter fileWriter=new FileWriter(file);//true��ʾ�ں�����Ӷ����Ǹ���
//			 BufferedWriter bufferwriter = new BufferedWriter(fileWriter);
//			 bufferwriter.write("");
//			 bufferwriter.close();
//		 } catch (Exception e) {
//			 // TODO: handle exception
//		 }
//		return this;       
//	 }

	 public TRFileHandler cleanFile(String filename)  {
		 String filepath=mpath+filename;
		 File file=new File(filepath);
		 try {
			 
			 FileWriter fileWriter=new FileWriter(file);//true��ʾ�ں�����Ӷ����Ǹ���
			 BufferedWriter bufferwriter = new BufferedWriter(fileWriter);
			 bufferwriter.write("");
			 bufferwriter.close();
		 } catch (Exception e) {
			 // TODO: handle exception
		 }
		return this;

       
	 }
	 
	 
	//�õ�	 
	 public String readFilebin(String fileNameString){
		 StringBuilder str =new StringBuilder();
			try {
				//�ļ����õĵ�ַ
				String fileName=Constans.mechanicalParameterPATH;
				//ByteArrayOutputStream byteArray=new ByteArrayOutputStream();
				DataInputStream dis = new DataInputStream(new FileInputStream(fileName)); 		
				
				byte[] itemBuf = new byte[2];  
			
				while ((dis.read(itemBuf) )!=-1) {				
					str.append(HexDecoding.bytesToHexString(itemBuf));
				}
				dis.close();

				//					 Toast.makeText(FileCreate3.this, "��ȡ�ļ��ɹ�",Toast.LENGTH_SHORT).show();				

			} catch (FileNotFoundException e) {
				//					 Toast.makeText(FileCreate3.this, "��ȡ�ļ�ʧ��",Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			java.lang.System.out.println(str.toString());
			return str.toString();
		}

	
//	//�õ�
//	 public String readFile(String filePath) {
//		 File file=new File(filePath);
//		 StringBuffer stringBuffer = new StringBuffer(); 
//		 FileReader filereader;
//		 
//		 if (!file.exists()) {
////			 Toast.makeText(FileCreate3.this, "�ļ�������",Toast.LENGTH_SHORT).show();
//			 return "1";		
//		 }
//		 
//			 
//			try {
//				filereader = new FileReader(filePath);
//				BufferedReader bufferreader=new BufferedReader(filereader);	
//							 
//				 while (true){
//			            String data = bufferreader.readLine();
//			            if (null == data)  { break; }
//			            stringBuffer.append(data + "\n");
//			        }
//				 bufferreader.close();
//				 filereader.close();
////				 return stringBuffer.toString();
//			 } 
//			
//			 catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return stringBuffer.toString();
//		
//
//		 
//	 }
	 
	//�õ� 
	//������������ٴ�����ʾ 
//	 public String[] readOutFile(String filePath) {
//		 File file=new File(filePath);
//		 StringBuffer stringBuffer = new StringBuffer(); 
//		 FileReader filereader;
////		 ArrayList<String> readlist=new ArrayList<String>();
//		 String[] readString = null;
//		 
//		 if (!file.exists()) {
//			 return null;		
//		 }		 
//			try {
//				filereader = new FileReader(filePath);
//				BufferedReader bufferreader=new BufferedReader(filereader);	
//							 
//				 while (true){
//			            String data = bufferreader.readLine();
//			            if (null == data)  { break; }
//			            stringBuffer.append(data + "\n");
//			        }
//				 bufferreader.close();
//				 filereader.close();
////				 return stringBuffer.toString();
////				 readString=stringBuffer.toString().split("\\W+");
//				 readString=stringBuffer.toString().split("[\\W&&[^,]]+");//��ȡ���ַ����Զ��Ŷ���ȡ���ַ���
//				 
//			 } 
//			
//			 catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return readString;
//		
//
//		 
//	 }
	 public String readAlarmOutFile(String filepath) {
		 File file=new File(filepath);
		 FileReader filereader;
		 String readString ="";	 
			try {
				filereader = new FileReader(filepath);
				BufferedReader bufferreader=new BufferedReader(filereader);	
				int i=1;
				while(true){
			            String data = bufferreader.readLine();
			            byte[] databyte=null; 
			            if(data==null){break;}
			            data=data.replace("\0", "");
			            if(i==1){data=data.substring(1, data.length());}
			            i=0;
			            databyte=data.getBytes("gb2312");
			           // System.out.println("����   "+data.length()+"   "+databyte.length);
			            if(databyte.length==20){readString=readString+data;}
			            if(databyte.length==19){readString=readString+data+"\0";}
			            if(databyte.length==18){readString=readString+data+"\0\0";}
			            if(databyte.length==17){readString=readString+data+"\0\0\0";}
			            if(databyte.length==16){readString=readString+data+"\0\0\0\0";}
			            if(databyte.length==15){readString=readString+data+"\0\0\0\0\0";}
			            if(databyte.length==14){readString=readString+data+"\0\0\0\0\0\0";}
			            if(databyte.length==13){readString=readString+data+"\0\0\0\0\0\0\0";}
			            if(databyte.length==12){readString=readString+data+"\0\0\0\0\0\0\0\0";}
			            if(databyte.length==11){readString=readString+data+"\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==10){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==9){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==8){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==7){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==6){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==5){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==4){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==3){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==2){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==1){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			            if(databyte.length==0){readString=readString+data+"\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0";}
			        }
				 bufferreader.close();
				 filereader.close();
			 } 
			
			 catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return readString; 
	 }
	 public String readAlarmOutFile1(String filepath) {
		 File file=new File(filepath);
		 FileReader filereader;
		 String readString ="";	 
			try {
				filereader = new FileReader(filepath);
				BufferedReader bufferreader=new BufferedReader(filereader);	
				int i=1;
				while(true){
			            String data = bufferreader.readLine();
			            if(data==null){break;}
			            data=data.replace("\0", "");
			            if(i==1){data=data.substring(1, data.length()); System.out.println(i+"      "+data);}
			            i=0;
			            readString=readString+data+"///";
			        }
				 bufferreader.close();
				 filereader.close();
			 }  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return readString; 
	 }
	 public String[] readOutFile(String filepath) {
//		 String filepath=mpath+filename;
		 File file=new File(filepath);
		 StringBuffer stringBuffer = new StringBuffer(); 
		 FileReader filereader;
		 String[] readString = null;
		 if (!file.exists()) {
//			 return null;		
		 }	 
			try {
				filereader = new FileReader(filepath);
				BufferedReader bufferreader=new BufferedReader(filereader);	
//				BufferedReader bufferreader=new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"UTF-8"));	
							 
				 while (true){
			            String data = bufferreader.readLine();
			            if (null == data)  { break; }
			            stringBuffer.append(data + "\n");
			        }
				 bufferreader.close();
				 filereader.close();
//				 return stringBuffer.toString();
//				 readString=stringBuffer.toString().split("\\W+");
//				 readString=stringBuffer.toString().split("[\\W&&[^,]]+");//��ȡ���ַ����Զ��Ŷ���ȡ���ַ���
//				 readString=stringBuffer.toString().split("=|/");//��=��/���ָ�
				 readString=stringBuffer.toString().split("/");//��/���ָ�
			 } 
			
			 catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return readString;
		

		 
	 }
/**
 * @Description: �г��ļ������е��ļ�
 */
	 public ArrayList<String> getfiles(String folderpath){
		 ArrayList<String> list_setting=new ArrayList<String>();
			File filename=new File(folderpath);
			File[] filenames=filename.listFiles();
			 System.out.println("���ȣ�"+filenames.length);
			 for(int i=0;i<filenames.length;i++){
				 list_setting.add(filenames[i].getName());
				System.out.println("�ļ�����"+filenames[i].getName());
			 }
			 return list_setting;
	 }
		   

	
	 /**
	  * 
	 * @Title: deleteFolder 
	 * @Description: ɾ���ļ��У��ݹ�ɾ�����е�file
	 * @param folderpath
	 * @return    
	 * @return boolean    
	 * @throws
	  */
	 public boolean deleteFolder(String folderpath) {
		 File folder=new File(folderpath);
		 File[] files = folder.listFiles();  
		 boolean flag = false;
		    for (int i = 0; i < files.length; i++) {   
		        
		        if (files[i].isFile()) { //ɾ�����ļ�     
		        	flag = deleteFile(files[i].getAbsolutePath());   
//		            if (!flag) break;   
		        } //ɾ����Ŀ¼   
		        else {   
		            flag = deleteFolder(files[i].getAbsolutePath());   
//		            if (!flag) break;   
		        }   
		    }   
//		    if (!flag) return false;   
		    //ɾ����ǰĿ¼   
		    if (folder.delete()) {   
		        return true;   
		    } else {   
		        return false;   
		    }   
	 
	 }
	 public boolean deleteFile(String filePath) {   
	     boolean flag = false;   
	     File file = new File(filePath);   
	     // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��   
	     if (file.isFile() && file.exists()) {   
	         file.delete();   
	         flag = true;   
	     }   
	     return flag;   
	 }  
	 /**
	  * 
	 * @Title: renameFolder 
	 * @Description: ��mouldData���޸��ļ������ֺ�����������
	 * @param oldFolderName
	 * @param newFolderName
	 * @return    
	 * @return boolean    
	 * @throws
	  */
	 public boolean renameFolder(String oldFolderName,String newFolderName) {   
		 
	     boolean flag = false;   
		 File file = new File(Constans.mouldDataPATH+ oldFolderName);        
         if(file.isDirectory()){//���ڸ��ļ��вŸ���
        	 file.renameTo(new File(Constans.mouldDataPATH+ newFolderName)); 
        	 flag = true;  
         }
	     return flag;   
	 }  
	 
	 
		/**
		 * 
		* @Title: copyFolder 
		* @Description: �����ļ���
		* @param oldPath
		* @param newPath    
		* @return void    
		* @throws
		 */
		 public void copyDirectiory(String oldPath,String newPath){
			try {
				(new File(newPath)).mkdirs();//���ļ��д��ھʹ���
		        // ��ȡԴ�ļ��е�ǰ�µ��ļ���Ŀ¼
		        File[] file = (new File(oldPath)).listFiles();
		        for (int i = 0; i < file.length; i++) {
		            if (file[i].isFile()) {//���ļ���ֱ�Ӹ���
		                // Դ�ļ�
		                File sourceFile = file[i];
		                // Ŀ���ļ�
		                File targetFile = new File(new File(newPath).getAbsolutePath() + File.separator + file[i].getName());
		                copyFile(sourceFile, targetFile);
		            }
		            if (file[i].isDirectory()) {//���ļ��У��ݹ�
		                // ׼�����Ƶ�Դ�ļ���
		                String dir1 = oldPath + "/" + file[i].getName();
		                // ׼�����Ƶ�Ŀ���ļ���
		                String dir2 = newPath + "/" + file[i].getName();
		                copyDirectiory(dir1, dir2);
		            }
		        }

			} catch (Exception e) {
				System.out.println("�����ļ��г���");
				e.printStackTrace();		}
		 }
		 /**
		  * 
		 * @Title: copyFile 
		 * @Description: �����ļ�
		 * @param sourceFile
		 * @param targetFile    
		 * @return void    
		 * @throws
		  */
		private void copyFile(File sourceFile, File targetFile) {

			BufferedInputStream inBuff = null;
	        BufferedOutputStream outBuff = null;
	        try {
	            // �½��ļ����������������л���
	            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
	            // �½��ļ���������������л���
	            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
	            // ��������
	            byte[] b = new byte[1024 * 5];
	            int len;
	            while ((len = inBuff.read(b)) != -1) {
	                outBuff.write(b, 0, len);
	            }
	            // ˢ�´˻���������
	            outBuff.flush();
	        } catch (IOException e) {
	        	System.out.println("�����ļ�����");
				e.printStackTrace();
			} finally {
	            // �ر���
	            if (inBuff != null)
					try {
						inBuff.close();
					} catch (IOException e) {
						System.out.println("�ر�������");
						e.printStackTrace();
					}
	            if (outBuff != null)
					try {
						outBuff.close();
					} catch (IOException e) {
						System.out.println("�ر�������");
						e.printStackTrace();
					}
	        }
			
			
		}




}
