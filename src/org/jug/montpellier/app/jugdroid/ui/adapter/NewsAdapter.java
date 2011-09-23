/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.News;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * The news adapter. This adapter uses the viewHolder pattern.
 * 
 * @author Eric Taix
 */
public class NewsAdapter extends AsyncImageAdapter {
	// The news list
	protected ArrayList<News> news;
	// The current activity
	protected Activity activity;

	static class ViewHolder {
		public TextView title;
		public TextView date;
		public TextView content;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public NewsAdapter(Activity activityP) {
		super(activityP);
		activity = activityP;
	}

	/**
	 * Set a new model list
	 * 
	 * @param newsP
	 */
	public void setNews(ArrayList<News> newsP) {
		news = newsP;
		// Force the view to be redrawn
		notifyDataSetChanged();
	}

	/**
	 * Return the last known speakers
	 * @return
	 */
	public ArrayList<News> getNews() {
		return news;		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		if (news != null) {
			return news.size();
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
		if (news != null && position >= 0 && position < news.size()) {
			return news.get(position);
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
		News ne = news.get(position);
		if (ne != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.news_item, parent, false);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.content = (TextView) convertView.findViewById(R.id.content);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Set the title
			holder.title.setText(ne.title);
			// Set the date
			holder.date.setText(ne.date);
			// Set the content
			holder.content.setText(ne.content);
			return convertView;
		}
		return null;
	}
	
}
