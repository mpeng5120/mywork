package com.dbutils;
import com.tr.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义数字密码键盘
 * @author suxh
 */
public class KeyPadUtil {
	private Context ctx;
	// 将要使用自定义键盘的EditText控件
	private TextView e;
	private String digitnum = "";

	private PopupWindow popup;
	private LinearLayout keypad_num;// 数字键盘
	private LinearLayout keypad_lower;// 字母键盘小写
	private LinearLayout keypad_uper;// 字母键盘大写
	private EditText keypad_word;
	private Button emptybtn;// 清空按钮
	private Button quedingbtn;// 确定按钮
	// 切换输入按钮
	private Button keypad_num_change1;
	private Button keypad_num_change2;
	private Button keypad_num_change3;
	// 回退按钮
	private Button keypad_num_backspace1;
	private Button keypad_num_backspace2;
	private Button keypad_num_backspace3;
	// 数字按钮
	private int[] kepad1_int = { R.id.keypad_num_0, R.id.keypad_num_1, R.id.keypad_num_2, R.id.keypad_num_3,R.id.keypad_num_p, R.id.keypad_num_4, R.id.keypad_num_5, R.id.keypad_num_6, R.id.keypad_num_d,R.id.keypad_num_7, R.id.keypad_num_8, R.id.keypad_num_9 ,R.id.keypad_num_point};
	private Button[] kepad1 = new Button[13];
	// 小写字母按钮
	private int[] kepad2_int = { R.id.keypad_lower_a, R.id.keypad_lower_b, R.id.keypad_lower_c, R.id.keypad_lower_d, R.id.keypad_lower_e, R.id.keypad_lower_f, R.id.keypad_lower_g, R.id.keypad_lower_h, R.id.keypad_lower_i, R.id.keypad_lower_j, R.id.keypad_lower_k, R.id.keypad_lower_l, R.id.keypad_lower_m, R.id.keypad_lower_n, R.id.keypad_lower_o, R.id.keypad_lower_p, R.id.keypad_lower_q, R.id.keypad_lower_r, R.id.keypad_lower_s, R.id.keypad_lower_t, R.id.keypad_lower_u, R.id.keypad_lower_v, R.id.keypad_lower_w, R.id.keypad_lower_x, R.id.keypad_lower_y, R.id.keypad_lower_z };
	private String[] kepad2_str = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	private Button[] kepad2 = new Button[26];
	// 大写字母按钮
	private int[] kepad3_int = { R.id.keypad_uper_a, R.id.keypad_uper_b, R.id.keypad_uper_c, R.id.keypad_uper_d, R.id.keypad_uper_e, R.id.keypad_uper_f, R.id.keypad_uper_g, R.id.keypad_uper_h, R.id.keypad_uper_i, R.id.keypad_uper_j, R.id.keypad_uper_k, R.id.keypad_uper_l, R.id.keypad_uper_m, R.id.keypad_uper_n, R.id.keypad_uper_o, R.id.keypad_uper_p, R.id.keypad_uper_q, R.id.keypad_uper_r, R.id.keypad_uper_s, R.id.keypad_uper_t, R.id.keypad_uper_u, R.id.keypad_uper_v, R.id.keypad_uper_w, R.id.keypad_uper_x, R.id.keypad_uper_y, R.id.keypad_uper_z };
	private String[] kepad3_str = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private Button[] kepad3 = new Button[26];

	// 键盘标识 0：数字键盘 1:小写字母 2：大写字母
	public int key_tag = 0;
	// 是否密文显示 true:明文 false:密文
	private boolean isPwd;
	private int length = 30;
	private boolean changed = true;

	public KeyPadUtil(Context ctx, EditText e, Boolean isPwd, int length) {
		super();
		this.ctx = ctx;
		this.isPwd = isPwd;
		this.e = e;
		this.length = length;
		initKeyPopupWindow();
		e.setOnClickListener(eListener);
	}

	public KeyPadUtil(Context ctx, EditText e, Boolean isPwd, int length, boolean changed) {
		super();
		this.ctx = ctx;
		this.isPwd = isPwd;
		this.e = e;
		this.length = length;
		this.changed = changed;
		initKeyPopupWindow();
		e.setOnClickListener(eListener);
	}

	
	public KeyPadUtil(Context ctx, TextView e, Boolean isPwd, int length, boolean changed) {
		super();
		this.ctx = ctx;
		this.isPwd = isPwd;
		this.e = e;
		this.length = length;
		this.changed = changed;
		initKeyPopupWindow();
//		e.setOnClickListener(eListener);
	}
	private OnClickListener eListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			keypad_word.setText(e.getText().toString());
			keypad_word.setSelection(null != e.getText().toString() ? e.getText().toString().length() : 0);
			popup.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			return;
		}
	};

	private void initKeyPopupWindow() {
		View menuView = LayoutInflater.from(ctx).inflate(R.layout.keypadview, null);
		keypad_word = (EditText) menuView.findViewById(R.id.keypadview_word);
		// 清空按钮
		emptybtn = (Button) menuView.findViewById(R.id.keypadview_left_btn);
		// 确定
		quedingbtn = (Button) menuView.findViewById(R.id.keypadview_ritht_btn);
		emptybtn.setOnClickListener(l);
		quedingbtn.setOnClickListener(l);

		keypad_num = (LinearLayout) menuView.findViewById(R.id.keypad_num);
		keypad_lower = (LinearLayout) menuView.findViewById(R.id.keypad_lower);
		keypad_uper = (LinearLayout) menuView.findViewById(R.id.keypad_uper);
		// 切换按钮初始化
		keypad_num_change1 = (Button) menuView.findViewById(R.id.keypad_num_change1);
		keypad_num_change2 = (Button) menuView.findViewById(R.id.keypad_num_change2);
		keypad_num_change3 = (Button) menuView.findViewById(R.id.keypad_num_change3);
		// 回退按钮初始化
		keypad_num_backspace1 = (Button) menuView.findViewById(R.id.keypad_num_backspace1);
		keypad_num_backspace2 = (Button) menuView.findViewById(R.id.keypad_num_backspace2);
		keypad_num_backspace3 = (Button) menuView.findViewById(R.id.keypad_num_backspace3);
		// 切换按钮添加事件
		if (changed) {
			keypad_num_change1.setOnClickListener(l);
			keypad_num_change2.setOnClickListener(l);
			keypad_num_change3.setOnClickListener(l);
		}
		// 回退按钮添加事件
		KeypadBackOnClickListener backlistener = new KeypadBackOnClickListener();
		keypad_num_backspace1.setOnClickListener(backlistener);
		keypad_num_backspace2.setOnClickListener(backlistener);
		keypad_num_backspace3.setOnClickListener(backlistener);

		KeypadOnClickListener keypad1listener = new KeypadOnClickListener();
		KeypadOnClickListener1 keypad2listener = new KeypadOnClickListener1();
		KeypadOnClickListener2 keypad3listener = new KeypadOnClickListener2();
		for (int i = 0; i < kepad1.length; i++) {
			kepad1[i] = (Button) menuView.findViewById(kepad1_int[i]);
			kepad1[i].setOnClickListener(keypad1listener);
		}
		for (int i = 0; i < kepad2.length; i++) {
			kepad2[i] = (Button) menuView.findViewById(kepad2_int[i]);
			kepad2[i].setOnClickListener(keypad2listener);
		}
		for (int i = 0; i < kepad3.length; i++) {
			kepad3[i] = (Button) menuView.findViewById(kepad3_int[i]);
			kepad3[i].setOnClickListener(keypad3listener);
		}

		this.popup = new PopupWindow(menuView, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//		this.popup.setAnimationStyle(R.style.menushow);
		this.popup.setBackgroundDrawable(new BitmapDrawable());
		this.popup.setFocusable(true);
		this.popup.update();
		keypad_word.setText(e.getText().toString());
		keypad_word.setSelection(null != e.getText().toString() ? e.getText().toString().length() : 0);
		popup.showAtLocation(menuView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);	
		keypad_num.setFocusableInTouchMode(true);
		keypad_lower.setFocusableInTouchMode(true);
		keypad_uper.setFocusableInTouchMode(true);
		if (isPwd) {
			keypad_word.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			e.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		} else {
			keypad_word.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			e.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
	}

	private OnClickListener l = new OnClickListener() {
		@Override
		public void onClick(View v) {		
			switch (v.getId()) {
			case R.id.keypadview_left_btn: // 清空
				e.setText("");
				keypad_word.setText("");
				break;
			case R.id.keypadview_ritht_btn:// 确定
				String keyworkString = keypad_word.getText().toString();
				e.setText(keyworkString);
				keypad_word.setText(keyworkString);
				popup.dismiss();
				break;
			case R.id.keypad_num_change1:// 切换按钮
				changeview();
				break;
			case R.id.keypad_num_change2:// 切换按钮
				changeview();
				break;
			case R.id.keypad_num_change3:// 切换按钮
				changeview();
				break;
			}
		}
	};

	/**
	 * 切换键盘视图
	 */
	private void changeview() {
		switch (key_tag) {
		case 0: // 数字->小写
			keypad_num.setVisibility(View.GONE);
			keypad_lower.setVisibility(View.VISIBLE);
			keypad_uper.setVisibility(View.GONE);
			key_tag = 1;
			break;
		case 1: // 小写->大写
			keypad_num.setVisibility(View.GONE);
			keypad_lower.setVisibility(View.GONE);
			keypad_uper.setVisibility(View.VISIBLE);
			key_tag = 2;
			break;
		case 2: // 大写->数字
			keypad_num.setVisibility(View.VISIBLE);
			keypad_lower.setVisibility(View.GONE);
			keypad_uper.setVisibility(View.GONE);
			key_tag = 0;
			break;
		}
	}

	// 数字键盘按钮监听
	private class KeypadOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			String keyworkString = keypad_word.getText().toString();
			int viewId = v.getId();
			switch (viewId) {
			case R.id.keypad_num_1:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "1";
				break;
			case R.id.keypad_num_2:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "2";
				break;
			case R.id.keypad_num_3:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "3";
				break;
			case R.id.keypad_num_4:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "4";
				break;
			case R.id.keypad_num_5:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "5";
				break;
			case R.id.keypad_num_6:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "6";
				break;
			case R.id.keypad_num_7:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "7";
				break;
			case R.id.keypad_num_8:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "8";
				break;
			case R.id.keypad_num_9:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "9";
				break;
			case R.id.keypad_num_0:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "0";
				break;
			case R.id.keypad_num_p:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "+";
				break;
			case R.id.keypad_num_d:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + "-";
				break;
			case R.id.keypad_num_point:
				if (keyworkString.length() == length) {
					return;
				}
				keyworkString = keyworkString + ".";
				break;
			}
			// 格式化 数据
			Log.e("mpeng","the key workstring is "+keyworkString);
			keypad_word.setText(keyworkString);

		}
	}

	// 小写字母键盘按钮监听
	private class KeypadOnClickListener1 implements OnClickListener {
		@Override
		public void onClick(View v) {
			digitnum = keypad_word.getText().toString();
			int viewId = v.getId();
			for (int i = 0; i < kepad2_int.length; i++) {
				if (viewId == kepad2_int[i]) {
					if (digitnum.length() == length) {
						return;
					} else {
						digitnum += kepad2_str[i];
					}
					break;
				}
			}
			// 格式化 数据
			keypad_word.setText(digitnum);
			e.setText(digitnum);
		}
	}

	// 大写字母键盘按钮监听
	private class KeypadOnClickListener2 implements OnClickListener {
		@Override
		public void onClick(View v) {
			digitnum = keypad_word.getText().toString();
			int viewId = v.getId();
			for (int i = 0; i < kepad3_int.length; i++) {
				if (viewId == kepad3_int[i]) {
					if (digitnum.length() == length) {
						return;
					} else {
						digitnum += kepad3_str[i];
					}
					break;
				}
			}
			// 格式化 数据
			keypad_word.setText(digitnum);
			e.setText(digitnum);
		}
	}

	// 后退按钮监听
	private class KeypadBackOnClickListener implements OnClickListener {
		public void onClick(View v) {
			digitnum = keypad_word.getText().toString();
			if (isStringEmpty(digitnum) || digitnum.length() <= 1) {
				digitnum = "";
			} else {
				digitnum = digitnum.substring(0, digitnum.length() - 1);
			}
			// 格式化 数据
			keypad_word.setText(digitnum);
			e.setText(digitnum);
		}
	}
	
	public boolean isStringEmpty(String value) {
		return !isStringNotEmpty(value);
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param value
	 * @return 字符串不为空返回true，否则返回false
	 */
	public boolean isStringNotEmpty(String value) {
		if (value != null && !"".equals(value)) {
			return true;
		} else {
			return false;
		}
	}
}
