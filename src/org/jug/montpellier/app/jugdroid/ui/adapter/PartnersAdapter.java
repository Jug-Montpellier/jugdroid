/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import greendroid.image.ImageProcessor;
import greendroid.widget.AsyncImageView;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Partner;

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
 * The partners adapter. This adapter uses the viewHolder pattern and also uses
 * an AsyncImageView to display a member's image.
 * 
 * @author etaix
 */
public class PartnersAdapter extends BaseAdapter implements ImageProcessor {

	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final Rect mRectSrc = new Rect();
	private final Rect mRectDest = new Rect();

	// The partners list
	private ArrayList<Partner> partners;

	static class ViewHolder {
		public AsyncImageView imageView;
		public TextView nameView;
		public TextView urlView;
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
	public PartnersAdapter(Context context) {
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
	 * @param partners
	 */
	public void setPartners(ArrayList<Partner> partnersP) {
		partners = partnersP;
		// Force the view to be redrawn
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		if (partners != null) {
			return partners.size();
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
		if (partners != null && position >= 0 && position < partners.size()) {
			return partners.get(position);
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
		Partner partner = partners.get(position);
		if (partner != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.image_item2_view, parent, false);
				holder = new ViewHolder();
				holder.imageView = (AsyncImageView) convertView.findViewById(R.id.async_image);
				holder.imageView.setImageProcessor(this);
				holder.nameView = (TextView) convertView.findViewById(R.id.fullname);
				holder.urlView = (TextView) convertView.findViewById(R.id.jobposition);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Set the image URL which will be loaded
			if (partner.logoURL != null && partner.logoURL.length() > 0) {
				holder.imageView.setUrl(partner.logoURL);
			}
			holder.imageView.setPaused(false);
			// Set the name
			holder.nameView.setText(partner.name);
			// Set the web site url
			holder.urlView.setText(partner.websiteUrl);
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
