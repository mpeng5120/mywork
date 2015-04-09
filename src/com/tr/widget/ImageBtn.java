package com.tr.widget;

import com.tr.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageBtn extends LinearLayout {
 
    private ImageView imageView;
    private TextView  textView;
    private CharSequence TextStr;
    private Drawable ImageDw;
    public ImageBtn(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public ImageBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      inflater.inflate(R.layout.imagebtn_item, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs
                ,R.styleable.imagebtn); 
        TextStr = typedArray.getText(R.styleable.imagebtn_textviewid);
        ImageDw = typedArray.getDrawable(R.styleable.imagebtn_imageviewid);
        if(TextStr==null)
        {
        	TextStr = null;
        }

        imageView=(ImageView) findViewById(R.id.imageview);
        textView=(TextView)findViewById(R.id.textview);
       
        textView.setText(TextStr);
        imageView.setImageDrawable(ImageDw);
        typedArray.recycle();
    }
     
    /**
     * 设置图片资源
     */ 
    public void setImageResource(int resId) { 
        imageView.setImageResource(resId); 
    } 
   
    /**
     * 设置显示的文字
     */ 
    public void setTextViewText(String text) { 
        textView.setText(text); 
    } 
 
}