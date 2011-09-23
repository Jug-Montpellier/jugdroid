package org.jug.montpellier.app.jugdroid.ui.fragment;

import org.jug.montpellier.app.jugdroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DashboardFragment extends Fragment {

	// ListView which contains last news
	private ListView listView;
	// Refresh menu item
	private MenuItem refreshItem;

	/**
	 * Main method of fragment. Creates the view which will be attached to the
	 * activity
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard, null, false);
		return view;
	}

	/**
	 * Add specific fragment's menu
	 * 
	 * @param menu
	 * @param inflater
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
		refreshItem = menu.findItem(R.id.menu_refresh);
		super.onCreateOptionsMenu(menu, inflater);

	}

	/**
	 * Handle menu items click
	 * 
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				refresh();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Refresh the dashboard : numbers of new items in each category and also the last news
	 */
	private void refresh() {
	
	}
}
