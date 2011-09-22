/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The members adapter. This adapter uses the viewHolder pattern and also uses
 * an AsyncImageView to display a member's image.
 * 
 * @author etaix
 */
public class MembersAdapter extends AsyncImageAdapter {
	// The speakers list
	protected ArrayList<Speaker> speakers;
	// The asynchronous image loader
	protected ImageLoader imgLoader;
	// The current activity
	protected Activity activity;

	static class ViewHolder {
		public ImageView imageView;
		public TextView fullnameView;
		public TextView jobPositionView;
		public TextView memberFonctionView;
	}

	/**
	 * Constructor which initialize the thumbnail tools (paint, mask, ...)
	 * 
	 * @param context
	 */
	public MembersAdapter(Activity activityP) {
		super(activityP);
		activity = activityP;
		imgLoader = new ImageLoader(activityP);
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

	/**
	 * Return the last known speakers
	 * @return
	 */
	public ArrayList<Speaker> getMembers() {
		return speakers;		
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
				convertView = inflater.inflate(R.layout.member_item, parent, false);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.photo);
				holder.fullnameView = (TextView) convertView.findViewById(R.id.fullname);
				holder.memberFonctionView = (TextView) convertView.findViewById(R.id.memberFonction);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Set the image URL which will be loaded
			if (speaker.photoUrl != null && speaker.photoUrl.length() > 0) {
				imgLoader.displayImage(speaker.photoUrl, activity, holder.imageView, true);
			}
			// Set the fullname
			holder.fullnameView.setText(speaker.fullName);
			// Set the job position
			holder.memberFonctionView.setText(speaker.memberFct);
			return convertView;
		}
		return null;
	}
	
}
