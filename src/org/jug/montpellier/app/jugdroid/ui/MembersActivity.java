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
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.adapter.MembersAdapter;

import android.os.Bundle;
import android.util.Log;
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
	 * Get JUG members list
	 */
	@Background
	void updateMembers() {
		ArrayList<Speaker> speakers = null;
		// Call the REST service
		try {
			String jsonStr = RestClient.send("http://192.168.2.13:9000/api/members.json");
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
			toastMessage("Error while retrieving JUG members", Toast.LENGTH_LONG);
			Log.e("JugDroid", "Error while parsing JSON response", e);
		}
		catch (Exception e) {
			// Other exceptions have already been logged
			toastMessage("Error while retrieving JUG members. Try again later.", Toast.LENGTH_LONG);
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