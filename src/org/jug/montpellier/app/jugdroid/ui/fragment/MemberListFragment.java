/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.fragment;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.tasks.MemberListTask;
import org.jug.montpellier.app.jugdroid.core.tasks.SpeakerListTask;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.Animation;
import org.jug.montpellier.app.jugdroid.ui.MemberDetailActivity;
import org.jug.montpellier.app.jugdroid.ui.MemberDetailActivity_;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersAdapter;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersLightAdapter;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ActionBar.Tab;
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
public class MemberListFragment extends Fragment implements OnItemClickListener, ActionBar.TabListener {

	private static final int ANIMATION_DELAY = 1000;
	private static final String SPEAKER_TAG = "SPEAKER_TAG";
	private static final String MEMBER_TAG = "MEMBER_TAG";

	private static final String SAVE_PEOPLE_INDEX = "index";
	private static final String SAVE_PEOPLE = "people";
	// Current selected index for memberList
	private int currentPosition = 0;
	// Current shown index
	private int positionShown = -1;
	// Members adapter
	private MembersAdapter listAdapter;
	// A flag to know if we have a the detail frame
	private boolean detailFrame = false;
	private ListView listView;
	// Refresh menu item
	private MenuItem refreshItem;
	// The details fragment container (useful for animation purpose)
	private View detailContainer;
	// The container of this fragment
	private View selfContainer;
	// Current actived tab
	private Tab currentTab;

	/**
	 * Main method of fragment. Creates the view which will be attached to the
	 * activity
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		selfContainer = container;
		listView = new ListView(getActivity());
		listView.setDivider(new ColorDrawable(getActivity().getResources().getColor(R.color.medium_gray)));
		listView.setDividerHeight(1);
		listView.setBackgroundColor(0);
		listView.setCacheColorHint(0);
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
			listAdapter = new MembersLightAdapter(getActivity());
			// In dual-pane mode, the list view highlights the selected item.
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(currentPosition);
		}
		else {
			listAdapter = new MembersAdapter(getActivity());
		}
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(this);

		// Try to restore the last state
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			currentPosition = savedInstanceState.getInt(SAVE_PEOPLE_INDEX, 0);
			ArrayList<Speaker> speakers = savedInstanceState.getParcelableArrayList(SAVE_PEOPLE);
			listAdapter.setMembers(speakers);
		}
		// If this fragment has not been saved then we have to update data
		else {
			refresh();
		}
		// Final setup => tabs (members + speakers). Must be done at the
		// finalization state as it will generate a onTabSelected event
		final ActionBar ab = getActivity().getSupportActionBar();
		currentTab = ab.newTab().setTag(MEMBER_TAG).setText(getText(R.string.members)).setTabListener(this);
		ab.addTab(currentTab);
		ab.addTab(ab.newTab().setTag(SPEAKER_TAG).setText(getText(R.string.speakers)).setTabListener(this));
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
		outState.putInt(SAVE_PEOPLE_INDEX, currentPosition);
		outState.putParcelableArrayList(SAVE_PEOPLE, listAdapter.getMembers());
	}

	/**
	 * Shows details of a selected item, either by displaying a fragment in-place
	 * in the current UI, or starting a whole new activity in which it is
	 * displayed.
	 */
	private void showDetails(int indexP) {
		currentPosition = indexP;
		Speaker speaker = (Speaker) listAdapter.getItem(currentPosition);

		if (detailFrame) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show data.
			listView.setItemChecked(currentPosition, true);
			if (currentPosition != positionShown && speaker != null) {
				MemberDetailsFragment fragment = MemberDetailsFragment.newInstance(speaker);
				// Execute a transaction, replacing any existing fragment with this
				// one inside the frame.
				Animation.fadeOut(detailContainer, ANIMATION_DELAY);
				getFragmentManager().beginTransaction().replace(R.id.member_detail_frame, fragment).commit();
				Animation.fadeIn(detailContainer, ANIMATION_DELAY);
				positionShown = currentPosition;
			}
		}
		// We are not in dual-mode: start a new intent
		else {
			Intent intent = new Intent(getActivity(), MemberDetailActivity_.class);
			intent.putExtra(MemberDetailActivity.MEMBER_EXTRA, (Parcelable) speaker);
			startActivity(intent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jug.montpellier.app.jugdroid.ui.JugActivity#refresh()
	 */
	public void refresh() {
		// If currentTab is not available, don't refresh: data will be refreshed
		// soon (onTabSelected event)
		if (currentTab != null) {
			// According to the selected tab's tag, update members
			if (currentTab.getTag().equals(MEMBER_TAG)) {
				updateMembers();
			}
			// Or speakers
			else {
				updateSpeakers();
			}
			// If width's value is 0, it means the selfContainer is not yet fully
			// initialized (in a dimension point of view). So don't rotate it: a
			// FragmentTransaction is taking place
			if (selfContainer.getWidth() != 0) {
				Animation.fadeOutAndRotate(selfContainer, ANIMATION_DELAY);
				Animation.fadeOut(detailContainer, ANIMATION_DELAY);
			}
			else {
				Animation.fadeOut(selfContainer, 10);
				Animation.fadeOut(detailContainer, 10);
			}
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	/**
	 * The user clicked on a Tab
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Animation.fadeOut(detailContainer, ANIMATION_DELAY);
		currentTab = tab;
		refresh();
	}

	/**
	 * Get JUG members list
	 */
	public void updateMembers() {
		refreshItem.setActionView(R.layout.refresh_indicator);
		new MemberListTask(getActivity()) {
			@Override
			public void onResult(ArrayList<Speaker> resultP) {
				listAdapter.setMembers(resultP);
				// Hide progress indicator
				refreshItem.setActionView(null);
				Animation.fadeIn(selfContainer, ANIMATION_DELAY);
				// Shows again the detail if we are in dual-mode
				if (detailFrame) {
					// First reset current position
					positionShown = -1;
					// And show the first position
					showDetails(0);
				}
			}
		}.execute(getActivity().getText(R.string.error_getting_information).toString());
	}

	/**
	 * Get speakers list
	 */
	void updateSpeakers() {
		refreshItem.setActionView(R.layout.refresh_indicator);
		new SpeakerListTask(getActivity()) {
			@Override
			public void onResult(ArrayList<Speaker> resultP) {
				listAdapter.setMembers(resultP);
				// Hide progress indicator
				refreshItem.setActionView(null);
				Animation.fadeIn(selfContainer, ANIMATION_DELAY);
				// Shows again the detail if we are in dual-mode
				if (detailFrame) {
					// First reset current position
					positionShown = -1;
					// And show the first position
					showDetails(0);
				}
			}
		}.execute(getText(R.string.error_getting_information).toString());
	}

}