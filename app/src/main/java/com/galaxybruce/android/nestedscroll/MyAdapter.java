package com.galaxybruce.android.nestedscroll;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author bruce.zhang
 * @date 2017/12/28 14:32
 * @description (亲，我是做什么的)
 * <p>
 * modification history:
 */
public class MyAdapter extends BaseAdapter {

    List<String> list;


    public MyAdapter(Context context, List<String> list) {
        super();
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    LayoutInflater inflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view;
        final TextView text;

        if (convertView == null) {
//            Log.i("aaaaaa", "createView: " + position);
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        } else {
            view = convertView;
        }

        text = (TextView) view;

        final String item = getItem(position);
        if (item instanceof CharSequence) {
            text.setText((CharSequence) item);
        } else {
            text.setText(item.toString());
        }

        return view;
    }
}
