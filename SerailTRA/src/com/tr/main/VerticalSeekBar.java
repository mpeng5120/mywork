/**
 * 
 */
package com.tr.main;

import com.dbutils.Constans;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsSeekBar;
import android.widget.SeekBar;

/**
 * @author ������
 *
 */
public class VerticalSeekBar extends SeekBar {
		
	private VerticalSeekBarListener tempBarListener;
	
	public VerticalSeekBar(Context context) {
		super(context);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldw,oldh );//�����߿�ֵ
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		int df= getMeasuredWidth()-getMeasuredHeight();
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth()*Math.abs(df));//�����߿�ֵ
	}

	protected void onDraw(Canvas c) {
		c.rotate(-90);//��ת90�ȣ���bar������
		c.translate(-getHeight(), 0);//��������ת�õ���bar�Ƶ���ȷλ��
		super.onDraw(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));

			onSizeChanged(getWidth(), getHeight(), 0, 0);
			
			break;
		case MotionEvent.ACTION_UP:
			
			tempBarListener.onStopTrackingTouch(this);
			
			break;
		case MotionEvent.ACTION_CANCEL:

		}
		return true;
	}
	
	

	public synchronized void setProgressAndThumb(int progress){
		setProgress(getMax() - (getMax()- progress));
		onSizeChanged(getWidth(), getHeight() , 0, 0);
	}

	public synchronized void setMaximum(int maximum){
		setMax(maximum);
	}

	public synchronized int getMaximum(){
		return getMax();
	}
	
	public void initProgress(){
		setProgress(Constans.currentSeekbarValue);
	}
	


	@Override
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener){
		
		super.setOnSeekBarChangeListener(listener);
		
		tempBarListener=(VerticalSeekBarListener)listener;
		
	}
}





