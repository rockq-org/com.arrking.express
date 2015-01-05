package com.arrking.android.component;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager
{
  float distanceX;
  float endX;
  float endY;
  float startX;
  float startY;

  public MyViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = super.dispatchTouchEvent(paramMotionEvent);
    if (bool);
    switch (paramMotionEvent.getAction())
    {
    case 1:
    default:
      getParent().requestDisallowInterceptTouchEvent(true);
      return bool;
    case 0:
      this.startX = paramMotionEvent.getX();
      this.startY = paramMotionEvent.getY();
      this.distanceX = 0.0F;
      return bool;
    case 2:
    }
    this.distanceX = (paramMotionEvent.getX() - this.endX);
    this.endX = paramMotionEvent.getX();
    this.endY = paramMotionEvent.getY();
    if ((Math.abs(this.endY - this.startY) > 0.0F) && (Math.abs(this.endY - this.startY) > Math.abs(this.endX - this.startX)))
    {
      getParent().requestDisallowInterceptTouchEvent(false);
      return bool;
    }
    PagerAdapter localPagerAdapter = getAdapter();
    if (((localPagerAdapter != null) && (getCurrentItem() == 0) && (this.distanceX > 0.0F)) || ((localPagerAdapter != null) && (getCurrentItem() == -1 + localPagerAdapter.getCount()) && (this.distanceX < 0.0F)))
    {
      getParent().requestDisallowInterceptTouchEvent(false);
      return bool;
    }
    getParent().requestDisallowInterceptTouchEvent(true);
    return bool;
  }
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.MyViewPager
 * JD-Core Version:    0.6.2
 */
