package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cindy on 2016/7/14.
 */
public class DrinkAdapter extends BaseAdapter {

    LayoutInflater inflater;
    List<Drink> drinkList=new ArrayList<>();

    public DrinkAdapter(Context contex, List<Drink> drinks)
    {
        drinkList=drinks;
        inflater=LayoutInflater.from(contex);
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.listview_drink_item,null);
            holder=new Holder();
            holder.drinkName=(TextView)convertView.findViewById(R.id.drinkNameTextView);
            holder.imageView=(ImageView)convertView.findViewById(R.id.imageView);
            holder.mPriceTextView=(TextView)convertView.findViewById(R.id.mPriceTextView);
            holder.lPirceTextView=(TextView)convertView.findViewById(R.id.lPriceTextView);
            convertView.setTag(holder);
        }
        else{
            holder=(Holder)convertView.getTag();

        }
        Drink drink=drinkList.get(position);
        holder.drinkName.setText(drink.getName());
        holder.mPriceTextView.setText(String.valueOf(drink.getmPrice()));
        holder.lPirceTextView.setText(String.valueOf(drink.getlPrice()));
        holder.imageView.setImageResource(drink.imageId);

        return convertView;
    }


    class Holder{
        ImageView imageView;
        TextView mPriceTextView;
        TextView lPirceTextView;
        TextView drinkName;

    }

}
