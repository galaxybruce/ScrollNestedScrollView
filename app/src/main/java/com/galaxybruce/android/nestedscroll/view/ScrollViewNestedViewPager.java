package com.galaxybruce.android.nestedscroll.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * @author bruce.zhang
 * @date 2017/12/28 13:54
 * @description 嵌套在scrollview中的ViewPager
 * <p>
 * modification history:
 */
public class ScrollViewNestedViewPager extends ViewPager {

    public ScrollViewNestedViewPager(Context context) {
        this(context, null);
    }

    public ScrollViewNestedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //防止打开界面是自动滚动到viewpager
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
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
                // 保证子View能够接收到Action_move事件
                lastX = x;
                lastY = y;
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                dealtX += Math.abs(x - lastX);
                dealtY += Math.abs(y - lastY);
                if (dealtX >= dealtY) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    //子view是下拉刷新view，tablyout滚动到定不是时，拦截事件交给下拉刷新组件
                    if (refreshEnable()) {
                        if (!scrollToBottom()) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        boolean disallowIntercept;
                        if(y > lastY) {
                            disallowIntercept = scrollToBottom() && canScrollDown();
                        } else {
                            disallowIntercept = scrollToBottom() && canScrollUp();
                        }
                        getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int parentHeight = getParentHeight();
        int height = parentHeight - getTabViewHeight();
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int getParentHeight() {
        ViewGroup parent = (ViewGroup)getParent();
        while(parent != null)
        {
            if (parent instanceof ScrollNestedScrollView) {
                return ((ScrollNestedScrollView)parent).getMeasuredHeight();
            }

            if(parent.getParent() instanceof ViewGroup) {
                parent = (ViewGroup) parent.getParent();
            } else {
                parent = null;
            }
        }
        return 0;
    }

    private int getTabViewHeight() {
        ViewGroup parent = (ViewGroup) this.getParent();
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            if (parent.getChildAt(i) instanceof TabLayout) {
                return parent.getChildAt(i).getMeasuredHeight();
            }
        }
        return 0;
    }

    private boolean refreshEnable() {
        return getChildAt(getCurrentItem()) instanceof SwipeRefreshLayout;
    }

    private boolean canScrollUp(){
        View view = getChildAt(getCurrentItem());
        if(view instanceof AbsListView) {
            return ViewCompat.canScrollVertically(view, 1);
        } else  if(view instanceof RecyclerView) {
            return ((RecyclerView)view).canScrollVertically(1);
        } else if(view instanceof ScrollNestedSwipeRefreshLayout) {
            return ((ScrollNestedSwipeRefreshLayout)view).canScrollUp();
        }
        return false;
    }

    private boolean canScrollDown(){
        View view = getChildAt(getCurrentItem());
        if(view instanceof AbsListView) {
            return ViewCompat.canScrollVertically(view, -1);
        } else if(view instanceof RecyclerView) {
            return ((RecyclerView)view).canScrollVertically(-1);
        } else if(view instanceof ScrollNestedSwipeRefreshLayout) {
            return ((ScrollNestedSwipeRefreshLayout)view).canScrollDown();
        }
        return false;
    }

    public boolean scrollToBottom(){
        ViewGroup parent = (ViewGroup)getParent();
        while(parent != null)
        {
            if (parent instanceof ScrollNestedScrollView) {
                return ((ScrollNestedScrollView)parent).scrollToBottom();
            }

            if(parent.getParent() instanceof ViewGroup) {
                parent = (ViewGroup) parent.getParent();
            } else {
                parent = null;
            }
        }
        return false;
    }

}
