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
import org.jug.montpellier.app.jugdroid.models.Partner;
import org.jug.montpellier.app.jugdroid.ui.adapter.PartnersAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * Shows the partners list
 * 
 * @author etaix
 */
@EActivity 
public class PartnersActivity extends JugActivity implements OnItemClickListener {

	@ViewById(R.id.list)
	ListView listView;
	
	// Partners adapter
	private PartnersAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new PartnersAdapter(this); 
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
		// Update members list
		setLoading(true);
		updatePartners();
	}

	/**
	 * Set the content view as we can't set it using EActivity annotation
	 * because GreenDroid doesn't use the conventionnal #setContentView method
	 */
	@BeforeCreate
	public void onBeforeCreate() {
		setActionBarContentView(R.layout.partners_list);
	}
		
	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        final Partner partner = (Partner) adapter.getItem(position);
        Intent intent = new Intent(this, PartnerDetailActivity_.class);
        intent.putExtra(PartnerDetailActivity.PARTNER_EXTRA, partner);
        startActivity(intent);
	}

	/**
	 * Get partners list
	 */
	@Background
	void updatePartners() {
		ArrayList<Partner> partners = null;
		// Call the REST service
		try {
			String jsonStr = RestClient.send("http://192.168.2.13:9000/api/partners/currentyear.json");
			if (jsonStr != null) {
				JsonFactory jsonFactory = new JsonFactory();
				JsonParser jp = jsonFactory.createJsonParser(jsonStr);
				ObjectMapper objectMapper = new ObjectMapper();
				// Parse response
				partners = objectMapper.readValue(jp, new TypeReference<ArrayList<Partner>>() {
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
			updatePartnersUI(partners);
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
		updatePartners();
	}

	/**
	 * Update members list UI. Can't use a ArrayList<Speaker> as
	 * AndroiAnnotations is not able to use generic list/
	 */
	@SuppressWarnings("unchecked")
	@UiThread
	void updatePartnersUI(ArrayList partners) {
		adapter.setPartners(partners);
		setLoading(false);
	}
}
