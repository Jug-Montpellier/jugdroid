/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.ui.adapter.EventAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Shows the members list
 * 
 * @author etaix
 */
public class EventsActivity extends FragmentActivity {

	// Events adapter
	private EventAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new EventAdapter(this); 
//		setListAdapter(adapter);
//		// Update members list
//		setLoading(true);
//		updateEvents();
	}

	/**
	 * The user has clicked on a member to see details
	 */
//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        final Speaker speaker = (Speaker) adapter.getItem(position);
//        Intent intent = new Intent(this, MemberDetailActivity_.class);
//        intent.putExtra(MemberDetailActivity.SPEAKER_EXTRA, speaker);
//        startActivity(intent);
//    }
//	
//	/**
//	 * Get JUG members list
//	 */
//	@Background
//	void updateEvents() {
//		ArrayList<Event> events = null;
//		// Call the REST service
//		try {
//			String jsonStr = RestClient.send("/api/events/all.json");
//			if (jsonStr != null) {
//				JsonFactory jsonFactory = new JsonFactory();
//				JsonParser jp = jsonFactory.createJsonParser(jsonStr);
//				ObjectMapper objectMapper = new ObjectMapper();
//				// Parse response
//				events = objectMapper.readValue(jp, new TypeReference<ArrayList<Event>>() {
//				});
//			}
//		}
//		catch (JSONException e) {
//			toastMessage((String) getText(R.string.error_getting_information), Toast.LENGTH_LONG);
//			Log.e("JugDroid", "Error while parsing JSON response", e);
//		}
//		catch (Exception e) {
//			// Other exceptions have already been logged
//			toastMessage((String) getText(R.string.error_getting_information), Toast.LENGTH_LONG);
//		}
//		finally {
//			updateEventsUI(events);
//		}
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.jug.montpellier.app.jugdroid.ui.JugActivity#refresh()
//	 */
//	@Override
//	public void refresh() {
//		setLoading(true);
//		updateEvents();
//	}
//
//	/**
//	 * Update members list UI. Can't use a ArrayList<Speaker> as
//	 * AndroiAnnotations is not able to use generic list/
//	 */
//	@SuppressWarnings("unchecked")
//	@UiThread
//	void updateEventsUI(ArrayList events) {
//		adapter.setEvents(events);
//		setLoading(false);
//	}
}
