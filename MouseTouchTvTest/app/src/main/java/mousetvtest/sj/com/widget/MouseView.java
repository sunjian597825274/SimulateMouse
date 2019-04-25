/*
 * TVMouseView.java
 * 
 * Version:1.0.0
 *
 * Date: 2014�?�?�?
 *
 * Copyright 2012-2014 Tamic. All Rights Reserved
 */
package mousetvtest.sj.com.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import mousetvtest.sj.com.mousetouchtvtest.R;

public class MouseView extends FrameLayout {
	private ImageView mMouseView;
	private Bitmap mMouseBitmap;
	private int mMouseX = 0;
	private int mMouseY = 0;

	

	public MouseView(Context context) {
		super(context);
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (mMouseView != null && mMouseBitmap != null) {
			mMouseView.measure(MeasureSpec.makeMeasureSpec(mMouseBitmap.getWidth(), MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(mMouseBitmap.getHeight(), MeasureSpec.EXACTLY));
		}
	}
	
	private void init() {
		Drawable drawable =	getResources().getDrawable(
				R.mipmap.shubiao);
		mMouseBitmap = drawableToBitamp(drawable);
		mMouseView = new ImageView(getContext());
		mMouseView.setImageBitmap(mMouseBitmap);
		addView(mMouseView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

	}
	public void moveMouseView(int x , int y){
		//做判断是否出界
		if(mMouseView != null) {
			mMouseX += x;
			mMouseY += y;
			mMouseX=mMouseX<0?0:mMouseX;
			mMouseX=mMouseX>getWidth()-mMouseView.getMeasuredWidth()?getWidth()-mMouseView.getMeasuredWidth():mMouseX;
			mMouseY=mMouseY<0?0:mMouseY;
			mMouseY=mMouseY>getHeight()-mMouseView.getMeasuredHeight()?getHeight()-mMouseView.getMeasuredHeight():mMouseY;
			mMouseView.layout(mMouseX, mMouseY, mMouseX + mMouseView.getMeasuredWidth(), mMouseY + mMouseView.getMeasuredHeight());
		}
	}
	public int getmMouseX(){
		return mMouseX;
	}
	public int getmMouseY(){
		return mMouseY;
	}
	 private Bitmap drawableToBitamp(Drawable drawable) {
		 
		 BitmapDrawable bd = (BitmapDrawable) drawable;
		 Bitmap bitmap = bd.getBitmap();
		 return Bitmap.createScaledBitmap(bitmap, 50, 50 ,true);
	 }
	 

}