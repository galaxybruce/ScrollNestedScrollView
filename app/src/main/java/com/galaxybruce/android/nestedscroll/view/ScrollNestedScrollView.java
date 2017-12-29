package com.galaxybruce.android.nestedscroll.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * @author bruce.zhang
 * @date 2017/12/28 13:40
 * @description (亲，我是做什么的)
 *
 *
 * 部分原理参考这篇文章：
 * ScrollView+TabLayout+ViewPager+ListView复杂滑动嵌套、上拉加载
 * http://blog.csdn.net/hello_1s/article/details/53468201
 *
 * 在ScrollView中使用ViewPager以及ViewPager使用wrap_content
 * http://smilehacker.com/zai-scrollviewzhong-shi-yong-viewpageryi-ji-viewpagershi-yong-wrap_content.html
 * modification history:
 */
public class ScrollNestedScrollView extends ScrollView {

    public interface OnScrollViewToBottomListener {
        void onScrollToBottom();

        void onScrollChanged(ScrollNestedScrollView scrollView,
                             int x, int y, int oldx, int oldy);
    }

    private OnScrollViewToBottomListener onScrollViewToBottomListener = null;

    public ScrollNestedScrollView(Context context) {
        super(context);
    }

    public ScrollNestedScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollViewToBottomListener(OnScrollViewToBottomListener onScrollViewToBottomListener){
        this.onScrollViewToBottomListener = onScrollViewToBottomListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if(onScrollViewToBottomListener != null) {
            if (scrollToBottom()) {
                onScrollViewToBottomListener.onScrollToBottom();
            } else {
                onScrollViewToBottomListener.onScrollChanged(this, l, t, oldl, oldt);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //滚动到底部时，不允许再滚回来，需要按返回键

        if (scrollToBottom()) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // NestedViewPager中通过getParent获取的(ViewGroup) this.getParent().getParent()).getMeasuredHeight();
        // 不准确，需要等到父布局测量好后再进行一次测量
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            return;
        }

        if (getChildCount() > 0) {
            final View child = getChildAt(0);
            final int widthPadding;
            final int heightPadding;
            final int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
            final FrameLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                widthPadding = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
                heightPadding = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin;
            } else {
                widthPadding = getPaddingLeft() + getPaddingRight();
                heightPadding = getPaddingTop() + getPaddingBottom();
            }

            final int desiredHeight = getMeasuredHeight() - heightPadding;
            if (child.getMeasuredHeight() > desiredHeight) {
                final int childWidthMeasureSpec = getChildMeasureSpec(
                        widthMeasureSpec, widthPadding, lp.width);
                final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        0, MeasureSpec.UNSPECIFIED);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    public boolean scrollToBottom(){
        View view = getChildAt(getChildCount() - 1);
        int d = view.getBottom();
        d -= (getHeight() + getScrollY());

        return d == 0;
    }

}
