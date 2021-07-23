package com.example.project1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<String> {
    String[] names;
    String[] flags;
    Context mContext;
    int[] Flags = {
            R.drawable.aaaa,
            R.drawable.bbbb,
            R.drawable.cccc,
            R.drawable.dddd,
            R.drawable.ic_launcher_background
    };

    public MyAdapter(Context context, String[] countryNames, String[] countryFlags) {
        super(context, R.layout.listview_item);
        this.names = countryNames;
        this.flags = countryFlags;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyAdapter.ViewHolder mViewHolder = new MyAdapter.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            mViewHolder.mFlag = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyAdapter.ViewHolder) convertView.getTag();
        }
        if (flags[position].equals("school")) {
            mViewHolder.mFlag.setImageResource(Flags[0]);
        } else if (flags[position].equals("building")) {
            mViewHolder.mFlag.setImageResource(Flags[1]);
        } else if (flags[position].equals("hotel")) {
            mViewHolder.mFlag.setImageResource(Flags[2]);
        } else if (flags[position].equals("residence")) {
            mViewHolder.mFlag.setImageResource(Flags[3]);
        } else if (flags[position].equals("facility")) {
            mViewHolder.mFlag.setImageResource(Flags[4]);
        } else {
            mViewHolder.mFlag.setImageResource(R.mipmap.ic_launcher);
        }
        mViewHolder.mName.setText(names[position]);

        return convertView;
    }

    static class ViewHolder {
        ImageView mFlag;
        TextView mName;
    }
}