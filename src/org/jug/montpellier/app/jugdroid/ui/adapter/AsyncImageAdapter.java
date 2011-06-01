/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import greendroid.image.ImageProcessor;

import org.jug.montpellier.app.jugdroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * The abstract adapter. This adapter aims to provide the base method for image async loading.
 * 
 * @author etaix
 */
public abstract class AsyncImageAdapter extends BaseAdapter implements ImageProcessor {

	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Rect mRectSrc = new Rect();
	private final Rect mRectDest = new Rect();

	private Bitmap mMask;
	private int mThumbnailSizeHeight;
	private int mThumbnailSizeWidth;
	private int mThumbnailRadius;
	private LayoutInflater mInflater;

	/**
	 * Constructor which initialize the thumbnail tools (paint, mask, ...)
	 * 
	 * @param context
	 */
	public AsyncImageAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mThumbnailSizeHeight = context.getResources().getDimensionPixelSize(R.dimen.thumbnail_size_height);
		mThumbnailSizeWidth = context.getResources().getDimensionPixelSize(R.dimen.thumbnail_size_width);
		mThumbnailRadius = context.getResources().getDimensionPixelSize(R.dimen.thumbnail_radius);
		prepareMask();
	}

	/**
	 * Draw the background
	 */
	private void prepareMask() {
		mMask = Bitmap.createBitmap(mThumbnailSizeWidth, mThumbnailSizeHeight, Bitmap.Config.ARGB_8888);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		Canvas c = new Canvas(mMask);
		c.drawRoundRect(new RectF(0, 0, mThumbnailSizeWidth, mThumbnailSizeHeight), mThumbnailRadius, mThumbnailRadius, paint);
	}

	/**
	 * Process and display an image white it has beeen loaded
	 */
	public Bitmap processImage(Bitmap bitmap) {
		Bitmap result = Bitmap.createBitmap(mThumbnailSizeWidth, mThumbnailSizeHeight, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(result);

		mRectSrc.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
		mRectDest.set(0, 0, mThumbnailSizeWidth, mThumbnailSizeHeight);
		c.drawBitmap(bitmap, mRectSrc, mRectDest, null);
		c.drawBitmap(mMask, 0, 0, mPaint);
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public final View getView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent, mInflater);
	}
	
	/**
	 * Do the same thing as get(int,View,ViewGroup) does but add the LayoutInflater as parameter
	 */
	public abstract View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater);
}
