/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.BackendException;
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.Partner;
import org.jug.montpellier.app.jugdroid.ui.adapter.PartnersAdapter;

import android.content.Intent;
import android.os.Bundle;
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
		try {
			// Call the REST service
			partners = RestClient.getList(Partner.class, "/api/partners/currentyear.json");
		}
		catch (BackendException e) {
			toastMessage((String) getText(R.string.error_getting_information), Toast.LENGTH_LONG);
		}
		updatePartnersUI(partners);
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
