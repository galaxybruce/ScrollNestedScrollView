package com.galaxybruce.android.nestedscroll.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

/**
 * @author bruce.zhang
 * @date 2017/12/28 13:54
 * @description (亲，我是做什么的)
 * <p>
 * modification history:
 */
public class ScrollNestedSwipeRefreshLayout extends SwipeRefreshLayout {

    public ScrollNestedSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public ScrollNestedSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    int lastX = -1;
    int lastY = -1;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                if (dealtX < dealtY) {
                    if(y > lastY) {
                        // 只有tablayout滚动到顶部并且listview滚动到顶部时，下滑的时候，下拉刷新才有效
                        // 这是时候需要屏蔽外层事件
                        if (scrollToBottom()) {
                            boolean disallowIntercept = !canScrollDown();
                            getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
                        }
                    }
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean canScrollUp(){
        View view = getChildAt(0);
        if(view instanceof AbsListView) {
            return ViewCompat.canScrollVertically(view, 1);
        } else  if(view instanceof RecyclerView) {
            return ((RecyclerView)view).canScrollVertically(1);
        }
        return false;
    }

    public boolean canScrollDown(){
        View view = getChildAt(0);
        if(view instanceof AbsListView) {
            return ViewCompat.canScrollVertically(view, -1);
        } else  if(view instanceof RecyclerView) {
            return ((RecyclerView)view).canScrollVertically(-1);
        }
        return false;
    }

    private boolean scrollToBottom(){
        ScrollViewNestedViewPager viewPager = (ScrollViewNestedViewPager) (this.getParent());
        return viewPager.scrollToBottom();
    }

}
