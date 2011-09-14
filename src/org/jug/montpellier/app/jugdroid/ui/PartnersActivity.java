/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.R;

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
	}

}
