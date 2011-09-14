/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Partner;
import org.jug.montpellier.app.jugdroid.ui.fragment.PartnerDetailsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItem;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * Shows partner details. This class does not inherit from JugActivity as
 * there's no need to show a refresh item
 * 
 * @author etaix
 */
@EActivity
public class PartnerDetailActivity extends FragmentActivity {

	public static final String PARTNER_EXTRA = "partner";

	@ViewById(R.id.name)
	TextView nameView;
	@ViewById(R.id.url)
	TextView websiteUrlView;
	@ViewById(R.id.description)
	TextView descriptionView;
	// @ViewById(R.id.async_image)
	// AsyncImageView imageView;

	// The speaker to display details from
	@Extra(PARTNER_EXTRA)
	Partner partner;

	/**
	 * Hack a Fragment API issue with AndroidAnnotations
	 */
	@BeforeCreate
	public void onBeforeCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Don't call super.onCreate() here.

		if (savedInstanceState == null) {
			// During initial setup, plug in the details fragment.
			PartnerDetailsFragment details = new PartnerDetailsFragment();
			details.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
		}

		// Show the up button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, PartnersActivity_.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}
}
