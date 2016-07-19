package com.example.user.simpleui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE_DRINK_MENU_ACTIVITY=0;

    TextView textView;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;
    Spinner spinner;

    String selectedTea="black tea";

    String menuResults="";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    List<Order> orders=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.textView);//取出textView的id,並記得轉換成TextView
        editText=(EditText)findViewById(R.id.editText);//取出editText的id,並轉成EditText
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        listView=(ListView)findViewById(R.id.listView);
        spinner=(Spinner)findViewById(R.id.spinner);

        sharedPreferences=getSharedPreferences("setting",MODE_PRIVATE); //mode_private覆蓋 ,mode_append  累加上去
        editor=sharedPreferences.edit();


        editText.setText(sharedPreferences.getString("editText",""));
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) { //打完鍵盤會 call onKey的Function

                String text=editText.getText().toString();
                editor.putString("editText",text);
                editor.commit(); //寫入edittext的xml裡面

                //按Enter及按下按鈕的瞬間
                if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN)
                {
                    submit(v);
                    return true;//防止Enter後又空一行
                }
                return false;
            }
        });

        //home work ************************************************
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("spinner",position);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //home work *****************************************************

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)group.findViewById(checkedId);
                selectedTea=radioButton.getText().toString();
            }
        });

        String history=Utils.readFile(this,"history");
        String[] datas=history.split("\n");
        for (String data:datas)
        {
            Order order=Order.newInstanceWithData(data);
            if(order != null)
                orders.add(order);
        }

        setupListView();
        setupSpinner();
        spinner.setSelection(sharedPreferences.getInt("spinner",0)); //homework add

        ParseObject parseObject=new ParseObject("Test");
        parseObject.put("foo","bar"); //put(欄位,值)
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null)
                Toast.makeText(MainActivity.this,"上傳成功",Toast.LENGTH_LONG).show();

            }
        }
        );

        ParseQuery<ParseObject> query= new ParseQuery<ParseObject>("Test");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    for (ParseObject object:objects)
                    {
                        Toast.makeText(MainActivity.this,object.getString("foo"),Toast.LENGTH_LONG).show();
                    }
                }

            }

        });

        Log.d("Debug","MainActivity OnCreate");

    }

    public void setupListView()
    {
        /*
       //List列表，給一Array固定值
        String[] data=new String[]{"black tea","green tea","1","2","3","4","5"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        */
        OrderAdapter adapter=new OrderAdapter(this,orders);
        listView.setAdapter(adapter);
    }

    public void setupSpinner() //下拉選單設定，使用Array方式給值(array.xml)使變更容易
    {
        String[] data=getResources().getStringArray(R.array.storeInfos);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data);
        spinner.setAdapter(adapter);
    }


    public void submit(View view)
    {
        String text=editText.getText().toString(); //取得editText輸入的文字，並轉成String

        textView.setText(text);//將editText輸入的文字顯示在textView上

        Order order=new Order();
        order.note=text;
        order.menuResults=menuResults;
        order.storeInfo=(String)spinner.getSelectedItem();

        orders.add(order);

        Utils.writeFile(this,"history",order.toData() + "\n");

        setupListView();

        editText.setText("");//清空editText
        menuResults=""; //清空menuResults

    }

    public void goToMenu(View view)
    {
        Intent intent = new Intent();
        intent.setClass(this,DrinkMenuActivity.class);
        startActivityForResult(intent,REQUEST_CODE_DRINK_MENU_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CODE_DRINK_MENU_ACTIVITY)
        {
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(this,"完成菜單",Toast.LENGTH_SHORT).show();
                menuResults=(data.getStringExtra("results"));
                //textView.setText(data.getStringExtra("results"));
            }

            if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this,"取消菜單",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Debug","MainActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug","MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Debug","MainActivity onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Debug","MainActivity onStop");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("Debug","MainActivity onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Debug","MainActivity onRestart");
    }
}
