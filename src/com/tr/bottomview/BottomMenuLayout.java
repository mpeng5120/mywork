package com.tr.bottomview;

import java.util.ArrayList;
import java.util.List;

import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.programming.Config;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Author  : �?��
 * Date    : 2011-9-22
 *
 * Note    : 
 */
public class BottomMenuLayout extends LinearLayout {

	//实例化layout使用的类    
	private LayoutInflater mInflater;     

	//保存菜单按钮的集合，每一个集合元素代表一个按钮，包含了按钮所�?��的信息：图片，文字，按键处理事件�?
	private List<BottomButton> bottomButtons;    
	
	//封装菜单按钮的布�??
	private View bottomMenuLayout;   
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private Context myContext;
	private ProgressDialog p_dialog;
	private Handler updateMenuItemHandler;
	private TimeUpdateRunnable timeThread;
	private ArrayAdapter<String> userAdapter;
	private Spinner spinner;
	private SharedPreferences preferences ; 
	private boolean TimeRunning =false;
	/**
	 * 该�?�?��根据按钮背景图片来调整�?
	 */
	public static final int bottom_layoutHeight = 100;
	public static final String MainActivity = "";
	
	public BottomMenuLayout(Context context) 
	{
		super(context);
		myContext = context;
		Config.TimeRunning= false;
	}
	
	public BottomMenuLayout(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		myContext = context;	
		Config.TimeRunning= false;
	}
	
	public void processInitButton()
	{
	
		initLayout(this.getContext());    
		

//		bindingButton();                  
		initView();
		resizeLayout();                   
	}
	public void destroyTimeThread()
	{
		Log.e("mpeng","destroy time ");
		TimeRunning= false;
              
	}
	private void initLayout(Context context)
	{
		this.mInflater = LayoutInflater.from(context);
		bottomMenuLayout = mInflater.inflate(R.layout.bottom_menu_layout, null);
		addView(bottomMenuLayout);
	}
//	365Q-R5NW-GFKV-FZQK
	private void resizeLayout()
	{
		View customView = getChildAt(0);
		android.view.ViewGroup.LayoutParams params = customView.getLayoutParams();
		int screenHeight = GlobalUtils.getInstance().getScreenHeight();
		
		int lessHeight = screenHeight - bottom_layoutHeight;
		params.height = lessHeight;
		customView.setLayoutParams(params);
	}
	private  Handler mHandler = new Handler() {
        @Override
        public void handleMessage (Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    long sysTime = System.currentTimeMillis();
                  CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    tv1.setText(sysTimeStr);
                    break;
                
                default:
                    break;
            }
        }
    };
    /**
	 * 
	 */
    public class TimeUpdateRunnable implements Runnable {
       
        @Override
        public void run() {
        	 while(TimeRunning){
                 try {
                     Thread.sleep(1000);
                     Message msg = new Message();
                     msg.what = 111;
                     mHandler.sendMessage(msg);
                }
                catch (InterruptedException e) {
                   e.printStackTrace();
                }
   
        }  
    }	
    }
	private void initView()
	{
		tv1 = (TextView) findViewById(R.id.textViewa);
		tv2 = (TextView) findViewById(R.id.textViewb);
		tv3 = (TextView) findViewById(R.id.textViewc);
		tv4 = (TextView) findViewById(R.id.textViewd);
		tv5 = (TextView) findViewById(R.id.textViewe);
		if(!Config.TimeRunning)
		{
			
			timeThread = new TimeUpdateRunnable();
			Thread a1 =new Thread(timeThread);
			TimeRunning = true;
			a1.start();
			
		}
		tv2.setText("权限:");
		tv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				new AlertDialog.Builder(myContext).setTitle("123").setPositiveButton("好", null).show();
				showDialog();
			}
		});
		if(Config.userselect == 0 )
		{
			tv3.setText("一般用户");
		}else if(Config.userselect == 1)
		{
			tv3.setText("高级用户");
		}else if(Config.userselect == 2)
		{
			tv3.setText("超级用户");
		}
		tv4.setText("原点状态:");
	}
	private void showDialog()
	{
		preferences = myContext.getSharedPreferences("user",myContext.MODE_WORLD_READABLE);
		preferences.edit().putString("curname","" ).commit();  
		preferences.edit().putString("user1password", "").commit();
		preferences.edit().putString("user1password", "123").commit();
		preferences.edit().putString("user2password", "456").commit();     
       // TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(myContext);
		List<String> userName = new ArrayList<String>();
		userName.add("一般用户");
		userName.add("高级用户");
		userName.add("超级用户");
		
		final View DialogView = factory.inflate(R.layout.dialog, null);
		spinner = (Spinner)DialogView.findViewById(R.id.AccountSpinner);
		userAdapter= new ArrayAdapter<String>(factory.getContext(),R.layout.simple_spinner_item,userName);	
		userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(userAdapter);
		spinner.setSelection(Config.userselect);
		userAdapter.notifyDataSetChanged();
		final EditText passWord = (EditText) DialogView.findViewById(R.id.PasswordEidtText);
		AlertDialog dlg = new AlertDialog.Builder(
				myContext)
				.setTitle("登陆框")
				.setView(DialogView)
				.setPositiveButton("确定",
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						// TODO Auto-generated method
						// stub
						String key = "";
						if(spinner.getSelectedItemPosition() == 1)
						{
							 key  = "user1password";
						}else if(spinner.getSelectedItemPosition() == 2)
						{
							 key  = "user2password";
						}else
						{
							key = "user0password";
						}
						if(passWord.getText().toString().equals(preferences.getString(key, "")))
						{
							tv3.setText(spinner.getSelectedItem().toString());
							Config.userselect=spinner.getSelectedItemPosition();							
							updateMenuItemHandler.sendEmptyMessage(222);
						}
						else
						{
							AlertDialog dialog_password = new AlertDialog.Builder(myContext)
							.setTitle("提示")
							.setMessage("密码不正确")
							.setNegativeButton(R.string.T_titlecancel,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog,int which) {
											dialog.dismiss();
										}
									}).show();
						}
						}
				})
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(
									DialogInterface dialog,
									int which)
							{
								// TODO Auto-generated method

								dialog.dismiss();
							}
						}).create();
			dlg.show();


	}
	public void setHandler(Handler hd)
	{
		updateMenuItemHandler = hd;
	}
	
}
