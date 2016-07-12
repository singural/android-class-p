package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    RadioGroup radioGroup;

    String selectedSex="男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textView);//取出textView的id,並記得轉換成TextView
        editText=(EditText)findViewById(R.id.editText);//取出editText的id,並轉成EditText
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) { //打完鍵盤會 call onKey的Function
                //按Enter及按下按鈕的瞬間
                if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    submit(v);
                    return true;//防止Enter後又空一行
                }
                return false;
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.maleRadioButton)
                {
                    selectedSex="男";
                }else if(checkedId==R.id.femaleRadioButton)
                {
                    selectedSex="女";
                }
            }
        });
    }

    public void submit(View view)
    {
        String text=editText.getText().toString(); //取得editText輸入的文字，並轉成String
        text=text + "  性別：" + selectedSex;
        textView.setText(text);//將editText輸入的文字顯示在textView上

        editText.setText("");//清空editText

    }
}
