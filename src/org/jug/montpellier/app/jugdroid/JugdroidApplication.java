/**
 * 
 */
package org.jug.montpellier.app.jugdroid;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
 


/**
 * Application class which initialize ACRA
 * @author etaix
 */
@ReportsCrashes(
  formKey = "dF8wYXJuSHY2cDVCRHlWYXp0bWxwSlE6MQ", 
  mode = ReportingInteractionMode.TOAST, 
  resToastText = R.string.crash_toast_text
  )
public class JugdroidApplication extends Application {
	@Override
	public void onCreate() {
		// The following line triggers the initialization of ACRA
		ACRA.init(this);
		super.onCreate();
	}
}
