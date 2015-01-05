package com.arrking.android.component;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arrking.express.R;

public class LoadingUI extends LinearLayout
{
  private String noticeStr = "";
  TextView noticeTV;

  public LoadingUI(Context paramContext)
  {
    super(paramContext);
  }

  public LoadingUI(Context paramContext, String paramString)
  {
    super(paramContext);
    // HORIZONTAL got the integer value 0 and Vertical got 1
    setOrientation(LinearLayout.VERTICAL);
    setGravity(17);
    setBackgroundResource(R.drawable.loading);
    this.noticeStr = paramString;
    ProgressBar localProgressBar = new ProgressBar(paramContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    localProgressBar.setLayoutParams(localLayoutParams);
    localProgressBar.setIndeterminateDrawable(paramContext.getResources().getDrawable(R.drawable.progerssbar_bg));
    setLayoutParams(localLayoutParams);
    addView(localProgressBar);
    this.noticeTV = new TextView(paramContext);
    this.noticeTV.setText(this.noticeStr);
    this.noticeTV.setTextColor(-1);
    this.noticeTV.setTextSize(14.0F);
    this.noticeTV.setLayoutParams(localLayoutParams);
    addView(this.noticeTV);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    Log.v("onTouchEvent", "onTouchEvent");
    return true;
  }

  public void setText(String paramString)
  {
    this.noticeTV.setText(paramString);
  }

  public void setTextColor(int paramInt)
  {
    this.noticeTV.setTextColor(paramInt);
  }

  public void setVisiable(int paramInt)
  {
    this.noticeTV.setVisibility(paramInt);
  }
}
