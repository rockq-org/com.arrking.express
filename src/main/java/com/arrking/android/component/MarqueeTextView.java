package com.arrking.android.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView
{
  public MarqueeTextView(Context paramContext)
  {
    super(paramContext);
  }

  public MarqueeTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MarqueeTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean isFocused()
  {
    return true;
  }
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.MarqueeTextView
 * JD-Core Version:    0.6.2
 */
