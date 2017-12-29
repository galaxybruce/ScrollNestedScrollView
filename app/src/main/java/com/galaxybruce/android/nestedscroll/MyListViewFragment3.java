package com.galaxybruce.android.nestedscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xujun.contralayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bruce.zhang
 * @date 2017/12/28 13:57
 * @description (亲，我是做什么的)
 * <p>
 * modification history:
 */
public class MyListViewFragment3 extends Fragment {

    private ListView myListView;
    private BaseAdapter adapter;
    private List<String> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weibo2_listview,container,false);

        myListView = (ListView) view.findViewById(R.id.listView);
        list = new ArrayList<>();

        adapter = new MyAdapter(getActivity(), list);
        myListView.setAdapter(adapter);
        return view;
    }

    public void loadData(){
        //加载数据list.add（）……别忘了刷新哈
    }

}
