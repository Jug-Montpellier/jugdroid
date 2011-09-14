package org.jug.montpellier.app.jugdroid.ui;


import org.jug.montpellier.app.jugdroid.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.WebView;

/**
 * An activity which displays informations from an URL.<br/>
 * The URL can be an external URL (a web site URL) or an internal URL (typically an asset)
 * @author Eric Taix
 *
 */
public class WebContentActivity extends FragmentActivity {

    public static final String EXTRA_CONTENT_URL = "CONTENT_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String contentUrl = getIntent().getStringExtra(EXTRA_CONTENT_URL);
        if (!TextUtils.isEmpty(contentUrl)) {
            setContentView(R.layout.web_view);
            final WebView webView = (WebView) findViewById(R.id.web_view);
                webView.loadUrl(contentUrl);
        }
    }

}
