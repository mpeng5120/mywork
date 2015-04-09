/**
 * 
 */
package com.tr.widget;

import com.tr.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class PositionItemWidget extends  LinearLayout{

	/**
	 * @param context
	 * @param attrs
	 */
	private CheckBox mCheckBox= null;
	private TextView mTextView = null;
	private EditText mEditText = null;
	private Button mButton = null;
	public PositionItemWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		LayoutInflater layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.position_item, this); 
		TypedArray typedArray = context.obtainStyledAttributes(attrs
        ,R.styleable.positionitem); 
		initWidget(typedArray);
	}
	
	private void initWidget(TypedArray typedArray)
    {
		mCheckBox = (CheckBox) findViewById(R.id.checkBox);
		mTextView = (TextView) findViewById(R.id.Value);
		mEditText = (EditText) findViewById(R.id.EditTextValue);
		mButton   = (Button) findViewById(R.id.button1);
//        mTextView = (TextView)findViewById(R.id.tv_item);
        String CheckboxString = typedArray.getString(R.styleable.positionitem_checkbox_text);
        String EditTextString = typedArray.getString(R.styleable.positionitem_edit_text);
        String ButtonString = typedArray.getString(R.styleable.positionitem_button_text);
        String TextString = typedArray.getString(R.styleable.positionitem_textview);
        mCheckBox.setText(CheckboxString);
        mTextView.setText(TextString);
        mEditText.setText(EditTextString);
        mButton.setText(ButtonString);
        mEditText.setEnabled(false);
        mButton.setEnabled(false);
        
        mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					mEditText.setEnabled(true);
					mButton.setEnabled(true);
				}else
				{
					mEditText.setEnabled(false);
					mButton.setEnabled(false);
				}
			}
		});
        mCheckBox.setChecked(false);

        {
        	mButton.setVisibility(View.GONE);
        }
        
//        int textColor = typedArray.getColor(R.styleable.LevelMenuItem_text_color,
//                0xffffffff);
//        float textSize = typedArray.getDimension(R.styleable.LevelMenuItem_text_size,
//                20);
//        mTextView.setText(textString);
//        mTextView.setTextColor(textColor);
//        mTextView.setTextSize(textSize);
//         
//        mImageView = (ImageView)findViewById(R.id.image_item);
//        int imageHeight = (int) typedArray.getDimension(R.styleable.LevelMenuItem_image_height, 25);
//        int imageWidth = (int) typedArray.getDimension(R.styleable.LevelMenuItem_image_width, 25);
//        int imageSrc = typedArray.getResourceId(R.styleable.LevelMenuItem_image_src, 0);
//        int imageBg = typedArray.getResourceId(R.styleable.LevelMenuItem_image_bg, 0);
//        int imageAlpha = typedArray.getInt(R.styleable.LevelMenuItem_image_alpha, 255);
//        mImageView.setAlpha(imageAlpha);
//        mImageView.setImageResource(imageSrc);
//        mImageView.setBackgroundResource(imageBg);
//        LayoutParams layoutParams = new LayoutParams(imageWidth, imageHeight);
//        mImageView.setLayoutParams(layoutParams);
//         
        typedArray.recycle();
    }

    public void setChecked(boolean isChecked) {
    	mCheckBox.setChecked(isChecked);

    }
    public TextView getTextView()
    {
    	return mTextView;
    }
    public EditText getEditView()
    {
    	return mEditText;
    }
    public CheckBox getCheckBox()
    {
    	return mCheckBox;
    }
    public boolean isChecked()
    {
    	
    	return mCheckBox.isChecked();
    }
    
    public String  getEditTextString()
    {
    	
    	return mEditText.getText().toString();
    }
}
