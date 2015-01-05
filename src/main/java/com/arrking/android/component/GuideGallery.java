package com.arrking.android.component;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class GuideGallery extends Gallery
{
  private static int firstChildPaddingLeft;
  private static int firstChildWidth;
  private boolean flag;
  private Camera mCamera = new Camera();
  private int mPaddingLeft;
  private int mWidth;
  private int offsetX;

  public GuideGallery(Context paramContext)
  {
    super(paramContext);
    setStaticTransformationsEnabled(true);
  }

  public GuideGallery(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setAttributesValue(paramContext, paramAttributeSet);
    setStaticTransformationsEnabled(true);
  }

  public GuideGallery(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setAttributesValue(paramContext, paramAttributeSet);
    setStaticTransformationsEnabled(true);
  }

  private void setAttributesValue(Context paramContext, AttributeSet paramAttributeSet)
  {
    this.mPaddingLeft = paramContext.obtainStyledAttributes(paramAttributeSet, new int[] { 16842966 }).getDimensionPixelSize(0, 0);
  }

  protected boolean getChildStaticTransformation(View paramView, Transformation paramTransformation)
  {
    paramTransformation.clear();
    paramTransformation.setTransformationType(Transformation.TYPE_MATRIX);
    this.mCamera.save();
    Matrix localMatrix = paramTransformation.getMatrix();
    if (this.flag)
    {
      firstChildWidth = getChildAt(0).getWidth();
      firstChildPaddingLeft = getChildAt(0).getPaddingLeft();
      this.flag = false;
    }
    this.offsetX = (firstChildWidth / 2 + firstChildPaddingLeft + this.mPaddingLeft - this.mWidth / 2);
    this.mCamera.translate(this.offsetX, 0.0F, 0.0F);
    this.mCamera.getMatrix(localMatrix);
    this.mCamera.restore();
    return true;
  }

  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.flag)
    {
      this.mWidth = (paramInt1 * 2);
      getLayoutParams().width = this.mWidth;
      this.flag = true;
    }
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    paramMotionEvent.offsetLocation(-this.offsetX, 0.0F);
    return super.onTouchEvent(paramMotionEvent);
  }
}

/* Location:           /Users/hain/git/bpo/foods-e-home/sample/classes/
 * Qualified Name:     com.arrking.android.component.GuideGallery
 * JD-Core Version:    0.6.2
 */
