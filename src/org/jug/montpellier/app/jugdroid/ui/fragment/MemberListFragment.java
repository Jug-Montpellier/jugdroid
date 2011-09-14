/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.fragment;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.tasks.MemberListTask;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.Animation;
import org.jug.montpellier.app.jugdroid.ui.MemberDetailActivity;
import org.jug.montpellier.app.jugdroid.ui.MemberDetailActivity_;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersLightAdapter;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Fragment for members list
 * 
 * @author Eric Taix
 */
public class MemberListFragment extends Fragment implements OnItemClickListener {

	private static final String SAVE_INDEX = "index";
	private static final String SAVE_MEMBERS = "members";
	// Current selected index
	private int positionChecked = 0;
	// Current shown index
	private int positionShown = -1;
	// Members adapter
	private MembersAdapter memberAdapter;
	// A flag to know if we have a the detail frame
	private boolean detailFrame = false;
	private ListView listView;
	// Refresh menu item
	private MenuItem refreshItem;
	// The details fragment container (useful for animation purpose)
	private View detailContainer;
	
	
	/**
	 * Main method of fragment. Creates the view which will be attached to the
	 * activity
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		listView = new ListView(getActivity());
		// Set up that this fragment has a menu (onCreateOptionsMenu will be called)
		setHasOptionsMenu(true);
		return listView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		detailContainer = getActivity().findViewById(R.id.member_detail_frame);
		detailFrame = (detailContainer != null) && (detailContainer.getVisibility() == View.VISIBLE);

		// We are in dual-pane mode
		if (detailFrame) {
			memberAdapter = new MembersLightAdapter(getActivity());
			// In dual-pane mode, the list view highlights the selected item.
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(positionChecked);
		}
		else {
			memberAdapter = new MembersAdapter(getActivity());
		}
		listView.setAdapter(memberAdapter);
		listView.setOnItemClickListener(this);

		// Try to restore the last state
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			positionChecked = savedInstanceState.getInt(SAVE_INDEX, 0);
			ArrayList<Speaker> speakers = savedInstanceState.getParcelableArrayList(SAVE_MEMBERS);
			memberAdapter.setMembers(speakers);
		}
		// If this fragment has not been saved then we have to update members
		else {
			updateMembers();
		}
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
				updateMembers();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Handle list item click and show details
	 */
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		showDetails(position);
	}

	/**
	 * Save the current index, then fragment will be able to restore it
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVE_INDEX, positionChecked);
		outState.putParcelableArrayList(SAVE_MEMBERS, memberAdapter.getMembers());
	}

	/**
	 * Get JUG members list
	 */
	void updateMembers() {
		// Show progress indicator
		refreshItem.setActionView(R.layout.refresh_indicator);
		new MemberListTask(getActivity()) {
			@Override
			public void onResult(ArrayList<Speaker> resultP) {
				memberAdapter.setMembers(resultP);
				// Hide progress indicator
				refreshItem.setActionView(null);
				// Shows again the detail if we are in dual-mode
				if (detailFrame) {
					showDetails(positionChecked);
				}
			}
		}.execute(getText(R.string.error_getting_information).toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jug.montpellier.app.jugdroid.ui.JugActivity#refresh()
	 */
	public void refresh() {
		// setLoading(true);
		updateMembers();
	}

	/**
	 * Shows details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	private void showDetails(int indexP) {
		positionChecked = indexP;
		Speaker speaker = (Speaker) memberAdapter.getItem(positionChecked);

		if (detailFrame) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show data.
			listView.setItemChecked(positionChecked, true);
			if (positionChecked != positionShown && speaker != null) {
				MemberDetailsFragment fragment = MemberDetailsFragment.newInstance(speaker);
				// Execute a transaction, replacing any existing fragment with this
				// one inside the frame.
				Animation.alpha(detailContainer, 1.0f, 0.0f, 3000, 0);
				getFragmentManager().beginTransaction()
				                    .replace(R.id.member_detail_frame, fragment)
				                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				                    .commit();
				Animation.alpha(detailContainer, 0.0f, 1.0f, 3000, 0);
				positionShown = positionChecked;
			}
		}
		// We are not in dual-mode: start a new intent
		else {
			Intent intent = new Intent(getActivity(), MemberDetailActivity_.class);
			intent.putExtra(MemberDetailActivity.MEMBER_EXTRA, (Parcelable) speaker);
			startActivity(intent);
		}
	}
}