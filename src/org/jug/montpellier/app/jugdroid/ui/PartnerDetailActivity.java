/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import greendroid.app.GDActivity;
import greendroid.widget.AsyncImageView;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Partner;

import android.os.Bundle;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * Shows partner details. This class does not inherit from JugActivity as there's
 * no need to show a refresh item
 * 
 * @author etaix
 */
@EActivity
public class PartnerDetailActivity extends GDActivity {

	public static final String PARTNER_EXTRA = "partnerExtra";

	@ViewById(R.id.name)
	TextView nameView;
	@ViewById(R.id.url)
	TextView websiteUrlView;
	@ViewById(R.id.description)
	TextView descriptionView;
	@ViewById(R.id.async_image)
	AsyncImageView imageView;

	// The speaker to display details from
	@Extra(PARTNER_EXTRA)
	Partner partner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (partner != null) {
			// Set fullname, job position, company
			nameView.setText(partner.name);
			websiteUrlView.setText(partner.websiteUrl);
			descriptionView.setText(partner.description);
			// Set the image URL which will ne download in the background
			if (partner.logoURL != null && partner.logoURL.length() > 0) {
				imageView.setUrl(partner.logoURL);
			}
		}
	}

	/**
	 * Set the content view as we can't set it using EActivity annotation
	 * because GreenDroid doesn't use the conventionnal #setContentView method
	 */
	@BeforeCreate
	public void onBeforeCreate() {
		setActionBarContentView(R.layout.activity_partner_details);
	}
}
