package com.ahaya.earthquakeviewer.Sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ahaya.earthquakeviewer.R;

public class CleanableText extends LinearLayout {
    EditText editText;
    Button button;

    public CleanableText(Context context) {
        this(context, null);
    }

    public CleanableText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CleanableText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //从layout 资源文件中inflate  view
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(infService);
        layoutInflater.inflate(R.layout.cleanable_edit_text, this, true);
        hookupButton();
    }

    private void hookupButton (){
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                editText.setText("");
            }
        });
    }
}

// 从代码构建布局
class ClearableText2 extends LinearLayout {

    EditText editText;
    Button button;

    public ClearableText2(Context context) {
        this(context,null);
    }

    public ClearableText2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClearableText2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);

        editText = new EditText(getContext());
        button = new Button(getContext());

        button.setText("Clean");

        int layoutH = LayoutParams.WRAP_CONTENT;
        int layoutW = LayoutParams.MATCH_PARENT;

        addView(editText, new LinearLayout.LayoutParams(layoutW,layoutH));
        addView(button, new LinearLayout.LayoutParams(layoutW,layoutH));


        hookupButton();

    }

    private void hookupButton (){
        button.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                editText.setText("");
            }
        });
    }
}
