package org.jug.montpellier.app.jugdroid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyActivity extends Activity {

	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		LinearLayout layout = new LinearLayout(this);
	    layout.setOrientation(LinearLayout.VERTICAL);
	    
	    TextView label = new TextView(this);
	    label.setText("Firstname");
	    EditText firstName = new EditText(this);
	    
	    layout.addView(label);
	    layout.addView(firstName);
	    
	    setContentView(layout);
	}

}
