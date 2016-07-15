package com.example.user.simpleui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cindy on 2016/7/14.
 */
public class Drink {
    String name;
    int mPrice=0;
    int lPrice=0;
    int imageId;


    public JSONObject getJsonObject()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("mPrice",mPrice);
            jsonObject.put("lPrice",lPrice);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
        //  "name" : "冬瓜紅茶",
        // "price": 35,

    }

    public static Drink newInstanceWithData(String data)
    {
        Drink drink=new Drink();
        try {
            JSONObject jsonObject=new JSONObject(data);
            drink.name=jsonObject.getString("name");
            drink.lPrice=jsonObject.getInt("lPrice");
            drink.mPrice=jsonObject.getInt("mPrice");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return drink;
    }

}
