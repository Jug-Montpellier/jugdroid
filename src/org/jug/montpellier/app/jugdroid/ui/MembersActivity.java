/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import java.util.ArrayList;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.MemberListFragment.SpeakerSelectedListener;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;

/**
 * Shows the members list
 * 
 * @author etaix
 */
@EActivity
public class MembersActivity extends JugActivity implements SpeakerSelectedListener {

	/**
	 * Set the content view as we can't set it using EActivity annotation
	 * because GreenDroid doesn't use the conventionnal #setContentView method.<br/>
	 */
	@BeforeCreate
	public void onBeforeCreate() {
		// Load the layout according to the current orientation layout. See layout/activity_members.xml et layout-land/activity-member.xml
		setActionBarContentView(R.layout.activity_member);
	}
	

	@Override
	public void onListItemSelected(Speaker speaker, int index) {
        Intent intent = new Intent(this, MemberDetailActivity_.class);
        intent.putExtra("speaker", (Parcelable)speaker);
        startActivity(intent);
    }
	
	/**
	 * Get JUG members list
	 */
	@Background
	void updateMembers() {
		ArrayList<Speaker> speakers = null;
		// Call the REST service
		try {
			String jsonStr = RestClient.send("/api/members/all.json");
			if (jsonStr != null) {
				JsonFactory jsonFactory = new JsonFactory();
				JsonParser jp = jsonFactory.createJsonParser(jsonStr);
				ObjectMapper objectMapper = new ObjectMapper();
				// Parse response
				speakers = objectMapper.readValue(jp, new TypeReference<ArrayList<Speaker>>() {
				});
			}
		}
		catch (JSONException e) {
			toastMessage((String) getText(R.string.error_getting_information), Toast.LENGTH_LONG);
			Log.e("JugDroid", "Error while parsing JSON response", e);
		}
		catch (Exception e) {
			// Other exceptions have already been logged
			toastMessage((String) getText(R.string.error_getting_information), Toast.LENGTH_LONG);
		}
		finally {
			updateMembersUI(speakers);
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
}
