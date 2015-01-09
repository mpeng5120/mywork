/**
 * MStar Software
 * Copyright (c) 2011 - 2012 MStar Semiconductor, Inc. All rights reserved.
 *
 * All software, firmware and related documentation herein (鈥淢Star Software鈥� are
 * intellectual property of MStar Semiconductor, Inc. (鈥淢Star鈥� and protected by
 * law, including, but not limited to, copyright law and international treaties.
 * Any use, modification, reproduction, retransmission, or republication of all
 * or part of MStar Software is expressly prohibited, unless prior written
 * permission has been granted by MStar.
 *
 * By accessing, browsing and/or using MStar Software, you acknowledge that you
 * have read, understood, and agree, to be bound by below terms (鈥淭erms鈥� and to
 * comply with all applicable laws and regulations:
 *
 * 1. MStar shall retain any and all right, ownership and interest to MStar
 * Software and any modification/derivatives thereof.  No right, ownership,
 * or interest to MStar Software and any modification/derivatives thereof is
 * transferred to you under Terms.
 *
 * 2. You understand that MStar Software might include, incorporate or be supplied
 * together with third party鈥檚 software and the use of MStar Software may require
 * additional licenses from third parties.  Therefore, you hereby agree it is your
 * sole responsibility to separately obtain any and all third party right and
 * license necessary for your use of such third party鈥檚 software.
 *
 * 3. MStar Software and any modification/derivatives thereof shall be deemed as
 * MStar鈥檚 confidential information and you agree to keep MStar鈥檚 confidential
 * information in strictest confidence and not disclose to any third party.
 *
 * 4. MStar Software is provided on an 鈥淎S IS鈥�basis without warranties of any kind.
 * Any warranties are hereby expressly disclaimed by MStar, including without
 * limitation, any warranties of merchantability, non-infringement of intellectual
 * property rights, fitness for a particular purpose, error free and in conformity
 * with any international standard.  You agree to waive any claim against MStar for
 * any loss, damage, cost or expense that you may incur related to your use of MStar
 * Software.  In no event shall MStar be liable for any direct, indirect, incidental
 * or consequential damages, including without limitation, lost of profit or revenues,
 * lost or damage of data, and unauthorized system use.  You agree that this Section 4
 * shall still apply without being affected even if MStar Software has been modified
 * by MStar in accordance with your request or instruction for your use, except
 * otherwise agreed by both parties in writing.
 *
 * 5. If requested, MStar may from time to time provide technical supports or
 * services in relation with MStar Software to you for your use of MStar Software
 * in conjunction with your or your customer鈥檚 product (鈥淪ervices鈥�.  You understand
 * and agree that, except otherwise agreed by both parties in writing, Services are
 * provided on an 鈥淎S IS鈥�basis and the warranty disclaimer set forth in Section 4
 * above shall apply.
 *
 * 6. Nothing contained herein shall be construed as by implication, estoppels or
 * otherwise: (a) conferring any license or right to use MStar name, trademark,
 * service mark, symbol or any other identification; (b) obligating MStar or any
 * of its affiliates to furnish any person, including without limitation, you and
 * your customers, any assistance of any kind whatsoever, or any information; or
 * (c) conferring any license or right under any intellectual property right.
 *
 * 7. These terms shall be governed by and construed in accordance with the laws
 * of Taiwan, R.O.C., excluding its conflict of law rules.  Any and all dispute
 * arising out hereof or related hereto shall be finally settled by arbitration
 * referred to the Chinese Arbitration Association, Taipei in accordance with
 * the ROC Arbitration Law and the Arbitration Rules of the Association by three (3)
 * arbitrators appointed in accordance with the said Rules.  The place of
 * arbitration shall be in Taipei, Taiwan and the language shall be English.
 * The arbitration award shall be final and binding to both parties.
 */
package com.wifiexchange;

import android.os.Handler;
import android.util.Log;

public class LittleDownTimer implements Runnable
{
	final static int TIME_OUT_MSG = 101;
	private static int totalMenuTime = 5;
	private static int countMenu = totalMenuTime;
	private static Handler handler;
	private static boolean enableMenu = false;
	private static boolean isStopMenu = false;
	private static boolean isStop = true;
	// /for 3 second selected return
	final static int SELECT_RETURN_MSG = 102;
	private static LittleDownTimer instance;

	public static void setMenuStatus( int second)
	{
		isStopMenu = false;
		totalMenuTime = second;
	}

	public static void resetMenu()
	{
		countMenu = totalMenuTime;
		isStopMenu = false;
	}
	public static void restartMenu()
	{
		isStopMenu = false;
	}
	public static void resumeMenu()
	{
		if (isStop)
		{
			isStop = false;
			Log.d("LittleDownTimer", " new Thread");
			new Thread(instance).start();
		}
		enableMenu = true;
		countMenu = totalMenuTime;
	}

	public static void pauseMenu()
	{
		enableMenu = false;
	}


	public static void stopMenu(){
		isStopMenu = true;
	}

	public static void destory()
	{
		isStop = true;
	}

	private LittleDownTimer()
	{
		isStop = true;
		enableMenu = true;
	}

	public void start()
	{
		if (isStop)
		{
			isStop = false;
			Log.d("LittleDownTimer", " new Thread");
			new Thread(this).start();
		}
		isStopMenu = false;
	}

	public static LittleDownTimer getInstance()
	{
		if (instance == null)
		{
			instance = new LittleDownTimer();
		}
		return instance;
	}

	public static void setHandler(Handler hndler)
	{

		handler = hndler;
	}

	private void decreaseMenu()
	{
		countMenu--;
	}



	@Override
	public void run()
	{
		while (!isStop)
		{
			try
			{
				Thread.currentThread();
				Thread.sleep(400);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if(!isStopMenu){
				if (enableMenu)
				{
					if (countMenu == 0)
					{
						if(handler != null)
							handler.sendEmptyMessage(TIME_OUT_MSG);
						else
							Log.e("mpeng","the handler is null");
					}
					decreaseMenu();
				}
			}			
		}
	}
}