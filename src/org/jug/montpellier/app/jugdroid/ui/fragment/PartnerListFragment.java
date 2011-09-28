/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.fragment;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.tasks.PartnersListTask;
import org.jug.montpellier.app.jugdroid.models.Partner;
import org.jug.montpellier.app.jugdroid.ui.Animation;
import org.jug.montpellier.app.jugdroid.ui.PartnerDetailActivity;
import org.jug.montpellier.app.jugdroid.ui.PartnerDetailActivity_;
import org.jug.montpellier.app.jugdroid.ui.adapter.PartnersLightAdapter;
import org.jug.montpellier.app.jugdroid.ui.adapter.PartnersAdapter;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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
 * Fragment for partners list
 * 
 * @author Eric Taix
 */
public class PartnerListFragment extends Fragment implements OnItemClickListener {

	private static final String SAVE_INDEX = "index";
	private static final String SAVE_PARTNERS = "partners";
	// Current selected index
	private int positionChecked = 0;
	// Current shown index
	private int positionShown = -1;
	// Partners adapter
	private PartnersAdapter partnerAdapter;
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
		detailContainer = getActivity().findViewById(R.id.partner_detail_frame);
		detailFrame = (detailContainer != null) && (detailContainer.getVisibility() == View.VISIBLE);

		// We are in dual-pane mode
		if (detailFrame) {
			partnerAdapter = new PartnersLightAdapter(getActivity());
			// In dual-pane mode, the list view highlights the selected item.
			listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			showDetails(positionChecked);
		}
		else {
			partnerAdapter = new PartnersAdapter(getActivity());
		}
		listView.setAdapter(partnerAdapter);
		listView.setOnItemClickListener(this);

		// Try to restore the last state
		if (savedInstanceState != null) {
			// Restore last state for checked position.
			positionChecked = savedInstanceState.getInt(SAVE_INDEX, 0);
			ArrayList<Partner> partners = savedInstanceState.getParcelableArrayList(SAVE_PARTNERS);
			partnerAdapter.setPartners(partners);
		}
		// If this fragment has not been saved then we have to update partners
		else {
			updatePartners();
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
				updatePartners();
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
		outState.putParcelableArrayList(SAVE_PARTNERS, partnerAdapter.getPartners());
	}

	/**
	 * Get JUG partners list
	 */
	void updatePartners() {
		// Show progress indicator
		refreshItem.setActionView(R.layout.refresh_indicator);
		new PartnersListTask(getActivity()) {
			@Override
			public void onResult(ArrayList<Partner> resultP) {
				partnerAdapter.setPartners(resultP);
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
		updatePartners();
	}

	/**
	 * Shows details of a selected item, either by
	 * displaying a fragment in-place in the current UI, or starting a whole new
	 * activity in which it is displayed.
	 */
	private void showDetails(int indexP) {
		positionChecked = indexP;
		Partner partner = (Partner) partnerAdapter.getItem(positionChecked);

		if (detailFrame) {
			// We can display everything in-place with fragments, so update
			// the list to highlight the selected item and show data.
			listView.setItemChecked(positionChecked, true);
			if (positionChecked != positionShown && partner != null) {
				PartnerDetailsFragment fragment = PartnerDetailsFragment.newInstance(partner);
				// Execute a transaction, replacing any existing fragment with this
				// one inside the frame.
				Animation.alpha(detailContainer, 1.0f, 0.0f, 3000, 0);
				getFragmentManager().beginTransaction()
				                    .replace(R.id.partner_detail_frame, fragment)
				                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				                    .commit();
				Animation.alpha(detailContainer, 0.0f, 1.0f, 3000, 0);
				positionShown = positionChecked;
			}
		}
		// We are not in dual-mode: start a new intent
		else {
			Intent intent = new Intent(getActivity(), PartnerDetailActivity_.class);
			intent.putExtra(PartnerDetailActivity.PARTNER_EXTRA, (Parcelable) partner);
			startActivity(intent);
		}
	}
}