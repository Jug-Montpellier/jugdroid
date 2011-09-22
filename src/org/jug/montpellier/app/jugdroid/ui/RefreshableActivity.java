/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import android.app.Activity;

/**
 * This interface defines an Activity which can be refreshed by a user
 * @author Eric Taix
 */
public interface RefreshableActivity {

	/**
	 * Returns the activity
	 * @return
	 */
	public Activity getActivity();
	
	/**
	 * Data have been refreshed
	 */
	public void onRefreshed();
}
