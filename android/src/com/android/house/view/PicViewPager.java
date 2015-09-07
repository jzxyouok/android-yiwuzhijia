package com.android.house.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 
 * @author shenliangwei
 * @description 自定义ViewPager,添加设置是否可以左右滑动的功能，应用在主页面上。 效果：当滑动到map
 *              fragment时是左右滑动切换功能失效
 */
public class PicViewPager extends ViewPager {

	private boolean isCanScroll = true; // 默认可以滑动

	public PicViewPager(Context context) {
		super(context);
	}

	public PicViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置是否可以滑动
	 * 
	 * @param isCanScroll
	 */
	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}

	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		super.setCurrentItem(item);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}

	}
}
