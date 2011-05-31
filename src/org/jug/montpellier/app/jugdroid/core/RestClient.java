/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

/**
 * This is a helper class which provides convenient methods to request REST APIs
 * from PlayJUG
 * 
 * @author etaix
 */
public class RestClient {

	/**
	 * To convert the InputStream to String we use the BufferedReader.readLine()
	 * method. We iterate until the BufferedReader return null which means
	 * there's no more data to read. Each line will appended to a StringBuilder
	 * and returned as String.
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e("JugDroid","Error while converting JSON stream to response");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.e("JugDroid","Error while closing JSON stream");
			}
		}
		return sb.toString();
	}

	/**
	 * This method connects to a REST service and returns it's response.<br/>
	 * This method may return null if no response has been received or if an
	 * error occured
	 */
	public static String send(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Execute the request
		HttpResponse response;
		try {
			Log.d("JudDroid", url);
			response = httpclient.execute(httpget);
			Log.d("JudDroid", response.toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {
				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				Log.d("JugDroid", "<jsonobject>\n" + result + "\n</jsonobject>");

				// Closing the input stream will trigger connection release
				instream.close();
				return result;
			}
			return null;
		} catch (Exception e) {
			Log.e("JugDroid", "Error while requesting REST service", e);
			throw e;
		}
	}

}
