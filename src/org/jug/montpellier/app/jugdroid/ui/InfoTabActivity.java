package org.jug.montpellier.app.jugdroid.ui;

import greendroid.app.ActionBarActivity;
import greendroid.app.GDTabActivity;

import org.jug.montpellier.app.jugdroid.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * This activity displays informations about jugdroid. It has 2 sub-activites : AboutActivity and WebContentActivity
 * @author eric
 */
public class InfoTabActivity extends GDTabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.info);
        
        final String aboutText =  getString(R.string.about);
        final Intent aboutIntent = new Intent(this, AboutActivity.class);
        aboutIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
        addTab(aboutText, aboutText, aboutIntent);

        final String licenseText =  getString(R.string.license);
        final Intent licenseIntent = new Intent(this, WebContentActivity.class);
        licenseIntent.putExtra(ActionBarActivity.GD_ACTION_BAR_VISIBILITY, View.GONE);
        licenseIntent.putExtra(WebContentActivity.EXTRA_CONTENT_URL, "file:///android_asset/LICENSE.txt");
        addTab(licenseText, licenseText, licenseIntent);
    }

    public void onAppUrlClicked(View v) {
        final Uri appUri = Uri.parse(getString(R.string.app_url));
        startActivity(new Intent(Intent.ACTION_VIEW, appUri));
    }
}
