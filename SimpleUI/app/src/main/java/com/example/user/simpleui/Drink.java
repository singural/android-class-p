package com.example.user.simpleui;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Cindy on 2016/7/14.
 */

@ParseClassName("Drink")
public class Drink extends ParseObject{
//    private String name;
//    private int mPrice=0;
//    private int lPrice=0;

    public void setName(String name) {
        put("name",name);
    }

    public String getName() {
        return getString("name");
    }

    public void setmPrice(int mPrice) {
        this.put("mPrice",mPrice);

    }

    public int getmPrice() {
        return getInt("mPrice");
    }

    public void setlPrice(int lPrice) {
        this.put("lPrice",lPrice);
    }

    public int getlPrice() {
        return getInt("lPrice");
    }

    public ParseFile getImage() {
        return getParseFile("image");
    }


    int imageId;


    public JSONObject getJsonObject()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name",getName());
            jsonObject.put("mPrice",getmPrice());
            jsonObject.put("lPrice",getlPrice());

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
            drink.setName(jsonObject.getString("name"));
            drink.setlPrice(jsonObject.getInt("lPrice"));
            drink.setmPrice(jsonObject.getInt("mPrice"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return drink;
    }

    public static ParseQuery<Drink> getQuery(){return ParseQuery.getQuery(Drink.class);}

    public static void syncDrinksFromRemote(final FindCallback<Drink> callback)
    {
        Drink.getQuery().findInBackground(new FindCallback<Drink>() {
            @Override
            public void done(final List<Drink> objects, ParseException e) {
                if(e==null)
                {
                    //local端先刪除後,再下載一份新的data到local端
                    Drink.unpinAllInBackground("Drink", new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                Drink.pinAllInBackground("Drink", objects);
                            }

                        }
                    });
                    callback.done(objects,e);
                }
                else
                { //無網路時從local拿資料
                    Drink.getQuery().fromLocalDatastore().findInBackground(callback);
                }
            }
        });
    }


}
