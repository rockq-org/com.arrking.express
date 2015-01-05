package com.arrking.android.component;

import android.support.v4.view.ViewPager;

public abstract interface PageIndicator extends ViewPager.OnPageChangeListener
{
  public abstract void notifyDataSetChanged();

  public abstract void setCurrentItem(int paramInt);

  public abstract void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener);

  public abstract void setViewPager(ViewPager paramViewPager);

  public abstract void setViewPager(ViewPager paramViewPager, int paramInt);
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.PageIndicator
 * JD-Core Version:    0.6.2
 */
