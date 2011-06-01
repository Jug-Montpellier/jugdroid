/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import greendroid.widget.AsyncImageView;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The members adapter. This adapter uses the viewHolder pattern and also uses
 * an AsyncImageView to display a member's image.
 * 
 * @author etaix
 */
public class MembersAdapter extends AsyncImageAdapter {
	// The speakers list
	private ArrayList<Speaker> speakers;

	static class ViewHolder {
		public AsyncImageView imageView;
		public TextView fullnameView;
		public TextView jobPositionView;
	}
	/**
	 * Constructor which initialize the thumbnail tools (paint, mask, ...)
	 * 
	 * @param context
	 */
	public MembersAdapter(Context context) {
		super(context);
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
	public View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater) {
		Speaker speaker = speakers.get(position);
		if (speaker != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.image_item2_view, parent, false);
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
			if (speaker.image != null && speaker.image.length() > 0) {
				holder.imageView.setUrl(speaker.image);
			}
			holder.imageView.setPaused(false);
			// Set the fullname
			holder.fullnameView.setText(speaker.fullName);
			// Set the job position
			holder.jobPositionView.setText(speaker.activity + " - " + speaker.company);
			return convertView;
		}
		return null;
	}
}
