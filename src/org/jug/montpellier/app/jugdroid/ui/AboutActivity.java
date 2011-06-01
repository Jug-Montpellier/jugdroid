package org.jug.montpellier.app.jugdroid.ui;

import greendroid.app.GDActivity;

import org.jug.montpellier.app.jugdroid.R;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * This activity displays informations about jugdroid and also about the JUG Montpellier
 * @author eric
 */
public class AboutActivity extends GDActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarContentView(R.layout.about);
        
        final TextView aboutText = (TextView) findViewById(R.id.about);
        aboutText.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
}
