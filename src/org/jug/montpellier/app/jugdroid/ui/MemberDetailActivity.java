/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import greendroid.app.GDActivity;
import greendroid.widget.AsyncImageView;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;

import android.os.Bundle;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * Shows member details. This class does not inherit from JugActivity as there's
 * no need to show a refresh item
 * 
 * @author etaix
 */
@EActivity
public class MemberDetailActivity extends GDActivity {

	public static final String SPEAKER_EXTRA = "speakerExtra";

	@ViewById(R.id.fullname)
	TextView fullNameView;
	@ViewById(R.id.jobposition)
	TextView jobPositionView;
	@ViewById(R.id.company)
	TextView compagnyView;
	@ViewById(R.id.companyURL)
	TextView companyURLView;
	@ViewById(R.id.description)
	TextView descView;
	@ViewById(R.id.personalURL)
	TextView personalView;
	@ViewById(R.id.async_image)
	AsyncImageView imageView;

	// The speaker to display details from
	@Extra(SPEAKER_EXTRA)
	Speaker speaker;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (speaker != null) {
			// Set fullname, job position, company
			fullNameView.setText(speaker.fullName);
			jobPositionView.setText(speaker.activity);
			compagnyView.setText(speaker.company);
			companyURLView.setText(speaker.companyURL);
			descView.setText(speaker.description);
			personalView.setText(speaker.personalURL);
			// Set the image URL which will ne download in the background
			if (speaker.photoUrl != null && speaker.photoUrl.length() > 0) {
				imageView.setUrl(speaker.photoUrl); 
			}
		}
	}

	/**
	 * Set the content view as we can't set it using EActivity annotation
	 * because GreenDroid doesn't use the conventionnal #setContentView method
	 */
	@BeforeCreate
	public void onBeforeCreate() {
		setActionBarContentView(R.layout.activity_member_details);
	}
}
