package com.tr.bottomview;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tr.R;
import com.tr.main.TR_Main_Activity;
import com.tr.maintainguide.TR_MaintainGuide_Activity;
import com.tr.newpragram.NewPragramActivity;
import com.tr.parameter_setting.TR_Parameter_Setting_Activity;
import com.tr.programming.Config;
import com.tr.programming.TR_Programming_Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;

/**
 * Author  : 简洋
 * Date    : 2011-9-26
 *
 * Note    :  底部菜单，继承后实现onCreateBottomButton方法， 组装自己的按钮。
 *            按以下步骤来实现：
 *            1. 在子类中继承实现getButtonList方法，在方法中封装BottomButton对象返回，每一个BottomButton代表一个菜单项，具体属性见BottomButton定义。
 *            2. 在子类中继承实现getContentViewLayoutResId方法，返回layout xml的ID。
 *            3. 在子类中继承实现onCreatOverride方法，原先在onCreat方法中完成的事情挪到这里，super.onCreate(savedInstanceState);和setContentView就不需要调用了。
 * 			  
 */
public abstract class BaseBottomMenuActivity extends Activity 
{
	private LayoutInflater mInflater;   //实例化layout使用的类    
	protected BottomMenuLayout bottomMenuLayout;   //底部菜单UI部分
	protected View contentView;                    //页面中间UI部分
	private Handler mHandler = new Handler(){

		/* (non-Javadoc)
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			updateMenuItem();
			
		}

		
	};
	private MenuItem MainActivityItem;
	private MenuItem pragramItem;
	private MenuItem settingItem;
	private MenuItem parameterItem;
	private MenuItem MaintainItem;
	private void updateMenuItem()
	{
		Log.e("mpeng","updateMenuItem");
		if(Config.userselect == 0)
		{				
			  pragramItem.setVisible(false);
			  settingItem.setVisible(true);
			  parameterItem.setVisible(false);
			  MaintainItem.setVisible(false);
		}else if(Config.userselect == 1)
		{
			 pragramItem.setVisible(true);
			  settingItem.setVisible(true);
			  parameterItem.setVisible(false);
			  MaintainItem.setVisible(true);
//		  MainActivityItem.setEnabled(false);
//		  MainActivityItem.setCheckable(false);
//		  settingItem.setEnabled(false);
//		  settingItem.setCheckable(false);
//		  parameterItem.setEnabled(false);
//		  parameterItem.setCheckable(false);
//		  MaintainItem.setVisible(false);
		}else if(Config.userselect == 2)
		{
			  pragramItem.setVisible(true);
			  settingItem.setVisible(true);
			  parameterItem.setVisible(true);
			  MaintainItem.setVisible(true);
		}
		 
		  
//		  settingItem= menu.getItem(2);
//		  parameterItem= menu.getItem(3);
//		  MaintainItem= menu.getItem(4);
	}
	final protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//实例化工具类
		if(GlobalUtils.getInstance() == null)
			new GlobalUtils(getWindowManager());
		
		//创建出完整的页面Layout，并设置为当前Activity的页面。
		bottomMenuLayout = new BottomMenuLayout(this);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		bottomMenuLayout.setOrientation(BottomMenuLayout.VERTICAL);
		bottomMenuLayout.setLayoutParams(layoutParams);
        setContentView(bottomMenuLayout);
        

        //将业务自定义的layout实例化出来，设置给完整页面Layout的内容部分。其中，获取业务自定义layoutID的时候回调了子类的方法。
        this.mInflater = LayoutInflater.from(this);
        contentView = mInflater.inflate(getContentViewLayoutResId(), null);
        bottomMenuLayout.addView(contentView);
        
        
        //回调子类,正常处理onCreate方法。
        onCreatOverride(savedInstanceState);

		bottomMenuLayout.processInitButton();
		bottomMenuLayout.setHandler(mHandler);
	}
	

	/**
	 * 菜单跳转
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity, menu);// 菜单
		  MainActivityItem = menu.getItem(0);
		  pragramItem= menu.getItem(1);
		  settingItem= menu.getItem(2);
		  parameterItem= menu.getItem(3);
		  MaintainItem= menu.getItem(4);
		 
		 if(getClass().getName().equals("com.tr.main.TR_Main_Activity")){
			 MainActivityItem.setEnabled(false);
			 MainActivityItem.setCheckable(false);
		 }else if(getClass().getName().equals("com.tr.programming.TR_Programming_Activity")){
			 pragramItem.setEnabled(false);
			 pragramItem.setCheckable(false);
		 }else if(getClass().getName().equals("com.tr.parameter_setting.TR_Parameter_Setting_Activity")){
			 parameterItem.setEnabled(false);
			 parameterItem.setCheckable(false);
		 }else if(getClass().getName().equals("com.tr.maintainguide.TR_MaintainGuide_Activity")){
			 MaintainItem.setEnabled(false);
			 MaintainItem.setCheckable(false);
		 }else if(getClass().getName().equals("com.tr.newpragram.NewPragramActivity")){
			 settingItem.setEnabled(false);
			 settingItem.setCheckable(false);
		 }
		 Log.e("mpeng","onCreateOptionsMenu");
		 updateMenuItem();
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_activity_main:
			startActivity(new Intent().setClass(this,
					TR_Main_Activity.class));
			break;
		case R.id.menu_activity_programming:
				startActivity(new Intent().setClass(this,
						TR_Programming_Activity.class));
			break;
		case R.id.menu_activity_setting:			
				startActivity(new Intent().setClass(this,
						NewPragramActivity.class));	
				break;
		case R.id.menu_activity_parameter_setting:			
				startActivity(new Intent().setClass(this,
						TR_Parameter_Setting_Activity.class));
			break;
		case R.id.menu_activity_maintainGuide:		
				startActivity(new Intent().setClass(this,
						TR_MaintainGuide_Activity.class));
			break;

		default:
			// 对没有处理的事件，交给父类来处理
			return super.onOptionsItemSelected(item);
		}
		// 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		return true;
	}

	
	/**
	 * 为避免子类错误调用，覆盖该方法，并定义为空方法。
	 */
	public void setContentView(int layoutResID) { }

	public  BottomMenuLayout getBaseLayout() { 
		return	bottomMenuLayout;
	}

	/**
	 * 子类实现后，在原来的onCreate方法中内容移到这里来操作。
	 * @param savedInstanceState
	 */
	protected abstract void onCreatOverride(Bundle savedInstanceState);
	
	/**
	 * 返回layout xml的ID
	 * 原本在Activity的onCreate方法中调用的setContentView(R.layout.xxxxLayoutId); 现在从该方法返回。
	 * @return
	 */
	public abstract int getContentViewLayoutResId();

}
