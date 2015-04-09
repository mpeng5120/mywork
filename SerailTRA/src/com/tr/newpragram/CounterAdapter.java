/**
 * 
 */
package com.tr.newpragram;
import java.util.ArrayList;
import java.util.HashMap;

import com.tr.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.method.NumberKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class CounterAdapter extends BaseAdapter {

		private class buttonViewHolder {
			TextView numText;
			TextView counterText;


		}

		private ArrayList<HashMap<String, Object>> mAppList;
		private LayoutInflater mInflater;
		private Context mContext;
		private String[] keyString;
		private buttonViewHolder holder;	
		private int mselectItem = -1;
		// MyAdapter�Ĺ��캯��
		public CounterAdapter(Context c, ArrayList<HashMap<String, Object>> appList,
				String[] from) {
			mAppList = appList;
			mContext = c;
		
			mInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			keyString = new String[from.length];
			System.arraycopy(from, 0, keyString, 0, from.length);

		}

		public String[] getkeyString() {
			return this.keyString;
		}
		public void setSelectItem(int selectItem) {
			mselectItem = selectItem;
		}
		@Override
		public int getCount() {
			return mAppList.size();
		}

		@Override
		public Object getItem(int position) {
			return mAppList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (buttonViewHolder) convertView.getTag();
			} else {
				convertView = mInflater.inflate(R.layout.counter_list_item, null);
				
				holder = new buttonViewHolder();
				holder.numText = (TextView) convertView
						.findViewById(R.id.numText);
				holder.counterText = (TextView) convertView
						.findViewById(R.id.vouleEditText);

				convertView.setTag(holder);
			}

			HashMap<String, Object> map_item = mAppList.get(position);
			if (map_item != null) {
				String numText =  map_item.get(keyString[0]).toString();
				String counterText = map_item.get(keyString[1]).toString();
				holder.numText.setText(numText);
				holder.counterText.setText(counterText);
				holder.counterText.setClickable(true);
				holder.counterText.setOnClickListener(new setValueListener(position));// ����趨ֵ�м�����
			}
			if (position == mselectItem) {
	    	    convertView.setBackgroundColor(Color.RED);
            }else{//�����ѡ�У�����Ϊ��ɫ
            	convertView.setBackgroundColor(Color.BLACK);
			}
			return convertView;
		}

		// �趨ֵ�м�����
		class setValueListener implements android.view.View.OnClickListener {

			private int position;

			// ���캯��
			setValueListener(int pos) {
				position = pos;
			}

			@Override
			public void onClick(final View v) {

				final EditText etEditText = new EditText(mContext);
				String setValueString = mAppList.get(position).get(
						keyString[1]).toString();
				etEditText.setHint("����������");
				etEditText.setText(setValueString);
				etEditText.setSelection(setValueString.length());// ���ù��λ��
				// ����ֻ������0~9�����ֺ͵��
				etEditText.setKeyListener(new NumberKeyListener() {
					@Override
					protected char[] getAcceptedChars() {
						return new char[] { '1', '2', '3', '4', '5', '6', '7',
								'8', '9', '0', '.','+','-' };
					}

					@Override
					public int getInputType() {
						return android.text.InputType.TYPE_CLASS_NUMBER;// ���ּ���
					}
				});

				new AlertDialog.Builder(mContext)
				.setTitle("������趨ֵ")
				.setView(etEditText)
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0,
							int arg1) {
						String editString = etEditText
								.getText().toString();
						double editDouble=0;
						if (!editString.startsWith(".")) {
							if (editString.contains(".")) {
								String[] editStrings = editString.split("\\.");
								if (2 == editStrings.length) {
									editDouble = Double.parseDouble(editString);
									/*if (Math.abs(editDouble) >= 100000) {
										Toast.makeText(getActivity(),"��ֵ������Χ������������",Toast.LENGTH_SHORT).show();
										editString = "";
									} else {*/
										editString = String.format("%1$+5.1f",editDouble);
									//}
								} else {
									Toast.makeText(mContext,"���ָ�ʽ��������������",Toast.LENGTH_SHORT).show();
									editString = "";
								}
							} /*else {
								editDouble = Double.parseDouble(editString);
								if (Math.abs(editDouble) >= 100000) {
									Toast.makeText(getActivity(),"��ֵ������Χ������������",Toast.LENGTH_SHORT).show();
									editString = "";
								} else {
									//editString = String.format("%1$+5.1f",editDouble);
								}
							}
*/
						} else {
							Toast.makeText(mContext,"���ָ�ʽ��������������",Toast.LENGTH_SHORT).show();
							editString = "";
						}
						HashMap<String, Object> map = new HashMap<String, Object>();
						map = mAppList.get(position);
						map.put("value",editString);						
						mAppList.set(position, map);
						notifyDataSetChanged();
					}

				}).setNegativeButton("ȡ��", null).show();

			}
		}
	
}
