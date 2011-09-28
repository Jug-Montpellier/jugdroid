/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.ui.fragment.PartnerListFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;

/**
 * Shows the partners list
 * 
 * @author etaix
 */
@EActivity(R.layout.activity_partner)
public class PartnersActivity extends FragmentActivity {

	/**
	 * Hack due to a Fragment API issue with AndroidAnnotations
	 */
	@BeforeCreate
	public void onBeforeCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		PartnerListFragment frag = new PartnerListFragment();
		// Execute a transaction, replacing any existing fragment with this one
		// inside the frame.
		getSupportFragmentManager().beginTransaction().replace(
				R.id.partners_list_frame, frag).commit();
	}

}
