package com.galaxybruce.android.nestedscroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author bruce.zhang
 * @date 2017/12/29 10:07
 * @description (亲，我是做什么的)
 * <p>
 * modification history:
 */
public class RecycleViewAdapter extends KWBaseRecyclerAdapter<String> {
    public RecycleViewAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        Log.i("aaaaaa", "createView: " + i);
        TextView textView = new TextView(mContext);
        return new ItemHolder(textView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemHolder itemHolder = (ItemHolder) holder;
        final String data = mDatas.get(position);
        itemHolder.textView.setText(data);
        itemHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "String: " + data, Toast.LENGTH_LONG).show();
            }
        });
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemHolder(TextView itemView) {
            super(itemView);
            textView = itemView;
        }
    }
}