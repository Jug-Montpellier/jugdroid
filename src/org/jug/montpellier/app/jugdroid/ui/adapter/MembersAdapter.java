package org.jug.montpellier.app.jugdroid.ui.adapter;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * This class adapt a model (a list of Speaker) to the widget
 * 
 * @author etaix
 */
public class MembersAdapter extends BaseAdapter implements TitleProvider {

	private LayoutInflater mInflater;
	private ArrayList<Speaker> speakers;

	public MembersAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@Override
	public int getCount() {
		if (speakers != null) {
			return speakers.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (speakers != null && position > 0 && position < speakers.size()) {
			return speakers.get(position);
		}		 
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.flow_item, null);
		}
		((TextView) convertView.findViewById(R.id.userFullName)).setText(speakers.get(position).fullName);
		return convertView; 
	}

	/*
	 * (non-Javadoc)
	 * @see org.taptwo.android.widget.TitleProvider#getTitle(int)
	 */
	@Override
	public String getTitle(int position) {
		return speakers.get(position).fullName;
	}


}
