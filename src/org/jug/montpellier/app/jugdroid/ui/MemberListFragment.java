/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersAdapter;

import com.googlecode.androidannotations.annotations.UiThread;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

/**
 * Fragment for members list
 * 
 * @author eric
 */
public class MemberListFragment extends ListFragment {

	// Current selected index
	private int index = 0;
	// Members adapter
	private MembersAdapter adapter;

	private SpeakerSelectedListener selectedListener;

	/**
	 * Handle item click and delegate this event to the attached activity
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		index = position;
		Speaker speaker = (Speaker)adapter.getItem(index);
		selectedListener.onListItemSelected(speaker, position);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new MembersAdapter(getActivity());
		setListAdapter(adapter);
		// If not null the restore previous state
		if (savedInstanceState != null) {
			index = savedInstanceState.getInt("index", 0);
			Speaker speaker = savedInstanceState.getParcelable("speaker");
			selectedListener.onListItemSelected(speaker, index);
		}
	}

	/**
	 * Save the current index, then fragment will be able to restore it
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("index", index);
		outState.putParcelable("speaker", (Speaker)adapter.getItem(index));
	}

	/**
	 * Attach this fragment to the activity parameter
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			selectedListener = (SpeakerSelectedListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ListItemSelectedListener in Activity");
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jug.montpellier.app.jugdroid.ui.JugActivity#refresh()
	 */
	@Override
	public void refresh() {
		setLoading(true);
		updateMembers();
	}

	/**
	 * Update members list UI. Can't use a ArrayList<Speaker> as
	 * AndroiAnnotations is not able to use generic list/
	 */
	@SuppressWarnings("unchecked")
	@UiThread
	void updateMembersUI(ArrayList speakers) {
		adapter.setMembers(speakers);
		setLoading(false);
	}
	
	public interface SpeakerSelectedListener {
		public void onListItemSelected(Speaker speaker, int index);
	}
}