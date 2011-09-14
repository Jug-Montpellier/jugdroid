package org.jug.montpellier.app.jugdroid.ui;


import org.jug.montpellier.app.jugdroid.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * This activity displays informations about jugdroid and also about the JUG Montpellier
 * @author Eric Taix
 */
public class AboutActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);
        
        final TextView aboutText = (TextView) findViewById(R.id.about);
        aboutText.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
}
