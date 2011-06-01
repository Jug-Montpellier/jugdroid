/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import greendroid.widget.AsyncImageView;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Partner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The partners adapter. This adapter uses the viewHolder pattern and also uses
 * an AsyncImageView to display a member's image.
 * 
 * @author etaix
 */
public class PartnersAdapter extends AsyncImageAdapter {
	// The partners list
	private ArrayList<Partner> partners;

	static class ViewHolder {
		public AsyncImageView imageView;
		public TextView nameView;
		public TextView urlView;
	}
	/**
	 * Constructor which initialize the thumbnail tools (paint, mask, ...)
	 * 
	 * @param context
	 */
	public PartnersAdapter(Context context) {
		super(context);
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
	public View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater) {
		Partner partner = partners.get(position);
		if (partner != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.image_item2_view, parent, false);
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
}
