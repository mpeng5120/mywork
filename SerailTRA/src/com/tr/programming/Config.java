/**
 * 
 */
package com.tr.programming;

import java.util.ArrayList;
import java.util.HashMap;

import wifiRunnablesAndChatlistener.posccalmQueryRunnable;

import com.tr.R;

/**
 * @author Administrator
 *
 */
public class Config {
	
	//��LIST�ڽ��н�����г�ʼ����
	//λ��
	
	public static boolean AutoState = false;
	public static boolean AlarmState = false;
	public static boolean MoveState = false;
	public static boolean firstCreate =true;
	public static boolean StopState = false;
	public static byte[] pspfpaxle=new byte[200];
	public static int SelectArmId = -1;
	public static int SelectSpeedId = -1;	
	public static int ArmNum = 3;	
	public static boolean isMutiThread = false;
	public static int userselect = 0;
	public static boolean TimeRunning = false;

	public static posccalmQueryRunnable posccalmqueryrunnable = null;
}