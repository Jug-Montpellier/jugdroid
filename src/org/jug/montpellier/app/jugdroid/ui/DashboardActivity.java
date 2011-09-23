package org.jug.montpellier.app.jugdroid.ui;

import java.util.ArrayList;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.core.tasks.NewsListTask;
import org.jug.montpellier.app.jugdroid.models.News;
import org.jug.montpellier.app.jugdroid.ui.adapter.NewsAdapter;
import org.jug.montpellier.app.jugdroid.ui.widget.NewInfoButton;
import org.jug.montpellier.app.jugdroid.ui.widget.NewInfoProvider;
import org.jug.montpellier.app.jugdroid.ui.widget.RandomNewInfoProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.Menu;
import android.support.v4.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * The Dashboard activity (the main activity).
 * 
 * @author etaix
 */
@EActivity(R.layout.activity_home)
public class DashboardActivity extends FragmentActivity {

	private static final String SAVE_NEWS = "news";
	private static final String SAVE_OPENED = "opened";
	
	// Define Dashboard buttons
	@ViewById(R.id.home_btn_sessions)
	NewInfoButton sessions;
	@ViewById(R.id.home_btn_partners)
	NewInfoButton partners;
	@ViewById(R.id.home_btn_members)
	NewInfoButton members;
	@ViewById(R.id.home_btn_about)
	NewInfoButton about;
	@ViewById(R.id.news_list)
	ListView newsList;
	@ViewById(R.id.last_news)
	TextView lastNews;
	@ViewById(R.id.last_date)
	TextView lastDate;
	@ViewById(R.id.slide)
	SlidingDrawer slide;
	@ViewById(R.id.last_news_container)
	LinearLayout lastNewsContainer;

	// Refresh menu item
	private MenuItem refreshItem;
	// News adapter (it contains model items)
	private NewsAdapter newsAdapter;

	// The NewInfoProvider implementation
	private NewInfoProvider infoProvider = new RandomNewInfoProvider();

	/**
	 * Hack de Fragment API issue with AndroidAnnotations
	 */
	@BeforeCreate
	public void onBeforeCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Don't call super.onCreate() here.

		// Add action items
		final ActionBar ab = getSupportActionBar();
		// Set defaults for logo & home up
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayUseLogoEnabled(false);
		// Inject the NewInfoProvider: may be we can do injection with RoboGuice ?
		sessions.setInfoProvider(infoProvider);
		partners.setInfoProvider(infoProvider);
		members.setInfoProvider(infoProvider);

		// Set the news adapter
		newsAdapter = new NewsAdapter(this);
		newsList.setAdapter(newsAdapter);
		// Set list view as transparent
		newsList.setBackgroundColor(0);
		newsList.setCacheColorHint(0);
		// Set the last news selected otherwise the text doesn't marquee
		lastNews.setSelected(true);
		// Set listener to show / hide last news
		slide.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				lastNewsContainer.setVisibility(View.GONE);
				newsList.setVisibility(View.VISIBLE);
			}
		});
		slide.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				newsList.setVisibility(View.GONE);
				lastNewsContainer.setVisibility(View.VISIBLE);
			}
		});
		// Reset last news values
		lastDate.setText("");
		lastNews.setText("");
		
		// Try to restore the last state
		if (savedInstanceState != null) {
			ArrayList<News> ne = savedInstanceState.getParcelableArrayList(SAVE_NEWS);
			// Set also the last news
			updateNews(ne);
			boolean opened = savedInstanceState.getBoolean(SAVE_OPENED);
			if (opened) {
				slide.open();
			}
			else {
				slide.close();
			}
		}
		// If the state has not been saved then we have to update data
		else {
			refresh();
		}
	}

	/**
	 * Save the current index, then fragment will be able to restore it
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(SAVE_NEWS, newsAdapter.getNews());
		outState.putBoolean(SAVE_OPENED, new Boolean(slide.isOpened()));
	}

	
	/**
	 * Add specific fragment's menu
	 * 
	 * @param menu
	 * @param inflater
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		refreshItem = menu.findItem(R.id.menu_refresh);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Handle menu items click
	 * 
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				refresh();
				break;
			case R.id.menu_options:
				Intent intent = new Intent(this, PreferencesActivity.class);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Refresh the dashboard : numbers of new items in each category and also the
	 * last news
	 */
	private void refresh() {
		refreshItem.setActionView(R.layout.refresh_indicator);
		new NewsListTask(this) {
			@Override
			public void onResult(ArrayList<News> resultP) {
				updateNews(resultP);
				// Hide progress indicator
				refreshItem.setActionView(null);

			}
		}.execute(getText(R.string.error_getting_information).toString());
	}

	/**
	 * Update the news UI (list and last news). Also set the adapter's model list the 'result' parameter
	 * @param resultP
	 */
	private void updateNews(ArrayList<News> resultP) {
		if (resultP != null && resultP.size() != 0) {
			// Update last news values
			lastNews.setText(resultP.get(0).title);
			lastDate.setText(resultP.get(0).date);
		}
		else {
			lastNews.setText("");
			lastDate.setText("");					
		}
		// Update the list
		newsAdapter.setNews(resultP);		
	}
	
	@Click(R.id.home_btn_sessions)
	void sessionsClicked() {
		// startActivity(new Intent(this, EventsActivity_.class));
	}

	@Click(R.id.home_btn_partners)
	void partnersClicked() {
		startActivity(new Intent(this, PartnersActivity_.class));
	}

	@Click(R.id.home_btn_members)
	void membersClicked() {
		startActivity(new Intent(this, MembersActivity_.class));
	}

	@Click(R.id.home_btn_about)
	void aboutClicked() {
	}

	/**
	 * Handle click on action items
	 */
	// public boolean onHandleActionBarItemClick(ActionBarItem item, int position)
	// {
	// switch (item.getItemId()) {
	// case R.id.action_bar_refresh:
	// return true;
	// case R.id.action_bar_info:
	// startActivity(new Intent(this, InfoTabActivity.class));
	// return true;
	// default:
	// return super.onHandleActionBarItemClick(item, position);
	// }
	// }

}