package com.tr.maintainguide;

import java.io.File;
import java.io.IOException;

import wifiRunnablesAndChatlistener.KeyCodeSend;
import wifiRunnablesAndChatlistener.posccalmQueryRunnable;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Instrumentation;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dbutils.Constans;
import com.dbutils.OpenFiles;
import com.explain.HexDecoding;
import com.tr.ExitTR;
import com.tr.R;
import com.tr.bottomview.BaseBottomMenuActivity;
import com.tr.main.TR_Main_Activity;
import com.tr.main.VerticalSeekBar;
import com.tr.main.VerticalSeekBarListener;
import com.tr.newpragram.NewPragramActivity;
import com.tr.parameter_setting.TR_Parameter_Setting_Activity;
import com.tr.programming.TR_Programming_Activity;
import com.wifiexchange.WifiSetting_Info;

public class TR_MaintainGuide_Activity extends BaseBottomMenuActivity {

	public static boolean alreadyLogin=false;
	
	public static Button wifi_led;
	private AlertDialog KeyMsgDialog = null;
	private KeyEventReceiver KER;
	public static posccalmQueryRunnable PosccalmRunnable;
	private Thread PoccQueryThread;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("destroy");
		  unregisterReceiver(KER);
		  if(PosccalmRunnable!=null)
		  PosccalmRunnable.destroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		if(WifiSetting_Info.mClient!=null){
//			wifi_led.setVisibility(View.VISIBLE);
//        }
		System.out.println("TR_Setting_Activity   onResume");
		KER = new KeyEventReceiver();		
        IntentFilter filter = new IntentFilter();
        filter.addAction("KeyMsg");
        registerReceiver(KER, filter);
        
        if(PosccalmRunnable!=null)
  		  PosccalmRunnable.destroy();
        PosccalmRunnable = new posccalmQueryRunnable(TR_MaintainGuide_Activity.this);
		PoccQueryThread = new Thread(PosccalmRunnable);
		PoccQueryThread.start();
	}
	public int getContentViewLayoutResId() { return R.layout.tr_maintain; }
	protected void onCreatOverride(Bundle savedInstanceState)  {
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//		ExitTR.getInstance().addActivity(this);
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tr_maintain);
	
		
		ActionBar bar = getActionBar();
		bar.setDisplayShowTitleEnabled(false);// 不显示app名
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


		bar.addTab(bar.newTab().setText("维护监视类").setTabListener(new MyTabListener(new Fragments_maintain())));
		bar.addTab(bar.newTab().setText("指南").setTabListener(new MyTabListener(new Fragments_guide())));

		wifi_led=(Button)findViewById(R.id.wifi_led);
		if(wifi_led==null){return;}
		
		ImageButton btn_stop = (ImageButton) findViewById(R.id.stop);
		if(btn_stop==null){return;}
		ButtonListener b = new ButtonListener();           
		btn_stop.setOnClickListener(b);  
		btn_stop.setOnTouchListener(b);
		
		ImageButton btn_f1 = (ImageButton) findViewById(R.id.f1);
		if(btn_f1==null){return;}
		btn_f1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openThisFileFromSD("maintainguideactivity.pdf");
			}
		});
		
		
		
		//seekbar初始化
		VerticalSeekBar seekbar = (VerticalSeekBar) findViewById(R.id.seekbar);
		if(seekbar==null){return;}
        seekbar.setMax(500);//最大档位
        seekbar.initProgress();//默认档位
        seekbar.setOnSeekBarChangeListener(new VerticalSeekBarListener(TR_MaintainGuide_Activity.this));
        
        getActionBar().setDisplayShowTitleEnabled(true);
	}
	class ButtonListener implements OnClickListener, OnTouchListener{  
		   public boolean onTouch(View v, MotionEvent event) {  
		            if(v.getId() == R.id.stop){    
		                if(event.getAction() == MotionEvent.ACTION_DOWN){ 
		                	KeyCodeSend.send(999, TR_MaintainGuide_Activity.this);
		                }  
		            }  
		            return false;  
		        }

		@Override
		public void onClick(View v) {
		}  
		          
	}
	/**
	 * 
	* @Title: openThisFile 
	* @Description: 通过调用OpenFiles类返回的Intent，打开相应的文件;文件暂时都默认放在 rawPATH下
	* @param string    
	* @return void    
	* @throws
	 */
	public void openThisFileFromSD(String filename) {

		File currentPath = new File(Constans.rawPATH + filename);
		
		if (currentPath != null && currentPath.isFile()){// 存在该文件
			String fileName = currentPath.toString();
			Intent intent;
			try{
			if (checkEndsWithInStringArray(fileName, getResources()
					.getStringArray(R.array.fileEndingPdf))) {
				intent = OpenFiles.getPdfFileIntent(currentPath);
				startActivity(intent);
			} else {
				Toast.makeText(TR_MaintainGuide_Activity.this, "无法打开该文件，请安装相应的软件！",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
				// TODO: handle exception
			Toast.makeText(TR_MaintainGuide_Activity.this, "无法打开该文件，请安装相应的软件！",
					Toast.LENGTH_SHORT).show();
			}
		} else {// 不存在该文件
			Toast.makeText(TR_MaintainGuide_Activity.this, "对不起，不存在该文件！", Toast.LENGTH_SHORT)
					.show();
		}
	}
	/**
	 * 
	* @Title: checkEndsWithInStringArray 
	* @Description: 定义用于检查要打开的文件的后缀是否在遍历后缀数组中
	* @param checkItsEnd
	* @param fileEndings
	* @return    
	* @return boolean    
	* @throws
	 */
	private boolean checkEndsWithInStringArray(String checkItsEnd,
			String[] fileEndings) {
		for (String aEnd : fileEndings) {
			if (checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
	class MyTabListener implements TabListener{
		private Fragment fragment;

		public MyTabListener(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO 自动生成的方法存根
			ft.add(R.id.fragment_container_setting,fragment, null);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO 自动生成的方法存根
			ft.remove(fragment);
		}
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.maintain_guide_activity, menu);// 菜单
//		return true;
//
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.menu_activity_main:
//			startActivity(new Intent().setClass(TR_MaintainGuide_Activity.this, TR_Main_Activity.class));
//			break;
//		case R.id.menu_activity_programming:
//			startActivity(new Intent().setClass(TR_MaintainGuide_Activity.this, TR_Programming_Activity.class));
//			break;
//		case R.id.menu_activity_setting:
//			startActivity(new Intent().setClass(TR_MaintainGuide_Activity.this,NewPragramActivity.class));
//			break;
//		case R.id.menu_activity_parameter_setting:
//			startActivity(new Intent().setClass(TR_MaintainGuide_Activity.this, TR_Parameter_Setting_Activity.class));
//			break;
//		default:
//			// 对没有处理的事件，交给父类来处理
//			return super.onOptionsItemSelected(item);
//		}
//		return true;
//	}
	
	/**
	 * 返回键直接回到主界面
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0) {
			//DelayTimerQueryRunnble.destroy();
			startActivity(new Intent().setClass(this, TR_Main_Activity.class));
		}
		return super.onKeyDown(keyCode, event);
	}
	
	 private class KeyEventReceiver extends BroadcastReceiver
	    {
	        @Override
	        public void onReceive(Context context, Intent intent)
	        {
	            String action = intent.getAction();
	            Log.d("mpeng","the KeyEventReceiver");
	            if(action.equals("KeyMsg"))
	            {
	            	Log.d("mpeng","KeyEventReceiver 1111111");
	            	 byte[] keycode = new byte[2];
	            	 keycode = intent.getExtras().getByteArray("keycode");
	            	 KeyMsgHandle(keycode,context);            
	            }
	            
	        }
	    }
	
	private void KeyMsgHandle(byte[]  kc ,Context context)
	{
			byte [] temp= new byte[2];
			System.arraycopy(kc, 0, temp, 0, 2);
		 int Key_Value =HexDecoding.Array2Toint(temp);//255
		 int Key_Function = kc[2] & 0xFF;//255
//		 if(KeyMsgDialog != null)
//      	KeyMsgDialog.dismiss();
//       KeyMsgDialog = new AlertDialog.Builder(context).setTitle("提示")
//      .setMessage("keyvalue:"+ Key_Value+"keyfunction :"+Key_Function)
//      .setPositiveButton("OK", null).show();
//		
		 switch(Key_Value)
		 {
		 	 case 14:	
		 		KeyCodeSend.send(999, TR_MaintainGuide_Activity.this);
			 break;
		 	 case 11:  //HOME	
		 		//有警报
			 	if(WifiSetting_Info.alarmFlag==1){
			 		KeyCodeSend.send(996, TR_MaintainGuide_Activity.this);
			 	}else{
		 		new Thread() {
					public void run() {
				 		Instrumentation inst = new Instrumentation();//方法2
		                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
					}
				}.start();
			 	}
			 	break;
		 	 case 12:			 		
		 	 case 34:		 		
		 	 case 13:		 	
		 	 case 24:		 	
		 	 case 33:		 	
		 	 case 23:		 	
		 	 //case 153:
		 		 if(Key_Function==0)
		 		 {
		 			 Log.e("mpeng","SHOW key msg dialog");
			        if(KeyMsgDialog != null)
		            	KeyMsgDialog.dismiss();
		             KeyMsgDialog = new AlertDialog.Builder(context).setTitle("提示")
		            .setMessage("是否要进行自由操作！"+ Key_Value)
		            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent it = new Intent();
							it.setClass(TR_MaintainGuide_Activity.this,NewPragramActivity.class);
							it.setFlags(3);
							startActivity(it);
						}
					})
		            .setPositiveButton("取消", null)
		            .show();
		             KeyMsgDialog.setCanceledOnTouchOutside(false);
		 		 }
		 		 break;
		 		 
		 		 
		 	 case 32:
		 		 KeyCodeSend.send(26, TR_MaintainGuide_Activity.this);		 		 
		 		 break;
		 	 case 22:
		 		KeyCodeSend.send(25, TR_MaintainGuide_Activity.this);
		 		 break;
		 	 case 31:
		 		KeyCodeSend.send(24, TR_MaintainGuide_Activity.this);
		 		 break;
		 	 case 21:
		 		KeyCodeSend.send(23, TR_MaintainGuide_Activity.this);
		 		 break;	 
		 }
		
	}
	

}
