package org.jug.montpellier.app.jugdroid;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class WorkerThread {
private ImageView myView;
	
	
	
	public void onClick(View v) {
	    new Thread(new Runnable() {
	        public void run() {
	            // Do the job here
	        	final Bitmap bitmap = null;
	            myView.post(new Runnable() {
	                public void run() {
	                    myView.setImageBitmap(bitmap);
	                }
	            });
	        }
	    }).start();
	}
}
