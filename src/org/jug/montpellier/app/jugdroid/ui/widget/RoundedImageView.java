/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An ImageView with rounded corners
 * 
 * @author Eric Taix
 */
public class RoundedImageView extends ImageView {

	public RoundedImageView(Context context) {
		super(context);
	}

	public RoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		Drawable drawable = getDrawable();

		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
		int w = getWidth(), h = getHeight();

		Bitmap roundBitmap = ImageUtility.getRoundedCornerBitmap(getContext(), bitmap, 10, w, h);
		canvas.drawBitmap(roundBitmap, 0, 0, null);
	}

}
