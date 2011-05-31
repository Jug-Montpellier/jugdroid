/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import greendroid.app.GDActivity;
import greendroid.graphics.drawable.ActionBarDrawable;
import greendroid.widget.ActionBarItem;
import greendroid.widget.LoaderActionBarItem;

import org.jug.montpellier.app.jugdroid.R;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Base class for Jugdroid activities. Provides convenient methods
 * 
 * @author etaix
 */
public abstract class JugActivity extends GDActivity {

	private LoaderActionBarItem loaderItem;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Add the refresh action item
		loaderItem = (LoaderActionBarItem) getActionBar().newActionBarItem(LoaderActionBarItem.class);
		addActionBarItem(loaderItem.setDrawable(new ActionBarDrawable(getResources(),
				com.cyrilmottier.android.greendroid.R.drawable.gd_action_bar_refresh)), R.id.action_bar_refresh);
	}
	
	/**
	 * Toast a message with a certain duration. Typically used to inform the
	 * user when an error occured.
	 * 
	 * @param message
	 *            The message to display
	 * @param duration
	 *            A duration (ie Toast.LENGTH_SHORT or Toast.LENGTH_LONG)
	 */
	public void toastMessage(final String message, final int duration) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					Context context = getApplicationContext();
					Toast toast = Toast.makeText(context, message, duration);
					toast.show();
				}
				catch (RuntimeException e) {
					Log.e("JugDroid", "A runtime exception was thrown while executing code in the ui thread", e);
				}
			}
		});
	}

	@Override
	public final boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case R.id.action_bar_refresh:
			refresh();
			return true;
		default:
			return onActionBarItemClick(item, position);
		}
	}
	
	/**
	 * This method can to be overidden when an activity needs to handle ActionBarItem click 
	 * @param item
	 * @param position
	 * @return
	 */
	public boolean onActionBarItemClick(ActionBarItem item, int position) {	
		return false;
	}
	
	/**
	 * This method must be overriden to refresh data 
	 */
	public abstract void refresh();		
	
	/**
	 * Display or hide the infinite progressbar. This method can only be called from the UI thread.
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		loaderItem.setLoading(loading);
	}
}
