/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

/**
 * @author Eric Taix
 *
 */

import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.ui.Animation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoader {

	// The in memory cache
	private MemoryCache memoryCache = new MemoryCache();
	// The thread which load image asynchronously
	private ImageLoaderThread photoLoaderThread = new ImageLoaderThread();
	// 
	private ImageQueue imageQueue = new ImageQueue();
	// A map with ImageView/image_url
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	// Default drawable in case we are not able to decode the url
	final int stub_id = R.drawable.user;
	
	public ImageLoader(Context context) {
		// Make the background thread low priority
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
	}

	/**
	 * Displays an image in an ImageView
	 * 
	 * @param url
	 * @param activity
	 * @param imageView
	 */
	public void displayImage(String url, Activity activity, ImageView imageView, boolean animate) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		// If bitmap exists in the cache then display it immediately
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		}
		else {
			addTask(url, activity, imageView, animate);
		}
	}

	private void addTask(String url, Activity activity, ImageView imageView, boolean animate) {
		// This ImageView may be used for other images before. So there may be
		// some old tasks in the queue. We need to discard them.
		imageQueue.clean(imageView);
		ImageTask p = new ImageTask(url, imageView, animate);
		synchronized (imageQueue.imageTasks) {
			imageQueue.imageTasks.push(p);
			imageQueue.imageTasks.notifyAll();
		}

		// start thread if it's not started yet
		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	public void stopThread() {
		photoLoaderThread.interrupt();
	}

	/**
	 * Clear the cache
	 */
	public void clearCache() {
		memoryCache.clear();
	}

	// ======================== Inner Classes ==============================

	/**
	 * Task for the queue
	 */
	private class ImageTask {
		public String url;
		public ImageView imageView;
		public boolean animate;

		public ImageTask(String urlP, ImageView imgP, boolean animateP) {
			url = urlP;
			imageView = imgP;
			animate = animateP;
		}
	}

	/**
	 * Stores list of photos to download
	 */
	private class ImageQueue {
		private Stack<ImageTask> imageTasks = new Stack<ImageTask>();

		public void clean(ImageView image) {
			for (int iLoop = 0; iLoop < imageTasks.size();) {
				if (imageTasks.get(iLoop).imageView == image)
					imageTasks.remove(iLoop);
				else
					iLoop++;
			}
		}
	}

	/**
	 * The thread which will load the image
	 */
	private class ImageLoaderThread extends Thread {

		private Bitmap getBitmap(String url) {
			try {
				URL imageUrl = new URL(url);
				Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openStream(), null, null);
				return bitmap;
			}
			catch (Exception ex) {
				Log.e("jugdroid", "Unable to create the bitmap from URL: " + url, ex);
				return null;
			}
		}

		public void run() {
			try {
				while (true) {
					// Thread waits until there are any images to load in the queue
					if (imageQueue.imageTasks.size() == 0) {
						synchronized (imageQueue.imageTasks) {
							imageQueue.imageTasks.wait();
						}
					}
					// If there's at least one image to load
					if (imageQueue.imageTasks.size() != 0) {
						ImageTask task;
						synchronized (imageQueue.imageTasks) {
							task = imageQueue.imageTasks.pop();
						}
						Bitmap bmp = getBitmap(task.url);
						memoryCache.put(task.url, bmp);
						String tag = imageViews.get(task.imageView);
						if (tag != null && tag.equals(task.url)) {
							ImageDisplayer bd = new ImageDisplayer(bmp, task.imageView, task.animate);
							Activity activity = (Activity) task.imageView.getContext();
							// Update the image on the UI thread
							activity.runOnUiThread(bd);
						}
					}
					if (Thread.interrupted())
						break;
				}
			}
			catch (InterruptedException e) {
				// Allow thread to exit
			}
		}
	}

	/**
	 * Used to display bitmap in the UI thread
	 * 
	 * @author Eric Taix
	 */
	private class ImageDisplayer implements Runnable {
		Bitmap bitmap;
		ImageView imageView;
		boolean animate;

		public ImageDisplayer(Bitmap b, ImageView i, boolean a) {
			bitmap = b;
			imageView = i;
			animate = a;
		}

		public void run() {
			if (animate) {
				Animation.alpha(imageView, 1.0f, 0.0f, 300, 0);
			}
			if (bitmap != null)
				imageView.setImageBitmap(bitmap);
//			else
//				imageView.setImageResource(stub_id);
			if (animate) {
				Animation.alpha(imageView, 0.0f, 1.0f, 800, 0);
			}
		}
	}

}