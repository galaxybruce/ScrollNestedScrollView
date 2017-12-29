package com.galaxybruce.android.nestedscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xujun.contralayout.R;

import java.util.ArrayList;

/**
 * @author bruce.zhang
 * @date 2017/12/28 13:57
 * @description (亲，我是做什么的)
 * <p>
 * modification history:
 */
public class MyListViewFragment extends Fragment {

    private ArrayList<String> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.weibo2_recycler,container,false);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);

        list = new ArrayList<>();
        for(int i =0;i<100;i++){
            list.add("数据："+i);
        }

        RecycleViewAdapter adapter = new RecycleViewAdapter(getContext());
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void loadData(){
        //加载数据list.add（）……别忘了刷新哈
    }

}
