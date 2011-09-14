/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core.tasks;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.core.BackendException;
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.Partner;
import org.jug.montpellier.app.jugdroid.models.Speaker;

import android.app.Activity;

/**
 * This task retrieves partners list
 * @author Eric Taix
 */
public abstract class PartnersListTask extends ToastableTask<Integer, ArrayList<Partner>> {

	public PartnersListTask(Activity activityP) {
		super(activityP);
	}

	@Override
	public final ArrayList<Partner> onExecute() throws BackendException {
		// Call the REST service
		ArrayList<Partner> partners = RestClient.getList(Partner.class, "/api/partners/currentyear.json");
		return partners;
	}

}
