package com.arrking.android.component;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TransparentLoadingUI extends LinearLayout
{
  private String noticeStr = "";
  TextView noticeTV;

  public TransparentLoadingUI(Context paramContext)
  {
    super(paramContext);
  }

  public TransparentLoadingUI(Context paramContext, String paramString)
  {
    super(paramContext);
    setOrientation(1);
    setGravity(17);
    ProgressBar localProgressBar = new ProgressBar(paramContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    localProgressBar.setLayoutParams(localLayoutParams);
    localProgressBar.setIndeterminateDrawable(paramContext.getResources().getDrawable(2130837743));
    setLayoutParams(localLayoutParams);
    addView(localProgressBar);
    this.noticeTV = new TextView(paramContext);
    this.noticeStr = paramString;
    this.noticeTV.setText(this.noticeStr);
    this.noticeTV.setTextColor(paramContext.getResources().getColor(2131165329));
    this.noticeTV.setTextSize(14.0F);
    this.noticeTV.setLayoutParams(localLayoutParams);
    addView(this.noticeTV);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    Log.v("onTouchEvent", "onTouchEvent");
    return true;
  }

  public void setTextCloor(int paramInt)
  {
    this.noticeTV.setTextColor(paramInt);
  }
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.TransparentLoadingUI
 * JD-Core Version:    0.6.2
 */
