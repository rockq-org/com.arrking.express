package com.arrking.android.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.ScrollView;

public class YLScrollView extends ScrollView
{
  private GestureDetector a;
  private ScrollViewListener b = null;

  public YLScrollView(Context paramContext)
  {
    super(paramContext);
  }

  public YLScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public YLScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.b == null)
      return;
    this.b.scrollViewListener(this, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setGestureDetector(GestureDetector paramGestureDetector)
  {
    this.a = paramGestureDetector;
  }

  public void setScrollViewListener(ScrollViewListener paramScrollViewListener)
  {
    this.b = paramScrollViewListener;
  }
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.YLScrollView
 * JD-Core Version:    0.6.2
 */
