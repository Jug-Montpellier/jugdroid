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
public abstract class MemberListTask extends ToastableTask<Integer, ArrayList<Speaker>> {

	public MemberListTask(Activity activityP) {
		super(activityP);
	}

	@Override
	public final ArrayList<Speaker> onExecute() throws BackendException {
		// Call the REST service
		ArrayList<Speaker> speakers = RestClient.getList(Speaker.class, "/api/members.json");
		return speakers;
	}

}
