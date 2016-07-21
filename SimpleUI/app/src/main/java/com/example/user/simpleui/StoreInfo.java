package com.example.user.simpleui;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Cindy on 2016/7/20.
 */

@ParseClassName("StoreInfo")
public class StoreInfo extends ParseObject{

    public void setName(String name) {
        put("name",name);
    }

    public String getName() {
        return getString("name");
    }

    public void setAddress(String address) {
        put("address",address);
    }

    public String getAddress() {
        return getString("address");
    }


}
