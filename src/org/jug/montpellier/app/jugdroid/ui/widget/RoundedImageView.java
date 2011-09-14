/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.widget;

import org.jug.montpellier.app.jugdroid.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An ImageView with rounded corners
 * 
 * @author Eric Taix
 */
public class RoundedImageView extends ImageView {

	private Bitmap mMask;
	private int mThumbnailRadius;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public RoundedImageView(Context context) {
		super(context);
		init(context);
	}

	public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public RoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * Prepare the mask
	 * 
	 * @param context
	 */
	private void init(Context context) {
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mThumbnailRadius = context.getResources().getDimensionPixelSize(R.dimen.thumbnail_radius);
	}

	/**
	 * Init the mask if it has not been already instanciated. 
	 */
	private void initMask() {
		if (mMask == null) {
			mMask = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.FILL_AND_STROKE);
			Canvas c = new Canvas(mMask);
			c.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()), mThumbnailRadius, mThumbnailRadius, paint);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		initMask();
		super.onDraw(canvas);
		canvas.drawBitmap(mMask, 0, 0, mPaint);
	}

}
