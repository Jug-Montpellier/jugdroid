/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Eric Taix
 * 
 */
public abstract class ToastableTask<C, D> extends AsyncTask<String, C, D> {

	protected Activity activity;

	public ToastableTask(Activity activityP) {
		activity = activityP;
	}

	@Override
	protected final D doInBackground(final String... toastMsg) {
		try {
			D result = onExecute();
			return result;
		}
		catch (Throwable th) {
			if (toastMsg.length > 0) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							Toast toast = Toast.makeText(activity, toastMsg[0], Toast.LENGTH_SHORT);
							toast.show();
						}
						catch (RuntimeException e) {
							Log.e("JugDroid","A runtime exception was thrown while executing code in the ui thread",e);
						}
					}
				});
			}

		}
		return null;
	}

	@Override
	protected final void onPostExecute(D result) {
		onResult(result);
	}

	public abstract D onExecute() throws Exception;

	public abstract void onResult(D resultP);

}
