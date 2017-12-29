package com.galaxybruce.android.nestedscroll;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView base adapter
 *  created by bruce.zhang
 */
public abstract class KWBaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected Resources mResources;
	protected ArrayList<T> mDatas = new ArrayList<T>();

	public KWBaseRecyclerAdapter(Context context)
	{
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mResources = context.getResources();
	}

	@Override
	public int getItemCount() {
		return mDatas == null ? 0 : mDatas.size();
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public int getHeaderViewCount()
	{
		return 0;
	}

	public void setData(ArrayList<T> data)
	{
		mDatas = data;
		notifyDataSetChanged();
	}
	
	public void addData(T data)
	{
		int position = getDataSize();
		if (mDatas != null) {
			mDatas.add(data);
		}
//		notifyDataSetChanged();
		notifyItemInserted(position + getHeaderViewCount());
	}
	
	public void addData(int index, T data)
	{
		if (mDatas != null) {
			mDatas.add(index, data);
		}
//		notifyDataSetChanged();
		notifyItemInserted(index + getHeaderViewCount());
	}

	public void addData(List<T> data) {
		addData(data, true);
	}

	public void addData(List<T> data, boolean refresh) {
		if(data == null) return;

		int position = getDataSize() + getHeaderViewCount();
		int count = data.size();
		if (mDatas != null) {
			mDatas.addAll(data);
		}
		if(refresh)
		{
			notifyDataSetChanged();
//			this.notifyItemRangeInserted(position, count);
		}
	}

	public void removeData(T data) {
		if(mDatas != null) {
			mDatas.remove(data);
		}
		notifyDataSetChanged();
	}

	public void clear()
	{
		clear(true);
	}

	public void clear(boolean refresh) {
		if(mDatas != null)
		{
			mDatas.clear();
		}
		if(refresh)
		{
			notifyDataSetChanged();
		}
	}

	public int getDataSize() {
		return mDatas == null ? 0 : mDatas.size();
	}

	public ArrayList<T> getData() {
		return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
	}


}
