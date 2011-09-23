/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core.tasks;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.core.BackendException;
import org.jug.montpellier.app.jugdroid.core.RestClient;
import org.jug.montpellier.app.jugdroid.models.News;

import android.app.Activity;

/**
 * This task retrieves news list
 * @author Eric Taix
 */
public abstract class NewsListTask extends ToastableTask<Integer, ArrayList<News>> {

	public NewsListTask(Activity activityP) {
		super(activityP);
	}

	@Override
	public final ArrayList<News> onExecute() throws BackendException {
		// Call the REST service
		ArrayList<News> news = RestClient.getList(News.class, "/api/news/all.json");
		return news;
	}

}
