/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core.tasks;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.core.BackendException;
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.Speaker;

import android.app.Activity;

/**
 * This task retrieves members list
 * @author Eric Taix
 */
public abstract class SpeakerListTask extends ToastableTask<Integer, ArrayList<Speaker>> {

	public SpeakerListTask(Activity activityP) {
		super(activityP);
	}

	@Override
	public final ArrayList<Speaker> onExecute() throws BackendException {
		// Call the REST service
		ArrayList<Speaker> speakers = RestClient.getList(Speaker.class, "/api/speakers/all.json");
		return speakers;
	}

}
