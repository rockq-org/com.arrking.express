package com.arrking.android.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery extends Gallery
{
  public MyGallery(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private boolean isScrollingLeft(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2)
  {
    return paramMotionEvent2.getX() > paramMotionEvent1.getX();
  }

  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2));
    for (int i = 21; ; i = 22)
    {
      onKeyDown(i, null);
      return false;
    }
  }
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.MyGallery
 * JD-Core Version:    0.6.2
 */
