package com.example.myactivity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<area> list;
	private LayoutInflater mInflater;
	TextView textView = null;
	public MyAdapter(Context context,ArrayList<area> list){
		this.context = context;
		this.list=list;
		mInflater = LayoutInflater.from(context);
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			textView = new TextView(context);
		} else {
			textView = (TextView) convertView;
		}
		textView.setText(list.get(position).getName());
		textView.setTextSize(18);
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(0, 20, 0, 20);
		textView.setTextColor(Color.BLACK);
		return textView;
	}
	

}
