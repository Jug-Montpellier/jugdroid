/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Event;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The event adapter. This adapter uses the viewHolder pattern.
 * 
 * @author etaix
 */
public class EventAdapter extends BaseAdapter {
	// The partners list
	private ArrayList<Event> events;
	// The inflater
	private LayoutInflater inflater;

	static class ViewHolder {
		public TextView nameView;
		public TextView dateView;
		public LinearLayout stateView;
	}
	/**
	 * Constructor which initialize the thumbnail tools (paint, mask, ...)
	 * 
	 * @param context
	 */
	public EventAdapter(Context context) {
		super();
		inflater = LayoutInflater.from(context);
	}

	/**
	 * Set a new model list
	 * 
	 * @param events
	 */
	public void setEvents(ArrayList<Event> eventsP) {
		events = eventsP;
		// Force the view to be redrawn
		notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		if (events != null) {
			return events.size();
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
		if (events != null && position >= 0 && position < events.size()) {
			return events.get(position);
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
		Event event = events.get(position);
		if (event != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.event_view, parent, false);
				holder = new ViewHolder();
				holder.nameView = (TextView) convertView.findViewById(R.id.name);
				holder.dateView = (TextView) convertView.findViewById(R.id.date);
				holder.stateView = (LinearLayout) convertView.findViewById(R.id.state);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Set the name, date 
			holder.nameView.setText(event.title);
			holder.dateView.setText(event.date);
			// Set the state of the event (depends on its internal state - ie opened or closed - and on its date)			
			holder.stateView.setBackgroundColor(Color.RED);
			return convertView;
		}
		return null;
	}
}
