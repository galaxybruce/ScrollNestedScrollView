package com.galaxybruce.android.nestedscroll;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.ScrollView;

import com.galaxybruce.android.nestedscroll.view.ScrollViewNestedViewPager;
import com.galaxybruce.android.nestedscroll.view.ScrollNestedScrollView;
import com.xujun.contralayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bruce.zhang
 * @date 2017/12/28 14:00
 * @description (亲，我是做什么的)
 * <p>
 * modification history:
 */
public class ScrollViewEnterActivity extends AppCompatActivity {

    private ScrollNestedScrollView mScrollView;


    private ScrollViewNestedViewPager viewPager;
    private TabLayout tabLayout;
    private MyFragmentAdapter viewpageadapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo2);

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i <50 ; i++) {
            strings.add("recyclerview " + i);
        }
        RecycleViewAdapter adapter = new RecycleViewAdapter(ScrollViewEnterActivity.this);
        adapter.setData(strings);
        recyclerView.setAdapter(adapter);

        viewPager = (ScrollViewNestedViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        mScrollView = (ScrollNestedScrollView) findViewById(R.id.activity_scroll_view_enter);
        mScrollView.setOnScrollViewToBottomListener(new ScrollNestedScrollView.OnScrollViewToBottomListener() {

            @Override
            public void onScrollToBottom() {
                //滑到底部刷新每个tab中的数据
//                fragments.get(viewPager.getCurrent()).loadData();
            }

            @Override
            public void onScrollChanged(ScrollNestedScrollView scrollView, int x, int y, int oldx, int oldy) {

            }
        });

        fragments = new ArrayList<>();
        fragments.add(new MyListViewFragment());
        fragments.add(new MyListViewFragment2());
        fragments.add(new MyListViewFragment3());

        viewpageadapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewpageadapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置欲缓存页
        viewPager.setOffscreenPageLimit(3);

        //我通过xml设置该属性不管用，这样好用（不知道为什么），设置该属性目的是防止listView（ViewPager）跳到顶部

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                viewPager.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        final int keyCode = event.getKeyCode();
        final int action = event.getAction();

        if (keyCode == KeyEvent.KEYCODE_BACK && action == KeyEvent.ACTION_DOWN) {
            if (mScrollView.scrollToBottom()) {
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }


    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "标题"+position;
        }
    }


}
