/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import java.util.ArrayList;

import greendroid.image.ImageProcessor;
import greendroid.widget.AsyncImageView;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;

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
import android.widget.TextView;

/**
 * The members adapter. This adapter uses the viewHolder pattern and also uses
 * an AsyncImageView to display a member's image.
 * 
 * @author etaix
 */
public class MembersAdapter extends BaseAdapter implements ImageProcessor {

	private static final StringBuilder urlBuilder = new StringBuilder();
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Rect mRectSrc = new Rect();
	private final Rect mRectDest = new Rect();

	// The speakers list
	private ArrayList<Speaker> speakers;

	static class ViewHolder {
		public AsyncImageView imageView;
		public TextView fullnameView;
		public TextView jobPositionView;
	}

	private Bitmap mMask;
	private int mThumbnailSize;
	private int mThumbnailRadius;
	private LayoutInflater mInflater;

	/**
	 * Constructor which initialize the thumbnail tools (paint, mask, ...)
	 * 
	 * @param context
	 */
	public MembersAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		mThumbnailSize = context.getResources().getDimensionPixelSize(R.dimen.thumbnail_size);
		mThumbnailRadius = context.getResources().getDimensionPixelSize(R.dimen.thumbnail_radius);
		prepareMask();
	}

	/**
	 * Draw the background
	 */
	private void prepareMask() {
		mMask = Bitmap.createBitmap(mThumbnailSize, mThumbnailSize, Bitmap.Config.ARGB_8888);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		Canvas c = new Canvas(mMask);
		c.drawRoundRect(new RectF(0, 0, mThumbnailSize, mThumbnailSize), mThumbnailRadius, mThumbnailRadius, paint);
	}

	/**
	 * Set a new model list
	 * 
	 * @param speakersP
	 */
	public void setMembers(ArrayList<Speaker> speakersP) {
		speakers = speakersP;
		// Force the view to be redrawn
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		if (speakers != null) {
			return speakers.size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int position) {
		if (speakers != null && position >= 0 && position < speakers.size()) {
			return speakers.get(position);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		Speaker speaker = speakers.get(position);
		if (speaker != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.image_item2_view, parent, false);
				holder = new ViewHolder();
				holder.imageView = (AsyncImageView) convertView.findViewById(R.id.async_image);
				holder.imageView.setImageProcessor(this);
				holder.fullnameView = (TextView) convertView.findViewById(R.id.fullname);
				holder.jobPositionView = (TextView) convertView.findViewById(R.id.jobposition);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Set the image URL which will be loaded
			urlBuilder.setLength(0);
			holder.imageView.setUrl(urlBuilder.toString());
			// Set the fullname
			holder.fullnameView.setText(speaker.fullName);
			// Set the job position
			holder.jobPositionView.setText(speaker.activity + " - " + speaker.company);

			return convertView;
		}
		return null;
	}

	/**
	 * Process and display an image white it has beeen loaded
	 */
	public Bitmap processImage(Bitmap bitmap) {
		Bitmap result = Bitmap.createBitmap(mThumbnailSize, mThumbnailSize, Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(result);

		mRectSrc.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
		mRectDest.set(0, 0, mThumbnailSize, mThumbnailSize);
		c.drawBitmap(bitmap, mRectSrc, mRectDest, null);
		c.drawBitmap(mMask, 0, 0, mPaint);
		return result;
	}
}
