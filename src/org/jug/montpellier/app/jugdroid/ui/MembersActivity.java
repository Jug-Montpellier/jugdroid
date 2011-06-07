/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.BackendException;
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;

/**
 * Shows the members list
 * 
 * @author etaix
 */
@EActivity
public class MembersActivity extends JugListActivity {

	// Members adapter
	private MembersAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new MembersAdapter(this);
		setListAdapter(adapter);
		// Update members list
		setLoading(true);
		updateMembers();
	}

	/**
	 * The user has clicked on a member to see details
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		final Speaker speaker = (Speaker) adapter.getItem(position);
		Intent intent = new Intent(this, MemberDetailActivity_.class);
		intent.putExtra(MemberDetailActivity.SPEAKER_EXTRA, speaker);
		startActivity(intent);
	}

	/**
	 * Get JUG members list
	 */
	@Background
	void updateMembers() {
		ArrayList<Speaker> speakers = null;
		try {
			// Call the REST service
			speakers = RestClient.getList(Speaker.class, "/api/members.json");
		}
		catch (BackendException e) {
			toastMessage((String) getText(R.string.error_getting_information), Toast.LENGTH_LONG);
		}
		updateMembersUI(speakers);
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
}
