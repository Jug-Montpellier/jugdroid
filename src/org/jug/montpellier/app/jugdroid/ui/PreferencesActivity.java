/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * This activity shows the preferences of JugDroid
 * 
 * @author Eric Taix
 */
public class PreferencesActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
