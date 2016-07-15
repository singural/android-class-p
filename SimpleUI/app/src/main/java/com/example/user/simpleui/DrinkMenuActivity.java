package com.example.user.simpleui;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDialog.onDrinkOrderListener {

    TextView totalTextView;
    ListView drinkMenuListView;

    String[] names = {"冬瓜紅茶", "玫瑰鹽奶蓋紅茶", "珍珠紅茶拿鐵", "紅茶拿鐵"};
    int[] mPrices = {25, 35, 45, 35};
    int[] lPrices = {35, 45, 55, 45};
    int[] imageId = {R.drawable.drink1, R.drawable.drink2, R.drawable.drink3, R.drawable.drink4};

    List<Drink> drinks = new ArrayList<>();
    List<Drink> orders = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);
        Log.d("Debug", "DrinkMenuActivity onCreate");
        setData();

        totalTextView = (TextView) findViewById(R.id.totalTextView);
        drinkMenuListView = (ListView) findViewById(R.id.drinkMenuListView);

        setupDrinkMenuListView();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setData() {
        for (int i = 0; i < names.length; i++) {
            Drink drink = new Drink();
            drink.name = names[i];
            drink.mPrice = mPrices[i];
            drink.lPrice = lPrices[i];
            drink.imageId = imageId[i];
            drinks.add(drink);

        }
    }

    private void setupDrinkMenuListView() {
        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        drinkMenuListView.setAdapter(adapter);

        drinkMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrinkAdapter drinkAdapter = (DrinkAdapter) parent.getAdapter();
                Drink drink = (Drink) drinkAdapter.getItem(position);
                showDrinkOrderDialog(drink);

                //orders.add(drink);
                //updateTotal();
            }
        });

    }


    public void showDrinkOrderDialog(Drink drink)
    {

        FragmentManager fragmentManager=getFragmentManager();

        FragmentTransaction ft=fragmentManager.beginTransaction();

        DrinkOrderDialog dialog=DrinkOrderDialog.newInstance("","");
        Fragment prev=getFragmentManager().findFragmentByTag("DrinkOrderDialog");
        if(prev !=null) //如果原來的activity已經有同一個Dialog時，先刪除該Dialog
        {
            ft.remove(prev);
        }

        //ft.replace(R.id.root,dialog);
        ft.addToBackStack(null); //回上一頁
        //ft.commit();
        dialog.show(ft,"DrinkOrderDialog");


    }


    public void updateTotal() {
        int total = 0;
        for (Drink drink : orders) {
            total += drink.mPrice;
        }
        totalTextView.setText(String.valueOf(total));
    }

    public void done(View view) {
        Intent intent = new Intent();

        JSONArray jsonArray = new JSONArray();

        for (Drink drink : orders) {
            JSONObject jsonObject = drink.getJsonObject();
            jsonArray.put(jsonObject);
        }

        intent.putExtra("results", jsonArray.toString());

        setResult(RESULT_OK, intent);
        finish(); //將資料帶回上層,所以finish

    }

    public void cancel(View view) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Log.d("Debug", "DrinkMenuActivity onStart");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DrinkMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.simpleui/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Debug", "DrinkMenuActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Debug", "DrinkMenuActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DrinkMenu Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.user.simpleui/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        Log.d("Debug", "DrinkMenuActivity onStop");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Debug", "DrinkMenuActivity onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Debug", "DrinkMenuActivity onRestart");
    }

    @Override
    public void onDrinkOrderFinished() {

    }
}
